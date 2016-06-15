package com.iia.cdsm.myqcm.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.User;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;
import com.iia.cdsm.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainQCMActivity extends AppCompatActivity {

    UserWSAdapter userWSAdapter = new UserWSAdapter(MainQCMActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qcm);

        final EditText editLogin = (EditText)this.findViewById(R.id.editLogin);
        final EditText editPassword = (EditText)this.findViewById(R.id.editPassword);
        Button btConnexion = (Button)this.findViewById(R.id.btConnexion);

        btConnexion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = editLogin.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(MainQCMActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                userWSAdapter.getUser(login, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        User user = new User();
                        try {
                            user = userWSAdapter.jsonToItem(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(MainQCMActivity.this, "INSERT JSON OK", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(MainQCMActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(MainQCMActivity.this, "ERREUR CONNEXION", Toast.LENGTH_LONG).show();
                    }

            });
            }
        });
    }
}
