package com.example.moviesubmission2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.moviesubmission2.Search.SearchActivity;

import static android.provider.BaseColumns._ID;
import static com.example.moviesubmission2.DatabaseContract.CONTENT_URI;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.DESKRIPSI;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.GAMBAR;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.JUDUL;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RATE;
import static com.example.moviesubmission2.DatabaseContract.MovieColumns.RILIS;

public class DetailFavorite extends AppCompatActivity {

    public static String EXTRA_ID = "extra_id";
    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_DESKRIPSI = "extra_overview";
    public static String EXTRA_DATE = "extra_release_date";
    public static String EXTRA_IMAGE = "extra_poster_jpg";
    public static String EXTRA_RATE = "extra_rate";

    TextView tvFilmTitle, tvFilmDate, tvFilmRate, tvFilmDesc, tvFilmID;
    ImageView ivFilmImage, favorite, unfavorite;

    String img;

    boolean isAdd;

    private ProgressBar progressBar;

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
        setContentView(R.layout.activity_detail_favorite);

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

        final String id = getIntent().getStringExtra(EXTRA_ID);
        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        final String desc = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        final String rate = getIntent().getStringExtra(EXTRA_RATE);
        final String image = getIntent().getStringExtra(EXTRA_IMAGE);
        final String date = getIntent().getStringExtra(EXTRA_DATE);

        tvFilmDate.setText(date);
        tvFilmDesc.setText(desc);
        tvFilmRate.setText(rate);
        tvFilmTitle.setText(title);
        Glide.with(DetailFavorite.this)
                .load("http://image.tmdb.org/t/p/w500/"+image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(ivFilmImage);


        progressBar.setVisibility(View.INVISIBLE);

        FavMovieHelper Helper = FavMovieHelper.getInstance(getApplicationContext());
        Helper.open();
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + id),
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

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues contentValues = new ContentValues();
                contentValues.put(_ID, id);
                contentValues.put(JUDUL, title);
                contentValues.put(DESKRIPSI, desc);
                contentValues.put(RILIS, date);
                contentValues.put(GAMBAR, image);
                contentValues.put(RATE, rate);
                getContentResolver().insert(CONTENT_URI,contentValues);
                favorite.setVisibility(View.INVISIBLE);
                unfavorite.setVisibility(View.VISIBLE);
                Toast.makeText(DetailFavorite.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
            }
        });

        unfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(
                        Uri.parse(CONTENT_URI + "/" + id),
                        null,
                        null
                );

                favorite.setVisibility(View.VISIBLE);
                unfavorite.setVisibility(View.INVISIBLE);
                Toast.makeText(DetailFavorite.this, "Remove from Favorite", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
