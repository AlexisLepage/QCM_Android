package com.iia.cdsm.myqcm.View.Fragment;

import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.View.CursorAdapter.AnswerCursorAdapter;
import com.iia.cdsm.myqcm.View.CursorAdapter.CategoryCursorAdapter;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 17/06/2016.
 */
public class QuestionFragment extends Fragment{
    ListView lvAnswers = null;
    Question question = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        TextView tvTitleQuestion = (TextView) view.findViewById(R.id.tvTitleQuestion);
        lvAnswers = (ListView) view.findViewById(R.id.lv_answers);

        final ArrayList<Long> longs = new ArrayList<Long>();

        lvAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean ok = true;
                for(long lon : longs){
                    if (lon == id){
                        parent.getChildAt(position).setBackgroundColor(Color.WHITE);
                        longs.remove(lon);
                        ok = false;
                    }
                }

                if (ok){
                    parent.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.colorPrimaryTactFactory));
                    longs.add(id);
                }
            }
        });


        Long id = this.getArguments().getLong("id");

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
        int number = lvAnswers.getAdapter().getCount();
        for (int i = 0; i < lvAnswers.getAdapter().getCount(); i++){
            //Toast.makeText(getActivity(), "ANSWER "+ i + "CHECKED",Toast.LENGTH_LONG ).show();
        }

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
