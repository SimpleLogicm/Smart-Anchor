package com.anchor.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anchor.model.Category;
import com.anchor.model.Product;
import com.anchor.model.Scheme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReturnEditItem extends BaseActivity {
	int check=0;
	int activity_load_flag = 0;
	String CategoriesSpinner = "";
	String ProductSpinner = "";
	String price = "";
	LoginDataBaseAdapter loginDataBaseAdapter;
	Spinner spnProductSpec, spnScheme;
	TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity,txt_rp;
	EditText txtDeleiveryQuantity1;
	static int quantity = 0, deleiveryQuantity = 0;
	static float rp, mrp, totalprice, productprice;
	static String scheme = "";
	EditText editTextQuantity,spnProduct;
	static String category, productName, productSpec, productQuantity,
			productDeleiveryQuantity, productScheme, productrp, productmrp,
			producttotalPrice;
	Button buttonAddMOre,buttonEditBack;

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
		setContentView(R.layout.edit_product);
		
		// create a instance of SQLite Database
	     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	    // Global_Data.GLOVEL_SubCategory_Button = "";
		
//		//Reading all 
//	         List<Local_Data> contacts = dbvoc.getAllMain();      
//	          for (Local_Data cn : contacts) {
//	        	Global_Data.local_user = ""+cn.getUser();
//	        	Global_Data.local_pwd = ""+cn.getPwd();
//	        	System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
//	        	//Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//	        	                             }
		
		    SharedPreferences spf=ReturnEditItem.this.getSharedPreferences("SimpleLogic",0);        
	        SharedPreferences.Editor editor=spf.edit();        
	        editor.putString("order", "new");
	        editor.commit();
		
	      //Reading all 
//	         List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
//	         results1.add("Select Category");
//	          for (Local_Data cn : contacts1) 
//	          {
//	        	  if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
//	        	  {
//	        		  String str_categ = ""+cn.getStateName();
//	        		  results1.add(str_categ);
//	        	  }
//	        	 
//	        	//Global_Data.local_pwd = ""+cn.getPwd();
//	        	
//	        	
//	        	//System.out.println("Local Values:-"+Global_Data.local_user);
//	        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//	        	                             
//	          }
	          
	        
	        TextView welcomeUser= findViewById(R.id.txtWelcomeUser);
	        //question_value.setTypeface(null,Typeface.BOLD);
	      // welcomeUser.setText(spf.getString("FirstName", "")+" "+ spf.getString("LastName", ""));
		
		txtPrice = findViewById(R.id.txtPrice);
		//txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity);
		txtDeleiveryQuantity1 = findViewById(R.id.txtDeleiveryQuantity);
		//spnCategory = (Spinner) findViewById(R.id.spnCategory);
		spnProduct = findViewById(R.id.spnProduct);
		spnProductSpec = findViewById(R.id.spnProductSpec);
		spnScheme = findViewById(R.id.spnScheme);

		editTextRP = findViewById(R.id.editTextRP);
		editTextMRP = findViewById(R.id.editTextMRP);

		editTextQuantity = findViewById(R.id.editTextQuantity);


		txt_rp = findViewById(R.id.textRP);
		// for label RP change
		SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
		String rpstr=spf1.getString("var_rp", "");
		txt_rp.setText(rpstr);

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
					}
				}

			}
		});

		listProduct = new ArrayList<String>();
		dataAdapterProduct = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listProduct);

		listProductSpec = new ArrayList<String>();
		dataAdapterProductSpec = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listProductSpec);

		listScheme = new ArrayList<String>();
		dataAdapterScheme = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listScheme);

		final List<String> listCategory = new ArrayList<String>();
		listCategory.add("Select Category");
		 
		 // adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results1);
		  adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results);
		  adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results2);
		  
		  Discount_list.add("Select Discount Type");
		  Discount_list.add("Rupees");
		  Discount_list.add("Percentage");
		  
		  Discount_Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Discount_list);
		  Discount_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		  spnScheme.setAdapter(Discount_Adapter);
		
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
		  
		  
		  if(Global_Data.amount != "")
		  {
			  spnProduct.setText(Global_Data.product_dec);
			  editTextQuantity.setText(Global_Data.total_qty);
			  editTextMRP.setText(Global_Data.MRP);
			  editTextRP.setText(Global_Data.RP);
			 // spnScheme.setEnabled(true);
			  txtPrice.setText("Total Price : "+Global_Data.amount);
			//  txtDeleiveryQuantity1.setVisibility(View.VISIBLE);
			  
		  	if (!Global_Data.actual_discount.equalsIgnoreCase(""))
			{
				Log.d("Globel categary", "in");
				spnScheme.setSelection(Discount_Adapter.getPosition(Global_Data.actual_discount));
				//Global_Data.GLOVEL_CATEGORY_SELECTION = "";
				
				txtDeleiveryQuantity1.setText(Global_Data.scheme_amount);
				
			}
			 
		  }
		  else
		  {
			  //editTextQuantity.setFocusable(false) ; 
			  editTextMRP.setText("");
			  editTextRP.setText("");
			 // spnScheme.setEnabled(false);
			  txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
		  }
		
