package com.example.creativediligence.champsapp;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ParseQueries {
    //Members Area Group Related Queries
    public void GetGroupData(final Context mContext, String groupName, final RecyclerView rv) {
        final ArrayList<String> posts = new ArrayList<>();
        final ArrayList<String> dates = new ArrayList<>();
        final ArrayList<String> postCreator = new ArrayList<>();
        ParseQuery<ParseObject> groupData = new ParseQuery<ParseObject>(mContext.getResources().getString(R.string.GroupClassName));
        groupData.whereEqualTo(mContext.getResources().getString(R.string.GroupClassElem_groupName), groupName);
        groupData.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.size() > 0) {
                    for (ParseObject ob : objects) {
                        posts.add(ob.getString(mContext.getResources().getString(R.string.GroupClassElem_postContent)));
                        dates.add(ob.getString(mContext.getResources().getString(R.string.GroupClassElem_postDateTime)));
                        postCreator.add(ob.getString(mContext.getResources().getString(R.string.GroupClassElem_postCreator)));
                    }

                    //rv = findViewById(R.id.group_content_recyclerview);

                    rv.setHasFixedSize(true);
                    GroupContentAdapter adapter = new GroupContentAdapter(mContext, R.layout.group_content_custom_layout, posts, dates, postCreator);
                    rv.setAdapter(adapter);

                    LinearLayoutManager llm = new LinearLayoutManager(mContext);
                    rv.setLayoutManager(llm);
                } else {
                    Toast.makeText(mContext, "No Posts Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public ParseObject GetUserProfileData(Context mContext){
        ParseObject data=null;
        ParseQuery<ParseObject> profileData=new ParseQuery<ParseObject>(mContext.getResources().getString(R.string.UserProfilesClassName));
        profileData.whereEqualTo("currentUserName", ParseUser.getCurrentUser().getUsername());
        profileData.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){

                    for (ParseObject ob : objects) {

                        ob.saveInBackground();

                    }

                }else {

                }
            }
        });
        return data;
    }

    public void SetUserProfileData(final Context mContext, final ArrayList<String> prefName, final ArrayList<String> prefValues, final String profileClassName){

        /*ParseObject userProfiles =new ParseObject(profileClassName);
        for (int i=0;i<prefName.size();i++) {
            userProfiles.put(prefName.get(i),prefValues.get(i));
        }
        userProfiles.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){

                }else{
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        ParseQuery<ParseObject> allProfiles= new ParseQuery<ParseObject>(profileClassName);
        allProfiles.whereEqualTo("currentUserName",ParseUser.getCurrentUser().getUsername());
        allProfiles.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null && objects.size()>0){
                    Toast.makeText(mContext, "Updating Profile", Toast.LENGTH_SHORT).show();
                    for (ParseObject ob : objects) {
                        for (int i=0;i<prefName.size();i++) {
                            ob.put(prefName.get(i),prefValues.get(i));
                        }
                        ob.saveInBackground();

                    }

                }else {
                    ParseObject userProfiles =new ParseObject(profileClassName);
                    for (int i=0;i<prefName.size();i++) {
                        userProfiles.put(prefName.get(i),prefValues.get(i));
                    }
                    userProfiles.saveInBackground();
                }
            }
        });


    }
}
