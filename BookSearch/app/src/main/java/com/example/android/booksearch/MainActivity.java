package com.example.android.booksearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*API URL INFO*/
    private final String GOOGLE_BOOKS_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private final String QUERY_PARAM = "q";
    private final String QUERY_MAX_RESULTS = "maxResults";
    private final String LOG_TAG = "MainActivity";
    private TextView mSearchString;
    private String mSearchKey = "";
    private String finalURL = "";
    private ArrayList<Book> bookData = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Inside the Oncreate Method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View emptyView = findViewById(R.id.no_internet_view_id);
        Button searchButton = findViewById(R.id.search_button_id);
        mSearchString = findViewById(R.id.search_string_id);
        ImageView findImage = findViewById(R.id.find_image_id);

        if (checkNetworkConnectivity()) {
            emptyView.setVisibility(View.INVISIBLE);

        } else {

            searchButton.setVisibility(View.INVISIBLE);
            mSearchString.setVisibility(View.INVISIBLE);
            findImage.setVisibility(View.INVISIBLE);

        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchKey = mSearchString.getText().toString();
                if (!mSearchKey.isEmpty()) {
                    finalURL = buildURL(GOOGLE_BOOKS_API_BASE_URL, QUERY_PARAM, QUERY_MAX_RESULTS, mSearchKey);
                    Log.d(LOG_TAG, "The URL generated :" + finalURL);
                    Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                    intent.putExtra("URL", finalURL);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter a Search String", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private String buildURL(String baseURL, String queryParams, String maxResults, String searchString) {

        String finalURL = "";

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String no_results = sharedPreferences.getString(getString(R.string.no_of_rows_key), getString(R.string.default_limit));

        Uri builtUri = Uri.parse(baseURL)
                .buildUpon()
                .appendQueryParameter(queryParams, searchString)
                .appendQueryParameter(maxResults, no_results)
                .build();

        finalURL = builtUri.toString();


        return finalURL;

    }


    private boolean checkNetworkConnectivity() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
