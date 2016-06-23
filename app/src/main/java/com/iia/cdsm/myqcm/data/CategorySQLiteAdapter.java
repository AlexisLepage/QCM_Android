package com.iia.cdsm.myqcm.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.iia.cdsm.myqcm.Entities.Category;

import java.util.ArrayList;

/**
 * Created by Alexis on 20/02/2016.
 */
public class CategorySQLiteAdapter {

    public static final String TABLE_CATEGORY = "category";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "name";
    public static final String COL_CREATED_AT = "created_at";
    public static final String COL_UPDATED_AT = "updated_at";

    public SQLiteDatabase db;
    private MyQcmSQLiteOpenHelper helper;
    private Context ctx;

    /**
     * Helper Object to access db
     * @param context
     */
    public CategorySQLiteAdapter(Context context){
        this.ctx = context;
        helper = new MyQcmSQLiteOpenHelper(context, MyQcmSQLiteOpenHelper.DB_NAME, null, 1);
    }

    /**
     * Script SQL for Create Table Category
     * @return String
     */
    public static String getSchema(){
        return "CREATE TABLE " + TABLE_CATEGORY + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_CREATED_AT + " TEXT NOT NULL, "
                + COL_UPDATED_AT + " TEXT NOT NULL);";
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
     * Insert Category in DB
     * @param category
     * @return line result
     */
    public long insertCategory(Category category){
        return db.insert(TABLE_CATEGORY, null, this.categoryToContentValues(category));
    }

    /**
     * Update Category in DB
     * @param category
     * @return line result
     */
    public long updateCategory(Category category){
        ContentValues valuesUpdate = this.categoryToContentValues(category);
        String whereClausesUpdate = COL_ID + "= ?";
        String[] whereArgsUpdate =  {String.valueOf(category.getId())};

        return db.update(TABLE_CATEGORY, valuesUpdate, whereClausesUpdate, whereArgsUpdate);
    }

    /**
     * Select a Category with his Id.
     * @param id
     * @return Category
     */
    public Category getCategory(long id){

        String[] cols = {COL_ID, COL_NAME, COL_CREATED_AT, COL_UPDATED_AT};
        String whereClausesSelect = COL_ID + "= ?";
        String[] whereArgsSelect = {String.valueOf(id)};

        Cursor c = db.query(TABLE_CATEGORY, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Category result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Select a Category with his Name.
     * @param name
     * @return Category
     */
    public Category getCategoryByName(String name){

        String[] cols = {COL_ID, COL_NAME, COL_CREATED_AT, COL_UPDATED_AT};
        String whereClausesSelect = COL_NAME + "= ?";
        String[] whereArgsSelect = {String.valueOf(name)};

        Cursor c = db.query(TABLE_CATEGORY, cols, whereClausesSelect, whereArgsSelect,null, null, null);

        Category result = null;

        if (c.getCount() > 0){
            c.moveToFirst();
            result = cursorToItem(c);
        }

        return result;
    }

    /**
     * Get all Category
     * @return ArrayList<>
     */
    public ArrayList<Category> getAllCategory(){
        ArrayList<Category> result = null;
        Cursor c = getAllCursor();

        if (c.moveToFirst()){
            result = new ArrayList<Category>();
            do {
                result.add(this.cursorToItem(c));
            } while (c.moveToNext());
        }
        c.close();
        return result;
    }

    /**
     * Delete Category with category object
     * @param category
     * @return long
     */
    public long deleteCategory(Category category){
        String whereClausesDelete = COL_ID + "= ?";
        String[] whereArgsDelete = {String.valueOf(category.getId())};

        return this.db.delete(TABLE_CATEGORY, whereClausesDelete, whereArgsDelete);
    }

    /**
     * Convert Category to ContentValues
     * @param category
     * @return ContentValue
     */
    private ContentValues categoryToContentValues(Category category){
        ContentValues values = new ContentValues();
        values.put(COL_ID, category.getId());
        values.put(COL_NAME, category.getName());
        values.put(COL_CREATED_AT, category.getCreated_at());
        values.put(COL_UPDATED_AT, category.getUpdated_at());

        return values;
    }

    /**
     * Cursor convert to Category
     * @param c
     * @return Category
     */
    public Category cursorToItem(Cursor c){
        Category result = new Category();
        result.setId(c.getLong(c.getColumnIndex(COL_ID)));
        result.setName(c.getString(c.getColumnIndex(COL_NAME)));
        result.setCreated_at(c.getString(c.getColumnIndex(COL_CREATED_AT)));
        result.setUpdated_at(c.getString(c.getColumnIndex(COL_UPDATED_AT)));

        return result;
    }

    /**
     * Get all Cursor in Category Table
     * @return Cursor
     */
    public Cursor getAllCursor(){
        String[] cols = {COL_ID, COL_NAME, COL_CREATED_AT, COL_UPDATED_AT};
        Cursor c = db.query(TABLE_CATEGORY, cols, null, null, null, null, null);
        return c;
    }
}
