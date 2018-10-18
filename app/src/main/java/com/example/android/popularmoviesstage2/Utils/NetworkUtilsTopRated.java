package com.example.android.popularmoviesstage2.Utils;

import android.net.Uri;
import android.util.Log;

import com.example.android.popularmoviesstage2.Data.Contract;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static com.example.android.popularmoviesstage2.Data.Contract.BASE_URL;
import static com.example.android.popularmoviesstage2.Data.Contract.TOP_RATED_PART;

public class NetworkUtilsTopRated {
    private static final String TAG = NetworkUtilsTopRated.class.getSimpleName();

    private static final String TOP_MOVIES_URL =
            BASE_URL + TOP_RATED_PART + Contract.API_KEY;

    //build URL method
    public static URL buildUrlTop() {
        Uri builtUri;
        builtUri = Uri.parse(TOP_MOVIES_URL).buildUpon()
                .build();
        //  Return the URL used to query API
        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + builtUri);
        //return statement
        return url;
    }

    public static String getResponseFromHttpUrlTop(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getResponseFromHttpUrlT() {
        return null;
    }
}
