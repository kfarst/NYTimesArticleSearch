package com.example.kfarst.nytimesarticlesearch;

/**
 * Created by kfarst on 7/21/16.
 */
import com.loopj.android.http.*;

public class NYTimesApiClient {
    private static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";
    private static final String API_KEY = "0f924b4c4ba444ff827a50c0d96995d9";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void getArticles(RequestParams params, JsonHttpResponseHandler responseHandler) {
        params.put("api-key", API_KEY);
        client.get(getAbsoluteUrl("articlesearch.json"), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
