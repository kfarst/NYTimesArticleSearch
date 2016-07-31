package com.example.kfarst.nytimesarticlesearch.models;

import android.text.TextUtils;
import android.util.Log;

import com.github.underscore.$;
import com.github.underscore.Block;
import com.github.underscore.Function1;
import com.github.underscore.Predicate;
import com.loopj.android.http.RequestParams;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by kfarst on 7/27/16.
 */
@Parcel
public class SearchFilterParams {
    String beginDate;

    public int getPage() {
        return page;
    }

    public String getQuery() {
        return query;
    }

    int page;
    String query;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort.toLowerCase();
    }

    String sort = "Relevance";
    ArrayList<Category> categories = new ArrayList<>();
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    static String ARG_QUERY = "q";
    static String ARG_PAGE = "page";
    static String ARG_FILTERS = "fq";
    static String ARG_NEWS_DESK = "news_desk";

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

    public String getBeginDateAsString() {
        return beginDate != null ? beginDate: null;
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

    public void setBeginDate(Calendar date) {
        this.beginDate = DATE_FORMAT.format(date.getTimeInMillis());
    }

    public String getNewsDeskParams() {
        List<Category> selectedCategories = getSelectedCategories();

        if (selectedCategories.size() > 0) {
            String categories = TextUtils.join(",", selectedCategoryNames(selectedCategories));
            return ARG_NEWS_DESK + ":(" + categories + ")";
        }

        return "";
    }

    private List<Category> getSelectedCategories() {
        return $.filter(categories, new Predicate<Category>() {
            public Boolean apply(Category category) {
                return category.isSelected();
            }
        });
    }

    private List<Object> selectedCategoryNames(List<Category> selectedCategories) {
        return $.pluck(selectedCategories, "name");
    }
}
