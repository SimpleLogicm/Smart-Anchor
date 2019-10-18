package com.anchor.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.activities.NEAREST_CU_Adapter.customButtonListener;
import com.anchor.webservice.ConnectionDetector;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Nearest_Customer extends Activity implements customButtonListener {
	static ConnectionDetector cd;
	String CUSTOMER_NAMEd = "";
	static  Boolean isInternetPresent = false;
	 String cust_mob_no;
	 HttpPost http_post;
	 HttpResponse http_resp;
	 HttpClient http_client;
	 List<NameValuePair> http_nmvalpair;
	 HttpEntity http_entity;
	ArrayAdapter adapter;
	ListView listnearcust;
	//CustomListAdapter adapter1;
	JSONObject jsonobject;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	JSONArray jsonarray;
	String str,str1;
	String customer_address = "";
	LoginDataBaseAdapter loginDataBaseAdapter;
	ProgressDialog dialog;
	String device_id = "";
	String line;
	NEAREST_CU_Adapter customListAdapter;
	EditText searchbox;
	ArrayList <String> product_value = new ArrayList<String>();
	ArrayList <String> searchResults = new ArrayList<String>();
	BufferedReader in = null;
	ArrayList<HashMap<String, String>> arraylist1;
	ArrayList<HashMap<String, String>> arraylist2;
	private ArrayList<HashMap<String, String>> dataArrayList;
	String CUSTOMER_NAME = "Customer_name";
	String CUSTOMER_ADDRESS = "Customer_address";
	String CUSTOMER_DISTANCE = "Customer_distance";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nearest_custlist);
		listnearcust= findViewById(R.id.list_nearest);
		cd = new ConnectionDetector(this);
		isInternetPresent = cd.isConnectingToInternet();
		//searchbox=(EditText)findViewById(R.id.searchbox);
		
