package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;



public class VideoHighlightsActivity extends AppCompatActivity {
    //@BindView(R.id.rvVideoHighlights)
    RecyclerView rvVideoHighlights;
    //@BindView(R.id.ivBack)
    ImageView ivBack;
    VideoHighLightsAdapter videoHighLightsAdapter;
    ArrayList<Comments> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_highlights);
        rvVideoHighlights=findViewById(R.id.rvVideoHighlights);
        ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        videoHighLightsAdapter = new VideoHighLightsAdapter(list, this);
        rvVideoHighlights.setLayoutManager(new LinearLayoutManager(this));
        rvVideoHighlights.setAdapter(videoHighLightsAdapter);
        rvVideoHighlights.setNestedScrollingEnabled(false);
        rvVideoHighlights.setHasFixedSize(false);
    }

    /*@OnClick(R.id.ivBack)
    public void ivBack() {
        onBackPressed();
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
