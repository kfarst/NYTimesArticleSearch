package com.example.kfarst.nytimesarticlesearch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kfarst.nytimesarticlesearch.fragments.SearchFilterFragment;
import com.example.kfarst.nytimesarticlesearch.fragments.SearchPagerFragment;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

/**
 * Created by kfarst on 7/22/16.
 */
public class SearchFilterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Begin Date", "Sort & Categories" };
    private Context context;
    private SearchFilterParams params;

    public SearchFilterPagerAdapter(FragmentManager fm, Context context, SearchFilterParams params) {
        super(fm);
        this.context = context;
        this.params = params;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return SearchPagerFragment.newInstance(position, this.params);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
