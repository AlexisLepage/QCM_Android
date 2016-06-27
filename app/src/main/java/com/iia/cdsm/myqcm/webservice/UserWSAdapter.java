package com.iia.cdsm.myqcm.webservice;


import android.content.Context;

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

import java.util.ArrayList;

/**
 * Created by Alexis on 23/04/2016.
 * Class for import JSON flux in to this application.
 */
public class UserWSAdapter {

    private static final String BASE_URL = "http://192.168.1.31/Qcm/web/app_dev.php/api";
    private static final String UPDATE_QCM = "update_qcm";
    private static final String ENTITY_USER = "users";
    private static final String ENTITY_USER_SIMPLE = "user";
    private static final String ENTITY_QCM = "qcm";
    private static final String ENTITY_CATEGORY = "category";
    private static final String JSON_COL_ID = "id";
    private static final String JSON_COL_ID_QCM = "id_qcm";
    private static final String JSON_COL_ID_USER = "id_user";
    private static final String JSON_COL_LOGIN = "username";
    private static final String JSON_COL_FIRSTNAME = "firstname";

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
    private static final String JSON_COL_IS_SELECTED = "is_selected";
    private static final String JSON_COL_NOTE = "note";

    private static final String JSON_LIST_USER_QCM = "user_qcms";
    private static final String JSON_LIST_QUESTION = "questions";
    private static final String JSON_LIST_ANSWER = "answers";

    private Context ctx;
    private AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Create a context for UserWSAdapter.
     * @param context
     * @return
     */
    public UserWSAdapter(Context context){

        this.ctx = context;
    }

    /**
     * Select a User by his Login in WebService.
     * @param login
     * @param handler
     */
    public void getUser(String login, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s/%s", BASE_URL, ENTITY_USER, login);
        client.get(url, handler);
    }

    /**
     * Select all Users in WebService
     * @param responseHandler
     * @return
     */
    public void getAllUser(AsyncHttpResponseHandler responseHandler){
        String url = String.format("%s/%s", BASE_URL, ENTITY_USER);
        client.get(url, responseHandler);
    }

    /**
     * POST Qcm at WebService.
     * @param json
     * @param handler
     */
    public void postQcm(String json, AsyncHttpResponseHandler handler){
        String url = String.format("%s/%s", BASE_URL, UPDATE_QCM);
        RequestParams params = new RequestParams();
        params.add("json", json);
        client.post(url, params, handler);
    }

    /**
     * POST Login at WebService to check if exist.
     * @param login
     * @param handler
     */
    public void postLoginUser(String login, AsyncHttpResponseHandler handler) {
        String url = String.format("%s/%s", BASE_URL, UPDATE_QCM);
        RequestParams params = new RequestParams();
        params.add("login", login);
        client.post(url, params, handler);
    }