//		if(!Global_Data.lat_val.equalsIgnoreCase("") && !Global_Data.long_val.equalsIgnoreCase(""))
//		{
//			View_NearestCustomer(Global_Data.address,Global_Data.lat_val,Global_Data.long_val);
//		}
//		else
//		{
		try
		{
			AppLocationManager appLocationManager = new AppLocationManager(Nearest_Customer.this);
			Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
			Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

			PlayService_Location PlayServiceManager = new PlayService_Location(Nearest_Customer.this);

			if(PlayServiceManager.checkPlayServices(Nearest_Customer.this))
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


			View_NearestCustomer(Global_Data.address,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE);
		//}

		dataArrayList=new ArrayList<HashMap<String, String>>();
//		listnearcust.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                //String contactId = (TextView) view.findViewById(R.id.label);
//            	 String value = (String)parent.getItemAtPosition(position);
//                // Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
////
////            	 List<Local_Data> contacts1 = dbvoc.getCustomerAddress(value.toString().trim());
//////
////            	 for (Local_Data cn : contacts1)
//// 		        {
////
//// 		      	  //product_value.add(cn.getVariant()+" "+cn.getStateName());
//// 		      	//  product_value.add(cn.getVariant()+" "+"ITEM NUMBER " + cn.getImei());
////            		 customer_address = cn.getStateName().trim();
////
//// 		        }
////
////            	  getListViewnew(value.toString().trim(),customer_address);
////
//			    }
//
//        });
		
//		searchResults=new ArrayList<String>(product_value);
//		adapter = new ArrayAdapter<String>(Nearest_Customer.this, R.layout.filtertxt, Cable1Array);
//		//adapter.notifyDataSetChanged();
//		listnearcust.setAdapter(adapter);
		
//		searchbox.addTextChangedListener(new TextWatcher() {
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                //get the text in the EditText
//                String searchString=searchbox.getText().toString();
//                int textLength=searchString.length();
//                searchResults.clear();
//
//                for(int i=0;i<product_value.size();i++)
//                {
//                    String playerName=product_value.get(i).toString();
//                    if(textLength<=playerName.length()){
//                        //compare the String in EditText with Names in the ArrayList
//                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
//                            searchResults.add(product_value.get(i));
//                    }
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//
//            }
//
//            public void afterTextChanged(Editable s) {
//
//
//            }
//        });
			try
			{
				android.app.ActionBar mActionBar = getActionBar();
				mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
				// mActionBar.setDisplayShowHomeEnabled(false);
				// mActionBar.setDisplayShowTitleEnabled(false);
				LayoutInflater mInflater = LayoutInflater.from(this);
				Intent i = getIntent();
				String name = i.getStringExtra("retialer");
				View mCustomView = mInflater.inflate(R.layout.action_bar, null);
				mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
				TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
				mTitleTextView.setText("Nearest Customer");

				TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
				SharedPreferences sp = Nearest_Customer.this.getSharedPreferences("SimpleLogic", 0);

//		       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//		       //	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//				   todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//				}
				try
				{
					int target  = Math.round(sp.getFloat("Target",0));
					int achieved  = Math.round(sp.getFloat("Achived",0));
					Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
					if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
					{
						int age = Math.round(age_float);

						todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
					}else
					{
						int age = Math.round(age_float);

						todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
					}

				}catch(Exception ex){ex.printStackTrace();}

				if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
					//       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
					todaysTarget.setText("Today's Target Acheived");
				}

				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				mActionBar.setHomeButtonEnabled(true);
				mActionBar.setDisplayHomeAsUpEnabled(true);
			}catch(Exception ex){ex.printStackTrace();}

	}

	public  void View_NearestCustomer(String address,String latitude,String longitude)
    {

		SharedPreferences sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
		device_id = sp.getString("devid", "");
       
        loginDataBaseAdapter=new LoginDataBaseAdapter(Nearest_Customer.this);
	    loginDataBaseAdapter=loginDataBaseAdapter.open();
        
        Log.d("device_id ","device_id"+device_id);

       // dbvoc = new DataBaseHelper(context);

        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        dialog = new ProgressDialog(Nearest_Customer.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Metal");
        dialog.setCancelable(false);
        dialog.show();
        
        domain =  getResources().getString(R.string.service_domain);

       // Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }



      try {
        Log.d("Server url","Server url"+domain+"customer_list?device_id="+device_id+"&lat="
       + URLEncoder.encode(latitude, "UTF-8")+"&lon="
    	       + URLEncoder.encode(longitude, "UTF-8")+"&email="
    	    	       +Global_Data.GLOvel_USER_EMAIL);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      StringRequest stringRequest = null;
      try {
        stringRequest = new StringRequest(Method.GET,domain+"customer_list?device_id="+device_id+"&lat="
        	       + URLEncoder.encode(latitude, "UTF-8")+"&lon="
        	       + URLEncoder.encode(longitude, "UTF-8")+"&email="
        	    	       +Global_Data.GLOvel_USER_EMAIL,
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
                                  if(json.has("message"))
                                  {
                                      response_result = json.getString("message");
                                  }
                                  else
                                  {
                                      response_result = "data";
                                  }


                                  if(response_result.equalsIgnoreCase("User not found")) {

                                      //Toast.makeText(Nearest_Customer.this, response_result, Toast.LENGTH_LONG).show();

									  Toast toast = Toast.makeText(getApplicationContext(),response_result,Toast.LENGTH_LONG);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();

                                      Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
                       			      startActivity(a);
                       			      finish();

                                  }
								  if(response_result.equalsIgnoreCase("Customers not found")) {

									 // Toast.makeText(Nearest_Customer.this, response_result, Toast.LENGTH_LONG).show();

									  Toast toast = Toast.makeText(getApplicationContext(),response_result,Toast.LENGTH_LONG);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();

									  Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
									  startActivity(a);
									  finish();
								  }
                                  else
                                  if(json.getJSONArray("data").length()<= 0) {

                                     // Toast.makeText(Nearest_Customer.this, "Customers not found", Toast.LENGTH_LONG).show();

									  Toast toast = Toast.makeText(getApplicationContext(),"Customers not found",Toast.LENGTH_LONG);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();

                                      Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
                       			      startActivity(a);
                       			      finish();
                                  }
                                  else {

                                      JSONArray data = json.getJSONArray("data");
                                      Log.d("data", "data" + data.toString());

                                      Log.d("data", "data length" + data.length());
                                     // Log.d("customers", "customers" + customers.toString());
                                     // Log.d("devices", "devices" + devices.toString());

                                   
                                      dataArrayList.clear();  											
                                    for (int i = 0; i < data.length(); i++) {

                                          JSONObject jsonObject = data.getJSONObject(i);
                                          HashMap<String, String> map = new HashMap<String, String>();
                                          map.put(CUSTOMER_NAME, jsonObject.getString("customer_name"));
                                          map.put(CUSTOMER_ADDRESS, jsonObject.getString("customer_address"));
                                          map.put(CUSTOMER_DISTANCE, jsonObject.getString("distance"));
                                         // product_value.add(jsonObject.getString("code"));
                                          dataArrayList.add(map);


                                      }
                                    
//                                    searchResults=new ArrayList<String>(product_value);
//                            		adapter = new ArrayAdapter<String>(Nearest_Customer.this, R.layout.filtertxt, product_value);
//                            		//adapter.notifyDataSetChanged();
//                            		listnearcust.setAdapter(adapter);
                            		
                            		customListAdapter = new NEAREST_CU_Adapter(Nearest_Customer.this, dataArrayList);
                            		customListAdapter.setCustomButtonListner(Nearest_Customer.this);
                                    listnearcust.setAdapter(customListAdapter);

                                       //dbvoc.update_stockChecks(s_code,s_stock);
                                     // Toast.makeText(Nearest_Customer.this, "Users Sync Successfully.", Toast.LENGTH_LONG).show();

									  Toast toast = Toast.makeText(getApplicationContext(),"Users Sync Successfully.",Toast.LENGTH_LONG);
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
                            	  Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
                   			      startActivity(a);
                   			      finish();
                            	  
                              }

                              dialog.dismiss();
                          } catch (JSONException e) {
                              e.printStackTrace();
                            //  finish();
                              dialog.dismiss();
                              Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
               			      startActivity(a);
               			      finish();
                          }
                          dialog.dismiss();

                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          //Toast.makeText(Nearest_Customer.this, error.getMessage(), Toast.LENGTH_LONG).show();

                          if (error instanceof TimeoutError || error instanceof NoConnectionError) {
							  Toast toast = Toast.makeText(getApplicationContext(), "Network Error",Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
                          } else if (error instanceof AuthFailureError) {
//                              Toast.makeText(Nearest_Customer.this,
//                                      "Server AuthFailureError  Error",
//                                      Toast.LENGTH_LONG).show();
							  Toast toast = Toast.makeText(getApplicationContext(),  "Server AuthFailureError  Error",Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
                          } else if (error instanceof ServerError) {

							  Toast toast = Toast.makeText(getApplicationContext(),"Customers not found",Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
                          } else if (error instanceof NetworkError) {

							  Toast toast = Toast.makeText(getApplicationContext(), "Network   Error",Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
                          } else if (error instanceof ParseError) {
							  Toast toast = Toast.makeText(getApplicationContext(),"ParseError   Error",Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
                          }
                          else
                          {
                             // Toast.makeText(Nearest_Customer.this, error.getMessage(), Toast.LENGTH_LONG).show();

							  Toast toast = Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
                          }
                          dialog.dismiss();
                          Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
           			      startActivity(a);
           			      finish();
                         // finish();
                      }
                  });
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
		      startActivity(a);
		      finish();
      }

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
	
	public void getListViewnew(final String value1,String value2,String name)
	{
		 CUSTOMER_NAMEd = name;
		 final Dialog dialognew = new Dialog(Nearest_Customer.this);
	        dialognew.setCancelable(false);
	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //tell the Dialog to use the dialog.xml as it's layout description
	        dialognew.setContentView(R.layout.customer_address_dialog); 

	        final EditText userInput = dialognew
	                .findViewById(R.id.update_textdialog);
	        
	        final EditText distance = dialognew
	                .findViewById(R.id.cu_distance);

	         // item_description.setText("Address");
	        
	                final Button Submit = dialognew
	                        .findViewById(R.id.update_textdialogclick);
	                
	                final ImageView call_btn = dialognew
	                        .findViewById(R.id.cust_adrs_call);

		            final ImageView map_btn = dialognew
				            .findViewById(R.id.cust_adrs_map);
	                
	                Submit.setText("OK");

		dbvoc= new DataBaseHelper(this);


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

		map_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String s = "";
//				String latlong = c_latlon.getText().toString().trim();
//				String address = c_address.getText().toString().trim();
				// Toast.makeText(view.getContext(), s.trim(), Toast.LENGTH_SHORT).show();
				//Log.d("latlong", "latlong" + latlong);

				if (isInternetPresent) {

//						Toast.makeText(Nearest_Customer.this, "Wait..", Toast.LENGTH_SHORT).show();
//						GeocodingLocation locationAddress = new GeocodingLocation();
//						locationAddress.getAddressFromLocation(value1,
//								Nearest_Customer.this, new GeocoderHandler());

					try {
						if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value1)) {
							Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
									Uri.parse("http://maps.google.com/maps?saddr=" + Global_Data.GLOvel_LATITUDE + "," + Global_Data.GLOvel_LONGITUDE + "&daddr=" + value1 + ""));
							startActivity(intent);
						} else {
							Toast.makeText(Nearest_Customer.this, "Address not found", Toast.LENGTH_SHORT).show();
						}

					} catch (Exception ex) {
						ex.printStackTrace();
					}



				} else {
					Toast toast = Toast.makeText(Nearest_Customer.this, "You don't have internet connection.", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});

	        Submit.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

	                    dialognew.dismiss();

	            }
	        });


		call_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String mobileno = "";
				List<Local_Data> cust_mob = dbvoc.getmob_byshop(CUSTOMER_NAMEd.trim());

				for (Local_Data cnn : cust_mob) {
					mobileno = cnn.getCust_mob();
				}

				if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(mobileno.trim()))
				{

					requestPhoneCallPermission(mobileno.trim());
				}
				else
				{
					Toast.makeText(Nearest_Customer.this, "Contact No Not Found.", Toast.LENGTH_LONG).show();
				}


			}
		});
	        
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
	
	public void onButtonClickListner(int position, String value1,String value2,View v,String value3) {
//        Toast.makeText(DeleteOrder.this, "Button click " + value,
//                Toast.LENGTH_SHORT).show();

      int pos = position;
      
      String value = value1;
      String valuen = value2;
		String name = value3;

      getListViewnew(value,valuen,name);

    }

 @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();

	 Intent a = new Intent(Nearest_Customer.this,MainActivity.class);
	 startActivity(a);
	 //finish();

	}

	private class GeocoderHandler extends Handler {
		@Override
		public void handleMessage(Message message) {
			String locationAddress;
			switch (message.what) {
				case 1:
					Bundle bundle = message.getData();
					locationAddress = bundle.getString("address");
					break;
				default:
					locationAddress = null;
			}

			if(locationAddress.equalsIgnoreCase("Location not found for this address"))
			{
				Toast.makeText(Nearest_Customer.this, "Customer location not found.", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://maps.google.com/maps?saddr="+Global_Data.GLOvel_LATITUDE+","+Global_Data.GLOvel_LONGITUDE+"&daddr="+locationAddress+""));
				startActivity(intent);
			}

		}
	}

	private void requestPhoneCallPermission(final String mobile_number) {
		Dexter.withActivity(this)
				.withPermission(Manifest.permission.CALL_PHONE)
				.withListener(new PermissionListener() {
					@Override
					public void onPermissionGranted(PermissionGrantedResponse response) {

						Intent callIntent = new Intent(Intent.ACTION_CALL);
						callIntent.setData(Uri.parse("tel:" + mobile_number));
						startActivity(callIntent);

						return;

					}

					@Override
					public void onPermissionDenied(PermissionDeniedResponse response) {
						// check for permanent denial of permission
						if (response.isPermanentlyDenied()) {
							showSettingsDialog();
						}
					}

					@Override
					public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
						token.continuePermissionRequest();
					}
				}).check();
	}

	/**
	 * Showing Alert Dialog with Settings option
	 * Navigates user to app settings
	 * NOTE: Keep proper title and message depending on your app
	 */
	private void showSettingsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Nearest_Customer.this);
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
}