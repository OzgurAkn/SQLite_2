package com.example.ozgur.diary.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Ozgur on 28/03/2016.
 */
public class DiaryContentProvider extends ContentProvider
{
    private Database database;
    private static final UriMatcher uriMatcher;
    private static final int DIARIES = 1;

    public static final String AUTHORITY = "com.example.ozgur.diary";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + Constants.TABLE_NAME);

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, Constants.TABLE_NAME, DIARIES);
    }

    @Override
    public boolean onCreate()
    {
        this.database = new Database(this.getContext());
        this.database.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Cursor cursor = null;
        switch(uriMatcher.match(uri)){
            case DIARIES:
                cursor = this.database.getDiaries();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI" + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        this.database.insertDiary(values);
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
