package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iia.cdsm.myqcm.R;

/**
 * Created by Alex on 16/06/2016.
 */
public class AfterQuestionsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_questions);

        TextView tvNote = (TextView) findViewById(R.id.tvNote);

        Bundle extra = getIntent().getExtras();
        float note = extra.getFloat("note");

        tvNote.setText(String.valueOf(note)+"/20");

        getActionBar().setTitle(R.string.congratulation);

        new CountDownTimer(5000,5000) {
            @Override
            public void onTick(long millisUntilFinished) { }

            @Override
            public void onFinish() {
                AfterQuestionsActivity.this.finish();
            }
        }.start();
    }
}
