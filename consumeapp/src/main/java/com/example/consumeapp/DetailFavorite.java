package com.example.consumeapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailFavorite extends AppCompatActivity {

    public static String EXTRA_TITLE = "extra_title";
    public static String EXTRA_DESKRIPSI = "extra_overview";
    public static String EXTRA_DATE = "extra_release_date";
    public static String EXTRA_IMAGE = "extra_poster_jpg";
    public static String EXTRA_RATE = "extra_rate";

    TextView tvFilmTitle, tvFilmDate, tvFilmRate, tvFilmDesc, tvFilmID;
    ImageView ivFilmImage;

    String img;

    private ProgressBar progressBar;

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


        progressBar.setVisibility(View.VISIBLE);

        final String title = getIntent().getStringExtra(EXTRA_TITLE);
        String desc = getIntent().getStringExtra(EXTRA_DESKRIPSI);
        String rate = getIntent().getStringExtra(EXTRA_RATE);
        String image = getIntent().getStringExtra(EXTRA_IMAGE);
        String date = getIntent().getStringExtra(EXTRA_DATE);

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




    }
}
