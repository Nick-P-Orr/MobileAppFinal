package com.example.notely;

import android.graphics.Bitmap;

public class ListItem {
    public Bitmap Image;
    public String NoteID;
    public String Title;
    public String Category;
    public String StartDate;
    public String EndDate;
    public String LastEdit;
    public String FilePath;

    public String getNoteID() {
        return this.NoteID;
    }

    public String getTitle() {
        return this.Title;
    }

    public String getCategory() {
        return this.Category;
    }

    public String getStartDate() {
        return this.StartDate;
    }

    public String getEndDate() {
        return this.EndDate;
    }

    public String getFilePath() {
        return this.FilePath;
    }

    public String getLastEdit() {
        return this.LastEdit;
    }
}


