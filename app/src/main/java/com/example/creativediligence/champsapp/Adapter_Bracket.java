package com.example.creativediligence.champsapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class Adapter_Bracket extends RecyclerView.Adapter<Adapter_Bracket.MyViewHolder> {
Context mContext;
List<String> mTitles;
HashMap<String, List<String>> mBracketDetail;
HashMap<String, List<String>> mBracketPosition;

    public Adapter_Bracket(Context context,List<String> titles,HashMap<String, List<String>> bracketDetail,HashMap<String, List<String>> bracketPosition){
        mContext=context;
        mTitles=titles;
        mBracketDetail=bracketDetail;
        mBracketPosition=bracketPosition;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.custom_bracket_layout, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
    holder.title_text.setText(mTitles.get(position));
    holder.more_info.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = holder.getAdapterPosition();
            if(holder.content_layout.getVisibility()==View.GONE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.more_info_layout.setBackground(mContext.getResources().getDrawable(R.drawable.top_corners));

                } else {
                    holder.more_info_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.top_corners));
                }
                holder.more_info.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arr_up));
                holder.content_layout.setVisibility(View.VISIBLE);

                LinearLayoutManager linearLayoutManager= new LinearLayoutManager(mContext);
                Adapter_BracketContent adapter_bracketContent=new Adapter_BracketContent(mContext,mBracketDetail.get(mTitles.get(position)),mBracketPosition.get(mTitles.get(position)));
                holder.contentsRecyclerView.setLayoutManager(linearLayoutManager);
                holder.contentsRecyclerView.setAdapter(adapter_bracketContent);

            }else{
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    holder.more_info_layout.setBackground(mContext.getResources().getDrawable(R.drawable.all_corners));

                } else {
                    holder.more_info_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.all_corners));
                }
                holder.more_info.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arr_down));
                holder.content_layout.setVisibility(View.GONE);
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView more_info;
        public TextView title_text;
        public ImageView delete;
        LinearLayout more_info_layout;
        LinearLayout content_layout;
        RecyclerView contentsRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            more_info=itemView.findViewById(R.id.more_info_img);
            delete=itemView.findViewById(R.id.delete_bracket_img);
            title_text=itemView.findViewById(R.id.title_text);
            content_layout=itemView.findViewById(R.id.content_layout);
            more_info_layout=itemView.findViewById(R.id.more_info_layout);
            contentsRecyclerView=itemView.findViewById(R.id.content_recyclerview);
            contentsRecyclerView.hasFixedSize();

        }
    }
}
