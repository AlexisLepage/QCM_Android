package com.iia.cdsm.myqcm;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UserWSAdapterTest {

    private static final String BASE_URL = "http://192.168.1.31/Qcm/web/app_dev.php/api";
    private static final String ENTITY_USER = "users";

    @Test
        public void getUser() throws IOException {
            String login = "admin";

            String strUrl = String.format("%s/%s/%s", BASE_URL, ENTITY_USER, login);

            try {
                URL url = new URL(strUrl);
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                urlConn.connect();

                assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());

            } catch (IOException e) {
                System.err.println("Error HTTP connection");
                e.printStackTrace();
                throw e;
            }

    }

    @Test
    public void getAllUser() throws IOException {
        String login = "admin";

        String strUrl = String.format("%s/%s", BASE_URL, ENTITY_USER);

        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.connect();

            assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());

        } catch (IOException e) {
            System.err.println("Error HTTP connection");
            e.printStackTrace();
            throw e;
        }

    }

}