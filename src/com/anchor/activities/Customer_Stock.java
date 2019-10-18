package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Customer_Stock extends Activity {
	
	private boolean isSpinnerInitial = true;
	DataBaseHelper dbvoc = new DataBaseHelper(this);

	LoginDataBaseAdapter loginDataBaseAdapter;
	private String Current_Date = "";
	Spinner feed_spinner,category_spinner,product_spinner,variant_spinner;
	Button claims_submit;
	private String[] feed_state = { "Competitors Stock" };
	private String[] category_state = { "Category" };
	private String[] product_state = { "Product" };
	private String[] variant_state = { "Variant" };
	int check=0;
	int check_product=0;
	int check_ProductSpec=0;
	private String CAT_ID = "";
	private String PRO_ID = "";
	private String VAR_ID = "";
	
	
	private String name,CP_NAME,RE_TEXT;
	
	EditText edit_quantity,edit_details;
	
	private String filePath = null;
	List<String> Stock;
	List<String> Categary;
	List<String> product;
	List<String> variants;
	ArrayAdapter<String> adapter_state1;
	ArrayAdapter<String> adapter_state2;
	//ArrayAdapter<String> adapter_state4;
	ArrayAdapter<String> adapter_state3;
	ArrayAdapter<String> adapter_state4;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customer_claims);
		
		// Receiving the data from previous activity
		//  Intent i = getIntent();

		 // image or video path that is captured in previous activity
		
		isSpinnerInitial = true;
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	    loginDataBaseAdapter=loginDataBaseAdapter.open();
	    Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = sdf.format(c.getTime());
        Current_Date = sdf.format(c.getTime());
        
        Intent i = getIntent();
	    name = i.getStringExtra("retialer");
	    CP_NAME = i.getStringExtra("CP_NAME");
	    RE_TEXT = i.getStringExtra("RE_TEXT");
	    
	   
	   
		filePath = i.getStringExtra("filePath");
		
		if (filePath != null) {
			// Displaying the image or video on the screen
			//previewMedia(isImage);
			//Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_LONG).show();
		} else {
			//Toast.makeText(getApplicationContext(),"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
		}
				
			claims_submit= findViewById(R.id.claims_submit);
			edit_quantity= findViewById(R.id.edit_quantity);
			edit_details= findViewById(R.id.edit_details);

		    edit_details.setFilters(new InputFilter[]{filter});
			
			//feed_spinner=(Spinner)findViewById(R.id.comp_stock);
			category_spinner= findViewById(R.id.category);
			product_spinner= findViewById(R.id.product);
			variant_spinner= findViewById(R.id.variant);
			
//    		category_spinner.setOnItemSelectedListener(this);
//		    product_spinner.setOnItemSelectedListener(this);
//		    variant_spinner.setOnItemSelectedListener(this);
		   // feed_spinner.setOnItemSelectedListener(this);
		
		  
		  
		  Categary = new ArrayList<String>();
		  product = new ArrayList<String>();
		  variants = new ArrayList<String>();
		  Stock = new ArrayList<String>();
		  
		  Categary.add("Select Category");
		  product.add("Select product");
		  variants.add("Select variants");
		  Stock.add("Select Competitors Stock");
		    
		  List<Local_Data> contacts2 = dbvoc.getAllProductPackSizes();      
          for (Local_Data cn : contacts2) 
          {
        	     
        	  Stock.add(cn.get_PackSizes_desc());
	      }

		// category old logic commit by vinod 7 9 2016
//          List<Local_Data> contacts = dbvoc.getCategory();
//          for (Local_Data cn : contacts)
//          {
//
//        	  Categary.add(cn.get_category_desc());
//	      }
          
          //ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Stock);
		  //ArrayAdapter<String> adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Categary);
		 // ArrayAdapter<String> adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, product);
		 // ArrayAdapter<String> adapter_state4 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, variants);
		  
		  
		  /*adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Stock);
		  adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  feed_spinner.setAdapter(adapter_state1);*/


			List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
			//results1.add("Select Category");
			for (Local_Data cn : contacts1)
			{
				if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
				{
					String str_categ = ""+cn.getStateName();
					Categary.add(str_categ);
				}
			}
	       
		  adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Categary);
		  adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  category_spinner.setAdapter(adapter_state2);
		  
		  adapter_state3= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, product);
		  adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  product_spinner.setAdapter(adapter_state3);

		  adapter_state4= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, variants);
		  adapter_state4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  variant_spinner.setAdapter(adapter_state4);
		 // category_spinner.setOnItemSelectedListener(this);
