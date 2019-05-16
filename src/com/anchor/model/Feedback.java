package com.anchor.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "feed_back_table")
public class Feedback {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    private String dateString;

    @ColumnInfo(name = "feedback")
    private String feedback;
//number of milliseconds from 1 jan 1970
    @ColumnInfo(name = "time_stamp")
    private long timeStamp;

    public Feedback(String dateString, String feedback, long timeStamp) {
        this.dateString = dateString;
        this.feedback = feedback;
        this.timeStamp = timeStamp;
    }

/*@ColumnInfo(name = "userName")
    private String userName;
    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public long getTimeStamp() { return timeStamp; }

    public void setTimeStamp(long timeStamp) { this.timeStamp = timeStamp; }


    @Override
    public String toString() {
        return "(Date : " + dateString + "),(Feedback : " + feedback +")\n";
    }
}
