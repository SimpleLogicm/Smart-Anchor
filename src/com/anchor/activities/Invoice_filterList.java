package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoice_filterList extends Activity implements OnItemSelectedListener{ 
	Button filter_btn, cable1,cable2,cable3,cable4,cable5;
	ListView alllist;
	ArrayAdapter adapter;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	 // Array of strings...

    private ArrayList<HashMap<String, String>> dataArrayList;

	 ArrayList <String> product_value = new ArrayList<String>();
	 ArrayList <String> searchResults = new ArrayList<String>();
	EditText search_filter;
	private LinearLayout brand_subcategaries;
	
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
	
	private String Secondary_Category_f = "";
	private String Secondary_Sub_Category_f = "";
	private String Size1_f = "";
	private String Size2_f = "";
	private String Voltage_Watts_Amps_f = "";
	private String Colour_f = "";
	private String Metal_Aluminum_Wt_f = "";
	private String Metal_Copper_Wt_f = "";
	private String Product_Weight_f = "";
	private String Bending_Radius_f = "";
	private String PLANNING_MAKE_BUY_CODE_f = "";
	
	//OnClickListener buttonClick;
	
	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_invoicedetail_list);
		alllist = findViewById(R.id.list_all);
		//cable1 = (Button) findViewById(R.id.subcat1);
		//cable2 = (Button) findViewById(R.id.subcat2);
		//cable3 = (Button) findViewById(R.id.subcat3);
		//cable4 = (Button) findViewById(R.id.subcat4);
		//cable5 = (Button) findViewById(R.id.subcat5);
		
		search_filter = findViewById(R.id.search_filter);
		
		if(Global_Data.GLOVEL_FILTER_FLAG == "TRUE")
		{	
			// iterate and display values
	        System.out.println("Fetching Keys and corresponding [Multiple] Values n");
	        for (Map.Entry<String, List<String>> entry : Global_Data.map.entrySet()) 
	        {
	            String key = entry.getKey();
	            List<String> values = entry.getValue();
	            System.out.println("Key = " + key);
	            if(key.equalsIgnoreCase("Secondary_Category"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Secondary_Category.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Secondary_Sub_Category"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Secondary_Sub_Category.add(value);
	            	 }
	            	
	            }
	            
	            
	            if(key.equalsIgnoreCase("Size1"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Size1.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Size2"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Size2.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Voltage_Watts_Amps"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Voltage_Watts_Amps.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Colour"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Colour.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Metal_Aluminum_Wt"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Metal_Aluminum_Wt.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Metal_Copper_Wt"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Metal_Copper_Wt.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Product_Weight"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Product_Weight.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("Bending_Radius"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 Bending_Radius.add(value);
	            	 }
	            	
	            }
	            
	            if(key.equalsIgnoreCase("PLANNING_MAKE_BUY_CODE"))
	            {
	            	 System.out.println("Values = " + values + "n");
	            	 
	            	 for(String value : values)
	            	 {
	            		 PLANNING_MAKE_BUY_CODE.add(value);
	            	 }
	            	
	            }
	           
	        }
	        
	        Log.d("Secondary_Category","Secondary_Category"+Arrays.asList(Secondary_Category));
	        
	        System.out.println(Arrays.toString(Secondary_Category.toArray()));
	        StringBuilder commaSepValueBuilder = new StringBuilder();

	        try {

	            for (int k = 0; k < Secondary_Category.size(); k++) {
	                int kk = Secondary_Category.size();

	                System.out.println("splitting test" + Secondary_Category.get(k));
	                commaSepValueBuilder.append(Secondary_Category.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Secondary_Category.size()-1){
	                    commaSepValueBuilder.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Secondary_Category_f = commaSepValueBuilder.toString();
	            Log.d("Secondary_Category_f","Secondary_Category_f"+ Secondary_Category_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        Log.d("Secondary_Sub_Category","Secondary_Sub_Category"+Arrays.asList(Secondary_Sub_Category));
	        
	        System.out.println(Arrays.toString(Secondary_Sub_Category.toArray()));
	        StringBuilder commaSepValueBuilder1 = new StringBuilder();

	        try {

	            for (int k = 0; k < Secondary_Sub_Category.size(); k++) {
	                int kk = Secondary_Sub_Category.size();

	                System.out.println("splitting test" + Secondary_Sub_Category.get(k));
	                commaSepValueBuilder1.append(Secondary_Sub_Category.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Secondary_Sub_Category.size()-1){
	                    commaSepValueBuilder1.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Secondary_Sub_Category_f = commaSepValueBuilder1.toString();
	            Log.d("Secory_Sub_Categ_f","Secondary_Sub_Category_f"+ Secondary_Sub_Category_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        Log.d("Size1","Size1"+Arrays.asList(Size1));
	        
	        System.out.println(Arrays.toString(Size1.toArray()));
	        StringBuilder commaSepValueBuilder2 = new StringBuilder();

	        try {

	            for (int k = 0; k < Size1.size(); k++) {
	                int kk = Size1.size();

	                System.out.println("splitting test" + Size1.get(k));
	                commaSepValueBuilder2.append(Size1.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Size1.size()-1){
	                    commaSepValueBuilder2.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Size1_f = commaSepValueBuilder2.toString();
	            Log.d("Size1_f","Size1_f"+ Size1_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        Log.d("Size2","Size2"+Arrays.asList(Size2));
	        
	        System.out.println(Arrays.toString(Size2.toArray()));
	        StringBuilder commaSepValueBuilder3 = new StringBuilder();

	        try {

	            for (int k = 0; k < Size2.size(); k++) {
	                int kk = Size2.size();

	                System.out.println("splitting test" + Size2.get(k));
	                commaSepValueBuilder3.append(Size2.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Size2.size()-1){
	                    commaSepValueBuilder3.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Size2_f = commaSepValueBuilder3.toString();
	            Log.d("Size2_f","Size2_f"+ Size2_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        System.out.println(Arrays.toString(Voltage_Watts_Amps.toArray()));
	        StringBuilder commaSepValueBuilder4 = new StringBuilder();

	        try {

	            for (int k = 0; k < Voltage_Watts_Amps.size(); k++) {
	                int kk = Voltage_Watts_Amps.size();

	                System.out.println("splitting test" + Voltage_Watts_Amps.get(k));
	                commaSepValueBuilder4.append(Voltage_Watts_Amps.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Voltage_Watts_Amps.size()-1){
	                    commaSepValueBuilder4.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Voltage_Watts_Amps_f = commaSepValueBuilder4.toString();
	            Log.d("Voltage_Watts_Amps_f","Voltage_Watts_Amps_f"+ Voltage_Watts_Amps_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        System.out.println(Arrays.toString(Colour.toArray()));
	        StringBuilder commaSepValueBuilder5 = new StringBuilder();

	        try {

	            for (int k = 0; k < Colour.size(); k++) {
	                int kk = Colour.size();

	                System.out.println("splitting test" + Colour.get(k));
	                commaSepValueBuilder5.append(Colour.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Colour.size()-1){
	                    commaSepValueBuilder5.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Colour_f = commaSepValueBuilder5.toString();
	            Log.d("Colour_f","Colour_f"+ Colour_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        System.out.println(Arrays.toString(Metal_Aluminum_Wt.toArray()));
	        StringBuilder commaSepValueBuilder6 = new StringBuilder();

	        try {

	            for (int k = 0; k < Metal_Aluminum_Wt.size(); k++) {
	                int kk = Metal_Aluminum_Wt.size();

	                System.out.println("splitting test" + Metal_Aluminum_Wt.get(k));
	                commaSepValueBuilder6.append(Metal_Aluminum_Wt.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Metal_Aluminum_Wt.size()-1){
	                    commaSepValueBuilder6.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Metal_Aluminum_Wt_f = commaSepValueBuilder6.toString();
	            Log.d("Metal_Aluminum_Wt_f","Metal_Aluminum_Wt_f"+ Metal_Aluminum_Wt_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        System.out.println(Arrays.toString(Metal_Copper_Wt.toArray()));
	        StringBuilder commaSepValueBuilder7 = new StringBuilder();

	        try {

	            for (int k = 0; k < Metal_Copper_Wt.size(); k++) {
	                int kk = Metal_Copper_Wt.size();

	                System.out.println("splitting test" + Metal_Copper_Wt.get(k));
	                commaSepValueBuilder7.append(Metal_Copper_Wt.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Metal_Copper_Wt.size()-1){
	                    commaSepValueBuilder7.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Metal_Copper_Wt_f = commaSepValueBuilder7.toString();
	            Log.d("Metal_Copper_Wt_f","Metal_Copper_Wt_f"+ Metal_Copper_Wt_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        System.out.println(Arrays.toString(Product_Weight.toArray()));
	        StringBuilder commaSepValueBuilder8 = new StringBuilder();

	        try {

	            for (int k = 0; k < Product_Weight.size(); k++) {
	                int kk = Product_Weight.size();

	                System.out.println("splitting test" + Product_Weight.get(k));
	                commaSepValueBuilder8.append(Product_Weight.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Product_Weight.size()-1){
	                    commaSepValueBuilder8.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Product_Weight_f = commaSepValueBuilder8.toString();
	            Log.d("Product_Weight_f","Product_Weight_f"+ Product_Weight_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        System.out.println(Arrays.toString(Bending_Radius.toArray()));
	        StringBuilder commaSepValueBuilder9 = new StringBuilder();

	        try {

	            for (int k = 0; k < Bending_Radius.size(); k++) {
	                int kk = Bending_Radius.size();

	                System.out.println("splitting test" + Bending_Radius.get(k));
	                commaSepValueBuilder9.append(Bending_Radius.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != Bending_Radius.size()-1){
	                    commaSepValueBuilder9.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            Bending_Radius_f = commaSepValueBuilder9.toString();
	            Log.d("Bending_Radius_f","Bending_Radius_f"+ Bending_Radius_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        System.out.println(Arrays.toString(PLANNING_MAKE_BUY_CODE.toArray()));
	        StringBuilder commaSepValueBuilder10 = new StringBuilder();

	        try {

	            for (int k = 0; k < PLANNING_MAKE_BUY_CODE.size(); k++) {
	                int kk = PLANNING_MAKE_BUY_CODE.size();

	                System.out.println("splitting test" + PLANNING_MAKE_BUY_CODE.get(k));
	                commaSepValueBuilder10.append(PLANNING_MAKE_BUY_CODE.get(k));

	              //  Global_Val.count = product_value.size();
	                if ( k != PLANNING_MAKE_BUY_CODE.size()-1){
	                    commaSepValueBuilder10.append(", ");
	                }
	               // System.out.println("delete barcode result" + str);
	                //Log.d("delete barcode","DBAR"+str);
	            }
	            PLANNING_MAKE_BUY_CODE_f = commaSepValueBuilder10.toString();
	            Log.d("PLANG_KE_BUY_DE_","PLANNING_MAKE_BUY_CODE_f"+ PLANNING_MAKE_BUY_CODE_f);

	        }catch(Exception e){e.printStackTrace();}
	        
	        
	        
	        
	        if(Secondary_Category_f.equalsIgnoreCase("") && Secondary_Sub_Category_f.equalsIgnoreCase("") && Size1_f.equalsIgnoreCase("")&& Size2_f.equalsIgnoreCase("")&& Voltage_Watts_Amps_f.equalsIgnoreCase("")&& Colour_f.equalsIgnoreCase("")&& Metal_Aluminum_Wt_f.equalsIgnoreCase("")&& Metal_Copper_Wt_f.equalsIgnoreCase("")&& Product_Weight_f.equalsIgnoreCase("")&& Bending_Radius_f.equalsIgnoreCase("")&& PLANNING_MAKE_BUY_CODE_f.equalsIgnoreCase(""))
	        {
	        	GetListINVOICE(Global_Data.GLOVEL_INVOICE_VALUE);
	        	Log.d("Filter value not sele", "any");
	        	
	        }
	        else
	        {
	        	Log.d("Filter value selected ", "any");
	        	List<Local_Data> contacts1 = dbvoc.ITEM_List_INVOICEBYFILTER(Global_Data.GLOVEL_INVOICE_VALUE,Secondary_Category_f,Secondary_Sub_Category_f,Size1_f,Size2_f,Voltage_Watts_Amps_f,Colour_f,Metal_Aluminum_Wt_f,Metal_Copper_Wt_f,Product_Weight_f,Bending_Radius_f,PLANNING_MAKE_BUY_CODE_f);
		        
		        for (Local_Data cn : contacts1) 
		        {
		      	 
		      	  //product_value.add(cn.getVariant()+" "+cn.getStateName());
		      	  product_value.add(cn.getVariant()+"\n"+"ITEM NUMBER " + cn.getImei());
		            
		        }
	        }
	        
			
	  	
      	    searchResults=new ArrayList<String>(product_value);
			adapter = new ArrayAdapter<String>(Invoice_filterList.this, R.layout.filtertxt, searchResults);
			adapter.notifyDataSetChanged();
			alllist.setAdapter(adapter);
			Global_Data.GLOVEL_FILTER_FLAG = "";
			
			if(adapter== null)
			 {
				 search_filter.setEnabled(false); 
			 }
			 else
			 {
				 search_filter.setEnabled(true);  
			 }
		}
		else
		if(Global_Data.GLOVEL_INVOICE_VALUE != null && Global_Data.GLOVEL_INVOICE_VALUE != "" && Global_Data.GLOVEL_INVOICE_VALUE != "null")
		{	
			GetListINVOICE(Global_Data.GLOVEL_INVOICE_VALUE);
		}
		
		alllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	            @Override
	            public void onItemClick(AdapterView<?> parent, View view,
	                                    int position, long id) {

	                //String contactId = (TextView) view.findViewById(R.id.label);
	            	 String value = (String)parent.getItemAtPosition(position); 
	                // Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();
	 
				      
				      getListView(value.trim());
				      
				    }

	        });
		
		filter_btn = findViewById(R.id.filter_btn);
		
		//brand_subcategaries = (LinearLayout) findViewById(R.id.brand_subcategaries);
		
		 dataArrayList=new ArrayList<HashMap<String, String>>();
		 
		 
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
		
		search_filter = findViewById(R.id.search_filter);
		
		
		filter_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Global_Data.GLOVEL_RETURN_FLAG="INVOICE_LIST";
				Global_Data.map.clear();
				Intent intent = new Intent(getApplicationContext(), New_filterList.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
	    	}
		});
		
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
			SharedPreferences sp = Invoice_filterList.this.getSharedPreferences("SimpleLogic", 0);

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
		
		 Global_Data.GLOVEL_LONG_DESC = ""; 
		// Global_Data.GLOVEL_SubCategory_Button = "";
		 Intent i = new Intent(Invoice_filterList.this,Filter_List.class);
		 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	     startActivity(i);
		 this.finish();
		
		 
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stubs
		super.onResume();
		
	}
	
	public void GetListINVOICE(String name)
	{
		List<Local_Data> contacts1 = dbvoc.ITEM_List_INVOICE(name);
	        
        for (Local_Data cn : contacts1) 
        {
      	 
      	  //product_value.add(cn.getVariant()+" "+cn.getStateName());
      	  product_value.add(cn.getVariant()+" "+"ITEM NUMBER " + cn.getImei());
            
        }
  	
      	    searchResults=new ArrayList<String>(product_value);
			adapter = new ArrayAdapter<String>(Invoice_filterList.this, R.layout.filtertxt, searchResults);
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
	
	public void getListView(final String value)
	{
		 final Dialog dialognew = new Dialog(Invoice_filterList.this);
	        dialognew.setCancelable(false);
	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        //tell the Dialog to use the dialog.xml as it's layout description
	        dialognew.setContentView(R.layout.update_dialog);

	        final EditText userInput = dialognew
	                .findViewById(R.id.update_textdialog);

	                final Button Submit = dialognew
	                        .findViewById(R.id.update_textdialogclick);
	                
	                final Button update_cancel = dialognew
	                        .findViewById(R.id.update_cancel);

	        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value))
	        {
	            userInput.setText(value);
	        }

//	        final Button cancel = (Button) dialognew
//	                .findViewById(R.id.cancel);

	        Submit.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

//	                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(userInput.getText().toString()))
//	                {
//	                    update_salesupdate.setText(userInput.getText().toString());
//	                }
                    String[] s = value.split("ITEM NUMBER");
	            	 List<Local_Data> contacts1 = dbvoc.ITEM_description_byINVOICEID(s[1].trim());
//				        
				      for (Local_Data cn : contacts1) 
				      {
				    	  Global_Data.GLOVEL_LONG_DESC = cn.getVariant().trim();
				    	  Global_Data.GLOVEL_ITEM_MRP = cn.getMRP().trim();
				    	  Global_Data.GLOvel_ITEM_NUMBER = s[1].trim();
				      }
                       
				      Intent i = new Intent(Invoice_filterList.this,NewOrderActivity.class);
					     startActivity(i);
			                finish();
	                    dialognew.dismiss();


	            }
	        });
	        
	        update_cancel.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {

//	               
	            	
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
