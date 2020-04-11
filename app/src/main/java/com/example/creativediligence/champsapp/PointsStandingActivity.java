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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointsStandingActivity extends AppCompatActivity {
    List<ParseObject> resultsList;
    int initialListLength;
    int deflength;
    Button button;
    private ArrayList<Helper_BasicDataModel> data;
    private ArrayList<String> tabTitles;


    Toolbar toolbar;
    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar,PointsStandingActivity.this);

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_standing);

       /* initialListLength = 5;
        deflength=initialListLength;
        button = findViewById(R.id.show_more_button);*/

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Track & Field Points Standing");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tabTitles = new ArrayList<>(Arrays.asList("All Results", "Top Male","Top Female"));
        SetuplayoutMain();

        //data = new ArrayList<BasicDataModel>();
       /* for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new BasicDataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.drawableArray[i]
            ));
        }*/

        //GetPointsStandingData();
    }

    public void SetuplayoutMain() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(tabTitles.size());
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), PointsStandingActivity.this, tabTitles);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //new PreferenceMethods().setColorChosen(tabLayout, EventsOverviewActivity.this);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }

    public void GetPointsStandingData() {
        ParseQuery<ParseObject> EventResults = new ParseQuery<ParseObject>("EventResults");
        EventResults.addDescendingOrder("point2");

        EventResults.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    resultsList = objects;
                    if (resultsList.size() <= initialListLength) {
                        initialListLength = resultsList.size();


                    }
                    for (int i = 0; i < initialListLength; i++) {
                        data.add(new Helper_BasicDataModel(resultsList.get(i).getString("institution"), resultsList.get(i).getString("points"), resultsList.get(i).getInt("position")));
                    }
                    /*RecyclerView recyclerView = findViewById(R.id.points_standing_rv);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PointsStandingActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    PointsStandingAdapter adapter = new PointsStandingAdapter(data);
                    recyclerView.setAdapter(adapter);*/
                } else {
                    Toast.makeText(PointsStandingActivity.this, "Nothing To See yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void SortResults(View view) {
        /*TextView tv = findViewById(R.id.title_text);
        Button but = (Button) view;
        String butText = but.getText().toString();
        tv.setText(butText);
        Toast.makeText(this, view.getTag().toString() + "/" + but.getText(), Toast.LENGTH_SHORT).show();*/

    }

    public void ToVideoHighlights(View view) {
        Toast.makeText(this, "Yet to be Developed", Toast.LENGTH_SHORT).show();
    }

    public void ShowMore(View view) {
        Button button = (Button) view;
        Toast.makeText(this, button.getText().toString() + " pressed ", Toast.LENGTH_SHORT).show();


        if(button.getText().toString().equals("More")){
            initialListLength += deflength;
        }else {
            initialListLength -= deflength;
        }
        Toast.makeText(this, ""+initialListLength, Toast.LENGTH_SHORT).show();
        SetupLayout();
        /*if (initialListLength>resultsList.size()){
            initialListLength=resultsList.size();
            button.setClickable(false);
            button.setTextColor(Color.LTGRAY);
        }
        for (int i = 0; i < initialListLength; i++) {
            data.add(new BasicDataModel(resultsList.get(i).getString("institution"), resultsList.get(i).getString("points"), resultsList.get(i).getInt("position")));
        }
        RecyclerView recyclerView = findViewById(R.id.points_standing_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PointsStandingActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        PointsStandingAdapter adapter = new PointsStandingAdapter(data);
        recyclerView.setAdapter(adapter);*/
    }

    public void SetupLayout() {
        data = new ArrayList<>();

        if (initialListLength >= resultsList.size() ) {
            initialListLength = resultsList.size();
            button.setText("Less");
            Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();

        }else if(initialListLength <= deflength ){
            initialListLength = deflength;
            button.setText("More");
            Toast.makeText(this, "here "+initialListLength, Toast.LENGTH_SHORT).show();
        }


        for (int i = 0; i < initialListLength; i++) {
            data.add(new Helper_BasicDataModel(resultsList.get(i).getString("institution"), resultsList.get(i).getString("points"), resultsList.get(i).getInt("position")));
        }
       /* RecyclerView recyclerView = findViewById(R.id.points_standing_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PointsStandingActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        PointsStandingAdapter adapter = new PointsStandingAdapter(data);
        recyclerView.setAdapter(adapter);*/
    }

    class PagerAdapter extends FragmentPagerAdapter {


        ArrayList<String> tabTitles;
        Context context;
        ArrayList<String[]> mArrayList;

        public PagerAdapter(FragmentManager fm, Context context, ArrayList<String> mTabTitles) {
            super(fm);
            this.context = context;
            tabTitles = mTabTitles;
            //mArrayList = arrayList;
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {
            Bundle args = new Bundle();
            PointsStandingResFragment bf = new PointsStandingResFragment();
            //args.putStringArray("someArray", mArrayList.get(position));
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
            View tab = LayoutInflater.from(PointsStandingActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles.get(position));
            return tab;
        }

    }

}