//		  spnProduct.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View view) {
//	            	
//	            	if(!spnProduct.getText().toString().trim().equalsIgnoreCase(""))
//	            	{
//	            		getListViewnew(spnProduct.getText().toString().trim());
//	            	}
//	            	
//	            	
//	            }
//		  });
//		  
		  

		
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
		
//		ActionBar mActionBar = getActionBar();
//		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//       // mActionBar.setDisplayShowHomeEnabled(false);
//       // mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
// 
//        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
//        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
//        mTitleTextView.setText("Order Booking");
//        
//        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
//        SharedPreferences sp = Item_Edit_Activity.this.getSharedPreferences("SimpleLogic", 0);
//       
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//        	todaysTarget.setText("Today's Target Acheived");
//		}
//        
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
//        mActionBar.setHomeButtonEnabled(true);
//        mActionBar.setDisplayHomeAsUpEnabled(true);
       /* mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);*/
       
		buttonAddMOre = findViewById(R.id.buttonAddMOre);
		buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
		
		buttonEditBack = findViewById(R.id.buttonEditBack);
		buttonEditBack.setBackgroundColor(Color.parseColor("#414042"));
		
//			buttonAddMOre.setOnTouchListener(new OnTouchListener() {
		buttonEditBack.setOnTouchListener(new OnTouchListener() {
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
		        
		        Global_Data.GLOVEL_ORDER_REJECT_FLAG = "";
				 finish();
			
		        return true;
		    }
		    return false;
		}
	});  
		
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
//					if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
//						Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Category", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
					
//					else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
//							Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Product", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}
					
