package com.example.kfarst.nytimesarticlesearch.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kfarst.nytimesarticlesearch.fragments.DateFragment;
import com.example.kfarst.nytimesarticlesearch.fragments.SearchFilterPagerFragment;

/**
 * Created by kfarst on 7/22/16.
 */
public class SearchFilterPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Start Date", "Tab2", "Tab3" };
    private Context context;

    public SearchFilterPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return DateFragment.newInstance();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
