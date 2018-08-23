package com.anchor.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.activities.AllOrders_Sync;
import com.anchor.activities.AppLocationManager;
import com.anchor.activities.Check_Null_Value;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.LoginDataBaseAdapter;
import com.anchor.activities.MainActivity;
import com.anchor.activities.Order;
import com.anchor.activities.PlayService_Location;
import com.anchor.activities.R;
import com.anchor.activities.Youtube_Player_Activity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

import cpm.simplelogic.helper.Config;

/**
 * Created by vinod on 21-03-2016.
 */

public class getServices {
    static String response_result = "";
    static String order_image_url = "";
    static Bitmap blob_data_logo;
    static byte[] imgBytesData;
    static String str;
    static String strr="";
    static int items_count = 0;
    static ArrayList<String> PRODUCTOrder_ids = new ArrayList<String>();
    private static float t_total = 0;
    private static float achived_total = 0;
    private static Calendar calendarn;
    private static Double total_ammount = 0.0;
    private static int year;
    private static ArrayList<String> mobile_numbers = new ArrayList<String>();
    private int month;
    private int day;
    Button createaccount,updateaccountdetails,addopportunity,salesupdate,hotcases,upcomingtasks,target;
     static String final_response = "";
     static Activity activity;
   // PreferencesHelper Prefs;
    TextView USERNAME,LOCATION,TARGETS,ACHIVE;
    static LoginDataBaseAdapter loginDataBaseAdapter;
    Location location;
    static String email_adress = "";
    
   // static String Account_Flag = "";
    static  String customer_id = "";
    Boolean isInternetPresent = false;
    //ConnectionDetector cd;
    static ProgressDialog dialog;
    static JSONObject ParentJson;
    Context ctx;
    static DataBaseHelper dbvoc;
   // static LoginDataBaseAdapter loginDataBaseAdapter;

    static String Retailer_Flag = "";
    static String FEED_Flag = "";
    static String COMP_Flag = "";
    static String CLAIM_Flag = "";
    static String COMPS_Flag = "";
    static String PICTURE_Flag = "";

    static String order_Flag = "";
    static String return_order_Flag = "";
    static String no_order_Flag = "";
    static String market_survey_Flag = "";
    static String travel_expenses_Flag = "";
    static String misc_expenses_Flag = "";
    static String calenderdata_Flag = "";

    static String Final_Flag_N = "";
    static String Final_Flag_ORDER_N = "";
    static int simState;
    static String Sim_Number ="";
    static  Context context;
    static String device_id = "";
    static String Order_number = "";
    static ArrayList<String> s_code = new ArrayList<String>();
    static ArrayList<String> s_stock = new ArrayList<String>();

