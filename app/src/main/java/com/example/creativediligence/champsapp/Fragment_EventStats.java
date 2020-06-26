package com.example.creativediligence.champsapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class Fragment_EventStats extends Fragment {
    RecyclerView rv;
    Adapter_GraphData adapter;
    GraphView mGraph;
    TextView pbTextView;
    ArrayList<String> mEditTextTimes;
    ArrayList<String> mEditTextDates;
    int profileState=0;
    boolean profileStateBool=false;
    ImageView colapseTimesImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.athlete_profile_page2_custom_layout,container,false);

        mGraph = (GraphView)view.findViewById(R.id.graph);
        mGraph.getViewport().setMinX(0);
        mGraph.getViewport().setMaxX(9);
        mGraph.getViewport().setXAxisBoundsManual(true);


        pbTextView=view.findViewById(R.id.athlete_personal_best);
        colapseTimesImage=view.findViewById(R.id.collapse_times_image);
        colapseTimesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rv.getVisibility()==View.VISIBLE){
                    rv.setVisibility(View.GONE);
                    colapseTimesImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_arr_down));
                }else{
                    rv.setVisibility(View.VISIBLE);
                    colapseTimesImage.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_arr_up));
                }

            }
        });
        if(savedInstanceState!=null){
            Toast.makeText(getContext(), "Instance Exists", Toast.LENGTH_SHORT).show();
        }else {
            mEditTextTimes = getArguments().getStringArrayList("times");
            mEditTextDates=getArguments().getStringArrayList("dates");
            //Log.d("times-es", mEditTextDates.get(0));
        }
       /*mGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) ;
                }
            }
        });*/

        rv=view.findViewById(R.id.times_rv);
        rv.setHasFixedSize(true);

        if(getContext() instanceof Activity_AthleteProfile2){
            profileState= ((Activity_AthleteProfile2) getContext()).editprofileState;
            profileStateBool=((Activity_AthleteProfile2) getContext()).profileEditState;
        }

         adapter = new Adapter_GraphData(getContext(),mEditTextTimes,profileState,profileStateBool,pbTextView,mEditTextDates);
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        EditGraph();
        return view;
    }

    public void getRVAdapterMethod(int i,boolean bool){
        adapter.ChangeEditState(i,bool);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(getContext(), "stateSaved", Toast.LENGTH_SHORT).show();
        outState.putStringArrayList("times",mEditTextTimes);
    }

    public void EditGraph(){
        LineGraphSeries<DataPoint> series=adapter.GetDataPoints();
        mGraph.removeAllSeries();
        mGraph.addSeries(series);
    }

    public void UploadData(final String eventName){
        ParseObject eventsObject = new ParseObject("AthleteEventsData");
        eventsObject.put("createdBy", ParseUser.getCurrentUser().getUsername());
        eventsObject.put("eventName",eventName);
        eventsObject.put("timeInputs",adapter.GetEditTextInputs());
        eventsObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(getContext(), eventName+" Data Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public String GetTimes(){
        return adapter.GetEditTextInputs();
    }

    public String GetDates(){
        return adapter.GetEditTextDateInputs();
    }


}
