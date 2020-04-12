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

public class Adapter_AllSportsCommon extends RecyclerView.Adapter<Adapter_AllSportsCommon.MyViewHolder> {
    private ArrayList<Helper_BasicDataModel> mDataModel;
    private Context mContext;
    private ArrayList<Helper_BasicDataModel> mLData;
    private ArrayList<Helper_BasicDataModel> mMData;
    private ArrayList<Helper_BasicDataModel> mRData;
    private String mSportsTitle;


    public Adapter_AllSportsCommon(ArrayList<Helper_BasicDataModel> dataModel, Context context) {
        mDataModel = dataModel;
        mContext = context;
        mLData = new ArrayList<>();
        mMData = new ArrayList<>();
        mRData = new ArrayList<>();

        for (int i = 2; i < mDataModel.size(); i += 3) {
            mLData.add(mDataModel.get(i - 2));
            mMData.add(mDataModel.get(i - 1));
            mRData.add(mDataModel.get(i));
        }


    }

    public Adapter_AllSportsCommon(ArrayList<Helper_BasicDataModel> dataModel, String sportsTitle,Context context) {
        mDataModel = dataModel;
        mContext = context;
        mLData = new ArrayList<>();
        mMData = new ArrayList<>();
        mRData = new ArrayList<>();
        mSportsTitle=sportsTitle;

        for (int i = 2; i < mDataModel.size(); i += 3) {
            mLData.add(mDataModel.get(i - 2));
            mMData.add(mDataModel.get(i - 1));
            mRData.add(mDataModel.get(i));
        }


    }

    // Create new views (invoked by the layout manager)
    @Override
    public Adapter_AllSportsCommon.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_buttons_layout_with_cards, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.mTextView1.setText(mLData.get(position).getTitle());
        holder.iconView1.setImageResource(mLData.get(position).getImage());
        holder.iconView1.setAdjustViewBounds(true);
        holder.iconView1.setMaxHeight(300);
        holder.iconView1.setMaxWidth(300);

        holder.mTextView2.setText(mMData.get(position).getTitle());
        holder.iconView2.setImageResource(mMData.get(position).getImage());
        holder.iconView2.setAdjustViewBounds(true);
        holder.iconView2.setMaxHeight(300);
        holder.iconView2.setMaxWidth(300);

        holder.mTextView3.setText(mRData.get(position).getTitle());
        holder.iconView3.setImageResource(mRData.get(position).getImage());
        holder.iconView3.setAdjustViewBounds(true);
        holder.iconView3.setMaxHeight(300);
        holder.iconView3.setMaxWidth(300);


        holder.mCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String currentValue = mLData.get(position).getTitle();
                Log.d("CardView", "CardView Clicked: " + currentValue);



                switch (position) {
                    case 0:

                        try {
                            Intent intent = new Intent(mContext, Activity_Common_SportsEventOverview.class);
                            intent.putExtra("sportName",mSportsTitle);
                            intent.putExtra("activityName",currentValue);
                            mContext.startActivity(intent);
                            return;
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    case 1:


                        try {
                            Intent athleteIntent = new Intent(mContext, Activity_TeamsInstitution.class);
                            athleteIntent.putExtra("isHomepage",false);
                            mContext.startActivity(athleteIntent);
                            return;
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    default:
                        //Toast.makeText(mContext, "Not Yet Developed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        holder.mCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = mMData.get(position).getTitle();
                Toast.makeText(mContext, currentValue, Toast.LENGTH_SHORT).show();
                switch (position) {


                    case 0:
                        try {
                            Intent pointsstandingIntent = new Intent(mContext, Activity_Athletes.class);
                            pointsstandingIntent.putExtra("isHomepage",false);
                            mContext.startActivity(pointsstandingIntent);
                            return;
                        }catch (Exception e){
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    case 1:



                       try {
                             Intent teamsIntent = new Intent(mContext, Activity_PointsStanding.class);
                             //teamsIntent.putExtra("isHomepage",false);
                              mContext.startActivity(teamsIntent);
                            return;
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    default:
                        //Toast.makeText(mContext, "Not Yet Developed 2", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.mCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, position+"", Toast.LENGTH_SHORT).show();
                switch (position) {


                    case 0:
                        try {
                            Intent coachIntent = new Intent(mContext, Activity_Coaches.class);
                            coachIntent.putExtra("isHomepage",false);
                            mContext.startActivity(coachIntent);
                            //new DialogCreator().DialogCreatorPointsStanding(mContext, mDataModel);
                            return;
                        }catch (Exception e){
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }



                    case 1:

                        try {
                            Intent bracketIntent = new Intent(mContext, Activity_Bracket.class);
                            bracketIntent.putExtra("sportName",mSportsTitle);
                            bracketIntent.putExtra("activityName","Events");
                            bracketIntent.putExtra("isHomepage",false);
                            mContext.startActivity(bracketIntent);;
                            return;
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }



                    default:
                        Toast.makeText(mContext, "Not Yet Developed 3", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataModel.size() / 3;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView1;
        public CardView mCardView2;
        public TextView mTextView1;
        public TextView mTextView2;

        public ImageView iconView1;
        public ImageView iconView2;
        public CardView mCardView3;
        public ImageView iconView3;
        public TextView mTextView3;

        public MyViewHolder(View v) {
            super(v);

            mCardView1 = v.findViewById(R.id.card_view_main1);
            mCardView2 = v.findViewById(R.id.card_view_main2);
            mCardView3 = v.findViewById(R.id.card_view_main3);
            mTextView1 = v.findViewById(R.id.button_text1);
            mTextView2 = v.findViewById(R.id.button_text2);
            mTextView3 = v.findViewById(R.id.button_text3);

            iconView1 = v.findViewById(R.id.button_image1);
            iconView2 = v.findViewById(R.id.button_image2);
            iconView3 = v.findViewById(R.id.button_image3);
        }
    }
}
