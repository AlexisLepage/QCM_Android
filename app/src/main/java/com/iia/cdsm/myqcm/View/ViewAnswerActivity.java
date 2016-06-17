package com.iia.cdsm.myqcm.View;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;

/**
 * Created by Alex on 17/06/2016.
 */
public class ViewAnswerActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_answer);

        final ListView lvQcms = (ListView)this.findViewById(R.id.lv_answers);

        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this);

        answerSQLiteAdapter.open();
        answerSQLiteAdapter.getAllAnswer();
        answerSQLiteAdapter.close();
    }
}
