package com.iia.cdsm.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.myqcm.Entities.Answer;

import java.util.ArrayList;

/**
 * Created by Alexis on 18/02/2016.
 */
public class AnswerSQLiteAdapter {

    public static final String TABLE_ANSWER = "answer";
    public static final String TABLE_QUESTION = "question";

    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_IS_VALID = "is_valid";
    public static final String COL_IS_SELECTED = "is_selected";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_QUESTION_ID = "question_id";

    private SQLiteDatabase db;
    private MyQcmSQLiteOpenHelper helper;

    /**
     * Helper Object to access db
     * @param context
     */
    public AnswerSQLiteAdapter(Context context){
        helper = new MyQcmSQLiteOpenHelper(context, MyQcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Script SQL for Create Table Answer
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_ANSWER + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_IS_VALID + " BOOLEAN, "
                + COL_IS_SELECTED + " BOOLEAN, "
                + COL_CREATED_AT + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL, "
                + COL_QUESTION_ID + " INTEGER, "
                + "FOREIGN KEY(" + COL_QUESTION_ID + ") REFERENCES "
                + TABLE_QUESTION + "(id) " + ");";
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
     * Insert Answer in DB
     * @param answer
     * @return line result
     */
    public long insertAnswer(Answer answer){
        return db.insert(TABLE_ANSWER, null, this.answerToContentValues(answer));
    }

    /**
     * Update Answer in DB
     * @param answer
     * @return line result
     */
    public long updateAnswer(Answer answer){
        ContentValues valuesUpdate = this.answerToContentValues(answer);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(answer.getId())};

        return db.update(TABLE_ANSWER, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Answer with his Id.
     * @param id
     * @return Answer
     */
    public Answer getAnswer(long id){

        String[] cols = {COL_ID, COL_TITLE, COL_IS_VALID, COL_IS_SELECTED, COL_CREATED_AT, COL_UPDATED_AT, COL_QUESTION_ID};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_ANSWER, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Answer result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Select a Answer with his Title.
     * @param title
     * @return Answer
     */
    public Answer getAnswerByTitle(String title){

        String[] cols = {COL_ID, COL_TITLE, COL_IS_VALID, COL_IS_SELECTED, COL_CREATED_AT, COL_UPDATED_AT, COL_QUESTION_ID};
        String whereClausesSelect = COL_TITLE + "= ?";
        String[] whereArgsSelect = {String.valueOf(title)};

        Cursor c = db.query(TABLE_ANSWER, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Answer result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Get all Answer
     * @return ArrayList<>
     */
    public ArrayList<Answer> getAllAnswer(){
        ArrayList<Answer> result = null;
        Cursor c = getAllCursor();

        if (c.moveToFirst()){
            result = new ArrayList<Answer>();
            do {
                result.add(this.cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Delete Answer with answer object
     * @param answer
     * @return long
     */
    public long deleteAnswer(Answer answer){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(answer.getId())};

        return this.db.delete(TABLE_ANSWER, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Convert answer to ContentValues
     * @param answer
     * @return ContentValue
     */
    private ContentValues answerToContentValues(Answer answer){
        ContentValues values = new ContentValues();
        values.put(COL_ID, answer.getId());
        values.put(COL_TITLE, answer.getTitle());
        values.put(COL_IS_VALID, answer.getIs_valid());
        values.put(COL_IS_SELECTED, answer.getIs_selected());
        values.put(COL_CREATED_AT, answer.getCreated_at());
        values.put(COL_UPDATED_AT, answer.getUpdated_at());
        values.put(COL_QUESTION_ID, answer.getQuestion_id());

        return values;
    }

    /**
     * Cursor convert to Answer
     * @param c
     * @return Answer
     */
    public Answer cursorToItem(Cursor c){
        Answer result = new Answer();
        result.setId(c.getLong(c.getColumnIndex(COL_ID)));
        result.setTitle(c.getString(c.getColumnIndex(COL_TITLE)));
        result.setIs_valid(Boolean.valueOf(c.getString(c.getColumnIndex(COL_IS_VALID))));
        result.setIs_selected(Boolean.valueOf(c.getString(c.getColumnIndex(COL_IS_SELECTED))));
        result.setCreated_at(c.getString(c.getColumnIndex(COL_CREATED_AT)));
        result.setUpdated_at(c.getString(c.getColumnIndex(COL_UPDATED_AT)));
        result.setQuestion_id(c.getLong(c.getColumnIndex(COL_QUESTION_ID)));

        return result;
    }

    /**
     * Get all Cursor in Answer Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_TITLE, COL_IS_VALID, COL_IS_SELECTED, COL_CREATED_AT, COL_UPDATED_AT, COL_QUESTION_ID};
        Cursor c = db.query(TABLE_ANSWER, cols, null, null, null, null, null);
        return c;
    }

    /**
     * Get all Cursor By QuestionId in Answer Table
     * @return Cursor
     */
    public Cursor getAllCursorByQuestionId(Long idQuestion){
        String[] cols = {COL_ID, COL_TITLE, COL_IS_VALID, COL_IS_SELECTED, COL_CREATED_AT, COL_UPDATED_AT, COL_QUESTION_ID};
        Cursor c = db.query(TABLE_ANSWER, cols, COL_QUESTION_ID + "=" + idQuestion, null, null, null, null);
        return c;
    }
}
