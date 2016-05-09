package com.iia.cdsm.myqcm.webservice;


import com.iia.cdsm.myqcm.Entities.Answer;
import com.iia.cdsm.myqcm.Entities.Category;
import com.iia.cdsm.myqcm.Entities.Qcm;
import com.iia.cdsm.myqcm.Entities.Question;
import com.iia.cdsm.myqcm.Entities.User;
import com.iia.cdsm.myqcm.data.AnswerSQLiteAdapter;
import com.iia.cdsm.myqcm.data.CategorySQLiteAdapter;
import com.iia.cdsm.myqcm.data.QcmSQLiteAdapter;
import com.iia.cdsm.myqcm.data.QuestionSQLiteAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Alexis on 23/04/2016.
 * Class for import JSON flux in to this application.
 */
public class UserWSAdapter {

    private static final String BASE_URL = "http://192.168.1.31/Qcm/web/app_dev.php/api";
    private static final String ENTITY_USER = "users";
    private static final String ENTITY_QCM = "qcm";
    private static final String ENTITY_CATEGORY = "category";

    private static final String JSON_COL_ID = "id";
    private static final String JSON_COL_LOGIN = "username";
    private static final String JSON_COL_PASSWORD = "password";
    private static final String JSON_COL_EMAIL = "email";
    private static final String JSON_COL_NAME = "name";
    private static final String JSON_COL_IS_AVAILABLE = "is_available";
    private static final String JSON_COL_BEGINNING_AT = "beginning_at";
    private static final String JSON_COL_FINISHED_AT = "finished_at";
    private static final String JSON_COL_DURATION = "duration";
    private static final String JSON_COL_CREATED_AT = "created_at";
    private static final String JSON_COL_UPDATED_AT = "updated_at";
    private static final String JSON_COL_TITLE = "title";
    private static final String JSON_COL_VALUE = "value";
    private static final String JSON_COL_IS_VALID = "is_valid";

    private static final String JSON_LIST_USER_QCM = "user_qcms";
    private static final String JSON_LIST_QUESTION = "questions";
    private static final String JSON_LIST_ANSWER = "answers";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getUser(String login, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY_USER, login);
        client.get(url, handler);
    }

    public static void getAllUser(AsyncHttpResponseHandler responseHandler){
        String url = String.format("%s/%s", BASE_URL, ENTITY_USER);
        client.get(url, responseHandler);
    }

    public static User jsonToItem(JSONObject json) throws JSONException {

        User user = new User();
        user.setId(json.optInt(JSON_COL_ID));
        user.setLogin(json.optString(JSON_COL_LOGIN));
        user.setPassword(json.optString(JSON_COL_PASSWORD));
        user.setEmail(json.optString(JSON_COL_EMAIL));

        //Récupération QCM
        if (json.optString(JSON_LIST_USER_QCM) != null){
            JSONArray arrayUserQcm = new JSONArray(json.getString(JSON_LIST_USER_QCM));

            for (int i = 0; i < arrayUserQcm.length(); i++) {
                JSONObject jsonUserQcms = new JSONObject(arrayUserQcm.getString(i));

                JSONObject jsonQcm = new JSONObject(jsonUserQcms.getString(ENTITY_QCM));

                Qcm qcm = new Qcm();
                qcm.setId(jsonQcm.optInt(JSON_COL_ID));
                qcm.setName(jsonQcm.getString(JSON_COL_NAME));
                qcm.setIs_available(jsonQcm.getBoolean(JSON_COL_IS_AVAILABLE));
                qcm.setBeginning_at(jsonQcm.getString(JSON_COL_BEGINNING_AT));
                qcm.setFinished_at(jsonQcm.getString(JSON_COL_FINISHED_AT));
                qcm.setDuration(jsonQcm.getInt(JSON_COL_DURATION));
                qcm.setCreated_at(jsonQcm.getString(JSON_COL_CREATED_AT));
                qcm.setUpdated_at(jsonQcm.getString(JSON_COL_UPDATED_AT));

                //GetCategory pour savoir si elle est déjà présente,
                //Dans le cas contraire l'insérer.
                JSONObject jsonCategory = new JSONObject(jsonQcm.getString(ENTITY_CATEGORY));

                CategorySQLiteAdapter categorySQLiteAdapter = new QcmSQLiteAdapter(UserWSAdapter.this);
                categorySQLiteAdapter.open();
                Category categoryResult = CategorySQLiteAdapter.getCategoryByName(jsonCategory.getString(JSON_COL_NAME));
                categorySQLiteAdapter.close();


                if(categoryResult == null){

                    //Création de l'objet
                    Category category = new Category();
                    category.setId(jsonCategory.getInt(JSON_COL_ID));
                    category.setName(jsonCategory.getString(JSON_COL_NAME));
                    category.setCreated_at(jsonCategory.getString(JSON_COL_CREATED_AT));
                    category.setUpdated_at(jsonCategory.getString(JSON_COL_UPDATED_AT));

                    //Insertion en base de la catégorie
                    CategorySQLiteAdapter.insertCategory(category);

                    categoryResult = category;
                }

                //Liaison de l'objet
                qcm.setCategory_id(categoryResult.getId());

                //Insertion en base du Qcm.
                QcmSQLiteAdapter.insertQcm(qcm);

                JSONArray arrayQuestions = new JSONArray(json.getString(JSON_LIST_QUESTION));
                if (arrayQuestions != null){
                    for (int j = 0; j < arrayQuestions.length(); j++) {
                        JSONObject rowQuestion = arrayQuestions.getJSONObject(j);

                        Question question = new Question();
                        question.setId(rowQuestion.optInt(JSON_COL_ID));
                        question.setTitle(rowQuestion.optString(JSON_COL_TITLE));
                        question.setValue(rowQuestion.optInt(JSON_COL_VALUE));
                        question.setCreated_at(rowQuestion.optString(JSON_COL_CREATED_AT));
                        question.setUpdated_at(rowQuestion.optString(JSON_COL_UPDATED_AT));
                        question.setQcm_id(jsonQcm.optInt(JSON_COL_ID));

                        //Insertion en base de la question
                        QuestionSQLiteAdapter.insertQuestion(question);

                        // Liste des réponses
                        JSONArray arrayAnswers = new JSONArray(rowQuestion.getString(JSON_LIST_ANSWER));

                        if (arrayAnswers != null){
                            for (int k = 0; k < arrayAnswers.length(); k++) {
                                JSONObject rowAnswer = arrayAnswers.getJSONObject(k);

                                Answer answer = new Answer();
                                answer.setId(rowAnswer.optInt(JSON_COL_ID));
                                answer.setTitle(rowAnswer.optString(JSON_COL_TITLE));
                                answer.setIs_valid(rowAnswer.optBoolean(JSON_COL_IS_VALID));
                                answer.setCreated_at(rowAnswer.optString(JSON_COL_CREATED_AT));
                                answer.setUpdated_at(rowAnswer.optString(JSON_COL_UPDATED_AT));
                                answer.setQuestion_id(rowQuestion.optInt(JSON_COL_ID));

                                //Insertion en base de la réponse
                                AnswerSQLiteAdapter.insertAnswer(answer);
                            }
                        }
                    }
                }
            }
        }



        return user;
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
