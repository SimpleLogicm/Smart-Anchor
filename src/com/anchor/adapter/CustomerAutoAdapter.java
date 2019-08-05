package com.anchor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Local_Data;
import com.anchor.activities.R;

import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;


public class CustomerAutoAdapter extends RecyclerView.Adapter<CustomerAutoAdapter.ContactViewHolder> {

    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Local_Data> contactList;
    private List<Local_Data> contactListfilter;
    String name = "";
    DataBaseHelper dbvoc;
    String activity_name = "";
    String stage_s;



    static Context mcontext;
    public CustomerAutoAdapter(List<Local_Data> contactList, Context context) {
        this.contactList = contactList;
        this.mcontext = context;
        //this.contactListfilter.addAll(this.contactList);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        if(contactList.size() > 0)
        {
            Local_Data ci = contactList.get(i);
            contactViewHolder.cs_name.setText("Name : "+ci.getName());
            contactViewHolder.cs_address.setText("Address : "+ci.getAddress());
          
            
        }


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.auto_customer_layout, viewGroup, false);
        return new ContactViewHolder(itemView);
    }



    public static class ContactViewHolder extends RecyclerView.ViewHolder  {

        protected TextView cs_name;
        protected TextView cs_address;
       

        public ContactViewHolder(View v) {
            super(v);

            cs_name =   v.findViewById(R.id.cs_name);
            cs_address =  v.findViewById(R.id.cs_address);
            
        }

       

    }










}
