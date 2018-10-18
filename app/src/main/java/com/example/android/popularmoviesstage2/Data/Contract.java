package com.example.android.popularmoviesstage2.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    private static final String KEY = "?api_key=";
    /* information. Each Movies info is an element of the "results" array */
    public static final String RESULTS = "results";
    /*all  children objects in "results" array */
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String POSTER_PATH = "poster_path";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String TITLE = "title";

    //TODO put your own Api key here
    public static final String API_KEY = KEY+ "0baa0dbb0893e2930c6885d76a3d4d66";

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String POPULAR_PART = "popular";
    public static final String TOP_RATED_PART = "top_rated";
    //keys of the intent bundle
    public static final String EXTRA_TITLE = "extra_title ";
    public static final String EXTRA_URL = "extra_url ";
    public static final String EXTRA_YEAR = "extra_year ";
    public static final String EXTRA_RATE = "extra_rate ";
    public static final String EXTRA_OVERVIEW = "extra_overview";
    //the url for the vedio trailer
    public static final String EXTRA_WEBVIEW_URL = "web_view_url ";
    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key="
            +API_KEY+ "&language=en-US";
    public static final String REVIEW= "https://api.themoviedb.org/3/review/{review_id}?api_key="+API_KEY;
    //the url of value of image view
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String W185 = "w185";
    public static final String W500 = "w500";
    public static final String W780 = "w780";

    //inner class for movie table
    public static abstract class movieEntry implements BaseColumns {
        public final static String TABLE_MOVIE_NAME = "MOVIE";
        //columns of the table
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_MOVIE_NAME = "name";

        //the values for the columns


        //content Uri
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);


    }
    //set the content Authority
    public static final String CONTENT_AUTHORITY = "com.example.android.popularmoviesstage2";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";


}