    public static void sendRequest(Context contextn)
    {

        SharedPreferences sp = contextn.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        device_id = sp.getString("devid", "");

        context = contextn;

         loginDataBaseAdapter=new LoginDataBaseAdapter(context);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
         dbvoc = new DataBaseHelper(context);

        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Anchor");
        dialog.setCancelable(false);
        dialog.show();
        
        domain = context.getResources().getString(R.string.service_domain);

       // Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }F

      Log.d("Server url","Server url"+domain+"menus/sync_masters?imei_no="+device_id);

      StringRequest stringRequest = null;
      stringRequest = new StringRequest(domain+"menus/sync_masters?imei_no="+device_id,
	          new Response.Listener<String>() {
	              @Override
	              public void onResponse(String response) {
	                  //showJSON(response);
	                  // Log.d("jV", "JV" + response);
	                  Log.d("jV", "JV length" + response.length());
	                  // JSONObject person = (JSONObject) (response);
	                  try {
	                      JSONObject json = new JSONObject(new JSONTokener(response));
	                      try{

	                    	  String response_result = "";
	                          if(json.has("result"))
	                          {
	                              response_result = json.getString("result");
	                          }
	                          else
	                          {
	                              response_result = "data";
	                          }

	                          if(response_result.equalsIgnoreCase("Data is up to date.")) {

	                              Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

	                          }
	                          else 
                        	  if(response_result.equalsIgnoreCase("Device not registered")) {

	                              Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

	                          }
	                          else {

	                              JSONArray items = json.getJSONArray("products");
	                              JSONArray customers = json.getJSONArray("customers");
	                               //TODO DONE for distributor problem
	                              JSONArray distributors = json.getJSONArray("distributors");
	                              JSONArray reasons = json.getJSONArray("reasons");
	                              JSONArray states = json.getJSONArray("states");
	                              JSONArray cities = json.getJSONArray("cities");
	                              JSONArray beats = json.getJSONArray("beats");

//                                  JSONObject logo_img = json.getJSONObject("customer_logo");
//	                              String logo_imgstr=logo_img.getString("logo_image_string");

//                                  byte[] imgBytesData = android.util.Base64.decode(logo_imgstr);
//                                  //Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytesData, 0, imgBytesData.length);

	                              Log.d("items", "items" + items.toString());
	                              Log.d("customers", "customers" + customers.toString());
	                              // TODO DONE for distributor problem
	                              Log.d("distributors", "distributors" + customers.toString());
	                              
	                              Log.d("reasons", "reasons" + reasons.toString());

	                              Log.d("items", "items length" + items.length());
	                              Log.d("customers", "customers length" + customers.length());
	                              
	                              Log.d("states", "states length" + states.length());
	                              Log.d("cities", "cities length" + cities.length());
	                              Log.d("beats", "beats length" + beats.length());
	                              
	                              Log.d("states", "states length" + states.toString());
	                              Log.d("cities", "cities length" + cities.toString());
	                              Log.d("beats", "beats length" + beats.toString());

	                             // Log.d("customers", "customers" + customers.toString());
	                             // Log.d("devices", "devices" + devices.toString());

                                 // String logo_bs64img="";

//                                  byte[] imgBytesData = android.util.Base64.decode(logo_bs64img);
//                                  Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytesData, 0, imgBytesData.length);

//                                  byte[] imageBytes= Base64.decode(bs64img, Base64.DEFAULT);
//                                  InputStream is = new ByteArrayInputStream(imageBytes);
//                                  BufferedImage img = ImageIO.read(is);

	                            for (int i = 0; i < items.length(); i++) {

	                                  JSONObject jsonObject = items.getJSONObject(i);

  //                                        loginDataBaseAdapter.insertItems(jsonObject.getString("code"), jsonObject.getString("addl_item_code_barcode"),
  //                                        jsonObject.getString("main_group"), jsonObject.getString("item_name_wo_shade"), jsonObject.getString("item_name_w_shade_category"),
  //                                        jsonObject.getString("brand_name"), jsonObject.getString("shade_name"), jsonObject.getString("size")
  //                                        , "", "", "", jsonObject.getString("mrp")
  //                                        , jsonObject.getString("product"), "", jsonObject.getString("category"), jsonObject.getString("category_group")
  //                                        , "", jsonObject.getString("season"), jsonObject.getString("tmp_rm_product"), jsonObject.getString("fs_style"), "", "", "", "", "");

	                                  loginDataBaseAdapter.insertEntryITEM_MASTER(jsonObject.getString("code"),jsonObject.getString("name"), jsonObject.getString("primary_category"),
	                                		  jsonObject.getString("sub_category"), jsonObject.getString("product_variant"), jsonObject.getString("retail_price"), 
	                                		  jsonObject.getString("mrp"), jsonObject.getString("qualifying_qty"),
	                                		  jsonObject.getString("free_qty"), jsonObject.getString("status"),"","","","");

	                                  // loginDataBaseAdapter.insertEntryITEM_MASTER(String Item_Number,	String Unit_Rate, String Organization_Code,	String Organization_Name, String Lot_Control, String Lot_Status, String	Inspection_Required, String Lot_Divisible,	String UOM, String Inventory_Type,	String Business_Vertical, String Primary_Category,	String Primary_Sub_Category, String Secondary_Category,	String Secondary_Sub_Category, String Description,	String Long_Description, String Size1, String Size2, String Voltage_Watts_Amps,	String IS_Code, String Colour,	String Invoice_Description1, String Invoice_Description2, String Old_Item_Code,	String Metal_Aluminum_Wt, String Metal_Copper_Wt, String Metal_Lead_Wt, String Packing_Quantity, String Packing_UOM, String Specification_Code,	String HSS_Code,String HSS_Description,	String Product_OD, String Product_Weight, String Bending_Radius, String Flange,	String Traverse, String Barrel,	String Drum_Carrying_Capacity,String Drum_Type, String Drum_Category,	String PLANNER_CODE,String PLANNING_MAKE_BUY_CODE,String WIP_SUPPLY_TYPE,String DEFAULT_LOT_STATUS_ID, String DEFAULT_LOT_STATUS, String LOT_PREFIX,	String START_AUTO_LOT_NUMBER, String EXPENSE_ACCOUNT, String COST_OF_SALES_ACCOUNT,	String SALES_ACCOUNT, String UNIT_WEIGHT, String UNIT_VOLUME, String UNIT_LENGTH, String UNIT_WIDTH,String UNIT_HEIGHT);	
	                              }
	                            
	                            for (int i = 0; i < customers.length(); i++) {

	                                JSONObject jsonObject = customers.getJSONObject(i);
                                    List<Local_Data> contactsr =  dbvoc.getCustomer_BYID(jsonObject.getString("name"),jsonObject.getString("shop_name"));

                                    if(contactsr.size() <= 0)
                                    {
                                        loginDataBaseAdapter.insertCustMaster(jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("shop_name"), jsonObject.getString("address"), jsonObject.getString("street"), jsonObject.getString("landmark"),
                                                jsonObject.getString("pincode"), jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"), jsonObject.getString("email"), jsonObject.getString("status"), jsonObject.getString("state_code"), jsonObject.getString("city_code"),jsonObject.getString("beat_code"),jsonObject.getString("vatin"),"","","","");
                                    }
                                    else
                                    {
                                        dbvoc.deletesalesupdatebyID(jsonObject.getString("name"),jsonObject.getString("shop_name"));
                                        loginDataBaseAdapter.insertCustMaster(jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("shop_name"), jsonObject.getString("address"), jsonObject.getString("street"), jsonObject.getString("landmark"),
                                                jsonObject.getString("pincode"), jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"), jsonObject.getString("email"), jsonObject.getString("status"), jsonObject.getString("state_code"), jsonObject.getString("city_code"),jsonObject.getString("beat_code"),jsonObject.getString("vatin"),"","","","");
                                    }
                                }
	                            
	                            // TODO DONE for distributor problem
	                            for (int i = 0; i < distributors.length(); i++) {

	                                  JSONObject jsonObject = distributors.getJSONObject(i);

//                                        loginDataBaseAdapter.insertItems(jsonObject.getString("code"), jsonObject.getString("addl_item_code_barcode"),
//                                        jsonObject.getString("main_group"), jsonObject.getString("item_name_wo_shade"), jsonObject.getString("item_name_w_shade_category"),
//                                        jsonObject.getString("brand_name"), jsonObject.getString("shade_name"), jsonObject.getString("size")
//                                        , "", "", "", jsonObject.getString("mrp")
//                                        , jsonObject.getString("product"), "", jsonObject.getString("category"), jsonObject.getString("category_group")
//                                        , "", jsonObject.getString("season"), jsonObject.getString("tmp_rm_product"), jsonObject.getString("fs_style"), "", "", "", "", "");

	                                  loginDataBaseAdapter.insertDistributors(jsonObject.getString("code"),jsonObject.getString("name"), jsonObject.getString("shop_name"),
	                                		  jsonObject.getString("address1"), jsonObject.getString("address2"), jsonObject.getString("street"), 
	                                		  jsonObject.getString("landmark"), jsonObject.getString("state_code"),
	                                		  jsonObject.getString("city_code"), jsonObject.getString("pincode"),
	                                		  jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"),
	                                		  jsonObject.getString("email"), jsonObject.getString("status"));
	                  	                                  
                                // loginDataBaseAdapter.insertEntryITEM_MASTER(String Item_Number,	String Unit_Rate, String Organization_Code,	String Organization_Name, String Lot_Control, String Lot_Status, String	Inspection_Required, String Lot_Divisible,	String UOM, String Inventory_Type,	String Business_Vertical, String Primary_Category,	String Primary_Sub_Category, String Secondary_Category,	String Secondary_Sub_Category, String Description,	String Long_Description, String Size1, String Size2, String Voltage_Watts_Amps,	String IS_Code, String Colour,	String Invoice_Description1, String Invoice_Description2, String Old_Item_Code,	String Metal_Aluminum_Wt, String Metal_Copper_Wt, String Metal_Lead_Wt, String Packing_Quantity, String Packing_UOM, String Specification_Code,	String HSS_Code,String HSS_Description,	String Product_OD, String Product_Weight, String Bending_Radius, String Flange,	String Traverse, String Barrel,	String Drum_Carrying_Capacity,String Drum_Type, String Drum_Category,	String PLANNER_CODE,String PLANNING_MAKE_BUY_CODE,String WIP_SUPPLY_TYPE,String DEFAULT_LOT_STATUS_ID, String DEFAULT_LOT_STATUS, String LOT_PREFIX,	String START_AUTO_LOT_NUMBER, String EXPENSE_ACCOUNT, String COST_OF_SALES_ACCOUNT,	String SALES_ACCOUNT, String UNIT_WEIGHT, String UNIT_VOLUME, String UNIT_LENGTH, String UNIT_WIDTH,String UNIT_HEIGHT);	
	                              }
	                            
	                            
	                            for (int i = 0; i < reasons.length(); i++) {

	                                  JSONObject jsonObject = reasons.getJSONObject(i);

                                        loginDataBaseAdapter.insertno_orderReason(jsonObject.getString("code"), jsonObject.getString("desc"));
	                  	                                  
                                	}
	                            
	                            for (int i = 0; i < states.length(); i++) {

	                                  JSONObject jsonObject = states.getJSONObject(i);

                                      loginDataBaseAdapter.insertStates("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("status"),"", 
                              				"", "", jsonObject.getString("code"));
	                  	                                  
                              	}
	                            
	                            for (int i = 0; i < cities.length(); i++) {

	                                  JSONObject jsonObject = cities.getJSONObject(i);

                                    loginDataBaseAdapter.insertCities("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("state_code"), jsonObject.getString("status"),"", 
                            				"","", "", jsonObject.getString("code"));
	                  	                                  
                            	}
	                            
	                            for (int i = 0; i < beats.length(); i++) {

	                                  JSONObject jsonObject = beats.getJSONObject(i);

                                    loginDataBaseAdapter.insertBeats("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("state_code"), jsonObject.getString("city_code"), jsonObject.getString("status"), "", 
                            				"","", "", jsonObject.getString("code"));
	                  	                                  
                            	}

	                               //dbvoc.update_stockChecks(s_code,s_stock);
	                              Toast.makeText(context, "items Sync Successfully.", Toast.LENGTH_LONG).show();
	                             // Global_Val.STOCK_SERVICE_FLAG = "";
	                              dialog.dismiss();
	                              //finish();

	                          }

	                        //  finish();
	                          // }

	                          // output.setText(data);
	                      }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }

	                      dialog.dismiss();
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
	                      Toast.makeText(context,
	                              "Network Error",
	                              Toast.LENGTH_LONG).show();
	                  } else if (error instanceof AuthFailureError) {
	                      Toast.makeText(context,
	                              "Server AuthFailureError  Error",
	                              Toast.LENGTH_LONG).show();
	                  } else if (error instanceof ServerError) {
	                      Toast.makeText(context,
	                              "Server   Error",
	                              Toast.LENGTH_LONG).show();
	                  } else if (error instanceof NetworkError) {
	                      Toast.makeText(context,
	                              "Network   Error",
	                              Toast.LENGTH_LONG).show();
	                  } else if (error instanceof ParseError) {
	                      Toast.makeText(context,
	                              "ParseError   Error",
	                              Toast.LENGTH_LONG).show();
	                  }
	                  else
	                  {
	                      Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
	                  }
	                  dialog.dismiss();
	                 // finish();
	              }
	          });



      RequestQueue requestQueue = Volley.newRequestQueue(context);

        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


    public static void sendRequestnew(Context contextn, String wait)
    {

        context = contextn;
        SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        device_id = sp.getString("devid", "");
        loginDataBaseAdapter=new LoginDataBaseAdapter(context);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        dbvoc = new DataBaseHelper(context);

        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage(wait);
        dialog.setTitle("Anchor");
        dialog.setCancelable(false);
        dialog.show();

        domain = context.getResources().getString(R.string.service_domain);

        // Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }

        Log.d("Server url","Server url"+domain+"menus/sync_masters?imei_no="+device_id);
        StringRequest stringRequest = null;
        stringRequest = new StringRequest(domain+"menus/sync_masters?imei_no="+device_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //showJSON(response);
                        // Log.d("jV", "JV" + response);
                        Log.d("jV", "JV length" + response.length());
                        final_response = response;
                        Context mActivity = context;
                        new LongOperation().execute(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Toast.makeText(context,
                                    "Network Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(context,
                                    "Server AuthFailureError  Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context,
                                    "Server   Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(context,
                                    "Network   Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(context,
                                    "ParseError   Error",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();

                        Intent intentn = new Intent(context, MainActivity.class);
                        context.startActivity(intentn);
                        //((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        ((Activity) context).finish();
                        // finish();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    
    public static void View_NearestCustomer(Context contextn,String address,String latitude,String longitude)
    {


        context = contextn;
        loginDataBaseAdapter=new LoginDataBaseAdapter(context);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
        SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        device_id = sp.getString("devid", "");
        Log.d("device_id ","device_id"+device_id);

       // dbvoc = new DataBaseHelper(context);

        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Anchor");
        dialog.setCancelable(false);
        dialog.show();

       // Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }

      try {
        Log.d("Server url","Server url"+domain+"menus/sync_stocks?imei_no="+device_id+"&latitude="
       + URLEncoder.encode(latitude, "UTF-8")+"&longitude="
    	       + URLEncoder.encode(longitude, "UTF-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      StringRequest stringRequest = null;
      try {
        stringRequest = new StringRequest(domain+"menus/sync_stocks?imei_no="+device_id+"&latitude="
        	       + URLEncoder.encode(latitude, "UTF-8")+"&longitude="
        	       + URLEncoder.encode(longitude, "UTF-8"),
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          //showJSON(response);
                          // Log.d("jV", "JV" + response);
                          Log.d("jV", "JV length" + response.length());
                          // JSONObject person = (JSONObject) (response);
                          try {
                              JSONObject json = new JSONObject(new JSONTokener(response));
                              try{
                                  String response_result = "";
                                  if(json.has("result"))
                                  {
                                      response_result = json.getString("result");
                                  }
                                  else
                                  {
                                      response_result = "data";
                                  }

                                  if(response_result.equalsIgnoreCase("Data is up to date.")) {

                                      Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                  }
                                  else
                                  if(json.getJSONArray("stocks").length()<= 0) {

                                      Toast.makeText(context.getApplicationContext(), "Stock is up to date.", Toast.LENGTH_LONG).show();

                                  }
                                  else {


                                      JSONArray stocks = json.getJSONArray("stocks");
                                      Log.d("stocks", "stocks" + stocks.toString());

                                      Log.d("stocks", "stocks length" + stocks.length());
                                     // Log.d("customers", "customers" + customers.toString());
                                     // Log.d("devices", "devices" + devices.toString());

                                    s_code.clear();
                                    s_stock.clear();

                                    for (int i = 0; i < stocks.length(); i++) {

                                          JSONObject jsonObject = stocks.getJSONObject(i);

  //                                        loginDataBaseAdapter.insertItems(jsonObject.getString("code"), jsonObject.getString("addl_item_code_barcode"),
  //                                        jsonObject.getString("main_group"), jsonObject.getString("item_name_wo_shade"), jsonObject.getString("item_name_w_shade_category"),
  //                                        jsonObject.getString("brand_name"), jsonObject.getString("shade_name"), jsonObject.getString("size")
  //                                        , "", "", "", jsonObject.getString("mrp")
  //                                        , jsonObject.getString("product"), "", jsonObject.getString("category"), jsonObject.getString("category_group")
  //                                        , "", jsonObject.getString("season"), jsonObject.getString("tmp_rm_product"), jsonObject.getString("fs_style"), "", "", "", "", "");

                                          s_code.add(jsonObject.getString("code"));
                                          s_stock.add(jsonObject.getString("current_stock"));
                                          //dbvoc.update_stockCheck(jsonObject.getString("code"),jsonObject.getString("current_stock"));

                                      }

                                       //dbvoc.update_stockChecks(s_code,s_stock);
                                      Toast.makeText(context, "Stock Sync Successfully.", Toast.LENGTH_LONG).show();
                                     // Global_Val.STOCK_SERVICE_FLAG = "";
                                      dialog.dismiss();
                                      //finish();
                                  }

                                //  finish();
                                  // }

                                  // output.setText(data);
                              }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


                              dialog.dismiss();
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
                              Toast.makeText(context,
                                      "Network Error",
                                      Toast.LENGTH_LONG).show();
                          } else if (error instanceof AuthFailureError) {
                              Toast.makeText(context,
                                      "Server AuthFailureError  Error",
                                      Toast.LENGTH_LONG).show();
                          } else if (error instanceof ServerError) {
                              Toast.makeText(context,
                                      "Server   Error",
                                      Toast.LENGTH_LONG).show();
                          } else if (error instanceof NetworkError) {
                              Toast.makeText(context,
                                      "Network   Error",
                                      Toast.LENGTH_LONG).show();
                          } else if (error instanceof ParseError) {
                              Toast.makeText(context,
                                      "ParseError   Error",
                                      Toast.LENGTH_LONG).show();
                          }
                          else
                          {
                              Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                          }
                          dialog.dismiss();
                         // finish();
                      }
                  });
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
    
    public static void SYNCORDER_BYORDER_ID(Context contextn){
     
      context = contextn;
      final ProgressDialog dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
      String uploadImage = "";
      dbvoc = new DataBaseHelper(contextn);

//  List<Local_Data> contacts2 = dbvoc.getAllOrderby_cusID("1012");
//      List<Local_Data> contacts2 = dbvoc.getAllOrderby_cusID(Global_Val.customer_id);
//      int ln = contacts2.size();
//
//
//      for (Local_Data cn : contacts2) {
//
//          CUSTOMER_NAME = cn.getStateName();
//
//      }

      //filePath =  new Uri(android.os.Environment.getExternalStorageDirectory(), "SIYARAMS"+"/" + "vinod" + "/" + CUSTOMER_NAME);

//      try
//      {
//          String filename = CUSTOMER_NAME+".png";
//          String path = "/mnt/sdcard/"+"SIYARAMS"+"/" + "vinod" + "/" + CUSTOMER_NAME + filename;
//          File f = new File(android.os.Environment.getExternalStorageDirectory(), "SIYARAMS"+"/" + "vinod" + "/" + CUSTOMER_NAME+"/"+filename);  //
//          Uri imageUri = Uri.fromFile(f);
//
//          bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//          uploadImage = getStringImage(bitmap);
//
//      }
//      catch (Exception  e)
//      {
//          e.printStackTrace();
//      }

      JSONObject jsonBody = new JSONObject();
      try {

          JSONArray product = new JSONArray();
          JSONArray order = new JSONArray();
          JSONObject product_valuenew = new JSONObject();

          int a = 0;

          List<Local_Data> contacts = dbvoc.GetOrders("","");
          //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
          for (Local_Data cn : contacts)
          {
              JSONObject product_value = new JSONObject();
              product_value.put("order_number", cn.get_category_code());
             // product_value.put("order_date", cn.getCUSTOMER_ORDER_DATE());
              product_value.put("order_take_by", cn.get_custadr());
             // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
             // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
              //product_value.put("signature_image_name", uploadImage);
              product_value.put("device_code", Global_Data.device_id);
              product_value.put("order_type", cn.get_shedule_payment_mode());
             // product_value.put("conference_code", cn.getconference_id());
              order.put(product_value);
              Log.d("count", "a" + ++a);
              //delete_order_no = cn.getORDER_NUMBER();
              List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts(cn.get_category_code());
              for (Local_Data cnp : contactsproduct) {

                  JSONObject item = new JSONObject();
                  item.put("order_number", cnp.get_category_code());
                  item.put("customer_name", cnp.get_custadr());
                  item.put("total_qty", cnp.get_stocks_product_quantity());
                  item.put("MRP", cnp.getMRP());
                  item.put("amount", cnp.get_Claims_amount());
                  item.put("scheme_amount", cnp.get_Target_Text());
                  product.put(item);
                  //Log.d("quantity","quantity"+cnp.getquantity());
              }
          }

//          for (int i = 0; i < 10; i++)
//          {
//
//
//
//          }

          product_valuenew.put("orders", order);
          product_valuenew.put("order_products", product);
          product_valuenew.put("imei_no", Global_Data.device_id);
          Log.d("Orders", order.toString());

          Log.d("order_products",product.toString());

          // HashMap<String, String> params = new HashMap<String, String>();
          //params.put("token", json.toString());

          dialog.setMessage("Order Sync in Progress, Please Wait");
          dialog.setTitle("Siyaram");
          dialog.setCancelable(false);
          dialog.show();

          // RequestQueue queue = Volley.newRequestQueue(getBaseContext());
         // PreferencesHelper Prefs = new PreferencesHelper(MasterSyncData.this);

          //String URL = Prefs.GetPreferences("URL");
          String  domain = context.getResources().getString(R.string.service_domain);

          Log.i("volley", "domain: " + domain);
          JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"api/orders", product_valuenew, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                  Log.i("volley", "response: " + response);

                  String response_result = "";
                  //if (response.has("result")) {
                  try {
                      response_result = response.getString("result");

//                          if (response_result.equalsIgnoreCase("Device not found.")) {
//                              Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                              toast.setGravity(Gravity.CENTER, 0, 0);
//                              toast.show();
//                              dialog.dismiss();
//                          }

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

                  if (response_result.equalsIgnoreCase("Device not found.")) {
                      Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                      toast.setGravity(Gravity.CENTER, 0, 0);
                      toast.show();
                      dialog.dismiss();


                  } else {
//                  else
//                  {
//                      response_result = "data";
//                  }


                      Toast.makeText(context, "Order Sync Successfully", Toast.LENGTH_LONG).show();

                      // dbvoc.getDeleteTable("order_products");
                      //dbvoc.getDeleteTable("orders");

                      // dbvoc.getDeleteTableorder_bycustomer(Global_Data.order_retailer.trim());
                      //dbvoc.getDeleteTableorderproduct_bycustomer(Global_Data.order_retailer.trim());
//                  List<Local_Data> contacts = dbvoc.GetOrders(Global_Val.customer_id);
//                  for (Local_Data cn : contacts)
//                  {
//                      // JSONObject product_value = new JSONObject();
//                      //product_value.put("order_number", cn.getORDER_NUMBER());
//
//                      dbvoc.deleteOrderproductByOCID(cn.getORDER_NUMBER());
//                      dbvoc.deleteOrderTABLE_QuantityValue(cn.getORDER_NUMBER());
//                      dbvoc.deleteBarcode_ByOrder(cn.getORDER_NUMBER());
//                      dbvoc.deleteORDERSNEW(cn.getORDER_NUMBER());
//
//                  }


                  //dbvoc.deleteOrderByOCID(Global_Val.customer_id);
                  //dbvoc.getDeleteTable("DESIGN_CHECK");

//                  Intent i = new Intent(MasterSyncData.this, MyAndroidAppActivity.class);
//                  //				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                  i.putExtra("user_name", user_name);
//                  i.putExtra("confrence_name", confrence_name);
//                  i.putExtra("BackFlag", "nothing");
//                  Global_Val.STOCK_SERVICE_FLAG = "TRUE";
                  //				i.putExtra("Barcode_Number", userInput.getText().toString());
                  //				i.putExtra("BackFlag","Barcode");
//                  startActivity(i);
//                  MasterSyncData.this.finish();
                  dialog.dismiss();
              }
              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Log.i("volley", "error: " + error);
                  Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                  dialog.dismiss();
              }
          });

          RequestQueue requestQueue = Volley.newRequestQueue(context);
          // queue.add(jsObjRequest);
          int socketTimeout = 30000;//30 seconds - change to what you want
          RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
          jsObjRequest.setRetryPolicy(policy);
          requestQueue.add(jsObjRequest);

      } catch (JSONException e) {
          e.printStackTrace();


      }

//add the request object to the queue to be executed
      //ApplicationController.getInstance().addToRequestQueue(req);


//      StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://cb899c5b.ngrok.io/siyarams/api/orders",
//              new Response.Listener<String>() {
//                  @Override
//                  public void onResponse(String response) {
//                      Toast.makeText(MasterSyncData.this,response,Toast.LENGTH_LONG).show();
//                  }
//              },
//              new Response.ErrorListener() {
//                  @Override
//                  public void onErrorResponse(VolleyError error) {
//                      Toast.makeText(MasterSyncData.this,error.toString(),Toast.LENGTH_LONG).show();
//                  }
//              }){
//          @Override
//          protected Map<String,String> getParams(){
//
//              try {
//
//                  JSONObject json = new JSONObject();
//                  json.put("name", "student");
//
//                  JSONArray array = new JSONArray();
//                  JSONObject item = new JSONObject();
//                  item.put("information", "test");
//                  item.put("id", 3);
//                  item.put("name", "course1");
//                  array.put(item);
//
//                  json.put("course", array);
//
//                 // message = json.toString();
//
//              }catch (Exception e){}
//
//
//              Map<String,String> params = new HashMap<String, String>();
//              params.put("user","user1");
//              params.put("name","name");
//             // params.put(KEY_EMAIL, email);
//              return params;
//          }
//
//      };
//
//      RequestQueue requestQueue = Volley.newRequestQueue(this);
//      requestQueue.add(stringRequest);
  }
    
    public static void SYNCORDER_BYCustomer(Context contextn,String order_id){
  	 context = contextn;

// Now formattedDate have current date/time
       // Toast.makeText(this, formattedDate, Toast.LENGTH_SHORT).show();

        dialog = new ProgressDialog(contextn, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        if (dialog != null && dialog.isShowing()){
            dialog.dismiss();}

        dialog.setMessage("Order Sync in Progress, Please Wait");
        dialog.setTitle("Anchor");
        dialog.setCancelable(false);
        dialog.show();

        new OrderAyncTask().execute();


 }

    public static void SYNCORDER_AllOrders(Context contextn){
        context = contextn;



        new AllOrderAyncTask().execute();

    }

    public static void SYNCORDER_BYCustomer_Return(Context contextn){
     	 context = contextn;
        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; "+c.getTime());



        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String formattedDate = df.format(c.getTime());

        final ProgressDialog dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        String uploadImage = "";
        dbvoc = new DataBaseHelper(contextn);

        JSONObject jsonBody = new JSONObject();
       
        try {

            JSONArray customer = new JSONArray();
            JSONArray product = new JSONArray();
            JSONArray order = new JSONArray();
            JSONObject product_valuenew = new JSONObject();

            int a = 0;
            String s = "";

            List<Local_Data> customers_contacts = dbvoc.getAllRetailer_cre();
            if(customers_contacts.size() > 0)
            {
               // Retailer_Flag = "true";
            }
            else
            {
               // Retailer_Flag = "false";
            }

            for (Local_Data cn : customers_contacts)
            {
                JSONObject product_value = new JSONObject();
                product_value.put("user_email", cn.getemail());
                product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
                product_value.put("name", cn.getCUSTOMER_NAME());
                product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
                product_value.put("address", cn.getADDRESS());
                product_value.put("street", cn.getSTREET());
                product_value.put("landmark", cn.getLANDMARK());
                product_value.put("pincode", cn.getPIN_CODE());
                product_value.put("mobile_no", cn.getMOBILE_NO());
                product_value.put("email", cn.getEMAIL_ADDRESS());
                product_value.put("status", cn.getSTATUS());
                product_value.put("state_code", cn.getSTATE_ID());
                product_value.put("city_code", cn.getCITY_ID());
                product_value.put("beat_code", cn.getBEAT_ID());
                product_value.put("vatin", cn.getvatin());
                product_value.put("latitude", cn.getlatitude());
                product_value.put("longitude", cn.getlongitude());
                customer.put(product_value);

            }
           
            List<Local_Data> contacts = dbvoc.GetOrders_return("Secondary Sales / Retail Sales",Global_Data.GLOvel_GORDER_ID_RETURN);
            //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
            for (Local_Data cn : contacts)
            { 
                JSONObject product_value = new JSONObject();
                product_value.put("order_number", cn.get_category_code());
                
                Order_number = cn.get_category_code();
               // product_value.put("order_date", cn.getCUSTOMER_ORDER_DATE());
               // product_value.put("order_take_by", "");
                product_value.put("customer_code", cn.get_category_id());
                
                product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
                
                customer_id = cn.get_category_id();
                product_value.put("latitude", cn.getlatitude());
                product_value.put("longitude", cn.getlongitude());
                product_value.put("signature_path", cn.getSignature_image());
                product_value.put("distributor_code", cn.getDISTRIBUTER_ID());
               // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
               // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
                //product_value.put("signature_image_name", uploadImage);
                product_value.put("device_code", Global_Data.device_id);

                
                if(cn.get_shedule_payment_mode().equalsIgnoreCase("Secondary Sales / Retail Sales"))
                {
               	 s = "Retail Sales";
                }
                else
                {
               	 s = cn.get_shedule_payment_mode();
                }
                product_value.put("order_type", s);
                // product_value.put("conference_code", cn.getconference_id());
                order.put(product_value);
                Log.d("count", "a" + ++a);
                //delete_order_no = cn.getORDER_NUMBER();
                List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts_return(cn.get_category_code());
                for (Local_Data cnp : contactsproduct) {
                    JSONObject item = new JSONObject();
                    item.put("order_number", cnp.get_category_code());
                    item.put("item_number", cnp.get_delivery_product_id());
                    item.put("total_return_qty", cnp.get_stocks_product_quantity());
                    item.put("MRP", cnp.getMRP());
                    item.put("amount", cnp.get_Claims_amount());

                    ++items_count;
                    //item.put("scheme_amount", cnp.get_Target_Text());
                    //item.put("item_number", cnp.get_delivery_product_id());
                    //item.put("discount_type", cnp.get_stocks_product_text());
                    product.put(item);
                    //Log.d("quantity","quantity"+cnp.getquantity());
                }
            }

//            for (int i = 0; i < 10; i++)
//            {
   //
   //
   //
//            }

            product_valuenew.put("return_orders", order);
            product_valuenew.put("return_order_products", product);
            product_valuenew.put("customers", customer);
            product_valuenew.put("imei_no", Global_Data.device_id);

            Log.d("customers",customer.toString());

            Log.d("return_orders", order.toString());

            Log.d("return_order_products",product.toString());

            // HashMap<String, String> params = new HashMap<String, String>();
            //params.put("token", json.toString());

            dialog.setMessage("Order Sync in Progress, Please Wait");
            dialog.setTitle("Anchor");
            dialog.setCancelable(false);
            dialog.show();

            // RequestQueue queue = Volley.newRequestQueue(getBaseContext());
           // PreferencesHelper Prefs = new PreferencesHelper(MasterSyncData.this);

            //String URL = Prefs.GetPreferences("URL");
            String  domain = context.getResources().getString(R.string.service_domain);
            Log.i("volley", "domain: " + domain);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"return_orders/save_return_orders", product_valuenew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);

                    String response_result = "";
                    //if (response.has("result")) {
                    try {
                        response_result = response.getString("result");

//                            if (response_result.equalsIgnoreCase("Device not found.")) {
//                                Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                                dialog.dismiss();
//                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (response_result.equalsIgnoreCase("Device not found.")) {
                        Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        dialog.dismiss();
                    } else {
//                    else
//                    {
//                        response_result = "data";
//                    }


                        Toast.makeText(context, "Return Order Sync Successfully", Toast.LENGTH_LONG).show();

                    mobile_numbers.clear();

                        if (!Global_Data.customer_MobileNumber.equalsIgnoreCase(null) && !Global_Data.customer_MobileNumber.equalsIgnoreCase("null") && !Global_Data.customer_MobileNumber.equalsIgnoreCase("") && !Global_Data.customer_MobileNumber.equalsIgnoreCase(" ")) {
                        mobile_numbers.add(Global_Data.customer_MobileNumber);
                    }

                        if (!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null") && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("") && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" ")) {
                        mobile_numbers.add(Global_Data.cus_MAnager_mobile);
                    }

                    String gaddress = "";
                    try {
                        if (Global_Data.address.equalsIgnoreCase("null")) {
                            gaddress = "";
                        } else {
                            gaddress = Global_Data.address;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                        String sms_body = "Dear " + Global_Data.CUSTOMER_NAME_NEW + " ," + "\n" + " " + Global_Data.order_retailer + " at " + Global_Data.CUSTOMER_ADDRESS_NEW + " at " + formattedDate + " has to return " + String.valueOf(items_count) + " items." + "\n\n" + " Thank you." + "\n" + " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME + "\n" + " " + gaddress;

                        // dbvoc.getDeleteTable("order_products");
                        //dbvoc.getDeleteTable("orders");

                        if (!mobile_numbers.isEmpty() && mobile_numbers.size() > 0) {


                        for (int i = 0; i < mobile_numbers.size(); i++) {
                            String message = sms_body;
                            String tempMobileNumber = mobile_numbers.get(i).toString();
                            //String tempMobileNumber = "8454858739";
                            //Global_Data.sendSMS("8454858739",sms_body,context);

                            //  Global_Data.sendSMS(tempMobileNumber, message,context);
                        }
                    }

                    Global_Data.GLOvel_GORDER_ID_RETURN = "";
                    List<Local_Data> contactsn = dbvoc.Getcustomer_email(customer_id);
                    //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
                        for (Local_Data cn : contactsn) {
                            email_adress = cn.get_Description();
                    }

                        String val = "";
                        dbvoc.updateCustomerby_CreateAt(val);
                        dbvoc.getDeleteTableorder_bycustomer_return(Global_Data.order_retailer.trim(), "Secondary Sales / Retail Sales", Order_number);
                        dbvoc.getDeleteTableorderproduct_bycustomer_return(Global_Data.order_retailer.trim(), "Secondary Sales / Retail Sales", Order_number);
                        dialog.dismiss();
                        final Dialog dialog = new Dialog(context);
                    dialog.setCancelable(false);

                        //tell the Dialog to use the dialog.xml as it's layout description
                        dialog.setContentView(R.layout.dialog);
                        dialog.setTitle("Order Status :");

                        TextView txt = (TextView) dialog.findViewById(R.id.txtOrderID);

                        txt.setText("Order is generated.");
                    TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
                    TextView txtEmail = (TextView) dialog.findViewById(R.id.txtEmail);

                        txtEmail.setText("Mail has been sent to " + email_adress);
                        if (!mobile_numbers.isEmpty() && mobile_numbers.size() > 0) {
                            txtMessage.setText("Sms Send Successfully");
                    }

                        ImageView dialogButton = (ImageView) dialog.findViewById(R.id.dialogButton);

                        dialogButton.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                Intent intentn = new Intent(context, MainActivity.class);
                                context.startActivity(intentn);
                                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                ((Activity) context).finish();

                            }
                        });

                        dialog.show();

                        //Intent intentn = new Intent(context, MainActivity.class);
                        //context.startActivity(intentn);
                        //context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //((Activity) context).finish();
//                    List<Local_Data> contacts = dbvoc.GetOrders(Global_Val.customer_id);
//                    for (Local_Data cn : contacts)
//                    {
//                        // JSONObject product_value = new JSONObject();
//                        //product_value.put("order_number", cn.getORDER_NUMBER());
                        //
//                        dbvoc.deleteOrderproductByOCID(cn.getORDER_NUMBER());
//                        dbvoc.deleteOrderTABLE_QuantityValue(cn.getORDER_NUMBER());
//                        dbvoc.deleteBarcode_ByOrder(cn.getORDER_NUMBER());
//                        dbvoc.deleteORDERSNEW(cn.getORDER_NUMBER());
                        //
//                    }


                    //dbvoc.deleteOrderByOCID(Global_Val.customer_id);
                    //dbvoc.getDeleteTable("DESIGN_CHECK");

//                    Intent i = new Intent(MasterSyncData.this, MyAndroidAppActivity.class);
//                    //				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.putExtra("user_name", user_name);
//                    i.putExtra("confrence_name", confrence_name);
//                    i.putExtra("BackFlag", "nothing");
//                    Global_Val.STOCK_SERVICE_FLAG = "TRUE";
                    //				i.putExtra("Barcode_Number", userInput.getText().toString());
                    //				i.putExtra("BackFlag","Barcode");
//                    startActivity(i);
//                    MasterSyncData.this.finish();
                }
                }
            },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(context,
                                "your internet connection is not working, saving locally. Please sync when Internet is available",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(context,
                                "Server AuthFailureError  Error",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(context,
                                "Server   Error",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(context,
                                "your internet connection is not working, saving locally. Please sync when Internet is available",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(context,
                                "ParseError   Error",
                                Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                    // finish();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            // queue.add(jsObjRequest);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (JSONException e) {
            e.printStackTrace();


        }


    } 
    
    public static void SYNCORDER_BYCustomerINSTI(Context contextn,String Quote_status){
        
      	 context = contextn;
        final ProgressDialog dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        String uploadImage = "";
        dbvoc = new DataBaseHelper(contextn);

       

        JSONObject jsonBody = new JSONObject();
       
        try {

            JSONArray CUSTOMERSN = new JSONArray();
            JSONArray product = new JSONArray();
            JSONArray order = new JSONArray();
            JSONObject product_valuenew = new JSONObject();

            int a = 0;
            String s = "";

            List<Local_Data> contacts_customer = dbvoc.getAllRetailer_cre();

            for (Local_Data cn : contacts_customer)
            {
                JSONObject product_value = new JSONObject();
                product_value.put("user_email", cn.getemail());
                product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
                product_value.put("name", cn.getCUSTOMER_NAME());
                product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
                product_value.put("address", cn.getADDRESS());
                product_value.put("street", cn.getSTREET());
                product_value.put("landmark", cn.getLANDMARK());
                product_value.put("pincode", cn.getPIN_CODE());
                product_value.put("mobile_no", cn.getMOBILE_NO());
                product_value.put("email", cn.getEMAIL_ADDRESS());
                product_value.put("status", cn.getSTATUS());
                product_value.put("state_code", cn.getSTATE_ID());
                product_value.put("city_code", cn.getCITY_ID());
                product_value.put("beat_code", cn.getBEAT_ID());
                product_value.put("vatin", cn.getvatin());
                product_value.put("latitude", cn.getlatitude());
                product_value.put("longitude", cn.getlongitude());
                CUSTOMERSN.put(product_value);

            }

            List<Local_Data> contacts = dbvoc.GetOrdersInSI(Global_Data.order_retailer.trim(),Global_Data.GLObalOrder_id,"Institutional Sales");
            //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
            for (Local_Data cn : contacts)
            {
                JSONObject product_value = new JSONObject();
                product_value.put("quote_number", cn.get_category_code());
                product_value.put("original_quote_number", cn.get_category_code());
                //product_value.put("aasm_state", "");
               // product_value.put("order_date", cn.getCUSTOMER_ORDER_DATE());
               // product_value.put("order_take_by", "");
                product_value.put("customer_code", cn.get_category_id());
                product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
                product_value.put("latitude", cn.getlatitude());
                product_value.put("longitude", cn.getlongitude());

                Order_number = cn.get_category_code();
                customer_id = cn.get_category_id();
               // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
               // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
                //product_value.put("signature_image_name", uploadImage);
                product_value.put("device_code", Global_Data.device_id);
                
                if(cn.get_shedule_payment_mode().equalsIgnoreCase("Secondary Sales / Retail Sales"))
                {
               	 s = "Retail Sales";
                }
                else
                {
               	 s = cn.get_shedule_payment_mode();
                }
               // product_value.put("order_type", s);
                // product_value.put("conference_code", cn.getconference_id());
                order.put(product_value);
                Log.d("count", "a" + ++a);
                //delete_order_no = cn.getORDER_NUMBER();
                List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts(cn.get_category_code());
                for (Local_Data cnp : contactsproduct) {

                    JSONObject item = new JSONObject();
                    item.put("quote_number", cnp.get_category_code());
                    //item.put("customer_name", cnp.get_custadr());
                    item.put("total_qty", cnp.get_stocks_product_quantity());
                    item.put("MRP", cnp.getMRP());
                    item.put("amount", cnp.get_Claims_amount());
                    item.put("scheme_amount", cnp.get_Target_Text());
                    item.put("item_number", cnp.get_delivery_product_id());
                    item.put("discount_type", cnp.get_stocks_product_text());
                    product.put(item);
                    //Log.d("quantity","quantity"+cnp.getquantity());

                }

            }

//            for (int i = 0; i < 10; i++)
//            {
   //
   //
   //
//            }

            product_valuenew.put("customers", CUSTOMERSN);
            product_valuenew.put("quotations", order);
            product_valuenew.put("quotation_products", product);
            product_valuenew.put("imei_no", Global_Data.device_id);

            Log.d("customers", CUSTOMERSN.toString());
            Log.d("quotations", order.toString());

            Log.d("quotation_products",product.toString());

            // HashMap<String, String> params = new HashMap<String, String>();
            //params.put("token", json.toString());

            dialog.setMessage("Order Sync in Progress, Please Wait");
            dialog.setTitle("Anchor");
            dialog.setCancelable(false);
            dialog.show();


            // RequestQueue queue = Volley.newRequestQueue(getBaseContext());
           // PreferencesHelper Prefs = new PreferencesHelper(MasterSyncData.this);


            //String URL = Prefs.GetPreferences("URL");
            String  domain = context.getResources().getString(R.string.service_domain);
            Log.i("volley", "domain: " + domain);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"quotations/save_quotations", product_valuenew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);

                    String response_result = "";
                    //if (response.has("result")) {
                    try {
                        response_result = response.getString("result");

//                            if (response_result.equalsIgnoreCase("Device not found.")) {
//                                Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                                dialog.dismiss();
//                            }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (response_result.equalsIgnoreCase("Device not found.")) {
                        Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        dialog.dismiss();
                    } else {

                    String val = "";
                    dbvoc.updateCustomerby_CreateAt(val);
                        Toast.makeText(context, "Order send for approval successfully", Toast.LENGTH_LONG).show();


                        // dbvoc.getDeleteTable("order_products");
                        //dbvoc.getDeleteTable("orders");
//                    List<Local_Data> contactsn = dbvoc.Getcustomer_email(customer_id);
//                    //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
//                    for (Local_Data cn : contactsn)
//                    {
//                   	 email_adress = cn.get_Description();
//                    }

                        //dbvoc.getDeleteTableorder_bycustomer_IN(Global_Data.order_retailer.trim(),"Institutional Sales",Order_number);
                        //dbvoc.getDeleteTableorderproduct_bycustomer_IN(Global_Data.order_retailer.trim(),"Institutional Sales",Order_number);
                        dialog.dismiss();
                        Intent intentn = new Intent(context, MainActivity.class);
                        context.startActivity(intentn);
                        ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        ((Activity) context).finish();
//   					final Dialog dialog = new Dialog(context);
//
//   					//tell the Dialog to use the dialog.xml as it's layout description
//   					dialog.setContentView(R.layout.dialog);
//   					dialog.setTitle("Order Status :");
//
//   					TextView txt = (TextView) dialog.findViewById(R.id.txtOrderID);
//
//   					txt.setText("Order is generated.");
//   					TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
//   					TextView txtEmail = (TextView) dialog.findViewById(R.id.txtEmail);
//   					
//   					txtEmail.setText("Mail has been sent to " + email_adress );
//
//                       
//   					ImageView dialogButton = (ImageView) dialog.findViewById(R.id.dialogButton);
//   									
//   					dialogButton.setOnClickListener(new OnClickListener() {
//   						@Override
//   						public void onClick(View v) {
//   							dialog.dismiss();
//   							
//   						
//   							
//   							//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//   							Intent intentn = new Intent(context, MainActivity.class);
//   							context.startActivity(intentn);
//   							((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//   							((Activity) context).finish();
//   						   
//   						}
//   					});
//
//   					dialog.show();

                        //Intent intentn = new Intent(context, MainActivity.class);
                        //context.startActivity(intentn);
                        //context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        //((Activity) context).finish();
//                    List<Local_Data> contacts = dbvoc.GetOrders(Global_Val.customer_id);
//                    for (Local_Data cn : contacts)
//                    {
//                        // JSONObject product_value = new JSONObject();
//                        //product_value.put("order_number", cn.getORDER_NUMBER());
                        //
//                        dbvoc.deleteOrderproductByOCID(cn.getORDER_NUMBER());
//                        dbvoc.deleteOrderTABLE_QuantityValue(cn.getORDER_NUMBER());
//                        dbvoc.deleteBarcode_ByOrder(cn.getORDER_NUMBER());
//                        dbvoc.deleteORDERSNEW(cn.getORDER_NUMBER());
                        //
//                    }


                    //dbvoc.deleteOrderByOCID(Global_Val.customer_id);
                    //dbvoc.getDeleteTable("DESIGN_CHECK");

//                    Intent i = new Intent(MasterSyncData.this, MyAndroidAppActivity.class);
//                    //				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    i.putExtra("user_name", user_name);
//                    i.putExtra("confrence_name", confrence_name);
//                    i.putExtra("BackFlag", "nothing");
//                    Global_Val.STOCK_SERVICE_FLAG = "TRUE";
                    //				i.putExtra("Barcode_Number", userInput.getText().toString());
                    //				i.putExtra("BackFlag","Barcode");
//                    startActivity(i);
//                    MasterSyncData.this.finish();
                }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);
                    Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            // queue.add(jsObjRequest);
            int socketTimeout = 30000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (JSONException e) {
            e.printStackTrace();


        }


    } 
    
    public static void SYNCORDER_BYCustomerINSTI_NEW(Context contextn, final String Quote_status){

        context = contextn;
       final ProgressDialog dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
       String uploadImage = "";
       dbvoc = new DataBaseHelper(contextn);

        try
        {
            AppLocationManager appLocationManager = new AppLocationManager(context);
            Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
            Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

            PlayService_Location PlayServiceManager = new PlayService_Location(context);

            if(PlayServiceManager.checkPlayServices(context))
            {
                Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

            }
            else
            if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
            {
                Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
            }

        }catch(Exception ex){ex.printStackTrace();}

      

       JSONObject jsonBody = new JSONObject();
      
       try {

    	  // Long randomPIN = System.currentTimeMillis();
           String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
    	   
           JSONArray product = new JSONArray();
           JSONArray order = new JSONArray();
           JSONObject product_valuenew = new JSONObject();

           int a = 0;
           String s = "";
          

           List<Local_Data> contacts = dbvoc.GetOrdersInSI(Global_Data.order_retailer.trim(),Global_Data.GLObalOrder_id,"Institutional Sales");
           //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
           for (Local_Data cn : contacts)
           {
               JSONObject product_value = new JSONObject();
               product_value.put("quote_number", PINString);
               product_value.put("original_quote_number", cn.get_category_code());
               product_value.put("aasm_state", Quote_status);
               product_value.put("customer_code", cn.get_category_id());
               product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
               product_value.put("latitude", Global_Data.GLOvel_LATITUDE);
               product_value.put("longitude", Global_Data.GLOvel_LONGITUDE);
               Order_number = cn.get_category_code();
               customer_id = cn.get_category_id();
              // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
              // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
               //product_value.put("signature_image_name", uploadImage);
               product_value.put("device_code", Global_Data.device_id);
               
               if(cn.get_shedule_payment_mode().equalsIgnoreCase("Secondary Sales / Retail Sales"))
               {
              	 s = "Retail Sales";
               }
               else
               {
              	 s = cn.get_shedule_payment_mode();
               }
              // product_value.put("order_type", s);
               // product_value.put("conference_code", cn.getconference_id());
               order.put(product_value);
               Log.d("count", "a" + ++a);
               //delete_order_no = cn.getORDER_NUMBER();
               List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts(cn.get_category_code());
               for (Local_Data cnp : contactsproduct) {

                   JSONObject item = new JSONObject();
                   item.put("quote_number", "QNO"+PINString);
                   //item.put("customer_name", cnp.get_custadr());
                   item.put("total_qty", cnp.get_stocks_product_quantity());
                   item.put("MRP", cnp.getMRP());
                   item.put("amount", cnp.get_Claims_amount());
                  // item.put("scheme_amount", cnp.get_Target_Text());
                   item.put("item_number", cnp.get_delivery_product_id());
                  // item.put("discount_type", cnp.get_stocks_product_text());
                   product.put(item);
                   //Log.d("quantity","quantity"+cnp.getquantity());

               }

           }

//           for (int i = 0; i < 10; i++)
//           {
  //
  //
  //
//           }

           product_valuenew.put("quotations", order);
           product_valuenew.put("quotation_products", product);
           product_valuenew.put("imei_no", Global_Data.device_id);

           Log.d("quotations", order.toString());

           Log.d("quotation_products",product.toString());

           // HashMap<String, String> params = new HashMap<String, String>();
           //params.put("token", json.toString());

           dialog.setMessage("Order Sync in Progress, Please Wait");
           dialog.setTitle("Anchor");
           dialog.setCancelable(false);
           dialog.show();


           // RequestQueue queue = Volley.newRequestQueue(getBaseContext());
          // PreferencesHelper Prefs = new PreferencesHelper(MasterSyncData.this);


           //String URL = Prefs.GetPreferences("URL");
           String  domain = context.getResources().getString(R.string.service_domain);
           Log.i("volley", "domain: " + domain);
           JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"quotations/save_quotations", product_valuenew, new Response.Listener<JSONObject>() {
               @Override
               public void onResponse(JSONObject response) {
                   Log.i("volley", "response: " + response);


                   String response_result = "";
                   // if (response.has("result")) {
                       try {
                           response_result = response.getString("result");

//                           if (response_result.equalsIgnoreCase("Device not found.")) {
//                               Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                               toast.setGravity(Gravity.CENTER, 0, 0);
//                               toast.show();
//                               dialog.dismiss();
//                           }

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }


                   if (response_result.equalsIgnoreCase("Device not found.")) {
                       Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                       toast.setGravity(Gravity.CENTER, 0, 0);
                       toast.show();
                       dialog.dismiss();
                   } else {
//                   else
//                   {
//                       response_result = "data";
//                   }


                       //String response_result = "";
                       if (response.has("message")) {
                           try {
                           response_result = response.getString("message");
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       } else {
                       response_result = "Order send for approval successfully";
                   }

                       Toast.makeText(context, response_result, Toast.LENGTH_LONG).show();

                       // dbvoc.getDeleteTable("order_products");
                       //dbvoc.getDeleteTable("orders");
//                   List<Local_Data> contactsn = dbvoc.Getcustomer_email(customer_id);
//                   //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
//                   for (Local_Data cn : contactsn)
//                   {
//                  	 email_adress = cn.get_Description();
//                   }

                       //dbvoc.getDeleteTableorder_bycustomer_IN(Global_Data.order_retailer.trim(),"Institutional Sales",Order_number);
                       //dbvoc.getDeleteTableorderproduct_bycustomer_IN(Global_Data.order_retailer.trim(),"Institutional Sales",Order_number);
                       if (Quote_status.equalsIgnoreCase("lost") || Quote_status.equalsIgnoreCase("ordered")) {
                           dbvoc.getDeleteTableorder_bycustomer_INN("Institutional Sales", Global_Data.GLObalOrder_id);
                           dbvoc.getDeleteTableorderproduct_bycustomer_INN("Institutional Sales", Global_Data.GLObalOrder_id);
                       }


                       dialog.dismiss();
                       Intent intentn = new Intent(context, MainActivity.class);
                       context.startActivity(intentn);
                       ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                       //((Activity) context).finish();
//  					final Dialog dialog = new Dialog(context);
//
//  					//tell the Dialog to use the dialog.xml as it's layout description
//  					dialog.setContentView(R.layout.dialog);
//  					dialog.setTitle("Order Status :");
//
//  					TextView txt = (TextView) dialog.findViewById(R.id.txtOrderID);
//
//  					txt.setText("Order is generated.");
//  					TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
//  					TextView txtEmail = (TextView) dialog.findViewById(R.id.txtEmail);
//  					
//  					txtEmail.setText("Mail has been sent to " + email_adress );
//
//                      
//  					ImageView dialogButton = (ImageView) dialog.findViewById(R.id.dialogButton);
//  									
//  					dialogButton.setOnClickListener(new OnClickListener() {
//  						@Override
//  						public void onClick(View v) {
//  							dialog.dismiss();
//  							
//  						
//  							
//  							//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//  							Intent intentn = new Intent(context, MainActivity.class);
//  							context.startActivity(intentn);
//  							((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//  							((Activity) context).finish();
//  						   
//  						}
//  					});
//
//  					dialog.show();

                       //Intent intentn = new Intent(context, MainActivity.class);
                       //context.startActivity(intentn);
                       //context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                       //((Activity) context).finish();
//                   List<Local_Data> contacts = dbvoc.GetOrders(Global_Val.customer_id);
//                   for (Local_Data cn : contacts)
//                   {
//                       // JSONObject product_value = new JSONObject();
//                       //product_value.put("order_number", cn.getORDER_NUMBER());
                       //
//                       dbvoc.deleteOrderproductByOCID(cn.getORDER_NUMBER());
//                       dbvoc.deleteOrderTABLE_QuantityValue(cn.getORDER_NUMBER());
//                       dbvoc.deleteBarcode_ByOrder(cn.getORDER_NUMBER());
//                       dbvoc.deleteORDERSNEW(cn.getORDER_NUMBER());
                       //
//                   }


                   //dbvoc.deleteOrderByOCID(Global_Val.customer_id);
                   //dbvoc.getDeleteTable("DESIGN_CHECK");

//                   Intent i = new Intent(MasterSyncData.this, MyAndroidAppActivity.class);
//                   //				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                   i.putExtra("user_name", user_name);
//                   i.putExtra("confrence_name", confrence_name);
//                   i.putExtra("BackFlag", "nothing");
//                   Global_Val.STOCK_SERVICE_FLAG = "TRUE";
                   //				i.putExtra("Barcode_Number", userInput.getText().toString());
                   //				i.putExtra("BackFlag","Barcode");
//                   startActivity(i);
//                   MasterSyncData.this.finish();
               }
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Log.i("volley", "error: " + error);
                   Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                   dialog.dismiss();
               }
           });

           RequestQueue requestQueue = Volley.newRequestQueue(context);
           // queue.add(jsObjRequest);
           int socketTimeout = 30000;//30 seconds - change to what you want
           RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
           jsObjRequest.setRetryPolicy(policy);
           requestQueue.add(jsObjRequest);

       } catch (JSONException e) {
           e.printStackTrace();


       }


   }

	public static void SyncDataToServercommon(final Context context)
	{
		System.gc();
		String reason_code = "";
		try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);




		    dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
	        dialog.setMessage("Please wait....");
	        dialog.setTitle("Anchor");
	        dialog.setCancelable(false);
	        dialog.show();

			 String domain = "";
			 String device_id = "";


            SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
            device_id = sp.getString("devid", "");
			 domain = context.getResources().getString(R.string.service_domain);



			 JsonObjectRequest jsObjRequest = null;
			 try
			 {



				 Log.d("Server url","Server url"+domain+"expenses_travles/save_travel_expenses");


				 JSONArray order = new JSONArray();
                 JSONArray fR = new JSONArray();
                 JSONArray COMP = new JSONArray();
                 JSONArray CLAIM = new JSONArray();
                 JSONArray COMPS = new JSONArray();
                 JSONArray PICTURE = new JSONArray();
				 //JSONObject product_value = new JSONObject();
				 JSONObject product_value_n = new JSONObject();
				 JSONArray product_imei = new JSONArray();

                 final DataBaseHelper dbvoc = new DataBaseHelper(context);

				 List<Local_Data> contacts = dbvoc.getAllRetailer_cre();
		          if(contacts.size() > 0)
		          {
		        	  Retailer_Flag = "true";
		          }
		          else
		          {
		        	  Retailer_Flag = "false";
		          }

		          for (Local_Data cn : contacts)
		          {
		             JSONObject product_value = new JSONObject();
		             product_value.put("user_email", cn.getemail());
		             product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
		             product_value.put("name", cn.getCUSTOMER_NAME());
		             product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
		             product_value.put("address", cn.getADDRESS());
		             product_value.put("street", cn.getSTREET());
		             product_value.put("landmark", cn.getLANDMARK());
		             product_value.put("pincode", cn.getPIN_CODE());
		             product_value.put("mobile_no", cn.getMOBILE_NO());
		             product_value.put("email", cn.getEMAIL_ADDRESS());
		             product_value.put("status", cn.getSTATUS());
		             product_value.put("state_code", cn.getSTATE_ID());
		             product_value.put("city_code", cn.getCITY_ID());
		             product_value.put("beat_code", cn.getBEAT_ID());
		             product_value.put("vatin", cn.getvatin());
                     product_value.put("latitude", cn.getlatitude());
                     product_value.put("longitude", cn.getlongitude());
					 order.put(product_value);

		          }


//                 List<Local_Data> confeed = dbvoc.getAllFeedback_CREATEDATE();
//                 if(confeed.size() > 0)
//                 {
//                     FEED_Flag = "true";
//                 }
//                 else
//                 {
//                     FEED_Flag = "false";
//                 }
//
//                 for (Local_Data cn : confeed)
//                 {
//                     JSONObject Feed = new JSONObject();
//                     Feed.put("customer_id", cn.getCust_Code());
//                     Feed.put("user_id", cn.getEMAIL_ADDRESS());
//                     Feed.put("date", cn.getC_Date());
//                     Feed.put("text", cn.get_Description());
//                     fR.put(Feed);
//                 }
//
//                 List<Local_Data> concomp = dbvoc.getAllComplaints_BYCUSTOMERID();
//                 if(concomp.size() > 0)
//                 {
//                     COMP_Flag = "true";
//                 }
//                 else
//                 {
//                     COMP_Flag = "false";
//                 }
//
//                 for (Local_Data cn : concomp)
//                 {
//                     JSONObject cm = new JSONObject();
//                     cm.put("customer_id", cn.getCust_Code());
//                     cm.put("user_id", cn.getEMAIL_ADDRESS());
//                     cm.put("date", cn.getC_Date());
//                     cm.put("text", cn.get_Description());
//                     COMP.put(cm);
//                 }
//
//                 List<Local_Data> conclaims = dbvoc.getAllClaims_BYCUSTOMERID();
//                 if(conclaims.size() > 0)
//                 {
//                     CLAIM_Flag = "true";
//                 }
//                 else
//                 {
//                     CLAIM_Flag = "false";
//                 }
//
//                 for (Local_Data cn : conclaims)
//                 {
//                     JSONObject cl = new JSONObject();
//                     cl.put("customer_id", cn.getCust_Code());
//                     cl.put("user_id", cn.getEMAIL_ADDRESS());
//                     cl.put("date", cn.getC_Date());
//                     cl.put("text", cn.get_Description());
//                     cl.put("amount", cn.get_Claims_amount());
//                     CLAIM.put(cl);
//                 }
//
//                 List<Local_Data> conccomss = dbvoc.getAllCOMPETITION_STOCKS_BYCUSTOMERID();
//                 if(conccomss.size() > 0)
//                 {
//                     COMPS_Flag = "true";
//                 }
//                 else
//                 {
//                     COMPS_Flag = "false";
//                 }
//
//                 for (Local_Data cn : conccomss)
//                 {
//                     JSONObject cll = new JSONObject();
//                     cll.put("customer_id", cn.getCust_Code());
//                     cll.put("user_id", cn.getEMAIL_ADDRESS());
//                     cll.put("category_id", cn.get_category_id());
//                     cll.put("product_id", cn.get_product_code());
//                     cll.put("product_variant_id", cn.get_variants_code());
//                     cll.put("competition_product_text", cn.get_Description());
//
//
//                     COMPS.put(cll);
//                 }
//
//                 List<Local_Data> concphoto= dbvoc.getAllPICTURESD_BYCUSTOMERID();
//                 if(concphoto.size() > 0)
//                 {
//                     PICTURE_Flag = "true";
//                 }
//                 else
//                 {
//                     PICTURE_Flag = "false";
//                 }
//
//                 for (Local_Data cn : concphoto)
//                 {
//                     JSONObject picture = new JSONObject();
//                     picture.put("customer_id", cn.getCust_Code());
//                     picture.put("user_id", cn.getEMAIL_ADDRESS());
//                     picture.put("media_type", cn.get_mediaType());
//                     picture.put("media_text", cn.get_mediaDisc());
//                     picture.put("location", cn.get_mediaUrl());
//
//
//
//                     PICTURE.put(picture);
//                 }


//		          if(Retailer_Flag.equalsIgnoreCase("true") || FEED_Flag.equalsIgnoreCase("true") || COMP_Flag.equalsIgnoreCase("true")|| CLAIM_Flag.equalsIgnoreCase("true") || COMPS_Flag.equalsIgnoreCase("true") || PICTURE_Flag.equalsIgnoreCase("true"))
//		          {
//                      Final_Flag_N += " "+"Retailer";
//		          }
//                 else
//                  {
//                      Final_Flag_N = "";
//                  }

                 if(Retailer_Flag.equalsIgnoreCase("true"))
                 {
                     Final_Flag_N += " "+"Retailer";
                 }
                 else
                 {
                     Final_Flag_N = "";
                 }

		          if(Final_Flag_N.equalsIgnoreCase(""))
		          {
		              Toast.makeText(context, "No record found for sync.", Toast.LENGTH_SHORT).show();
		              dialog.dismiss();
		          }
		          else {


				// product_imei.put(Global_Data.device_id);
				  product_value_n.put("customers", order);
                 // product_value_n.put("customer_service_feedbacks", fR);
                  // product_value_n.put("customer_service_complaints", COMP);
                  // product_value_n.put("customer_service_claims", CLAIM);
                  // product_value_n.put("customer_service_competition_stocks", COMPS);
                  // product_value_n.put("customer_service_media", PICTURE);
                      // product_value_n.put("imei_no", Global_Data.device_id);
                      product_value_n.put("imei_no", Global_Data.device_id);

				 Log.d("customers",product_value_n.toString());
				 //Log.d("expenses_travels",product_value_n.toString());

//
//
//				 //product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//				// product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//
				  jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"customers/create_customer", product_value_n, new Response.Listener<JSONObject>() {
		                @Override
		                public void onResponse(JSONObject response) {
		                    Log.i("volley", "response: " + response);

		                    Log.d("jV", "JV length" + response.length());
			                  //JSONObject json = new JSONObject(new JSONTokener(response));
							  try{

								  String response_result = "";
							      if(response.has("message"))
							      {
							          response_result = response.getString("message");
							      }
							      else
							      {
							          response_result = "data";
							      }


							      if(response_result.equalsIgnoreCase("Customer created successfully.")) {

							    	  dialog.dismiss();
                                      List<Local_Data> contacts = dbvoc.getAllRetailer_cre();
                                       for (Local_Data cn : contacts)
                                      {
                                          //dbvoc.updateCustomerby_Createid(cn.getLEGACY_CUSTOMER_CODE());
                                          dbvoc.deletesalesupdatebyID(cn.getCUSTOMER_NAME(),cn.getCUSTOMER_SHOPNAME());
                                      }
                                      Toast toast = Toast.makeText(context,response_result, Toast.LENGTH_SHORT);

                                      Global_Data.SYNC_SERVICE_FLAG = "TRUE";
								   	  Toast.makeText(context, response_result, Toast.LENGTH_LONG).show();
							         Intent a = new Intent(context,MainActivity.class);
							          context.startActivity(a);
                                      ((Activity)context).finish();


							      }
							      else
								   {

							    	  dialog.dismiss();
							         // Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
							    	  Toast toast = Toast.makeText(context,response_result, Toast.LENGTH_SHORT);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();
							          Intent a = new Intent(context,MainActivity.class);
							          context.startActivity(a);
                                       ((Activity)context).finish();


							      }

							    //  finish();
							      // }

							      // output.setText(data);
							  }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


							  dialog.dismiss();
			                  dialog.dismiss();




		                }
		            }, new Response.ErrorListener() {
		                @Override
		                public void onErrorResponse(VolleyError error) {
		                    Log.i("volley", "error: " + error);
		                    Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
		                    dialog.dismiss();
		                }
		            });

				  RequestQueue requestQueue = Volley.newRequestQueue(context);

			        int socketTimeout = 300000;//30 seconds - change to what you want
			        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			        jsObjRequest.setRetryPolicy(policy);
			        // requestQueue.se
			        //requestQueue.add(jsObjRequest);
			        jsObjRequest.setShouldCache(false);
			        requestQueue.getCache().clear();
			        requestQueue.add(jsObjRequest);
		        }
			 }catch(Exception e)
			 {
				 e.printStackTrace();
				 dialog.dismiss();
			 }





			//createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
			//createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}



			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests

                }
			 */

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DATA", e.getMessage());
		}
	}

    public static void SyncDataToServerCustomer(final Context context)
    {
        System.gc();
        final Calendar c = Calendar.getInstance();
        String reason_code = "";
        try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);




            dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please wait....");
            dialog.setTitle("Anchor");
            dialog.setCancelable(false);
            dialog.show();

            String domain = "";
            String device_id = "";


            SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
            device_id = sp.getString("devid", "");

            domain = context.getResources().getString(R.string.service_domain);



            JsonObjectRequest jsObjRequest = null;
            try
            {



                Log.d("Server url","Server url"+domain+"customer_service_feedbacks");


                JSONArray order = new JSONArray();
                JSONArray CUSTOMERSN = new JSONArray();
                JSONArray fR = new JSONArray();
                JSONArray COMP = new JSONArray();
                JSONArray CLAIM = new JSONArray();
                JSONArray COMPS = new JSONArray();
                JSONArray PICTURE = new JSONArray();
                JSONArray MARKET_SURVEY = new JSONArray();
                JSONArray TRAVEL_EXPENSES = new JSONArray();
                JSONArray MISC_EXPENSES = new JSONArray();
                JSONArray CALENDER_DATA = new JSONArray();
                //JSONObject product_value = new JSONObject();
                JSONObject product_value_n = new JSONObject();
                JSONArray product_imei = new JSONArray();

                final DataBaseHelper dbvoc = new DataBaseHelper(context);

                List<Local_Data> contacts = dbvoc.getAllRetailer_cre();
                if(contacts.size() > 0)
                {
                    Retailer_Flag = "true";
                }
                else
                {
                    Retailer_Flag = "false";
                }

                for (Local_Data cn : contacts)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("user_email", cn.getemail());
                    product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
                    product_value.put("name", cn.getCUSTOMER_NAME());
                    product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
                    product_value.put("address", cn.getADDRESS());
                    product_value.put("street", cn.getSTREET());
                    product_value.put("landmark", cn.getLANDMARK());
                    product_value.put("pincode", cn.getPIN_CODE());
                    product_value.put("mobile_no", cn.getMOBILE_NO());
                    product_value.put("email", cn.getEMAIL_ADDRESS());
                    product_value.put("status", cn.getSTATUS());
                    product_value.put("state_code", cn.getSTATE_ID());
                    product_value.put("city_code", cn.getCITY_ID());
                    product_value.put("beat_code", cn.getBEAT_ID());
                    product_value.put("vatin", cn.getvatin());
                    product_value.put("latitude", cn.getlatitude());
                    product_value.put("longitude", cn.getlongitude());
                    CUSTOMERSN.put(product_value);

                }


                List<Local_Data> market_s = dbvoc.Get_SURVEY_ANSWERS();
                if(market_s.size() > 0)
                {
                    market_survey_Flag = "true";
                }
                else
                {
                    market_survey_Flag = "false";
                }

                for (Local_Data cn : market_s)
                {
                    JSONObject markets = new JSONObject();
                   // markets.put("code", cn.getCode());
                    markets.put("user_email", cn.getuser_email());
                    markets.put("answer_date", cn.getanswer_date());
                    markets.put("survey_code", cn.getsurvey_code());
                    markets.put("customer_code", cn.getCust_Code());
                    markets.put("question_code", cn.getquestion_code());
                    markets.put("customer_choice", cn.getcustomer_choice());
                    markets.put("latitude", cn.getlatitude());
                    markets.put("longitude", cn.getlongitude());

                    MARKET_SURVEY.put(markets);
                }

                List<Local_Data> tr_ex = dbvoc.getAllTravelExpenses();
                if(tr_ex.size() > 0)
                {
                    travel_expenses_Flag = "true";
                }
                else
                {
                    travel_expenses_Flag = "false";
                }

                for (Local_Data cn : tr_ex)
                {
                    JSONObject tt = new JSONObject();
                    tt.put("code", cn.getCode());
                    tt.put("user_email", cn.getuser_email());
                    tt.put("travel_from", cn.get_travel_from());
                    tt.put("travel_to", cn.get_travel_to());
                    tt.put("travel_date", cn.get_travel_date());
                    tt.put("travel_mode", cn.get_travel_mode());
                    tt.put("travel_cost", cn.get_travel_cost());
                    tt.put("travel_text", cn.get_travel_text());

                    TRAVEL_EXPENSES.put(tt);
                }


                List<Local_Data> misc_ex = dbvoc.getAllMisceExpenses();
                if(misc_ex.size() > 0)
                {
                    misc_expenses_Flag = "true";
                }
                else
                {
                    misc_expenses_Flag = "false";
                }

                for (Local_Data cn : misc_ex)
                {
                    JSONObject misc = new JSONObject();
                    misc.put("code", cn.getCode());
                    misc.put("user_email", cn.getuser_email());
                    misc.put("misc_date", cn.getmisc_date());
                    misc.put("misc_amount", cn.getmisc_cost());
                    misc.put("misc_text", cn.getmisc_text());

                    MISC_EXPENSES.put(misc);
                }

                List<Local_Data> calender = dbvoc.getCalender_EventAllNEW();
                if(calender.size() > 0)
                {
                    calenderdata_Flag = "true";
                }
                else
                {
                    calenderdata_Flag = "false";
                }

                for (Local_Data cn : calender)
                {
                    JSONObject calenderdata = new JSONObject();
                    calenderdata.put("code", cn.getcalender_id());
                    calenderdata.put("user_email", cn.getuser_email());
                    calenderdata.put("entry_type", cn.getcalender_type());
                    calenderdata.put("from_date", cn.getfrom_date());
                    calenderdata.put("to_date", cn.getto_date());
                    calenderdata.put("details", cn.getcalender_details());
                    calenderdata.put("latitude", cn.getlatitude());
                    calenderdata.put("longitude", cn.getlongitude());
                    calenderdata.put("flag", cn.getcalender_delete_flag());

                    CALENDER_DATA.put(calenderdata);
                }


                List<Local_Data> confeed = dbvoc.getAllFeedback_CREATEDATE();
                if(confeed.size() > 0)
                {
                    FEED_Flag = "true";
                }
                else
                {
                    FEED_Flag = "false";
                }

                for (Local_Data cn : confeed)
                {
                    JSONObject Feed = new JSONObject();
                    Feed.put("code", cn.getCode());
                    Feed.put("customer_code", cn.getCust_Code());
                    Feed.put("user_email", cn.getEMAIL_ADDRESS());
                    Feed.put("date", cn.getC_Date());
                    Feed.put("text", cn.get_Description());
                    Feed.put("latitude", cn.getlatitude());
                    Feed.put("longitude", cn.getlongitude());
                    fR.put(Feed);
                }

                List<Local_Data> concomp = dbvoc.getAllComplaints_BYCUSTOMERID();
                if(concomp.size() > 0)
                {
                    COMP_Flag = "true";
                }
                else
                {
                    COMP_Flag = "false";
                }

                for (Local_Data cn : concomp)
                {
                    JSONObject cm = new JSONObject();
                    cm.put("code", cn.getCode());
                    cm.put("customer_code", cn.getCust_Code());
                    cm.put("user_email", cn.getEMAIL_ADDRESS());
                    cm.put("date", cn.getC_Date());
                    cm.put("text", cn.get_Description());
                    cm.put("latitude", cn.getlatitude());
                    cm.put("longitude", cn.getlongitude());
                    COMP.put(cm);
                }

                List<Local_Data> conclaims = dbvoc.getAllClaims_BYCUSTOMERID();
                if(conclaims.size() > 0)
                {
                    CLAIM_Flag = "true";
                }
                else
                {
                    CLAIM_Flag = "false";
                }

                for (Local_Data cn : conclaims)
                {
                    JSONObject cl = new JSONObject();
                   cl.put("code", cn.getCode());
                    cl.put("customer_code", cn.getCust_Code());
                    cl.put("user_email", cn.getEMAIL_ADDRESS());
                    cl.put("date", cn.getC_Date());
                    cl.put("text", cn.get_Description());
                    cl.put("amount", cn.get_Claims_amount());
                    cl.put("latitude", cn.getlatitude());
                    cl.put("longitude", cn.getlongitude());
                    CLAIM.put(cl);
                }

                List<Local_Data> conccomss = dbvoc.getAllCOMPETITION_STOCKS_BYCUSTOMERID();
                if(conccomss.size() > 0)
                {
                    COMPS_Flag = "true";
                }
                else
                {
                    COMPS_Flag = "false";
                }

                for (Local_Data cn : conccomss)
                {
                    JSONObject cll = new JSONObject();
                    cll.put("code", cn.getCode());
                    cll.put("customer_code", cn.getCust_Code());
                    cll.put("user_email", cn.getEMAIL_ADDRESS());
                    //cll.put("category_id", cn.get_category_id());
                   // cll.put("product_id", cn.get_product_code());
                    cll.put("product_code", cn.get_variants_code());
                    cll.put("competition_product_text", cn.get_Description());
                    cll.put("competition_product_quantity", cn.get_stocks_product_quantity());
                    cll.put("latitude", cn.getlatitude());
                    cll.put("longitude", cn.getlongitude());


                    COMPS.put(cll);
                }

//                List<Local_Data> concphoto= dbvoc.getAllPICTURESD_BYCUSTOMERID();
//                if(concphoto.size() > 0)
//                {
//                    PICTURE_Flag = "true";
//                }
//                else
//                {
//                    PICTURE_Flag = "false";
//                }
//
//                for (Local_Data cn : concphoto)
//                {
//                    JSONObject picture = new JSONObject();
//                    picture.put("customer_code", cn.getCust_Code());
//                    picture.put("user_email", cn.getEMAIL_ADDRESS());
//                    picture.put("media_type", cn.get_mediaType());
//                    picture.put("media_text", cn.get_mediaDisc());
//                    picture.put("location", cn.get_mediaUrl());
//                    PICTURE.put(picture);
//                }


                if(Retailer_Flag.equalsIgnoreCase("true") || FEED_Flag.equalsIgnoreCase("true") || COMP_Flag.equalsIgnoreCase("true")|| CLAIM_Flag.equalsIgnoreCase("true") || COMPS_Flag.equalsIgnoreCase("true") ||  travel_expenses_Flag.equalsIgnoreCase("true") ||  misc_expenses_Flag.equalsIgnoreCase("true") ||  calenderdata_Flag.equalsIgnoreCase("true") ||  market_survey_Flag.equalsIgnoreCase("true"))
                {
                    Final_Flag_N += " "+"Retailer";
                }
                else
                {
                    Final_Flag_N = "";
                }

//                if(Final_Flag_N.equalsIgnoreCase(""))
//                {
//                    Toast.makeText(context, "No record found for sync.", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                }
//                else {

                    // product_imei.put(Global_Data.device_id);
                     // product_value_n.put("customers", order);
                 product_value_n.put("customers", CUSTOMERSN);
                 product_value_n.put("survey_answers", MARKET_SURVEY);
                 product_value_n.put("expenses_travels", TRAVEL_EXPENSES);
                 product_value_n.put("expenses_miscs", MISC_EXPENSES);
                 product_value_n.put("calender_entries", CALENDER_DATA);
                 product_value_n.put("feedbacks", fR);
                 product_value_n.put("complaints", COMP);
                 product_value_n.put("claims", CLAIM);
                 product_value_n.put("stocks", COMPS);
                // product_value_n.put("customer_service_media", PICTURE);
                product_value_n.put("imei_no", Global_Data.device_id);
                product_value_n.put("emp_code", Global_Data.emp_code);
                product_value_n.put("email", Global_Data.GLOvel_USER_EMAIL);

               // Log.d("customers",product_value_n.toString());

                Log.d("customers Service",product_value_n.toString());

                    //Log.d("expenses_travels",product_value_n.toString());



                    Log.d("domain","domain"+domain+"uploads/upload_masters_data");
//
                    jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"uploads/upload_masters_data", product_value_n, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("volley", "response: " + response);

                            Log.d("jV", "JV length" + response.length());
                            //JSONObject json = new JSONObject(new JSONTokener(response));
                            try{

                                String response_result = "";
//                                if(response.has("result"))
//                                {
                                    response_result = response.getString("result");
//                                    if(response_result.equalsIgnoreCase("Device not found."))
//                                    {
//                                        Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                                        toast.setGravity(Gravity.CENTER, 0, 0);
//                                        toast.show();
//                                        dialog.dismiss();
//                                    }
//                                }
//                                else
//                                {
//                                    response_result = "data";
//                                }


                                if (response_result.equalsIgnoreCase("Device not found.")) {
                                    Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    dialog.dismiss();
                                } else if (response_result.equalsIgnoreCase("Created Data Successfully.")) {

                                    dialog.dismiss();

                                    String val = "";

                                    dbvoc.getDeleteTable("expenses_travels");
                                    dbvoc.getDeleteTable("expenses_miscs");
                                    dbvoc.getDeleteTable("Survey_Answers");
                                    dbvoc.getDeleteTablecalender_event_BYFLAG("YES");

                                    dbvoc.updateCustomerby_CreateAt(val);
                                    dbvoc.updateORDER_feedback(val);
                                    dbvoc.updateORDER_claims(val);
                                    dbvoc.updateORDER_complaints(val);
                                    dbvoc.updateORDER_stocks(val);
                                  // dbvoc.updateORDER_feedback(val);
                                    dbvoc.updateORDER_calenderevent(val);

                                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                                    SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm:ss");

                                    String Current_Date = sdf.format(c.getTime());
                                    String Current_Time = sdf_time.format(c.getTime());


                                    dbvoc.getDeleteTable("order_details");
                                    LoginDataBaseAdapter loginDataBaseAdapter = new LoginDataBaseAdapter(context);
                                    loginDataBaseAdapter = loginDataBaseAdapter.open();

                                    loginDataBaseAdapter.insert_order_details(Current_Date, Current_Time);

                                    Calendar calendar = Calendar.getInstance();

                                    int yearnew = calendar.get(Calendar.YEAR);
                                    int monthnew = calendar.get(Calendar.MONTH);
                                    int daynew = calendar.get(Calendar.DAY_OF_MONTH);

                                   int month = calendar.get(Calendar.MONTH);
                                   int  year = calendar.get(Calendar.YEAR);

                                    Formatter fmt = new Formatter();
                                    // fmt.format("%tB %tb %tm", calendar, calendar, calendar);

                                    String mm = fmt.format("%tB", calendar).toString();


                                    Calendar calendarold = Calendar.getInstance();
                                    calendarold.add(calendarold.MONTH, -1);
                                    int yearold = calendarold.get(calendarold.YEAR);
                                    int monthold = calendarold.get(calendarold.MONTH);
                                    int dayold = calendarold.get(calendarold.DAY_OF_MONTH);

                                    Formatter fmtt = new Formatter();
                                    // fmt.format("%tB %tb %tm", calendar, calendar, calendar);

                                    String mmm = fmtt.format("%tB", calendarold).toString();

                                    Log.d("year", "YEAR"+yearnew);
                                    Log.d("yearold", "YEAROLD"+yearold);
                                    Log.d("month", "MONTH"+monthnew);
                                    Log.d("monthold", "MONTHOLD"+monthold);
                                    Log.d("month String", "MONTH String"+mm);
                                    Log.d("monthold String", "MONTHOLD String"+mmm);

                                    String cureent_month = mm+"-"+yearnew;
                                    String old_month = mmm+"-"+yearold;

                                    Date current = new Date();
                                    //create a date one day after current date



                                    try
                                    {
                                        List<Local_Data> contacts2 = dbvoc.getAllCalender_EventValue();

                                        if(contacts2.size() != 0 )
                                        {
                                            for (Local_Data cn : contacts2) {

                                                String from_date = cn.getfrom_date();


                                                //create date object
                                                Date next = new Date(from_date);

                                                    if(next.after(current)){
                                                        System.out.println("The date is future day");
                                                    } else {
                                                        if(from_date.contains("-"))
                                                        {
                                                            String from_date_Array [] = from_date.split("-");
                                                            String final_fromdate = from_date_Array[1]+"-"+from_date_Array[2];

                                                            if(final_fromdate.equalsIgnoreCase(cureent_month) || final_fromdate.equalsIgnoreCase(old_month))
                                                            {
                                                                Log.d("From Data", "From Date"+cn.getfrom_date());
                                                            }
                                                            else
                                                            {
                                                                dbvoc.getDeleteTableCalenderEntity(cn.getfrom_date());
                                                            }

                                                        }
                                                	}
                                            }
                                        }
                                    }
                                    catch(Exception ex){
                                        ex.printStackTrace();
                                    }

                                    Toast.makeText(context, response_result, Toast.LENGTH_LONG).show();
                                    Intent a = new Intent(context,AllOrders_Sync.class);
                                    context.startActivity(a);
                                    ((Activity)context).finish();


                                }
                                else
                                {

                                    dialog.dismiss();
                                    Toast.makeText(context, response_result, Toast.LENGTH_LONG).show();
                                    // Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
//                                    String val = "";
//
//                                    dbvoc.getDeleteTable("expenses_travels");
//                                    dbvoc.getDeleteTable("expenses_miscs");
//                                    dbvoc.getDeleteTable("Survey_Answers");
//                                    dbvoc.getDeleteTablecalender_event_BYFLAG("YES");
//
//                                    dbvoc.updateORDER_feedback(val);
//                                    dbvoc.updateORDER_claims(val);
//                                    dbvoc.updateORDER_complaints(val);
//                                    dbvoc.updateORDER_stocks(val);
//
//                                    Toast toast = Toast.makeText(context,response_result, Toast.LENGTH_SHORT);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
//                                    Intent a = new Intent(context,MainActivity.class);
//                                    context.startActivity(a);
//                                    ((Activity)context).finish();


                                }

                                //  finish();
                                // }

                                // output.setText(data);
                            }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


                            dialog.dismiss();
                            dialog.dismiss();




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("volley", "error: " + error);
                            Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(context);

                    int socketTimeout = 300000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsObjRequest.setRetryPolicy(policy);
                    // requestQueue.se
                    //requestQueue.add(jsObjRequest);
                    jsObjRequest.setShouldCache(false);
                    requestQueue.getCache().clear();
                    requestQueue.add(jsObjRequest);
                //}
            }catch(Exception e)
            {
                e.printStackTrace();
                dialog.dismiss();
            }

            //createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
            //createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}



			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests

                }
			 */

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("DATA", e.getMessage());
        }
    }

