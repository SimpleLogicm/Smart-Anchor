package com.anchor.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.ComplaintAdapter;
import com.anchor.adapter.HorizontalRecyclerViewAdapter;
import com.anchor.helper.MultipartUtility;
import com.anchor.helper.MyPeriodicwork;
import com.anchor.helper.PrefManager;
import com.anchor.interfaces.Customer_S_Interface;
import com.anchor.model.Complaint;
import com.anchor.model.ImageModel;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cpm.simplelogic.helper.ConnectionDetector;

public class ComplaintsActivity extends Activity implements Customer_S_Interface,LocationListener {
    private ProgressDialog dialog;
    String longitude,latitude;
    LocationManager locationManager;
    //    private Button submit;

    int check=0;
    int check2=0;
    int check3=0;
    int check4=0;
    private RecyclerView myRv;
    String TypeText,areaText;
    static String final_response = "";
    String response_result = "";
    String comment="";
    String productItem,complaintTypeItem,areaTypeItem;
    ArrayAdapter<String> dataAdapter,dataAdapter1,dataAdapter3;
    //    private TextView complaint;
    private TextInputEditText text1_value,text2_value,text3_value,text4_value;
    private TextInputLayout text1_value_text,text2_value_text,text3_value_text,text4_value_text;
    AutoCompleteTextView autoCompleteTextView1;
    String text1_value_hint = "";
    String text2_value_hint = "";
    String text3_value_hint = "";
    String text4_value_hint = "";
    private List<Complaint> complaintList = new ArrayList<>();
    private static final String TAG = "ComplaintTesting";
    ComplaintAdapter myAdapter;

    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    ArrayList<String> product_list = new ArrayList<>();
    ArrayList<String> type_list = new ArrayList<>();
    ArrayList<String> area_list = new ArrayList<>();
    Spinner product_spinner,type_spinner,c_spinner_area;
    private PrefManager prefManager;
    FloatingActionButton c_fab_add;
    RecyclerView c_horizontalRecyclerView;
    private HorizontalRecyclerViewAdapter horizontalAdapter;
    private LinearLayoutManager horizontalLayoutManager;
    Boolean B_flag;
    String image_check = "";
    String custServiceType;
    ConnectionDetector cd;
    private String pictureImagePath = "";
    ArrayList<ImageModel> imageModelArrayList = new ArrayList<>();
    int image_count = 0;
    String response_result_image = "";
    Button submit;
    ArrayList<String> list_items = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.complains_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        product_spinner =  findViewById(R.id.c_spinner_search_product);
        type_spinner =  findViewById(R.id.c_spinner_search_type);
        c_spinner_area =  findViewById(R.id.c_spinner_area);
        c_fab_add =  findViewById(R.id.c_fab_add);
        autoCompleteTextView1 = findViewById(R.id.autoCompleteTextView1);

        submit = findViewById(R.id.btnNewComplaintSubmit);
        cd  = new ConnectionDetector(getApplicationContext());
        c_horizontalRecyclerView =  findViewById(R.id.c_horizontalRecyclerView);

        dialog = new ProgressDialog(ComplaintsActivity.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        Global_Data.context = ComplaintsActivity.this;
        try{
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                    MyPeriodicwork.class, 15, TimeUnit.MINUTES
            ).addTag("otpValidator").build();
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("Work",
                    ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);

        }catch(Exception ex) {
            ex.printStackTrace();
        }


        init();
        getLocation();
//        Log.d(TAG, "onCreate: test now has data");
//        myAdapter = new ComplaintAdapter(this, complaintList);
//        myRv.setLayoutManager(new LinearLayoutManager(this));
//        myRv.setAdapter(myAdapter);

