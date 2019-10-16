package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.Spinner_List_Adapter;
import com.anchor.adapter.Sub_Dealer_ProductList_Adapter;
import com.anchor.model.Spiner_List_Model;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;
import cpm.simplelogic.helper.GPSTracker;


public class Sub_Dealer_ProductList extends Activity {

    int pp = 0;
    GPSTracker gps;
    String q_check = "";
    ProgressDialog dialog;
    String str;
    HashMap<String, String> map;
    ArrayList<String> list1 = new ArrayList<String>();
    ArrayList<String> list2 = new ArrayList<String>();
    ArrayList<String> list3 = new ArrayList<String>();
    Boolean isInternetPresent = false;

    private ArrayList<String> p_id = new ArrayList<String>();
    private ArrayList<String> p_name = new ArrayList<String>();
    private ArrayList<String> p_mrp = new ArrayList<String>();
    private ArrayList<String> p_rp = new ArrayList<String>();
    private ArrayList<String> p_q = new ArrayList<String>();
    private ArrayList<String> p_price = new ArrayList<String>();
    private ArrayList<String> resultsvarient = new ArrayList<String>();


    ConnectionDetector cd;
    ArrayList<HashMap<String, String>> SwipeList;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private Sub_Dealer_ProductList_Adapter adapter;
    private ListView swipeListView;

