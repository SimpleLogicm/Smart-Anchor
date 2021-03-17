package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.anchor.adapter.AutoCompleteContactArrayAdapter;
import com.anchor.adapter.AutocompleteAdapternew;
import com.anchor.adapter.CustomerAutoAdapter;
import com.anchor.model.RCTOData;
import com.anchor.model.SubDealerModel;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.anchor.activities.Global_Data.AllresultSubDealer;
import static com.anchor.activities.Global_Data.SubDealer_List;

public class Sub_Dealer_Order_Main extends Activity implements OnItemSelectedListener {
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String state_name = "";
    String city_name = "";
    String district_name = "";
    Button s_submit, s_no_order, s_previous_order;
    Spinner s_state, s_district, s_city;
    String[] s;
    ProgressDialog progressDialog;
    ArrayAdapter<String> adapter_state1;
    AutoCompleteTextView s_sub_dealer_search, s_dealer_search;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_city;
    ArrayAdapter<String> adapter_state3;
    HttpResponse response;
    Spinner Beat_search;
    Cursor cursor;
    String[] from;
    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    String C_ID = "";
    String S_ID = "";
    String B_ID = "";
    SharedPreferences sp;
    private ArrayList<String> results_Beats = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();
    private ArrayList<String> results_city = new ArrayList<String>();
    private ArrayList<String> resultcustomer = new ArrayList<String>();
    TextView customer_address;
    static String final_response = "";
    String response_result = "";
    JSONArray City_JSON = null;
    JSONArray STATE_JSON = null;
    JSONArray SubDealer_JSON = null;
    HashMap<String, String> statemap = new HashMap<String, String>();
    HashMap<String, String> citymap = new HashMap<String, String>();
    HashMap<String, String> districmap = new HashMap<String, String>();
    ArrayList<SubDealerModel> All_sdealers = new ArrayList<SubDealerModel>();
    String valid_sub_dealer_flag = "";
    String sub_dealer_code = "";
    AutocompleteAdapternew adapter1;
    RecyclerView auto_recycleview;
    CustomerAutoAdapter customerAutoAdapter;
    List<Local_Data> autolist = new ArrayList<>();
    int Count = 1;
    ScrollView auto_scroll;
    ArrayList<String> customers = new ArrayList<String>();
    HashMap<String, String> Dealer_map = new HashMap<>();
    HashMap<String, String> Dealer_ma_CODE = new HashMap<>();
    String beat_code = "";
    String bs_check = "";
    int i = 0;
    ArrayAdapter<String> adaptorBeats;
    String beat_click_flag = "";
    String spinner_flag = "";
    LoginDataBaseAdapter loginDataBaseAdapter;
    HashMap<String,String> Customers_map_EMAIL = new HashMap<String,String>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_dealer_order_main);

        auto_scroll = findViewById(R.id.auto_scroll);
        s_submit = findViewById(R.id.s_submit);
        s_no_order = findViewById(R.id.s_no_order);
        s_previous_order = findViewById(R.id.s_previous_order);
        s_state = findViewById(R.id.s_state);
        s_city = findViewById(R.id.s_city);
        s_district = findViewById(R.id.s_district);
        s_sub_dealer_search = findViewById(R.id.s_sub_dealer_search);
        s_dealer_search = findViewById(R.id.s_dealer_search);
        auto_recycleview = findViewById(R.id.auto_recycleview);
        Beat_search = findViewById(R.id.Beat_search);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, SubDealer_List);
        s_sub_dealer_search.setThreshold(1);// will start working from
        // first character
        s_sub_dealer_search.setAdapter(adapter);// setting the adapter
        // data into the
        // AutoCompleteTextView
        s_sub_dealer_search.setTextColor(Color.BLACK);
//        WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(Sub_Dealer_Order_Main.this, LinearLayoutManager.VERTICAL, false);
//        wrapContentLinearLayoutManager.setStackFromEnd(true);
//        auto_recycleview.setLayoutManager(wrapContentLinearLayoutManager);
//        adapter1 = new AutocompleteAdapternew(this, android.R.layout.simple_spinner_dropdown_item);
//        s_dealer_search.setAdapter(adapter1);


