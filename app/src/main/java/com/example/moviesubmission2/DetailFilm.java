package com.example.moviesubmission2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviesubmission2.Database.FavMovieHelper;
import com.example.moviesubmission2.Favorite.FavoriteActivity;
import com.example.moviesubmission2.Home.MainActivity;
import com.example.moviesubmission2.Movie.Movie;
import com.example.moviesubmission2.Search.SearchActivity;
import com.example.moviesubmission2.TVShow.TVShow;

import static android.provider.BaseColumns._ID;
import static com.example.moviesubmission2.DatabaseContract.CONTENT_URI;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.GAMBAR;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.JUDUL;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RATE;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RILIS;

public class DetailFilm extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TVSHOW = "extra_tvshow";

    TextView tvFilmTitle, tvFilmDate, tvFilmRate, tvFilmDesc, tvFilmID;
    ImageView ivFilmImage, favorite, unfavorite;

    String img;

    boolean isAdd;

    private ProgressBar progressBar;


//    DatabaseHelper db;
//    FavMovieHelper favMovieHelper;

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
        setContentView(R.layout.activity_detail_film);

        tvFilmID = findViewById(R.id.id);
        tvFilmDate = findViewById(R.id.date);
        tvFilmDesc = findViewById(R.id.desc);
        tvFilmRate = findViewById(R.id.rate);
        tvFilmTitle = findViewById(R.id.title);
        ivFilmImage = findViewById(R.id.image);
        progressBar = findViewById(R.id.progressBarShowDetail);
        favorite = findViewById(R.id.favorite);
        unfavorite = findViewById(R.id.unfavorite);

        progressBar.setVisibility(View.VISIBLE);

        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        final TVShow tvshow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        final Handler handler = new Handler();
//        favMovieHelper = new FavMovieHelper(this);

        if (movie != null){

                    try{
                        Thread.sleep(5000);
                    }
                    catch (Exception e) { }

                    handler.post(new Runnable() {
                        public void run() {


                            String vote_average = Double.toString(movie.getMovieRate());
                            String idFilm = Integer.toString(movie.getMovieId());
                            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getMovieImage();
                            img = url_image;

                            tvFilmID.setText(idFilm);
                            tvFilmTitle.setText(movie.getMovieTitle());
                            tvFilmDate.setText(movie.getMovieDate());
                            tvFilmRate.setText(vote_average);
                            tvFilmDesc.setText(movie.getMovieDesc());




                            Glide.with(DetailFilm.this)
                                    .load(url_image)
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .into(ivFilmImage);


                            progressBar.setVisibility(View.INVISIBLE);

                            FavMovieHelper Helper = FavMovieHelper.getInstance(getApplicationContext());
                            Helper.open();
                            Cursor cursor = getContentResolver().query(
                                    Uri.parse(CONTENT_URI + "/" + idFilm),
                                    null,
                                    null,
                                    null,
                                    null
                            );

                            if (cursor != null) {
                                if (cursor.moveToFirst()) isAdd = true;
                                cursor.close();
                            }else{
//                                Log.d("tes", idFilm);
                            }


                            if (isAdd){
                                favorite.setVisibility(View.INVISIBLE);
                                unfavorite.setVisibility(View.VISIBLE);
                            }else{
                                favorite.setVisibility(View.VISIBLE);
                                unfavorite.setVisibility(View.INVISIBLE);
                            }






                        }
                    });


            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(_ID, movie.getMovieId());
                    contentValues.put(JUDUL, movie.getMovieTitle());
                    contentValues.put(DESKRIPSI, movie.getMovieDesc());
                    contentValues.put(RILIS, movie.getMovieDate());
                    contentValues.put(GAMBAR, movie.getMovieImage());
                    contentValues.put(RATE, movie.getMovieRate());
                    getContentResolver().insert(CONTENT_URI,contentValues);
                    favorite.setVisibility(View.INVISIBLE);
                    unfavorite.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailFilm.this, "Added to Favorite", Toast.LENGTH_SHORT).show();

                }


            });

            unfavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContentResolver().delete(
                            Uri.parse(CONTENT_URI + "/" + movie.getMovieId()),
                            null,
                            null
                    );
                    unfavorite.setVisibility(View.INVISIBLE);
                    favorite.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailFilm.this, "Remove from Favorite", Toast.LENGTH_SHORT).show();
                }
            });




        }else{

                    try{
                        Thread.sleep(5000);
                    }
                    catch (Exception e) { }

                    handler.post(new Runnable() {
                        public void run() {

                            String idFilm = Integer.toString(tvshow.getTVShowId());
                            String vote_average = Double.toString(tvshow.getTVShowRate());
                            String url_image = "https://image.tmdb.org/t/p/w185" + tvshow.getTVShowImage();
                            img = url_image;

                            tvFilmID.setText(idFilm);
                            tvFilmDate.setText(tvshow.getTVShowDate());
                            tvFilmDesc.setText(tvshow.getTVShowDesc());
                            tvFilmRate.setText(vote_average);
                            tvFilmTitle.setText(tvshow.getTVShowTitle());




                            Glide.with(DetailFilm.this)
                                    .load(url_image)
                                    .placeholder(R.color.colorAccent)
                                    .dontAnimate()
                                    .into(ivFilmImage);


                            progressBar.setVisibility(View.INVISIBLE);


                            FavMovieHelper Helper = FavMovieHelper.getInstance(getApplicationContext());
                            Helper.open();
                            Cursor cursor = getContentResolver().query(
                                    Uri.parse(CONTENT_URI + "/" + idFilm),
                                    null,
                                    null,
                                    null,
                                    null
                            );

                            if (cursor != null) {
                                if (cursor.moveToFirst()) isAdd = true;
                                cursor.close();
                            }else{
//                                Log.d("tes", idFilm);
                            }


                            if (isAdd){
                                favorite.setVisibility(View.INVISIBLE);
                                unfavorite.setVisibility(View.VISIBLE);
                            }else{
                                favorite.setVisibility(View.VISIBLE);
                                unfavorite.setVisibility(View.INVISIBLE);
                            }

                        }
                    });



            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(_ID, tvshow.getTVShowId());
                    contentValues.put(JUDUL, tvshow.getTVShowTitle());
                    contentValues.put(DESKRIPSI, tvshow.getTVShowDesc());
                    contentValues.put(RILIS, tvshow.getTVShowDate());
                    contentValues.put(GAMBAR, tvshow.getTVShowImage());
                    contentValues.put(RATE, tvshow.getTVShowRate());
                    getContentResolver().insert(CONTENT_URI,contentValues);
                    favorite.setVisibility(View.INVISIBLE);
                    unfavorite.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailFilm.this, "Added to Favorite", Toast.LENGTH_SHORT).show();

                }
            });

            unfavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getContentResolver().delete(
                            Uri.parse(CONTENT_URI + "/" + tvshow.getTVShowId()),
                            null,
                            null
                    );

                    unfavorite.setVisibility(View.INVISIBLE);
                    favorite.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailFilm.this, "Remove from Favorite", Toast.LENGTH_SHORT).show();
                }
            });



        }



    }
}
