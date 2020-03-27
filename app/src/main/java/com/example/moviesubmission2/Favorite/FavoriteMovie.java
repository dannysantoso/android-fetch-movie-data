package com.example.moviesubmission2.Favorite;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.GAMBAR;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.JUDUL;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RATE;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RILIS;
import static com.example.moviesubmission2.DatabaseContract.getColumnInt;
import static com.example.moviesubmission2.DatabaseContract.getColumnString;

public class FavoriteMovie implements Parcelable {

    Integer movieId;
    String movieImage;
    String movieDesc;
    String movieTitle;
    String movieRate;
    String movieDate;

    public FavoriteMovie(Integer movieId, String movieImage, String movieDesc, String movieTitle, String movieRate, String movieDate) {
        this.movieId = movieId;
        this.movieImage = movieImage;
        this.movieDesc = movieDesc;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieDate = movieDate;
    }

    public FavoriteMovie(Cursor cursor){
        this.movieId = getColumnInt(cursor, _ID);
        this.movieTitle = getColumnString(cursor, JUDUL);
        this.movieDesc = getColumnString(cursor, DESKRIPSI);
        this.movieDate = getColumnString(cursor, RILIS);
        this.movieImage = getColumnString(cursor, GAMBAR);
        this.movieRate = getColumnString(cursor, RATE);
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(String movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    protected FavoriteMovie(Parcel in) {
        movieId = in.readInt();
        movieImage = in.readString();
        movieDesc = in.readString();
        movieTitle = in.readString();
        movieRate = in.readString();
        movieDate = in.readString();
    }

    public static final Creator<FavoriteMovie> CREATOR = new Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel in) {
            return new FavoriteMovie(in);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(movieImage);
        dest.writeString(movieDesc);
        dest.writeString(movieTitle);
        dest.writeString(movieRate);
        dest.writeString(movieDate);
    }
}
