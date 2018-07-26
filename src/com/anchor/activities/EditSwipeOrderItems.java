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

import com.anchor.model.DatabaseProductModel;
import com.anchor.model.Product;
import com.anchor.model.Products_Mine;
import com.anchor.model.Scheme;

import java.util.ArrayList;
import java.util.List;

//import com.simplelogic.database.DatabaseHandler;

public class EditSwipeOrderItems extends BaseActivity {

	Spinner spnCategory, spnProduct, spnProductSpec, spnScheme;
	TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity;
	static int quantity = 0, deleiveryQuantity = 0;
	static float rp, mrp, totalprice, productprice;
	static String scheme = "";
	EditText editTextQuantity;
	static String category, productName, productSpec, productQuantity,
			productDeleiveryQuantity, productScheme, productrp, productmrp,
			producttotalPrice;
	Button buttonAddMOre, buttonPreviewOrder;
	ArrayList<Product> productList = new ArrayList();
	Product p=new Product();
	List<String> listProductSpec;
	ArrayAdapter<String> dataAdapterProductSpec ;

	List<String> listScheme;
	ArrayAdapter<String> dataAdapterScheme;
	 static int categoryID,productID,schemeID;
	 int index=0;
	 String order="";
	 String orderString="";
	 int dbschemeID;

	 ArrayList<Scheme> dataScheme = new ArrayList<Scheme>();
	 ArrayList<Product> dataProducts = new ArrayList<Product>();
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_neworder);
		SharedPreferences sp = getApplicationContext().getSharedPreferences("SimpleLogic", 0);

	     order=sp.getString("order", "");
	    
	    if (order.equalsIgnoreCase("new")) {
	    	
	    	orderString="Delivery";
	    	
	    }
	    else if (order.equalsIgnoreCase("return")) {
	    	
	    	orderString="Return";
	    	
	    }
	    
      
		txtPrice = (TextView) findViewById(R.id.txtPrice);
		txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity);
		spnCategory = (Spinner) findViewById(R.id.spnCategory);
		spnProduct = (Spinner) findViewById(R.id.spnProduct);
		spnProductSpec = (Spinner) findViewById(R.id.spnProductSpec);
		spnScheme = (Spinner) findViewById(R.id.spnScheme);

		spnScheme.setVisibility(View.GONE);
		
		
		editTextRP = (TextView) findViewById(R.id.editTextRP);
		editTextMRP = (TextView) findViewById(R.id.editTextMRP);

		editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
		

		listProductSpec = new ArrayList<String>();
		 dataAdapterProductSpec = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listProductSpec);
		 dataAdapterProductSpec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		 listScheme = new ArrayList<String>();
		 dataAdapterScheme = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listScheme);
		 dataAdapterScheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 sp = EditSwipeOrderItems.this.getSharedPreferences("SimpleLogic", 0);
