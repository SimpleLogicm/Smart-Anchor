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
import android.support.v7.widget.SearchView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.Customer_Invoices_adapter;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.Customer_Info;


public class Customer_Invoices extends Activity {

    List<Customer_Info> Allresult = new ArrayList<Customer_Info>();
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    ProgressDialog dialog;
    RecyclerView recList;
    String Customer_id = "";
    String City_id = "";
    String Beat_id = "";
    private SearchView mSearchView;
    Customer_Invoices_adapter ca;
    String s[];
    ArrayList<String> All_customers = new ArrayList<String>();

    AutoCompleteTextView autoCompleteTextView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.customer_invoices);

        autoCompleteTextView1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        recList = (RecyclerView) findViewById(R.id.c_info_card);
        // recList.addItemDecoration(new DividerItemDecoration(Customer_Invoices.this, DividerItemDecoration.VERTICAL_LIST));

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        SharedPreferences spf = Customer_Invoices.this.getSharedPreferences("SimpleLogic", 0);
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
        mTitleTextView.setText("Customer Invoice");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Customer_Invoices.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
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

        // setupSearchView();

        dialog = new ProgressDialog(Customer_Invoices.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Customer Loading....");
        dialog.setTitle("Sales App");
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

                    ca = new Customer_Invoices_adapter(Allresult,Customer_Invoices.this);
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
                    Toast toast = Toast.makeText(Customer_Invoices.this,
                            "Customer Not Found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else {
                    for (Local_Data cn : contacts) {

                        recList.setVisibility(View.VISIBLE);


                        ci.ic_name = cn.getCUSTOMER_SHOPNAME();
                        ci.icustomer_code = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                        ci.invoice_number = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                        ci.invoice_date = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                        ci.invoice_due_date = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                        ci.invoice_due_amount = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));



                    }


                    result.add(ci);

                    ca = new Customer_Invoices_adapter(result,Customer_Invoices.this);
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
        Intent i=new Intent(Customer_Invoices.this, Customer_info_main.class);
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

                Customer_Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast toast = Toast.makeText(Customer_Invoices.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            } else {
                Customer_Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {
                        recList.setVisibility(View.VISIBLE);
                    }
                });


                List<Customer_Info> result = new ArrayList<Customer_Info>();
                for (Local_Data cn : contacts3) {

                    Customer_Info ci = new Customer_Info();
                    ci.ic_name = cn.getCUSTOMER_SHOPNAME();
                    ci.icustomer_code = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                    ci.invoice_number = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                    ci.invoice_date = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                    ci.invoice_due_date = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));
                    ci.invoice_due_amount = String.valueOf(Html.fromHtml("<b>" +"Address : "+ "</b>"+cn.getAddress()));

                    Customer_id = cn.getCust_Code();
                    City_id = cn.getCITY_ID();
                    Beat_id = cn.getBEAT_ID();

                    All_customers.add(cn.getCUSTOMER_SHOPNAME());


                    result.add(ci);
                    Allresult.add(ci);
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

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();
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
