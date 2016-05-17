package com.iia.cdsm.myqcm.View;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Category;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;

/**
 * Created by Alex on 11/05/2016.
 */
public class CategoryCursorAdapter extends CursorAdapter{
    public CategoryCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public CategoryCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_view_category, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView tvNameCategory = (TextView) view.findViewById(R.id.tvNameCategory);

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(context);
        Category category = categorySQLiteAdapter.cursorToItem(cursor);

        tvNameCategory.setText(category.getName());
    }
}
