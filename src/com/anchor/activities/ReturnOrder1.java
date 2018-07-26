package com.anchor.activities;

import android.app.ActionBar;
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
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.animation.ActivitySwitcher;
import com.anchor.model.Category;
import com.anchor.model.DatabaseProductModel;
import com.anchor.model.Product;
import com.anchor.model.Products_Mine;
import com.anchor.model.Scheme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ReturnOrder1 extends BaseActivity {
	int check=0;
	MediaPlayer mpplayer;
	String categ_name,subcateg_name;
	int check_product=0;
	int check_ProductSpec=0;
	String CategoriesSpinner = "";
	AutoCompleteTextView Product_Variant;
	String ProductSpinner = "";
	String price = "";
	String str_variant;
	LoginDataBaseAdapter loginDataBaseAdapter;
	Spinner spnCategory, spnProductSpec, spnScheme, spnProduct;
	TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity,txt_rp;
	EditText txtDeleiveryQuantity1;
	static int quantity = 0, deleiveryQuantity = 0;
	static float rp, mrp, totalprice,totalprice1,totalprc_scheme, productprice;
	static String scheme = "";
	EditText editTextQuantity;
	static String category, productName, productSpec, productQuantity,
			productDeleiveryQuantity, productScheme, productrp, productmrp,
			producttotalPrice;
	Button buttonAddMOre, buttonPreviewOrder;

	//ArrayList<DatabaseModel> dataCategories,dataVarients;
	ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct;
	//ArrayList productList = new ArrayList();
	 List<String> listProduct,listProductSpec;
	 List<String> listScheme;
	 ArrayList<String> Discount_list = new ArrayList<String>();
	 ArrayAdapter<String> dataAdapterScheme;
	 static int categoryID,productID,schemeID;
	 HashMap<String, String> categoriesMap,productsMap;
	 int dbschemeID;
	 private ArrayList<String> results = new ArrayList<String>();
	 private ArrayList<String> results1 = new ArrayList<String>();
	 private ArrayList<String> results2 = new ArrayList<String>();
	 private ArrayList<String> result_product = new ArrayList<String>();
	 ArrayAdapter<String> adapter_state1;
	 ArrayAdapter<String> adapter_state2;
	 ArrayAdapter<String> adapter_state3;
	 ArrayAdapter<String> Discount_Adapter;
	 
	    SimpleCursorAdapter cursorAdapter;
		DataBaseHelper dbvoc = new DataBaseHelper(this);
	 
	ArrayList<Category> dataCategories = new ArrayList<Category>();
	ArrayList<Product> dataProducts = new ArrayList<Product>();
	ArrayList<Scheme> dataScheme = new ArrayList<Scheme>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.return_order_home);
		mpplayer = new MediaPlayer();
		// create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	        txtPrice = (TextView) findViewById(R.id.txtPrice1);
			//txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity1);
			//txtDeleiveryQuantity1 = (EditText) findViewById(R.id.txtDeleiveryQuantity);
			spnCategory = (Spinner) findViewById(R.id.spnCategory);
			spnProduct = (Spinner) findViewById(R.id.spnProduct);
		    Product_Variant = (AutoCompleteTextView) findViewById(R.id.Product_Variant);
			//spnProductSpec = (Spinner) findViewById(R.id.spnProductSpec);
			spnScheme = (Spinner) findViewById(R.id.spnScheme1);
			//spnScheme.setVisibility(View.INVISIBLE);

			editTextRP = (TextView) findViewById(R.id.editTextRP);
			editTextMRP = (TextView) findViewById(R.id.editTextMRP);

			editTextQuantity = (EditText) findViewById(R.id.editTextQuantity1);

		txt_rp = (TextView) findViewById(R.id.textRP);

       // for label RP change
		SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
		String rpstr=spf1.getString("var_rp", "");
		txt_rp.setText(rpstr);

		SharedPreferences spf11 = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);
		Global_Data.app_sound = spf11.getBoolean("var_addmore", false);

		SharedPreferences sound = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);
		Global_Data.sound_file = sound.getString("var_addsound", "");

		results2.clear();
		List<Local_Data> contacts2 = dbvoc.getAllVariant();
		for (Local_Data cn : contacts2) {
			results2.add(cn.getStateName());
			result_product.add(cn.getStateName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item,
				results2);
		Product_Variant.setThreshold(1);// will start working from
		// first character
		Product_Variant.setAdapter(adapter);// setting the adapter
		// data into the
		// AutoCompleteTextView
		Product_Variant.setTextColor(Color.BLACK);

		Product_Variant.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int DRAWABLE_LEFT = 0;
				final int DRAWABLE_TOP = 1;
				final int DRAWABLE_RIGHT = 2;
				final int DRAWABLE_BOTTOM = 3;

				if(event.getAction() == MotionEvent.ACTION_UP) {
					if(event.getRawX() >= (Product_Variant.getRight() - Product_Variant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

						View view = ReturnOrder1.this.getCurrentFocus();
						if (view != null) {
							InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
						}
						//autoCompleteTextView1.setText("");
						Product_Variant.showDropDown();
						return true;
					}
				}
				return false;
			}
		});

		Product_Variant.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
									long id) {
				//Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

				Global_Data.hideSoftKeyboard(ReturnOrder1.this);

				editTextQuantity.setFocusableInTouchMode(true);
				editTextQuantity.setEnabled(true);

				List<Local_Data> cont = dbvoc.getProductByCat(Product_Variant.getText().toString().trim());
				//results2.add("Select Variant");
				for (Local_Data cn1 : cont) {
					String str_var = ""+cn1.getStateName();
					String str_var1 = ""+cn1.getMRP();
					String str_var2 = ""+cn1.get_Description();
					String str_var3 = ""+cn1.get_Claims();
					Global_Data.amnt= ""+cn1.get_Description();
					Global_Data.amnt1= ""+cn1.get_Claims();

					categ_name = cn1.getCategory();
					subcateg_name = cn1.getSubcateg();

					editTextRP.setText(str_var);
					editTextMRP.setText(str_var1);

					txtPrice.setText("Total Price : "+"");

					if (editTextQuantity.getText().toString().length() != 0) {
						if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")  && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
						{
							long final_mrp = (Long.valueOf(editTextMRP.getText().toString()))*(Long.valueOf(editTextQuantity.getText().toString().trim()));
							txtPrice.setText("Total Price : "+final_mrp);
							price = String.valueOf(final_mrp);

							// txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
						}
						else
						{
							if(!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase(null) && !editTextMRP.getText().toString().equalsIgnoreCase("null")  && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
							{
								// Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
								// txtPrice.setText("Total Price : "+final_mrp);
								// price = String.valueOf(final_mrp);
								//txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
							}
						}
					}
				}

				adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnCategory.setAdapter(adapter_state1);
				//spnCategory.setOnItemSelectedListener(NewOrderActivity.this);

				adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spnProduct.setAdapter(adapter_state2);
				//spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(categ_name)){
					int spinnerPosition = adapter_state1.getPosition(categ_name);
					spnCategory.setSelection(spinnerPosition);
				}
			}
		});

		Product_Variant.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {



			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				listScheme.clear();
				listScheme.add("Select Scheme");

				dataAdapterScheme.notifyDataSetChanged();
				dataAdapterScheme.setDropDownViewResource(R.layout.spinner_item);
				spnScheme.setAdapter(dataAdapterScheme);
				rp = 0.00f;
				mrp = 0.00f;
				productprice = rp;

				editTextRP.setText("" + rp);
				editTextMRP.setText("" + mrp);
				editTextQuantity.setFocusableInTouchMode(false);
				editTextQuantity.setEnabled(false);
				//txtPrice.setText("Total Price : ");

			}
		});

		    editTextQuantity.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {



			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if(!String.valueOf(s).equalsIgnoreCase(""))
				{
					if(Integer.parseInt(String.valueOf(s))<=0)
					{
						editTextQuantity.setText("");
						txtPrice.setText("Total Price : "+"");
						price = String.valueOf("");
					}
				}
				else
				{
					txtPrice.setText("Total Price : "+"");
					//price = String.valueOf(" ");
				}

			}
		});
	     
	     
			Global_Data.GLOVEL_SubCategory_Button = "";
		
//		//Reading all 
//	         List<Local_Data> contacts = dbvoc.getAllMain();      
//	          for (Local_Data cn : contacts) {
//	        	Global_Data.local_user = ""+cn.getUser();
//	        	Global_Data.local_pwd = ""+cn.getPwd();
//	        	System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
//	        	//Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//	        	                             }
		
		    SharedPreferences spf=ReturnOrder1.this.getSharedPreferences("SimpleLogic",0);        
	        SharedPreferences.Editor editor=spf.edit();        
	        editor.putString("order", "new");
	        editor.commit();
		
	      //Reading all 
	         List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
	         results1.add("Select Category");
	          for (Local_Data cn : contacts1) 
	          {
	        	  if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
	        	  {
	        		  String str_categ = ""+cn.getStateName();
	        		  results1.add(str_categ);
	        	  }
              }
	          
	        //Reading all 
//		         List<Local_Data> contacts2 = dbvoc.HSS_DescriptionITEM1();
		         results.add("Select Product");
//		          for (Local_Data cn : contacts2) {
//		        	String str_product = ""+cn.getStateName();
//		        	//Global_Data.local_pwd = ""+cn.getPwd();
//		        	
//		        	results.add(str_product);
//		        	System.out.println("Local Values:-"+Global_Data.local_user);
//		        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//		        	                             }
//		          
		          adapter_state2 = new ArrayAdapter<String>(this, R.layout.spinner_item, results);
		          adapter_state2.setDropDownViewResource(R.layout.spinner_item);
				  spnProduct.setAdapter(adapter_state2);
		          
		          
		        //Reading all 
			        // List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2();
			         //results2.add("Select Variant");