//		 TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//	        //question_value.setTypeface(null,Typeface.BOLD);
//
//	       welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
		

		Intent i = getIntent();
		String name = i.getStringExtra("retialer");
		if (i.hasExtra("data")) {
			//Log.e("data", "***********productList**********");
			productList=i.getParcelableArrayListExtra("productsList");
			//p=i.getParcelableExtra("product");
			index=Integer.parseInt(i.getStringExtra("productIndex"));
			p=productList.get(index);
			
		}
		
		final List<String> listCategory = new ArrayList<String>();
		listCategory.add(p.getCategory());
		
		final ArrayAdapter<String> dataAdapterCategory = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listCategory);
		dataAdapterCategory
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnCategory.setAdapter(dataAdapterCategory);
		spnCategory.setClickable(false);
		
		
		final List<String> listProduct = new ArrayList<String>();
		listProduct.add(p.getProductName());
		final ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, listProduct);
		//dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProduct.setAdapter(dataAdapterProduct);
        spnProduct.setClickable(false);
		
        categoryID=p.getCategoryID();
        productID=p.getProductID();
        /*Asynk task to load proaduct specs*/
        
        //myDbHelper = new DatabaseHandler(getApplicationContext());
        LoadProductVarientsAsyncTask loadProductVarientsAsyncTask=new LoadProductVarientsAsyncTask(this);
        loadProductVarientsAsyncTask.execute();
        
        quantity=Integer.parseInt(p.getProductQuantity());
        deleiveryQuantity=Integer.parseInt(p.getProductDeleiveryQuantity());
        totalprice=Float.parseFloat(p.getProducttotalPrice());
        txtPrice.setText("Total Price : " + String.format("%.2f", Float.parseFloat(p.getProducttotalPrice())));
        txtDeleiveryQuantity.setText(orderString+" Quantity : "+ p.getProductDeleiveryQuantity());
        
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

				}
				else {
					LoadProductPriceAsyncTask loadProductPriceAsyncTask=new LoadProductPriceAsyncTask(EditSwipeOrderItems.this);
					loadProductPriceAsyncTask.execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
        
        /*listProductSpec.clear();
		listProductSpec.add(p.getProductSpec());
		dataAdapterProductSpec.notifyDataSetChanged();
		dataAdapterProductSpec
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnProductSpec.setAdapter(dataAdapterProductSpec);*/
		
		editTextQuantity.setText(""+p.getProductQuantity());
		
		
		/*listScheme.clear();
		//listScheme.add("Select Scheme");
		listScheme.add(p.getProductScheme());

		dataAdapterScheme.notifyDataSetChanged();
		dataAdapterScheme
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnScheme.setAdapter(dataAdapterScheme);*/

		
		
		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8A0808")));

		actionBar.setTitle(name);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);*/
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
			mTitleTextView.setText(name);
			TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);


