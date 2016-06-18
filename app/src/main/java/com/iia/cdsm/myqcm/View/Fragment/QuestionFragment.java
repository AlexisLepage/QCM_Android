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
import android.widget.ListView;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.CursorAdapter.AnswerCursorAdapter;
import com.iia.cdsm.myqcm.View.CursorAdapter.CategoryCursorAdapter;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;

import java.util.ArrayList;

/**
 * Created by Alex on 17/06/2016.
 */
public class QuestionFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        new LoadTask(this).execute();

        TextView tvTitleQuestion = (TextView) view.findViewById(R.id.tvTitleQuestion);

        Long id = this.getArguments().getLong("id");

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this.getActivity());
        questionSQLiteAdapter.open();
        Question question = questionSQLiteAdapter.getQuestion(id);
        questionSQLiteAdapter.close();

        tvTitleQuestion.setText(question.getTitle());

        return view;
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
            Cursor c = answerSQLiteAdapter.getAllCursor();
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
