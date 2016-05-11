package com.iia.cdsm.myqcm;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UserWSAdapterTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getUser(){
        String BASE_URL = "http://192.168.1.31/Qcm/web/app_dev.php/api";
        String ENTITY_USER = "users";
        String login = "admin";
        AsyncHttpClient client = new AsyncHttpClient();
        JsonHttpResponseHandler handler = new JsonHttpResponseHandler();

        String url = String.format("%s/%s/%s", BASE_URL, ENTITY_USER, login);
        RequestHandle response = client.get(url, handler);

        Boolean success = false;
        if(response != null){
            success = true;
        }

        assertTrue(success);
    }

}