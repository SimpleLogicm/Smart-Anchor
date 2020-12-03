package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.anchor.helper.MyPeriodicwork;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cpm.simplelogic.helper.GPSTracker;


public class Expenses extends Activity implements OnItemSelectedListener {
	private DatePickerDialog fromDatePickerDialog,fromDatePickerDialog1;
	private SimpleDateFormat dateFormatter;
	Boolean isInternetPresent = false;
	GPSTracker gps;

	ConnectionDetector cd;
    String[] popUpContents;
	PopupWindow popupWindowDogs;
	ProgressDialog dialog;
	int spine_flag = 0;
	Spinner type_spinner;
	EditText exp_date,exp_cost,exp_discr,exp_from,exp_to,exp_mot;
	Button exp_submit;
	LoginDataBaseAdapter loginDataBaseAdapter;
	private String Current_Date = "";
	Date date2;
	private String Expenses_text = "Select";
	private String[] city_state = { "Select Type", "Travel", "Miscellaneous" };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenses);

		type_spinner= findViewById(R.id.expenses);
		exp_date= findViewById(R.id.exp_date);
		exp_cost= findViewById(R.id.exp_cost);
		exp_discr= findViewById(R.id.exp_discr);
		exp_from= findViewById(R.id.exp_from);
		exp_to= findViewById(R.id.exp_to);
		exp_mot= findViewById(R.id.exp_mot);
		exp_submit= findViewById(R.id.exp_submit);

		exp_discr.setFilters(new InputFilter[]{filter});
		exp_to.setFilters(new InputFilter[]{filter});
		exp_from.setFilters(new InputFilter[]{filter});
		exp_mot.setFilters(new InputFilter[]{filter});

		dateFormatter = new SimpleDateFormat("MMMM-yyyy", Locale.US);
		Calendar newCalendar = Calendar.getInstance();
		cd = new ConnectionDetector(Expenses.this);

		Global_Data.context = Expenses.this;

		try {
			PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
					MyPeriodicwork.class, 15, TimeUnit.MINUTES
			).addTag("otpValidator").build();
			WorkManager.getInstance(this).enqueueUniquePeriodicWork("Work",
					ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);

		}catch(Exception ex) {
			ex.printStackTrace();
		}



		fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				String yr_reg=Integer.toString(year);
				String mnth_reg=Integer.toString(monthOfYear+1);
				String date_reg=Integer.toString(dayOfMonth);

				exp_date.setText(date_reg+"-"+(dateFormatter.format(newDate.getTime())));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		exp_date.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fromDatePickerDialog.getWindow().getAttributes().verticalMargin = 0.5F;
				fromDatePickerDialog.show();
			}
		});


		ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, city_state);

		adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type_spinner.setAdapter(adapter_state1);
		type_spinner.setOnItemSelectedListener(this);

		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String strDate = sdf.format(c.getTime());
		Current_Date = sdf.format(c.getTime());
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
			TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText("Expenses");

			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = Expenses.this.getSharedPreferences("SimpleLogic", 0);

			ImageView H_LOGO = mCustomView.findViewById(R.id.Header_logo);
			H_LOGO.setImageResource(R.drawable.rs);
			H_LOGO.setVisibility(View.VISIBLE);




