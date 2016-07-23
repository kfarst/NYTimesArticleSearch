package com.example.kfarst.nytimesarticlesearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kfarst.nytimesarticlesearch.fragments.SearchFilterPagerFragment;
import com.example.kfarst.nytimesarticlesearch.models.Article;
import com.example.kfarst.nytimesarticlesearch.adapters.ArticleArrayAdapter;
import com.example.kfarst.nytimesarticlesearch.support.EndlessRecyclerViewScrollListener;
import com.example.kfarst.nytimesarticlesearch.api.NYTimesApiClient;
import com.example.kfarst.nytimesarticlesearch.R;
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

        gvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(stagaggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadMoreArticles(page);
            }
        });

        adapter = new ArticleArrayAdapter(articles);

        gvResults.setAdapter(adapter);
    }

    public void onArticleSearch(final View view) {
        if (articles.size() > 0) {
            articles.clear();
            adapter.notifyDataSetChanged();
        }

        Toast.makeText(view.getContext(), R.string.searching, Toast.LENGTH_SHORT).show();

        loadMoreArticles(0);

        Toast.makeText(SearchActivity.this, R.string.done, Toast.LENGTH_SHORT).show();
    }

    public void renderSearchFilterFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        SearchFilterPagerFragment alertDialog = SearchFilterPagerFragment.newInstance(0);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        alertDialog.show(fm, "fragment_pager_view");

    }

    private void loadMoreArticles (int page) {
        String query = etQuery.getText().toString();

        RequestParams params = new RequestParams();
        params.put("page", page);
        params.put("q", query);

        NYTimesApiClient.getArticles(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJson(articleJsonResults));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, R.string.search_failed, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(SearchActivity.this, R.string.search_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
