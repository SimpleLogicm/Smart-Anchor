package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.Invoices_Adapter;
import com.anchor.helper.PrefManager;
import com.anchor.helper.SimpleDividerItemDecoration;
import com.anchor.model.Invoice_Download_Model;
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

//import com.jcraft.jsch.Channel;
//import com.jcraft.jsch.ChannelSftp;
//import com.jcraft.jsch.JSch;
//import com.jcraft.jsch.JSchException;
//import com.jcraft.jsch.Session;
//import com.jcraft.jsch.SftpException;
//import org.apache.commons.net.ftp.FTP;
//import org.apache.commons.net.ftp.FTPClient;


public class Invoices extends Activity implements Invoices_Adapter.UserAdapterListener, Invoices_Adapter.UserAdapterListenernew, DatePickerDialog.OnDateSetListener {
    //FTPClient ftp = null;
    ConnectionDetector cd;
    static String final_response = "";
    String response_result = "";
    private ShimmerRecyclerView recyclerView;
    Invoices_Adapter Invoices_Adapter;
    List<Ledger_Model> invoice_list = new ArrayList<>();
    List<Invoice_Download_Model> invoiceDownloadModels = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    String click_detect_flag = "";
    static Calendar calendar;
    static int Year, Month, Day;
    EditText invoice_period_value, invoice_period_value2;
    Spinner in_businees_division, in_businees_brand;
    ArrayAdapter<String> in_businees_division_adapter, in_businees_brand_adapter;

    private ArrayList<String> in_businees_division_results = new ArrayList<String>();
    private ArrayList<String> in_businees_brand_results = new ArrayList<String>();
    private PrefManager prefManager;
    String option_flag = "";
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    File fileIn;

