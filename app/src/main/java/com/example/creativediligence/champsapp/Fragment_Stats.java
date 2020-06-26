package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_Stats extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Adapter_ViewPager_Page2 adapter;
    int tabPos;

    int editprofileState;
    boolean profileEditState;
    String editTextTimes;
    String editTextDates;

    public Fragment_Stats() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.athlete_profile_page2, container, false);
        viewPager = view.findViewById(R.id.athlete_profile_events_viewpager);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = view.findViewById(R.id.athlete_profile_events_tab_layout);
        //ArrayList<String> eventsList=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionAthlete");
        query.whereEqualTo("created_by", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    ArrayList<String> eventsList = new ArrayList<>();
                    for (ParseObject obj : objects) {
                        eventsList = new ArrayList<>(Arrays.asList(obj.getString("Event").trim().split(",")));
                        editTextTimes = obj.getString("timeInputs");
                        editTextDates=obj.getString("dateInputs");
                    }

                    if (editTextTimes!=null && eventsList!=null ) {
                        //Toast.makeText(getContext(), "all gooooood", Toast.LENGTH_SHORT).show();
                        InitializeXMLElems(eventsList, editTextTimes,editTextDates);
                    }
                }
            }
        });

        //tabLayout.addTab(tabLayout.newTab().setText("200m"));
        //tabLayout.addTab(tabLayout.newTab().setText("400m"));
        // tabLayout.addTab(tabLayout.newTab().setText("1500m"));


        return view;
    }

    public void InitializeXMLElems(ArrayList<String> eventsList, String editTextTimes,String editTextDates) {
        for (int i = 0; i < eventsList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(eventsList.get(i)));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new Adapter_ViewPager_Page2(getContext(), getChildFragmentManager(), tabLayout.getTabCount(), editTextTimes,editTextDates);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                tabPos = tab.getPosition();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void UpdateXMLElems(ArrayList<String> eventsList) {
        String temp = "1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0";
        String temp2 = "2020/06/16,2020/06/16,2020/06/16,2020/06/16,2020/06/16,2020/06/16," +
                "2020/06/16,2020/06/16,2020/06/16,2020/06/16";
        tabLayout.addTab(tabLayout.newTab().setText(eventsList.get(tabLayout.getTabCount())));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        Fragment[] frags;
        String eventTimes = "";
        String eventDates="";
        if (adapter != null) {
            frags = adapter.fragments;
            for (int i=0;i<frags.length;i++) {
                Fragment fragment=frags[i];
                if (fragment instanceof Fragment_EventStats) {

                    eventTimes = eventTimes + ((Fragment_EventStats) fragment).GetTimes();
                    eventDates=eventDates+((Fragment_EventStats) fragment).GetDates();
                    if (i < tabLayout.getTabCount() - 1) {
                        eventTimes = eventTimes + ":";
                        eventDates=eventDates+":";
                    }
                    //Log.d("eventTimes", eventTimes);
                }

            }
            eventTimes = eventTimes + temp;
            eventDates=eventDates+temp2;
        } else {
            eventTimes = temp;
            eventDates=temp2;
        }

        Log.d("eventTimes", eventTimes.split(":").length + "//" + tabLayout.getTabCount() + "//"+eventTimes);


        adapter = new Adapter_ViewPager_Page2(getContext(), getChildFragmentManager(), tabLayout.getTabCount(), eventTimes,eventDates);
        viewPager.setAdapter(adapter);


    }

    public void ChangeChildTab(int pageNumber, boolean smoothScrool) {
        viewPager.setCurrentItem(pageNumber, smoothScrool);
    }


    public void InnerFrag(int i, boolean bool) {
        profileEditState = bool;
        editprofileState = i;
        for (int ii = 0; ii < tabLayout.getTabCount(); ii++) {
            Fragment frag = adapter.fragments[ii];
            if (frag instanceof Fragment_EventStats) {
                ((Fragment_EventStats) frag).getRVAdapterMethod(i, bool);
            }
        }

    }

    public void EditGraph() {
        Fragment frag = adapter.fragments[tabPos];
        if (frag instanceof Fragment_EventStats) {
            ((Fragment_EventStats) frag).EditGraph();
        }
    }

    public void UpdateEventsData() {
        if (editprofileState == 0 && adapter != null) {
            Fragment[] frags = adapter.fragments;
            String eventNames = "";
            String eventTimes = "";
            String eventDates="";
            for (int i = 0; i < frags.length; i++) {
                Fragment fragment = frags[i];
                if (fragment instanceof Fragment_EventStats) {
                    eventNames = eventNames + tabLayout.getTabAt(i).getText().toString();
                    eventTimes = eventTimes + ((Fragment_EventStats) fragment).GetTimes();
                    eventDates=eventDates+((Fragment_EventStats) fragment).GetDates();
                    if (i < tabLayout.getTabCount()-1) {
                        eventTimes = eventTimes + ":";
                        eventDates=eventDates+":";
                        eventNames = eventNames + ",";
                    }
                }

            }
            Log.d("times", eventNames + "//" + frags.length + "//" + tabLayout.getTabCount() + "//");
            UpdateEventsData2(eventNames, eventTimes,eventDates);
        }
        //MergeTimes();
    }

    public void MergeTimes(String eventNames, String eventTimes,String eventDates) {
        //if (editprofileState == 0) {

        ParseObject eventsObject = new ParseObject("InstitutionAthlete");
        eventsObject.put("created_by", ParseUser.getCurrentUser().getUsername());
        eventsObject.put("Event", eventNames);
        eventsObject.put("timeInputs", eventTimes);
        eventsObject.put("dateInputs", eventDates);
        eventsObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(getContext(), " Data Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // }
    }

    public void UpdateEventsData2(final String eventNames, final String eventTimes, final String eventDates) {
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionAthlete");
        query.whereEqualTo("created_by", ParseUser.getCurrentUser().getUsername());
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject tempObjs : objects) {
                        tempObjs.put("created_by", ParseUser.getCurrentUser().getUsername());
                        tempObjs.put("Event", eventNames);
                        tempObjs.put("timeInputs", eventTimes);
                        tempObjs.put("dateInputs", eventDates);
                        tempObjs.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getContext(), " Data Updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } else {
                    if (e != null) {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        MergeTimes(eventNames, eventTimes,eventDates);
                    }
                }
            }
        });
    }


}