        prefManager = new PrefManager(this);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("New "+prefManager.getCustomer_service_type());

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        horizontalAdapter = new HorizontalRecyclerViewAdapter(imageModelArrayList, ComplaintsActivity.this,this);
        horizontalLayoutManager = new LinearLayoutManager(ComplaintsActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalLayoutManager.setReverseLayout(true);
        horizontalLayoutManager.setStackFromEnd(true);
        c_horizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        c_horizontalRecyclerView.setAdapter(horizontalAdapter);

//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
//        }

        ImageView Header_logo = findViewById(R.id.Header_logo);
        TextView mTitleTextView = findViewById(R.id.screenname);
        mTitleTextView.setText(prefManager.getCustomer_service_type());

        TextView todaysTarget = findViewById(R.id.todaysTarget);

        SharedPreferences sp = ComplaintsActivity.this.getSharedPreferences("SimpleLogic", 0);

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

//        try
//        {
//
//            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//            getSupportActionBar().setDisplayShowCustomEnabled(true);
//            getSupportActionBar().setCustomView(R.layout.action_bar);
//            View view =getSupportActionBar().getCustomView();
//
//            view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//            TextView mTitleTextView = (TextView) view.findViewById(R.id.screenname);
//            mTitleTextView.setText(prefManager.getCustomer_service_type());
//
//            TextView todaysTarget = (TextView) view.findViewById(R.id.todaysTarget);
//
//            SharedPreferences sp = ComplaintsActivity.this.getSharedPreferences("SimpleLogic", 0);
//
//            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//                //todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//                todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//            }
//
//            try
//            {
//                int target  = (int) Math.round(sp.getFloat("Target",0));
//                int achieved  = (int) Math.round(sp.getFloat("Achived",0));
//                Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
//                if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
//                {
//                    int age = (int) Math.round(age_float);
//
//                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
//                }else
//                {
//                    int age = (int) Math.round(age_float);
//
//                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
//                }
//
//            }catch(Exception ex){ex.printStackTrace();}
//            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//                todaysTarget.setText("Today's Target Acheived");
//            }
//
//            getActionBar().setHomeButtonEnabled(true);
//            getActionBar().setDisplayHomeAsUpEnabled(true);
//
//        }catch(Exception ex){ex.printStackTrace();}


        if (cd.isConnectingToInternet())
        {
            dialog = new ProgressDialog(ComplaintsActivity.this);
            dialog.setMessage("Please wait....");
            dialog.setTitle("Smart Anchor App");
            dialog.setCancelable(false);
            dialog.show();

            ComplaintTypeResult();
        }
        else
        {
            Toast toast = Toast.makeText(ComplaintsActivity.this,"You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(order_home);
            finish();

        }


        if(prefManager.getCustomer_service_type().equalsIgnoreCase("Marketing Tool"))
        {
            product_list.add("Selects Type Of Request");
            type_list.add("Selects Business Unit");
            area_list.add("Selects Brand");
            comment="Please Enter Claim";
            text1_value_text.setHint("Item Description");
            text2_value_text.setHint("Quantity");
            text2_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            type_spinner.setVisibility(View.VISIBLE);
            c_spinner_area.setVisibility(View.VISIBLE);
            autoCompleteTextView1.setVisibility(View.VISIBLE);
            autoCompleteTextView1.setHint("Item Description");
            text1_value_text.setVisibility(View.GONE);
            text2_value_text.setVisibility(View.VISIBLE);
            text2_value.setImeOptions(EditorInfo.IME_ACTION_DONE);
            text3_value_text.setVisibility(View.GONE);
            text4_value_text.setVisibility(View.GONE);
            c_horizontalRecyclerView.setVisibility(View.GONE);
        }else if(prefManager.getCustomer_service_type().equalsIgnoreCase("Claims"))
        {
            product_list.add("Selects Claim Type");
            type_list.add("Selects Product Category");
            area_list.add("Selects Sub Category");
            comment="Please Enter Claim";
            text1_value_text.setHint("Reference 1 (Invoice No)");
            text2_value_text.setHint("Reference 2 (Scheme)");
            text3_value_text.setHint("Reference 3");
            type_spinner.setVisibility(View.VISIBLE);
            c_spinner_area.setVisibility(View.VISIBLE);
            autoCompleteTextView1.setVisibility(View.GONE);
            text1_value_text.setVisibility(View.VISIBLE);
            text2_value_text.setVisibility(View.VISIBLE);
            text3_value_text.setVisibility(View.VISIBLE);
            text3_value.setImeOptions(EditorInfo.IME_ACTION_DONE);
            text4_value_text.setVisibility(View.GONE);
            c_horizontalRecyclerView.setVisibility(View.GONE);
        }else if(prefManager.getCustomer_service_type().equalsIgnoreCase("Logistics"))
        {
            product_list.add("Selects Type of Logistics");
            type_list.add("Selects Product Category");
            area_list.add("Selects Sub Category");
            comment="Please Enter Claim";
            text1_value_text.setHint("Item Description");
            text2_value_text.setHint("Invoice No");
            text3_value_text.setHint("Quantity");
            text3_value.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            text4_value_text.setHint("Remarks");
            autoCompleteTextView1.setHint("Item Description");
            type_spinner.setVisibility(View.VISIBLE);
            c_spinner_area.setVisibility(View.VISIBLE);
            autoCompleteTextView1.setVisibility(View.VISIBLE);
            text1_value_text.setVisibility(View.GONE);
            text2_value_text.setVisibility(View.VISIBLE);
            text3_value_text.setVisibility(View.VISIBLE);
            text4_value_text.setVisibility(View.VISIBLE);
            text4_value.setImeOptions(EditorInfo.IME_ACTION_DONE);
            c_horizontalRecyclerView.setVisibility(View.GONE);
        }else if(prefManager.getCustomer_service_type().equalsIgnoreCase("Feedbacks"))
        {
            product_list.add("Selects Type Of Feedback");
           // type_list.add("Selects Business Unit");
           // area_list.add("Selects Brand");
            comment="Please Enter Claim";
            text1_value_text.setHint("Description");
            type_spinner.setVisibility(View.GONE);
            c_spinner_area.setVisibility(View.GONE);
            text1_value_text.setVisibility(View.VISIBLE);
            text1_value.setImeOptions(EditorInfo.IME_ACTION_DONE);
            autoCompleteTextView1.setVisibility(View.GONE);
            text2_value_text.setVisibility(View.GONE);
            text3_value_text.setVisibility(View.GONE);
            text4_value_text.setVisibility(View.GONE);
            c_horizontalRecyclerView.setVisibility(View.GONE);
        }

        autoCompleteTextView1.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (autoCompleteTextView1.getRight() - autoCompleteTextView1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = ComplaintsActivity.this.getCurrentFocus();
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

//        type_list.add("20291 - 250*250 MM FLMOUNBOX WITH 3 COMP");
//        type_list.add("20358S - BELL INDICATOR");
//        type_list.add("20392 - ROMA SPMS75 A");
//        type_list.add("20405 - 25A MOTOR STARTER SWITCH SP");
//        type_list.add("20405MB - 25A STARTER SWITCH - MATT BLACK");


//        area_list.add("Product Quality - Packaging issue, broken piece");
////        area_list.add("Delivery - Delay in delivery");
////        area_list.add("Schemes - Not credited, Not clear");

        // Creating ArrayAdapter using the string array and default spinner layout
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, product_list);
        // Specify layout to be used when list of choices appears
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        product_spinner.setAdapter(dataAdapter);
       // product_spinner.setOnItemSelectedListener(this);

        dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, type_list);
        // Specify layout to be used when list of choices appears
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        type_spinner.setAdapter(dataAdapter1);
       // type_spinner.setOnItemSelectedListener(this);

        dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area_list);
        // Specify layout to be used when list of choices appears
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        c_spinner_area.setAdapter(dataAdapter3);
      //  c_spinner_area.setOnItemSelectedListener(this);