//			          for (Local_Data cn : contacts3) {
//			        	str_variant = ""+cn.getStateName();
//			        	//Global_Data.local_pwd = ""+cn.getPwd();
//			        	
//			        	results2.add(str_variant);
//			        	//System.out.println("Local Values:-"+Global_Data.local_user);
//			        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//			        		        	
//			        	
//			        	                             }
			          
			          
//
//			          adapter_state3 = new ArrayAdapter<String>(this, R.layout.spinner_item, results2);
//			          adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//					  spnProductSpec.setAdapter(adapter_state3);
			          
					  
//					  List<Local_Data> cont = dbvoc.getProductByCat(str_variant);
//				         //results2.add("Select Variant");
//				          for (Local_Data cn1 : cont) {
//				        	String str_var = ""+cn1.get_Claims();
//				            String str_var1 = ""+cn1.getCategory();
//				        	
//				            editTextRP.setText(str_var);
//				            editTextMRP.setText(str_var1);
//				            
//				            //Global_Data.local_pwd = ""+cn.getPwd();
//				        	
//				        	//results2.add(str_variant);
//				        	//System.out.println("Local Values:-"+Global_Data.local_user);
//				        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//				        	
//				        	
//				        	
//				        	                             }
					  
					  
		          			          
//	        TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//	        //question_value.setTypeface(null,Typeface.BOLD);
//	       welcomeUser.setText(spf.getString("FirstName", "")+" "+ spf.getString("LastName", ""));
		
		
		listProduct = new ArrayList<String>();
		dataAdapterProduct = new ArrayAdapter<String>(
				this, R.layout.spinner_item, listProduct);

		listProductSpec = new ArrayList<String>();
		dataAdapterProductSpec = new ArrayAdapter<String>(
				this, R.layout.spinner_item, listProductSpec);

		listScheme = new ArrayList<String>();
		dataAdapterScheme = new ArrayAdapter<String>(
				this, R.layout.spinner_item, listScheme);

		final List<String> listCategory = new ArrayList<String>();
		listCategory.add("Select Category");
		 
		  adapter_state1 = new ArrayAdapter<String>(this,R.layout.spinner_item, results1);
//		  adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results);
//		  adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results2);
		  
//		  Discount_list.add("Select Discount Type");
//		  Discount_list.add("Rupees");
//		  Discount_list.add("Percentage");
//		  
//		  Discount_Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Discount_list);
//		  Discount_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//		  spnScheme.setAdapter(Discount_Adapter);
		
//		  adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  city_spinner.setAdapter(adapter_state1);
//		  city_spinner.setOnItemSelectedListener(this);
//		  
//		  adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  beat_spinner.setAdapter(adapter_state2);
//		  beat_spinner.setOnItemSelectedListener(this);
//		  
//		  adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  retail_spinner.setAdapter(adapter_state3);
//		  retail_spinner.setOnItemSelectedListener(this);
		  
//		  txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
//		  if(Global_Data.GLOVEL_LONG_DESC != "")
//		  {
//			  spnProduct.setText(Global_Data.GLOVEL_LONG_DESC);
//			  editTextQuantity.setFocusable(true) ;
//			  editTextMRP.setText(Global_Data.GLOVEL_ITEM_MRP);
//			  spnScheme.setEnabled(true);
//			  txtPrice.setText("Total Price : "+Global_Data.GLOVEL_ITEM_MRP);
//			 
//		  }
//		  else
//		  {
//			  editTextQuantity.setFocusable(false) ; 
//			  editTextMRP.setText("");
//			  spnScheme.setEnabled(false);
//		  }
//		
		spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
									   int pos, long arg3) {
//					// TODO Auto-generated method stub

				//editTextQuantity.setText("");
				if(!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))){
					txtPrice.setText("Total Price : "+"");
				}


//					 Toast.makeText(parent.getContext(),
//					 "OnItemSelectedListener : " +
//					 parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//					editTextQuantity.setFocusableInTouchMode(false);
//					editTextQuantity.setEnabled(false);
//					txtPrice.setText("Total Price : ");
//
				check_product=check_product+1;
				if(check_product>1)
				{

					if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category"))
					{

						//Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

						categ_name = "";
						subcateg_name = "";
						Toast toast = Toast.makeText(getApplicationContext(),"Please Select Category",Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					}
					else
					if (parent.getItemAtPosition(pos).toString()
							.equalsIgnoreCase("Select Product"))
					{
//					    	 	results2.clear();
//								results2.add("Select Variant");
//								adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//						        adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//								spnProductSpec.setAdapter(adapter_state3);

						categ_name = "";
						subcateg_name = "";
						results2.add("");
						results2.clear();

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReturnOrder1.this,
								android.R.layout.simple_spinner_dropdown_item,
								result_product);
						Product_Variant.setThreshold(1);// will start working from
						// first character
						Product_Variant.setAdapter(adapter);// setting the adapter
						// data into the
						// AutoCompleteTextView
						Product_Variant.setTextColor(Color.BLACK);
						Product_Variant.setText("");

						//Toast.makeText(getApplicationContext(), "Please Select Product", Toast.LENGTH_LONG).show();

					}
					else
					{

						results2.clear();

						List<Local_Data> contacts33 = dbvoc.HSS_DescriptionITEM1_IDD(parent.getItemAtPosition(pos).toString().trim());
						for (Local_Data cn : contacts33)
						{
							Global_Data.GLOVEL_PRODUCT_ID = cn.getCust_Code();
						}

						if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOVEL_PRODUCT_ID)) {

							//List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_ID(Global_Data.GLOVEL_PRODUCT_ID);
							List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_BYNAME(spnCategory.getSelectedItem().toString().trim(),parent.getItemAtPosition(pos).toString().trim());
							// results2.add("Select Variant");
							for (Local_Data cn : contacts3)
							{
								str_variant = ""+cn.getStateName();
								//Global_Data.local_pwd = ""+cn.getPwd();

								results2.add(str_variant);
								//System.out.println("Local Values:-"+Global_Data.local_user);
								//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();


							}



//							 adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//							 adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//							 spnProductSpec.setAdapter(adapter_state3);

							ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReturnOrder1.this,android.R.layout.simple_spinner_dropdown_item,results2);
							Product_Variant.setThreshold(1);// will start working from
							// first character
							Product_Variant.setAdapter(adapter);// setting the adapter
							// data into the
							// AutoCompleteTextView
							Product_Variant.setTextColor(Color.BLACK);


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
		  
		  
		for(int i = 0 ; i < dataCategories.size();i++){
			listCategory.add(dataCategories.get(i).getDesc());
		}
		
		  dataAdapterCategory = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);
		  dataAdapterCategory.setDropDownViewResource(R.layout.spinner_item);
		  spnCategory.setAdapter(adapter_state1);
		  
		  if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
			{
				Log.d("Globel ", "in");
				spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
				Global_Data.GLOVEL_CATEGORY_SELECTION = "";
				
			}
		
		Intent i = getIntent();
		String name = i.getStringExtra("retialer");
		if (i.hasExtra("data")) {
			//Log.e("data", "***********productList**********");
			Global_Data.productList=i.getParcelableArrayListExtra("productsList"); 
		}
		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8A0808")));

		actionBar.setTitle(name);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
