package com.example.creativediligence.champsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter_ProfileEvents extends RecyclerView.Adapter<Adapter_ProfileEvents.MyViewHolder> {
    private ArrayList<String> mDataModel;
    private Context mContext;
    boolean mIsClickable;
    static public ArrayList<String[]> timeDate= new ArrayList<>();
    Fragment_Stats mStats;


    public Adapter_ProfileEvents(ArrayList<String> dataModel, Context context,boolean isClickable) {
        mDataModel = dataModel;
        mContext = context;
        mIsClickable=isClickable;


    }

    public Adapter_ProfileEvents(ArrayList<String> dataModel, Context context,boolean isClickable,Fragment_Stats stats) {
        mDataModel = dataModel;
        mContext = context;
        mIsClickable=isClickable;
        mStats=stats;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_ProfileEvents.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_events_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.mEventName.setText(mDataModel.get(position));

            holder.switchImageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=holder.getAdapterPosition();
                    if(mIsClickable) {
                        //Toast.makeText(mContext, "image touched", Toast.LENGTH_SHORT).show();
                        if (mContext instanceof Activity_AthleteProfile2) {
                            ((Activity_AthleteProfile2)mContext).SwitchPage(1,true,position);
                            //mStats.ChangeChildTab(position,true);
                        }
                    }

                }
            });

    }

    @Override
    public int getItemCount() {
        return mDataModel.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mEventName;
        ImageView switchImage;
        LinearLayout switchImageLayout;



        public MyViewHolder(View v) {
            super(v);
            mEventName = v.findViewById(R.id.event_name);

            switchImage = v.findViewById(R.id.switch_image);
            switchImageLayout = v.findViewById(R.id.switch_image_layout);

        }
    }
}
