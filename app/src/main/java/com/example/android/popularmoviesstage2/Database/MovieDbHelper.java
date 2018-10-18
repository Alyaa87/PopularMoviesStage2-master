package com.example.android.popularmoviesstage2.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmoviesstage2.Data.Contract;

public class MovieDbHelper extends SQLiteOpenHelper {
    //create constants.
    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 1;
    //constructor
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLE movie (_id INTEGER, name TEXT);
        String SQL_CREATE_MOVIE_TABLE =  "CREATE TABLE " + Contract.movieEntry.TABLE_MOVIE_NAME + "("
                + Contract.movieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.movieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean verification(String id) throws SQLException {
        int count = -1;
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            String query = "SELECT COUNT(*) FROM "
                    + Contract.movieEntry.TABLE_MOVIE_NAME + " WHERE " + Contract.movieEntry._ID + " = ?";
            c = db.rawQuery(query, new String[]{id});
            if (c.moveToFirst()) {
                count = c.getInt(0);
            }
            return count > 0;
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }
}
