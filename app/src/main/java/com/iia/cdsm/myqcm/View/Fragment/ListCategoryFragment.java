package com.iia.cdsm.myqcm.View.Fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.CursorAdapter.CategoryCursorAdapter;
import com.iia.cdsm.myqcm.View.Activity.ListQcmActivity;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;

/**
 * Created by Alex on 11/05/2016.
 */
public class ListCategoryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        new LoadTask(this).execute();

        ListView lv = (ListView) view.findViewById(R.id.lv_categories);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(view.getContext(), ListQcmActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        return view;
    }

    public class LoadTask extends AsyncTask<Void, Void, Cursor> {

        Context ctx;
        ListCategoryFragment lca;
        /**
         * Link Context with current activity
         * @param listCategoryActivity
         * listCategoryActivity
         */

        public LoadTask(ListCategoryFragment listCategoryActivity) {
            this.lca = listCategoryActivity;
            this.ctx = listCategoryActivity.getActivity();
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
            ListView lv = (ListView)this.lca.getView().findViewById(R.id.lv_categories);
            lv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }
}
