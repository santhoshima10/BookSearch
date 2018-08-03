package com.example.android.booksearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.booksearch.data.getBooksAPIData;

import java.util.List;


public class bookDataLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = "bookDataLoader";
    String inputURL;
    List<Book> result;

    public bookDataLoader(@NonNull Context context, String url) {
        super(context);
        inputURL = url;
    }

    @Override
    protected void onStartLoading() {
        if (result != null) {
            // Use cached data
            deliverResult(result);
        } else {
            // We have no data, so kick off loading it
            forceLoad();
        }
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {

        Log.d(LOG_TAG, "Inside the loadInBackground");
        getBooksAPIData booksAPIData = new getBooksAPIData();
        result = booksAPIData.fetchBooksData(inputURL);
        return result;
    }

    @Override
    public void deliverResult(List<Book> data) {
        // We’ll save the data for later retrieval
        result = data;
        // We can do any pre-processing we want here
        // Just remember this is on the UI thread so nothing lengthy!
        super.deliverResult(data);
    }
}