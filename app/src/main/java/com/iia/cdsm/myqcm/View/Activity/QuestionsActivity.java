package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.Entities.Qcm;
import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.Fragment.QuestionFragment;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;
import com.iia.cdsm.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Alex on 16/06/2016.
 */
public class QuestionsActivity extends Activity{

    int i = 0;
    TextView tvNowQuestion = null;
    TextView tvTimer = null;
    ImageView ivPrevious = null;
    ImageView ivNext = null;
    long id = 0;
    private static final String FORMAT = "%02d:%02d:%02d";
    boolean finished = false;
    boolean is_post = false;
    Qcm qcm = new Qcm();

    final QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this);
    final UserWSAdapter userWSAdapter = new UserWSAdapter(QuestionsActivity.this);

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        final Bundle extra = getIntent().getExtras();
        id = extra.getLong("id");

        ivNext = (ImageView) this.findViewById(R.id.ivNext);
        ivPrevious = (ImageView) this.findViewById(R.id.ivPrevious);
        tvTimer = (TextView) this.findViewById(R.id.tvTimer);
        tvNowQuestion = (TextView) this.findViewById(R.id.tvNowQuestion);


        qcmSQLiteAdapter.open();
        qcm = qcmSQLiteAdapter.getQcm(id);
        qcmSQLiteAdapter.close();

        getActionBar().setTitle(qcm.getName());
        Timer(qcm.getDuration());

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        final ArrayList<Question> questions = questionSQLiteAdapter.getQuestionByQcmId(qcm.getId());
        questionSQLiteAdapter.close();
        tvNowQuestion.setText("Question "+(i+1)+"/"+questions.size());

        ChangeFragmentQuestion(questions, i);
        ivPrevious.setVisibility(View.GONE);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (questions.size() == i || finished){
                    postJson();
                    i--;
                }else{
                    ChangeFragmentQuestion(questions, i);
                    ivPrevious.setVisibility(View.VISIBLE);
                    tvNowQuestion.setText("Question "+(i+1)+"/"+questions.size());
                    if (questions.get(questions.size()-1).equals(questions.get(i))){
                        ivNext.setImageResource(R.mipmap.finish);
                    }
                }
            }
        });

        ivPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivNext.setImageResource(R.mipmap.next);
                i--;
                ChangeFragmentQuestion(questions, i);
                tvNowQuestion.setText("Question "+(i+1)+"/"+questions.size());
                if (i == 0){
                    ivPrevious.setVisibility(View.GONE);
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

    @Override
    protected void onDestroy() {
        if (!is_post){
            postJson();
        }
        super.onDestroy();
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
                tvTimer.setTextColor(getResources().getColor(R.color.coloRed));
                ivPrevious.setVisibility(View.GONE);
                ivNext.setImageResource(R.mipmap.finish);
                finished = true;
            }
        }.start();
    }

    protected void postJson (){

        final ProgressDialog progressDialog = new ProgressDialog(QuestionsActivity.this);
        progressDialog.setMessage("Envoi des réponses...");
        progressDialog.show();

        final float note = calculateNote(id);

        sharedPreferences = getSharedPreferences(ConnectionActivity.MYPREFERENCES, MODE_PRIVATE);
        long userId = sharedPreferences.getLong(ConnectionActivity.USER_ID,0);

        RequestParams params = userWSAdapter.itemToParams(id, userId, note);

        is_post = true;

        userWSAdapter.postQcm(params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(QuestionsActivity.this, "ERREUR ENVOI JSON", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                qcm.setIs_done(1);

                QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(QuestionsActivity.this);
                qcmSQLiteAdapter.open();
                qcmSQLiteAdapter.updateQcm(qcm);
                qcmSQLiteAdapter.close();

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Intent intent = new Intent(QuestionsActivity.this, AfterQuestionsActivity.class);
                QuestionsActivity.this.finish();
                intent.putExtra("note", note);
                startActivity(intent);
            }
        });
    }

    protected float calculateNote(long idQcm){
        float note = 0;
        float allPoints = 0;
        Boolean goodAnswer = null;

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this);
        questionSQLiteAdapter.open();
        final ArrayList<Question> questions = questionSQLiteAdapter.getQuestionByQcmId(idQcm);
        questionSQLiteAdapter.close();

        for (Question question : questions){
            AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this);
            answerSQLiteAdapter.open();
            ArrayList<Answer> answers = answerSQLiteAdapter.getAnswerByIdQuestion(question.getId());
            answerSQLiteAdapter.close();

            allPoints = allPoints+question.getValue();
            goodAnswer = true;

            if (answers != null){
                for (Answer answer : answers){
                    if (((answer.getIs_valid() == 1) && (answer.getIs_selected() == 0)) || ((answer.getIs_valid() == 0) && (answer.getIs_selected() == 1))){
                        goodAnswer = false;
                    }
                }
                if (goodAnswer){
                    note = note+question.getValue();
                }
            }
        }

        note = (note*20)/allPoints;

        return (float) Math.round(note * 100) / 100;
    }
}