*/
		try
		{
			ActionBar mActionBar = getActionBar();
			mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
			// mActionBar.setDisplayShowHomeEnabled(false);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(this);

			View mCustomView = mInflater.inflate(R.layout.action_bar, null);
			mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
			TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText(Global_Data.order_retailer +" "+"("+Global_Data.AmountOutstanding+"/"+Global_Data.AmountOverdue+")");

			TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);

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
		}catch(Exception ex){ex.printStackTrace();}

       /* mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);*/
       
		buttonAddMOre = (Button) findViewById(R.id.buttonAddMOre);
		buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
		
		buttonAddMOre.setOnTouchListener(new OnTouchListener() {
			
	
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
			        b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			        //down event
			        b.setBackgroundColor(Color.parseColor("#910505"));
			        
					/*if (spnCategory.getSelectedItem().toString()
							.equalsIgnoreCase("Select Category")
							|| spnProduct.getSelectedItem().toString()
									.equalsIgnoreCase("Select Product")
							|| spnCategory.getSelectedItem().toString()
									.equalsIgnoreCase("Select Variant")
							|| editTextQuantity.getText().toString().length() == 0) {

						//Toast.makeText(getApplicationContext(),"Please Fill details ", Toast.LENGTH_SHORT).setGravity(Gravity.CENTER, 0, 0).show();
						Toast toast = Toast.makeText(getApplicationContext(),"Please Fill details ", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					*/
					if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
						Toast toast = Toast.makeText(ReturnOrder1.this,"Please Select Category", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}

					else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
						Toast toast = Toast.makeText(ReturnOrder1.this,"Please Select Product", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}

					else if (Product_Variant.getText().toString().trim().equalsIgnoreCase("")) {
						Toast toast = Toast.makeText(ReturnOrder1.this,"Please Select Variant", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}

					else if (editTextQuantity.getText().toString().length() == 0) {
						Toast toast = Toast.makeText(ReturnOrder1.this,"Please enter Quantity", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
//					else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")) {
//						Toast toast = Toast.makeText(NewOrderActivity.this,"Please Select Discount Type", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
					
					else{

						// TODO Auto-generated method stub
						// v.setBackgroundColor(Color.parseColor("#910505"));
						
						  // create a instance of SQLite Database
						
//						if (!spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type") && txtDeleiveryQuantity1.getText().toString().length() == 0) {
//							Toast toast = Toast.makeText(NewOrderActivity.this,"Please enter Discount Amount", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}
//						else
//						{
//							String discount_type = "";
//				   	        String discount_amount = "";
//						     if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type"))
//						     {
//						    	 discount_type = "";
//						    	 discount_amount = "";
//						     }
//						     else
//						     {
//						    	 
//					    		 discount_type = spnScheme.getSelectedItem().toString().trim();
//						    	 discount_amount = txtDeleiveryQuantity1.getText().toString().trim();
//						    	 
//						     }
//						
							  Global_Data.order_category = spnCategory.getSelectedItem().toString();
							  //Global_Data.order_product = spnProduct.getSelectedItem().toString();
							 // Global_Data.order_variant = spnProductSpec.getSelectedItem().toString();
							  //Global_Data.order_variant = spnProduct.getText().toString().trim();
	
						     loginDataBaseAdapter=loginDataBaseAdapter.open();
						     
	//					     //Reading all 
						       String item_name = "";
					    	   	List<Local_Data> contacts1 = dbvoc.getItemCode_return(spnCategory.getSelectedItem().toString().trim(),spnProduct.getSelectedItem().toString().trim(),Product_Variant.getText().toString().trim());
					    	   	for (Local_Data cn1 : contacts1) 
				    	         {
				    	       	 
							    	Global_Data.item_code_return = cn1.getItem_Code();
							    	Global_Data.GLOvel_ITEM_NUMBER_RETURN = cn1.getItem_Code();
							    	item_name = cn1.getProdname();
				    	       	 
				    	         }
						     if(Global_Data.GLOVEL_ORDER_REJECT_FLAG.equalsIgnoreCase("TRUE"))
						     {
						    	 List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLObalOrder_id_return,Global_Data.GLOvel_ITEM_NUMBER);
					   	          
								  if(contactsn.size() > 0)
								  {
									  ///Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT).show();

									  Toast toast = Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();
								  }
								  else
								  {
									 // Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG).show();

									  if (Global_Data.app_sound == true) {
										  mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
										  mpplayer.stop();
									  } else {
										  if (Global_Data.sound_file.length() > 0) {
											  //Toast.makeText(NewOrderActivity.this, "sndfile:"+Global_Data.sound_file, Toast.LENGTH_SHORT).show();
											  //mpplayer = new MediaPlayer();
											  try {
												  if (mpplayer.isPlaying()) {
													  mpplayer.reset();
												  }
												  mpplayer = MediaPlayer.create(ReturnOrder1.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Audio/" + Global_Data.sound_file));
////                                    mp.start();
												  //mMediaPlayer.setDataSource(filename);
												  mpplayer.start();
												  mpplayer.prepare();
											  } catch (Exception e) {

											  }

										  } else {
											  mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
											  mpplayer.start();
										  }

									  }



									  Toast toast = Toast.makeText(ReturnOrder1.this, "Item add successfully", Toast.LENGTH_SHORT);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();

									  //loginDataBaseAdapter.insertOrderProducts("", "",  Global_Data.GLObalOrder_id, "", spnCategory.getSelectedItem().toString() , spnProduct.getText().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,editTextMRP.getText().toString(), price, "", "",Global_Data.order_retailer,discount_amount,Global_Data.GLOvel_ITEM_NUMBER,discount_type);//Reading all
								  } 
						     }
						     else
						     {



								 String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
								  Global_Data.variant_rr=editTextRP.getText().toString();
								  Global_Data.variant_mrp=editTextMRP.getText().toString();
								  Global_Data.order_qty=editTextQuantity.getText().toString();
								  String strAmount=String.valueOf(Global_Data.order_amount);
								  
								  if(Global_Data.GLOvel_GORDER_ID_RETURN.equalsIgnoreCase(""))
								  {
									  if(Global_Data.sales_btnstring.equalsIgnoreCase("Secondary Sales / Retail Sales"))
									  {
										  Global_Data.GLObalOrder_id_return = PINString;
										  Global_Data.GLOvel_GORDER_ID_RETURN = PINString;
									  }
									  else
									  {
										  Global_Data.GLObalOrder_id_return = PINString;
										  Global_Data.GLOvel_GORDER_ID_RETURN = PINString;
									  }

									  try
									  {
										  AppLocationManager appLocationManager = new AppLocationManager(ReturnOrder1.this);
										  Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
										  Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
										  PlayService_Location PlayServiceManager = new PlayService_Location(ReturnOrder1.this);

										  if(PlayServiceManager.checkPlayServices(ReturnOrder1.this))
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
									 
									  loginDataBaseAdapter.insertReturnOrders("", Global_Data.GLOvel_GORDER_ID_RETURN, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer,  Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "",Global_Data.order_retailer,Global_Data.order_state,Global_Data.order_city,Global_Data.sales_btnstring,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Glovel_BEAT_ID);
								  }
								 
								  //loginDataBaseAdapter.insertOrders("", "", Global_Data.order_retailer, "", Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "");
								  
								  //loginDataBaseAdapter.insertOrderProducts("", "", "0"+PINString, "", Global_Data.order_category , Global_Data.order_product , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", Global_Data.order_qty , Global_Data.variant_rr, Global_Data.variant_mrp, strAmount, "", "",Global_Data.order_retailer);//Reading all
								 

								  
								  List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID_RETURN(Global_Data.GLObalOrder_id_return,Global_Data.GLOvel_ITEM_NUMBER_RETURN);      
					   	          
								  if(contactsn.size() > 0)
								  {
									  //Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT).show();

									  Toast toast = Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();
								  }
								  else
								  {
									  if (Global_Data.app_sound == true) {
										  mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
										  mpplayer.stop();
									  } else {
										  if (Global_Data.sound_file.length() > 0) {
											  //Toast.makeText(NewOrderActivity.this, "sndfile:"+Global_Data.sound_file, Toast.LENGTH_SHORT).show();
											  //mpplayer = new MediaPlayer();
											  try {
												  if (mpplayer.isPlaying()) {
													  mpplayer.reset();
												  }
												  mpplayer = MediaPlayer.create(ReturnOrder1.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Audio/" + Global_Data.sound_file));
////                                    mp.start();
												  //mMediaPlayer.setDataSource(filename);
												  mpplayer.start();
												  mpplayer.prepare();
											  } catch (Exception e) {

											  }

										  } else {
											  mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
											  mpplayer.start();
										  }
									  }

									  Toast toast = Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG);
									  toast.setGravity(Gravity.CENTER, 0, 0);
									  toast.show();

									  //loginDataBaseAdapter.insertOrderProducts("", "",  Global_Data.GLOvel_GORDER_ID, "", spnCategory.getSelectedItem().toString() , spnProduct.getText().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,editTextMRP.getText().toString(), price, "", "",Global_Data.order_retailer,discount_amount,Global_Data.GLOvel_ITEM_NUMBER,discount_type);//Reading all
									  loginDataBaseAdapter.insertReturnOrderProducts(" ", " ",  Global_Data.GLOvel_GORDER_ID_RETURN, "", spnCategory.getSelectedItem().toString() , spnProduct.getSelectedItem().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,Global_Data.variant_mrp, price, "", "",Global_Data.order_retailer," ",Global_Data.item_code_return," ", item_name);//Reading all
								  } 
						     }
				   	          
							  
	//				   	         List<Local_Data> contacts4 = dbvoc.getRR();      
	//				   	          for (Local_Data cn : contacts4) {
	//				   	        	String str = ""+cn.getRR();
	//				   	        	
	//				   	        	System.out.println("Local Values:-"+str+","+Global_Data.local_pwd);
	//				   	        	Toast.makeText(NewOrderActivity.this, "Login:"+str,Toast.LENGTH_SHORT).show();
	//				   	        	                             }
//	       					Product p = new Product();
//							p.setCategory(spnCategory.getSelectedItem().toString());
//							p.setProductName(spnProduct.getText().toString());
//							p.setProductSpec(spnProduct.getText().toString());
//							
//							String str=spnProduct.getText().toString();
//													
//							p.setProductrp("" + editTextMRP.getText().toString());
//							p.setProductmrp("" + editTextMRP.getText().toString());
//							p.setProductQuantity("" + editTextQuantity.getText().toString());
//							p.setProductDeleiveryQuantity("" + editTextQuantity.getText().toString());
//							p.setProducttotalPrice("" + price);
//							p.setProductScheme(spnScheme.getSelectedItem().toString());
//							p.setCategoryID(categoryID);
//							p.setProductID(productID);
//							p.setSchemeID(schemeID);
//							Global_Data.productList.add(p);
	    					
						     check_product=0;
						     check_ProductSpec=0;
							 check = 0;
	    					 editTextQuantity.setText("");
	    					 editTextQuantity.setEnabled(false);
	    					 spnCategory.setSelection(adapter_state1.getPosition("Select Category"));
						     // spnProductSpec.setSelection(adapter_state3.getPosition("Select Variant"));
						      Product_Variant.setText("");
	    					 spnProduct.setSelection(adapter_state2.getPosition("Select Product"));
//	    					 spnScheme.setSelection(Discount_Adapter.getPosition("Select Scheme"));
//	    					 spnScheme.setEnabled(false);
	    					 editTextMRP.setText("");
	    					 txtPrice.setText("");
	    					 editTextRP.setText("");
	    					// spnProduct.setText("");
	    					 
//							spnCategory.setAdapter(adapter_state1); 
//	
//							listProduct.clear();
//							listProduct.add("Select Product");
//	
//							dataAdapterProduct.notifyDataSetChanged();
//							dataAdapterProduct
//									.setDropDownViewResource(android.R.layout.simple_spinner_item);
//							//spnProduct.setAdapter(adapter_state2);
//	
//							listProductSpec.clear();
//							listProductSpec.add("Select Variant");
//							dataAdapterProductSpec.notifyDataSetChanged();
//							dataAdapterProductSpec
//									.setDropDownViewResource(android.R.layout.simple_spinner_item);
//							spnProductSpec.setAdapter(adapter_state3);
//	
//							listScheme.clear();
//							listScheme.add("Select Scheme");
//	
//							dataAdapterScheme.notifyDataSetChanged();
//							dataAdapterScheme
//									.setDropDownViewResource(android.R.layout.simple_spinner_item);
//							spnScheme.setAdapter(dataAdapterScheme);
//	
//							editTextRP.setText("" + rp);
//							editTextMRP.setText("" + mrp);
//							txtPrice.setText("Total Price : ");
//							rp = 0.00f;
//							mrp = 0.00f;
//							totalprice = 0.00f;
//							deleiveryQuantity = 0;
//							editTextQuantity.setText("");
							//txtDeleiveryQuantity.setText("Delivery Quantity :");
							  return true;
						//}
					}
				
			        return true;
			    }
			    return false;
			}
		});

		buttonPreviewOrder = (Button) findViewById(R.id.buttonPreviewOrder);
		buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
		
		buttonPreviewOrder.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
					 b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
                    if(Global_Data.app_sound==false)
                    {
                        mpplayer.stop();
                    }
					//down event
			    	 b.setBackgroundColor(Color.parseColor("#910505"));
						// TODO Auto-generated method stub
				    	 if(Global_Data.GLOVEL_ORDER_REJECT_FLAG.equalsIgnoreCase("TRUE"))
						 {
							 Intent i=new Intent(ReturnOrder1.this, Status_Activity.class);
							 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
							 startActivity(i);
							 finish();
						 }
				    	 else
				    	 {
				    		 if (!Global_Data.GLOvel_GORDER_ID_RETURN.equalsIgnoreCase("")) {
									/*final Intent i = new Intent(getApplicationContext(),
											PreviewOrderActivity.class);*/
									final Intent i = new Intent(getApplicationContext(),
											ReturnOrder2.class);
									i.putParcelableArrayListExtra("productsList", Global_Data.productList);
									i.putExtra("new","new");
									SharedPreferences sp = ReturnOrder1.this
											.getSharedPreferences("SimpleLogic", 0);

									i.putExtra("retialer",
											"" + sp.getString("RetailerName", ""));
									//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									Global_Data.GLOVEL_LONG_DESC = ""; 
									 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
									 Global_Data.GLOVEL_ITEM_MRP = "";
									
									i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									ActivitySwitcher.animationOut(findViewById(R.id.containerNewOrder), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
										@Override
										public void onAnimationFinished() {
											startActivity(i);
											finish();
										}
									});
									//NewOrderFragment.this.startActivity(i);
								}
								
								else {
									//Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();
									
									Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
				    	 }
			        return true;
			    }
			    return false;
			}
		});
