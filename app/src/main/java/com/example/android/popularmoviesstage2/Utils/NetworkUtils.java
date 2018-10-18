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
import static com.example.android.popularmoviesstage2.Data.Contract.POPULAR_PART;

public final class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String DEFAULT_MOVIES_URL =
            BASE_URL + POPULAR_PART + Contract.API_KEY;

    //build URL method
    public static URL buildUrl() {
        Uri builtUri;
        builtUri = Uri.parse(DEFAULT_MOVIES_URL).buildUpon()
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

    public static String getResponseFromHttpUrl(URL url) throws IOException {
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

    public static String getResponseFromHttpUrl() {
        return null;
    }
}