    /**
     * Convert JsonObjet to DataObject.
     * Insert a DataObjet if is't exist.
     * @param json
     * @return User
     */
    public User jsonToItem(JSONObject json) throws JSONException {

        CategorySQLiteAdapter categorySQLiteAdapter = new CategorySQLiteAdapter(this.ctx);
        QcmSQLiteAdapter qcmSQLiteAdapter = new QcmSQLiteAdapter(this.ctx);
        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this.ctx);
        AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this.ctx);

        User user = new User();
        user.setId(json.optInt(JSON_COL_ID));
        user.setLogin(json.optString(JSON_COL_LOGIN));
        user.setName(json.optString(JSON_COL_NAME));
        user.setFirstname(json.optString(JSON_COL_FIRSTNAME));
        user.setPassword(json.optString(JSON_COL_PASSWORD));
        user.setEmail(json.optString(JSON_COL_EMAIL));

        //Récupération QCM
        if (json.optString(JSON_LIST_USER_QCM) != null){
            JSONArray arrayUserQcm = new JSONArray(json.getString(JSON_LIST_USER_QCM));

            for (int i = 0; i < arrayUserQcm.length(); i++) {
                JSONObject jsonUserQcms = new JSONObject(arrayUserQcm.getString(i));

                JSONObject jsonQcm = new JSONObject(jsonUserQcms.getString(ENTITY_QCM));

                qcmSQLiteAdapter.open();
                Qcm qcmResult = qcmSQLiteAdapter.getQcm(jsonQcm.getLong(JSON_COL_ID));
                boolean identicQcm = false;

                if (qcmResult == null) {
                    Qcm qcm = new Qcm();
                    qcm.setId(jsonQcm.optInt(JSON_COL_ID));
                    qcm.setName(jsonQcm.getString(JSON_COL_NAME));
                    qcm.setIs_available(jsonQcm.getBoolean(JSON_COL_IS_AVAILABLE));
                    qcm.setBeginning_at(jsonQcm.getString(JSON_COL_BEGINNING_AT));
                    qcm.setFinished_at(jsonQcm.getString(JSON_COL_FINISHED_AT));
                    qcm.setDuration(jsonQcm.getInt(JSON_COL_DURATION));
                    qcm.setCreated_at(jsonQcm.getString(JSON_COL_CREATED_AT));
                    qcm.setUpdated_at(jsonQcm.getString(JSON_COL_UPDATED_AT));

                    identicQcm = true;
                    qcmResult = qcm;
                }else{
                    if (qcmResult.getId() != jsonQcm.getLong(JSON_COL_ID)){qcmResult.setId(jsonQcm.getLong(JSON_COL_ID));}
                    if (!qcmResult.getName().equals(jsonQcm.getString(JSON_COL_NAME))){qcmResult.setName(jsonQcm.getString(JSON_COL_NAME));}
                    if (!qcmResult.getIs_available().equals(jsonQcm.getBoolean(JSON_COL_IS_AVAILABLE))){qcmResult.setIs_available(jsonQcm.getBoolean(JSON_COL_IS_AVAILABLE));}
                    if (!qcmResult.getBeginning_at().equals(jsonQcm.getString(JSON_COL_BEGINNING_AT))){qcmResult.setBeginning_at(jsonQcm.getString(JSON_COL_BEGINNING_AT));}
                    if (!qcmResult.getFinished_at().equals(jsonQcm.getString(JSON_COL_FINISHED_AT))){qcmResult.setFinished_at(jsonQcm.getString(JSON_COL_FINISHED_AT));}
                    if (!qcmResult.getDuration().equals(jsonQcm.getInt(JSON_COL_DURATION))){qcmResult.setDuration(jsonQcm.getInt(JSON_COL_DURATION));}
                    if (!qcmResult.getUpdated_at().equals(jsonQcm.getString(JSON_COL_UPDATED_AT))){qcmResult.setUpdated_at(jsonQcm.getString(JSON_COL_UPDATED_AT));}
                }

                    JSONObject jsonCategory = new JSONObject(jsonQcm.getString(ENTITY_CATEGORY));

                    categorySQLiteAdapter.open();
                    Category categoryResult = categorySQLiteAdapter.getCategory(jsonCategory.getLong(JSON_COL_ID));

                    if(categoryResult == null){

                        Category category = new Category();
                        category.setId(jsonCategory.getInt(JSON_COL_ID));
                        category.setName(jsonCategory.getString(JSON_COL_NAME));
                        category.setCreated_at(jsonCategory.getString(JSON_COL_CREATED_AT));
                        category.setUpdated_at(jsonCategory.getString(JSON_COL_UPDATED_AT));

                        categorySQLiteAdapter.insertCategory(category);
                        categoryResult = category;
                    }else{
                        boolean identicCategory = true;
                        if (categoryResult.getId() != jsonCategory.getLong(JSON_COL_ID)){identicCategory = false; categoryResult.setId(jsonCategory.getLong(JSON_COL_ID));}
                        if (!categoryResult.getName().equals(jsonCategory.getString(JSON_COL_NAME))){identicCategory = false; categoryResult.setName(jsonCategory.getString(JSON_COL_NAME));}
                        if (!categoryResult.getUpdated_at().equals(jsonCategory.getString(JSON_COL_UPDATED_AT))){identicCategory = false; categoryResult.setUpdated_at(jsonCategory.getString(JSON_COL_UPDATED_AT));}
                        if (!identicCategory){
                            categorySQLiteAdapter.updateCategory(categoryResult);
                        }
                    }

                    categorySQLiteAdapter.close();

                    qcmResult.setCategory_id(categoryResult.getId());

                if (identicQcm){
                    qcmSQLiteAdapter.insertQcm(qcmResult);
                }else {
                    qcmSQLiteAdapter.updateQcm(qcmResult);
                }

                qcmSQLiteAdapter.close();

                    JSONArray arrayQuestions = new JSONArray(jsonQcm.getString(JSON_LIST_QUESTION));
                    if (arrayQuestions != null){
                        for (int j = 0; j < arrayQuestions.length(); j++) {

                            JSONObject rowQuestion = arrayQuestions.getJSONObject(j);

                            questionSQLiteAdapter.open();
                            Question questionResult = questionSQLiteAdapter.getQuestion(rowQuestion.getLong(JSON_COL_ID));

                            if (questionResult == null) {
                                Question question = new Question();
                                question.setId(rowQuestion.optInt(JSON_COL_ID));
                                question.setTitle(rowQuestion.optString(JSON_COL_TITLE));
                                question.setValue(rowQuestion.optInt(JSON_COL_VALUE));
                                question.setCreated_at(rowQuestion.optString(JSON_COL_CREATED_AT));
                                question.setUpdated_at(rowQuestion.optString(JSON_COL_UPDATED_AT));
                                question.setQcm_id(jsonQcm.optInt(JSON_COL_ID));

                                questionSQLiteAdapter.insertQuestion(question);
                            }else {
                                boolean identicQuestion = true;
                                if (questionResult.getId() != rowQuestion.getLong(JSON_COL_ID)){identicQuestion = false; questionResult.setId(rowQuestion.optInt(JSON_COL_ID));}
                                if (!questionResult.getTitle().equals(rowQuestion.getString(JSON_COL_TITLE))){identicQuestion = false; questionResult.setTitle(rowQuestion.optString(JSON_COL_TITLE));}
                                if (!questionResult.getValue().equals(rowQuestion.getInt(JSON_COL_VALUE))){identicQuestion = false; questionResult.setValue(rowQuestion.optInt(JSON_COL_VALUE));}
                                if (!questionResult.getUpdated_at().equals(rowQuestion.getString(JSON_COL_UPDATED_AT))){identicQuestion = false; questionResult.setUpdated_at(rowQuestion.optString(JSON_COL_UPDATED_AT));}
                                if (questionResult.getQcm_id() != jsonQcm.getLong(JSON_COL_ID)){identicQuestion = false; questionResult.setQcm_id(jsonQcm.optInt(JSON_COL_ID));}
                                if (!identicQuestion){
                                    questionSQLiteAdapter.updateQuestion(questionResult);
                                }
                            }

                            questionSQLiteAdapter.close();

                                JSONArray arrayAnswers = new JSONArray(rowQuestion.getString(JSON_LIST_ANSWER));

                                if (arrayAnswers != null){
                                    for (int k = 0; k < arrayAnswers.length(); k++) {
                                        JSONObject rowAnswer = arrayAnswers.getJSONObject(k);

                                        answerSQLiteAdapter.open();

                                        Answer answerResult = answerSQLiteAdapter.getAnswer(rowAnswer.getLong(JSON_COL_ID));

                                        if (answerResult == null){
                                            Answer answer = new Answer();
                                            answer.setId(rowAnswer.optInt(JSON_COL_ID));
                                            answer.setTitle(rowAnswer.optString(JSON_COL_TITLE));
                                            answer.setIs_valid(boolToInt(rowAnswer.optBoolean(JSON_COL_IS_VALID)));
                                            answer.setCreated_at(rowAnswer.optString(JSON_COL_CREATED_AT));
                                            answer.setUpdated_at(rowAnswer.optString(JSON_COL_UPDATED_AT));
                                            answer.setQuestion_id(rowQuestion.optInt(JSON_COL_ID));

                                            answerSQLiteAdapter.insertAnswer(answer);
                                        }else {
                                            boolean identicAnswer = true;
                                            if (answerResult.getId() != rowAnswer.getLong(JSON_COL_ID)){identicAnswer = false; answerResult.setId(rowAnswer.optInt(JSON_COL_ID));}
                                            if (!answerResult.getTitle().equals(rowAnswer.getString(JSON_COL_TITLE))){identicAnswer = false; answerResult.setTitle(rowAnswer.optString(JSON_COL_TITLE));}
                                            if (!answerResult.getIs_valid().equals(boolToInt(rowAnswer.optBoolean(JSON_COL_IS_VALID)))) {identicAnswer = false; answerResult.setIs_valid(boolToInt(rowAnswer.optBoolean(JSON_COL_IS_VALID)));}
                                            if (!answerResult.getUpdated_at().equals(rowAnswer.getString(JSON_COL_UPDATED_AT))){identicAnswer = false; answerResult.setUpdated_at(rowAnswer.optString(JSON_COL_UPDATED_AT));}
                                            if (answerResult.getQuestion_id() != rowQuestion.getLong(JSON_COL_ID)){identicAnswer = false; answerResult.setQuestion_id(rowQuestion.optInt(JSON_COL_ID));}
                                            if (!identicAnswer){
                                                answerSQLiteAdapter.updateAnswer(answerResult);
                                            }
                                        }
                                        answerSQLiteAdapter.close();
                                    }
                                }
                            }
                        }
            }
        }

        return user;
    }

    /**
     * Convert DataObject to JsonObjet.
     * @param qcm, id
     * @return JSONObject
     */
    public String itemToJson(Qcm qcm, long id) throws JSONException {

        String json= "";

        JSONObject jsonObject = new JSONObject();

        JSONObject jsonObjectUser = new JSONObject();
        jsonObjectUser.accumulate(JSON_COL_ID, id);

        JSONObject jsonObjectQcm = new JSONObject();
        jsonObjectQcm.accumulate(JSON_COL_ID, qcm.getId());
        jsonObjectQcm.accumulate(JSON_COL_NAME, qcm.getName());

        QuestionSQLiteAdapter questionSQLiteAdapter = new QuestionSQLiteAdapter(this.ctx);
        questionSQLiteAdapter.open();
        ArrayList<Question> questions = questionSQLiteAdapter.getQuestionByQcmId(qcm.getId());
        questionSQLiteAdapter.close();

        if (questions != null){
            for (Question question : questions) {
                JSONObject jsonObjectQuestion = new JSONObject();
                jsonObjectQuestion.accumulate(JSON_COL_ID, question.getId());
                jsonObjectQuestion.accumulate(JSON_COL_TITLE, question.getTitle());
                jsonObjectQuestion.accumulate(JSON_COL_VALUE, question.getValue());

                AnswerSQLiteAdapter answerSQLiteAdapter = new AnswerSQLiteAdapter(this.ctx);
                answerSQLiteAdapter.open();
                ArrayList<Answer> answers = answerSQLiteAdapter.getAnswerByIdQuestion(question.getId());
                answerSQLiteAdapter.close();

                if (answers != null){
                    for (Answer answer : answers){
                        JSONObject jsonObjectAnswer = new JSONObject();
                        jsonObjectAnswer.accumulate(JSON_COL_ID, answer.getId());
                        jsonObjectAnswer.accumulate(JSON_COL_TITLE, answer.getTitle());
                        jsonObjectAnswer.accumulate(JSON_COL_IS_VALID, answer.getIs_valid());
                        jsonObjectAnswer.accumulate(JSON_COL_IS_SELECTED, answer.getIs_selected());
                        jsonObjectQuestion.accumulate(JSON_LIST_ANSWER, jsonObjectAnswer);
                    }
                }

                jsonObjectQcm.accumulate(JSON_LIST_QUESTION, jsonObjectQuestion);
            }
        }

        jsonObject.accumulate(ENTITY_QCM, jsonObjectQcm);
        jsonObject.accumulate(ENTITY_USER_SIMPLE, jsonObjectUser);

        json = jsonObject.toString();

        return json;

    }

    /**
     * Convert DataObject to JsonObjet.
     * @param idQcm
     * @param idUser
     * @param note
     * @return JSONObject
     */
    public String returnJson(long idQcm, long idUser, float note) throws JSONException {

        String json= "";

        JSONObject jsonObject = new JSONObject();

        jsonObject.accumulate(JSON_COL_ID_QCM, idQcm);
        jsonObject.accumulate(JSON_COL_ID_USER, idUser);
        jsonObject.accumulate(JSON_COL_NOTE, note);

        json = jsonObject.toString();

        return json;

    }

    /**
     * Convert DataObject to Params.
     * @param item
     * @return RequestParams
     */
    public RequestParams itemToParams(User item){
        RequestParams params = new RequestParams();
        params.put(JSON_COL_LOGIN, item.getLogin());
        params.put(JSON_COL_PASSWORD, item.getPassword());

        return params;
    }

    public int boolToInt(boolean b) {
        return b ? 1 : 0;
    }
}
