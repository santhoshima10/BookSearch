package com.example.android.booksearch;


import java.io.Serializable;

public class Book implements Serializable {

    private String mBookTitle;
    private String mBookAuthor;
    private String mBookSmallThumbnailURL;
    private String mBookURL;

    public Book(String mBookTitle, String mBookAuthor, String mBookSmallThumbnailURL, String mBookURL) {

        this.mBookTitle = mBookTitle;
        this.mBookAuthor = mBookAuthor;
        this.mBookSmallThumbnailURL = mBookSmallThumbnailURL;
        this.mBookURL = mBookURL;
    }

    public String getmBookURL() {
        return mBookURL;
    }

    public void setmBookURL(String mBookURL) {
        this.mBookURL = mBookURL;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }

    public void setmBookTitle(String mBookTitle) {
        this.mBookTitle = mBookTitle;
    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public void setmBookAuthor(String mBookAuthor) {
        this.mBookAuthor = mBookAuthor;
    }

    public String getmBookSmallThumbnailURL() {
        return mBookSmallThumbnailURL;
    }

    public void setmBookSmallThumbnailURL(String mBookSmallThumbnailURL) {
        this.mBookSmallThumbnailURL = mBookSmallThumbnailURL;
    }
}
