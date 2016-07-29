package com.example.kfarst.nytimesarticlesearch.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.adapters.SearchFilterPagerAdapter;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

import org.parceler.Parcels;

/**
 * Created by kfarst on 7/22/16.
 */
public class SearchFilterFragment extends DialogFragment {
    private static String PARAMS_ARG = "params";

    public SearchFilterFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static SearchFilterFragment newInstance(SearchFilterParams params) {
        SearchFilterFragment frag = new SearchFilterFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAMS_ARG, Parcels.wrap(params));
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_filter, container);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SearchFilterPagerAdapter(getChildFragmentManager(), getContext(),
                (SearchFilterParams) Parcels.unwrap(getArguments().getParcelable(PARAMS_ARG))));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
