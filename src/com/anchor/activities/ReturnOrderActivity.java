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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ReturnOrderActivity extends BaseActivity {

	String CategoriesSpinner = "";
	String ProductSpinner = "";
	
	Spinner spnCategory, spnProduct, spnProductSpec, spnScheme;
	TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity;
	static int quantity = 0, deleiveryQuantity = 0;
	static float rp, mrp, totalprice, productprice;
	static String scheme = "Buy 6 and Get 2 Free";
	EditText editTextQuantity;
	static String category, productName, productSpec, productQuantity,
			productDeleiveryQuantity, productScheme, productrp, productmrp,
			producttotalPrice;
	Button buttonAddMOre, buttonPreviewOrder;

	ArrayList<Category> dataCategories =  new ArrayList<Category>();
	//dataVarients;
	ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct ;
	ArrayList productList = new ArrayList();
	 List<String> listProduct,listProductSpec ;
	 List<String> listScheme;
	 ArrayAdapter<String> dataAdapterScheme;
	 static int categoryID,productID,schemeID;
	 HashMap<String, String> categoriesMap,productsMap;
	 int dbschemeID;
	 
	 ArrayList<Product> dataProducts = new ArrayList<Product>();
	 ArrayList<Scheme> dataScheme = new ArrayList<Scheme>();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_neworder);
		
		
		 SharedPreferences spf=ReturnOrderActivity.this.getSharedPreferences("SimpleLogic",0);        
	        SharedPreferences.Editor editor=spf.edit();        
	        editor.putString("order", "return");
	        editor.commit();
		
		
		
		txtPrice = findViewById(R.id.txtPrice);
		txtDeleiveryQuantity = findViewById(R.id.txtDeleiveryQuantity);
		spnCategory = findViewById(R.id.spnCategory);
		spnProduct = findViewById(R.id.spnProduct);
		spnProductSpec = findViewById(R.id.spnProductSpec);
		spnScheme = findViewById(R.id.spnScheme);

		
		spnScheme.setVisibility(View.GONE);
		
		
		
		editTextRP = findViewById(R.id.editTextRP);
		editTextMRP = findViewById(R.id.editTextMRP);

		editTextQuantity = findViewById(R.id.editTextQuantity);
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
			
		
		
		Category categories1 = new Category(1, 1, "C001", "AP Deodorants", "Active");
		Category categories2 = new Category(2, 2, "C002", "AP DEO Stick 15", "Active");
		Category categories3 = new Category(3, 3, "C003", "Hand Sanitizer 30", "Active");
		Category categories4 = new Category(4, 4, "C004", "Junior Hand Sanitizer 30", "Active");
		/*Category categories5 = new Category(1, 0, "C005", "AP Deodorants", "Active");
		Category categories6 = new Category(2, 0, "C006", "AP DEO Stick 15", "Active");
		Category categories7 = new Category(3, 0, "C007", "Hand Sanitizer 30", "Active");
		Category categories8 = new Category(4, 0, "C008", "Junior Hand Sanitizer 30", "Active");
		Category categories9 = new Category(1, 0, "C009", "AP Deodorants", "Active");
		Category categories10 = new Category(2, 0, "C010", "AP DEO Stick 15", "Active");
		Category categories11 = new Category(3, 0, "C011", "Hand Sanitizer 30", "Active");
		Category categories12 = new Category(4, 0, "C012", "Junior Hand Sanitizer 30", "Active");
		Category categories13 = new Category(1, 0, "C013", "AP Deodorants", "Active");
		Category categories14 = new Category(2, 0, "C014", "AP DEO Stick 15", "Active");
		Category categories15 = new Category(3, 0, "C015", "Hand Sanitizer 30", "Active");
		Category categories16 = new Category(4, 0, "C016", "Junior Hand Sanitizer 30", "Active");
		Category categories17 = new Category(1, 0, "C017", "AP Deodorants", "Active");
		Category categories18 = new Category(2, 0, "C018", "AP DEO Stick 15", "Active");*/
		
		dataCategories.add(categories1);
		dataCategories.add(categories2);
		dataCategories.add(categories3);
		dataCategories.add(categories4);
		/*dataCategories.add(categories5);
		dataCategories.add(categories6);
		dataCategories.add(categories7);
		dataCategories.add(categories8);
		dataCategories.add(categories9);
		dataCategories.add(categories10);
		dataCategories.add(categories11);
		dataCategories.add(categories12);
		dataCategories.add(categories13);
		dataCategories.add(categories14);
		dataCategories.add(categories15);
		dataCategories.add(categories16);
		dataCategories.add(categories17);
		dataCategories.add(categories18);*/
		
		 
		
		for(int i = 0 ; i < dataCategories.size();i++){
			
			listCategory.add(dataCategories.get(i).getDesc());
			
		}
		
		
		
		// myDbHelper = new DatabaseHandler(getApplicationContext());
		 //dataCategories=(ArrayList<DatabaseModel>) myDbHelper.loadCategories();
		
		/* categoriesMap=new HashMap<String, String>();
		 int j=1;
		 for (Iterator iterator = dataCategories.iterator(); iterator.hasNext();) {
				DatabaseModel databaseModel = (DatabaseModel) iterator.next();
				//Log.e("DATA", ""+databaseModel);
				listCategory.add(databaseModel.getName());
				categoriesMap.put(""+j, ""+databaseModel.getId());
				j++;
			}*/
		
		  dataAdapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listCategory);
			dataAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnCategory.setAdapter(dataAdapterCategory);
		
		Intent i = getIntent();
		String name = i.getStringExtra("retialer");
		if (i.hasExtra("data")) {
			//Log.e("data", "***********productList**********");
			productList=i.getParcelableArrayListExtra("productsList");
		}
	/*	ActionBar actionBar = getActionBar();
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
			TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText(name);
			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = ReturnOrderActivity.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
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

//        TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//        //question_value.setTypeface(null,Typeface.BOLD);
//       welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}

		buttonAddMOre = findViewById(R.id.buttonAddMOre);
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
					}*/
			        
			        if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
						Toast toast = Toast.makeText(ReturnOrderActivity.this,"Please Select Category", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
					else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
							Toast toast = Toast.makeText(ReturnOrderActivity.this,"Please Select Product", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					
					else if (spnProductSpec.getSelectedItem().toString().equalsIgnoreCase("Select Variant")) {
						Toast toast = Toast.makeText(ReturnOrderActivity.this,"Please Select Variant", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				
					else if (editTextQuantity.getText().toString().length() == 0) {
						Toast toast = Toast.makeText(ReturnOrderActivity.this,"Please enter Quantity", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
					else {

						// TODO Auto-generated method stub
						// v.setBackgroundColor(Color.parseColor("#910505"));
						
						

						
       					Product p = new Product();
						p.setCategory(spnCategory.getSelectedItem().toString());
						p.setProductName(spnProduct.getSelectedItem().toString());
						p.setProductSpec(spnProductSpec.getSelectedItem().toString());
						p.setProductrp("" + rp);
						p.setProductmrp("" + mrp);
						p.setProductQuantity("" + quantity);
						p.setProductDeleiveryQuantity("" + deleiveryQuantity);
						p.setProducttotalPrice("" + totalprice);
						p.setProductScheme(spnScheme.getSelectedItem().toString());
						p.setCategoryID(categoryID);
						p.setProductID(productID);
						p.setSchemeID(schemeID);
						
						productList.add(p);
						
						
						
							
						
						
						
						spnCategory.setAdapter(dataAdapterCategory);


						listProduct.clear();
						listProduct.add("Select Product");

						dataAdapterProduct.notifyDataSetChanged();
						dataAdapterProduct
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spnProduct.setAdapter(dataAdapterProduct);

						listProductSpec.clear();
						listProductSpec.add("Select Variant");
						dataAdapterProductSpec.notifyDataSetChanged();
						dataAdapterProductSpec
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spnProductSpec.setAdapter(dataAdapterProductSpec);

						listScheme.clear();
						listScheme.add("Select Scheme");

						dataAdapterScheme.notifyDataSetChanged();
						dataAdapterScheme
								.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						spnScheme.setAdapter(dataAdapterScheme);

						editTextRP.setText("" + rp);
						editTextMRP.setText("" + mrp);
						txtPrice.setText("Total Price : ");
						rp = 0.00f;
						mrp = 0.00f;
						totalprice = 0.00f;
						deleiveryQuantity = 0;
						editTextQuantity.setText("");
						txtDeleiveryQuantity.setText("Return Quantity :");
						
						

					
					}
				
			        
			        
			        return true;
			    }
			    return false;
			}
		});

		buttonPreviewOrder = findViewById(R.id.buttonPreviewOrder);
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
			        //down event
			    	 b.setBackgroundColor(Color.parseColor("#910505"));

						// TODO Auto-generated method stub
						

						
						if (productList.size()!=0) {
							/*final Intent i = new Intent(getApplicationContext(),
									PreviewOrderActivity.class);*/
							final Intent i = new Intent(getApplicationContext(),
									PreviewOrderSwipeActivity.class);
							i.putParcelableArrayListExtra("productsList", productList);
							i.putExtra("return", "return");
							SharedPreferences sp = ReturnOrderActivity.this
									.getSharedPreferences("SimpleLogic", 0);

							i.putExtra("retialer",
									"" + sp.getString("RetailerName", ""));
							//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

							
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
		editTextQuantity.setText("");

		
			 
          editTextQuantity.setOnKeyListener(new OnKeyListener() {

  			@Override
  			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (editTextQuantity.getText().toString().length() != 0) {
					quantity = Integer.parseInt(editTextQuantity.getText()
							.toString());
					totalprice = productprice * quantity;
					txtPrice.setText("Total Price : " + String.format("%.2f", totalprice));
					//deleiveryQuantity = quantity;
//					txtDeleiveryQuantity.setText("Return Quantity : "
//							+ deleiveryQuantity);
					if (scheme.equalsIgnoreCase("Select Scheme")) {

						// Toast.makeText(parent.getContext(),
						// "OnItemSelectedListener : " +
						// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
						scheme = "Select Scheme";
						schemeID=0;
						if (editTextQuantity.getText().toString().length()!=0) {
							quantity=Integer.parseInt(editTextQuantity.getText().toString());
							deleiveryQuantity = quantity;
							txtDeleiveryQuantity.setText("Return Quantity : "
									+ deleiveryQuantity);
						}
					
						
						/*LoadProductIDsTask loadProductIDsTask=new LoadProductIDsTask(NewOrderFragment.this);
						loadProductIDsTask.execute();*/
						//txtDeleiveryQuantity.setText("Delivery Quantity : ");

					}
					
					else {
						
						schemeID=dbschemeID;
						String [] aray=scheme.split("and");
						int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
						int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
						if (editTextQuantity.getText().toString().length()!=0) {
							quantity=Integer.parseInt(editTextQuantity.getText().toString());
							int extra = quantity / buy;
							deleiveryQuantity = extra*get + quantity;
							txtDeleiveryQuantity.setText("Return Quantity : "
									+ deleiveryQuantity);
						}
						
						
					}
					
				}

				return false;
			}
		});

		editTextQuantity.setFocusable(false) ;
		spnCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(parent.getContext(),
				// "OnItemSelectedListener : " +
				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
				editTextQuantity.setFocusableInTouchMode(false);
				editTextQuantity.setEnabled(false);
				txtPrice.setText("Total Price : ");
				
				
				category = parent.getItemAtPosition(pos).toString();

				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Select Category")) {
					listProduct.clear();
					listProduct.add("Select Product");

					dataAdapterProduct.notifyDataSetChanged();
					dataAdapterProduct
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnProduct.setAdapter(dataAdapterProduct);

					listProductSpec.clear();
					listProductSpec.add("Select Variant");
					dataAdapterProductSpec.notifyDataSetChanged();
					dataAdapterProductSpec
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnProductSpec.setAdapter(dataAdapterProductSpec);

					listScheme.clear();
					listScheme.add("Select Scheme");

					dataAdapterScheme.notifyDataSetChanged();
					dataAdapterScheme
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnScheme.setAdapter(dataAdapterScheme);

					editTextRP.setText("" + rp);
					editTextMRP.setText("" + mrp);
					txtPrice.setText("Total Price : ");
					rp = 0.00f;
					mrp = 0.00f;
					totalprice = 0.00f;
					deleiveryQuantity = 0;
					editTextQuantity.setText("");
					txtDeleiveryQuantity.setText("Return Quantity :");

				}

				else {
					
					//categoryID = Integer.parseInt(categoriesMap.get(""+parent.getSelectedItemId()));
					
					categoryID =dataCategories.get(pos-1).getId();
					CategoriesSpinner = parent.getItemAtPosition(pos).toString();
					LoadProductsAsyncTask loadProductsAsyncTask=new LoadProductsAsyncTask(ReturnOrderActivity.this);
					loadProductsAsyncTask.execute();
					
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				editTextQuantity.setFocusableInTouchMode(false);
				editTextQuantity.setEnabled(false);
				txtPrice.setText("Total Price : ");
				
				
				productName = parent.getItemAtPosition(pos).toString();
				
				
				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Select Product")) {
					
					listProductSpec.clear();
					listProductSpec.add("Select Variant");
					dataAdapterProductSpec.notifyDataSetChanged();
					dataAdapterProductSpec
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnProductSpec.setAdapter(dataAdapterProductSpec);

					listScheme.clear();
					listScheme.add("Select Scheme");

					dataAdapterScheme.notifyDataSetChanged();
					dataAdapterScheme
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnScheme.setAdapter(dataAdapterScheme);

					/*editTextRP.setText("" + rp);
					editTextMRP.setText("" + mrp);
					txtPrice.setText("Total Price : ");
					rp = 0.00f;
					mrp = 0.00f;
					totalprice = 0.00f;
					deleiveryQuantity = 0;
					editTextQuantity.setText("");
					txtDeleiveryQuantity.setText("Delivery Quantity :");*/
					
					rp = 0.00f;
					mrp = 0.00f;
					productprice = rp;

					editTextRP.setText("" + rp);
					editTextMRP.setText("" + mrp);

				}

				else {
					
					//productID= Integer.parseInt(productsMap.get(""+parent.getSelectedItemId()));;
					
					productID = dataProducts.get(pos-1).getProductID();
					ProductSpinner =parent.getItemAtPosition(pos).toString();
					LoadProductVarientsAsyncTask loadProductVarientsAsyncTask=new LoadProductVarientsAsyncTask(ReturnOrderActivity.this);
					loadProductVarientsAsyncTask.execute();
					
				}
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		spnProductSpec.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				// Toast.makeText(parent.getContext(),
				// "OnItemSelectedListener : " +
				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
				productSpec = parent.getItemAtPosition(pos).toString();
				
				
				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Select Variant")) {
					
					listScheme.clear();
					listScheme.add("Select Scheme");

					dataAdapterScheme.notifyDataSetChanged();
					dataAdapterScheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnScheme.setAdapter(dataAdapterScheme);
					rp = 0.00f;
					mrp = 0.00f;
					productprice = rp;

					editTextRP.setText("" + rp);
					editTextMRP.setText("" + mrp);
					
					editTextQuantity.setFocusableInTouchMode(false);
					editTextQuantity.setEnabled(false);
					txtPrice.setText("Total Price : ");

				}
				else {
					editTextQuantity.setFocusableInTouchMode(true);
					editTextQuantity.setEnabled(true);
					LoadProductPriceAsyncTask loadProductPriceAsyncTask=new LoadProductPriceAsyncTask(ReturnOrderActivity.this);
					loadProductPriceAsyncTask.execute();
				}
				

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

		spnScheme.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				productScheme = parent.getItemAtPosition(pos).toString();

				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Select Scheme")) {

					// Toast.makeText(parent.getContext(),
					// "OnItemSelectedListener : " +
					// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
					scheme = "Select Scheme";
					schemeID=0;
					if (editTextQuantity.getText().toString().length()!=0) {
						quantity=Integer.parseInt(editTextQuantity.getText().toString());
					        deleiveryQuantity = quantity;
						txtDeleiveryQuantity.setText("Return Quantity : "
								+ deleiveryQuantity);
					}
				
					
					/*LoadProductIDsTask loadProductIDsTask=new LoadProductIDsTask(NewOrderFragment.this);
					loadProductIDsTask.execute();*/
					//txtDeleiveryQuantity.setText("Delivery Quantity : ");

				}

				else {
					schemeID=dbschemeID;
					scheme=parent.getSelectedItem().toString();
					
					String [] aray=scheme.split("and");
					int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
					int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
					if (editTextQuantity.getText().toString().length()!=0) {
						quantity=Integer.parseInt(editTextQuantity.getText().toString());
						int extra = quantity / buy;
						deleiveryQuantity = extra*get + quantity;
						txtDeleiveryQuantity.setText("Return Quantity : "
								+ deleiveryQuantity);
					}
					
					
				}

				/*if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Buy1Get2")) {

					// Toast.makeText(parent.getContext(),
					// "OnItemSelectedListener : " +
					// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
					scheme = "Buy1Get2";
					deleiveryQuantity = quantity * 2;
					txtDeleiveryQuantity.setText("Delivery Quantity : "
							+ deleiveryQuantity);

				}

				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Buy2Get1")) {

					scheme = "Buy2Get1";
					int extra = quantity / 2;
					deleiveryQuantity = extra + quantity;
					txtDeleiveryQuantity.setText("Delivery Quantity : "
							+ deleiveryQuantity);

				}

				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Buy3Get1")) {

					scheme = "Buy3Get1";
					int extra = quantity / 3;
					deleiveryQuantity = extra + quantity;
					txtDeleiveryQuantity.setText("Delivery Quantity : "
							+ deleiveryQuantity);

				}*/
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
		
		if (productList.size()!=0) {
			
			AlertDialog alertDialog = new AlertDialog.Builder(ReturnOrderActivity.this).create(); //Read Update
		    alertDialog.setTitle("Warning");
		    alertDialog.setMessage(" Are you sure you want to cancel order?"); 
		    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					 ReturnOrderActivity.this.finish();
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
			 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
		}
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
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
				
				/*dataProducts=(ArrayList<DatabaseProductModel>) myDbHelper.loadProducts(categoryID);
				productsMap =new HashMap<String, String>();
				int j=1;
					for (Iterator iterator = dataProducts.iterator(); iterator.hasNext();) {
						DatabaseProductModel databaseModel = (DatabaseProductModel) iterator.next();
						//Log.e("DATA", ""+databaseModel);
						listProduct.add(databaseModel.getProductDesc());
						productsMap.put(""+j, ""+databaseModel.getId());
						j++;
					}
*/			} catch (Exception e) {
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
	      
	      	spnProduct.setAdapter(dataAdapterProduct);
			
			
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
				/*
				 productVarients=(ArrayList<String>) myDbHelper.loadVarients(productID);

				 for (Iterator iterator = productVarients.iterator(); iterator
						.hasNext();) {
					listProductSpec.add((String) iterator.next());
					
				}*/
				

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
				
				for(int i = 0 ; i < productVarients.size();i++){
					
					listProductSpec.add(productVarients.get(i).getProductName());
					
				}
				
				
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
	     	
	      
	      	spnProductSpec.setAdapter(dataAdapterProductSpec);
			
			
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
				/*databaseModel= myDbHelper.loadProductsPrices(categoryID,
						                         productID,
						                         spnProductSpec.getSelectedItem().toString());
					
						
						
						
						//listScheme.add(databaseModel.getSchemeId());
					
						rp = databaseModel.getRp();
						mrp = databaseModel.getMrp();
						productprice = rp;
						
						
						//listScheme.add("Select Scheme");
						dbschemeID=databaseModel.getSchemeId();
						listScheme.add(myDbHelper.loadSchemes(databaseModel.getSchemeId()));
						
						*/
				Scheme scheme = new Scheme(1, "Buy 6 and Get 2 Free", "S001", "Y");
				
				if (!dataScheme.isEmpty())
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
				txtDeleiveryQuantity.setText("Return Quantity : "
						+ deleiveryQuantity);
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

}
