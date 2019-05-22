package com.anchor.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.helper.PrefManager;
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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import cpm.simplelogic.helper.ConnectionDetector;

public class Scheme_Filter_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    int counter = 0;
    Button btn_close, btn_Ongoing, btn_Quantity, btn_Value, btn_apply;
    String close_click_flag = "";
    String Ongoing_click_flag = "";
    String Quantity_click_flag = "";
    String Value_click_flag = "";
    Spinner target_year, target_quarter, target_half_year, target_month, bu_spinner, bu_spinnernew, division_spinnern, brand_spinnern;
    TextView view_text, type_text;
    ArrayAdapter<String> adapter_year;
    ArrayAdapter<String> adapter_month;
    ArrayAdapter<String> adapter_quarter;
    ArrayAdapter<String> adapter_half_year;
    ArrayAdapter<String> adapter_business_unit;
    ArrayAdapter<String> adapter_business_unitn;
    ArrayAdapter<String> adapter_business_division;
    ArrayAdapter<String> adapter_business_brand;
    private ArrayList<String> quarter_list = new ArrayList<String>();
    private ArrayList<String> year_list = new ArrayList<String>();
    private ArrayList<String> halfyear_list = new ArrayList<String>();
    private ArrayList<String> month_list = new ArrayList<String>();
    ArrayList<String> product_cate_list = new ArrayList<>();
    ArrayList<String> product_bu_list = new ArrayList<>();
    ArrayList<String> product_division_list = new ArrayList<>();
    ArrayList<String> product_brand_list = new ArrayList<>();

    private PrefManager prefManager;
    DatePickerDialog datePickerDialog;
    String click_detect_flag = "";
    static Calendar calendar;
    static int Year, Month, Day;
    EditText scheme_filter_date1, scheme_filter_date2;
    int from_date_value, to_date_value;
    int from_date_year_value, to_date_year_value;
    ConnectionDetector cd;
    private ProgressDialog dialog;
    static String final_response = "";
    String response_result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_scheme__filter);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();

        scheme_filter_date1 = findViewById(R.id.scheme_filter_date1);
        scheme_filter_date2 = findViewById(R.id.scheme_filter_date2);

        btn_close = findViewById(R.id.btn_close);
        btn_Ongoing = findViewById(R.id.btn_Ongoing);
        btn_Quantity = findViewById(R.id.btn_Quantity);
        btn_Value = findViewById(R.id.btn_Value);
        btn_apply = findViewById(R.id.btn_apply);

        target_year = findViewById(R.id.target_year);
        target_quarter = findViewById(R.id.target_quarter);
        target_half_year = findViewById(R.id.target_half_year);
        target_month = findViewById(R.id.target_month);
        bu_spinner = findViewById(R.id.bu_spinner);
        bu_spinnernew = findViewById(R.id.bu_spinnernew);
        division_spinnern = findViewById(R.id.division_spinnern);
        brand_spinnern = findViewById(R.id.brand_spinnern);

        view_text = findViewById(R.id.view_text);
        type_text = findViewById(R.id.type_text);

        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        cd = new ConnectionDetector(getApplicationContext());
        prefManager = new PrefManager(this);
        dialog = new ProgressDialog(Scheme_Filter_Activity.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);


        String view_text_value = "View ";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder(view_text_value + colored);

        builder.setSpan(new ForegroundColorSpan(Color.RED), view_text_value.length(), builder.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        view_text.setText(builder);


        String type_text_value = "Type ";
        SpannableStringBuilder builder2 = new SpannableStringBuilder(type_text_value + colored);

        builder2.setSpan(new ForegroundColorSpan(Color.RED), type_text_value.length(), builder2.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        type_text.setText(builder2);


        year_list.add("Yearly");
        int start_year = 2017;
        int current_year = Calendar.getInstance().get(Calendar.YEAR) + 1;

        while (start_year <= current_year) {
            int yy = start_year++;
            year_list.add(String.valueOf(yy));

        }

        adapter_year = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item, year_list);
        adapter_year.setDropDownViewResource(R.layout.spinner_item);
        target_year.setAdapter(adapter_year);
        target_year.setSelection(0, false);
        target_year.setOnItemSelectedListener(Scheme_Filter_Activity.this);


        // target_year.setOnItemSelectedListener(Scheme_Filter_Activity.this);

        month_list.clear();
        month_list.add("Monthly");
        quarter_list.clear();
        quarter_list.add("Quarterly");
        halfyear_list.clear();
        halfyear_list.add("Half Yearly");
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length; i++) {
            String monthr = months[i];
            System.out.println("month = " + monthr);
            month_list.add(months[i]);
            quarter_list.add(months[i]);
            halfyear_list.add(months[i]);
        }

        adapter_month = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item, month_list);
        adapter_month.setDropDownViewResource(R.layout.spinner_item);
        target_month.setAdapter(adapter_month);
        target_month.setSelection(0, false);
        target_month.setOnItemSelectedListener(Scheme_Filter_Activity.this);


        adapter_half_year = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item, halfyear_list);
        adapter_half_year.setDropDownViewResource(R.layout.spinner_item);
        target_half_year.setAdapter(adapter_half_year);
        target_half_year.setSelection(0, false);
        target_half_year.setOnItemSelectedListener(Scheme_Filter_Activity.this);


        adapter_quarter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.spinner_item, quarter_list);
        adapter_quarter.setDropDownViewResource(R.layout.spinner_item);
        target_quarter.setAdapter(adapter_quarter);
        target_quarter.setSelection(0, false);
        target_quarter.setOnItemSelectedListener(Scheme_Filter_Activity.this);


        scheme_filter_date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Global_Data.hideSoftKeyboard(Scheme_Filter_Activity.this);

                click_detect_flag = "from_date";

                datePickerDialog = DatePickerDialog.newInstance(Scheme_Filter_Activity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);
                datePickerDialog.setYearRange(2017, Year);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select From Date");

                datePickerDialog.show(getFragmentManager(), "Dealer App");

            }
        });

        scheme_filter_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Global_Data.hideSoftKeyboard(Scheme_Filter_Activity.this);
                click_detect_flag = "to_date";

                datePickerDialog = DatePickerDialog.newInstance(Scheme_Filter_Activity.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setYearRange(2017, Year);

                datePickerDialog.setAccentColor(Color.parseColor("#303F9F"));

                datePickerDialog.setTitle("Select To Date");

                datePickerDialog.show(getFragmentManager(), "Dealer App");

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global_Data.close_click_flag.equalsIgnoreCase("")) {

                    Global_Data.close_click_flag = "true";
                    Global_Data.Ongoing_click_flag = "";

                    bu_spinnernew.setEnabled(true);
                    bu_spinnernew.setClickable(true);

                    division_spinnern.setEnabled(true);
                    division_spinnern.setClickable(true);

                    brand_spinnern.setEnabled(true);
                    brand_spinnern.setClickable(true);

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_close.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    btn_Ongoing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {

                    bu_spinnernew.setEnabled(false);
                    bu_spinnernew.setClickable(false);

                    division_spinnern.setEnabled(false);
                    division_spinnern.setClickable(false);

                    brand_spinnern.setEnabled(false);
                    brand_spinnern.setClickable(false);

                    Global_Data.close_click_flag = "";
                    Global_Data.Ongoing_click_flag = "true";

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_close.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    btn_Ongoing.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                }


            }
        });

        btn_Ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Global_Data.Ongoing_click_flag.equalsIgnoreCase("")) {

                    Global_Data.close_click_flag = "";
                    Global_Data.Ongoing_click_flag = "true";

                    bu_spinnernew.setEnabled(false);
                    bu_spinnernew.setClickable(false);

                    division_spinnern.setEnabled(false);
                    division_spinnern.setClickable(false);

                    brand_spinnern.setEnabled(false);
                    brand_spinnern.setClickable(false);

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_close.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    btn_Ongoing.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);


                } else {
                    Global_Data.close_click_flag = "true";
                    Global_Data.Ongoing_click_flag = "";


                    bu_spinnernew.setEnabled(true);
                    bu_spinnernew.setClickable(true);

                    division_spinnern.setEnabled(true);
                    division_spinnern.setClickable(true);

                    brand_spinnern.setEnabled(true);
                    brand_spinnern.setClickable(true);

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_close.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    btn_Ongoing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        });


        btn_Quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global_Data.Quantity_click_flag.equalsIgnoreCase("")) {

                    Global_Data.Quantity_click_flag = "true";
                    Global_Data.Value_click_flag = "";

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_Quantity.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    btn_Value.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    Global_Data.Quantity_click_flag = "";
                    Global_Data.Value_click_flag = "true";

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_Quantity.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    btn_Value.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                }


            }
        });

        btn_Value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global_Data.Value_click_flag.equalsIgnoreCase("")) {

                    Global_Data.Quantity_click_flag = "";
                    Global_Data.Value_click_flag = "true";

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_Quantity.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    btn_Value.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);


                } else {
                    Global_Data.Quantity_click_flag = "true";
                    Global_Data.Value_click_flag = "";

                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
                    btn_Quantity.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    btn_Value.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }


            }
        });

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Global_Data.close_click_flag.equalsIgnoreCase("") && Global_Data.Ongoing_click_flag.equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(Scheme_Filter_Activity.this, "Please Select View .", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (Global_Data.Quantity_click_flag.equalsIgnoreCase("") && Global_Data.Value_click_flag.equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(Scheme_Filter_Activity.this, "Please Select Type.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else if (Global_Data.close_click_flag.equalsIgnoreCase("true")) {
                    if (!bu_spinner.getSelectedItem().toString().equalsIgnoreCase("Product Category")) {
                        Global_Data.target_product_cate_value = bu_spinner.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_cate_value = "";
                    }

                    if (!bu_spinnernew.getSelectedItem().toString().equalsIgnoreCase("Business Unit")) {
                        Global_Data.target_product_Bu_value = bu_spinnernew.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_Bu_value = "";
                    }

                    if (!division_spinnern.getSelectedItem().toString().equalsIgnoreCase("Category")) {
                        Global_Data.target_product_division_value = division_spinnern.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_division_value = "";
                    }

                    if (!brand_spinnern.getSelectedItem().toString().equalsIgnoreCase("Brand")) {
                        Global_Data.target_product_brand_value = brand_spinnern.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_brand_value = "";
                    }

                    if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {
                        Global_Data.year_value = target_year.getSelectedItem().toString();
                    } else {
                        Global_Data.year_value = "";
                    }

                    if (!target_month.getSelectedItem().toString().equalsIgnoreCase("Monthly")) {
                        Global_Data.monthly_value = target_month.getSelectedItem().toString();
                    } else {
                        Global_Data.monthly_value = "";
                    }

                    if (!target_quarter.getSelectedItem().toString().equalsIgnoreCase("Quarterly")) {
                        Global_Data.quartly_value = target_quarter.getSelectedItem().toString();
                    } else {
                        Global_Data.quartly_value = "";
                    }

                    if (!target_half_year.getSelectedItem().toString().equalsIgnoreCase("Half Yearly")) {
                        Global_Data.halfyear_value = target_half_year.getSelectedItem().toString();
                    } else {
                        Global_Data.halfyear_value = "";
                    }

                    if (!scheme_filter_date1.getText().toString().equalsIgnoreCase("")) {
                        Global_Data.scheme_from_date = scheme_filter_date1.getText().toString();
                    } else {
                        Global_Data.scheme_from_date = "";
                    }

                    if (!scheme_filter_date2.getText().toString().equalsIgnoreCase("")) {
                        Global_Data.scheme_to_date = scheme_filter_date2.getText().toString();
                    } else {
                        Global_Data.scheme_to_date = "";
                    }

                    Intent i = new Intent(Scheme_Filter_Activity.this, Scheme_EarningSummary.class);
                    startActivity(i);
                } else {
                    if (!bu_spinner.getSelectedItem().toString().equalsIgnoreCase("Product Category")) {
                        Global_Data.target_product_cate_value = bu_spinner.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_cate_value = "";
                    }

                    if (!bu_spinnernew.getSelectedItem().toString().equalsIgnoreCase("Business Unit")) {
                        Global_Data.target_product_Bu_value = bu_spinnernew.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_Bu_value = "";
                    }

                    if (!division_spinnern.getSelectedItem().toString().equalsIgnoreCase("Category")) {
                        Global_Data.target_product_division_value = division_spinnern.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_division_value = "";
                    }

                    if (!brand_spinnern.getSelectedItem().toString().equalsIgnoreCase("Brand")) {
                        Global_Data.target_product_brand_value = brand_spinnern.getSelectedItem().toString();
                    } else {
                        Global_Data.target_product_brand_value = "";
                    }

                    if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {
                        Global_Data.year_value = target_year.getSelectedItem().toString();
                    } else {
                        Global_Data.year_value = "";
                    }

                    if (!target_month.getSelectedItem().toString().equalsIgnoreCase("Monthly")) {
                        Global_Data.monthly_value = target_month.getSelectedItem().toString();
                    } else {
                        Global_Data.monthly_value = "";
                    }

                    if (!target_quarter.getSelectedItem().toString().equalsIgnoreCase("Quarterly")) {
                        Global_Data.quartly_value = target_quarter.getSelectedItem().toString();
                    } else {
                        Global_Data.quartly_value = "";
                    }


                    if (!target_half_year.getSelectedItem().toString().equalsIgnoreCase("Half Yearly")) {
                        Global_Data.halfyear_value = target_half_year.getSelectedItem().toString();
                    } else {
                        Global_Data.halfyear_value = "";
                    }

                    if (!scheme_filter_date1.getText().toString().equalsIgnoreCase("")) {
                        Global_Data.scheme_from_date = scheme_filter_date1.getText().toString();
                    } else {
                        Global_Data.scheme_from_date = "";
                    }

                    if (!scheme_filter_date2.getText().toString().equalsIgnoreCase("")) {
                        Global_Data.scheme_to_date = scheme_filter_date2.getText().toString();
                    } else {
                        Global_Data.scheme_to_date = "";
                    }


                    Intent i = new Intent(Scheme_Filter_Activity.this, Customer_Schemes_New.class);
                    startActivity(i);
                }

            }
        });


        if (cd.isConnectingToInternet()) {
            dialog.setMessage("Please wait Product Category Loading....");
            dialog.setTitle("Dealer App");
            dialog.setCancelable(false);
            dialog.show();

            bu_details();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent i = new Intent(Scheme_Filter_Activity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
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

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    public void bu_details() {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        String service_url = domain + "customer_schemes/get_scheme_earnings_data?email=" + Global_Data.CUSTOMER_EMAIL;

        Log.i("user list url", "user list url " + service_url);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new Scheme_Filter_Activity.getAllBU().execute(response);

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
                        } else {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        Intent m = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(m);

                        finish();
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

        DecimalFormat mFormat = new DecimalFormat("00");
        String date = mFormat.format(Double.valueOf(dayOfMonth)) + "-" + mFormat.format(Double.valueOf(monthOfYear + 1)) + "-" + year;

        int spinnerPosition = adapter_year.getPosition("Yearly");
        target_year.setSelection(spinnerPosition);

        int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
        target_half_year.setSelection(spinnerPosition2);

        int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
        target_quarter.setSelection(spinnerPosition3);

        int spinnerPosition4 = adapter_month.getPosition("Monthly");
        target_month.setSelection(spinnerPosition4);

        if (click_detect_flag.equalsIgnoreCase("from_date")) {
            scheme_filter_date1.setText("");
            if (!scheme_filter_date2.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(date, scheme_filter_date2.getText().toString());

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    scheme_filter_date1.setText(date);


                } else if (s.equalsIgnoreCase("t")) {

                    scheme_filter_date1.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "From Date should be less Than To Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                scheme_filter_date1.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select To Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        } else if (click_detect_flag.equalsIgnoreCase("to_date")) {
            scheme_filter_date2.setText("");
            if (!scheme_filter_date1.getText().toString().equalsIgnoreCase("")) {
                String s = CheckDates(scheme_filter_date1.getText().toString(), date);

                if (s.equalsIgnoreCase("f") || s.equalsIgnoreCase("a")) {
                    scheme_filter_date2.setText(date);

                } else if (s.equalsIgnoreCase("t")) {
                    scheme_filter_date2.setText("");
                    Toast toast = Toast.makeText(getApplicationContext(), "To Date should be Greater Than from Date ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                scheme_filter_date2.setText(date);
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select From Date ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }


    private class getAllBU extends AsyncTask<String, Void, String> {
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

                    Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Scheme_Filter_Activity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Scheme_Filter_Activity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else {

                    //dbvoc.getDeleteTable("delivery_products");

                    JSONArray business_units = response.getJSONArray("business_units");
                    Log.i("volley", "response business_units Length: " + business_units.length());
                    Log.d("volley", "business_units" + business_units.toString());

                    JSONArray primary_categories = response.getJSONArray("primary_categories");
                    Log.i("volley", "response primary_categories Length: " + primary_categories.length());
                    Log.d("volley", "primary_categories" + primary_categories.toString());

                    JSONArray business_categories = response.getJSONArray("business_categories");
                    Log.i("volley", "response business_categories Length: " + business_categories.length());
                    Log.d("volley", "business_categories" + business_categories.toString());

                    JSONArray sub_categories = response.getJSONArray("sub_categories");
                    Log.i("volley", "response sub_categories Length: " + sub_categories.length());
                    Log.d("volley", "sub_categories" + sub_categories.toString());

//                    if (primary_categories.length() <= 0) {
//
//                        Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
//                            public void run() {
//
//                                dialog.dismiss();
//                                //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
//
//                                Toast toast = Toast.makeText(Scheme_Filter_Activity.this, "Business Unit doesn't exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//
//                                Intent i=new Intent(Scheme_Filter_Activity.this, MainActivity.class);
//                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(i);
//                            }
//                        });
//                    } else {

                    product_bu_list.clear();
                    product_bu_list.add("Business Unit");

                    product_division_list.clear();
                    product_division_list.add("Category");


                    product_brand_list.clear();
                    product_brand_list.add("Brand");

                    product_cate_list.clear();
                    product_cate_list.add("Product Category");

                    for (int i = 0; i < primary_categories.length(); i++) {

                        try {
                            JSONObject jsonObject = primary_categories.getJSONObject(i);
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(primary_categories.getString(i))) {
                                {
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("primary_category"))) {
                                        product_cate_list.add(jsonObject.getString("primary_category"));
                                    }

                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                    for (int i = 0; i < business_units.length(); i++) {

                        JSONObject jsonObject = business_units.getJSONObject(i);
                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("business_unit"))) {
                            product_bu_list.add(jsonObject.getString("business_unit"));
                        }

                    }

                    for (int i = 0; i < sub_categories.length(); i++) {

                        JSONObject jsonObject = sub_categories.getJSONObject(i);

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("sub_category"))) {
                            product_brand_list.add(jsonObject.getString("sub_category"));
                        }

                    }

                    for (int i = 0; i < business_categories.length(); i++) {

                        JSONObject jsonObject = business_categories.getJSONObject(i);

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(jsonObject.getString("business_category"))) {
                            product_division_list.add(jsonObject.getString("business_category"));
                        }

                    }

                    Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            adapter_business_unit = new ArrayAdapter<String>(Scheme_Filter_Activity.this, R.layout.spinner_item, product_cate_list);
                            adapter_business_unit.setDropDownViewResource(R.layout.spinner_item);
                            bu_spinner.setAdapter(adapter_business_unit);

                            if (!Global_Data.target_product_cate_value.equalsIgnoreCase("")) {
                                int spinnerPosition4 = adapter_business_unit.getPosition(Global_Data.target_product_cate_value);
                                bu_spinner.setSelection(spinnerPosition4);
                            }

                            Global_Data.target_product_cate_value = "";



                            adapter_business_unitn = new ArrayAdapter<String>(Scheme_Filter_Activity.this, R.layout.spinner_item, product_bu_list);
                            adapter_business_unitn.setDropDownViewResource(R.layout.spinner_item);
                            bu_spinnernew.setAdapter(adapter_business_unitn);

                            if (!Global_Data.target_product_Bu_value.equalsIgnoreCase("")) {
                                int spinnerPosition4 = adapter_business_unitn.getPosition(Global_Data.target_product_Bu_value);
                                bu_spinnernew.setSelection(spinnerPosition4);
                            }

                            Global_Data.target_product_Bu_value = "";

                            adapter_business_division = new ArrayAdapter<String>(Scheme_Filter_Activity.this, R.layout.spinner_item, product_division_list);
                            adapter_business_division.setDropDownViewResource(R.layout.spinner_item);
                            division_spinnern.setAdapter(adapter_business_division);

                            if (!Global_Data.target_product_division_value.equalsIgnoreCase("")) {
                                int spinnerPosition4 = adapter_business_division.getPosition(Global_Data.target_product_division_value);
                                division_spinnern.setSelection(spinnerPosition4);
                            }

                            Global_Data.target_product_division_value = "";

                            adapter_business_brand = new ArrayAdapter<String>(Scheme_Filter_Activity.this, R.layout.spinner_item, product_brand_list);
                            adapter_business_brand.setDropDownViewResource(R.layout.spinner_item);
                            brand_spinnern.setAdapter(adapter_business_brand);

                            if (!Global_Data.target_product_brand_value.equalsIgnoreCase("")) {
                                int spinnerPosition4 = adapter_business_brand.getPosition(Global_Data.target_product_division_value);
                                brand_spinnern.setSelection(spinnerPosition4);
                            }

                            Global_Data.target_product_brand_value = "";


                            dialog.dismiss();
                        }
                    });

                    Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            dialog.dismiss();
                        }
                    });
                    //	dialog.dismiss();

                    //finish();

                    // }


                    // }

                    // output.setText(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();


                Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        Intent m = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(m);

                        finish();
                    }
                });

            }


            Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Scheme_Filter_Activity.this.runOnUiThread(new Runnable() {
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

    @Override
    protected void onResume() {
        super.onResume();

        if (!Global_Data.close_click_flag.equalsIgnoreCase("")) {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
            btn_close.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            btn_Ongoing.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else if (!Global_Data.Ongoing_click_flag.equalsIgnoreCase("")) {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
            btn_close.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            btn_Ongoing.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }

        if (!Global_Data.Quantity_click_flag.equalsIgnoreCase("")) {

            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
            btn_Quantity.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
            btn_Value.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else if (!Global_Data.Value_click_flag.equalsIgnoreCase("")) {
            Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.right);
            btn_Quantity.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            btn_Value.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
        }


        if (!Global_Data.year_value.equalsIgnoreCase("")) {
            int spinnerPosition = adapter_year.getPosition(Global_Data.year_value);
            target_year.setSelection(spinnerPosition);
        }

        if (!Global_Data.monthly_value.equalsIgnoreCase("")) {
            int spinnerPosition = adapter_month.getPosition(Global_Data.monthly_value);
            target_month.setSelection(spinnerPosition);
        }


        if (!Global_Data.quartly_value.equalsIgnoreCase("")) {
            int spinnerPosition = adapter_quarter.getPosition(Global_Data.quartly_value);
            target_quarter.setSelection(spinnerPosition);
        }


        if (!Global_Data.halfyear_value.equalsIgnoreCase("")) {
            int spinnerPosition1 = adapter_half_year.getPosition(Global_Data.halfyear_value);
            target_half_year.setSelection(spinnerPosition1);
        }


        if (!Global_Data.scheme_from_date.equalsIgnoreCase("")) {
            scheme_filter_date1.setText(Global_Data.scheme_from_date);
        }


        if (!Global_Data.scheme_to_date.equalsIgnoreCase("")) {
            scheme_filter_date2.setText(Global_Data.scheme_to_date);
        }


        Global_Data.year_value = "";
        Global_Data.halfyear_value = "";
        Global_Data.quartly_value = "";
        Global_Data.monthly_value = "";
        Global_Data.scheme_from_date = "";
        Global_Data.scheme_to_date = "";

    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View view, int arg2, long l) {
        counter++;
        Spinner spinner = (Spinner) arg0;
        if (spinner.getId() == R.id.target_quarter) {

            if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_quarter.getSelectedItem().toString().equalsIgnoreCase("Quarterly")) {
                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);
                //Global_Data.halfyear_value = "";

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);
                Global_Data.monthly_value = "";

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {
                    calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH, monthInt);
                    calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);
                    calendar2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 2);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    String from_day = String.format("%02d", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String from_month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1));


                    scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    scheme_filter_date2.setText(from_day + "-" + from_month + "-" + calendar.get(Calendar.YEAR));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);


                try {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    //cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                    Date date = cal.getTime();
                    String from_date = format.format(date);

                    Calendar cal2 = Calendar.getInstance();
                    cal2.clear();
                    cal2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal2.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                    cal2.add(Calendar.MONTH, 11);
                    Date date2 = cal2.getTime();
                    String to_date = format.format(date2);

                    scheme_filter_date1.setText(from_date);
                    scheme_filter_date2.setText(to_date);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_quarter.getSelectedItem().toString().equalsIgnoreCase("Quarterly")) {
                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);
                //Global_Data.halfyear_value = "";

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);
                Global_Data.monthly_value = "";

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {
                    calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH, monthInt);
                    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);
                    calendar2.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 2);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    //String from_day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
                    String from_month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1));
                    String from_day = String.format("%02d", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                    scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    scheme_filter_date2.setText(from_day + "-" + from_month + "-" + calendar.get(Calendar.YEAR));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } else if (spinner.getId() == R.id.target_half_year) {
            if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_half_year.getSelectedItem().toString().equalsIgnoreCase("Half Yearly")) {

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {
                    calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH, monthInt);
                    calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);
                    calendar2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 5);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    String from_day = String.format("%02d", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String from_month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1));

                    scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    scheme_filter_date2.setText(from_day + "-" + from_month + "-" + calendar.get(Calendar.YEAR));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);


                try {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    //cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                    Date date = cal.getTime();
                    String from_date = format.format(date);

                    Calendar cal2 = Calendar.getInstance();
                    cal2.clear();
                    cal2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal2.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                    cal2.add(Calendar.MONTH, 11);
                    Date date2 = cal2.getTime();
                    String to_date = format.format(date2);

                    scheme_filter_date1.setText(from_date);
                    scheme_filter_date2.setText(to_date);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_half_year.getSelectedItem().toString().equalsIgnoreCase("Half Yearly")) {

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {
                    calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH, monthInt);
                    calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);
                    calendar2.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 5);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    // String from_day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
                    String from_month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1));
                    String from_day = String.format("%02d", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

                    scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    scheme_filter_date2.setText(from_day + "-" + from_month + "-" + calendar.get(Calendar.YEAR));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


        } else if (spinner.getId() == R.id.target_year) {
            if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_half_year.getSelectedItem().toString().equalsIgnoreCase("Half Yearly")) {

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {
                    calendar.setTime(new SimpleDateFormat("MMM").parse(target_half_year.getSelectedItem().toString()));
                    int monthInt = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH, monthInt);
                    calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(target_half_year.getSelectedItem().toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);
                    calendar2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 5);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    //String from_day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
                    String from_month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1));
                    String from_day = String.format("%02d", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    scheme_filter_date2.setText(from_day + "-" + from_month + "-" + calendar.get(Calendar.YEAR));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_quarter.getSelectedItem().toString().equalsIgnoreCase("Quarterly")) {

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);

                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {
                    calendar.setTime(new SimpleDateFormat("MMM").parse(target_quarter.getSelectedItem().toString()));
                    int monthInt = calendar.get(Calendar.MONTH);
                    calendar.set(Calendar.MONTH, monthInt);
                    calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(target_quarter.getSelectedItem().toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);
                    calendar2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 2);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    // String from_day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
                    String from_month = String.format("%02d", (calendar.get(Calendar.MONTH) + 1));
                    String from_day = String.format("%02d", calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    scheme_filter_date2.setText(from_day + "-" + from_month + "-" + calendar.get(Calendar.YEAR));


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly") && !target_month.getSelectedItem().toString().equalsIgnoreCase("Monthly")) {
                //int spinnerPosition = adapter_year.getPosition("Yearly");
                //target_year.setSelection(spinnerPosition);

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);


                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(target_month.getSelectedItem().toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);

                    if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {
                        calendar2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    } else {
                        //calendar2.set(Calendar.YEAR,Calendar.YEAR);
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                    }

                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    calendar.add(Calendar.MONTH, 2);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    // String from_day = String.format("%02d", calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));
                    String from_day = String.format("%02d", calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));

                    if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {
                        scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                        scheme_filter_date2.setText(from_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    } else {
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        scheme_filter_date1.setText(to_day + "-" + to_month + "-" + year);
                        scheme_filter_date2.setText(from_day + "-" + to_month + "-" + year);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            } else if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);


                try {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    //cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                    Date date = cal.getTime();
                    String from_date = format.format(date);

                    Calendar cal2 = Calendar.getInstance();
                    cal2.clear();
                    cal2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal2.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                    cal2.add(Calendar.MONTH, 11);
                    Date date2 = cal2.getTime();
                    String to_date = format.format(date2);

                    scheme_filter_date1.setText(from_date);
                    scheme_filter_date2.setText(to_date);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);


                try {

                    scheme_filter_date1.setText("");
                    scheme_filter_date2.setText("");


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        } else if (spinner.getId() == R.id.target_month) {
            if (!target_month.getSelectedItem().toString().equalsIgnoreCase("Monthly")) {
                //int spinnerPosition = adapter_year.getPosition("Yearly");
                //target_year.setSelection(spinnerPosition);

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);


                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                Calendar calendar2 = Calendar.getInstance();
                calendar2.clear();
                try {

                    calendar2.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                    int monthInt2 = calendar2.get(Calendar.MONTH);
                    calendar2.set(Calendar.MONTH, monthInt2);

                    if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {
                        calendar2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    } else {
                        //calendar2.set(Calendar.YEAR,Calendar.YEAR);
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                    }

                    //  calendar2.add(Calendar.MONTH,11);
                    Date date1 = calendar.getTime();

                    System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    // calendar.add(Calendar.MONTH,2);

                    System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                            + "-"
                            + calendar.get(Calendar.DATE)
                            + "-"
                            + calendar.get(Calendar.YEAR));

                    Date date2 = calendar.getTime();

                    //target_to_month_container.setVisibility(View.VISIBLE);

                    String to_day = String.format("%02d", calendar2.get(Calendar.DAY_OF_MONTH));
                    String from_day = String.format("%02d", calendar2.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String to_month = String.format("%02d", (calendar2.get(Calendar.MONTH) + 1));

                    if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {
                        scheme_filter_date1.setText(to_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                        scheme_filter_date2.setText(from_day + "-" + to_month + "-" + calendar2.get(Calendar.YEAR));
                    } else {
                        int year = Calendar.getInstance().get(Calendar.YEAR);
                        scheme_filter_date1.setText(to_day + "-" + to_month + "-" + year);
                        scheme_filter_date2.setText(from_day + "-" + to_month + "-" + year);
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            } else if (!target_year.getSelectedItem().toString().equalsIgnoreCase("Yearly")) {

                int spinnerPosition2 = adapter_half_year.getPosition("Half Yearly");
                target_half_year.setSelection(spinnerPosition2);

                int spinnerPosition3 = adapter_quarter.getPosition("Quarterly");
                target_quarter.setSelection(spinnerPosition3);

                int spinnerPosition4 = adapter_month.getPosition("Monthly");
                target_month.setSelection(spinnerPosition4);


                try {

                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    Calendar cal = Calendar.getInstance();
                    cal.clear();
                    cal.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    //cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
                    Date date = cal.getTime();
                    String from_date = format.format(date);

                    Calendar cal2 = Calendar.getInstance();
                    cal2.clear();
                    cal2.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                    cal2.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
                    cal2.add(Calendar.MONTH, 11);
                    Date date2 = cal2.getTime();
                    String to_date = format.format(date2);

                    scheme_filter_date1.setText(from_date);
                    scheme_filter_date2.setText(to_date);


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        }

    }

    public String StringtointMonth(String input2) {
        switch (input2) {
            case "January":
            case "Jan":
                input2 = "01";
                break;

            case "February":
            case "Feb":
                input2 = "02";
                break;

            case "March":
            case "Mar":
                input2 = "03";
                break;

            case "April":
            case "Apr":
                input2 = "04";
                break;

            case "May":
                input2 = "05";
                break;

            case "June":
            case "Jun":
                input2 = "06";
                break;

            case "July":
            case "Jul":
                input2 = "07";
                break;

            case "August":
            case "Aug":
                input2 = "08";
                break;

            case "September":
            case "Sep":
            case "Sept":
                input2 = "09";
                break;

            case "October":
            case "Oct":
                input2 = "10";
                break;

            case "November":
            case "Nov":
                input2 = "11";
                break;

            case "December":
            case "Dec":
                input2 = "12";
                break;
        }

        return input2;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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

        Global_Data.Quantity_click_flag = "";
        Global_Data.Value_click_flag = "";
        Global_Data.close_click_flag = "";
        Global_Data.Ongoing_click_flag = "";
        Global_Data.year_value = "";
        Global_Data.halfyear_value = "";
        Global_Data.quartly_value = "";
        Global_Data.monthly_value = "";

        Global_Data.scheme_from_date = "";
        Global_Data.scheme_to_date = "";
        Global_Data.target_product_cate_value = "";

        Intent m = new Intent(getApplicationContext(), Order.class);
        startActivity(m);

        finish();
    }

}
