package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adapter_GraphTimesAndDates extends RecyclerView.Adapter<Adapter_GraphTimesAndDates.MyViewHolder> {
    private ArrayList<String> mDataModel;
    private Context mContext;


    public Adapter_GraphTimesAndDates(ArrayList<String> dataModel, Context context) {
        mDataModel = dataModel;
        mContext = context;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_GraphTimesAndDates.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_events_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


    }

    @Override
    public int getItemCount() {
        return mDataModel.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View v) {
            super(v);


        }
    }
}
