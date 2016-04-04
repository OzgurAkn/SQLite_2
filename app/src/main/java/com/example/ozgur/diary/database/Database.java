package com.example.ozgur.diary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by Ozgur on 28/03/2016.
 */
public class Database
{
    private static final String TAG = "Database.class";
    private SQLiteDatabase database;
    private final DatabaseHelper dbHelper;

    public Database(Context context)
    {
        dbHelper = new DatabaseHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    public void close()
    {
        database.close();
    }

    public void open()
    {
        try
        {
            database = dbHelper.getWritableDatabase();
        } catch (SQLiteException e)
        {
            Log.e(TAG, "Could not create a writeable database. Readable database has been opened");
            Log.e(TAG, e.getMessage());
            database = dbHelper.getReadableDatabase();
        }
    }


    public long insertDiary(ContentValues newDiaryValue)
    {
        try
        {
            newDiaryValue.put(Constants.DATE_NAME, System.currentTimeMillis());
            return database.insert(Constants.TABLE_NAME, null, newDiaryValue);
        } catch (SQLiteException e)
        {
            Log.e(TAG, "Inserting into database did not work");
            Log.e(TAG, e.getMessage());
            return -1;
        }
    }

    public Cursor getDiaries()
    {
        Cursor c = database.query(Constants.TABLE_NAME, null, null, null, null, null, null);
        return c;
    }
}
