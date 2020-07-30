package com.example.creativediligence.champsapp;

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

public class InstitutionsCoachesFragment extends Fragment {
    public static ArrayList<String> mData;
    RecyclerView rv;
    Button newCoach;
    TextView infoTv;
    String currentFragment;
    String userRole;

    public InstitutionsCoachesFragment() {
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

        mData = new ArrayList<>();
        currentFragment = getArguments().getString("fragmentName");
        userRole = getArguments().getString("userRole");
        final View rootView = inflater.inflate(R.layout.institutions_coach_fragment_main_layout, container, false);
        rv = rootView.findViewById(R.id.instu_coach_fragment_rv);
        infoTv = rootView.findViewById(R.id.coach_data_info_tv);
        PopulateRecyclerView();

        newCoach = rootView.findViewById(R.id.createTeamButton);
        if (!userRole.equals("Admin")) {
            newCoach.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new Helper_DialogCreator().NewCoachDialog(getContext(), rv, infoTv, currentFragment);
                    //mData = new ArrayList<>();
                    //PopulateRecyclerView(rootView);
                }
            });
        }else{
            newCoach.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }


    public void PopulateRecyclerView() {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionCoach");
        if (!userRole.equals("Admin")) {
            query.whereEqualTo("Institution", ParseUser.getCurrentUser().getUsername());
        }
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("Name"));
                    }

                    infoTv.setVisibility(View.GONE);


                    rv.setHasFixedSize(true);

                    CoachesFragmentAdapterMain adapter = new CoachesFragmentAdapterMain(getContext(), R.layout.home_fragment_layout_custom, mData,currentFragment);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);

                } else {
                    if (e != null) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No Data Exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        /*if (!mData.isEmpty() || mData != null) {

        }*/
    }
}
