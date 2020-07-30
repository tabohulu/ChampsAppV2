package com.example.creativediligence.champsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class InstitutionsEventsFragment extends Fragment {
    public static ArrayList<String> mData;
    RecyclerView rv;
    SharedPreferences prefs;
    TextView noDataTextView;
    List<ParseObject> storedData;

    public InstitutionsEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = this.getActivity().getSharedPreferences("com.example.creativediligence.champsapp", MODE_PRIVATE);
        mData = new ArrayList<>();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.institutions_events_fragment_main_layout, container, false);
        mData = new ArrayList<>();
        noDataTextView=rootView.findViewById(R.id.no_data_info_tv);
        noDataTextView.setVisibility(View.INVISIBLE);
        rv = rootView.findViewById(R.id.groups_fragment_rv);
        PopulateRecyclerView();


        Button newEventButton = rootView.findViewById(R.id.new_group_button);
        newEventButton.setText("Create Event");

        newEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Helper_DialogCreator().NewEventDialog(getContext(),rv,noDataTextView);
            }
        });

        return rootView;
    }

    public void PopulateRecyclerView() {

        final ArrayList<String> mData=new ArrayList<>();
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionEvent");
        query.whereEqualTo("createdBy", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    storedData=objects;
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("mEventName"));
                    }

                    noDataTextView.setVisibility(View.GONE);

                    //rv = (RecyclerView) rootview.findViewById(R.id.instu_coach_fragment_rv);
                    rv.setHasFixedSize(true);

                    EventsFragmentAdapterMain adapter = new EventsFragmentAdapterMain(getContext(), R.layout.home_fragment_layout_custom, mData,storedData);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getContext());
                    rv.setLayoutManager(llm);

                } else {
                    if(e!=null) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "No Data Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
