package com.anchor.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anchor.activities.Status_Adapter.customButtonListener;
import com.anchor.services.getServices;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import android.support.v7.app.ActionBar;

public class Status_Activity extends Activity implements customButtonListener {
	ListView list_statusclick;
	String ORDER_ID = "order_id";
	String MANAGER_REMARK = "manager_remarks";
	String ORDER_STATUS = "order_status";
	String ITEM_DESC = "item_desc";
	String ITEM_AMOUNT = "item_tamount";
	String ITEM_NUMBER = "item_number";
	 HttpPost http_post;
	 HttpResponse http_resp;
	 HttpClient http_client;
	 List<NameValuePair> http_nmvalpair;
	 HttpEntity http_entity;
	ArrayAdapter adapter;
	ListView status_list;
	//CustomListAdapter adapter1;
	JSONObject jsonobject;
	Button but_listclick,butaprove_listclick,but_addmore ;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	JSONArray jsonarray;
	String str,str1,cust_str;
	Spinner city_spinner, state_spinner,spinner_click ;
	String customer_address = "";
	LoginDataBaseAdapter loginDataBaseAdapter;
	ProgressDialog dialog;
	String device_id = "";
	String line;
	Button status_button,cancel_list;
	Status_Adapter customListAdapter;
	OrderStatus_Adapter OrderListAdapter;
	EditText searchbox;
	ArrayList <String> product_value = new ArrayList<String>();
	ArrayList <String> searchResults = new ArrayList<String>();
	BufferedReader in = null;
	ArrayList<HashMap<String, String>> arraylist1;
	ArrayList<HashMap<String, String>> arraylist2;
	private ArrayList<HashMap<String, String>> dataArrayList;
	private ArrayList<HashMap<String, String>> OrderDataList;
	String CUSTOMER_NAME = "Customer_name";
	String CUSTOMER_ADDRESS = "Customer_address";
	String CUSTOMER_DISTANCE = "Customer_distance";
	AutoCompleteTextView autoCompleteTextView1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_list);
		
		status_list=(ListView)findViewById(R.id.status_list);
		cancel_list=(Button)findViewById(R.id.cancel_list);
				
		TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        device_id = telephonyManager.getDeviceId();
		
        dataArrayList=new ArrayList<HashMap<String, String>>();
    	
    	OrderDataList=new ArrayList<HashMap<String, String>>();
    	
        if(Global_Data.GLOVEL_ORDER_REJECT_FLAG == "TRUE")
	    {
    	  getListViewg(Global_Data.GLObalOrder_id,Global_Data.GLOvel_REMARK);
	    } 
        else
        {
        	 View_NearestCustomer(device_id, Global_Data.cust_str);
        }
       
        
//        String[] Cable1Array = {"ARIAL BUNCHED CABLE","CONTROL CABLE","EXTRA HIGH VOLTAGE","INSTRUMENTS CABLES","POWER CABLES","SERVICE CABLES","SOLAR CABLES","TELEPHONE CABLES(ARM)","THERMOCOUPLE/COMPENSATING"};
//        adapter = new ArrayAdapter<String>(Status_Activity.this, R.layout.filtertxt, Cable1Array);
////		//adapter.notifyDataSetChanged();
//        status_list.setAdapter(adapter);
		
    	
    	
    	cancel_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				   finish();
			    }
        });
    	
	    	
    	
    	  status_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
               public void onItemClick(AdapterView<?> parent, View view,
                                     int position, long id) {
            	 
                 TextView contactId = (TextView) view.findViewById(R.id.ordername);
                 String str=contactId.getText().toString();
                 
                 TextView contactstatus = (TextView) view.findViewById(R.id.remark);
                 String strr=contactstatus.getText().toString();
                 
                 Global_Data.GLOvel_REMARK = strr;
             	// String value = (String)parent.getItemAtPosition(position); 
                  //Toast.makeText(getApplicationContext(),"fgd:"+str,Toast.LENGTH_LONG).show();
//                 if(Global_Data.GLOvel_REMARK.equalsIgnoreCase("ordered"))
//                 {
//                	 Toast.makeText(getApplicationContext(), "Quotation Already aproved", Toast.LENGTH_LONG).show();
//                 }
//                 else
//                 {
                	 getListViewg(str,strr);
                // }
             	  
 			    }
         });

				android.app.ActionBar mActionBar = getActionBar();
				mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
		      // mActionBar.setDisplayShowHomeEnabled(false);
		      // mActionBar.setDisplayShowTitleEnabled(false);
		       LayoutInflater mInflater = LayoutInflater.from(this);
		       Intent i = getIntent();
				String name = i.getStringExtra("retialer");
		       View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		       mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
		       TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
		       mTitleTextView.setText("Order Status");
		       
		       TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
		       SharedPreferences sp = Status_Activity.this.getSharedPreferences("SimpleLogic", 0);

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
//
//		       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//		       	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//				}


