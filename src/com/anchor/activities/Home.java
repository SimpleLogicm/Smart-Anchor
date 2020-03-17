package com.anchor.activities;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.slidingmenu.CalendarAct;
import com.anchor.webservice.ConnectionDetector;

import java.io.BufferedReader;
import java.util.List;

public class Home extends Fragment {


    BufferedReader in = null;
    ProgressDialog dialog;
    String line;
    Cursor cursor;
    private DataBaseHelper dbHelper;
    public SQLiteDatabase db;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    TextView txtWelcomeUser;

    static int cityID, beatID, retailerID;
    int data_stateid, data_cityID;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String data_beatID;
    DataBaseHelper dbvoc;
    View rootView;
    ImageView order, calendar, custom_serve, expenses, target, schedule, logout, pricing, add_retailernew, marketing_data, syncmdata;
    ImageView top_arrow;
    ImageView down_arrow;
    CardView main_my_view;
    TextView my_text;
    CardView main_bottomcard;
    boolean isUp;

    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Global_Data.Stock_warehouse_flag = "";
        Global_Data.Stock_product_flag = "";
        Global_Data.Stock_product_flag_value_check = "";
        Global_Data.Stock_warehouse_flag_value_check = "";
        rootView = inflater.inflate(R.layout.fragment_logout, container, false);

        order = rootView.findViewById(R.id.order);
        calendar = rootView.findViewById(R.id.calendar);
        //custom_serve=(ImageView)rootView.findViewById(R.id.custom_serve);
        expenses = rootView.findViewById(R.id.expenses);
        target = rootView.findViewById(R.id.target);
        pricing = rootView.findViewById(R.id.pricing);
        add_retailernew = rootView.findViewById(R.id.add_retailernew);
        marketing_data = rootView.findViewById(R.id.marketing_data);
        syncmdata = rootView.findViewById(R.id.syncmdata);
        txtWelcomeUser = rootView.findViewById(R.id.txtWelcomeUser);
        top_arrow = rootView.findViewById(R.id.my_button);
        down_arrow = rootView.findViewById(R.id.my_buttonfghf);
        main_my_view = rootView.findViewById(R.id.main_my_view);
        main_bottomcard = rootView.findViewById(R.id.main_bottomcard);
        my_text = rootView.findViewById(R.id.my_text);

        main_my_view.setVisibility(View.INVISIBLE);

        my_text.setText(Html.fromHtml("Rank : 2<sup>nd</sup>"));

        isUp = false;


        Global_Data.GLObalOrder_id = "";
        Global_Data.GLOvel_ITEM_NUMBER = "";
        Global_Data.GLOvel_GORDER_ID_RETURN = "";
        Global_Data.GLObalOrder_id_return = "";
        Global_Data.GLOvel_GORDER_ID = "";
        Global_Data.target_amount = "";
        Global_Data.target_grpby = "";

        String user_name = "";
        if (!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null")) {
            user_name = Global_Data.USER_FIRST_NAME.trim();
            if (!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null")) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim();
            }
        }

        txtWelcomeUser.setText(user_name + " : " + Global_Data.emp_code);

        loginDataBaseAdapter = new LoginDataBaseAdapter(getActivity());
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        Global_Data.CUSTOMER_SERVICE_FLAG = "";


        dbvoc = new DataBaseHelper(getActivity());
        cd = new ConnectionDetector(getActivity());

        top_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                slideUp(main_my_view);

                //myButton.setText("Slide up");

                isUp = true;
            }
        });

        down_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                slideDown(main_my_view);
                isUp = false;
            }
        });

        order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Local_Data> contacts = dbvoc.checkCustomer();
                if (contacts.size() <= 0) {

                    Toast toast = Toast.makeText(getActivity(),
                            "No customers found. Please try Sync or contact IT team", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    Global_Data.CUSTOMER_SERVICE_FLAG = "";
                    Intent intent = new Intent(getActivity(), Sales_Dash.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //getActivity().finish();
                }
            }
        });

        calendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

// 				 new Thread(new Runnable() {
//					   public void run() {
//					    	 Flwg();
//					      }
//					    }).start();
                Intent intent1 = new Intent(getActivity(), CalendarAct.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();
            }
        });

        expenses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Expenses.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();

            }
        });

        pricing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Pricing_Main.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();


            }
        });

        add_retailernew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = getActivity().getPackageManager().getLaunchIntentForPackage("subdealer.anchor.com.anchorsubdealer_registration");

                if (mIntent != null) {
                    try {

                        mIntent.putExtra("USER_EMAIL", Global_Data.GLOvel_USER_EMAIL);
                        mIntent.putExtra("USER_ID", Global_Data.GLOVEL_USER_ID);
                        mIntent.putExtra("USER_FIRST_NAME", Global_Data.USER_FIRST_NAME);
                        mIntent.putExtra("USER_LAST_NAME", Global_Data.USER_LAST_NAME);
                        mIntent.putExtra("USER_imei_no", Global_Data.imei_no);
                        startActivity(mIntent);

                    } catch (ActivityNotFoundException err) {
                        err.printStackTrace();
                        Toast.makeText(getActivity(), "Please install bar", Toast.LENGTH_SHORT).show();
//
                        try {
                            final String appPackageName = "subdealer.anchor.com.anchorsubdealer_registration"; // Can also use getPackageName(), as below
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(getActivity(), "App Not available in google play.", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    try {
                        final String appPackageName = "subdealer.anchor.com.anchorsubdealer_registration"; // Can also use getPackageName(), as below
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "App Not available in google play.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        marketing_data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Marketing.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();

            }
        });


        syncmdata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AllOrders_Sync.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();

            }
        });

        target.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();
                Intent i = new Intent(getActivity(), Target_REYC_Main.class);
                startActivity(i);
                getActivity().finish();


            }
        });


        return rootView;
    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {
        view.setVisibility(View.VISIBLE);
        main_bottomcard.setVisibility(View.GONE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                view.getHeight(),  // fromYDelta
                0);                // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {
        // main_my_view.setVisibility(View.GONE);
        main_bottomcard.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
    }

    // slide the view from its current position to below itself
    public void slideDownnew() {
        if(isUp == true)
        {
            isUp = false;
            main_my_view.setVisibility(View.GONE);
            main_bottomcard.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    0,                 // fromYDelta
                    main_my_view.getHeight()); // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            main_my_view.startAnimation(animate);
        }

    }

    public boolean isUpStatus() {
        return  isUp;
    }



    @Override
    public void onResume() {
        super.onResume();

//        if(getView() == null){
//            return;
//        }
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    if(isUp == true)
//                    {
//                        slideDown(main_my_view);
//                        isUp = false;
//                    }
//
//                    return true;
//                }
//                return false;
//            }
//        });
    }

}
