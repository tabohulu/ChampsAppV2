package com.example.creativediligence.champsapp.MembersArea.Teams;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.GroupFragmentGroupContentActivity;
import com.example.creativediligence.champsapp.R;
import com.example.creativediligence.champsapp.TeamFragmentTeamContentActivity;

import java.util.ArrayList;

public class TeamsFragmentAdapterMain extends RecyclerView.Adapter<TeamsFragmentAdapterMain.MyViewHolder> {
    ArrayList<String> mData ;

    Context mContext;
    int mId;

    public TeamsFragmentAdapterMain(Context context, int resourceId, ArrayList<String> data) {
        mData = data;
        mContext = context;
        mId = resourceId;


    }

    @Override
    public TeamsFragmentAdapterMain.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull TeamsFragmentAdapterMain.MyViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentValue = mData.get(position);
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, TeamFragmentTeamContentActivity.class);//todo:Design and functionality
                intent.putExtra("groupName",mData.get(position));
                mContext.startActivity(intent);


            }
        });

        //ToDo: Delete group on long press
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

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
