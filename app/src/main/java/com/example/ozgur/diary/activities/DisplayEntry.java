package com.example.ozgur.diary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.ozgur.diary.R;
import com.example.ozgur.diary.database.Constants;

public class DisplayEntry extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_entry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if(intent != null){
            String title = intent.getStringExtra(Constants.TITLE_NAME);
            String date = intent.getStringExtra(Constants.DATE_NAME);
            String content = intent.getStringExtra(Constants.CONTENT_NAME);

            final TextView vTitle = (TextView)findViewById(R.id.diaryShowTitle);
            final TextView vDate = (TextView)findViewById(R.id.diaryShowDate);
            final TextView vContent = (TextView)findViewById(R.id.diaryShowContent);

            vTitle.setText(title);
            vDate.setText(date);
            vContent.setText(content);
        }
    }

}
