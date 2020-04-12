package com.example.creativediligence.champsapp;

public class Helper_AthleteCoachModel {
    String mName;
    int mAge;
    Double mPb;
    String mInstitution;



    public Helper_AthleteCoachModel(String name, int age, Double pB){
        mName=name;
        mAge=age;
        mPb=pB;
    }

    public String getmInstitution() {
        return mInstitution;
    }

    public Helper_AthleteCoachModel(String name, int age, Double pB, String institution){
        mName=name;
        mAge=age;
        mPb=pB;
        mInstitution=institution;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public Double getmPb() {
        return mPb;
    }

    public void setmPb(Double mPb) {
        this.mPb = mPb;
    }
}
