package com.anchor.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.slidingmenu.CalendarAct;
import com.anchor.slidingmenu.CalendarAct.GridCellAdapter;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.toolbox.JsonObjectRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.GPSTracker;

public class Calendar_Event extends BaseActivity{
	EditText details;
	GPSTracker gps;
	Date date1;
	Date date2;
	private String Current_Date = "";
	Button submit,submit_details_save,submit_details_delete;
	ProgressDialog dialog;
	LoginDataBaseAdapter loginDataBaseAdapter;
	TextView from,to;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	private DatePickerDialog fromDatePickerDialog,fromDatePickerDialog1;
	private SimpleDateFormat dateFormatter;
	private static final String tag = "Calendar_Event";
	String popUpContents[];
	Boolean isInternetPresent = false;
	ConnectionDetector cd;
	PopupWindow popupWindowDogs;
	Button buttonShowDropDown;
	private TextView currentMonth;
	private Button selectedDayMonthYearButton;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	TextView textView3;

	String c_user_id = "";
	String c_id = "";
	String c_latlong = "";

	String update_flag = "";


	@SuppressLint("NewApi")
	private int month, year;
	@SuppressWarnings("unused")
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	//private final DateFormat dateFormatter = new DateFormat();
	//private static final String dateTemplate = "MMMM yyyy";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		setContentView(R.layout.calendar_event);
		from=(TextView)findViewById(R.id.from_details);
		to=(TextView)findViewById(R.id.to_details);
		textView3=(TextView)findViewById(R.id.textView3);
		details=(EditText)findViewById(R.id.details);
		submit=(Button)findViewById(R.id.submit_details);
		//submit_details_save = (Button)findViewById(R.id.submit_details_save);
		submit_details_delete = (Button)findViewById(R.id.submit_details_delete);

		details.setFilters(new InputFilter[]{filter});

		cd = new ConnectionDetector(Calendar_Event.this);
		dateFormatter = new SimpleDateFormat("MMMM-yyyy", Locale.US);

		if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Travel Planner"))
		{
			textView3.setText("Travel Details");
		}
		else
		if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Leave Management"))
		{
			textView3.setText("Leave Details");
		}
		else
		if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Task"))
		{
			textView3.setText("Task Details");
		}


		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String strDate = sdf.format(c.getTime());
		Current_Date = sdf.format(c.getTime());

		Calendar newCalendar = Calendar.getInstance();

		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		//int monthold = calendar.get(Calendar.MONTH)-1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		Calendar calendarnew = Calendar.getInstance();
		calendarnew.add(Calendar.MONTH ,-1);
		int monthn = calendarnew.get(Calendar.MONTH);

		Formatter fmtt = new Formatter();
		// fmt.format("%tB %tb %tm", calendar, calendar, calendar);

		String mmm = fmtt.format("%tB", calendarnew).toString();

		//Calendar cal = Calendar.getInstance();
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
		Formatter fmt = new Formatter();
		// fmt.format("%tB %tb %tm", calendar, calendar, calendar);

		String mm = fmt.format("%tB", calendar).toString();

		Log.d("C Month","C Month"+mm);

		Log.d("C Month old","C Month old"+mmm);
		Log.d("C MAXDAY","C MAXDAY"+days);
		Log.d("C year","C year"+year);
		Log.d("C year","C year"+year);

		String e_name = "";
		if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Travel Planner"))
		{
			e_name = "Travel";
		}
		else
		if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Leave Management"))
		{
			e_name = "Leave";
		}
		else
		if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Task"))
		{
			e_name = "Task";
		}

		List<Local_Data> contacts2 = dbvoc.getCalender_EventValue(Global_Data.select_date,e_name,"NO");

		if(contacts2.size() <= 0 )
		{
			from.setText(Global_Data.select_date);
			update_flag = "";
			submit_details_delete.setVisibility(View.INVISIBLE);
		}
		else
		{
			for (Local_Data cn : contacts2) {

				from.setText(Global_Data.select_date);
				to.setText(cn.getto_date());
				details.setText(cn.getcalender_details());

				c_user_id = cn.getuser_email();
				c_id = cn.getcalender_id();
				//	c_user_id = cn.getuser_email();


			}
			submit.setText("Update");
			submit_details_delete.setVisibility(View.VISIBLE);
			update_flag = "TRUE";
		}

