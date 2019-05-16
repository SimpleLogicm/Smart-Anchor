package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
//import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.ComplaintAdapter;
import com.anchor.adapter.HorizontalRecyclerViewAdapter;
import com.anchor.helper.MultipartUtility;
import com.anchor.helper.PrefManager;
import com.anchor.interfaces.Customer_S_Interface;
import com.anchor.local.database.ComplaintDatabase;
import com.anchor.local.repository.ComplaintRepository;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cpm.simplelogic.helper.ConnectionDetector;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewMarketingToolActivity extends AppCompatActivity implements Customer_S_Interface, LocationListener {
    private ProgressDialog dialog;
    String longitude, latitude;
    LocationManager locationManager;
    int check = 0;
    int check2 = 0;
    int check3 = 0;
    int check4 = 0;
    private RecyclerView myRv;
    String TypeText, areaText;
    static String final_response = "";
    String response_result = "";
    String comment = "";
    String productItem, complaintTypeItem, areaTypeItem;
    ArrayAdapter<String> stateAdapter, cityAdapter, areadataAdapter, customer_typedataAdapter, product_segmentdataAdapter, branddataAdapter, product_typedataAdapter, statusdataAdapter, call_typedataAdapter;
    //    private TextView complaint;
    private TextInputEditText text1_value, name_value, address_value;
    AutoCompleteTextView autoCompleteTextView1;
    private List<Complaint> complaintList = new ArrayList<>();
    private static final String TAG = "ComplaintTesting";
    ComplaintAdapter myAdapter;
    ComplaintRepository complaintRepository;
    CompositeDisposable compositeDisposable;
    private Toolbar toolbar;
    //private CoordinatorLayout coordinatorLayout;
    ArrayList<String> state_list = new ArrayList<>();
    ArrayList<String> city_list = new ArrayList<>();
    ArrayList<String> area_list = new ArrayList<>();
    ArrayList<String> customer_type_list = new ArrayList<>();
    ArrayList<String> product_segment_list = new ArrayList<>();
    ArrayList<String> brand_list = new ArrayList<>();
    ArrayList<String> product_type_list = new ArrayList<>();
    ArrayList<String> status_list = new ArrayList<>();
    ArrayList<String> call_type_list = new ArrayList<>();
    Spinner c_spinner_search_state, c_spinner_search_city, c_spinner_area, c_spinner_customer_type, c_spinner_product_segment, c_spinner_brand, c_spinner_product_type, c_spinner_status, c_spinner_call_type;
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
    File file1;
    MultipartUtility multipart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_marketing_tool);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try
        {

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.action_bar);
            View view =getSupportActionBar().getCustomView();

            view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = (TextView) view.findViewById(R.id.screenname);
            mTitleTextView.setText("New Marketing Tool");

            TextView todaysTarget = (TextView) view.findViewById(R.id.todaysTarget);

            SharedPreferences sp = NewMarketingToolActivity.this.getSharedPreferences("SimpleLogic", 0);

        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
			todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
		}

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

           getActionBar().setHomeButtonEnabled(true);
            getActionBar().setDisplayHomeAsUpEnabled(true);

        }catch(Exception ex){ex.printStackTrace();}


        c_spinner_search_state = findViewById(R.id.c_spinner_search_state);
        c_spinner_search_city = findViewById(R.id.c_spinner_search_city);
        c_spinner_area = findViewById(R.id.c_spinner_area);
        c_spinner_customer_type = findViewById(R.id.c_spinner_customer_type);
        c_spinner_product_segment = findViewById(R.id.c_spinner_product_segment);
        c_spinner_brand = findViewById(R.id.c_spinner_brand);
        c_spinner_product_type = findViewById(R.id.c_spinner_product_type);
        c_spinner_status = findViewById(R.id.c_spinner_status);
        c_spinner_call_type = findViewById(R.id.c_spinner_call_type);

        c_fab_add = findViewById(R.id.c_fab_add);
        autoCompleteTextView1 = findViewById(R.id.autoCompleteTextView1);

        submit = findViewById(R.id.btnNewComplaintSubmit);
        cd = new ConnectionDetector(getApplicationContext());
        c_horizontalRecyclerView = findViewById(R.id.c_horizontalRecyclerView);

        dialog = new ProgressDialog(NewMarketingToolActivity.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        init();
        getLocation();
//        Log.d(TAG, "onCreate: test now has data");
//        myAdapter = new ComplaintAdapter(this, complaintList);
//        myRv.setLayoutManager(new LinearLayoutManager(this));
//        myRv.setAdapter(myAdapter);

        prefManager = new PrefManager(this);



//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("New " + prefManager.getCustomer_service_type());

       // coordinatorLayout = findViewById(R.id.coordinatorLayout);

        horizontalAdapter = new HorizontalRecyclerViewAdapter(imageModelArrayList, NewMarketingToolActivity.this, this);
        horizontalLayoutManager = new LinearLayoutManager(NewMarketingToolActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontalLayoutManager.setReverseLayout(true);
        horizontalLayoutManager.setStackFromEnd(true);
        c_horizontalRecyclerView.setLayoutManager(horizontalLayoutManager);
        c_horizontalRecyclerView.setAdapter(horizontalAdapter);

//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setHomeButtonEnabled(true);
//        }

        if (cd.isConnectingToInternet()) {
            dialog = new ProgressDialog(NewMarketingToolActivity.this);
            dialog.setMessage("Please wait....");
            dialog.setTitle("Dealer App");
            dialog.setCancelable(false);
            dialog.show();

            ComplaintTypeResult();
        } else {
            Toast toast = Toast.makeText(NewMarketingToolActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent order_home = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(order_home);
            finish();

        }

        state_list.add("Selects State");
        city_list.add("Selects City");
        area_list.add("Selects Area");
        customer_type_list.add("Selects Customer Type");
        product_segment_list.add("Selects Product Segment");
        brand_list.add("Selects Brand");
        product_type_list.add("Selects Product Type");
        status_list.add("Selects Status");
        call_type_list.add("Selects Call Type");

        stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state_list);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_search_state.setAdapter(stateAdapter);
        // c_spinner_search_state.setOnItemSelectedListener(this);

        cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, city_list);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_search_city.setAdapter(cityAdapter);
        // c_spinner_search_city.setOnItemSelectedListener(this);

        areadataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, area_list);
        areadataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_area.setAdapter(areadataAdapter);
        // c_spinner_area.setOnItemSelectedListener(this);

        customer_typedataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, customer_type_list);
        customer_typedataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_customer_type.setAdapter(customer_typedataAdapter);
        // c_spinner_customer_type.setOnItemSelectedListener(this);

        product_segmentdataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, product_segment_list);
        product_segmentdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_product_segment.setAdapter(product_segmentdataAdapter);
        // c_spinner_product_segment.setOnItemSelectedListener(this);

        branddataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brand_list);
        branddataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_brand.setAdapter(branddataAdapter);
        // c_spinner_brand.setOnItemSelectedListener(this);

        product_typedataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, product_type_list);
        product_typedataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_product_type.setAdapter(product_typedataAdapter);
        // c_spinner_product_type.setOnItemSelectedListener(this);

        statusdataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status_list);
        statusdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_status.setAdapter(statusdataAdapter);
        // c_spinner_status.setOnItemSelectedListener(this);

        call_typedataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, call_type_list);
        call_typedataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        c_spinner_call_type.setAdapter(call_typedataAdapter);
        // c_spinner_call_type.setOnItemSelectedListener(this);


        //creating the database
        ComplaintDatabase complaintDatabase = ComplaintDatabase.getInstance(this);
        //repository interacting with database
        complaintRepository = new ComplaintRepository(complaintDatabase);

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
                String comment = text1_value.getText().toString().trim();
                String name_valuen = name_value.getText().toString().trim();
                String address_valuen = address_value.getText().toString().trim();


