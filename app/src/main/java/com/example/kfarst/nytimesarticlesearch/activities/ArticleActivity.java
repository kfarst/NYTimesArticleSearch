package com.example.kfarst.nytimesarticlesearch.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.Article;
import com.wang.avi.AVLoadingIndicatorView;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArticleActivity extends AppCompatActivity {
    @BindView(R.id.wvArticle) WebView webView;
    @BindView(R.id.avloadingIndicatorView) AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ButterKnife.bind(this);

        Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));

        avLoadingIndicatorView.setVisibility(View.VISIBLE);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                avLoadingIndicatorView.setVisibility(View.GONE);
            }
        });

        webView.loadUrl(article.getWebUrl());
    }

    public void closeArticle(View view) {
        finish();
    }
}
