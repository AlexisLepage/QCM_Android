package com.iia.cdsm.myqcm.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;

/**
 * Created by Alex on 11/05/2016.
 */
public class ViewCategoryActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        final ListView lvCategories = (ListView)this.findViewById(R.id.lv_categories);

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this);

        categorySQLiteAdapter.open();
        categorySQLiteAdapter.getAllCategory();
        categorySQLiteAdapter.close();
    }
}