//                if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(name_value.getText().toString().trim()))
//                {
//
//                Toast.makeText(Customer_Product_Quality.this, "Enter Name", Toast.LENGTH_SHORT).show();
//                    name_value.requestFocus();
//                }
//                else
//                if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(address_value.getText().toString().trim()))
//                {
//
//                Toast.makeText(Customer_Product_Quality.this, "Enter Address", Toast.LENGTH_SHORT).show();
//                    address_value.requestFocus();
//                }
//                else
//                if(c_spinner_search_state.getSelectedItem().toString().equalsIgnoreCase("Selects State"))
//                {
//                Toast.makeText(Customer_Product_Quality.this, "Selects State", Toast.LENGTH_SHORT).show();
//                    c_spinner_search_state.requestFocus();
//                }
//                else
//                if(c_spinner_search_city.getSelectedItem().toString().equalsIgnoreCase("Selects City"))
//                {
//                Toast.makeText(Customer_Product_Quality.this, "Selects City", Toast.LENGTH_SHORT).show();
//                    c_spinner_search_city.requestFocus();
//                }
//                else
                if (c_spinner_area.getSelectedItem().toString().equalsIgnoreCase("Selects Area")) {
                    Toast.makeText(NewMarketingToolActivity.this, "Selects Area", Toast.LENGTH_SHORT).show();
                    c_spinner_area.requestFocus();
                } else if (c_spinner_customer_type.getSelectedItem().toString().equalsIgnoreCase("Selects Customer Type")) {
                    Toast.makeText(NewMarketingToolActivity.this, "Selects Customer Type", Toast.LENGTH_SHORT).show();
                    c_spinner_customer_type.requestFocus();
                } else if (c_spinner_product_segment.getSelectedItem().toString().equalsIgnoreCase("Selects Product Segment")) {
                    Toast.makeText(NewMarketingToolActivity.this, "Selects Product Segment", Toast.LENGTH_SHORT).show();
                    c_spinner_product_segment.requestFocus();
                } else if (c_spinner_brand.getSelectedItem().toString().equalsIgnoreCase("Selects Brand")) {
                    Toast.makeText(NewMarketingToolActivity.this, "Selects Brand", Toast.LENGTH_SHORT).show();
                    c_spinner_brand.requestFocus();
                } else if (c_spinner_product_type.getSelectedItem().toString().equalsIgnoreCase("Selects Product Type")) {
                    Toast.makeText(NewMarketingToolActivity.this, "Selects Product Type", Toast.LENGTH_SHORT).show();
                    c_spinner_product_type.requestFocus();
                }