    public static void SyncMediaData(final Context context,String media_type,String media_code,String discription,String CUSTOMER_ID,String GLOvel_USER_EMAIL,String file_name)
    {
        System.gc();
        String reason_code = "";
        try {

            dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please wait....");
            dialog.setTitle("Anchor");
            dialog.setCancelable(false);
            dialog.show();

            String domain = "";
            String device_id = "";


            SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
            device_id = sp.getString("devid", "");

            domain = context.getResources().getString(R.string.service_domain);



            JsonObjectRequest jsObjRequest = null;
            try
            {



                Log.d("Server url","Server url"+domain+"customer_service_media");

                JSONArray PICTURE = new JSONArray();
                //JSONObject product_value = new JSONObject();
                JSONObject product_value_n = new JSONObject();
                JSONArray product_imei = new JSONArray();

                final DataBaseHelper dbvoc = new DataBaseHelper(context);

                    JSONObject picture = new JSONObject();
                    picture.put("customer_code",CUSTOMER_ID);
                    picture.put("user_email", GLOvel_USER_EMAIL);
                    picture.put("media_type", media_type);
                    picture.put("media_text", discription);
                    picture.put("media_data", media_code);
                    picture.put("filename", file_name);
                    PICTURE.put(picture);

                product_value_n.put("customer_service_media", PICTURE);
                product_value_n.put("imei_no", Global_Data.device_id);

                Log.d("customers Service",product_value_n.toString());

                jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"customer_service_media", product_value_n, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("volley", "response: " + response);

                            Log.d("jV", "JV length" + response.length());
                            //JSONObject json = new JSONObject(new JSONTokener(response));
                            try{

                                String response_result = "";
                                if(response.has("message"))
                                {
                                    response_result = response.getString("message");
                                }
                                else
                                {
                                    response_result = "data";
                                }


                                if(response_result.equalsIgnoreCase("Customer created successfully.")) {

                                    dialog.dismiss();
                                    Toast.makeText(context, response_result, Toast.LENGTH_LONG).show();
                                    Intent a = new Intent(context,Order.class);
                                    context.startActivity(a);
                                    ((Activity)context).finish();


                                }
                                else
                                {

                                    dialog.dismiss();
                                    Toast toast = Toast.makeText(context,response_result, Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
//                                    Intent a = new Intent(context,Order.class);
//                                    context.startActivity(a);
//                                    ((Activity)context).finish();
                                }

                                //  finish();
                                // }

                                // output.setText(data);
                            }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


                            dialog.dismiss();
                            dialog.dismiss();




                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("volley", "error: " + error);
                            Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(context);

                    int socketTimeout = 300000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsObjRequest.setRetryPolicy(policy);
                    // requestQueue.se
                    //requestQueue.add(jsObjRequest);
                    jsObjRequest.setShouldCache(false);
                    requestQueue.getCache().clear();
                    requestQueue.add(jsObjRequest);

            }catch(Exception e)
            {
                e.printStackTrace();
                dialog.dismiss();
            }





            //createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
            //createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}



			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests

                }
			 */

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("DATA", e.getMessage());
        }
    }

