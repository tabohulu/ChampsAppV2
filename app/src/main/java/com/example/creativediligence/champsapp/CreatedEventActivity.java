package com.example.creativediligence.champsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.creativediligence.champsapp.R;


public class CreatedEventActivity extends AppCompatActivity {
String[] createdEventData;
TextView createdEventTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_event);
        createdEventData=getIntent().getStringArrayExtra("createdEventData");
        //createdEventTextView=findViewById(R.id.created_event_name);
        createdEventTextView.setText("Event Name: "+createdEventData[0]);
    }
}
