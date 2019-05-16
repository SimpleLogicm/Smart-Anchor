package com.anchor.model;

import android.view.View;

import java.io.Serializable;


/**
 * Simple POJO model for example
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CustomerServiceModel implements Serializable {
    public String product_name;
    public String complaint_type;
    public String outer_date;
    public String inner_actiondate;
    public String description;

    public String response;
    public String respondant_name;
    public String status;
    public String type;
    public String description2;
    public String description3;
    public String description4;
    public String description5;
    public String description6;
    public String description7;

    private View.OnClickListener requestBtnClickListener;

    public CustomerServiceModel() {
    }

    public CustomerServiceModel(String product_name, String complaint_type,String outer_date, String inner_actiondate,String description,String response,String respondant_name,String status,String type,String description2,String description3,String description4,String description5,String description6,String description7) {
        this.product_name = product_name;
        this.complaint_type = complaint_type;
        this.outer_date = outer_date;
        this.inner_actiondate = inner_actiondate;
        this.description = description;
        this.response = response;
        this.respondant_name = respondant_name;
        this.status = status;
        this.type = type;
        this.description2 = description2;
        this.description3 = description3;
        this.description4 = description4;
        this.description5 = description5;
        this.description6 = description6;
        this.description7 = description7;
    }

    public String getDescription7() {
        return description7;
    }

    public void setDescription7(String description7) {
        this.description7 = description7;
    }

    public String getDescription6() {
        return description6;
    }

    public void setDescription6(String description6) {
        this.description6 = description6;
    }

    public String getDescription5() {
        return description5;
    }

    public void setDescription5(String description5) {
        this.description5 = description5;
    }

    public String getDescription4() {
        return description4;
    }

    public void setDescription4(String description4) {
        this.description4 = description4;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription2() {
        return description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getComplaint_type() {
        return complaint_type;
    }

    public void setComplaint_type(String complaint_type) {
        this.complaint_type = complaint_type;
    }

    public String getOuter_date() {
        return outer_date;
    }

    public void setOuter_date(String outer_date) {
        this.outer_date = outer_date;
    }

    public String getInner_actiondate() {
        return inner_actiondate;
    }

    public void setInner_actiondate(String inner_actiondate) {
        this.inner_actiondate = inner_actiondate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getRespondant_name() {
        return respondant_name;
    }

    public void setRespondant_name(String respondant_name) {
        this.respondant_name = respondant_name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

//    public static ArrayList<CustomerServiceModel> getTestingList() {
//        ArrayList<CustomerServiceModel> items = new ArrayList<>();
//        items.add(new CustomerServiceModel("01-01-2018", "20291 - 250*250 MM FLMOUNBOX WITH 3 COMP", "Product Quality", "Open"));
////        items.add(new Item("01-01-2018", "20358S - BELL INDICATOR", "Delivery ", "Close"));
////        items.add(new Item("01-01-2018", "20392 - ROMA SPMS75 A", "Schemes", "Open"));
//
//
//        return items;
//
//    }

}
