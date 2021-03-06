package com.anchor.adapter;

import android.content.Context;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.webservice.ConnectionDetector;

import java.util.List;

import cpm.simplelogic.helper.Customer_Info;

public class Customer_Invoices_adapter extends RecyclerView.Adapter<com.anchor.adapter.Customer_Invoices_adapter.ContactViewHolder> {

    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Customer_Info> contactList;
    private List<Customer_Info> contactListfilter;

    static Context mcontext;
    public Customer_Invoices_adapter(List<Customer_Info> contactList, Context context) {
        this.contactList = contactList;
        mcontext = context;
        //this.contactListfilter.addAll(this.contactList);
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(com.anchor.adapter.Customer_Invoices_adapter.ContactViewHolder contactViewHolder, int i) {
        Customer_Info ci = contactList.get(i);
        contactViewHolder.ic_name.setText(Html.fromHtml("<b>" +"Invoice Number : "+ "</b>")+ci.ic_name);
        contactViewHolder.icustomer_code.setText(Html.fromHtml("<b>" +"Customer code : "+ "</b>")+ci.icustomer_code);
       // contactViewHolder.invoice_number.setText(ci.invoice_number);
        contactViewHolder.invoice_date.setText(Html.fromHtml("<b>" +"Invoice date : "+ "</b>")+ci.invoice_date);
        contactViewHolder.invoice_amount.setText(Html.fromHtml("<b>" +"Invoice amount : "+ "</b>")+ci.invoice_amount);
        contactViewHolder.invoice_due_date.setText(Html.fromHtml("<b>" +"Invoice due date : "+ "</b>")+ci.invoice_due_date);
        contactViewHolder.invoice_due_amount.setText(Html.fromHtml("<b>" +"Invoice due amount : "+ "</b>")+ci.invoice_due_amount);

    }

    @Override
    public com.anchor.adapter.Customer_Invoices_adapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.customer_invoices_adapter, viewGroup, false);

        return new com.anchor.adapter.Customer_Invoices_adapter.ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder  {

        protected TextView ic_name;
        protected TextView icustomer_code;
        protected TextView invoice_number;
        protected TextView invoice_date;
        protected TextView invoice_due_date;
        protected TextView invoice_due_amount;
        protected TextView invoice_amount;


        public ContactViewHolder(View v) {
            super(v);

            ic_name = v.findViewById(R.id.ic_name);
            icustomer_code = v.findViewById(R.id.icustomer_code);
            invoice_number = v.findViewById(R.id.invoice_number);
            invoice_date = v.findViewById(R.id.invoice_date);
            invoice_due_date = v.findViewById(R.id.invoice_due_date);
            invoice_due_amount = v.findViewById(R.id.invoice_due_amount);
            invoice_amount = v.findViewById(R.id.invoice_amount);

        }


    }


}
