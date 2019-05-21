package com.anchor.activities;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.anchor.adapter.Scheme_EarningSummary_Adapter;
import com.anchor.helper.PrefManager;
import com.anchor.helper.SimpleDividerItemDecoration;
import com.anchor.model.SchemeDetailModel;
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
import java.util.Date;
import java.util.List;
import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class Scheme_EarningSummary extends AppCompatActivity implements Scheme_EarningSummary_Adapter.UserAdapterListener, Scheme_EarningSummary_Adapter.UserAdapterListenernew, DatePickerDialog.OnDateSetListener {
    private ProgressDialog dialog;
    private ShimmerRecyclerView recyclerView;
    Scheme_EarningSummary_Adapter scheme_earningSummary_adapter;
    List<SchemeDetailModel> scheme_list = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    String click_detect_flag = "";
    static Calendar calendar;
    static int Year, Month, Day;
    EditText scheme_period_value, scheme_period_value2;
    Spinner sc_businees_unit, sc_businees_division, sc_businees_category, sc_businees_brand;
    ArrayAdapter<String> sc_businees_unit_adapter, sc_businees_division_adapter, sc_businees_category_adapter, sc_businees_brand_adapter;

    private ArrayList<String> sc_businees_unit_results = new ArrayList<String>();
    private ArrayList<String> sc_businees_division_results = new ArrayList<String>();
    private ArrayList<String> sc_businees_category_results = new ArrayList<String>();
    private ArrayList<String> sc_businees_brand_results = new ArrayList<String>();
    ConnectionDetector cd;
    static String final_response = "";
    String response_result = "";
    String media_path_content_type = "";
    String document_url = "";
    String option_flag = "";
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    String business_unit = "";
    String business_division = "";
    String business_category = "";
    String business_brand = "";
    private PrefManager prefManager;
    public ArrayList<Long> list = new ArrayList<>();
    public DownloadManager downloadManager;
    public long refid;
    File file;
    String fileName;
    String scheme_names = "";
    String Title_Text = "";
    String type = "";
    private String update_At_Date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_scheme__earning_summary);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!Global_Data.Quantity_click_flag.equalsIgnoreCase("")) {
            Title_Text = "Scheme Earning Summary(Closed)   " + " Quantity wise scheme   " + "Data as on ";
            type = "quantity";
        } else {
            Title_Text = "Scheme Earning Summary(Closed)   " + " Value wise scheme   " + "Data as on ";
            ;
            type = "value";
        }

        setTitle(Title_Text);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        prefManager = new PrefManager(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        registerReceiver(onComplete51,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        AllAngleExpandableButton button = (AllAngleExpandableButton) findViewById(R.id.button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.gmail, R.drawable.download, R.drawable.details};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(getApplicationContext(), drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);

        recyclerView = findViewById(R.id.sc_lshimmer_recycler_view);
        scheme_period_value = findViewById(R.id.scheme_period_value);
        scheme_period_value2 = findViewById(R.id.scheme_period_value2);
        sc_businees_unit = findViewById(R.id.sc_businees_unit);
        sc_businees_division = findViewById(R.id.sc_businees_division);
        sc_businees_category = findViewById(R.id.sc_businees_category);
        sc_businees_brand = findViewById(R.id.sc_businees_brand);

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String to_date = format.format(today);

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));

        Date date = cal.getTime();
        String from_date = format.format(date);

        if (!Global_Data.scheme_from_date.equalsIgnoreCase("")) {
            scheme_period_value.setText(Global_Data.scheme_from_date);
        } else {
            scheme_period_value.setText(from_date);
        }


        if (!Global_Data.scheme_to_date.equalsIgnoreCase("")) {
            scheme_period_value2.setText(Global_Data.scheme_to_date);
        } else {
            scheme_period_value2.setText(to_date);
        }


        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