//					else if (spnProductSpec.getSelectedItem().toString().equalsIgnoreCase("Select Variant")) {
//						Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Variant", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
				
				   if (editTextQuantity.getText().toString().length() == 0) {
						Toast toast = Toast.makeText(ReturnEditItem.this,"Please enter Quantity", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
//					else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")) {
//						Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Discount Type", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
					
					else{

						// TODO Auto-generated method stub
						// v.setBackgroundColor(Color.parseColor("#910505"));
						
						  // create a instance of SQLite Database
						
//						if (!spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")&& txtDeleiveryQuantity1.getText().toString().length() == 0) {
//							Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please enter Discount Amount", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}
//						else
//						{
							  //Global_Data.order_category = spnCategory.getSelectedItem().toString();
							  //Global_Data.order_product = spnProduct.getSelectedItem().toString();
							 // Global_Data.order_variant = spnProductSpec.getSelectedItem().toString();
							  
	
						     loginDataBaseAdapter=loginDataBaseAdapter.open();

                       String[] s_price = txtPrice.getText().toString().split(":");
						     
//	//					     //Reading all 
//				   	         List<Local_Data> contacts = dbvoc.getRR();      
//				   	          for (Local_Data cn : contacts) {
//				   	        	String str = ""+cn.getRR();
//				   	        	//Global_Data.local_pwd = ""+cn.getPwd();
//				   	        	System.out.println("Local Values:-"+str);
//				   	        	//Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//				   	        	                             }
				   	         String discount_type = "";
				   	         String discount_amount = "";
//						     if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type"))
//						     {
//						    	 discount_type = "";
//						    	 discount_amount = "";
//						     }
//						     else
//						     {
//						    	 discount_type = spnScheme.getSelectedItem().toString().trim();
//						    	 discount_amount = txtDeleiveryQuantity1.getText().toString().trim();
//						     }
							  dbvoc.update_item_return(editTextQuantity.getText().toString().trim(),editTextMRP.getText().toString().trim(),s_price[1].trim(),discount_amount,discount_type,Global_Data.item_no,Global_Data.GLObalOrder_id_return);
							  //Toast.makeText(ReturnEditItem.this, "Item Update Successfully",Toast.LENGTH_LONG).show();

							   Toast toast = Toast.makeText(ReturnEditItem.this, "Item Update Successfully",Toast.LENGTH_LONG);
							   toast.setGravity(Gravity.CENTER, 0, 0);
							   toast.show();
							    
							    if(Global_Data.GLOVEL_ORDER_REJECT_FLAG == "")
							    {
							    	Intent intent = new Intent(ReturnEditItem.this, ReturnOrder2.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
							    	startActivity(intent);
			    					finish();
							    }
							    else
							    {
							    	Intent intent = new Intent(ReturnEditItem.this, Status_Activity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
							    	startActivity(intent);
			    					finish();
							    }
								
//							
							  return true;
						//}
					}
				
			        return true;
			    }
			    return false;
			}
		});

//		buttonPreviewOrder = (Button) findViewById(R.id.buttonPreviewOrder);
//		buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
//		buttonPreviewOrder.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View b, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if(event.getAction() == MotionEvent.ACTION_UP)
//			    {
//			        //up event
//					 b.setBackgroundColor(Color.parseColor("#414042"));
//			        return true;
//			    }
//			    if(event.getAction() == MotionEvent.ACTION_DOWN)
//			    {
//			        //down event
//			    	 b.setBackgroundColor(Color.parseColor("#910505"));
//						// TODO Auto-generated method stub
//						
//						if (Global_Data.productList.size()!=0) {
//							/*final Intent i = new Intent(getApplicationContext(),
//									PreviewOrderActivity.class);*/
//							final Intent i = new Intent(getApplicationContext(),
//									PreviewOrderSwipeActivity.class);
//							i.putParcelableArrayListExtra("productsList", Global_Data.productList);
//							i.putExtra("new","new");
//							SharedPreferences sp = Item_Edit_Activity.this
//									.getSharedPreferences("SimpleLogic", 0);
//
//							i.putExtra("retialer",
//									"" + sp.getString("RetailerName", ""));
//							//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//							Global_Data.GLOVEL_LONG_DESC = ""; 
//							 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
//							 Global_Data.GLOVEL_ITEM_MRP = "";
//							
//							i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//							ActivitySwitcher.animationOut(findViewById(R.id.containerNewOrder), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
//								@Override
//								public void onAnimationFinished() {
//									startActivity(i);
//									finish();
//								}
//							});
//							//NewOrderFragment.this.startActivity(i);
//						}
//						
//						else {
//							//Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();
//							
//							Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}
//
//			        return true;
//			    }
//			    return false;
//			}
//		});
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

		
		
