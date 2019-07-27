package com.example.notely;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CategoryListAdapter extends ArrayAdapter<CategoryListItem> {
    private LayoutInflater mInflater;

    public CategoryListAdapter(Context context, int rid, List<CategoryListItem> list) {
        super(context, rid, list);
        mInflater =
                (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Retrieve data
        CategoryListItem item = getItem(position);
        // Use layout file to generate View
        View view = mInflater.inflate(R.layout.list_item, null);
        // Set image
        ImageView image;
        image = view.findViewById(R.id.image);
        image.setImageBitmap(item.Image);
        // Set category
        TextView category;
        category = view.findViewById(R.id.category1);
        category.setText(item.Category);
        return view;
    }
}
