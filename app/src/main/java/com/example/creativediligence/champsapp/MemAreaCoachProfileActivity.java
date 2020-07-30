package com.example.creativediligence.champsapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemAreaCoachProfileActivity extends AppCompatActivity {
    TextView coachNameTextView;
    ImageView coachImageView;
    String coachName;
    ArrayList<EditText> editTexts;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_area_coach_profile);

        coachName = getIntent().getStringExtra("coachName");
        coachNameTextView = findViewById(R.id.coach_profile_name_tv);
        coachImageView = findViewById(R.id.coach_profile_image);

        coachNameTextView.setText(coachName);
        data = new ArrayList<>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.just_toolbar);
        toolbar.setTitle("Coach Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //XML objects
        EditText et1=findViewById(R.id.profile_name);
        EditText et2=findViewById(R.id.profile_founded);
        EditText et3=findViewById(R.id.profile_trophies);
        EditText et4=findViewById(R.id.profile_league);
        EditText et5=findViewById(R.id.profile_history);
        EditText et6=findViewById(R.id.profile_type);
        EditText et7=findViewById(R.id.profile_teams);
        editTexts=new ArrayList<>(Arrays.asList(et1,et2,et3,et4,et5,et6,et7));
        GetCoachData("Name", "InstitutionCoach");
    }

    public void GetCoachData(String key, String serverClass) {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(serverClass);
        query.whereEqualTo(key, coachName);
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {

                    for (ParseObject obj:objects){

                        data.add( obj.getString("Name"));
                        data.add( obj.getString("DOB"));
                        data.add( obj.getString("Trophies"));
                        data.add( obj.getString("Age"));
                        data.add( obj.getString("Biography"));
                        data.add( obj.getString("Team"));
                        data.add( obj.getString("Institution"));


                        for (int i=0;i<data.size();i++){
                            editTexts.get(i).setText(data.get(i));
                        }


                    }


                } else {
                    Toast.makeText(MemAreaCoachProfileActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