//		spnCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View arg1,
//					int pos, long arg3) {
////				// TODO Auto-generated method stub
//				
//				
//				
////				// Toast.makeText(parent.getContext(),
////				// "OnItemSelectedListener : " +
////				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
////				editTextQuantity.setFocusableInTouchMode(false);
////				editTextQuantity.setEnabled(false);
////				txtPrice.setText("Total Price : ");
////		
//				Global_Data.GLOVEL_ITEM_MRP = "";
////				category = parent.getItemAtPosition(pos).toString();
//				Log.d("Globel categary selection", Global_Data.GLOVEL_CATEGORY_SELECTION);
//				
//				 check=check+1;
//			   if(check>1)
//			   {
//				
////				if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
////				{
////					Log.d("Globel categary selection", "in");
////					spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
////					
////
////				}
////				else
////				{	
////                 if (Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
////                 {	 
//					if (parent.getItemAtPosition(pos).toString()
//							.equalsIgnoreCase("Select Category")) {
//						
//						Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();
//						
//					}
//					else
//					{
//						
//						Global_Data.GLOVEL_CATEGORY_SELECTION = parent.getItemAtPosition(pos).toString();
//						Intent intent = new Intent(getApplicationContext(), Filter_List.class);
//						Global_Data.GLOVEL_CATEGORY_NAME = parent.getItemAtPosition(pos).toString();
//						startActivity(intent);
//						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//						
//					}
////                 }
////                 else
////                 {
////                	 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
////                 }
//                 
//                	 
//				//}
//			   }	
////
////				else {
////					
////					//categoryID = Integer.parseInt(categoriesMap.get(""+parent.getSelectedItemId()));
////					categoryID =dataCategories.get(pos-1).getId();
////					CategoriesSpinner = parent.getItemAtPosition(pos).toString();
////					LoadProductsAsyncTask loadProductsAsyncTask=new LoadProductsAsyncTask(Item_Edit_Activity.this);
////					loadProductsAsyncTask.execute();
////					
////				}
//				
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		
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
//					LoadProductVarientsAsyncTask loadProductVarientsAsyncTask=new LoadProductVarientsAsyncTask(Item_Edit_Activity.this);
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
			    	  
