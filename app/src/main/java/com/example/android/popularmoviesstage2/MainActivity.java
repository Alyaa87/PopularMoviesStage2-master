package com.example.android.popularmoviesstage2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.popularmoviesstage2.Data.MovieData;
import com.example.android.popularmoviesstage2.Utils.NetworkUtils;
import com.example.android.popularmoviesstage2.Utils.NetworkUtilsTopRated;
import com.example.android.popularmoviesstage2.Utils.OpenMoviesUtils;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<MovieData> moviesArrayList;
    private Button check;
    private static final String STATE_MOVIES = "state_movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_MOVIES)) {
            //after check get the value of that key in  moviesArrayList
            moviesArrayList = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
        }
        setContentView(R.layout.activity_main);
        moviesArrayList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);
        check = findViewById(R.id.check_btn);
        mMovieAdapter = new MovieAdapter(this, moviesArrayList);
        //linearLayoutManager
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mMovieAdapter);
        //check Button for internet state.
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMovieAdapter.updateMovies(null);
                if (isOnline()) {
                    loadMovieData();
                } else {
                    showErrorMessage();
                    Toast.makeText(MainActivity.this, "Error , Check internet connection", Toast.LENGTH_LONG).show();
                }
            }
        });
        //add item decoration
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        loadMovieData();
    }

    //check internet state.
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void loadMovieData() {
        showData();
        String data = NetworkUtils.getResponseFromHttpUrl();
        new AsynTaskMethod().execute(data);
    }

    private void loadTopMovieData() {
        showData();
        String data2 = NetworkUtilsTopRated.getResponseFromHttpUrlT();
        new AsynTaskMethod2().execute(data2);
    }

    private void showData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        check.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        check.setVisibility(View.VISIBLE);
        //error toast
        Toast.makeText(this, "Error , Check internet connection", Toast.LENGTH_LONG).show();
    }

    //AsyncTask to fetch movie Data.
    public class AsynTaskMethod extends AsyncTask<String, Void, ArrayList<MovieData>> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected ArrayList<MovieData> doInBackground(String... params) {
            URL moviesRequestUrl = NetworkUtils.buildUrl();
            try {
                return OpenMoviesUtils.getMovies(NetworkUtils
                        .getResponseFromHttpUrl(moviesRequestUrl));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> resultArrayList) {
            moviesArrayList = resultArrayList;
            mMovieAdapter.addMovieArrayList(moviesArrayList);
            mMovieAdapter.notifyDataSetChanged();
            pdLoading.dismiss();
        }
    }

    //AsyncTask2 to fetch movie Data.
    public class AsynTaskMethod2 extends AsyncTask<String, Void, ArrayList<MovieData>> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected ArrayList<MovieData> doInBackground(String... params) {
            URL moviesRequestUrl = NetworkUtilsTopRated.buildUrlTop();
            try {
                return OpenMoviesUtils.getMovies(NetworkUtilsTopRated
                        .getResponseFromHttpUrlTop(moviesRequestUrl));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> resultArrayList2) {
            moviesArrayList = resultArrayList2;
            mMovieAdapter.addMovieArrayList(moviesArrayList);
            mMovieAdapter.notifyDataSetChanged();
            pdLoading.dismiss();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_MOVIES, moviesArrayList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.popular) {
            //set the Url to popular
            loadMovieData();
            return true;
        }
        if (id == R.id.top_rated) {
            //set the Url to top rated
            loadTopMovieData();
            return true;
        } if (id == R.id.favorite_movie){
            //open the favorite activity
            Intent favoritesIntent = new Intent(this,Favorites.class);
            startActivity(favoritesIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
