package com.example.creativediligence.champsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Activity_GraphData extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rv;
    String[] runTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__graph_data);

        runTimes=new String[10];
                for(int i=0;i<runTimes.length;i++){
runTimes[i]="Time "+(i+1);
                    Log.i("Parse",runTimes[i]);
                }
        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText("Race Times");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rv=findViewById(R.id.data_rv);
        rv.setHasFixedSize(true);

        //Adapter_GraphData adapter = new Adapter_GraphData(this,runTimes);
        //rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(Activity_GraphData.this);
        rv.setLayoutManager(llm);
    }
}