//        customerAutoAdapter = new CustomerAutoAdapter(autolist,Sub_Dealer_Order_Main.this );
//        auto_recycleview.setAdapter(customerAutoAdapter);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        Global_Data.Customers.clear();
        Global_Data.Customers_map.clear();
        Global_Data.spiner_list_modelListn.clear();
        Global_Data.state_code = "";
        Global_Data.GLOvel_SUB_GORDER_ID = "";
        Global_Data.Business_unit_code_array = "";
        dbvoc.getDeleteTable("sub_orders");
        dbvoc.getDeleteTable("sub_order_products");

        if (!Global_Data.Sub_Dealer_name.equalsIgnoreCase("")) {
            s_sub_dealer_search.setText(Global_Data.Sub_Dealer_name);
            s_sub_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.close_product, 0);
        }


        s_sub_dealer_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {


                    if (event.getRawX() >= (s_sub_dealer_search.getRight() - s_sub_dealer_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Sub_Dealer_Order_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        if (!s_sub_dealer_search.getText().toString().equalsIgnoreCase("")) {
                            s_sub_dealer_search.setText("");
                            s_sub_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0);
                        }

                        //autoCompleteTextView1.setText("");
                        s_sub_dealer_search.showDropDown();
                        return true;
                    }
                }
                return false;
            }
        });

        s_sub_dealer_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s_sub_dealer_search.getText().toString().trim().length() == 0) {

                    valid_sub_dealer_flag = "";
                    sub_dealer_code = "";
                } else {
                    s_sub_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.close_product, 0);
                }

            }
        });

        s_sub_dealer_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                Global_Data.hideSoftKeyboard(Sub_Dealer_Order_Main.this);

                String name = s_sub_dealer_search.getText().toString();

                for (int i = 0; i < AllresultSubDealer.size(); i++) {
                    if (name.equalsIgnoreCase(AllresultSubDealer.get(i).getShop_name())) {
                        Global_Data.Sub_Dealer_Code = AllresultSubDealer.get(i).getCode();
                        break;
                    }
                }


            }

        });


        s_dealer_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (s_dealer_search.getRight() - s_dealer_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Sub_Dealer_Order_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        if (!s_dealer_search.getText().toString().equalsIgnoreCase("")) {
                            s_dealer_search.setText("");
                            s_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0);
                        }

                        //autoCompleteTextView1.setText("");

                        s_dealer_search.showDropDown();
                        return true;
                    }
                }


                return false;
            }
        });

        s_dealer_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s_dealer_search.getText().toString().trim().length() == 0) {

                    if (beat_click_flag.equalsIgnoreCase("")) {
                        final ArrayAdapter<String> adapterstr = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                Global_Data.Customers);

                        s_dealer_search.setThreshold(1);
                        s_dealer_search.setAdapter(adapterstr);

                        s_dealer_search.setTextColor(Color.BLACK);

                        Beat_search.setSelection(0);
                    } else {
                        beat_click_flag = "";
                    }


                } else {
                    s_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.close_product, 0);
                }


            }
        });

        s_dealer_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {

                spinner_flag = Beat_search.getSelectedItem().toString();
                Global_Data.hideSoftKeyboard(Sub_Dealer_Order_Main.this);

                String name = s_dealer_search.getText().toString();

                String beats_id = "";
                List<Local_Data> contacts2 = dbvoc.getbeat_bycuname(name);
                if (contacts2.size() > 0) {
                    for (Local_Data localData : contacts2) {
                        beats_id = localData.getBEAT_ID();
                    }
                }

                try {
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(beats_id)) {
                        String beat_name = Dealer_ma_CODE.get(beats_id);
                        int spinnerPosition = adaptorBeats.getPosition(beat_name);
                        Beat_search.setSelection(spinnerPosition);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }

        });


        s_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (s_state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
//
//                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select State",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else if (s_district.getSelectedItem().toString().equalsIgnoreCase("Select District")) {
//
//                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select City",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//                else
                if (Beat_search.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Beat",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_sub_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer From The List",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer From The List",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    String sub_dealer_name = "";
                    String address_type = "";
                    sub_dealer_name = s_sub_dealer_search.getText().toString().trim();

                    valid_sub_dealer_flag = "";
                    //  sub_dealer_code = "";


                    if (AllresultSubDealer.size() > 0) {
                        for (RCTOData dataItem : AllresultSubDealer) {
                            if (dataItem.getShop_name().equalsIgnoreCase(s_sub_dealer_search.getText().toString())) {
                                sub_dealer_code = dataItem.getCode();
                                Global_Data.Sub_Dealer_Code = sub_dealer_code;
                                Global_Data.SUB_Mobile = dataItem.getMobile();
                                Global_Data.Sub_Email = dataItem.getEmail();
                                Global_Data.Sub_shop_name = dataItem.getShop_name();
                                Global_Data.Sub_Dealer_address = dataItem.getAddress();
                                valid_sub_dealer_flag = "yes";
                                break;
                            }

                        }
                    }

                    if (Global_Data.Customers_map.size() > 0) {
                        try {
                            Global_Data.Dealer_Code = Global_Data.Customers_map.get(s_dealer_search.getText().toString().trim());
                            Global_Data.Dealer_EMAIL = Customers_map_EMAIL.get(s_dealer_search.getText().toString().trim());

                            if (Global_Data.Dealer_Code.equalsIgnoreCase(null) || Global_Data.Dealer_Code.equalsIgnoreCase("null") || Global_Data.Dealer_Code.equalsIgnoreCase("")) {
                                Global_Data.Dealer_Code = "";
                            }
                        } catch (Exception ex) {
                            Global_Data.Dealer_Code = "";
                            ex.printStackTrace();
                        }
                    }

                    if (Global_Data.Dealer_Code.equalsIgnoreCase("") || !valid_sub_dealer_flag.equalsIgnoreCase("yes")) {

                        if (!valid_sub_dealer_flag.equalsIgnoreCase("yes")) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer From The List",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (Global_Data.Dealer_Code.equalsIgnoreCase("")) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer From The List",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    } else {
                        Global_Data.GLOvel_SUB_GORDER_ID = "";
                        Global_Data.statusOrderActivity = "";

                        String user_email = "";
                        sp = getSharedPreferences("SimpleLogic", 0);
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                                user_email = sp.getString("USER_EMAIL", "");
                            } else {
                                user_email = Global_Data.GLOvel_USER_EMAIL;
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        List<Local_Data> contacts = dbvoc.getUSERBY_Email(user_email);

                        if (contacts.size() <= 0) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "User Not Found ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            for (Local_Data cn : contacts) {
                                Global_Data.Business_unit_code_array = cn.getBunit();
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Business_unit_code_array)) {

                                Intent s_dub = new Intent(getApplicationContext(), SubDealer_NewOrderActivity.class);
                                startActivity(s_dub);
                                finish();
                            } else {
                                Global_Data.Business_unit_code_array = "";
                                Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "No Prodct Found ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }


                        }


                    }


                }
            }
        });

        s_previous_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Beat_search.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Beat",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_sub_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer From The List",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer From The List",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    String sub_dealer_name = "";
                    String address_type = "";
                    sub_dealer_name = s_sub_dealer_search.getText().toString().trim();

                    valid_sub_dealer_flag = "";
                    //  sub_dealer_code = "";


                    if (AllresultSubDealer.size() > 0) {
                        for (RCTOData dataItem : AllresultSubDealer) {
                            if (dataItem.getShop_name().equalsIgnoreCase(s_sub_dealer_search.getText().toString())) {
                                sub_dealer_code = dataItem.getCode();
                                Global_Data.Sub_Dealer_Code = sub_dealer_code;
                                Global_Data.SUB_Mobile = dataItem.getMobile();
                                Global_Data.Sub_Email = dataItem.getEmail();
                                Global_Data.Sub_shop_name = dataItem.getShop_name();
                                Global_Data.Sub_Dealer_address = dataItem.getAddress();
                                valid_sub_dealer_flag = "yes";
                                break;
                            }

                        }
                    }

                    if (Global_Data.Customers_map.size() > 0) {
                        try {
                            Global_Data.Dealer_Code = Global_Data.Customers_map.get(s_dealer_search.getText().toString().trim());
                            Global_Data.Dealer_EMAIL = Customers_map_EMAIL.get(s_dealer_search.getText().toString().trim());
                            if (Global_Data.Dealer_Code.equalsIgnoreCase(null) || Global_Data.Dealer_Code.equalsIgnoreCase("null") || Global_Data.Dealer_Code.equalsIgnoreCase("")) {
                                Global_Data.Dealer_Code = "";
                            }
                        } catch (Exception ex) {
                            Global_Data.Dealer_Code = "";
                            ex.printStackTrace();
                        }
                    }

                    if (Global_Data.Dealer_Code.equalsIgnoreCase("") || !valid_sub_dealer_flag.equalsIgnoreCase("yes")) {

                        if (!valid_sub_dealer_flag.equalsIgnoreCase("yes")) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer From The List",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (Global_Data.Dealer_Code.equalsIgnoreCase("")) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer From The List",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    } else {
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {
                            Global_Data.GLOvel_SUB_GORDER_ID = "";
                            Global_Data.statusOrderActivity = "";
                            String user_email = "";
                            sp = getSharedPreferences("SimpleLogic", 0);
                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                                    user_email = sp.getString("USER_EMAIL", "");
                                } else {
                                    user_email = Global_Data.GLOvel_USER_EMAIL;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                            List<Local_Data> contacts = dbvoc.getUSERBY_Email(user_email);

                            if (contacts.size() <= 0) {
                                Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "User Not Found ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            else
                            {
                                for (Local_Data cn : contacts) {
                                    Global_Data.Business_unit_code_array = cn.getBunit();
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Business_unit_code_array)) {

                                    getPrevious_OrderData(Global_Data.Dealer_Code, Global_Data.Sub_Dealer_Code);

                                } else {
                                    Global_Data.Business_unit_code_array = "";
                                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "No Prodct Found ", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }


                            }
                        } else {
                            // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }

                }

            }

        });

        s_no_order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (s_state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
//
//                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select State",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                } else if (s_district.getSelectedItem().toString().equalsIgnoreCase("Select District")) {
//
//                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select City",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
//                else
                if (Beat_search.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Beat",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_sub_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer From The List",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer From The List",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    valid_sub_dealer_flag = "";
                    if (AllresultSubDealer.size() > 0) {
                        for (RCTOData dataItem : AllresultSubDealer) {
                            if (dataItem.getShop_name().equalsIgnoreCase(s_sub_dealer_search.getText().toString())) {
                                sub_dealer_code = dataItem.getCode();
                                Global_Data.Sub_Dealer_Code = sub_dealer_code;
                                Global_Data.SUB_Mobile = dataItem.getMobile();
                                Global_Data.Sub_Email = dataItem.getEmail();
                                Global_Data.Sub_shop_name = dataItem.getShop_name();
                                Global_Data.Sub_Dealer_address = dataItem.getAddress();
                                valid_sub_dealer_flag = "yes";
                                break;
                            }

                        }
                    }

                    if (Global_Data.Customers_map.size() > 0) {
                        try {
                            Global_Data.Dealer_Code = Global_Data.Customers_map.get(s_dealer_search.getText().toString().trim());
                            Global_Data.Dealer_EMAIL = Customers_map_EMAIL.get(s_dealer_search.getText().toString().trim());
                            if (Global_Data.Dealer_Code.equalsIgnoreCase(null) || Global_Data.Dealer_Code.equalsIgnoreCase("null") || Global_Data.Dealer_Code.equalsIgnoreCase("")) {
                                Global_Data.Dealer_Code = "";
                            }
                        } catch (Exception ex) {
                            Global_Data.Dealer_Code = "";
                            ex.printStackTrace();
                        }
                    }

                    if (Global_Data.Dealer_Code.equalsIgnoreCase("") || !valid_sub_dealer_flag.equalsIgnoreCase("yes")) {

                        if (!valid_sub_dealer_flag.equalsIgnoreCase("yes")) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer From The List",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (Global_Data.Dealer_Code.equalsIgnoreCase("")) {
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer From The List",
                                    Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    } else {
                        Global_Data.GLOvel_SUB_GORDER_ID = "";
                        Global_Data.statusOrderActivity = "";
                        Intent s_dub = new Intent(getApplicationContext(), NoOrderActivity.class);
                        startActivity(s_dub);

                    }


                }
            }
        });

        // Reading all
        results1.add("Select State");
        results2.add("Select District");


        adapter_state1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, results1);
        adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_state.setAdapter(adapter_state1);
        s_state.setOnItemSelectedListener(this);

        List<Local_Data> contacts2 = dbvoc.getAllBeats();
        StringBuilder ss = new StringBuilder();
        results_Beats.add("Select Beat");
        if (contacts2.size() > 0) {
            for (Local_Data localData : contacts2) {
                String beats = localData.getStateName();
                results_Beats.add(beats);
                Dealer_map.put(localData.getStateName(), localData.getCode());
                Dealer_ma_CODE.put(localData.getCode(), localData.getStateName());

                if (i == 0) {
                    ss.append("[");
                }

                ss.append('"' + localData.getCode() + '"');
                if ((contacts2.size() - 1) != i) {
                    ss.append(",");
                } else {
                    ss.append("]");
                }

                ++i;

            }
            bs_check = ss.toString();
        } else {
            bs_check = "[]";
        }


        adaptorBeats = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, results_Beats);
        Beat_search.setAdapter(adaptorBeats);// setting the adapter
        Beat_search.setOnItemSelectedListener(this);


        try {


            cd = new ConnectionDetector(Sub_Dealer_Order_Main.this);

            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
//            if (progressDialog != null && progressDialog.isShowing())
//                progressDialog.dismiss();
                progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("Please wait....");
                progressDialog.setTitle("Smart Anchor App");
                progressDialog.setCancelable(false);
                progressDialog.show();

                try {

                    state_name = "";

                    customer_OnlineData(bs_check, "All");

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            } else {

                Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#910505")));
            TextView mTitleTextView = mCustomView
                    .findViewById(R.id.screenname);
            mTitleTextView.setText("Retailer Order");

            TextView todaysTarget = mCustomView
                    .findViewById(R.id.todaysTarget);
            SharedPreferences sp = Sub_Dealer_Order_Main.this
                    .getSharedPreferences("SimpleLogic", 0);

//            try {
//                int target = Math.round(sp.getFloat("Target", 0));
//                int achieved = Math.round(sp.getFloat("Achived", 0));
//                Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
//                if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
//                    int age = Math.round(age_float);
//
//                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
//                } else {
//                    int age = Math.round(age_float);
//
//                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
//                }
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//                // todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target",
//                // 0.00f)-sp.getFloat("Target", 0.00f))+"");
//                todaysTarget.setText("Today's Target Acheived");
//            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        Spinner spinner = (Spinner) arg0;
        //drop_value = drop_value+1;

        //if(drop_value > 1) {
        if (spinner.getId() == R.id.s_state) {


            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select State")) {

                Global_Data.state_code = "";
                state_name = "";
                city_name = "";
                district_name = "";
                districmap.clear();
                citymap.clear();

                results2.clear();
                results2.add("Select District");
                results_city.clear();
                results_city.add("Select City");

                adapter_state2 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, results2);
                adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s_district.setAdapter(adapter_state2);
                s_district.setOnItemSelectedListener(this);

                adapter_city = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, results_city);
                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s_city.setAdapter(adapter_city);
                s_city.setOnItemSelectedListener(this);

                s_sub_dealer_search.setText("");
                s_dealer_search.setText("");


            } else {

                String items = s_state.getSelectedItem().toString().trim();
                state_name = items;
                //String C_ID = "";
                Log.i("Selected item : ", items);

                cd = new ConnectionDetector(Sub_Dealer_Order_Main.this);

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();

                    progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage("Please wait....");
                    progressDialog.setTitle("Smart Anchor App");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    try {
                        results2.clear();
                        results2.add("Select District");
                        String state_code = statemap.get(state_name);
                        Global_Data.state_code = state_code;
                        districts_OnlineData(state_code);
                        customers.clear();
                        // getDealers();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }
        }

        if (spinner.getId() == R.id.s_district) {
            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select District")) {
                city_name = "";
                district_name = "";
                results_city.clear();
                results_city.add("Select City");
                citymap.clear();

                adapter_city = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, results_city);
                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s_city.setAdapter(adapter_city);
                s_city.setOnItemSelectedListener(this);

                s_sub_dealer_search.setText("");
                s_dealer_search.setText("");

            } else {

                String items = s_district.getSelectedItem().toString().trim();
                //String C_ID = "";
                Log.i("Selected item : ", items);

                city_name = "";
                district_name = items;
                results_city.clear();
                results_city.add("Select City");


                adapter_city = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, results_city);
                adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                s_city.setAdapter(adapter_city);
                s_city.setOnItemSelectedListener(this);

                cd = new ConnectionDetector(Sub_Dealer_Order_Main.this);

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage("Please wait....");
                    progressDialog.setTitle("Smart Anchor App");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    try {

                        String distric_code = districmap.get(items);
                        //customer_OnlineData(distric_code);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {

                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }

            }
        }

        if (spinner.getId() == R.id.Beat_search) {
            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select Beat")) {

                s_dealer_search.setText("");
                final ArrayAdapter<String> adapterstr = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        Global_Data.Customers);

                s_dealer_search.setThreshold(1);
                s_dealer_search.setAdapter(adapterstr);

                s_dealer_search.setTextColor(Color.BLACK);
                s_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0);

            } else {
                beat_click_flag = "yes";

                s_dealer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0);
                String items = Beat_search.getSelectedItem().toString().trim();
                //String C_ID = "";
                Log.i("Selected item : ", items);

                //cd = new ConnectionDetector(Sub_Dealer_Order_Main.this);

