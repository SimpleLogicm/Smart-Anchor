package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.Customer_Invoices_adapter;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.Customer_Info;


public class Customer_Invoices extends Activity {

    String G_RadioG_valueC = "";
    String G_c_start_date_value = "";
    String G_c_end_date_value = "";
    String G_c_all_dates_value = "";
    String  G_Filter_Flag = "";

    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    String response_result = "";
    static String final_response = "";
    Calendar myCalendar;
    HashMap<String, String> hm = new HashMap<String, String>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> badapter;
    DatePickerDialog.OnDateSetListener date,date1;
    ImageView filter_btn, close_filter;
    Button filter_submit, filter_clear;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    ProgressDialog dialog;
    RecyclerView recList;
    String Customer_id = "";
    String City_id = "";
    String Beat_id = "";
    Customer_Invoices_adapter ca;
    String s[];
    ArrayList<String> All_customers = new ArrayList<String>();
    EditText c_start_date,c_end_date;
    AutoCompleteTextView autoCompleteTextView1;
    List<Customer_Info> result = new ArrayList<Customer_Info>();
    List<Customer_Info> result_customer = new ArrayList<Customer_Info>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.customer_invoices);

        autoCompleteTextView1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        recList = (RecyclerView) findViewById(R.id.c_info_card);
        filter_btn = (ImageView) findViewById(R.id.filter_btn);
        // recList.addItemDecoration(new DividerItemDecoration(Customer_Invoices.this, DividerItemDecoration.VERTICAL_LIST));

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SharedPreferences spf = Customer_Invoices.this.getSharedPreferences("SimpleLogic", 0);
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
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Customer Invoice");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Customer_Invoices.this.getSharedPreferences("SimpleLogic", 0);

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
        }catch(Exception ex){ex.printStackTrace();}


        cd = new ConnectionDetector(getApplicationContext());

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        // setupSearchView();

        get_invoice_Data();

        filter_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FilterDialog();
            }
        });

        autoCompleteTextView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (autoCompleteTextView1.getRight() - autoCompleteTextView1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Customer_Invoices.this.getCurrentFocus();
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

                    ca = new Customer_Invoices_adapter(result,Customer_Invoices.this);
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

                Global_Data.hideSoftKeyboard(Customer_Invoices.this);

                String customer_name = "";
                customer_name = autoCompleteTextView1.getText().toString().trim();

                result_customer = new ArrayList<Customer_Info>();

                for (int i = 0; i < result.size(); i++) {
                    Customer_Info ci = result.get(i);

                    if (ci.invoice_number.equalsIgnoreCase(customer_name)) {

                        Customer_Info ci1 = result.get(i);

                        ci1.ic_name = ci.ic_name;
                        ci1.icustomer_code = ci.icustomer_code;
                        ci1.invoice_date = ci.invoice_date;
                        ci1.invoice_due_date = ci.invoice_due_date;
                        ci1.invoice_due_amount = ci.invoice_due_amount;

                        result_customer.add(ci1);
                    }
                }

                ca = new Customer_Invoices_adapter(result_customer, Customer_Invoices.this);
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
        Intent i=new Intent(Customer_Invoices.this, Customer_info_main.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }


    void FilterDialog() {
        final Dialog dialog1 = new Dialog(Customer_Invoices.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.customer_filter);

        myCalendar = Calendar.getInstance();

        filter_submit = (Button) dialog1.findViewById(R.id.filter_submit);
        filter_clear = (Button) dialog1.findViewById(R.id.filter_clear);
        c_start_date = (EditText) dialog1.findViewById(R.id.c_start_date);
        c_end_date = (EditText) dialog1.findViewById(R.id.c_end_date);

        final RadioButton c_all_dates = (RadioButton) dialog1.findViewById(R.id.c_all_dates);
        final RadioGroup cashradioGroup = (RadioGroup) dialog1.findViewById(R.id.cashradioGroup);
        final RadioButton radio_showall = (RadioButton) dialog1.findViewById(R.id.radio_showall);
        RadioButton radio_overdue = (RadioButton) dialog1.findViewById(R.id.radio_overdue);
        RadioButton radio_outstanding = (RadioButton) dialog1.findViewById(R.id.radio_outstanding);
        close_filter = (ImageView) dialog1.findViewById(R.id.close_filter);


        if (G_RadioG_valueC.equalsIgnoreCase("Show Overdue")) {
            cashradioGroup.check(radio_overdue.getId());
        } else if (G_RadioG_valueC.equalsIgnoreCase("Show Outstanding")) {
            cashradioGroup.check(radio_outstanding.getId());
        }
        else
        {
            cashradioGroup.check(radio_showall.getId());
        }

        if (!G_c_start_date_value.equalsIgnoreCase(""))
        {
            c_start_date.setText(G_c_start_date_value);
        }

        if (!G_c_end_date_value.equalsIgnoreCase(""))
        {
            c_end_date.setText(G_c_end_date_value);
        }

        if (!G_c_all_dates_value.equalsIgnoreCase(""))
        {
            c_all_dates.setChecked(true);
        }

        hm.clear();

        c_all_dates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    G_c_all_dates_value = "yes";
                    c_start_date.setText("");
                    c_end_date.setText("");

//                    c_start_date.setClickable(false);
//                    c_end_date.setClickable(false);
//                    c_start_date.setEnabled(false);
//                    c_end_date.setEnabled(false);

                    G_c_start_date_value = "";
                    G_c_end_date_value = "";
                }
                else
                {
                    G_c_all_dates_value = "";
                    c_start_date.setClickable(true);
                    c_end_date.setClickable(true);
                }
            }
        });
        c_end_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                c_all_dates.setChecked(false);
                G_c_all_dates_value = "";
                DatePickerDialog picker = new DatePickerDialog(Customer_Invoices.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });

        c_start_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                c_all_dates.setChecked(false);
                G_c_all_dates_value = "";
                DatePickerDialog picker = new DatePickerDialog(Customer_Invoices.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
             //   picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });


        filter_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioButtonID = cashradioGroup.getCheckedRadioButtonId();
                View radioButton = cashradioGroup.findViewById(radioButtonID);
                int idx = cashradioGroup.indexOfChild(radioButton);


                RadioButton r = (RadioButton) cashradioGroup.getChildAt(idx);
                String selectedtext = r.getText().toString();

                Log.d("Radio Value", "Radio value" + selectedtext);

                G_RadioG_valueC = selectedtext.trim();

                if(G_c_all_dates_value.equalsIgnoreCase(""))
                {
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_start_date.getText().toString()) && !Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_end_date.getText().toString())) {
                        Toast.makeText(Customer_Invoices.this, "Please select end date.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    if (!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_start_date.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_end_date.getText().toString())) {
                        Toast.makeText(Customer_Invoices.this, "Please select start date.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_start_date.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_end_date.getText().toString())) {


                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date strDate = sdf.parse(c_start_date.getText().toString());
                            Date endDate = sdf.parse(c_end_date.getText().toString());
                            Date date1 = new Date(c_start_date.getText().toString());
                            Date date2 = new Date(c_end_date.getText().toString());
                            Calendar cal1 = Calendar.getInstance();
                            Calendar cal2 = Calendar.getInstance();
                            cal1.setTime(date1);
                            cal2.setTime(date1);

                            if(strDate.after(endDate))
                            {
                                Toast toast = Toast.makeText(getApplicationContext(), " End date not a valid date.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            else
                            {
                                G_c_start_date_value = c_start_date.getText().toString();
                                G_c_end_date_value = c_end_date.getText().toString();
                                dialog1.dismiss();
                                get_invoice_Data();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                    }
                    else
                    {
                        G_c_start_date_value = c_start_date.getText().toString();
                        G_c_end_date_value = c_end_date.getText().toString();
                        dialog1.dismiss();
                        get_invoice_Data();
                    }


                }
                else
                {
                    G_c_start_date_value = "";
                    G_c_end_date_value = "";
                    get_invoice_Data();
                    dialog1.dismiss();
                }





            }
        });

        filter_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                G_RadioG_valueC = "";
                G_c_start_date_value = "";
                G_c_end_date_value = "";
                G_c_all_dates_value = "";


                cashradioGroup.check(radio_showall.getId());
                int radioButtonID = cashradioGroup.getCheckedRadioButtonId();
                View radioButton = cashradioGroup.findViewById(radioButtonID);
                int idx = cashradioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) cashradioGroup.getChildAt(idx);
                String selectedtext = r.getText().toString();
                dialog1.dismiss();
                get_invoice_Data();

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

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        c_start_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        c_end_date.setText(sdf.format(myCalendar.getTime()));
    }

    public void get_invoice_Data()
    {
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent)
        {			SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
            String device_id = sp.getString("devid", "");

            dialog = new ProgressDialog(Customer_Invoices.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please Wait....");
            dialog.setTitle("Sales App");
            dialog.setCancelable(false);
            dialog.show();

            String  domain = getResources().getString(R.string.service_domain);

            String c_value = "";

            if(G_RadioG_valueC.equalsIgnoreCase("Show Both") || G_RadioG_valueC.equalsIgnoreCase(""))
            {
                c_value = "";
            }
            else
            if(G_RadioG_valueC.equalsIgnoreCase("Show Overdue"))
            {
                c_value = "overdue";
            }
            else
            if(G_RadioG_valueC.equalsIgnoreCase("Show Outstanding"))
            {
                c_value = "outstanding";
            }

            String url = "";
            if(!G_c_all_dates_value.equalsIgnoreCase(""))
            {
                G_Filter_Flag = "true";
                if(c_value.equalsIgnoreCase(""))
                {
                    url = domain+"invoices?imei_no="+device_id+"&email="+Global_Data.CUSTOMER_EMAIL+"&customer_code="+Global_Data.customer_code;
                }
                else
                {
                    url = domain+"invoices?imei_no="+device_id+"&email="+Global_Data.CUSTOMER_EMAIL+"&customer_code="+Global_Data.customer_code+"&filter_value="+c_value;
                }

            }
            else
            if(G_c_all_dates_value.equalsIgnoreCase("") && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(G_c_start_date_value) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(G_c_end_date_value))
            {
                G_Filter_Flag = "true";

                if(c_value.equalsIgnoreCase(""))
                {
                    url = domain+"invoices?imei_no="+device_id+"&email="+Global_Data.CUSTOMER_EMAIL+"&customer_code="+Global_Data.customer_code+"&start_date="+G_c_start_date_value+"&end_date="+G_c_end_date_value;
                }
                else
                {
                    url = domain+"invoices?imei_no="+device_id+"&email="+Global_Data.CUSTOMER_EMAIL+"&customer_code="+Global_Data.customer_code+"&filter_value="+c_value+"&start_date="+G_c_start_date_value+"&end_date="+G_c_end_date_value;
                }


            }
            else
            {
                if(c_value.equalsIgnoreCase(""))
                {
                    G_Filter_Flag = "";
                    url = domain+"invoices?imei_no="+device_id+"&email="+Global_Data.CUSTOMER_EMAIL+"&customer_code="+Global_Data.customer_code;
                }
                else
                {
                    G_Filter_Flag = "true";
                    url = domain+"invoices?imei_no="+device_id+"&email="+Global_Data.CUSTOMER_EMAIL+"&customer_code="+Global_Data.customer_code+"&filter_value="+c_value;
                }

            }

            Log.i("volley", "url: " + url);
            Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

            StringRequest jsObjRequest = null;

            jsObjRequest = new StringRequest(url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("volley", "response: " + response);
                    final_response = response;

                    new Customer_Invoices.invoiceData().execute(response);

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
                            Intent launch = new Intent(Customer_Invoices.this, Customer_info_main.class);
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
            Toast toast = Toast.makeText(Customer_Invoices.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();


            Intent i=new Intent(Customer_Invoices.this, Customer_info_main.class);
            startActivity(i);
            finish();
        }
    }

    private  class  invoiceData extends AsyncTask<String, Void, String> {
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

                    Customer_Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Customer_Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });

                }
                else
                if (response_result.equalsIgnoreCase("No Data Found")) {

                    Customer_Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent launch = new Intent(Customer_Invoices.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                        }
                    });


                }
                else
                if (response_result.equalsIgnoreCase("User not registered with this device")) {

                    Customer_Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent launch = new Intent(Customer_Invoices.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                        }
                    });

                }
                else if (response_result.equalsIgnoreCase("Device not registered"))
                {
                    Customer_Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent launch = new Intent(Customer_Invoices.this, Customer_info_main.class);
                            startActivity(launch);
                            finish();
                        }
                    });

                }
                else
                {



                JSONArray customer_invoices = response.getJSONArray("invoices");
                Log.i("volley", "response reg customer_invoices Length: " + customer_invoices.length());
                Log.d("volley", "customer_invoices" + customer_invoices.toString());

                if (customer_invoices.length() <= 0) {

                    Customer_Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {

                            dialog.dismiss();

                            if(G_Filter_Flag.equalsIgnoreCase(""))
                            {
                                Toast toast = Toast.makeText(Customer_Invoices.this, "Invoices Not Found.", Toast.LENGTH_LONG);toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent launch = new Intent(Customer_Invoices.this, Customer_info_main.class);
                                startActivity(launch);
                                finish();
                            }
                            else
                            {
                                All_customers.clear();
                                result.clear();

                                ca = new Customer_Invoices_adapter(result,Customer_Invoices.this);
                                Customer_Invoices.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        dialog.dismiss();
                                        recList.setAdapter(ca);
                                        ca.notifyDataSetChanged();
                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Customer_Invoices.this, android.R.layout.simple_spinner_dropdown_item,
                                                All_customers);
                                        autoCompleteTextView1.setThreshold(1);// will start working from
                                        // first character
                                        autoCompleteTextView1.setAdapter(adapter);// setting the adapter
                                        // data into the
                                        // AutoCompleteTextView
                                        autoCompleteTextView1.setTextColor(Color.BLACK);
                                    }
                                });

                                Toast toast = Toast.makeText(Customer_Invoices.this, "Filter Invoices Data Not Found.", Toast.LENGTH_LONG);toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        }
                    });
                } else {


                    All_customers.clear();
                    result.clear();
                    for (int i = 0; i < customer_invoices.length(); i++) {

                        JSONObject jsonObject = customer_invoices.getJSONObject(i);

                        Customer_Info ci = new Customer_Info();
                        ci.ic_name = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_number"));
                        ci.icustomer_code = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("customer_id"));
                        ci.invoice_number = jsonObject.getString("invoice_number");
                        ci.invoice_date = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("date"));
                        ci.invoice_due_date = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_due_date"));
                        ci.invoice_due_amount = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_due_amount"));

                        ci.invoice_amount = Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("total"));

                        All_customers.add(jsonObject.getString("invoice_number"));
                        result.add(ci);

                    }

                    ca = new Customer_Invoices_adapter(result,Customer_Invoices.this);
                    Customer_Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            recList.setAdapter(ca);
                            ca.notifyDataSetChanged();
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Customer_Invoices.this, android.R.layout.simple_spinner_dropdown_item,
                                    All_customers);
                            autoCompleteTextView1.setThreshold(1);// will start working from
                            // first character
                            autoCompleteTextView1.setAdapter(adapter);// setting the adapter
                            // data into the
                            // AutoCompleteTextView
                            autoCompleteTextView1.setTextColor(Color.BLACK);
                        }
                    });

                    }

                Customer_Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });


            }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();


                Customer_Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {

                        Customer_Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {


                                dialog.dismiss();

                                Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent launch = new Intent(Customer_Invoices.this, Customer_info_main.class);
                                startActivity(launch);
                                finish();
                            }
                        });
                    }
                });

            }


            Customer_Invoices.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Customer_Invoices.this.runOnUiThread(new Runnable() {
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
