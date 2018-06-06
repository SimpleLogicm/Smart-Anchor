package com.anchor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.webservice.ConnectionDetector;

import java.util.List;

import cpm.simplelogic.helper.Customer_Info;

public class Customer_Schemes_adapter extends RecyclerView.Adapter<com.anchor.adapter.Customer_Schemes_adapter.ContactViewHolder> {

    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Customer_Info> contactList;
    private List<Customer_Info> contactListfilter;

    static Context mcontext;
    public Customer_Schemes_adapter(List<Customer_Info> contactList, Context context) {
        this.contactList = contactList;
        this.mcontext = context;
        //this.contactListfilter.addAll(this.contactList);
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(com.anchor.adapter.Customer_Schemes_adapter.ContactViewHolder contactViewHolder, int i) {
        Customer_Info ci = contactList.get(i);
        contactViewHolder.c_header.setText(ci.c_header);
        contactViewHolder.c_detail1.setText(Html.fromHtml("<b>" +"Customer code : "+ "</b>")+ci.c_detail1);
        contactViewHolder.c_detail2.setText(Html.fromHtml("<b>" +"Invoice date : "+ "</b>")+ci.c_detail2);
        contactViewHolder.c_detail3.setText(Html.fromHtml("<b>" +"Invoice due date : "+ "</b>")+ci.c_detail3);
        contactViewHolder.c_detail4.setText(Html.fromHtml("<b>" +"Invoice due amount : "+ "</b>")+ci.c_detail4);
        contactViewHolder.c_detail5.setText(Html.fromHtml("<b>" +"Invoice due amount : "+ "</b>")+ci.c_detail5);
        contactViewHolder.c_detail6.setText(Html.fromHtml("<b>" +"Invoice due amount : "+ "</b>")+ci.c_detail6);
        contactViewHolder.c_detail7.setText(Html.fromHtml("<b>" +"Invoice due amount : "+ "</b>")+ci.c_detail7);

    }

    @Override
    public com.anchor.adapter.Customer_Schemes_adapter.ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.customer_sheme_adapter, viewGroup, false);

        return new com.anchor.adapter.Customer_Schemes_adapter.ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder  {

        protected TextView c_header;
        protected TextView c_detail1;
        protected TextView c_detail2;
        protected TextView c_detail3;
        protected TextView c_detail4;
        protected TextView c_detail5;
        protected TextView c_detail6;
        protected TextView c_detail7;


        public ContactViewHolder(View v) {
            super(v);

            c_header =  (TextView) v.findViewById(R.id.c_header);
            c_detail1 =  (TextView) v.findViewById(R.id.c_detail1);
            c_detail2 = (TextView)  v.findViewById(R.id.c_detail2);
            c_detail3 = (TextView)  v.findViewById(R.id.c_detail3);
            c_detail4 = (TextView)  v.findViewById(R.id.c_detail4);
            c_detail5 = (TextView) v.findViewById(R.id.c_detail5);
            c_detail6 = (TextView) v.findViewById(R.id.c_detail6);
            c_detail7 = (TextView) v.findViewById(R.id.c_detail7);

        }


    }


}
