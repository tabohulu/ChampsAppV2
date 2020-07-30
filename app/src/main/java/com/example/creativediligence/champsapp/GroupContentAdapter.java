package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupContentAdapter extends RecyclerView.Adapter<GroupContentAdapter.MyViewHolder> {
    ArrayList<String> mName;
    ArrayList<String>  mDate;
    ArrayList<String>  mPoster;
    Context mContext;
    int mResourceId;

    public GroupContentAdapter(Context context, int id, ArrayList<String> names, ArrayList<String> dates, ArrayList<String> poster){
        mContext=context;
        mResourceId=id;
        mName=names;
        mDate=dates;
        mPoster=poster;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mResourceId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.TextViewPost.setText(mName.get(position));
        holder.TextViewDate.setText(mDate.get(position));
        holder.TextViewName.setText(mPoster.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String currentValue = mName.get(position);
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();




            }
        });
    }

    @Override
    public int getItemCount() {
        Toast.makeText(mContext, ""+mDate.size(), Toast.LENGTH_SHORT).show();
        return mName.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView TextViewName;
        public TextView TextViewPost;
        public TextView TextViewDate;


        public MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.group_content_cardview);
            TextViewName = v.findViewById(R.id.post_person_name);
            TextViewDate = v.findViewById(R.id.post_person_date);
            TextViewPost = v.findViewById(R.id.post_content);



        }


    }
}
