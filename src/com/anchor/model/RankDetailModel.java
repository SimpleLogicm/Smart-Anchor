package com.anchor.model;

public class RankDetailModel {

    String r_parameter_value;
    String r_weighttage_value;
    String r_obtained_value;
    String r_calculated_value;


    public RankDetailModel(String r_parameter_value, String r_weighttage_value, String r_obtained_value, String r_calculated_value) {
        this.r_parameter_value = r_parameter_value;
        this.r_weighttage_value = r_weighttage_value;
        this.r_obtained_value = r_obtained_value;
        this.r_calculated_value = r_calculated_value;

    }

    public String getR_parameter_value() {
        return r_parameter_value;
    }

    public void setR_parameter_value(String r_parameter_value) {
        this.r_parameter_value = r_parameter_value;
    }

    public String getR_weighttage_value() {
        return r_weighttage_value;
    }

    public void setR_weighttage_value(String r_weighttage_value) {
        this.r_weighttage_value = r_weighttage_value;
    }

    public String getR_obtained_value() {
        return r_obtained_value;
    }

    public void setR_obtained_value(String r_obtained_value) {
        this.r_obtained_value = r_obtained_value;
    }

    public String getR_calculated_value() {
        return r_calculated_value;
    }

    public void setR_calculated_value(String r_calculated_value) {
        this.r_calculated_value = r_calculated_value;
    }
}