//        scheme_list.add(new Scheme_Model("AEPL/WEEL","1842","1","CN","5","60","CN","2"));
//
//        scheme_list.add(new Scheme_Model("AEPL/WEEL","1842","1","CN","5","60","CN","2"));
//
//        scheme_list.add(new Scheme_Model("AEPL/WEEL","1842","1","CN","5","60","CN","2"));
//
//        scheme_list.add(new Scheme_Model("AEPL/WEEL","1842","1","CN","5","60","CN","2"));


        scheme_earningSummary_adapter = new Scheme_EarningSummary_Adapter(getApplicationContext(), scheme_list, Scheme_EarningSummary.this, Scheme_EarningSummary.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(scheme_earningSummary_adapter);

        //  recyclerView.hideShimmerAdapter();

        sc_businees_unit_results.add("Business Unit");
        sc_businees_unit_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                android.R.layout.simple_spinner_item, sc_businees_unit_results);
        sc_businees_unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sc_businees_unit.setAdapter(sc_businees_unit_adapter);

        Scheme_EarningSummary.SpinnerInteractionListenerUnit listener = new Scheme_EarningSummary.SpinnerInteractionListenerUnit();
        sc_businees_unit.setOnTouchListener(listener);
        sc_businees_unit.setOnItemSelectedListener(listener);

        sc_businees_division_results.add("Business Division");
        sc_businees_division_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                android.R.layout.simple_spinner_item, sc_businees_division_results);
        sc_businees_division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sc_businees_division.setAdapter(sc_businees_division_adapter);

        Scheme_EarningSummary.SpinnerInteractionListenerdivision listener2 = new Scheme_EarningSummary.SpinnerInteractionListenerdivision();
        sc_businees_division.setOnTouchListener(listener2);
        sc_businees_division.setOnItemSelectedListener(listener2);

        sc_businees_category_results.add("Business Category");
        sc_businees_category_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                android.R.layout.simple_spinner_item, sc_businees_category_results);
        sc_businees_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sc_businees_category.setAdapter(sc_businees_category_adapter);

        Scheme_EarningSummary.SpinnerInteractionListenerb_category listener3 = new Scheme_EarningSummary.SpinnerInteractionListenerb_category();
        sc_businees_category.setOnTouchListener(listener3);
        sc_businees_category.setOnItemSelectedListener(listener3);

        sc_businees_brand_results.add("Brand");
        sc_businees_brand_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                android.R.layout.simple_spinner_item, sc_businees_brand_results);
        sc_businees_brand_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sc_businees_brand.setAdapter(sc_businees_brand_adapter);

        Scheme_EarningSummary.SpinnerInteractionListenerb_brand listener4 = new Scheme_EarningSummary.SpinnerInteractionListenerb_brand();
        sc_businees_brand.setOnTouchListener(listener4);
        sc_businees_brand.setOnItemSelectedListener(listener4);

        recyclerView.showShimmerAdapter();
        cd = new ConnectionDetector(getApplicationContext());

        if (cd.isConnectingToInternet()) {
            SchemeList_Result();
        } else {
            Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(order_home);
            finish();

        }

        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {


                if (scheme_list.size() > 0 && (index == 1 || index == 2)) {
                    if (index == 1) {
                        option_flag = "gmail";
                        requestStoragePermission();
                    } else if (index == 2) {
                        option_flag = "csv";
                        requestStoragePermission();
                    }

                } else if (index == 3) {
                    Global_Data.close_scheme_name = "";
                    Intent m = new Intent(getApplicationContext(), Scheme_Details.class);
                    startActivity(m);
                } else {
                    Toast toast = Toast.makeText(Scheme_EarningSummary.this, "Record not exist.", Toast.LENGTH_LONG);
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


        scheme_period_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.hideSoftKeyboard(Scheme_EarningSummary.this);

                click_detect_flag = "from_date";

                datePickerDialog = DatePickerDialog.newInstance(Scheme_EarningSummary.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);
                datePickerDialog.setYearRange(2017,Year);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select From Date");

                datePickerDialog.show(getFragmentManager(), "Dealer App");

            }
        });

        scheme_period_value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.hideSoftKeyboard(Scheme_EarningSummary.this);

                click_detect_flag = "to_date";

                datePickerDialog = DatePickerDialog.newInstance(Scheme_EarningSummary.this, Year, Month, Day);

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

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent m = new Intent(getApplicationContext(), Scheme_Filter_Activity.class);
        startActivity(m);

        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DecimalFormat mFormat = new DecimalFormat("00");
        String date = mFormat.format(Double.valueOf(dayOfMonth)) + "-" + mFormat.format(Double.valueOf(monthOfYear + 1)) + "-" + year;

        // Toast.makeText(Customer_DatePIcker.this, date, Toast.LENGTH_LONG).show();

        if (click_detect_flag.equalsIgnoreCase("from_date")) {


            if (!scheme_period_value2.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(date, scheme_period_value2.getText().toString());

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    scheme_period_value.setText(date);
                    recyclerView.showShimmerAdapter();
                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        SchemeList_Result();
                    } else {
                        recyclerView.showShimmerAdapter();
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else if (s.equalsIgnoreCase("t")) {
                    scheme_period_value.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "From Date should be less Than To Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                scheme_period_value.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select To Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }


        } else if (click_detect_flag.equalsIgnoreCase("to_date")) {
            if (!scheme_period_value.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(scheme_period_value.getText().toString(), date);

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    scheme_period_value2.setText(date);
                    recyclerView.showShimmerAdapter();

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                } else if (s.equalsIgnoreCase("t")) {
                    scheme_period_value2.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "To Date should be Greater Than from Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                scheme_period_value2.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select From Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public void SchemeList_Result() {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";
        String period_type = "";

        if (!Global_Data.halfyear_value.equalsIgnoreCase("")) {
            period_type = "halfyear";
        } else if (!Global_Data.quartly_value.equalsIgnoreCase("")) {
            period_type = "quaterly";
        } else if (!Global_Data.monthly_value.equalsIgnoreCase("")) {
            period_type = "month";
        } else if (!Global_Data.year_value.equalsIgnoreCase("")) {
            period_type = "year";
        } else {
            period_type = "period";
        }

        try {
            service_url = domain + "customer_schemes/customer_scheme_earnings_index?email=" + Global_Data.CUSTOMER_EMAIL + "&start_date=" + scheme_period_value.getText().toString() + "&end_date=" + scheme_period_value2.getText().toString() + "&business_unit=" + URLEncoder.encode(Global_Data.target_product_Bu_value, "UTF-8") + "&primary_category=" + URLEncoder.encode(Global_Data.target_product_cate_value, "UTF-8") + "&business_category=" + URLEncoder.encode(Global_Data.target_product_division_value, "UTF-8") + "&sub_category=" + URLEncoder.encode(Global_Data.target_product_brand_value, "UTF-8") + "&type=" + URLEncoder.encode(type, "UTF-8") + "&period_type=" + period_type+"&page_type=index";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Scheme_EarningSummary.scheme_List_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(Scheme_EarningSummary.this, MainActivity.class);
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

    @Override
    public void onUserClicked(String action_type, SchemeDetailModel Scheme_Model, int position) {
        requestStoragePermissionnew(action_type, Scheme_Model, position);
    }

    private class scheme_List_Task extends AsyncTask<String, Void, String> {
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

                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Scheme_EarningSummary.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Scheme_EarningSummary.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("Customer not found.")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Scheme_EarningSummary.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else {
                    update_At_Date = "";

                    JSONArray customer_scheme_earnings = response.getJSONArray("customer_scheme_earnings");
                    Log.i("volley", "response customer_scheme_earnings Length: " + customer_scheme_earnings.length());
                    Log.d("volley", "customer_scheme_earnings" + customer_scheme_earnings.toString());

                    JSONArray business_units = response.getJSONArray("business_units");
                    Log.i("volley", "response business_units Length: " + business_units.length());
                    Log.d("volley", "business_units" + business_units.toString());

                    JSONArray primary_categories = response.getJSONArray("primary_categories");
                    Log.i("volley", "response primary_categories Length: " + primary_categories.length());
                    Log.d("volley", "primary_categories" + business_units.toString());

                    JSONArray business_categories = response.getJSONArray("business_categories");
                    Log.i("volley", "response business_categories Length: " + business_categories.length());
                    Log.d("volley", "business_categories" + business_categories.toString());

                    JSONArray sub_categories = response.getJSONArray("sub_categories");
                    Log.i("volley", "response sub_categories Length: " + sub_categories.length());
                    Log.d("volley", "sub_categories" + sub_categories.toString());

                    if (sc_businees_unit_results.size() < 2) {

                        sc_businees_unit_results.clear();
                        sc_businees_unit_results.add("Business Unit");

                        for (int i = 0; i < business_units.length(); i++) {

                            JSONObject jsonObject = business_units.getJSONObject(i);
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("business_unit"))) {
                                sc_businees_unit_results.add(jsonObject.getString("business_unit"));
                            }

                        }

                    }

                    if (sc_businees_division_results.size() < 2) {

                        sc_businees_division_results.clear();
                        sc_businees_division_results.add("Business Division");

                        for (int i = 0; i < primary_categories.length(); i++) {

                            JSONObject jsonObject = primary_categories.getJSONObject(i);
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("primary_category"))) {
                                sc_businees_division_results.add(jsonObject.getString("primary_category"));
                            }

                        }

                    }

                    if (sc_businees_category_results.size() < 2) {

                        sc_businees_category_results.clear();
                        sc_businees_category_results.add("Business Category");

                        for (int i = 0; i < business_categories.length(); i++) {

                            JSONObject jsonObject = business_categories.getJSONObject(i);

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("business_category"))) {
                                sc_businees_category_results.add(jsonObject.getString("business_category"));
                            }

                        }

                    }

                    if (sc_businees_brand_results.size() < 2) {

                        sc_businees_brand_results.clear();
                        sc_businees_brand_results.add("Brand");

                        for (int i = 0; i < sub_categories.length(); i++) {

                            JSONObject jsonObject = sub_categories.getJSONObject(i);

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("sub_category"))) {
                                sc_businees_brand_results.add(jsonObject.getString("sub_category"));
                            }

                        }

                    }


                    if (customer_scheme_earnings.length() <= 0) {

                        Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                            public void run() {

                                recyclerView.hideShimmerAdapter();
                                Toast toast = Toast.makeText(Scheme_EarningSummary.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                scheme_list.clear();
                                recyclerView.setVisibility(View.INVISIBLE);


                            }
                        });
                    } else {

                        scheme_list.clear();

                        Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                            public void run() {

                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        });


                        for (int i = 0; i < customer_scheme_earnings.length(); i++) {

                            JSONObject jsonObject = customer_scheme_earnings.getJSONObject(i);

                            scheme_list.add(new SchemeDetailModel("", "", "", "", "", "", "", isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("quantity")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("line_amount")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("tax_amount")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("total_amount")), "", "", "", "", "", isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("gift_amount")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("scheme_name"))));

                            update_At_Date = jsonObject.getString("update_at");

                        }


                        Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                            public void run() {

                                setTitle(Title_Text+update_At_Date);
                                recyclerView.hideShimmerAdapter();


                            }
                        });


                    }

                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {

                            recyclerView.hideShimmerAdapter();

                            scheme_earningSummary_adapter.notifyDataSetChanged();

                            sc_businees_unit_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                                    android.R.layout.simple_spinner_item, sc_businees_unit_results);
                            sc_businees_unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sc_businees_unit.setAdapter(sc_businees_unit_adapter);

                            int spinnerPosition = sc_businees_unit_adapter.getPosition(business_unit);
                            if (spinnerPosition > -1) {
                                sc_businees_unit.setSelection(spinnerPosition);
                            }


                            sc_businees_division_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                                    android.R.layout.simple_spinner_item, sc_businees_division_results);
                            sc_businees_division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sc_businees_division.setAdapter(sc_businees_division_adapter);

                            int spinnerPosition2 = sc_businees_division_adapter.getPosition(business_division);
                            if (spinnerPosition2 > -1) {
                                sc_businees_division.setSelection(spinnerPosition2);
                            }


                            sc_businees_category_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                                    android.R.layout.simple_spinner_item, sc_businees_category_results);
                            sc_businees_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sc_businees_category.setAdapter(sc_businees_category_adapter);

                            int spinnerPosition3 = sc_businees_category_adapter.getPosition(business_category);
                            if (spinnerPosition3 > -1) {
                                sc_businees_category.setSelection(spinnerPosition3);
                            }


                            sc_businees_brand_adapter = new ArrayAdapter<String>(Scheme_EarningSummary.this,
                                    android.R.layout.simple_spinner_item, sc_businees_brand_results);
                            sc_businees_brand_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sc_businees_brand.setAdapter(sc_businees_brand_adapter);

                            int spinnerPosition4 = sc_businees_brand_adapter.getPosition(business_brand);
                            if (spinnerPosition4 > -1) {
                                sc_businees_brand.setSelection(spinnerPosition4);
                            }
                        }
                    });
                    //	dialog.dismiss();

                    //finish();

                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(Scheme_EarningSummary.this, MainActivity.class);
                startActivity(intent);
                finish();

                Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                    public void run() {

                        recyclerView.hideShimmerAdapter();
                    }
                });

            }


            Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                public void run() {

                    recyclerView.hideShimmerAdapter();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
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
                                cd = new ConnectionDetector(getApplicationContext());
                                if (cd.isConnectingToInternet()) {
                                    if(option_flag.equalsIgnoreCase("gmail"))
                                    {
                                        Update_Download_Count("SchemeEarning","email");
                                    }
                                    else
                                    {
                                        Update_Download_Count("SchemeEarning","download");
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

    public void exportEmailInCSV() throws IOException {
        {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            final File storageDir = new File(path, "DealerApp");

            if (!storageDir.exists()) {
                storageDir.mkdir();
            }

//            boolean var = false;
//            if (!folder.exists())
//                var = folder.mkdir();

            // System.out.println("" + var);


            final String filename = storageDir.toString() + "/" + "Scheme_Earning_summary_Report" + ".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Scheme_EarningSummary.this, contentTitle, "wait...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if (option_flag.equalsIgnoreCase("gmail")) {
                        File yourFile = new File(storageDir, "Scheme_Earning_summary_Report" + ".csv");

                        ArrayList<String> att_file = new ArrayList<String>();

                        att_file.add(yourFile.getAbsolutePath());

                        // Document document = new Document();
                        try {
                            //  PdfWriter.getInstance(document, new FileOutputStream(yourFile));
                            //  document.open();
                            // addContent(document);
                            // document.close();
                            GenerateCSVCSV();
                            email_f(getApplicationContext(), "", "", "", "", att_file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        File yourFile = new File(storageDir, "Scheme_Earning_summary_Report" + ".csv");

                        // Document document = new Document();
                        try {
                            //PdfWriter.getInstance(document, new FileOutputStream(yourFile));
                            // document.open();
                            // addContent(document);
                            // document.close();
                            GenerateCSVCSV();
                            openFile(yourFile);
                        } catch (IOException e) {
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
            } else if (url.toString().contains(".jpg")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".png")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".jpeg")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".gif")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");

            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");

            } else if (url.toString().contains(".mp4") || url.toString().contains(".AVI") || url.toString().contains(".FLV") || url.toString().contains(".WMV") || url.toString().contains(".MOV")) {
                // Text file
                intent.setDataAndType(uri, "video/mp4");

            } else {
                intent.setDataAndType(uri, "text/csv");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    public static void email_f(Context context, String emailTo, String emailCC,
                               String subject, String emailText, List<String> filePaths) {
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{emailTo});
        emailIntent.putExtra(Intent.EXTRA_CC,
                new String[]{emailCC});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailText);
        //has to be an ArrayList
        ArrayList<Uri> uris = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's
        for (String file : filePaths) {
            File fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        Intent chooserIntent = Intent.createChooser(emailIntent, "Send mail...");
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(chooserIntent);
    }

    private void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("Scheme Report", catFont);
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

    private void createTable(Document document)
            throws DocumentException {
        Anchor anchor = new Anchor("Scheme Report", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        PdfPTable table = new PdfPTable(8);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Scheme Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Current Volume"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Current Eligibility"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Gift Detail"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Gift Value"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Short Volumn For Next Slab"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Next Scheme Slab"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Next Scheme Value"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        if (scheme_list.size() > 0) {

            for (int i = 0; i < scheme_list.size(); i++) {
                //PdfPTable table1 = new PdfPTable(7);
                table.addCell(scheme_list.get(i).getScheme_Name());
                table.addCell(scheme_list.get(i).getQuantity());
                table.addCell(scheme_list.get(i).getLine_Amount());
                table.addCell(scheme_list.get(i).getTax_Amount());
                table.addCell(scheme_list.get(i).getTotal_Amount());
                table.addCell(scheme_list.get(i).getGift_Amount());


            }
            catPart.add(table);
            document.add(catPart);
            document.close();
        }

    }

    public void GenerateCSVCSV() throws IOException {
        {

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            final File folder = new File(path, "DealerApp");

            if (!folder.exists()) {
                folder.mkdir();
            }

            boolean var = false;


            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "Scheme_Earning_summary_Report" + ".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Scheme_EarningSummary.this, contentTitle, "Generated...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    File yourFile = new File(folder, "Scheme_Earning_summary_Report" + ".csv");

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

                        String ss = " # # #" + scheme_period_value.getText().toString() + "#" + scheme_period_value2.getText().toString() + "# # # ";
                        String[] entrieshv = ss.split("#"); // array of your values
                        writer.writeNext(entrieshv);

                        String[] entries = "Scheme Name#Quantity#Line Amount#Tax Amount#Total Amount#Gift_Amount".split("#"); // array of your values
                        writer.writeNext(entries);


                        if (scheme_list.size() > 0) {
                            for (int i = 0; i < scheme_list.size(); i++) {


                                String s = scheme_list.get(i).getScheme_Name() + "#" + scheme_list.get(i).getQuantity() + "#" + scheme_list.get(i).getLine_Amount() + "#" + scheme_list.get(i).getTax_Amount() + "#" + scheme_list.get(i).getTotal_Amount() + "#" + scheme_list.get(i).getGift_Amount();
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

    public static String CheckDates(String from_date, String to_date) {

        SimpleDateFormat dfDate = new SimpleDateFormat("dd-MM-yyyy");

        String b = "";

        try {
            if (dfDate.parse(from_date).before(dfDate.parse(to_date))) {
                b = "f";//If start date is before end date
            } else if (dfDate.parse(from_date).equals(dfDate.parse(to_date))) {
                b = "a";//If two dates are equal
            } else {
                b = "t"; //If start date is after the end date
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return b;
    }

    class SpinnerInteractionListenerUnit implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

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
                if (selectedItem.equalsIgnoreCase("Business Unit")) {
                    business_unit = "";

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else {
                    business_unit = selectedItem;

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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

    class SpinnerInteractionListenerdivision implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

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
                if (selectedItem.equalsIgnoreCase("Business Division")) {
                    business_division = "";

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else {
                    business_division = selectedItem;

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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

    class SpinnerInteractionListenerb_category implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

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
                if (selectedItem.equalsIgnoreCase("Business Category")) {
                    business_category = "";

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else {
                    business_category = selectedItem;

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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

    class SpinnerInteractionListenerb_brand implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

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
                if (selectedItem.equalsIgnoreCase("Brand")) {
                    business_brand = "";

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else {
                    business_brand = selectedItem;

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {
                        recyclerView.showShimmerAdapter();
                        SchemeList_Result();
                    } else {
                        Toast toast = Toast.makeText(Scheme_EarningSummary.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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


    private void requestStoragePermissionnew(final String action_type, final SchemeDetailModel scheme_model, final int position) {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            //  Scheme_Model scheme_models = scheme_model.get(position);
                            //Toast.makeText(Scheme_EarningSummary.this, scheme_model.getScheme_Name(), Toast.LENGTH_SHORT).show();

                            if (action_type.equalsIgnoreCase("scheme")) {
                                dialog = new ProgressDialog(Scheme_EarningSummary.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                dialog.setMessage("Please wait....");
                                dialog.setTitle("Dealer");
                                dialog.setCancelable(false);
                                dialog.show();

                                download_schemes(scheme_model.getScheme_Name());
                            } else if (action_type.equalsIgnoreCase("view")) {
                                Global_Data.close_scheme_name = scheme_model.getScheme_Name();
                                Intent m = new Intent(getApplicationContext(), Scheme_Details.class);
                                startActivity(m);
                            }


                        }

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


    public void download_schemes(String scheme_name) {
        scheme_names = scheme_name;
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";

        try {
            service_url = domain + "customer_schemes/get_scheme_new_launch?email=" + Global_Data.CUSTOMER_EMAIL + "&document_sub_type=" + URLEncoder.encode(scheme_name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Scheme_EarningSummary.scheme_Download_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

//                        Intent intent = new Intent(Scheme_EarningSummary.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        dialog.dismiss();
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

    private class scheme_Download_Task extends AsyncTask<String, Void, String> {
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

                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });

                } else if (response_result.equalsIgnoreCase("Scheme document not found.")) {


                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("Document Sub Type not found.")) {


                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Scheme_EarningSummary.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else {

                    media_path_content_type = "";
                    document_url = "";

                    if (response.has("media_path_content_type")) {
                        media_path_content_type = response.getString("media_path_content_type");
                    }

                    if (response.has("document_url")) {
                        document_url = response.getString("document_url");
                    }

                    if (document_url.equalsIgnoreCase("")) {

                        Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                            public void run() {

                                Toast toast = Toast.makeText(Scheme_EarningSummary.this, "Scheme document not found.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                        });
                    } else {


                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                        String path = Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath();
                        String f_name = isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(document_url);
                        fileName = document_url.trim().substring(document_url.trim().lastIndexOf('/') + 1, f_name.trim().length());

                        String file_array[];

                        if (fileName.indexOf("?") > 0) {
                            file_array = fileName.split("\\?");
                            fileName = file_array[0].trim();
                        }
                        file = new File(path, "Anchor_Dealer_Schemes" + "/" + fileName.trim());


                        if (!file.exists()) {
                            file.mkdir();

                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(document_url
                                    .trim())));

                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(true);
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Anchor_Dealer_Schemes" + "/" + fileName.trim());
//                                request.allowScanningByMediaScanner();
//                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                            refid = downloadManager.enqueue(request);


                            Log.e("OUTNM", "" + refid);

                            list.add(refid);
                        } else {
                            //file.delete();
                            Log.d("exist", "exist");

                            file.delete();
                            file.mkdir();

                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(document_url
                                    .trim())));

                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(true);
                            request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Anchor_Dealer_Schemes" + "/" + fileName.trim());

                            refid = downloadManager.enqueue(request);


                            Log.e("OUTNM", "" + refid);

                            list.add(refid);


                        }


                    }

//                    Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            dialog.dismiss();
//
//
//                        }
//                    });
                    //	dialog.dismiss();

                    //finish();

                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });

            }


            Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                public void run() {

                    //dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                public void run() {
                    // dialog.dismiss();
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
        unregisterReceiver(onComplete51);
        hidePDialog();
    }

    private void hidePDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public BroadcastReceiver onComplete51 = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty()) {

                // dialog.dismiss();

                Log.e("INSIDE", "" + referenceId);
                Toast.makeText(ctxt, "Downloading Completed", Toast.LENGTH_SHORT).show();
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(Scheme_EarningSummary.this)
//                                .setSmallIcon(R.drawable.file_download)
//                                .setContentTitle("Anchor")
//                                .setContentText("Download completed");
//
//
//
//                NotificationManager notificationManager = (NotificationManager) Scheme_EarningSummary.this.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(455, mBuilder.build());

                list.clear();

                String action = intent.getAction();
                if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(refid);
                    downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                    Cursor c = downloadManager.query(query);
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                            String uriString = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));


                            // File pathn = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                            //  File storageDir = new File(pathn,"Anchor_Dealer_Schemes");

                            String strNew = uriString.replace("file:/", "");
                            File yourFile = new File(strNew.trim());


                            Scheme_EarningSummary.this.runOnUiThread(new Runnable() {
                                public void run() {

                                    dialog.dismiss();


                                }
                            });

                            openFile(yourFile);

                        }
                    }
                }

//                File pathn = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                 File storageDir = new File(pathn,"Anchor_Dealer_Schemes");
//                File yourFile = new File(storageDir, fileName);
//
//                Uri path = Uri.fromFile(yourFile);
//                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
//                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pdfOpenintent.setDataAndType(path, "application/pdf");
//                try {
//                    startActivity(pdfOpenintent);
//                }
//                catch (ActivityNotFoundException e) {
//
//                }


                // openFile(yourFile);
            }
        }
    };

    public void Update_Download_Count(String Module_name,String type) {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;

        String service_url = "";

        service_url = domain + "dealer_users/utilization_report_records?email="+Global_Data.CUSTOMER_EMAIL+"&module_name="+Module_name+"&action_name="+type;
        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Scheme_EarningSummary.Update_Dwonload_cound().execute(response);

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
