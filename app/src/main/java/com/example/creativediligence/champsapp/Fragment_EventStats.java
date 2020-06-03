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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Fragment_EventStats extends Fragment {
    RecyclerView rv;
    Adapter_GraphData adapter;
    GraphView mGraph;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.athlete_profile_page2_custom_layout,container,false);

        mGraph = (GraphView)view.findViewById(R.id.graph);
        mGraph.getViewport().setMinX(1);
        mGraph.getViewport().setMaxX(10);
        /*mGraph.getViewport().setMinY(0);
        mGraph.getViewport().setYAxisBoundsManual(true);*/
        mGraph.getViewport().setXAxisBoundsManual(true);
        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6),
                new DataPoint(5, 2),
                new DataPoint(6, 6),
                new DataPoint(7, 2),
                new DataPoint(8, 6),
                new DataPoint(9, 1)
        });*/


        rv=view.findViewById(R.id.times_rv);
        rv.setHasFixedSize(true);

         adapter = new Adapter_GraphData(getContext());
        rv.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        EditGraph();
        return view;
    }

    public void getRVAdapterMethod(int i,boolean bool){
        adapter.ChangeEditState(i,bool);
    }

    public void EditGraph(){
        LineGraphSeries<DataPoint> series=adapter.GetDataPoints();
        mGraph.removeAllSeries();
        mGraph.addSeries(series);
    }
}
