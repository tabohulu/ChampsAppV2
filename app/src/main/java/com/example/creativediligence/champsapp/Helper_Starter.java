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
import android.os.Environment;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Helper_Starter extends Application {
final static String TAG="Starter";
  @Override
  public void onCreate() {
    super.onCreate();

    ParseObject.registerSubclass(Helper_ChatMsg.class);
    ParseObject.registerSubclass(AllAthletes.class);
    ParseObject.registerSubclass(AllSubEvents.class);
    ParseObject.registerSubclass(AllEvents.class);
    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
   /* Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("f917188e6ef56273821b797ad09eae91a35d8a97")
            .clientKey("ad0f86b417246751414e2235427c36300f675b97")
            .server("http://52.14.179.101:80/parse/")
            .build()
    );*/

    Parse.initialize(new Parse.Configuration.Builder(this)
            .applicationId("3fc7tsUXeTlmvk8gArp63Blsemf0aHkPwQLPCnMZ")
            .clientKey("uA6wLlP2j2NAOravrRHykPHxP4TJ9thblQYpnP8m")
            .server("https://parseapi.back4app.com")
            .build()
    );



    //============================
    //UploadVideo();
    /*List<String> subEvents= Arrays.asList("400m","200m","100m");
    //List<String> subEvents= Arrays.asList("400m:Heat1","400m:Heat2","400m:Final");
    for (String subevent:subEvents){
      //CreateList2(subevent);
      CreateList(subevent,"Track Events");
    }*/


   /*ParseObject object = new ParseObject("InstitutionAthlete");
    object.put("created_by", ParseUser.getCurrentUser().getUsername());
    object.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException ex) {
        if (ex == null) {
          Log.i("Parse Result", "Successful!");
        } else {
          Log.i("Parse Result", "Failed" + ex.toString());
        }
      }
    });*/
    /*ParseObject object = new ParseObject("ExampleObject");
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
    });*/

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
  public void UploadVideo(){
    String myfolder = Environment.getExternalStorageDirectory() + "/NicheSports";
    File file =new File(myfolder,"add1.mp4");
    int size = (int) file.length();
    byte[] bytes = new byte[size];
    try {
      BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
      buf.read(bytes, 0, bytes.length);
      buf.close();
      ParseFile parseFile= new ParseFile("add1.mp4",bytes);
      ParseObject object= new ParseObject("Videos");
      object.put("videofile",parseFile);
      object.saveInBackground(new SaveCallback() {
        @Override
        public void done(ParseException e) {
          if(e==null){
            Log.d(TAG,"All Good");

          }else{
            Log.d(TAG,e.getMessage());
          }
        }
      });
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    ParseObject vids=new ParseObject("Videos");

  }

  public void CreateList(final String event, final String subCat){
    final List<AllSubEvents> temp = new ArrayList<>();
    ParseQuery<AllSubEvents> query=ParseQuery.getQuery(AllSubEvents.class);
    query.whereContains("main",event);
    query.findInBackground(new FindCallback<AllSubEvents>() {
      @Override
      public void done(List<AllSubEvents> objects, ParseException e) {
        if(e==null && objects.size()>0){

          /*ParseObject events=new ParseObject("AllEvents");
          events.put("sportName","Track and Field");
          events.put("subCategory",subCat);
          events.put("eventName",event);
          events.put("subEvents", objects);*/
          AllEvents allEvents=new AllEvents();
          allEvents.setSportName("Track and Field");allEvents.setEventName(event);
          allEvents.setSubCat(subCat);
          allEvents.setEventName(event);
          allEvents.setSubEvent(objects);

          allEvents.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if(e==null){
                Log.i("ParseResult", "Successful!");
              }else {
                Log.i("ParseResult", "Failed" + e.getMessage());
              }

            }
          });

        }else {
          if(e!=null){
            Log.d("Parse Result",e.getMessage());
          }else {
            Log.d("Parse Result","Something went wrong");
          }
        }

      }
    });

  }

  public void CreateList2(final String event){
    final List<AllAthletes> temp = new ArrayList<AllAthletes>();
    ParseQuery<AllAthletes> query=ParseQuery.getQuery(AllAthletes.class);
    query.whereContains("Sport","Track and Field");
    query.findInBackground(new FindCallback<AllAthletes>() {
      @Override
      public void done(List<AllAthletes> objects, ParseException e) {
        if(e==null && objects.size()>0){
          for (AllAthletes o:objects){
            List<String> events=o.getList("Events");
            Log.d("Parse Result",String.valueOf(events.contains(event)));
            if(events.contains(event.split(":")[0])){
              temp.add(o);
              Log.d("Parse Result",o.getObjectId());

            }

          }

          AllSubEvents subEvents=new AllSubEvents();
          subEvents.setMainEvent(event.split(":")[0]);
          subEvents.setName(event);
          subEvents.setParticipants(temp);
          subEvents.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
              if(e==null){
                Log.i("Parse Result", "Successful!");
              }else {
                Log.i("Parse Result", "Failed" + e.getMessage());
              }
            }
          });





        }else {
          if(e!=null){
            Log.d("Parse Result",e.getMessage());
          }else {
            Log.d("Parse Result","Something went wrong");
          }
        }

      }
    });

  }
}

