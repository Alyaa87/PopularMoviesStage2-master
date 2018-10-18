package com.example.android.popularmoviesstage2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.android.popularmoviesstage2.Data.Contract;
import com.example.android.popularmoviesstage2.Database.MovieDbHelper;
import com.squareup.picasso.Picasso;

import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_OVERVIEW;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_RATE;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_TITLE;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_URL;
import static com.example.android.popularmoviesstage2.Data.Contract.EXTRA_YEAR;
import static com.example.android.popularmoviesstage2.Data.Contract.TITLE;
import static com.example.android.popularmoviesstage2.Data.Contract.VIDEO_URL;

public class DetailsActivity extends AppCompatActivity {
    //field to store the movie details
    private String mUrl;
    private String mTitle;
    public TextView mTitleTxt, mReleaseDate, mVoteAverage, mOverview;
    private MovieDbHelper mDbHelper;
    private CheckBox mCheckBox;
    private Button videoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDbHelper = new MovieDbHelper(this);
        mCheckBox = (CheckBox) findViewById(R.id.favorites_checkbox);
        //when the checkbox is selected , add the movie to Favorite database
        onCheckboxClicked(mCheckBox);
        //videoView
        final VideoView videoView = (VideoView) findViewById(R.id.movie_video);
        //Creating MediaController
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        //specify the location of media file
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/media/1.mp4");

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();


        //Reference
        mTitleTxt = findViewById(R.id.original_title_tv);
        mReleaseDate = findViewById(R.id.release_date);
        mVoteAverage = findViewById(R.id.vote_average);
        mOverview = findViewById(R.id.overview);
        ImageView mMoviePoster = findViewById(R.id.movie_poster);
        RatingBar ratingBar = findViewById(R.id.ratingBar);
        videoButton = findViewById(R.id.video_button);



        Intent intentStartDetailActivity = getIntent();
        //get the intent
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString(EXTRA_TITLE) != null) {
            mTitle = bundle.getString(EXTRA_TITLE);
            mTitleTxt.setText(mTitle);
        }
        if (bundle.getString(EXTRA_URL) != null) {
            mUrl = bundle.getString(EXTRA_URL);
        }
        if (bundle.getString(EXTRA_YEAR) != null) {
            mReleaseDate.setText(bundle.getString(EXTRA_YEAR));
        }
        if (bundle.getString(EXTRA_RATE) != null) {
            int number = Integer.parseInt(bundle.getString(EXTRA_RATE));
            float d = (float) ((number * 5) / 10);
            ratingBar.setRating(d);
        }
        if (bundle.getString(EXTRA_OVERVIEW) != null) {
            mOverview.setText(bundle.getString(EXTRA_OVERVIEW));
        }
        Picasso.with(this)
                .load(Contract.IMAGE_URL + Contract.W780 + mUrl)
                .into(mMoviePoster);
    }
//
//    private void displayDatabaseInfo() {
//        // To access our database, we instantiate our subclass of SQLiteOpenHelper
//        // and pass the context, which is the current activity.
//
//        // Create and/or open a database to read from it
//
//        SQLiteDatabase db = mDbHelper.getReadableDatabase();}

//        private void insertMovieInfo() {
//            CheckBox checkBox = new CheckBox(this);
//            checkBox.isSelected();
//            if (checkBox.isSelected() == true) {
//                String movieName = TITLE.toString();
//                SQLiteDatabase db = mDbHelper.getWritableDatabase();
//                ContentValues values = new ContentValues();
//                values.put(Contract.movieEntry.COLUMN_MOVIE_NAME, movieName);
//
//                long newRowId = db.insert(Contract.movieEntry.TABLE_MOVIE_NAME, null, values);
//                if (newRowId != -1) {
//                    Toast.makeText(this, "Movie has been added successfully ", Toast.LENGTH_LONG).show();
//                } else if (newRowId == -1) {
//                    Toast.makeText(this, "Movie failed to save ", Toast.LENGTH_LONG).show();
//
//                }
//            }
//        }
    public void watchTrailerBtnClicked (View view){
                videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //coding

                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=" +VIDEO_URL)));
                Log.i("Video", "Video Playing....");
                Toast.makeText(DetailsActivity.this , "Watch the movie Trailer " , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            String movieName = mTitle.toString();
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Contract.movieEntry.COLUMN_MOVIE_NAME, movieName);

            long newRowId = db.insert(Contract.movieEntry.TABLE_MOVIE_NAME, null, values);
            if (newRowId != -1) {
                Toast.makeText(this, "Movie has been added successfully ", Toast.LENGTH_LONG).show();
            } else if (newRowId == -1) {
                Toast.makeText(this, "Movie failed to save ", Toast.LENGTH_LONG).show();

            }
        } else removeMovie(view);

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
        if (id == android.R.id.home) {
            onBackPressed();
        }

        if (id == R.id.favorite_movie) {
            //open the favorite activity
            Intent favoritesIntent = new Intent(this, Favorites.class);
            startActivity(favoritesIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void removeMovie (View view){

    }

}
