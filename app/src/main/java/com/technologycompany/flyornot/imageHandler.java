package com.technologycompany.flyornot;

import android.graphics.drawable.Drawable;

public class imageHandler {

    private int id;
    private String name;
    private String fly;
    private Drawable url;

    public imageHandler(){

    }

    public imageHandler(int id, String name, String fly, Drawable url) {

        this.id = id;
        this.name = name;
        this.fly = fly;
        this.url= url;
    }

    public int getId() {return id;}

    public String getName() {
        return name;
    }

    public String getFly() {
        return fly;
    }

    public Drawable getUrl() {
        return url;
    }

    public void setId(int id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setFly(String fly) {
        this.fly = fly;
    }

    public void setUrl(Drawable url) {
        this.url = url;
    }
}
