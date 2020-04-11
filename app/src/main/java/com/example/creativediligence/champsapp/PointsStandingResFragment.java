package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PointsStandingResFragment extends Fragment {
    ArrayList<Helper_BasicDataModel> data;
    //String[] events;
    String tabtitle;
    List<ParseObject> resultsList;
    int initialListLength=5;
    ArrayList<String> listContents;
    RecyclerView recyclerView;

    public PointsStandingResFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //events = getArguments().getStringArray("someArray");
        tabtitle=getArguments().getString("tabtitle");
        tabtitle.replace("\\s+","");
        data = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.points_standing_res_fragment, container, false);


                        recyclerView = (RecyclerView) rootView.findViewById(R.id.points_standing_rv);
                        recyclerView.setHasFixedSize(true);
                        GetPointsStandingData();

                        /*DetailsAdapter adapter = new DetailsAdapter(events, getContext(), listContents,tabtitle);
                        rv.setAdapter(adapter);

                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                        rv.setLayoutManager(llm);*/



        return rootView;
    }

    public void GetPointsStandingData() {
        ParseQuery<ParseObject> EventResults = new ParseQuery<ParseObject>("EventResults");
        EventResults.addDescendingOrder("point2");

        EventResults.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {

                    resultsList = objects;
                    //Toast.makeText(getContext(), "Something Exists/"+objects.size(), Toast.LENGTH_SHORT).show();
                    /*if (resultsList.size() <= initialListLength) {
                        initialListLength = resultsList.size();


                    }*/
                    for (int i = 0; i < resultsList.size(); i++) {
                        data.add(new Helper_BasicDataModel(resultsList.get(i).getString("institution"), resultsList.get(i).getString("points"), resultsList.get(i).getInt("position")));
                    }
                   /* RecyclerView recyclerView = findViewById(R.id.points_standing_rv);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));*/

                    PointsStandingAdapter adapter = new PointsStandingAdapter(data);
                    recyclerView.setAdapter(adapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                } else {
                    Toast.makeText(getContext(), "Nothing To See yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
