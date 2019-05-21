package com.anchor.model;

import java.io.Serializable;

public class Scheme_Model implements Serializable {

    private String Scheme_Name;
    private String Current_Volume;
    private String Current_Eligibility;
    private String Gift_Detail;
    private String Gift_Value;
    private String Short_Volumn_For_Next_Slab;
    private String Next_Scheme_Slab;
    private String Next_Scheme_Value;

    public Scheme_Model(String Scheme_Name, String Current_Volume, String Current_Eligibility, String Gift_Detail, String Gift_Value, String Short_Volumn_For_Next_Slab, String Next_Scheme_Slab, String Next_Scheme_Value) {
        this.Scheme_Name = Scheme_Name;
        this.Current_Volume = Current_Volume;
        this.Current_Eligibility = Current_Eligibility;
        this.Gift_Detail = Gift_Detail;
        this.Gift_Value = Gift_Value;
        this.Short_Volumn_For_Next_Slab = Short_Volumn_For_Next_Slab;
        this.Next_Scheme_Slab = Next_Scheme_Slab;
        this.Next_Scheme_Value = Next_Scheme_Value;
    }


    public String getScheme_Name() {
        return Scheme_Name;
    }

    public void setScheme_Name(String scheme_Name) {
        Scheme_Name = scheme_Name;
    }

    public String getCurrent_Volume() {
        return Current_Volume;
    }

    public void setCurrent_Volume(String current_Volume) {
        Current_Volume = current_Volume;
    }

    public String getCurrent_Eligibility() {
        return Current_Eligibility;
    }

    public void setCurrent_Eligibility(String current_Eligibility) {
        Current_Eligibility = current_Eligibility;
    }

    public String getGift_Detail() {
        return Gift_Detail;
    }

    public void setGift_Detail(String gift_Detail) {
        Gift_Detail = gift_Detail;
    }

    public String getGift_Value() {
        return Gift_Value;
    }

    public void setGift_Value(String gift_Value) {
        Gift_Value = gift_Value;
    }

    public String getShort_Volumn_For_Next_Slab() {
        return Short_Volumn_For_Next_Slab;
    }

    public void setShort_Volumn_For_Next_Slab(String short_Volumn_For_Next_Slab) {
        Short_Volumn_For_Next_Slab = short_Volumn_For_Next_Slab;
    }

    public String getNext_Scheme_Slab() {
        return Next_Scheme_Slab;
    }

    public void setNext_Scheme_Slab(String next_Scheme_Slab) {
        Next_Scheme_Slab = next_Scheme_Slab;
    }

    public String getNext_Scheme_Value() {
        return Next_Scheme_Value;
    }

    public void setNext_Scheme_Value(String next_Scheme_Value) {
        Next_Scheme_Value = next_Scheme_Value;
    }
}
