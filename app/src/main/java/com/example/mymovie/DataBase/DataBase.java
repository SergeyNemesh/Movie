package com.example.mymovie.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {


    public static final String TABLE_NAME = "my_collection";
    public static final String MOVIE_ID = "movieid";
    public static final String TITLE = "title";
    public static final String POSTER = "poster";
    public static final String OVERVIEW = "overview";
    public static final String RATE = "rate";
    public static final String DATA = "data";
    public static final String GENRES = "genre";

    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;

    public static final String DEL_NAME = "DELETE FROM " + TABLE_NAME + " WHERE " + MOVIE_ID + " = " ;

    public static final String TABLE_STRUCTURE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + MOVIE_ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT," + RATE + " INTEGER," + DATA + " INTEGER," + POSTER + " TEXT," + GENRES + " TEXT," + OVERVIEW + " TEXT)";

    public DataBase(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_STRUCTURE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}