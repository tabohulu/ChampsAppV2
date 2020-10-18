package com.example.creativediligence.champsapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

@ParseClassName("AllEvents")
public class AllEvents extends ParseObject {
    //Keys
    public static final String SPORT_NAME="sportName";
    public static final String SUB_CAT="subCategory";
    public static final String EVENT_NAME="eventName";
    public static final String SUB_EVENT="subEvents";

    //Getters
    public String getSportName(){
        return getString(SPORT_NAME);
    }

    public String getSubCat(){
        return getString(SUB_CAT);
    }
    public String getEventName(){
        return getString(EVENT_NAME);
    }
    public List<AllSubEvents> getSubEvents(){
        return getList(SUB_EVENT);
    }

    //Setters
    public void setSportName(String sportName){
        put(SPORT_NAME,sportName);
    }

    public void setSubCat(String subCat){
        put(SUB_CAT,subCat);
    }

    public void setEventName(String eventName){
        put(EVENT_NAME,eventName);
    }

    public void setSubEvent(List<AllSubEvents> subEvent){
        put(SUB_EVENT,subEvent);
    }
}
