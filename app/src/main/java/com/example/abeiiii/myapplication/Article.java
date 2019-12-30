package com.example.abeiiii.myapplication;

public class Article {

    String title;
    String author;
    String url2image;
    String date;
    String url;

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url2image='" + url2image + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl2image() {
        return url2image;
    }

    public String getDate() {

        return date.substring(0,10);
    }
}

