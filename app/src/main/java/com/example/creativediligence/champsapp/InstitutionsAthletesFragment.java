package com.example.creativediligence.champsapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.creativediligence.champsapp.Common.FileExploreDialog;
import com.example.creativediligence.champsapp.Common.InstitutionAthleteRecylerviewAdapter;
import com.example.creativediligence.champsapp.Common.URIPathUtility;
import com.example.creativediligence.champsapp.MembersArea.Coaches.CoachesFragmentAdapterMain;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import static android.content.Context.MODE_PRIVATE;

public class InstitutionsAthletesFragment extends Fragment {
    static final int PICKFILE_RESULT_CODE = 1;
    public static ArrayList<String> mData;
    RecyclerView rv;
    SharedPreferences prefs;
    TextView noDataTv;
    Button newAthleteButton;
    TextView selectedFileTV;
    File excelFile;
    Workbook workbook;
    String currentFragment;
    String userRole;

    public InstitutionsAthletesFragment() {
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
        final View rootView = inflater.inflate(R.layout.institutions_athletes_fragment_main_layout, container, false);
        mData = new ArrayList<>();
        noDataTv = rootView.findViewById(R.id.no_data_info_tv);
        noDataTv.setVisibility(View.INVISIBLE);
        currentFragment = getArguments().getString("fragmentName");
        userRole = getArguments().getString("userRole");

        rv = (RecyclerView) rootView.findViewById(R.id.athletes_fragment_rv);
        workbook = null;
        PopulateRecyclerView();


        newAthleteButton = rootView.findViewById(R.id.new_athlete_button);


        newAthleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogCreator().NewAthleteDialog(getContext(), rv, noDataTv, currentFragment);

            }
        });

        return rootView;
    }

    public void PopulateRecyclerView() {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("InstitutionAthlete");

        query.whereEqualTo("Institution", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("Name"));
                    }
                    rv.setHasFixedSize(true);
                    noDataTv.setVisibility(View.INVISIBLE);
                    InstitutionAthleteRecylerviewAdapter adapter = new InstitutionAthleteRecylerviewAdapter(getContext(), R.layout.home_fragment_layout_custom, mData,currentFragment);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);
                } else {
                    if (e != null) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "No Data Exists", Toast.LENGTH_SHORT).show();
                        noDataTv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        /*if (!mData.isEmpty() || mData != null) {

        }*/
    }


}
