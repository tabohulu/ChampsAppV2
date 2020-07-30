package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class TeamsFragment extends Fragment {


    public TeamsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ArrayList<String> mData = new ArrayList<>(Arrays.asList("Team1", "Team2", "Team3", "Team4"));
        ArrayList<String> mData2 = new ArrayList<>(Arrays.asList("PTeam1", "PTeam2", "PTeam3", "PTeam4"));
        final View rootView = inflater.inflate(R.layout.teams_fragment_main_layout, container, false);


        RecyclerView rv = rootView.findViewById(R.id.teams_fragment_rv);
        rv.setHasFixedSize(true);

        TeamsFragmentAdapter adapter = new TeamsFragmentAdapter(getContext(), R.layout.home_fragment_layout_custom, mData);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

//---------------------------------------------------------//
        RecyclerView rv2 = rootView.findViewById(R.id.teams_fragment_rv2);
        rv2.setHasFixedSize(true);

        TeamsFragmentAdapter adapter2 = new TeamsFragmentAdapter(getContext(), R.layout.home_fragment_layout_custom, mData2);
        rv2.setAdapter(adapter2);

        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        rv2.setLayoutManager(llm2);


        return rootView;
    }
}
