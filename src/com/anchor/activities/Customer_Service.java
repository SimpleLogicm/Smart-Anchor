package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Customer_Service extends Activity implements OnItemSelectedListener{
	 Spinner city_spinner,beat_spinner,retail_spinner;
	 TextView selVersion;
//	 HttpGet httppst;
//		HttpClient httpclint;
//		List<NameValuePair> nameValuePars;
//		HttpPost httppost;
//		HttpResponse response;
		LoginDataBaseAdapter adapter_ob;
		DataBaseHelper helper_ob;
		SQLiteDatabase db_ob;
		Cursor cursor;
		String[] from;
		ListView lv; 
		SimpleCursorAdapter cursorAdapter;
		DataBaseHelper dbvoc = new DataBaseHelper(this);
	 Button customer_submit;
	 private ArrayList<String> results = new ArrayList<String>();
	 private ArrayList<String> results1 = new ArrayList<String>();
	 private ArrayList<String> results2 = new ArrayList<String>();
	 private String[] city_state = { "Select City", "Mumbai" };
	 private String[] beat_state = { "Select Beat", "Andheri West","Andheri East","Vileparle East","Khar Road"};
	 private String[] retail_state = { "Select Retailer", "Life Care Medical", "Laxmi Collection","Amar Medical" };
	 private String[] list_categ = { "Select Category", "AP Deodorants", "AP DEO Stick 15","Hand Sanitizer 30","Junior Hand Sanitizer 30" };
	 private String[] list_prod = { "Select Product", "TITAN AFD", "REBEL AFD","ADORE AFD","OASIS AFD","VIVA APD"};
	 private String[] list_varnt = { "Select Variant", "50 GM","150 ML","250 ML"};
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_serve);
		  lv= findViewById(R.id.listView1);
		  customer_submit = findViewById(R.id.customer_submit);
		  city_spinner = findViewById(R.id.cust_city);
		  beat_spinner = findViewById(R.id.cust_beat);
		  retail_spinner = findViewById(R.id.cust_retailer);
		 
//		    String[] from = { helper_ob.FNAME};
//	        int[] to = { R.id.tv_fname, R.id.tv_lname };
//	        cursor = adapter_ob.queryName();
//	        cursorAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor, from, to);
		  
		    customer_submit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if (city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
						
						Toast toast = Toast.makeText(Customer_Service.this,"Please Select City", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
					else if (beat_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {
							Toast toast = Toast.makeText(Customer_Service.this,"Please Select Beat", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					
					else if (retail_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Retailer")) {
						
						Toast toast = Toast.makeText(Customer_Service.this,"Please Select Retailer", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				
				  else {
					  
//					  new Thread(new Runnable() {
//						   public void run() {
//						    	 Flwg();
//						      }
//						    }).start();
					  
					    Global_Data.retailer= retail_spinner.getSelectedItem().toString();
						Intent intent = new Intent(Customer_Service.this, Customer_Feed.class);
						startActivity(intent);
//						Intent intent = new Intent(Customer_Service.this, Customer_Feed.class);
//						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					   }
    	    	}
			});
		
//		  //Reading all 
//	         List<Local_Data> contacts = dbvoc.getAllList();
//	         results.add("Select City");
//	          for (Local_Data cn : contacts) {
//	        	String str_state = ""+cn.getStateName();
//	        	//Global_Data.local_pwd = ""+cn.getPwd();
//	        	
//	        	results.add(str_state);
//	        	//System.out.println("Local Values:-"+Global_Data.local_user);
//	        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
	//        	                             }
	          
	        //Reading all 
		         List<Local_Data> contacts1 = dbvoc.getAllCity();
		         results1.add("Select City");
		          for (Local_Data cn : contacts1) {
		        	String str_city = ""+cn.getStateName();
		        	//Global_Data.local_pwd = ""+cn.getPwd();
		        	
		        	results1.add(str_city);
		        	//System.out.println("Local Values:-"+Global_Data.local_user);
		        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
		        	                             }
		          
		        //Reading all 
			         List<Local_Data> contacts2 = dbvoc.getAllBeats();
			         results.add("Select Beat");
			          for (Local_Data cn : contacts2) {
			        	String str_beat = ""+cn.getStateName();
			        	//Global_Data.local_pwd = ""+cn.getPwd();
			        	
			        	results.add(str_beat);
			        	//System.out.println("Local Values:-"+Global_Data.local_user);
			        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
			        	                             }
			          
			        //Reading all 
				         List<Local_Data> contacts3 = dbvoc.getAllRetailers();
				         results2.add("Select Retailer");
				          for (Local_Data cn : contacts3) {
				        	String str_retailer = ""+cn.getStateName();
				        	//Global_Data.local_pwd = ""+cn.getPwd();
				        	
				        	results2.add(str_retailer);
				        	//System.out.println("Local Values:-"+Global_Data.local_user);
				        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
				        	                             }
			          
		    
		  ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results1);
		  ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results);
		  ArrayAdapter<String> adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results2);
		  		  
		  adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  city_spinner.setEnabled(false);   
		  city_spinner.setClickable(false);  
		  city_spinner.setAdapter(adapter_state1);
		  city_spinner.setOnItemSelectedListener(this);
		  
		  adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  beat_spinner.setAdapter(adapter_state2);
		  beat_spinner.setOnItemSelectedListener(this);
		  
		  adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  retail_spinner.setAdapter(adapter_state3);
		  retail_spinner.setOnItemSelectedListener(this);
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
			mTitleTextView.setText("Order");

			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = Customer_Service.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
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
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
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
		
			    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
	
	void Flwg()
    {
   	 //try
//		   {
//   		        httpclint=new DefaultHttpClient();
//			    httppst=new HttpGet("http://19e3fb71.ngrok.com/fieldsales/user/3/tasks/2015-09-15/all");
////			    nameValuePars=new ArrayList<NameValuePair>();
////			    httppst.setEntity(new UrlEncodedFormEntity(nameValuePars));
//				ResponseHandler<String> responseHandler = new BasicResponseHandler();
// 			    final String response = httpclint.execute(httppst, responseHandler);
// 			   // SharedData.Fwg_Resp=httpclint.execute(httppst, responseHandler);
// 			    System.out.println("server_resp===============================>"+response);
// 			    //finish();
//				//startActivity(new Intent(ProfileLifeStream.this,FollowTabber.class));
//				runOnUiThread(new Runnable() {
//				    public void run() {
//
//				    }
//				});
//
//			}catch(Exception e){
//				                 System.out.println("Exception:"+e.getMessage());
//			                   }
  }
}

