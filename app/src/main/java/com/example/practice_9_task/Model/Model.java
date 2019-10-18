package com.example.practice_9_task.Model;

import java.io.Serializable;
import java.util.UUID;

public class Model implements Serializable {

    private String mTitle;
    private String mDescription;
    private  String mTime;
    private String mDate;
    private  int mstate;
    private  boolean mStatebool;
    private  UUID key;

    public boolean ismStatebool() {
        return mStatebool;
    }

    public void setmStatebool(boolean mStatebool) {
        this.mStatebool = mStatebool;
    }

    public Model() {
        setKey(UUID.randomUUID());
    }

    public UUID getKey() {
        return key;
    }

    public void setKey(UUID uuid) {
        this.key = uuid;
    }
    public int getMstate() {
        return mstate;
    }

    public void setMstate(int mstate) {
        this.mstate = mstate;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }


}
