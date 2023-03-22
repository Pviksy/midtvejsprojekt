package com.opgavedb_1.entities.objects;

public class Team extends Object{

    public Team(String title) {
        super(title);
    }

    public Team(int id, String title) {
        super(id, title);
    }


    @Override
    public String toString() {
        return getTitle() + " - " + getId();
    }
}


