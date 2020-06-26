package com.example.creativediligence.champsapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendListAdapterMain extends RecyclerView.Adapter<FriendListAdapterMain.MyViewHolder> {
    ArrayList<String> mData ;

    Context mContext;
    int mId;
   ArrayList<Boolean> selectedFriends;

    public FriendListAdapterMain(Context context, int resourceId, ArrayList<String> data) {
        mData = data;
        mContext = context;
        mId = resourceId;
        selectedFriends=new ArrayList<>();
        for(int i=0;i<mData.size();i++){
            selectedFriends.add(i,false);
        }


    }

    @Override
    public FriendListAdapterMain.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final FriendListAdapterMain.MyViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, mData.get(position)+" selected", Toast.LENGTH_SHORT).show();
                if(selectedFriends.get(position)==false){
                    holder.ll.setBackgroundColor(mContext.getResources().getColor(R.color.sky_blue));
                    selectedFriends.set(position,true);
                }else {
                    holder.ll.setBackgroundColor(Color.WHITE);
                    selectedFriends.set(position,false);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout ll;
        public TextView mTextView;


        public MyViewHolder(View v) {
            super(v);

            ll=v.findViewById(R.id.friend_list_layout);
            mTextView = v.findViewById(R.id.friend_tv);


        }


    }
}
