package com.anchor.model;

import java.io.Serializable;

public class OutstandingModel implements Serializable {

    public String bu;
    public String credit_limit;
    public String outstanding;
    public String overdue;

    public OutstandingModel(String bu, String credit_limit, String outstanding, String overdue) {
        this.bu = bu;
        this.credit_limit = credit_limit;
        this.outstanding = outstanding;
        this.overdue = overdue;

    }

    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public String getCreditlimit() {
        return credit_limit;
    }

    public void setCreditlimit(String credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(String outstanding) {
        this.outstanding = outstanding;
    }

    public String getOverdue() {
        return overdue;
    }

    public void setOverdue(String overdue) {
        this.overdue = overdue;
    }

}
