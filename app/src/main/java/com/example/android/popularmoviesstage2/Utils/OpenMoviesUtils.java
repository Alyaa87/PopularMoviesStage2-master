package com.example.android.popularmoviesstage2.Utils;

import android.util.Log;

import com.example.android.popularmoviesstage2.Data.MovieData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.popularmoviesstage2.Data.Contract.OVERVIEW;
import static com.example.android.popularmoviesstage2.Data.Contract.POSTER_PATH;
import static com.example.android.popularmoviesstage2.Data.Contract.RELEASE_DATE;
import static com.example.android.popularmoviesstage2.Data.Contract.RESULTS;
import static com.example.android.popularmoviesstage2.Data.Contract.VOTE_AVERAGE;

public class OpenMoviesUtils {
    public static ArrayList<MovieData> getMovies(String moviesJsonStr)

            throws JSONException {
        MovieData movies;
        ArrayList<MovieData> moviesArrayList = new ArrayList<>();


        /* String array to hold each elements String */
        JSONObject myJson = new JSONObject(moviesJsonStr);
        //get all data inside JSONObject forecastJson
        JSONArray moviesArrayResults = myJson.getJSONArray(RESULTS);
        //get all position of array-->  parsedMoviesData that come from JSONArray-->  moviesArrayResults
        for (int i = 0; i < moviesArrayResults.length(); i++) {
            /* These are the values that will be collected */

            movies = new MovieData();
            long voteAverage;
            String title;
            String posterPath;
            String overview;
            String releaseDate;

            /* Get the JSON object representing the results */
            JSONObject objResult = moviesArrayResults.getJSONObject(i);
            /*Get the JSON object representing the--> ...vote_average... from -->JSONObject(results)*/
            voteAverage = objResult.optLong(VOTE_AVERAGE);
            //*Get the JSON object representing the -->...title... from--> JSONObject(results)*/
            title = objResult.optString("title");
            Log.d("moviesTitle", title);

            /*Get the JSON object representing the -->...poster_path... from -->JSONObject(results)*/
            posterPath = objResult.optString(POSTER_PATH);
            /*Get the JSON object representing the -->....overview.... from--> JSONObject(results)*/
            overview = objResult.optString(OVERVIEW);
            /*Get the JSON object representing the -->....release_date.... from--> JSONObject(results)*/
            releaseDate = objResult.optString(RELEASE_DATE);
            movies.setVoteAverage(voteAverage);
            movies.setOriginal_title(title);
            movies.setPoster_image(posterPath);
            movies.setOverview(overview);
            movies.setReleaseDate(releaseDate);

            moviesArrayList.add(movies);
        }

        return moviesArrayList;
    }

}
