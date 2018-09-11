package com.cc.newscompanion;

import android.net.Uri;

public class Article {
    private String headline;
    private String text;
    private Uri imageURI;
    private String date;
    private String author;
    private String webUrl;

    public Article(String headline, String text, Uri imageURI, String date, String author, String webUrl) {
        this.headline = headline;
        this.text = text;
        this.imageURI = imageURI;
        this.date = date;
        this.author = author;
        this.webUrl = webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getText() {
        return text;
    }

    public Uri getImageURI() {
        return imageURI;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getWebUrl() {
        return webUrl;
    }

    @Override
    public String toString() {
        return "HeadLine:"+this.headline+"\nImage URI:"+this.imageURI+"\nDate:"+date+"\nAuthor:"+this.author;
    }
}
