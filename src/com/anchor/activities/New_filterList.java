package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class New_filterList extends Activity implements OnItemSelectedListener{ 
	Button filter_btn, cable1,cable2,cable3,cable4,cable5;
	ListView alllist;
	String s = "";
	EditText search_filter;
	ArrayAdapter adapter;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	
	 ArrayList <String> product_value = new ArrayList<String>();
	 ArrayList <String> search_item_value = new ArrayList<String>();
	 ArrayList <String> searchResults = new ArrayList<String>();
	 
	 String[] hpModels;
		String[] hclModels ;
		String[] lenovoModels ;
		String[] sonyModels ;
		String[] dellModels ;
		String[] samsungModels;
		List<String> Secondary_Category = new ArrayList<String>();
		List<String> Secondary_Sub_Category = new ArrayList<String>();
		List<String> Size1 = new ArrayList<String>();
		List<String> Size2 = new ArrayList<String>();
		List<String> Voltage_Watts_Amps = new ArrayList<String>();
		List<String> Colour = new ArrayList<String>();
		List<String> Metal_Aluminum_Wt = new ArrayList<String>();
		List<String> Metal_Copper_Wt = new ArrayList<String>();
		List<String> Product_Weight = new ArrayList<String>();
		List<String> Bending_Radius = new ArrayList<String>();
		List<String> PLANNING_MAKE_BUY_CODE = new ArrayList<String>();
		Button short_record;
	 // Array of strings...
	
    private ArrayList<HashMap<String, String>> dataArrayList;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_filterlist);
		alllist = findViewById(R.id.list_all);
		 short_record = findViewById(R.id.short_recordn);
		
		 GetListFilter();
		
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
	            	search_item_value.clear();
	                 getDialog(value.trim());
				      
//				      Intent i = new Intent(New_filterList.this,Invoice_filterList.class);
//				      //Global_Data.GLOVEL_INVOICE_VALUE = value.trim();
////		             
//		                startActivity(i);
//		                finish();
	               
	            }

	        });
		
		short_record.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

               		Global_Data.GLOVEL_FILTER_FLAG = "TRUE";
            		//Global_Data.GLOVEL_INVOICE_VALUE = "";
            		Intent i = new Intent(New_filterList.this,Invoice_filterList.class);
        		 	startActivity(i);
        		 	finish();
  
            }

        });
		
		//filter_btn = (Button) findViewById(R.id.filter_btn);
		
		
		 dataArrayList=new ArrayList<HashMap<String, String>>();
		 
		 if(adapter== null)
		 {
			 //filter_btn.setEnabled(false); 
		 }
		 else
		 {
			// filter_btn.setEnabled(true);  
		 }
//		 
//		    product_value.add("Product 1");
//	        product_value.add("Product 2");
//	        product_value.add("Product 3");
//	        product_value.add("Product 4");
//	        product_value.add("Product 5");
//	        product_value.add("Product 6");
//	        product_value.add("Product 7");
	        
	       
	          
