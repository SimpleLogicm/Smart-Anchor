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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.OutstandingAdapter;
import com.anchor.helper.MyValueFormatter;
import com.anchor.helper.PrefManager;
import com.anchor.helper.SimpleDividerItemDecoration;
import com.anchor.model.OutstandingModel;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class OutstandingActivity extends Activity {
    
    BarChart barChart;
    RecyclerView listOutstanding;
    OutstandingAdapter outstandingAdapter;
    List<OutstandingModel> outstanding_list = new ArrayList<>();
    Button btn_seedetails,btn_paynow;
    ConnectionDetector cd;
    static String final_response = "";
    String response_result = "";
    private ProgressDialog dialog;
    ArrayList<String> labels = new ArrayList<String>();
    ArrayList<BarEntry> bargroup1 = new ArrayList<>();
    ArrayList<BarEntry> bargroup2 = new ArrayList<>();
    ArrayList<BarEntry> bargroup3 = new ArrayList<>();
    private PrefManager prefManager;
    Float credit_limit_total = 0.0f;
    Float outstanding_total = 0.0f;
    Float overdue_total = 0.0f;
    TextView total_creditlimit,total_outstanding,total_overdue;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_outstanding);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        total_creditlimit = findViewById(R.id.total_creditlimit);
        total_outstanding = findViewById(R.id.total_outstanding);
        total_overdue = findViewById(R.id.total_overdue);
        btn_seedetails = findViewById(R.id.btn_seedetails);
        btn_paynow = findViewById(R.id.btn_paynow);
        listOutstanding = findViewById(R.id.list_outstanding);
        barChart = findViewById(R.id.chart);

        prefManager = new PrefManager(this);

        listOutstanding.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        outstandingAdapter = new OutstandingAdapter(getApplicationContext(), outstanding_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        listOutstanding.setLayoutManager(mLayoutManager);
        listOutstanding.setItemAnimator(new DefaultItemAnimator());
        listOutstanding.setAdapter(outstandingAdapter);

//        ImageView Header_logo = (ImageView)findViewById(R.id.Header_logo);
////        TextView mTitleTextView = (TextView)findViewById(R.id.screenname);
////        mTitleTextView.setText("Outstanding/Overdue");
////
////        TextView todaysTarget = (TextView)findViewById(R.id.todaysTarget);
////
////        SharedPreferences sp = OutstandingActivity.this.getSharedPreferences("SimpleLogic", 0);
////
////        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
////            //todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
////            todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
////        }
////
////        Header_logo.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                finish();
////            }
////        });


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
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText(Global_Data.CUSTOMER_SERVICE_FLAG);

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = OutstandingActivity.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
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
            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}

        btn_seedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.GLOvel_BU =  "";
                Intent m = new Intent(getApplicationContext(),OutstandingDetails.class);
                m.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(m);
                finish();
            }
        });

        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        cd  = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet())
        {
            if(dialog == null) {
                dialog = new ProgressDialog(OutstandingActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                dialog.setMessage("Please Wait....");
                dialog.setTitle("Anchor App");
                dialog.setCancelable(false);
                dialog.show();
            }
            OutstandingActivityList_Result();
        }
        else
        {
            Toast toast = Toast.makeText(OutstandingActivity.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(order_home);
            finish();

        }

    }

    public void OutstandingActivityList_Result()
    {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";//Global_Data.GLOvel_USER_EMAIL
        
        service_url = domain + "outstanding_lists?email=" + Global_Data.CUSTOMER_EMAIL;

        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new OutstandingActivity.OutstandingActivity_List_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(OutstandingActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class OutstandingActivity_List_Task  extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                } else {
                    response_result = "data";
                }


                if (response_result.equalsIgnoreCase("User doesn't exist")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    OutstandingActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            try
                            {
                                if(dialog.isShowing()){
                                    dialog.dismiss();

                                }
                            }catch(Exception ex){ex.printStackTrace();}
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(OutstandingActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(OutstandingActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else
                if(response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    OutstandingActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            try
                            {
                                if(dialog.isShowing()){
                                    dialog.dismiss();

                                }
                            }catch(Exception ex){ex.printStackTrace();}

                            Toast toast = Toast.makeText(OutstandingActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(OutstandingActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {

                    //dbvoc.getDeleteTable("delivery_products");

                    JSONArray credit_profiles = response.getJSONArray("credit_profiles");
                    Log.i("volley", "response credit_profiles Length: " + credit_profiles.length());
                    Log.d("volley", "orders" + credit_profiles.toString());

                    if (credit_profiles.length() <= 0) {

                        OutstandingActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                try
                                {
                                    if(dialog.isShowing()){
                                        dialog.dismiss();

                                    }
                                }catch(Exception ex){ex.printStackTrace();}
                                Toast toast = Toast.makeText(OutstandingActivity.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent intent = new Intent(OutstandingActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {


                        labels.clear();
                        bargroup1.clear();
                        bargroup2.clear();
                        bargroup3.clear();
                        credit_limit_total = 0.0f;
                        outstanding_total = 0.0f;
                        overdue_total = 0.0f;

                        for (int i = 0; i < credit_profiles.length(); i++)
                        {

                            JSONObject jsonObject = credit_profiles.getJSONObject(i);

                            outstanding_list.add(new OutstandingModel(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("business_unit")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("credit_limit")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("amount_outstanding")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("amount_overdue"))));

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("credit_limit")))
                            {
                                bargroup1.add(new BarEntry(Float.valueOf(jsonObject.getString("credit_limit")), i));
                            }
                            else
                            {
                                bargroup1.add(new BarEntry(Float.valueOf(0.0f), i));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("amount_outstanding")))
                            {
                                bargroup2.add(new BarEntry(Float.valueOf(jsonObject.getString("amount_outstanding")), i));
                            }
                            else
                            {
                                bargroup2.add(new BarEntry(Float.valueOf(0.0f), i));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("amount_overdue")))
                            {
                                bargroup3.add(new BarEntry(Float.valueOf(jsonObject.getString("amount_overdue")), i));
                            }
                            else
                            {
                                bargroup3.add(new BarEntry(Float.valueOf(0.0f), i));
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("business_unit")))
                            {
                                labels.add(jsonObject.getString("business_unit"));
                            }
                            else
                            {
                                labels.add("");
                            }






                            try
                            {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("credit_limit")))
                                {
                                    credit_limit_total +=Float.valueOf(jsonObject.getString("credit_limit"));
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("amount_outstanding")))
                                {
                                    outstanding_total +=Float.valueOf(jsonObject.getString("amount_outstanding"));
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("amount_overdue")))
                                {
                                    overdue_total +=Float.valueOf(jsonObject.getString("amount_overdue"));
                                }

                            }catch(Exception ex){
                                ex.printStackTrace();
                            }

                        }


                        OutstandingActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run() {

                                total_creditlimit.setText(String.valueOf(formatter.format(credit_limit_total)));
                                total_outstanding.setText(String.valueOf(formatter.format(outstanding_total)));
                                total_overdue.setText(String.valueOf(formatter.format(overdue_total)));

                                outstandingAdapter.notifyDataSetChanged();

                                BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Credit Limit");  // creating dataset for group1
                                barDataSet1.setColor(Color.rgb(66, 133, 245));

                                //barDataSet1.setColor(Color.rgb(0, 155, 0));
                                //barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                                BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Outstanding"); // creating dataset for group1
                                barDataSet2.setColor(Color.rgb(220, 67, 54));
                                //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                                BarDataSet barDataSet3 = new BarDataSet(bargroup3, "Overdue"); // creating dataset for group1
                                barDataSet3.setColor(Color.rgb(255, 255, 0));
                                //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

                                barDataSet1.setValueFormatter(new MyValueFormatter());
                                barDataSet2.setValueFormatter(new MyValueFormatter());
                                barDataSet3.setValueFormatter(new MyValueFormatter());

                                ArrayList<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
                                dataSets.add(barDataSet1);
                                dataSets.add(barDataSet2);
                                dataSets.add(barDataSet3);

                                // dataSets.add(barDataSet3);
                                BarData data = new BarData(labels, dataSets); // initialize the Bardata with argument labels and dataSet
                                barChart.setData(data);

                                barChart.getAxisRight().setEnabled(false);
                                barChart.getAxisRight().setDrawGridLines(false);
                              //  barChart.getAxisLeft().setDrawGridLines(false);
                                barChart.getXAxis().setDrawGridLines(false);
                                //barChart.setVisibleXRangeMaximum(5);
                               // barChart.setVisibleXRangeMinimum(5);

                                barChart.setDescription("");  // set the description
                                barChart.animateY(5000);
                                barChart.invalidate();
                            }
                        });
                    }

                    OutstandingActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            try
                            {
                                if(dialog.isShowing()){
                                    dialog.dismiss();

                                }
                            }catch(Exception ex){ex.printStackTrace();}

                        }
                    });
                    //	dialog.dismiss();

                    //finish();

                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(OutstandingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                OutstandingActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        try
                        {
                            if(dialog.isShowing()){
                                dialog.dismiss();

                            }
                        }catch(Exception ex){ex.printStackTrace();}
                    }
                });

            }


            OutstandingActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    try
                    {
                        if(dialog.isShowing()){
                            dialog.dismiss();

                        }
                    }catch(Exception ex){ex.printStackTrace();}
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            OutstandingActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                        try
                        {
                            if(dialog.isShowing()){
                                dialog.dismiss();

                            }
                        }catch(Exception ex){ex.printStackTrace();}

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
        //super.onBackPressed();
        Global_Data.GLOvel_BU =  "";
        Intent i = new Intent(OutstandingActivity.this,Order.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}