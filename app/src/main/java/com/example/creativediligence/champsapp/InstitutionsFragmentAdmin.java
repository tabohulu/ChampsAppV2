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

public class InstitutionsFragmentAdmin extends Fragment {
    private String currentFragment;
    private String userRole;
    ArrayList<ParseObject> retrievedData;
    private ArrayList<String> mData;
    private RecyclerView rv;
    private TextView noDataTv;
    private Button newInstitutionButton;


    public InstitutionsFragmentAdmin() {
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
        final View rootView = inflater.inflate(R.layout.fragment_institution_admin, container, false);

        //Initialize xml elements
        rv = rootView.findViewById(R.id.institutions_fragment_rv);
        noDataTv = rootView.findViewById(R.id.no_data_info_tv);
        newInstitutionButton = rootView.findViewById(R.id.new_institution_button);

        //initialize variables
        currentFragment = getArguments().getString("fragmentName");
        userRole = getArguments().getString("userRole");
        mData=new ArrayList<>();
        retrievedData=new ArrayList<>();

        //on-click listener for button
        newInstitutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogCreator().NewInstitutionDialog(getContext(),rv,noDataTv,currentFragment);

            }
        });

    //load data in recycler view
        PopulateRecyclerView();

        return rootView;
    }

    public void PopulateRecyclerView() {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Institution");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        retrievedData.add(ob);
                        mData.add(ob.getString("Name"));
                    }
                    rv.setHasFixedSize(true);
                    noDataTv.setVisibility(View.INVISIBLE);
                    InstitutionsFragmentAdminAdapterMain adapter = new InstitutionsFragmentAdminAdapterMain(getContext(), R.layout.home_fragment_layout_custom, mData,currentFragment);//todo change adapter
                    //adapter.notifyDataSetChanged();
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


    }
}
