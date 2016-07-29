package com.example.kfarst.nytimesarticlesearch.models;

import com.github.underscore.$;
import com.github.underscore.Block;
import com.loopj.android.http.RequestParams;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Arrays.asList;

/**
 * Created by kfarst on 7/27/16.
 */
@Parcel
public class SearchFilterParams {
    String beginDate;
    int page;
    String query;
    ArrayList<Category> categories = new ArrayList<>();
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyymmdd");


    public void setPage(int page) {
        this.page = page;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchFilterParams() { }

    public Long getBeginDate() throws ParseException {
        return beginDate != null ? DATE_FORMAT.parse(beginDate).getTime() : null;
    }

    public void addCategory(String name) {
       categories.add(new Category(name));
    }

    public ArrayList<Category> addCategoriesFromArray(String[] categories) {
        $.each(asList(categories), new Block<String>() {
            public void apply(String name) {
                addCategory(name);
            }
        });

        return this.categories;
    }
}
