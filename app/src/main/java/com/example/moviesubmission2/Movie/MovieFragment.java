package com.example.moviesubmission2.Movie;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.moviesubmission2.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private ProgressBar progressBar;
    private MovieViewModel moviesViewModel;
    private MovieAdapter adapter;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new MovieAdapter();
        View view = inflater.inflate(R.layout.fragment_movie,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);

        moviesViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        moviesViewModel.getMovies().observe(this, getMovie);
        moviesViewModel.setMovies("EXTRA_MOVIE");

        showLoading(true);

        return view;

    }

    private Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                adapter.setMovie(movie);
            }

            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }



}
