package com.example.moviesubmission2.Favorite;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesubmission2.Home.MainActivity;
import com.example.moviesubmission2.R;
import com.example.moviesubmission2.Search.SearchActivity;
import com.example.moviesubmission2.SettingActivity;

import static com.example.moviesubmission2.DatabaseContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    private Cursor mList;

    private FavoriteAdapter mAdapter;

    private RecyclerView mRecycler;

    private ProgressBar mProgressBar;

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
        setContentView(R.layout.activity_favorite);

        mRecycler = findViewById(R.id.movieList);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);

        mProgressBar = findViewById(R.id.progressBar);

        mAdapter = new FavoriteAdapter(getApplicationContext());
        mAdapter.setListMovies(mList);
        mRecycler.setAdapter(mAdapter);

        new LoadMovieAsync().execute();
    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            mProgressBar.setVisibility(View.GONE);

            mList = notes;
            mAdapter.setListMovies(mList);
            mAdapter.notifyDataSetChanged();

            if (mList.getCount() == 0){
//                showSnackbarMessage();
            }
        }
    }
}
