package com.example.creativediligence.champsapp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_AthleteProfile2 extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    int tabPos=0;

    ImageView editProfileImage;
    ImageView cancelProflieEditImage;

    Adapter_ViewPager adapter;

    int editprofileState = 0;
    boolean profileEditState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__athlete_profile2);

        editProfileImage = findViewById(R.id.edit_profile_img);
        cancelProflieEditImage = findViewById(R.id.cancel_edit_img);

        toolbar = findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        mTitle.setText(Helper_MyData.activityTitles[2]);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPager=findViewById(R.id.athlete_profile_viewpager);
        tabLayout=findViewById(R.id.athlete_profile_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("Basic Info"));
        tabLayout.addTab(tabLayout.newTab().setText("Stats"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

       adapter = new Adapter_ViewPager(Activity_AthleteProfile2.this,getSupportFragmentManager(), tabLayout.getTabCount(),
                editprofileState,profileEditState);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabPos=tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        cancelProflieEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Activity_AthleteProfile2.this, "Profile Creation Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        editProfileImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                if (editprofileState == 0) {
                    editProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_done));
                    editprofileState += 1;
                    profileEditState = true;

                } else {
                    editProfileImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_edit));
                    editprofileState -= 1;
                    profileEditState = false;
                }
                Fragment frag = adapter.fragments[tabPos];
                Toast.makeText(Activity_AthleteProfile2.this, ""+(frag instanceof Fragment_BasicInfo), Toast.LENGTH_LONG).show();
                if (frag instanceof Fragment_BasicInfo) {
                    ((Fragment_BasicInfo)frag).Change(editprofileState,profileEditState);
                }else if(frag instanceof Fragment_Stats){
                    ((Fragment_Stats)frag).InnerFrag(editprofileState,profileEditState);

                }
                /*Log.d("fragment_act",profileEditState+"");
                Log.d("fragment_act",editprofileState+"");*/
               /*final Adapter_ViewPager adapter = new Adapter_ViewPager(Activity_AthleteProfile2.this,getSupportFragmentManager(), tabLayout.getTabCount(),
                        editprofileState,profileEditState);
                viewPager.setAdapter(adapter);*/
                viewPager.getAdapter().notifyDataSetChanged();



            }
        });


    }

    public void SwitchPage(int pageNumber,boolean smoothScrool,int childPageNumber){
        viewPager.setCurrentItem(pageNumber,smoothScrool);
        Fragment frag = adapter.fragments[tabPos];
        if (frag instanceof Fragment_Stats) {
            ((Fragment_Stats)frag).ChangeChildTab(childPageNumber,smoothScrool);
        }
    }

    public void EditGraph(){
        Fragment frag = adapter.fragments[tabPos];
        if (frag instanceof Fragment_Stats) {
            ((Fragment_Stats)frag).EditGraph();
        }
    }

    public void GetEventsList(){
        Fragment frag = adapter.fragments[tabPos];
        if (frag instanceof Fragment_BasicInfo) {
            ArrayList<String> eventsList=((Fragment_BasicInfo) frag).GetEventsList();
            Fragment frag1 = adapter.fragments[tabPos+1];
            if (frag1 instanceof Fragment_Stats) {
                ((Fragment_Stats) frag1).UpdateXMLElems(eventsList);
            }
        }

    }
}
