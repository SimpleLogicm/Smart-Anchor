package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */


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
import android.text.Html;
import android.text.TextWatcher;
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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cpm.simplelogic.helper.Customer_Info;




public class Customer_info_main extends Activity {

    Calendar myCalendar;
    HashMap<String, String> hm = new HashMap<String, String>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> badapter;
    List<Customer_Info> Allresult = new ArrayList<Customer_Info>();
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    ProgressDialog dialog;
    RecyclerView recList;
    String Customer_id = "";
    String City_id = "";
    String Beat_id = "";
    private SearchView mSearchView;
    Customer_info_main_adapter ca;
    String[] s;
    ArrayList<String> All_customers = new ArrayList<String>();

    AutoCompleteTextView autoCompleteTextView1;
    ImageView close_filter;
    Button filter_submit, filter_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.customer_info_main);

        autoCompleteTextView1 = findViewById(R.id.autoCompleteTextView1);
        recList = findViewById(R.id.c_info_card);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SharedPreferences spf = Customer_info_main.this.getSharedPreferences("SimpleLogic", 0);
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
            mTitleTextView.setText("Customer Profile");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Customer_info_main.this.getSharedPreferences("SimpleLogic", 0);

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


       // setupSearchView();



        dialog = new ProgressDialog(Customer_info_main.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Customer Loading....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();

        new CustomerASN().execute();



        autoCompleteTextView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (autoCompleteTextView1.getRight() - autoCompleteTextView1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Customer_info_main.this.getCurrentFocus();
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

                    ca = new Customer_info_main_adapter(Allresult,Customer_info_main.this);
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

                Global_Data.hideSoftKeyboard(Customer_info_main.this);

                String customer_name = "";
                String address_type = "";
                if(autoCompleteTextView1.getText().toString().trim().indexOf(":") > 0)
                {
                    s = autoCompleteTextView1.getText().toString().trim().split(":");
                    customer_name = s[0].trim();
                    address_type = s[1].trim();
                }
                else
                {
                    customer_name = autoCompleteTextView1.getText().toString().trim();
                }
//
//
//				Global_Data.credit_limit_amount = "";
//				Global_Data.outstandings_amount = "";

                List<Local_Data> contacts = dbvoc.getCustomerCode(customer_name);

                Customer_Info ci = new Customer_Info();
                List<Customer_Info> result = new ArrayList<Customer_Info>();
                if(contacts.size() <= 0)
                {
                    Toast toast = Toast.makeText(Customer_info_main.this,
                            "Customer Not Found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    for (Local_Data cn : contacts) {

                        recList.setVisibility(View.VISIBLE);


                        ci.name = String.valueOf(Html.fromHtml("<b>" +"Name : "+ "</b>"+cn.getCUSTOMER_NAME()));
                        ci.mobile = cn.getMOBILE_NO();
                        ci.shop_name = cn.getCUSTOMER_SHOPNAME();
                        ci.icustomer_code = cn.getCust_Code();
                        ci.address = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                        ci.latlong = cn.getlatitude()+","+cn.getlongitude();
                        Customer_id = cn.getCust_Code();
                        City_id = cn.getCITY_ID();
                        Beat_id = cn.getBEAT_ID();



                    }

                    List<Local_Data> cityi = dbvoc.getcityByState_idn(City_id);
                    if(cityi.size() > 0)
                    {
                        for (Local_Data cnnn : cityi) {
                            ci.city_name = String.valueOf(Html.fromHtml("<b>" +"City Name : "+ "</b>"+cnnn.getCityName()));
                        }
                    }

                    List<Local_Data> beati = dbvoc.getbeatByCityIDn(Beat_id);
                    if(beati.size() > 0)
                    {
                        for (Local_Data cnnnn : beati) {
                            //  ci.Beat_name = cnnnn.getCityName();
                            ci.Beat_name = String.valueOf(Html.fromHtml("<b>" +"Beat Name : "+ "</b>"+cnnnn.getCityName()));
                        }
                    }

                    List<Local_Data> contactlimit = dbvoc.getCreditprofileData(Customer_id);
                    if(contactlimit.size() > 0) {

                        for (Local_Data cnn : contactlimit) {

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cnn.get_credit_limit())) {
                                Double credit_limit =  ((Double.valueOf(cnn.get_credit_limit())));
                                ci.credit_limit = String.valueOf(Html.fromHtml("<b>" +"Credit Limit : "+ "</b>"+String.format("%.2f",credit_limit)));
                            } else {
                                ci.credit_limit = String.valueOf(Html.fromHtml("<b>" +"Credit Limit Not Found"+ "</b>"));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cnn.get_shedule_outstanding_amount())) {
                                Double amt_outstanding =  ((Double.valueOf(cnn.get_shedule_outstanding_amount())));
                                ci.amount1 = String.valueOf(Html.fromHtml("<b>" +"Amount Outstanding : "+ "</b>"+String.format("%.2f",amt_outstanding)));


                            } else {

                                ci.amount1  = String.valueOf(Html.fromHtml("<b>" +"Amount Outstanding Not Found"+ "</b>"));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cnn.getAmmount_overdue())) {
                                Double amt_overdue =  ((Double.valueOf(cnn.getAmmount_overdue())));
                                ci.amount2 = String.valueOf(Html.fromHtml("<b>" +"Amount Overdue : "+ "</b>"+String.format("%.2f",amt_overdue)));


                            } else {
                                ci.amount2  = String.valueOf(Html.fromHtml("<b>" +"Amount Overdue Not Found"+ "</b>"));

                            }


                            }
                        }
                        else
                        {
                            ci.credit_limit = String.valueOf(Html.fromHtml("<b>" +"Credit Limit Not Found"+ "</b>"));
                            ci.amount1  = String.valueOf(Html.fromHtml("<b>" +"Amount Outstanding Not Found"+ "</b>"));
                            ci.amount2  = String.valueOf(Html.fromHtml("<b>" +"Amount Overdue Not Found"+ "</b>"));
                        }

                        result.add(ci);

                    ca = new Customer_info_main_adapter(result,Customer_info_main.this);
                    recList.setAdapter(ca);
                    ca.notifyDataSetChanged();
                }


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
           Intent i=new Intent(Customer_info_main.this, Sales_Dash.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();

    }


    private  class CustomerASN extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            List<Local_Data> contacts3 = dbvoc.getCustomersForList();

            if (contacts3.size() <= 0) {
                // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                Customer_info_main.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(Customer_info_main.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            } else {
                Customer_info_main.this.runOnUiThread(new Runnable() {
                    public void run() {
                        recList.setVisibility(View.VISIBLE);
                    }
                });


                List<Customer_Info> result = new ArrayList<Customer_Info>();
                for (Local_Data cn : contacts3) {

                    Customer_Info ci = new Customer_Info();
                    ci.name = String.valueOf(Html.fromHtml("<b>" +"Name : "+ "</b>"+cn.getCUSTOMER_NAME()));
                    ci.mobile = cn.getMOBILE_NO();
                    ci.shop_name = cn.getCUSTOMER_SHOPNAME();
                    ci.icustomer_code = cn.getCust_Code();
                    ci.address = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                    ci.latlong = cn.getlatitude()+","+cn.getlongitude();
                    Customer_id = cn.getCust_Code();
                    City_id = cn.getCITY_ID();
                    Beat_id = cn.getBEAT_ID();

                    All_customers.add(cn.getCUSTOMER_SHOPNAME());

                    List<Local_Data> cityi = dbvoc.getcityByState_idn(City_id);
                    if(cityi.size() > 0)
                    {
                        for (Local_Data cnnn : cityi) {
                            ci.city_name = String.valueOf(Html.fromHtml("<b>" +"City Name : "+ "</b>"+cnnn.getCityName()));
                        }
                    }

                    List<Local_Data> beati = dbvoc.getbeatByCityIDn(Beat_id);
                    if(beati.size() > 0)
                    {
                        for (Local_Data cnnnn : beati) {
                            //  ci.Beat_name = cnnnn.getCityName();
                            ci.Beat_name = String.valueOf(Html.fromHtml("<b>" +"Beat Name : "+ "</b>"+cnnnn.getCityName()));
                        }
                    }

                    List<Local_Data> contactlimit = dbvoc.getCreditprofileData(Customer_id);
                    if(contactlimit.size() > 0) {

                        for (Local_Data cnn : contactlimit) {

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cnn.get_credit_limit())) {
                                Double credit_limit =  ((Double.valueOf(cnn.get_credit_limit())));
                                ci.credit_limit = String.valueOf(Html.fromHtml("<b>" +"Credit Limit : "+ "</b>"+String.format("%.2f",credit_limit)));
                            } else {
                                ci.credit_limit = String.valueOf(Html.fromHtml("<b>" +"Credit Limit Not Found"+ "</b>"));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cnn.get_shedule_outstanding_amount())) {
                                Double amt_outstanding =  ((Double.valueOf(cnn.get_shedule_outstanding_amount())));
                                ci.amount1 = String.valueOf(Html.fromHtml("<b>" +"Amount Outstanding : "+ "</b>"+String.format("%.2f",amt_outstanding)));


                            } else {

                                ci.amount1  = String.valueOf(Html.fromHtml("<b>" +"Amount Outstanding Not Found"+ "</b>"));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cnn.getAmmount_overdue())) {
                                Double amt_overdue =  ((Double.valueOf(cnn.getAmmount_overdue())));
                                ci.amount2 = String.valueOf(Html.fromHtml("<b>" +"Amount Overdue : "+ "</b>"+String.format("%.2f",amt_overdue)));


                            } else {
                                ci.amount2  = String.valueOf(Html.fromHtml("<b>" +"Amount Overdue Not Found"+ "</b>"));

                            }


                        }
                    }
                    else
                    {
                        ci.credit_limit = String.valueOf(Html.fromHtml("<b>" +"Credit Limit Not Found"+ "</b>"));
                        ci.amount1  = String.valueOf(Html.fromHtml("<b>" +"Amount Outstanding Not Found"+ "</b>"));
                        ci.amount2  = String.valueOf(Html.fromHtml("<b>" +"Amount Overdue Not Found"+ "</b>"));
                    }

                    result.add(ci);
                    Allresult.add(ci);
                }
                ca = new Customer_info_main_adapter(result,Customer_info_main.this);
                Customer_info_main.this.runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        recList.setAdapter(ca);
                        ca.notifyDataSetChanged();
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Customer_info_main.this, android.R.layout.simple_spinner_dropdown_item,
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

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();
            Customer_info_main.this.runOnUiThread(new Runnable() {
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

//    private void setupSearchView() {
//       // mSearchView.setIconifiedByDefault(false);
//        mSearchView.setOnQueryTextListener(this);
//        mSearchView.setSubmitButtonEnabled(true);
//        mSearchView.setQueryHint("Search Here");
//    }
//
//    public boolean onQueryTextChange(String newText) {
//        ca.filter(newText);
//        return true;
//    }
//
//    public boolean onQueryTextSubmit(String query) {
//        return false;
//    }



}
