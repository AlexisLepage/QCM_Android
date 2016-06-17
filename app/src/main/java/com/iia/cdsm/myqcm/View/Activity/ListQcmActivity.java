package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.CursorAdapter.QcmCursorAdapter;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;

/**
 * Created by Alexis on 17/05/2016.
 */
public class ListQcmActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qcm_list);

        new LoadTask(this).execute();

        ListView lv = (ListView) this.findViewById(R.id.lv_qcms);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), BeforeQuestionsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    public class LoadTask extends AsyncTask<Void, Void, Cursor> {

        private ListQcmActivity ctx;

        /**
         * Link Context with current activity
         * @param listQcmActivity
         */
        public LoadTask(ListQcmActivity listQcmActivity) {
            this.ctx = listQcmActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... params) {

            QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this.ctx);
            qcmSQLiteAdapter.open();
            //récupération extra
            Bundle extra = getIntent().getExtras();
            Long id = extra.getLong("id");
            Cursor c = qcmSQLiteAdapter.getCursorByIdCategory(id);
            return c;
        }

        @Override
        protected void onPostExecute(Cursor result) {

            QcmCursorAdapter adapter = new QcmCursorAdapter(this.ctx, result, 0);
            ListView lv = (ListView)this.ctx.findViewById(R.id.lv_qcms);
            lv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}
