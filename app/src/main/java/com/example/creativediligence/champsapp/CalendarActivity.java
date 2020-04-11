package com.example.creativediligence.champsapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CalendarActivity extends AppCompatActivity {
    ArrayList<DateItems> tabTitles;
    ArrayList<String> parsedDates;
    Date firstDate;
    Date lastDate;
    int prevPosition;
    int nextPosition;
    int tempPp;
    int tempNp;
    int swipeCount;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Calendar");
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

        parsedDates = new ArrayList<>();
        tabTitles = SetupTabTitles();

        prevPosition = -1;
        nextPosition = 0;
        tempPp = -1;
        tempNp = 0;
        swipeCount = 0;


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(tabTitles.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {


            }

            @Override
            public void onPageSelected(int position) {

                prevPosition = position - 1;
                nextPosition = position + 1;
            }

            @Override
            public void onPageScrollStateChanged(int position) {


                if (position == 0) {
                    if (tempNp == nextPosition && tempPp == prevPosition) {
                        swipeCount++;
                        Log.d("CurrentPosition", swipeCount + "");
                    } else {
                        swipeCount = 0;
                        Log.d("CurrentPosition", swipeCount + "");
                    }


                    Log.d("CurrentPosition", prevPosition + "->" + nextPosition);
                    tempPp = prevPosition;
                    tempNp = nextPosition;
                    if (swipeCount > 0) {


                        Calendar cal = Calendar.getInstance();
                        if (nextPosition > 5) {
                            cal.setTime(lastDate);
                            cal.add(Calendar.DATE, 1);

                            TabReset(cal);

                        } else if (prevPosition < 0) {
                            cal.setTime(firstDate);
                            cal.add(Calendar.DATE, -6);

                            TabReset(cal);
                        }


                    }
                }
            }
        });
        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), CalendarActivity.this, tabTitles, firstDate, lastDate,parsedDates);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        //new PreferenceMethods().setColorChosen(tabLayout, TestActivity.this);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }

    public Date ParseDate(String date_str) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy");
        Date dateStr = null;
        try {
            dateStr = formatter.parse(date_str);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public void TabReset(Calendar cal) {
        Date date = cal.getTime();
        tabTitles = SetupTabTitles(date);
        nextPosition = 0;
        prevPosition = -1;
        tempPp = -1;
        tempNp = 0;
        swipeCount = 0;


        PagerAdapter pagerAdapter =
                new PagerAdapter(getSupportFragmentManager(), CalendarActivity.this, tabTitles, firstDate, lastDate,parsedDates);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        // Iterate over all tabs and set the custom view
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }
    }

    public ArrayList<DateItems> SetupTabTitles() {
        Calendar cal = Calendar.getInstance();
        Date c = cal.getTime();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy");


        ArrayList<DateItems> tabTitles = new ArrayList<>();
        parsedDates = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            cal.setTime(c);
            cal.add(Calendar.DATE, i);
            Date date = cal.getTime();
            parsedDates.add(formatter.format(date));
            if (i == 0) {
                firstDate = date;
            } else if (i == 5) {
                lastDate = date;
            }
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
            String dayOfWeek = simpleDateformat.format(date);
            String monthOfYear = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
            String dayOfMonth = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            String year = String.valueOf(cal.get(Calendar.YEAR));
            tabTitles.add(new DateItems(year, monthOfYear.substring(0, 3), dayOfMonth, dayOfWeek));


            Log.d("Datess", dayOfWeek + " " + " " + dayOfMonth + " " + monthOfYear + " " + year);

        }

        return tabTitles;
    }

    public ArrayList<DateItems> SetupTabTitles(Date pageEndDate) {
        Calendar cal = Calendar.getInstance();
        final SimpleDateFormat formatter = new SimpleDateFormat("MM dd yyyy");


        ArrayList<DateItems> tabTitles = new ArrayList<>();
        parsedDates = new ArrayList<>();
        for (int i = 0; i < 6; i++) {

            cal.setTime(pageEndDate);
            cal.add(Calendar.DATE, i);
            Date date = cal.getTime();
            parsedDates.add(formatter.format(date));
            if (i == 0) {
                firstDate = date;
            } else if (i == 5) {
                lastDate = date;
            }
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
            String dayOfWeek = simpleDateformat.format(date);
            String monthOfYear = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
            String dayOfMonth = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            String year = String.valueOf(cal.get(Calendar.YEAR));
            tabTitles.add(new DateItems(year, monthOfYear.substring(0, 3), dayOfMonth, dayOfWeek));


            Log.d("Datess", dayOfWeek + " " + " " + dayOfMonth + " " + monthOfYear + " " + year);

        }

        return tabTitles;
    }

    class DateItems {

        private String month;
        private String year;
        private String weekDay;
        private String monthDay;

        public DateItems(String yr, String mnth, String wd, String md) {
            year = yr;
            month = mnth;
            weekDay = wd;
            monthDay = md;

        }

        public String getMonth() {
            return month;
        }

        public String getYear() {
            return year;
        }

        public String getWeekDay() {
            return weekDay;
        }


        public String getMonthDay() {
            return monthDay;
        }


    }

    class PagerAdapter extends FragmentStatePagerAdapter {


        ArrayList<DateItems> tabTitles;
        ArrayList<String> mParsedDates;
        Context mContext;
        Date mFirstDate;
        Date mLAstDate;


        public PagerAdapter(FragmentManager fm, Context context, ArrayList<DateItems> mTabTitles, Date firstDate, Date lastDate,ArrayList<String> parsedDates) {
            super(fm);
            mContext = context;
            tabTitles = mTabTitles;
            mFirstDate = firstDate;
            mLAstDate = lastDate;
            mParsedDates=parsedDates;
        }

        @Override
        public int getCount() {
            return tabTitles.size();
        }

        @Override
        public Fragment getItem(int position) {


            Bundle args = new Bundle();
            MyCalendarFragment bf = new MyCalendarFragment();
            args.putString("tabtitle", tabTitles.get(position).getMonth());
            args.putString("weekDay", tabTitles.get(position).getWeekDay());
            args.putString("monthDay", tabTitles.get(position).getMonthDay());
            args.putString("year", tabTitles.get(position).getYear());
            args.putString("searchString",mParsedDates.get(position));

            bf.setArguments(args);
            return bf;


        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles.get(position).getMonth();
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.custom_tab2, null);
            TextView tv = (TextView) tab.findViewById(R.id.month_year);
            TextView tv1 = tab.findViewById(R.id.weekDay);
            TextView tv2 = tab.findViewById(R.id.monthDay);
            tv.setText(tabTitles.get(position).getMonth());
            tv1.setText(tabTitles.get(position).weekDay);
            tv2.setText(tabTitles.get(position).monthDay);
            return tab;
        }

    }
}
