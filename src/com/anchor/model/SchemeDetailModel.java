package com.anchor.model;

import java.io.Serializable;

public class SchemeDetailModel implements Serializable {

    private String  Invoice_No;
    private String Invoice_Date;
    private String Order_No;
    private String Order_Date;
    private String Order_Type;
    private String Item_Code;
    private String Item_Name;
    private String Quantity;
    private String Line_Amount;
    private String Tax_Amount;
    private String Total_Amount;
    private String Eligible_Volumn;
    private String CN1;
    private String CN2;
    private String CN3;
    private String CN4;
    private String Gift_Amount;
    private String Scheme_Name;

    public SchemeDetailModel(String Invoice_No, String Invoice_Date, String Order_No, String Order_Date, String Order_Type, String Item_Code, String Item_Name, String Quantity, String Line_Amount, String Tax_Amount, String Total_Amount, String Eligible_Volumn, String CN1, String CN2, String CN3, String CN4, String Gift_Amount, String Scheme_Name) {
        this.Invoice_No = Invoice_No;
        this.Invoice_Date = Invoice_Date;
        this.Order_No = Order_No;
        this.Order_Date = Order_Date;
        this.Order_Type = Order_Type;
        this.Item_Code = Item_Code;
        this.Item_Name = Item_Name;
        this.Quantity = Quantity;
        this.Line_Amount = Line_Amount;
        this.Tax_Amount = Tax_Amount;
        this.Total_Amount = Total_Amount;
        this.Eligible_Volumn = Eligible_Volumn;
        this.CN1 = CN1;
        this.CN2 = CN2;
        this.CN3 = CN3;
        this.CN4 = CN4;
        this.Gift_Amount = Gift_Amount;
        this.Scheme_Name = Scheme_Name;
    }

    public String getScheme_Name() {
        return Scheme_Name;
    }

    public void setScheme_Name(String scheme_Name) {
        Scheme_Name = scheme_Name;
    }

    public String getInvoice_No() {
        return Invoice_No;
    }

    public void setInvoice_No(String invoice_No) {
        Invoice_No = invoice_No;
    }

    public String getInvoice_Date() {
        return Invoice_Date;
    }

    public void setInvoice_Date(String invoice_Date) {
        Invoice_Date = invoice_Date;
    }

    public String getOrder_No() {
        return Order_No;
    }

    public void setOrder_No(String order_No) {
        Order_No = order_No;
    }

    public String getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(String order_Date) {
        Order_Date = order_Date;
    }

    public String getOrder_Type() {
        return Order_Type;
    }

    public void setOrder_Type(String order_Type) {
        Order_Type = order_Type;
    }

    public String getItem_Code() {
        return Item_Code;
    }

    public void setItem_Code(String item_Code) {
        Item_Code = item_Code;
    }

    public String getItem_Name() {
        return Item_Name;
    }

    public void setItem_Name(String item_Name) {
        Item_Name = item_Name;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getLine_Amount() {
        return Line_Amount;
    }

    public void setLine_Amount(String line_Amount) {
        Line_Amount = line_Amount;
    }

    public String getTax_Amount() {
        return Tax_Amount;
    }

    public void setTax_Amount(String tax_Amount) {
        Tax_Amount = tax_Amount;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }

    public String getEligible_Volumn() {
        return Eligible_Volumn;
    }

    public void setEligible_Volumn(String eligible_Volumn) {
        Eligible_Volumn = eligible_Volumn;
    }

    public String getCN1() {
        return CN1;
    }

    public void setCN1(String CN1) {
        this.CN1 = CN1;
    }

    public String getCN2() {
        return CN2;
    }

    public void setCN2(String CN2) {
        this.CN2 = CN2;
    }

    public String getCN3() {
        return CN3;
    }

    public void setCN3(String CN3) {
        this.CN3 = CN3;
    }

    public String getCN4() {
        return CN4;
    }

    public void setCN4(String CN4) {
        this.CN4 = CN4;
    }

    public String getGift_Amount() {
        return Gift_Amount;
    }

    public void setGift_Amount(String gift_Amount) {
        Gift_Amount = gift_Amount;
    }
}
