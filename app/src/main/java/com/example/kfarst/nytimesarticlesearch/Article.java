package com.example.kfarst.nytimesarticlesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kfarst on 7/17/16.
 */
public class Article implements Serializable {
    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    String webUrl;
    String headline;
    String thumbnail;

    // Decodes article json into article model object
    public static Article fromJson(JSONObject jsonObject) {
        Article b = new Article();
        // Deserialize json into object fields
        try {
            b.webUrl = jsonObject.getString("web_url");
            b.headline = jsonObject.getJSONObject("headline").getString("main");

            JSONArray multimedia = jsonObject.getJSONArray("multimedia");

            if (multimedia.length() > 0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                b.thumbnail = "http://www.nytimes.com/" + multimediaJson.getString("url");
            } else {
                b.thumbnail = "";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    // Decodes array of article json results into article model objects
    public static ArrayList<Article> fromJson(JSONArray jsonArray) {
        JSONObject articleJson;
        ArrayList<Article> articles = new ArrayList<Article>(jsonArray.length());
        // Process each result in json array, decode and convert to article object
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                articleJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Article article = Article.fromJson(articleJson);
            if (article != null) {
                articles.add(article);
            }
        }

        return articles;
    }
}
