package com.iia.cdsm.myqcm.View;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import com.iia.cdsm.myqcm.Entities.Category;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Alex on 11/05/2016.
 */
public class ListCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        new LoadTask(this).execute();
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

            CategoryCusorAdapter adapter = new CategoryCusorAdapter(this.ctx, result, 0);
            ListView lv = (ListView)this.ctx.findViewById(R.id.lv_categories);
            lv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}
