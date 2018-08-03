package com.example.android.booksearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = "SearchResultsActivity";
    private static final int LOADER_ID = 1;
    ArrayAdapter<Book> listAdapter;
    ArrayList<Book> books = new ArrayList<>();
    ListView listView;
    View emptyView;
    ProgressBar spinningProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Inside the Oncreate Method");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        spinningProgressBar = findViewById(R.id.loading_spinner);

        getSupportActionBar().setTitle("Search Results");

        if (getIntent().getStringExtra("URL") != null) {

            getSupportLoaderManager().initLoader(LOADER_ID, null, this);

        }
        emptyView = findViewById(R.id.empty_view_id);
        listView = findViewById(R.id.list_view_id);
        listAdapter = new booksAdapter(this, books);
        listView.setAdapter(listAdapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(books.get(position).getmBookURL()));
                startActivity(intent);
            }
        });


    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d(LOG_TAG, "Inside the OnCreateLoader");
        return new bookDataLoader(this, getIntent().getStringExtra("URL"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {

        Log.d(LOG_TAG, "Inside the OnLoadFinished");
        spinningProgressBar.setVisibility(View.GONE);
        listAdapter.clear();
        if (data != null) {
            listAdapter.addAll(data);
        } else {
            listView.setEmptyView(emptyView);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        Log.d(LOG_TAG, "Inside the OnLoaderReset");
        // listAdapter.clear();

    }


}
