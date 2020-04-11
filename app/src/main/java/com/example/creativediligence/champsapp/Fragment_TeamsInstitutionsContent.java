package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Fragment_TeamsInstitutionsContent extends Fragment {
    ArrayList<Helper_BasicDataModel> data;
    String[] events;
    String tabtitle;
    ArrayList<String> listContents;

    public Fragment_TeamsInstitutionsContent() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        events = getArguments().getStringArray("someArray");
        tabtitle=getArguments().getString("tabtitle");
        tabtitle.replace("\\s+","");
        data = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_blank, container, false);


        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        rv.setHasFixedSize(true);

        FragmentAdapter_TeamsInstitutions adapter = new FragmentAdapter_TeamsInstitutions(events, getContext(), listContents,tabtitle);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);



        return rootView;
    }
}
