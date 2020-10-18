package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;
import java.util.List;

public class Adapter_DialogCreatorEventAthletes extends RecyclerView.Adapter<Adapter_DialogCreatorEventAthletes.MyViewHolder> {
    Context mContext;
    String[] mData;
    private ArrayList<Helper_BasicDataModel> mDataModel;
    private ArrayList<String> mAthleteNames;
    private String mEvent;
    int mCards;
    public Adapter_DialogCreatorEventAthletes(Context context, ArrayList<String> names, String event, int cards){
        mContext=context;
        mAthleteNames=names;
        mData=new String[mAthleteNames.size()];
        for (int i=0;i<mAthleteNames.size();i++){
            mData[i]="-1";
        }
        mEvent=event;
        mCards=cards;
    }
    @NonNull
    @Override
    public Adapter_DialogCreatorEventAthletes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_events_athletes_recyclerview_layout_more_info, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {
        Glide.with(mContext)
                .load(R.drawable.ic_thumb)
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.mPersonIcon);

        holder.mTextView.setText(mAthleteNames.get(i));
        List<String> list=new ArrayList<>();
        list.add("---");
        for(int ii=0;ii<mAthleteNames.size();ii++){
            list.add(String.valueOf(ii+1));
        }
        final int pos=i;
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, list);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.mOptions.setAdapter(dataAdapter);
        holder.mOptions.setSelection(0);
        Toast.makeText(mContext, "number of cards : "+mCards, Toast.LENGTH_SHORT).show();
        holder.mOptions.setVisibility(View.GONE);
        holder.spinnerText.setVisibility(View.GONE);
        if(mEvent.contains("Final") || !mEvent.contains(":")){
            holder.mOptions.setVisibility(View.VISIBLE);
            holder.spinnerText.setVisibility(View.VISIBLE);
        }
        holder.mOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                Log.d("Positions","adapter positiion: "+pos+ " spinner position: "+position);
                int r=0;
                for (int iv=0;iv<mData.length;iv++){
                    if(mData[iv].equals(holder.mOptions.getSelectedItem().toString())){
                        Toast.makeText(mContext, "Position "+ mData[iv]+" already selected!", Toast.LENGTH_SHORT).show();
                        holder.mOptions.setSelection(0);
                        mData[pos]="-1";
                        r++;
                        break;

                    }


                }if(r==0 &&holder.mOptions.getSelectedItemPosition()!=0){
                    mData[pos]=holder.mOptions.getSelectedItem().toString();
                    Log.d("Data", mData + "i: "+pos);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return mAthleteNames.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public Spinner mOptions;
        public ImageView mPersonIcon;
        public TextView spinnerText;

        public MyViewHolder(View v) {
            super(v);


            mTextView = v.findViewById(R.id.athelete_placeholder);
            mOptions = v.findViewById(R.id.predicted_position_placeholder);
            mPersonIcon=v.findViewById(R.id.athlete_icon);
            spinnerText=v.findViewById(R.id.tvEvent);





        }


    }
}
