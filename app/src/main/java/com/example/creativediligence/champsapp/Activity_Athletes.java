package com.example.creativediligence.champsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class Activity_Athletes extends AppCompatActivity {

    static String TAG = "AthletesActivity";
    ArrayList<Helper_AthleteCoachModel> athletes;
    Toolbar toolbar;
    Button newProfileButton;
    LinearLayout linLay;
    boolean isHomepage;
    ProgressBar gettingData;
    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar,AthletesActivity.this);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }*/
        GetAthleteData("AllAthletes","Name","Age","Personal_Best");

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.athletes_coaches_activity);
        isHomepage=getIntent().getBooleanExtra("isHomepage",false);


         toolbar = findViewById(R.id.atletes_toolbar);
         newProfileButton=findViewById(R.id.new_profile_button);
         linLay=findViewById(R.id.rv_linLay);
         gettingData=findViewById(R.id.getting_data);

         if(isHomepage){
             toolbar.setTitle("Athletes");
         }else {
             toolbar.setTitle("Track & Field Athletes");
             newProfileButton.setVisibility(View.GONE);
             LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
             lp.weight= (float) 0.93;
            linLay.setLayoutParams(lp);
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
        SetupOnclickListeners();

GetAthleteData("AllAthletes","Name","Age","Personal_Best");
    }

    public void GetAthleteData(String className, final String nameKey, final String ageKey, final String pBKey) {
        gettingData.setVisibility(View.VISIBLE);
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
                            String temp = object.getString(ageKey).trim();
                            if(temp.equals("")){
                                temp="0";
                            }
                            int age =Integer.parseInt(temp);
                            //Double pb = object.getDouble(pBKey);
                            Double pb=10.5;
                            Log.i(TAG,name+"/"+age+"/"+pb);
                            mAthlete.add(new Helper_AthleteCoachModel(name, age, pb));
                            mAthleteNames.add(name);
                        }

                        Adapter_AthleteCoachInfo adapter=new Adapter_AthleteCoachInfo(Activity_Athletes.this, R.layout.hr_resource_card_layout,mAthlete,1);
                        RecyclerView rv=findViewById(R.id.athletes_rv);
                        rv.hasFixedSize();
                        rv.setAdapter(adapter);
                        LinearLayoutManager llm = new LinearLayoutManager(Activity_Athletes.this);
                        rv.setLayoutManager(llm);
                        gettingData.setVisibility(View.GONE);


                    }
                } else {
                    Toast.makeText(Activity_Athletes.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("ParseError", e.getMessage());
                }
            }
        });

    }

    public void SetupOnclickListeners(){
        newProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Activity_Athletes.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                //Intent athleteProfileIntent=new Intent(Activity_Athletes.this,Activity_AthleteProfile2.class);
                //Activity_Athletes.this.startActivity(athleteProfileIntent);
                Toast.makeText(Activity_Athletes.this, "Pending Dev", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
