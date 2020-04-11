package com.example.creativediligence.champsapp;

public class Helper_BasicDataModel {

    private String title;
    private String author;
    private int image;
    private String athleticEvent;

    public Helper_BasicDataModel(String title, String author, int image_, String aEvent) {
        this.title = title;
        this.author = author;
        this.image =image_;
        this.athleticEvent=aEvent;
    }

    public Helper_BasicDataModel(String title, String author, int image_) {
        this.title = title;
        this.author = author;
        this.image =image_;
    }

    public Helper_BasicDataModel(String title, int image_) {
        this.title = title;
        this.image =image_;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAthleticEvent() {
        return athleticEvent;
    }

    public void setAthleticEvent(String athleticEvent) {
        this.athleticEvent = athleticEvent;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image=image;
    }
}
