package com.anchor.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.Ledger_Adapter;
import com.anchor.helper.PrefManager;
import com.anchor.helper.SimpleDividerItemDecoration;
import com.anchor.model.Ledger_Model;
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
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class Ledger_Report extends Activity implements Ledger_Adapter.UserAdapterListener, Ledger_Adapter.UserAdapterListenernew, DatePickerDialog.OnDateSetListener{

    ConnectionDetector cd;
    static String final_response = "";
    String response_result = "";

    private ShimmerRecyclerView recyclerView;
    Ledger_Adapter ledger_adapter; 
    List<Ledger_Model> ledger_list = new ArrayList<>();
    DatePickerDialog datePickerDialog ;
    String click_detect_flag = "";
    static Calendar calendar ;
    static int Year, Month, Day ;
    EditText ledger_period_value,ledger_period_value2;
    int from_date_value,to_date_value;
    int from_date_year_value,to_date_year_value;
    private PrefManager prefManager;
    AllAngleExpandableButton download_csv;
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);

    String openingb_date = "";
    String openingb_debit_credit = "";
    String closingb_date = "";
    String closinggb_debit_credit = "";
    TextView opening_balance,closing_balance;
    String option_flag = "";
    String G_opening_value = "";
    String G_closing_value = "";
    private String update_At_Date = "";
    String server_flag = "";
    TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_ledger__report);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        setTitle("Ledger Report     "+"Data as on");

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        prefManager = new PrefManager(this);
        ImageView Header_logo = (ImageView) findViewById(R.id.Header_logo);
         mTitleTextView = (TextView)findViewById(R.id.screenname);
        mTitleTextView.setText(Global_Data.CUSTOMER_SERVICE_FLAG);

        TextView todaysTarget = (TextView)findViewById(R.id.todaysTarget);

        SharedPreferences sp = Ledger_Report.this.getSharedPreferences("SimpleLogic", 0);

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

        download_csv =  findViewById(R.id.download_csv);
        recyclerView =  findViewById(R.id.l_lshimmer_recycler_view);
        opening_balance =  findViewById(R.id.opening_balance);
        closing_balance =  findViewById(R.id.closing_balance);

        ledger_period_value =  findViewById(R.id.ledger_period_value);
        ledger_period_value2 =  findViewById(R.id.ledger_period_value2);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        String monthString = new DateFormatSymbols().getMonths()[month];

        ledger_period_value.setText(monthString+" "+year);
        ledger_period_value2.setText(monthString+" "+year);

        from_date_value = month;
        from_date_year_value = year;

        to_date_value = month;
        to_date_year_value = year;

        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.gmail, R.drawable.download};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(getApplicationContext(), drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        download_csv.setButtonDatas(buttonDatas);

        download_csv.setEnabled(false);
        download_csv.setClickable(false);

        recyclerView.showShimmerAdapter();

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

//        ledger_list.add(new Ledger_Model("3-APR-2018","Credit Memo","456546456566","CH345435","fghgfhfhgfh","200","2324"));
//        ledger_list.add(new Ledger_Model("3-APR-2018","Credit Memo","456546456566","CH345435","fghgfhfhgfh","200","2324"));
//        ledger_list.add(new Ledger_Model("3-APR-2018","Credit Memo","456546456566","CH345435","fghgfhfhgfh","200","2324"));
//        ledger_list.add(new Ledger_Model("3-APR-2018","Credit Memo","456546456566","CH345435","fghgfhfhgfh","200","2324"));
//        ledger_list.add(new Ledger_Model("3-APR-2018","Credit Memo","456546456566","CH345435","fghgfhfhgfh","200","2324"));
//        ledger_list.add(new Ledger_Model("3-APR-2018","Credit Memo","456546456566","CH345435","fghgfhfhgfh","200","2324"));
        ledger_adapter = new Ledger_Adapter(Ledger_Report.this, ledger_list,Ledger_Report.this,Ledger_Report.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Ledger_Report.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(ledger_adapter);

        recyclerView.showShimmerAdapter();

        cd  = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet())
        {
            Ledger_ReportList_Result();
        }
        else
        {
            server_flag = "";
            Toast toast = Toast.makeText(Ledger_Report.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(order_home);
            finish();

        }



        download_csv.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {

                if (ledger_list.size()>0)
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
                    Toast toast = Toast.makeText(Ledger_Report.this,"Record not exist.", Toast.LENGTH_LONG);
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

        ledger_period_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_detect_flag = "from_date";
                //Global_Data.hideSoftKeyboard(Ledger_Report.this);

                if(server_flag.equalsIgnoreCase("true"))
                {
                    server_flag = "";

                    MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(Ledger_Report.this, new MonthPickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(int selectedMonth, int selectedYear) {

                            from_date_value = selectedMonth;
                            from_date_year_value = selectedYear;
                            if(!ledger_period_value2.getText().toString().equalsIgnoreCase(""))
                            {
                                String s = CheckDates(selectedMonth,from_date_year_value,to_date_value,to_date_year_value);

                                if(s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a"))
                                {
                                    ledger_period_value.setText(getMonth(selectedMonth) + " " +selectedYear);

                                    cd  = new ConnectionDetector(getApplicationContext());
                                    if (cd.isConnectedToInternet())
                                    {
                                        download_csv.setEnabled(false);
                                        download_csv.setClickable(false);
                                        recyclerView.showShimmerAdapter();
                                        Ledger_ReportList_Result();
                                    }
                                    else
                                    {
                                        Toast toast = Toast.makeText(Ledger_Report.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                }
                                else if(s.equalsIgnoreCase("t"))
                                {
                                    Toast toast = Toast.makeText(Ledger_Report.this,"From Month should be less ", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                            else
                            {
                                ledger_period_value.setText(getMonth(selectedMonth) + " " +selectedYear);
                                Toast toast = Toast.makeText(Ledger_Report.this,"Please Select To Date Month ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    }, /* activated number in year */ Year, Month);

                    builder.setMinYear(2017);
                    builder.build().show();


                }
                else
                {
                    Toast toast = Toast.makeText(Ledger_Report.this,"Please wait data loading on previous filter.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        ledger_period_value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_detect_flag = "to_date";
                //Global_Data.hideSoftKeyboard(Ledger_Report.this);

                if(server_flag.equalsIgnoreCase("true"))
                {
                    server_flag = "";

//                    datePickerDialog = DatePickerDialog.newInstance(Ledger_Report.this, Year, Month, Day);
//                    datePickerDialog.setThemeDark(false);
//                    datePickerDialog.setYearRange(2017,Year);
//                    datePickerDialog.showYearPickerFirst(false);
//                    datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));
//                    datePickerDialog.setTitle("Select To Date");
//                    datePickerDialog.show(getFragmentManager(), "Smart Anchor App");

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(Ledger_Report.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        to_date_value = selectedMonth;
                        to_date_year_value = selectedYear;
                        if (!ledger_period_value.getText().toString().equalsIgnoreCase("")) {
                            String s = CheckDates(from_date_value, from_date_year_value, selectedMonth, to_date_year_value);

                            if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                                ledger_period_value2.setText(getMonth(selectedMonth) + " " + selectedYear);

                                if (cd.isConnectedToInternet()) {
                                    download_csv.setEnabled(false);
                                    download_csv.setClickable(false);
                                    recyclerView.showShimmerAdapter();
                                    Ledger_ReportList_Result();
                                } else {
                                    Toast toast = Toast.makeText(Ledger_Report.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                }
                            } else if (s.equalsIgnoreCase("t")) {
                                Toast toast = Toast.makeText(Ledger_Report.this, "TO Month should be Greater than ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        } else {
                            ledger_period_value2.setText(getMonth(selectedMonth) + " " + selectedYear);
                            Toast toast = Toast.makeText(Ledger_Report.this, "Please Select From Date Month ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }

                    }
                }, /* activated number in year */ Year, Month);
                builder.setMinYear(2017);
                builder.build()
                        .show();

            }
            else
            {
                Toast toast = Toast.makeText(Ledger_Report.this,"Please wait data loading on previous filter.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        Intent m = new Intent(getApplicationContext(),Order.class);
        startActivity(m);
        finish();
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    public void Ledger_ReportList_Result()
    {
        String domain = getResources().getString(R.string.service_domain);


        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " +  Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";


        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(ledger_period_value.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(ledger_period_value2.getText().toString()))
        {
            try
            {
                SimpleDateFormat dfDate  = new SimpleDateFormat("dd-MM-yyyy");

                Calendar c = Calendar.getInstance();
                c.set(Calendar.MONTH,from_date_value);
                c.set(Calendar.YEAR,from_date_year_value);
                c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
                String f_date = dfDate.format(c.getTime());

                Calendar c1 = Calendar.getInstance();
                c1.set(Calendar.MONTH,to_date_value);
                c1.set(Calendar.YEAR,to_date_year_value);
                c1.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
                String t_date = dfDate.format(c1.getTime());

                service_url = domain + "customer_ledgers?email=" + Global_Data.CUSTOMER_EMAIL+ "&start_date=" +f_date+ "&end_date=" + t_date;

            }catch(Exception ex){
                ex.printStackTrace();
            }

        }
        else
        {
            service_url = domain + "customer_ledgers?email=" + Global_Data.CUSTOMER_EMAIL;
        }


        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Ledger_Report.Ledger_Report_List_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        server_flag = "true";
                        Intent intent = new Intent(Ledger_Report.this, MainActivity.class);
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        if (click_detect_flag.equalsIgnoreCase("from_date")) {

//            if(server_flag.equalsIgnoreCase("true"))
//            {
//                server_flag = "";

                from_date_value = monthOfYear;
                from_date_year_value = year;
                if(!ledger_period_value2.getText().toString().equalsIgnoreCase(""))
                {
                    String s = CheckDates(monthOfYear,from_date_year_value,to_date_value,to_date_year_value);

                    if(s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a"))
                    {
                        ledger_period_value.setText(getMonth(monthOfYear) + " " +year);

                        cd  = new ConnectionDetector(getApplicationContext());
                        if (cd.isConnectedToInternet())
                        {
                            download_csv.setEnabled(false);
                            download_csv.setClickable(false);
                            recyclerView.showShimmerAdapter();
                            Ledger_ReportList_Result();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(Ledger_Report.this,"You don't have internet connection.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else if(s.equalsIgnoreCase("t"))
                    {
                        Toast toast = Toast.makeText(Ledger_Report.this,"From Month should be less ", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                else
                {
                    ledger_period_value.setText(getMonth(monthOfYear) + " " +year);
                    Toast toast = Toast.makeText(Ledger_Report.this,"Please Select To Date Month ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

//            }
//            else
//            {
//                Toast toast = Toast.makeText(Ledger_Report.this,"Please wait data loading on previous filter.", Toast.LENGTH_LONG);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
//            }


        }else if (click_detect_flag.equalsIgnoreCase("to_date")) {

            to_date_value = monthOfYear;
            to_date_year_value = year;
            if (!ledger_period_value.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(from_date_value, from_date_year_value, monthOfYear, to_date_year_value);

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    ledger_period_value2.setText(getMonth(monthOfYear) + " " + year);

                    if (cd.isConnectedToInternet()) {
                        download_csv.setEnabled(false);
                        download_csv.setClickable(false);
                        recyclerView.showShimmerAdapter();
                        Ledger_ReportList_Result();
                    } else {
                        Toast toast = Toast.makeText(Ledger_Report.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else if (s.equalsIgnoreCase("t")) {
                    Toast toast = Toast.makeText(Ledger_Report.this, "TO Month should be Greater than ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                ledger_period_value2.setText(getMonth(monthOfYear) + " " + year);
                Toast toast = Toast.makeText(Ledger_Report.this, "Please Select From Date Month ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }


    }

    private class Ledger_Report_List_Task  extends AsyncTask<String, Void, String> {
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

                    Ledger_Report.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Ledger_Report.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Ledger_Report.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
                else
                if(response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Ledger_Report.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Ledger_Report.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Ledger_Report.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });



                }
                else {

                    //dbvoc.getDeleteTable("delivery_products");
                    update_At_Date = "";

                    JSONArray customer_ledgers = response.getJSONArray("customer_ledgers");
                    Log.i("volley", "response customer_ledgers Length: " + customer_ledgers.length());
                    Log.d("volley", "customer_ledgers" + customer_ledgers.toString());

                    JSONArray opening_closing_balance = response.getJSONArray("opening_closing_balance");
                    Log.i("volley", "response opening_closing_balance Length: " + opening_closing_balance.length());
                    Log.d("volley", "opening_closing_balance" + opening_closing_balance.toString());

                    if (customer_ledgers.length() <= 0) {

                        Ledger_Report.this.runOnUiThread(new Runnable() {
                            public void run() {

                                server_flag = "true";
                                recyclerView.hideShimmerAdapter();
                                Toast toast = Toast.makeText(Ledger_Report.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                openingb_date = "";
                                openingb_debit_credit = "";
                                closingb_date = "";
                                closinggb_debit_credit = "";
                                ledger_list.clear();
                                recyclerView.setVisibility(View.GONE);
                                opening_balance.setVisibility(View.GONE);
                                closing_balance.setVisibility(View.GONE);



//                                Intent intent = new Intent(Ledger_Report.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        });
                    } else {

                        server_flag = "true";
                        Ledger_Report.this.runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerView.setVisibility(View.VISIBLE);
                                opening_balance.setVisibility(View.VISIBLE);
                                closing_balance.setVisibility(View.VISIBLE);
                            }
                        });
                        ledger_list.clear();
                         openingb_date = "";
                         openingb_debit_credit = "";
                         closingb_date = "";
                         closinggb_debit_credit = "";
                         String d_cre_flag = "";
                         G_opening_value = "";
                         G_closing_value = "";


                        for (int i = 0; i < opening_closing_balance.length(); i++)
                        {

                            JSONObject jsonObject = opening_closing_balance.getJSONObject(i);

                           // openingb_date = jsonObject.getString("opening_balance_date");
                           // closingb_date = jsonObject.getString("closing_balance_date");
                            closinggb_debit_credit = jsonObject.getString("closing_balance");

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("opening_credit_balance")))
                            {
                                d_cre_flag = "credit";
                                openingb_debit_credit = jsonObject.getString("opening_credit_balance");
                            }
                            else
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("opening_debit_balance")))
                            {
                                d_cre_flag = "debit";
                                openingb_debit_credit = jsonObject.getString("opening_debit_balance");
                            }
                            else
                            {
                                d_cre_flag = "";
                                openingb_debit_credit ="0.0";
                            }



//                            if(d_cre_flag.equalsIgnoreCase("credit"))
//                            {
//                                ledger_list.add(new Ledger_Model(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(openingb_date),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString("Opening Balance"),"","",""," ",isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(openingb_debit_credit)));
//                            }
//                            else
//                            {
//                                ledger_list.add(new Ledger_Model(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(openingb_date),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString("Opening Balance"),"","","",isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(openingb_debit_credit)," "));
//                            }






                        }

                            for (int i = 0; i < customer_ledgers.length(); i++)
                            {

                            JSONObject jsonObject = customer_ledgers.getJSONObject(i);

                            ledger_list.add(new Ledger_Model(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("txn_date")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("txn_type")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_no")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("cheque_no")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("particulars")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("debit_amt")),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("credit_amt"))));

                                update_At_Date = jsonObject.getString("update_at");


                            }

//                        ledger_list.add(new Ledger_Model(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(closingb_date),isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString("Closing Balance"),"","","",isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(closinggb_debit_credit)," "));


                        Ledger_Report.this.runOnUiThread(new Runnable()
                        {
                            public void run() {

                                recyclerView.hideShimmerAdapter();
                                ledger_adapter.notifyDataSetChanged();
                               // setTitle("Ledger Report "+"Data as on "+update_At_Date);
                                mTitleTextView.setText("Ledger Report "+"Data as on "+update_At_Date);


                                try
                                {
                                    DecimalFormat formatter = new DecimalFormat("#,##,##,###");
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(openingb_debit_credit))
                                    {
                                        double opening_value = Double.parseDouble(openingb_debit_credit);
                                        opening_balance.setText("Opening Balance Rs. " + formatter.format(opening_value));
                                        G_opening_value = formatter.format(opening_value);
                                    }

                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(closinggb_debit_credit))
                                    {
                                        double closing_value = Double.parseDouble(closinggb_debit_credit);
                                        closing_balance.setText("Closing Balance Rs. " + formatter.format(closing_value));
                                        G_closing_value = formatter.format(closing_value);
                                    }



                                }catch(Exception ex){
                                    server_flag = "true";
                                    ex.printStackTrace();
                                }


                                download_csv.setEnabled(true);
                                download_csv.setClickable(true);


                                }
                        });



                    }

                    Ledger_Report.this.runOnUiThread(new Runnable() {
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

                Intent intent = new Intent(Ledger_Report.this, MainActivity.class);
                startActivity(intent);
                finish();

                Ledger_Report.this.runOnUiThread(new Runnable() {
                    public void run() {

                        recyclerView.hideShimmerAdapter();
                    }
                });

            }


            Ledger_Report.this.runOnUiThread(new Runnable() {
                public void run() {

                    recyclerView.hideShimmerAdapter();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Ledger_Report.this.runOnUiThread(new Runnable() {
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

    public static String CheckDates(int from_date_month,int from_year, int to_date_month,int to_year)  {

        SimpleDateFormat dfDate  = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH,from_date_month);
        c.set(Calendar.YEAR,from_year);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));

        String f_date = dfDate.format(c.getTime());

        Calendar c1 = Calendar.getInstance();
        c1.set(Calendar.MONTH,to_date_month);
        c1.set(Calendar.YEAR,to_year);
        String t_date = dfDate.format(c1.getTime());


        String  b = "";

        try {
            if(dfDate.parse(f_date).before(dfDate.parse(t_date)))
            {
                b = "f";//If start date is before end date
            }
            else if(dfDate.parse(f_date).equals(dfDate.parse(t_date)))
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


            final String filename = storageDir.toString() + "/" + "Ledger_Report"+".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Ledger_Report.this, contentTitle, "wait...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if(option_flag.equalsIgnoreCase("gmail"))
                    {
                        File yourFile = new File(storageDir, "Ledger_Report"+".csv");

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
                        File yourFile = new File(storageDir, "Ledger_Report"+".csv");

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
            }
            else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
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
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{emailTo});
        emailIntent.putExtra(Intent.EXTRA_CC,
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
                                if (cd.isConnectedToInternet()) {
                                    if(option_flag.equalsIgnoreCase("gmail"))
                                    {
                                        Update_Download_Count("CustomerLedger","email");
                                    }
                                    else
                                    {
                                        Update_Download_Count("CustomerLedger","download");
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Ledger_Report.this);
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
        Anchor anchor = new Anchor("Ledger Report", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        PdfPTable table = new PdfPTable(7);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Transaction Type"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Invoice No"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Cheque No"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Perticulers"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Debit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Credit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        if(ledger_list.size() > 0) {

            for (int i = 0; i < ledger_list.size(); i++) {
                //PdfPTable table1 = new PdfPTable(7);
                table.addCell(ledger_list.get(i).getDate());
                table.addCell(ledger_list.get(i).getTransaction_Type());
                table.addCell(ledger_list.get(i).getInvoice_No());
                table.addCell(ledger_list.get(i).getCheque_No());
                table.addCell(ledger_list.get(i).getPerticulers());
                table.addCell(ledger_list.get(i).getDebit());
                table.addCell(ledger_list.get(i).getCredit());


            }
            catPart.add(table);
            document.add(catPart);
            document.close();
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


            final String filename = folder.toString() + "/" + "Ledger_Report"+".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Ledger_Report.this, contentTitle, "Generated...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    File yourFile = new File(folder, "Ledger_Report"+".csv");

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

                        String ss = " # # #"+ledger_period_value.getText().toString()+"#"+ledger_period_value2.getText().toString()+"# # # ";
                        String[] entrieshv = ss.split("#"); // array of your values
                        writer.writeNext(entrieshv);

                        String[] entrieso = " # # #Opening Balance Rs#Closing Balance Rs# # # ".split("#"); // array of your values
                        writer.writeNext(entrieso);

                        String sss = " # # #"+G_opening_value+"#"+G_closing_value+"# # # ";
                        String[] entriesoc = sss.split("#"); // array of your values
                        writer.writeNext(entriesoc);

                        String[] entries = "Date#Transaction Type#Invoice No#Cheque No#Perticulers#Debit#Credit".split("#"); // array of your values
                        writer.writeNext(entries);


                        if (ledger_list.size() > 0) {
                            for(int i=0; i<ledger_list.size(); i++)
                            {
                                String s = ledger_list.get(i).getDate()+"#"+ledger_list.get(i).getTransaction_Type()+"#"+ledger_list.get(i).getInvoice_No()+"#"+ledger_list.get(i).getCheque_No()+"#"+ledger_list.get(i).getPerticulers()+"#"+ledger_list.get(i).getDebit()+"#"+ledger_list.get(i).getCredit ();
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

                new Ledger_Report.Update_Dwonload_cound().execute(response);

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
