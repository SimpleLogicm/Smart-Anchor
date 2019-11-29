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
import android.provider.Settings;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.ProductSearchAdapter;
import com.anchor.model.Product;
import com.anchor.swipelistview.sample.adapters.Product_AllVarient_Adapter;
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

public class ProductAll_Varients extends Activity {

    int pp = 0;
    GPSTracker gps;
    String q_check = "";
    ProgressDialog dialog;
    String str;
    HashMap<String, String> map;
    ArrayList<String> list1 = new ArrayList<String>();
    ArrayList<String> list2 = new ArrayList<String>();
    ArrayList<String> Glist1 = new ArrayList<String>();
    ArrayList<String> Glist2 = new ArrayList<String>();
    Boolean isInternetPresent = false;

    private   ArrayList<String> p_id = new ArrayList<String>();
    private ArrayList<String> p_name = new ArrayList<String>();
    private ArrayList<String> p_mrp = new ArrayList<String>();
    private ArrayList<String> p_rp = new ArrayList<String>();
    private ArrayList<String> p_q = new ArrayList<String>();
    private ArrayList<String> p_price = new ArrayList<String>();
    private ArrayList<String> resultsvarient = new ArrayList<String>();


    ConnectionDetector cd;
    ArrayList<HashMap<String, String>> SwipeList;
    ArrayList<HashMap<String, String>> GSwipeList;
    ArrayList<String> Amount_tp = new ArrayList<String>();
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private static final int REQUEST_CODE_SETTINGS = 0;
    private ArrayList<String> Distributer_list = new ArrayList<String>();
    private Product_AllVarient_Adapter adapter;
    private ArrayList<Product> dataOrder;
    LoginDataBaseAdapter loginDataBaseAdapter;
    private ListView swipeListView;
    TextView textView1, tabletextview1, tabletextview2, tabletextview3;
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

    ImageView imgView;
    static float totalPrice;
    String statusOrderActivity = "";
    Button  buttonPreviewAddMOre, buttonPreviewHome, addmorenews;
    public static final int SIGNATURE_ACTIVITY = 1;
    AutoCompleteTextView Product_Variant;
    ProductSearchAdapter productSearchAdapter;



