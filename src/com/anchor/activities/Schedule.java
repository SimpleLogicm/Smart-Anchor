package com.anchor.activities;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Schedule extends ListActivity{
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	private String RE_TEXT = "";
	final List<String> elements = Arrays.asList("12/05/2015      Delivery Location 1", "22/05/2015      Delivery Location 2", "25/05/2015      Delivery Location 3",
			   "29/05/2015      Delivery Location 4", "02/06/2015      Delivery Location 5", "07/06/2015      Delivery Location 6", "11/06/2015      Delivery Location 7",
			   "14/06/2015      Delivery Location 8", "19/06/2015      Delivery Location 9", "26/06/2015      Delivery Location 10");
	
	List<String> elementsnew;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 Intent i = getIntent();
		 RE_TEXT = i.getStringExtra("RE_TEXT");
			
			
		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
		
		  
		  elementsnew = new ArrayList<String>();
		  
		  List<Local_Data> contacts = dbvoc.getDeliveryScheduleJoin();      
          for (Local_Data cn : contacts) 
          {
        	     
        	  elementsnew.add(cn.get_shedule_depatch_date()+"                     " + cn.get_shedule_order_id());
         }
          dbvoc.close();
          
		  ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.schedule,elementsnew);
		  listView.setAdapter(adapter);
		  
		  ActionBar mActionBar = getActionBar();
			mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
	       // mActionBar.setDisplayShowHomeEnabled(false);
	       // mActionBar.setDisplayShowTitleEnabled(false);
	        LayoutInflater mInflater = LayoutInflater.from(this);
	       
	        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
	        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
	        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
	        mTitleTextView.setText("Delivery Schedule");
	        
	        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
	        
	        ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
	        H_LOGO.setImageResource(R.drawable.timelist);
	        H_LOGO.setVisibility(View.VISIBLE);
	        
	        SharedPreferences sp = Schedule.this.getSharedPreferences("SimpleLogic", 0);
	       
//	        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
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
//	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
	        	todaysTarget.setText("Today's Target Acheived");
			}
	        
	        mActionBar.setCustomView(mCustomView);
	        mActionBar.setDisplayShowCustomEnabled(true);
	        mActionBar.setHomeButtonEnabled(true);
	        mActionBar.setDisplayHomeAsUpEnabled(true);
		  
		  listView.setOnItemClickListener(new OnItemClickListener() {
		   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		    //Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			   Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
				          Toast.LENGTH_SHORT).show(); 
			   Intent intent = new Intent(Schedule.this, Schedule1.class);
			   intent.putExtra("order_id", ((TextView) view).getText());
			   intent.putExtra("RE_TEXT", RE_TEXT);
  			   startActivity(intent);
  			   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		   }
		  });
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