//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        //	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
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


		buttonAddMOre = (Button) findViewById(R.id.buttonAddMOre);
		buttonAddMOre.setVisibility(View.INVISIBLE);
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

			 			if (spnCategory.getSelectedItem().toString()
							.equalsIgnoreCase("Select Category")
							|| spnProduct.getSelectedItem().toString()
									.equalsIgnoreCase("Select Product")
							|| spnProductSpec.getSelectedItem().toString()
									.equalsIgnoreCase("Select Variant")
							|| editTextQuantity.getText().toString().length() == 0) {

						//Toast.makeText(getApplicationContext(),"Please Fill details ", Toast.LENGTH_SHORT).setGravity(Gravity.CENTER, 0, 0).show();
						Toast toast = Toast.makeText(getApplicationContext(),"Please Fill details ", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
					else {

						// TODO Auto-generated method stub
						// v.setBackgroundColor(Color.parseColor("#910505"));
						
						Product p = new Product();
						p.setCategory(category);
						p.setProductName(productName);
						p.setProductSpec(productSpec);
						p.setProductrp("" + rp);
						p.setProductmrp("" + mrp);
						p.setProductQuantity("" + quantity);
						p.setProductDeleiveryQuantity("" + deleiveryQuantity);
						p.setProducttotalPrice("" + totalprice);
						p.setProductScheme(scheme);

						productList.add(p);
						
						listCategory.clear();
						listCategory.add("Select Category");
						listCategory.add("AP Deo Spray");
						listCategory.add("57 GM Deo Stick");
						listCategory.add("30 ML Hand Sanitizer");
						dataAdapterCategory.notifyDataSetChanged();
						dataAdapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
						txtDeleiveryQuantity.setText(orderString+" Quantity :");
						
						

					
					}
				
			        
			        
			        return true;
			    }
			    return false;
			}
		});

		buttonPreviewOrder = (Button) findViewById(R.id.buttonPreviewOrder);
		buttonPreviewOrder.setText("Save");
		//buttonPreviewOrder.setVisibility(View.INVISIBLE);
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
						

			    	 AlertDialog alertDialog = new AlertDialog.Builder(EditSwipeOrderItems.this).create(); //Read Update
					    alertDialog.setTitle("Confirmation");
					    alertDialog.setMessage("Are you sure you want to Save?"); 
					    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub

								Intent i = new Intent(getApplicationContext(),
										PreviewOrderSwipeActivity.class);
								
								 if (orderString.equalsIgnoreCase("Return")) {
								    	
								    	i.putExtra("return", "return");
								    	
								    }
								 else if (orderString.equalsIgnoreCase("Delivery")) {
								    	
								    	i.putExtra("new", "new");
								    	
								    }
								 
								if (order.equalsIgnoreCase("previous")) {
									
									i.putExtra("previous", "previous");
								}
								
								
								Product p1 = new Product();
								p1.setCategory(p.getCategory());
								p1.setProductName(p.getProductName());
								p1.setProductSpec(productSpec);
								p1.setProductrp("" + rp);
								p1.setProductmrp("" + mrp);
								p1.setProductQuantity("" + quantity);
								p1.setProductDeleiveryQuantity("" + deleiveryQuantity);
								p1.setProducttotalPrice("" + totalprice);
								p1.setProductScheme(scheme);
								p1.setCategoryID(categoryID);
								p1.setProductID(productID);
								p1.setSchemeID(schemeID);

								//productList.remove(p);
								//productList.add(p1);
								
								productList.set(index, p1);
								
								i.putParcelableArrayListExtra("productsList", productList);

								SharedPreferences sp = EditSwipeOrderItems.this
										.getSharedPreferences("SimpleLogic", 0);

								i.putExtra("retialer",
										"" + sp.getString("RetailerName", ""));
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

								finish();
								EditSwipeOrderItems.this.startActivity(i);
								
								
								
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
						
						/*if (productList.size()!=0) {
							Intent i = new Intent(getApplicationContext(),
									PreviewOrderActivity.class);
							i.putParcelableArrayListExtra("productsList", productList);

							SharedPreferences sp = EditOrderItems.this
									.getSharedPreferences("SimpleLogic", 0);

							i.putExtra("retialer",
									"" + sp.getString("RetailerName", ""));
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

							finish();
							EditOrderItems.this.startActivity(i);
						}
						
						else {
							//Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();
							
							Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}*/

					
			        return true;
			    }
			    return false;
			}
		});

		//editTextQuantity.setText("");

		editTextQuantity.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (editTextQuantity.getText().toString().length() != 0) {
					quantity = Integer.parseInt(editTextQuantity.getText()
							.toString());
					totalprice = productprice * quantity;
					txtPrice.setText("Total Price : " + String.format("%.2f", totalprice));
//					deleiveryQuantity = quantity;
//					txtDeleiveryQuantity.setText(orderString+" Quantity : "
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
							txtDeleiveryQuantity.setText(orderString+" Quantity : "
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
							txtDeleiveryQuantity.setText(orderString+" Quantity : "
									+ deleiveryQuantity);
						}
						
						
					}
					
				}

				return false;
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
						txtDeleiveryQuantity.setText(orderString+" Quantity : "+ deleiveryQuantity);
				}
					/*LoadProductIDsTask loadProductIDsTask=new LoadProductIDsTask(EditSwipeOrderItems.this);
					loadProductIDsTask.execute();*/
					//txtDeleiveryQuantity.setText(orderString+" Quantity : "+deleiveryQuantity);

				}
				
				else {
					schemeID=dbschemeID;
					scheme=parent.getItemAtPosition(pos).toString();
					//Log.e("DATA scheme", scheme);
					String [] aray=scheme.split("and");
					int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
					int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
					if (editTextQuantity.getText().toString().length()!=0) {
						quantity=Integer.parseInt(editTextQuantity.getText().toString());
						int extra = quantity / buy;
						deleiveryQuantity = extra*get + quantity;
						txtDeleiveryQuantity.setText(orderString+" Quantity : "
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
			this.finish();
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
		buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
			
			Intent i=new Intent(getApplicationContext(), PreviewOrderSwipeActivity.class);
			 if (orderString.equalsIgnoreCase("Return")) {
			    	
			    	i.putExtra("return", "return");
			    	
			    }
			 
			 else if (orderString.equalsIgnoreCase("Delivery")) {
			    	
			    	i.putExtra("new", "new");
			    	
			    }
			 
			if (order.equalsIgnoreCase("previous")) {
				
				i.putExtra("previous", "previous");
			}
			
			
			
			//productList.add(p);
			 i.putParcelableArrayListExtra("productsList", productList);
			 
			 
			 SharedPreferences  sp=EditSwipeOrderItems.this.getSharedPreferences("SimpleLogic", 0);

				i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				finish();
				this.startActivity(i);
			 
		
		
		
		 
		
	}
	
	
	public class LoadProductVarientsAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		//private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;
		ArrayList<Products_Mine> productVarients = new ArrayList<Products_Mine>();

		public LoadProductVarientsAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			//dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			/*this.dialog.setMessage("Loading Variants");
			this.dialog.show();*/
			listProductSpec.clear();
			//listProductSpec.add("Select Variant");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				
				//  productVarients=(ArrayList<String>) myDbHelper.loadVarients(productID);
 
				Products_Mine products_Mine1 = new Products_Mine("57 GM", 1);
				Products_Mine products_Mine2 = new Products_Mine("150 ML", 2);
				Products_Mine products_Mine3 = new Products_Mine("15 ML", 3);
				Products_Mine products_Mine4 = new Products_Mine("30 ML", 4);
				Products_Mine products_Mine5 = new Products_Mine("250 ML", 5);
				Products_Mine products_Mine6 = new Products_Mine("(70+28) GM", 6);
				Products_Mine products_Mine7 = new Products_Mine("300 ML", 7);
				Products_Mine products_Mine8 = new Products_Mine("150 ML", 8);
				
				productVarients.add(products_Mine1);
				productVarients.add(products_Mine2);
				productVarients.add(products_Mine3);
				productVarients.add(products_Mine4);
				productVarients.add(products_Mine5);
				productVarients.add(products_Mine6);
				productVarients.add(products_Mine7);
				productVarients.add(products_Mine8);
				 
				 for(int i = 0 ;i< productVarients.size();i++){
					 
					 if(productID == productVarients.get(i).getProductId()){
						 
						 listProductSpec.add(productVarients.get(i).getProductName());
						 
					 }
				 }
					
					
				/*	
				 for (Iterator iterator = productVarients.iterator(); iterator
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
			/*if (dialog.isShowing()) {
				dialog.dismiss();
				
			}*/
			
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
				//databaseModel= myDbHelper.loadProductsPrices(categoryID,productID,spnProductSpec.getSelectedItem().toString());
						
						//schemeId.add(Integer.valueOf(databaseModel.getSchemeId()));
						//listScheme.add(databaseModel.getSchemeId());
				
Product product1 = new Product("AP Deodorants", "RHYTHM APD", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","178.50", 1, 1,1);
				
				Product product2 = new Product("AP Deodorants", "VIVA APD", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","138.50", 1, 2,1);
				Product product3= new Product("AP Deodorants", "ICON APD", "100ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","158.50", 1, 3,1);
				Product product4 = new Product("AP Deodorants", "ODYSSEY APD", "130ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","168.50", 1, 4,1);
				Product product5 = new Product("AP Deodorants", "IGNITE AFD", "180ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","178.50", 1, 5,1);
				Product product6 = new Product("AP Deodorants", "VOGUE AFD", "130ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","168.50", 1, 6,1);
				Product product7 = new Product("AP Deodorants", "ATLAS AFD", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","158.50", 1, 7,1);
				Product product8 = new Product("AP Deodorants", "ALEXA AFD", "180ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","148.50", 1, 8,1);
				Product product9 = new Product("AP Deodorants", "TITAN AFD", "100ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","178.50", 1, 9,1);
				Product product10 = new Product("AP Deodorants", "REBEL AFD", "190ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","188.50", 1, 10,1);
				 Product product11 = new Product("AP Deodorants", "AMAZON AFD", "170ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","148.50", 1, 11,1);
				Product product12 = new Product("AP Deodorants", "KNIGHT AFD", "80ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","148.50", 1, 12,1);
				Product product13 = new Product("AP Deodorants", "BLUSH AFD", "170ML","", "productDeleiveryQuantity","productScheme", "productrp", "290.00","178.50", 1, 13,1);
				Product product14 = new Product("AP Deodorants", "ADORE AFD", "190ML","", "productDeleiveryQuantity","productScheme", "productrp", "220.00","168.50", 1, 14,1);
				Product product15 = new Product("AP Deodorants", "OASIS AFD", "100ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","178.50", 1, 15,1);
				Product product16 = new Product("AP Deodorants", "MIST AFD", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","168.50", 1, 16,1);
				Product product17 = new Product("AP Deodorants", "AP 57 RHYTHM", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","158.50", 1, 17,1);
				Product product18 = new Product("AP Deodorants", "3 PACK - TB/N/CV", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","158.50", 1, 18,1);
				Product product19 = new Product("AP Deodorants", "6 PACK (with 2 Bag Tag)", "170ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","138.50", 1, 19,1);
				Product product20 = new Product("AP Deodorants", "6 PACK", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "290.00","178.50", 1, 20,1);
				Product product21 = new Product("AP Deodorants", "AP 57 VIVA", "180ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","138.50", 1, 21,1);
				Product product22 = new Product("AP Deodorants", "AP 57 ICON", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","118.50", 1, 22,1);
				Product product23 = new Product("AP Deodorants", "AP 15 RHYTHM", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","128.50", 1, 23,1);
				Product product24 = new Product("AP Deodorants", "AP 15 VIVA", "130ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","138.50", 1, 24,1);
				Product product25 = new Product("AP Deodorants", "AP 15 ICON", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","148.50", 1, 25,1);
				Product product26 = new Product("AP Deodorants", "AP 15 ODYSSEY", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","158.50", 1, 26,1);
				Product product27 = new Product("AP Deodorants", "TB 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "220.00","168.50", 1, 27,1);
				Product product28 = new Product("AP Deodorants", "CL 30", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","178.50", 1, 28,1);
				Product product29 = new Product("AP Deodorants", "CV 30", "130ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","188.50", 1, 29,1);
				Product product30 = new Product("AP Deodorants", "CM 30", "130ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","198.50", 1, 30,1);
				Product product31 = new Product("AP Deodorants", "NATURAL 30", "160ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","188.50", 1, 31,1);
				Product product32 = new Product("AP Deodorants", "BC 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","178.50", 1, 32,1);
				Product product33 = new Product("AP Deodorants", "MANGO 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","168.50", 1, 33,1);
				Product product34 = new Product("AP Deodorants", "MM 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","158.50", 1, 34,1);
				Product product35 = new Product("AP Deodorants", "ORANGE 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","148.50", 1, 35,1);
				Product product36 = new Product("AP Deodorants", "GA 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","148.50", 1, 36,1);
				Product product37 = new Product("AP Deodorants", "STRAWBERRY 30", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","158.50", 1, 37,1);
				Product product38 = new Product("AP Deodorants", "WW 3 PACK", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","178.50", 1, 38,1);
				Product product39 = new Product("AP Deodorants", "MRW 3 PACK", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","148.50", 1, 39,1);
				Product product40 = new Product("AP Deodorants", "3 PACK - CL/N/CM", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "290.00","178.50", 1, 40,1);
				Product product41 = new Product("AP Deodorants", "BF SANDAL", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","138.50", 1, 41,1);
				Product product42 = new Product("AP Deodorants", "BF ROSE", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","128.50", 1, 42,1);
				Product product43 = new Product("AP Deodorants", "BF LAVENDER", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","118.50", 1, 43,1);
				Product product44 = new Product("AP Deodorants", "SC MUSK", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","138.50", 1, 44,1);
				Product product45 = new Product("AP Deodorants", "SC CITRUS", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","132.50", 1, 45,1);
				Product product46 = new Product("AP Deodorants", "SC AQUA", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "220.00","143.50", 1, 46,1);
				Product product47 = new Product("AP Deodorants", "STRAWBEERRY 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","158.50", 1, 47,1);
				Product product48 = new Product("AP Deodorants", "GA 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","175.50", 1, 48,1);
				Product product49 = new Product("AP Deodorants", "ORNAGE 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","175.50", 1, 49,1);
				Product product50 = new Product("AP Deodorants", "MM 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","174.50", 1, 50,1);
				Product product51 = new Product("AP Deodorants", "MANGO 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","173.50", 1, 51,1);
				Product product52 = new Product("AP Deodorants", "BC 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","173.50", 1, 52,1);
				Product product53 = new Product("AP Deodorants", "NATURAL 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "220.00","175.50", 1, 53,1);
				Product product54 = new Product("AP Deodorants", "CM 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","178.50", 1, 54,1);
				Product product55 = new Product("AP Deodorants", "CV 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","176.50", 1, 55,1);
				Product product56 = new Product("AP Deodorants", "TB 250", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","175.50", 1, 56,1);
				Product product57 = new Product("Foot Spray", "FOOT SOUL", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","176.50", 1, 57,1);
				Product product58 = new Product("Wet Wipes", "WW CITRUS", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","156.50", 1, 58,1);
				Product product59 = new Product("Wet Wipes", "WW ALOE", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","166.50", 1, 59,1);
				Product product60 = new Product("Soap", "AQUA SOAP", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "290.00","177.50", 1, 60,1);
				Product product61 = new Product("Soap", "COLOGNE SOAP", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "290.00","179.50", 1, 61,1);
				Product product62 = new Product("FW + SOAP", "FW + SOAP", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "280.00","132.50", 1, 62,1);
				Product product63 = new Product("Talc", "AQUA TALC", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "270.00","143.50", 1, 63,1);
				Product product64 = new Product("Talc", "IGNITE TALC", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "260.00","1548.50", 1, 64,1);
				Product product65 = new Product("Talc Combo", "C+A TALC COMBO", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "250.00","122.50", 1, 65,1);
				Product product66 = new Product("Talc Combo", "C+I TALC COMBO", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "240.00","124.50", 1, 66,1);
				Product product67 = new Product("Talc Combo", "I+A TALC COMBO", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "230.00","156.50", 1, 67,1);
				Product product68 = new Product("AFD Combo", "AFD COMBO M", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "220.00","143.50", 1, 68,1);
				Product product69 = new Product("AFD Combo", "AFD COMBO W", "150ML","", "productDeleiveryQuantity","productScheme", "productrp", "210.00","123.50", 1, 69,1);
				 
				
				
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
				dataProducts.add(product24);
				dataProducts.add(product25);
				dataProducts.add(product26);
				dataProducts.add(product27);
				dataProducts.add(product28);
				dataProducts.add(product29);
				dataProducts.add(product30);
				dataProducts.add(product31);
				dataProducts.add(product32);
				dataProducts.add(product33);
				dataProducts.add(product34);
				dataProducts.add(product35);
				dataProducts.add(product36);
				dataProducts.add(product37);
				dataProducts.add(product38);
				dataProducts.add(product39);
				dataProducts.add(product40);
				dataProducts.add(product41);
				dataProducts.add(product42);
				dataProducts.add(product43);
				dataProducts.add(product44);
				dataProducts.add(product45);
				dataProducts.add(product46);
				dataProducts.add(product47);
				dataProducts.add(product48);
				dataProducts.add(product49);
				dataProducts.add(product50);
				dataProducts.add(product51);
				dataProducts.add(product52);
				dataProducts.add(product53);
				dataProducts.add(product54);
				dataProducts.add(product55);
				dataProducts.add(product56);
				dataProducts.add(product57);
				dataProducts.add(product58);
				dataProducts.add(product59);
				dataProducts.add(product60);
				dataProducts.add(product61);
				dataProducts.add(product62);
				dataProducts.add(product63);
				dataProducts.add(product64);
				dataProducts.add(product65);
				dataProducts.add(product66);
				dataProducts.add(product67);
				dataProducts.add(product68);
				dataProducts.add(product69); 
				 
				
				Scheme scheme = new Scheme(1, "Buy 6 and Get 2 Free", "S001", "Y");
				dataScheme.add(scheme);	
				
			for (int i = 0; i < dataScheme.size(); i++) {
				
				if(dataProducts.get(i).getProductID() == dataScheme.get(i).getScheme_id()){
					
					listScheme.add(dataScheme.get(i).getScheme_desc());
					
					rp = Float.parseFloat(dataProducts.get(i).getProducttotalPrice());
					mrp =Float.parseFloat(dataProducts.get(i).getProductmrp());
					productprice = rp;
					dbschemeID=dataProducts.get(i).getSchemeID();
				}
				
			}
				
				
				
				/*
				rp = databaseModel.getRp();
				mrp = databaseModel.getMrp();
				productprice = rp;*/
				
				
				/*//listScheme.add("Select Scheme");
				dbschemeID=databaseModel.getSchemeId();
				listScheme.add(myDbHelper.loadSchemes(databaseModel.getSchemeId()));*/
					
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
				txtDeleiveryQuantity.setText(orderString+" Quantity : "
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
				productID=databaseModel.getId();
				
				categoryID=databaseModel.getCategoryId();
				
				schemeID=databaseModel.getSchemeId();
					
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
			
			
			
			
			
			
			
		}
	}

	
}
