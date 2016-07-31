package com.example.kfarst.nytimesarticlesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.activities.ArticleActivity;
import com.example.kfarst.nytimesarticlesearch.models.Article;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        @BindView(R.id.ivImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;

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
            ButterKnife.bind(this, itemView);
        }

        public void bind (final Article article, final AdapterView.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent i = new Intent(context, ArticleActivity.class);
                    i.putExtra("article", Parcels.wrap(article));
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
        return new ViewHolder(listItemView);
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
