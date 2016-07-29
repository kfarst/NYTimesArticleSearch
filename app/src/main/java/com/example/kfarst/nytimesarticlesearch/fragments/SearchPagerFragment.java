package com.example.kfarst.nytimesarticlesearch.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

import org.parceler.Parcels;

public class SearchPagerFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private static final String ARG_PARAMS = "params";

    // TODO: Rename and change types of parameters
    private int position;
    private SearchFilterParams params;

    public SearchPagerFragment() {
        // Required empty public constructor
    }

    public static SearchPagerFragment newInstance(int position, SearchFilterParams params) {
        SearchPagerFragment fragment = new SearchPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        args.putParcelable(ARG_PARAMS, Parcels.wrap(params));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            params = Parcels.unwrap(getArguments().getParcelable(ARG_PARAMS));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_pager, container, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        insertNestedFragment(position);
    }

    private Fragment getFragmentForPosition(int position) {
        switch (position) {
            case 0:
                return DateFragment.newInstance(params);
            case 1:
                return OptionsFragment.newInstance(params);
            default:
                return null;
        }
    }

    // Embeds the child fragment dynamically
    private void insertNestedFragment(int position) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_pager_view_container, getFragmentForPosition(position)).commit();
    }
}
