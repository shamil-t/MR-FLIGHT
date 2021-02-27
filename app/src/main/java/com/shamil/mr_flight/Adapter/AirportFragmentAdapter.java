package com.shamil.mr_flight.Adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shamil.mr_flight.Fragments.ArrivalFragment;
import com.shamil.mr_flight.Fragments.DepartureFragment;

public class AirportFragmentAdapter extends FragmentPagerAdapter {

    Context ctx;
    int tabs;
    Bundle bundle;

    public AirportFragmentAdapter(FragmentManager fm, Context ctx, int tabs, Bundle bundle){
        super(fm);
        this.ctx = ctx;
        this.tabs = tabs;
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                ArrivalFragment arrivalFragment = new ArrivalFragment();
                arrivalFragment.setArguments(bundle);
                return arrivalFragment;
            case 1:
                DepartureFragment departureFragment = new DepartureFragment();
                departureFragment.setArguments(bundle);
                return departureFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
