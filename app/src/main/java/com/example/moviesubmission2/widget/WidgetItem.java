package com.example.moviesubmission2.widget;


import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.GAMBAR;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.JUDUL;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RATE;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RILIS;
import static com.example.moviesubmission2.DatabaseContract.getColumnInt;
import static com.example.moviesubmission2.DatabaseContract.getColumnString;

public class WidgetItem {

    @SerializedName("description")
    private String mDesc;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("image")
    private String mImage;

    @SerializedName("date")
    private String mDate;

    @SerializedName("id")
    private int id;

    @SerializedName("rate")
    private String mRate;

    public String getmDesc() {
        return mDesc;
    }

    public void setmDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmRate() {
        return mRate;
    }

    public void setmRate(String mRate) {
        this.mRate = mRate;
    }

    public WidgetItem(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.mTitle = getColumnString(cursor, JUDUL);
        this.mImage = getColumnString(cursor, GAMBAR);
        this.mDate = getColumnString(cursor, RILIS);
        this.mDesc = getColumnString(cursor, DESKRIPSI);
        this.mRate = getColumnString(cursor, RATE);

    }

    @Override
    public String toString() {
        return
                "WidgetItem{" +
                        "description = '" + mDesc + '\'' +
                        ",title = '" + mTitle + '\'' +
                        ",image = '" + mImage + '\'' +
                        ",date = '" + mDate + '\'' +
                        ",id = '" + id + '\'' +
                        ",rate = '" + mRate + '\'' +
                        "}";
    }
}
