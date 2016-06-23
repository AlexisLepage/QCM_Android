package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.Entities.Qcm;
import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.Fragment.QuestionFragment;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 16/06/2016.
 */
public class QuestionsActivity extends Activity{

    int i = 0;
    TextView tvNowQuestion = null;
    TextView tvTimer = null;
    Button btPrevious = null;
    Button btNext = null;
    private static final String FORMAT = "%02d:%02d:%02d";
    boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Bundle extra = getIntent().getExtras();
        btNext = (Button) this.findViewById(R.id.btNext);
        btPrevious = (Button) this.findViewById(R.id.btPrevious);
        tvTimer = (TextView) this.findViewById(R.id.tvTimer);
        tvNowQuestion = (TextView) this.findViewById(R.id.tvNowQuestion);

        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this);
        qcmSQLiteAdapter.open();
        final Qcm qcm = qcmSQLiteAdapter.getQcm(extra.getLong("id"));
        qcmSQLiteAdapter.close();

        Timer(qcm.getDuration());

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        final ArrayList<Question> questions = questionSQLiteAdapter.getQuestionByQcmId(qcm.getId());
        questionSQLiteAdapter.close();
        tvNowQuestion.setText("Question "+(i+1)+"/"+questions.size());

        ChangeFragmentQuestion(questions, i);
        btPrevious.setVisibility(View.GONE);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (questions.size() == i || finished){
                    Intent intent = new Intent(QuestionsActivity.this, AfterQuestionsActivity.class);
                    QuestionsActivity.this.finish();
                    startActivity(intent);
                }else{
                    ChangeFragmentQuestion(questions, i);
                    btPrevious.setVisibility(View.VISIBLE);
                    tvNowQuestion.setText("Question "+(i+1)+"/"+questions.size());
                    if (questions.get(questions.size()-1).equals(questions.get(i))){
                        btNext.setText(R.string.finish);
                    }
                }
            }
        });

        btPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btNext.setText(R.string.next);
                i--;
                ChangeFragmentQuestion(questions, i);
                tvNowQuestion.setText("Question "+(i+1)+"/"+questions.size());
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

    protected void Timer(long time){

        new CountDownTimer(time*60*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                tvTimer.setText(R.string.timer_finished);
                btPrevious.setVisibility(View.GONE);
                btNext.setText(R.string.finish);
                finished = true;
            }
        }.start();
    }
}