//	           buttonClick = new OnClickListener() {
//	              public void onClick(View v) {
//	                  String idxStr = (String)v.getTag();
//			        	Toast.makeText(Filter_List.this, idxStr,Toast.LENGTH_SHORT).show();
//
//	                  //tv.setText(idxStr);
//	              }
//	          };
		
		
		
		search_filter = findViewById(R.id.search_filter);
		
		search_filter.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //get the text in the EditText
                String searchString=search_filter.getText().toString();
                int textLength=searchString.length();
                searchResults.clear();

                for(int i=0;i<product_value.size();i++)
                {
                    String playerName= product_value.get(i);
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
			TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText("Filter");
			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = New_filterList.this.getSharedPreferences("SimpleLogic", 0);

//       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//       //	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		   todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
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
    
    @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		// Global_Data.GLOVEL_LONG_DESC = ""; 
		 //Global_Data.GLOVEL_SubCategory_Button = "";
    	 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    	 Intent i = new Intent(New_filterList.this,Invoice_filterList.class);
	     startActivity(i);
          finish();
		
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
	
	public void GetListFilter()
	{
		//List<Local_Data> contacts1 = dbvoc.ITEM_List(name);
	        
		product_value.add("Secondary Category");
		product_value.add("Secondary Sub Category");
		product_value.add("Size1");
		product_value.add("Size2");
		product_value.add("Voltage Watts Amps");
		product_value.add("Colour");
		product_value.add("Metal Aluminum Wt");
		product_value.add("Metal Copper Wt");
		product_value.add("Product Weight");
		product_value.add("Bending Radius");
		product_value.add("PLANNING_MAKE_BUY_CODE");
  	
      	    searchResults=new ArrayList<String>(product_value);
			adapter = new ArrayAdapter<String>(New_filterList.this, R.layout.filtertxt, searchResults);
			adapter.notifyDataSetChanged();
			alllist.setAdapter(adapter);
	}
	
	public void getDialog(String value)
	{
		
		if (value.equalsIgnoreCase("Secondary Category")) 
		{
			 
//		        
			 s = "Secondary_Category";
		} 
		else
		if (value.equalsIgnoreCase("Secondary Sub Category")) 
		{
			
//			        
			 s = "Secondary_Sub_Category";
		} 	
		else
		if (value.equalsIgnoreCase("Size1")) 
		{
			s = "Size1";
		} 	
		
		else
		if (value.equalsIgnoreCase("Size2")) 
		{
			s = "Size2";
		} 
		else
		if (value.equalsIgnoreCase("Voltage Watts Amps")) 
		{
			s = "Voltage_Watts_Amps";
		 } 	
		else
		if (value.equalsIgnoreCase("Colour")) 
		{
			s = "Colour";
		} 	
	
		else
		if (value.equalsIgnoreCase("Metal Aluminum Wt")) 
		{
			s = "Metal_Aluminum_Wt";
		 } 	
	
		else
		if (value.equalsIgnoreCase("Metal Copper Wt")) 
		{
			s = "Metal_Copper_Wt";
		 } 	
	
		else
		if (value.equalsIgnoreCase("Product Weight")) 
		{
			s = "Product_Weight";
		 } 	
		else
		if (value.equalsIgnoreCase("Bending Radius")) 
		{
			s = "Bending_Radius";
		 }
		else
		if (value.equalsIgnoreCase("PLANNING_MAKE_BUY_CODE")) 
		{
			s = "PLANNING_MAKE_BUY_CODE";
		 } 	
		
		
		 // Intialize  readable sequence of char values
		List<Local_Data> contacts1 = dbvoc.getItemProductData(s,Global_Data.GLOVEL_INVOICE_VALUE);
//        
	     for (Local_Data cn : contacts1) 
	     {
	    	 if(!search_item_value.contains(cn.get_travel_text()) && !cn.get_travel_text().equalsIgnoreCase(""))
	    	 {
	    		 search_item_value.add(cn.get_travel_text());
	    	 }
	    	
	     }
        final CharSequence[] dialogList=  search_item_value.toArray(new CharSequence[search_item_value.size()]);
        final AlertDialog.Builder builderDialog = new AlertDialog.Builder(New_filterList.this);
        builderDialog.setTitle("Select ");
        int count = dialogList.length;
        boolean[] is_checked = new boolean[count]; // set is_checked boolean false;

        // Creating multiple selection by using setMutliChoiceItem method
        builderDialog.setMultiChoiceItems(dialogList, is_checked,
                new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton, boolean isChecked) {
                    }
                });
        
        
        
        builderDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ListView list = ((AlertDialog) dialog).getListView();
                // make selected item in the comma seprated string
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < list.getCount(); i++) {
                    boolean checked = list.isItemChecked(i);

                    if (checked) {
                        if (stringBuilder.length() > 0) stringBuilder.append(",");
                        stringBuilder.append(list.getItemAtPosition(i));

                    }
                }

                /*Check string builder is empty or not. If string builder is not empty.
                  It will display on the screen.
                 */
                if (stringBuilder.toString().trim().equals("")) {

                    //((TextView) findViewById(R.id.text)).setText("Click here to open Dialog");
                    stringBuilder.setLength(0);

                } else {

                   // ((TextView) findViewById(R.id.text)).setText(stringBuilder);
                	List<String> list2 = Arrays.asList(stringBuilder.toString());
                	//Toast.makeText(getApplicationContext(), stringBuilder, Toast.LENGTH_LONG).show();
                	Global_Data.map.put(s,list2);
                }
            }
        });

        builderDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // ((TextView) findViewById(R.id.text)).setText("Click here to open Dialog");
                    }
                });
        AlertDialog alert = builderDialog.create();
        alert.show();
	}
	
	
}
