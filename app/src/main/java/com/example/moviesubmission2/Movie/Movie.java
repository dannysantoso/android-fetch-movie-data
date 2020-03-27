package com.example.moviesubmission2.Movie;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    Integer movieId;
    String movieImage;
    String movieDesc;
    String movieTitle;
    Double movieRate;
    String movieDate;


    public Movie(Integer movieId, String movieImage, String movieTitle, Double movieRate, String movieDesc, String movieDate) {
        this.movieId = movieId;
        this.movieImage = movieImage;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieDesc = movieDesc;
        this.movieDate = movieDate;
    }



    protected Movie(Parcel in) {
        movieId = in.readInt();
        movieImage = in.readString();
        movieTitle = in.readString();
        movieRate = in.readDouble();
        movieDesc = in.readString();
        movieDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Double getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(Double movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getMovieDate() {
        return movieDate;
    }

    public void setMovieDate(String movieDate) {
        this.movieDate = movieDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(movieImage);
        dest.writeString(movieTitle);
        dest.writeDouble(movieRate);
        dest.writeString(movieDesc);
        dest.writeString(movieDate);
    }

    public Movie(JSONObject object) {
        try {
            Integer id = object.getInt("id");
            Double vote_average = object.getDouble("vote_average");
            String title = object.getString("title");

            String overview = object.getString("overview");
            String release_date = object.getString("release_date");
            String poster_path = object.getString("poster_path");

            this.movieId = id;

            this.movieRate = vote_average;
            this.movieTitle = title;


            this.movieDesc = overview;
            this.movieDate = release_date;
            this.movieImage = poster_path;


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
