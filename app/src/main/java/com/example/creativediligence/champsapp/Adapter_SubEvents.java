package com.example.creativediligence.champsapp;

import android.content.Context;
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

import java.util.List;

public class Adapter_SubEvents extends RecyclerView.Adapter<Adapter_SubEvents.MyViewHolder>  {

    Context mContext;
    String[] mData;
    String mTabtitle;
    List<String> mAthleteNames;
    String mGeneralEvent;

    public Adapter_SubEvents(Context context, List<String> arrayList, String generalevent) {

        mContext = context;
        mAthleteNames = arrayList;//heat events
        mGeneralEvent=generalevent;//which heat;

    }



    @Override
    public Adapter_SubEvents.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout2, parent, false);
        // set the view's size, margins, paddings and layout parameters
        Adapter_SubEvents.MyViewHolder vh = new Adapter_SubEvents.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.mTextView.setText(mAthleteNames.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                String currentValue = mAthleteNames.get(position);
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();
                /*Intent intent =new Intent(mContext, SubEventsActivity.class);
                intent.putExtra("eventname",currentValue);
                intent.putExtra("generalevents",mTabtitle);
                mContext.startActivity(intent);*/

                //new DialogCreator().DialogCreatorEventAthletes2(mContext, mAthleteNames, currentValue);
                new Helper_DialogCreator().QueryAthletes(mContext,currentValue,mGeneralEvent,mAthleteNames.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mAthleteNames.size();
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
