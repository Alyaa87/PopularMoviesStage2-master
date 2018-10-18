package com.example.android.popularmoviesstage2.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.popularmoviesstage2.Data.Contract;

public class MovieProvider extends ContentProvider {

    private MovieDbHelper mHelperDb;
    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MovieDbHelper.class.getSimpleName();

    //Movies case code
    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;

    /**
     * UriMatcher object
     */

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_MOVIES + "/#", MOVIES_ID);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_MOVIES, MOVIES);

    }
    /**
     * Initialize the provider and the database helper object.
     */
    @Override
    public boolean onCreate() {
        mHelperDb = new MovieDbHelper(getContext());
        return true;
    }

    /**
     * Perform the query for the given URI. Use the given projection, selection, selection arguments, and sort order.
     */

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,

                        String sortOrder) {

        // Get readable database
        SQLiteDatabase database = mHelperDb.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:

                cursor = database.query(Contract.movieEntry.TABLE_MOVIE_NAME, projection
                        , selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIES_ID:

                selection = Contract.movieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                // This will perform a query on the pets table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(Contract.movieEntry.TABLE_MOVIE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        return cursor;
    }

    /**
     * Insert new data into the provider with the given ContentValues.
     */

    @Override

    public Uri insert(Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = mHelperDb.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI to be returned

        switch (match) {
            case MOVIES:
                //  Insert new values into the database
                // Inserting values into tasks table
                long id = db.insertWithOnConflict(Contract.movieEntry.TABLE_MOVIE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.movieEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            //Set the value for the returnedUri and write the default case for unknown URI's
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);

        // Return constructed uri (this points to the newly inserted row of data)
        return returnUri;

    }

    /**
     * Updates the data at the given selection and selection arguments, with the new ContentValues.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Delete the data at the given selection and selection arguments.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writable database
        SQLiteDatabase database = mHelperDb.getWritableDatabase();

        final int match = sUriMatcher.match(uri);

        // Keep track of the number of deleted tasks
        int tasksDeleted; // starts as 0

        //  Write the code to delete a single row of data
        // [Hint] Use selections to delete an item by its row ID
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case MOVIES_ID:
                // Get the task ID from the URI path
                String id = uri.getPathSegments().get(1);
                // Use selections/selectionArgs to filter for this ID
                tasksDeleted = database.delete(Contract.movieEntry.TABLE_MOVIE_NAME,
                        Contract.movieEntry._ID + "=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver of a change and return the number of items deleted
        if (tasksDeleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return tasksDeleted;
    }

    /**
     * Returns the MIME type of data for the content URI.
     */
    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not implemented");
    }


}



