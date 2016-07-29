package com.example.kfarst.nytimesarticlesearch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.adapters.CategoryArrayAdapter;
import com.example.kfarst.nytimesarticlesearch.models.Category;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;
import com.example.kfarst.nytimesarticlesearch.support.CategoriesCompletionView;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static String PARAMS_ARG = "params";

    @BindView(R.id.spOrder) Spinner spOrder;
    @BindView(R.id.gvCategories) RecyclerView gvCategories;

    public OptionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(SearchFilterParams params) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAMS_ARG, Parcels.wrap(params));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);

        ButterKnife.bind(this, view);

        SearchFilterParams params = (SearchFilterParams) Parcels.unwrap(getArguments().getParcelable(PARAMS_ARG));

        gvCategories.hasFixedSize();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);

        gvCategories.setLayoutManager(gridLayoutManager);

        CategoryArrayAdapter adapter = new CategoryArrayAdapter(params.addCategoriesFromArray(getResources().getStringArray(R.array.categories_array)));

        gvCategories.setAdapter(adapter);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private ArrayList<Category> getCategoryObjects(String[] categories) {
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        for (int i = 0; i < categories.length; i++) {
            categoryArrayList.add(new Category(categories[i]));
        }

        return categoryArrayList;
    }
}
