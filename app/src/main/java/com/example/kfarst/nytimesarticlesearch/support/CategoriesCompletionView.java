package com.example.kfarst.nytimesarticlesearch.support;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.Category;
import com.tokenautocomplete.TokenCompleteTextView;

/**
 * Created by kfarst on 7/27/16.
 */
public class CategoriesCompletionView extends TokenCompleteTextView<Category> {
    public CategoriesCompletionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected View getViewForObject(Category category) {
        LayoutInflater l = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView view = (TextView) l.inflate(R.layout.token_category, (ViewGroup) getParent(), false);
        view.setText(category.getName());

        return view;
    }

    @Override
    protected Category defaultObject(String completionText) {
        return new Category(completionText);
    }
}
