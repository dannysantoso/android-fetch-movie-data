package com.example.consumeapp;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.consumeapp.Favorite.FavoriteAdapter;

import static com.example.consumeapp.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    private Cursor mList;

    private FavoriteAdapter mAdapter;

    private RecyclerView mRecycler;

    private ProgressBar mProgressBar;

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

            try {
                if (mList.getCount() == 0){
                    Toast.makeText(MainActivity.this, "NO DATA", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(MainActivity.this, "NO DATA", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
