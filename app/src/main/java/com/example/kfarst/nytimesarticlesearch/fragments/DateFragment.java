package com.example.kfarst.nytimesarticlesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.CompoundButton;

import com.example.kfarst.nytimesarticlesearch.R;
import com.example.kfarst.nytimesarticlesearch.models.SearchFilterParams;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DateFragment extends Fragment {
    private static SearchFilterParams mParams;

    @BindView(R.id.scDateToggle) SwitchCompat scDateToggle;

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

        final CalendarView startDatePicker = (CalendarView) view.findViewById(R.id.startDatePicker);

        final long now = new Date().getTime();

        // Do not allow a future date to be picked
        startDatePicker.setMaxDate(now);

        try {
            if (mParams.getBeginDate() != null) {
                startDatePicker.setDate(mParams.getBeginDate());
            } else {
                scDateToggle.toggle();
                startDatePicker.setAlpha(0.5f);
                startDatePicker.setDate(now);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        startDatePicker.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();

                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                mParams.setBeginDate(cal);

                if (scDateToggle.isChecked()) {
                    scDateToggle.toggle();
                }
            }
        });

        scDateToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
                if (isSelected) {
                    startDatePicker.setAlpha(0.5f);
                    mParams.setBeginDate(null);
                    startDatePicker.setDate(now);
                } else {
                    startDatePicker.setAlpha(1.0f);
                }
            }
        });

        return view;
    }
}
