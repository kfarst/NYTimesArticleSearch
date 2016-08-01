package com.example.kfarst.nytimesarticlesearch.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.adapters.ArticleArrayAdapter;
import com.example.kfarst.nytimesarticlesearch.api.NYTimesApiClient;
import com.example.kfarst.nytimesarticlesearch.fragments.SearchFilterFragment;
import com.example.kfarst.nytimesarticlesearch.models.Article;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;
import com.example.kfarst.nytimesarticlesearch.support.DeviceDimensionsHelper;
import com.example.kfarst.nytimesarticlesearch.support.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class SearchActivity extends AppCompatActivity implements SearchFilterFragment.SearchFilterDialogListener {
    SearchView etQuery;
    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    RecyclerView gvResults;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    AVLoadingIndicatorView avLoadingIndicatorView;
    SearchFilterParams filters = new SearchFilterParams();
    RequestParams searchParams = new RequestParams();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_search);

        setupViews();
    }

    private void setupViews() {
        etQuery = (SearchView) findViewById(R.id.etQuery);
        avLoadingIndicatorView = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        articles = new ArrayList<>();

        gvResults = (RecyclerView) findViewById(R.id.gvResults);
        gvResults.setHasFixedSize(true);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, 1);

        gvResults.setLayoutManager(staggeredGridLayoutManager);

        gvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadMoreArticles(page);
            }
        });

        adapter = new ArticleArrayAdapter(articles);

        gvResults.setAdapter(adapter);

        etQuery.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                etQuery.clearFocus();
                onArticleSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void onArticleSearch() {
        if (articles.size() > 0) {
            articles.clear();
            adapter.notifyDataSetChanged();
        }

        String query = etQuery.getQuery().toString();
        filters.setQuery(query);
        searchParams.put("q", filters.getQuery());

        String newsDesk = filters.getNewsDeskParams();

        try {
            if (filters.getBeginDate() != null) {
                searchParams.put("begin_date", filters.getBeginDateAsString());
            } else {
                searchParams.remove("begin_date");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (filters.getSort().equalsIgnoreCase("Relevance")) {
            searchParams.remove("sort");
        } else {
            searchParams.put("sort", filters.getSort().toLowerCase());
        }

        if (!TextUtils.isBlank(newsDesk)) {
            searchParams.put("fq", newsDesk);
        } else {
            searchParams.remove("fq");
        }

        loadMoreArticles(0);
    }

    public void renderSearchFilterFragment(View view) {
        FragmentManager fm = getSupportFragmentManager();
        SearchFilterFragment alertDialog = SearchFilterFragment.newInstance(filters);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        alertDialog.show(fm, "fragment_search_filter");

    }

    private void loadMoreArticles (int page) {

        filters.setPage(page);

        searchParams.put("page", filters.getPage());

        NYTimesApiClient.getArticles(searchParams, new JsonHttpResponseHandler() {
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

            @Override
            public void onStart() {
                startAnim();
            }

            @Override
            public void onFinish() {
                stopAnim();
            }
        });
    }

    void startAnim(){
        avLoadingIndicatorView.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        avLoadingIndicatorView.setVisibility(View.GONE);
    }

    @Override
    public void onFinishEditDialog(SearchFilterParams params) {
        filters = params;
    }
}
