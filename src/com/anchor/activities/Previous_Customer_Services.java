package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.FoldingCellListAdapter;
import com.anchor.helper.PrefManager;
import com.anchor.model.CustomerServiceModel;
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
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Global_Data.p_cus_services_status_check;

public class Previous_Customer_Services extends Activity {
    private ProgressDialog dialog;
    static String final_response = "";
    String response_result = "";
    private Toolbar toolbar;
    ConnectionDetector cd;
    FoldingCellListAdapter adapter;
    private CoordinatorLayout coordinatorLayout;
    Button c_new_button, c_cancel_button;
    ListView theListView;
    List<CustomerServiceModel> customer_service_list = new ArrayList<>();
    private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.previous_customer_services);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
//                .coordinatorLayout);
//
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
//        }
        prefManager = new PrefManager(this);
        dialog = new ProgressDialog(Previous_Customer_Services.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        // get our list view
        theListView = findViewById(R.id.mainListView);
        c_new_button =  findViewById(R.id.c_new_button);
        c_cancel_button =  findViewById(R.id.c_cancel_button);

        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Customer Services");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Previous_Customer_Services.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
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
            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}


        adapter = new FoldingCellListAdapter(this, customer_service_list);

        // add default btn handler for each request btn on each item if custom handler not found
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });

        // set elements to adapter
        theListView.setAdapter(adapter);

        dialog.setMessage("Please Wait....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();

        cd  = new ConnectionDetector(getApplicationContext());

        if (cd.isConnectingToInternet())
        {
            CustomerServiceResult();
        }
        else
        {
            Toast toast = Toast.makeText(Previous_Customer_Services.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(order_home);
            finish();

        }

        // set on click event listener to list view
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(pos);
            }
        });

        c_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(Previous_Customer_Services.this, CustomerServicesActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                startActivity(i);
                finish();

            }
        });

        c_new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Product Quality")){
                    Intent i = new Intent(Previous_Customer_Services.this, NewMarketingToolActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(Previous_Customer_Services.this,ComplaintsActivity.class);
                    startActivity(i);
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
        // TODO Auto-generated method stub
        //super.onBackPressed();

        Intent i = new Intent(Previous_Customer_Services.this, CustomerServicesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

    public void CustomerServiceResult()
    {
        String domain = getResources().getString(R.string.service_domain);

       // String domain = "https://ecart.anchor-group.in/metal/api/v1/";

        Log.i("volley", "domain: " + domain);
        //Log.i("volley", "email: " + Global_Data.CityName);

        StringRequest jsObjRequest = null;
        String service_url = "";

        //service_url = domain + "beats/beats_performance?imei_no=" + Global_Data.CityName + "&email=" + Global_Data.CityName;

        //Global_Data.CUSTOMER_EMAIL = "priyanka.konka@simplelogic.in";

        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Marketing Tool"))
        {
            service_url = domain + "customer_service_complaints/display_list?email="+ Global_Data.CUSTOMER_EMAIL+"&type="+ "marketing_tool";
        }
        else
        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Product Quality"))
        {
            service_url = domain + "customer_service_complaints/display_list?email="+ Global_Data.CUSTOMER_EMAIL+"&type="+ "product_quality";
        }
        else
        {
            service_url = domain + "customer_service_complaints/display_list?email="+ Global_Data.CUSTOMER_EMAIL+"&type="+prefManager.getCustomer_service_type().toLowerCase().toLowerCase();
        }


        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new CustomerServiceTask().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(Previous_Customer_Services.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
                        } else {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 600000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class CustomerServiceTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {

            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                } else {
                    response_result = "data";
                }

                if (response_result.equalsIgnoreCase("Customer Not Found.")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                        public void run() {

                            // upcoming_recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Previous_Customer_Services.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            dialog.dismiss();
//                            Intent intent = new Intent(Previous_Customer_Services.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    });
                }
                else
                if(response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                        public void run() {


                            // upcoming_recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Toast toast = Toast.makeText(Previous_Customer_Services.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Previous_Customer_Services.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {

                    //dbvoc.getDeleteTable("delivery_products");

                    p_cus_services_status_check.clear();
                    JSONArray users = response.getJSONArray("customer_service_complaints");
                    Log.i("volley", "response users Length: " + users.length());
                    Log.d("volley", "users" + users.toString());
                    if (users.length() <= 0) {

                        Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                            public void run() {

                                //upcoming_recyclerView.hideShimmerAdapter();
                                Toast toast = Toast.makeText(Previous_Customer_Services.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

//                                Intent intent = new Intent(Org_Events.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        });
                    } else {

                        //list_Empoloyee.clear();
                        customer_service_list.clear();
                        //All_Employees.clear();
                        for (int i = 0; i < users.length(); i++)
                        {

                            JSONObject jsonObject = users.getJSONObject(i);

                            customer_service_list.add(new CustomerServiceModel(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("product_name")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("type_complaint")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("date")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("action_date")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("detail1")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("response")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("respondent_name")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("status")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("type_complaint")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("detail2")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("detail3")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("detail4")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("detail5")), Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("detail6")),""));
                            //list_Empoloyee.add(jsonObject.getString("name"));

                            //All_Employees.add(emp_list.get(i));

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("status")))
                            {
                                p_cus_services_status_check.put(i,jsonObject.getString("status"));
                            }
                            else
                            {
                                p_cus_services_status_check.put(i,"");
                            }



                        }


                        Previous_Customer_Services.this.runOnUiThread(new Runnable()
                        {
                            public void run() {

                                // upcoming_recyclerView.hideShimmerAdapter();
                                adapter.notifyDataSetChanged();

//                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(BirthdayActivity.this,
//                                        android.R.layout.simple_spinner_dropdown_item,list_Empoloyee);
//                                autoCompleteTextView.setThreshold(1);// will start working from
//// first character
//                                autoCompleteTextView.setAdapter(adapter);// setting the adapter
//// data into the
//// AutoCompleteTextView
//                                autoCompleteTextView.setTextColor(Color.BLACK);

                                //recyclerView.hideShimmerAdapter();
                                // Org_Events_adapter.notifyDataSetChanged();

                                //AutoCompleteEmployeeArrayAdapter adapter = new AutoCompleteEmployeeArrayAdapter(Org_Events.this,R.layout.emp_search_layout, All_Employees);

                                // autoCompleteTextView.setAdapter(adapter);

                                //autoCompleteTextView.setTextColor(Color.BLACK);
                                // autoCompleteTextView.setDropDownBackgroundDrawable(new ColorDrawable(getApplicationContext().getResources().getColor(R.color.white)));
                            }
                        });



                    }

                    Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                        public void run() {

                            //upcoming_recyclerView.hideShimmerAdapter();
                        }
                    });
                    //	dialog.dismiss();
                    //finish();
                }

                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();
                dialog.dismiss();
                Intent intent = new Intent(Previous_Customer_Services.this, MainActivity.class);
                startActivity(intent);
                finish();

                Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                    public void run() {

                        // upcoming_recyclerView.hideShimmerAdapter();
                    }
                });

            }


            Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                public void run() {

                    //upcoming_recyclerView.hideShimmerAdapter();
                }
            });
            dialog.dismiss();
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Previous_Customer_Services.this.runOnUiThread(new Runnable() {
                public void run() {
                    //upcoming_recyclerView.hideShimmerAdapter();
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
}
