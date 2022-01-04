package com.anchor.model;

import java.io.Serializable;

public class Promotional_Model implements Serializable {

    private String email;
    private String event_id;
    private String meet_in;
    private String meet_out;
    private String description;
    private String description1;
    private String description2;
    private String latitude;
    private String longitude;
    private String address;
    private String image_url;

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getMeet_in() {
        return meet_in;
    }

    public void setMeet_in(String meet_in) {
        this.meet_in = meet_in;
    }

    public String getMeet_out() {
        return meet_out;
    }

    public void setMeet_out(String meet_out) {
        this.meet_out = meet_out;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}