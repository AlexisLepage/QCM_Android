package com.iia.cdsm.myqcm.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.User;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ConnectionActivity extends AppCompatActivity {

    UserWSAdapter userWSAdapter = new UserWSAdapter(ConnectionActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final EditText editLogin = (EditText)this.findViewById(R.id.editLogin);
        final EditText editPassword = (EditText)this.findViewById(R.id.editPassword);
        Button btConnexion = (Button)this.findViewById(R.id.btConnexion);

        btConnexion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = editLogin.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(ConnectionActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();

                userWSAdapter.getUser(login, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        User user = null;
                        try {
                            user = userWSAdapter.jsonToItem(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        User FinalUser = new User(user.getId(),user.getLogin(),user.getName(), user.getFirstname(), user.getPassword(), user.getEmail());
                        Intent intent = new Intent(ConnectionActivity.this, HomeActivity.class);
                        intent.putExtra("user", FinalUser);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(ConnectionActivity.this, "ERREUR CONNEXION", Toast.LENGTH_LONG).show();
                    }

            });
            }
        });
    }
}