    boolean firstLaunch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_all_varients);

        cd = new ConnectionDetector(getApplicationContext());

        Global_Data.Varient_value_add_flag = "";

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        txttotalPreview = findViewById(R.id.txttotalPreviewv);

        buttonPreviewAddMOre = findViewById(R.id.buttonPreviewAddMOrev);
        buttonPreviewHome = findViewById(R.id.buttonPreviewv);
        addmorenews = findViewById(R.id.addmorenews);
        swipeListView = findViewById(R.id.example_lv_list);
        swipeListView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        Product_Variant = findViewById(R.id.newProduct_varient);
        map = new HashMap<String, String>();

        //txttotalPreview.setText("Total		:		"+"");

        SwipeList = new ArrayList<HashMap<String, String>>();
        GSwipeList = new ArrayList<HashMap<String, String>>();


        productSearchAdapter = new ProductSearchAdapter(this, android.R.layout.simple_dropdown_item_1line);
        Product_Variant.setAdapter(productSearchAdapter);


        SharedPreferences spf = ProductAll_Varients.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();
        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Product List");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = ProductAll_Varients.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
            try {
                int target = Math.round(sp.getFloat("Target", 0));
                int achieved = Math.round(sp.getFloat("Achived", 0));
                Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
                if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
                } else {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }


            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}


        Product_Variant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (Product_Variant.getRight() - Product_Variant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = ProductAll_Varients.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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

                Global_Data.hideSoftKeyboard(ProductAll_Varients.this);

                q_check = "";
                Global_Data.Order_hashmap.clear();
                p_id.clear();
                p_q.clear();
                p_price.clear();
                p_name.clear();
                p_mrp.clear();
                p_rp.clear();

                try
                {

                    List<Local_Data> cont1 = dbvoc.getSearchProduct_with_name(Product_Variant.getText().toString());
//
//                        cont1 = dbvoc.getProductvarientbyname(Global_Data.Search_business_unit_name,Global_Data.Search_Category_name,Global_Data.Search_BusinessCategory_name,Global_Data.Search_brand_name,Product_Variant.getText().toString());



                    if (cont1.size() <= 0) {
                        // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                        ProductAll_Varients.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(ProductAll_Varients.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        });

                    } else {
                        GSwipeList.clear();
                        Glist1.clear();
                        Glist2.clear();
                        pp=0;
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
                         //   Log.d("ITEM_NUMBER N", "ITEM_NUMBER N" + cnt1.getCode());



                            List<Local_Data> contactsn = dbvoc.GetOrder_Product_BY_ORDER_ID(Global_Data.GLObalOrder_id, cnt1.getCode());

                            if (contactsn.size() > 0) {
                                for (Local_Data cn : contactsn) {

                                    Glist1.add(cn.get_delivery_product_order_quantity());
                                    Glist2.add("PRICE : " + cn.getAmount());
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getAmount())) {
                                        pp += Double.valueOf(cn.getAmount());
                                    }
                                }
                            } else {
                                Glist1.add("");
                                Glist2.add("");
                            }

                            GSwipeList.add(mapp);
                        }

                        ProductAll_Varients.this.runOnUiThread(new Runnable() {
                            public void run() {
                                swipeListView.setItemsCanFocus(true);

                                adapter = new Product_AllVarient_Adapter(ProductAll_Varients.this, GSwipeList, Glist1, Glist2);

                                swipeListView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                txttotalPreview.setText("Total : " + pp);

                            }
                        });



                    }
                }catch (Exception ex){ex.printStackTrace();}




            }
        });

        Product_Variant.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(Product_Variant.getText().toString().trim().length() == 0) {
                    //new VarientASN().execute();

                    adapter = new Product_AllVarient_Adapter(ProductAll_Varients.this, SwipeList, list1, list2);
                    swipeListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }
        });


        dialog = new ProgressDialog(ProductAll_Varients.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Product Loading....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();

        new VarientASN().execute();

        buttonPreviewAddMOre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String buttonText = ((Button) arg0).getText().toString();

                if (buttonText.equalsIgnoreCase("Add More")) {
                    p_id.clear();
                    p_name.clear();
                    p_mrp.clear();
                    p_q.clear();
                    p_price.clear();
                    p_rp.clear();
                    Global_Data.Order_hashmap.clear();

                    Global_Data.GLOVEL_LONG_DESC = "";
                    Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                    Global_Data.GLOVEL_ITEM_MRP = "";
                   // Global_Data.Search_business_unit_name = "";
                    Global_Data.Search_Category_name = "";
                    Global_Data.Search_BusinessCategory_name = "";
                    Global_Data.Search_brand_name = "";

                    Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();

                } else {



                    requestGPSPermissionsigna();
                }


            }
        });

        addmorenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (!Global_Data.GLOvel_GORDER_ID.equalsIgnoreCase("")) {

                    if (Global_Data.Order_hashmap.size() > 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ProductAll_Varients.this).create(); //Read Update
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

                                Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
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

                        Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }


                    //NewOrderFragment.this.startActivity(i);
                } else {

                    if (Global_Data.Order_hashmap.size() > 0) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ProductAll_Varients.this).create(); //Read Update
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



                                Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
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
                    }
                    else
                    {
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



                        Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }



                }

            }
        });

        buttonPreviewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (!Global_Data.GLOvel_GORDER_ID.equalsIgnoreCase("")) {

//                    if (p_id.isEmpty()) {
//                        AlertDialog alertDialog = new AlertDialog.Builder(ProductAll_Varients.this).create(); //Read Update
//                        alertDialog.setTitle("Warning");
//                        alertDialog.setMessage("Are you sure you want to preview order without saving current list items?");
//                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                Global_Data.GLOVEL_LONG_DESC = "";
//                                Global_Data.GLOVEL_CATEGORY_SELECTION = "";
//                                Global_Data.GLOVEL_ITEM_MRP = "";
//                                Global_Data.Search_Category_name = "";
//
//                                Global_Data.Search_Product_name = "";
//
//                                Intent i = new Intent(ProductAll_Varients.this, PreviewOrderSwipeActivity.class);
//                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                startActivity(i);
//                                finish();
//                            }
//                        });
//
//                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//
//                        alertDialog.show();
//                    } else {
                        Global_Data.GLOVEL_LONG_DESC = "";
                        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                        Global_Data.GLOVEL_ITEM_MRP = "";
                     //   Global_Data.Search_business_unit_name = "";
                        Global_Data.Search_Category_name = "";
                        Global_Data.Search_BusinessCategory_name = "";
                        Global_Data.Search_brand_name = "";

                        Intent i = new Intent(ProductAll_Varients.this, PreviewOrderSwipeActivity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                 //   }


                    //NewOrderFragment.this.startActivity(i);
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

                if(Global_Data.array_of_pVarient.size() >0)
                {
                    StringBuilder ss = new StringBuilder();
                    String[] mStringArray = new String[Global_Data.array_of_pVarient.size()];
                    mStringArray = Global_Data.array_of_pVarient.toArray(mStringArray);
                    for(int i=0; i<Global_Data.array_of_pVarient.size(); i++)
                    {


                        ss.append('"' +Global_Data.array_of_pVarient.get(i)+ '"');
                        if((Global_Data.array_of_pVarient.size()-1) != i)
                        {
                            ss.append(",");
                        }


                    }

                    Global_Data.Product_Array = ss.toString();
                    List<Local_Data> cont1 = dbvoc.getSearchProduct(ss.toString());

                    if (cont1.size() <= 0) {
                        // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                        ProductAll_Varients.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(ProductAll_Varients.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
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
                        pp=0;
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

                           // resultsvarient.add(cnt1.getProduct_variant());

                            List<Local_Data> contactsn = dbvoc.GetOrder_Product_BY_ORDER_ID(Global_Data.GLObalOrder_id, cnt1.getCode());


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

                        ProductAll_Varients.this.runOnUiThread(new Runnable() {
                            public void run() {
                                swipeListView.setItemsCanFocus(true);

                                adapter = new Product_AllVarient_Adapter(ProductAll_Varients.this, SwipeList, list1, list2);

                                swipeListView.setAdapter(adapter);

//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductAll_Varients.this,android.R.layout.simple_spinner_dropdown_item,resultsvarient);
//                                Product_Variant.setThreshold(1);// will start working from
//                                // first character
//                                Product_Variant.setAdapter(adapter);// setting the adapter
//                                // data into the
//                                // AutoCompleteTextView
//                                Product_Variant.setTextColor(Color.BLACK);
                                txttotalPreview.setText("Total : " + pp);


                            }
                        });



                    }
                }
                else
                {
//                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_business_unit_name) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_Category_name)) {

                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_business_unit_name)) {

//                        List<Local_Data> cont1 = dbvoc.getProductvarientbycategoryandproduct(Global_Data.Search_business_unit_name,Global_Data.Search_Category_name,Global_Data.Search_BusinessCategory_name,Global_Data.Search_brand_name);

                        List<Local_Data> cont1 = dbvoc.getProductvarientWithoutCategory(Global_Data.Search_business_unit_name,Global_Data.Search_BusinessCategory_name,Global_Data.Search_brand_name);

                        if (cont1.size() <= 0) {
                            // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                            ProductAll_Varients.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(ProductAll_Varients.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
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
                            pp=0;
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

                               // resultsvarient.add(cnt1.getProduct_variant());

                                List<Local_Data> contactsn = dbvoc.GetOrder_Product_BY_ORDER_ID(Global_Data.GLObalOrder_id, cnt1.getCode());


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

                            ProductAll_Varients.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    swipeListView.setItemsCanFocus(true);

                                    adapter = new Product_AllVarient_Adapter(ProductAll_Varients.this, SwipeList, list1, list2);

                                    swipeListView.setAdapter(adapter);

//                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProductAll_Varients.this,android.R.layout.simple_spinner_dropdown_item,resultsvarient);
//                                    Product_Variant.setThreshold(1);// will start working from
//                                    // first character
//                                    Product_Variant.setAdapter(adapter);// setting the adapter
//                                    // data into the
//                                    // AutoCompleteTextView
//                                    Product_Variant.setTextColor(Color.BLACK);
                                    txttotalPreview.setText("Total : " + pp);


                                }
                            });



                        }
                    } else {

                        List<Local_Data> cont1 = dbvoc.getProductvarient();

                        if (cont1.size() <= 0) {
                            // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                            ProductAll_Varients.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast toast = Toast.makeText(ProductAll_Varients.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();
                                }
                            });

                        } else {
                            resultsvarient.clear();
                            SwipeList.clear();
                            pp=0;
                            for (Local_Data cnt1 : cont1) {
                                HashMap<String, String> mapp = new HashMap<String, String>();
                                mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm() + " RP : " + cnt1.getStateName() + " MRP : " + cnt1.getMRP());
                                mapp.put(TAG_QTY, "");
                                mapp.put(TAG_PRICE, cnt1.getMRP());
                                mapp.put(TAG_ITEM_NUMBER, cnt1.getCode());
                                mapp.put(TAG_ITEM_SQ, cnt1.getSQ());
                                mapp.put(TAG_ITEM_MQ, cnt1.getMQ());
                                mapp.put(TAG_STOCK, "");
                                //  Log.d("ITEM_NUMBER N", "ITEM_NUMBER N" + cnt1.getCode());

                                List<Local_Data> contactsn = dbvoc.GetOrder_Product_BY_ORDER_ID(Global_Data.GLObalOrder_id, cnt1.getCode());

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

                            ProductAll_Varients.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    swipeListView.setItemsCanFocus(true);

                                    adapter = new Product_AllVarient_Adapter(ProductAll_Varients.this, SwipeList, list1, list2);

                                    swipeListView.setAdapter(adapter);
                                    txttotalPreview.setText("Total : " + pp);


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
            ProductAll_Varients.this.runOnUiThread(new Runnable() {
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

                ProductAll_Varients.this.runOnUiThread(new Runnable() {
                    public void run() {
                        View parentView = null;

                        if(!(Global_Data.Order_hashmap.isEmpty())) {

                            try
                            {
                                for (Object name : Global_Data.Order_hashmap.keySet()) {

                                    Object key = name.toString();
                                    Object value = Global_Data.Order_hashmap.get(name);
                                    //System.out.println(key + " " + value);
                                    Log.d("KEY", "Key: " + key + " Value: " + value);
                                    JSONObject item = new JSONObject();

                                    String[] key_array = String.valueOf(key).split("&");
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(String.valueOf(value))) {

                                        String[] key_value_array = String.valueOf(value).split("pq");
                                        String[] key_value_price_array = key_value_array[1].split("pprice");
                                        String[] key_value_pname_array = key_value_price_array[1].split("pmrp");
                                        String[] key_value_pmrp_array = key_value_pname_array[1].split("prp");

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
                            }catch(Exception ex){ex.printStackTrace();}

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

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();

            if (!p_id.isEmpty() && q_check.equalsIgnoreCase("yes")) {

               // Long randomPIN = System.currentTimeMillis();
                String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
                if (Global_Data.GLOvel_GORDER_ID.equalsIgnoreCase("")) {
                    if (Global_Data.sales_btnstring.equalsIgnoreCase("Secondary Sales / Retail Sales")) {
                        Global_Data.GLObalOrder_id = PINString;
                        Global_Data.GLOvel_GORDER_ID = PINString;
                    } else {
                        Global_Data.GLObalOrder_id =  PINString;
                        Global_Data.GLOvel_GORDER_ID =  PINString;
                    }

                    try {
                        AppLocationManager appLocationManager = new AppLocationManager(ProductAll_Varients.this);
                        Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                        Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                        PlayService_Location PlayServiceManager = new PlayService_Location(ProductAll_Varients.this);

                        if (PlayServiceManager.checkPlayServices(ProductAll_Varients.this)) {
                            Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    List<Local_Data> checkq = dbvoc.checkOrderExist(Global_Data.GLOvel_CUSTOMER_ID,Global_Data.GLObalOrder_id);

                    if (checkq.size() <= 0) {
                        loginDataBaseAdapter.insertOrders("", Global_Data.GLOvel_GORDER_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "", Global_Data.order_retailer, Global_Data.order_state, Global_Data.order_city, Global_Data.sales_btnstring, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, Global_Data.Glovel_BEAT_ID, "", "", "", "", "", "", "","","");
                    }

                }


                Double pp = 0.0;
                try {
                    for (int k = 0; k < p_id.size(); k++) {


                        List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLObalOrder_id, p_id.get(k));

                        if (contactsn.size() > 0) {

                            for (Local_Data cn : contactsn) {

//                                if(Global_Data.Varient_value_add_flag.equalsIgnoreCase("yes"))
//                                {

                                if (!(p_q.get(k).equalsIgnoreCase(cn.get_delivery_product_order_quantity())) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k))) {
                                    dbvoc.update_itemamountandquantity(String.valueOf(p_q.get(k)), String.valueOf(p_price.get(k)), p_id.get(k), Global_Data.GLObalOrder_id);
                                } else if (!(p_q.get(k).equalsIgnoreCase(cn.get_delivery_product_order_quantity())) && !(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k)))) {
                                    dbvoc.getDeleteTableorderproduct_byITEM_NUMBER(p_id.get(k), Global_Data.GLObalOrder_id);
                                }

//                                }
//                                else
//                                {
//                                    int quantity = Integer.parseInt(cn.get_delivery_product_order_quantity()) + Integer.parseInt(p_q.get(k));
//                                    Double amount = Double.valueOf(cn.getAmount()) + Double.valueOf(p_price.get(k));
//                                    dbvoc.update_itemamountandquantity(String.valueOf(quantity), String.valueOf(amount), p_id.get(k), Global_Data.GLObalOrder_id);
//                                }

                            }
                        } else {

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k))) {
                                loginDataBaseAdapter.insertOrderProducts(" ", " ", Global_Data.GLOvel_GORDER_ID, "", Global_Data.Search_Category_name, Global_Data.Search_Product_name, p_name.get(k), " ", "", " ", "", p_q.get(k), p_rp.get(k), p_mrp.get(k), p_price.get(k), "", "", Global_Data.order_retailer, " ", p_id.get(k), " ", p_name.get(k));//Reading all

                               // Log.d("pPRIZE","Pprize"+ p_price.get(k));
                            }


                        }

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(p_q.get(k))) {
                            pp += Double.valueOf(p_price.get(k));
                        }



                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                buttonPreviewAddMOre.setEnabled(true);
                buttonPreviewAddMOre.setText("Save");
                txttotalPreview.setText("Total : " + pp);
                Global_Data.Varient_value_add_flag = "yes";

                List<Local_Data> checkq = dbvoc.getItemName(Global_Data.GLObalOrder_id);

                if (checkq.size() <= 0) {
                    q_check = "";
                    Global_Data.Order_hashmap.clear();
                    p_id.clear();
                    p_q.clear();
                    p_price.clear();
                    p_name.clear();
                    p_mrp.clear();
                    p_rp.clear();
                    // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                    ProductAll_Varients.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast toast = Toast.makeText(ProductAll_Varients.this, "All item delete, Please add new item in order.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Product_Variant.setText("");

                            Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            finish();
                        }
                    });

                } else {
                    pp = 0.0;
                    for(Local_Data qtr : checkq)
                    {
                        pp += Double.valueOf(qtr.getAmount());
                    }
                    txttotalPreview.setText("Total : " + pp);
                    q_check = "";
                    Global_Data.Order_hashmap.clear();

                    if(!Product_Variant.getText().toString().equalsIgnoreCase(""))
                    {
                        Product_Variant.setText("");
                    }

                    p_id.clear();
                    p_q.clear();
                    p_price.clear();
                    p_name.clear();
                    p_mrp.clear();
                    p_rp.clear();
                    Toast toast = Toast.makeText(ProductAll_Varients.this, "Items add successfully", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }



            } else {

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
                Toast toast = Toast.makeText(getApplicationContext(), "Please enter quantity. ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }


            ProductAll_Varients.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

        }

        @Override
        protected void onPreExecute() {


            ProductAll_Varients.this.runOnUiThread(new Runnable() {
                public void run() {


//          dialog.setMessage("Please wait....");
//          dialog.setTitle("Siyaram App");
//          dialog.setCancelable(false);
//          dialog.show();

                    buttonPreviewAddMOre.setEnabled(false);
                    buttonPreviewAddMOre.setText("Wait...");
                    //int pic = R.drawable.round_btngray;
                    // buttonPreviewAddMOre.setBackgroundResource(pic);
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
        //txttotalPreview.setText("Total		:		"+sum);
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

            AlertDialog alertDialog = new AlertDialog.Builder(ProductAll_Varients.this).create(); //Read Update
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

                    Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
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

            Intent i = new Intent(ProductAll_Varients.this, NewOrderActivity.class);
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

                            gps = new GPSTracker(ProductAll_Varients.this);
                            if(!gps.canGetLocation()){

                                gps.showSettingsAlertnew();
                            }
                            else
                            {
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


}
