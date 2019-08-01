package com.example.notely;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListItemAdapter extends BaseAdapter implements ListAdapter {
    private List<ListItem> list;
    private Context context;

    public ListItemAdapter(List<ListItem> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        ListItem item = (ListItem) getItem(position);

        ImageView image;
        image = view.findViewById(R.id.image);
        image.setImageBitmap(item.Image);
        // Set title
        TextView title;
        title = view.findViewById(R.id.title1);
        title.setText(item.Title);
        // Set category
        TextView category;
        category = view.findViewById(R.id.category1);
        category.setText(item.Category);
        // Set start_date
        TextView startDate;
        startDate = view.findViewById(R.id.startDate1);
        startDate.setText(item.StartDate);
        // Set end_date
        TextView endDate;
        endDate = view.findViewById(R.id.endDate1);
        endDate.setText(item.EndDate);


        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ListItem item = (ListItem) getItem(position);

                String strings;
                strings = ("Title: " + item.getTitle() + "\n" + "\n");
                strings += ("Category: " + item.getCategory() + "\n" + "\n");
                strings += ("Start Date: " + item.getStartDate() + "\n" + "\n");
                strings += ("End Date: " + item.getEndDate() + "\n" + "\n");

                //Get and read the text file
                File file = new File(item.getFilePath());
                StringBuilder noteText = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = br.readLine()) != null) {
                        noteText.append(line);
                        noteText.append('\n');
                    }
                    br.close();
                } catch (IOException e) {
                }

                strings += ("Note: " + noteText);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                sendIntent.putExtra(Intent.EXTRA_TEXT, strings);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Notely: " + item.getTitle());
                sendIntent.setType("text/plain");
                context.startActivity(Intent.createChooser(sendIntent, "Share Via:"));

                return true;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                Bundle bundle = new Bundle();
                ListItem item = (ListItem) getItem(position);
                bundle.putString("NoteID", item.getNoteID());
                bundle.putString("Title", item.getTitle());
                bundle.putString("Category", item.getCategory());
                bundle.putString("StartDate", item.getStartDate());
                bundle.putString("EndDate", item.getEndDate());
                bundle.putString("FilePath", item.getFilePath());
                bundle.putString("LastEdit", item.getLastEdit());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        //Edit and Delete Buttons
        Button deleteButton = view.findViewById(R.id.delete_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListItem item = (ListItem) getItem(position);
                String Title = item.getTitle();
                String LastEdit = item.getLastEdit();
                String FilePath = item.getFilePath();
                String path = "/data/data/" + context.getPackageName() + "/Notely.db";
                // Open the database. If it doesn't exist, create it.
                SQLiteDatabase db;
                db = SQLiteDatabase.openOrCreateDatabase(path, null);
                db.delete("Notes", "Title = ? and LastEdit = ?", new String[]{Title, LastEdit});
                db.close();

                try {
                    File file = new File(FilePath);
                    file.delete();
                } catch (NullPointerException e) {
                }

                Toast.makeText(context, Title + " Deleted!",
                        Toast.LENGTH_LONG).show();

                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
}