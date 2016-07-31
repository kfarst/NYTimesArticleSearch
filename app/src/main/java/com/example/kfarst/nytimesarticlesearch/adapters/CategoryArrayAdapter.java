package com.example.kfarst.nytimesarticlesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.Category;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kfarst on 7/28/16.
 */
public class CategoryArrayAdapter extends RecyclerView.Adapter<CategoryArrayAdapter.ViewHolder> {
    private List<Category> mCategories;
    private AdapterView.OnItemClickListener listener;

    // Pass in the contact array into the constructor
    public CategoryArrayAdapter(List<Category> listItems) {
        mCategories = listItems;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        @BindView(R.id.tvCategory)
        TextView tvCategory;
        @BindView(R.id.scCategory)
        SwitchCompat scCategory;

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

        public void bind (final Category category, final AdapterView.OnItemClickListener listener) {
        }
    }

    @Override
    public CategoryArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View listItemView = inflater.inflate(R.layout.item_category, parent, false);

        // Return a new holder instance
        return new ViewHolder(listItemView);
    }

    @Override
    public void onBindViewHolder(CategoryArrayAdapter.ViewHolder holder, int position) {
        final Category category = mCategories.get(position);

        holder.tvCategory.setText(category.getName());
        holder.scCategory.setChecked(category.isSelected());

        holder.scCategory.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                category.setSelected(b);
            }
        });

        holder.bind(mCategories.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }
}

