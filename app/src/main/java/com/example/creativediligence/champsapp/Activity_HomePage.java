package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;



import java.util.ArrayList;


public class Activity_HomePage extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView buttonsRV;
    ArrayList<Helper_BasicDataModel> data;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //initialize xml elements
        InitializeXMLElements();

        //setup recycler buttons
        SetupRecyclerViewButtons();
    }

    public void InitializeXMLElements() {
        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        buttonsRV=findViewById(R.id.buttons_recyclerview);
        buttonsRV.setHasFixedSize(true);
    }

    public void SetupRecyclerViewButtons(){
        layoutManager = new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        buttonsRV.setLayoutManager(layoutManager);
        buttonsRV.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        for (int i = 0; i < Helper_MyData.homePageButtons.length; i++) {
            data.add(new Helper_BasicDataModel(
                    Helper_MyData.homePageButtons[i],
                    Helper_MyData.homePageImages[i]
            ));
        }
        //Toast.makeText(this, ""+data.size(), Toast.LENGTH_LONG).show();
        Adapter_Homepage homeButtonsAdapter = new Adapter_Homepage(data, Activity_HomePage.this);
        buttonsRV.setAdapter(homeButtonsAdapter);
    }
}
