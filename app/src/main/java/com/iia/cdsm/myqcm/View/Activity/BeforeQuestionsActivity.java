package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iia.cdsm.myqcm.Entities.Qcm;
import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;

/**
 * Created by Alex on 16/06/2016.
 */
public class BeforeQuestionsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_questions);
        getActionBar().setTitle(R.string.qcm);

        ImageView ivYes = (ImageView) this.findViewById(R.id.btYesQuestions);
        ImageView ivNo = (ImageView) this.findViewById(R.id.btNoQuestions);
        TextView nameQcm = (TextView) this.findViewById(R.id.tvNameQcmBeforeQuestions);

        Bundle extra = getIntent().getExtras();
        Long id = extra.getLong("id");

        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this);
        qcmSQLiteAdapter.open();
        final Qcm qcm = qcmSQLiteAdapter.getQcm(id);
        qcmSQLiteAdapter.close();

        nameQcm.setText(qcm.getName());

        ivNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeforeQuestionsActivity.this.finish();
            }
        });

        ivYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), QuestionsActivity.class);
                intent.putExtra("id", qcm.getId());
                BeforeQuestionsActivity.this.finish();
                startActivity(intent);
            }
        });
    }
}

