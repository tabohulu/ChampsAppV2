package com.example.creativediligence.champsapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

public class Adapter_AthleteCoachInfo extends RecyclerView.Adapter<Adapter_AthleteCoachInfo.MyViewHolder> {
    public ArrayList<Helper_AthleteCoachModel> mData;
    public ArrayList<String> mURLData;

    public Context mContext;
    public int mId;
    public int mActivityNumber;

    public Adapter_AthleteCoachInfo(Context context, int resourceId, ArrayList<Helper_AthleteCoachModel> data) {
        mData = data;
        mContext = context;
        mId = resourceId;


    }

    public Adapter_AthleteCoachInfo(Context context, int resourceId, ArrayList<Helper_AthleteCoachModel> data, int activityNumber) {
        mData = data;
        mContext = context;
        mId = resourceId;
        mActivityNumber = activityNumber;

    }

    public Adapter_AthleteCoachInfo(Context context, int resourceId, ArrayList<Helper_AthleteCoachModel> data, String currentFragment, ArrayList<String> data2) {
        mData = data;
        mContext = context;
        mId = resourceId;
        mURLData = data2;


    }

    @Override
    public Adapter_AthleteCoachInfo.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AthleteCoachInfo.MyViewHolder holder, final int position) {
    //Transform pic into one with round corner
        Glide.with(mContext)
                .load(R.drawable.ic_thumb)
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.mPersonIcon);


        final String name = mData.get(position).getmName();
        String pB = String.valueOf(mData.get(position).getmPb());
        String age = String.valueOf(mData.get(position).getmAge());
        holder.mName.setText(mData.get(position).getmName());
        holder.mAgeContent.setText(age);
        holder.mPBContent.setText(pB);

        holder.moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Activity_AthleteCoachDetails.class);
                intent.putExtra("athleteName", name);
                intent.putExtra("activityNumber", mActivityNumber);
                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //CardBody
        public CardView mCardView;
        //Title TextViews
        public TextView mAgeHeading;
        public TextView mGamesHeading;
        public TextView mPBHeading;
        public TextView mGoalsHeading;
        //Content TextViews
        public TextView mAgeContent;
        public TextView mGamesContent;
        public TextView mPBContent;
        public TextView mGoalsContent;
        //More Info LinearLayout
        public LinearLayout moreInfo;
        //Name TextView
        public TextView mName;
        //ImageView
        public ImageView mPersonIcon;


        public TextView mTextView;
        public TextView auTextView;

        public ImageView settingsIconView;
        public ImageView deleteIconView;

        public MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view_aux);

            mAgeHeading = v.findViewById(R.id.person_age_heading);
            mGamesHeading = v.findViewById(R.id.person_games_heading);
            mPBHeading = v.findViewById(R.id.person_PB_heading);
            mGoalsHeading = v.findViewById(R.id.person_goals_heading);

            mAgeContent = v.findViewById(R.id.person_age_content);
            mGamesContent = v.findViewById(R.id.person_games_content);
            mPBContent = v.findViewById(R.id.person_PB_content);
            mGoalsContent = v.findViewById(R.id.person_goals_content);

            moreInfo = v.findViewById(R.id.more_info);

            mName = v.findViewById(R.id.person_name);

            mPersonIcon = v.findViewById(R.id.person_icon);


        }


    }
}