//                isInternetPresent = cd.isConnectingToInternet();
//                if (isInternetPresent) {
//                    if (progressDialog != null && progressDialog.isShowing())
//                        progressDialog.dismiss();
//                    progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
//                    progressDialog.setMessage("Please wait....");
//                    progressDialog.setTitle("Smart Anchor App");
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();

                try {
                    List<String> Customers_n = new ArrayList<>();
                    String customer_name = "";
                    String beat_id = Dealer_map.get(items);
                    List<Local_Data> contacts2 = dbvoc.getcustomerByCityName(beat_id);
                    if (contacts2.size() > 0) {
                        for (Local_Data localData : contacts2) {
                            customer_name = localData.get_stocks_product_name();
                            Customers_n.add(customer_name);

                        }
                    }

                    final ArrayAdapter<String> adapterstr = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            Customers_n);

                    s_dealer_search.setThreshold(1);
                    s_dealer_search.setAdapter(adapterstr);

                    s_dealer_search.setTextColor(Color.BLACK);

                    if (!spinner_flag.equalsIgnoreCase("Select Beat")) {
                        s_dealer_search.setText("");
                    } else {
                        spinner_flag = "";
                    }


                    //customer_OnlineData(bs_check,"beat_click");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

//                } else {
//
//                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                }

            }
        }

        if (spinner.getId() == R.id.s_city) {
            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select City")) {

                s_sub_dealer_search.setText("");
                s_dealer_search.setText("");


            } else {

                String items = s_city.getSelectedItem().toString();
                city_name = items;
                Log.i("Selected item : ", items);

                cd = new ConnectionDetector(Sub_Dealer_Order_Main.this);

//                isInternetPresent = cd.isConnectingToInternet();
//                if (isInternetPresent) {
//                    if (progressDialog != null && progressDialog.isShowing())
//                        progressDialog.dismiss();
//                    progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
//                    progressDialog.setMessage("Please wait....");
//                    progressDialog.setTitle("Smart Anchor App");
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
//
//                    try {
//
//                        String city_code = citymap.get(items);
//                        sub_OnlineData(city_code);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//
//                } else {
//
//                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//
//                }


            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    public void State_OnlineData() {

        String user_email = "";
        sp = this.getSharedPreferences("SimpleLogic", 0);
        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String domain = getResources().getString(R.string.service_domain);
        String service_domain = domain + "menus/get_states_for_sub_dealer_sync?email=" + user_email;


        Log.i("user list url", "order list url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                //  auto_searchcust.setText(namemm);
                new getstate_Data().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


//                        Intent intent = new Intent(ShopProfile.this, ShopProfile.class);
//                        startActivity(intent);
//                        finish();
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server AuthFailureError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof ParseError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "ParseError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                        progressDialog.dismiss();
                        //  State_Offline_Data();
                        // finish();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 300000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(true);
        //requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class getstate_Data extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });
                } else {
                    response_result = "data";
                    try {

                        STATE_JSON = response.getJSONArray("states");
                        // City = response.getJSONArray("business_product_categories");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.i("volley", "response states Length: " + STATE_JSON.length());
                    Log.d("volley", "states" + STATE_JSON.toString());

                    if (STATE_JSON.length() <= 0) {

                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "City Record doesn't exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                        });
                    } else {

                        statemap.clear();
                        results1.clear();
                        results1.add("Select State");
                        for (int i = 0; i < STATE_JSON.length(); i++) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = STATE_JSON.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(STATE_JSON.getString(i))) {
                                    {
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("name").trim())) {

                                            if (!results1.contains(jsonObject.getString("name").trim())) {
                                                results1.add(jsonObject.getString("name").trim());
                                                statemap.put(jsonObject.getString("name").trim(), jsonObject.getString("code").trim());

                                            }

                                        }
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                try {

                                    adapter_state1 = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                                            android.R.layout.simple_spinner_item, results1);
                                    adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    s_state.setAdapter(adapter_state1);

                                    progressDialog.dismiss();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                            }
                        });


                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();


                Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                });

            }


            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
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

    public void districts_OnlineData(String state_code) {

        String user_email = "";
        sp = this.getSharedPreferences("SimpleLogic", 0);
        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String domain = getResources().getString(R.string.service_domain);
        String service_domain = domain + "sub_dealers/sync_districts?email=" + user_email + "&state_code=" + state_code;


        Log.i("user list url", "order list url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new getDistricts_Data().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server AuthFailureError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof ParseError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "ParseError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                        progressDialog.dismiss();
                        //  State_Offline_Data();
                        // finish();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 300000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(true);
        //requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class getDistricts_Data extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });
                } else {
                    response_result = "data";

                    try {

                        JSONArray DISTRIC_JSON = response.getJSONArray("districts");
                        // City = response.getJSONArray("business_product_categories");
                        Log.i("volley", "response districts Length: " + DISTRIC_JSON.length());
                        Log.d("volley", "districts" + DISTRIC_JSON.toString());

                        if (DISTRIC_JSON.length() <= 0) {

                            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                                public void run() {

                                    progressDialog.dismiss();
                                    Toast toast = Toast.makeText(getApplicationContext(), "District Record doesn't exist", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                }
                            });
                        } else {

                            String districts = "";
                            String districts_Code = "";


                            for (int i = 0; i < DISTRIC_JSON.length(); i++) {

                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = DISTRIC_JSON.getJSONObject(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                try {
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(DISTRIC_JSON.getString(i))) {
                                        {
                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("name").trim())) {
                                                districts = jsonObject.getString("name").trim();
                                                // districts_Code = jsonObject.getString("code").trim();
                                                results2.add(jsonObject.getString("name").trim());
                                                districmap.put(jsonObject.getString("name").trim(), jsonObject.getString("sub_code").trim());

                                            }
                                        }


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }


                            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                                public void run() {

                                    try {
                                        adapter_state2 = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                                                android.R.layout.simple_spinner_item, results2);
                                        adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        s_district.setAdapter(adapter_state2);
                                        progressDialog.dismiss();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }
                            });


                            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                                public void run() {

                                    progressDialog.dismiss();
                                }
                            });
                            //	dialog.dismiss();

                            //finish();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();


                Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                });

            }


            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
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


    @Override
    public void onDestroy() {
        super.onDestroy();

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }

    }

    public void customer_OnlineData(String beat_code, String beat_flag) {

        String domain = getResources().getString(R.string.service_domain);
        String service_domain = "";
//        if(beat_flag.equalsIgnoreCase("beat_click"))
//        {
        try {
            service_domain = domain + "customers/get_statewise_customers?beat_ids=" + URLEncoder.encode(beat_code, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        }
//        else
//        {
//            service_domain = domain + "customers/get_statewise_customers?beat_code=" + beat_code;
//        }


        Log.i("user list url", "order list url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                //  auto_searchcust.setText(namemm);
                new getcustomer_Data().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server AuthFailureError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof ParseError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "ParseError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }

                        progressDialog.dismiss();
                        //  State_Offline_Data();
                        // finish();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 300000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(true);
        //requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class getcustomer_Data extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);

                Global_Data.Customers.clear();
                Global_Data.Customers_map.clear();
                Customers_map_EMAIL.clear();

                if (response.has("message")) {
                    response_result = response.getString("message");

                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });

                } else {
                    response_result = "data";


                    try {

                        City_JSON = response.getJSONArray("customers");
                        // City = response.getJSONArray("business_product_categories");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.i("volley", "response customers Length: " + City_JSON.length());
                    Log.d("volley", "customers" + City_JSON.toString());

                    if (City_JSON.length() <= 0) {

                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Dealers Record doesn't exist.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                        });
                    } else {


                        for (int i = 0; i < City_JSON.length(); i++) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = City_JSON.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(City_JSON.getString(i))) {
                                    {
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("shop_name").trim())) {
                                            Global_Data.Customers.add(jsonObject.getString("shop_name"));
                                            Global_Data.Customers_map.put(jsonObject.getString("shop_name"), jsonObject.getString("id"));
                                            Global_Data.Customers_map.put(jsonObject.getString("shop_name"), jsonObject.getString("id"));
                                            Customers_map_EMAIL.put(jsonObject.getString("shop_name"), jsonObject.getString("email"));
                                        }
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                final ArrayAdapter<String> adapterstr = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        Global_Data.Customers);

                                s_dealer_search.setThreshold(1);
                                s_dealer_search.setAdapter(adapterstr);

                                s_dealer_search.setTextColor(Color.BLACK);


                            }
                        });


                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();


                Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                });

            }


            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
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

    public void sub_OnlineData(String city_code) {

        String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
        try {
            AppLocationManager appLocationManager = new AppLocationManager(Sub_Dealer_Order_Main.this);
            Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
            Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
            PlayService_Location PlayServiceManager = new PlayService_Location(Sub_Dealer_Order_Main.this);

            if (PlayServiceManager.checkPlayServices(Sub_Dealer_Order_Main.this)) {
                Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

            } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String domain = getResources().getString(R.string.service_domain);
        String service_domain = null;
        try {
//            service_domain = domain + "sub_dealers/get_sub_dealers_for_sub_dealer_order?city_code=" + city_code+"&lat="
//                    + URLEncoder.encode( Global_Data.GLOvel_LATITUDE, "UTF-8")+"&lon="
//                    + URLEncoder.encode( Global_Data.GLOvel_LONGITUDE, "UTF-8");
            service_domain = domain + "sub_dealers/get_sub_dealers_for_sub_dealer_order?city_id=" + city_code + "&lat="
                    + URLEncoder.encode("21.8970873", "UTF-8") + "&lon="
                    + URLEncoder.encode("83.3980456", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        Log.i("user Sub Dealer url", "Sub Dealer url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new getsubDealer_Data().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server AuthFailureError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof ParseError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "ParseError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }

                        progressDialog.dismiss();
                        //  State_Offline_Data();
                        // finish();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 300000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(true);
        //requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class getsubDealer_Data extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                All_sdealers.clear();
                if (response.has("message")) {
                    response_result = response.getString("message");

                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });

                } else {
                    response_result = "data";


                    try {

                        SubDealer_JSON = response.getJSONArray("sub_dealers");
                        // City = response.getJSONArray("business_product_categories");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.i("volley", "response SubDealer_JSON Length: " + SubDealer_JSON.length());
                    Log.d("volley", "SubDealer_JSON" + SubDealer_JSON.toString());

                    if (SubDealer_JSON.length() <= 0) {

                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Sub Dealer Record doesn't exist.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                        });
                    } else {

                        for (int i = 0; i < SubDealer_JSON.length(); i++) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = SubDealer_JSON.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(SubDealer_JSON.getString(i))) {
                                    {
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("shop_name").trim())) {
                                            SubDealerModel di = new SubDealerModel();

                                            di.name = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("firm_name"));
                                            di.proprietor_mobile1 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("proprietor_mobile1"));
                                            di.proprietor_name1 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("proprietor_name1"));

                                            di.proprietor_email1 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("proprietor_email1"));
                                            di.proprietor_mobile2 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("proprietor_mobile2"));
                                            di.proprietor_name2 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("proprietor_name2"));
                                            di.proprietor_email2 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("proprietor_email2"));
                                            di.shop_name = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("shop_name"));
                                            di.code = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("code"));
                                            //  di.Stage = jsonObject.getString("stage").trim();

                                            //  di.p_approval1 = "Approval 1 : "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("approval_status1").trim());

                                            // di.p_approval2 = "Approval 2 : "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("approval_status2").trim());

                                            // di.p_approval3 = "Approval 3 : "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("approval_status3").trim());
                                            //  di.city = jsonObject.getString("city_name").trim();
                                            // di.code = jsonObject.getString("code").trim();
                                            //   di.p_approved = jsonObject.getString("approved").trim();

                                            // di.remarks1 =jsonObject.getString("approver_remarks1").trim();
                                            //  di.remarks2 =jsonObject.getString("approver_remarks2").trim();

                                            All_sdealers.add(di);


                                        }
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                try {
                                    AutoCompleteContactArrayAdapter adapter = new AutoCompleteContactArrayAdapter(Sub_Dealer_Order_Main.this, R.layout.auto_layout, All_sdealers);
                                    s_sub_dealer_search.setAdapter(adapter);

                                    progressDialog.dismiss();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                            }
                        });


                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();


                Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                });

            }


            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();

        Intent i = new Intent(Sub_Dealer_Order_Main.this, MapsActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }

