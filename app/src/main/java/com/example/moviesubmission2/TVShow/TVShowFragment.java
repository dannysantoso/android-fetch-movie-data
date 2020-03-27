package com.example.moviesubmission2.TVShow;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class TVShowFragment extends Fragment {


    private ProgressBar progressBar;
    private TVShowViewModel tvShowViewModel;
    private TVShowAdapter adapter;


    public TVShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new TVShowAdapter();
        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.tvshowList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);


        progressBar = view.findViewById(R.id.progressBar);

        tvShowViewModel = ViewModelProviders.of(this).get(TVShowViewModel.class);
        tvShowViewModel.getTVShows().observe(this, getTvShow);
        tvShowViewModel.setTVShows("EXTRA_TVSHOW");

        showLoading(true);

        return view;
    }

    private Observer<ArrayList<TVShow>>getTvShow = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvShows) {
            if (tvShows != null) {
                adapter.setTVShow(tvShows);
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
