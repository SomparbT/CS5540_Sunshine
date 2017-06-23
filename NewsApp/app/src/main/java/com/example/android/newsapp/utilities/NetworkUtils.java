package com.example.android.newsapp.utilities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Siriporn on 6/14/2017.
 */

public class NetworkUtils {
    final static String NEWSAPI_BASE_URL =
            "https://newsapi.org/v1/articles";

    final static String PARAM_SOURCE = "source";
    final static String source = "the-next-web";

    final static String PARAM_SORTBY = "sortBy";
    final static String sortBy = "latest";

    final static String PARAM_APIKEY = "apiKey";


    /**
     * Builds the URL used to query newsapi.
     *
     * @param newsapiSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the GitHub.
     */
    public static URL buildUrl(String newsapiSearchQuery) {
        Uri builtUri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE, source)
                .appendQueryParameter(PARAM_SORTBY, sortBy)
                .appendQueryParameter(PARAM_APIKEY, Configuration.apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
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

}
