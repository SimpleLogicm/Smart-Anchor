package com.anchor.model;

import java.io.Serializable;

public class Spiner_List_Model implements Serializable {

    public boolean isSelected;
    String primary_category;
    String sub_category;
    String product_variant;
    String business_unit;
    String business_category;
    String code;



    public Spiner_List_Model(String primary_category, String sub_category, String product_variant, String business_unit, String business_category,String code) {
        this.primary_category = primary_category;
        this.sub_category = sub_category;
        this.product_variant = product_variant;
        this.business_unit = business_unit;
        this.business_category = business_category;
        this.code = code;

    }

    public Spiner_List_Model() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPrimary_category() {
        return primary_category;
    }

    public void setPrimary_category(String primary_category) {
        this.primary_category = primary_category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getProduct_variant() {
        return product_variant;
    }

    public void setProduct_variant(String product_variant) {
        this.product_variant = product_variant;
    }

    public String getBusiness_unit() {
        return business_unit;
    }

    public void setBusiness_unit(String business_unit) {
        this.business_unit = business_unit;
    }

    public String getBusiness_category() {
        return business_category;
    }

    public void setBusiness_category(String business_category) {
        this.business_category = business_category;
    }
}
