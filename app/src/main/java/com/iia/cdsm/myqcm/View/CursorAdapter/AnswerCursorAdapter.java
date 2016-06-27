package com.iia.cdsm.myqcm.View.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;

/**
 * Created by Alex on 17/06/2016.
 */
public class AnswerCursorAdapter extends CursorAdapter {

    public AnswerCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public AnswerCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_view_answer, parent, false);
        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(context);
        Answer answer = answerSQLiteAdapter.cursorToItem(cursor);
        if (answer.getIs_selected()==1){
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryTactFactory));
        }

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTest = (TextView) view.findViewById(R.id.tvTest);

        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(context);
        Answer answer = answerSQLiteAdapter.cursorToItem(cursor);

        tvTest.setText(answer.getTitle());


    }
}
