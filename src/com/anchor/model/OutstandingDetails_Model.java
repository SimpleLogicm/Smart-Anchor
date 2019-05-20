package com.anchor.model;

import java.io.Serializable;

public class OutstandingDetails_Model implements Serializable {

    private String Reference_No;
    private String invoice_no;
    private String Date;
    private String Type;
    private String Product_Category;
    private String Value;
    private String Due_Amount;
    private String Due_Date;
    private String Total_No_Of_Days;

    public OutstandingDetails_Model(String Reference_No, String Date, String Type, String Product_Category, String Value, String Due_Amount, String Due_Date, String Total_No_Of_Days, String invoice_no) {
        this.Reference_No = Reference_No;
        this.Date = Date;
        this.Type = Type;
        this.Product_Category = Product_Category;
        this.Value = Value;
        this.Due_Amount = Due_Amount;
        this.Due_Date = Due_Date;
        this.Total_No_Of_Days = Total_No_Of_Days;
        this.invoice_no = invoice_no;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getReference_No() {
        return Reference_No;
    }

    public void setReference_No(String reference_No) {
        Reference_No = reference_No;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getProduct_Category() {
        return Product_Category;
    }

    public void setProduct_Category(String product_Category) {
        Product_Category = product_Category;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getDue_Amount() {
        return Due_Amount;
    }

    public void setDue_Amount(String due_Amount) {
        Due_Amount = due_Amount;
    }

    public String getDue_Date() {
        return Due_Date;
    }

    public void setDue_Date(String due_Date) {
        Due_Date = due_Date;
    }

    public String getTotal_No_Of_Days() {
        return Total_No_Of_Days;
    }

    public void setTotal_No_Of_Days(String total_No_Of_Days) {
        Total_No_Of_Days = total_No_Of_Days;
    }
}