//		  
//		  adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  product_spinner.setAdapter(adapter_state3);
//		  product_spinner.setOnItemSelectedListener(this);
//		  
//		  
//		  adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  variant_spinner.setAdapter(adapter_state4);
//		  variant_spinner.setOnItemSelectedListener(this);
		  
		 
		 
		  
		 
		 
		  
		  
		  //variant_spinner.setOnItemSelectedListener(this);
		
		
		claims_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				 
				/*if(feed_spinner.getSelectedItem().toString() == "Select Competitors Stock")
				 {
					 Toast.makeText(getApplicationContext(),"Please Select Competitors Stock", Toast.LENGTH_LONG).show();
				 }
				 else*/
				 if(category_spinner.getSelectedItem().toString() == "Select Category")
				 {
					 //Toast.makeText(getApplicationContext(),"Please Select Category", Toast.LENGTH_LONG).show();

					 Toast toast = Toast.makeText(getApplicationContext(),"Please Select Category", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, 0);
					 toast.show();

				 }
				 else
				 if(product_spinner.getSelectedItem().toString() == "Select product")
				 {
					 //Toast.makeText(getApplicationContext(),"Please Select Product", Toast.LENGTH_LONG).show();

					 Toast toast = Toast.makeText(getApplicationContext(),"Please Select Product", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, 0);
					 toast.show();

				 }
				 else
				 if(variant_spinner.getSelectedItem().toString() == "Select variants")
				 {
					// Toast.makeText(getApplicationContext(),"Please Variant", Toast.LENGTH_LONG).show();

					 Toast toast = Toast.makeText(getApplicationContext(),"Please Variant", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, 0);
					 toast.show();
				 }
				 else
				 if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(edit_quantity.getText().toString()))
				 {
					 //Toast.makeText(getApplicationContext(),"Please Enter Quantity", Toast.LENGTH_LONG).show();

					 Toast toast = Toast.makeText(getApplicationContext(),"Please Enter Quantity", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, 0);
					 toast.show();

				 }
				 else
				 if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(edit_details.getText().toString()))
				 {
					// Toast.makeText(getApplicationContext(),"Please Enter Details", Toast.LENGTH_LONG).show();

					 Toast toast = Toast.makeText(getApplicationContext(),"Please Enter Details", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, 0);
					 toast.show();
				 }
				 else
				 {				
					 
//					 String RE_ID = "";
					 List<Local_Data> contacts = dbvoc.VARIENT_ID(variant_spinner.getSelectedItem().toString());
		             for (Local_Data cn : contacts)
		             {

						 VAR_ID = cn.getItem_Code();
						 PRO_ID = cn.getItem_Code();
						 CAT_ID = cn.getItem_Code();
			         }

					 String gaddress = "";
					 try {
						 if (Global_Data.address.equalsIgnoreCase("null")) {
							 gaddress = "";
						 } else {
							 gaddress = Global_Data.address;
						 }
					 }catch(Exception ex){ex.printStackTrace();}

					 String sms_body = "";
					 sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" There is competitive information from  " + Global_Data.CUSTOMER_NAME_NEW + "  that I have uploaded." + " This is to keep you updated."+"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;

					 if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
					 {
					//	 Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, Customer_Stock.this);
						 // mobile_numbers.add(Global_Data.cus_MAnager_mobile);
					 }

					 try
					 {
						 AppLocationManager appLocationManager = new AppLocationManager(Customer_Stock.this);
						 Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
						 Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
						 PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Stock.this);

						 if(PlayServiceManager.checkPlayServices(Customer_Stock.this))
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

					 Long randomPIN = System.currentTimeMillis();
					 String PINString = String.valueOf(randomPIN);

		 			 loginDataBaseAdapter.insertCustomerServiceCompetitionStock("", Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_USER_EMAIL,
		 					CAT_ID,PRO_ID,VAR_ID, "PSIZE_ID", "PNAME", edit_quantity.getText().toString(), edit_details.getText().toString(), Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,PINString);
					 
					// Toast.makeText(getApplicationContext(),"Your Data Save Successfuly", Toast.LENGTH_LONG).show();

					 Toast toast = Toast.makeText(getApplicationContext(),"Your Data Save Successfuly", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, 0);
					 toast.show();
					 
				 	Intent intent = new Intent(Customer_Stock.this, Order.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					finish();
				 }
				
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
	       /* Intent i = getIntent();
			String name = i.getStringExtra("retialer");
			filePath = i.getStringExtra("filePath");

			if (filePath != null) {
				// Displaying the image or video on the screen
				//previewMedia(isImage);
				//Toast.makeText(getApplicationContext(),filePath, Toast.LENGTH_LONG).show();
			} else {
				//Toast.makeText(getApplicationContext(),"Sorry, file path is missing!", Toast.LENGTH_LONG).show();
			}
			*/
				View mCustomView = mInflater.inflate(R.layout.action_bar, null);
				mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
				TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
				mTitleTextView.setText(Global_Data.retailer);

				TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
				ImageView H_LOGO = mCustomView.findViewById(R.id.Header_logo);
				H_LOGO.setImageResource(R.drawable.list);
				H_LOGO.setVisibility(View.VISIBLE);
				SharedPreferences sp = Customer_Stock.this.getSharedPreferences("SimpleLogic", 0);

//	        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//				todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//			}
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
//	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
					todaysTarget.setText("Today's Target Acheived");
				}

				mActionBar.setCustomView(mCustomView);
				mActionBar.setDisplayShowCustomEnabled(true);
				mActionBar.setHomeButtonEnabled(true);
				mActionBar.setDisplayHomeAsUpEnabled(true);
			}catch(Exception ex){ex.printStackTrace();}


		category_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
									   int pos, long arg3) {
//				// TODO Auto-generated method stub





				check=check+1;
				if(check>1)
				{
					if (parent.getItemAtPosition(pos).toString()
							.equalsIgnoreCase("Select Category")) {

						product.clear();
						product.add("Select Product");
						adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, product);
						adapter_state3.setDropDownViewResource(R.layout.spinner_item);
						product_spinner.setAdapter(adapter_state3);

						variants.clear();
						variants.add("Select Variant");
						adapter_state4 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, variants);
						adapter_state4.setDropDownViewResource(R.layout.spinner_item);
						variant_spinner.setAdapter(adapter_state4);

						//Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					}
					else
					{



						product.clear();
						//List<Local_Data> contacts22 = dbvoc.HSS_DescriptionITEM1_ID(Global_Data.GLOVEL_CATEGORY_ID);
						List<Local_Data> contacts22 = dbvoc.HSS_DescriptionITEM1_category_name(parent.getItemAtPosition(pos).toString().trim());
						product.add("Select Product");
						for (Local_Data cn : contacts22) {
							String str_product = ""+cn.getStateName();
							//Global_Data.local_pwd = ""+cn.getPwd();

							product.add(str_product);
							System.out.println("Local Values:-"+Global_Data.local_user);
							//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
						}

						adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, product);
						adapter_state3.setDropDownViewResource(R.layout.spinner_item);
						product_spinner.setAdapter(adapter_state3);
						//startActivity(intent);
						//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

					}
