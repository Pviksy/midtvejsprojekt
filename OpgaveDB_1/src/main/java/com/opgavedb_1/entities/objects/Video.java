package com.opgavedb_1.entities.objects;

//Mikkel

public class Video extends Object {

    private String URL;

    public Video(String title) {
        super(title);
    }

    public Video(int id, String title) {
        super(id, title);
    }

    public Video(int id, String URL, String title) {
        super(id, title);
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
