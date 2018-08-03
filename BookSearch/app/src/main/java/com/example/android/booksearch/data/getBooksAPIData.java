package com.example.android.booksearch.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.android.booksearch.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class getBooksAPIData {

    private static final String LOG_TAG = "getBooksAPIData";

    private URL createURL(String url) {
        URL inputURL = null;

        try {
            inputURL = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error in incoming URL" + url);

        }
        return inputURL;
    }

    private String makeHttpRequest(URL url) {

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String inputResponse = "";

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            inputResponse = readFromStream(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in making http connection" + url);

        }
        return inputResponse;

    }

    private String readFromStream(InputStream inputStream) {
        String responseInString = "";
        StringBuilder builder = new StringBuilder();
        String line = "";
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            line = bufferedReader.readLine();
            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();

            }
            responseInString = builder.toString();
        } catch (IOException e) {

            Log.e(LOG_TAG, "Error in stream reading" + e.toString());

        }
        return responseInString;


    }

    private ArrayList<Book> getBookData(String jsonResponse) {
        ArrayList<Book> books = new ArrayList<>();
        StringBuilder builder;
        String title;
        String author;
        String smallThumbNailURL = "";
        String bookURL;

        if (jsonResponse == null || jsonResponse.isEmpty()) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            long totalSearchResults = jsonObject.getLong("totalItems");
            if (totalSearchResults == 0) {
                return null;

            } else {
                JSONArray itemsArray = jsonObject.optJSONArray("items");
                if (itemsArray != null) {
                    for (int i = 0; i < itemsArray.length(); i++) {
                        builder = new StringBuilder();
                        JSONObject booksObject = itemsArray.optJSONObject(i);
                        if (booksObject != null) {
                            JSONObject volumeInfoObject = booksObject.optJSONObject("volumeInfo");
                            if (volumeInfoObject != null) {
                                title = volumeInfoObject.optString("title");
                                JSONArray authorsArray = volumeInfoObject.optJSONArray("authors");
                                if (authorsArray != null) {
                                    for (int j = 0; j < authorsArray.length(); j++) {
                                        builder.append(authorsArray.getString(j));
                                        if (authorsArray.length() > 1 && j != authorsArray.length() - 1) {
                                            builder.append(",");
                                        }
                                    }
                                }
                                author = builder.toString();
                                JSONObject thumbNailObj = volumeInfoObject.optJSONObject("imageLinks");
                                if (thumbNailObj != null) {
                                    smallThumbNailURL = thumbNailObj.optString("smallThumbnail");
                                }

                                bookURL = volumeInfoObject.optString("infoLink");


                                Log.d(LOG_TAG, "Title :" + title);
                                Log.d(LOG_TAG, "Author :" + author);
                                Log.d(LOG_TAG, "thumbnail :" + smallThumbNailURL);
                                Log.d(LOG_TAG, "BookURL :" + bookURL);

                                Book bookItemData = new Book(title, author, smallThumbNailURL, bookURL);
                                books.add(bookItemData);
                            }
                        }
                    }

                }

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Error in conversion of String response to JSON" + e.toString());
        }


        return books;
    }

    public ArrayList<Book> fetchBooksData(String URL) {

        Log.d(LOG_TAG, "Inside the fetchBooksData");

        URL inputURL = createURL(URL);
        String response = makeHttpRequest(inputURL);

        ArrayList<Book> bookArrayList = new ArrayList<>();
        bookArrayList = getBookData(response);
        return bookArrayList;

    }


    public Bitmap fetchImageData(URL url) {
        Log.d(LOG_TAG, "Inside the fetchImageData");

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        Bitmap bmp = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            bmp = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in making http connection for loading images" + url);

        }

        return bmp;


    }

}
