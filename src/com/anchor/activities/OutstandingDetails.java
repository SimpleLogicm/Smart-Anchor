package com.anchor.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.activities.Global_Data;
import com.anchor.activities.MainActivity;
import com.anchor.activities.OutstandingActivity;
import com.anchor.activities.R;
import com.anchor.adapter.OutstandingDetails_Adapter;
import com.anchor.helper.PrefManager;
import com.anchor.helper.SimpleDividerItemDecoration;
import com.anchor.model.OutstandingDetails_Model;
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
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.fangxu.allangleexpandablebutton.AllAngleExpandableButton;
import com.fangxu.allangleexpandablebutton.ButtonData;
import com.fangxu.allangleexpandablebutton.ButtonEventListener;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.opencsv.CSVWriter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class OutstandingDetails extends AppCompatActivity implements OutstandingDetails_Adapter.UserAdapterListener, OutstandingDetails_Adapter.UserAdapterListenernew,DatePickerDialog.OnDateSetListener {

    private ShimmerRecyclerView recyclerView;
    OutstandingDetails_Adapter outstandingDetails_adapter;
    List<OutstandingDetails_Model> outdetails_list = new ArrayList<>();
    DatePickerDialog datePickerDialog ;
    String click_detect_flag = "";
    static Calendar calendar ;
    static int Year, Month, Day ;
    EditText out_de_period_value,out_de_period_value2;
    ConnectionDetector cd;
    static String final_response = "";
    String response_result = "";
    private PrefManager prefManager;
    String option_flag = "";
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    AllAngleExpandableButton button;
    Spinner outstanding_category;
    ArrayAdapter<String> outstanding_category_adapter;
    private ArrayList<String> outstanding_category_results = new ArrayList<String>();
    String product_category = "";
    int check=0;
    int service_hit = 0;
    private String update_At_Date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outstanding_details);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ImageView Header_logo = (ImageView)findViewById(R.id.Header_logo);
        TextView mTitleTextView = (TextView)findViewById(R.id.screenname);
        mTitleTextView.setText("Outstanding/Overdue");


        TextView todaysTarget = (TextView)findViewById(R.id.todaysTarget);

        SharedPreferences sp = OutstandingDetails.this.getSharedPreferences("SimpleLogic", 0);

        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
            //todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
            todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
        }

        Header_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR) ;
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        prefManager = new PrefManager(this);

        outstanding_category = findViewById(R.id.outstanding_category);
        button = findViewById(R.id.out_button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.gmail, R.drawable.download};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(getApplicationContext(), drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);

        button.setEnabled(false);
        button.setClickable(false);

        recyclerView =  findViewById(R.id.ou_detail_lshimmer_recycler_view);
        out_de_period_value =  findViewById(R.id.out_de_period_value);
        out_de_period_value2 =  findViewById(R.id.out_de_period_value2);

        recyclerView.showShimmerAdapter();

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        outstandingDetails_adapter = new OutstandingDetails_Adapter(getApplicationContext(), outdetails_list,OutstandingDetails.this,OutstandingDetails.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(outstandingDetails_adapter);

        recyclerView.showShimmerAdapter();

        outstanding_category_results.clear();
        outstanding_category_results.add("Product Category");
        outstanding_category_adapter = new ArrayAdapter<String>(OutstandingDetails.this,
                android.R.layout.simple_spinner_item, outstanding_category_results);
        outstanding_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        outstanding_category.setAdapter(outstanding_category_adapter);
        //outstanding_category.setOnItemSelectedListener(this);
        // outstanding_category.setAdapter();
        //outstanding_category.setSelected(false);  // must
        // outstanding_category.setSelection(0,true);

        SpinnerInteractionListener listener = new SpinnerInteractionListener();
        outstanding_category.setOnTouchListener(listener);
        outstanding_category.setOnItemSelectedListener(listener);



        cd  = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet())
        {
            OutstandingDetailsList_Result();
        }
        else
        {
            Toast toast = Toast.makeText(OutstandingDetails.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(order_home);
            finish();

        }


        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {

                if (outdetails_list.size()>0)
                {
                    if(index  == 1)
                    {
                        option_flag = "gmail";
                        requestStoragePermission();
                    }
                    else
                    if (index ==2)
                    {
                        option_flag = "csv";
                        requestStoragePermission();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(OutstandingDetails.this,"Record not exist.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });


        out_de_period_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.hideSoftKeyboard(OutstandingDetails.this);

                click_detect_flag = "from_date";

                datePickerDialog = DatePickerDialog.newInstance(OutstandingDetails.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);
                datePickerDialog.setYearRange(2017,Year);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select From Date");

                datePickerDialog.show(getFragmentManager(), "Dealer App");

            }
        });

        out_de_period_value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.hideSoftKeyboard(OutstandingDetails.this);

                click_detect_flag = "to_date";

                datePickerDialog = DatePickerDialog.newInstance(OutstandingDetails.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setYearRange(2017,Year);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select To Date");

                datePickerDialog.show(getFragmentManager(), "Dealer App");

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Global_Data.GLOvel_BU =  "";
        Intent m = new Intent(getApplicationContext(), OutstandingActivity.class);
        startActivity(m);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    //finish();
}

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DecimalFormat mFormat= new DecimalFormat("00");
        String date = mFormat.format(Double.valueOf(dayOfMonth)) + "-" + mFormat.format(Double.valueOf(monthOfYear+1)) + "-" + year;

        // Toast.makeText(Customer_DatePIcker.this, date, Toast.LENGTH_LONG).show();

        if(click_detect_flag.equalsIgnoreCase("from_date"))
        {
            if(!out_de_period_value2.getText().toString().equalsIgnoreCase(""))
            {
                String s = CheckDates(date,out_de_period_value2.getText().toString());

                if(s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a"))
                {
                    out_de_period_value.setText(date);

                    cd  = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet())
                    {
                        button.setEnabled(false);
                        button.setClickable(false);
                        OutstandingDetailsList_Result();
                    }
                    else
                    {
                        recyclerView.showShimmerAdapter();
                        Toast toast = Toast.makeText(OutstandingDetails.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                }
                else if(s.equalsIgnoreCase("t"))
                {
                    out_de_period_value.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),"From Date should be less Than To Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            else
            {
                out_de_period_value.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(),"Please Select To Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
        else
        if(click_detect_flag.equalsIgnoreCase("to_date"))
        {
            if(!out_de_period_value.getText().toString().equalsIgnoreCase(""))
            {
                String s = CheckDates(out_de_period_value.getText().toString(),date);

                if(s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a"))
                {
                    out_de_period_value2.setText(date);
                    recyclerView.showShimmerAdapter();

                    cd  = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet())
                    {
                        button.setEnabled(false);
                        button.setClickable(false);
                        OutstandingDetailsList_Result();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(OutstandingDetails.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                }
                else if(s.equalsIgnoreCase("t"))
                {
                    out_de_period_value2.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(),"To Date should be Greater Than from Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
            else
            {
                out_de_period_value2.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(),"Please Select From Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public void OutstandingDetailsList_Result()
    {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " +  Global_Data.GLOvel_USER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";


        try {
            service_url = domain + "outstanding_lists/outstanding_index?email=" +Global_Data.GLOvel_USER_EMAIL+ "&start_date=" + out_de_period_value.getText().toString()+ "&end_date=" + out_de_period_value2.getText().toString()+"&business_unit=" + URLEncoder.encode(Global_Data.GLOvel_BU, "UTF-8")+"&primary_category=" +URLEncoder.encode(product_category, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new OutstandingDetails.OutstandingDetails_List_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(OutstandingDetails.this, MainActivity.class);
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

    private class OutstandingDetails_List_Task  extends AsyncTask<String, Void, String> {
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

                    OutstandingDetails.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(OutstandingDetails.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(OutstandingDetails.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else
                if(response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    OutstandingDetails.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(OutstandingDetails.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(OutstandingDetails.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });



                }
                else {

                    //dbvoc.getDeleteTable("delivery_products");

                    JSONArray outstanding_lists = response.getJSONArray("outstanding_lists");
                    Log.i("volley", "response outstanding_lists Length: " + outstanding_lists.length());
                    Log.d("volley", "outstanding_lists" + outstanding_lists.toString());

                    if (outstanding_lists.length() <= 0) {

                        OutstandingDetails.this.runOnUiThread(new Runnable() {
                            public void run() {

                                recyclerView.hideShimmerAdapter();
                                Toast toast = Toast.makeText(OutstandingDetails.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                outdetails_list.clear();
                                recyclerView.setVisibility(View.GONE);

//                                Intent intent = new Intent(OutstandingDetails.this, OutstandingActivity.class);
//                                startActivity(intent);
//                                finish();

                            }
                        });
                    } else {


                        update_At_Date = "";
                        OutstandingDetails.this.runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        });

                        outdetails_list.clear();

                        if(service_hit == 0)
                        {
                            outstanding_category_results.clear();
                            outstanding_category_results.add("Product Category");
                        }


                        for (int i = 0; i < outstanding_lists.length(); i++)
                        {
                            JSONObject jsonObject = outstanding_lists.getJSONObject(i);

                            outdetails_list.add(new OutstandingDetails_Model(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("reference_no")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("txn_date")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("txn_type")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("primary_category")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_amt")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("due_amt")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("due_date")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("total_no_of_days")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_no"))));
                            update_At_Date = jsonObject.getString("update_at");

                            if(service_hit == 0)
                            {
                                if(!outstanding_category_results.contains(jsonObject.getString("primary_category")))
                                {
                                    outstanding_category_results.add(jsonObject.getString("primary_category"));
                                }

                            }



                        }


                        OutstandingDetails.this.runOnUiThread(new Runnable()
                        {
                            public void run() {

                                setTitle("Outstanding/Overdue Data as on "+update_At_Date);
                                recyclerView.hideShimmerAdapter();
                                outstandingDetails_adapter.notifyDataSetChanged();
                                button.setEnabled(true);
                                button.setClickable(true);

                                if(service_hit == 0)
                                {
                                    outstanding_category_adapter = new ArrayAdapter<String>(OutstandingDetails.this,
                                            R.layout.custom_spinner, outstanding_category_results);
                                    outstanding_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    outstanding_category.setAdapter(outstanding_category_adapter);
                                }

                                ++service_hit;


                            }
                        });



                    }

                    OutstandingDetails.this.runOnUiThread(new Runnable() {
                        public void run() {

                            recyclerView.hideShimmerAdapter();
                        }
                    });
                    //	dialog.dismiss();

                    //finish();

                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(OutstandingDetails.this, MainActivity.class);
                startActivity(intent);
                finish();

                OutstandingDetails.this.runOnUiThread(new Runnable() {
                    public void run() {

                        recyclerView.hideShimmerAdapter();
                    }
                });

            }


            OutstandingDetails.this.runOnUiThread(new Runnable() {
                public void run() {

                    recyclerView.hideShimmerAdapter();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            OutstandingDetails.this.runOnUiThread(new Runnable() {
                public void run() {
                    recyclerView.hideShimmerAdapter();
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

    public static String CheckDates(String from_date, String to_date)  {

        SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");

        String  b = "";

        try {
            if(dfDate.parse(from_date).before(dfDate.parse(to_date)))
            {
                b = "f";//If start date is before end date
            }
            else if(dfDate.parse(from_date).equals(dfDate.parse(to_date)))
            {
                b = "a";//If two dates are equal
            }
            else
            {
                b = "t"; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    private void requestStoragePermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {


                            try {
                                if (cd.isConnectingToInternet()) {
                                    if(option_flag.equalsIgnoreCase("gmail"))
                                    {
                                        Update_Download_Count("CreditProfile","email");
                                    }
                                    else
                                    {
                                        Update_Download_Count("CreditProfile","download");
                                    }
                                }

                                exportEmailInCSV();
                            } catch (IOException e) {
                                e.printStackTrace();
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



    private void openFile(File url) {

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");

            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            }
            else if (url.toString().contains(".mp4") || url.toString().contains(".AVI") || url.toString().contains(".FLV") || url.toString().contains(".WMV") || url.toString().contains(".MOV")) {
                // Text file
                intent.setDataAndType(uri, "video/mp4");

            }
            else {
                intent.setDataAndType(uri, "text/csv");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    public static void email_f(Context context, String emailTo, String emailCC,
                               String subject, String emailText, List<String> filePaths)
    {
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{emailTo});
        emailIntent.putExtra(android.content.Intent.EXTRA_CC,
                new String[]{emailCC});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (String file : filePaths)
        {
            File fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
        Intent chooserIntent = Intent.createChooser(emailIntent, "Send mail...");
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(chooserIntent);
    }

    private  void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Ledger Report", catFont);
        // anchor.setName("Ledger Report");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);


        Paragraph subPara = new Paragraph("");
        Paragraph subPara2 = new Paragraph("");
        // Section subCatPart = catPart.addSection(subPara);
//        subCatPart.add(new Paragraph("Hello"));
//
        //subPara = new Paragraph("Subcategory 2", subFont);
//       subCatPart = catPart.addSection(subPara);
//        subCatPart.add(new Paragraph("Paragraph 1"));
//        subCatPart.add(new Paragraph("Paragraph 2"));
//        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        //createList(subCatPart);


        // add a table
        createTable(document);

//        // now add all this to the document
        //document.add(catPart);
//
//        // Next section
//        anchor = new Anchor("Second Chapter", catFont);
//        anchor.setName("Second Chapter");
//
//        // Second parameter is the number of the chapter
//        catPart = new Chapter(new Paragraph(anchor), 1);
//
//        subPara = new Paragraph("Subcategory", subFont);
//        subCatPart = catPart.addSection(subPara);
//        subCatPart.add(new Paragraph("This is a very important message"));
//
//        // now add all this to the document
        //  document.add(catPart);

    }

    private  void createTable(Document document)
            throws DocumentException {
        Anchor anchor = new Anchor("Outstanding Report", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        PdfPTable table = new PdfPTable(8);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Reference no"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Product Category"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Value"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Due Amount"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Due Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total No Of Days"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        if(outdetails_list.size() > 0) {

            for (int i = 0; i < outdetails_list.size(); i++) {
                //PdfPTable table1 = new PdfPTable(7);
                table.addCell(outdetails_list.get(i).getReference_No());
                table.addCell(outdetails_list.get(i).getDate());
                table.addCell(outdetails_list.get(i).getType());
                table.addCell(outdetails_list.get(i).getProduct_Category());
                table.addCell(outdetails_list.get(i).getValue());
                table.addCell(outdetails_list.get(i).getDue_Amount());
                table.addCell(outdetails_list.get(i).getDue_Date());
                table.addCell(outdetails_list.get(i).getTotal_No_Of_Days());


            }
            catPart.add(table);
            document.add(catPart);
            document.close();
        }

    }

    class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

        boolean userSelect = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            userSelect = true;
            return false;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (userSelect) {
                // Your selection handling code here
                userSelect = false;
                String selectedItem = parent.getItemAtPosition(position).toString(); //this is your selected item
                if(selectedItem.equalsIgnoreCase("Product Category"))
                {
                    product_category = "";

                    cd  = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet())
                    {
                        recyclerView.showShimmerAdapter();
                        OutstandingDetailsList_Result();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(OutstandingDetails.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                }
                else
                {
                    product_category = selectedItem;

                    cd  = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet())
                    {
                        recyclerView.showShimmerAdapter();
                        OutstandingDetailsList_Result();
                    }
                    else
                    {
                        Toast toast = Toast.makeText(OutstandingDetails.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    }

    public void exportEmailInCSV() throws IOException {
        {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            final File storageDir = new File(path,"DealerApp");

            if(!storageDir.exists())
            {
                storageDir.mkdir();
            }

//            boolean var = false;
//            if (!folder.exists())
//                var = folder.mkdir();

            // System.out.println("" + var);


            final String filename = storageDir.toString() + "/" + "Outstanding"+".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    OutstandingDetails.this, contentTitle, "wait...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if(option_flag.equalsIgnoreCase("gmail"))
                    {
                        File yourFile = new File(storageDir, "Outstanding"+".csv");

                        ArrayList<String> att_file = new ArrayList<String>();

                        att_file.add(yourFile.getAbsolutePath());

                        // Document document = new Document();
                        try {
                            //  PdfWriter.getInstance(document, new FileOutputStream(yourFile));
                            //  document.open();
                            // addContent(document);
                            // document.close();
                            GenerateCSVCSV();
                            email_f(getApplicationContext(),"","","","",att_file);
                        }  catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    else
                    {
                        File yourFile = new File(storageDir, "Outstanding"+".csv");

                        // Document document = new Document();
                        try {
                            //PdfWriter.getInstance(document, new FileOutputStream(yourFile));
                            // document.open();
                            // addContent(document);
                            // document.close();
                            GenerateCSVCSV();
                            openFile(yourFile);
                        }  catch (IOException e) {
                            e.printStackTrace();
                        }

//                        ArrayList<String> att_file = new ArrayList<String>();
//                        att_file.add(yourFile.getAbsolutePath());
//                        openFile(yourFile);
                    }

//                    File yourFile = new File(storageDir, "Test.csv");
//                    Intent target = new Intent(Intent.ACTION_VIEW);
//                    target.setDataAndType(Uri.parse(yourFile.getAbsolutePath()), "text/csv");
//                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                    Intent intent = Intent.createChooser(target, "Open File");
//                    try {
//                        startActivity(intent);
//                    } catch (ActivityNotFoundException e) {
//                        // Instruct the user to install a PDF reader here, or something
//                    }


                }
            };

            new Thread() {
                public void run() {
                    try {


                    } catch (Exception e) {
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();

                }
            }.start();

        }

    }

    public void GenerateCSVCSV() throws IOException {
        {

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            final File folder = new File(path,"DealerApp");

            if(!folder.exists())
            {
                folder.mkdir();
            }

            boolean var = false;


            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "Outstanding"+".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    OutstandingDetails.this, contentTitle, "Generated...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    File yourFile = new File(folder, "Outstanding"+".csv");

                    try {
                        //openFile(yourFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            };

            new Thread() {
                public void run() {
                    try {


                        CSVWriter writer = null;

                        writer = new CSVWriter(new FileWriter(filename), ',');
                        String[] entriesh = " # # #Start Date#End Date# # # ".split("#"); // array of your values
                        writer.writeNext(entriesh);

                        String ss = " # # #"+out_de_period_value.getText().toString()+"#"+out_de_period_value2.getText().toString()+"# # # ";
                        String[] entrieshv = ss.split("#"); // array of your values
                        writer.writeNext(entrieshv);


                        String[] entries = "Reference No#Invoice No#Date#Type#Product Category#Value#Due Amount#Due Date#Total No Of Days".split("#"); // array of your values
                        writer.writeNext(entries);


                        if (outdetails_list.size() > 0) {
                            for(int i=0; i<outdetails_list.size(); i++)
                            {
                                String s = outdetails_list.get(i).getReference_No()+"#"+outdetails_list.get(i).getInvoice_no()+"#"+outdetails_list.get(i).getDate()+"#"+outdetails_list.get(i).getType()+"#"+outdetails_list.get(i).getProduct_Category()+"#"+outdetails_list.get(i).getValue()+"#"+outdetails_list.get(i).getDue_Amount()+"#"+outdetails_list.get(i).getDue_Date()+"#"+outdetails_list.get(i).getTotal_No_Of_Days();
                                String[] entriesdata = s.split("#"); // array of your values
                                writer.writeNext(entriesdata);



                            }
                        }
                        writer.close();

                    } catch (Exception e) {
                    }
                    handler.sendEmptyMessage(0);
                    progDailog.dismiss();
                }
            }.start();

        }

    }

    public void Update_Download_Count(String Module_name,String type) {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        //Log.i("volley", "email: " + prefManager.getUser_Email());

        StringRequest jsObjRequest = null;

        String service_url = "";

        service_url = domain + "dealer_users/utilization_report_records?email="+Global_Data.GLOvel_USER_EMAIL+"&module_name="+Module_name+"&action_name="+type;
        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new OutstandingDetails.Update_Dwonload_cound().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Network Error",
//                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Server AuthFailureError  Error",
//                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Server   Error",
//                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
//                            Toast.makeText(getApplicationContext(),
//                                    "Network   Error",
//                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
//                            Toast.makeText(getApplicationContext(),
//                                    "ParseError   Error",
//                                    Toast.LENGTH_LONG).show();
                        } else {
                            //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        // dialog.dismiss();


                        // finish();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 100000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }

    private class Update_Dwonload_cound extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... response) {

            try {
                JSONObject response1 = new JSONObject(final_response);
                if (response1.has("message")) {
                    response_result = response1.getString("message");
                    Log.d(" Download Count Status","D Status"+response_result);
                } else {
                    response_result = "data";
                    Log.d(" Download Count Status","D Status N"+response_result);
                }
            } catch (JSONException e) {
                e.printStackTrace();


            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {


        }

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }


    }


}


