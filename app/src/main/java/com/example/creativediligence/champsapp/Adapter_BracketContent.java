package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_BracketContent extends RecyclerView.Adapter<Adapter_BracketContent.MyViewHolder> {
    Context mContext;
    List<String> mAthletes;
    List<String> mPositions;

    public Adapter_BracketContent(Context context, List<String> athletes,List<String> positions){
        mContext=context;
        mAthletes=athletes;
        mPositions=positions;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.athlete_within_brackets_layout,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int posiition) {
        holder.expandedListTextView.setText(mAthletes.get(posiition));
        holder.athletePosition.setText(mPositions.get(posiition));


    }

    @Override
    public int getItemCount() {
        return mAthletes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView expandedListTextView;
        TextView athletePosition;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            expandedListTextView= itemView.findViewById(R.id.teams_institution_children);
            athletePosition=itemView.findViewById(R.id.tvPosition);
        }
    }
}
