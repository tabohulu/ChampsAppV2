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

public class MessageFragmentAdapter extends RecyclerView.Adapter<MessageFragmentAdapter.MyViewHolder> {
    String[] mData={"Hello", "World"};
    Context mContext;
    int mId;

    public MessageFragmentAdapter(Context context, int resourceId) {

        mContext = context;
        mId=resourceId;



    }

    @Override
    public MessageFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MessageFragmentAdapter.MyViewHolder holder, final int position) {
        holder.TextViewLeft.setText(mData[position]);
        holder.mCardViewLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentValue = mData[position];
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();




            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardViewLeft;
        public TextView TextViewLeft;

        public CardView mCardViewRight;
        public TextView TextViewRight;


        public MyViewHolder(View v) {
            super(v);

            mCardViewLeft = v.findViewById(R.id.cardview_left);
            TextViewLeft = v.findViewById(R.id.other_tv);

            mCardViewLeft.setCardBackgroundColor(mContext.getResources().getColor(R.color.sky_blue));

            mCardViewRight = v.findViewById(R.id.cardview_right);
            TextViewRight = v.findViewById(R.id.me_tv);

            mCardViewRight.setVisibility(View.INVISIBLE);
            TextViewRight.setVisibility(View.INVISIBLE);

        }


    }
}
