package com.example.moviesubmission2.Movie;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviesubmission2.DetailFilm;
import com.example.moviesubmission2.R;

import java.util.ArrayList;
import java.util.Vector;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    ArrayList<Movie> movies = new ArrayList<>();

    void setMovie(ArrayList<Movie> movies){
        if(movies == null) return;
        this.movies.clear();
        this.movies.addAll(movies);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, date, rate, description, id;
        public LinearLayout movieItem;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movieImg);
            title = itemView.findViewById(R.id.movieTitle);
            date = itemView.findViewById(R.id.movieDate);
            rate = itemView.findViewById(R.id.movieRate);
            description = itemView.findViewById(R.id.movieDesc);
            movieItem = itemView.findViewById(R.id.movieItem);
            id = itemView.findViewById(R.id.movieId);
        }

        public void bind(final Movie movie) {

            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getMovieImage();

//            image.setImageResource(movie.getMovieImage());
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(image);

            id.setText(Integer.toString(movie.getMovieId()));
            title.setText(movie.getMovieTitle());
            date.setText(movie.getMovieDate());
            rate.setText(Double.toString(movie.getMovieRate()));
            description.setText(movie.getMovieDesc());
            movieItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailFilm.class);
                    intent.putExtra(DetailFilm.EXTRA_MOVIE, movie);
                     itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
