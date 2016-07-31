package com.example.kfarst.nytimesarticlesearch.models;

import org.parceler.Parcel;

/**
 * Created by kfarst on 7/28/16.
 */

@Parcel
public class Category {
    String name;
    boolean selected;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Category() { }
}
