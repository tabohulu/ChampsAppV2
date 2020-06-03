package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;


public class VideoHighLightsAdapter extends RecyclerView.Adapter<VideoHighLightsAdapter.MyViewHolder> {
    ArrayList<Comments> list = new ArrayList<>();
    Context context;


    public VideoHighLightsAdapter(ArrayList<Comments> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //        @BindView(R.id.tvPosition)
//        TextView tvPosition;
//        @BindView(R.id.tvInstitution)
//        TextView tvInstitution;
       // @BindView(R.id.ivPlayThumb)
        ImageView ivPlayThumb;
        //@BindView(R.id.ivVIdImage)
        ImageView ivVIdImage;

        public MyViewHolder(View view) {
            super(view);
            ivPlayThumb=view.findViewById(R.id.ivPlayThumb);
            ivVIdImage=view.findViewById(R.id.ivVIdImage);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_highlights_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(R.drawable.ic_thumb)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.ivVIdImage);
        Glide.with(context)
                .load(R.drawable.ic_video_thumb)
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.ivPlayThumb);
//        holder.tvPosition.setText(list.get(position).getImage());
//        holder.tvInstitution.setText(list.get(position).getTitle());
//        holder.tvPoints.setText(list.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return /*list.size()*/6;
    }

}
