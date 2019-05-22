package com.anchor.model;

import java.io.Serializable;

public class Ledger_Model implements Serializable {

    private String Date;
    private String Transaction_Type;
    private String Invoice_No;
    private String Cheque_No;
    private String Perticulers;
    private String Debit;
    private String Credit;
    private String update_at;





    public Ledger_Model(String Date, String Transaction_Type, String Invoice_No, String Cheque_No, String Perticulers, String Debit, String Credit) {
        this.Date = Date;
        this.Transaction_Type = Transaction_Type;
        this.Invoice_No = Invoice_No;
        this.Cheque_No = Cheque_No;
        this.Perticulers = Perticulers;
        this.Debit = Debit;
        this.Credit = Credit;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTransaction_Type() {
        return Transaction_Type;
    }

    public void setTransaction_Type(String transaction_Type) {
        Transaction_Type = transaction_Type;
    }

    public String getInvoice_No() {
        return Invoice_No;
    }

    public void setInvoice_No(String invoice_No) {
        Invoice_No = invoice_No;
    }

    public String getCheque_No() {
        return Cheque_No;
    }

    public void setCheque_No(String cheque_No) {
        Cheque_No = cheque_No;
    }

    public String getPerticulers() {
        return Perticulers;
    }

    public void setPerticulers(String perticulers) {
        Perticulers = perticulers;
    }

    public String getDebit() {
        return Debit;
    }

    public void setDebit(String debit) {
        Debit = debit;
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }
}
