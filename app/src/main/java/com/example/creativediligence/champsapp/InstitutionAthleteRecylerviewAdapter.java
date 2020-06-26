package com.example.creativediligence.champsapp.Common;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.creativediligence.champsapp.ToInstitutionDetailsActivity;

import java.util.ArrayList;

public class InstitutionAthleteRecylerviewAdapter extends GeneralRecyclerViewAdapter {

    public InstitutionAthleteRecylerviewAdapter(Context context, int resourceId, ArrayList<String> data, String currentFragment) {
        super(context, resourceId, data, currentFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.mTextView.setText(mData.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String currentValue = mData.get(position);
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();

                if(mCurrentFragment.equals("Institutions")) {
                    Intent intent = new Intent(mContext, ToInstitutionDetailsActivity.class);
                    intent.putExtra("institutionName", mData.get(position));
                    mContext.startActivity(intent);
                }else /*if(mCurrentFragment.equals("Institution Managers"))*/{
                    Toast.makeText(mContext, "Nothing Done Yet", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}
