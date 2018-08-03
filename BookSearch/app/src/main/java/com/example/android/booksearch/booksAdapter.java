package com.example.android.booksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.booksearch.data.getBooksAPIData;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class booksAdapter extends ArrayAdapter<Book> {

    List<Book> books;


    public booksAdapter(@NonNull Context context, @NonNull List<Book> objects) {
        super(context, 0, objects);
        books = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (convertView == null) {

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

        }

        ImageView imageThumbnail = listItemView.findViewById(R.id.book_image_thumbnail_id);
        TextView bookTitle = listItemView.findViewById(R.id.book_title_id);
        TextView bookAuthor = listItemView.findViewById(R.id.book_author_id);

        try {
            URL url = new URL(books.get(position).getmBookSmallThumbnailURL());
            new getImageFromURL(imageThumbnail).execute(url);
        } catch (IOException ex) {
            Log.d("booksAdapter", ex.toString());
        }

        bookTitle.setText(books.get(position).getmBookTitle());
        bookAuthor.setText(books.get(position).getmBookAuthor());


        return listItemView;
    }

    @Override
    public int getCount() {

        if (books == null) {
            return 0;
        } else {
            return books.size();
        }

    }

    private class getImageFromURL extends AsyncTask<URL, Void, Bitmap> {

        ImageView bmImage;

        public getImageFromURL(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            bmImage.setImageBitmap(null);
            bmImage.setImageBitmap(bitmap);
        }

        @Override
        protected Bitmap doInBackground(URL... urls) {
            getBooksAPIData getBooksAPIData = new getBooksAPIData();
            Bitmap bmp = getBooksAPIData.fetchImageData(urls[0]);
            return bmp;
        }
    }


}