    public static void GetNewLaunch_Data(Context contextn)
    {
        context = contextn;
        SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        device_id = sp.getString("devid", "");
        loginDataBaseAdapter=new LoginDataBaseAdapter(context);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        dbvoc = new DataBaseHelper(context);

        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Anchor");
        dialog.setCancelable(false);
        dialog.show();

        domain = context.getResources().getString(R.string.service_domain);

        // Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }F



        Log.d("Server url","Server url"+domain+"menus/sync_masters?imei_no="+device_id);

        StringRequest stringRequest = null;
        stringRequest = new StringRequest(domain+"menus/sync_masters?imei_no="+device_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //showJSON(response);
                        // Log.d("jV", "JV" + response);
                        Log.d("jV", "JV length" + response.length());
                        // JSONObject person = (JSONObject) (response);
                        try {
                            JSONObject json = new JSONObject(new JSONTokener(response));
                            try{

                                String response_result = "";
                                if(json.has("result"))
                                {
                                    response_result = json.getString("result");
                                }
                                else
                                {
                                    response_result = "data";
                                }


                                if(response_result.equalsIgnoreCase("Data is up to date.")) {

                                    Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                }
                                else
                                if(response_result.equalsIgnoreCase("Device not registered")) {

                                    Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                }
                                else {

                                    JSONArray users = json.getJSONArray("users");
//
                                    Log.i("volley", "response reg users Length: " + users.length());
//
                                    Log.d("users", "users" + users.toString());
//
                                    //

                                    for (int i = 0; i < users.length(); i++) {

                                        JSONObject jsonObject = users.getJSONObject(i);

                                        Config.YOUTUBE_VIDEO_CODE = jsonObject.getString("user_name");

                                        Config.YOUTUBE_VIDEO_DATE = jsonObject.getString("user_name");
                                        Config.YOUTUBE_VIDEO_DISCRIPTION = jsonObject.getString("user_name");

//                                        loginDataBaseAdapter.insertEntry(jsonObject.getString("user_name"), jsonObject.getString("encrypted_password"), jsonObject.getString("date_of_joining"), jsonObject.getString("mob_no"), jsonObject.getString("email"), jsonObject.getString("reporting_to"),
//                                                jsonObject.getString("first_name"), jsonObject.getString("last_name"),"", "", "", "", "",
//                                                "", Device_id, "", jsonObject.getString("address"),"","", jsonObject.getString("id"));
                                    }

                                    Intent launch = new Intent(context,Youtube_Player_Activity.class);
                                    context.startActivity(launch);

                                    dialog.dismiss();
                                    //finish();

                                }

                                //  finish();
                                // }

                                // output.setText(data);
                            }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


                            dialog.dismiss();
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
                            Toast.makeText(context,
                                    "Network Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(context,
                                    "Server AuthFailureError  Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ServerError) {
                            Toast.makeText(context,
                                    "Server   Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof NetworkError) {
                            Toast.makeText(context,
                                    "Network   Error",
                                    Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            Toast.makeText(context,
                                    "ParseError   Error",
                                    Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                        dialog.dismiss();
                        // finish();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private static class  LongOperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... response) {
            try
            {
                final Calendar c = Calendar.getInstance();
                JSONObject json = new JSONObject(final_response);
                try{

                    String response_result = "";
                    if(json.has("result"))
                    {
                        response_result = json.getString("result");
                    }
                    else
                    {
                        response_result = "data";
                    }

                    if(response_result.equalsIgnoreCase("Data is up to date.")) {
                        //activity = context;

                        final String finalResponse_result = response_result;
                        ((Activity)context).runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context.getApplicationContext(), finalResponse_result, Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                getTargetDataservice(context);
                           }
                        });
                    }
                    else
                    if(response_result.equalsIgnoreCase("Device not registered")) {

                        final String finalResponse_result1 = response_result;
                        ((Activity)context).runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context.getApplicationContext(), finalResponse_result1, Toast.LENGTH_LONG).show();
                            }
                        });

                       // Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                    }
                    else {

                        JSONArray items = json.getJSONArray("products");
                        JSONArray customers = json.getJSONArray("customers");
                        //TODO DONE for distributor problem
                        JSONArray distributors = json.getJSONArray("distributors");

                        JSONArray reasons = json.getJSONArray("reasons");

                        JSONArray states = json.getJSONArray("states");
                        JSONArray cities = json.getJSONArray("cities");
                        JSONArray beats = json.getJSONArray("beats");
                        JSONArray schemes = json.getJSONArray("schemes");
                        JSONArray Survey_Questions = json.getJSONArray("survey_questions");

                        JSONArray distributor_beats = json.getJSONArray("distributor_beats");

                        JSONArray credit_profile = json.getJSONArray("credit_profiles");

                        JSONArray logo_img = json.getJSONArray("customer_logo_splash");

                        JSONArray l1_contacts = json.getJSONArray("l1_contacts");

                        JSONArray label_acc = json.getJSONArray("label_changes");

                        JSONArray warehouse = json.getJSONArray("warehouses");

                        JSONArray Order_Category = json.getJSONArray("order_categories");

                        JSONArray users_emp = json.getJSONArray("users");

                        JSONArray payment_terms = json.getJSONArray("payment_terms");

                        dbvoc.getDeleteTable("cities");
                        dbvoc.getDeleteTable("states");
                        dbvoc.getDeleteTable("beats");
                        dbvoc.getDeleteTable("customer_master");
                        dbvoc.getDeleteTable("distributors");
                        dbvoc.getDeleteTable("Survey_Questions");
                        dbvoc.getDeleteTable("distributor_beats");

                        dbvoc.getDeletecredit_limitsAll();

                       // JSONArray scheme = json.getJSONArray("scheme");

                         //JSONArray credit_limits = json.getJSONArray("cr_limits");
                         //JSONArray outstandings = json.getJSONArray("outstandings");

                        Log.d("items", "items" + items.toString());
                        Log.d("customers", "customers" + customers.toString());
                        // TODO DONE for distributor problem
                        Log.d("distributors", "distributors" + customers.toString());

                        Log.d("reasons", "reasons" + reasons.toString());

                        Log.d("items", "items length" + items.length());
                        Log.d("customers", "customers length" + customers.length());

                        Log.d("states", "states length" + states.length());
                        Log.d("cities", "cities length" + cities.length());
                        Log.d("beats", "beats length" + beats.length());

                        Log.d("states", "states length" + states.toString());
                        Log.d("cities", "cities length" + cities.toString());
                        Log.d("beats", "beats length" + beats.toString());
                        Log.d("distributor_beats", "distributor_beats length" + distributor_beats.toString());
                        Log.d("credit_profile", "credit_profile" + credit_profile.toString());
                        Log.d("l1_contacts", "l1_contacts" + l1_contacts.toString());
                        Log.d("schemes", "schemes" + schemes.toString());
                        Log.d("warehouse", "warehouse" + warehouse.toString());
                        Log.d("Order_Category", "Order_Category" + Order_Category.toString());

                       // Log.d("Survey_Questions", "Survey_Questions length" + Survey_Questions.toString());
                      // Log.d("credit_limits", "credit_limits length" + credit_limits.toString());
                        // Log.d("scheme", "scheme length" + scheme.toString());
                       // Log.d("outstandings", "outstandings length" + outstandings.toString());


                        // Log.d("customers", "customers" + customers.toString());
                        // Log.d("devices", "devices" + devices.toString());

                        for (int i = 0; i < label_acc.length(); i++) {

                            JSONObject jsonObject = label_acc.getJSONObject(i);
                            loginDataBaseAdapter.insertLABEL_CHANGES(jsonObject.getString("variable_name"), jsonObject.getString("new_label"), jsonObject.getString("editable"), jsonObject.getString("mandatory"), jsonObject.getString("allow"));

                            Global_Data.Var_Label =jsonObject.getString("variable_name");
                            Global_Data.editable = jsonObject.getString("editable");
                            Global_Data.mandatory = jsonObject.getString("mandatory");
                            Global_Data.allow = jsonObject.getString("allow");


                            if(Global_Data.Var_Label.equalsIgnoreCase("no_order"))
                            {
                               // Global_Data.New_Label =jsonObject.getString("new_label");
                               // Global_Data.editable =jsonObject.getString("editable");
                                // Prefs.SavePreferences("VAR_NOOREDER", cn.getVarLabel_account());

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_norder",jsonObject.getString("new_label"));
                                editor.commit();
                            }

                            if(Global_Data.Var_Label.equalsIgnoreCase("return_order"))
                            {
                                //Global_Data.New_Label =jsonObject.getString("new_label");
                               // Global_Data.editable =jsonObject.getString("editable");

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_retorder", jsonObject.getString("new_label"));
                                editor.commit();
                            }

                            if(Global_Data.Var_Label.equalsIgnoreCase("rp"))
                            {
                                //Global_Data.New_Label = jsonObject.getString("new_label");
                                // Prefs.SavePreferences("VAR_NOOREDER", cn.getVarLabel_account());

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_rp", jsonObject.getString("new_label"));
                                editor.commit();
                            }

                            if(Global_Data.Var_Label.equalsIgnoreCase("schedule"))
                            {
                               // Global_Data.New_Label = jsonObject.getString("new_label");
                                // Prefs.SavePreferences("VAR_NOOREDER", cn.getVarLabel_account());

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_schedule", jsonObject.getString("new_label"));
                                editor.commit();
                            }


                            if(Global_Data.Var_Label.equalsIgnoreCase("detail1"))
                            {
                                //Global_Data.New_Label = jsonObject.getString("new_label");
                               // Global_Data.editable = jsonObject.getString("editable");

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_detail1",jsonObject.getString("new_label"));
                                editor.commit();

                                SharedPreferences spf1=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor1=spf1.edit();
                                editor1.putString("var_detail1_edit", jsonObject.getString("editable"));
                                editor1.commit();

                                SharedPreferences spf2=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor2=spf2.edit();
                                editor2.putString("var_detail1_mandate", jsonObject.getString("mandatory"));
                                editor2.commit();

                                SharedPreferences spf3=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor3=spf3.edit();
                                editor3.putString("var_detail1_allow", jsonObject.getString("allow"));
                                editor3.commit();
                            }

                            if(Global_Data.Var_Label.equalsIgnoreCase("detail2"))
                            {
                               // Global_Data.New_Label = jsonObject.getString("new_label");
                                // Prefs.SavePreferences("VAR_NOOREDER", cn.getVarLabel_account());

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_detail2", jsonObject.getString("new_label"));
                                editor.commit();

                                SharedPreferences spf1=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor1=spf1.edit();
                                editor1.putString("var_detail2_edit", jsonObject.getString("editable"));
                                editor1.commit();

                                SharedPreferences spf2=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor2=spf2.edit();
                                editor2.putString("var_detail2_mandate",jsonObject.getString("mandatory"));
                                editor2.commit();

                                SharedPreferences spf3=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor3=spf3.edit();
                                editor3.putString("var_detail2_allow", jsonObject.getString("allow"));
                                editor3.commit();

                            }

                            if(Global_Data.Var_Label.equalsIgnoreCase("details4"))
                            {
                                //Global_Data.New_Label = jsonObject.getString("new_label");
                                // Prefs.SavePreferences("VAR_NOOREDER", cn.getVarLabel_account());

                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("var_detail4", jsonObject.getString("new_label"));
                                editor.commit();

                                SharedPreferences spf1=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor1=spf1.edit();
                                editor1.putString("var_detail4_edit", jsonObject.getString("editable"));
                                editor1.commit();

                                SharedPreferences spf2=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor2=spf2.edit();
                                editor2.putString("var_detail4_mandate", jsonObject.getString("mandatory"));
                                editor2.commit();

                                SharedPreferences spf3=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor3=spf3.edit();
                                editor3.putString("var_detail4_allow", jsonObject.getString("allow"));
                                editor3.commit();

                            }

                        }

                        List<Local_Data> cont_lab = dbvoc.getAllLabels();
                        //results.add("Select Beat");
                        for (Local_Data cn : cont_lab)

                        for(int z=0;z<logo_img.length();z++)
                        {
                            JSONObject logo_imgjobj=logo_img.getJSONObject(z);
                            str=logo_imgjobj.getString("name");
                            strr=logo_imgjobj.getString("data_image_string");
                            if(str.equalsIgnoreCase("logo"))
                            {
                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("logo_data", strr);
                                editor.commit();
                            }
                            if(str.equalsIgnoreCase("splash")){
                                SharedPreferences spf=context.getSharedPreferences("SimpleLogic",0);
                                SharedPreferences.Editor editor=spf.edit();
                                editor.putString("splash_data", strr);
                                editor.commit();
                            }
                        }


                        for (int i = 0; i < users_emp.length(); i++) {

                            JSONObject jsonObject = users_emp.getJSONObject(i);

                            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("address").toString())) {
                                dbvoc.updateUserEMPNO_BY_EMILID(jsonObject.getString("emp_code"),jsonObject.getString("email"),jsonObject.getString("address"));
                            }
                            else
                            {
                                dbvoc.updateUserEMPNO_BY_EMILID(jsonObject.getString("emp_code"),jsonObject.getString("email"),"");
                            }


                        }
                        for (int i = 0; i < l1_contacts.length(); i++) {
                            dbvoc.getDeleteTable("L1_Contact");

                            JSONObject jsonObject = l1_contacts.getJSONObject(i);
                            loginDataBaseAdapter.insert_L1_CONTACT("",jsonObject.getString("title"),jsonObject.getString("heading"),jsonObject.getString("sub_heading"),jsonObject.getString("address"),jsonObject.getString("contact_no1"),jsonObject.getString("contact_no2"),jsonObject.getString("email_id1"),jsonObject.getString("email_id2"),jsonObject.getString("website"),"","","");

                        }

                        for (int i = 0; i < Order_Category.length(); i++) {
                            //dbvoc.getDeleteTable("scheme_new");

                            JSONObject jsonObject = Order_Category.getJSONObject(i);
                            loginDataBaseAdapter.insert_ORDER_CATEGORY(jsonObject.getString("code"),jsonObject.getString("name"),"","","","");

                        }

                        for (int i = 0; i < payment_terms.length(); i++) {

                            JSONObject jsonObject = payment_terms.getJSONObject(i);
                            loginDataBaseAdapter.insert_asset_code_table_data(jsonObject.getString("code"),jsonObject.getString("name"),"","", "", "", "", "");

                        }

                        for (int i = 0; i < schemes.length(); i++) {
                            //dbvoc.getDeleteTable("scheme_new");

                            JSONObject jsonObject = schemes.getJSONObject(i);
                            loginDataBaseAdapter.insert_itemSchemenew(jsonObject.getString("code"),jsonObject.getString("name"),jsonObject.getString("scheme_type"),jsonObject.getString("description"),jsonObject.getString("display_name"),jsonObject.getString("product_code"),jsonObject.getString("qualifying_quantity"),jsonObject.getString("amount"),jsonObject.getString("foc_product_code"),"","","","","","");

                        }

                        for (int i = 0; i < warehouse.length(); i++) {
                            //dbvoc.getDeleteTable("scheme_new");

                            JSONObject jsonObject = warehouse.getJSONObject(i);
                            loginDataBaseAdapter.insert_Warehouse("",jsonObject.getString("code"),"",jsonObject.getString("name"),"",jsonObject.getString("city_code"),jsonObject.getString("state_code"),"","","","","");

                        }

                        List<Local_Data> checkproducts =  dbvoc.HSS_DescriptionITEM();

                        if(checkproducts.size() <= 0)
                        {
                            Log.d("FIRST SYNC","FIRST SYNC");
                            for (int i = 0; i < items.length(); i++) {

                                JSONObject jsonObject = items.getJSONObject(i);

                                loginDataBaseAdapter.insertEntryITEM_MASTER(jsonObject.getString("code"),jsonObject.getString("name"), jsonObject.getString("primary_category"),
                                        jsonObject.getString("sub_category"), jsonObject.getString("product_variant"), jsonObject.getString("retail_price"),
                                        jsonObject.getString("mrp"), jsonObject.getString("qualifying_qty"),
                                        jsonObject.getString("free_qty"), jsonObject.getString("status"), jsonObject.getString("business_unit"),jsonObject.getString("business_category"),jsonObject.getString("standard_qty"),jsonObject.getString("master_qty"));



                            }

                        }
                        else
                        {
                            for (int i = 0; i < items.length(); i++) {

                                JSONObject jsonObject = items.getJSONObject(i);

                                dbvoc.getDeletePRODUCT(jsonObject.getString("code"));

                                if(jsonObject.getString("status").equalsIgnoreCase("active"))
                                {
                                    loginDataBaseAdapter.insertEntryITEM_MASTER(jsonObject.getString("code"),jsonObject.getString("name"), jsonObject.getString("primary_category"),
                                            jsonObject.getString("sub_category"), jsonObject.getString("product_variant"), jsonObject.getString("retail_price"),
                                            jsonObject.getString("mrp"), jsonObject.getString("qualifying_qty"),
                                            jsonObject.getString("free_qty"), jsonObject.getString("status"),jsonObject.getString("business_unit"),jsonObject.getString("business_category"),jsonObject.getString("standard_qty"),jsonObject.getString("master_qty"));
                                }

                            }
                        }


                        for (int i = 0; i < customers.length(); i++) {

                            JSONObject jsonObject = customers.getJSONObject(i);
                            List<Local_Data> contactsr =  dbvoc.getCustomer_BYID(jsonObject.getString("name"),jsonObject.getString("shop_name"));

                            if(contactsr.size() <= 0)
                            {
//                                loginDataBaseAdapter.insertCustMaster(jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("shop_name"), jsonObject.getString("address"), jsonObject.getString("street"), jsonObject.getString("landmark"),
//                                        jsonObject.getString("pincode"), jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"), jsonObject.getString("email"), jsonObject.getString("status"), jsonObject.getString("state_code"), jsonObject.getString("city_code"),jsonObject.getString("beat_code"),jsonObject.getString("vatin"),"","",jsonObject.getString("latitude"),jsonObject.getString("longitude"));

                                loginDataBaseAdapter.insertCustMaster(jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("shop_name"), jsonObject.getString("address"), jsonObject.getString("street"), jsonObject.getString("landmark"),
                                        jsonObject.getString("pincode"), jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"), jsonObject.getString("email"), jsonObject.getString("status"), jsonObject.getString("state_code"), jsonObject.getString("city_code"),jsonObject.getString("beat_code"),jsonObject.getString("vatin"),"","","","");
                            }
                            else
                            {
                                dbvoc.deletesalesupdatebyID(jsonObject.getString("name"),jsonObject.getString("shop_name"));
                                loginDataBaseAdapter.insertCustMaster(jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("shop_name"), jsonObject.getString("address"), jsonObject.getString("street"), jsonObject.getString("landmark"),
                                        jsonObject.getString("pincode"), jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"), jsonObject.getString("email"), jsonObject.getString("status"), jsonObject.getString("state_code"), jsonObject.getString("city_code"),jsonObject.getString("beat_code"),jsonObject.getString("vatin"),"","","","");

                            }

                        }


                        for (int i = 0; i < distributors.length(); i++) {

                            JSONObject jsonObject = distributors.getJSONObject(i);



                            loginDataBaseAdapter.insertDistributors(jsonObject.getString("code"),jsonObject.getString("name"), jsonObject.getString("shop_name"),
                                    jsonObject.getString("address1"), jsonObject.getString("address2"), jsonObject.getString("street"),
                                    jsonObject.getString("landmark"), jsonObject.getString("state_code"),
                                    jsonObject.getString("city_code"), jsonObject.getString("pincode"),
                                    jsonObject.getString("landline_no"), jsonObject.getString("mobile_no"),
                                    jsonObject.getString("email"), jsonObject.getString("status"));


                        }


                        for (int i = 0; i < reasons.length(); i++) {

                            JSONObject jsonObject = reasons.getJSONObject(i);

                            if(!jsonObject.getString("status").equalsIgnoreCase("active"))
                            {
                                dbvoc.getDeleteNOOrder(jsonObject.getString("code"));
                            }


                            if(jsonObject.getString("status").equalsIgnoreCase("active"))
                            {
                                loginDataBaseAdapter.insertno_orderReason(jsonObject.getString("code"), jsonObject.getString("desc"));
                            }



                        }

                        for (int i = 0; i < states.length(); i++) {

                            JSONObject jsonObject = states.getJSONObject(i);

                            loginDataBaseAdapter.insertStates("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("status"),"",
                                    "", "", jsonObject.getString("code"));

                        }

                        for (int i = 0; i < cities.length(); i++) {

                            JSONObject jsonObject = cities.getJSONObject(i);

                            loginDataBaseAdapter.insertCities("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("state_code"), jsonObject.getString("status"),"",
                                    "","", "", jsonObject.getString("code"));

                        }

                        for (int i = 0; i < beats.length(); i++) {

                            JSONObject jsonObject = beats.getJSONObject(i);
                            loginDataBaseAdapter.insertBeats("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("state_code"), jsonObject.getString("city_code"), jsonObject.getString("status"), "",
                                    "","", "", jsonObject.getString("code"));

                        }

                        for (int i = 0; i < Survey_Questions.length(); i++) {

                            JSONObject jsonObject = Survey_Questions.getJSONObject(i);

                            loginDataBaseAdapter.insert_Survey_Questions(jsonObject.getString("survey_code"),jsonObject.getString("question_code"),jsonObject.getString("active_from"),jsonObject.getString("active_to"),jsonObject.getString("question"),jsonObject.getString("option1"),jsonObject.getString("option2"),jsonObject.getString("option3"),jsonObject.getString("option4"),jsonObject.getString("option5"),"","");

                        }

                        for (int i = 0; i < distributor_beats.length(); i++) {

                            JSONObject jsonObject = distributor_beats.getJSONObject(i);

                            loginDataBaseAdapter.insert_DistriButorBeat(jsonObject.getString("code"),jsonObject.getString("distributor_code"),jsonObject.getString("beat_code"),jsonObject.getString("status"));

                        }

                        for (int i = 0; i < credit_profile.length(); i++) {

                            JSONObject jsonObject = credit_profile.getJSONObject(i);

                            loginDataBaseAdapter.insert_credit_profile("",jsonObject.getString("customer_code"),jsonObject.getString("customer_code"),"","","","",jsonObject.getString("credit_limit"),jsonObject.getString("amount_outstanding"),jsonObject.getString("amount_overdue"));


                        }

//                        for (int i = 0; i < scheme.length(); i++) {
//
//                            JSONObject jsonObject = scheme.getJSONObject(i);
//
//                            loginDataBaseAdapter.insertBeats("", "",jsonObject.getString("code"), jsonObject.getString("name"), jsonObject.getString("state_code"), jsonObject.getString("city_code"), jsonObject.getString("status"), "",
//                                    "","", "", jsonObject.getString("code"));
//
//                        }

//                        for (int i = 0; i < credit_limits.length(); i++) {
//
//                            JSONObject jsonObject = credit_limits.getJSONObject(i);
//                            loginDataBaseAdapter.insertCreditLimit("",jsonObject.getString("customer_code"), jsonObject.getString("customer_code"),"", jsonObject.getString("credit_limit"),"","");
//
//                        }
//
//                        for (int i = 0; i < outstandings.length(); i++) {
//
//                            JSONObject jsonObject = outstandings.getJSONObject(i);
//
//                            loginDataBaseAdapter.insertOutstandings(jsonObject.getString("code"),jsonObject.getString("customer_code"),jsonObject.getString("customer_code"),jsonObject.getString("user_email"), jsonObject.getString("order_number"),jsonObject.getString("order_amount"), jsonObject.getString("delivery_status"), jsonObject.getString("amount_collected"), jsonObject.getString("payment_status"), jsonObject.getString("amount_outstanding"),"","");
//
//                        }

//                        loginDataBaseAdapter.insert_itemScheme("s001","RUPEE DISCOUNT","11 rupees discount on purchase of 10 no of 200 ml Shampoo","11 rupees discount on  10","a001","10","11","");

                        //dbvoc.update_stockChecks(s_code,s_stock);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                        SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm:ss");

                        String Current_Date = sdf.format(c.getTime());
                        String Current_Time = sdf_time.format(c.getTime());


                        dbvoc.getDeleteTable("order_details");
                        LoginDataBaseAdapter loginDataBaseAdapter = new LoginDataBaseAdapter(context);
                        loginDataBaseAdapter = loginDataBaseAdapter.open();

                        loginDataBaseAdapter.insert_order_details(Current_Date, Current_Time);

                        final String finalResponse_result1 = response_result;
                        ((Activity)context).runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(context, "items Sync Successfully.", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                t_total = 0;
                                achived_total = 0;
                                getTargetDataservice(context);

                            }
                        });

                        // Global_Val.STOCK_SERVICE_FLAG = "";
                        //dialog.dismiss();
                        //finish();

                    }

                    //  finish();
                    // }

                    // output.setText(data);
                }catch(JSONException e){
                    e.printStackTrace();
                    dialog.dismiss(); }


               // dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
                //  finish();
                dialog.dismiss();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            dialog.dismiss();

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    public static void getTargetDataservicenew(final Context contextn)
    {
        SharedPreferences sp = contextn.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        device_id = sp.getString("devid", "");

        calendarn = Calendar.getInstance();
        year = calendarn.get(Calendar.YEAR);
        loginDataBaseAdapter=new LoginDataBaseAdapter(contextn);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        dbvoc = new DataBaseHelper(contextn);
        dialog = new ProgressDialog(contextn, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Target Sync....");
        dialog.setTitle("Sales App");
        dialog.setCancelable(false);
        dialog.show();

        try
        {

            String  domain = contextn.getResources().getString(R.string.service_domain);

            Log.i("volley", "domain: " + domain);
            Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL);
            Log.i("target url", "target url " + domain+"targets?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"targets?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());


                    try{


                        //   for (int a = 0; a < response.length(); a++) {

//	                        JSONObject person = (JSONObject) response.getJSONArray(response);
                        //
                        //   String name = response.getString("result44");

                        String response_result = "";
                        if(response.has("result"))
                        {
                            response_result = response.getString("result");
                        }
                        else
                        {
                            response_result = "data";
                        }


                        if(response_result.equalsIgnoreCase("User doesn't exist")) {

                            Toast.makeText(contextn, response_result, Toast.LENGTH_LONG).show();

                        }
                        else {

                            dbvoc.getDeleteTable("targets");

                            JSONArray targets = response.getJSONArray("targets");


                            Log.i("volley", "response reg targets Length: " + targets.length());

                            Log.d("States", "targets" + targets.toString());

                            //
                            for (int i = 0; i < targets.length(); i++) {

                                JSONObject jsonObject = targets.getJSONObject(i);

//
                                loginDataBaseAdapter.insertTargets("","", "",
                                        jsonObject.getString("year"), jsonObject.getString("month"), jsonObject.getString("target"),
                                        jsonObject.getString("achieved"), "", "");

                                if(jsonObject.getString("year").equalsIgnoreCase(String.valueOf(year)))
                                {
                                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("target").toString()))
                                    {
                                        t_total +=Float.valueOf(jsonObject.getString("target").toString());
                                    }

                                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("achieved").toString()))
                                    {
                                        achived_total +=Float.valueOf(jsonObject.getString("achieved").toString());
                                    }
                                }



                            }


