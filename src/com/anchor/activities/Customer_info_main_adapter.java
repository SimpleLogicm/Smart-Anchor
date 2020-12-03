package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.webservice.ConnectionDetector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import cpm.simplelogic.helper.Customer_Info;

public class Customer_info_main_adapter extends RecyclerView.Adapter<Customer_info_main_adapter.ContactViewHolder> {


    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Customer_Info> contactList;
    static ImageView filter_btn, close_filter;
    static Button filter_submit, filter_back;
    static Context mcontext;
    public Customer_info_main_adapter(List<Customer_Info> contactList, Context context) {
        this.contactList = contactList;
        mcontext = context;
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
        contactViewHolder.c_code.setText(ci.icustomer_code);
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
        protected TextView c_code;
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
        protected ImageView c_schemes;

        public ContactViewHolder(View v) {
            super(v);

            c_name = v.findViewById(R.id.c_name);
            c_code = v.findViewById(R.id.c_code);
            c_name2 = v.findViewById(R.id.c_name2);
            c_address = v.findViewById(R.id.c_address);
            c_credit_profile = v.findViewById(R.id.c_credit_profile);
            c_ammount1 = v.findViewById(R.id.c_ammount1);
            c_ammount2 = v.findViewById(R.id.c_ammount2);
            c_latlon = v.findViewById(R.id.c_latlon);
            c_mobile_number = v.findViewById(R.id.c_mobile_number);
            c_beat_name = v.findViewById(R.id.c_beat_name);
            c_call = v.findViewById(R.id.c_call);
            c_city_name = v.findViewById(R.id.c_city_name);
            c_location = v.findViewById(R.id.c_location);
            c_invoice = v.findViewById(R.id.c_invoice);
            c_schemes = v.findViewById(R.id.c_schemes);

            c_call.setOnClickListener(this);
            c_location.setOnClickListener(this);
            c_invoice.setOnClickListener(this);
            c_schemes.setOnClickListener(this);
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
                                view.getContext().startActivity(intent);
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
                                view.getContext().startActivity(intent);
                            } else {
                                Toast.makeText(mcontext, "Address not found", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                } else {
                    Toast toast = Toast.makeText(view.getContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            else if (view.getId() == c_call.getId())
            {
                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_mobile_number.getText().toString().trim()))
                {

                    requestPhoneCallPermission(c_mobile_number.getText().toString().trim());
                }
            }
            else if (view.getId() == c_invoice.getId())
            {

                Global_Data.customer_code =  c_code.getText().toString().trim();
                Intent i=new Intent( view.getContext(), Customer_Invoices.class);
                view.getContext().startActivity(i);

            }
//            else if (view.getId() == c_schemes.getId())
//            {
//
//                Global_Data.Scheme_report_Type = "";
//                Global_Data.customer_code =  c_code.getText().toString().trim();
//                FilterDialog_scheme();
//
//            }
        }
    }



    public static void FilterDialog_scheme() {
        final Dialog dialog1 = new Dialog(mcontext);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.c_scheme_dialog);



        filter_submit = dialog1.findViewById(R.id.c_filter_submit);
        filter_back = dialog1.findViewById(R.id.c_filter_back);
        final RadioGroup c_radioGroup = dialog1.findViewById(R.id.c_radioGroup);
        RadioButton s_management = dialog1.findViewById(R.id.s_management);
        RadioButton s_report = dialog1.findViewById(R.id.s_report);
        close_filter = dialog1.findViewById(R.id.close_filter);


        filter_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                   if (c_radioGroup.getCheckedRadioButtonId() == -1)
                    {
                        Toast.makeText(mcontext, "Please Select Scheme Report Type.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        int selectedId = c_radioGroup.getCheckedRadioButtonId();
                        RadioButton rb= dialog1.findViewById(selectedId);
                        String radioText=rb.getText().toString();

                        if(radioText.equalsIgnoreCase("Scheme Management"))
                        {
                            Global_Data.Scheme_report_Type = "scheme_management";
                        }
                        else
                        if(radioText.equalsIgnoreCase("Shortfall Report"))
                        {
                            Global_Data.Scheme_report_Type = "shortfall_report";
                        }
                        dialog1.dismiss();
                        Intent i=new Intent(mcontext, Customer_Schemes.class);
                        mcontext.startActivity(i);
                    }


                }
        });

        filter_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.Scheme_report_Type = "";

                dialog1.dismiss();

            }
        });

        close_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();

    }



    public static void requestPhoneCallPermission(final String mobile_number) {
        Dexter.withActivity((Activity) mcontext)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + mobile_number));
                        mcontext.startActivity(callIntent);

                        return;

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private static void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private static void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", mcontext.getPackageName(), null);
        intent.setData(uri);
        ((Activity) mcontext).startActivityForResult(intent, 101);
    }

}
