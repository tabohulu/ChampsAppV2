package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GroupFragmentGroupContentActivity extends AppCompatActivity {
    RecyclerView rv;

    String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_fragment_group_content);

        //Toolbar
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(groupName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        groupName = getIntent().getStringExtra("groupName");
        rv = findViewById(R.id.group_content_recyclerview);
        new ParseQueries().GetGroupData(this, groupName, rv);


    }

    public void CreateNewComment(View view) {
        //Open a dialog
        new Helper_DialogCreator().NewPostDialog(this, groupName, rv);


    }
}
