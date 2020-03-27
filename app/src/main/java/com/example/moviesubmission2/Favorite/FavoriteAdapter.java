package com.example.moviesubmission2.Favorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesubmission2.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MovieViewHolder> {
    private Cursor mListMovies;
    private Context mContext;

    public FavoriteAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        FavoriteMovie movie = getItem(position);
        holder.bind(movie);
    }



    @Override
    public int getItemCount() {
        if (mListMovies == null){
            return 0;
        }
        return mListMovies.getCount();
    }

    public void setListMovies(Cursor mList) {
        this.mListMovies = mList;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, date, rate, description;
        public LinearLayout movieItem;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movieImg);
            title = itemView.findViewById(R.id.movieTitle);
            date = itemView.findViewById(R.id.movieDate);
            rate = itemView.findViewById(R.id.movieRate);
            description = itemView.findViewById(R.id.movieDesc);
            movieItem = itemView.findViewById(R.id.movieItem);
        }

        public void bind(final FavoriteMovie movie) {
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getMovieImage();

//            image.setImageResource(movie.getMovieImage());
            Glide.with(mContext)
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(image);

            title.setText(movie.getMovieTitle());
            date.setText(movie.getMovieDate());
            rate.setText(movie.getMovieRate());
            description.setText(movie.getMovieDesc());
            movieItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent Intent = new Intent(mContext, com.example.moviesubmission2.DetailFavorite.class);
                    Intent.putExtra(com.example.moviesubmission2.DetailFavorite.EXTRA_ID, movie.getMovieId().toString());
                    Intent.putExtra(com.example.moviesubmission2.DetailFavorite.EXTRA_TITLE, movie.getMovieTitle());
                    Intent.putExtra(com.example.moviesubmission2.DetailFavorite.EXTRA_DESKRIPSI, movie.getMovieDesc());
                    Intent.putExtra(com.example.moviesubmission2.DetailFavorite.EXTRA_RATE, movie.getMovieRate());
                    Intent.putExtra(com.example.moviesubmission2.DetailFavorite.EXTRA_DATE, movie.getMovieDate());
                    Intent.putExtra(com.example.moviesubmission2.DetailFavorite.EXTRA_IMAGE, movie.getMovieImage());
                    mContext.startActivity(Intent);
                }
            });
        }
    }


    private FavoriteMovie getItem(int position){
        if (!mListMovies.moveToPosition(position)){
            throw new IllegalStateException("Position invalid");
        }
        return new FavoriteMovie(mListMovies);
    }
}
