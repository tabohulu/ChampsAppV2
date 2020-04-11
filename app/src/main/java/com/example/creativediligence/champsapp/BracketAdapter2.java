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

import java.util.HashMap;
import java.util.List;

public class BracketAdapter2 extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private int mActivityNumber;

    public BracketAdapter2(Context context, List<String> expandableListTitle,
                           HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    public BracketAdapter2(Context context, List<String> expandableListTitle,
                           HashMap<String, List<String>> expandableListDetail, int activityNumber) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        mActivityNumber=activityNumber;
        //exp.setDividerHeight(30);
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.athlete_within_brackets_layout, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.teams_institution_children);
        LinearLayout mainLayout=convertView.findViewById(R.id.main_layout);
        LinearLayout submainLayout=convertView.findViewById(R.id.sub_main_layout);
        expandedListTextView.setText(expandedListText);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)submainLayout.getLayoutParams();
        if (!isLastChild){
            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
           //submainLayout.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                submainLayout.setBackground(context.getResources().getDrawable(R.drawable.plain));
            }else {
                submainLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.plain));
            }
        }else{
            params.setMargins(0, 0, 0, 32); //substitute parameters for left, top, right, bottom
            //submainLayout.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
               submainLayout.setBackground(context.getResources().getDrawable(R.drawable.bottom_corners));
            }else {
                submainLayout.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.bottom_corners));
            }
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.teams_institution_listgroup, null);
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.teams_institution_parent);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)listTitleTextView.getLayoutParams();

        if (isExpanded) {
            params.setMargins(0, 0, 0, 0); //substitute parameters for left, top, right, bottom
            listTitleTextView.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                listTitleTextView.setBackground(context.getResources().getDrawable(R.drawable.top_corners));
            }else {
                listTitleTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.top_corners));
            }
        }else {
            params.setMargins(0, 0, 0, 32); //substitute parameters for left, top, right, bottom
            listTitleTextView.setLayoutParams(params);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                listTitleTextView.setBackground(context.getResources().getDrawable(R.drawable.all_corners));
            }else {
                listTitleTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.all_corners));
            }
        }






        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