//                else
//                if(c_spinner_status.getSelectedItem().toString().equalsIgnoreCase("Selects Status"))
//                {
//                    Toast.makeText(Customer_Product_Quality.this, "Selects Status", Toast.LENGTH_SHORT).show();
//                    c_spinner_status.requestFocus();
//                }
                else if (c_spinner_call_type.getSelectedItem().toString().equalsIgnoreCase("Selects Call Type")) {
                    Toast.makeText(NewMarketingToolActivity.this, "Selects Call Type", Toast.LENGTH_SHORT).show();
                    c_spinner_call_type.requestFocus();
                } else if (!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(text1_value.getText().toString().trim())) {

                    Toast.makeText(NewMarketingToolActivity.this, "Enter Remark", Toast.LENGTH_SHORT).show();
                    text1_value.requestFocus();
                } else {
                    requestGPSPermission1();

                }


            }
        });


        c_spinner_search_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {

                check2 = check2 + 1;
                if (check2 > 2) {
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Selects State")) {

                        city_list.clear();
                        city_list.add("Select City");
                        cityAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, city_list);
                        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        c_spinner_search_city.setAdapter(cityAdapter);

                        area_list.clear();
                        area_list.add("Select Area");
                        areadataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, area_list);
                        areadataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        c_spinner_area.setAdapter(areadataAdapter);


                        Toast toast = Toast.makeText(NewMarketingToolActivity.this, "Selects State", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else {

                        if (cd.isConnectingToInternet()) {
                            dialog = new ProgressDialog(NewMarketingToolActivity.this);
                            dialog.setMessage("Please wait....");
                            dialog.setTitle("Dealer App");
                            dialog.setCancelable(false);
                            dialog.show();

                            city_list.clear();
                            city_list.add("Select City");

                            city_Data(parent.getItemAtPosition(pos).toString().trim());
                        } else {

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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

        c_spinner_search_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {

                check3 = check3 + 1;
                if (check3 > 2) {
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Selects City")) {

                        area_list.clear();
                        area_list.add("Select Area");
                        areadataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, area_list);
                        areadataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        c_spinner_area.setAdapter(areadataAdapter);


                        Toast toast = Toast.makeText(NewMarketingToolActivity.this, "Selects City", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else {

                        if (cd.isConnectingToInternet()) {
                            dialog = new ProgressDialog(NewMarketingToolActivity.this);
                            dialog.setMessage("Please wait....");
                            dialog.setTitle("Dealer App");
                            dialog.setCancelable(false);
                            dialog.show();

                            area_list.clear();
                            area_list.add("Select Area");

                            area_Data(parent.getItemAtPosition(pos).toString().trim());
                        } else {

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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

//        c_spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View arg1,
//                                       int pos, long arg3) {
//
//                if(!prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Claims") )
//                {
//                    check3=check3+1;
//                    if(check3>2)
//                    {
//
//                        if (parent.getItemAtPosition(pos).toString()
//                                .equalsIgnoreCase("Selects Sub Category")) {
//
//                            autoCompleteTextView1.setText("");
//                            list_items.clear();
//
//
//                            Toast toast = Toast.makeText(Customer_Product_Quality.this, "Selects Sub Category", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();
//
//                        }
//                        else
//                        {
//
//                            if (cd.isConnectedToInternet())
//                            {
//                                autoCompleteTextView1.setText("");
//                                list_items.clear();
//
//                                getproducts();
//                            }
//                            else
//                            {
//
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "You don't have internet connection.", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                            }
//
//                        }
//
//                    }
//                }
//
//
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//                // TODO Auto-generated method stub
//
//            }
//        });
    }

    private void init() {

        myRv = findViewById(R.id.rvComplaint);
//        complaint = (TextView) findViewById(R.id.tvCardComplaint);
        text1_value = findViewById(R.id.text1_value);
        name_value = findViewById(R.id.name_value);
        address_value = findViewById(R.id.address_value);


        compositeDisposable = new CompositeDisposable();

        prefManager = new PrefManager(this);
        custServiceType = prefManager.getCustomer_service_type();


    }


    public void onComplaintSubmitClicked(View view) {

        //checking if the complaint is empty
        if (String.valueOf(text1_value.getText()).isEmpty()) {
            Toast.makeText(this, "Please fill the Complaint", Toast.LENGTH_SHORT).show();
            return;
        }

        //getting the data from the UI
        final String newComplaint = String.valueOf(text1_value.getText());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        final String currentDate = dateFormat.format(date).toString();
        //checking if DateTime works
        final long timeStamp = new Date().getTime();

        //clearing the TextInputEditText
        text1_value.setText("");
        text1_value.clearFocus();
//        numberOfNewLines(newComplaint);

        /*
        //adding the view at the top of the rv at runtime without a database
        GlobalData.complaintListData.add(0,new Complaint(currentDate,newComplaint));
        */

        //we just have to add the data to the database the rest will automatically be taken care of
        Disposable disposable = Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                        complaintList.add(new Complaint(currentDate, newComplaint, timeStamp));
                        Log.d(TAG, "subscribe: on submit clicked complaint list is being updated\n");
                        complaintRepository.insertComplaint(new Complaint(currentDate, newComplaint, timeStamp));
                        //loadData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(NewMarketingToolActivity.this, "Complaint added successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(NewMarketingToolActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);

    }
    /*
    //just for testing
    private void numberOfNewLines(String x) {
        int length = x.split("\r\n|\n|\r").length;
//        System.out.println("\n\n\nnumber of new lines in " + x + " is = " + length + "\n\n\n");
        Log.d(TAG, "\n\n\nnumberOfNewLines: in" + x + " is = " + length + "\n\n\n");
//        String substring = x.substring(0,60);
//        Log.d(TAG, "Substring is : \n" + substring);
    }
*/

    private void deleteData() {
        Disposable disposable = Observable
                .create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                        Log.d(TAG, "subscribe: on submit clicked feed list is being updated\n");
                        complaintRepository.deleteAllComplaints();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(NewMarketingToolActivity.this, "Complaints deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(NewMarketingToolActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
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

        Intent i = new Intent(NewMarketingToolActivity.this, Previous_Customer_Services.class);
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

                                if (image_count < 1) {
                                    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NewMarketingToolActivity.this);

                                    builder.setTitle("Add Photo!");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {

                                        @Override

                                        public void onClick(DialogInterface dialog, int item) {

                                            if (options[item].equals("Take Photo")) {
                                                image_check = "photo";

                                                File photoFile = null;
                                                try {
                                                    photoFile = createImageFile();
                                                } catch (IOException ex) {
                                                    // Error occurred while creating the File
                                                    Log.i("Image TAG", "IOException");
                                                    pictureImagePath = "";
                                                }
                                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                                startActivityForResult(cameraIntent, 1);

                                            } else if (options[item].equals("Choose from Gallery")) {

                                                image_check = "gallery";
                                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                                startActivityForResult(intent, 2);


                                            } else if (options[item].equals("Cancel")) {

                                                dialog.dismiss();

                                            }

                                        }

                                    });

                                    builder.show();
                                } else {
                                    Toast.makeText(NewMarketingToolActivity.this, "You can take 1 picture in one record", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(NewMarketingToolActivity.this);
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
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            //locationText.setText(locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+", "+
            //  addresses.get(0).getAddressLine(1)+", "+addresses.get(0).getAddressLine(2));
        } catch (Exception e) {

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
        Toast.makeText(NewMarketingToolActivity.this, "Please Enable GPS", Toast.LENGTH_SHORT).show();
    }

    public class doFileUpload extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... response) {

//            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//            @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            longitude = String.valueOf(location.getLongitude());
//            latitude = String.valueOf(location.getLatitude());
            //Toast.makeText(Customer_Product_Quality.this, ""+latitude+longitude, Toast.LENGTH_SHORT).show();


            //String urlString = domain + "sub_dealers";
            try {


                //File uploadFile1 = new File("/sdcard/myvideo.mp4");

//                Customer_Product_Quality.this.runOnUiThread(new Runnable() {
//                    public void run() {
                String domain = getResources().getString(R.string.service_domain) + "customer_service_complaints/create_complaint";
                Log.i("volley", "domain: " + domain);
                String charset = "UTF-8";

                try {
                    multipart = new MultipartUtility(domain, charset);
                    multipart.addFormField("type", "product_quality");
                    multipart.addFormField("from", "smart_anchor");
                    multipart.addFormField("email", Global_Data.CUSTOMER_EMAIL);
                    multipart.addFormField("user_email", Global_Data.GLOvel_USER_EMAIL);
                    //  multipart.addFormField("state", c_spinner_search_state.getSelectedItem().toString());
                    //  multipart.addFormField("city", c_spinner_search_city.getSelectedItem().toString());
                    multipart.addFormField("type_complaint", c_spinner_area.getSelectedItem().toString());
                    multipart.addFormField("customer_type", c_spinner_customer_type.getSelectedItem().toString());
                    multipart.addFormField("product_segment", c_spinner_product_segment.getSelectedItem().toString());
                    multipart.addFormField("brand", c_spinner_brand.getSelectedItem().toString());
                    multipart.addFormField("product_type", c_spinner_product_type.getSelectedItem().toString());
                    // multipart.addFormField("status", c_spinner_status.getSelectedItem().toString());
                    multipart.addFormField("call_type", c_spinner_call_type.getSelectedItem().toString());
                    multipart.addFormField("remarks", text1_value.getText().toString().trim());
                    // multipart.addFormField("name", name_value.getText().toString());
                    //  multipart.addFormField("address", address_value.getText().toString());


                    multipart.addFormField("latitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(latitude)));
                    multipart.addFormField("longitude", (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(longitude)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//
//
//                    }
//                });


                // multipart.addFilePart("picture1", file1);

                try {
                    Uri uri1 = Uri.parse(pictureImagePath);
                    file1 = new File(uri1.getPath());
                    if (!pictureImagePath.equalsIgnoreCase("")) {
                        multipart.addFilePart("picture1", file1);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }


                List<String> response1 = multipart.finish();

                Log.v("complaint_resp", "SERVER REPLIED:");


                for (String line : response1) {
                    Log.v("rht", "Line : " + line);
                    response_result_image = line;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {

                                JSONObject obj = new JSONObject(response_result_image);
//                                String ss=obj.getString("message");
//                                System.out.println(""+ss);

                                Toast.makeText(NewMarketingToolActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

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
                                    Intent a = new Intent(NewMarketingToolActivity.this, MainActivity.class);
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
                                    Toast.makeText(NewMarketingToolActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                hidePDialog();
                                Intent a = new Intent(NewMarketingToolActivity.this, MainActivity.class);
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

                        Intent a = new Intent(NewMarketingToolActivity.this, MainActivity.class);
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

                    Intent a = new Intent(NewMarketingToolActivity.this, CustomerServicesActivity.class);
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


    public void ComplaintTypeResult() {
        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        //Log.i("volley", "email: " + Global_Data.CityName);

        StringRequest jsObjRequest = null;
        String service_url = "";
        //String email="payal@gmail.com";
        String email = prefManager.getUser_Email();

        service_url = domain + "customer_service_complaints/product_quality_list?email=" + Global_Data.CUSTOMER_EMAIL;

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

                        Intent intent = new Intent(NewMarketingToolActivity.this, MainActivity.class);
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

        RetryPolicy policy = new DefaultRetryPolicy(0, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

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
                }

                if (response_result.equalsIgnoreCase("Customer not found.")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            // upcoming_recyclerView.hideShimmerAdapter();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

//                            Intent intent = new Intent(NewMarketingToolActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    });
                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            Intent intent = new Intent(NewMarketingToolActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else {

                    JSONArray area = response.getJSONArray("areas");
                    JSONArray customer_type = response.getJSONArray("customer_types");
                    JSONArray product_segment = response.getJSONArray("product_segments");
                    JSONArray brand = response.getJSONArray("brands");
                    JSONArray product_type = response.getJSONArray("product_types");
                    //JSONArray status = response.getJSONArray("status");
                    JSONArray call_type = response.getJSONArray("call_types");


                    Log.d("volley", "area" + area.toString());
                    Log.d("volley", "customer_type" + customer_type.toString());
                    Log.d("volley", "product_segment" + product_segment.toString());
                    Log.d("volley", "brand" + brand.toString());
                    Log.d("volley", "product_type" + product_type.toString());
                    //  Log.d("volley", "status" + status.toString());
                    Log.d("volley", "call_type" + call_type.toString());


                    if (area.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < area.length(); i++) {
                            JSONObject jsonObject = area.getJSONObject(i);

                            area_list.add(jsonObject.getString("area"));

                        }
                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                // Creating ArrayAdapter using the string array and default spinner layout
                                areadataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, area_list);
                                // Specify layout to be used when list of choices appears
                                areadataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_area.setAdapter(areadataAdapter);
                                //product_spinner.setOnItemSelectedListener(Customer_Product_Quality.this);
                            }
                        });
                    }

                    if (customer_type.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < customer_type.length(); i++) {
                            JSONObject jsonObject = customer_type.getJSONObject(i);

                            customer_type_list.add(jsonObject.getString("customer_type"));


                        }
                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                // Creating ArrayAdapter using the string array and default spinner layout
                                customer_typedataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, customer_type_list);
                                // Specify layout to be used when list of choices appears
                                customer_typedataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_customer_type.setAdapter(customer_typedataAdapter);
                                //c_spinner_customer_type.setOnItemSelectedListener(Customer_Product_Quality.this);
                            }
                        });
                    }


                    if (product_segment.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < product_segment.length(); i++) {
                            JSONObject jsonObject = product_segment.getJSONObject(i);

                            product_segment_list.add(jsonObject.getString("product_segment"));


                        }
                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                // Creating ArrayAdapter using the string array and default spinner layout
                                product_segmentdataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, product_segment_list);
                                // Specify layout to be used when list of choices appears
                                product_segmentdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_product_segment.setAdapter(product_segmentdataAdapter);
                                //product_spinner.setOnItemSelectedListener(Customer_Product_Quality.this);
                            }
                        });
                    }

                    if (brand.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < brand.length(); i++) {
                            JSONObject jsonObject = brand.getJSONObject(i);

                            brand_list.add(jsonObject.getString("brand"));


                        }
                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                // Creating ArrayAdapter using the string array and default spinner layout
                                branddataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, brand_list);
                                // Specify layout to be used when list of choices appears
                                branddataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_brand.setAdapter(branddataAdapter);
                                //product_spinner.setOnItemSelectedListener(Customer_Product_Quality.this);
                            }
                        });
                    }

                    if (product_type.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < product_type.length(); i++) {
                            JSONObject jsonObject = product_type.getJSONObject(i);

                            product_type_list.add(jsonObject.getString("product_type"));


                        }
                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                // Creating ArrayAdapter using the string array and default spinner layout
                                product_typedataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, product_type_list);
                                // Specify layout to be used when list of choices appears
                                product_typedataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_product_type.setAdapter(product_typedataAdapter);
                                //product_spinner.setOnItemSelectedListener(Customer_Product_Quality.this);
                            }
                        });
                    }

