package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CoachesActivity extends AppCompatActivity {

    static String TAG = "AthletesActivity";
    ArrayList<AthleteModel> athletes;
    Toolbar toolbar;
    boolean isHomepage;

    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar, CoachesActivity.this);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athletes);
isHomepage=getIntent().getBooleanExtra("isHomepage",false);

        toolbar = findViewById(R.id.atletes_toolbar);
        if(isHomepage){
            toolbar.setTitle("Coaches");
        }else {
            toolbar.setTitle("Track & Field Coaches");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GetAthleteData("Coaches", "name", "age", "wins");
    }

    public ArrayList<AthleteModel> GetAthleteData(String className, final String nameKey, final String ageKey, final String pBKey) {
        final ArrayList<AthleteModel> mAthlete = new ArrayList<>();
        final ArrayList<String> mAthleteNames = new ArrayList<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(className);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    Log.i("ParseFIB", "Got " + objects.size() + " objects");

                    if (objects.size() > 0) {


                        for (ParseObject object : objects) {
                            String name = object.getString(nameKey);
                            String age = object.getString(ageKey);
                            Double pb = object.getDouble(pBKey);
                            Log.i(TAG, name + "/" + age + "/" + pb);
                            mAthlete.add(new AthleteModel(name, Integer.parseInt(age), pb));
                            mAthleteNames.add(name);
                        }

                        PeopleInfoRecyclerViewAdapter adapter=new PeopleInfoRecyclerViewAdapter(CoachesActivity.this, R.layout.hr_resource_card_layout,mAthlete,2);
                        RecyclerView rv=findViewById(R.id.athletes_rv);
                        rv.hasFixedSize();
                        rv.setAdapter(adapter);
                        LinearLayoutManager llm = new LinearLayoutManager(CoachesActivity.this);
                        rv.setLayoutManager(llm);


                    }
                } else {
                    Toast.makeText(CoachesActivity.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("ParseError", e.getMessage());
                }
            }
        });
        return mAthlete;
    }
}
