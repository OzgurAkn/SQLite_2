package com.example.ozgur.diary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ozgur on 28/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper
{

    private static final String TAG = " DatabaseHelper.class";

    private static final String CREATE_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + " (" +
            Constants.KEY_ID + " integer primary key autoincrement, " +
            Constants.TITLE_NAME + " text not null, " +
            Constants.CONTENT_NAME + " text not null, " +
            Constants.DATE_NAME + " long);";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.i(TAG, " Creating the tables of the database");
        try
        {
            db.execSQL(CREATE_TABLE);
        } catch (SQLiteException e)
        {
            Log.e(TAG, " Create table exception");
            Log.e(TAG, " " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.v(TAG, " Upgrading from version " + oldVersion + " to " + newVersion + " and will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }
}
