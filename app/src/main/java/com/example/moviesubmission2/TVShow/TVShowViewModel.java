package com.example.moviesubmission2.TVShow;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesubmission2.Movie.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVShowViewModel extends ViewModel {
    private static final String API_KEY = "127c768e99e7a9f8faa335e999d8a920";
    private MutableLiveData<ArrayList<TVShow>> listTvShows = new MutableLiveData<>();

    public void setTVShows(final String tvShows){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> listItems = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" +API_KEY+ "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        TVShow tvshowItems = new TVShow(weather);
                        listItems.add(tvshowItems);
                    }
                    listTvShows.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });

}
    public LiveData<ArrayList<TVShow>> getTVShows() {
        return listTvShows;
    }
}
