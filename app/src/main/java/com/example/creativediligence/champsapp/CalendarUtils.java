package com.example.creativediligence.champsapp;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.creativediligence.champsapp.Helper_DialogCreator;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CalendarUtils {
    public Context mContext;
    public ProgressBar mProgressBar;
    public CaldroidFragment mCaldroidFragment;
    public static String TAG=CalendarUtils.class.getSimpleName();

    public CalendarUtils(Context context, ProgressBar progressBar, CaldroidFragment caldroidFragment){
        mContext=context;
        mProgressBar=progressBar;
        mCaldroidFragment=caldroidFragment;
    }


    public void DownloadAndSetupCalDroid() {


        // Toast.makeText(this, "Not Yet Developed", Toast.LENGTH_SHORT).show();
        final HashMap<Date, Drawable> hm = new HashMap();
        final HashMap<Date, ArrayList<CalendarEventsItem>> hm2 = new HashMap();
        final ArrayList<CalendarEventsItem> calendarEvents = new ArrayList<>();

        ParseQuery<ParseObject> masterCalendarEvent=new ParseQuery<ParseObject>("MasterCalendar");
        masterCalendarEvent.setLimit(50);
        masterCalendarEvent.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null && objects.size()>0){
                    for(ParseObject ob:objects){
                        CalendarEventsItem ci = new CalendarEventsItem(
                                ob.getString("eventName"),
                                ob.getString("eventDate"),
                                ob.getString("eventType"),
                                ob.getString("eventStart")+" - "+ob.getString("eventEnd"),
                                new ColorDrawable(mContext.getResources().getColor(R.color.sky_blue)));

                        Log.d(TAG,ci.getEventDate());

                        calendarEvents.add(ci);

                    }
                    Toast.makeText(mContext, "Complete", Toast.LENGTH_SHORT).show();

                        SetupCaldroid(calendarEvents, hm, hm2);//todo : fix null error here

                }else{
                    if(e!=null){
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });







    }

    public void SetupCaldroid(ArrayList<CalendarEventsItem> calendarEvents, HashMap<Date,Drawable> hm, final HashMap<Date,ArrayList<CalendarEventsItem>> hm2){
        // Put elements to the map for event color
        for (CalendarEventsItem ce : calendarEvents) {
            if (!hm.containsKey(ParseDate(ce.getEventDate()))) {
                hm.put(ParseDate(ce.getEventDate()), ce.getEventColor());
                //Log.d(TAG,ce.getEventDate()+ce.getEventColor());
            }
        }
        // Put elements to the map for events
        for (CalendarEventsItem ce : calendarEvents) {
            //case where that event doesnt exist in hasmap, hust add it
            if (!hm2.containsKey(ParseDate(ce.getEventDate()))) {
                ArrayList<CalendarEventsItem> ff=new ArrayList<>();
                ff.add(ce);
                hm2.put(ParseDate(ce.getEventDate()), ff);
            }//case where date exist, update key values
            else{
                ArrayList<CalendarEventsItem> ff=hm2.get(ParseDate(ce.getEventDate()));
                ff.add(ce);
                hm2.put(ParseDate(ce.getEventDate()), ff);
            }
            Log.d("hm2Value",hm2.get(ParseDate(ce.getEventDate())).toString());
        }

        mCaldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        mCaldroidFragment.setArguments(args);


        mCaldroidFragment.setBackgroundDrawableForDates(hm);
        mCaldroidFragment.refreshView();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy");
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String dayOfMonth= String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

                SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
                String dayOfWeek=simpleDateformat.format(date);

                new Helper_DialogCreator().CalendarDialog(mContext,dayOfMonth,dayOfWeek,hm2.get(date),formatter.format(date),mProgressBar,mCaldroidFragment);
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(mContext, text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(mContext,
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                Toast.makeText(mContext,
                        "Caldroid view is created",
                        Toast.LENGTH_SHORT).show();
            }

        };

        mCaldroidFragment.setCaldroidListener(listener);
        FragmentTransaction t = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
        t.replace(R.id.trainingSchedule, mCaldroidFragment);
        t.commit();
        mProgressBar.setVisibility(View.INVISIBLE);

    }

    public Date ParseDate(String date_str) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy");
        Date dateStr = null;
        try {
            dateStr = formatter.parse(date_str);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
