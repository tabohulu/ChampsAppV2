package com.example.creativediligence.champsapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.List;

@ParseClassName("AllAthletes")
public class AllAthletes extends ParseObject {
    public static final String NAME = "Name";
    public static final String DOB = "DOB";
    public static final String AGE = "Age";
    public static final String SPORT = "Sport";
    public static final String INSTITUTION = "Institution";
    public static final String BIO = "Biography";
    public static final String EVENTS = "Events";

    public String getName() {
        return getString(NAME);
    }

    public void setName(String Name) {
        put(NAME, Name);
    }


    public String getDob() {
        return getString(DOB);
    }

    public void setDob(String Dob) {
        put(DOB, Dob);
    }

    public String getAge() {
        return getString(AGE);
    }

    public void setAge(String age){
        put(AGE,age);
    }

    public String getSport() {
        return getString(SPORT);

    }

    public void setSport(String sport){
        put(SPORT,sport);
    }

    public String getInstitution() {
        return getString(INSTITUTION);

    }

    public void setInstitution(String institution){
        put(INSTITUTION,institution);
    }

    public String getBio() {
        return getString(BIO);
    }

    public void setBio(String bio){
        put(BIO,bio);
    }

    public List<String> getEvents() {
        return getList(EVENTS);
    }

    public void setEvents(List<String> events){
        put(EVENTS,events);
    }


}
