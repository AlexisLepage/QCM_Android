package com.iia.cdsm.myqcm.View.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.CursorAdapter.AnswerCursorAdapter;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;

import java.util.ArrayList;

/**
 * Created by Alex on 17/06/2016.
 */
public class QuestionFragment extends Fragment{
    ListView lvAnswers = null;
    Question question = null;
    long id = 0;
    final ArrayList<Long> longs = new ArrayList<Long>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        id = this.getArguments().getLong("id");

        TextView tvTitleQuestion = (TextView) view.findViewById(R.id.tvTitleQuestion);
        lvAnswers = (ListView) view.findViewById(R.id.lv_answers);

        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this.getActivity());
        answerSQLiteAdapter.open();
        ArrayList<Answer> answersSelected = answerSQLiteAdapter.getAnswerByIdQuestionAndIsSelected(id);
        answerSQLiteAdapter.close();

        if (answersSelected != null){
            for (Answer answer : answersSelected){
                longs.add(answer.getId());
            }
        }

        lvAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean ok = true;
                long idTemp= 0;
                for(long lon : longs){
                    if (lon == id){
                        ok = false;
                    }
                }
                if (ok){
                    parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.colorPrimaryTactFactory));
                    longs.add(id);
                }else {
                    parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    longs.remove(id);
                }
            }
        });




        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this.getActivity());
        questionSQLiteAdapter.open();
        question = questionSQLiteAdapter.getQuestion(id);
        questionSQLiteAdapter.close();

        new LoadTask(this).execute();

        tvTitleQuestion.setText(question.getTitle());

        return view;
    }

    @Override
    public void onDestroyView() {
        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this.getActivity());
        answerSQLiteAdapter.open();
        ArrayList<Answer> answers = answerSQLiteAdapter.getAnswerByIdQuestion(id);

        if (answers != null){
            for (Answer answer : answers){
                answer.setIs_selected(0);
                answerSQLiteAdapter.updateAnswer(answer);
            }
        }

        if (longs != null){
            for(long lon : longs){
                Answer answer = answerSQLiteAdapter.getAnswer(lon);
                answer.setIs_selected(1);
                answerSQLiteAdapter.updateAnswer(answer);
            }
        }

        answerSQLiteAdapter.close();

        super.onDestroyView();
    }

    public class LoadTask extends AsyncTask<Void, Void, Cursor> {

        Context ctx;
        QuestionFragment lca;
        /**
         * Link Context with current activity
         * @param questionFragment
         * questionFragment
         */

        public LoadTask(QuestionFragment questionFragment) {
            this.lca = questionFragment;
            this.ctx = questionFragment.getActivity();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... params) {

            AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this.ctx);
            answerSQLiteAdapter.open();
            Cursor c = answerSQLiteAdapter.getAllCursorByQuestionId(question.getId());
            return c;
        }

        @Override
        protected void onPostExecute(Cursor result) {

            AnswerCursorAdapter adapter = new AnswerCursorAdapter(this.ctx, result, 0);
            ListView lv = (ListView)this.lca.getView().findViewById(R.id.lv_answers);
            lv.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }


}
