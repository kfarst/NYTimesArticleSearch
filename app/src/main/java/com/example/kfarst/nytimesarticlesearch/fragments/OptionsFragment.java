package com.example.kfarst.nytimesarticlesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.adapters.CategoryArrayAdapter;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static String PARAMS_ARG = "params";
    private static SearchFilterParams params;

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

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sort_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spOrder.setAdapter(spinnerAdapter);

        spOrder.setOnItemSelectedListener(this);

        params = (SearchFilterParams) Parcels.unwrap(getArguments().getParcelable(PARAMS_ARG));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        gvCategories.setLayoutManager(gridLayoutManager);

        CategoryArrayAdapter categoriesAdapter = new CategoryArrayAdapter(params.addCategoriesFromArray(getResources().getStringArray(R.array.categories_array)));

        gvCategories.setAdapter(categoriesAdapter);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView.getId() == R.id.spOrder) {
            params.setSort(adapterView.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