//		       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//		 //       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//		       	todaysTarget.setText("Today's Target Acheived");
//				}
		       
		       mActionBar.setCustomView(mCustomView);
		       mActionBar.setDisplayShowCustomEnabled(true);
		       mActionBar.setHomeButtonEnabled(true);
		       mActionBar.setDisplayHomeAsUpEnabled(true);
	}

	public  void View_NearestCustomer(String device_id, String cust_id)
    {
      
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        device_id = telephonyManager.getDeviceId();
        
         loginDataBaseAdapter=new LoginDataBaseAdapter(Status_Activity.this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();

	     // dbvoc = new DataBaseHelper(context);

        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        dialog = new ProgressDialog(Status_Activity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Metal");
        dialog.setCancelable(false);
        dialog.show();
        
        domain =  getResources().getString(R.string.service_domain);
        
        Log.d("url", "url customer"+domain+"quotations/send_quotation_status?customer_code="+cust_id+"&imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL);

       // Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }

    
      StringRequest stringRequest = null;
      stringRequest = new StringRequest(Method.GET,domain+"quotations/send_quotation_status?customer_code="+cust_id+"&imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL,
     
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

	                          if(response_result.equalsIgnoreCase("product not found")) {
	                              //Toast.makeText(Status_Activity.this, response_result, Toast.LENGTH_LONG).show();

								  Toast toast = Toast.makeText(Status_Activity.this, response_result, Toast.LENGTH_LONG);
								  toast.setGravity(Gravity.CENTER, 0, 0);
								  toast.show();

	                              Intent a = new Intent(Status_Activity.this,MainActivity.class);
	               			      startActivity(a);
	               			      finish();
	                          }
	                          else
	                          if(response_result.equalsIgnoreCase("Device not registered")) 
	                          {

		                           // Toast.makeText(Status_Activity.this, response_result, Toast.LENGTH_LONG).show();

								  Toast toast = Toast.makeText(Status_Activity.this, response_result, Toast.LENGTH_LONG);
								  toast.setGravity(Gravity.CENTER, 0, 0);
								  toast.show();

		                      }
	                          else
	                          if(json.getJSONArray("quotations").length()<= 0) {

	                             // Toast.makeText(Status_Activity.this, "Quotations Not Found for Customer", Toast.LENGTH_LONG).show();

								  Toast toast = Toast.makeText(Status_Activity.this, "Quotations Not Found for Customer", Toast.LENGTH_LONG);
								  toast.setGravity(Gravity.CENTER, 0, 0);
								  toast.show();

	                              Intent a = new Intent(Status_Activity.this,MainActivity.class);
	               			      startActivity(a);
	               			      finish();

	                          }
	                          else {
	                        	  
	                        	  dbvoc.getDeleteTable("status_master");

	                              JSONArray quotations = json.getJSONArray("quotations");
	                              Log.d("quotations", "quotations" + quotations.toString());

	                              Log.d("quotations", "quotations length" + quotations.length());
	                              
	                              JSONArray quotation_products = json.getJSONArray("quotation_products");
	                              Log.d("quotation_products", "quotation_products" + quotation_products.toString());

	                              Log.d("quotation_products", "quotation_products length" + quotation_products.length());
	                              
	                             // Log.d("customers", "customers" + customers.toString());
	                             // Log.d("devices", "devices" + devices.toString());

	                           
	                                  dataArrayList.clear();  											
	                                 for(int i = 0; i < quotations.length(); i++)
									 {

	                                  JSONObject jsonObject = quotations.getJSONObject(i);
	                                  HashMap<String, String> map = new HashMap<String, String>();
	                                  map.put(ORDER_ID, jsonObject.getString("original_quote_number"));
	                                  if(jsonObject.getString("comments").equalsIgnoreCase(null) || jsonObject.getString("comments").equalsIgnoreCase("null"))
	                                  {
	                                	  map.put(MANAGER_REMARK, "no comment");
	                                  }else
	                                  {
	                                	  map.put(MANAGER_REMARK, jsonObject.getString("comments"));
	                                  }
	                                  
	                                 
	                                  map.put(ORDER_STATUS, jsonObject.getString("aasm_state"));
	                                  
	                                  if(jsonObject.getString("aasm_state").equalsIgnoreCase("ordered") || jsonObject.getString("aasm_state").equalsIgnoreCase("lost"))
	                                  {
	                                	  dbvoc.getDeleteTableorder_bycustomer_INN("Institutional Sales",jsonObject.getString("original_quote_number"));
	                                	  dbvoc.getDeleteTableorderproduct_bycustomer_INN("Institutional Sales",jsonObject.getString("original_quote_number"));
	                                  }
	                                  // product_value.add(jsonObject.getString("code"));
	                                  dataArrayList.add(map);
	                              }
	                                 
	                                 for (int i = 0; i < quotation_products.length(); i++) {

		                                  JSONObject jsonObject = quotation_products.getJSONObject(i);
		                                //  HashMap<String, String> map = new HashMap<String, String>();
		                                 // map.put(ORDER_ID, jsonObject.getString("order_number"));
		                                 
		                                  loginDataBaseAdapter.Order_Status(jsonObject.getString("original_quote_number"), jsonObject.getString("item_name"), jsonObject.getString("amount"), jsonObject.getString("item_number"));
		                                 
//		                                  if(jsonObject.getString("aasm_state").equalsIgnoreCase("approved"))
//		                                  {
//		                                	  dbvoc.getDeleteTableorderproduct_bycustomer_INN("Institutional Sales",jsonObject.getString("order_number"));
//		                                  }
		                              }
	                            
	                                
//                                    searchResults=new ArrayList<String>(product_value);
//                            		adapter = new ArrayAdapter<String>(Nearest_Customer.this, R.layout.filtertxt, product_value);
//                            		//adapter.notifyDataSetChanged();
//                            		listnearcust.setAdapter(adapter);
	                    		
	                    		customListAdapter = new Status_Adapter(Status_Activity.this, dataArrayList);
                        		customListAdapter.setCustomButtonListner(Status_Activity.this);
                        		status_list.setAdapter(customListAdapter);
	                            
//	                        	String[] Cable1Array = {"ARIAL BUNCHED CABLE","CONTROL CABLE","EXTRA HIGH VOLTAGE","INSTRUMENTS CABLES","POWER CABLES","SERVICE CABLES","SOLAR CABLES","TELEPHONE CABLES(ARM)","THERMOCOUPLE/COMPENSATING"};
//	                            adapter = new ArrayAdapter<String>(Status_Activity.this, R.layout.filtertxt, Cable1Array);
////	                    		//adapter.notifyDataSetChanged();
//	                            status_list.setAdapter(adapter);
	                            
                        		  //dbvoc.update_stockChecks(s_code,s_stock);
	                             // Toast.makeText(Status_Activity.this, "List Sync Successfully.", Toast.LENGTH_LONG).show();

								  Toast toast = Toast.makeText(Status_Activity.this, "List Sync Successfully.", Toast.LENGTH_LONG);
								  toast.setGravity(Gravity.CENTER, 0, 0);
								  toast.show();



	                             // Global_Val.STOCK_SERVICE_FLAG = "";
	                              dialog.dismiss();
	                              //finish();
	                          }

	                        //  finish();
	                          // }

	                          // output.setText(data);
	                      }catch(JSONException e){
	                    	  
	                    	  e.printStackTrace(); dialog.dismiss(); 
	                    	  Intent a = new Intent(Status_Activity.this,MainActivity.class);
	           			      startActivity(a);
	           			      finish();
                         }


	                      dialog.dismiss();
	                  } catch (JSONException e) {
	                      e.printStackTrace();
	                    //  finish();
	                      dialog.dismiss();
	                      Intent a = new Intent(Status_Activity.this,MainActivity.class);
	       			      startActivity(a);
	       			      finish();
	                  }
	                  dialog.dismiss();

	              }
	          },
	          new Response.ErrorListener() {
	              @Override
	              public void onErrorResponse(VolleyError error) {
	                  //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

	                  if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//	                      Toast.makeText(Status_Activity.this,
//	                              "Network Error",
//	                              Toast.LENGTH_LONG).show();
						  Toast toast = Toast.makeText(Status_Activity.this,  "Network Error", Toast.LENGTH_LONG);
						  toast.setGravity(Gravity.CENTER, 0, 0);
						  toast.show();

	                  } else if (error instanceof AuthFailureError) {
//	                      Toast.makeText(Status_Activity.this,
//	                              "Server AuthFailureError  Error",
//	                              Toast.LENGTH_LONG).show();
						  Toast toast = Toast.makeText(Status_Activity.this,  "Server AuthFailureError  Error", Toast.LENGTH_LONG);
						  toast.setGravity(Gravity.CENTER, 0, 0);
						  toast.show();
	                  } else if (error instanceof ServerError) {
//	                      Toast.makeText(Status_Activity.this,
//	                              "Server   Error",
//	                              Toast.LENGTH_LONG).show();
						  Toast toast = Toast.makeText(Status_Activity.this,
								  "Server   Error",
								  Toast.LENGTH_LONG);
						  toast.setGravity(Gravity.CENTER, 0, 0);
						  toast.show();
	                  } else if (error instanceof NetworkError) {
//	                      Toast.makeText(Status_Activity.this,
//	                              "Network   Error",
//	                              Toast.LENGTH_LONG).show();

						  Toast toast = Toast.makeText(Status_Activity.this,
								  "Network   Error",
								  Toast.LENGTH_LONG);
						  toast.setGravity(Gravity.CENTER, 0, 0);
						  toast.show();
	                  } else if (error instanceof ParseError) {
//	                      Toast.makeText(Status_Activity.this,
//	                              "ParseError   Error",
//	                              Toast.LENGTH_LONG).show();

						  Toast toast = Toast.makeText(Status_Activity.this,
								  "ParseError   Error",
								  Toast.LENGTH_LONG);
						  toast.setGravity(Gravity.CENTER, 0, 0);
						  toast.show();
	                  }
	                  else
	                  {
	                      //Toast.makeText(Status_Activity.this, error.getMessage(), Toast.LENGTH_LONG).show();

						  Toast toast = Toast.makeText(Status_Activity.this,
								  error.getMessage(),
								  Toast.LENGTH_LONG);
						  toast.setGravity(Gravity.CENTER, 0, 0);
						  toast.show();
	                  }
	                  
	                  dialog.dismiss();
	                  Intent a = new Intent(Status_Activity.this,MainActivity.class);
	   			      startActivity(a);
	   			      finish();
	                 // finish();
	              }
	          });

      RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

	
	public void getListViewg(String order_id,String remark)
	{
		 Global_Data.GLOVEL_ORDER_REJECT_FLAG = "";
		 Global_Data.GLObalOrder_id = order_id;
		 final Dialog dialognew = new Dialog(Status_Activity.this);
	        dialognew.setCancelable(false);
	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //tell the Dialog to use the dialog.xml as it's layout description
	        dialognew.setContentView(R.layout.status_click);
	        
	        list_statusclick = (ListView) dialognew
	                        .findViewById(R.id.list_statusclick);
	        
	        but_listclick = (Button) dialognew
                    .findViewById(R.id.butback_listclick);
	        
	        butaprove_listclick = (Button) dialognew
                    .findViewById(R.id.butapprove_listclick);
	        
	        spinner_click = (Spinner) dialognew
                    .findViewById(R.id.spinner_addmore);
	        
	        but_addmore = (Button) dialognew
                    .findViewById(R.id.but_addmore_click);
           String []customer_array = {"Select Quotation status","ordered", "lost", "pending"};
	        
           if(Global_Data.GLOvel_REMARK.equals("ordered") || Global_Data.GLOvel_REMARK.equals("lost")) 
           {
        	   butaprove_listclick.setEnabled(false);
        	   butaprove_listclick.setBackgroundResource(android.R.drawable.btn_default);
        	   but_addmore.setEnabled(false);
        	   spinner_click.setEnabled(false);
        	   but_addmore.setBackgroundResource(android.R.drawable.btn_default);
        	   spinner_click.setBackgroundResource(android.R.drawable.btn_default);
           }
           else
           {
        	   but_addmore.setEnabled(true);
        	   but_addmore.setEnabled(true);
        	   butaprove_listclick.setEnabled(true);
           }
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item,
					customer_array);
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        spinner_click.setAdapter(adapter);
	        
	        OrderDataList.clear();
	        
	        List<Local_Data> contcheck = dbvoc.getStatusOrderId_BYORDERPRODUCT(order_id); 
	        if(contcheck.size() <= 0)
	        {
	        	 List<Local_Data> cont1 = dbvoc.getStatusOrderId(order_id);      
		          for (Local_Data cnt1 : cont1) {
		        	  HashMap<String, String> map = new HashMap<String, String>();
		        	  map.put(ITEM_DESC, cnt1.getOrderStatus());
		              map.put(ITEM_AMOUNT, cnt1.getOrderAmount());
		              map.put(ITEM_NUMBER, cnt1.getorder_number());
		              OrderDataList.add(map);
		          }
	        }
	        else
	        {
	        	 List<Local_Data> cont1 = dbvoc.getStatusOrderId_BYORDERPRODUCT(order_id);      
		          for (Local_Data cnt1 : cont1) {
		        	  HashMap<String, String> map = new HashMap<String, String>();
		        	  map.put(ITEM_DESC, cnt1.getOrderStatus());
		              map.put(ITEM_AMOUNT, cnt1.getOrderAmount());
		              map.put(ITEM_NUMBER, cnt1.getorder_number());
		              OrderDataList.add(map);
		          }
	        }
	       
	          
	         
	          
	        
