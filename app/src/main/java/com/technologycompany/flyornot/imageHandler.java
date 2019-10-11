package com.technologycompany.flyornot;

import android.graphics.drawable.Drawable;

public class imageHandler {

    private String name;
    private String fly;
    private Drawable url;

    public imageHandler(){

    }

    public imageHandler(String name, String fly, Drawable url) {

        this.name = name;
        this.fly = fly;
        this.url= url;
    }

    public String getName() {
        return name;
    }

    public String getFly() {
        return fly;
    }

    public Drawable getUrl() {
        return url;
    }

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
