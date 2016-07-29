package com.example.kfarst.nytimesarticlesearch.models;

import com.loopj.android.http.RequestParams;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kfarst on 7/27/16.
 */
@Parcel
public class SearchFilterParams extends RequestParams {
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyymmdd");

    public SearchFilterParams() { }

    public SearchFilterParams(String sort_order, String newest) {
        super(sort_order, newest);
    }

    public long getBeginDate() {
        try {
            return DATE_FORMAT.parse(get("begin_date")).getTime();
        } catch (ParseException e) {
            return Calendar.MILLISECOND;
        }
    }

    public String get(String key) {
       return get(key);
    }
}
