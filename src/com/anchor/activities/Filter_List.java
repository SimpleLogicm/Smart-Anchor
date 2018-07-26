package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Filter_List extends Activity implements OnItemSelectedListener{ 
	Button filter_btn, cable1,cable2,cable3,cable4,cable5;
	ListView alllist;
	ArrayAdapter adapter;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	 // Array of strings...
	
    private ArrayList<HashMap<String, String>> dataArrayList;
	String[] Cable1Array = {"ARIAL BUNCHED CABLE","CONTROL CABLE","EXTRA HIGH VOLTAGE","INSTRUMENTS CABLES","POWER CABLES","SERVICE CABLES","SOLAR CABLES","TELEPHONE CABLES(ARM)","THERMOCOUPLE/COMPENSATING"};
	String[] Cable2Array = {"BUILDING MANAGEMENT SYSTEM","FLEXIBLE-MULTI CORE","FLEXIBLE-SINGLE CORE","FLEXIBLE-TWIN TWISTED","OPTICAL FIBRE CABLE","RUBBER CABLE","WELDING CABLE"};
	String[] Cable3Array = {"COXIAL CABLE","HOUSE WIRE 180M","HOUSE WIRE 180MPP","HOUSE WIRE 200MPP","HOUSE WIRE 300MPP","HOUSE WIRE 90M","SPEAKER CABLE","SUBMERSIBLE","SWITCHBOARD CABLE","TWIN FLAT"};
	String[] Cable4Array = {"X"};
	 ArrayList <String> product_value = new ArrayList<String>();
	 ArrayList <String> searchResults = new ArrayList<String>();
	EditText search_filter;
	private LinearLayout brand_subcategaries;
	//OnClickListener buttonClick;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.filter_list);
		alllist = (ListView) findViewById(R.id.list_all);
		
		Global_Data.GLOVEL_FILTER_FLAG = "";
		//cable1 = (Button) findViewById(R.id.subcat1);
		//cable2 = (Button) findViewById(R.id.subcat2);
		//cable3 = (Button) findViewById(R.id.subcat3);
		//cable4 = (Button) findViewById(R.id.subcat4);
		//cable5 = (Button) findViewById(R.id.subcat5);
		
		alllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {

	                //String contactId = (TextView) view.findViewById(R.id.label);
	            	String value = (String)parent.getItemAtPosition(position); 
	                // Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
//	                 List<Local_Data> contacts1 = dbvoc.ITEM_description(value.trim());
////				        
//				      for (Local_Data cn : contacts1) 
//				      {
//				    	  Global_Data.GLOVEL_LONG_DESC = cn.getVariant().toString().trim();
//				      }
				      
				      Intent i = new Intent(Filter_List.this,Invoice_filterList.class);
				      Global_Data.GLOVEL_INVOICE_VALUE = value.trim();
//		             
		                startActivity(i);
		                //finish();
	               
	            }

	        });
		
		//filter_btn = (Button) findViewById(R.id.filter_btn);
		search_filter = (EditText) findViewById(R.id.search_filter);
		brand_subcategaries = (LinearLayout) findViewById(R.id.brand_subcategaries);
		
		 dataArrayList=new ArrayList<HashMap<String, String>>();
		 
		 if(adapter== null)
		 {
			 search_filter.setEnabled(false); 
		 }
		 else
		 {
			 search_filter.setEnabled(true);  
		 }
//		 
//		    product_value.add("Product 1");
//	        product_value.add("Product 2");
//	        product_value.add("Product 3");
//	        product_value.add("Product 4");
//	        product_value.add("Product 5");
//	        product_value.add("Product 6");
//	        product_value.add("Product 7");
	        
	        List<Local_Data> contacts1 = dbvoc.Primary_CategoryITEM(Global_Data.GLOVEL_CATEGORY_NAME);
	        
	          for (Local_Data cn : contacts1) 
	          {
	        	  Log.d("global sub category", "category"+Global_Data.GLOVEL_SubCategory_Button);
	        	  Button btn = new Button(this);
	        	  if(Global_Data.GLOVEL_SubCategory_Button.equalsIgnoreCase(cn.getStateName()))
	              {
	        		  btn.setText(cn.getStateName());
	        		  //btn.setBackgroundColor(Color.GRAY);
	        		  btn.setEnabled(false);
	        		  Log.d("global sub category", "category in"+Global_Data.GLOVEL_SubCategory_Button);
	        		 // filter_btn.setEnabled(true);
	        		 // filter_btn.setClickable(true);
	        		  GetList(cn.getStateName());
	              }
	        	  else
	        	  {
	        		  btn.setText(cn.getStateName());
	        		  btn.setEnabled(true);
	        		  //btn.setBackgroundResource(android.R.drawable.btn_default);
	        	  }
	        	  
	              btn.setOnClickListener(buttonClick);
	              brand_subcategaries.addView(btn);
	              int idx = brand_subcategaries.indexOfChild(btn);
	              btn.setTag(Integer.toString(idx));
	        	
	              
	          }
	          
