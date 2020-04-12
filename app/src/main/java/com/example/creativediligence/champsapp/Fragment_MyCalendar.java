package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_MyCalendar extends Fragment {
    String tabtitle;
    String weekDay;
    String monthDay;
    String dateString;

    public Fragment_MyCalendar() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //events = getArguments().getStringArray("someArray");
        tabtitle = getArguments().getString("tabtitle");
        tabtitle.replace("\\s+", "");
        weekDay = getArguments().getString("weekDay");
        monthDay = getArguments().getString("monthDay");
        dateString = getArguments().getString("searchString");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.calendar_fragment_blank, container, false);
        final TextView noData = rootView.findViewById(R.id.no_event);
        final ExpandableListView eventsExpandableListView = rootView.findViewById(R.id.expandableList);
        final ArrayList<String> sportsType = new ArrayList<>();
        final HashMap<String, ArrayList<Helper_CalendarEventsItem>> eventDeets = new HashMap<>();
        // CalendarExpandableListAdapter adapter=new CalendarExpandableListAdapter(getContext(),)
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MasterCalendar");
        query.whereEqualTo("eventDate", dateString.trim());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        Helper_CalendarEventsItem ci = new Helper_CalendarEventsItem(ob.getString("eventName"),
                                ob.getString("eventDate"),
                                ob.getString("eventStart") + "-" + ob.getString("eventEnd"),
                                ob.getString("eventType"), "Venue");
                        ArrayList<Helper_CalendarEventsItem> list = new ArrayList<>();


                        if (eventDeets.containsKey(ob.getString("eventType"))) {
                           list = eventDeets.get(ob.getString("eventType"));


                        } else{
                            sportsType.add(ob.getString("eventType"));
                        }
                        list.add(ci);
                        eventDeets.put(ob.getString("eventType"), list);
                    }
                    FragmentAdapterExpandable_MyCalendar adapter=new FragmentAdapterExpandable_MyCalendar(getContext(),sportsType,eventDeets);
                    eventsExpandableListView.setAdapter(adapter);

                    noData.setText(objects.size() + " events on this day");
                    noData.setVisibility(View.GONE);


                } else {
                    if (e != null) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        noData.setText("No Event On This Day");
                    }
                }
            }
        });


        return rootView;
    }
}
