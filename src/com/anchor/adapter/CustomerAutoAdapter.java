package com.anchor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.R;
import com.anchor.activities.Sub_Dealer_Order_Main;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;


public class CustomerAutoAdapter extends RecyclerView.Adapter<CustomerAutoAdapter.ContactViewHolder>  {

    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Local_Data> contactList;
    private List<Local_Data> contactListfilter;
    String name = "";
    DataBaseHelper dbvoc;
    String activity_name = "";
    String stage_s;
    List<Local_Data> autolist = new ArrayList<>();




    static Context mcontext;
    public CustomerAutoAdapter(List<Local_Data> contactList, Context context) {
        this.contactList = contactList;
        this.mcontext = context;

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        if(contactList.size() > 0 && Global_Data.click_flag.equalsIgnoreCase(""))
        {
            Local_Data ci = contactList.get(i);
            contactViewHolder.cs_name.setText("Shop Name : "+ci.getName());
            contactViewHolder.cs_shope.setText(ci.getName());
            contactViewHolder.cs_address.setText("Address : "+ci.getAddress());
          
            
        }
//        else
//        {
//            ((Sub_Dealer_Order_Main) mcontext).findViewById(R.id.auto_recycleview).setVisibility(View.GONE);
//        }

        contactViewHolder.customer_glayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.click_flag = "yes";
                ((Sub_Dealer_Order_Main) mcontext).findViewById(R.id.auto_recycleview).setVisibility(View.GONE);
             //  RecyclerView recyclerView = ((Sub_Dealer_Order_Main) mcontext).findViewById(R.id.auto_recycleview);
//                recyclerView.setAdapter(null);
//                contactList.clear();
              //  recyclerView.setVisibility(View.GONE);
               // AutoCompleteTextView at = ((Sub_Dealer_Order_Main) mcontext).findViewById(R.id.s_dealer_search);
               // at.setText(contactViewHolder.cs_shope.getText().toString());
                ((Sub_Dealer_Order_Main) mcontext).onClickCalled(contactViewHolder.cs_shope.getText().toString());
            }
        });



    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.auto_customer_layout, viewGroup, false);
        return new ContactViewHolder(itemView);
    }




    public static class ContactViewHolder extends RecyclerView.ViewHolder  {

        protected TextView cs_name,cs_shope;
        protected TextView cs_address;
        protected LinearLayout customer_glayout;
       

        public ContactViewHolder(View v) {
            super(v);

            cs_name =   v.findViewById(R.id.cs_name);
            cs_shope =   v.findViewById(R.id.cs_shope);
            cs_address =  v.findViewById(R.id.cs_address);
            customer_glayout =  v.findViewById(R.id.customer_glayout);
            
        }

       

    }










}
