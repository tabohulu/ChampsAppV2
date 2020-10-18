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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Adapter_Events_SubEvents extends RecyclerView.Adapter<Adapter_Events_SubEvents.MyViewHolder> {
    Context mContext;
    List<String> mSubEvents;
    HashMap<String, List<AllAthletes>> mParticipants;


    public Adapter_Events_SubEvents(Context context, List<String> subEvents, HashMap<String, List<AllAthletes>> participants){
        mContext=context;
        mSubEvents=subEvents;
        mParticipants=participants;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cards_layout2,viewGroup,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        holder.mTextView.setText(mSubEvents.get(i));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                String currentValue = mSubEvents.get(position);
                Log.d("CardView", "CardView Clicked: " + currentValue);
                List<AllAthletes> parts=mParticipants.get(currentValue);
                List<String> temp=new ArrayList<>();
                for(AllAthletes p:parts){
                    temp.add(p.getObjectId());
                }

                Toast.makeText(mContext, "CardView Clicked: " + currentValue+"=>"+temp.toString().replace(",",":"), Toast.LENGTH_SHORT).show();

                /*Intent intent =new Intent(mContext, SubEventsActivity.class);
                intent.putExtra("eventname",currentValue);
                intent.putExtra("generalevents",mTabtitle);
                mContext.startActivity(intent);*/

                //new DialogCreator().DialogCreatorEventAthletes2(mContext, mAthleteNames, currentValue);
                new Helper_DialogCreator().QueryAthletes2(mContext,currentValue,currentValue.split(":")[0],parts.size(),temp);

            }
        });

    }

    public void GetAllParticipants(){

    }

    @Override
    public int getItemCount() {
        return mSubEvents.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView auTextView;
        public ImageView iconView;


        public MyViewHolder(@NonNull View v) {
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
