package com.iia.cdsm.myqcm.webservice;


import com.iia.cdsm.myqcm.Entities.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexis on 23/04/2016.
 * Class for import JSON flux in to this application.
 */
public class UserWSAdapter {

    private static final String BASE_URL = "http://localhost/Qcm/web/app_dev.php/api";
    private static final String ENTITY = "users";

    private static final String JSON_COL_ID = "id";
    private static final String JSON_COL_LOGIN = "username";
    private static final String JSON_COL_PASSWORD = "password";
    private static final String JSON_COL_EMAIL = "email";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getUser(int login, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY, login);
        client.get(url, handler);
    }

    public static void getAllUser(AsyncHttpResponseHandler responseHandler){
        String url = String.format("%s/%s", BASE_URL, ENTITY);
        client.get(url, responseHandler);
    }

    public static User jsonToItem(JSONObject json){
        User item = new User();
        item.setId(json.optInt(JSON_COL_ID));
        item.setLogin(json.optString(JSON_COL_LOGIN));
        item.setPassword(json.optString(JSON_COL_PASSWORD));
        item.setEmail(json.optString(JSON_COL_EMAIL));
        return item;
    }

    public static JSONObject itemToJson(User item) throws JSONException {

        JSONObject result = new JSONObject();

        if (item.getLogin() != null){
            result.put(JSON_COL_LOGIN, item.getLogin());
        }

        if (item.getPassword() != null){
            result.put(JSON_COL_PASSWORD, item.getPassword());
        }

        return result;
    }

    public static RequestParams itemToParams(User item){
        RequestParams params = new RequestParams();
        params.put(JSON_COL_LOGIN, item.getLogin());
        params.put(JSON_COL_PASSWORD, item.getPassword());

        return params;
    }
}