//    public void onClickCalled(String anyValue) {
//
//        ArrayList<String> s = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item,
//                s);
//        s_dealer_search.setThreshold(1);// will start working from
//        // first character
//        s_dealer_search.setAdapter(adapter);// setting the adapter
//        // data into the
//        // AutoCompleteTextView
//        s_dealer_search.setTextColor(Color.BLACK);
//
//        s_dealer_search.setText(anyValue);
//        // auto_recycleview.setVisibility(View.GONE);
//        // auto_recycleview.setAlpha(0);
//        //holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
//    }

    public void getPrevious_OrderData(String dealer_id,String Sub_Dealer_Code) {
        SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        String device_id = sp.getString("devid", "");
        progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        progressDialog.setMessage("Please Wait....");
        progressDialog.setTitle("Smart Anchor App");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {
            //TODO USER EMAIL

            String domain = getResources().getString(R.string.service_domain);

            Log.i("volley", "domain: " + domain);
            Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL);
            Log.i("target url", "target url " + domain + "sub_dealers/previous_order?sub_dealer_code=" + Sub_Dealer_Code);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain + "sub_dealers/previous_order?sub_dealer_code="+Sub_Dealer_Code, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());
                    try {

                        String response_result = "";
                        if (response.has("message")) {
                            response_result = response.getString("message");
                            Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {


                            dbvoc.getDeleteTable("sub_orders");
                            dbvoc.getDeleteTable("sub_order_products");

                            //JSONArray previous_orders = response.getJSONArray("previous_orders");
                            JSONArray previous_order_products = response.getJSONArray("order_products");

                            //Log.i("volley", "response reg previous_orders Length: " + previous_orders.length());
                            Log.i("volley", "response reg previous_order_products Length: " + previous_order_products.length());


                            //	Log.d("States", "previous_orders" + previous_orders.toString());
                            Log.d("States", "previous_order_products" + previous_order_products.toString());

                            if (previous_order_products.length() <= 0) {
                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Previous order not found.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {

                                for (int i = 0; i < previous_order_products.length(); i++) {

                                    JSONObject jsonObject = previous_order_products.getJSONObject(i);

                                    Global_Data.GLOvel_SUB_GORDER_ID = jsonObject.getString("order_number").trim();
                                    // Global_Data.Previous_Order_ServiceOrder_ID = jsonObject.getString("order_number").trim();

                                    loginDataBaseAdapter.insertSUb_OrderProducts("", "", jsonObject.getString("order_number"), "", "", "", "", "","", " ", "", jsonObject.getString("quantity"), jsonObject.getString("retail_price"), jsonObject.getString("mrp"), jsonObject.getString("total"), "", "", "", " ", jsonObject.getString("product_id"), " ", jsonObject.getString("product_name"));


                                }

                                Global_Data.statusOrderActivity = "Yes";
                                Intent intent = new Intent(Sub_Dealer_Order_Main.this, SubDealer_PreviewActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                                progressDialog.dismiss();
                            }
                            progressDialog.dismiss();

                            //finish();

                        }


                        // }

                        // output.setText(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }


                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);
                    //Toast.makeText(Order.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getApplicationContext(), "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    progressDialog.dismiss();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(Sub_Dealer_Order_Main.this);
            // queue.add(jsObjRequest);
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 200000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();
            progressDialog.dismiss();
        }
    }

}

