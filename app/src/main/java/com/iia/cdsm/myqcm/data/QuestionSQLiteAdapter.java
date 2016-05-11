package com.iia.cdsm.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.myqcm.Entities.Question;

import java.util.ArrayList;

/**
 * Created by Alexis on 18/02/2016.
 */
public class QuestionSQLiteAdapter {

    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_QCM = "qcm";

    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_VALUE = "value";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_QCM_ID = "qcm_id";

    private SQLiteDatabase db;
    private MyQcmSQLiteOpenHelper helper;

    /**
     * Helper Object to access db
     * @param context
     */
    public QuestionSQLiteAdapter(Context context){
        helper = new MyQcmSQLiteOpenHelper(context, MyQcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Script SQL for Create Table Question
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_QUESTION + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_VALUE + " INTEGER NOT NULL, "
                + COL_CREATED_AT + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL, "
                + COL_QCM_ID + " INTEGER, "
                + "FOREIGN KEY(" + COL_QCM_ID + ") REFERENCES "
                + TABLE_QCM + "(id) " + ");";
    }

    /**
     * Open the connection with Database
     */
    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    /**
     * Close the connection with Database
     */
    public void close(){
        this.db.close();
    }

    /**
     * Insert Question in DB
     * @param question
     * @return line result
     */
    public long insertQuestion(Question question){
        return db.insert(TABLE_QUESTION, null, this.questionToContentValues(question));
    }

    /**
     * Update Question in DB
     * @param question
     * @return line result
     */
    public long updateQuestion(Question question){
        ContentValues valuesUpdate = this.questionToContentValues(question);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(question.getId())};

        return db.update(TABLE_QUESTION, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Question with his Id.
     * @param id
     * @return Question
     */
    public Question getQuestion(long id){

        String[] cols = {COL_ID, COL_TITLE, COL_VALUE, COL_CREATED_AT, COL_UPDATED_AT, COL_QCM_ID};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_QUESTION, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Question result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Select a Question with his Title.
     * @param title
     * @return Question
     */
    public Question getQuestionByTitle(String title){

        String[] cols = {COL_ID, COL_TITLE, COL_VALUE, COL_CREATED_AT, COL_UPDATED_AT, COL_QCM_ID};
        String whereClausesSelect = COL_TITLE + "= ?";
        String[] whereArgsSelect = {String.valueOf(title)};

        Cursor c = db.query(TABLE_QUESTION, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Question result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Get all Question
     * @return ArrayList<>
     */
    public ArrayList<Question> getAllQuestion(){
        ArrayList<Question> result = null;
        Cursor c = getAllCursor();

        if (c.moveToFirst()){
            result = new ArrayList<Question>();
            do {
                result.add(this.cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Delete Question with question object
     * @param question
     * @return long
     */
    public long deleteQuestion(Question question){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(question.getId())};

        return this.db.delete(TABLE_QUESTION, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Convert question to ContentValues
     * @param question
     * @return ContentValue
     */
    private ContentValues questionToContentValues(Question question){
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, question.getTitle());
        values.put(COL_VALUE, question.getValue());
        values.put(COL_CREATED_AT, question.getCreated_at());
        values.put(COL_UPDATED_AT, question.getUpdated_at());
        values.put(COL_QCM_ID, question.getQcm_id());

        return values;
    }

    /**
     * Cursor convert to Question
     * @param c
     * @return Question
     */
    public Question cursorToItem(Cursor c){
        Question result = new Question();
        result.setId(c.getLong(c.getColumnIndex(COL_ID)));
        result.setTitle(c.getString(c.getColumnIndex(COL_TITLE)));
        result.setValue(c.getInt(c.getColumnIndex(COL_VALUE)));
        result.setCreated_at(c.getString(c.getColumnIndex(COL_CREATED_AT)));
        result.setUpdated_at(c.getString(c.getColumnIndex(COL_UPDATED_AT)));
        result.setQcm_id(c.getLong(c.getColumnIndex(COL_QCM_ID)));

        return result;
    }

    /**
     * Get all Cursor in Question Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_TITLE, COL_VALUE, COL_CREATED_AT, COL_UPDATED_AT, COL_QCM_ID};
        Cursor c = db.query(TABLE_QUESTION, cols, null, null, null, null, null);
        return c;
    }
}

