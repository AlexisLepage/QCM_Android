package com.iia.cdsm.myqcm.View;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;
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
