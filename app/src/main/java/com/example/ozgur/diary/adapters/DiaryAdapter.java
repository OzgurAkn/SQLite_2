package com.example.ozgur.diary.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ozgur.diary.R;
import com.example.ozgur.diary.database.Constants;
import com.example.ozgur.diary.database.Database;
import com.example.ozgur.diary.models.DiaryEntry;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ozgur on 28/03/2016.
 */
public class DiaryAdapter extends BaseAdapter
{

    private Database database;
    private LayoutInflater inflater;
    private ArrayList<DiaryEntry> entries;

    public DiaryAdapter(Database database, Context context)
    {
        this.database = database;
        inflater = LayoutInflater.from(context);
        entries = new ArrayList<DiaryEntry>();
    }

    public void setData(Cursor cursor)
    {
        if (cursor.moveToFirst())
        {
            do
            {
                String title = cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME));
                String content = cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME));

                DateFormat dateFormat = DateFormat.getDateTimeInstance();
                long rawDate = cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME));

                Date date = new Date(rawDate);
                String dateDate = dateFormat.format(date);

                DiaryEntry entry = new DiaryEntry(title, content, dateDate);

                this.entries.add(entry);
            }
            while (cursor.moveToNext());
        }
    }

    public void setDataToNull()
    {
        this.entries.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return this.entries.size();
    }

    @Override
    public Object getItem(int position)
    {
        return this.entries.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder holder;
        View view = convertView;
        if ((view == null) || view.getTag() == null)
        {
            //The ViewHolder has not yet been made and must be made
            view = this.inflater.inflate(R.layout.listitem_main, null);

            holder = new ViewHolder();
            holder.setTitle((TextView) view.findViewById(R.id.name));
            holder.setDate((TextView) view.findViewById(R.id.datetext));

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        //Set the required information
        holder.setEntry((DiaryEntry) getItem(position));
        holder.getTitle().setText(holder.getEntry().getTitle());
        holder.getDate().setText(holder.getEntry().getRecordedDate());

        view.setTag(holder);
        return view;
    }

    private class ViewHolder
    {
        private DiaryEntry entry;
        private TextView title;
        private TextView date;

        public DiaryEntry getEntry()
        {
            return entry;
        }

        public void setEntry(DiaryEntry entry)
        {
            this.entry = entry;
        }

        public TextView getDate()
        {
            return date;
        }

        public void setDate(TextView date)
        {
            this.date = date;
        }

        public TextView getTitle()
        {
            return title;
        }

        public void setTitle(TextView title)
        {
            this.title = title;
        }
    }
}