//		buttonPreviewOrder.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//
//				
//				if (productList.size()!=0) {
//					Intent i = new Intent(getApplicationContext(),
//							PreviewOrderActivity.class);
//					i.putParcelableArrayListExtra("productsList", productList);
//
//					SharedPreferences sp = NewOrderFragment.this
//							.getSharedPreferences("SimpleLogic", 0);
//
//					i.putExtra("retialer",
//							"" + sp.getString("RetailerName", ""));
//					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//					finish();
//					NewOrderFragment.this.startActivity(i);
//				}
//				
//				else {
//					//Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();
//					
//					Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//
//			}
//		});
//		editTextQuantity.setText("");
//
//		
//		editTextQuantity.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (editTextQuantity.getText().toString().length() != 0) {
//					quantity = Integer.parseInt(editTextQuantity.getText()
//							.toString());
//					totalprice = productprice * quantity;
//					Global_Data.order_amount=totalprice;
//					txtPrice.setText("Total Price : " + String.format("%.2f", totalprice));
//					//deleiveryQuantity = quantity;
////					txtDeleiveryQuantity.setText("Delivery Quantity : "
////							+ deleiveryQuantity);
//					if (scheme.equalsIgnoreCase("Select Scheme")) {
//
//						// Toast.makeText(parent.getContext(),
//						// "OnItemSelectedListener : " +
//						// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//						scheme = "Select Scheme";
//						schemeID=0;
//						if (editTextQuantity.getText().toString().length()!=0) {
//							quantity=Integer.parseInt(editTextQuantity.getText().toString());
//							deleiveryQuantity = quantity;
//							txtDeleiveryQuantity.setText("Delivery Quantity : "
//									+ deleiveryQuantity);
//						}
//	
//						/*LoadProductIDsTask loadProductIDsTask=new LoadProductIDsTask(NewOrderFragment.this);
//						loadProductIDsTask.execute();*/
//						//txtDeleiveryQuantity.setText("Delivery Quantity : ");
//
//					}
//					
//					else {
//						
//						schemeID=dbschemeID;
//						String [] aray=scheme.split("and");
//						int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
//						int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
//						if (editTextQuantity.getText().toString().length()!=0) {
//							quantity=Integer.parseInt(editTextQuantity.getText().toString());
//							int extra = quantity / buy;
//							deleiveryQuantity = extra*get + quantity;
//							txtDeleiveryQuantity.setText("Delivery Quantity : "
//									+ deleiveryQuantity);
//						}
//					}
//				}
//				return false;
//			}
//		});



		spnCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
									   int pos, long arg3) {
//				// TODO Auto-generated method stub



//				// Toast.makeText(parent.getContext(),
//				// "OnItemSelectedListener : " +
//				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//				editTextQuantity.setFocusableInTouchMode(false);
//				editTextQuantity.setEnabled(false);
//				txtPrice.setText("Total Price : ");
//
				Global_Data.GLOVEL_ITEM_MRP = "";

//				category = parent.getItemAtPosition(pos).toString();
				Log.d("Globel categary", Global_Data.GLOVEL_CATEGORY_SELECTION);

				check=check+1;
				if(check>1)
				{

					// editTextQuantity.setText("");

					if(!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))){
						txtPrice.setText("Total Price : "+"");
					}


//				if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
//				{
//					Log.d("Globel categary selection", "in");
//					spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
//
//
//				}
//				else
//				{
//                 if (Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
//                 {
					if (parent.getItemAtPosition(pos).toString()
							.equalsIgnoreCase("Select Category")) {

						categ_name = "";
						subcateg_name = "";

						results.clear();
						results.add("Select Product");
						adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
						adapter_state2.setDropDownViewResource(R.layout.spinner_item);
						spnProduct.setAdapter(adapter_state2);
						//spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

//						results2.clear();
//						results2.add("Select Variant");
//						adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//				        adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//						spnProductSpec.setAdapter(adapter_state3);

						results2.clear();

						ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReturnOrder1.this,
								android.R.layout.simple_spinner_dropdown_item,
								result_product);
						Product_Variant.setThreshold(1);// will start working from
						// first character
						Product_Variant.setAdapter(adapter);// setting the adapter
						// data into the
						// AutoCompleteTextView
						Product_Variant.setTextColor(Color.BLACK);
						Product_Variant.setText("");

						//Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(ReturnOrder1.this, "Please Select Category", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					}
					else
					{

						Global_Data.GLOVEL_CATEGORY_SELECTION = parent.getItemAtPosition(pos).toString();
						//Intent intent = new Intent(getApplicationContext(), Filter_List.class);
						Global_Data.GLOVEL_CATEGORY_NAME = parent.getItemAtPosition(pos).toString();

						List<Local_Data> contacts2 = dbvoc.HSS_DescriptionITEM_ID(parent.getItemAtPosition(pos).toString().trim());
						//results.add("Select Product");
						for (Local_Data cn : contacts2)
						{
							Global_Data.GLOVEL_CATEGORY_ID = cn.getCust_Code();


							//Global_Data.local_pwd = ""+cn.getPwd();

							//results.add(str_product);
							//System.out.println("Local Values:-"+Global_Data.local_user);
							//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
						}

						results.clear();
						//List<Local_Data> contacts22 = dbvoc.HSS_DescriptionITEM1_ID(Global_Data.GLOVEL_CATEGORY_ID);
						List<Local_Data> contacts22 = dbvoc.HSS_DescriptionITEM1_category_name(parent.getItemAtPosition(pos).toString().trim());
						results.add("Select Product");
						for (Local_Data cn : contacts22) {
							String str_product = ""+cn.getStateName();
							//Global_Data.local_pwd = ""+cn.getPwd();

							results.add(str_product);
							System.out.println("Local Values:-"+Global_Data.local_user);
							//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
						}

						adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
						adapter_state2.setDropDownViewResource(R.layout.spinner_item);
						spnProduct.setAdapter(adapter_state2);
						// spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

						if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name)){
							int spinnerPosition = adapter_state2.getPosition(subcateg_name);
							spnProduct.setSelection(spinnerPosition);
						}
						//startActivity(intent);
						//overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

					}
//                 }
//                 else
//                 {
//                	 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
//                 }


					//}
				}
//
//				else {
//
//					//categoryID = Integer.parseInt(categoriesMap.get(""+parent.getSelectedItemId()));
//					categoryID =dataCategories.get(pos-1).getId();
//					CategoriesSpinner = parent.getItemAtPosition(pos).toString();
//					LoadProductsAsyncTask loadProductsAsyncTask=new LoadProductsAsyncTask(NewOrderActivity.this);
//					loadProductsAsyncTask.execute();
//
//				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});


//		spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View arg1,
//					int pos, long arg3) {
//				// TODO Auto-generated method stub
//				editTextQuantity.setFocusableInTouchMode(false);
//				editTextQuantity.setEnabled(false);
//				txtPrice.setText("Total Price : ");
//		
//				productName = parent.getItemAtPosition(pos).toString();
//				
//				
//				if (parent.getItemAtPosition(pos).toString()
//						.equalsIgnoreCase("Select Product")) {
//					
//					listProductSpec.clear();
//					listProductSpec.add("Select Variant");
//					dataAdapterProductSpec.notifyDataSetChanged();
//					dataAdapterProductSpec
//							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//					spnProductSpec.setAdapter(adapter_state3);
//
//					listScheme.clear();
//					listScheme.add("Select Scheme");
//
//					dataAdapterScheme.notifyDataSetChanged();
//					dataAdapterScheme
//							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//					spnScheme.setAdapter(dataAdapterScheme);
//
//					/*editTextRP.setText("" + rp);
//					editTextMRP.setText("" + mrp);
//					txtPrice.setText("Total Price : ");
//					rp = 0.00f;
//					mrp = 0.00f;
//					totalprice = 0.00f;
//					deleiveryQuantity = 0;
//					editTextQuantity.setText("");
//					txtDeleiveryQuantity.setText("Delivery Quantity :");*/
//					
//					rp = 0.00f;
//					mrp = 0.00f;
//					productprice = rp;
//
//					editTextRP.setText("" + rp);
//					editTextMRP.setText("" + mrp);
//
//				}
//
//				else {
//					
//					//productID= Integer.parseInt(productsMap.get(""+parent.getSelectedItemId()));;
//					productID = dataProducts.get(pos-1).getProductID();
//					ProductSpinner =parent.getItemAtPosition(pos).toString();
//					LoadProductVarientsAsyncTask loadProductVarientsAsyncTask=new LoadProductVarientsAsyncTask(NewOrderActivity.this);
//					loadProductVarientsAsyncTask.execute();
//				}
//	    	}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});

