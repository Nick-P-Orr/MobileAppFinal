package com.example.notely;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CategoryListAdapter extends BaseAdapter implements ListAdapter {
    private List<CategoryListItem> list;
    private Context context;
    private String enteredText = "";

    public CategoryListAdapter(List<CategoryListItem> list, Context context) {
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.category_list_item, null);
        }

        CategoryListItem item = (CategoryListItem) getItem(position);

        // Set image
        ImageView image;
        image = view.findViewById(R.id.image);
        image.setImageBitmap(item.Image);
        // Set category
        TextView category;
        category = view.findViewById(R.id.category1);
        category.setText(item.Category);


        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryListItem item = (CategoryListItem) getItem(position);
                Intent intent =
                        new Intent(context, IndividualCategory.class);
                Bundle bundle = new Bundle();
                bundle.putString("Category", item.getCategory());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }

        });

        //Rename and Delete Buttons
        Button deleteButton = view.findViewById(R.id.delete_button);
        Button editButton = view.findViewById(R.id.rename_button);

        if (Utils.getCurrentColorTheme().equals("Dark")) {
            deleteButton.setBackgroundColor(Color.DKGRAY);
            editButton.setBackgroundColor(Color.DKGRAY);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListItem item = (CategoryListItem) getItem(position);
                String Category = item.getCategory();

                updateCategory(Category, "Unlisted");

                Toast.makeText(context, Category + " Removed!",
                        Toast.LENGTH_LONG).show();

                list.remove(position);
                notifyDataSetChanged();

                Intent intent =
                        new Intent(context, Categories.class);
                context.startActivity(intent);

            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryListItem item = (CategoryListItem) getItem(position);
                final String Category = item.getCategory();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if (Utils.getCurrentColorTheme().equals("Dark")) {
                    builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                }

                builder.setTitle("Enter new Category");
                builder.setCancelable(false);

                // Set up the input
                final EditText input = new EditText(context);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_DATETIME_VARIATION_NORMAL);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enteredText = input.getText().toString();
                        updateCategory(Category, enteredText);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

        return view;
    }


    public void updateCategory(String oldCategory, String newCategory) {
        String path = "/data/data/" + context.getPackageName() + "/Notely.db";
        // Open the database. If it doesn't exist, create it.
        SQLiteDatabase db;
        db = SQLiteDatabase.openOrCreateDatabase(path, null);

        ContentValues newValues = new ContentValues();
        newValues.put("Category", newCategory);
        db.update("Notes", newValues, "Category = ?", new String[]{oldCategory});
        db.close();

        Intent intent =
                new Intent(context, Categories.class);
        context.startActivity(intent);
    }
}
