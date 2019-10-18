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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Target extends Activity implements OnItemSelectedListener {
	Spinner type_spinner;
	static final String TAG_MONTH = "month";
	static final String TAG_TARGET = "target";
	static final String TAG_ACHIEVED = "achieved";
	private ArrayList<HashMap<String, String>> dataArrayList;
	private ArrayAdapter<String> listAdapter;
	static int a= 0;
	Target_Adapter target_adapter;
	private Calendar calendar;
	private int year, month, day;
	ListView list_target;
	TextView list_text,textViewm,textView1;
	LoginDataBaseAdapter loginDataBaseAdapter;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	List<String> elementsNew,T_YEARS,T_TEXT,T_ACH;
	
	Boolean TargetFlag = false;
	//private String[] city_state = {"Please Select", "Daily", "Weekly", "Monthly", "Yearly" };
	private String[] city_state = {"Please Select", "Monthly", "Yearly" };
	final List<String> elements = Arrays.asList("      2008                  2000/-                   2000/-", "      2009                  2000/-                   1500/-", "      2010                  2000/-                   1700/-",
			"      2011                  2000/-                   1800/-", "      2012                  2000/-                   1650/-", "      2013                  2000/-                   2000/-", "      2014                  2000/-                   1900/-",
			"      2015                  2000/-                   1300/-", "      2016                  2000/-                   1760/-", "      2017                  2000/-                   2000/-");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.target);
		
	    //ListView listView = getListView();
		
		 loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		 loginDataBaseAdapter=loginDataBaseAdapter.open();
		 
		
		    
		elementsNew = new ArrayList<String>();	
		T_YEARS = new ArrayList<String>();	
		T_TEXT = new ArrayList<String>();
		T_ACH = new ArrayList<String>();
		
		list_target= findViewById(R.id.list_target);
		list_text = findViewById(R.id.list_text);
		textViewm = findViewById(R.id.textViewm);
		textView1 = findViewById(R.id.textView1);
		type_spinner= findViewById(R.id.target);
		list_target.setTextFilterEnabled(true); 
		
		if(!elementsNew.isEmpty())
		{
			elementsNew.clear();
		}

		dataArrayList=new ArrayList<HashMap<String, String>>();
		
		/*if(!T_YEARS.isEmpty())
		{
			T_YEARS.clear();
		}
		
		
		if(!T_TEXT.isEmpty())
		{
			T_TEXT.clear();
		}
		
		if(!T_ACH.isEmpty())
		{
			T_ACH.clear();
		}*/
		
		/* List<Local_Data> contacts = dbvoc.getTarget();      
         for (Local_Data cn : contacts) 
         {			
        	    T_YEARS.add(cn.get_Target_Year());
        	   // T_TEXT.add(cn.get_Target_Text());
        	   // T_ACH.add(cn.get_Target_Achieved());
         	
          }  */
		
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.schedule,elements);
		//list_target.setAdapter(adapter);
		 list_target.setOnItemSelectedListener(this);
		
		  ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, city_state);
			  
		  adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  type_spinner.setAdapter(adapter_state1);
		  type_spinner.setOnItemSelectedListener(this);


			int spinnerPosition = adapter_state1.getPosition(city_state[2]);
			type_spinner.setSelection(spinnerPosition);

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
			mTitleTextView.setText("Target");

			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = Target.this.getSharedPreferences("SimpleLogic", 0);

			ImageView H_LOGO = mCustomView.findViewById(R.id.Header_logo);
			H_LOGO.setImageResource(R.drawable.tar);
			H_LOGO.setVisibility(View.VISIBLE);

			Calendar c = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
			String strDate = sdf.format(c.getTime());

			todaysTarget.setText("Date :- "+strDate);

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}


		List<Local_Data> contacts = dbvoc.checkTargets();
		if(contacts.size() <= 0)
		{

			Toast toast = Toast.makeText(Target.this,"Target Not Found.", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			Intent intent = new Intent(Target.this, MainActivity.class);
			startActivity(intent);
			finish();

		}

	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		//List<Local_Data> contacts = dbvoc.getTarget(); 
		elementsNew.clear();
		dataArrayList.clear();
		TargetFlag = false;
		if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Please Select")) {

//			if(a != 0)
//			{
//				++a;
			//	Toast.makeText(getApplicationContext(),"Please Select Quarter", Toast.LENGTH_LONG).show();

			Toast toast = Toast.makeText(getApplicationContext(),"Please Select Quarter", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

			//}

		}
		else
		if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Yearly"))
		{   
			textViewm.setVisibility(View.GONE);
			list_target.setVisibility(View.VISIBLE);
			list_text.setVisibility(View.GONE);
			textView1.setText("Year");
			List<Local_Data> contacts = dbvoc.getTargetByYear();      
            for (Local_Data cn : contacts) 
            {
            	TargetFlag = true;
            	elementsNew.add("           "+cn.get_Target_Year()+"                 "+cn.get_Target_Text()+"/-"+"                 "+cn.get_Target_Achieved()+"/-");

				HashMap<String, String> map = new HashMap<String, String>();

				map.put(TAG_MONTH, cn.get_Target_Year());

				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_Target_Text()))
				{
					map.put(TAG_TARGET, cn.get_Target_Text()+".0/-");
				}
				else
				{
					map.put(TAG_TARGET, "0.0"+"/-");
				}

				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_Target_Achieved()))
				{
					map.put(TAG_ACHIEVED, cn.get_Target_Achieved()+".0/-");
				}
				else
				{
					map.put(TAG_ACHIEVED, "0.0"+"/-");
				}

				dataArrayList.add(map);

            }  
		        //elementsNew = A("      2008                  2000/-                   2000/-");
			//ArrayAdapter<String> adapternew = new ArrayAdapter<String>(this, R.layout.schedule,elementsNew);
			target_adapter = new Target_Adapter(Target.this, dataArrayList);
			list_target.setAdapter(target_adapter);
			//list_target.setAdapter(target_adapter);
			list_target.deferNotifyDataSetChanged();

			if(target_adapter.isEmpty())
			{
				list_target.setVisibility(View.VISIBLE);
				list_text.setVisibility(View.GONE);
			} 
		}     
		else
		if(type_spinner.getSelectedItem().toString().equalsIgnoreCase("Monthly"))
		{
			list_target.setVisibility(View.VISIBLE);
			textViewm.setVisibility(View.GONE);
			list_text.setVisibility(View.GONE);
			textView1.setText("Month");
			calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);

			List<Local_Data> contacts = dbvoc.getTarget();      
            for (Local_Data cn : contacts) 
            {
            	TargetFlag = true;
            	elementsNew.add("    "+cn.get_Target_Year()+"  "+cn.get_Target_Month()+"         "+cn.get_Target_Text()+"/-"+"           "+cn.get_Target_Achieved()+"/-");
				HashMap<String, String> map = new HashMap<String, String>();

				if(cn.get_Target_Year().equalsIgnoreCase(String.valueOf(year))) {


					map.put(TAG_MONTH, cn.get_Target_Month());

					if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_Target_Text())) {
						map.put(TAG_TARGET, cn.get_Target_Text()+"/-");
					} else {
						map.put(TAG_TARGET, "0.0"+"/-");
					}

					if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_Target_Achieved())) {
						map.put(TAG_ACHIEVED, cn.get_Target_Achieved()+"/-");
					} else {
						map.put(TAG_ACHIEVED, "0.0"+"/-");
					}

					dataArrayList.add(map);
				}

			}
			//elementsNew = A("      2008                  2000/-                   2000/-");
			//ArrayAdapter<String> adapternew = new ArrayAdapter<String>(this, R.layout.schedule,elementsNew);
			target_adapter = new Target_Adapter(Target.this, dataArrayList);
			list_target.setAdapter(target_adapter);
			//list_target.setAdapter(target_adapter);
			list_target.deferNotifyDataSetChanged();

			if(target_adapter.isEmpty())
			{
				list_target.setVisibility(View.VISIBLE);
				list_text.setVisibility(View.GONE);
			}

		}
		else
		{
			list_target.setVisibility(View.GONE);
			list_text.setVisibility(View.VISIBLE);
			textViewm.setVisibility(View.GONE);
			list_text.setText("No record found");
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
		     elementsNew.clear();
		Intent i=new Intent(Target.this, MainActivity.class);
		startActivity(i);
  		    //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
	
}
