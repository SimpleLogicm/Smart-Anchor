package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.anchor.webservice.ConnectionDetector;
//how to give voice for icon when swipe in android
public class Sales_Dash extends Activity implements OnItemSelectedListener{ 
	//Button retail_sales, institute_sales;
	ImageView retail_sales, institute_sales,customer_services,quote_status,schedule_listn,C_profile,outstandingBtn,schemeBtn,otherInfoBtn,sub_delaer_order,pro_activity_click;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	TextView schedule_txt,textView1sf;
	TextView txtWelcomeUser;

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sales_dash);
		Global_Data.CUSTOMER_SERVICE_FLAG = "";

		retail_sales = (ImageView) findViewById(R.id.retail_sales);
		institute_sales = (ImageView) findViewById(R.id.institute_sales);
		customer_services = (ImageView) findViewById(R.id.customer_services);
		//quote_status = (ImageView) findViewById(R.id.quote_status);
		schedule_listn = (ImageView) findViewById(R.id.schedule_listn);
		C_profile = (ImageView) findViewById(R.id.C_profile);
		schedule_txt = (TextView) findViewById(R.id.schedule_txt);
		textView1sf = (TextView) findViewById(R.id.textView1sf);
		outstandingBtn = (ImageView) findViewById(R.id.outstanding_btn);
		schemeBtn = (ImageView) findViewById(R.id.scheme_btn);
		otherInfoBtn = (ImageView) findViewById(R.id.otherinfo_btn);
		sub_delaer_order = (ImageView) findViewById(R.id.sub_delaer_order);
		pro_activity_click = (ImageView) findViewById(R.id.pro_activity_click);

		txtWelcomeUser=(TextView) findViewById(R.id.txtWelcomeUser);
		cd = new ConnectionDetector(getApplicationContext());
		String user_name = "";
		if(!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null"))
		{
			user_name = Global_Data.USER_FIRST_NAME.trim();
			if(!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null"))
			{
				user_name +=  " " + Global_Data.USER_LAST_NAME.trim();
			}
		}

		txtWelcomeUser.setText(user_name+" : "+Global_Data.emp_code);

		// for label change
		SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
		String schedulestr=spf1.getString("var_schedule", "");

		if(schedulestr.length()>0)
		{
			schedule_txt.setText(schedulestr);
		}else{
			schedule_txt.setText("SCHEDULE");
		}

		retail_sales.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Global_Data.CUSTOMER_SERVICE_FLAG = "" ;
				Global_Data.sales_btnstring="Secondary Sales / Retail Sales";
				Intent intent = new Intent(getApplicationContext(), Order.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	    	}
		});

		sub_delaer_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});

		pro_activity_click.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(), Promotion_Activity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});

		C_profile.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), Customer_info_main.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			}
		});
	
    institute_sales.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Global_Data.sales_btnstring="Institutional Sales";
			Global_Data.CUSTOMER_SERVICE_FLAG = "" ;
			Intent intent = new Intent(getApplicationContext(), Order.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    	}
	});

		customer_services.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Global_Data.sales_btnstring="Secondary Sales / Retail Sales";
			Global_Data.CUSTOMER_SERVICE_FLAG = "CUSTOMER_SERVICE" ;
			Intent intent = new Intent(getApplicationContext(), Order.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		}
	});

//		quote_status.setOnClickListener(new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//
//			isInternetPresent = cd.isConnectingToInternet();
//			if (isInternetPresent)
//			{
//				Intent a = new Intent(Sales_Dash.this,Order.class);
//				startActivity(a);
//				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//				//finish();
//			}
//			else
//			{
//				//Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//
//				Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
//			}
//		}
//	});

		schedule_listn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Global_Data.sales_btnstring="Secondary Sales / Retail Sales";
			Global_Data.CUSTOMER_SERVICE_FLAG = "SCHEDULE" ;
			Intent intent = new Intent(getApplicationContext(), Order.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		}
	});

		outstandingBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				Global_Data.CUSTOMER_SERVICE_FLAG="Outstanding/Overdue";
				if (isInternetPresent)
				{
					//Global_Data.CUSTOMER_SERVICE_FLAG = "CUSTOMER_SERVICE" ;
					Intent intent = new Intent(getApplicationContext(), Order.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//					Intent a = new Intent(Sales_Dash.this,OutstandingActivity.class);
//					startActivity(a);
//					finish();
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});

		schemeBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				Global_Data.CUSTOMER_SERVICE_FLAG="Scheme";
				if (isInternetPresent)
				{

					Intent intent = new Intent(getApplicationContext(), Order.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

//					Intent a = new Intent(Sales_Dash.this,Scheme_Filter_Activity.class);
//					startActivity(a);
//					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					//finish();
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});

		otherInfoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				isInternetPresent = cd.isConnectingToInternet();
				Global_Data.CUSTOMER_SERVICE_FLAG="Other Info";
				if (isInternetPresent)
				{
					Intent a = new Intent(Sales_Dash.this,OtherInfoActivity.class);
					startActivity(a);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					//finish();
				}
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			}
		});

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
			TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText("Order Type");

			TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = Sales_Dash.this.getSharedPreferences("SimpleLogic", 0);

//	       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	       	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			}

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
//	       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
				todaysTarget.setText("Today's Target Acheived");
			}

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}

		}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
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
		
	 Intent i = new Intent(Sales_Dash.this,MainActivity.class);
	 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
     startActivity(i);
	 finish();
	}

}
