package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsCustomAdapter extends RecyclerView.Adapter<ResultsCustomAdapter.MyViewHolder> {


    ArrayList<String> mHomeTeams;
    ArrayList<String> mAwayTeams;
    ArrayList<String> mHomeScore;
    ArrayList<String> mAwayScore;
    int mResourceId;
    Context mContext;

    public ResultsCustomAdapter(Context context, int resourceId, ArrayList<String> homeTeams, ArrayList<String> awayTeams, ArrayList<String> homeScore, ArrayList<String> awayScore) {
        mContext = context;
        mResourceId = resourceId;
        mHomeTeams=homeTeams;
        mAwayTeams=awayTeams;
        mHomeScore=homeScore;
        mAwayScore=awayScore;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(mResourceId, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

       myViewHolder.mTeam1Name.setText(mHomeTeams.get(i));

        myViewHolder.mTeam2Name.setText(mAwayTeams.get(i));

        myViewHolder.mTeam1Score.setText(mHomeScore.get(i));

        myViewHolder.mTeam2Score.setText(mAwayScore.get(i));

        myViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = myViewHolder.getAdapterPosition();
                Toast.makeText(mContext, "Results "+(position+1)+" presssed", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHomeScore.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mTeam1Name;
        public TextView mTeam2Name;
        public TextView mTeam1Score;
        public TextView mTeam2Score;
        public LinearLayout ll;


        public MyViewHolder(View v) {
            super(v);
            mTeam1Name = v.findViewById(R.id.team1_name);
            mTeam1Score = v.findViewById(R.id.team1_score);

            mTeam2Name = v.findViewById(R.id.team2_name);
            mTeam2Score = v.findViewById(R.id.team2_score);

            ll=v.findViewById(R.id.results_layout);


        }


    }
}
