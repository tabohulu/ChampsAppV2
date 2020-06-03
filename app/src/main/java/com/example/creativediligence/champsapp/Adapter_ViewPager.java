package com.example.creativediligence.champsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;


class Adapter_ViewPager extends FragmentPagerAdapter {
    public Fragment[] fragments;
    Context mContext;
    int mTotalTabs;
    int mEditprofileState;
    boolean mProfileEditState;
    Bundle args;
    Fragment_BasicInfo bi = null;

    public Adapter_ViewPager(Context context, FragmentManager fm, int totalTabs, int editprofileState, boolean profileEditState) {
        super(fm);
        mContext = context;
        mTotalTabs = totalTabs;
        mEditprofileState = editprofileState;
        mProfileEditState = profileEditState;

        fragments = new Fragment[mTotalTabs];


    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            args = new Bundle();
            args.putBoolean("editStateBool", mProfileEditState);
            args.putInt("editStateInt", mEditprofileState);

            bi = new Fragment_BasicInfo();
            bi.setArguments(args);
            return bi;
        } else if (position == 1) {
            Fragment_Stats stats = new Fragment_Stats();
            return stats;
        } else {
            return null;
        }


    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        fragments[position] = createdFragment;
        return createdFragment;
    }

    @Override
    public int getCount() {
        return mTotalTabs;
    }
}