//
				}
//
//

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		product_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
									   int pos, long arg3) {
//					// TODO Auto-generated method stub

				check_product=check_product+1;
				if(check_product>1)
				{

					if (category_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Category"))
					{

						Toast toast = Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					}
					else
					if (parent.getItemAtPosition(pos).toString()
							.equalsIgnoreCase("Select Product"))
					{
						variants.clear();
						variants.add("Select Variant");
						adapter_state4 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, variants);
						adapter_state4.setDropDownViewResource(R.layout.spinner_item);
						variant_spinner.setAdapter(adapter_state4);

						//Toast.makeText(getApplicationContext(), "Please Select Product", Toast.LENGTH_LONG).show();

					}
					else
					{


						List<Local_Data> contacts33 = dbvoc.HSS_DescriptionITEM1_IDD(parent.getItemAtPosition(pos).toString().trim());
						for (Local_Data cn : contacts33)
						{
							Global_Data.GLOVEL_PRODUCT_ID = cn.getCust_Code();
						}

						if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOVEL_PRODUCT_ID)) {
							variants.clear();
							//List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_ID(Global_Data.GLOVEL_PRODUCT_ID);
							List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_BYNAME(category_spinner.getSelectedItem().toString().trim(),parent.getItemAtPosition(pos).toString().trim());
							variants.add("Select Variant");
							for (Local_Data cn : contacts3)
							{

								variants.add(cn.getStateName().trim());
							}



							adapter_state4 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, variants);
							adapter_state4.setDropDownViewResource(R.layout.spinner_item);
							variant_spinner.setAdapter(adapter_state4);

						}


					}
