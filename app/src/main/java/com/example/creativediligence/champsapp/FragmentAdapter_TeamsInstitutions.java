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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

public class FragmentAdapter_TeamsInstitutions extends RecyclerView.Adapter<FragmentAdapter_TeamsInstitutions.MyViewHolder> {
    Context mContext;
    String[] mData;
    String mTabtitle;

    public FragmentAdapter_TeamsInstitutions(String[] data, Context context, ArrayList<String> arrayList, String tabtitle) {

        mContext = context;
        mData = data;//events
        mTabtitle = tabtitle;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.teams_institution_data_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Glide.with(mContext)
                .load(R.drawable.ic_thumb)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.iconView);
        holder.mTextView.setText(mData[position]);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String currentValue = mData[position];
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();
                /*Intent intent =new Intent(mContext, TeamsInstitutionsDetailsActivity.class);
                 *//*intent.putExtra("eventname",currentValue);*//*
                intent.putExtra("schoolname",currentValue);
                mContext.startActivity(intent);*/

                //new DialogCreator().DialogCreatorEventAthletes2(mContext, mAthleteNames, currentValue);

            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Item Count", "" + mData.length);
        return mData.length;
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
