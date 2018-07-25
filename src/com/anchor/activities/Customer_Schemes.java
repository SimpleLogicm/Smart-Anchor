package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.Customer_Schemes_adapter;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.Customer_Info;


public class Customer_Schemes extends Activity {



    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    String response_result = "";
    static String final_response = "";
    String url = "";
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    ProgressDialog dialog;
    RecyclerView recList;
    Customer_Schemes_adapter ca;
    String s[];
    ArrayList<String> Filter_List = new ArrayList<String>();
    AutoCompleteTextView autoCompleteTextView1;
    List<Customer_Info> result = new ArrayList<Customer_Info>();
    List<Customer_Info> result_customer = new ArrayList<Customer_Info>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.customer_schemes);

        autoCompleteTextView1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        recList = (RecyclerView) findViewById(R.id.c_info_card);

        // recList.addItemDecoration(new DividerItemDecoration(Customer_Schemes.this, DividerItemDecoration.VERTICAL_LIST));

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SharedPreferences spf = Customer_Schemes.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Customer Schemes");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Customer_Schemes.this.getSharedPreferences("SimpleLogic", 0);

        try
        {
            int target  = (int) Math.round(sp.getFloat("Target",0));
            int achieved  = (int) Math.round(sp.getFloat("Achived",0));
            Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
            if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
            {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
            }else
            {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
            }

        }catch(Exception ex){ex.printStackTrace();}


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        cd = new ConnectionDetector(getApplicationContext());


        get_scheme_Data();



        autoCompleteTextView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (autoCompleteTextView1.getRight() - autoCompleteTextView1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Customer_Schemes.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        autoCompleteTextView1.showDropDown();
                        return true;
                    }
                }
                return false;
            }
        });

        autoCompleteTextView1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(autoCompleteTextView1.getText().toString().trim().length() == 0) {

                    ca = new Customer_Schemes_adapter(result,Customer_Schemes.this);
                    recList.setAdapter(ca);
                    ca.notifyDataSetChanged();



                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                Global_Data.hideSoftKeyboard(Customer_Schemes.this);

                String customer_name = "";
                customer_name = autoCompleteTextView1.getText().toString().trim();

                result_customer = new ArrayList<Customer_Info>();

                for (int i = 0; i < result.size(); i++) {
                    Customer_Info ci = result.get(i);

                    if (ci.c_header.equalsIgnoreCase(customer_name)) {

                        Customer_Info ci1 = result.get(i);

                        if(Global_Data.Scheme_report_Type.equalsIgnoreCase("scheme_management"))
                        {
                            ci1.c_header =ci.c_header;
                            ci1.c_detail1 = ci.c_detail1;
                            ci1.c_detail2 =  ci.c_detail2;
                            ci1.c_detail3 =  ci.c_detail3;
                            ci1.c_detail4 =  ci.c_detail4;
                            ci1.c_detail5 = ci.c_detail5;
                            ci1.c_detail6 = "";
                            ci1.c_detail7 = "";


                        }
                        else
                        if(Global_Data.Scheme_report_Type.equalsIgnoreCase("shortfall_report"))
                        {
                            ci1.c_header =ci.c_header;
                            ci1.c_detail1 = ci.c_detail1;
                            ci1.c_detail2 =  ci.c_detail2;
                            ci1.c_detail3 =  ci.c_detail3;
                            ci1.c_detail4 =  ci.c_detail4;
                            ci1.c_detail5 = ci.c_detail5;
                            ci1.c_detail6 = ci.c_detail6;
                            ci1.c_detail7 = "";


                        }

                        result_customer.add(ci1);
                    }
                }

                ca = new Customer_Schemes_adapter(result_customer, Customer_Schemes.this);
                recList.setAdapter(ca);
                ca.notifyDataSetChanged();
            }

        });


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
        Global_Data.customer_code = "";
        Intent i=new Intent(Customer_Schemes.this, Customer_info_main.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }






    public void get_scheme_Data()
    {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {
            SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
            String device_id = sp.getString("devid", "");

            dialog = new ProgressDialog(Customer_Schemes.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please Wait....");
            dialog.setTitle("Sales App");
            dialog.setCancelable(false);
            dialog.show();

            String  domain = getResources().getString(R.string.service_domain);


            url = domain+"scheme_managements?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL+"&customer_code="+Global_Data.customer_code+"&scheme_report_type="+Global_Data.Scheme_report_Type;





            Log.i("volley", "url: " + url);
            Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL);

            StringRequest jsObjRequest = null;

            jsObjRequest = new StringRequest(url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("volley", "response: " + response);
                    final_response = response;

                    new Customer_Schemes.schemeData().execute(response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();
                            //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(),
                                        "Network Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(),
                                        "Server AuthFailureError  Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getApplicationContext(),
                                        "Server   Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(),
                                        "Network   Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getApplicationContext(),
                                        "ParseError   Error",
                                        Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                            }
                            dialog.dismiss();
                            Intent launch = new Intent(Customer_Schemes.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                            // finish();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 300000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            // requestQueue.se
            //requestQueue.add(jsObjRequest);
            jsObjRequest.setShouldCache(false);
            requestQueue.getCache().clear();
            requestQueue.add(jsObjRequest);




        }
        else
        {
            Toast toast = Toast.makeText(Customer_Schemes.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            Intent i=new Intent(Customer_Schemes.this, Customer_info_main.class);
            startActivity(i);
            finish();
        }
    }

    private  class  schemeData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                } else {
                    response_result = "data";
                }


                if (response_result.equalsIgnoreCase("doesn't exist")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    Customer_Schemes.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Customer_Schemes.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });

                }
                else
                if (response_result.equalsIgnoreCase("No Data Found")) {

                    Customer_Schemes.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent launch = new Intent(Customer_Schemes.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                        }
                    });


                }
                else
                if (response_result.equalsIgnoreCase("User not registered with this device")) {

                    Customer_Schemes.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent launch = new Intent(Customer_Schemes.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                        }
                    });

                }
                else if (response_result.equalsIgnoreCase("Device not registered"))
                {
                    Customer_Schemes.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent launch = new Intent(Customer_Schemes.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                        }
                    });

                }
                else
                {



                    JSONArray Customer_Schemes = response.getJSONArray("result");
                    Log.i("volley", "response reg Customer_Schemes Length: " + Customer_Schemes.length());
                    Log.d("volley", "Customer_Schemes" + Customer_Schemes.toString());

                    if (Customer_Schemes.length() <= 0) {

                        Customer_Schemes.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();


                                    Toast toast = Toast.makeText(Customer_Schemes.this, "Schemes Not Found.", Toast.LENGTH_LONG);toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent launch = new Intent(Customer_Schemes.this, Customer_info_main.class);
                                    startActivity(launch);
                                    finish();




                            }
                        });
                    } else {


                        Filter_List.clear();
                        result.clear();
                        for (int i = 0; i < Customer_Schemes.length(); i++) {

                            JSONObject jsonObject = Customer_Schemes.getJSONObject(i);

                            Customer_Info ci = new Customer_Info();

                            if(Global_Data.Scheme_report_Type.equalsIgnoreCase("scheme_management"))
                            {
                                ci.c_header =Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("organization_name"));
                                ci.c_detail1 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("quantity"));
                                ci.c_detail2 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("line_amount"));
                                ci.c_detail3 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("tax_amount"));
                                ci.c_detail4 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("total_amount"));
                                ci.c_detail5 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("gift_amount"));
                                ci.c_detail6 = "";
                                ci.c_detail7 = "";

                                Filter_List.add(jsonObject.getString("organization_name"));
                            }
                            else
                            if(Global_Data.Scheme_report_Type.equalsIgnoreCase("shortfall_report"))
                            {
                                ci.c_header =Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("current_volume"));
                                ci.c_detail1 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("current_eligibility"));
                                ci.c_detail2 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("gift_detail"));
                                ci.c_detail3 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("gift_value"));
                                ci.c_detail4 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("short_volume_for_next_slab"));
                                ci.c_detail5 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("next_scheme_slab"));
                                ci.c_detail6 = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("next_scheme_value"));
                                ci.c_detail7 = "";

                                Filter_List.add(jsonObject.getString("current_volume"));
                            }

                            result.add(ci);

                        }

                        ca = new Customer_Schemes_adapter(result,Customer_Schemes.this);
                        Customer_Schemes.this.runOnUiThread(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                recList.setAdapter(ca);
                                ca.notifyDataSetChanged();
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Customer_Schemes.this, android.R.layout.simple_spinner_dropdown_item,
                                        Filter_List);
                                autoCompleteTextView1.setThreshold(1);// will start working from
                                // first character
                                autoCompleteTextView1.setAdapter(adapter);// setting the adapter
                                // data into the
                                // AutoCompleteTextView
                                autoCompleteTextView1.setTextColor(Color.BLACK);
                            }
                        });

                    }

                    Customer_Schemes.this.runOnUiThread(new Runnable() {
                        public void run() {

                            dialog.dismiss();
                        }
                    });


                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();


                Customer_Schemes.this.runOnUiThread(new Runnable() {
                    public void run() {

                        Customer_Schemes.this.runOnUiThread(new Runnable() {
                            public void run() {


                                dialog.dismiss();

                                Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent launch = new Intent(Customer_Schemes.this, Customer_info_main.class);
                                startActivity(launch);
                                finish();
                            }
                        });
                    }
                });

            }


            Customer_Schemes.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Customer_Schemes.this.runOnUiThread(new Runnable() {
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
}