//			    	  if(!txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase("") && !txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase(null) && !txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase("null"))
//			    	  {
//			    		  
//			    	  
//				    	  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
//				    	  {
//				    		  int final_mrp = (Integer.parseInt(editTextMRP.getText().toString()))*(Integer.parseInt(String.valueOf(s)));
//				    		  int final_discount = Integer.parseInt(String.valueOf(s));
//				    		  // int final_discount = (Integer.parseInt(txtDeleiveryQuantity1.getText().toString()))*(Integer.parseInt(String.valueOf(s)));
//				    		 // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
//				    		  int price1 = (final_mrp)-(final_discount);
//				    		  txtPrice.setText("Total Price : "+price1);
//				    		  price = String.valueOf(price1);
//				    	  }
//				    	  else
//			    		  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
//				    	  {
//			    			   Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(String.valueOf(s)));
//			    			   int final_discount = Integer.parseInt(String.valueOf(s));
//			    			   //Float final_discount = (Float.valueOf((txtDeleiveryQuantity1.getText().toString())))*(Float.valueOf(String.valueOf(s)));
//			//		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
//			//		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);
//			    			   
//			    			   Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
//			    			   Float price1 = (final_mrp)-(per_value);
//			
//				    		  txtPrice.setText("Total Price : "+price1);
//				    		  price = String.valueOf(price1);
//				    	  }	  
//			    	  
//			    	  }
//			    	  else
//			    	  {
					  if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") &&  !editTextRP.getText().toString().equalsIgnoreCase("") && !editTextRP.getText().toString().equalsIgnoreCase("null") && !editTextRP.getText().toString().equalsIgnoreCase(null)) {
						  try
						  {
							  Double final_mrp = Double.valueOf(editTextRP.getText().toString()) * Double.valueOf(editTextQuantity.getText().toString().trim());
							  txtPrice.setText("Total Price : "+final_mrp);
							  price = String.valueOf(final_mrp);
						  }catch(Exception ex){ex.printStackTrace();}
						  //}
					  }
					  else
					  {
						  txtPrice.setText("Total Price : " + "");
						  price = "0";
					  }
			    	  
			      }
			        //Field2.setText("");
			   }
			  });
        
		txtDeleiveryQuantity1.addTextChangedListener(new TextWatcher() {

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
			    	  
			    	  if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null"))
			    	  {
						  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
						  {
							  long final_mrp = (Long.valueOf(editTextRP.getText().toString()))*(Long.valueOf(editTextQuantity.getText().toString().trim()));
							  long final_discount = Long.parseLong(String.valueOf(s));
							  //int final_discount = (Integer.parseInt(String.valueOf(s)))*(Integer.parseInt(editTextQuantity.getText().toString()));
							  // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
							  long price1 = (final_mrp)-(final_discount);
							  txtPrice.setText("Total Price : "+price1);
							  price = String.valueOf(price1);
						  }
				    	  else
			    		  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
				    	  {
			    			   Float final_mrp = (Float.valueOf(editTextRP.getText().toString()))*(Float.valueOf(editTextQuantity.getText().toString()));
			    			   int final_discount = Integer.parseInt(String.valueOf(s));
			    			   //Float final_discount = (Float.valueOf((String.valueOf(s))))*(Float.valueOf(editTextQuantity.getText().toString()));
	//		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
	//		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);
			    			   
			    			   Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
			    			   Float price1 = (final_mrp)-(per_value);
	
				    		  txtPrice.setText("Total Price : "+price1);
				    		  price = String.valueOf(price1);
				    	  }	  
			    	  
			    	  }  
			    	 
			    		  
			      }
			        //Field2.setText("");
			   }
			  });

		spnScheme.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				productScheme = parent.getItemAtPosition(pos).toString();
				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Select Discount Type")) {

					txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
					
					if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null"))
			    	  {
						 Float final_mrp = (Float.valueOf(editTextRP.getText().toString()))*(Float.valueOf(editTextQuantity.getText().toString().trim()));
			    		 txtPrice.setText("Total Price : "+final_mrp);
			    		 price = String.valueOf(final_mrp);
			    	  }
					else
					{
						if(!editTextRP.getText().toString().equalsIgnoreCase("") && !editTextRP.getText().toString().equalsIgnoreCase(null) && !editTextRP.getText().toString().equalsIgnoreCase("null"))
						{
							 Float final_mrp = (Float.valueOf(editTextRP.getText().toString()));
				    		 txtPrice.setText("Total Price : "+final_mrp);
				    		 price = String.valueOf(final_mrp);
						}
					}

				}
				else
				if(activity_load_flag != 0)	
				{
					activity_load_flag++;
					if(parent.getItemAtPosition(pos).toString()
							.equalsIgnoreCase("Rupees"))	
					{
//						txtDeleiveryQuantity1.setText("");
//						txtDeleiveryQuantity1.setVisibility(View.VISIBLE);
//						txtDeleiveryQuantity1.setHint("Rs.");
//						txtPrice.setText("Total Price : ");
					}
					else
					if(parent.getItemAtPosition(pos).toString()
							.equalsIgnoreCase("Percentage"))	
					{
//						txtDeleiveryQuantity1.setText("");
//						txtDeleiveryQuantity1.setVisibility(View.VISIBLE);
//						txtDeleiveryQuantity1.setHint("%");
//						txtPrice.setText("Total Price : ");
					}
				}
				
				activity_load_flag++;
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
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		this.finish();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stubs
		super.onResume();
		buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
		//buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
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
	
	public void getListViewnew(final String value)
	{
		 final Dialog dialognew = new Dialog(ReturnEditItem.this);
	        dialognew.setCancelable(false);
	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //tell the Dialog to use the dialog.xml as it's layout description
	        dialognew.setContentView(R.layout.update_dialog_edit);

	        final EditText userInput = dialognew
	                .findViewById(R.id.update_textdialog);
	        
	        final TextView headertext = dialognew
	                .findViewById(R.id.item_description);
	         headertext.setText("");
	         headertext.setText("ITEM DESCRIPTION");

	                final Button Submit = dialognew
	                        .findViewById(R.id.update_textdialogclick);
	                
	                final Button update_cancel = dialognew
	                        .findViewById(R.id.update_cancel);
	                
	                update_cancel.setVisibility(View.GONE);

	        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value))
	        {
	            userInput.setText(value);
	        }

//	        final Button cancel = (Button) dialognew
//	                .findViewById(R.id.cancel);

	        Submit.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

	                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(userInput.getText().toString()))
	                {
	                	spnProduct.setText(userInput.getText().toString());
	                }
	            		
	            	
	                    dialognew.dismiss();


	            }
	        });

//	        cancel.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                dialognew.dismiss();
	//
	//
//	            }
//	        });

	        dialognew.show();
	}

}
