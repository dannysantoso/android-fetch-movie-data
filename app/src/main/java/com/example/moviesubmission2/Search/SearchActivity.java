package com.example.moviesubmission2.Search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesubmission2.Favorite.FavoriteActivity;
import com.example.moviesubmission2.Home.MainActivity;
import com.example.moviesubmission2.R;
import com.example.moviesubmission2.SettingActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
    SearchAdapter adapter;
    EditText textTitle;
    ProgressBar progressBar;
    Button btnTv, btnMovie;

    SearchViewModel searchViewModel;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.setting) {
//            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.favorite){
            Intent intent = new Intent(this, FavoriteActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }else if (item.getItemId() == R.id.search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textTitle = findViewById(R.id.textTitle);
        progressBar = findViewById(R.id.progressBar);
        btnTv = findViewById(R.id.btnTv);
        btnMovie = findViewById(R.id.btnMovie);

        RecyclerView rv = findViewById(R.id.movieList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchAdapter();
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);

        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);

        btnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = textTitle.getText().toString();

                if (TextUtils.isEmpty(movieTitle)){
                    Toast.makeText(SearchActivity.this, "Please input in search textfield", Toast.LENGTH_SHORT).show();
                }

                showLoading(true);
//                setMovie(movieTitle);
                searchViewModel.setMovie(movieTitle);
            }
        });



        btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieTitle = textTitle.getText().toString();

                if (TextUtils.isEmpty(movieTitle)){
                    Toast.makeText(SearchActivity.this, "Please input in search textfield", Toast.LENGTH_SHORT).show();
                }

                showLoading(true);
//                setTv(movieTitle);
                searchViewModel.setMovie(movieTitle);

            }
        });

        searchViewModel.getListMovies().observe(this, new Observer<ArrayList<MovieSearch>>() {
            @Override
            public void onChanged(ArrayList<MovieSearch> movieSearches) {
                adapter.setData(movieSearches);
                showLoading(false);
            }
        });


    }

//    public void setMovie(final String movies){
//        final ArrayList<MovieSearch> listMovie = new ArrayList<>();
//
//        String apiKey = "127c768e99e7a9f8faa335e999d8a920";
//        String url = "https://api.themoviedb.org/3/search/movie?api_key=" +apiKey+ "&language=en-US&query="+movies;
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    //parsing json
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    JSONArray list = responseObject.getJSONArray("results");
//
//                    for (int i = 0; i < list.length(); i++) {
//                        JSONObject object = list.getJSONObject(i);
//                        MovieSearch searchItems = new MovieSearch();
//                        searchItems.setmTitle(object.getString("title"));
//                        searchItems.setmDesc(object.getString("overview"));
//                        searchItems.setmDate(object.getString("release_date"));
//                        searchItems.setmImage(object.getString("poster_path"));
//                        searchItems.setmRate(object.getString("vote_average"));
//                        listMovie.add(searchItems);
//                    }
//                    //set data ke adapter
//                    adapter.setData(listMovie);
//                    showLoading(false);
//                } catch (Exception e) {
//                    Log.d("Exception", e.getMessage());
//                    showLoading(false);
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("onFailure", error.getMessage());
//            }
//        });
//    }

//    public void setTv(final String movies){
//        final ArrayList<MovieSearch> listMovie = new ArrayList<>();
//
//        String apiKey = "127c768e99e7a9f8faa335e999d8a920";
//        String url = "https://api.themoviedb.org/3/search/tv?api_key=" +apiKey+ "&language=en-US&query="+movies;
//
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        client.get(url, new AsyncHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                try {
//                    //parsing json
//                    String result = new String(responseBody);
//                    JSONObject responseObject = new JSONObject(result);
//                    JSONArray list = responseObject.getJSONArray("results");
//
//                    for (int i = 0; i < list.length(); i++) {
//                        JSONObject object = list.getJSONObject(i);
//                        MovieSearch searchItems = new MovieSearch();
//                        searchItems.setmTitle(object.getString("name"));
//                        searchItems.setmDesc(object.getString("overview"));
//                        searchItems.setmDate(object.getString("first_air_date"));
//                        searchItems.setmImage(object.getString("poster_path"));
//                        searchItems.setmRate(object.getString("vote_average"));
//                        listMovie.add(searchItems);
//                    }
//                    //set data ke adapter
//                    adapter.setData(listMovie);
//                    showLoading(false);
//                } catch (Exception e) {
//                    Log.d("Exception", e.getMessage());
//                    showLoading(false);
//                }
//
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                Log.d("onFailure", error.getMessage());
//            }
//        });
//    }



    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
