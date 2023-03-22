package com.opgavedb_1.entities.objects;

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


}