//		spnProductSpec.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View arg1,
//					int pos, long arg3) {
//				// TODO Auto-generated method stub
//				// Toast.makeText(parent.getContext(),
//				// "OnItemSelectedListener : " +
//				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//				editTextQuantity.setText("");
//				txtPrice.setText("Total Price : "+"");
//				productSpec = parent.getItemAtPosition(pos).toString();
//
//				if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select Variant")) {
//					listScheme.clear();
//					listScheme.add("Select Scheme");
//
//					dataAdapterScheme.notifyDataSetChanged();
//					dataAdapterScheme.setDropDownViewResource(R.layout.spinner_item);
//					spnScheme.setAdapter(dataAdapterScheme);
//					rp = 0.00f;
//					mrp = 0.00f;
//					productprice = rp;
//
//					editTextRP.setText("" + rp);
//					editTextMRP.setText("" + mrp);
//					editTextQuantity.setFocusableInTouchMode(false);
//					editTextQuantity.setEnabled(false);
//					//txtPrice.setText("Total Price : ");
//				}
//				else {
//					editTextQuantity.setFocusableInTouchMode(true);
//					editTextQuantity.setEnabled(true);
//
//					 List<Local_Data> cont = dbvoc.getProductByCat(productSpec);
//			         //results2.add("Select Variant");
//			          for (Local_Data cn1 : cont) {
//			        	String str_var = ""+cn1.getStateName();
//			            String str_var1 = ""+cn1.getCategory();
//			            String str_var2 = ""+cn1.get_Description();
//			            String str_var3 = ""+cn1.get_Claims();
//			            Global_Data.amnt= ""+cn1.get_Description();
//			            Global_Data.amnt1= ""+cn1.get_Claims();
//
//			            editTextRP.setText(str_var);
//			            editTextMRP.setText(str_var1);
//
////			        	String scheme_str = ("Buy "+Global_Data.amnt+" and Get "+Global_Data.amnt1+" Free");
////
////
////			              Discount_list.clear();
////			              Discount_list.add("Select Scheme");
////						  Discount_list.add(scheme_str);
//////						  Discount_list.add("Percentage");
////
////						  Discount_Adapter = new ArrayAdapter<String>(NewOrderActivity.this,android.R.layout.simple_spinner_item, Discount_list);
////						  Discount_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
////						  spnScheme.setAdapter(Discount_Adapter);
//
//			            //Global_Data.local_pwd = ""+cn.getPwd();
//
//			        	//results2.add(str_variant);
//			        	//System.out.println("Local Values:-"+Global_Data.local_user);
//			        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//
//			                                      }
//
////					LoadProductPriceAsyncTask loadProductPriceAsyncTask=new LoadProductPriceAsyncTask(NewOrderActivity.this);
////					loadProductPriceAsyncTask.execute();
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		editTextQuantity.addTextChangedListener(new TextWatcher() {
//		
//			   @Override
//			   public void afterTextChanged(Editable s) {}
//		
//			   @Override    
//			   public void beforeTextChanged(CharSequence s, int start,
//			     int count, int after) {
//			   }
//		
//			   @Override    
//			   public void onTextChanged(CharSequence s, int start,
//			     int before, int count) {
//			      if(s.length() != 0)
//			      {
//			    	  
//			    	  
//			    	  
////			    	  
////			    	  if(!txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase("") && !txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase(null) && !txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase("null"))
////			    	  {
////			    		  
////			    	  
////				    	  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
////				    	  {
////				    		  int final_mrp = (Integer.parseInt(editTextMRP.getText().toString()))*(Integer.parseInt(String.valueOf(s)));
////				    		  //int final_discount = (Integer.parseInt(txtDeleiveryQuantity1.getText().toString()))*(Integer.parseInt(String.valueOf(s)));
////				    		  int final_discount = Integer.parseInt(String.valueOf(s));
////				    		 // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
////				    		  int price1 = (final_mrp)-(final_discount);
////				    		  txtPrice.setText("Total Price : "+price1);
////				    		  price = String.valueOf(price1);
////				    	  }
////				    	  else
////			    		  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
////				    	  {
////			    			   Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(String.valueOf(s)));
////			    			  // Float final_discount = (Float.valueOf((txtDeleiveryQuantity1.getText().toString())))*(Float.valueOf(String.valueOf(s)));
////			    			   int final_discount = Integer.parseInt(String.valueOf(s));
////			//		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
////			//		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);
////			    			   
////			    			   Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
////			    			   Float price1 = (final_mrp)-(per_value);
////			
////				    		  txtPrice.setText("Total Price : "+price1);
////				    		  price = String.valueOf(price1);
////				    	  }	  
////			    	  
////			    	  }
////			    	  else
////			    	  {
////			    		  Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(String.valueOf(s)));
////			    		  txtPrice.setText("Total Price : "+final_mrp);
////			    		  price = String.valueOf(final_mrp);
////			    	  }
////			    		  
//			    	  
//			      }
//			        //Field2.setText("");
//			   }
//			  });
	
    //editTextQuantity.setText("");

//		editTextQuantity.addTextChangedListener(new TextWatcher() {
//			   @Override
//			   public void afterTextChanged(Editable s) {}
//
//			   @Override
//			   public void beforeTextChanged(CharSequence s, int start,
//			     int count, int after) {
//			   }
//
//			   @Override
//			   public void onTextChanged(CharSequence s, int start,
//			     int before, int count) {
//			      if(s.length() != 0)
//			      {
//
//			    if (editTextQuantity.getText().toString().length() != 0) {
//					quantity = Integer.parseInt(editTextQuantity.getText()
//							.toString());
//
//					if(quantity > Integer.valueOf(Global_Data.amnt));
//					{
//					// String scheme_str = ("Buy "+Global_Data.amnt+" and Get "+Global_Data.amnt1+" Free");
//
//
//		              Discount_list.clear();
//		              Discount_list.add("Select Scheme");
//					 // Discount_list.add(scheme_str);
////					  Discount_list.add("Percentage");
//
//					  Discount_Adapter = new ArrayAdapter<String>(ReturnOrder1.this,R.layout.spinner_item, Discount_list);
//					  Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
//					  spnScheme.setAdapter(Discount_Adapter);
//					}
////					else{
////						Toast.makeText(NewOrderActivity.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
////					}
////					productprice=Float.valueOf(editTextRP.getText().toString());
////
////					totalprice = productprice * quantity;
////
////					Global_Data.order_amount=totalprice;
////					//Toast.makeText(NewOrderActivity.this, ""+Global_Data.order_amount ,Toast.LENGTH_SHORT).show();
////
////					int aaa=Integer.parseInt(Global_Data.amnt);
////
////					totalprc_scheme= ((quantity/productprice)*aaa);
////
////					totalprice1=(quantity+((quantity/productprice)*aaa));
////
//////					String vvv=String.valueOf(totalprice1);
//////
//////					int delivery= (quantity+Integer.valueOf(vvv));
////
////
////					txtPrice.setText("Total Price : " + String.format("%.2f", totalprc_scheme));
////
////					txtDeleiveryQuantity.setText("Delivery Quantity : " + String.format("%.2f", totalprice1));
//
//
//					//deleiveryQuantity = quantity;
////					txtDeleiveryQuantity.setText("Delivery Quantity : "
////							+ deleiveryQuantity);
////					if (scheme.equalsIgnoreCase("Select Scheme")) {
////
////						// Toast.makeText(parent.getContext(),
////						// "OnItemSelectedListener : " +
////						// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
////						scheme = "Select Scheme";
////						schemeID=0;
////						if (editTextQuantity.getText().toString().length()!=0) {
////							quantity=Integer.parseInt(editTextQuantity.getText().toString());
////							deleiveryQuantity = quantity;
////							txtDeleiveryQuantity.setText("Delivery Quantity : "
////									+ deleiveryQuantity);
////						}
////
////						/*LoadProductIDsTask loadProductIDsTask=new LoadProductIDsTask(NewOrderFragment.this);
////						loadProductIDsTask.execute();*/
////						//txtDeleiveryQuantity.setText("Delivery Quantity : ");
////
////					}
////
////					else {
////
////						schemeID=dbschemeID;
////						String [] aray=scheme.split("and");
////						int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
////						int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
////						if (editTextQuantity.getText().toString().length()!=0) {
////							quantity=Integer.parseInt(editTextQuantity.getText().toString());
////							int extra = quantity / buy;
////							deleiveryQuantity = extra*get + quantity;
////							txtDeleiveryQuantity.setText("Delivery Quantity : "
////									+ deleiveryQuantity);
////						}
////					}
//				}}
//
//
//		        //Field2.setText("");
//		   }
//		  });

		editTextQuantity.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {}

			@Override
			public void beforeTextChanged(CharSequence s, int start,
										  int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start,
									  int before, int count) {
				if(s.length() != 0)
				{

					try
					{
						if (editTextQuantity.getText().toString().length() != 0) {

							if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")  && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
							{
								double final_mrp = (Double.valueOf(editTextMRP.getText().toString()))*(Double.valueOf(editTextQuantity.getText().toString().trim()));
								txtPrice.setText("Total Price : "+final_mrp);
								price = String.valueOf(final_mrp);

								// txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
							}
							else
							{
								if(!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase(null) && !editTextMRP.getText().toString().equalsIgnoreCase("null")  && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
								{
									// Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
									// txtPrice.setText("Total Price : "+final_mrp);
									// price = String.valueOf(final_mrp);
									//txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
								}

							}
//
						}

					}catch(Exception ex){ex.printStackTrace();}
				}


				//Field2.setText("");
			}
		});

		
		//editTextQuantity.setFocusable(false) ;
		
		
//        
//		txtDeleiveryQuantity1.addTextChangedListener(new TextWatcher() {
//
//			   @Override
//			   public void afterTextChanged(Editable s) {}
//
//			   @Override    
//			   public void beforeTextChanged(CharSequence s, int start,
//			     int count, int after) {
//			   }
//
//			   @Override    
//			   public void onTextChanged(CharSequence s, int start,
//			     int before, int count) {
//			      if(s.length() != 0)
//			      {
//			    	  if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null"))
//			    	  {
//			    		  
//			    		 
//			    			  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
//					    	  {
//					    		  int final_mrp = (Integer.parseInt(editTextMRP.getText().toString()))*(Integer.parseInt(editTextQuantity.getText().toString()));
//					    		 // int final_discount = (Integer.parseInt(String.valueOf(s)))*(Integer.parseInt(editTextQuantity.getText().toString()));
//					    		  int final_discount = Integer.parseInt(String.valueOf(s));
//					    		  // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
//					    		  int price1 = (final_mrp)-(final_discount);
//					    		  txtPrice.setText("Total Price : "+price1);
//					    		  price = String.valueOf(price1);
//					    	  }
//					    	  else
//				    		  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
//					    	  {
//				    			  if(Integer.parseInt(String.valueOf(s)) >=100)
//					    		  {
//				   	        		   Toast toast = Toast.makeText(getApplicationContext(),"Discount not more than 99",1000);
//				   	        		 toast.setGravity(Gravity.CENTER, 0, 0);
//									   toast.show();
//
//					    		  }
//				    			  else
//				    			  {
//				    				  Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(editTextQuantity.getText().toString()));
//					    			   int final_discount = Integer.parseInt(String.valueOf(s));
//					    			   // Float final_discount = (Float.valueOf((String.valueOf(s))))*(Float.valueOf(editTextQuantity.getText().toString()));
//			//		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
//			//		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);
//					    			   
//					    			   Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
//					    			   Float price1 = (final_mrp)-(per_value);
//			
//						    		  txtPrice.setText("Total Price : "+price1);
//						    		  price = String.valueOf(price1);
//				    			  }
//				    			   
//					    	  }	 
//			    		  
//				    	   
//			    	  
//			    	  } 
//			    		  
//			    	  
//			      }
//			      
//			    	  
//			        //Field2.setText("");
//			   }
//			  });

		spnScheme.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
									   int pos, long arg3) {
				// TODO Auto-generated method stub
				productScheme = parent.getItemAtPosition(pos).toString();
				//editTextQuantity.setText(" ");
				//txtPrice.setText("Total Price : "+"");
				//txtPrice.setText("Total Price : "+"");
				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Select Scheme")) {

					//txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);

//					if(editTextQuantity.getText().toString().equalsIgnoreCase("") || editTextQuantity.getText().toString().equalsIgnoreCase("0"))
//					{
//						txtPrice.setText("Total Price : "+"");
//						price = String.valueOf("");
//					}
					if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")  && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
					{
						long final_mrp = (Long.valueOf(editTextMRP.getText().toString()))*(Long.valueOf(editTextQuantity.getText().toString().trim()));
						txtPrice.setText("Total Price : "+final_mrp);
						price = String.valueOf(final_mrp);

						// txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
					}
					else
					{
						if(!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase(null) && !editTextMRP.getText().toString().equalsIgnoreCase("null")  && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
						{
//							Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
//							txtPrice.setText("Total Price : "+final_mrp);
//							price = String.valueOf(final_mrp);
							//txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
						}

					}

				}
				else{

					productprice=Float.valueOf(editTextMRP.getText().toString());

					totalprice = productprice * quantity;

					Global_Data.order_amount=totalprice;
					//Toast.makeText(NewOrderActivity.this, ""+Global_Data.order_amount ,Toast.LENGTH_SHORT).show();

					int aaa=Integer.parseInt(Global_Data.amnt);

					totalprc_scheme= ((quantity/productprice)*aaa);

					totalprice1=(quantity+((quantity/productprice)*aaa));

//					String vvv=String.valueOf(totalprice1);
//
//					int delivery= (quantity+Integer.valueOf(vvv));


					txtPrice.setText("Total Price : " + String.format("%.2f", totalprc_scheme));

					txtDeleiveryQuantity.setText("Delivery Quantity : " + String.format("%.2f", totalprice1));


				}
//				if(parent.getItemAtPosition(pos).toString()
//						.equalsIgnoreCase("Rupees"))
//				{
//					txtDeleiveryQuantity1.setText("");
//					txtDeleiveryQuantity1.setVisibility(View.VISIBLE);
//					txtDeleiveryQuantity1.setHint("Rs.");
//					txtPrice.setText("Total Price : ");
//				}
//				else
//				if(parent.getItemAtPosition(pos).toString()
//						.equalsIgnoreCase("Percentage"))
//				{
//					txtDeleiveryQuantity1.setText("");
//					txtDeleiveryQuantity1.setVisibility(View.VISIBLE);
//					txtDeleiveryQuantity1.setHint("%");
//					txtPrice.setText("Total Price : ");
//				}

//				else {
//					schemeID=dbschemeID;
//					scheme=parent.getSelectedItem().toString();
//
//					String [] aray=scheme.split("and");
//					int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
//					int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
//					if (editTextQuantity.getText().toString().length()!=0) {
//						quantity=Integer.parseInt(editTextQuantity.getText().toString());
//						int extra = quantity / buy;
//						deleiveryQuantity = extra*get + quantity;
//						txtDeleiveryQuantity.setText("Delivery Quantity : "
//								+ deleiveryQuantity);
//    				}
//    			}


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
            if(Global_Data.app_sound==false)
            {
                mpplayer.stop();
            }
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
        if(Global_Data.app_sound==false)
        {
            mpplayer.stop();
        }
		 Global_Data.GLOVEL_LONG_DESC = ""; 
		 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
		 Global_Data.GLOVEL_ITEM_MRP = "";
		// Global_Data.productList.clear();

		if (Global_Data.PREVIOUS_ORDER_BACK_FLAG_REURN.equalsIgnoreCase("TRUE"))
		 {
			 Intent i=new Intent(ReturnOrder1.this, ReturnOrder2.class);
			 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			 startActivity(i);
			 finish();
		 }
		 else
		 {
			 if (!Global_Data.GLOvel_GORDER_ID_RETURN.equalsIgnoreCase("")) {
					
					AlertDialog alertDialog = new AlertDialog.Builder(ReturnOrder1.this).create(); //Read Update
				    alertDialog.setTitle("Warning");
				    alertDialog.setMessage(" Are you sure you want to back without order sync.?"); 
				    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(!Global_Data.GLObalOrder_id_return.equalsIgnoreCase(""))
							{
								//Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG).show();

								Toast toast = Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
							Intent i=new Intent(ReturnOrder1.this, Order.class);
							 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
							 startActivity(i);
							 finish();
						}
					});

		           alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							  dialog.cancel();
						}
					});
				  

				    alertDialog.show(); 
				}
				else {
					
					if (Global_Data.PREVIOUS_ORDER_BACK_FLAG_REURN.equalsIgnoreCase("TRUE")) 
					{
						if(!Global_Data.GLObalOrder_id_return.equalsIgnoreCase(""))
						{
							Toast toast = Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
						Intent i=new Intent(ReturnOrder1.this, ReturnOrder2.class);
						 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						 startActivity(i);
						 finish();
					}
					else
					{
						if(!Global_Data.GLObalOrder_id_return.equalsIgnoreCase(""))
						{
							//Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG).show();
							Toast toast = Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();

						}
						Intent i=new Intent(ReturnOrder1.this, Order.class);
						 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						 startActivity(i);
						 finish();
					}
					 
				}
		 }
		 
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stubs
		super.onResume();
		buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
		buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
	}
	
	public class LoadProductsAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		private Context context;
		private boolean webServiceResponse;

		public LoadProductsAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading Products");
			this.dialog.show();
			listProduct.clear();
			listProduct.add("Select Product");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				
				Product product1 = new Product("AP DEO Stick 15", "TITAN AFD", "100ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","178.50", 2, 1,1);
				Product product2 = new Product("AP DEO Stick 15", "REBEL AFD", "190ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","188.50", 2, 2,1);
				Product product3 = new Product("AP DEO Stick 15", "ADORE AFD", "190ML","", "productDeleiveryQuantity","productScheme", "productrp", "220.00","168.50", 2, 3,1);
				Product product4 = new Product("AP DEO Stick 15", "OASIS AFD", "100ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","178.50", 2, 4,1);
				Product product5 = new Product("AP DEO Stick 15", "VIVA APD", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","138.50", 2, 5,1);
				Product product6 = new Product("AP DEO Stick 15", "ALEXA AFD", "180ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","148.50", 2, 6,1);
				
				Product product7 = new Product("AP Deodorants", "MANGO 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","168.50", 1, 7,1);
				Product product8= new Product("AP Deodorants", "ICON APD", "100ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","158.50", 1, 8,1);
				Product product9 = new Product("AP Deodorants", "ATLAS AFD", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","158.50", 1, 9,1);
				Product product10 = new Product("AP Deodorants", "AP 15 RHYTHM", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","128.50", 1, 10,1);
				Product product11 = new Product("AP Deodorants", "AP 15 VIVA", "130ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","138.50", 1, 11,1);
				Product product12 = new Product("AP Deodorants", "AP 15 ICON", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","148.50", 1, 12,1);
				Product product13 = new Product("AP Deodorants", "AP 15 ODYSSEY", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","158.50", 1, 13,1);
				Product product14 = new Product("AP Deodorants", "CM 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","178.50", 1, 14,1);
				
				Product product15 = new Product("Hand Sanitizer 30", "NATURAL 30", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","188.50", 3, 15,1);
				Product product16 = new Product("Hand Sanitizer 30", "ORANGE 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","148.50", 3, 16,1);
				Product product17 = new Product("Hand Sanitizer 30", "STRAWBERRY 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","158.50", 3, 17,1);
				Product product18 = new Product("Hand Sanitizer 30", "STRAWBEERRY 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","158.50", 3, 18,1);
				Product product19 = new Product("Hand Sanitizer 30", "GA 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","175.50", 3, 19,1);
				
				Product product20 = new Product("Junior Hand Sanitizer 30", "ORNAGE 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","175.50", 4, 20,1);
				Product product21 = new Product("Junior Hand Sanitizer 30", "MANGO 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","173.50", 4, 21,1);
				Product product22 = new Product("Junior Hand Sanitizer 30", "CV 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","176.50", 4, 22,1);
				Product product23 = new Product("Junior Hand Sanitizer 30", "TB 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","175.50", 4, 23,1);
				 
				if (!dataProducts.isEmpty())
					dataProducts.clear();
					
					
				dataProducts.add(product1);
				dataProducts.add(product2);
				dataProducts.add(product3);
				dataProducts.add(product4);
				dataProducts.add(product5);
				dataProducts.add(product6);
				dataProducts.add(product7);
				dataProducts.add(product8);
				dataProducts.add(product9);
				dataProducts.add(product10);
				dataProducts.add(product11);
				dataProducts.add(product12);
				dataProducts.add(product13);
				dataProducts.add(product14);
				dataProducts.add(product15);
				dataProducts.add(product16);
				dataProducts.add(product17);
				dataProducts.add(product18);
				dataProducts.add(product19);
				dataProducts.add(product20);
				dataProducts.add(product21);
				dataProducts.add(product22);
				dataProducts.add(product23);
				
				
				//dataProducts=(ArrayList<DatabaseProductModel>) myDbHelper.loadProducts(categoryID);
				//productsMap =new HashMap<String, String>();
				/*int j=1;
					for (Iterator iterator = dataProducts.iterator(); iterator.hasNext();) {
						DatabaseProductModel databaseModel = (DatabaseProductModel) iterator.next();
						//Log.e("DATA", ""+databaseModel);
						listProduct.add(databaseModel.getProductDesc());
						productsMap.put(""+j, ""+databaseModel.getId());
						j++;
					}*/
				
				
				for (int i = 0; i < dataProducts.size(); i++) {
					
					if (categoryID == 1 && dataProducts.get(i).getCategory().equalsIgnoreCase(CategoriesSpinner)){
						listProduct.add(dataProducts.get(i).getProductName());
					} else if (categoryID == 2 && dataProducts.get(i).getCategory().equalsIgnoreCase(CategoriesSpinner)){
						listProduct.add(dataProducts.get(i).getProductName());
					}else if (categoryID == 3 && dataProducts.get(i).getCategory().equalsIgnoreCase(CategoriesSpinner)){
						listProduct.add(dataProducts.get(i).getProductName());
					}else if (categoryID == 4 && dataProducts.get(i).getCategory().equalsIgnoreCase(CategoriesSpinner)){
						listProduct.add(dataProducts.get(i).getProductName());
					}
					
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
				
			}
			
	     	dataAdapterProduct.notifyDataSetChanged();
	      
	      	//spnProduct.setAdapter(adapter_state2);
			
			
		}
	}
	
	
	public class LoadProductVarientsAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;
		ArrayList<Products_Mine> productVarients = new ArrayList<Products_Mine>();

		public LoadProductVarientsAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading Variants");
			this.dialog.show();
			listProductSpec.clear();
			listProductSpec.add("Select Variant");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				
				 //productVarients=(ArrayList<String>) myDbHelper.loadVarients(productID);
				
				
				Products_Mine products_Mine1 = new Products_Mine("50 GM", 1);
				Products_Mine products_Mine2 = new Products_Mine("150 ML", 2);
				Products_Mine products_Mine3 = new Products_Mine("250 ML", 3);
				/*Products_Mine products_Mine4 = new Products_Mine("30 ML", 4);
				Products_Mine products_Mine5 = new Products_Mine("250 ML", 5);
				Products_Mine products_Mine6 = new Products_Mine("(70+28) GM", 6);
				Products_Mine products_Mine7 = new Products_Mine("300 ML", 7);
				Products_Mine products_Mine8 = new Products_Mine("150 ML", 8);*/
				
				if (!productVarients.isEmpty())
					productVarients.clear();
				
				productVarients.add(products_Mine1);
				productVarients.add(products_Mine2);
				productVarients.add(products_Mine3);
				/*productVarients.add(products_Mine4);
				productVarients.add(products_Mine5);
				productVarients.add(products_Mine6);
				productVarients.add(products_Mine7);
				productVarients.add(products_Mine8);*/
				
				
				 
				/*for (int i = 0; i < dataProducts.size(); i++) {
					
					for(int j = 0 ; j < productVarients.size();j++){
						
						
						
						if (ProductSpinner.equalsIgnoreCase(dataProducts.get(i).getCategory())){
							listProductSpec.add(productVarients.get(i).getProductName());
						}
						
						
					if (categoryID == 1 && dataProducts.get(i).getCategory().equalsIgnoreCase(ProductSpinner) && productID ==1){
						
					} else if (categoryID == 2 && dataProducts.get(i).getCategory().equalsIgnoreCase(ProductSpinner)  && productID ==2){
						listProductSpec.add(productVarients.get(i).getProductName());
					}else if (categoryID == 3 && dataProducts.get(i).getCategory().equalsIgnoreCase(ProductSpinner)  && productID ==3){
						listProductSpec.add(productVarients.get(i).getProductName());
					}else if (categoryID == 4 && dataProducts.get(i).getCategory().equalsIgnoreCase(ProductSpinner)  && productID ==4){
						listProductSpec.add(productVarients.get(i).getProductName());
					}
				}
		}
			*/	
				
				for(int j = 0 ; j < productVarients.size();j++){
					
						listProductSpec.add(productVarients.get(j).getProductName());
					}
				 
				 
				
				/* for (Iterator iterator = productVarients.iterator(); iterator
						.hasNext();) {
					listProductSpec.add((String) iterator.next());
					
				}*/
				
				
				
				
				
//				dataVarients=(ArrayList<DatabaseModel>) myDbHelper.loadVarients(spnCategory.getSelectedItem().toString(),spnProduct.getSelectedItem().toString());
				//listProductSpec=productVarients;
					
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return null;
		}  

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
				
			}
			
			dataAdapterProductSpec.notifyDataSetChanged();
	     	
	      
	      	//spnProductSpec.setAdapter(adapter_state3);
			
			
		}
	}
	
	
	public class LoadProductPriceAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		private List<Integer> schemeId;
		DatabaseProductModel databaseModel ;
	

		public LoadProductPriceAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
			schemeId=new ArrayList<Integer>();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading details");
			this.dialog.show();
			/*listProduct.clear();
			listProduct.add("Select Product");*/
			
			listScheme.clear();
			listScheme.add("Select Scheme");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				//databaseModel= myDbHelper.loadProductsPrices(categoryID,productID,spnProductSpec.getSelectedItem().toString());
				
					Scheme scheme = new Scheme(1, "Buy 6 and Get 2 Free", "S001", "Y");
					
					if(!dataScheme.isEmpty())
						dataScheme.clear();
					
					dataScheme.add(scheme);	
					
				for (int i = 0; i < dataScheme.size(); i++) {
					
					if(dataProducts.get(i).getSchemeID() == dataScheme.get(i).getScheme_id()){
						
						listScheme.add(dataScheme.get(i).getScheme_desc());
						
						rp = Float.parseFloat(dataProducts.get(i).getProducttotalPrice());
						mrp =Float.parseFloat(dataProducts.get(i).getProductmrp());
						productprice = rp;
						dbschemeID=dataProducts.get(i).getSchemeID();
					}
					
				}
					
						
						
						
						//listScheme.add("Select Scheme");
						
						//listScheme.add(myDbHelper.loadSchemes(databaseModel.getSchemeId()));
						
						
						
					
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
				
			}
			
			/*for (Iterator iterator = schemeId.iterator(); iterator.hasNext();) {
				int id = ((Integer) iterator.next()).intValue();
				if (id==1) {
					
				}
				
			}*/
			
			editTextRP.setText("" + rp);
			editTextMRP.setText("" + mrp);
			if (editTextQuantity.getText().toString().length()!=0) {
	
				quantity=Integer.parseInt(editTextQuantity.getText().toString());
				deleiveryQuantity = quantity;
				totalprice=quantity*rp;
				//txtDeleiveryQuantity.setText("Delivery Quantity : "
				//		+ deleiveryQuantity);
				txtPrice.setText("Total Price : " + String.format("%.2f", totalprice));
				
    		}
	     	dataAdapterScheme.notifyDataSetChanged();
	      	spnScheme.setAdapter(dataAdapterScheme);
    	}
	}

	public class LoadProductIDsTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		//private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		private Context context;
		private List<Integer> schemeId;
		DatabaseProductModel databaseModel ;
	

		public LoadProductIDsTask(Activity activity) {
			this.activity = activity;
			context=activity;
			//dialog = new ProgressDialog(context);
			schemeId=new ArrayList<Integer>();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			this.dialog.setMessage("Loading IDs");
//			this.dialog.show();
			/*listProduct.clear();
			listProduct.add("Select Product");*/
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				/*databaseModel= myDbHelper.loadProductsPrices(spnCategory.getSelectedItem().toString(),
						                         spnProduct.getSelectedItem().toString(),
						                         spnProductSpec.getSelectedItem().toString());*/
					
			/*	Log.e("DATA", "After categoryID : "+databaseModel.getCategoryId());
				Log.e("DATA", "After productID : "+databaseModel.getId());
				Log.e("DATA", "After schemeID : "+databaseModel.getSchemeId());*/
						
						//schemeId.add(Integer.valueOf(databaseModel.getSchemeId()));
						//listScheme.add(databaseModel.getSchemeId());
					
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			/*if (dialog.isShowing()) {
				dialog.dismiss();
				
			}*/
			
			productID=databaseModel.getId();
			
			categoryID=databaseModel.getCategoryId();
			
			schemeID=databaseModel.getSchemeId();
		
		}
	}
	
