package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class Activity_SubEvents extends AppCompatActivity {
    final String TAG = "SubEventsActivity";
    String eventname;
    String sportName;
    List<String> subevents;

    Toolbar toolbar;

    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar, SubEventsActivity.this);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_events);
        eventname = getIntent().getStringExtra("eventname");
        sportName = getIntent().getStringExtra("generalevents").replace(" ","");

        if(sportName.equals("TrackandField")) {
            sportName="TrackAndField";
        }


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(eventname);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Log.d(TAG,eventname+"//"+ sportName+"//"+sportName.contains("And") );

        GetSubEvents();
    }

    public void SetupLayout() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_recycler_view2);
        rv.setHasFixedSize(true);

        Adapter_SubEvents adapter = new Adapter_SubEvents(Activity_SubEvents.this, subevents, eventname);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(Activity_SubEvents.this);
        rv.setLayoutManager(llm);
    }

    public void GetSubEvents() {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(sportName);
        query.whereEqualTo("eventName", eventname);
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                        for (ParseObject obs : objects) {
                            subevents = obs.getList("subEvents");
                        }
                    SetupLayout();
                } else {
                    if(e!=null){
                        Log.d(TAG, e.getMessage());
                    }else{
                        Log.d(TAG,"Something isnt right");
                    }
                }
            }
        });
    }
}
