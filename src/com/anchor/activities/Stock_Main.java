package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.helper.MyPeriodicwork;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cpm.simplelogic.helper.ContactInfo;
import cpm.simplelogic.helper.Stock_Info;


public class Stock_Main extends BaseActivity {

    int warehouse_flagnew = 0;
    int product_flagnew = 0;
    int check=0;
    int check_product=0;
    int check_ProductSpec=0;
    String response_result = "";

    ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct;
    //ArrayList productList = new ArrayList();
    List<String> listProduct,listProductSpec;
    List<String> listScheme;
    ProgressDialog dialog;
    ArrayList<String> All_Product = new ArrayList<String>();
    ArrayList<String> All_Warehouse = new ArrayList<String>();
    ArrayList<String> All_ProductDefault = new ArrayList<String>();

    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();

    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    RecyclerView recList;
    RelativeLayout rlout_price,rlout_stock;
    AutoCompleteTextView warehouse_list,product_list;
    List<Stock_Info> result = new ArrayList<Stock_Info>();
    Button ware_result_button;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.stock);

        cd  = new ConnectionDetector(getApplicationContext());

        rlout_price = findViewById(R.id.rlout_price);
        rlout_stock = findViewById(R.id.rlout_stock);

        warehouse_list = findViewById(R.id.warehouse_list);
        product_list = findViewById(R.id.product_list);

        ware_result_button = findViewById(R.id.ware_result_button);

        recList = findViewById(R.id.cardListn);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        dialog = new ProgressDialog(Stock_Main.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);



        Global_Data.context = Stock_Main.this;

        try {
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                    MyPeriodicwork.class, 15, TimeUnit.MINUTES
            ).addTag("otpValidator").build();
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("Work",
                    ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);

        }catch(Exception ex) {
            ex.printStackTrace();
        }



        rlout_stock.setBackgroundResource(R.drawable.single_btab);
        rlout_price.setBackgroundResource(R.drawable.single_wtab);

        rlout_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        Pricing_Main.class);
                startActivity(intent);
                finish();
            }
        });


        ware_result_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(warehouse_list.getText().toString()) || Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(product_list.getText().toString()))
                {
                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent)
                    {
                        getWarehouseDetails();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(Stock_Main.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                }
                else
                {
                    Toast toast = Toast.makeText(Stock_Main.this,"Please Select Warehouse Search Options.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }



            }
        });





        SharedPreferences spf=Stock_Main.this.getSharedPreferences("SimpleLogic",0);
        SharedPreferences.Editor editor=spf.edit();
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
            mTitleTextView.setText("Stock");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Stock_Main.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
            try
            {
                int target  = Math.round(sp.getFloat("Target",0));
                int achieved  = Math.round(sp.getFloat("Achived",0));
                Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
                if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
                {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
                }else
                {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
                }

            }catch(Exception ex){ex.printStackTrace();}


            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}


        recList.setVisibility(View.INVISIBLE);


        dialog = new ProgressDialog(Stock_Main.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Customer Loading....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();


        new Stock_Main.StockASN().execute();

        if((!Global_Data.Stock_warehouse_flag.equalsIgnoreCase("")))
        {
            warehouse_list.setText(Global_Data.Stock_warehouse_flag);
        }

        if((!Global_Data.Stock_product_flag.equalsIgnoreCase("")))
        {
            product_list.setText(Global_Data.Stock_product_flag);
        }

        product_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                ware_result_button.setVisibility(View.VISIBLE);

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (product_list.getRight() - product_list.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Stock_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        // product_list.showDropDown();

                        Global_Data.Stock_product_flag = "";
                        Global_Data.Stock_product_flag_value_check = "";
                        Intent i=new Intent(Stock_Main.this,Stock_Product_Options.class);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);

                        return true;
                    }
                }
                return false;
            }
        });

        product_list.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(product_list.getText().toString().trim().length() == 0) {

                    //ca = new Customer_info_main_adapter(Allresult,Stock_Main.this);
                    //recList.setAdapter(ca);
                    //ca.notifyDataSetChanged();
                    //ware_result_button.setVisibility(View.VISIBLE);



                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        product_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                ware_result_button.setVisibility(View.VISIBLE);
                Global_Data.hideSoftKeyboard(Stock_Main.this);
                Global_Data.Stock_product_flag_value_check = "";
                Global_Data.Stock_warehouse_flag_value_check = "";
                Global_Data.Stock_product_flag = "";
                Global_Data.Stock_warehouse_flag = "";


            }
        });

        warehouse_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;
                ware_result_button.setVisibility(View.VISIBLE);

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (warehouse_list.getRight() - warehouse_list.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Stock_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        // warehouse_list.showDropDown();
                        Global_Data.Stock_warehouse_flag = "";
                        Global_Data.Stock_warehouse_flag_value_check = "";
                        Intent i=new Intent(Stock_Main.this, Stock_options.class);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        //finish();
                        return true;
                    }
                }
                return false;
            }
        });

        warehouse_list.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(warehouse_list.getText().toString().trim().length() == 0) {

                    //ca = new Customer_info_main_adapter(Allresult,Stock_Main.this);
                    //recList.setAdapter(ca);
                    //ca.notifyDataSetChanged();



                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        warehouse_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                ware_result_button.setVisibility(View.VISIBLE);
                Global_Data.hideSoftKeyboard(Stock_Main.this);
                Global_Data.Stock_product_flag_value_check = "";
                Global_Data.Stock_warehouse_flag_value_check = "";
                Global_Data.Stock_product_flag = "";
                Global_Data.Stock_warehouse_flag = "";


            }
        });


    }








    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i <= size; i++) {
            ContactInfo ci = new ContactInfo();
            ci.name = "101"+ " " + "abc";
            ci.rp = ContactInfo.SURNAME_PREFIX + i;
            ci.mrp = ContactInfo.EMAIL_PREFIX + i + "@test.com";

            result.add(ci);

        }

        return result;
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

    private  class StockASN extends AsyncTask<String, Void, String> {

        int a= 0;
        @Override
        protected String doInBackground(String... response) {

            List<Local_Data> contacts3 = dbvoc.getAllitemList();

            if (contacts3.size() <= 0) {
                // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                Stock_Main.this.runOnUiThread(new Runnable() {
                    public void run() {

                        // dialog.dismiss();
                        ++a;
                        Toast toast = Toast.makeText(Stock_Main.this, "Sorry Products Record Not Found.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });

//                Intent intent = new Intent(getApplicationContext(),
//                        MainActivity.class);
//                startActivity(intent);
            } else {

                for (Local_Data cn : contacts3) {

                    All_Product.add(cn.getProdname());

                }

                Stock_Main.this.runOnUiThread(new Runnable() {
                    public void run() {
                        // dialog.dismiss();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Stock_Main.this, android.R.layout.simple_spinner_dropdown_item,
                                All_Product);
                        product_list.setThreshold(1);// will start working from
                        product_list.setAdapter(adapter);// setting the adapter
                        product_list.setTextColor(Color.BLACK);
                    }
                });



            }

            List<Local_Data> warehouse = dbvoc.getAllwarehouseList();

            if (warehouse.size() <= 0) {
                // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                Stock_Main.this.runOnUiThread(new Runnable() {
                    public void run() {
                        //  dialog.dismiss();
                        ++a;
                        Toast toast = Toast.makeText(Stock_Main.this, "Sorry Warehouses Record Not Found.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });

//                Intent intent = new Intent(getApplicationContext(),
//                        MainActivity.class);
//                startActivity(intent);
            } else {

                for (Local_Data cn : warehouse) {

                    All_Warehouse.add(cn.getWare_name());

                }

                Stock_Main.this.runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Stock_Main.this, android.R.layout.simple_spinner_dropdown_item,
                                All_Warehouse);
                        warehouse_list.setThreshold(1);// will start working from
                        warehouse_list.setAdapter(adapter);// setting the adapter
                        warehouse_list.setTextColor(Color.BLACK);
                    }
                });

                if(a==2)
                {
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivity(intent);
                }



            }

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();
            Stock_Main.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }


    @Override
    public void onBackPressed() {

        Intent i=new Intent(Stock_Main.this, Pricing_Main.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }


    public void getWarehouseDetails()
    {

        new Stock_Main.WarehouseData().execute();


    }

    private class WarehouseData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
                String device_id = sp.getString("devid", "");

                recList.setVisibility(View.INVISIBLE);

                result.clear();

                warehouse_flagnew = 0;
                product_flagnew = 0;
                String warehouse_flag = "";
                String product_flag = "";
                String[] city_state;
                String[] sub_caty;
                String state_name = "";
                String city_name = "";
                String state_code = "";
                String city_code = "";
                String warehouse_code = "";

                String caategory_name = "";
                String sub_caategory_name = "";
                String caategory_code = "";
                String sub_caategory_code = "";
                String product_code = "";


                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(warehouse_list.getText().toString()))
                {
                    warehouse_flag = "true";

                    if(warehouse_list.getText().toString().equalsIgnoreCase("All States"))
                    {
                        state_code = "";
                        city_code = "";
                        warehouse_code = "";

                    }
                    else
                    if(warehouse_list.getText().toString().contains("All Cities"))
                    {
                        city_state = warehouse_list.getText().toString().split("-");
                        state_name = city_state[0];

                        List<Local_Data> state_namen = dbvoc.getState_id(state_name.trim());

                        for (Local_Data nm : state_namen)
                        {

                            state_code = nm.getSTATE_ID();
                        }

                        city_code = "";
                        warehouse_code = "";

                    }
                    else
                    if(Global_Data.Stock_warehouse_flag_value_check.equalsIgnoreCase("All States AND All Cities"))
                    {
                        city_state = warehouse_list.getText().toString().split("-");
                        state_name = city_state[0];
                        city_name = city_state[1];

                        List<Local_Data> state_namen = dbvoc.getState_id(state_name.trim());

                        for (Local_Data nm : state_namen)
                        {

                            state_code = nm.getSTATE_ID();
                        }

                        List<Local_Data> city_namen = dbvoc.getCity_id(city_name.trim());

                        for (Local_Data cnm : city_namen)
                        {

                            city_code = cnm.getCITY_ID();
                        }

                        warehouse_code = "";

                    }
                    else
                    {
                        List<Local_Data> w_namen = dbvoc.getwarehouseByname(warehouse_list.getText().toString().trim());

                        if(w_namen.size() > 0)
                        {
                            warehouse_flagnew = 0;
                            for (Local_Data wn : w_namen)
                            {

                                warehouse_code = wn.getWare_code();
                            }
                        }
                        else
                        {
                            warehouse_flagnew = 1;
                        }


                        state_code = "";
                        city_code = "";


                    }

                }
                else
                {
                    state_code = "";
                    city_code = "";
                    warehouse_code = "";
                }


                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(product_list.getText().toString()))
                {
                    product_flag = "true";

                    if(product_list.getText().toString().equalsIgnoreCase("All Category"))
                    {
                        caategory_code = "";
                        sub_caategory_code = "";
                        product_code = "";

                    }
                    else
                    if(product_list.getText().toString().contains("All Sub Category"))
                    {
                        sub_caty = product_list.getText().toString().split("-");
                        caategory_name = sub_caty[0];

//                List<Local_Data> c_namen = dbvoc.HSS_DescriptionITEM_ID(caategory_name.trim());
//
//                for (Local_Data cn : c_namen)
//                {

                        caategory_code = caategory_name;
                        //  }

                        sub_caategory_code = "";
                        product_code = "";

                    }
                    else
                    if(Global_Data.Stock_product_flag_value_check.equalsIgnoreCase("Category and All Sub Category"))
                    {
                        sub_caty = product_list.getText().toString().split("-");
                        caategory_name = sub_caty[0];
                        sub_caategory_name = sub_caty[1];

//                List<Local_Data> c_namen = dbvoc.HSS_DescriptionITEM_ID(caategory_name.trim());
//
//                for (Local_Data cn : c_namen)
//                {

                        caategory_code = caategory_name;
                        //  }

                        sub_caategory_code = sub_caategory_name;
                        product_code = "";
                    }
                    else
                    {
                        List<Local_Data> c_namen = dbvoc.PRODUCT_ID(product_list.getText().toString().trim());

                        if(c_namen.size() > 0)
                        {
                            product_flagnew = 0;
                            for (Local_Data cn : c_namen)
                            {

                                product_code = cn.getItem_Code();
                            }
                        }
                        else
                        {
                            product_flagnew = 1;
                        }

                        caategory_code = "";
                        sub_caategory_code = "";

                    }

                }
                else
                {
                    caategory_code = "";
                    sub_caategory_code = "";
                    product_code = "";
                }

                if(warehouse_flagnew == 0 && product_flagnew == 0) {
                    try {


                        String domain = getResources().getString(R.string.service_domain);

                        Log.i("volley", "domain: " + domain);
                        Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL);
                        String service_url = "";

                        service_url = domain + "wh_stocks/send_stocks?email=" + Global_Data.GLOvel_USER_EMAIL + "&warehouse_code=" + warehouse_code + "&product_code=" + product_code + "&state_code=" + state_code + "&city_code=" + city_code + "&primary_category=" + URLEncoder.encode(caategory_code, "UTF-8") + "&sub_category=" + URLEncoder.encode(sub_caategory_code, "UTF-8");


                        Log.i("target url", "target url " + service_url);

                        JsonObjectRequest jsObjRequest = new JsonObjectRequest(service_url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("volley", "response: " + response);
                                //  Log.i("volley", "response reg Length: " + response.length());


                                try {


                                    if (response.has("result")) {
                                        response_result = response.getString("result");
                                    } else {
                                        response_result = "data";
                                    }


                                    if (response_result.equalsIgnoreCase("User doesn't exist")) {

                                        // Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG).show();

                                        Stock_Main.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                dialog.dismiss();
                                                Toast toast = Toast.makeText(Stock_Main.this, response_result, Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();

                                                Intent i = new Intent(Stock_Main.this, Pricing_Main.class);
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();

                                            }
                                        });


                                    } else if (response_result.equalsIgnoreCase("Please provide all the data")) {

                                        // Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG).show();

                                        Stock_Main.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                dialog.dismiss();
                                                Toast toast = Toast.makeText(Stock_Main.this, response_result, Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();

                                                Intent i = new Intent(Stock_Main.this, Pricing_Main.class);
                                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(i);
                                                finish();

                                            }
                                        });


                                    } else {

                                        JSONArray stocks = response.getJSONArray("stocks");


                                        Log.i("volley", "response stocks Length: " + stocks.length());

                                        Log.d("stocks", "stocks" + stocks.toString());

                                        if (stocks.length() <= 0) {

                                            Stock_Main.this.runOnUiThread(new Runnable() {
                                                public void run() {

                                                    dialog.dismiss();
                                                    //Toast.makeText(getActivity(), "Target not found.", Toast.LENGTH_LONG).show();

                                                    Toast toast = Toast.makeText(Stock_Main.this, "Warehouse Data Not Found.", Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();

                                                }
                                            });


                                        } else {

                                            View view = Stock_Main.this.getCurrentFocus();
                                            if (view != null) {
                                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                            }

                                            ware_result_button.setVisibility(View.GONE);
                                            recList.setVisibility(View.VISIBLE);
                                            for (int i = 0; i < stocks.length(); i++) {

                                                JSONObject jsonObject = stocks.getJSONObject(i);


                                                Stock_Info ci = new Stock_Info();

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("product_primary_category"))) {
                                                    String product_category = Html.fromHtml("<b>" + "PRODUCT : " + "</b>") + jsonObject.getString("product_primary_category");
                                                    if (!jsonObject.getString("product_sub_category").equalsIgnoreCase("null") && !jsonObject.getString("product_sub_category").equalsIgnoreCase(null) & !jsonObject.getString("product_sub_category").equalsIgnoreCase("") & !jsonObject.getString("product_sub_category").equalsIgnoreCase(" ")) {
                                                        product_category += " - " + jsonObject.getString("product_sub_category");
                                                    }

                                                    if (!jsonObject.getString("product_variant").equalsIgnoreCase("null") && !jsonObject.getString("product_variant").equalsIgnoreCase(null) & !jsonObject.getString("product_variant").equalsIgnoreCase("") & !jsonObject.getString("product_variant").equalsIgnoreCase(" ")) {
                                                        product_category += " - " + jsonObject.getString("product_variant");
                                                    }

                                                    if (!jsonObject.getString("product_name").equalsIgnoreCase("null") && !jsonObject.getString("product_name").equalsIgnoreCase(null) & !jsonObject.getString("product_name").equalsIgnoreCase("") & !jsonObject.getString("product_name").equalsIgnoreCase(" ")) {
                                                        product_category += " - " + jsonObject.getString("product_name");
                                                    }


                                                    ci.ss_product = product_category;
                                                } else {
                                                    ci.ss_product = Html.fromHtml("<b>" + "PRODUCT : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("product_rp"))) {
                                                    ci.ss_RP = Html.fromHtml("<b>" + "RP : " + "</b>") + jsonObject.getString("product_rp");
                                                } else {
                                                    ci.ss_RP = Html.fromHtml("<b>" + "RP : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("product_mrp"))) {
                                                    ci.ss_MRP = Html.fromHtml("<b>" + "MRP : " + "</b>") + jsonObject.getString("product_mrp");
                                                } else {
                                                    ci.ss_MRP = Html.fromHtml("<b>" + "MRP : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("wh_name"))) {
                                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("wh_type"))) {
                                                        ci.ss_name = jsonObject.getString("wh_type") + " - " + jsonObject.getString("wh_name");
                                                    } else {
                                                        ci.ss_name = jsonObject.getString("wh_name");
                                                    }

                                                } else {
                                                    ci.ss_name = "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("wh_address"))) {

                                                    ci.ss_address = Html.fromHtml("<b>" + "ADDRESS : " + "</b>") + jsonObject.getString("wh_address");

                                                } else {
                                                    ci.ss_address = Html.fromHtml("<b>" + "ADDRESS : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("gross_stock"))) {

                                                    ci.ss_grossstock = Html.fromHtml("<b>" + "GROSS STOCK : " + "</b>") + jsonObject.getString("gross_stock");

                                                } else {
                                                    ci.ss_grossstock = Html.fromHtml("<b>" + "GROSS STOCK : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("sellable_stock"))) {

                                                    ci.ss_sellable = Html.fromHtml("<b>" + "SELLABLE : " + "</b>") + jsonObject.getString("sellable_stock");

                                                } else {
                                                    ci.ss_sellable = Html.fromHtml("<b>" + "SELLABLE : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("updated_at"))) {

                                                    ci.updated_at = Html.fromHtml("<b>" + "UPDATED : " + "</b>") + jsonObject.getString("updated_at");

                                                } else {
                                                    ci.updated_at = Html.fromHtml("<b>" + "UPDATED : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("wh_city_name"))) {

                                                    ci.city = Html.fromHtml("<b>" + "CITY : " + "</b>") + jsonObject.getString("wh_city_name");

                                                } else {
                                                    ci.city = Html.fromHtml("<b>" + "CITY : " + "</b>") + "";
                                                }

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("wh_state_name"))) {

                                                    ci.state = Html.fromHtml("<b>" + "STATE : " + "</b>") + jsonObject.getString("wh_state_name");

                                                } else {
                                                    ci.state = Html.fromHtml("<b>" + "STATE : " + "</b>") + "";
                                                }

                                                result.add(ci);

                                            }

                                            Stock_Main.this.runOnUiThread(new Runnable() {
                                                public void run() {

                                                    Stock_Adapter ca = new Stock_Adapter(result, Stock_Main.this);
                                                    recList.setAdapter(ca);
                                                    // ca.notifyDataSetChanged();
                                                    //recList.setRecycledViewPool();


                                                    dialog.dismiss();

                                                }
                                            });


                                        }

                                        Stock_Main.this.runOnUiThread(new Runnable() {
                                            public void run() {


                                                dialog.dismiss();

                                            }
                                        });


                                        //finish();

                                    }


                                    // }

                                    // output.setText(data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Stock_Main.this.runOnUiThread(new Runnable() {
                                        public void run() {


                                            dialog.dismiss();

                                        }
                                    });

                                }


                                Stock_Main.this.runOnUiThread(new Runnable() {
                                    public void run() {


                                        dialog.dismiss();

                                    }
                                });
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("volley", "error: " + error);

                                Stock_Main.this.runOnUiThread(new Runnable() {
                                    public void run() {


                                        Toast toast = Toast.makeText(Stock_Main.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                        dialog.dismiss();

                                        Intent i = new Intent(Stock_Main.this, Pricing_Main.class);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        finish();

                                    }
                                });



                            }
                        });

                        RequestQueue requestQueue = Volley.newRequestQueue(Stock_Main.this);
                        // queue.add(jsObjRequest);
                        jsObjRequest.setShouldCache(false);
                        int socketTimeout = 200000;//30 seconds - change to what you want
                        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        jsObjRequest.setRetryPolicy(policy);
                        requestQueue.add(jsObjRequest);

                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();


                    }

                }
                else
                {
                    dialog.dismiss();
                    if(warehouse_flagnew == 1 ) {

                        Stock_Main.this.runOnUiThread(new Runnable() {
                            public void run() {


                                warehouse_list.setText("");
                                Toast toast = Toast.makeText(Stock_Main.this,"Please Enter Valid Warehouse Name.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }
                        });

                    }
                    else
                    if(product_flagnew == 1 ) {

                        Stock_Main.this.runOnUiThread(new Runnable() {
                            public void run() {


                                product_list.setText("");
                                Toast toast = Toast.makeText(Stock_Main.this,"Please Enter Valid Product Name.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                            }
                        });



                    }

                }


            } catch (Exception e) {

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {

            dialog.setMessage("Please wait Warehouse Details Sync....");
            dialog.setTitle("Smart Anchor App");
            dialog.setCancelable(false);
            dialog.show();
        }
    }

}
