package com.iia.cdsm.myqcm.View.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Qcm;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;

/**
 * Created by Alexis on 17/05/2016.
 */
public class QcmCursorAdapter extends CursorAdapter{
    public QcmCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public QcmCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_view_qcm, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView tvNameQcm = (TextView) view.findViewById(R.id.tvNameQcm);
        final TextView tvDurationQcm = (TextView) view.findViewById(R.id.tvDurationQcm);

        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(context);
        Qcm qcm = qcmSQLiteAdapter.cursorToItem(cursor);

        tvNameQcm.setText(qcm.getName());
        tvDurationQcm.setText(qcm.getDuration().toString() + " min");
    }
}