//	public void getListViewnew(final String value)
//	{
//		 final Dialog dialognew = new Dialog(NewOrderActivity.this);
//	        dialognew.setCancelable(false);
//	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
//	        //tell the Dialog to use the dialog.xml as it's layout description
//	        dialognew.setContentView(R.layout.update_dialog);
//
//	        final EditText userInput = (EditText) dialognew
//	                .findViewById(R.id.update_textdialog);
//	        
//	        final TextView headertext = (TextView) dialognew
//	                .findViewById(R.id.item_description);
//	         headertext.setText("");
//	         headertext.setText("ITEM DESCRIPTION");
//
//	                final Button Submit = (Button) dialognew
//	                        .findViewById(R.id.update_textdialogclick);
//	                
//	                final Button update_cancel = (Button) dialognew
//	                        .findViewById(R.id.update_cancel);
//	                
//	                update_cancel.setVisibility(View.GONE);
//
//	        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value))
//	        {
//	            userInput.setText(value);
//	        }
//
////	        final Button cancel = (Button) dialognew
////	                .findViewById(R.id.cancel);
//
//	        Submit.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//
////	                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(userInput.getText().toString()))
////	                {
////	                	spnProduct.setText(userInput.getText().toString());
////	                }
//	            		
//	            	
//	                   // dialognew.dismiss();
//
//
//	            }
//	        });
//
////	        cancel.setOnClickListener(new View.OnClickListener() {
////	            @Override
////	            public void onClick(View v) {
////	                dialognew.dismiss();
//	//
//	//
////	            }
////	        });
//
//	        //dialognew.show();
//	}

