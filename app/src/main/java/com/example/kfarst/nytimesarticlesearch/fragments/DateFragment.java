package com.example.kfarst.nytimesarticlesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateFragment extends Fragment {
    private static SearchFilterParams mParams;

    public DateFragment() {
        // Required empty public constructor
    }

    public static DateFragment newInstance(SearchFilterParams params) {
        DateFragment fragment = new DateFragment();
        mParams = params;
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
        View view = inflater.inflate(R.layout.fragment_date, container, false);

        ButterKnife.bind(this, view);

        CalendarView startDatePicker = (CalendarView) view.findViewById(R.id.startDatePicker);

        startDatePicker.setDate(mParams.has("begin_date") ? mParams.getBeginDate() : new Date().getTime());

        startDatePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //GregorianCalendar date = new GregorianCalendar( year, month, dayOfMonth );
                //mListItem.setDueDate(date.getTimeInMillis());
                //startDatePicker.setDate(date.getTimeInMillis());
            }
        });

        return view;
    }
}