                            SharedPreferences spf=contextn.getSharedPreferences("SimpleLogic",0);
                            SharedPreferences.Editor editor=spf.edit();
                            //editor.putString("UserID", "admin");
                            //editor.putString("pwd", "test");
                            editor.putFloat("Target", t_total);
                            editor.putFloat("Achived", achived_total);
                            //editor.putString("SimID", simSerial);
                            editor.commit();
                            dialog.dismiss();


//                            Intent intent = new Intent(getActivity(), Target.class);
//                            startActivity(intent);
//                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                            //finish();

                        }


                        // }

                        // output.setText(data);
                    }catch(JSONException e){e.printStackTrace();
                        dialog.dismiss();


                    }


                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("volley", "error: " + error);
                    //Toast.makeText(contextn, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

                    dialog.dismiss();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(contextn);
            // queue.add(jsObjRequest);
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 200000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();



        }
    }

    public static void getTargetDataservice(final Context contextn)
    {
        t_total = 0;
        achived_total = 0;

        SharedPreferences sp = contextn.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        device_id = sp.getString("devid", "");

         calendarn = Calendar.getInstance();
        year = calendarn.get(Calendar.YEAR);
        loginDataBaseAdapter=new LoginDataBaseAdapter(contextn);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        dbvoc = new DataBaseHelper(contextn);
        dialog = new ProgressDialog(contextn, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait Target Sync....");
        dialog.setTitle("Sales App");
        dialog.setCancelable(false);
        dialog.show();

        try
        {

            String  domain = contextn.getResources().getString(R.string.service_domain);

            Log.i("volley", "domain: " + domain);
            Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL);
            Log.i("target url", "target url " + domain+"targets?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"targets?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());

                    try{
                        //   for (int a = 0; a < response.length(); a++) {

//	                        JSONObject person = (JSONObject) response.getJSONArray(response);
                        //
                        //   String name = response.getString("result44");

                        String response_result = "";
                        if(response.has("result"))
                        {
                            response_result = response.getString("result");
                        }
                        else
                        {
                            response_result = "data";
                        }


                        if(response_result.equalsIgnoreCase("User doesn't exist")) {

                            Toast.makeText(contextn, response_result, Toast.LENGTH_LONG).show();

                        }
                        else {

                            dbvoc.getDeleteTable("targets");

                            JSONArray targets = response.getJSONArray("targets");


                            Log.i("volley", "response reg targets Length: " + targets.length());

                            Log.d("States", "targets" + targets.toString());

                            //
                            for (int i = 0; i < targets.length(); i++) {

                                JSONObject jsonObject = targets.getJSONObject(i);


                                loginDataBaseAdapter.insertTargets("","", "",
                                        jsonObject.getString("year"), jsonObject.getString("month"), jsonObject.getString("target"),
                                        jsonObject.getString("achieved"), "", "");

                                if(jsonObject.getString("year").equalsIgnoreCase(String.valueOf(year)))
                                {
                                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("target").toString()))
                                    {
                                        if(!jsonObject.getString("target").equalsIgnoreCase("null") && !jsonObject.getString("target").equalsIgnoreCase(null) & !jsonObject.getString("target").equalsIgnoreCase("") & !jsonObject.getString("target").equalsIgnoreCase(" "))
                                        {
                                            t_total +=Float.valueOf(jsonObject.getString("target").toString());
                                        }
                                        else
                                        {
                                            t_total +=Float.valueOf("0.0");
                                        }
                                    }

                                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("achieved").toString()))
                                    {
                                        if(!jsonObject.getString("achieved").equalsIgnoreCase("null") && !jsonObject.getString("achieved").equalsIgnoreCase(null) & !jsonObject.getString("achieved").equalsIgnoreCase("") & !jsonObject.getString("achieved").equalsIgnoreCase(" "))
                                        {
                                            achived_total +=Float.valueOf(jsonObject.getString("achieved").toString());
                                        }
                                        else
                                        {
                                            achived_total +=Float.valueOf("0.0");
                                        }
                                    }
                                }
                                }

                            SharedPreferences spf=contextn.getSharedPreferences("SimpleLogic",0);
                            SharedPreferences.Editor editor=spf.edit();

                            //editor.putString("UserID", "admin");
                            //editor.putString("pwd", "test");
                            editor.putFloat("Target", t_total);
                            editor.putFloat("Achived", achived_total);
                            //editor.putString("SimID", simSerial);
                            editor.commit();
                            dialog.dismiss();
                            Intent intentn = new Intent(context, MainActivity.class);
                            context.startActivity(intentn);
                            //((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            ((Activity) context).finish();

