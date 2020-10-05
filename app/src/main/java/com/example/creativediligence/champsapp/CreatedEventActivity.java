package com.example.creativediligence.champsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.R;


public class CreatedEventActivity extends AppCompatActivity {
String createdEventData;
EditText createdEventTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_event);
        createdEventData=getIntent().getStringExtra("createdEventData");
        Toast.makeText(this, ""+(createdEventData), Toast.LENGTH_SHORT).show();
        createdEventTextView=findViewById(R.id.event_name_textView);
        //createdEventTextView.setText("Event Name: "+createdEventData[0]);
    }
}
