package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.AutoCompleteContactArrayAdapter;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Sub_Dealer_Order_Main extends Activity implements OnItemSelectedListener {
    private String Re_Text = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String state_name = "";
    String city_name = "";
    String district_name = "";
    Button s_submit;
    Spinner s_state, s_district, s_city;
    String s[];
    ProgressDialog progressDialog;
    ArrayAdapter<String> adapter_state1;
    AutoCompleteTextView s_sub_dealer_search, s_dealer_search;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_city;
    ArrayAdapter<String> adapter_state3;
    HttpResponse response;
    Cursor cursor;
    String[] from;
    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    String C_ID = "";
    String S_ID = "";
    String B_ID = "";
    SharedPreferences sp;
    ArrayList<String> list_sub_dealers = new ArrayList<String>();
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();
    private ArrayList<String> results_city = new ArrayList<String>();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_dealer_order_main);

        s_submit = findViewById(R.id.s_submit);
        s_state = findViewById(R.id.s_state);
        s_city = findViewById(R.id.s_city);
        s_district = findViewById(R.id.s_district);
        s_sub_dealer_search = findViewById(R.id.s_sub_dealer_search);
        s_dealer_search = findViewById(R.id.s_dealer_search);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list_sub_dealers);
        s_sub_dealer_search.setThreshold(1);// will start working from
        // first character
        s_sub_dealer_search.setAdapter(adapter);// setting the adapter
        // data into the
        // AutoCompleteTextView
        s_sub_dealer_search.setTextColor(Color.BLACK);

        Global_Data.Customers.clear();
        Global_Data.Customers_map.clear();

        s_sub_dealer_search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (s_sub_dealer_search.getRight() - s_sub_dealer_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Sub_Dealer_Order_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

                if(s_sub_dealer_search.getText().toString().trim().length() == 0) {

                    valid_sub_dealer_flag = "";
                    sub_dealer_code = "";
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        s_sub_dealer_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {


                valid_sub_dealer_flag = "";
                sub_dealer_code = "";
                for (SubDealerModel dataItem : All_sdealers)
                {
                    if(dataItem.shop_name.equalsIgnoreCase(s_sub_dealer_search.getText().toString()))
                    {
                        sub_dealer_code = dataItem.code;
                        valid_sub_dealer_flag = "yes";
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

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (s_dealer_search.getRight() - s_dealer_search.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Sub_Dealer_Order_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

                if(s_dealer_search.getText().toString().trim().length() == 0) {

                    //valid_sub_dealer_flag = "";
                    //sub_dealer_code = "";
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        s_dealer_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {

            }

        });

        s_submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent s_dub = new Intent(getApplicationContext(), SubDealer_NewOrderActivity.class);
                startActivity(s_dub);
                finish();

                if (s_state.getSelectedItem().toString().equalsIgnoreCase("Select State")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select State",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_district.getSelectedItem().toString().equalsIgnoreCase("Select District")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select City",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_city.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Beat",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_sub_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Sub Dealer",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (s_dealer_search.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Sub_Dealer_Order_Main.this, "Please Select Dealer",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    String sub_dealer_name = "";
                    String address_type = "";
                    sub_dealer_name = s_sub_dealer_search.getText().toString().trim();


//                    Intent s_dub = new Intent(getApplicationContext(), Sub_Dealer_ProductList.class);
//                    startActivity(s_dub);
//                    finish();


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

        try {


            cd = new ConnectionDetector(Sub_Dealer_Order_Main.this);

            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
//            if (progressDialog != null && progressDialog.isShowing())
//                progressDialog.dismiss();
                progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("Please wait....");
                progressDialog.setTitle("Anchor App");
                progressDialog.setCancelable(false);
                progressDialog.show();

                try {

                    state_name = "";

                    State_OnlineData();

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
            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.screenname);
            mTitleTextView.setText("Order");

            TextView todaysTarget = (TextView) mCustomView
                    .findViewById(R.id.todaysTarget);
            SharedPreferences sp = Sub_Dealer_Order_Main.this
                    .getSharedPreferences("SimpleLogic", 0);

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
            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
                // todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target",
                // 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

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
                    progressDialog.setTitle("Anchor App");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    try {
                        results2.clear();
                        results2.add("Select District");

                        String state_code = statemap.get(state_name);
                        districts_OnlineData(state_code);
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
                    progressDialog.setTitle("Anchor App");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    try {

                        String distric_code = districmap.get(items);
                        City_OnlineData(distric_code);
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

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    progressDialog = new ProgressDialog(Sub_Dealer_Order_Main.this, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setMessage("Please wait....");
                    progressDialog.setTitle("Anchor App");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    try {

                        String city_code = citymap.get(items);
                        sub_OnlineData(city_code);
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
        String service_domain = domain + "menus/get_states_for_sub_dealer_synce?email=" + user_email;


        Log.i("user list url", "order list url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                //  auto_searchcust.setText(namemm);
                new Sub_Dealer_Order_Main.getstate_Data().execute(response);

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

                new Sub_Dealer_Order_Main.getDistricts_Data().execute(response);

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

    public void City_OnlineData(String dis_code) {

        String domain = getResources().getString(R.string.service_domain);
        String service_domain = domain + "sub_dealers/sync_cities?district_code=" + dis_code;


        Log.i("user list url", "order list url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                //  auto_searchcust.setText(namemm);
                new Sub_Dealer_Order_Main.getcity_Data().execute(response);

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

    private class getcity_Data extends AsyncTask<String, Void, String> {
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

                        City_JSON = response.getJSONArray("cities");
                        // City = response.getJSONArray("business_product_categories");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.i("volley", "response cities Length: " + City_JSON.length());
                    Log.d("volley", "cities" + City_JSON.toString());

                    if (City_JSON.length() <= 0) {

                        Sub_Dealer_Order_Main.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "City Record doesn't exist.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                        });
                    } else {

                        String CITY = "";
                        String CITY_Code = "";


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
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("name").trim())) {
                                            CITY = jsonObject.getString("name").trim();
                                            CITY_Code = jsonObject.getString("code").trim();
                                            results_city.add(jsonObject.getString("name").trim());
                                            citymap.put(jsonObject.getString("name").trim(), jsonObject.getString("code").trim());
                                            Log.d("CITY", "CITY" + CITY);
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
                                    adapter_city = new ArrayAdapter<String>(Sub_Dealer_Order_Main.this,
                                            android.R.layout.simple_spinner_item, results_city);
                                    adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    s_city.setAdapter(adapter_city);

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

    public void sub_OnlineData(String city_code) {

        String domain = getResources().getString(R.string.service_domain);
        String service_domain = domain + "sub_dealers/sync_cities?city_code=" + city_code;


        Log.i("user Sub Dealer url", "Sub Dealer url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Sub_Dealer_Order_Main.getsubDealer_Data().execute(response);

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

                        SubDealer_JSON = response.getJSONArray("cities");
                        // City = response.getJSONArray("business_product_categories");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.i("volley", "response cities Length: " + City_JSON.length());
                    Log.d("volley", "cities" + City_JSON.toString());

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
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("name").trim()))
                                        {
                                            SubDealerModel di = new SubDealerModel();

                                            di.name = jsonObject.getString("firm_name").trim();
                                            di.proprietor_mobile1 = jsonObject.getString("proprietor_mobile1").trim();
                                            di.proprietor_name1 = jsonObject.getString("proprietor_name1").trim();
                                            di.proprietor_email1 = jsonObject.getString("proprietor_email1").trim();
                                            di.proprietor_mobile2 = jsonObject.getString("proprietor_mobile2").trim();
                                            di.proprietor_name2 = jsonObject.getString("proprietor_name2").trim();
                                            di.proprietor_email2 = jsonObject.getString("proprietor_email2").trim();
                                            di.shop_name = jsonObject.getString("shop_name").trim();
                                            di.Stage = jsonObject.getString("stage").trim();

                                            di.p_approval1 = "Approval 1 : "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("approval_status1").trim());

                                            di.p_approval2 = "Approval 2 : "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("approval_status2").trim());

                                            di.p_approval3 = "Approval 3 : "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("approval_status3").trim());
                                            di.city = jsonObject.getString("city_name").trim();
                                            di.code = jsonObject.getString("code").trim();
                                            di.p_approved = jsonObject.getString("approved").trim();

                                            di.remarks1 =jsonObject.getString("approver_remarks1").trim();
                                            di.remarks2 =jsonObject.getString("approver_remarks2").trim();

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
                                    AutoCompleteContactArrayAdapter adapter = new AutoCompleteContactArrayAdapter(Sub_Dealer_Order_Main.this,R.layout.auto_layout, All_sdealers);
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

        Intent i = new Intent(Sub_Dealer_Order_Main.this, Sales_Dash.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }
}

