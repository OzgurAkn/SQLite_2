package com.example.ozgur.diary.activities;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ozgur.diary.R;
import com.example.ozgur.diary.adapters.DiaryAdapter;
import com.example.ozgur.diary.database.Constants;
import com.example.ozgur.diary.database.Database;
import com.example.ozgur.diary.database.DatabaseHelper;
import com.example.ozgur.diary.database.DiaryContentProvider;
import com.example.ozgur.diary.models.DiaryEntry;

import org.w3c.dom.Text;

public class DisplayDiaries extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>
{

    private Database database;
    private DiaryAdapter adapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_diaries);

        this.database = new Database(this);
        this.database.open();

        this.adapter = new DiaryAdapter(this.database, this);
        this.setListAdapter(this.adapter);

        this.textView = (TextView) this.findViewById(R.id.list_count);
        textView.setText("Count: " + this.adapter.getCount());

        getLoaderManager().initLoader(0, null, this);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                DiaryEntry entry = (DiaryEntry) adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), DisplayEntry.class);
                intent.putExtra(Constants.TITLE_NAME, entry.getTitle());
                intent.putExtra(Constants.DATE_NAME, entry.getRecordedDate());
                intent.putExtra(Constants.CONTENT_NAME, entry.getContent());
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DisplayDiaries.this.addEntries();
            }
        });
    }

    private void addEntries()
    {
        ContentValues values = new ContentValues();
        values.put(Constants.TITLE_NAME, Constants.SAMPLE_TITLE);
        values.put(Constants.CONTENT_NAME, Constants.SAMPLE_CONTENT);
        values.put(Constants.DATE_NAME, System.currentTimeMillis());

        this.database.insertDiary()
        for (int i = 0; i < 1000; i++)
        {
            ContentResolver contentResolver = getContentResolver();
            contentResolver.insert(DiaryContentProvider.CONTENT_URI, values);
        }

        this.adapter.notifyDataSetChanged();

        CharSequence text = "Added 1000 entries";
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();

        startActivity(new Intent(this, DisplayDiaries.class));
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(this, DiaryContentProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if (data != null)
        {
            this.adapter.setData(data);
            textView.setText("Count: " + this.adapter.getCount());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        this.adapter.setDataToNull();
    }
}
