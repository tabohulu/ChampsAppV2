package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class SquadCustomAdapter extends RecyclerView.Adapter<SquadCustomAdapter.MyViewHolder> {
    String[] playerNames = {"Player1", "Player2", "Player3", "Player4", "Player5", "Player6", "Player7", "Player8", "Player9"};
    ArrayList<String> playerSkills = new ArrayList<String>(Arrays.asList("skill1,skill2,skill3", "skill1,skill2,skill3", "skill1,skill2,skill3", "skill1,skill2,skill3", "skill1,skill2,skill3",
            "skill1,skill2,skill3", "skill1,skill2,skill3", "skill1,skill2,skill3", "skill1,skill2,skill3"));
    int mResourceId;
    Context mContext;

    public SquadCustomAdapter(Context context, int resourceId) {
        mContext = context;
        mResourceId = resourceId;

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        int one = (1 + (3 * i));
        int two = (2 + (3 * i));
        int three = (3 + (3 * i));
        myViewHolder.mPlayer1Name.setText(playerNames[0 + (3 * i)] + "/" +one);
        myViewHolder.mPlayer2Name.setText(playerNames[1 + (3 * i)] + "/" + two);
        myViewHolder.mPlayer3Name.setText(playerNames[2 + (3 * i)] + "/" + three);


    }

    @Override
    public int getItemCount() {
        return playerSkills.size() / 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mPlayer1Name;
        public TextView mPlayer2Name;
        public TextView mPlayer3Name;

        public TextView mPlayer1Skills;
        public TextView mPlayer2Skills;
        public TextView mPlayer3Skills;


        public ImageView mPlayer1Image;
        public ImageView mPlayer2Image;
        public ImageView mPlayer3Image;

        public MyViewHolder(View v) {
            super(v);

            mPlayer1Image = v.findViewById(R.id.player1Image);
            mPlayer1Name = v.findViewById(R.id.player1Name);
            mPlayer1Skills = v.findViewById(R.id.player1Skills);

            mPlayer2Image = v.findViewById(R.id.player2Image);
            mPlayer2Name = v.findViewById(R.id.player2Name);
            mPlayer2Skills = v.findViewById(R.id.player2Skills);

            mPlayer3Image = v.findViewById(R.id.player3Image);
            mPlayer3Name = v.findViewById(R.id.player3Name);
            mPlayer3Skills = v.findViewById(R.id.player3Skills);


            mPlayer1Image.setMaxHeight(100);
            mPlayer1Image.setMaxWidth(100);

            mPlayer2Image.setMaxHeight(100);
            mPlayer2Image.setMaxWidth(100);


            mPlayer3Image.setMaxHeight(100);
            mPlayer3Image.setMaxWidth(100);


        }


    }
}
