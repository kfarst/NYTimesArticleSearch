package com.example.kfarst.nytimesarticlesearch.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.adapters.SearchFilterPagerAdapter;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kfarst on 7/22/16.
 */
public class SearchFilterFragment extends DialogFragment implements View.OnClickListener {
    private static String PARAMS_ARG = "params";
    private static SearchFilterParams params;

    @BindView(R.id.ibClose) ImageButton ibClose;
    @BindView(R.id.ibDone) ImageButton ibDone;

    public interface SearchFilterDialogListener {
        void onFinishEditDialog(SearchFilterParams params);
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_filter, container);

        params = (SearchFilterParams) Parcels.unwrap(getArguments().getParcelable(PARAMS_ARG));

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SearchFilterPagerAdapter(getChildFragmentManager(), getContext(), params));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        ButterKnife.bind(this, view);

        ibClose.setOnClickListener(this);
        ibDone.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ibDone) {
            SearchFilterDialogListener listener = (SearchFilterDialogListener) getActivity();
            listener.onFinishEditDialog(params);
        }

        dismiss();
    }
}