//	@Override
//	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//
//		Spinner spinner = (Spinner) arg0;
//
//		if (spinner.getId() == R.id.spnCategory) {
//			if (arg0.getItemAtPosition(arg2).toString()
//					.equalsIgnoreCase("Select Category")) {
//
//				results.clear();
//				results.add("Select Product");
//				results2.clear();
//				results2.add("Select Variant");
//
//			    adapter_state2 = new ArrayAdapter<String>(this,
//			    		R.layout.spinner_item, results);
//				adapter_state2.setDropDownViewResource(R.layout.spinner_item);
//				spnProduct.setAdapter(adapter_state2);
//				spnProduct.setOnItemSelectedListener(this);
//
////				list_cities.add("");
////				list_cities.clear();
////				// String []customer_array = {"Apple", "Banana", "Cherry",
////				// "Date", "Grape", "Kiwi", "Mango", "Pear"};
////				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////						android.R.layout.simple_spinner_dropdown_item,
////						list_cities);
////				autoCompleteTextView1.setThreshold(1);// will start working from
////														// first character
////				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
////															// data into the
////															// AutoCompleteTextView
////				autoCompleteTextView1.setTextColor(Color.BLACK);
////				autoCompleteTextView1.setText("");
//
//				// adapter_state3 = new
//				// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
//				// results2);
//				// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//				// retail_spinner.setAdapter(adapter_state3);
//				// retail_spinner.setOnItemSelectedListener(this);
//			} else {
//
//				String items = spnCategory.getSelectedItem().toString();
//				String C_ID = "";
//				Log.i("Selected item : ", items);
//
//				// List<Local_Data> contacts =
//				// dbvoc.getBeats_CITYID(state_spinner.getSelectedItem().toString());
//				// for (Local_Data cn : contacts)
//				// {
//				//
//				// C_ID = cn.getStateName();
//				// //CAT_ID = cn.getStateName();
//				// //F_CITY_ID = cn.getStateName();
//				// //F_BEAT_ID = cn.get_category_id();
//				// }
//
//				if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(items)) {
////					results.clear();
////					results.add("Select Product");
//
//					results2.clear();
//					results2.add("Select Variant");
//					List<Local_Data> contacts2 = dbvoc.getProductByCat(items);
//					for (Local_Data cn : contacts2) {
//
//						results.add(cn.getStateName());
//						Global_Data.pname=cn.getProduct_nm();
//						//Toast.makeText(ReturnOrder1.this,"val:"+Global_Data.pname,Toast.LENGTH_SHORT).show();
//
//						Toast toast = Toast.makeText(ReturnOrder1.this,"val:"+Global_Data.pname,Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
//					adapter_state2 = new ArrayAdapter<String>(ReturnOrder1.this,
//							R.layout.spinner_item, results);
//					adapter_state2
//							.setDropDownViewResource(R.layout.spinner_item);
//					spnProduct.setAdapter(adapter_state2);
//
////					adapter_state1 = new ArrayAdapter<String>(NewOrderActivity.this,
////							android.R.layout.simple_spinner_item, results_beat);
////					adapter_state1
////							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////					spnProductSpec.setAdapter(adapter_state1);
//
//				}
//
////				if (!Global_Data.GLOvel_CITY_n.equalsIgnoreCase("")) {
////					spnProduct.setSelection(adapter_state2
////							.getPosition(Global_Data.GLOvel_CITY_n
////									.toUpperCase()));
////				} else if (!Global_Data.GLOvel_CITY.equalsIgnoreCase("")) {
////					spnProductSpec
////							.setSelection(adapter_state2
////									.getPosition(Global_Data.GLOvel_CITY
////											.toUpperCase()));
////				}
//			}
//		}
//
//
////
////		if (spinner.getId() == R.id.cust_city) {
////			if (arg0.getItemAtPosition(arg2).toString()
////					.equalsIgnoreCase("Select City")) {
////				results_beat.clear();
////				results_beat.add("Select Beat");
////				// /results2.clear();
////				// results2.add("Select Customer");
////				adapter_beat = new ArrayAdapter<String>(this,
////						android.R.layout.simple_spinner_item, results_beat);
////				adapter_beat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////				beat_spinner.setAdapter(adapter_beat);
////				beat_spinner.setOnItemSelectedListener(this);
////
////				list_cities.add("");
////				list_cities.clear();
////				// String []customer_array = {"Apple", "Banana", "Cherry",
////				// "Date", "Grape", "Kiwi", "Mango", "Pear"};
////				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////						android.R.layout.simple_spinner_dropdown_item,
////						list_cities);
////				autoCompleteTextView1.setThreshold(1);// will start working from
////														// first character
////				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
////															// data into the
////															// AutoCompleteTextView
////				autoCompleteTextView1.setTextColor(Color.BLACK);
////				autoCompleteTextView1.setText("");
////
////				// adapter_state3 = new
////				// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
////				// results2);
////				// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////				// retail_spinner.setAdapter(adapter_state3);
////				// retail_spinner.setOnItemSelectedListener(this);
////			} else {
////
////				String items = city_spinner.getSelectedItem().toString();
////				String C_ID = "";
////				Log.i("Selected item : ", items);
////
////				// List<Local_Data> contacts =
////				// dbvoc.getBeats_CITYID(state_spinner.getSelectedItem().toString());
////				// for (Local_Data cn : contacts)
////				// {
////				//
////				// C_ID = cn.getStateName();
////				// //CAT_ID = cn.getStateName();
////				// //F_CITY_ID = cn.getStateName();
////				// //F_BEAT_ID = cn.get_category_id();
////				// }
////
////				if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(items)) {
//////					results_beat.clear();
//////					results_beat.add("Select Beat");
////					List<Local_Data> contacts2 = dbvoc.getbeatByCityName((items));
////					for (Local_Data cn : contacts2) {
////
////						results_beat.add(cn.getPwd());
////					}
////					adapter_beat = new ArrayAdapter<String>(Order.this,
////							android.R.layout.simple_spinner_item, results_beat);
////					adapter_beat
////							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////					beat_spinner.setAdapter(adapter_beat);
////				}
////
////				if (!Global_Data.GLOvel_CITY_n.equalsIgnoreCase("")) {
////					beat_spinner.setSelection(adapter_beat
////							.getPosition(Global_Data.GLOvel_CITY_n
////									.toUpperCase()));
////				} else if (!Global_Data.GLOvel_CITY.equalsIgnoreCase("")) {
////					beat_spinner
////							.setSelection(adapter_beat
////									.getPosition(Global_Data.GLOvel_CITY
////											.toUpperCase()));
////				}
////			}
////		}
////
////
////
////
////
////
////		 if(spinner.getId() == R.id.cust_beat)
////		 {
////		 if (arg0.getItemAtPosition(arg2).toString()
////				.equalsIgnoreCase("Select Beat")) {
////
////			list_cities.add("");
////			list_cities.clear();
////			// String []customer_array = {"Apple", "Banana", "Cherry", "Date",
////			// "Grape", "Kiwi", "Mango", "Pear"};
////			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////					android.R.layout.simple_spinner_dropdown_item, list_cities);
////			autoCompleteTextView1.setThreshold(1);// will start working from
////													// first character
////			autoCompleteTextView1.setAdapter(adapter);// setting the adapter
////														// data into the
////														// AutoCompleteTextView
////			autoCompleteTextView1.setTextColor(Color.BLACK);
////			autoCompleteTextView1.setText("");
////			// results.clear();
////			// results.add("Select City");
////			// results2.clear();
////			// results2.add("Select Customer");
////			// // adapter_state2 = new
////			// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
////			// results);
////			// //
////			// adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////			// // city_spinner.setAdapter(adapter_state2);
////			// // city_spinner.setOnItemSelectedListener(this);
////			//
////			// adapter_state3 = new
////			// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
////			// results2);
////			// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////			// retail_spinner.setAdapter(adapter_state3);
////			// retail_spinner.setOnItemSelectedListener(this);
////
////		} else {
////
////			String items = beat_spinner.getSelectedItem().toString();
////			String C_ID = "";
////			Log.i("Selected item : ", items);
////
////			if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(items)) {
////				list_cities.clear();
////				List<Local_Data> contacts2 = dbvoc.getcustomerByCityName(items);
////				for (Local_Data cn : contacts2) {
////
//////					if(!cn.getPURPOSE_ADDRESS().equalsIgnoreCase(null) && !cn.getPURPOSE_ADDRESS().equalsIgnoreCase("null") && !cn.getPURPOSE_ADDRESS().equalsIgnoreCase(""))
//////					{
////						list_cities.add(cn.get_stocks_product_name()); //+ ":" +cn.getPURPOSE_ADDRESS());
//////					}
//////					else
//////					{
//////						list_cities.add(cn.get_stocks_product_name());
//////					}
////
////				}
////				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////						android.R.layout.simple_spinner_dropdown_item,
////						list_cities);
////				autoCompleteTextView1.setThreshold(1);// will start working from
////														// first character
////				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
////															// data into the
////															// AutoCompleteTextView
////				autoCompleteTextView1.setTextColor(Color.BLACK);
////			}
////
////		}
////		 }
//
//	}
//
//	@Override
//	public void onNothingSelected(AdapterView<?> arg0) {
//		// TODO Auto-generated method stub
//
//	}

}