//                            Intent intent = new Intent(getActivity(), Target.class);
//                            startActivity(intent);
//                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


                            //finish();

                        }


                        // }

                        // output.setText(data);
                    }catch(JSONException e){e.printStackTrace();
                        dialog.dismiss();
                        Intent intentn = new Intent(context, MainActivity.class);
                        context.startActivity(intentn);
                        //((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        ((Activity) context).finish();

                        }


                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.i("volley", "error: " + error);
                    //Toast.makeText(contextn, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
                    Intent intentn = new Intent(context, MainActivity.class);
                    context.startActivity(intentn);
                   // ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    ((Activity) context).finish();
                    dialog.dismiss();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(contextn);
            // queue.add(jsObjRequest);
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 200000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();

        }
    }

    public static class OrderAyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            // Here you can show progress bar or something on the similar lines.
            // Since you are in a UI thread here.
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // After completing execution of given task, control will return here.
            // Hence if you want to populate UI elements with fetched data, do it here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            // You can track you progress update here
        }

        @Override
        protected Void doInBackground(Void... params) {


            String uploadImage = "";
            dbvoc = new DataBaseHelper(context);

            Calendar c = Calendar.getInstance();
            System.out.println("Current time =&gt; "+c.getTime());

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            final String formattedDate = df.format(c.getTime());
            JSONObject jsonBody = new JSONObject();

            try {

                JSONArray customer = new JSONArray();
                JSONArray product = new JSONArray();
                JSONArray order = new JSONArray();
                JSONObject product_valuenew = new JSONObject();

                int a = 0;
                String s = "";

                List<Local_Data> customers_contacts = dbvoc.getAllRetailer_cre();
                if(customers_contacts.size() > 0)
                {
                    //  Retailer_Flag = "true";
                }
                else
                {
                    // Retailer_Flag = "false";
                }

                for (Local_Data cn : customers_contacts)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("user_email", cn.getemail());
                    product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
                    product_value.put("name", cn.getCUSTOMER_NAME());
                    product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
                    product_value.put("address", cn.getADDRESS());
                    product_value.put("street", cn.getSTREET());
                    product_value.put("landmark", cn.getLANDMARK());
                    product_value.put("pincode", cn.getPIN_CODE());
                    product_value.put("mobile_no", cn.getMOBILE_NO());
                    product_value.put("email", cn.getEMAIL_ADDRESS());
                    product_value.put("status", cn.getSTATUS());
                    product_value.put("state_code", cn.getSTATE_ID());
                    product_value.put("city_code", cn.getCITY_ID());
                    product_value.put("beat_code", cn.getBEAT_ID());
                    product_value.put("vatin", cn.getvatin());
                    product_value.put("latitude", cn.getlatitude());
                    product_value.put("longitude", cn.getlongitude());
                    customer.put(product_value);

                }

                byte b5[];

                List<Local_Data> contacts = dbvoc.GetOrders("Secondary Sales / Retail Sales", Global_Data.GLOvel_GORDER_ID);
                //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");

                for (Local_Data cn : contacts)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("order_number", cn.get_category_code());

                    Order_number = cn.get_category_code();
                    // product_value.put("order_date", cn.getCUSTOMER_ORDER_DATE());
                    // product_value.put("order_take_by", "");
                    product_value.put("customer_code", cn.get_category_id());
                    product_value.put("email", Global_Data.GLOvel_USER_EMAIL);

                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getlatitude()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getlongitude())) {

                        product_value.put("latitude", cn.getlatitude());
                        product_value.put("longitude", cn.getlongitude());
                    }
                    else
                    {
                        product_value.put("latitude", Global_Data.GLOvel_LATITUDE);
                        product_value.put("longitude", Global_Data.GLOvel_LONGITUDE);
                    }

                    product_value.put("distributor_code", cn.getDISTRIBUTER_ID());
                    product_value.put("details1", cn.getOrder_detail1());
                    product_value.put("details2", cn.getOrder_detail2());
                    product_value.put("details3", cn.getOrder_detail3());
                    product_value.put("details4", cn.getOrder_detail4());
                    product_value.put("order_category_code", cn.getOrder_category_type());
                    product_value.put("shipment_priority", cn.getshipment_pri());
                    product_value.put("payment_term_code", cn.getAsset_code());

                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getSignature_image()))
                    {
                        order_image_url = cn.getSignature_image().trim();
                        // File filepath = new File(cn.getimg_ordersign());
                        // String  path =  "file://"+filepath.getPath();
                        try {
                            Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(cn.getSignature_image()));
                            ByteArrayOutputStream bos5 = new ByteArrayOutputStream();
                            mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos5);
                            b5 = bos5.toByteArray();

                            String getsign_str= Base64.encodeToString(b5,Base64.DEFAULT);
                            product_value.put("signature_path",getsign_str);

                        } catch (Exception e) {
                            e.printStackTrace();
                            product_value.put("signature_path", "");
                        }



                    }
                    else
                    {
                        product_value.put("signature_path", "");
                    }

                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getimg_ordersign()))
                    {
                        order_image_url = cn.getimg_ordersign().trim();
                        // File filepath = new File(cn.getimg_ordersign());
                        // String  path =  "file://"+filepath.getPath();
                        try {
                            Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(cn.getimg_ordersign()));
                            ByteArrayOutputStream bos5 = new ByteArrayOutputStream();
                            mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos5);
                            b5 = bos5.toByteArray();

                            String getsign_str= Base64.encodeToString(b5,Base64.DEFAULT);
                            product_value.put("order_image_string",getsign_str);

                        } catch (Exception e) {
                            e.printStackTrace();
                            product_value.put("order_image_string", "");
                        }



                    }
                    else
                    {
                        product_value.put("order_image_string", "");
                    }


                    // product_value.put("signature_path", cn.getSignature_image());
                    customer_id = cn.get_category_id();
                    // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
                    // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
                    //product_value.put("signature_image_name", uploadImage);
                    product_value.put("device_code", Global_Data.device_id);

                    if(cn.get_shedule_payment_mode().equalsIgnoreCase("Secondary Sales / Retail Sales"))
                    {
                        s = "Retail Sales";
                    }
                    else
                    {
                        s = cn.get_shedule_payment_mode();
                    }
                    product_value.put("order_type", s);
                    // product_value.put("conference_code", cn.getconference_id());
                    order.put(product_value);
                    Log.d("count", "a" + ++a);
                    //delete_order_no = cn.getORDER_NUMBER();
                    List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts(cn.get_category_code());
                    for (Local_Data cnp : contactsproduct) {
                        JSONObject item = new JSONObject();
                        item.put("order_number", cnp.get_category_code());
                        item.put("item_number",cnp.get_delivery_product_id());
                        item.put("total_qty", cnp.get_stocks_product_quantity());
                        item.put("MRP", cnp.getMRP());
                        item.put("amount", cnp.get_Claims_amount());
                        item.put("scheme_code", cnp.getSche_code());

                        total_ammount += Double.valueOf(cnp.get_Claims_amount());

                        //item.put("scheme_amount", cnp.get_Target_Text());
                        //item.put("item_number", cnp.get_delivery_product_id());
                        //item.put("discount_type", cnp.get_stocks_product_text());
                        product.put(item);
                        //Log.d("quantity","quantity"+cnp.getquantity());
                    }
                }

//         for (int i = 0; i < 10; i++)
//         {
//
//
//
//         }

                product_valuenew.put("orders", order);
                product_valuenew.put("order_products", product);
                product_valuenew.put("customers", customer);
                product_valuenew.put("imei_no", Global_Data.device_id);
                Log.d("customers",customer.toString());

                Log.d("Orders", order.toString());

                Log.d("order_products",product.toString());

                Log.d("product_valuenew",product_valuenew.toString());

                // HashMap<String, String> params = new HashMap<String, String>();
                //params.put("token", json.toString());




                // RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                // PreferencesHelper Prefs = new PreferencesHelper(MasterSyncData.this);


                //String URL = Prefs.GetPreferences("URL");
                String  domain = context.getResources().getString(R.string.service_domain);
                Log.i("volley", "domain: " + domain);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"orders/save_orders", product_valuenew, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("volley", "response: " + response);


                        String response_result = "";
                        //if (response.has("result")) {
                        try {
                            response_result = response.getString("result");

//                         if (response_result.equalsIgnoreCase("Device not found.")) {
//                             Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                             toast.setGravity(Gravity.CENTER, 0, 0);
//                             toast.show();
//                             dialog.dismiss();
//                         }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    dialog.dismiss();
                                }
                            });
                        }

                        if (response_result.equalsIgnoreCase("Device not found.")) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    dialog.dismiss();


                                }
                            });

                        } else {
//                 else
//                 {
//                     response_result = "data";
//                 }


                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context, "Order Sync Successfully", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();


                                }
                            });

                            mobile_numbers.clear();

                            if (!Global_Data.customer_MobileNumber.equalsIgnoreCase(null) && !Global_Data.customer_MobileNumber.equalsIgnoreCase("null") && !Global_Data.customer_MobileNumber.equalsIgnoreCase("") && !Global_Data.customer_MobileNumber.equalsIgnoreCase(" ")) {
                                mobile_numbers.add(Global_Data.customer_MobileNumber);
                            }

                            if (!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null") && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("") && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" ")) {
                                mobile_numbers.add(Global_Data.cus_MAnager_mobile);
                            }

                            String gaddress = "";
                            try {
                                if (Global_Data.address.equalsIgnoreCase("null")) {
                                    gaddress = "";
                                } else {
                                    gaddress = Global_Data.address;
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    public void run() {


                                        dialog.dismiss();
                                    }
                                });
                            }
                            String sms_body = "Dear " + Global_Data.CUSTOMER_NAME_NEW + " ," + "\n" + " Thank you for your order for " + Global_Data.order_retailer + " at " + Global_Data.CUSTOMER_ADDRESS_NEW + " at " + formattedDate + " for Rs. " + String.valueOf(total_ammount) + "." + "\n\n" + " Thank you." + "\n" + " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME + "\n" + " " + gaddress;

                            //sendSMS("8454858739",sms_body,context);

                            //Uri uri = Uri.parse("file://"+Environment.getExternalStorageDirectory()+"/test.png");
                            //sendLongSMS("8454858739",sms_body,context);


                            if (!mobile_numbers.isEmpty() && mobile_numbers.size() > 0) {

                                for (int i = 0; i < mobile_numbers.size(); i++) {
                                    String message = sms_body;
                                    String tempMobileNumber = mobile_numbers.get(i).toString();
                                    //  Global_Data.sendSMS(tempMobileNumber, message,context);
                                }
                            }

                            // dbvoc.getDeleteTable("order_products");
                            //dbvoc.getDeleteTable("orders");
                            List<Local_Data> contactsn = dbvoc.Getcustomer_email(customer_id);
                            //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
                            for (Local_Data cn : contactsn) {
                                email_adress = cn.get_Description();
                            }
                            Global_Data.GLOvel_GORDER_ID = "";
                            String val = "";
                            dbvoc.updateCustomerby_CreateAt(val);
                            dbvoc.getDeleteTableorder_bycustomer(Global_Data.order_retailer.trim(), "Secondary Sales / Retail Sales", Order_number);
                            dbvoc.getDeleteTableorderproduct_bycustomer(Global_Data.order_retailer.trim(), "Secondary Sales / Retail Sales", Order_number);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    dialog.dismiss();
                                    final Dialog dialog1 = new Dialog(context);
                                    dialog1.setCancelable(false);

                                    //tell the Dialog to use the dialog.xml as it's layout description
                                    dialog1.setContentView(R.layout.dialog);
                                    dialog1.setTitle("Order Status :");

                                    TextView txt = (TextView) dialog1.findViewById(R.id.txtOrderID);

                                    txt.setText("Order is generated.");
                                    TextView txtMessage = (TextView) dialog1.findViewById(R.id.txtMessage);
                                    TextView txtEmail = (TextView) dialog1.findViewById(R.id.txtEmail);

                                    txtEmail.setText("Mail will be sent to " + email_adress);
                                    if (!mobile_numbers.isEmpty() && mobile_numbers.size() > 0) {
                                        txtMessage.setText("Sms Send Successfully");
                                    }


                                    ImageView dialogButton = (ImageView) dialog1.findViewById(R.id.dialogButton);

                                    dialogButton.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog1.dismiss();

                                            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                            getTargetDataservice(context);


                                        }
                                    });

                                    dialog1.show();
                                }
                            });


                            //Intent intentn = new Intent(context, MainActivity.class);
                            //context.startActivity(intentn);
                            //context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            //((Activity) context).finish();