//	        String[] Cable1Array = {"ARIAL BUNCHED CABLE","CONTROL CABLE","EXTRA HIGH VOLTAGE","INSTRUMENTS CABLES","POWER CABLES","SERVICE CABLES","SOLAR CABLES","TELEPHONE CABLES(ARM)","THERMOCOUPLE/COMPENSATING"};
//            adapter = new ArrayAdapter<String>(Status_Activity.this, R.layout.filtertxt, Cable1Array);
////    		//adapter.notifyDataSetChanged();
//            list_statusclick.setAdapter(adapter);
	        
	        OrderListAdapter = new OrderStatus_Adapter(Status_Activity.this, OrderDataList);
	        //OrderListAdapter.setCustomButtonListner(Status_Activity.this);
	        list_statusclick.setAdapter(OrderListAdapter);
	                
//	        final Button cancel = (Button) dialognew
//	                .findViewById(R.id.cancel_list);
	       
				//View_NearestCustomer(device_id, cust_str);
	        
	        but_addmore.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	 Global_Data.GLOVEL_ORDER_REJECT_FLAG = "TRUE";
	            	 Intent i = new Intent(Status_Activity.this,NewOrderActivity.class);
	        		 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	        	     startActivity(i);
	        	     Status_Activity.this.finish();
	        	     dialognew.dismiss();
	
	            }
	        });
		        
	        butaprove_listclick.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

					if (spinner_click.getSelectedItem().toString().equalsIgnoreCase("Select Quotation status")) {
						Toast toast = Toast.makeText(Status_Activity.this,"Please Select Quotation status", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					else
					{
					Log.d("flag new", "flag new"+spinner_click.getSelectedItem().toString().trim());	
	            	getServices.SYNCORDER_BYCustomerINSTI_NEW(Status_Activity.this,spinner_click.getSelectedItem().toString().trim()); 
	            	//View_NearestCustomer(device_id, Global_Data.cust_str);
	                dialognew.dismiss();
					}
	
	            }
	        });
	        
	        but_listclick.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	View_NearestCustomer(device_id, Global_Data.cust_str);
	                dialognew.dismiss();
	
	            }
	        });
        
	        dialognew.show();
	}
	
	public void getListViewnew(final String value1,String value2)
	{
		 final Dialog dialognew = new Dialog(Status_Activity.this);
	        dialognew.setCancelable(false);
	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //tell the Dialog to use the dialog.xml as it's layout description
	        dialognew.setContentView(R.layout.customer_address_dialog); 

	        final EditText userInput = (EditText) dialognew
	                .findViewById(R.id.update_textdialog);
	        
	        final EditText distance = (EditText) dialognew
	                .findViewById(R.id.cu_distance);
	        
	       
	        
	         // item_description.setText("Address");
	        

	                final Button Submit = (Button) dialognew
	                        .findViewById(R.id.update_textdialogclick);
	                
	                final Button update_cancel = (Button) dialognew
	                        .findViewById(R.id.update_cancel);
	                update_cancel.setVisibility(View.GONE);
	                
	                Submit.setText("OK");

	        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value1))
	        {
	            userInput.setText("Address : "+ value1);
	        }
	        
	        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value2))
	        {
	        	distance.setText("Distance : "+value2);
	        }

//	        final Button cancel = (Button) dialognew
//	                .findViewById(R.id.cancel);

	        Submit.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            		            	
	                    dialognew.dismiss();

	            }
	        });
	        
//	        update_cancel.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//
////	               
//	            	
//	                    dialognew.dismiss();
//
//
//	            }
	       // });

//	        cancel.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                dialognew.dismiss();
	//
	//
//	            }
//	        });

	        dialognew.show();
	}
	
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
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
	
	public void onButtonClickListner(int position, String value1,String value2,View v) {
//        Toast.makeText(DeleteOrder.this, "Button click " + value,
//                Toast.LENGTH_SHORT).show();

      int pos = position;
      
      String value = value1;
      String valuen = value2;

      getListViewnew(value,valuen);
    }

 @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
			    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
	
}