package com.example.creativediligence.champsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


class Adapter_ViewPager_Page2 extends FragmentPagerAdapter {
    public Fragment[] fragments;
    Context mContext;
    int mTotalTabs;
     ;

    public Adapter_ViewPager_Page2(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        mContext = context;
        mTotalTabs = totalTabs;

        fragments = new Fragment[mTotalTabs];


    }

    @Override
    public Fragment getItem(int position) {

            Fragment_EventStats bi = new Fragment_EventStats();
            return bi;



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
