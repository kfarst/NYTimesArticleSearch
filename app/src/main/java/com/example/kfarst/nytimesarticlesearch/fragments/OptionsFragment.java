package com.example.kfarst.nytimesarticlesearch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.Category;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;
import com.example.kfarst.nytimesarticlesearch.support.CategoriesCompletionView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    //@BindView(R.id.searchView) CategoriesCompletionView searchView;

    public OptionsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(SearchFilterParams params) {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
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

        //ButterKnife.bind(this, view);

        //CategoriesCompletionView searchView = (CategoriesCompletionView) view.findViewById(R.id.searchView);

        //ArrayAdapter<Category> adapter = new ArrayAdapter<Category>(getContext(),
        //        android.R.layout.simple_dropdown_item_1line, getCategoryObjects(getResources().getStringArray(R.array.categories_array)));

        //searchView.setAdapter(adapter);
        //searchView.allowDuplicates(false);

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
