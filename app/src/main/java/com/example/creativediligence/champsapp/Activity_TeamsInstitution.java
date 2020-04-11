package com.example.creativediligence.champsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_TeamsInstitution extends AppCompatActivity {
    ArrayList<String> tabTitles;
    ArrayList<String[]> tabContents;
    ArrayList<String[]> institutionContents;
    Toolbar toolbar;
    boolean isHomepage;

    @Override
    public void onResume() {
        /*new PreferenceMethods().setColorChosen(toolbar,Teams_Institution_Activity2.this);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_ti);
        new PreferenceMethods().setColorChosen(tabLayout,Teams_Institution_Activity2.this);*/
        super.onResume();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams__institution_2);
        isHomepage=getIntent().getBooleanExtra("isHomepage",false);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(isHomepage) {
            toolbar.setTitle("Teams/Institutions");
        }else{
            toolbar.setTitle("Track & Field Teams/Institutions");
        }
        //new PreferenceMethods().setColorChosen(toolbar,Teams_Institution_Activity2.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tabTitles = new ArrayList<>(Arrays.asList("Males", "Females"));
        GetFieldEvents();
    }

    public void GetFieldEvents() {
        institutionContents = new ArrayList<>();

        ParseQuery<ParseObject> trackEvents = ParseQuery.getQuery("Institution");
        trackEvents.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    String[] strings = new String[objects.size()];
                    ArrayList<String> maleSchool=new ArrayList<>();
                    ArrayList<String> femaleSchool=new ArrayList<>();

                    for (ParseObject obs : objects) {
                       /* if(obs.getString("sex").equals("M")){
                            maleSchool.add(obs.getString("Name"));
                        }else  if(obs.getString("sex").equals("F")){
                            femaleSchool.add(obs.getString("Name"));
                        }else {*/
                            maleSchool.add(obs.getString("Name"));
                            femaleSchool.add(obs.getString("Name"));
                        //}


                    }
                    institutionContents.add(maleSchool.toArray(new String[0]));
                    institutionContents.add(femaleSchool.toArray(new String[0]));
                    Setuplayout();

                } else {
                    Toast.makeText(Activity_TeamsInstitution.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("ParseError", e.getMessage());
                }
            }
        });
    }


    public void Setuplayout() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_ti);
        viewPager.setOffscreenPageLimit(tabTitles.size());
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), Activity_TeamsInstitution.this, tabTitles, institutionContents);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout_ti);
        //new PreferenceMethods().setColorChosen(tabLayout,Teams_Institution_Activity2.this);
        tabLayout.setupWithViewPager(viewPager);


        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }




    class PagerAdapter extends FragmentPagerAdapter {


        ArrayList<String> tabTitles;
        Context context;
        ArrayList<String[]> mArrayList;

        public PagerAdapter(FragmentManager fm, Context context, ArrayList<String> mTabTitles, ArrayList<String[]> arrayList) {
            super(fm);
            this.context = context;
            tabTitles = mTabTitles;
            mArrayList = arrayList;
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            Fragment_TeamsInstitutionsContent bf = new Fragment_TeamsInstitutionsContent();
            args.putStringArray("someArray", mArrayList.get(position));
            args.putString("tabtitle",tabTitles.get(position));
            bf.setArguments(args);
            return bf;
            /*switch (position) {
                case 0:
                    Bundle args = new Bundle();
                    BlankFragment bf = new BlankFragment();
                    args.putStringArray("someArray", mArrayList.get(0));
                    bf.setArguments(args);
                    return bf;
                case 1:
                    Bundle args1 = new Bundle();
                    BlankFragment bf1 = new BlankFragment();
                    args1.putStringArray("someArray", mArrayList.get(1));
                    bf1.setArguments(args1);
                    return bf1;
                case 2:
                    Bundle args2 = new Bundle();
                    BlankFragment bf2 = new BlankFragment();
                    args2.putStringArray("someArray", mArrayList.get(2));
                    bf2.setArguments(args2);
                    return bf2;
            }*/


        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles.get(position);
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(Activity_TeamsInstitution.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles.get(position));
            return tab;
        }

    }
}
