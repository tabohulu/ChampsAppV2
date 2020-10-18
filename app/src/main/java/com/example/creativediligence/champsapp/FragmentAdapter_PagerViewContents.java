package com.example.creativediligence.champsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FragmentAdapter_PagerViewContents extends RecyclerView.Adapter<FragmentAdapter_PagerViewContents.MyViewHolder> {
    Context mContext;
    String[] mData;
    String[] mData2;
    String mTabtitle;
    HashMap<String,ArrayList<String>> eventSubEvents= new HashMap<>();
    HashMap<String, List<AllAthletes>> eventSubEventsAthletes= new HashMap<>();

    private ArrayList<String> mAthleteNames;
    public ArrayList<String> mTabTitleList;

    public FragmentAdapter_PagerViewContents(String[] data, Context context, ArrayList<String> arrayList, String tabtitle) {

        mContext = context;
        mAthleteNames = arrayList;
        mData = data;//events
        mTabtitle = tabtitle;
    }

    public FragmentAdapter_PagerViewContents(String[] data, String[] data2,Context context, ArrayList<String> arrayList, String tabtitle) {

        mContext = context;
        mAthleteNames = arrayList;
        mData = data;//events
        mData2=data2;
        mTabtitle = tabtitle;
        GetExtraInfo();
        //Toast.makeText(context, ""+tabtitle, Toast.LENGTH_SHORT).show();
    }

    public FragmentAdapter_PagerViewContents(Context context, ArrayList<String> arrayList, String tabtitle) {

        mContext = context;
        mAthleteNames = arrayList;
        mTabtitle = tabtitle;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FragmentAdapter_PagerViewContents.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                             int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.general_card_layout_with_image_and_extra, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override


    public void onBindViewHolder(final MyViewHolder holder, int position) {



        holder.mTextView.setText(mData[position]);
        holder.showContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if(holder.contentInfo.getVisibility()==View.GONE){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.top_corners));

                    }else {
                        holder.linearLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.top_corners));
                    }
                    holder.showContent.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arr_up));
                    holder.contentInfo.setVisibility(View.VISIBLE);
                    Adapter_Events_SubEvents adapter=new Adapter_Events_SubEvents(mContext,eventSubEvents.get(mData[position]),eventSubEventsAthletes);
                    holder.contentInfo.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    holder.contentInfo.setLayoutManager(llm);
                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.linearLayout.setBackground(mContext.getResources().getDrawable(R.drawable.all_corners));
                    }else {
                        holder.linearLayout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.all_corners));
                    }
                    holder.showContent.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_arr_down));
                    holder.contentInfo.setVisibility(View.GONE);
                }
            }
        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!mTabTitleList.contains(mTabtitle)) {

                }*/
                int position = holder.getAdapterPosition();
                final String currentValue = mData[position];
                String obID = mData2[position];
                ArrayList<String> individualID = new ArrayList<>(Arrays.asList(obID.split(":")));
                Log.d("CardView", "CardView Clicked: " + currentValue);
                Toast.makeText(mContext, "CardView Clicked: " + currentValue + " // " + eventSubEvents.get(currentValue), Toast.LENGTH_SHORT).show();

                /*if(eventSubEvents.containsKey(currentValue)) {
                    eventSubEvents.get(currentValue);
                    Log.d("newStufh", currentValue + "//" + eventSubEvents.get(currentValue).toString());
                }
                else {
                    ParseQuery<AllSubEvents> query = ParseQuery.getQuery(AllSubEvents.class);
                    final ArrayList<String> temp= new ArrayList<>();
                    for(String ion:individualID){
                    query.getInBackground(ion, new GetCallback<AllSubEvents>() {
                        @Override
                        public void done(AllSubEvents object, ParseException e) {
                            if (e == null) {
                                temp.add(object.getName());
                                Log.d("newStufg", currentValue + "//" + object.getName());
                                eventSubEvents.put(currentValue, temp);

                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }*/
                /*Intent intent = new Intent(mContext, Activity_SubEvents.class);
                intent.putExtra("eventname", currentValue);
                intent.putExtra("generalevents", mTabtitle);
                mContext.startActivity(intent);*/

                //new DialogCreator().DialogCreatorEventAthletes2(mContext, mAthleteNames, currentValue);


            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("Item Count", "" + mData.length);
        //GetExtraInfo();
        return mData.length;
    }
    public void GetExtraInfo(){
        for(int position=0;position<mData.length;position++) {
            final String currentValue = mData[position];
            String obID = mData2[position];
            ArrayList<String> individualID = new ArrayList<>(Arrays.asList(obID.split(":")));

             if(eventSubEvents.containsKey(currentValue)) {
                    eventSubEvents.get(currentValue);
                    Log.d("newStufh", currentValue + "//" + eventSubEvents.get(currentValue).toString());
                }
                else {
                    ParseQuery<AllSubEvents> query = ParseQuery.getQuery(AllSubEvents.class);
                    final ArrayList<String> temp= new ArrayList<>();
                    for(String ion:individualID){
                    query.getInBackground(ion, new GetCallback<AllSubEvents>() {
                        @Override
                        public void done(AllSubEvents object, ParseException e) {
                            if (e == null) {
                                temp.add(object.getName());
                                eventSubEventsAthletes.put(object.getName(),object.getParticipants());
                                Log.d("newStufg", currentValue + "//" + object.getName());
                                eventSubEvents.put(currentValue, temp);

                            } else {
                                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }


        }

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTextView;
        public TextView auTextView;
        public ImageView iconView;
        public RecyclerView contentInfo;
        public ImageView showContent;
        public LinearLayout linearLayout;

        public MyViewHolder(View v) {
            super(v);

            mCardView = v.findViewById(R.id.card_view_aux);
            mTextView = v.findViewById(R.id.athletes_name_textview);
            auTextView = v.findViewById(R.id.athlete_personal_best);
            iconView = v.findViewById(R.id.sub_event_image);
            iconView.setAdjustViewBounds(true);
            iconView.setMaxHeight(100);
            iconView.setMaxWidth(100);
            contentInfo=v.findViewById(R.id.extra_info_contents);
            showContent=v.findViewById(R.id.show_content_img);
            linearLayout=v.findViewById(R.id.layout_outline);
            contentInfo.hasFixedSize();

        }


    }
}
