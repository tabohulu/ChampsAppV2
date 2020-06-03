package com.example.creativediligence.champsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


public class Activity_Common_SportsEventOverview extends AppCompatActivity {
    final static String TAG = Activity_Common_SportsEventOverview.class.getSimpleName();
    ArrayList<String> tabTitles;
    ArrayList<String[]> tabContents;
    ArrayList<String[]> eventContents;
    SharedPreferences prefs;
    Toolbar toolbar;
    int colorChosen;
    ListView webView;
    ImageView showFeed;
    ArrayList<String> tweets;
    ConfigurationBuilder cb;
    String sportsTitle;
    String activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_activity_sports_event_overview);
        sportsTitle=getIntent().getStringExtra("sportName");
        activityName=getIntent().getStringExtra("activityName");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(sportsTitle+" "+activityName);
        //new PreferenceMethods().setColorChosen(toolbar, EventsOverviewActivity.this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tabTitles = new ArrayList<>(Arrays.asList("Field Events", "Track Events"));
        //GetFieldEvents();
        GetAllSportsEvents(sportsTitle);

        webView = findViewById(R.id.test_webview);
        showFeed = findViewById(R.id.twitterFeed);
        showFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.getVisibility() == View.GONE) {
                    webView.setVisibility(View.VISIBLE);
                } else if (webView.getVisibility() == View.VISIBLE) {
                    webView.setVisibility(View.GONE);
                }
            }
        });

        tweets = new ArrayList<>();
        cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("vwbQWZL3IyiN5A5E7Vcpb8QaR")
                .setOAuthConsumerSecret("DbJfda8UCxO369R5KMLkzo8M0nW8e1eV3yLE01H4DP8M4H21lJ")
                .setOAuthAccessToken("294358300-b5QE4rviGYN3oYzhNi7BGJpBJalBor8WOnkZkbUp")
                .setOAuthAccessTokenSecret("idNh1jOCwlPEqCe9y0fjxPBQshxsGfH7G480LCRmpXB2N");


        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1)
            new GetTwitterTask().execute();
        else
            new GetTwitterTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    }


    public void Setuplayout() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(tabTitles.size());
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), Activity_Common_SportsEventOverview.this, tabTitles, eventContents);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //new PreferenceMethods().setColorChosen(tabLayout, EventsOverviewActivity.this);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        //GetAllSportsEvents("Track and Field");
    }

    public void GetAllSportsEvents(final String eventName) {
        eventContents = new ArrayList<>();
        final HashMap<String, ArrayList<String>> sportEvents = new HashMap<>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("AllSports");
        query.whereContains("sportName", eventName);
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {

                    for (ParseObject obj : objects) {
                        final String[] cats = obj.getString("categories").split(",");
                        tabTitles = new ArrayList<>(Arrays.asList(cats));
                        ParseRelation<ParseObject> rel = obj.getRelation("allEvents");
                        rel.getQuery().findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e == null & objects.size() > 0) {

                                    for (ParseObject ob2 : objects) {
                                        for (int i = 0; i < cats.length; i++) {

                                            if (ob2.getString("eventCategory").equals(cats[i]) && sportEvents.containsKey(cats[i])) {
                                                ArrayList<String> temp = sportEvents.get(cats[i]);
                                                temp.add(ob2.getString("eventName"));
                                                sportEvents.put(cats[i], temp);

                                                Log.d("SEO", ob2.getString("eventName") + " // " + cats[i]);
                                            } else if (ob2.getString("eventCategory").equals(cats[i]) && !sportEvents.containsKey(cats[i])) {
                                                ArrayList<String> temp = new ArrayList<>();
                                                temp.add(ob2.getString("eventName"));
                                                sportEvents.put(cats[i], temp);

                                                Log.d("SEO", ob2.getString("eventName") + " // " + cats[i]+"//");
                                            }


                                        }
                                    }
                                    for(int i=0;i<cats.length;i++){
                                        eventContents.add(sportEvents.get(cats[i]).toArray(new String[0]));
                                    }
                                    Setuplayout();
                                }else{
                                    Toast.makeText(Activity_Common_SportsEventOverview.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                                    finish();
                                    Log.e("ParseError", e.getMessage());
                                }
                            }
                        });
                    }

                }
            }
        });
    }

    public void GetTrackEvents() {
        //GetFieldEvents();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TrackEvents");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {

                    String[] strings = new String[objects.size()];
                    int df = 0;
                    for (ParseObject obs : objects) {

                        strings[df] = obs.getString("eventname");
                        df++;

                    }
                    eventContents.add(strings);
                    try {
                        Setuplayout();
                    } catch (Exception ex) {
                        Toast.makeText(Activity_Common_SportsEventOverview.this, "Something went wrong,please retry", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(Activity_Common_SportsEventOverview.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("ParseError", e.getMessage());
                }
            }
        });
    }

    public void GetFieldEvents() {
        eventContents = new ArrayList<>();

        ParseQuery<ParseObject> trackEvents = ParseQuery.getQuery("FieldEvents");
        trackEvents.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    String[] strings = new String[objects.size()];
                    int df = 0;
                    for (ParseObject obs : objects) {

                        strings[df] = obs.getString("eventname");
                        df++;

                    }
                    eventContents.add(strings);
                    GetTrackEvents();
                } else {
                    Toast.makeText(Activity_Common_SportsEventOverview.this, "Nothing to show", Toast.LENGTH_SHORT).show();
                    finish();
                    Log.e("ParseError", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar, EventsOverviewActivity.this);
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        //new PreferenceMethods().setColorChosen(tabLayout, EventsOverviewActivity.this);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


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
            Fragment_PagerViewContents bf = new Fragment_PagerViewContents();
            args.putStringArray("someArray", mArrayList.get(position));
            args.putString("tabtitle", tabTitles.get(position));
            args.putString("sportName",sportsTitle);
            bf.setArguments(args);
            return bf;


        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles.get(position);
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(Activity_Common_SportsEventOverview.this).inflate(R.layout.custom_tab, null);
            TextView tv = tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles.get(position));
            return tab;
        }

    }


    class GetTwitterTask extends AsyncTask<String, Void, ArrayList<String>> {

        @Override
        protected void onPostExecute(ArrayList<String> arrayList) {
            ArrayAdapter<String> aa = new ArrayAdapter<String>(Activity_Common_SportsEventOverview.this, android.R.layout.simple_list_item_1, arrayList);
            webView.setAdapter(aa);
            /*for(int i=0;i<arrayList.size();i++){
                Toast.makeText(MainActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
            }*/
            super.onPostExecute(arrayList);
        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {


            TwitterFactory tf = new TwitterFactory(cb.build());
            twitter4j.Twitter twitter = tf.getInstance();


            Query query = new Query("trackalerts");
            QueryResult result = null;
            try {
                result = twitter.search(query);
            } catch (twitter4j.TwitterException e) {
                Log.e(TAG, e.getErrorMessage());
            }
            if (result != null) {
                for (twitter4j.Status status : result.getTweets()) {
                    Log.e("Tweets", "@" + status.getUser().getScreenName() + ":" + status.getText());
                    tweets.add("@" + status.getUser().getScreenName() + ":" + status.getText());
                }

            }
            return tweets;
        }
    }


}