//                 List<Local_Data> contacts = dbvoc.GetOrders(Global_Val.customer_id);
//                 for (Local_Data cn : contacts)
//                 {
//                     // JSONObject product_value = new JSONObject();
//                     //product_value.put("order_number", cn.getORDER_NUMBER());
//
//                     dbvoc.deleteOrderproductByOCID(cn.getORDER_NUMBER());
//                     dbvoc.deleteOrderTABLE_QuantityValue(cn.getORDER_NUMBER());
//                     dbvoc.deleteBarcode_ByOrder(cn.getORDER_NUMBER());
//                     dbvoc.deleteORDERSNEW(cn.getORDER_NUMBER());
//
//                 }


                            //dbvoc.deleteOrderByOCID(Global_Val.customer_id);
                            //dbvoc.getDeleteTable("DESIGN_CHECK");

//                 Intent i = new Intent(MasterSyncData.this, MyAndroidAppActivity.class);
//                 //				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 i.putExtra("user_name", user_name);
//                 i.putExtra("confrence_name", confrence_name);
//                 i.putExtra("BackFlag", "nothing");
//                 Global_Val.STOCK_SERVICE_FLAG = "TRUE";
                            //				i.putExtra("Barcode_Number", userInput.getText().toString());
                            //				i.putExtra("BackFlag","Barcode");
//                 startActivity(i);
//                 MasterSyncData.this.finish();
                        }
                    }
                },   new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(final VolleyError error) {
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                   public void run() {

                                       Toast.makeText(context,
                                               "your internet connection is not working, saving locally. Please sync when Internet is available",
                                               Toast.LENGTH_LONG).show();
                                   }
                               });

                        } else if (error instanceof AuthFailureError) {

                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context,
                                            "Server AuthFailureError  Error",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        } else if (error instanceof ServerError) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context,
                                            "Server   Error",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        } else if (error instanceof NetworkError) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context,
                                            "your internet connection is not working, saving locally. Please sync when Internet is available",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        } else if (error instanceof ParseError) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context,
                                            "ParseError   Error",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else
                        {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        ((Activity) context).runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                            }
                        });

                        // finish();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                // queue.add(jsObjRequest);
                int socketTimeout = 30000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsObjRequest.setRetryPolicy(policy);
                requestQueue.add(jsObjRequest);

            } catch (JSONException e) {
                e.printStackTrace();
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });

            }
            return null;
        }
    }


    public static class AllOrderAyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            if (dialog != null && dialog.isShowing()) {
                dialog.cancel();
                //dialog.dismiss();
            }

            dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Order Sync in Progress, Please Wait");
            dialog.setTitle("Anchor");
            dialog.setCancelable(false);
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // After completing execution of given task, control will return here.
            // Hence if you want to populate UI elements with fetched data, do it here.
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            // You can track you progress update here
        }

        @Override
        protected Void doInBackground(Void... params)
        {

            PRODUCTOrder_ids.clear();
            //ArrayList productList = new ArrayList();

            String uploadImage = "";
            dbvoc = new DataBaseHelper(context);
            final Calendar c = Calendar.getInstance();

            //String strDate = sdf.format(c.getTime());


            final ArrayList<String> order_results = new ArrayList<String>();


            final JSONObject jsonBody = new JSONObject();

            try {

                JSONArray customer = new JSONArray();
                JSONArray product = new JSONArray();
                JSONArray order = new JSONArray();

                JSONArray product_return = new JSONArray();
                JSONArray order_return = new JSONArray();

                JSONArray no_orders = new JSONArray();

                JSONObject product_valuenew = new JSONObject();

                int a = 0;
                int order_count = 0;
                String s = "";

                List<Local_Data> customers_contacts = dbvoc.getAllRetailer_cre();
                if(customers_contacts.size() > 0)
                {
                    Retailer_Flag = "true";
                }
                else
                {
                    Retailer_Flag = "false";
                }

                for (Local_Data cn : customers_contacts)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("user_email", cn.getemail());
                    product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
                    product_value.put("name", cn.getCUSTOMER_NAME());
                    product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
                    product_value.put("address", cn.getADDRESS());
                    product_value.put("street", cn.getSTREET());
                    product_value.put("landmark", cn.getLANDMARK());
                    product_value.put("pincode", cn.getPIN_CODE());
                    product_value.put("mobile_no", cn.getMOBILE_NO());
                    product_value.put("email", cn.getEMAIL_ADDRESS());
                    product_value.put("status", cn.getSTATUS());
                    product_value.put("state_code", cn.getSTATE_ID());
                    product_value.put("city_code", cn.getCITY_ID());
                    product_value.put("beat_code", cn.getBEAT_ID());
                    product_value.put("vatin", cn.getvatin());
                    product_value.put("latitude", cn.getlatitude());
                    product_value.put("longitude", cn.getlongitude());
                    customer.put(product_value);

                }


                byte b5[];
                List<Local_Data> contacts = dbvoc.GetAllOrders("Secondary Sales / Retail Sales");

                if(contacts.size() > 0)
                {
                    order_Flag = "true";
                }
                else
                {
                    order_Flag = "false";
                }

                //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");
                for (Local_Data cn : contacts)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("order_number", cn.get_category_code());

                    Order_number = cn.get_category_code();
                    order_results.add(Order_number);
                    ++order_count;
                    // product_value.put("order_date", cn.getCUSTOMER_ORDER_DATE());
                    // product_value.put("order_take_by", "");
                    product_value.put("customer_code", cn.get_category_id());

                    product_value.put("email", cn.getuser_email());
                    product_value.put("distributor_code", cn.getDISTRIBUTER_ID());

                    product_value.put("details1", cn.getOrder_detail1());
                    product_value.put("details2", cn.getOrder_detail2());
                    product_value.put("details3", cn.getOrder_detail3());
                    product_value.put("details4", cn.getOrder_detail4());
                    product_value.put("order_category_code", cn.getOrder_category_type());



                    // product_value.put("signature", cn.getSignature_image());
                    //product_value.put("distributor_id", cn.getDISTRIBUTER_ID());

                    customer_id = cn.get_category_id();
                    // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
                    // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
                    //product_value.put("signature_image_name", uploadImage);
                    product_value.put("device_code", Global_Data.device_id);

                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getlatitude()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getlongitude())) {

                        product_value.put("latitude", cn.getlatitude());
                        product_value.put("longitude", cn.getlongitude());
                    }
                    else
                    {
                        product_value.put("latitude", Global_Data.GLOvel_LATITUDE);
                        product_value.put("longitude", Global_Data.GLOvel_LONGITUDE);
                    }

                    product_value.put("shipment_priority", cn.getshipment_pri());
                    product_value.put("payment_term_code", cn.getAsset_code());
                   // product_value.put("signature_path", cn.getSignature_image());

                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getSignature_image()))
                    {
                        order_image_url = cn.getSignature_image().trim();
                        // File filepath = new File(cn.getimg_ordersign());
                        // String  path =  "file://"+filepath.getPath();
                        try {
                            Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(cn.getSignature_image()));
                            ByteArrayOutputStream bos5 = new ByteArrayOutputStream();
                            mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos5);
                            b5 = bos5.toByteArray();

                            String getsign_str= Base64.encodeToString(b5,Base64.DEFAULT);
                            product_value.put("signature_path",getsign_str);

                        } catch (Exception e) {
                            e.printStackTrace();
                            product_value.put("signature_path", "");
                        }



                    }
                    else
                    {
                        product_value.put("signature_path", "");
                    }

                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getimg_ordersign()))
                    {
                        order_image_url = cn.getimg_ordersign().trim();
                        // File filepath = new File(cn.getimg_ordersign());
                        // String  path =  "file://"+filepath.getPath();
                        try {
                            Bitmap mImageBitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(cn.getimg_ordersign()));
                            ByteArrayOutputStream bos5 = new ByteArrayOutputStream();
                            mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos5);
                            b5 = bos5.toByteArray();

                            String getsign_str= Base64.encodeToString(b5,Base64.DEFAULT);
                            product_value.put("order_image_string",getsign_str);

                        } catch (Exception e) {
                            e.printStackTrace();
                            product_value.put("order_image_string", "");
                        }



                    }
                    else
                    {
                        product_value.put("order_image_string", "");
                    }




                    if(cn.get_shedule_payment_mode().equalsIgnoreCase("Secondary Sales / Retail Sales"))
                    {
                        s = "Retail Sales";
                    }
                    else
                    {
                        s = cn.get_shedule_payment_mode();
                    }
                    product_value.put("order_type", s);
                    // product_value.put("conference_code", cn.getconference_id());
                    order.put(product_value);
                    Log.d("count", "a" + ++a);
                    //delete_order_no = cn.getORDER_NUMBER();
                    List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts(cn.get_category_code());
                    for (Local_Data cnp : contactsproduct) {
                        JSONObject item = new JSONObject();
                        item.put("order_number", cnp.get_category_code());
                        item.put("item_number",cnp.get_delivery_product_id());
                        item.put("total_qty", cnp.get_stocks_product_quantity());
                        item.put("MRP", cnp.getMRP());
                        item.put("amount", cnp.get_Claims_amount());
                        item.put("scheme_code", cnp.getSche_code());

                        PRODUCTOrder_ids.add(cnp.get_category_code());


                        //item.put("scheme_amount", cnp.get_Target_Text());
                        //item.put("item_number", cnp.get_delivery_product_id());
                        //item.put("discount_type", cnp.get_stocks_product_text());
                        product.put(item);
                        //Log.d("quantity","quantity"+cnp.getquantity());
                    }
                }

//         for (int i = 0; i < 10; i++)
//         {
//
//
//
//         }

                List<Local_Data> return_order_con = dbvoc.GetOrders_return_All("Secondary Sales / Retail Sales");

                if(return_order_con.size() > 0)
                {
                    return_order_Flag = "true";
                }
                else
                {
                    return_order_Flag = "false";
                }

                for (Local_Data cn : return_order_con)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("order_number", cn.get_category_code());

                    Order_number = cn.get_category_code();
                    customer_id = cn.get_category_id();
                    // product_value.put("order_date", cn.getCUSTOMER_ORDER_DATE());
                    // product_value.put("order_take_by", "");
                    product_value.put("customer_code", customer_id);

                    product_value.put("email", Global_Data.GLOvel_USER_EMAIL);


                    product_value.put("latitude", cn.getlatitude());
                    product_value.put("longitude", cn.getlongitude());
                    product_value.put("signature_path", cn.getSignature_image());
                    product_value.put("distributor_code", cn.getDISTRIBUTER_ID());
                    // product_value.put("customer_account_code", cn.getCUSTOMER_ID());
                    // product_value.put("remarks", cn.getCUSTOMER_REMARKS());
                    //product_value.put("signature_image_name", uploadImage);
                    product_value.put("device_code", Global_Data.device_id);


                    if(cn.get_shedule_payment_mode().equalsIgnoreCase("Secondary Sales / Retail Sales"))
                    {
                        s = "Retail Sales";
                    }
                    else
                    {
                        s = cn.get_shedule_payment_mode();
                    }
                    product_value.put("order_type", s);
                    // product_value.put("conference_code", cn.getconference_id());
                    order_return.put(product_value);
                    Log.d("count", "a" + ++a);
                    //delete_order_no = cn.getORDER_NUMBER();
                    List<Local_Data> contactsproduct = dbvoc.Get_OrderProducts_return(cn.get_category_code());
                    for (Local_Data cnp : contactsproduct) {
                        JSONObject item = new JSONObject();
                        item.put("order_number", cnp.get_category_code());
                        item.put("item_number", cnp.get_delivery_product_id());
                        item.put("total_return_qty", cnp.get_stocks_product_quantity());
                        item.put("MRP", cnp.getMRP());
                        item.put("amount", cnp.get_Claims_amount());

                        ++items_count;
                        //item.put("scheme_amount", cnp.get_Target_Text());
                        //item.put("item_number", cnp.get_delivery_product_id());
                        //item.put("discount_type", cnp.get_stocks_product_text());
                        product_return.put(item);
                        //Log.d("quantity","quantity"+cnp.getquantity());
                    }
                }

                List<Local_Data> no_order_con = dbvoc.getNoOrders();

                if(no_order_con.size() > 0)
                {
                    no_order_Flag = "true";
                }
                else
                {
                    no_order_Flag = "false";
                }

                for (Local_Data cn : no_order_con)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("customer_code", cn.getLEGACY_CUSTOMER_CODE());
                    product_value.put("order_number", cn.getorder_number());

                    if( cn.getreason_type().equalsIgnoreCase("Other"))
                    {
                        product_value.put("reason_name", cn.getreason_code());
                    }
                    else
                    {
                        product_value.put("reason_code", cn.getreason_code());
                    }


                    product_value.put("user_email", cn.getuser_email());
                    product_value.put("latitude", cn.getlatitude());
                    product_value.put("longitude", cn.getlongitude());

                    no_orders.put(product_value);

                }



                if(order_Flag.equalsIgnoreCase("true") || return_order_Flag.equalsIgnoreCase("true") || no_order_Flag.equalsIgnoreCase("true") || Retailer_Flag.equalsIgnoreCase("true"))
                {
                    Final_Flag_ORDER_N += " "+"order";
                }
                else
                {
                    Final_Flag_ORDER_N = "";
                }

                if(Final_Flag_ORDER_N.equalsIgnoreCase(""))
                {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(context, "No record found for sync.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                }
                else {
                    product_valuenew.put("orders", order);
                    product_valuenew.put("order_products", product);
                    product_valuenew.put("return_orders", order_return);
                    product_valuenew.put("return_order_products", product_return);
                    product_valuenew.put("no_orders", no_orders);
                    product_valuenew.put("customers", customer);
                    product_valuenew.put("imei_no", Global_Data.device_id);

                    Log.d("Orders", order.toString());
                    Log.d("order_products", product.toString());
                    Log.d("return_orders", order_return.toString());
                    Log.d("return_order_products", product_return.toString());
                    Log.d("no_orders",no_orders.toString());
                    Log.d("customers",customer.toString());

                    // HashMap<String, String> params = new HashMap<String, String>();
                    //params.put("token", json.toString());



                    // RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                    // PreferencesHelper Prefs = new PreferencesHelper(MasterSyncData.this);
                    Global_Data.SYNC_ORDER_COUNT = String.valueOf(order_count);

                    //String URL = Prefs.GetPreferences("URL");
                    String domain = context.getResources().getString(R.string.service_domain);
                    Log.i("volley", "domain: " + domain + "uploads/upload_orders");
                    final LoginDataBaseAdapter finalLoginDataBaseAdapter = loginDataBaseAdapter;
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain + "uploads/upload_orders", product_valuenew, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("volley", "response: " + response);
                            //  Toast.makeText(context, "Order Sync Successfully", Toast.LENGTH_LONG).show();


//                        if(response.has("result"))
//                        {
                            try {
                                response_result = response.getString("result");

//                                if(response_result.equalsIgnoreCase("Device not found."))
//                                {
//                                    Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
//                                    dialog.dismiss();
//                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    public void run() {

                                        dialog.dismiss();
                                    }
                                });
                            }
//                        }
//                        else
//                        {
//                            response_result = "data";
//                        }
                            if (response_result.equalsIgnoreCase("Device not found."))
                            {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast toast = Toast.makeText(context, "Device Not Found", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        dialog.dismiss();
                                    }
                                });

                            } else if (response_result.equalsIgnoreCase("Created Data Successfully.")) {

                                final String finalResponse_result = response_result;
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast toast = Toast.makeText(context, finalResponse_result, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                });

                                String val = "";
                                dbvoc.getDeleteTableorder("Secondary Sales / Retail Sales");

                                try {
                                    if (PRODUCTOrder_ids.isEmpty()) {
                                        Log.d("Product array empty", "EMPTY");
                                    } else {
                                        for (int i = 0; i < PRODUCTOrder_ids.size(); i++) {
                                            dbvoc.getDeleteTableorderproduct_byOrder_id(PRODUCTOrder_ids.get(i));
                                        }
                                    }
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                    ((Activity) context).runOnUiThread(new Runnable() {
                                        public void run() {

                                            dialog.dismiss();
                                        }
                                    });
                                }

                                PRODUCTOrder_ids.clear();
                                dbvoc.getDeleteTable("no_orders");
                                dbvoc.getDeleteTable("returnordernew");
                                dbvoc.getDeleteTable("returnorder_products_new");
                                dbvoc.updateCustomerby_CreateAt(val);
                                for (int i = 0; i < order_results.size(); i++) {
                                    dbvoc.getDeleteTableorderproduct(order_results.get(i));
                                }

                                ((Activity) context).runOnUiThread(new Runnable() {
                                    public void run() {

                                        dialog.dismiss();
                                    }
                                });


                                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
                                SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm:ss");

                                String Current_Date = sdf.format(c.getTime());
                                String Current_Time = sdf_time.format(c.getTime());


                                dbvoc.getDeleteTable("order_details");
                                LoginDataBaseAdapter loginDataBaseAdapter = new LoginDataBaseAdapter(context);
                                loginDataBaseAdapter = loginDataBaseAdapter.open();

                                loginDataBaseAdapter.insert_order_details(Current_Date, Current_Time);

                                Intent intentn = new Intent(context, AllOrders_Sync.class);
                                context.startActivity(intentn);
                            }
                            else
                            {
                                ((Activity) context).runOnUiThread(new Runnable() {
                                    public void run() {

                                        dialog.dismiss();
                                        Toast toast = Toast.makeText(context,response_result, Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                    }
                                });

                            }

                            // dbvoc.getDeleteTable("order_products");
                            //dbvoc.getDeleteTable("orders");
                            //List<Local_Data> contactsn = dbvoc.Getcustomer_email(customer_id);
                            //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");

                            //((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            //((Activity) context).finish();

                            //Intent intentn = new Intent(context, MainActivity.class);
                            //context.startActivity(intentn);
                            //context.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            //((Activity) context).finish();
//                 List<Local_Data> contacts = dbvoc.GetOrders(Global_Val.customer_id);
//                 for (Local_Data cn : contacts)
//                 {
//                     // JSONObject product_value = new JSONObject();
//                     //product_value.put("order_number", cn.getORDER_NUMBER());
//
//                     dbvoc.deleteOrderproductByOCID(cn.getORDER_NUMBER());
//                     dbvoc.deleteOrderTABLE_QuantityValue(cn.getORDER_NUMBER());
//                     dbvoc.deleteBarcode_ByOrder(cn.getORDER_NUMBER());
//                     dbvoc.deleteORDERSNEW(cn.getORDER_NUMBER());
//
//                 }

                            //dbvoc.deleteOrderByOCID(Global_Val.customer_id);
                            //dbvoc.getDeleteTable("DESIGN_CHECK");

//                 Intent i = new Intent(MasterSyncData.this, MyAndroidAppActivity.class);
//                 //				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                 i.putExtra("user_name", user_name);
//                 i.putExtra("confrence_name", confrence_name);
//                 i.putExtra("BackFlag", "nothing");
//                 Global_Val.STOCK_SERVICE_FLAG = "TRUE";
                            //				i.putExtra("Barcode_Number", userInput.getText().toString());
                            //				i.putExtra("BackFlag","Barcode");
//                 startActivity(i);
//                 MasterSyncData.this.finish();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("volley", "error: " + error);

                            ((Activity) context).runOnUiThread(new Runnable() {
                                public void run() {

                                    Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }
                            });

                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    // queue.add(jsObjRequest);
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsObjRequest.setRetryPolicy(policy);
                    requestQueue.add(jsObjRequest);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
            }
            return  null;
        }
    }
}
