package com.example.creativediligence.champsapp;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FragmentAdapter_PointsStanding extends RecyclerView.Adapter<FragmentAdapter_PointsStanding.PointsStandingViewHolder> {

    private ArrayList<Helper_BasicDataModel> mPointsData;


    public FragmentAdapter_PointsStanding(ArrayList<Helper_BasicDataModel> pointsData){
        mPointsData=pointsData;

    }


    @Override
    public PointsStandingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_pointsstanding_layout,viewGroup,false);
        PointsStandingViewHolder vh=new PointsStandingViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PointsStandingViewHolder pointsStandingViewHolder, int i) {
        pointsStandingViewHolder.institutionName.setText(mPointsData.get(i).getTitle());
        pointsStandingViewHolder.institutionPosition.setText(String.valueOf(i+1));
        pointsStandingViewHolder.institutionPoints.setText(mPointsData.get(i).getAuthor());
        //This line is temporal and might be dealt with in the future

    }

    @Override
    public int getItemCount() {
Log.d("data size",""+mPointsData.size());
        return mPointsData.size();

    }


    public static class PointsStandingViewHolder extends RecyclerView.ViewHolder {
        public TextView institutionPosition;
        public TextView institutionName;
        public TextView institutionPoints;


        public PointsStandingViewHolder(View itemView) {
            super(itemView);
            institutionPosition = itemView.findViewById(R.id.position_tv);
            institutionName = itemView.findViewById(R.id.institution_name_tv);
            institutionPoints=itemView.findViewById(R.id.institution_points_tv);

        }
    }
}
