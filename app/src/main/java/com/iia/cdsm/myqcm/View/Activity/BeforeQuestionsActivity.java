package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.R;

/**
 * Created by Alex on 16/06/2016.
 */
public class BeforeQuestionsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_questions);

        Button btYes = (Button) this.findViewById(R.id.btYesQuestions);
        Button btNo = (Button) this.findViewById(R.id.btNoQuestions);

        btNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeforeQuestionsActivity.this.finish();
            }
        });

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = getIntent().getExtras();
                Long id = extra.getLong("id");
                Intent intent = new Intent(v.getContext(), QuestionsActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}

