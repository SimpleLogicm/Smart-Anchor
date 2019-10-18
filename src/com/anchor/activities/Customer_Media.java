package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class Customer_Media extends Activity implements OnItemSelectedListener{
	Spinner feed_spinner,category_spinner,product_spinner,variant_spinner;
	Button claims_submit;
	private String[] feed_state = { "Competitors Stock" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_claims);
		
		claims_submit= findViewById(R.id.claims_submit);
		//feed_spinner=(Spinner)findViewById(R.id.comp_stock);
		
		  ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, feed_state);
		  
		  
		  adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  feed_spinner.setAdapter(adapter_state1);
		  feed_spinner.setOnItemSelectedListener(this);
		  
		 /* adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  category_spinner.setAdapter(adapter_state2);
		  category_spinner.setOnItemSelectedListener(this);*/
		  
		
		
		
		claims_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Customer_Media.this, Customer_Media.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	    	}
		});
		
		//feed_spinner = (Spinner) findViewById(R.id.feed_spinner);
//		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, feed_state);
//		  adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  feed_spinner.setAdapter(adapter_state);
//		  feed_spinner.setOnItemSelectedListener(this);
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
			//  mTitleTextView.setText(Global_Data.retailer);

	        /*TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
	        SharedPreferences sp = Customer_Media.this.getSharedPreferences("SimpleLogic", 0);

	        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
	        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
			}
	        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
	        	todaysTarget.setText("Today's Target Acheived");
			}*/

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
			    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
	
}