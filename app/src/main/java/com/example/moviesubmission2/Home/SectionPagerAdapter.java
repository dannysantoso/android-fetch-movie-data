package com.example.moviesubmission2.Home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviesubmission2.Movie.MovieFragment;
import com.example.moviesubmission2.R;
import com.example.moviesubmission2.TVShow.TVShowFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private static final int[] TAB_TITLES = new int[]{R.string.movie, R.string.tvshow};
    private final Context context;


    public SectionPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MovieFragment();
            case 1:
                return new TVShowFragment();
            default:
                return new Fragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
