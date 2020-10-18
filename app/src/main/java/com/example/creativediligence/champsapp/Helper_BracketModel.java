package com.example.creativediligence.champsapp;

public class Helper_BracketModel {
    String mAthleteName;
    String mAthletePosition;



    public Helper_BracketModel(String athleteName,String athletePosition){
        mAthleteName=athleteName;
        mAthletePosition=athletePosition;

    }

    public String getmAthleteName() {
        return mAthleteName;
    }

    public void setmAthleteName(String mAthleteName) {
        this.mAthleteName = mAthleteName;
    }

    public String getmAthletePosition() {
        return mAthletePosition;
    }

    public void setmAthletePosition(String mAthletePosition) {
        this.mAthletePosition = mAthletePosition;
    }
}
