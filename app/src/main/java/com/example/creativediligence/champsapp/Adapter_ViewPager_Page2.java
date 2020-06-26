package com.example.creativediligence.champsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;


class Adapter_ViewPager_Page2 extends FragmentPagerAdapter {
    public Fragment[] fragments;
    Context mContext;
    int mTotalTabs;
    String[] eventTimesArray;
    String[] mEventDatesArray;


    public Adapter_ViewPager_Page2(Context context, FragmentManager fm, int totalTabs, String eventTimeStrings, String editTextDates) {
        super(fm);
        mContext = context;
        mTotalTabs = totalTabs;
        if (eventTimeStrings != null) {
            eventTimesArray = eventTimeStrings.split(":");
            //mEventDatesArray =editTextDates.split(":");
        }
        Log.d("times", editTextDates);
        if (editTextDates != null) {
            mEventDatesArray = editTextDates.split(":");
        }

        fragments = new Fragment[mTotalTabs];


    }

    public Adapter_ViewPager_Page2(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        mContext = context;
        mTotalTabs = totalTabs;
        //eventTimesArray = eventTimeStrings.split(":");
        fragments = new Fragment[mTotalTabs];


    }

    @Override
    public Fragment getItem(int position) {

        Fragment_EventStats bi = new Fragment_EventStats();
        Bundle args = new Bundle();
        Log.d("GetItem", eventTimesArray.length + "");
        if (eventTimesArray != null) {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(eventTimesArray[position].split(",")));

            args.putStringArrayList("times", temp);
            bi.setArguments(args);
        }
        Log.d("times", mEventDatesArray[position]);
        if (mEventDatesArray != null) {
            ArrayList<String> temp = new ArrayList<>(Arrays.asList(mEventDatesArray[position].split(",")));

            args.putStringArrayList("dates", temp);
            bi.setArguments(args);
        }
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
