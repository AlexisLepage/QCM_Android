package com.iia.cdsm.myqcm.View.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class ConnectionActivity extends AppCompatActivity {

    UserWSAdapter userWSAdapter = new UserWSAdapter(ConnectionActivity.this);
    public static final String MYPREFERENCES = "MyPrefs";
    public static final String USER_ID = "userId";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        final EditText editLogin = (EditText)this.findViewById(R.id.editLogin);
        final EditText editPassword = (EditText)this.findViewById(R.id.editPassword);
        Button btConnexion = (Button)this.findViewById(R.id.btConnexion);

        sharedPreferences = getSharedPreferences(MYPREFERENCES, MODE_PRIVATE);

            btConnexion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = editLogin.getText().toString();
                String password = editPassword.getText().toString();

                if (login.equals("") || password.equals("")) {
                    Toast.makeText(ConnectionActivity.this, "Renseigné votre login et votre mot de passe !", Toast.LENGTH_LONG).show();
                }else{

                    final ProgressDialog progressDialog = new ProgressDialog(ConnectionActivity.this);
                    progressDialog.setMessage("Connexion...");
                    progressDialog.show();

                    User user = new User(login, password);

                    userWSAdapter.auth(user, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            if (response.length() != 0){
                                User user = null;

                                try {
                                    user = userWSAdapter.jsonToItem(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                assert user != null;
                                editor.putLong(USER_ID, user.getId());
                                editor.apply();

                                User FinalUser = new User(user.getId(),user.getLogin(),user.getName(), user.getFirstname(), user.getPassword(), user.getEmail());
                                Intent intent = new Intent(ConnectionActivity.this, HomeActivity.class);
                                intent.putExtra("user", FinalUser);
                                startActivity(intent);
                                ConnectionActivity.this.finish();
                            }else {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(ConnectionActivity.this, "ERREUR DE LOGIN OU PASSWORD", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);

                            Toast.makeText(ConnectionActivity.this, "ERREUR CONNEXION", Toast.LENGTH_LONG).show();
                        }

                    });
                }

            }
        });
    }
}
