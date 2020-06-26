package com.example.creativediligence.champsapp;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.creativediligence.champsapp.Common.CalendarEventsItem;
import com.example.creativediligence.champsapp.Common.CalendarUtils;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TeamFragmentTeamContentActivity extends AppCompatActivity {

    ArrayList<String> homeTeams=new ArrayList<>(Arrays.asList("HomeTeam1","HomeTeam2","HomeTeam3"));
    ArrayList<String> awayTeams=new ArrayList<>(Arrays.asList("AwayTeam1","AwayTeam2","AwayTeam3"));
    ArrayList<String> homeScores=new ArrayList<>(Arrays.asList("3","3","1"));
    ArrayList<String> awayScores=new ArrayList<>(Arrays.asList("2","3","0"));
    CaldroidFragment caldroidFragment;
    ProgressBar pbar;

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Resumed", Toast.LENGTH_LONG).show();
        if(pbar!=null){
            CalendarUtils cu=new CalendarUtils(this,pbar,caldroidFragment);
            cu.DownloadAndSetupCalDroid();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Paused", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_fragment_team_content);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Team X");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);*/




        pbar=findViewById(R.id.progress_bar);

        CalendarUtils cu=new CalendarUtils(this,pbar,caldroidFragment);
        cu.DownloadAndSetupCalDroid();

        RecyclerView rv =findViewById(R.id.squadRecyclerView);
        rv.setHasFixedSize(true);

        SquadCustomAdapter adapter = new SquadCustomAdapter( this,R.layout.squad_custom_layout);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);


        RecyclerView rv1 =findViewById(R.id.resultsRecyclerView);
        rv1.setHasFixedSize(true);

        ResultsCustomAdapter adapter1 = new ResultsCustomAdapter( this,R.layout.results_custom_layout,homeTeams,awayTeams,homeScores,awayScores);
        rv1.setAdapter(adapter1);

        LinearLayoutManager llm1 = new LinearLayoutManager(this);
        rv1.setLayoutManager(llm1);
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
                        CalendarEventsItem ci = new CalendarEventsItem(ob.getString("eventDate"),
                                ob.getString("eventName"),
                                ob.getString("eventType"),
                                ob.getString("eventStart")+" - "+ob.getString("eventEnd"),
                                new ColorDrawable(getResources().getColor(R.color.sky_blue)));
                        Log.d("TAG",ci.getEventPeriod());

                        calendarEvents.add(ci);

                    }
                    Toast.makeText(TeamFragmentTeamContentActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                    SetupCaldroid(calendarEvents,hm,hm2);
                }else{
                    if(e!=null){
                        Toast.makeText(TeamFragmentTeamContentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(TeamFragmentTeamContentActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
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

        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);


        caldroidFragment.setBackgroundDrawableForDates(hm);
        caldroidFragment.refreshView();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy");
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String dayOfMonth= String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

                SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
                String dayOfWeek=simpleDateformat.format(date);

                new DialogCreator().CalendarDialog(TeamFragmentTeamContentActivity.this,dayOfMonth,dayOfWeek,hm2.get(date),formatter.format(date),pbar,caldroidFragment);
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                Toast.makeText(getApplicationContext(),
                        "Long click " + formatter.format(date),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCaldroidViewCreated() {
                Toast.makeText(getApplicationContext(),
                        "Caldroid view is created",
                        Toast.LENGTH_SHORT).show();
            }

        };
        caldroidFragment.setCaldroidListener(listener);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.trainingSchedule, caldroidFragment);
        t.commit();
        pbar.setVisibility(View.INVISIBLE);

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
