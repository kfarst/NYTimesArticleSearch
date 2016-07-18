package com.example.kfarst.nytimesarticlesearch;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kfarst.nytimesarticlesearch.activities.ArticleActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by kfarst on 7/17/16.
 */
public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder> {
    private List<Article> mArticles;
    private AdapterView.OnItemClickListener listener;

    // Pass in the contact array into the constructor
    public ArticleArrayAdapter(List<Article> listItems) {
        mArticles = listItems;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView ivImage;
        public TextView tvTitle;
        private String webUrl;

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }

        public void bind (final Article article, final AdapterView.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent i = new Intent(context, ArticleActivity.class);
                    i.putExtra("url", article.webUrl);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public ArticleArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View listItemView = inflater.inflate(R.layout.item_article_result, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(listItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleArrayAdapter.ViewHolder holder, int position) {
        Article article = mArticles.get(position);

        holder.tvTitle.setText(article.getHeadline());

        if (!TextUtils.isEmpty(article.getThumbnail())) {
            Picasso
                    .with(holder.ivImage.getContext())
                    .load(article.getThumbnail())
                    .into(holder.ivImage);
        }

        holder.bind(mArticles.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
}
