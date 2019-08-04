package com.example.notely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListItemAdapter extends ArrayAdapter<ListItem> {
    private LayoutInflater mInflater;

    public ListItemAdapter(Context context, int rid, List<ListItem> list) {
        super(context, rid, list);
        mInflater =
                (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Retrieve data
                ListItem item = getItem(position);
        // Use layout file to generate View
                View view = mInflater.inflate(R.layout.list_item, null);
        // Set image
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
                return view;
    }
}