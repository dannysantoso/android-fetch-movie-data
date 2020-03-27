package com.example.moviesubmission2.TVShow;

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

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {
    ArrayList<TVShow> tvshows = new ArrayList<>();

    void setTVShow(ArrayList<TVShow> tvshows){
        if(tvshows == null) return;
        this.tvshows.clear();
        this.tvshows.addAll(tvshows);
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvshow, parent, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        TVShow tvShow = tvshows.get(position);
        holder.bind(tvShow);
    }

    @Override
    public int getItemCount() {
        return tvshows.size();
    }

    public class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, date, rate, description, id;
        public LinearLayout tvshowItem;
        public TVShowViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.tvshowImg);
            title = itemView.findViewById(R.id.tvshowTitle);
            date = itemView.findViewById(R.id.tvshowDate);
            rate = itemView.findViewById(R.id.tvshowRate);
            description = itemView.findViewById(R.id.tvshowDesc);
            tvshowItem = itemView.findViewById(R.id.tvshowItem);
            id = itemView.findViewById(R.id.tvshowId);
        }

        public void bind(final TVShow tvShow) {

            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getTVShowImage();

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(image);

            id.setText(Integer.toString(tvShow.getTVShowId()));
            title.setText(tvShow.getTVShowTitle());
            date.setText(tvShow.getTVShowDate());
            rate.setText(Double.toString(tvShow.getTVShowRate()));
            description.setText(tvShow.getTVShowDesc());
            tvshowItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailFilm.class);
                    intent.putExtra(DetailFilm.EXTRA_TVSHOW, tvShow);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}

