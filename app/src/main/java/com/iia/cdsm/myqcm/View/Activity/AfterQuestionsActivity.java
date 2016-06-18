package com.iia.cdsm.myqcm.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iia.cdsm.myqcm.R;

/**
 * Created by Alex on 16/06/2016.
 */
public class AfterQuestionsActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_questions);

        Button btReturnHome = (Button) this.findViewById(R.id.btReturnHome);

        btReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterQuestionsActivity.this.finish();
            }
        });
    }
}