//                    if (status.length() <= 0) {
//
//                        Customer_Product_Quality.this.runOnUiThread(new Runnable() {
//                            public void run() {
//                                // upcoming_recyclerView.hideShimmerAdapter();
////                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
////                                toast.setGravity(Gravity.CENTER, 0, 0);
////                                toast.show();
//                            }
//                        });
//                    } else {
//
//                        for (int i = 0; i < status.length(); i++)
//                        {
//                            JSONObject jsonObject = status.getJSONObject(i);
//
//                            status_list.add(jsonObject.getString("status"));
//
//
//
//                        }
//                        Customer_Product_Quality.this.runOnUiThread(new Runnable()
//                        {
//                            public void run() {
//
//                                // Creating ArrayAdapter using the string array and default spinner layout
//                                statusdataAdapter = new ArrayAdapter<String>(Customer_Product_Quality.this, android.R.layout.simple_spinner_item, status_list);
//                                // Specify layout to be used when list of choices appears
//                                statusdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                // Applying the adapter to our spinner
//                                c_spinner_status.setAdapter(statusdataAdapter);
//                                //product_spinner.setOnItemSelectedListener(Customer_Product_Quality.this);
//                            }
//                        });
//                    }

                    if (call_type.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                // upcoming_recyclerView.hideShimmerAdapter();
//                                Toast toast = Toast.makeText(Customer_Product_Quality.this, "Type Complaints not exist", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < call_type.length(); i++) {
                            JSONObject jsonObject = call_type.getJSONObject(i);

                            call_type_list.add(jsonObject.getString("call_type"));


                        }
                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                // Creating ArrayAdapter using the string array and default spinner layout
                                call_typedataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, call_type_list);
                                // Specify layout to be used when list of choices appears
                                call_typedataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_call_type.setAdapter(call_typedataAdapter);
                                //product_spinner.setOnItemSelectedListener(Customer_Product_Quality.this);
                            }
                        });
                    }


                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
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

                Intent intent = new Intent(NewMarketingToolActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        dialog.dismiss();
                        //upcoming_recyclerView.hideShimmerAdapter();
                    }
                });
            }

            NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    //upcoming_recyclerView.hideShimmerAdapter();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
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
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void city_Data(String state) {
        String domain = getResources().getString(R.string.service_domain);
        StringRequest jsObjRequest = null;
        String Service_url = "";

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + prefManager.getUser_Email());
        try {

            Service_url = domain + "customer_service_complaints/populate_sub_category?email=" + prefManager.getUser_Email() + "&state=" + URLEncoder.encode(c_spinner_search_state.getSelectedItem().toString().trim(), "UTF-8") + "&type=" + URLEncoder.encode(prefManager.getCustomer_service_type().toLowerCase(), "UTF-8");


            Log.i("user list url", "user list url " + Service_url);

            jsObjRequest = new StringRequest(Service_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("volley", "response: " + response);
                    final_response = response;

                    new NewMarketingToolActivity.getCity().execute(response);

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

    private class getCity extends AsyncTask<String, Void, String> {
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

                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else {

                    //dbvoc.getDeleteTable("delivery_products");

                    JSONArray city = response.getJSONArray("city");
                    Log.i("volley", "response city Length: " + city.length());
                    Log.d("volley", "city" + city.toString());


                    //
                    if (city.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                                //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(NewMarketingToolActivity.this, "City doesn't exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < city.length(); i++) {

                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(city.getString(i))) {
                                    {
                                        JSONObject jsonObject = city.getJSONObject(i);
                                        city_list.add(jsonObject.getString("city"));

                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                cityAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, city_list);
                                // Specify layout to be used when list of choices appears
                                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_search_city.setAdapter(cityAdapter);
                                dialog.dismiss();
                            }
                        });

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    // }

                    // output.setText(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();

                NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });
            }

            NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
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

    public void area_Data(String state) {
        String domain = getResources().getString(R.string.service_domain);
        StringRequest jsObjRequest = null;
        String Service_url = "";

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + prefManager.getUser_Email());
        try {

            Service_url = domain + "customer_service_complaints/populate_sub_category?email=" + prefManager.getUser_Email() + "&state=" + URLEncoder.encode(c_spinner_search_state.getSelectedItem().toString().trim()) + "&city=" + URLEncoder.encode(c_spinner_search_city.getSelectedItem().toString().trim(), "UTF-8") + "&type=" + URLEncoder.encode(prefManager.getCustomer_service_type().toLowerCase(), "UTF-8");


            Log.i("user list url", "user list url " + Service_url);

            jsObjRequest = new StringRequest(Service_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("volley", "response: " + response);
                    final_response = response;

                    new NewMarketingToolActivity.getArea().execute(response);

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

    private class getArea extends AsyncTask<String, Void, String> {
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

                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                    NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else {

                    //dbvoc.getDeleteTable("delivery_products");

                    JSONArray area = response.getJSONArray("area");
                    Log.i("volley", "response area Length: " + area.length());
                    Log.d("volley", "area" + area.toString());


                    //
                    if (area.length() <= 0) {

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                                //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(NewMarketingToolActivity.this, "Area doesn't exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        });
                    } else {

                        for (int i = 0; i < area.length(); i++) {

                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(area.getString(i))) {
                                    {
                                        JSONObject jsonObject = area.getJSONObject(i);
                                        area_list.add(jsonObject.getString("area"));

                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                areadataAdapter = new ArrayAdapter<String>(NewMarketingToolActivity.this, android.R.layout.simple_spinner_item, area_list);
                                // Specify layout to be used when list of choices appears
                                areadataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                // Applying the adapter to our spinner
                                c_spinner_area.setAdapter(areadataAdapter);
                                dialog.dismiss();
                            }
                        });

                        NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    // }

                    // output.setText(data);
                }
            } catch (JSONException e) {
                e.printStackTrace();

                NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });
            }

            NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            NewMarketingToolActivity.this.runOnUiThread(new Runnable() {
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
        dialog.setTitle("Dealer");
        dialog.setCancelable(false);
        dialog.show();

        String domain = getResources().getString(R.string.service_domain);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + prefManager.getUser_Email());

        String service_url = "";

        try {

            if (prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool")) {
                service_url = domain + "customer_service_complaints/populate_item_description?email=" +
                        URLEncoder.encode(prefManager.getUser_Email(), "UTF-8") + "&product_category=" + URLEncoder.encode(c_spinner_search_state.getSelectedItem().toString(), "UTF-8") + "&sub_category=" + URLEncoder.encode(c_spinner_area.getSelectedItem().toString(), "UTF-8") + "&type=" + URLEncoder.encode("marketing_tool", "UTF-8");
                ;
            } else {
                service_url = domain + "customer_service_complaints/populate_item_description?email=" +
                        URLEncoder.encode(prefManager.getUser_Email(), "UTF-8") + "&product_category=" + URLEncoder.encode(c_spinner_search_state.getSelectedItem().toString(), "UTF-8") + "&sub_category=" + URLEncoder.encode(c_spinner_area.getSelectedItem().toString(), "UTF-8") + "&type=" + URLEncoder.encode(prefManager.getCustomer_service_type().toLowerCase(), "UTF-8");
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

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NewMarketingToolActivity.this,
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


                                Toast toast = Toast.makeText(NewMarketingToolActivity.this,
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

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this,
                                    "Network Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this,
                                    "Server AuthFailureError  Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this,
                                    "Server   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this,
                                    "Network   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof ParseError) {


                            Toast toast = Toast.makeText(NewMarketingToolActivity.this,
                                    "ParseError   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            // Toast.makeText(Image_Gellary.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(NewMarketingToolActivity.this, error.getMessage(), Toast.LENGTH_LONG);
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

        RequestQueue requestQueue = Volley.newRequestQueue(NewMarketingToolActivity.this);

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
                                dialog = new ProgressDialog(NewMarketingToolActivity.this, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                                dialog.setMessage("Sync in Progress, Please Wait");
                                dialog.setTitle("Dealer");
                                dialog.setCancelable(false);
                                dialog.show();

                                new doFileUpload().execute();
                            } else {
                                Toast toast = Toast.makeText(NewMarketingToolActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
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


