package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.Fragment.QuestionFragment;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;

import java.util.ArrayList;

/**
 * Created by Alex on 16/06/2016.
 */
public class QuestionsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Bundle extra = getIntent().getExtras();
        Fragment fragmentQuestion = new QuestionFragment();
        Bundle args = new Bundle();
        args.putLong("id", extra.getLong("id"));
        fragmentQuestion.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame_question, fragmentQuestion).commit();

    }
}
