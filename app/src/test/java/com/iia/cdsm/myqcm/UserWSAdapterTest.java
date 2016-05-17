package com.iia.cdsm.myqcm;

import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UserWSAdapterTest {

    private static final String BASE_URL = "http://192.168.1.31/Qcm/web/app_dev.php/api";
    private static final String ENTITY_USER = "users";

    /**
     * Test GetUser function to WebService.
     * @throws IOException
     */
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

    /**
     * Get all User function to WebService.
     * @throws IOException
     */
    @Test
    public void getAllUser() throws IOException {
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