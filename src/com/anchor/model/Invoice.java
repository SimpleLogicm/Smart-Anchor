package com.anchor.model;

import com.google.gson.annotations.SerializedName;

public class Invoice {


    @SerializedName("id")
    private int id;
    @SerializedName("project_id")
    private int projectId;
    @SerializedName("customer_id")
    private int customerId;
    @SerializedName("retailer_id")
    private int retailerId;
    @SerializedName("created_by")
    private String  createdBy;
    @SerializedName("order_id")
    private int orderId;
    @SerializedName("date")
    private String date;
    @SerializedName("tax_name_1")
    private String taxName1;
    @SerializedName("tax_name_2")
    private String taxName2;
    @SerializedName("tax_name_3")
    private String taxName3;
    @SerializedName("tax_name_4")
    private String taxName4;
    @SerializedName("tax_name_5")
    private String taxName5;
    @SerializedName("tax_amount_1")
    private int taxAmount1;
    @SerializedName("tax_amount_2")
    private int taxAmount2;
    @SerializedName("tax_amount_3")
    private int taxAmount3;
    @SerializedName("tax_amount_4")
    private int taxAmount4;
    @SerializedName("tax_amount_5")
    private int taxAmount5;
    @SerializedName("total")
    private int total;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("invoice_number")
    private String invoiceNumber;
    @SerializedName("invoice_due_date")
    private String invoiceDueDate;
    @SerializedName("invoice_due_amount")
    private int invoiceDueAmount;

    //empty constructor
    public Invoice() {
    }

    //constructor by passing an invoice
    public Invoice(Invoice invoice) {
        this.id = invoice.id;
        this.projectId = invoice.projectId;
        this.customerId = invoice.customerId;
        this.retailerId = invoice.retailerId;
        this.createdBy = invoice.createdBy;
        this.orderId = invoice.orderId;
        this.date = invoice.date;
        this.taxName1 = invoice.taxName1;
        this.taxName2 = invoice.taxName2;
        this.taxName3 = invoice.taxName3;
        this.taxName4 = invoice.taxName4;
        this.taxName5 = invoice.taxName5;
        this.taxAmount1 = invoice.taxAmount1;
        this.taxAmount2 = invoice.taxAmount2;
        this.taxAmount3 = invoice.taxAmount3;
        this.taxAmount4 = invoice.taxAmount4;
        this.taxAmount5 = invoice.taxAmount5;
        this.total = invoice.total;
        this.createdAt = invoice.createdAt;
        this.updatedAt = invoice.updatedAt;
        this.invoiceNumber = invoice.invoiceNumber;
        this.invoiceDueDate = invoice.invoiceDueDate;
        this.invoiceDueAmount = invoice.invoiceDueAmount;
    }

    public void copy(Invoice invoice){
        this.id = invoice.id;
        this.projectId = invoice.projectId;
        this.customerId = invoice.customerId;
        this.retailerId = invoice.retailerId;
        this.createdBy = invoice.createdBy;
        this.orderId = invoice.orderId;
        this.date = invoice.date;
        this.taxName1 = invoice.taxName1;
        this.taxName2 = invoice.taxName2;
        this.taxName3 = invoice.taxName3;
        this.taxName4 = invoice.taxName4;
        this.taxName5 = invoice.taxName5;
        this.taxAmount1 = invoice.taxAmount1;
        this.taxAmount2 = invoice.taxAmount2;
        this.taxAmount3 = invoice.taxAmount3;
        this.taxAmount4 = invoice.taxAmount4;
        this.taxAmount5 = invoice.taxAmount5;
        this.total = invoice.total;
        this.createdAt = invoice.createdAt;
        this.updatedAt = invoice.updatedAt;
        this.invoiceNumber = invoice.invoiceNumber;
        this.invoiceDueDate = invoice.invoiceDueDate;
        this.invoiceDueAmount = invoice.invoiceDueAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(int retailerId) {
        this.retailerId = retailerId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTaxName1() {
        return taxName1;
    }

    public void setTaxName1(String taxName1) {
        this.taxName1 = taxName1;
    }

    public String getTaxName2() {
        return taxName2;
    }

    public void setTaxName2(String taxName2) {
        this.taxName2 = taxName2;
    }

    public String getTaxName3() {
        return taxName3;
    }

    public void setTaxName3(String taxName3) {
        this.taxName3 = taxName3;
    }

    public String getTaxName4() {
        return taxName4;
    }

    public void setTaxName4(String taxName4) {
        this.taxName4 = taxName4;
    }

    public String getTaxName5() {
        return taxName5;
    }

    public void setTaxName5(String taxName5) {
        this.taxName5 = taxName5;
    }

    public int getTaxAmount1() {
        return taxAmount1;
    }

    public void setTaxAmount1(int taxAmount1) {
        this.taxAmount1 = taxAmount1;
    }

    public int getTaxAmount2() {
        return taxAmount2;
    }

    public void setTaxAmount2(int taxAmount2) {
        this.taxAmount2 = taxAmount2;
    }

    public int getTaxAmount3() {
        return taxAmount3;
    }

    public void setTaxAmount3(int taxAmount3) {
        this.taxAmount3 = taxAmount3;
    }

    public int getTaxAmount4() {
        return taxAmount4;
    }

    public void setTaxAmount4(int taxAmount4) {
        this.taxAmount4 = taxAmount4;
    }

    public int getTaxAmount5() {
        return taxAmount5;
    }

    public void setTaxAmount5(int taxAmount5) {
        this.taxAmount5 = taxAmount5;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public void setInvoiceDueDate(String invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public int getInvoiceDueAmount() {
        return invoiceDueAmount;
    }

    public void setInvoiceDueAmount(int invoiceDueAmount) {
        this.invoiceDueAmount = invoiceDueAmount;
    }
}
