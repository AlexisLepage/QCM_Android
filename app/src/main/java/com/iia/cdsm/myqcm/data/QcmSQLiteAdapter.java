package com.iia.cdsm.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.myqcm.Entities.Qcm;

import java.util.ArrayList;

/**
 * Created by Alexis on 17/02/2016.
 */
public class QcmSQLiteAdapter {

    public static final String TABLE_QCM = "qcm";
    public static final String TABLE_CATEGORY = "category";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_IS_AVAILABLE = "is_available";
    public static final String COL_BEGINNING_AT = "beginning_at";
    public static final String COL_FINISHED_AT = "finished_at";
    public static final String COL_DURATION = "duration";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_CATEGORY_ID = "category_id";

    private SQLiteDatabase db;
    private MyQcmSQLiteOpenHelper helper;
    private Context ctx;

    /**
     * Helper Object to access db
     * @param context
     */
    public QcmSQLiteAdapter(Context context){
        this.ctx = context;
        helper = new MyQcmSQLiteOpenHelper(context, MyQcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Script SQL for Create Table Qcm
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_QCM + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_IS_AVAILABLE + " BOOLEAN, "
                + COL_BEGINNING_AT + " TEXT NOT NULL, "
                + COL_FINISHED_AT + " TEXT NOT NULL, "
                + COL_DURATION + " INTEGER NOT NULL, "
                + COL_CREATED_AT + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL, "
                + COL_CATEGORY_ID + " INTEGER, "
                + "FOREIGN KEY(" + COL_CATEGORY_ID + ") REFERENCES "
                + TABLE_CATEGORY + "(id) " + ");";
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
     * Insert Qcm in DB
     * @param qcm
     * @return line result
     */
    public long insertQcm(Qcm qcm){
        return db.insert(TABLE_QCM, null, this.qcmToContentValues(qcm));
    }

    /**
     * Update Qcm in DB
     * @param qcm
     * @return line result
     */
    public long updateQcm(Qcm qcm){
        ContentValues valuesUpdate = this.qcmToContentValues(qcm);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(qcm.getId())};

        return db.update(TABLE_QCM, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Qcm with his Id.
     * @param id
     * @return Qcm
     */
    public Qcm getQcm(long id){

        String[] cols = {COL_ID, COL_NAME, COL_IS_AVAILABLE, COL_BEGINNING_AT, COL_BEGINNING_AT,
                COL_FINISHED_AT, COL_DURATION, COL_CREATED_AT, COL_UPDATED_AT, COL_CATEGORY_ID};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_QCM, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Qcm result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Select a Qcm with his Name.
     * @param name
     * @return Qcm
     */
    public Qcm getQcmByName(String name){

        String[] cols = {COL_ID, COL_NAME, COL_IS_AVAILABLE, COL_BEGINNING_AT, COL_BEGINNING_AT,
                COL_FINISHED_AT, COL_DURATION, COL_CREATED_AT, COL_UPDATED_AT, COL_CATEGORY_ID};
        String whereClausesSelect = COL_NAME + "= ?";
        String[] whereArgsSelect = {String.valueOf(name)};

        Cursor c = db.query(TABLE_QCM, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Qcm result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Get all Qcm
     * @return ArrayList<>
     */
    public ArrayList<Qcm> getAllQcm(){
        ArrayList<Qcm> result = null;
        Cursor c = getAllCursor();

        if (c.moveToFirst()){
            result = new ArrayList<Qcm>();
            do {
                result.add(this.cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Delete Qcm with qcm object
     * @param qcm
     * @return long
     */
    public long deleteQcm(Qcm qcm){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(qcm.getId())};

        return this.db.delete(TABLE_QCM, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Convert qcm to ContentValues
     * @param qcm
     * @return ContentValue
     */
    private ContentValues qcmToContentValues(Qcm qcm){
        ContentValues values = new ContentValues();
        values.put(COL_ID, qcm.getId());
        values.put(COL_NAME, qcm.getName());
        values.put(COL_IS_AVAILABLE, qcm.getIs_available());
        values.put(COL_BEGINNING_AT, qcm.getBeginning_at());
        values.put(COL_FINISHED_AT, qcm.getFinished_at());
        values.put(COL_DURATION, qcm.getDuration());
        values.put(COL_CREATED_AT, qcm.getCreated_at());
        values.put(COL_UPDATED_AT, qcm.getUpdated_at());
        values.put(COL_CATEGORY_ID, qcm.getCategory_id());

        return values;
    }

    /**
     * Cursor convert to Qcm
     * @param c
     * @return Qcm
     */
    public Qcm cursorToItem(Cursor c){
        Qcm result = new Qcm();
        result.setId(c.getLong(c.getColumnIndex(COL_ID)));
        result.setName(c.getString(c.getColumnIndex(COL_NAME)));
        result.setIs_available(Boolean.valueOf(c.getString(c.getColumnIndex(COL_IS_AVAILABLE))));
        result.setBeginning_at(c.getString(c.getColumnIndex(COL_BEGINNING_AT)));
        result.setFinished_at(c.getString(c.getColumnIndex(COL_FINISHED_AT)));
        result.setDuration(c.getInt(c.getColumnIndex(COL_DURATION)));
        result.setCreated_at(c.getString(c.getColumnIndex(COL_CREATED_AT)));
        result.setUpdated_at(c.getString(c.getColumnIndex(COL_UPDATED_AT)));
        result.setCategory_id(c.getLong(c.getColumnIndex(COL_CATEGORY_ID)));

        return result;
    }

    /**
     * Get all Cursor in Qcm Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_NAME, COL_IS_AVAILABLE, COL_BEGINNING_AT, COL_BEGINNING_AT,
                COL_FINISHED_AT, COL_DURATION, COL_CREATED_AT, COL_UPDATED_AT, COL_CATEGORY_ID};
        Cursor c = db.query(TABLE_QCM, cols, null, null, null, null, null);
        return c;
    }

    /**
     * Select a Cursor with his IdCategory.
     * @param id
     * @return Cursor
     */
    public Cursor getCursorByIdCategory(Long id){

        ArrayList<Qcm> result = null;

        String[] cols = {COL_ID, COL_NAME, COL_IS_AVAILABLE, COL_BEGINNING_AT, COL_BEGINNING_AT,
                COL_FINISHED_AT, COL_DURATION, COL_CREATED_AT, COL_UPDATED_AT, COL_CATEGORY_ID};
        String whereClausesSelect = COL_CATEGORY_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_QCM, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        return c;
    }

}
