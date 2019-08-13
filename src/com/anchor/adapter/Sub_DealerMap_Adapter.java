package com.anchor.adapter;

/**
 * Created by vv on 01-03-2018.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.R;
import com.anchor.activities.Sub_Dealer_Order_Main;
import com.anchor.model.SubDealerModel;

import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;


public class Sub_DealerMap_Adapter extends RecyclerView.Adapter<Sub_DealerMap_Adapter.ContactViewHolder> {

    static ConnectionDetector cd;
    static Boolean isInternetPresent = false;
    private List<SubDealerModel> contactList;
    private List<SubDealerModel> contactListfilter;
    String name = "";
    DataBaseHelper dbvoc;
    String activity_name = "";

    static Context mcontext;

    public Sub_DealerMap_Adapter(List<SubDealerModel> contactList, Context context) {
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
        SubDealerModel ci = contactList.get(i);
        contactViewHolder.d_name.setText("Shop Name : " + ci.shop_name);
        contactViewHolder.p_delaer_name.setText("Firm Name : " + ci.name);
        contactViewHolder.p_proprietor_mobile1.setText("Proprietor Mobile : " + ci.proprietor_mobile1);
        contactViewHolder.p_proprietor_name1.setText("proprietor Name : " + ci.proprietor_name1);
        contactViewHolder.p_proprietor_email1.setText("proprietor Email : " + ci.proprietor_email1);
        contactViewHolder.p_dealer_city.setText("City : " + ci.city);
      //  contactViewHolder.p_stages.setText("Stage : " + ci.Stage);
        contactViewHolder.p_dealer_code.setText(ci.code);
       

        contactViewHolder.sub_h_container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Global_Data.Sub_Dealer_Code = contactViewHolder.p_dealer_code.getText().toString();
                mcontext.startActivity(new Intent(mcontext, Sub_Dealer_Order_Main.class));

            }
        });

      

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.sub_dealermap_adapter, viewGroup, false);
        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView d_name;
        protected TextView p_delaer_name;
        protected TextView p_proprietor_mobile1;
        protected TextView p_proprietor_name1;
        protected TextView p_proprietor_email1;
        protected TextView p_dealer_city;
        protected TextView p_dealer_code;
        protected TextView p_stages;
        RelativeLayout sub_h_container;
      

        public ContactViewHolder(View v) {
            super(v);

            d_name = v.findViewById(R.id.d_name);
            p_delaer_name = v.findViewById(R.id.p_delaer_name);
            p_proprietor_mobile1 = v.findViewById(R.id.p_proprietor_mobile1);
            p_proprietor_name1 = v.findViewById(R.id.p_proprietor_name1);
            p_proprietor_email1 = v.findViewById(R.id.p_proprietor_email1);
            p_dealer_city = v.findViewById(R.id.p_dealer_city);
            p_dealer_code = v.findViewById(R.id.p_dealer_code);
            p_stages = v.findViewById(R.id.p_stages);
            sub_h_container = v.findViewById(R.id.sub_h_container);
           

         
        }

        // Handles the row being being clicked

    }


    

   

  
}
