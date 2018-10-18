package com.example.android.popularmoviesstage2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesstage2.Data.Contract;
import com.example.android.popularmoviesstage2.Data.MovieData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_OVERVIEW;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_RATE;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_TITLE;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_URL;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_YEAR;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyHolder> {

    ArrayList<MovieData> movieDataArrayList;
    private LayoutInflater inflater;
    private Context mContext;

    public void addMovieArrayList(ArrayList<MovieData> resultMovieDataArrayList) {
        movieDataArrayList = resultMovieDataArrayList;
    }

    // create constructor to initialize context and data sent from MainActivity
    public MovieAdapter(Context context, ArrayList<MovieData> data) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        this.movieDataArrayList = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public MovieAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data to the holder.
    @Override
    public void onBindViewHolder(MovieAdapter.MyHolder holder, final int position) {
        final MovieData movies = movieDataArrayList.get(position);
        //view the movie title
        holder.original_title.setText(movies.getOriginal_title());

        //view Images.
        Picasso.with(mContext)
                .load(Contract.IMAGE_URL + Contract.W500 + movieDataArrayList
                        .get(position).getPoster_image())
                .into(holder.posterImage);


        //set onclick listener for the movie items.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                //put Extra (send intent).
                Bundle bundle = new Bundle();

                bundle.putString(EXTRA_TITLE, movies.getOriginal_title());
                bundle.putString(EXTRA_RATE, String.valueOf(movies.getVoteAverage()));
                bundle.putString(EXTRA_YEAR, movies.getReleaseDate());
                bundle.putString(EXTRA_URL, movies.getPoster_image());
                bundle.putString(EXTRA_OVERVIEW, movies.getOverview());

                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    // return total item count from List
    @Override
    public int getItemCount() {
        if (movieDataArrayList != null) {
            return movieDataArrayList.size();
        } else {
            return 0;
        }
    }

    public void
    updateMovies(ArrayList<MovieData> movie_list) {
        this.movieDataArrayList = movie_list;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView original_title;
        private ImageView posterImage;

        // create constructor to get widget reference
        private MyHolder(View itemView) {
            super(itemView);
            original_title = itemView.findViewById(R.id.movie_name);
            posterImage = itemView.findViewById(R.id.poster_image);
        }
    }

}