//
				}



			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

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
	
//	@Override
//	public void onItemSelected(AdapterView<?> parent, View arg1, int arg2,
//			long arg3) {
//		// TODO Auto-generated method stub
//
//		 if(isSpinnerInitial)
//	        {
//	            isSpinnerInitial = false;
//	        }
//	        else
//	        {
//			 Spinner spinner = (Spinner) parent;
//		     if(spinner.getId() == R.id.comp_stock)
//		     {
//
//
//		     }
//		     else if(spinner.getId() == R.id.category)
//		     {
//
//		    	   if (category_spinner.getSelectedItem().toString() == "Select Category")
//		    	   {
//		    		     product.clear();
//		    		     product.add("Select product");
//		    		     adapter_state3= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, product);
//		    	         adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		    			 product_spinner.setAdapter(adapter_state3);
//
//		    			 variants.clear();
//		    		 	 variants.add("Select variants");
//		    		     adapter_state4= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, variants);
//		    	         adapter_state4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		    	         variant_spinner.setAdapter(adapter_state4);
//		    	   }
//		    	   else
//		    	   {
//			    	    String items = category_spinner.getSelectedItem().toString();
//		                String C_ID = "";
//		                Log.i("Selected item : ", items);
//
//		                List<Local_Data> contacts = dbvoc.getCategoryy(category_spinner.getSelectedItem().toString());
//		                for (Local_Data cn : contacts)
//		                {
//
//		                	C_ID = cn.get_category_id();
//		                	CAT_ID = cn.get_category_id();
//		      	        }
//
//		                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(C_ID))
//		                {
//		                	getproductA(C_ID);
//		                }
//		    	   }
//
//		     }
//		     else if(spinner.getId() == R.id.product)
//		     {
//
//	    	 if (product_spinner.getSelectedItem().toString() == "Select product")
//	    	   {
//	    		 	 variants.clear();
//	    		 	 variants.add("Select variants");
//	    		     adapter_state4= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, variants);
//	    	         adapter_state4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	    	         variant_spinner.setAdapter(adapter_state4);
//	    	   }
//	    	   else
//	    	   {
//		    	    String C_ID = "";
//	                String items = product_spinner.getSelectedItem().toString();
//	                Log.i("Selected item : ", items);
//
//	                List<Local_Data> contacts = dbvoc.getProducty(items);
//	                for (Local_Data cn : contacts)
//	                {
//
//	                	C_ID = cn.get_product_ids();
//	                	PRO_ID = cn.get_product_ids();
//	      	        }
//
//	                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(C_ID))
//	                {
//	                	getvariantsA(C_ID);
//	                }
//	    	   }
//
//		     }
//
//		     else if(spinner.getId() == R.id.variant)
//		     {
//
//		    	 	String C_ID = "";
//	                String items = product_spinner.getSelectedItem().toString();
//	                Log.i("Selected item : ", items);
//
//	                List<Local_Data> contacts = dbvoc.getVarianty(items);
//	                for (Local_Data cn : contacts)
//	                {
//	                	VAR_ID = cn.get_variants_desc();
//	      	        }
//		     }
//
//	       }
//	}



	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		 Categary.clear();
		 product.clear();
		 variants.clear();
		 Stock.clear();
		  
	    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		this.finish();
	}



	
	public void getproductA(String ID)
	{
        List<Local_Data> contacts2 = dbvoc.getProduct(ID);      
        for (Local_Data cn : contacts2) 
        {
      	     
    	 product.add(cn.get_product_desc());
        }
         adapter_state3= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, product);
         adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 product_spinner.setAdapter(adapter_state3);
	}
	
	public void getvariantsA(String ID)
	{
		List<Local_Data> contacts2 = dbvoc.getVariants(ID);      
        for (Local_Data cn : contacts2) 
        {
      	     
    	variants.add(cn.get_variants_desc());
        }
            
     	adapter_state4= new ArrayAdapter<String>(Customer_Stock.this,android.R.layout.simple_spinner_item, variants);
     	adapter_state4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		variant_spinner.setAdapter(adapter_state4);
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
}
