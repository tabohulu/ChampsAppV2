package com.example.creativediligence.champsapp;

import android.graphics.drawable.ColorDrawable;

public class CalendarEventsItem {

    private String eventName;
    private String eventDate;
    private String eventPeriod;
    private String eventType;
    private String eventVenue;
    private ColorDrawable eventColor;

    public String getEventVenue() {
        return eventVenue;
    }

    public CalendarEventsItem(String mEventName, String mEventDate, String mEventPeriod, String mEventType, ColorDrawable mEventColor){
        eventName=mEventName;
        eventDate=mEventDate;
        eventPeriod=mEventPeriod;
        eventType=mEventType;
        eventColor=mEventColor;


    }

    public CalendarEventsItem(String mEventName, String mEventDate, String mEventPeriod, String mEventType, String mEventVenue){
        eventName=mEventName;
        eventDate=mEventDate;
        eventPeriod=mEventPeriod;
        eventType=mEventType;
        eventVenue=mEventVenue;


    }

    public ColorDrawable getEventColor() {
        return eventColor;
    }

    public void setEventColor(ColorDrawable eventColor) {
        this.eventColor = eventColor;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventPeriod() {
        return eventPeriod;
    }

    public void setEventPeriod(String eventPeriod) {
        this.eventPeriod = eventPeriod;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