		submit_details_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {



				AlertDialog alertDialog = new AlertDialog.Builder(Calendar_Event.this).create(); //Read Update
				alertDialog.setTitle("Warning");
				alertDialog.setMessage("Are you sure you want to delete this event ?");
				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {


						dbvoc.updateCalendervalue("YES",c_id);
						dbvoc.updateORDER_SIGNATUREnew(Current_Date,c_id);

						Toast toast = Toast.makeText(Calendar_Event.this,"Event delete successfully.",
								Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						Intent intentn = new Intent(getApplicationContext(), CalendarAct.class);
						startActivity(intentn);
						finish();
						//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

					}
				});

				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						dialog.cancel();

					}
				});
				alertDialog.show();

			}

		});


		fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				String yr_reg=Integer.toString(year);
				String mnth_reg=Integer.toString(monthOfYear+1);
				String date_reg=Integer.toString(dayOfMonth);

				from.setText(date_reg+"-"+(dateFormatter.format(newDate.getTime())));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

		fromDatePickerDialog1 = new DatePickerDialog(this, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				String yr_reg=Integer.toString(year);
				String mnth_reg=Integer.toString(monthOfYear+1);
				String date_reg=Integer.toString(dayOfMonth);

				to.setText(date_reg+"-"+(dateFormatter.format(newDate.getTime())));
			}

		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


		from.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fromDatePickerDialog.getWindow().getAttributes().verticalMargin = 0.5F;
				fromDatePickerDialog.show();
			}
		});

		to.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				fromDatePickerDialog1.getWindow().getAttributes().verticalMargin = 0.5F;
				fromDatePickerDialog1.show();
			}
		});


		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				requestGPSPermissionsigna();

			}
		});

//		submit_details_save.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(from.getText().toString()))
//				{
//					//Toast.makeText(getApplicationContext(),"Please Select From Date", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(getApplicationContext(),"Please Select From Date", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//				else
//				if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(to.getText().toString()))
//				{
//					//Toast.makeText(getApplicationContext(),"Please Select To Date", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(getApplicationContext(),"Please Select To Date", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//				else
//				if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(details.getText().toString()))
//				{
//					//Toast.makeText(getApplicationContext(),"Please Enter Travel Details", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(getApplicationContext(),"Please Enter Travel Details", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//				else
//				{
//
//
//					if(update_flag.equalsIgnoreCase("TRUE"))
//					{
//						dbvoc.getDeleteTablecalender_event(c_id);
//						loginDataBaseAdapter.insertCalenderEntries("", "", c_user_id,c_id,Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"");
//
//						//Toast.makeText(getApplicationContext(),"Update Successfully.",Toast.LENGTH_LONG).show();
//						Toast toast = Toast.makeText(getApplicationContext(),"Update Successfully.", Toast.LENGTH_LONG);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
//					else
//					{
//						SecureRandom random = new SecureRandom();
//						loginDataBaseAdapter.insertCalenderEntries("", "", Global_Data.GLOvel_USER_EMAIL, new BigInteger(130,random).toString(32),Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"");
//
//						//Toast.makeText(getApplicationContext(),"Save Successfully.",Toast.LENGTH_LONG).show();
//
//						Toast toast = Toast.makeText(getApplicationContext(),"Save Successfully.", Toast.LENGTH_LONG);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
//
//
//					Intent a = new Intent(Calendar_Event.this,MainActivity.class);
//					startActivity(a);
//					finish();
//
//
//
////					isInternetPresent = cd.isConnectingToInternet();
////
////					if (isInternetPresent)
////					{
////						call_service_Calender_Event();
////					}
////					else
////					{
////						Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
////					}
//
//
////						 Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();
////
////						 Intent intent = new Intent(Expenses.this, Order.class);
////						 startActivity(intent);
////						 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
////						 finish();
//				}
//
//
//
//			}
//		});

		ActionBar mActionBar = getActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
		// mActionBar.setDisplayShowHomeEnabled(false);
		// mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);
		Intent i = getIntent();
		String name = i.getStringExtra("retialer");
		View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
		TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
		mTitleTextView.setText(Global_Data.calspinner);

		TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);

		ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
		H_LOGO.setImageResource(R.drawable.cal);
		H_LOGO.setVisibility(View.VISIBLE);

		SharedPreferences sp = Calendar_Event.this.getSharedPreferences("SimpleLogic", 0);

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

	public void call_service_Calender_Event()
	{
		System.gc();
		String reason_code = "";
		try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);




