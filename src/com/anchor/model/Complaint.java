package com.anchor.model;

public class Complaint {

    private int id;


    private String dateString;


    private String complaint;
    //number of milliseconds from 1 jan 1970

    private long timeStamp;

    public Complaint(String dateString, String complaint, long timeStamp) {
        this.dateString = dateString;
        this.complaint = complaint;
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

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public long getTimeStamp() { return timeStamp; }

    public void setTimeStamp(long timeStamp) { this.timeStamp = timeStamp; }


    @Override
    public String toString() {
        return "(Date : " + dateString + "),(Complaint : " + complaint +")\n";
    }
}
