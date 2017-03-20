package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.webservice.ConnectionDetector;

import java.util.List;

import cpm.simplelogic.helper.Stock_Info;

public class Stock_Adapter extends RecyclerView.Adapter<Stock_Adapter.ContactViewHolder> {

    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Stock_Info> contactList;
    private List<Stock_Info> contactListfilter;


    static Context mcontext;
    public Stock_Adapter(List<Stock_Info> contactList, Context context) {
        this.contactList = contactList;
        this.mcontext = context;
        //this.contactListfilter.addAll(this.contactList);
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        Stock_Info ci = contactList.get(i);
        contactViewHolder.ss_name.setText(ci.ss_name);
        contactViewHolder.ss_address.setText(ci.ss_address);
        contactViewHolder.ss_product.setText(ci.ss_product);
        contactViewHolder.ss_RP.setText(ci.ss_RP);
        contactViewHolder.ss_MRP.setText(ci.ss_MRP);
        contactViewHolder.ss_grossstock.setText(ci.ss_grossstock);
        contactViewHolder.ss_sellable.setText(ci.ss_sellable);
        contactViewHolder.ss_update.setText(ci.updated_at);
        contactViewHolder.ss_city.setText(ci.city);
        contactViewHolder.ss_state.setText(ci.state);

        if (i > 0 && !(contactList.get(i).ss_name.equalsIgnoreCase(contactList.get(i-1).ss_name))) {
           // month_value = ci.ss_name;
            contactViewHolder.ss_name.setVisibility(View.VISIBLE);
           // contactViewHolder.product_header2.setVisibility(View.VISIBLE);
        } else {
            if(i != 0)
            {
                contactViewHolder.ss_name.setVisibility(View.GONE);
               // contactViewHolder.product_header2.setVisibility(View.GONE);
            }
            else
            {
                contactViewHolder.ss_name.setVisibility(View.VISIBLE );
            }


        }


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.stock_adapter, viewGroup, false);



        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView ss_name;
        protected TextView ss_address;
        protected TextView ss_product;
        protected TextView ss_RP;
        protected TextView ss_MRP;
        protected TextView ss_grossstock;
        protected TextView ss_sellable;
        protected TextView ss_update;
        protected TextView ss_city;
        protected TextView ss_state;


        public ContactViewHolder(View v) {
            super(v);

            ss_name =  (TextView) v.findViewById(R.id.ss_name);
            ss_address =  (TextView) v.findViewById(R.id.ss_address);
            ss_product = (TextView)  v.findViewById(R.id.ss_product);
            ss_RP = (TextView)  v.findViewById(R.id.ss_RP);
            ss_MRP = (TextView) v.findViewById(R.id.ss_MRP);
            ss_grossstock = (TextView) v.findViewById(R.id.ss_grossstock);
            ss_sellable = (TextView) v.findViewById(R.id.ss_sellable);
            ss_update = (TextView) v.findViewById(R.id.ss_update);
            ss_city = (TextView) v.findViewById(R.id.ss_city);
            ss_state = (TextView) v.findViewById(R.id.ss_state);



        }

        // Handles the row being being clicked

    }




}