//	           buttonClick = new OnClickListener() {
//	              public void onClick(View v) {
//	                  String idxStr = (String)v.getTag();
//			        	Toast.makeText(Filter_List.this, idxStr,Toast.LENGTH_SHORT).show();
//
//	                  //tv.setText(idxStr);
//	              }
//	          };
		
		search_filter.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=search_filter.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<product_value.size();i++)
                {
                    String playerName=product_value.get(i).toString();
                    if(textLength<=playerName.length()){
                        //compare the String in EditText with Names in the ArrayList
                        if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
                            searchResults.add(product_value.get(i));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {


            }
        });
		
		search_filter = (EditText) findViewById(R.id.search_filter);
		
		
//		filter_btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Global_Data.GLOVEL_RETURN_FLAG="FILTER_LIST";
//				Intent intent = new Intent(getApplicationContext(), ExpandFilter.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//				
//	    	}
//		});
		
//		cable1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				 searchResults=new ArrayList<String>(product_value);
//				adapter = new ArrayAdapter<String>(Filter_List.this, R.layout.filtertxt, searchResults);
//				adapter.notifyDataSetChanged();
//				alllist.setAdapter(adapter);
//				
//	    	}
//		});
	
//		cable2.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				adapter = new ArrayAdapter<String>(Filter_List.this, R.layout.filtertxt, Cable2Array);
//				adapter.notifyDataSetChanged();
//				alllist.setAdapter(adapter);
//	    	}
//		});
	
//		cable3.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				adapter = new ArrayAdapter<String>(Filter_List.this, R.layout.filtertxt, Cable3Array);
//				adapter.notifyDataSetChanged();
//				alllist.setAdapter(adapter);
//	    	}
//		});
	
//		cable4.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				adapter = new ArrayAdapter<String>(Filter_List.this, R.layout.filtertxt, Cable4Array);
//				adapter.notifyDataSetChanged();
//				alllist.setAdapter(adapter);
//			}
//		});

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
			TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText("Filter");

			TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = Filter_List.this.getSharedPreferences("SimpleLogic", 0);

//       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//      // 	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		   todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
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
//       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
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
	
	OnClickListener buttonClick = new OnClickListener() {
        public void onClick(View v) {
        	
        	for(int i=0; i<brand_subcategaries.getChildCount(); i++){
        	    if(brand_subcategaries.getChildAt(i) instanceof Button)
        	        ((Button)brand_subcategaries.getChildAt(i)).
        	        setEnabled(true);
        	    //b.setEnabled(false);
        	}
        	
        	 Button b = (Button)v;
        	
        	// b.setBackgroundColor(Color.GRAY);
        	 b.setEnabled(false);
        
        	 Global_Data.GLOVEL_SubCategory_Button = b.getText().toString();
        	 String buttonText = b.getText().toString();
        	//Toast.makeText(Filter_L ist.this, buttonText,Toast.LENGTH_SHORT).show();
        	 Log.d("Sub Categary name", buttonText);
        	 
        	 Global_Data.GLOVEL_SUBCATEGORY_NAME = buttonText.toString().trim();
        	 
        	 product_value.clear();
        	 
        	 List<Local_Data> contacts1 = dbvoc.ITEM_List(buttonText.toString().trim());
 	        
	          for (Local_Data cn : contacts1) 
	          {
	        	 
	        	  //product_value.add(cn.getVariant()+" "+cn.getStateName());
	        	  product_value.add(cn.getStateName());
	              
	          }
        	
	        	searchResults=new ArrayList<String>(product_value);
				adapter = new ArrayAdapter<String>(Filter_List.this, R.layout.filtertxt, searchResults);
				adapter.notifyDataSetChanged();
				alllist.setAdapter(adapter);
				
				if(adapter== null)
				 {
					 search_filter.setEnabled(false); 
				 }
				 else
				 {
					 search_filter.setEnabled(true);  
				 }

        }
    };
    
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		 Global_Data.GLOVEL_LONG_DESC = ""; 
		 Global_Data.GLOVEL_SubCategory_Button = "";
		 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		 
		  Intent i = new Intent(Filter_List.this,NewOrderActivity.class);
	      startActivity(i);
		  this.finish();
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stubs
		super.onResume();
		
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
	
	public void GetList(String name)
	{
		List<Local_Data> contacts1 = dbvoc.ITEM_List(name);
	        
        for (Local_Data cn : contacts1) 
        {
      	 
      	  //product_value.add(cn.getVariant()+" "+cn.getStateName());
      	  product_value.add(cn.getStateName());
            
        }
  	
      	    searchResults=new ArrayList<String>(product_value);
			adapter = new ArrayAdapter<String>(Filter_List.this, R.layout.filtertxt, searchResults);
			adapter.notifyDataSetChanged();
			alllist.setAdapter(adapter);
	}
}