//      if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//      	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		  todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//		}
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
//      	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
				todaysTarget.setText("Today's Target Acheived");
			}
			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}
		exp_cost.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {



			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if(!String.valueOf(s).equalsIgnoreCase(""))
				{
					if(Integer.parseInt(String.valueOf(s))<=0)
					{
						exp_cost.setText("");
					}
				}

			}
		});

		exp_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				requestGPSPermissionsigna();

			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
							   long arg3) {
		// TODO Auto-generated method stub

		if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Travel")) {

			Expenses_text = "Travel";
			exp_date.setVisibility(View.VISIBLE);
			exp_cost.setVisibility(View.VISIBLE);
			exp_discr.setVisibility(View.VISIBLE);
			exp_from.setVisibility(View.VISIBLE);
			exp_to.setVisibility(View.VISIBLE);
			exp_mot.setVisibility(View.VISIBLE);
			exp_submit.setVisibility(View.VISIBLE);

			exp_cost.setText("");
			exp_discr.setText("");
			exp_mot.setText("");
			exp_from.setText("");
			exp_to.setText("");
			exp_date.setText("");
			//exp_discr.setText("");
		}
		else if (type_spinner.getSelectedItem().toString().equalsIgnoreCase("Miscellaneous")) {
			Expenses_text = "Miscellaneous";
			exp_date.setVisibility(View.VISIBLE);
			exp_cost.setVisibility(View.VISIBLE);
			exp_discr.setVisibility(View.VISIBLE);
			exp_from.setVisibility(View.GONE);
			exp_to.setVisibility(View.GONE);
			exp_mot.setVisibility(View.GONE);
			exp_submit.setVisibility(View.VISIBLE);

			exp_cost.setText("");
			exp_discr.setText("");
			exp_mot.setText("");
			exp_from.setText("");
			exp_to.setText("");
			exp_date.setText("");
		}else if (type_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Type")) {

			Expenses_text = "Select";

			exp_date.setVisibility(View.GONE);
			exp_cost.setVisibility(View.GONE);
			exp_discr.setVisibility(View.GONE);
			exp_from.setVisibility(View.GONE);
			exp_to.setVisibility(View.GONE);
			exp_mot.setVisibility(View.GONE);
			exp_submit.setVisibility(View.GONE);
		}
	}
	@Override
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		this.finish();
	}

	public void call_service_Expenses_TRAVEL(String user_email,String from_date,String to_date,String date1,
											 String mode,String cost,String dec,String cureentdate1,String cureentdate2)
	{
		System.gc();
		String reason_code = "";
		try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);




			dialog = new ProgressDialog(Expenses.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			dialog.setMessage("Please wait....");
			dialog.setTitle("Smart Anchor App");
			dialog.setCancelable(false);
			dialog.show();

			String domain = "";
			String device_id = "";


			SharedPreferences sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
			device_id = sp.getString("devid", "");

			domain = this.getResources().getString(R.string.service_domain);

			// Global_Val global_Val = new Global_Val();
//		        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//		            domain = context.getResources().getString(R.string.service_domain);
//		        }
//		        else
//		        {
//		            domain = URL.toString();
//		        }
			// StringRequest stringRequest = null;

			JsonObjectRequest jsObjRequest = null;
			try
			{



				Log.d("Server url","Server url"+domain+"expenses_travles/save_travel_expenses");


				JSONArray order = new JSONArray();
				JSONObject product_value = new JSONObject();
				JSONObject product_value_n = new JSONObject();
				JSONArray product_imei = new JSONArray();

				product_value.put("user_email", Global_Data.GLOvel_USER_EMAIL);
				product_value.put("travel_from", from_date);
				product_value.put("travel_to",to_date);
				product_value.put("travel_date", date1);
				product_value.put("travel_mode", mode);
				product_value.put("travel_cost", cost);
				product_value.put("travel_text", dec);
				// product_value.put("imei_no", Global_Data.device_id);

				order.put(product_value);
				product_imei.put(Global_Data.device_id);
				product_value_n.put("expenses_travels", order);
				product_value_n.put("imei_no", Global_Data.device_id);

				Log.d("expenses_travels",product_value_n.toString());
				//Log.d("expenses_travels",product_value_n.toString());

//				 
//				
//				 //product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//				// product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//			      
				jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"expenses_travles/save_travel_expenses", product_value_n, new Response.Listener<JSONObject>() {
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


							if(response_result.equalsIgnoreCase("Travel Expenses created successfully.")) {

								dialog.dismiss();
								//Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

								Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

								Intent a = new Intent(Expenses.this,MainActivity.class);
								startActivity(a);
								finish();
							}
							else
							{

								dialog.dismiss();
								// Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
								Toast toast = Toast.makeText(Expenses.this,response_result, Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								Intent a = new Intent(Expenses.this,MainActivity.class);
								startActivity(a);
								finish();

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
						//Toast.makeText(Expenses.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(Expenses.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						dialog.dismiss();
					}
				});

				RequestQueue requestQueue = Volley.newRequestQueue(Expenses.this);

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

	public void call_service_Expenses_MISC(String user_email,String date1,String cost,String dec)
	{
		System.gc();
		String reason_code = "";
		try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);




			dialog = new ProgressDialog(Expenses.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			dialog.setMessage("Please wait....");
			dialog.setTitle("Smart Anchor App");
			dialog.setCancelable(false);
			dialog.show();

			String domain = "";
			String device_id = "";


			SharedPreferences sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
			device_id = sp.getString("devid", "");

			domain = this.getResources().getString(R.string.service_domain);

			// Global_Val global_Val = new Global_Val();
//		        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//		            domain = context.getResources().getString(R.string.service_domain);
//		        }
//		        else
//		        {
//		            domain = URL.toString();
//		        }
			// StringRequest stringRequest = null;

			JsonObjectRequest jsObjRequest = null;
			try
			{



				Log.d("Server url","Server url"+domain+"expenses_miscs/save_misc_expenses");


				JSONArray order = new JSONArray();
				JSONObject product_value = new JSONObject();
				JSONObject product_imei = new JSONObject();
				JSONObject product_value_n = new JSONObject();

				product_value.put("user_email", Global_Data.GLOvel_USER_EMAIL);
				product_value.put("misc_date", date1);
				product_value.put("misc_amount", cost);
				product_value.put("misc_text", dec);
				// product_value.put("imei_no", Global_Data.device_id);

				order.put(product_value);

				product_value_n.put("expenses_miscs", order);
				product_value_n.put("imei_no", Global_Data.device_id);
				Log.d("expenses_miscs",product_value_n.toString());

//				 
//				
//				 //product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//				// product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//			      
				jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"expenses_miscs/save_misc_expenses", product_value_n, new Response.Listener<JSONObject>() {
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


							if(response_result.equalsIgnoreCase("Misc Expenses created successfully."))
							{

								dialog.dismiss();
								// Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
								Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

								Intent a = new Intent(Expenses.this,MainActivity.class);
								startActivity(a);
								finish();
							}
							else
							{

								dialog.dismiss();
								// Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
								Toast toast = Toast.makeText(Expenses.this,response_result, Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								// Intent a = new Intent(Expenses.this,MainActivity.class);
								//startActivity(a);
								// finish();

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
						//Toast.makeText(Expenses.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(Expenses.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						dialog.dismiss();
					}
				});

				RequestQueue requestQueue = Volley.newRequestQueue(Expenses.this);

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

	InputFilter filter = new InputFilter() {
		@Override
		public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
			for (int i = start; i < end; i++) {
				int type = Character.getType(source.charAt(i));
				//System.out.println("Type : " + type);
				if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
					return "";
				}
			}
			return null;
		}
	};

	private void requestGPSPermissionsigna() {

		Dexter.withActivity(this)
				.withPermissions(
						Manifest.permission.ACCESS_FINE_LOCATION
				)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						// check if all permissions are granted
						if (report.areAllPermissionsGranted()) {
							gps = new GPSTracker(Expenses.this);
							if(!gps.canGetLocation()){

								gps.showSettingsAlertnew();
							}
							else
							{
								Date date1 = new Date();


								if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_date.getText().toString()))
								{
									date2 = new Date(exp_date.getText().toString());
									Calendar cal1 = Calendar.getInstance();
									Calendar cal2 = Calendar.getInstance();
									cal1.setTime(date1);
									cal2.setTime(date1);
								}

								if(Expenses_text == "Travel")
								{
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_date.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Select Date", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Select Date", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
									}
									else
									if(date2.compareTo(date1)>0)
									{
										//System.out.println("Date1 is after Date2");
										// Toast.makeText(getApplicationContext(),"Selected date can not to be a future date.", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Selected date can not to be a future date.", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_cost.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Enter Cost", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter Cost", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_discr.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Enter Description", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter Description", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_from.getText().toString()))
									{
										//Toast.makeText(getApplicationContext(),"Please Enter Form Field Data", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter Form Field Data", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_to.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Enter To Field Data", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter To Field Data", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_mot.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Enter Mode of Travel", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter Mode of Travel", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									{

										Long randomPIN = System.currentTimeMillis();
										String PINString = String.valueOf(randomPIN);

										loginDataBaseAdapter.insertExpenceTravels("1", "1",Global_Data.GLOvel_USER_EMAIL,
												"1", exp_from.getText().toString(), exp_to.getText().toString(), exp_date.getText().toString(), exp_mot.getText().toString(), exp_cost.getText().toString(), exp_discr.getText().toString(),
												"","","", "", Current_Date, Current_Date,PINString);

										Toast toast = Toast.makeText(getApplicationContext(),
												"Travel Expense Save Successfully", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

										Intent a = new Intent(Expenses.this,MainActivity.class);
										startActivity(a);
										finish();


//						 isInternetPresent = cd.isConnectingToInternet();
//
//						if (isInternetPresent)
//	                    {
//							call_service_Expenses_TRAVEL(Global_Data.GLOvel_USER_EMAIL,exp_from.getText().toString(),exp_to.getText().toString(), exp_date.getText().toString(), exp_mot.getText().toString(), exp_cost.getText().toString(), exp_discr.getText().toString(),
//				 					Current_Date, Current_Date);
//	                    }
//		   	        	else
//		   	        	{
//		   	        	   //Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//
//							Toast toast = Toast.makeText(Expenses.this,"You don't have internet connection.", Toast.LENGTH_LONG);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//
//		   	        	}


//						 Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();
//
//						 Intent intent = new Intent(Expenses.this, Order.class);
//						 startActivity(intent);
//						 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//						 finish();
									}
								}
								else
								if(Expenses_text == "Miscellaneous")
								{
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_date.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Select Date", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Select Date", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(date2.compareTo(date1)>0)
									{
										//System.out.println("Date1 is after Date2");
										//Toast.makeText(getApplicationContext(),"Selected date can not to be a future date.", Toast.LENGTH_LONG).show();


										Toast toast = Toast.makeText(Expenses.this,"Selected date can not to be a future date.", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_cost.getText().toString()))
									{
										//Toast.makeText(getApplicationContext(),"Please Enter Cost", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter Cost", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

									}
									else
									if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(exp_discr.getText().toString()))
									{
										// Toast.makeText(getApplicationContext(),"Please Enter Description", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(Expenses.this,"Please Enter Description", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
									}
									else
									{

										Long randomPIN = System.currentTimeMillis();
										String PINString = String.valueOf(randomPIN);

										loginDataBaseAdapter.insertExpencesMiscs("1", "1", Global_Data.GLOvel_USER_EMAIL, "1", exp_date.getText().toString(), exp_cost.getText().toString(), exp_discr.getText().toString(), "", "", "",
												"", Current_Date, Current_Date,PINString);

										Toast toast = Toast.makeText(getApplicationContext(),
												"Miscellaneous Expense Save Successfully", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

										Intent a = new Intent(Expenses.this,MainActivity.class);
										startActivity(a);
										finish();

//						  isInternetPresent = cd.isConnectingToInternet();
//
//							if (isInternetPresent)
//		                    {
//								call_service_Expenses_MISC(Global_Data.GLOvel_USER_EMAIL, exp_date.getText().toString(), exp_cost.getText().toString(), exp_discr.getText().toString());
//		                    }
//			   	        	else
//			   	        	{
//			   	        	 //Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//
//								Toast toast = Toast.makeText(Expenses.this,"You don't have internet connection.", Toast.LENGTH_LONG);
//								toast.setGravity(Gravity.CENTER, 0, 0);
//								toast.show();
//			   	        	}


									}
								}
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
}