        //it will load data in the beginning of the activity
        //loadData();

        //for testing
//        deleteData();

        c_fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                requestStoragePermission();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String comment=text1_value.getText().toString().trim();
                String comment2=text2_value.getText().toString().trim();
                String comment3=text3_value.getText().toString().trim();

                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("feedbacks"))
                {
                    if(product_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Type Of Feedback"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Type Of Feedback", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(comment))
                    {

                        Toast.makeText(ComplaintsActivity.this, "Please Enter Description", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        requestGPSPermission1();

                    }
                }
                else
                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("claims"))
                {
                    if(product_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Claim Type"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Claim Type", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Product Category"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Product Category", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(c_spinner_area.getSelectedItem().toString().equalsIgnoreCase("Selects Sub Category"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Sub Category", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(comment) && !Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(comment2) && !Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(comment3))
                    {

                        Toast.makeText(ComplaintsActivity.this, "Please Enter Reference", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        requestGPSPermission1();
                    }
                }
                else
                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("logistics"))
                {
                    if(product_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Type of Logistics"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Type of Logistics", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Product Category"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Product Category", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(c_spinner_area.getSelectedItem().toString().equalsIgnoreCase("Selects Sub Category"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Sub Category", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(autoCompleteTextView1.getText().toString().trim()))
                    {

                        Toast.makeText(ComplaintsActivity.this, "Enter Item Description", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        requestGPSPermission1();
                    }
                }
                else
                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Marketing Tool"))
                {
                    if(product_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Type Of Request"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Type Of Request", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Selects Business Unit"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Business Unit", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(c_spinner_area.getSelectedItem().toString().equalsIgnoreCase("Selects Brand"))
                    {
                        Toast.makeText(ComplaintsActivity.this, "Selects Brand", Toast.LENGTH_SHORT).show();
                    }
                    else
                    if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(autoCompleteTextView1.getText().toString().trim()))
                    {

                        Toast.makeText(ComplaintsActivity.this, "Enter Item Description", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        requestGPSPermission1();
                    }
                }


            }
        });


        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {

                check2=check2+1;
                if(check2>2)
                {
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Selects Product Category")) {

                        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
                        {
                            area_list.clear();
                            area_list.add("Selects Brand");
                        }
                        else
                        {
                            area_list.clear();
                            area_list.add("Selects Sub Category");
                        }

                        dataAdapter3 = new ArrayAdapter<String>(ComplaintsActivity.this, android.R.layout.simple_spinner_item, area_list);
                        // Specify layout to be used when list of choices appears
                        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // Applying the adapter to our spinner
                        c_spinner_area.setAdapter(dataAdapter3);



                        Toast toast = Toast.makeText(ComplaintsActivity.this, "Selects Product Category", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                    else
                    {

                        if (cd.isConnectingToInternet())
                        {
                            dialog = new ProgressDialog(ComplaintsActivity.this);
                            dialog.setMessage("Please wait....");
                            dialog.setTitle("Smart Anchor App");
                            dialog.setCancelable(false);
                            dialog.show();

                            if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
                            {
                                area_list.clear();
                                area_list.add("Selects Brand");
                            }
                            else
                            {
                                area_list.clear();
                                area_list.add("Selects Sub Category");
                            }

                            sub_cate_Data(parent.getItemAtPosition(pos).toString().trim());
                        }
                        else
                        {

                            Toast toast = Toast.makeText(ComplaintsActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        c_spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {

                if(!prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Claims") )
                {
                    check3=check3+1;
                    if(check3>2)
                    {

                        if (parent.getItemAtPosition(pos).toString()
                                .equalsIgnoreCase("Selects Sub Category")) {

                            autoCompleteTextView1.setText("");
                            list_items.clear();


                            Toast toast = Toast.makeText(ComplaintsActivity.this, "Selects Sub Category", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                        else
                        {

                            if (cd.isConnectingToInternet())
                            {
                                autoCompleteTextView1.setText("");
                                list_items.clear();

                                getproducts();
                            }
                            else
                            {

                                Toast toast = Toast.makeText(ComplaintsActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }

                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void init() {

        myRv =  findViewById(R.id.rvComplaint);
//        complaint = (TextView) findViewById(R.id.tvCardComplaint);
        text1_value =  findViewById(R.id.text1_value);
        text2_value =  findViewById(R.id.text2_value);
        text3_value =  findViewById(R.id.text3_value);
        text4_value =  findViewById(R.id.text4_value);

        text1_value_text =  findViewById(R.id.text1_value_text);
        text2_value_text =  findViewById(R.id.text2_value_text);
        text3_value_text =  findViewById(R.id.text3_value_text);
        text4_value_text =  findViewById(R.id.text4_value_text);

        //TextInputLayout textInputLayout = (TextInputLayout)findViewById(R.id.text_input_layout);
        //etNewComplaint.setHint("Hello");


        prefManager = new PrefManager(this);
        custServiceType=prefManager.getCustomer_service_type();


    }





    @Override
    protected void onDestroy() {
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

        Intent i = new Intent(ComplaintsActivity.this, Previous_Customer_Services.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

    //@Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if(adapterView.getId() == R.id.c_spinner_search_product)
//        {
//            productItem = adapterView.getItemAtPosition(i).toString();
//            switch (productItem) {
//                case "Selects Product":
//                   // Toast.makeText(this, ""+complaintTypeItem, Toast.LENGTH_SHORT).show();
//                    break;
//                case "Samsung":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " in Korean means 3 Stars!", Toast.LENGTH_SHORT).show();
//                    break;
//                case "Foxconn":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " is world's largest contract electronics manufacturer", Toast.LENGTH_SHORT).show();
//                    break;
//                case "Apple":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " was founded in 1976!", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//        else
//        if(adapterView.getId() == R.id.c_spinner_search_type)
//        {
//            complaintTypeItem = adapterView.getItemAtPosition(i).toString();
//            //Toast.makeText(this, ""+productItem, Toast.LENGTH_SHORT).show();
//            switch (complaintTypeItem) {
//                case "Selects Complaint Type":
//                    //Toast.makeText(this, ""+productItem, Toast.LENGTH_SHORT).show();
//                    break;
//                case "Type 1":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " in Korean means 3 Stars!", Toast.LENGTH_SHORT).show();
//                    break;
//                case "Type 2":
//                   // Toast.makeText(getApplicationContext(), selectedItem + " is world's largest contract electronics manufacturer", Toast.LENGTH_SHORT).show();
//                    break;
//                case "Type 3":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " was founded in 1976!", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//        else
//        if(adapterView.getId() == R.id.c_spinner_area)
//        {
//            areaTypeItem = adapterView.getItemAtPosition(i).toString();
//            switch (areaTypeItem) {
//                case "Selects Area of Complaint":
//                    //Toast.makeText(this, ""+areaTypeItem, Toast.LENGTH_SHORT).show();
//                    break;
//                case "Type 1":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " in Korean means 3 Stars!", Toast.LENGTH_SHORT).show();
//                    break;
//                case "Type 2":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " is world's largest contract electronics manufacturer", Toast.LENGTH_SHORT).show();
//                    break;
//                case "Type 3":
//                    //Toast.makeText(getApplicationContext(), selectedItem + " was founded in 1976!", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//
//    }

//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

    public void fillWithData(String imag_url) {

        ImageModel imageModel0 = new ImageModel();
        imageModel0.setId(System.currentTimeMillis());
        imageModel0.setImageName("Battle");
        imageModel0.setImagePath(imag_url);
        imageModelArrayList.add(imageModel0);

        c_horizontalRecyclerView.getLayoutManager().scrollToPosition(0);

        horizontalAdapter.notifyDataSetChanged();
    }

    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            B_flag = isDeviceSupportCamera();

                            if (B_flag == true) {

                                if(image_count <1)
                                {
                                    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ComplaintsActivity.this);

                                    builder.setTitle("Add Photo!");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {

                                        @Override

                                        public void onClick(DialogInterface dialog, int item) {

                                            if (options[item].equals("Take Photo"))

                                            {
                                                image_check = "photo";

                                                File photoFile = null;
                                                try {
                                                    photoFile = createImageFile();
                                                } catch (IOException ex) {
                                                    // Error occurred while creating the File
                                                    Log.i("Image TAG", "IOException");
                                                    pictureImagePath = "";
                                                }
                                                if (photoFile != null) {
                                                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                    Uri photoURI = FileProvider.getUriForFile(ComplaintsActivity.this,
                                                            BuildConfig.APPLICATION_ID + ".provider",
                                                            photoFile);
                                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);



                                                  //  cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                                    startActivityForResult(cameraIntent, 1);
                                                }
                                            } else if (options[item].equals("Choose from Gallery"))

                                            {

                                                image_check = "gallery";
                                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                startActivityForResult(intent, 2);


                                            } else if (options[item].equals("Cancel")) {

                                                dialog.dismiss();

                                            }

                                        }

                                    });

                                    builder.show();
                                }
                                else
                                {
                                    Toast.makeText(ComplaintsActivity.this, "You can take 1 picture in one record", Toast.LENGTH_SHORT).show();
                                }





                            } else {
                                Toast.makeText(getApplicationContext(), "no camera on this device", Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintsActivity.this);
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

    private boolean isDeviceSupportCamera() {
        // this device has a camera
// no camera on this device
        return getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "exp";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "D_CUstomer_Service");

        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        pictureImagePath = "file:" + image.getAbsolutePath();
        // mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1 && resultCode == RESULT_OK) {
            ++image_count;
            //new Expenses.LongOperation().execute();

//			try {
//
//				//dbvoc.updateORDER_order_image(pictureImagePath, Global_Data.GLObalOrder_id);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
            c_horizontalRecyclerView.setVisibility(View.VISIBLE);
            fillWithData(pictureImagePath);

        } else if (requestCode == 2 && resultCode == RESULT_OK) {

            try {
                ++image_count;
                c_horizontalRecyclerView.setVisibility(View.VISIBLE);
                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                pictureImagePath = "file:" + c.getString(columnIndex);

                fillWithData(pictureImagePath);

                c.close();

                //new Expenses.LongOperation().execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void image_Count_Flag(int value) {
        image_count = value;
    }

    @Override
    public int image_Count_n() {
        return image_count;
    }

    @Override
    public void onLocationChanged(Location location) {
        //locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
          latitude=String.valueOf(location.getLatitude());
          longitude=String.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
                  //  addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        }catch(Exception e)
        {

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(ComplaintsActivity.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
    }

    public  class doFileUpload extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... response) {

//            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            longitude = String.valueOf(location.getLongitude());
//            latitude = String.valueOf(location.getLatitude());
            //Toast.makeText(ComplaintsActivity.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();
            String domain = getResources().getString(R.string.service_domain) + "customer_service_complaints/create_complaint";
            Log.i("volley", "domain: " + domain);

            //String urlString = domain + "sub_dealers";
            try {
                Uri uri1 = Uri.parse(pictureImagePath);
                final File file1 = new File(uri1.getPath());
                String charset = "UTF-8";
                //File uploadFile1 = new File("/sdcard/myvideo.mp4");

                MultipartUtility multipart = new MultipartUtility(domain, charset);


                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("feedbacks"))
                {
                    multipart.addFormField("type",prefManager.getCustomer_service_type().toLowerCase());
                    multipart.addFormField("email", Global_Data.CUSTOMER_EMAIL);
                    multipart.addFormField("user_email", Global_Data.GLOvel_USER_EMAIL);
                    multipart.addFormField("type_complaint", product_spinner.getSelectedItem().toString());
                   // multipart.addFormField("area_complaint",areaTypeItem);
                  //  multipart.addFormField("product", productItem);
                    multipart.addFormField("text", text1_value.getText().toString());
                    multipart.addFormField("latitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(latitude)));
                    multipart.addFormField("longitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(longitude)));
                }
                else
                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("claims"))
                {
                    multipart.addFormField("type",prefManager.getCustomer_service_type().toLowerCase());
                    multipart.addFormField("email", Global_Data.CUSTOMER_EMAIL);
                    multipart.addFormField("user_email", Global_Data.GLOvel_USER_EMAIL);
                    multipart.addFormField("type_complaint", product_spinner.getSelectedItem().toString());
                    multipart.addFormField("product_category", type_spinner.getSelectedItem().toString());
                    multipart.addFormField("sub_category", c_spinner_area.getSelectedItem().toString());
                    // multipart.addFormField("area_complaint",areaTypeItem);
                    //  multipart.addFormField("product", productItem);
                    multipart.addFormField("invoice_no", text1_value.getText().toString());
                    multipart.addFormField("scheme", text2_value.getText().toString());
                    multipart.addFormField("reference", text3_value.getText().toString());
                    multipart.addFormField("latitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(latitude)));
                    multipart.addFormField("longitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(longitude)));
                }

                else
                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("logistics"))
                {
                    multipart.addFormField("type",prefManager.getCustomer_service_type().toLowerCase());
                    multipart.addFormField("email", Global_Data.CUSTOMER_EMAIL);
                    multipart.addFormField("user_email", Global_Data.GLOvel_USER_EMAIL);
                    multipart.addFormField("type_complaint", product_spinner.getSelectedItem().toString());
                    multipart.addFormField("product_category", type_spinner.getSelectedItem().toString());
                    multipart.addFormField("sub_category", c_spinner_area.getSelectedItem().toString());
                    // multipart.addFormField("area_complaint",areaTypeItem);
                    //  multipart.addFormField("product", productItem);
                    multipart.addFormField("item_description", autoCompleteTextView1.getText().toString().trim());
                    multipart.addFormField("invoice_no", text2_value.getText().toString());
                    multipart.addFormField("quantity", text3_value.getText().toString());
                    multipart.addFormField("remarks", text4_value.getText().toString());
                    multipart.addFormField("latitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(latitude)));
                    multipart.addFormField("longitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(longitude)));
                }
                else
                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
                {
                    multipart.addFormField("type","marketing_tool");
                    multipart.addFormField("email", Global_Data.CUSTOMER_EMAIL);
                    multipart.addFormField("user_email", Global_Data.GLOvel_USER_EMAIL);
                    multipart.addFormField("type_complaint", product_spinner.getSelectedItem().toString());
                    multipart.addFormField("business_unit", type_spinner.getSelectedItem().toString());
                    multipart.addFormField("brand", c_spinner_area.getSelectedItem().toString());
                    // multipart.addFormField("area_complaint",areaTypeItem);
                    //  multipart.addFormField("product", productItem);
                    multipart.addFormField("item_description", autoCompleteTextView1.getText().toString().trim());
                    multipart.addFormField("quantity", text2_value.getText().toString());
                    multipart.addFormField("latitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(latitude)));
                    multipart.addFormField("longitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(longitude)));
                }



                // multipart.addFilePart("picture1", file1);

                if (!pictureImagePath.equalsIgnoreCase("")) {
                    multipart.addFilePart("picture1", file1);
                }

                List<String> response1 = multipart.finish();

                //Log.v("complaint_resp", "multipart:"+multipart.toString());



                for (String line : response1) {
                    Log.v("rht", "Line : " + line);
                    response_result_image = line;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {

                                JSONObject obj = new JSONObject(response_result_image);
//                                String ss=obj.getString("message");
//                                System.out.println(""+ss);

                                Toast.makeText(ComplaintsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                                if (obj.getString("message").equalsIgnoreCase("Images uploaded sucessfully.")) {
                                    if (file1.exists()) {
                                        if (file1.delete()) {
                                            //System.out.println("file Deleted :" + outletsignboard_mCurrentPhotoPath);
                                        } else {
                                           // System.out.println("file not Deleted :" + outletsignboard_mCurrentPhotoPath);
                                        }
                                    }
                                    //outletsignboard_mCurrentPhotoPath = "";
                                    hidePDialog();
                                    Intent a = new Intent(ComplaintsActivity.this, MainActivity.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(a);
                                    finish();
                                }

                                Log.d("My App", obj.toString());

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response_result_image + "\"");
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(response_result_image);
                                    Toast.makeText(ComplaintsActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hidePDialog();
                                Intent a = new Intent(ComplaintsActivity.this, MainActivity.class);
                                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(a);
                                finish();
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {

                        Intent a = new Intent(ComplaintsActivity.this, MainActivity.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(a);
                        finish();
                        hidePDialog();
                    }
                });
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    Intent a = new Intent(ComplaintsActivity.this, CustomerServicesActivity.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                    finish();
                    hidePDialog();
                }
            });

        }

        @Override
        protected void onPreExecute() {




            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public void ComplaintTypeResult()
    {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        //Log.i("volley", "email: " + Global_Data.CityName);

        StringRequest jsObjRequest = null;
        String service_url = "";
        //String email="payal@gmail.com";
        String email=Global_Data.CUSTOMER_EMAIL;

        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Marketing Tool"))
        {
            service_url = domain + "customer_service_complaints/list_of_complaint_type?email=" + Global_Data.CUSTOMER_EMAIL+"&type="+"marketing_tool";
        }
        else
        {
            service_url = domain + "customer_service_complaints/list_of_complaint_type?email=" + Global_Data.CUSTOMER_EMAIL+"&type="+prefManager.getCustomer_service_type().toLowerCase();
        }


        //service_url = "http://150.242.140.105:8002/api/v1/users/employee_list";

        Log.i("volley", "service_url: " + service_url);

        jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                new ComplaintTypeTask().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent intent = new Intent(ComplaintsActivity.this, MainActivity.class);
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
        int socketTimeout = 300000;//30 seconds - change to what you want
        //RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        RetryPolicy policy = new DefaultRetryPolicy(0,-1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }


    private class ComplaintTypeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                } else {
                    response_result = "data";
                }  //10500+1200+1500+4000+500+300+2500+1000+2000

                if (response_result.equalsIgnoreCase("Customer not found.")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    ComplaintsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                           // upcoming_recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Toast toast = Toast.makeText(ComplaintsActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(ComplaintsActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else
                if(response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    ComplaintsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            Toast toast = Toast.makeText(ComplaintsActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(ComplaintsActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                else {

                    JSONArray type_complaints = response.getJSONArray("type_complaints");
                    JSONArray primary_category = response.getJSONArray("product_category");
                   // JSONArray business_unit = response.getJSONArray("business_unit");

                    Log.d("volley", "users" + type_complaints.toString());
                   // Log.d("volley", "users" + area_complaints.toString());
                  //  Log.d("volley", "users" + products.toString());

                    if (primary_category.length() <= 0) {

                        ComplaintsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                               // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(ComplaintsActivity.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < primary_category.length(); i++)
                        {
                            JSONObject jsonObject = primary_category.getJSONObject(i);

                            if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
                            {
                                type_list.add(jsonObject.getString("business_unit"));
                            }
                            else
                            {
                                type_list.add(jsonObject.getString("primary_category"));
                            }


                        }
                        ComplaintsActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run() {
                                dialog.dismiss();
                                // Creating ArrayAdapter using the string array and default spinner layout
                                dataAdapter1 = new ArrayAdapter<String>(ComplaintsActivity.this, android.R.layout.simple_spinner_item, type_list);
                                // Specify layout to be used when list of choices appears
                                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                type_spinner.setAdapter(dataAdapter1);
                                //product_spinner.setOnItemSelectedListener(ComplaintsActivity.this);
                              }
                        });
                    }

                    if (type_complaints.length() <= 0) {

                        ComplaintsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                // upcoming_recyclerView.hideShimmerAdapter();
                                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("feedbacks"))
                                {
                                    Toast toast = Toast.makeText(ComplaintsActivity.this, "Feedback Type Not Exist.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                                else
                                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("claims"))
                                {
                                    Toast toast = Toast.makeText(ComplaintsActivity.this, "Claims Type Not Exist.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }

                                else
                                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("logistics"))
                                {
                                    Toast toast = Toast.makeText(ComplaintsActivity.this, "Logistic Type Not Exist.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                                else
                                if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
                                {
                                    Toast toast = Toast.makeText(ComplaintsActivity.this, "Marketing Tool Type Not Exist.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }

                            }
                        });
                    } else {

                        for (int i = 0; i < type_complaints.length(); i++)
                        {
                            JSONObject jsonObject = type_complaints.getJSONObject(i);
                            product_list.add(jsonObject.getString("name"));


                        }
                        ComplaintsActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run() {
                                dataAdapter = new ArrayAdapter<String>(ComplaintsActivity.this, android.R.layout.simple_spinner_item, product_list);
                                // Specify layout to be used when list of choices appears
                                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                product_spinner.setAdapter(dataAdapter);

                            }
                        });
                    }

//                    if (area_complaints.length() <= 0) {
//
//                        ComplaintsActivity.this.runOnUiThread(new Runnable() {
//                            public void run() {
//                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(ComplaintsActivity.this, "Area Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                            }
//                        });
//                    } else {
//
//                        for (int i = 0; i < area_complaints.length(); i++)
//                        {
//                            JSONObject jsonObject = area_complaints.getJSONObject(i);
//                            area_list.add(jsonObject.getString("name"));
//
//
//                        }
//                        ComplaintsActivity.this.runOnUiThread(new Runnable()
//                        {
//                            public void run() {
//                                dataAdapter3 = new ArrayAdapter<String>(ComplaintsActivity.this, android.R.layout.simple_spinner_item, area_list);
//                                // Specify layout to be used when list of choices appears
//                                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                // Applying the adapter to our spinner
//                                c_spinner_area.setAdapter(dataAdapter3);
//                                //c_spinner_area.setOnItemSelectedListener(ComplaintsActivity.this);
//
//                            }
//                        });
//                    }

                    ComplaintsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                           // upcoming_recyclerView.hideShimmerAdapter();
                        }
                    });
                    //	dialog.dismiss();
                    //finish();
                }

                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(ComplaintsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                ComplaintsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        //upcoming_recyclerView.hideShimmerAdapter();
                    }
                });
            }

            ComplaintsActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    //upcoming_recyclerView.hideShimmerAdapter();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            ComplaintsActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
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

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    public void sub_cate_Data(String primary_category)
    {
        String domain = getResources().getString(R.string.service_domain);
        StringRequest jsObjRequest = null;
        String Service_url = "";

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " +  Global_Data.CUSTOMER_EMAIL);
        try {
            if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Marketing Tool"))
            {
                 Service_url = domain + "customer_service_complaints/populate_sub_category?email=" +  Global_Data.CUSTOMER_EMAIL+"&product_category=" + URLEncoder.encode(primary_category, "UTF-8")+"&type=" + "marketing_tool";
            }
            else
            {
                 Service_url = domain + "customer_service_complaints/populate_sub_category?email=" +  Global_Data.CUSTOMER_EMAIL+"&product_category=" + URLEncoder.encode(primary_category, "UTF-8")+"&type=" + URLEncoder.encode(prefManager.getCustomer_service_type().toLowerCase(), "UTF-8");
            }


            Log.i("user list url", "user list url " + Service_url);

            jsObjRequest = new StringRequest(Service_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("volley", "response: " + response);
                    final_response = response;

                    new ComplaintsActivity.getsubCate().execute(response);

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
                            dialog.dismiss();
                            // finish();
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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

    private class getsubCate extends AsyncTask<String, Void, String> {
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

                    ComplaintsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(ComplaintsActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                }
                else
                if(response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    ComplaintsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(ComplaintsActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });



                }
                else {

                    //dbvoc.getDeleteTable("delivery_products");

                    JSONArray sub_categories = response.getJSONArray("sub_categories");
                    Log.i("volley", "response sub_categories Length: " + sub_categories.length());
                    Log.d("volley", "sub_categories" + sub_categories.toString());


                    //
                    if (sub_categories.length() <= 0) {

                        ComplaintsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                                //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(ComplaintsActivity.this, "Sub Categories doesn't exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < sub_categories.length(); i++) {

                            try {
                                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(sub_categories.getString(i))){
                                    {
                                        JSONObject jsonObject = sub_categories.getJSONObject(i);
                                        area_list.add(jsonObject.getString("sub_category"));

                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        ComplaintsActivity.this.runOnUiThread(new Runnable()
                        {
                            public void run() {

                                dataAdapter3 = new ArrayAdapter<String>(ComplaintsActivity.this, android.R.layout.simple_spinner_item, area_list);
                                // Specify layout to be used when list of choices appears
                                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_area.setAdapter(dataAdapter3);
                                dialog.dismiss();
                            }
                        });

                        ComplaintsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    // }

                    // output.setText(data);
                } }catch (JSONException e) {
                e.printStackTrace();

                ComplaintsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });
            }

            ComplaintsActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            ComplaintsActivity.this.runOnUiThread(new Runnable() {
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

    public void getproducts() {

        dialog.setMessage("Please wait....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();

        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + Global_Data.CUSTOMER_EMAIL);

        String service_url = "";

        try {

            if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
            {
                service_url = domain + "customer_service_complaints/populate_item_description?email=" +
                        URLEncoder.encode(Global_Data.CUSTOMER_EMAIL, "UTF-8") + "&product_category=" + URLEncoder.encode(type_spinner.getSelectedItem().toString(), "UTF-8") + "&sub_category=" + URLEncoder.encode(c_spinner_area.getSelectedItem().toString(), "UTF-8")+ "&type=" + URLEncoder.encode("marketing_tool", "UTF-8");
            }
            else
            {
                service_url = domain + "customer_service_complaints/populate_item_description?email=" +
                        URLEncoder.encode(Global_Data.CUSTOMER_EMAIL, "UTF-8") + "&product_category=" + URLEncoder.encode(type_spinner.getSelectedItem().toString(), "UTF-8") + "&sub_category=" + URLEncoder.encode(c_spinner_area.getSelectedItem().toString(), "UTF-8") + "&type=" + URLEncoder.encode(prefManager.getCustomer_service_type().toLowerCase(), "UTF-8");
            }



            Log.i("volley", "service_url: " + service_url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = null;

        stringRequest = new StringRequest(service_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //showJSON(response);
                        // Log.d("jV", "JV" + response);
                        Log.d("jV", "JV length" + response.length());
                        // JSONObject person = (JSONObject) (response);
                        try {
                            JSONObject json = new JSONObject(new JSONTokener(response));
                            try {
                                String response_result = "";
                                if (json.has("message")) {
                                    response_result = json.getString("message");
                                } else {
                                    response_result = "data";
                                }


                                if (response_result.equalsIgnoreCase("No Data Found")) {

                                    dialog.hide();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                } else if (response_result.equalsIgnoreCase("User not registered with this device")) {

                                    dialog.hide();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();



                                } else if (response_result.equalsIgnoreCase("Device not registered")) {
                                    dialog.hide();
                                    // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();



                                } else {


                                    JSONArray item_description = json.getJSONArray("item_description");

                                    Log.i("volley", "response reg products Length: " + item_description.length());

                                    Log.d("users", "products" + item_description.toString());



                                    if (item_description.length() > 0) {
                                        for (int i = 0; i < item_description.length(); i++) {
                                            JSONObject object = item_description.getJSONObject(i);
                                            list_items.add(object.getString("name"));

                                            }

                                        // Global_Data.catalogue_m = catalogue_m;
                                        dialog.hide();

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComplaintsActivity.this,
                                                android.R.layout.simple_spinner_dropdown_item,
                                                list_items);
                                        autoCompleteTextView1.setThreshold(1);// will start working from
                                        // first character
                                        autoCompleteTextView1.setAdapter(adapter);// setting the adapter
                                        // data into the
                                        // AutoCompleteTextView
                                        autoCompleteTextView1.setTextColor(Color.BLACK);




                                    } else {
                                        dialog.hide();
                                        // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                        Toast toast = Toast.makeText(getApplicationContext(), "Product Not Found.", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                    }

                                    //finish();

                                }

                                //  finish();
                                // }

                                // output.setText(data);
                            } catch (JSONException e) {
                                e.printStackTrace();


                                Toast toast = Toast.makeText(ComplaintsActivity.this,
                                        "Service Error",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                                dialog.hide();
                            }
                            dialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  finish();
                            dialog.dismiss();
                        }
                        dialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(Image_Gellary.this,
//                                    "Network Error",
//                                    Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(ComplaintsActivity.this,
                                    "Network Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(ComplaintsActivity.this,
                                    "Server AuthFailureError  Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(ComplaintsActivity.this,
                                    "Server   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(ComplaintsActivity.this,
                                    "Network   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof ParseError) {


                            Toast toast = Toast.makeText(ComplaintsActivity.this,
                                    "ParseError   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            // Toast.makeText(Image_Gellary.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(ComplaintsActivity.this, error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                        dialog.dismiss();
                        // finish();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ComplaintsActivity.this);

        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        requestQueue.add(stringRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        //requestQueue.add(stringRequest);
//                MyApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void requestGPSPermission1() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            cd = new ConnectionDetector(getApplicationContext());
                            if (cd.isConnectingToInternet()) {
                                dialog = new ProgressDialog(ComplaintsActivity.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                dialog.setMessage("Sync in Progress, Please Wait");
                                dialog.setTitle("Smart Anchor App");
                                dialog.setCancelable(false);
                                dialog.show();
                                new doFileUpload().execute();
                            }
                            else {
                                Toast toast = Toast.makeText(ComplaintsActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
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

}

