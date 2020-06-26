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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class GroupsFragment extends Fragment {
    public static ArrayList<String> mData;
    RecyclerView rv;
    SharedPreferences prefs;

    public GroupsFragment() {
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
        final View rootView = inflater.inflate(R.layout.groups_fragment_main_layout, container, false);
        mData = new ArrayList<>();
        PopulateRecyclerView(rootView);


        Button newGroupButton = rootView.findViewById(R.id.new_group_button);
        newGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DialogCreator().NewGroupDialog(getContext());
                mData = new ArrayList<>();
                PopulateRecyclerView(rootView);
            }
        });

        return rootView;
    }

    public void PopulateRecyclerView(final View rootview) {

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Groups");
        query.whereEqualTo("userName", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        mData.add(ob.getString("groupName"));
                    }
                    rv = (RecyclerView) rootview.findViewById(R.id.groups_fragment_rv);
                    rv.setHasFixedSize(true);

                    GroupFragmentAdapterMain adapter = new GroupFragmentAdapterMain(getContext(), R.layout.home_fragment_layout_custom, mData);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    rv.setLayoutManager(llm);
                } else {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*if (!mData.isEmpty() || mData != null) {

        }*/
    }
}
