package com.anchor.model;

import java.io.Serializable;

public class Invoice_Download_Model implements Serializable {


    private String INVOICE_NUM;
    private String TRX_DATE;
    private String ORDER_NUMBER;
    private String ORDERED_DATE;
    private String PAYMENT_TERM;
    private String PAYMENT_DATE;
    private String CAT_TYPE;
    private String CUST_NUMBER;
    private String CUST_NAME;
    private String ITEM_CODE;
    private String DESCRIPTION;
    private String HSN;
    private String TAX_RATE;
    private String ITEM_QTY;
    private String UNIT_PRICE;
    private String AMOUNT;
    private String CGST_RATE;
    private String CGST_VALUE;
    private String SGST_RATE;
    private String SGST_VALUE;
    private String IGST_RATE;
    private String IGST_VALUE;
    private String AR_TOTAL;
    private String PICK_SLIP_NO;
    private String update_at;

    public Invoice_Download_Model(String INVOICE_NUM, String TRX_DATE, String ORDER_NUMBER, String ORDERED_DATE, String PAYMENT_TERM, String PAYMENT_DATE, String CAT_TYPE, String CUST_NUMBER, String CUST_NAME, String ITEM_CODE, String DESCRIPTION, String HSN, String TAX_RATE, String ITEM_QTY, String UNIT_PRICE, String AMOUNT, String CGST_RATE, String CGST_VALUE, String SGST_RATE, String SGST_VALUE, String IGST_RATE, String IGST_VALUE, String AR_TOTAL, String PICK_SLIP_NO, String update_at) {
        this.INVOICE_NUM = INVOICE_NUM;
        this.TRX_DATE = TRX_DATE;
        this.ORDER_NUMBER = ORDER_NUMBER;
        this.ORDERED_DATE = ORDERED_DATE;
        this.PAYMENT_TERM = PAYMENT_TERM;
        this.PAYMENT_DATE = PAYMENT_DATE;
        this.CAT_TYPE = CAT_TYPE;
        this.CUST_NUMBER = CUST_NUMBER;
        this.CUST_NAME = CUST_NAME;
        this.ITEM_CODE = ITEM_CODE;
        this.DESCRIPTION = DESCRIPTION;
        this.HSN = HSN;
        this.TAX_RATE = TAX_RATE;
        this.ITEM_QTY = ITEM_QTY;
        this.UNIT_PRICE = UNIT_PRICE;
        this.AMOUNT = AMOUNT;
        this.CGST_RATE = CGST_RATE;
        this.CGST_VALUE = CGST_VALUE;
        this.SGST_RATE = SGST_RATE;
        this.SGST_VALUE = SGST_VALUE;
        this.IGST_RATE = IGST_RATE;
        this.IGST_VALUE = IGST_VALUE;
        this.AR_TOTAL = AR_TOTAL;
        this.PICK_SLIP_NO = PICK_SLIP_NO;
        this.update_at = update_at;

    }

    public String getCAT_TYPE() {
        return CAT_TYPE;
    }

    public void setCAT_TYPE(String CAT_TYPE) {
        this.CAT_TYPE = CAT_TYPE;
    }

    public String getORDER_NUMBER() {
        return ORDER_NUMBER;
    }

    public void setORDER_NUMBER(String ORDER_NUMBER) {
        this.ORDER_NUMBER = ORDER_NUMBER;
    }

    public String getINVOICE_NUM() {
        return INVOICE_NUM;
    }

    public void setINVOICE_NUM(String INVOICE_NUM) {
        this.INVOICE_NUM = INVOICE_NUM;
    }

    public String getTRX_DATE() {
        return TRX_DATE;
    }

    public void setTRX_DATE(String TRX_DATE) {
        this.TRX_DATE = TRX_DATE;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getCUST_NUMBER() {
        return CUST_NUMBER;
    }

    public void setCUST_NUMBER(String CUST_NUMBER) {
        this.CUST_NUMBER = CUST_NUMBER;
    }

    public String getORDERED_DATE() {
        return ORDERED_DATE;
    }

    public void setORDERED_DATE(String ORDERED_DATE) {
        this.ORDERED_DATE = ORDERED_DATE;
    }

    public String getPAYMENT_TERM() {
        return PAYMENT_TERM;
    }

    public void setPAYMENT_TERM(String PAYMENT_TERM) {
        this.PAYMENT_TERM = PAYMENT_TERM;
    }

    public String getPAYMENT_DATE() {
        return PAYMENT_DATE;
    }

    public void setPAYMENT_DATE(String PAYMENT_DATE) {
        this.PAYMENT_DATE = PAYMENT_DATE;
    }

    public String getCUST_NAME() {
        return CUST_NAME;
    }

    public void setCUST_NAME(String CUST_NAME) {
        this.CUST_NAME = CUST_NAME;
    }

    public String getITEM_CODE() {
        return ITEM_CODE;
    }

    public void setITEM_CODE(String ITEM_CODE) {
        this.ITEM_CODE = ITEM_CODE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getHSN() {
        return HSN;
    }

    public void setHSN(String HSN) {
        this.HSN = HSN;
    }

    public String getTAX_RATE() {
        return TAX_RATE;
    }

    public void setTAX_RATE(String TAX_RATE) {
        this.TAX_RATE = TAX_RATE;
    }

    public String getITEM_QTY() {
        return ITEM_QTY;
    }

    public void setITEM_QTY(String ITEM_QTY) {
        this.ITEM_QTY = ITEM_QTY;
    }

    public String getUNIT_PRICE() {
        return UNIT_PRICE;
    }

    public void setUNIT_PRICE(String UNIT_PRICE) {
        this.UNIT_PRICE = UNIT_PRICE;
    }

    public String getCGST_RATE() {
        return CGST_RATE;
    }

    public void setCGST_RATE(String CGST_RATE) {
        this.CGST_RATE = CGST_RATE;
    }

    public String getCGST_VALUE() {
        return CGST_VALUE;
    }

    public void setCGST_VALUE(String CGST_VALUE) {
        this.CGST_VALUE = CGST_VALUE;
    }

    public String getSGST_RATE() {
        return SGST_RATE;
    }

    public void setSGST_RATE(String SGST_RATE) {
        this.SGST_RATE = SGST_RATE;
    }

    public String getSGST_VALUE() {
        return SGST_VALUE;
    }

    public void setSGST_VALUE(String SGST_VALUE) {
        this.SGST_VALUE = SGST_VALUE;
    }

    public String getIGST_RATE() {
        return IGST_RATE;
    }

    public void setIGST_RATE(String IGST_RATE) {
        this.IGST_RATE = IGST_RATE;
    }

    public String getIGST_VALUE() {
        return IGST_VALUE;
    }

    public void setIGST_VALUE(String IGST_VALUE) {
        this.IGST_VALUE = IGST_VALUE;
    }

    public String getAR_TOTAL() {
        return AR_TOTAL;
    }

    public void setAR_TOTAL(String AR_TOTAL) {
        this.AR_TOTAL = AR_TOTAL;
    }

    public String getPICK_SLIP_NO() {
        return PICK_SLIP_NO;
    }

    public void setPICK_SLIP_NO(String PICK_SLIP_NO) {
        this.PICK_SLIP_NO = PICK_SLIP_NO;
    }


}
