package com.example.android.popularmoviesstage2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesstage2.Data.Contract;
import com.example.android.popularmoviesstage2.Database.MovieDbHelper;

public class Favorites extends AppCompatActivity {
    private TextView favoriteMovieName;
    private TextView noMoviesTextView;
    private MovieDbHelper mDbHelper;
    private CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDbHelper = new MovieDbHelper(this);
        // Find all relevant views that we will need to read user input from
        favoriteMovieName = (TextView) findViewById(R.id.favorite_movie_tv);
        noMoviesTextView = (TextView)findViewById(R.id.no_movies_tv);
        mCheckBox = (CheckBox) findViewById(R.id.favorites_checkbox);
        CheckBox checkBox = new CheckBox(this);
        //add if statement to show favorite movies list
        //else show noMoviesText msg.
        if (checkBox.isSelected() ==true){
          displayDatabaseInfo();}

//        } else setNoMoviesTextView();


    }

    private void insertMovieInfo() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        // Create and/or open a database to read from it


// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.movieEntry.COLUMN_MOVIE_NAME
        };

//
//        Cursor cursor = db.query(
//                Contract.movieEntry.TABLE_MOVIE_NAME,   // The table to query
//                projection,             // The array of columns to return (pass null to get all)
//                null,              // The columns for the WHERE clause
//                null,          // The values for the WHERE clause
//                null,                   // don't group the rows
//                null,                   // don't filter by row groups
//                null
//        );

        Cursor cursor = getContentResolver().query(Contract.movieEntry.CONTENT_URI ,
                projection , null , null , null);
        // Perform this raw SQL query "SELECT * FROM movie"
        // to get a Cursor that contains all rows from the movie table.

        TextView displayView = (TextView) findViewById(R.id.favorite_movie_tv);

        try {
            // Create a header in the Text View that looks like this:
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.

            displayView.setText("The movie table contains " + cursor.getCount() + " movie.\n\n");
            displayView.append(Contract.movieEntry._ID + " - " +
                    Contract.movieEntry.COLUMN_MOVIE_NAME +
                    "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(Contract.movieEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Contract.movieEntry.COLUMN_MOVIE_NAME);


            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);

                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + "\n"));

            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid
            cursor.close();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    private void setNoMoviesTextView(){
//        noMoviesTextView.setVisibility(View.VISIBLE);
//        favoriteMovieName.setVisibility(View.INVISIBLE);
//    }
}
