package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Activity_AthleteCoachDetails extends AppCompatActivity {
    String athleteName;

    Toolbar toolbar;
    int activityNumber;
    List<String> hrInfoGroup = new ArrayList<>();
    String className;
    String queryKey;

    TextView personNameTV;
    TextView personMoreInfoTV;
    TextView personAgeContent;
    TextView personPBContent;

    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar, AthleteDetailsActivity.this);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_details);
        athleteName = getIntent().getStringExtra("athleteName");
        activityNumber = getIntent().getIntExtra("activityNumber", -1);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(athleteName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        hrInfoGroup = new ArrayList<>();
        className = "";

        personNameTV = findViewById(R.id.person_name);
        personNameTV.setText(athleteName);
        personMoreInfoTV = findViewById(R.id.more_info);
        GetAthletesData(activityNumber);
        personAgeContent=findViewById(R.id.person_age_content);
        personPBContent=findViewById(R.id.person_PB_content);


    }

    public void GetAthletesData(final int activityNumber) {
        final ExpandableListView brackets = findViewById(R.id.athlete_details_exp_lv);

        if (activityNumber == 1) {
            hrInfoGroup = new ArrayList<String>(Arrays.asList("Biography", "Stats", "Event","Position"));
            className = "InstitutionAthlete";
            queryKey="Name";
            personMoreInfoTV.setText(className.substring(0,className.length()-1));
        } else if (activityNumber == 2) {
            hrInfoGroup = new ArrayList<String>(Arrays.asList("Biography", "History", "Events Coaching", "Events Won"));
            className = "Coaches";
            queryKey="name";
            personMoreInfoTV.setText(className.substring(0,className.length()-2));
        }

        final HashMap<String, List<String>> hrResourceDetails = new HashMap<>();

        ParseQuery<ParseObject> athlete = new ParseQuery<ParseObject>(className);
        athlete.whereEqualTo(queryKey, athleteName);
        //athlete.setLimit(1);
        athlete.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        if(activityNumber==2) {
                            hrResourceDetails.put(hrInfoGroup.get(0), new ArrayList<String>(Arrays.asList(ob.getString(hrInfoGroup.get(0).toLowerCase().trim()))));
                            hrResourceDetails.put(hrInfoGroup.get(1), new ArrayList<String>(Arrays.asList(ob.getString(hrInfoGroup.get(1).toLowerCase().trim()))));
                            hrResourceDetails.put(hrInfoGroup.get(2), (ob.<String>getList(hrInfoGroup.get(2).toLowerCase().trim().replace(" ", ""))));
                            hrResourceDetails.put(hrInfoGroup.get(3), (ob.<String>getList(hrInfoGroup.get(3).toLowerCase().trim().replace(" ", ""))));
                            personAgeContent.setText(String.valueOf(ob.getInt("age")));
                            //personPBContent.setText(String.valueOf( ob.getDouble("pb")));
                        }else{
                            hrResourceDetails.put(hrInfoGroup.get(0), new ArrayList<String>(Arrays.asList(ob.getString(hrInfoGroup.get(0)))));
                            hrResourceDetails.put(hrInfoGroup.get(1), new ArrayList<String>(Arrays.asList(ob.getString(hrInfoGroup.get(1)))));
                            String[] temp=ob.getString(hrInfoGroup.get(2)).split(",");
                            hrResourceDetails.put(hrInfoGroup.get(2), new ArrayList<String>(Arrays.asList(temp)));
                            hrResourceDetails.put(hrInfoGroup.get(3), new ArrayList<String>(Arrays.asList(ob.getString(hrInfoGroup.get(3)))));
                            personAgeContent.setText(ob.getString("Age"));
                        }



                    }
                    AdapterExpandable_AthleteCoachDetails expandableListAdapter = new AdapterExpandable_AthleteCoachDetails(Activity_AthleteCoachDetails.this, hrInfoGroup, hrResourceDetails, 1);
                    brackets.setAdapter(expandableListAdapter);
                } else {
                    Log.d("Errors", e.getMessage());
                }
            }
        });


    }
}