//		    dialog = new ProgressDialog(Calendar_Event.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
//	        dialog.setMessage("Please wait....");
//	        dialog.setTitle("Metal");
//	        dialog.setCancelable(false);
//	        dialog.show();

			String domain = "";
			String device_id = "";


			SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
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



				// SecureRandom random = new SecureRandom();

				String code = "";

				if(update_flag.equalsIgnoreCase("TRUE"))
				{
					dbvoc.getDeleteTablecalender_event(c_id);

					String event_nname = "";

					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Travel Planner"))
					{
						event_nname = "Travel";
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Leave Management"))
					{
						event_nname = "Leave";
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Task"))
					{
						event_nname = "Task";
					}

					try
					{
						AppLocationManager appLocationManager = new AppLocationManager(Calendar_Event.this);
						Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
						Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

						PlayService_Location PlayServiceManager = new PlayService_Location(Calendar_Event.this);

						if(PlayServiceManager.checkPlayServices(Calendar_Event.this))
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

					loginDataBaseAdapter.insertCalenderEntries("", "", c_user_id,c_id,event_nname, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"NO");

					code =  c_id;

					String gaddress = "";

					try {
						if (Global_Data.address.equalsIgnoreCase("null")) {
							gaddress = "";
						} else {
							gaddress = Global_Data.address;
						}
					}catch(Exception ex){ex.printStackTrace();}

					String sms_body = "";
					Log.d("CALENDER_EVENT_TYPE","CALENDER_EVENT_TYPE"+Global_Data.CALENDER_EVENT_TYPE);
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Travel Planner"))
					{
						sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" I have planned a Travel to  " + from.getText().toString().trim() + " from to " + to.getText().toString().trim() + " for " + details.getText().toString().trim() +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Leave Management"))
					{
						sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" I have planned a Leave to  " + from.getText().toString().trim() + " from to " + to.getText().toString().trim() + " for " + details.getText().toString().trim() +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Task"))
					{
						sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" I have planned a Task to  " + from.getText().toString().trim() + " from to " + to.getText().toString().trim() + " for " + details.getText().toString().trim() +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
					}


					if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
					{
						// Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, Calendar_Event.this);
						// mobile_numbers.add(Global_Data.cus_MAnager_mobile);
					}



					Toast.makeText(getApplicationContext(),"Calender Entry Update Successfully.",Toast.LENGTH_LONG).show();
					Intent a = new Intent(Calendar_Event.this,CalendarAct.class);
					startActivity(a);
					finish();
				}
				else
				{
					String gaddress = "";

					try {
						if (Global_Data.address.equalsIgnoreCase("null")) {
							gaddress = "";
						} else {
							gaddress = Global_Data.address;
						}
					}catch(Exception ex){ex.printStackTrace();}

					String sms_body = "";
					Log.d("CALENDER_EVENT_TYPE","CALENDER_EVENT_TYPE"+Global_Data.CALENDER_EVENT_TYPE);
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Travel Planner"))
					{
						sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" I have planned a Travel to  " + from.getText().toString().trim() + " from to " + to.getText().toString().trim() + " for " + details.getText().toString().trim() +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Leave Management"))
					{
						sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" I have planned a Leave to  " + from.getText().toString().trim() + " from to " + to.getText().toString().trim() + " for " + details.getText().toString().trim() +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Task"))
					{
						sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" I have planned a Task to  " + from.getText().toString().trim() + " from to " + to.getText().toString().trim() + " for " + details.getText().toString().trim() +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
					}


					if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
					{
						// Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, Calendar_Event.this);
						// mobile_numbers.add(Global_Data.cus_MAnager_mobile);
					}

					String event_nname = "";

					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Travel Planner"))
					{
						event_nname = "Travel";
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Leave Management"))
					{
						event_nname = "Leave";
					}
					else
					if(Global_Data.CALENDER_EVENT_TYPE.equalsIgnoreCase("Task"))
					{
						event_nname = "Task";
					}




					try
					{
						AppLocationManager appLocationManager = new AppLocationManager(Calendar_Event.this);
						Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
						Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

						PlayService_Location PlayServiceManager = new PlayService_Location(Calendar_Event.this);

						if(PlayServiceManager.checkPlayServices(Calendar_Event.this))
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


					SecureRandom random = new SecureRandom();
					loginDataBaseAdapter.insertCalenderEntries("", "", Global_Data.GLOvel_USER_EMAIL, new BigInteger(130,random).toString(32),event_nname, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"NO");

					code =  new BigInteger(130,random).toString(32);

					Toast.makeText(getApplicationContext(),"Calender Entry Created Successfully.",Toast.LENGTH_LONG).show();

					Intent a = new Intent(Calendar_Event.this,CalendarAct.class);
					startActivity(a);
					finish();
				}

				Log.d("Server url","Server url"+domain+"calendars/create_calender_entry");


//				 JSONArray order = new JSONArray();
//				 JSONObject product_value = new JSONObject();
//				 JSONObject product_value_n = new JSONObject();
//				 JSONArray product_imei = new JSONArray();
//
//				 product_value.put("code", code);
//				 product_value.put("user_email", Global_Data.GLOvel_USER_EMAIL);
//				 product_value.put("entry_type", Global_Data.CALENDER_EVENT_TYPE);
//				 product_value.put("from_date",from.getText().toString().trim());
//				 product_value.put("to_date", to.getText().toString().trim());
//				 product_value.put("details", details.getText().toString().trim());
//				 product_value.put("latitude", Global_Data.GLOvel_LATITUDE);
//				 product_value.put("longitude", Global_Data.GLOvel_LONGITUDE);
//				// product_value.put("latlon", Global_Data.lat_val+","+Global_Data.long_val);
//
//				// product_value.put("imei_no", Global_Data.device_id);
//
//				 order.put(product_value);
//				// product_imei.put(Global_Data.device_id);
//				 product_value_n.put("calender_entries", order);
//				 product_value_n.put("imei_no", Global_Data.device_id);
//
//				 Log.d("calender_entries",product_value_n.toString());
//				 //Log.d("expenses_travels",product_value_n.toString());
//
////
////
////				 //product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
////				// product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
////
//				  jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"calendars/create_calender_entry", product_value_n, new Response.Listener<JSONObject>() {
//		                @Override
//		                public void onResponse(JSONObject response) {
//		                    Log.i("volley", "response: " + response);
//
//		                    Log.d("jV", "JV length" + response.length());
//			                  //JSONObject json = new JSONObject(new JSONTokener(response));
//							  try{
//
//								  String response_result = "";
//							      if(response.has("message"))
//							      {
//							          response_result = response.getString("message");
//							      }
//							      else
//							      {
//							          response_result = "data";
//							      }
//
//
//							      if(response_result.equalsIgnoreCase("Calender Entry created successfully.")) {
//
//							    	  dialog.dismiss();
//								   	  //Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
//									  Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
//									  toast.setGravity(Gravity.CENTER, 0, 0);
//									  toast.show();
//							          Intent a = new Intent(Calendar_Event.this,CalendarAct.class);
//								   	  startActivity(a);
//								   	  finish();
//							      }
//							      else
//								   {
//
//							    	  dialog.dismiss();
//									  // dbvoc.getDeleteTablecalender_event(c_id);
//									  // Global_Data.SYNC_SERVICE_FLAG  = "TRUE";
//							         // Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
//							    	  Toast toast = Toast.makeText(Calendar_Event.this,response_result, Toast.LENGTH_SHORT);
//									  toast.setGravity(Gravity.CENTER, 0, 0);
//									  toast.show();
//							          Intent a = new Intent(Calendar_Event.this,CalendarAct.class);
//								   	  startActivity(a);
//								   	  finish();
//
//							      }
//
//							    //  finish();
//							      // }
//
//							      // output.setText(data);
//							  }catch(JSONException e){e.printStackTrace();
//								  Intent a = new Intent(Calendar_Event.this,CalendarAct.class);
//								  startActivity(a);
//								  finish();
//
//								  dialog.dismiss(); }
//
//
//							  dialog.dismiss();
//			                  dialog.dismiss();
//
//
//
//
//		                }
//		            }, new Response.ErrorListener() {
//		                @Override
//		                public void onErrorResponse(VolleyError error) {
//
//							Intent a = new Intent(Calendar_Event.this,CalendarAct.class);
//							startActivity(a);
//							finish();
//		                    Log.i("volley", "error: " + error);
//
//							Toast toast = Toast.makeText(getApplicationContext(), "Some server error occur or Your net not working", Toast.LENGTH_LONG);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//		                    dialog.dismiss();
//		                }
//		            });
//
//				  RequestQueue requestQueue = Volley.newRequestQueue(Calendar_Event.this);
//
//			        int socketTimeout = 200000;//30 seconds - change to what you want
//			        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//			        jsObjRequest.setRetryPolicy(policy);
//			        // requestQueue.se
//			        //requestQueue.add(jsObjRequest);
//			        jsObjRequest.setShouldCache(false);
//			        requestQueue.getCache().clear();
//			        requestQueue.add(jsObjRequest);

			}catch(Exception e)
			{
				e.printStackTrace();
				//dialog.dismiss();
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		this.finish();
	}

	public static String getlength(int len){
		int number = len;
		int length = (int) Math.log10(number) + 1;
		if(length == 1)
		{
			return "0"+len;
		}
		else
		{
			return ""+len;
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
							gps = new GPSTracker(Calendar_Event.this);
							if(!gps.canGetLocation()){

								gps.showSettingsAlertnew();
							}
							else
							{
								if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(from.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(to.getText().toString()))
								{
									date1 = new Date(from.getText().toString());
									date2 = new Date(to.getText().toString());
									Calendar cal1 = Calendar.getInstance();
									Calendar cal2 = Calendar.getInstance();
									cal1.setTime(date1);
									cal2.setTime(date1);
								}


								if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(from.getText().toString()))
								{

									Toast toast = Toast.makeText(getApplicationContext(), "Please Select From Date", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
								else
								if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(to.getText().toString()))
								{
									//Toast.makeText(getApplicationContext(),"Please Select To Date", Toast.LENGTH_LONG).show();
									Toast toast = Toast.makeText(getApplicationContext(), "Please Select To Date", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
								else
								if(date1.compareTo(date2)>0)
								{
									//System.out.println("Date1 is after Date2");
									//Toast.makeText(getApplicationContext(),"To Date not a valid date.", Toast.LENGTH_LONG).show();
									Toast toast = Toast.makeText(getApplicationContext(), "To Date not a valid date.", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
								else
								if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(details.getText().toString()))
								{

									Toast toast = Toast.makeText(getApplicationContext(),"Please Enter Travel Details", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}

								else
								{

//						 SecureRandom random = new SecureRandom();
//						loginDataBaseAdapter.insertCalenderEntries("", "", Global_Data.GLOvel_USER_EMAIL, new BigInteger(130,random).toString(32),Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date);

//						 isInternetPresent = cd.isConnectingToInternet();
//
//						if (isInternetPresent)
//	                    {
									call_service_Calender_Event();
//	                    }
//		   	        	else
//		   	        	{
//		   	        	 //Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//							Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.", Toast.LENGTH_LONG);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//		   	        	}


//						 Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();
//
//						 Intent intent = new Intent(Expenses.this, Order.class);
//						 startActivity(intent);
//						 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//						 finish();
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
