package com.iia.cdsm.myqcm.View;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.Category;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;

/**
 * Created by Alex on 11/05/2016.
 */
public class ListCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        new LoadTask(this).execute();

        ListView lv = (ListView)this.findViewById(R.id.lv_categories);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ListQcmActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    public class LoadTask extends AsyncTask<Void, Void, Cursor> {

        private ListCategoryActivity ctx;

        /**
         * Link Context with current activity
         * @param listCategoryActivity
         */
        public LoadTask(ListCategoryActivity listCategoryActivity) {
            this.ctx = listCategoryActivity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... params) {

            CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this.ctx);
            categorySQLiteAdapter.open();
            Cursor c = categorySQLiteAdapter.getAllCursor();
            return c;
        }

        @Override
        protected void onPostExecute(Cursor result) {

            CategoryCursorAdapter adapter = new CategoryCursorAdapter(this.ctx, result, 0);
            ListView lv = (ListView)this.ctx.findViewById(R.id.lv_categories);
            lv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}
