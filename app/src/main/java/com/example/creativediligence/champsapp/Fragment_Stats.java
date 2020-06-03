package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Fragment_Stats extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    Adapter_ViewPager_Page2 adapter;
     int tabPos;
    public Fragment_Stats() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.athlete_profile_page2,container,false);
        viewPager=view.findViewById(R.id.athlete_profile_events_viewpager);
        viewPager.setOffscreenPageLimit(4);
        tabLayout=view.findViewById(R.id.athlete_profile_events_tab_layout);
        //ArrayList<String> eventsList=new ArrayList<>();
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("InstitutionAthlete");
        query.whereEqualTo("created_by", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size()>0 && e==null){
                    ArrayList<String> eventsList=new ArrayList<>();
                    for(ParseObject obj:objects){
                         eventsList= new ArrayList<>(Arrays.asList(obj.getString("Event").trim().split(",")));
                    }
                    InitializeXMLElems(eventsList);
                }
            }
        });

        //tabLayout.addTab(tabLayout.newTab().setText("200m"));
        //tabLayout.addTab(tabLayout.newTab().setText("400m"));
       // tabLayout.addTab(tabLayout.newTab().setText("1500m"));






        return view;
    }

    public void InitializeXMLElems(ArrayList<String> eventsList){
        for(int i=0;i<eventsList.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(eventsList.get(i)));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new Adapter_ViewPager_Page2(getContext(),getChildFragmentManager(), tabLayout.getTabCount());
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
    }

    public void UpdateXMLElems(ArrayList<String> eventsList){

            tabLayout.addTab(tabLayout.newTab().setText(eventsList.get(tabLayout.getTabCount())));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Adapter_ViewPager_Page2 adapter = new Adapter_ViewPager_Page2(getContext(),getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


    }

    public void ChangeChildTab(int pageNumber,boolean smoothScrool){
        viewPager.setCurrentItem(pageNumber, smoothScrool);
    }


public void InnerFrag(int i, boolean bool){
        Fragment frag=adapter.fragments[tabPos];
        if(frag instanceof Fragment_EventStats){
            ((Fragment_EventStats)frag).getRVAdapterMethod(i,bool);
        }

}

public void EditGraph(){
    Fragment frag=adapter.fragments[tabPos];
    if(frag instanceof Fragment_EventStats){
        ((Fragment_EventStats)frag).EditGraph();
    }
}


}
