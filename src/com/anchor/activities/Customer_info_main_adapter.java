package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.webservice.ConnectionDetector;

import java.util.List;

import cpm.simplelogic.helper.Customer_Info;

public class Customer_info_main_adapter extends RecyclerView.Adapter<Customer_info_main_adapter.ContactViewHolder> {

    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Customer_Info> contactList;
    private List<Customer_Info> contactListfilter;

    static Context mcontext;
    public Customer_info_main_adapter(List<Customer_Info> contactList, Context context) {
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
        Customer_Info ci = contactList.get(i);
        contactViewHolder.c_name.setText(ci.shop_name);
        contactViewHolder.c_name2.setText(ci.name);
        contactViewHolder.c_address.setText(ci.address);
        contactViewHolder.c_credit_profile.setText(ci.credit_limit);
        contactViewHolder.c_ammount1.setText(ci.amount1);
        contactViewHolder.c_ammount2.setText(ci.amount2);
        contactViewHolder.c_latlon.setText(ci.latlong);
        contactViewHolder.c_beat_name.setText(ci.Beat_name);
        contactViewHolder.c_city_name.setText(ci.city_name);
        contactViewHolder.c_mobile_number.setText(ci.mobile);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.customer_info_main_adapter, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView c_name;
        protected TextView c_name2;
        protected TextView c_address;
        protected TextView c_credit_profile;
        protected TextView c_ammount1;
        protected TextView c_ammount2;
        protected TextView c_beat_name;
        protected TextView c_city_name;
        protected TextView c_latlon;
        protected TextView c_mobile_number;
        protected ImageView c_call;
        protected ImageView c_location;
        protected ImageView c_invoice;

        public ContactViewHolder(View v) {
            super(v);

            c_name =  (TextView) v.findViewById(R.id.c_name);
            c_name2 =  (TextView) v.findViewById(R.id.c_name2);
            c_address = (TextView)  v.findViewById(R.id.c_address);
            c_credit_profile = (TextView)  v.findViewById(R.id.c_credit_profile);
            c_ammount1 = (TextView) v.findViewById(R.id.c_ammount1);
            c_ammount2 = (TextView) v.findViewById(R.id.c_ammount2);
            c_latlon = (TextView) v.findViewById(R.id.c_latlon);
            c_mobile_number = (TextView) v.findViewById(R.id.c_mobile_number);
            c_beat_name = (TextView) v.findViewById(R.id.c_beat_name);
            c_call = (ImageView) v.findViewById(R.id.c_call);
            c_city_name = (TextView) v.findViewById(R.id.c_city_name);
            c_location = (ImageView) v.findViewById(R.id.c_location);
            c_invoice = (ImageView) v.findViewById(R.id.c_invoice);

            c_call.setOnClickListener(this);
            c_location.setOnClickListener(this);
            c_invoice.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
            cd = new ConnectionDetector(view.getContext());
            isInternetPresent = cd.isConnectingToInternet();

            if (view.getId() == c_location.getId()) {
                String s = "";
                String latlong = c_latlon.getText().toString().trim();
                String address = c_address.getText().toString().trim();
                // Toast.makeText(view.getContext(), s.trim(), Toast.LENGTH_SHORT).show();
                Log.d("latlong", "latlong" + latlong);

                if (isInternetPresent) {
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(latlong) && !(latlong.equalsIgnoreCase("0.0,0.0")) && !(latlong.equalsIgnoreCase(","))) {
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(address)) {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=" + Global_Data.GLOvel_LATITUDE + "," + Global_Data.GLOvel_LONGITUDE + "&daddr=" + address + ""));
                                ((Activity) view.getContext()).startActivity(intent);
                            } else {
                                Toast.makeText(mcontext, "Address not found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else {
//                        Toast.makeText(mcontext, "Wait..", Toast.LENGTH_SHORT).show();
//                        GeocodingLocation locationAddress = new GeocodingLocation();
//                        locationAddress.getAddressFromLocation(address,
//                                (Activity) view.getContext(), new GeocoderHandler());

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(address)) {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse("http://maps.google.com/maps?saddr=" + Global_Data.GLOvel_LATITUDE + "," + Global_Data.GLOvel_LONGITUDE + "&daddr=" + address + ""));
                                ((Activity) view.getContext()).startActivity(intent);
                            } else {
                                Toast.makeText(mcontext, "Address not found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                } else {
                    Toast toast = Toast.makeText((Activity) view.getContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            else if (view.getId() == c_call.getId())
            {
                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_mobile_number.getText().toString().trim()))
                {

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+c_mobile_number.getText().toString().trim()));
                    view.getContext().startActivity(callIntent);
                }
            }
            else if (view.getId() == c_invoice.getId())
            {
                Intent i=new Intent( view.getContext(), Customer_Invoices.class);
                view.getContext().startActivity(i);

            }
        }
    }

    private static class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

            if(locationAddress.equalsIgnoreCase("Location not found for this address"))
            {
                Toast.makeText(mcontext, "Customer location not found.", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+Global_Data.GLOvel_LATITUDE+","+Global_Data.GLOvel_LONGITUDE+"&daddr="+locationAddress+""));
                ((Activity)mcontext).startActivity(intent);
            }

        }
    }
}
