/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.example.creativediligence.champsapp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class Helper_Starter extends Application {
final static String TAG="Starter";
  @Override
  public void onCreate() {
    super.onCreate();

    ParseObject.registerSubclass(Helper_ChatMsg.class);
    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("f917188e6ef56273821b797ad09eae91a35d8a97")
            .clientKey("ad0f86b417246751414e2235427c36300f675b97")
            .server("http://52.14.179.101:80/parse/")
            .build()
    );

    //============================
    ParseObject object = new ParseObject("ExampleObject");
    object.put("myNumber", "123");
    object.put("myString", "rob");

    object.saveInBackground(new SaveCallback () {
      @Override
      public void done(ParseException ex) {
        if (ex == null) {
          Log.i("Parse Result", "Successful!");
        } else {
          Log.i("Parse Result", "Failed" + ex.toString());
        }
      }
    });

/*    final ParseQuery<ParseObject> coach = ParseQuery.getQuery("Coaches");

    coach.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e==null && objects.size()>0){
          for (ParseObject po:objects){
            Log.d(TAG,po.getString("name"));
            ParseObject school= (ParseObject)po.get("parent");
            school.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
              @Override
              public void done(ParseObject object, ParseException e) {
                Log.d(TAG, object.getString("name"));
              }
            });

          }
        }
      }
    });*/
       //Athlete Info
   /* String[] institution ={"School1", "School2", "School3", "School4", "School5"};
    String[] coach ={"CoachA", "CoachB", "CoachC", "CoachD", "CoachE","CoachF"};

    ParseObject institutions=new ParseObject("Institutions");
    institutions.put("name",institution[0]);
    for (int i=0;i<2;i++) {
      ParseObject coaches = new ParseObject("Coaches");
      coaches.put("name", coach[i]);
      coaches.put("parent",institutions);
      coaches.saveInBackground();
    }*/
       /* String[] names = {"Athlete1", "Athlete2", "Athlete3", "Athlete4", "Athlete5"};
        String[] institution ={"School1", "School2", "School3", "School4", "School5"};
        String[] coaches ={"CoachA", "CoachB", "CoachC", "CoachD", "CoachE"};
        String[] event = {"100m", "100m", "100m", "200m", "400m"};
        Integer[] age = {28, 25, 23, 62, 58};
        Double[] pb = {10.92, 15.21, 12.33, 16.12, 10.05};

        //Even Info
        String[] eventsTitles = {"100m", "200m", "400m"};
        String[] raceTitles100m = {"Heats1", "Heats2", "Heats3", "Final"};
        String[] raceTitles200m = {"Heats1", "Heats2", "Heats3", "Heats4"};
        String[] raceTitles400m = {"Heats1", "Heats2", "Final"};

        HashMap<String, String[]> events = new HashMap<String, String[]>();
        ArrayList<String[]> races = new ArrayList<>();
        races.add(raceTitles100m);
        races.add(raceTitles200m);
        races.add(raceTitles400m);

        for (int i = 0; i < eventsTitles.length; i++) {
            events.put(eventsTitles[i], races.get(i));
        }*/

//Add General Event Data
     /*   for (int i = 0; i < eventsTitles.length; i++) {
            ParseObject generalEvents = new ParseObject("GeneralEvents");
            generalEvents.put("eventname", eventsTitles[i]);
            generalEvents.put("numberofevents", races.get(i).length);
            generalEvents.addAll("races", Arrays.asList(races.get(i)));
            generalEvents.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Save in Background", "Success");
                    } else {
                        Log.e("Save in Background", "Error: " + e.getMessage());
                    }
                }
            });

        }*/

        /*for (int i = 0; i < names.length; i++) {
          ParseObject team = new ParseObject("Institution");
          team.put("name",institution[i]);
            ParseObject athletes = new ParseObject("Athletes");
            athletes.put("name", names[i]);
            athletes.put("age", age[i]);
            athletes.put("event", event[i]);
            athletes.put("pb", pb[i]);
            athletes.put("parent",team);

          ParseObject coach = new ParseObject("Coaches");
          coach.put("name", coaches[i]);
          coach.put("parent",team);
            athletes.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Log.i("Save in Background", "Success");
                    } else {
                        Log.i("Save in Background", "Error: " + e.getMessage());
                    }
                }
            });

          coach.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if (e == null) {
                Log.i("Save in Background", "Success");
              } else {
                Log.i("Save in Background", "Error: " + e.getMessage());
              }
            }
          });

        }*/


      //=======================


    //ParseUser.enableAutomaticUser();

    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    defaultACL.setPublicWriteAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

  }
}

