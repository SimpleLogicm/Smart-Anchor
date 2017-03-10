package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Schedule2 extends Activity {
	Button but_schdl2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule2);
		
		but_schdl2=(Button)findViewById(R.id.but_schdl2);
		
		but_schdl2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					onBackPressed();
		    	}
			});
		 
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
        mTitleTextView.setText("Delivery Schedule");
        
        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Schedule2.this.getSharedPreferences("SimpleLogic", 0);
        
        ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
        H_LOGO.setImageResource(R.drawable.timelist);
        H_LOGO.setVisibility(View.VISIBLE);
        
       
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
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
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
    		    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
}
