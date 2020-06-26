package com.example.creativediligence.champsapp;

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

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class EventsFragmentAdapterMain extends RecyclerView.Adapter<EventsFragmentAdapterMain.MyViewHolder> {
    ArrayList<String> mData ;

    Context mContext;
    int mId;
    List<ParseObject>mStoredData;

    public EventsFragmentAdapterMain(Context context, int resourceId, ArrayList<String> data, List<ParseObject> storedData) {
        mData = data;
        mContext = context;
        mId = resourceId;
        mStoredData=storedData;


    }

    @Override
    public EventsFragmentAdapterMain.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull EventsFragmentAdapterMain.MyViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentValue = mData.get(position);
                ParseObject individualEventData=mStoredData.get(position);

                String[] items={individualEventData.getString("eventName"),
                        individualEventData.getString("eventSport"),
                        individualEventData.getString("eventDate"),
                        individualEventData.getString("eventTime")};

                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, CreatedEventActivity.class);
                intent.putExtra("createdEventData",items);
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
