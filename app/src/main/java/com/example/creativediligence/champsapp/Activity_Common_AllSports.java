package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;



import java.util.ArrayList;

public class Activity_Common_AllSports extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView allSportsRv;
    ArrayList<Helper_BasicDataModel> data;
    private String sportsTitle;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sports_common);

        sportsTitle=getIntent().getStringExtra("sportName");
        InitializeXML();
        SetupRecyclerView();
    }

    public void InitializeXML(){
        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(sportsTitle);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        allSportsRv=findViewById(R.id.all_sports_general_rv);
        allSportsRv.setHasFixedSize(true);
    }

    public void SetupRecyclerView(){
        layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        allSportsRv.setLayoutManager(layoutManager);
        allSportsRv.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        for (int i = 0; i < Helper_MyData.sportGeneralHomepageButtons.length; i++) {
            data.add(new Helper_BasicDataModel(
                    Helper_MyData.sportGeneralHomepageButtons[i],
                    Helper_MyData.sportGeneralHomepageImages[i]
            ));
        }
        Adapter_AllSportsCommon homeButtonsAdapter = new Adapter_AllSportsCommon(data,sportsTitle, Activity_Common_AllSports.this);
        allSportsRv.setAdapter(homeButtonsAdapter);
    }
}
