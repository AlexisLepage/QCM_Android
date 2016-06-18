package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Bundle extra = getIntent().getExtras();
        final Button btNext = (Button) this.findViewById(R.id.btNext);
        final Button btPrevious = (Button) this.findViewById(R.id.btPrevious);

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        final ArrayList<Question> questions = questionSQLiteAdapter.getQuestionByQcmId(extra.getLong("id"));
        questionSQLiteAdapter.close();

        ChangeFragmentQuestion(questions, i);
        btPrevious.setVisibility(View.GONE);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (questions.size() == i){
                    Intent intent = new Intent(QuestionsActivity.this, AfterQuestionsActivity.class);
                    QuestionsActivity.this.finish();
                    startActivity(intent);
                }else{
                    ChangeFragmentQuestion(questions, i);
                    btPrevious.setVisibility(View.VISIBLE);
                    if (questions.get(questions.size()-1).equals(questions.get(i))){
                        btNext.setText("Terminer");
                    }
                }
            }
        });

        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btNext.setText("Suivant");
                i--;
                ChangeFragmentQuestion(questions, i);
                if (i == 0){
                    btPrevious.setVisibility(View.GONE);
                }

            }
        });

    }

    protected void ChangeFragmentQuestion(ArrayList<Question> questions, int i){

        Fragment fragmentQuestion = new QuestionFragment();
        Bundle args = new Bundle();
        args.putLong("id", questions.get(i).getId());
        fragmentQuestion.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame_question, fragmentQuestion).commit();
    }
}
