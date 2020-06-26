package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Activity_Coaches extends AppCompatActivity {

    static String TAG = "AthletesActivity";
    ArrayList<Helper_AthleteCoachModel> athletes;
    Toolbar toolbar;
    boolean isHomepage;
    Button newProfileButton;

    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar, CoachesActivity.this);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athletes_coaches_activity);
        isHomepage = getIntent().getBooleanExtra("isHomepage", false);
        newProfileButton = findViewById(R.id.new_profile_button);
        newProfileButton.setVisibility(View.GONE);

        toolbar = findViewById(R.id.atletes_toolbar);
        if (isHomepage) {
            toolbar.setTitle("Coaches");
        } else {
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

    public ArrayList<Helper_AthleteCoachModel> GetAthleteData(String className, final String nameKey, final String ageKey, final String pBKey) {
        final ArrayList<Helper_AthleteCoachModel> mAthlete = new ArrayList<>();
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
                            mAthlete.add(new Helper_AthleteCoachModel(name, Integer.parseInt(age), pb));
                            mAthleteNames.add(name);
                        }

                        Adapter_AthleteCoachInfo adapter = new Adapter_AthleteCoachInfo(Activity_Coaches.this, R.layout.hr_resource_card_layout, mAthlete, 2);
                        RecyclerView rv = findViewById(R.id.athletes_rv);
                        rv.hasFixedSize();
                        rv.setAdapter(adapter);
                        LinearLayoutManager llm = new LinearLayoutManager(Activity_Coaches.this);
                        rv.setLayoutManager(llm);


                    }
                } else {
                    Toast.makeText(Activity_Coaches.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("ParseError", e.getMessage());
                }
            }
        });
        return mAthlete;
    }
}
