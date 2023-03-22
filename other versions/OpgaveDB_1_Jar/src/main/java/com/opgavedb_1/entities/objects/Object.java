package com.opgavedb_1.entities.objects;

//Mikkel

abstract class Object {

    private int id;
    private String title;

    public Object(String title) {
        this.title = title;
    }

    public Object(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
