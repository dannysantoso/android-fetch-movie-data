package com.example.moviesubmission2.Search;

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
import com.example.moviesubmission2.DetailSearch;
import com.example.moviesubmission2.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    ArrayList<MovieSearch> mData = new ArrayList<>();

    public void setData(ArrayList<MovieSearch> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new SearchViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        holder.bind(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, desc, rate;
        ImageView image;
        public LinearLayout movieItem;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle);
            date = itemView.findViewById(R.id.movieDate);
            desc = itemView.findViewById(R.id.movieDesc);
            rate = itemView.findViewById(R.id.movieRate);
            image = itemView.findViewById(R.id.movieImg);
            movieItem = itemView.findViewById(R.id.movieItem);
        }

        public void bind(final MovieSearch movieSearch) {
            String url_image = "https://image.tmdb.org/t/p/w185" + movieSearch.getmImage();

//            image.setImageResource(movie.getMovieImage());
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(image);

            title.setText(movieSearch.getmTitle());
            date.setText(movieSearch.getmDate());
            desc.setText(movieSearch.getmDesc());
            rate.setText(movieSearch.getmRate());
            movieItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailSearch.class);
                    intent.putExtra(DetailSearch.EXTRA_ID, movieSearch.getmID());
                    intent.putExtra(DetailSearch.EXTRA_TITLE, movieSearch.getmTitle());
                    intent.putExtra(DetailSearch.EXTRA_DATE, movieSearch.getmDate());
                    intent.putExtra(DetailSearch.EXTRA_DESKRIPSI, movieSearch.getmDesc());
                    intent.putExtra(DetailSearch.EXTRA_IMAGE, movieSearch.getmImage());
                    intent.putExtra(DetailSearch.EXTRA_RATE, movieSearch.getmRate());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
