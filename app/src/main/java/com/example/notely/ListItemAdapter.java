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
    public ListItemAdapter(Context context, int rid, List<ListItem> list){
        super(context, rid, list);
        mInflater =
                (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parent){
// Retrieve data
        ListItem item = (ListItem)getItem(position);
// Use layout file to generate View
        View view = mInflater.inflate(R.layout.list_item, null);
// Set image
        ImageView image;
        image = (ImageView)view.findViewById(R.id.image);
        image.setImageBitmap(item.image);
// Set user name
        TextView title;
        title = (TextView)view.findViewById(R.id.title);
        title.setText(item.title);
// Set comment
        TextView date;
        date = (TextView) view.findViewById(R.id.date);
        date.setText(item.date);
        return view;
    }
}