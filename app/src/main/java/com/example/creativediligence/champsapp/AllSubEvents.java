package com.example.creativediligence.champsapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("AllSubEvents")
public class AllSubEvents extends ParseObject{
    public static final String MAIN_EVENT="main";
    public static final String NAME="name";
    public static final String PARTICIPANTS="participants";

    public String getMainEvent(){
        return getString(MAIN_EVENT);
    }

    public void setMainEvent(String mainEvent){
        put(MAIN_EVENT,mainEvent);
    }
    public String getName(){
        return getString(NAME);
    }

    public void setName(String name){
        put(NAME,name);
    }

    public List<AllAthletes> getParticipants(){
        return getList(PARTICIPANTS);
    }

    public void setParticipants(List<AllAthletes> participants){
        put(PARTICIPANTS,participants);
    }

}
