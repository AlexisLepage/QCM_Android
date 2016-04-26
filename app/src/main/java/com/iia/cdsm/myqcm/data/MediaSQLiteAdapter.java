package com.iia.cdsm.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.myqcm.Entities.Media;

import java.util.ArrayList;

/**
 * Created by Alexis on 18/02/2016.
 */
public class MediaSQLiteAdapter {

    public static final String TABLE_MEDIA = "media";
    public static final String TABLE_TYPEMEDIA = "typeMedia";
    public static final String TABLE_QUESTION = "question";

    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_URL = "url";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_UPDATED_AT = "updated_at";
    public static final String COL_TYPEMEDIA_ID = "typeMedia_id";
    public static final String COL_QUESTION_ID = "question_id";

    private SQLiteDatabase db;
    private MyQcmSQLiteOpenHelper helper;

    /**
     * Helper Object to access db
     * @param context
     */
    public MediaSQLiteAdapter(Context context){
        helper = new MyQcmSQLiteOpenHelper(context, MyQcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Script SQL for Create Table Media
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_MEDIA + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_URL + " TEXT NOT NULL, "
                + COL_CREATED_AT + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL, "
                + COL_TYPEMEDIA_ID + " INTEGER, "
                + COL_QUESTION_ID + " INTEGER, "
                + "FOREIGN KEY(" + COL_TYPEMEDIA_ID + ") REFERENCES "
                + TABLE_TYPEMEDIA + "(id) " + ", "
                + "FOREIGN KEY(" + COL_QUESTION_ID + ") REFERENCES "
                + TABLE_QUESTION + "(id) " + ");";
    }

    public void open(){
        this.db = this.helper.getWritableDatabase();
    }

    public void close(){
        this.db.close();
    }

    /**
     * Insert Media in DB
     * @param media
     * @return line result
     */
    public long insertMedia(Media media){
        return db.insert(TABLE_MEDIA, null, this.mediaToContentValues(media));
    }

    /**
     * Update Media in DB
     * @param media
     * @return line result
     */
    public long updateMedia(Media media){
        ContentValues valuesUpdate = this.mediaToContentValues(media);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(media.getId())};

        return db.update(TABLE_MEDIA, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Media with his Id.
     * @param id
     * @return Media
     */
    public Media getMedia(long id){

        String[] cols = {COL_ID, COL_URL, COL_NAME, COL_CREATED_AT, COL_UPDATED_AT, COL_TYPEMEDIA_ID, COL_QUESTION_ID};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_MEDIA, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Media result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Get all Media
     * @return ArrayList<>
     */
    public ArrayList<Media> getAllMedia(){
        ArrayList<Media> result = null;
        Cursor c = getAllCursor();

        if (c.moveToFirst()){
            result = new ArrayList<Media>();
            do {
                result.add(this.cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Delete Media with media object
     * @param media
     * @return long
     */
    public long deleteMedia(Media media){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(media.getId())};

        return this.db.delete(TABLE_MEDIA, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Convert media to ContentValues
     * @param media
     * @return ContentValue
     */
    private ContentValues mediaToContentValues(Media media){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, media.getName());
        values.put(COL_URL, media.getUrl());
        values.put(COL_CREATED_AT, media.getCreated_at());
        values.put(COL_UPDATED_AT, media.getUpdated_at());
        values.put(COL_TYPEMEDIA_ID, media.getTypeMedia_id());
        values.put(COL_QUESTION_ID, media.getQuestion_id());

        return values;
    }

    /**
     * Cursor convert to Media
     * @param c
     * @return Media
     */
    public Media cursorToItem(Cursor c){
        Media result = new Media();
        result.setId(c.getLong(c.getColumnIndex(COL_ID)));
        result.setName(c.getString(c.getColumnIndex(COL_NAME)));
        result.setUrl(c.getString(c.getColumnIndex(COL_URL)));
        result.setCreated_at();
        result.setUpdated_at();
        result.setTypeMedia_id(c.getLong(c.getColumnIndex(COL_TYPEMEDIA_ID)));
        result.setQuestion_id(c.getLong(c.getColumnIndex(COL_QUESTION_ID)));

        return result;
    }

    /**
     * Get all Cursor in Media Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_NAME, COL_URL, COL_CREATED_AT, COL_UPDATED_AT, COL_TYPEMEDIA_ID, COL_QUESTION_ID};
        Cursor c = db.query(TABLE_MEDIA, cols, null, null, null, null, null);
        return c;
    }
}
