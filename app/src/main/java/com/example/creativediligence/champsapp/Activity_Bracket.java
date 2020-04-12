package com.example.creativediligence.champsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Bracket extends AppCompatActivity {
    ExpandableListView brackets;
    List<String> bracketTitle;
    HashMap<String, List<String>> bracketDetail;
    String mSportsTitle;
    boolean isHomepage;

    Toolbar toolbar;
    @Override
    public void onResume() {
        //new PreferenceMethods().setColorChosen(toolbar,BracketActivity.this);
        GetBracketData();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bracket);

        isHomepage=getIntent().getBooleanExtra("isHomepage",false);

        mSportsTitle=getIntent().getStringExtra("sportName");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(isHomepage){
            toolbar.setTitle("Brackets");
        }else {
            toolbar.setTitle("Track & Field Brackets");
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView tv=findViewById(R.id.msg_tv);
        if(isHomepage){
            tv.setVisibility(View.GONE);
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Activity_Bracket.this, Activity_Common_SportsEventOverview.class);
                    intent.putExtra("sportName", mSportsTitle);
                    intent.putExtra("activityName", "Events");
                    Activity_Bracket.this.startActivity(intent);

            }
        });

        GetBracketData();
    }

    public void GetBracketData(){
        final ExpandableListView brackets=findViewById(R.id.brackets_exp_lv);


        final List<String> bracketTitle=new ArrayList<String>();
        final  List<String> positions =new ArrayList<>();
        final HashMap<String, List<String>> bracketDetail=new HashMap<>();
        ParseQuery<ParseObject> bracket =new ParseQuery<ParseObject>("Bracket");
        bracket.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        bracket.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    for (ParseObject ob:objects){

                        String string=ob.getString("bracketname");
                        List<String> str=ob.getList("athlete");
                        List<String> pos =ob.getList("position");
                        bracketTitle.add(string);
                        bracketDetail.put(string,str);
                    }
                    AdapterExpandable_Bracket expandableListAdapter = new AdapterExpandable_Bracket(Activity_Bracket.this, bracketTitle, bracketDetail);
                    brackets.setAdapter(expandableListAdapter);
                }else{
                    TextView warning =findViewById(R.id.warning_TV);
                    warning.setText("Bracket Data non-existent");
                    warning.setVisibility(View.VISIBLE);
                }
            }
        });
    }




}
