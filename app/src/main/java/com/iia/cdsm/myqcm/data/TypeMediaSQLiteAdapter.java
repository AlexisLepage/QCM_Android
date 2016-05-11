package com.iia.cdsm.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.myqcm.Entities.TypeMedia;

import java.util.ArrayList;

/**
 * Created by Alexis on 20/02/2016.
 */
public class TypeMediaSQLiteAdapter {

    public static final String TABLE_TYPEMEDIA = "typeMedia";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";

    private SQLiteDatabase db;
    private MyQcmSQLiteOpenHelper helper;

    /**
     * Helper Object to access db
     * @param context
     */
    public TypeMediaSQLiteAdapter(Context context){
        helper = new MyQcmSQLiteOpenHelper(context, MyQcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Script SQL for Create Table TypeMedia
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_TYPEMEDIA + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL);";
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
     * Insert TypeMedia in DB
     * @param typeMedia
     * @return line result
     */
    public long insertTypeMedia(TypeMedia typeMedia){
        return db.insert(TABLE_TYPEMEDIA, null, this.typeMediaToContentValues(typeMedia));
    }

    /**
     * Update TypeMedia in DB
     * @param typeMedia
     * @return line result
     */
    public long updateTypeMedia(TypeMedia typeMedia){
        ContentValues valuesUpdate = this.typeMediaToContentValues(typeMedia);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(typeMedia.getId())};

        return db.update(TABLE_TYPEMEDIA, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a TypeMedia with his Id.
     * @param id
     * @return TypeMedia
     */
    public TypeMedia getTypeMedia(long id){

        String[] cols = {COL_ID, COL_NAME};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_TYPEMEDIA, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        TypeMedia result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Get all TypeMedia
     * @return ArrayList<>
     */
    public ArrayList<TypeMedia> getAllTypeMedia(){
        ArrayList<TypeMedia> result = null;
        Cursor c = getAllCursor();

        if (c.moveToFirst()){
            result = new ArrayList<TypeMedia>();
            do {
                result.add(this.cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Delete TypeMedia with typeMedia object
     * @param typeMedia
     * @return long
     */
    public long deleteTypeMedia(TypeMedia typeMedia){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(typeMedia.getId())};

        return this.db.delete(TABLE_TYPEMEDIA, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Convert typeMedia to ContentValues
     * @param typeMedia
     * @return ContentValue
     */
    private ContentValues typeMediaToContentValues(TypeMedia typeMedia){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, typeMedia.getName());

        return values;
    }

    /**
     * Cursor convert to TypeMedia
     * @param c
     * @return TypeMedia
     */
    public TypeMedia cursorToItem(Cursor c){
        TypeMedia result = new TypeMedia();
        result.setId(c.getLong(c.getColumnIndex(COL_ID)));
        result.setName(c.getString(c.getColumnIndex(COL_NAME)));

        return result;
    }

    /**
     * Get all Cursor in TypeMedia Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_NAME};
        Cursor c = db.query(TABLE_TYPEMEDIA, cols, null, null, null, null, null);
        return c;
    }
}
