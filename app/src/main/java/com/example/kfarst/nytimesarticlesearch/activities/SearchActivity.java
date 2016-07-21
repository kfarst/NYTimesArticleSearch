package com.example.kfarst.nytimesarticlesearch.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kfarst.nytimesarticlesearch.Article;
import com.example.kfarst.nytimesarticlesearch.ArticleArrayAdapter;
import com.example.kfarst.nytimesarticlesearch.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    ImageButton btnSearch;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    RecyclerView gvResults;
    StaggeredGridLayoutManager stagaggeredGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_main);

        setupViews();
    }

    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        btnSearch = (ImageButton) findViewById(R.id.btnQuery);
        articles = new ArrayList<>();

        gvResults = (RecyclerView) findViewById(R.id.gvResults);
        gvResults.setHasFixedSize(true);

        stagaggeredGridLayoutManager = new StaggeredGridLayoutManager(4, 1);

        gvResults.setLayoutManager(stagaggeredGridLayoutManager);

        adapter = new ArticleArrayAdapter(articles);

        gvResults.setAdapter(adapter);
    }

    public void onArticleSearch(final View view) {
        String query = etQuery.getText().toString();

        if (articles.size() > 0) {
            articles.clear();
            adapter.notifyDataSetChanged();
        }

        Toast.makeText(view.getContext(), R.string.searching, Toast.LENGTH_SHORT).show();

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "0f924b4c4ba444ff827a50c0d96995d9");
        params.put("page", 0);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJson(articleJsonResults));
                    adapter.notifyDataSetChanged();

                    Toast.makeText(view.getContext(), R.string.done, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(view.getContext(), R.string.search_failed, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(view.getContext(), R.string.search_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
