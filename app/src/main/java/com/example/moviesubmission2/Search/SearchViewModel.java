package com.example.moviesubmission2.Search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MovieSearch>> listMovies = new MutableLiveData<>();

    public void setMovie(final String movies){
        final ArrayList<MovieSearch> listMovie = new ArrayList<>();

        String apiKey = "127c768e99e7a9f8faa335e999d8a920";
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +apiKey+ "&language=en-US&query="+movies;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //parsing json
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        MovieSearch searchItems = new MovieSearch();
                        searchItems.setmID(object.getString("id"));
                        searchItems.setmTitle(object.getString("title"));
                        searchItems.setmDesc(object.getString("overview"));
                        searchItems.setmDate(object.getString("release_date"));
                        searchItems.setmImage(object.getString("poster_path"));
                        searchItems.setmRate(object.getString("vote_average"));
                        listMovie.add(searchItems);
                    }
                    //set data ke adapter
                    listMovies.postValue(listMovie);
                    
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

    //for TVshow

    public void setTv(final String movies){
        final ArrayList<MovieSearch> listMovie = new ArrayList<>();

        String apiKey = "127c768e99e7a9f8faa335e999d8a920";
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" +apiKey+ "&language=en-US&query="+movies;


        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //parsing json
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject object = list.getJSONObject(i);
                        MovieSearch searchItems = new MovieSearch();
                        searchItems.setmID(object.getString("id"));
                        searchItems.setmTitle(object.getString("name"));
                        searchItems.setmDesc(object.getString("overview"));
                        searchItems.setmDate(object.getString("first_air_date"));
                        searchItems.setmImage(object.getString("poster_path"));
                        searchItems.setmRate(object.getString("vote_average"));
                        listMovie.add(searchItems);
                    }
                    //set data ke adapter
                    listMovies.postValue(listMovie);
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

    public MutableLiveData<ArrayList<MovieSearch>> getListMovies() {
        return listMovies;
    }

    public void setListMovie(MutableLiveData<ArrayList<MovieSearch>> listMovies) {
        this.listMovies = listMovies;
    }
}