    public static TextView txttotalPreview;
    static final String TAG_ITEMNAME = "product_name";
    static final String TAG_QTY = "total_qty";
    static final String TAG_PRICE = "MRP";
    static final String TAG_RP = "RP";
    static final String TAG_AMOUNT = "amount";
    static final String TAG_ITEM_NUMBER = "item_number";
    static final String TAG_ITEM_SQ = "SQ";
    static final String TAG_ITEM_MQ = "MQ";
    static final String TAG_STOCK = "PRODUCT_STOCK";
    Button buttonPreviewAddMOre, buttonPreviewv, addmorenews;
    public static final int SIGNATURE_ACTIVITY = 1;
    AutoCompleteTextView Product_Variant;
    RecyclerView spinner_recycleview;
    Spinner_List_Adapter spinner_list_adapter;
    List<Spiner_List_Model> snlist = new ArrayList<>();
    Button list_ok;
    String response_result;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String save_flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_dealer_product_list);

        cd = new ConnectionDetector(getApplicationContext());

        Global_Data.Varient_value_add_flag = "";
        txttotalPreview = findViewById(R.id.txttotalPreviewv);

        buttonPreviewAddMOre = findViewById(R.id.buttonPreviewAddMOrev);
        buttonPreviewv = findViewById(R.id.buttonPreviewv);
        addmorenews = findViewById(R.id.addmorenews);
        list_ok = findViewById(R.id.list_ok);
        swipeListView = findViewById(R.id.example_lv_list);
        swipeListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        Product_Variant = findViewById(R.id.newProduct_varient);


        // search_adapter = new SP_AutoCompleteAdapter(this, android.R.layout.simple_dropdown_item_1line);
        // Product_Variant.setAdapter(search_adapter);
        spinner_recycleview = findViewById(R.id.spinner_recycleview);

        spinner_recycleview.setLayoutManager(new LinearLayoutManager(Sub_Dealer_ProductList.this));
        spinner_list_adapter = new Spinner_List_Adapter(Sub_Dealer_ProductList.this, snlist);
        spinner_recycleview.setAdapter(spinner_list_adapter);

        map = new HashMap<String, String>();
        SwipeList = new ArrayList<HashMap<String, String>>();


        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        SharedPreferences spf = Sub_Dealer_ProductList.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();
        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Product List");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Sub_Dealer_ProductList.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
            try {
                int target = (int) Math.round(sp.getFloat("Target", 0));
                int achieved = (int) Math.round(sp.getFloat("Achived", 0));
                Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
                if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                    int age = (int) Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
                } else {
                    int age = (int) Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }


            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        Product_Variant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Product_Variant.getRight() - Product_Variant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Sub_Dealer_ProductList.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        Product_Variant.showDropDown();
                        return true;
                    }
                }
                return false;
            }
        });

        Product_Variant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                Global_Data.hideSoftKeyboard(Sub_Dealer_ProductList.this);

                q_check = "";
                Global_Data.Order_hashmap.clear();
                p_id.clear();
                p_q.clear();
                p_price.clear();
                p_name.clear();
                p_mrp.clear();
                p_rp.clear();

                try {

                    List<Local_Data> cont1 = dbvoc.getSearchProduct_with_name(Product_Variant.getText().toString());
                    if (cont1.size() <= 0) {
                        Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent i = new Intent(Sub_Dealer_ProductList.this, NewOrderActivity.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        });

                    } else {
                        SwipeList.clear();
                        list1.clear();
                        list2.clear();
                        list3.clear();
                        pp = 0;
                        for (Local_Data cnt1 : cont1) {
                            HashMap<String, String> mapp = new HashMap<String, String>();
                            mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm());
                            mapp.put(TAG_QTY, "");
                            mapp.put(TAG_PRICE, cnt1.getMRP());
                            mapp.put(TAG_RP, cnt1.getStateName());
                            mapp.put(TAG_ITEM_NUMBER, cnt1.getCode());
                            mapp.put(TAG_ITEM_SQ, cnt1.getSQ());
                            mapp.put(TAG_ITEM_MQ, cnt1.getMQ());
                            mapp.put(TAG_STOCK, "");

                            List<Local_Data> contactsn = dbvoc.GetOrder_Product_BY_SUBORDER_ID(Global_Data.GLOvel_SUB_GORDER_ID, cnt1.getCode());

                            if (contactsn.size() > 0) {
                                for (Local_Data cn : contactsn) {

                                    list1.add(cn.get_delivery_product_order_quantity());
                                    list2.add("PRICE : " + cn.getAmount());
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getAmount())) {
                                        pp += Double.valueOf(cn.getAmount());
                                    }
                                }
                            } else {
                                list1.add("");
                                list2.add("");
                            }


                            SwipeList.add(mapp);
                        }

                        Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                            public void run() {
                                swipeListView.setItemsCanFocus(true);

                                adapter = new Sub_Dealer_ProductList_Adapter(Sub_Dealer_ProductList.this, SwipeList, list1, list2);

                                swipeListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                txttotalPreview.setText("Total : " + pp);

                            }
                        });


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });

        Product_Variant.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Product_Variant.getText().toString().trim().length() == 0) {
                    new Sub_Dealer_ProductList.VarientASN().execute();

                }

            }
        });


        dialog = new ProgressDialog(Sub_Dealer_ProductList.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Product Loading....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();

        new VarientASN().execute();

        buttonPreviewAddMOre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                requestGPSPermissionsigna();


            }
        });


        addmorenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (!Global_Data.GLOvel_SUB_GORDER_ID.equalsIgnoreCase("")) {

                    if (Global_Data.Order_hashmap.size() > 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Sub_Dealer_ProductList.this).create(); //Read Update
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Are you sure you want to Add more items without saving current list items?");
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Global_Data.GLOVEL_LONG_DESC = "";
                                Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                                Global_Data.GLOVEL_ITEM_MRP = "";
                                // Global_Data.Search_business_unit_name = "";
                                Global_Data.Search_Category_name = "";
                                Global_Data.Search_BusinessCategory_name = "";
                                Global_Data.Search_brand_name = "";
                                p_id.clear();
                                p_name.clear();
                                p_mrp.clear();
                                p_q.clear();
                                p_price.clear();
                                p_rp.clear();
                                Global_Data.Order_hashmap.clear();

                                Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                startActivity(i);
                                finish();
                            }
                        });

                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                        alertDialog.show();
                    } else {
                        Global_Data.GLOVEL_LONG_DESC = "";
                        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                        Global_Data.GLOVEL_ITEM_MRP = "";
                        // Global_Data.Search_business_unit_name = "";
                        Global_Data.Search_Category_name = "";
                        Global_Data.Search_BusinessCategory_name = "";
                        Global_Data.Search_brand_name = "";
                        p_id.clear();
                        p_name.clear();
                        p_mrp.clear();
                        p_q.clear();
                        p_price.clear();
                        p_rp.clear();
                        Global_Data.Order_hashmap.clear();

                        Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }


                    //NewOrderFragment.this.startActivity(i);
                } else {

                    if (Global_Data.Order_hashmap.size() > 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(Sub_Dealer_ProductList.this).create(); //Read Update
                        alertDialog.setTitle("Warning");
                        alertDialog.setMessage("Are you sure you want to Add more items without saving current list items?");
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Global_Data.GLOVEL_LONG_DESC = "";
                                Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                                Global_Data.GLOVEL_ITEM_MRP = "";
                                //   Global_Data.Search_business_unit_name = "";
                                Global_Data.Search_Category_name = "";
                                Global_Data.Search_BusinessCategory_name = "";
                                Global_Data.Search_brand_name = "";
                                p_id.clear();
                                p_name.clear();
                                p_mrp.clear();
                                p_q.clear();
                                p_price.clear();
                                p_rp.clear();
                                Global_Data.Order_hashmap.clear();


                                Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                startActivity(i);
                                finish();

                            }
                        });

                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });


                        alertDialog.show();
                    } else {
                        Global_Data.GLOVEL_LONG_DESC = "";
                        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                        Global_Data.GLOVEL_ITEM_MRP = "";
                        //  Global_Data.Search_business_unit_name = "";
                        Global_Data.Search_Category_name = "";
                        Global_Data.Search_BusinessCategory_name = "";
                        Global_Data.Search_brand_name = "";
                        p_id.clear();
                        p_name.clear();
                        p_mrp.clear();
                        p_q.clear();
                        p_price.clear();
                        p_rp.clear();
                        Global_Data.Order_hashmap.clear();


                        Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }


                }

            }
        });


        buttonPreviewv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (!Global_Data.GLOvel_SUB_GORDER_ID.equalsIgnoreCase("")) {


                    Global_Data.GLOVEL_LONG_DESC = "";
                    Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                    Global_Data.GLOVEL_ITEM_MRP = "";
                    //   Global_Data.Search_business_unit_name = "";
                    Global_Data.Search_Category_name = "";
                    Global_Data.Search_BusinessCategory_name = "";
                    Global_Data.Search_brand_name = "";

                    Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_PreviewActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                    finish();

                } else {

                    Toast toast = Toast.makeText(getApplicationContext(), "Please save order before preview.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        });


    }


    private class VarientASN extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            try {

                if (Global_Data.array_of_pVarient.size() > 0) {
                    StringBuilder ss = new StringBuilder();
                    String[] mStringArray = new String[Global_Data.array_of_pVarient.size()];
                    mStringArray = Global_Data.array_of_pVarient.toArray(mStringArray);
                    for (int i = 0; i < Global_Data.array_of_pVarient.size(); i++) {


                        ss.append('"' + Global_Data.array_of_pVarient.get(i) + '"');
                        if ((Global_Data.array_of_pVarient.size() - 1) != i) {
                            ss.append(",");
                        }


                    }

                    List<Local_Data> cont1 = dbvoc.getSearchProduct(ss.toString());

                    if (cont1.size() <= 0) {
                        // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                        Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        });

                    } else {
                        resultsvarient.clear();
                        SwipeList.clear();
                        list1.clear();
                        list2.clear();
                        pp = 0;
                        for (Local_Data cnt1 : cont1) {
                            HashMap<String, String> mapp = new HashMap<String, String>();
                            mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm());
                            mapp.put(TAG_QTY, "");
                            mapp.put(TAG_PRICE, cnt1.getMRP());
                            mapp.put(TAG_RP, cnt1.getStateName());
                            mapp.put(TAG_ITEM_NUMBER, cnt1.getCode());
                            mapp.put(TAG_ITEM_SQ, cnt1.getSQ());
                            mapp.put(TAG_ITEM_MQ, cnt1.getMQ());
                            mapp.put(TAG_STOCK, "");



                            if(!Global_Data.GLOvel_SUB_GORDER_ID.equalsIgnoreCase(""))
                            {
                                List<Local_Data> contactsn = dbvoc.GetSUBOrder_Product_BY_ORDER_ID(Global_Data.GLOvel_SUB_GORDER_ID, cnt1.getCode());
                                if (contactsn.size() > 0) {
                                    for (Local_Data cn : contactsn) {

                                        list1.add(cn.get_delivery_product_order_quantity());
                                        list2.add("PRICE : " + cn.getAmount());
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getAmount())) {
                                            pp += Double.valueOf(cn.getAmount());
                                        }
                                    }
                                } else {
                                    list1.add("");
                                    list2.add("");
                                }
                            }
                            else
                            {
                                list1.add("");
                                list2.add("");
                            }



                            //  Log.d("ITEM_NUMBER N", "ITEM_NUMBER N" + cnt1.getCode());

                            resultsvarient.add(cnt1.getProduct_variant());


                            SwipeList.add(mapp);
                        }

                        Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                            public void run() {
                                swipeListView.setItemsCanFocus(true);

                                adapter = new Sub_Dealer_ProductList_Adapter(Sub_Dealer_ProductList.this, SwipeList, list1, list2);

                                swipeListView.setAdapter(adapter);

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Sub_Dealer_ProductList.this, android.R.layout.simple_spinner_dropdown_item, resultsvarient);
                                Product_Variant.setThreshold(1);// will start working from
                                // first character
                                Product_Variant.setAdapter(adapter);// setting the adapter
                                // data into the
                                // AutoCompleteTextView
                                Product_Variant.setTextColor(Color.BLACK);
                                 txttotalPreview.setText("Total : " + pp);


                            }
                        });


                    }
                } else {
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_business_unit_name) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_Category_name)) {

                        List<Local_Data> cont1 = dbvoc.getProductvarientbycategoryandproduct(Global_Data.Search_business_unit_name, Global_Data.Search_Category_name, Global_Data.Search_BusinessCategory_name, Global_Data.Search_brand_name);

                        if (cont1.size() <= 0) {
                            // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            });

                        } else {
                            resultsvarient.clear();
                            SwipeList.clear();
                            list1.clear();
                            list2.clear();
                            pp = 0;
                            for (Local_Data cnt1 : cont1) {
                                HashMap<String, String> mapp = new HashMap<String, String>();
                                mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm());
                                mapp.put(TAG_QTY, "");
                                mapp.put(TAG_PRICE, cnt1.getMRP());
                                mapp.put(TAG_RP, cnt1.getStateName());
                                mapp.put(TAG_ITEM_NUMBER, cnt1.getCode());
                                mapp.put(TAG_ITEM_SQ, cnt1.getSQ());
                                mapp.put(TAG_ITEM_MQ, cnt1.getMQ());
                                mapp.put(TAG_STOCK, "");
                                //  Log.d("ITEM_NUMBER N", "ITEM_NUMBER N" + cnt1.getCode());

                                resultsvarient.add(cnt1.getProduct_variant());
                                if(!Global_Data.GLOvel_SUB_GORDER_ID.equalsIgnoreCase(""))
                                {
                                    List<Local_Data> contactsn = dbvoc.GetSUBOrder_Product_BY_ORDER_ID(Global_Data.GLOvel_SUB_GORDER_ID, cnt1.getCode());
                                    if (contactsn.size() > 0) {
                                        for (Local_Data cn : contactsn) {

                                            list1.add(cn.get_delivery_product_order_quantity());
                                            list2.add("PRICE : " + cn.getAmount());
                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getAmount())) {
                                                pp += Double.valueOf(cn.getAmount());
                                            }
                                        }
                                    } else {
                                        list1.add("");
                                        list2.add("");
                                    }
                                }
                                else
                                {
                                    list1.add("");
                                    list2.add("");
                                }

                                SwipeList.add(mapp);
                            }

                            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    swipeListView.setItemsCanFocus(true);

                                    adapter = new Sub_Dealer_ProductList_Adapter(Sub_Dealer_ProductList.this, SwipeList, list1, list2);

                                    swipeListView.setAdapter(adapter);

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Sub_Dealer_ProductList.this, android.R.layout.simple_spinner_dropdown_item, resultsvarient);
                                    Product_Variant.setThreshold(1);// will start working from
                                    // first character
                                    Product_Variant.setAdapter(adapter);// setting the adapter
                                    // data into the
                                    // AutoCompleteTextView
                                    Product_Variant.setTextColor(Color.BLACK);
                                      txttotalPreview.setText("Total : " + pp);


                                }
                            });


                        }
                    } else {

                        List<Local_Data> cont1 = dbvoc.getProductvarient();

                        if (cont1.size() <= 0) {
                            // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            });

                        } else {
                            resultsvarient.clear();
                            SwipeList.clear();
                            pp = 0;
                            for (Local_Data cnt1 : cont1) {
                                HashMap<String, String> mapp = new HashMap<String, String>();
                                mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm() + " RP : " + cnt1.getStateName() + " MRP : " + cnt1.getMRP());
                                mapp.put(TAG_QTY, "");
                                mapp.put(TAG_PRICE, cnt1.getMRP());
                                mapp.put(TAG_ITEM_NUMBER, cnt1.getCode());
                                mapp.put(TAG_ITEM_SQ, cnt1.getSQ());
                                mapp.put(TAG_ITEM_MQ, cnt1.getMQ());
                                mapp.put(TAG_STOCK, "");
                                list1.add("");
                                list2.add("");

                                SwipeList.add(mapp);
                            }

                            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    swipeListView.setItemsCanFocus(true);

                                    adapter = new Sub_Dealer_ProductList_Adapter(Sub_Dealer_ProductList.this, SwipeList, list1, list2);

                                    swipeListView.setAdapter(adapter);
                                    //  txttotalPreview.setText("Total : " + pp);


                                }
                            });


                        }

                    }
                }


                dialog.dismiss();

            } catch (Exception ex) {
                ex.printStackTrace();
                dialog.dismiss();
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();
            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


    }

    private class Varientsave extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            try {

                Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                    public void run() {
                        View parentView = null;

                        if (!(Global_Data.Order_hashmap.isEmpty())) {

                            try {
                                for (Object name : Global_Data.Order_hashmap.keySet()) {

                                    Object key = name.toString();
                                    Object value = Global_Data.Order_hashmap.get(name);
                                    //System.out.println(key + " " + value);
                                    Log.d("KEY", "Key: " + key + " Value: " + value);
                                    JSONObject item = new JSONObject();

                                    String key_array[] = String.valueOf(key).split("&");
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(String.valueOf(value))) {

                                        String key_value_array[] = String.valueOf(value).split("pq");
                                        String key_value_price_array[] = key_value_array[1].split("pprice");
                                        String key_value_pname_array[] = key_value_price_array[1].split("pmrp");
                                        String key_value_pmrp_array[] = key_value_pname_array[1].split("prp");

                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(key_value_price_array[0])) {
                                            q_check = "yes";
                                            p_id.add(key_array[1]);
                                            p_q.add(key_value_array[0]);
                                            p_price.add(key_value_price_array[0]);
                                            p_name.add(key_value_pname_array[0]);
                                            p_mrp.add(key_value_pmrp_array[0]);
                                            p_rp.add(key_value_pmrp_array[1]);
                                            Log.d("quantity", "quantity" + key_value_array[0]);
                                        }


                                    }
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }

                    }
                });


            } catch (Exception ex) {
                ex.printStackTrace();
                dialog.dismiss();
            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            if (!p_id.isEmpty() && q_check.equalsIgnoreCase("yes")) {

//                cd = new ConnectionDetector(Sub_Dealer_ProductList.this);
//                isInternetPresent = cd.isConnectingToInternet();
//                if (isInternetPresent) {

                    try {
                        AppLocationManager appLocationManager = new AppLocationManager(Sub_Dealer_ProductList.this);
                        Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                        Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                        PlayService_Location PlayServiceManager = new PlayService_Location(Sub_Dealer_ProductList.this);

                        if (PlayServiceManager.checkPlayServices(Sub_Dealer_ProductList.this)) {
                            Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                        }

                        if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck_b(Global_Data.GLOvel_LATITUDE) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck_b(Global_Data.GLOvel_LONGITUDE)) {
                            LocationAddress locationAddress = new LocationAddress();
                            LocationAddress.getAddressFromLocation(Double.valueOf(Global_Data.GLOvel_LATITUDE), Double.valueOf(Global_Data.GLOvel_LONGITUDE),
                                    getApplicationContext(), new GeocoderHandler());
                        }

                        String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());

                        if (Global_Data.GLOvel_SUB_GORDER_ID.equalsIgnoreCase("") ||  Global_Data.statusOrderActivity.equalsIgnoreCase("Yes")) {

                            Global_Data.GLOvel_SUB_GORDER_ID = "R" + PINString;


                            loginDataBaseAdapter.insertSUBOrders("", Global_Data.GLOvel_SUB_GORDER_ID, Global_Data.GLOvel_USER_EMAIL, Global_Data.Sub_Dealer_Code, Global_Data.SUB_Mobile, Global_Data.Sub_Email, Global_Data.Dealer_Code, Global_Data.address, Global_Data.Sub_shop_name, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "", "", "", "", "", "", "", "", "", "", "");

                            if (Global_Data.statusOrderActivity.equalsIgnoreCase("Yes")) {

                                dbvoc.update_ORDER_ID_SUBDEALER(Global_Data.GLOvel_SUB_GORDER_ID);
                            }

                        }

                        Global_Data.statusOrderActivity = "";

                        Double pp = 0.0;
                        try {
                            for (int k = 0; k < p_id.size(); k++) {


                                List<Local_Data> contactsn = dbvoc.GetSUB_Orders_BY_ORDER_ID(Global_Data.GLOvel_SUB_GORDER_ID, p_id.get(k));

                                if (contactsn.size() > 0) {

                                    for (Local_Data cn : contactsn) {

                                        if (!(p_q.get(k).equalsIgnoreCase(cn.get_delivery_product_order_quantity())) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k))) {
                                            dbvoc.update_itemamountandquantity_subDealer(String.valueOf(p_q.get(k)), String.valueOf(p_price.get(k)), p_id.get(k), Global_Data.GLOvel_SUB_GORDER_ID);
                                        } else if (!(p_q.get(k).equalsIgnoreCase(cn.get_delivery_product_order_quantity())) && !(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k)))) {
                                            dbvoc.getDeleteTableSuborderproduct_byITEM_NUMBER(p_id.get(k), Global_Data.GLOvel_SUB_GORDER_ID);
                                        }
                                    }
                                } else {

                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k))) {
                                        loginDataBaseAdapter.insertSUb_OrderProducts(" ", " ", Global_Data.GLOvel_SUB_GORDER_ID, "", Global_Data.Search_Category_name, Global_Data.Search_Product_name, p_name.get(k), " ", "", " ", "", p_q.get(k), p_rp.get(k), p_mrp.get(k), p_price.get(k), "", "", Global_Data.order_retailer, " ", p_id.get(k), " ", p_name.get(k));//Reading all
                                    }


                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k))) {
                                    pp += Double.valueOf(p_price.get(k));
                                }
                            }

                            q_check = "";
                            Global_Data.Order_hashmap.clear();
                            p_id.clear();
                            p_q.clear();
                            p_price.clear();
                            p_name.clear();
                            p_mrp.clear();
                            p_rp.clear();

                            runOnUiThread(new Runnable() {
                                public void run() {

                                    if(save_flag.equalsIgnoreCase(""))
                                    {
                                        save_flag = "Yes";
                                        Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Items add successfully", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                    else
                                    {
                                        Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Items Updated successfully", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }



                                    buttonPreviewAddMOre.setEnabled(true);
                                    buttonPreviewAddMOre.setText("Save");


                                }
                            });


                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


//                } else {
//                    dialog.dismiss();
//                    Toast.makeText(Sub_Dealer_ProductList.this, "You don't have internet connection.", Toast.LENGTH_SHORT).show();
//                }

            } else {

                dialog.dismiss();
                q_check = "";
                Global_Data.Order_hashmap.clear();
                p_id.clear();
                p_q.clear();
                p_price.clear();
                p_name.clear();
                p_mrp.clear();
                p_rp.clear();
                buttonPreviewAddMOre.setEnabled(true);
                buttonPreviewAddMOre.setText("Save");

                if(save_flag.equalsIgnoreCase(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter quantity. ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    Toast toast = Toast.makeText(Sub_Dealer_ProductList.this, "Items Updated successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }


            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

        }

        @Override
        protected void onPreExecute() {


            Sub_Dealer_ProductList.this.runOnUiThread(new Runnable() {
                public void run() {


                    buttonPreviewAddMOre.setEnabled(false);
                    buttonPreviewAddMOre.setText("Wait...");
                    if (dialog == null)
                        dialog = new ProgressDialog(Sub_Dealer_ProductList.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    dialog.setMessage("Please wait Product Loading....");
                    dialog.setTitle("Smart Anchor App");
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void updateSum(Double sum) {

        // txttotalPreview.setText("Total		:		"+sum);
        Double total = 0.0;

        if (!(Global_Data.Order_hashmap.isEmpty())) {

            try {
                for (Object name : Global_Data.Order_hashmap.keySet()) {

                    Object key = name.toString();
                    Object value = Global_Data.Order_hashmap.get(name);
                    //System.out.println(key + " " + value);
                    Log.d("KEY", "Key: " + key + " Value: " + value);
                    JSONObject item = new JSONObject();

                    String key_array[] = String.valueOf(key).split("&");
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(String.valueOf(value))) {

                        String key_value_array[] = String.valueOf(value).split("pq");
                        String key_value_price_array[] = key_value_array[1].split("pprice");


                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(key_value_price_array[0])) {

                            total += Double.valueOf(key_value_price_array[0]);


                        }


                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            txttotalPreview.setText("Total		:		" + total);

        } else {
            txttotalPreview.setText("Total		:		" + 0.0);
        }


    }


    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public void onBackPressed() {

        if (Global_Data.Order_hashmap.size() > 0) {

            AlertDialog alertDialog = new AlertDialog.Builder(Sub_Dealer_ProductList.this).create(); //Read Update
            alertDialog.setTitle("Warning");
            alertDialog.setMessage("Are you sure you want to discard the entered quantity?");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Global_Data.GLOVEL_LONG_DESC = "";
                    Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                    Global_Data.GLOVEL_ITEM_MRP = "";
                    //  Global_Data.Search_business_unit_name = "";
                    Global_Data.Search_Category_name = "";
                    Global_Data.Search_BusinessCategory_name = "";
                    Global_Data.Search_brand_name = "";
                    p_id.clear();
                    p_name.clear();
                    p_mrp.clear();
                    p_q.clear();
                    p_price.clear();
                    p_rp.clear();
                    Global_Data.Order_hashmap.clear();

                    Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });


            alertDialog.show();


        } else {
            Global_Data.GLOVEL_LONG_DESC = "";
            Global_Data.GLOVEL_CATEGORY_SELECTION = "";
            Global_Data.GLOVEL_ITEM_MRP = "";
            // Global_Data.Search_business_unit_name = "";
            Global_Data.Search_Category_name = "";
            Global_Data.Search_BusinessCategory_name = "";
            Global_Data.Search_brand_name = "";
            p_id.clear();
            p_name.clear();
            p_mrp.clear();
            p_q.clear();
            p_price.clear();
            p_rp.clear();
            Global_Data.Order_hashmap.clear();

            Intent i = new Intent(Sub_Dealer_ProductList.this, SubDealer_NewOrderActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

    }

    private void requestGPSPermissionsigna() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            p_id.clear();
                            p_name.clear();
                            p_mrp.clear();
                            p_q.clear();
                            p_price.clear();
                            p_rp.clear();
                            q_check = "";

                            gps = new GPSTracker(Sub_Dealer_ProductList.this);
                            if (!gps.canGetLocation()) {

                                gps.showSettingsAlertnew();
                            } else {

                                new Varientsave().execute();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress = "";
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    //locationAddress = " ";
            }
            //  LOCATION.setText(locationAddress);


            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(locationAddress)) {
                Global_Data.address = locationAddress;
                Log.d("GLOBEL ADDRESS G", "V" + locationAddress);

            } else {
                Global_Data.address = "";
                Log.d("GLOBEL ADDRESS G", "address not found.");
            }


        }
    }
}
