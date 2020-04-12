package com.example.creativediligence.champsapp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentAdapterExpandable_MyCalendar extends BaseExpandableListAdapter {

    Context mContext;
    ArrayList<String> mSportsTypeTitles;
    HashMap<String, ArrayList<Helper_CalendarEventsItem>> mEventsList;

    public FragmentAdapterExpandable_MyCalendar(Context context, ArrayList<String> sportsTypeTitles, HashMap<String, ArrayList<Helper_CalendarEventsItem>> eventsList){
        mContext=context;
        mSportsTypeTitles=sportsTypeTitles;
        mEventsList=eventsList;

    }

    @Override
    public int getGroupCount() {
        return mSportsTypeTitles.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mEventsList.get(mSportsTypeTitles.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return mSportsTypeTitles.get(i);
    }

    @Override
    public Object getChild(int listPosition, int expListPosition) {
        return mEventsList.get(mSportsTypeTitles.get(listPosition)).get(expListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expListPosition) {
        return expListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calendar_expandable_list_group, null);
        }

        TextView title= convertView.findViewById(R.id.sportEventType);
        title.setText((String) getGroup(listPosition));
        title.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)title.getLayoutParams();
        if(isExpanded){
            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
            title.setLayoutParams(params);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                title.setBackground(mContext.getResources().getDrawable(R.drawable.top_corners));
            }else {
                title.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.top_corners));
            }

        }else{
            params.setMargins(0, 0, 0, 32); //substitute parameters for left, top, right, bottom
            title.setLayoutParams(params);
        }

        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.calendar_expandable_list_item_new, null);
        }

        Helper_CalendarEventsItem calendarEventsItem= (Helper_CalendarEventsItem) getChild(listPosition,expListPosition);

        TextView name= convertView.findViewById(R.id.sportEventName);
        name.setText(calendarEventsItem.getEventName());
        name.setTypeface(null, Typeface.BOLD);

        TextView date= convertView.findViewById(R.id.sportEventDate);
        date.setText(calendarEventsItem.getEventDate());
        date.setTypeface(null, Typeface.BOLD);

        TextView time= convertView.findViewById(R.id.sportEventTime);
        time.setText(calendarEventsItem.getEventPeriod());
        time.setTypeface(null, Typeface.BOLD);

        TextView venue= convertView.findViewById(R.id.sportEventVenue);
        venue.setText(calendarEventsItem.getEventVenue());
        venue.setTypeface(null, Typeface.BOLD);

        LinearLayout linLay=convertView.findViewById(R.id.lnExpanded);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) linLay.getLayoutParams();

        if(!isLastChild){
            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
            linLay.setLayoutParams(params);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                linLay.setBackground(mContext.getResources().getDrawable(R.drawable.plain));
            }else {
                linLay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.plain));
            }

        }else{
            params.setMargins(0, 0, 0, 32); //substitute parameters for left, top, right, bottom
            linLay.setLayoutParams(params);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                linLay.setBackground(mContext.getResources().getDrawable(R.drawable.bottom_corners));
            }else {
                linLay.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bottom_corners));
            }

        }





        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
