package com.example.moviesubmission2.TVShow;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TVShow implements Parcelable {
    Integer TVShowId;
    String TVShowImage;
    String TVShowDesc;
    String TVShowTitle;
    Double TVShowRate;
    String TVShowDate;

    public TVShow(Integer TVShowId, String TVShowImage, String TVShowTitle, Double TVShowRate, String TVShowDate, String TVShowDesc) {
        this.TVShowId = TVShowId;
        this.TVShowImage = TVShowImage;
        this.TVShowTitle = TVShowTitle;
        this.TVShowRate = TVShowRate;
        this.TVShowDate = TVShowDate;
        this.TVShowDesc = TVShowDesc;
    }

    protected TVShow(Parcel in) {
        TVShowId = in.readInt();
        TVShowImage = in.readString();
        TVShowTitle = in.readString();
        TVShowRate = in.readDouble();
        TVShowDate = in.readString();
        TVShowDesc = in.readString();
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public Integer getTVShowId() {
        return TVShowId;
    }

    public void setTVShowId(Integer TVShowId) {
        this.TVShowId = TVShowId;
    }

    public String getTVShowImage() {
        return TVShowImage;
    }

    public void setTVShowImage(String TVShowImage) {
        this.TVShowImage = TVShowImage;
    }

    public String getTVShowTitle() {
        return TVShowTitle;
    }

    public void setTVShowTitle(String TVShowTitle) {
        this.TVShowTitle = TVShowTitle;
    }

    public Double getTVShowRate() {
        return TVShowRate;
    }

    public void setTVShowRate(Double TVShowRate) {
        this.TVShowRate = TVShowRate;
    }

    public String getTVShowDate() {
        return TVShowDate;
    }

    public void setTVShowDate(String TVShowDate) {
        this.TVShowDate = TVShowDate;
    }

    public String getTVShowDesc() {
        return TVShowDesc;
    }

    public void setTVShowDesc(String TVShowDesc) {
        this.TVShowDesc = TVShowDesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(TVShowId);
        dest.writeString(TVShowImage);
        dest.writeString(TVShowTitle);
        dest.writeDouble(TVShowRate);
        dest.writeString(TVShowDate);
        dest.writeString(TVShowDesc);
    }

    public TVShow(JSONObject object) {
        try{
            Integer id = object.getInt("id");
            String title = object.getString("name");

            String release_date = object.getString("first_air_date");

            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");

            Double vote_average = object.getDouble("vote_average");

            this.TVShowId = id;
            this.TVShowTitle = title;

            this.TVShowDate = release_date;

            this.TVShowDesc = overview;
            this.TVShowImage = poster_path;


            this.TVShowRate = vote_average;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
