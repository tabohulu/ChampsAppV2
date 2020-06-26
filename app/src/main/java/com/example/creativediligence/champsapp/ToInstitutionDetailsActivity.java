package com.example.creativediligence.champsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToInstitutionDetailsActivity extends AppCompatActivity {

    ArrayList<EditText> editTexts;
    String title;
    ArrayList<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_institution_details);
        title=getIntent().getStringExtra("institutionName");

        //Toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
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
        EditText et8 = findViewById(R.id.profile_coaches);
        editTexts=new ArrayList<>(Arrays.asList(et1,et2,et3,et4,et5,et6,et7,et8));
        GetInstitutionData();



    }

    public void GetInstitutionData(){
        data=new ArrayList<>();
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("Institution");
        query.whereEqualTo("Name",title);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null && objects.size()>0){

                    for (ParseObject obj:objects){

                       data.add( obj.getString("Name"));
                       data.add( obj.getString("Founded"));
                       data.add( obj.getString("Trophies"));
                       data.add( obj.getString("League"));
                       data.add( obj.getString("History"));
                       data.add( obj.getString("Type"));
                       data.add( obj.getString("Teams"));
                       data.add( obj.getString("Coaches"));


                        for (int i=0;i<data.size();i++){
                            editTexts.get(i).setText(data.get(i));
                        }


                    }


                }
            }
        });
    }
}
