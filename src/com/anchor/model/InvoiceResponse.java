package com.anchor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvoiceResponse {

    @SerializedName("invoices")
    private List<Invoice> invoices = null;

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }
}
