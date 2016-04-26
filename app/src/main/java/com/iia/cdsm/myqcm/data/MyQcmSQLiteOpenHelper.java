package com.iia.cdsm.myqcm.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iia.cdsm.myqcm.Entities.TypeMedia;

/**
 * Created by Alexis on 17/02/2016.
 */
public class MyQcmSQLiteOpenHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "myqcm.sqlite";

    public MyQcmSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyQcmSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AnswerSQLiteAdapter.getSchema());
        db.execSQL(CategorySQLiteAdapter.getSchema());
        db.execSQL(MediaSQLiteAdapter.getSchema());
        db.execSQL(QcmSQLiteAdapter.getSchema());
        db.execSQL(QuestionSQLiteAdapter.getSchema());
        db.execSQL(TypeMediaSQLiteAdapter.getSchema());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
