package com.example.creativediligence.champsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.CalendarEventsDetailsActivity;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roomorama.caldroid.CaldroidFragment;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventsAdapter extends RecyclerView.Adapter<CalendarEventsAdapter.MyViewHolder> {

    Context mContext;
    int mId;
    final static String TAG=CalendarEventsAdapter.class.getSimpleName();
    ArrayList<CalendarEventsItem> mEventsItems;
    ProgressBar mProgressBar;
    CaldroidFragment mCaldroidFragment;

    public CalendarEventsAdapter(Context context, int resourceId, ArrayList<CalendarEventsItem> eventsItems, ProgressBar progressBar, CaldroidFragment caldroidFragment) {

        mContext = context;
        mId = resourceId;
        mEventsItems =eventsItems;
        mProgressBar=progressBar;
        mCaldroidFragment = caldroidFragment;



    }

    @Override
    public CalendarEventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(mId, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull final CalendarEventsAdapter.MyViewHolder holder, int position) {
        holder.mEventName.setText(mEventsItems.get(position).getEventName());
        holder.mEventPeriod.setText(mEventsItems.get(position).getEventPeriod());

        holder.mLayoutWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                Toast.makeText(mContext, "Event "+ mEventsItems.get(position).getEventName()+" Clicked", Toast.LENGTH_SHORT).show();
                String pp=mEventsItems.get(position).getEventPeriod();
                int dash_pos=pp.indexOf("-");
                int lastIndex=pp.length();
                String start=pp.substring(0,dash_pos-1);
                String end =pp.substring(dash_pos+2,lastIndex);
                Intent intent = new Intent(mContext, CalendarEventsDetailsActivity.class);
                intent.putExtra("eventDate",mEventsItems.get(position).getEventDate());
                intent.putExtra("eventStart",start);
                intent.putExtra("eventEnd",end);
                intent.putExtra("eventType",mEventsItems.get(position).getEventType());
                intent.putExtra("eventName",mEventsItems.get(position).getEventName());
                Log.d(TAG,mEventsItems.get(position).getEventName());
                mContext.startActivity(intent);
            }
        });

        holder.mDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position=holder.getAdapterPosition();
                final String currentValue = mEventsItems.get(position).getEventName();
                //Toast.makeText(mContext, currentValue+ " delete icon clicked", Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(mContext);
                final View textView = li.inflate(R.layout.delete_confirm_layout,null);

                AlertDialog.Builder deleteConfirmDialog= new AlertDialog.Builder(mContext, R.style.MyDialogTheme);
                deleteConfirmDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String serverClass="MasterCalendar";
                        String key="eventName";
                       /* if(mCurrentFragment.equals("Institutions")){
                            serverClass="Institution";
                            key="Name";
                        }else if(mCurrentFragment.equals("Institution Managers")){
                            serverClass="InstitutionManager";
                            key="Name";
                        }*/
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
                                                mEventsItems.remove(holder.getAdapterPosition());
                                                notifyDataSetChanged();
                                                Toast.makeText(mContext, "Delete Complete", Toast.LENGTH_SHORT).show();
                                                CalendarUtils cu=new CalendarUtils(mContext,mProgressBar,mCaldroidFragment);
                                                cu.DownloadAndSetupCalDroid();
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

    @Override
    public int getItemCount() {
        return mEventsItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mEventName;
        public TextView mEventPeriod;
        public LinearLayout mLayoutWrapper;
        public ImageView mDeleteIcon;

        public MyViewHolder(View v) {
            super(v);

            mEventName = v.findViewById(R.id.event_name);
            mEventPeriod = v.findViewById(R.id.event_period);
            mLayoutWrapper=v.findViewById(R.id.layout_wrapper);
            mDeleteIcon=v.findViewById(R.id.delete_image);

        }


    }
}