    AllAngleExpandableButton button;
    String invoice_number = "";
    String media_path_content_type = "";
    String document_url = "";
    File file;
    String fileName;
    public ArrayList<Long> list = new ArrayList<>();
    public DownloadManager downloadManager;
    public long refid;
    String download_invoice_no = "";
    String business_division = "";
    String business_brand = "";
    int menu_click_flag = 0;
    private ProgressDialog dialog;
    private String update_At_Date = "";
    TextView mTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_invoices);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        setTitle("Invoices/CN/DN     " + "Data as on ");

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
             mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText(Global_Data.CUSTOMER_SERVICE_FLAG);

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Invoices.this.getSharedPreferences("SimpleLogic", 0);

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

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        prefManager = new PrefManager(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        registerReceiver(onComplete6,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        button = (AllAngleExpandableButton) findViewById(R.id.invoices_button_expandable);
        final List<ButtonData> buttonDatas = new ArrayList<>();
        int[] drawable = {R.drawable.plus, R.drawable.gmail, R.drawable.download};
        for (int i = 0; i < drawable.length; i++) {
            ButtonData buttonData = ButtonData.buildIconButton(getApplicationContext(), drawable[i], 0);
            buttonDatas.add(buttonData);
        }
        button.setButtonDatas(buttonDatas);

        button.setEnabled(false);
        button.setClickable(false);

        recyclerView = findViewById(R.id.in_lshimmer_recycler_view);

        invoice_period_value = findViewById(R.id.invoice_period_value);
        invoice_period_value2 = findViewById(R.id.invoice_period_value2);

        in_businees_division = findViewById(R.id.in_businees_division);
        in_businees_brand = findViewById(R.id.in_businees_brand);

        recyclerView.showShimmerAdapter();

        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        dialog = new ProgressDialog(Invoices.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

//        invoice_list.add(new Ledger_Model("3-APR-2018","Invoice","456546456566"," ","So Reference 4575676576","204610",""));
//        invoice_list.add(new Ledger_Model("4-APR-2018","Invoice","456546456566"," ","So Reference 4575676576","204610",""));
//        invoice_list.add(new Ledger_Model("5-APR-2018","Invoice","456546456566"," ","So Reference 4575676576","204610",""));
//        invoice_list.add(new Ledger_Model("6-APR-2018","Invoice","456546456566"," ","So Reference 4575676576","204610",""));

        Invoices_Adapter = new Invoices_Adapter(getApplicationContext(), invoice_list, Invoices.this, Invoices.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(Invoices_Adapter);

        recyclerView.showShimmerAdapter();

        in_businees_division_results.add("Product Category");
        in_businees_division_adapter = new ArrayAdapter<String>(Invoices.this,
                android.R.layout.simple_spinner_item, in_businees_division_results);
        in_businees_division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        in_businees_division.setAdapter(in_businees_division_adapter);

        Invoices.SpinnerInteractionListenerdivision listener2 = new Invoices.SpinnerInteractionListenerdivision();
        in_businees_division.setOnTouchListener(listener2);
        in_businees_division.setOnItemSelectedListener(listener2);

//        in_businees_brand_results.add("Business Brand");
//        in_businees_brand_adapter = new ArrayAdapter<String>(Invoices.this,
//                android.R.layout.simple_spinner_item, in_businees_brand_results);
//        in_businees_brand_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        in_businees_brand.setAdapter(in_businees_brand_adapter);

        cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectedToInternet()) {
            InvoicesList_Result();
        } else {
            Toast toast = Toast.makeText(Invoices.this, "You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(order_home);
            finish();

        }


        // ConnectFTP();

        invoice_period_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Global_Data.hideSoftKeyboard(Invoices.this);

                click_detect_flag = "from_date";

                datePickerDialog = DatePickerDialog.newInstance(Invoices.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setYearRange(2017,Year);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select From Date");

                datePickerDialog.show(getFragmentManager(), "Smart Anchor App");

            }
        });

        invoice_period_value2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Global_Data.hideSoftKeyboard(Invoices.this);

                click_detect_flag = "to_date";

                datePickerDialog = DatePickerDialog.newInstance(Invoices.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);
                datePickerDialog.setYearRange(2017,Year);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select To Date");

                datePickerDialog.show(getFragmentManager(), "Dealer App");

            }
        });

        button.setButtonEventListener(new ButtonEventListener() {
            @Override
            public void onButtonClicked(int index) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value2.getText().toString()) && invoice_list.size() > 0) {

                    if (index == 1)
                    {
                        menu_click_flag = 1;

                    } else if (index == 2) {
                        menu_click_flag = 2;
                    }

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectedToInternet()) {

                            if(dialog == null)
                            {
                                dialog = new ProgressDialog(Invoices.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                            }

                            dialog.setMessage("Please Wait....");
                            dialog.setTitle("Smart Anchor App");
                            dialog.setCancelable(false);
                            dialog.show();

                        InvoicesList_Result_Download();
                    } else {
                        Toast toast = Toast.makeText(Invoices.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(order_home);
                        finish();

                    }

                } else {
                    if (!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value.getText().toString()) && !Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value2.getText().toString())) {
                        Toast toast = Toast.makeText(Invoices.this, "Please select from date and to date.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(Invoices.this, "Record not exist.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                }

            }

            @Override
            public void onExpand() {

            }

            @Override
            public void onCollapse() {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent m = new Intent(getApplicationContext(), Order.class);
        startActivity(m);

        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        DecimalFormat mFormat = new DecimalFormat("00");
        String date = mFormat.format(Double.valueOf(dayOfMonth)) + "-" + mFormat.format(Double.valueOf(monthOfYear + 1)) + "-" + year;

        if (click_detect_flag.equalsIgnoreCase("from_date")) {
            if (!invoice_period_value2.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(date, invoice_period_value2.getText().toString());

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    invoice_period_value.setText(date);

                    button.setEnabled(false);
                    button.setClickable(false);
                    recyclerView.showShimmerAdapter();
                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectedToInternet()) {
                        InvoicesList_Result();
                    } else {
                        recyclerView.showShimmerAdapter();
                        Toast toast = Toast.makeText(Invoices.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else if (s.equalsIgnoreCase("t")) {
                    invoice_period_value.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "From Date should be less Than To Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                invoice_period_value.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select To Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else if (click_detect_flag.equalsIgnoreCase("to_date")) {
            if (!invoice_period_value.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(invoice_period_value.getText().toString(), date);

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    invoice_period_value2.setText(date);
                    recyclerView.showShimmerAdapter();
                    button.setEnabled(false);
                    button.setClickable(false);

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectedToInternet()) {
                        InvoicesList_Result();
                    } else {
                        Toast toast = Toast.makeText(Invoices.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                } else if (s.equalsIgnoreCase("t")) {
                    invoice_period_value2.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "To Date should be Greater Than from Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                invoice_period_value2.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select From Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public void InvoicesList_Result() {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value2.getText().toString())) {

            try {
                service_url = domain + "customer_invoices/invoice_details?email=" + Global_Data.CUSTOMER_EMAIL + "&start_date=" + invoice_period_value.getText().toString() + "&end_date=" + invoice_period_value2.getText().toString() + "&cat_type=" + URLEncoder.encode(business_division, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ;
        } else {
            try {
                service_url = domain + "customer_invoices/invoice_details?email=" + Global_Data.CUSTOMER_EMAIL + "&cat_type=" + URLEncoder.encode(business_division, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ;
        }


        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Invoices.invoices_List_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(Invoices.this, MainActivity.class);
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

    public void InvoicesList_Result_Download() {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";

        String type = "";
        if (menu_click_flag == 1) {
            type = "email";
        } else if (menu_click_flag == 2) {
            type = "download";

        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(invoice_period_value2.getText().toString())) {

            try {
                service_url = domain + "customer_invoices/download_invoice_csv?email=" + Global_Data.CUSTOMER_EMAIL + "&start_date=" + invoice_period_value.getText().toString() + "&end_date=" + invoice_period_value2.getText().toString() + "&cat_type=" + URLEncoder.encode(business_division, "UTF-8")+ "&type=" + URLEncoder.encode(type, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ;
        } else {
            try {
                service_url = domain + "customer_invoices/download_invoice_csv?email=" + Global_Data.CUSTOMER_EMAIL + "&cat_type=" + URLEncoder.encode(business_division, "UTF-8")+ "&type=" + URLEncoder.encode(type, "UTF-8");;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }


        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Invoices.invoices_List_Task_Download().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hidePDialog();
                        Intent intent = new Intent(Invoices.this, MainActivity.class);
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
    public void onUserClicked(Ledger_Model ledger_model, int position) {

        requestStoragePermissionnew(ledger_model, position);
    }

    private void requestStoragePermissionnew(final Ledger_Model ledger_model, final int position) {

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
                            //Toast.makeText(Customer_Schemes_New.this, scheme_model.getScheme_Name(), Toast.LENGTH_SHORT).show();

//                            try {
//                                boolean s = downloadAndSaveFile();
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }

                            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(Invoices.this).create();
                            alertDialog.setTitle("Smart Anchor App");
                            alertDialog.setMessage(" Are you sure you want to Continue?");
                            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog1, int which) {

                                    download_invoice_no = ledger_model.getInvoice_No();
                                    Log.d("Link Invoice no", "Link Invoice no " + download_invoice_no);
                                    dialog = new ProgressDialog(Invoices.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                    dialog.setMessage("Please wait....");
                                    dialog.setTitle("Anchor");
                                    dialog.setCancelable(false);
                                    dialog.show();

                                    try {
                                        // new MyAyncTask().execute();
                                        Invoices_Download_status();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();

                                }
                            });


                            alertDialog.show();



                            // download_Invoices(ledger_model.getInvoice_No());

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

    public void download_Invoices(String invoice_numbers) {
        invoice_number = invoice_numbers;
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";

        try {
            service_url = domain + "customer_schemes/get_scheme_new_launch?email=" + Global_Data.CUSTOMER_EMAIL + "&invoice_number=" + URLEncoder.encode(invoice_number, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Invoices.invoice_Download_Task().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        dialog.dismiss();
//                        Intent intent = new Intent(Customer_Schemes_New.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
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

    private class invoice_Download_Task extends AsyncTask<String, Void, String> {
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

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });

                } else if (response_result.equalsIgnoreCase("Scheme document not found.")) {


                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("Document Sub Type not found.")) {


                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
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

                        Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {

                                Toast toast = Toast.makeText(Invoices.this, "Scheme document not found.", Toast.LENGTH_LONG);
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
                        file = new File(path, "Anchor_Dealer_Invoices" + "/" + fileName.trim());


                        if (!file.exists()) {
                            file.mkdir();

                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(document_url
                                    .trim())));

                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            request.setAllowedOverRoaming(true);
                            request.setVisibleInDownloadsUi(true);
                            // request.setVisibleInDownloadsUi(true);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Anchor_Dealer_Invoices" + "/" + fileName.trim());

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
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Anchor_Dealer_Invoices" + "/" + fileName.trim());

                            refid = downloadManager.enqueue(request);


                            Log.e("OUTNM", "" + refid);

                            list.add(refid);


                        }


                    }

//                    Customer_Schemes_New.this.runOnUiThread(new Runnable() {
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

                Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });

            }


            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
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

    private class invoices_List_Task extends AsyncTask<String, Void, String> {
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

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Invoices.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {


                            recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(Invoices.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                } else {

                    update_At_Date = "";

                    JSONArray customer_ledgers = response.getJSONArray("customer_ledgers");
                    Log.i("volley", "response customer_ledgers Length: " + customer_ledgers.length());
                    Log.d("volley", "customer_ledgers" + customer_ledgers.toString());

                    JSONArray primary_categories = response.getJSONArray("product_categories");
                    Log.i("volley", "response primary_categories Length: " + primary_categories.length());
                    Log.d("volley", "primary_categories" + primary_categories.toString());



                    if (in_businees_division_results.size() < 2) {

                        in_businees_division_results.clear();
                        in_businees_division_results.add("Product Category");

                        for (int i = 0; i < primary_categories.length(); i++) {

                            JSONObject jsonObject = primary_categories.getJSONObject(i);
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("cat_type"))) {
                                in_businees_division_results.add(jsonObject.getString("cat_type"));
                            }

                        }

                    }
//
//                    if (in_businees_brand_results.size() < 2) {
//
//                        in_businees_brand_results.clear();
//                        in_businees_brand_results.add("Brand");
//
//                        for (int i = 0; i < sub_categories.length(); i++) {
//
//                            JSONObject jsonObject = sub_categories.getJSONObject(i);
//
//                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("sub_category"))) {
//                                in_businees_brand_results.add(jsonObject.getString("sub_category"));
//                            }
//
//                        }
//
//                    }

                    if (customer_ledgers.length() <= 0) {

                        Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {

                                recyclerView.hideShimmerAdapter();
                                Toast toast = Toast.makeText(Invoices.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                invoice_list.clear();
                                recyclerView.setVisibility(View.GONE);
//
//                                Intent intent = new Intent(Invoices.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        });
                    } else {

                        Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        });
                        invoice_list.clear();


                        for (int i = 0; i < customer_ledgers.length(); i++) {

                            JSONObject jsonObject = customer_ledgers.getJSONObject(i);

                            invoice_list.add(new Ledger_Model(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("txn_date")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("txn_type")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("invoice_no")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("product_category")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("sales_order")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("debit_amt")), isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(jsonObject.getString("credit_amt"))));

                            update_At_Date = jsonObject.getString("update_at");


                        }


                        Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {

                                recyclerView.hideShimmerAdapter();
                                Invoices_Adapter.notifyDataSetChanged();

                                mTitleTextView.setText("Invoices/CN/DN     " + "Data as on "+update_At_Date);

                                button.setEnabled(true);
                                button.setClickable(true);


                            }
                        });


                    }

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {

                            if (in_businees_division_results.size() < 2) {
                                in_businees_division_adapter = new ArrayAdapter<String>(Invoices.this,
                                        android.R.layout.simple_spinner_item, in_businees_division_results);
                                in_businees_division_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                in_businees_division.setAdapter(in_businees_division_adapter);
                            }


//                            in_businees_brand_adapter = new ArrayAdapter<String>(Invoices.this,
//                                    android.R.layout.simple_spinner_item, in_businees_brand_results);
//                            in_businees_brand_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            in_businees_brand.setAdapter(in_businees_brand_adapter);
//                            recyclerView.hideShimmerAdapter();
                        }
                    });
//                    	dialog.dismiss();

                    //finish();

                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(Invoices.this, MainActivity.class);
                startActivity(intent);
                finish();

                Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {

                        recyclerView.hideShimmerAdapter();
                    }
                });

            }


            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {

                    recyclerView.hideShimmerAdapter();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Invoices.this.runOnUiThread(new Runnable() {
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

    private class invoices_List_Task_Download extends AsyncTask<String, Void, String> {
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

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {

                            hidePDialog();
                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {
                            hidePDialog();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else {

                    JSONArray customer_ledgers = response.getJSONArray("customer_ledgers");
                    Log.i("volley", "response customer_ledgers Length: " + customer_ledgers.length());
                    Log.d("volley", "customer_ledgers" + customer_ledgers.toString());

                    if (customer_ledgers.length() <= 0) {

                        Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {
                                hidePDialog();
                                Toast toast = Toast.makeText(Invoices.this, "Record not exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                invoiceDownloadModels.clear();

                            }
                        });
                    } else {


                        invoiceDownloadModels.clear();


                        for (int i = 0; i < customer_ledgers.length(); i++) {

                            JSONObject jsonObject = customer_ledgers.getJSONObject(i);

                            invoiceDownloadModels.add(new Invoice_Download_Model(jsonObject.getString("invoice_num"), jsonObject.getString("trx_date"), jsonObject.getString("order_number"), jsonObject.getString("ordered_date"), jsonObject.getString("payment_term"), jsonObject.getString("payment_date"), jsonObject.getString("cat_type"), jsonObject.getString("cust_number"), jsonObject.getString("cust_name"), jsonObject.getString("item_code"), jsonObject.getString("description"), jsonObject.getString("hsn"), jsonObject.getString("tax_rate"), jsonObject.getString("item_qty"), jsonObject.getString("unit_price"), jsonObject.getString("amount"), jsonObject.getString("cgst_rate"), jsonObject.getString("cgst_value"), jsonObject.getString("sgst_rate"), jsonObject.getString("sgst_value"), jsonObject.getString("igst_rate"), jsonObject.getString("igst_value"), jsonObject.getString("ar_total"), jsonObject.getString("pick_slip_no"), jsonObject.getString("update_at")));



                        }


                        Invoices.this.runOnUiThread(new Runnable() {
                            public void run() {

                                hidePDialog();
                                if (menu_click_flag == 1) {
                                    option_flag = "gmail";
                                    requestStoragePermission();
                                } else if (menu_click_flag == 2) {
                                    option_flag = "csv";
                                    requestStoragePermission();
                                }

                                button.setEnabled(true);
                                button.setClickable(true);


                            }
                        });


                    }

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {

                            hidePDialog();
                        }
                    });

                }



            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(Invoices.this, MainActivity.class);
                startActivity(intent);
                finish();

                Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {

                        hidePDialog();
                    }
                });

            }


            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {

                    hidePDialog();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {
                    hidePDialog();
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

//    public void exportEmailInCSV() throws IOException {
//        {
//            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//            final File storageDir = new File(path, "DealerApp");
//
//            if (!storageDir.exists()) {
//                storageDir.mkdir();
//            }
//
////            boolean var = false;
////            if (!folder.exists())
////                var = folder.mkdir();
//
//            // System.out.println("" + var);
//
//
//            final String filename = storageDir.toString() + "/" + "Invoices.pdf";
//
//            // show waiting screen
//            CharSequence contentTitle = getString(R.string.app_name);
//            final ProgressDialog progDailog = ProgressDialog.show(
//                    Invoices.this, contentTitle, "wait...",
//                    true);//please wait
//            final Handler handler = new Handler() {
//                @Override
//                public void handleMessage(Message msg) {
//
//                    if (option_flag.equalsIgnoreCase("gmail")) {
//                        File yourFile = new File(storageDir, "Invoices.pdf");
//
//                        ArrayList<String> att_file = new ArrayList<String>();
//
//                        att_file.add(yourFile.getAbsolutePath());
//
//                        Document document = new Document();
//                        try {
//                            PdfWriter.getInstance(document, new FileOutputStream(yourFile));
//                            document.open();
//                            addContent(document);
//                            document.close();
//                            email_f(getApplicationContext(), "", "", "", "", att_file);
//                        } catch (DocumentException e) {
//                            e.printStackTrace();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    } else {
//                        File yourFile = new File(storageDir, "Invoices.pdf");
//
//                        Document document = new Document();
//                        try {
//                            PdfWriter.getInstance(document, new FileOutputStream(yourFile));
//                            document.open();
//                            addContent(document);
//                            document.close();
//                            openFile(yourFile);
//                        } catch (DocumentException e) {
//                            e.printStackTrace();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//
////                        ArrayList<String> att_file = new ArrayList<String>();
////                        att_file.add(yourFile.getAbsolutePath());
////                        openFile(yourFile);
//                    }
//
////                    File yourFile = new File(storageDir, "Test.csv");
////                    Intent target = new Intent(Intent.ACTION_VIEW);
////                    target.setDataAndType(Uri.parse(yourFile.getAbsolutePath()), "text/csv");
////                    target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
////
////                    Intent intent = Intent.createChooser(target, "Open File");
////                    try {
////                        startActivity(intent);
////                    } catch (ActivityNotFoundException e) {
////                        // Instruct the user to install a PDF reader here, or something
////                    }
//
//
//                }
//            };
//
//            new Thread() {
//                public void run() {
//                    try {
//
//                    } catch (Exception e) {
//                    }
//                    handler.sendEmptyMessage(0);
//                    progDailog.dismiss();
//
//                }
//            }.start();
//
//
//        }
//
//    }

    private void addContent(Document document) throws DocumentException {
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

    private void createTable(Document document)
            throws DocumentException {
        Anchor anchor = new Anchor("Outstanding Report", catFont);
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);
        PdfPTable table = new PdfPTable(6);

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

        c1 = new PdfPCell(new Phrase("Refernece No"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Sales Order"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Debit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Credit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);


        table.setHeaderRows(1);

        if (invoice_list.size() > 0) {

            for (int i = 0; i < invoice_list.size(); i++) {
                //PdfPTable table1 = new PdfPTable(7);
                table.addCell(invoice_list.get(i).getDate());
                table.addCell(invoice_list.get(i).getTransaction_Type());
                table.addCell(invoice_list.get(i).getInvoice_No());
                table.addCell(invoice_list.get(i).getPerticulers());
                table.addCell(invoice_list.get(i).getDebit());
                table.addCell(invoice_list.get(i).getCredit());


            }
            catPart.add(table);
            document.add(catPart);
            document.close();
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
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

    public void email_f(Context context, String emailTo, String emailCC,
                        String subject, String emailText, List<String> filePaths) {
        //need to "send multiple" to get more than one attachment
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // emailIntent.setType("text/plain");
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
            fileIn = new File(file);
            Uri u = Uri.fromFile(fileIn);
            uris.add(u);
        }
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        Intent chooserIntent = Intent.createChooser(emailIntent, "Send mail...");
        chooserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(chooserIntent);

        // File outputFile = new File(file);
        //  Uri uri = Uri.fromFile(fileIn);

//        Intent share = new Intent();
//        share.setAction(Intent.ACTION_SEND);
//        share.setType("application/pdf");
//        share.putExtra(Intent.EXTRA_STREAM, uri);
//        share.setPackage("com.whatsapp");
//
//        startActivity(share);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete6);
        hidePDialog();
    }

    private void hidePDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public BroadcastReceiver onComplete6 = new BroadcastReceiver() {

        public void onReceive(Context ctxt, Intent intent) {

            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);


            Log.e("IN", "" + referenceId);

            list.remove(referenceId);


            if (list.isEmpty()) {

                // dialog.dismiss();

                Log.e("INSIDE", "" + referenceId);
                Toast.makeText(ctxt, "Downloading Completed", Toast.LENGTH_SHORT).show();
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(Customer_Schemes_New.this)
//                                .setSmallIcon(R.drawable.file_download)
//                                .setContentTitle("Anchor")
//                                .setContentText("Download completed");
//
//
//
//                NotificationManager notificationManager = (NotificationManager) Customer_Schemes_New.this.getSystemService(Context.NOTIFICATION_SERVICE);
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


                            Invoices.this.runOnUiThread(new Runnable() {
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

//    private Boolean downloadAndSaveFile()
//            throws IOException {
//        FTPClient ftp = null;
//
//        try {
//            ftp = new FTPClient();
//            ftp.connect("ftp://125.22.54.155", 80);
//            Log.d(LOG_TAG, "Connected. Reply: " + ftp.getReplyString());
//
//            ftp.login("simplelogic", "pass@123");
//            Log.d(LOG_TAG, "Logged in");
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
//            Log.d(LOG_TAG, "Downloading");
//            ftp.enterLocalPassiveMode();
//
//            OutputStream outputStream = null;
//            boolean success = false;
//            try {
//                outputStream = new BufferedOutputStream(new FileOutputStream(
//                        "weborder/invoicepdf/7010118000009.pdf"));
//                success = ftp.retrieveFile("weborder/invoicepdf/7010118000009.pdf", outputStream);
//            } finally {
//                if (outputStream != null) {
//                    outputStream.close();
//                }
//            }
//
//            return success;
//        } finally {
//            if (ftp != null) {
//                ftp.logout();
//                ftp.disconnect();
//            }
//        }
//    }


//    public void ConnectFTP() {
//        boolean status = false;
//        try {
//            ftp = new FTPClient();
//            ftp.setDataTimeout(10 * 1000);
//            ftp.connect(InetAddress.getByName("ftp://10.254.180.209"), 21);
//            status = ftp.login("simplelogic", "pass@123");
//            ftp.enterLocalPassiveMode();
//            Log.e("isFTPConnected", String.valueOf(status));
//            downloadFTP("weborder/invoicepdf/7010118000009.pdf", "7010118000009.pdf", "");
//        } catch (SocketException e) {
//            e.printStackTrace();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void downloadFTP(String FTP_file, String SD_file, String folder) {
//        ConnectFTP();
//        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/qaqc");
//        dir.mkdirs();
//        try {
//            ftp.changeWorkingDirectory(folder);
//        } catch (IOException e1) {
//            // TODO Auto-generated catch block
//            e1.printStackTrace();
//        }
//
//        BufferedOutputStream desFileStream = null;
//        try {
//            ftp.enterLocalPassiveMode();
//            if (!ftp.setFileType(FTP.BINARY_FILE_TYPE)) {
//                Log.e("binary", "Setting binary file type failed.");
//            }
//            desFileStream = new BufferedOutputStream(new FileOutputStream(
//                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/qaqc/" + folder + "/" + SD_file),
//                    8 * 1024);
//
//            ftp.retrieveFile("/" + FTP_file, desFileStream);
//            desFileStream.flush();
//            desFileStream.close();
//            ftp.logout();
//            ftp.disconnect();
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }

//    public class MyAyncTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            // Here you can show progress bar or something on the similar lines.
//            // Since you are in a UI thread here.
//            super.onPreExecute();
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            // After completing execution of given task, control will return here.
//            // Hence if you want to populate UI elements with fetched data, do it here.
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//            // You can track you progress update here
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            boolean status = false;
//            try {
//
////                JSch jsch = new JSch();
////                Session session = jsch.getSession( "simplelogic", "125.22.54.155", 22 );
////                session.setConfig( "PreferredAuthentications", "password" );
////                session.setPassword( "pass@123" );
////                session.connect(10 * 1000 );
////                Channel channel = session.openChannel( "sftp" );
////                ChannelSftp sftp = ( ChannelSftp ) channel;
////                System.out.println("Connection established.");
////                System.out.println("Creating SFTP Channel.");
//
//                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//                final File storageDir = new File(path, "DealerApp/Invoice_pdf");
//
//                if (storageDir.exists()) {
//                    storageDir.delete();
//                    storageDir.mkdir();
//                } else {
//                    storageDir.mkdir();
//                }
//
//
//                final String filename = storageDir.toString() + "/" + download_invoice_no + ".pdf";
//
//                JSch jsch = new JSch();
//                Session session = null;
//                String Ftp_user_id = "";
//                String Ftp_user_password = "";
//                try {
//
//                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(prefManager.getFtp_user_id())) {
//                        Ftp_user_id = prefManager.getFtp_user_id();
//                    } else {
//                        Ftp_user_id = "simplelogic";
//                    }
//
//                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(prefManager.getFtp_user_password())) {
//                        Ftp_user_password = prefManager.getFtp_user_password();
//                    } else {
//                        Ftp_user_password = "P@ssw0rd321";
//                    }
//
//                    session = jsch.getSession(Ftp_user_id, "10.254.180.209", 21);
//                    session.setConfig("StrictHostKeyChecking", "no");
//                    session.setPassword(Ftp_user_password);
//                    //  session.setTimeout(80000);
//                    session.connect();
//
//                    Channel channel = session.openChannel("sftp");
//                    channel.connect();
//                    ChannelSftp sftpChannel = (ChannelSftp) channel;
//                    sftpChannel.get("weborder/invoicepdf/" + download_invoice_no + ".pdf", filename);
//
//                    Invoices.this.runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            dialog.dismiss();
//                            File yourFile = new File(filename);
//                            openFile(yourFile);
//
//                        }
//                    });
//                    sftpChannel.exit();
//                    session.disconnect();
//                } catch (final JSchException e) {
//                    e.printStackTrace();
//                    Invoices.this.runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            dialog.dismiss();
//                            Toast toast = Toast.makeText(Invoices.this, e.getMessage(), Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//
//                        }
//                    });
//
//                } catch (final SftpException e) {
//                    e.printStackTrace();
//                    Invoices.this.runOnUiThread(new Runnable() {
//                        public void run() {
//                            Toast toast = Toast.makeText(Invoices.this, e.getMessage(), Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//                            dialog.dismiss();
//
//                        }
//                    });
//
//                }
//
////                ftp = new FTPClient();
////
////                ftp.setDataTimeout(10 * 1000);
////                ftp.connect(InetAddress.getByName("125.22.54.155"),22);
////               // ftp.(new UnixPermissions("777"))
////                status = ftp.login("simplelogic", "pass@123");
////                ftp.enterLocalPassiveMode();
////                Log.e("isFTPConnected", String.valueOf(status));
////                downloadFTP("weborder/invoicepdf/7010118000009.pdf","7010118000009.pdf","");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

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


            final String filename = storageDir.toString() + "/" + "Invoices" + ".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Invoices.this, contentTitle, "wait...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    if (option_flag.equalsIgnoreCase("gmail")) {
                        File yourFile = new File(storageDir, "Invoices" + ".csv");

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
                        File yourFile = new File(storageDir, "Invoices" + ".csv");

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

    public void GenerateCSVCSV() throws IOException {
        {

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            final File folder = new File(path, "DealerApp");

            if (!folder.exists()) {
                folder.mkdir();
            }

            boolean var = false;


            System.out.println("" + var);


            final String filename = folder.toString() + "/" + "Invoices" + ".csv";

            // show waiting screen
            CharSequence contentTitle = getString(R.string.app_name);
            final ProgressDialog progDailog = ProgressDialog.show(
                    Invoices.this, contentTitle, "Generated...",
                    true);//please wait
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {

                    File yourFile = new File(folder, "Invoices" + ".csv");

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
                        String[] entriesh = " # # # # # # # # # # #Start Date#End Date# # # # # # # # # # # ".split("#"); // array of your values
                        writer.writeNext(entriesh);

                        String ss = " # # # # # # # # # # #" + invoice_period_value.getText().toString() + "#" + invoice_period_value2.getText().toString() + "# # # # # # # # # # # ";
                        String[] entrieshv = ss.split("#"); // array of your values
                        writer.writeNext(entrieshv);


                        String[] entries = "INVOICE_NUM#TRX_DATE#ORDER_NUMBER#ORDERED_DATE#PAYMENT_TERM#PAYMENT_DATE#CAT_TYPE#CUST_NUMBER#CUST_NAME#ITEM_CODE#DESCRIPTION#HSN#TAX_RATE#ITEM_QTY#UNIT_PRICE#AMOUNT#CGST_RATE#CGST_VALUE#SGST_RATE#SGST_VALUE#IGST_RATE#IGST_VALUE#AR_TOTAL#PICK_SLIP_NO".split("#"); // array of your values
                        writer.writeNext(entries);


                        if (invoiceDownloadModels.size() > 0) {
                            for (int i = 0; i < invoiceDownloadModels.size(); i++) {
                                String s = invoiceDownloadModels.get(i).getINVOICE_NUM() + "#" + invoiceDownloadModels.get(i).getTRX_DATE() + "#" + invoiceDownloadModels.get(i).getORDER_NUMBER() + "#" + invoiceDownloadModels.get(i).getORDERED_DATE() + "#" + invoiceDownloadModels.get(i).getPAYMENT_TERM() + "#" + invoiceDownloadModels.get(i).getPAYMENT_DATE() + "#" + invoiceDownloadModels.get(i).getCAT_TYPE() + "#" + invoiceDownloadModels.get(i).getCUST_NUMBER() + "#" + invoiceDownloadModels.get(i).getCUST_NAME() + "#" + invoiceDownloadModels.get(i).getITEM_CODE() + "#" + invoiceDownloadModels.get(i).getDESCRIPTION() + "#" + invoiceDownloadModels.get(i).getHSN() + "#" + invoiceDownloadModels.get(i).getTAX_RATE() + "#" + invoiceDownloadModels.get(i).getITEM_QTY() + "#" + invoiceDownloadModels.get(i).getUNIT_PRICE() + "#" + invoiceDownloadModels.get(i).getAMOUNT() + "#" + invoiceDownloadModels.get(i).getCGST_RATE() + "#" + invoiceDownloadModels.get(i).getCGST_VALUE() + "#" + invoiceDownloadModels.get(i).getSGST_RATE() + "#" + invoiceDownloadModels.get(i).getSGST_VALUE() + "#" + invoiceDownloadModels.get(i).getIGST_RATE() + "#" + invoiceDownloadModels.get(i).getIGST_VALUE() + "#" + invoiceDownloadModels.get(i).getAR_TOTAL() + "#" + invoiceDownloadModels.get(i).getPICK_SLIP_NO();
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
                if (selectedItem.equalsIgnoreCase("Product Category")) {
                    business_division = "";

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectedToInternet()) {
                        recyclerView.showShimmerAdapter();
                        InvoicesList_Result();
                    } else {
                        Toast toast = Toast.makeText(Invoices.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    }
                } else {
                    business_division = selectedItem;

                    cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectedToInternet()) {
                        recyclerView.showShimmerAdapter();
                        InvoicesList_Result();
                    } else {
                        Toast toast = Toast.makeText(Invoices.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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

    public void Invoices_Download_status() {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        StringRequest jsObjRequest = null;
        String service_url = "";

        try {
                service_url = domain + "customer_invoices/create_dealer_invoice_requests?email=" + Global_Data.CUSTOMER_EMAIL + "&invoice_no=" + download_invoice_no;
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Invoices.invoices_Download_status_check().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        hidePDialog();
                        Intent intent = new Intent(Invoices.this, MainActivity.class);
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

    private class invoices_Download_status_check extends AsyncTask<String, Void, String> {
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

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {

                            hidePDialog();
                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {
                            hidePDialog();

                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else {


                    Invoices.this.runOnUiThread(new Runnable() {
                        public void run() {

                            hidePDialog();
                            Toast toast = Toast.makeText(Invoices.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });

                }



            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(Invoices.this, MainActivity.class);
                startActivity(intent);
                finish();

                Invoices.this.runOnUiThread(new Runnable() {
                    public void run() {

                        hidePDialog();
                    }
                });

            }


            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {

                    hidePDialog();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Invoices.this.runOnUiThread(new Runnable() {
                public void run() {
                    hidePDialog();
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
