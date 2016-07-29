package com.example.kfarst.nytimesarticlesearch.models;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by kfarst on 7/28/16.
 */
public class Category implements Serializable {
    public String getName() {
        return name;
    }

    String name;

    public Category(String name) {

    }
}
