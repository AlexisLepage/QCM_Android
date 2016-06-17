package com.iia.cdsm.myqcm.View.CursorAdapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;

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
        return LayoutInflater.from(context).inflate(R.layout.activity_view_answer, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final CheckBox cbAnswer = (CheckBox) view.findViewById(R.id.cbAnswer);

        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(context);
        Answer answer = answerSQLiteAdapter.cursorToItem(cursor);

        cbAnswer.setText(answer.getTitle());
    }
}
