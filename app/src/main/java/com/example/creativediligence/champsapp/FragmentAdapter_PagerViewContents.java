package com.example.creativediligence.champsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class FragmentAdapter_PagerViewContents extends RecyclerView.Adapter<FragmentAdapter_PagerViewContents.MyViewHolder> {
    Context mContext;
    String[] mData;
    String mTabtitle;

    private ArrayList<String> mAthleteNames;

    public FragmentAdapter_PagerViewContents(String[] data, Context context, ArrayList<String> arrayList, String tabtitle) {

        mContext = context;
        mAthleteNames = arrayList;
        mData = data;//events
        mTabtitle = tabtitle;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FragmentAdapter_PagerViewContents.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.general_card_layout_with_image, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.mTextView.setText(mData[position]);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String currentValue = mData[position];
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, Activity_SubEvents.class);
                intent.putExtra("eventname", currentValue);
                intent.putExtra("generalevents", mTabtitle);
                mContext.startActivity(intent);

                //new DialogCreator().DialogCreatorEventAthletes2(mContext, mAthleteNames, currentValue);

            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("Item Count", "" + mData.length);
        return mData.length;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView auTextView;
        public ImageView iconView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view_aux);
            mTextView = v.findViewById(R.id.athletes_name_textview);
            auTextView = v.findViewById(R.id.athlete_personal_best);
            iconView = v.findViewById(R.id.sub_event_image);
            iconView.setAdjustViewBounds(true);
            iconView.setMaxHeight(100);
            iconView.setMaxWidth(100);

        }


    }
}
