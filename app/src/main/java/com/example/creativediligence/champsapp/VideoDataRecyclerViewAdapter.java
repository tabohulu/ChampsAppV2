package com.example.creativediligence.champsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VideoDataRecyclerViewAdapter extends GeneralRecyclerViewAdapter {

    public VideoDataRecyclerViewAdapter(Context context, int resourceId, ArrayList<String> data, String currentFragment, ArrayList<String> data2) {
        super(context, resourceId, data, currentFragment, data2);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.mTextView.setText(mData.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                String currentValue = mURLData.get(position);
                /*Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue, Toast.LENGTH_SHORT).show();*/

                LayoutInflater li = LayoutInflater.from(mContext);
                final View videoViewLayout = li.inflate(R.layout.video_popup_layout,null);


                AlertDialog.Builder videoPopupDialog= new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
                videoPopupDialog.setNegativeButton("cancel",null)
                        .setTitle(mData.get(position));
                videoPopupDialog.setView(videoViewLayout);
                AlertDialog alertDialog=videoPopupDialog.create();
                alertDialog.show();
                //*****************Video Setup****************************//
                VideoView videoView=videoViewLayout.findViewById(R.id.popup_vid_view);
                Uri uri = Uri.parse(currentValue);
                videoView.setVideoURI(uri);
                MediaController mediaController = new MediaController(mContext);
                mediaController.show();
                videoView.setMediaController(mediaController);
                videoView.requestFocus();
                videoView.start();
                //Toast.makeText(mContext, "Vid Success", Toast.LENGTH_LONG).show();



            }
        });

        //DeleteVids
        holder.deleteIconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final String currentValue = mData.get(position);
                //Toast.makeText(mContext, currentValue+ " delete icon clicked", Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(mContext);
                final View textView = li.inflate(R.layout.delete_confirm_layout,null);

                AlertDialog.Builder deleteConfirmDialog= new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
                deleteConfirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String serverClass="Videos";
                        String key="filename";

                        ParseQuery<ParseObject> deleteQuery = new ParseQuery<ParseObject>(serverClass);
                        deleteQuery.whereEqualTo(key,currentValue);
                        deleteQuery.setLimit(1);
                        deleteQuery.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if(e==null && objects.size()>0){
                                    for(ParseObject ob :objects){
                                        ob.deleteInBackground(new DeleteCallback() {
                                            @Override
                                            public void done(ParseException e) {
                                                mData.remove(holder.getAdapterPosition());
                                                notifyDataSetChanged();
                                                Toast.makeText(mContext, "Delete Complete", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }else{
                                    if (e != null) {
                                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "No Data Exists", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        });
                        //Toast.makeText(mContext, currentValue+" Deleted.", Toast.LENGTH_SHORT).show();

                    }
                })
                        .setNegativeButton("No",null)
                        .setTitle("Delete Item/"+currentValue);
                deleteConfirmDialog.setView(textView);
                AlertDialog alertDialog=deleteConfirmDialog.create();
                alertDialog.show();
            }
        });
    }
}
