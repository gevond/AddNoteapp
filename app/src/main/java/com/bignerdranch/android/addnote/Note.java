package com.bignerdranch.android.addnote;

import android.graphics.Color;

import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 11/30/2017.
 */

public class Note {

    private UUID mId;
    private String mTitle;
    private String mText;
    private Date mDate;
    private Date mNotDateNotification;
    private boolean mAlarm;
    private String mColor;

    public Note(){
        mTitle = "title";
        mText = "text";
        mId = UUID.randomUUID();
        mDate = new Date();
        mNotDateNotification = new Date(mDate.getTime() + (1000 * 60 * 60 * 24));
        mAlarm = false;
        mColor = "#99173C";
    }
    public Note(String title,String note){
        this.mTitle = title;
        this.mText = note;
        mId = UUID.randomUUID();
        mDate = new Date();
        mNotDateNotification = new Date(mDate.getTime() + (1000*60*60*24));
        mAlarm = false;
        mColor = "#99173C";
    }
    public Note(UUID id){
        this.mId = id;
    }

    public Date getDate() {
        return mDate;
    }

    public String getNote() {
        return mText;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setNote(String mNote) {
        this.mText = mNote;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public Date getNotDateNotification() {
        return mNotDateNotification;
    }

    public void setNotDateNotification(Date mNotDateNotification) {
        this.mNotDateNotification = mNotDateNotification;
    }

    public boolean isAlarm() {
        return mAlarm;
    }

    public void setAlarm(boolean mAlarm) {
        this.mAlarm = mAlarm;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }


}
