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
    String MRP;
    String RP;
    String SQ;
    String MQ;
    String STOCK;
    String QUANTITY;
    String Name;



    public Spiner_List_Model(String primary_category, String sub_category, String product_variant, String business_unit, String business_category,String code,String MRP,String RP,String SQ,String MQ,String STOCK,String QUANTITY,String Name) {
        this.primary_category = primary_category;
        this.sub_category = sub_category;
        this.product_variant = product_variant;
        this.business_unit = business_unit;
        this.business_category = business_category;
        this.code = code;
        this.MRP = MRP;
        this.RP = RP;
        this.SQ = SQ;
        this.MQ = MQ;
        this.STOCK = STOCK;
        this.QUANTITY = QUANTITY;
        this.Name = Name;


    }

    public Spiner_List_Model() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getRP() {
        return RP;
    }

    public void setRP(String RP) {
        this.RP = RP;
    }

    public String getSQ() {
        return SQ;
    }

    public void setSQ(String SQ) {
        this.SQ = SQ;
    }

    public String getMQ() {
        return MQ;
    }

    public void setMQ(String MQ) {
        this.MQ = MQ;
    }

    public String getSTOCK() {
        return STOCK;
    }

    public void setSTOCK(String STOCK) {
        this.STOCK = STOCK;
    }

    public String getQUANTITY() {
        return QUANTITY;
    }

    public void setQUANTITY(String QUANTITY) {
        this.QUANTITY = QUANTITY;
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
