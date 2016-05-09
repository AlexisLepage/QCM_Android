package com.iia.cdsm.myqcm.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iia.cdsm.myqcm.Entities.User;
import com.iia.cdsm.myqcm.R;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;
import com.iia.cdsm.myqcm.webservice.UserWSAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainQCMActivity extends AppCompatActivity {

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

                UserWSAdapter.getUser(login, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        User item = null;
                        try {
                            item = UserWSAdapter.jsonToItem(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(MainQCMActivity.this, item.getEmail(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);

                        Toast.makeText(MainQCMActivity.this, "ERREUR CONNEXION", Toast.LENGTH_LONG).show();
                    }
            });


                //Envoie requête au webservice en vérifiant login.
                //Réception réponse webservice

                //Action si réponse webservice est OK
                //Envoie requête de récupération de JSON.
                //Récupération du flux et convertion en Objet.
                //Insertion en base.

            }
        });


        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(MainQCMActivity.this);
        qcmSQLiteAdapter.open();
        qcmSQLiteAdapter.close();
    }





}
