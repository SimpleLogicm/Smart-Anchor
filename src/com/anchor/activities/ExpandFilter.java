package com.anchor.activities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class ExpandFilter extends Activity {

    private ExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;
    private ExpandableListView ExpandList;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    
    List<String> groupList;
	List<String> childList;
	Map<String, List<String>> laptopCollection;
	
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
	
	
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_expand);
        
        short_record = findViewById(R.id.short_record);
        
         // Secondary_Category.add("abcz");
//        Secondary_Sub_Category.add("");
//        Size1.add("");
//        Size2.add("");
//        Voltage_Watts_Amps.add("");
//        Colour.add("");
//        Metal_Aluminum_Wt.add("");
//        Metal_Copper_Wt.add("");
//        Product_Weight.add("");
//        Bending_Radius.add("");
//        PLANNING_MAKE_BUY_CODE.add("");
        
        createGroupList();

		createCollection();


       ExpandList = findViewById(R.id.exp_list);
//        ExpListItems = SetStandardGroups();
//        ExpAdapter = new ExpandListAdapter(ExpandFilter.this, ExpListItems);
//        ExpandList.setAdapter(ExpAdapter);
		
		final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
				this, groupList, laptopCollection);
		ExpandList.setAdapter(expListAdapter);

		ExpandList.setOnChildClickListener(new OnChildClickListener() {

			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				final String selected = (String) expListAdapter.getChild(
						groupPosition, childPosition);
				
				int index = parent.getFlatListPosition(ExpandableListView
		                   .getPackedPositionForChild(groupPosition, childPosition));
		            parent.setItemChecked(index, true);

			   
				
				final String ght = (String) expListAdapter.getGroup(groupPosition);
				
				Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
						.show();
				
				Toast.makeText(getBaseContext(), ght, Toast.LENGTH_LONG)
				.show();
				
				// put values into map
		        Global_Data.map.put("Secondary_Category", Secondary_Category);
		        Global_Data.map.put("Secondary_Sub_Category", Secondary_Sub_Category);
		        Global_Data.map.put("Size1", Size1);
		        Global_Data.map.put("Size2", Size2);
		        Global_Data.map.put("Voltage_Watts_Amps", Voltage_Watts_Amps);
		        Global_Data.map.put("Colour", Colour);
		        Global_Data.map.put("Metal_Aluminum_Wt", Metal_Aluminum_Wt);
		        Global_Data.map.put("Metal_Copper_Wt", Metal_Copper_Wt);
		        Global_Data.map.put("Product_Weight", Product_Weight);
		        Global_Data.map.put("Bending_Radius", Bending_Radius);
		        Global_Data.map.put("PLANNING_MAKE_BUY_CODE", PLANNING_MAKE_BUY_CODE);
		        
				return false;
			}
		});
		
		short_record.setOnClickListener(new View.OnClickListener() {

	            @Override
	            public void onClick(View arg0) {

	            		
	            	if(Global_Data.GLOVEL_RETURN_FLAG.equalsIgnoreCase("FILTER_LIST"))
	            	{	
		                Intent i = new Intent(ExpandFilter.this,Filter_List.class);
	//	                // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//	                i.putExtra("user_name", user_name);
	//	                i.putExtra("confrence_name", confrence_name);
	//	                // i.putExtra("Barcode_Number", userInput.getText().toString());
	//	                i.putExtra("BackFlag","Barcode");
		                startActivity(i);
		                finish();
	            	}
	            	else
	            	{
	            		Global_Data.GLOVEL_FILTER_FLAG = "TRUE";
	            		//Global_Data.GLOVEL_INVOICE_VALUE = "";
	            		Intent i = new Intent(ExpandFilter.this,Invoice_filterList.class);
            		 	startActivity(i);
            		 	finish();
	            	}

	            }

	        });
//        ExpandList.setOnChildClickListener(new OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                    int groupPosition, int childPosition, long id) {
//
//                String group_name = ExpListItems.get(groupPosition).getName();
//
//                ArrayList<Child> ch_list = ExpListItems.get(
//                        groupPosition).getItems();
//
//                String child_name = ch_list.get(childPosition).getName();
//
//                showToastMsg(group_name + "\n" + child_name);
//
//                return false;
//            }
//        });

//        ExpandList.setOnGroupExpandListener(new OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                String group_name = ExpListItems.get(groupPosition).getName();
//                showToastMsg(group_name + "\n Expanded");
//            }
//        });

//        ExpandList.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                String group_name = ExpListItems.get(groupPosition).getName();
//                showToastMsg(group_name + "\n Expanded");
//
//            }
//        });
    }

    public ArrayList<Group> SetStandardGroups() {

        ArrayList<Group> group_list = new ArrayList<Group>();
        ArrayList<Child> child_list;

        // Setting Group 1
        child_list = new ArrayList<Child>();
            Group gru1 = new Group();
            gru1.setName("Apple");
   
            Child ch1_1 = new Child();
            ch1_1.setName("Iphone");
            child_list.add(ch1_1);
    
            Child ch1_2 = new Child();
            ch1_2.setName("ipad");
            child_list.add(ch1_2);
    
            Child ch1_3 = new Child();
            ch1_3.setName("ipod");
            child_list.add(ch1_3);

        gru1.setItems(child_list);

        // Setting Group 2
        child_list = new ArrayList<Child>();
            Group gru2 = new Group();
            gru2.setName("SAMSUNG");
    
            Child ch2_1 = new Child();
            ch2_1.setName("Galaxy Grand");
            child_list.add(ch2_1);
    
            Child ch2_2 = new Child();
            ch2_2.setName("Galaxy Note");
            child_list.add(ch2_2);
    
            Child ch2_3 = new Child();
            ch2_3.setName("Galaxy Mega");
            child_list.add(ch2_3);
    
            Child ch2_4 = new Child();
            ch2_4.setName("Galaxy Neo");
            child_list.add(ch2_4);

        gru2.setItems(child_list);

        //listing all groups
        group_list.add(gru1);
        group_list.add(gru2);

        return group_list;
    }

    public void showToastMsg(String Msg) {
        Toast.makeText(getApplicationContext(), Msg, Toast.LENGTH_SHORT).show();
    }
    
    private void createGroupList() {
		groupList = new ArrayList<String>();
//		 List<Local_Data> contacts1 = dbvoc.Primary_SubCategoryITEM(Global_Data.GLOVEL_SUBCATEGORY_NAME);
//	        
//         for (Local_Data cn : contacts1) 
//         {
       	  
        	 groupList.add("Secondary Category");
        	 groupList.add("Secondary Sub Category");
        	 groupList.add("Size1");
        	 groupList.add("Size2");
        	 groupList.add("Voltage Watts Amps");
        	 groupList.add("Colour");
        	 groupList.add("Metal Aluminum Wt");
        	 groupList.add("Metal Copper Wt");
        	 groupList.add("Product Weight");
        	 groupList.add("Bending Radius");
        	 groupList.add("PLANNING_MAKE_BUY_CODE");
        	
        	 
     //    }
//		groupList.add("HP");
//		groupList.add("Dell");
//		groupList.add("Lenovo");
//		groupList.add("Sony");
//		groupList.add("HCL");
//		groupList.add("Samsung");
	}

	private void createCollection() {
		// preparing laptops collection(child)
		

		laptopCollection = new LinkedHashMap<String, List<String>>();

		for (String laptop : groupList) {
//			if (laptop.equalsIgnoreCase("Secondary Category")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Secondary_Category");
////			        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Secondary_Category.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Secondary_Category);
//			} 
//			else
//			if (laptop.equalsIgnoreCase("Secondary Sub Category")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Secondary_Sub_Category");
////				        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Secondary_Sub_Category.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Secondary_Sub_Category);
//			} 	
//			else
//			if (laptop.equalsIgnoreCase("Size1")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Size1");
////					        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Size1.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Size1);
//			 } 	
//			
//			else
//			if (laptop.equalsIgnoreCase("Size2")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Size2");
////					        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Size2.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Size2);
//			} 
//			else
//			if (laptop.equalsIgnoreCase("Voltage Watts Amps")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Voltage_Watts_Amps");
////					        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Voltage_Watts_Amps.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Voltage_Watts_Amps);
//			 } 	
//			else
//			if (laptop.equalsIgnoreCase("Colour")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Colour");
////						        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Colour.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Colour);
//			 } 	
//		
//			else
//			if (laptop.equalsIgnoreCase("Metal Aluminum Wt")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Metal_Aluminum_Wt");
////							        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Metal_Aluminum_Wt.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Metal_Aluminum_Wt);
//			 } 	
//		
//			else
//			if (laptop.equalsIgnoreCase("Metal Copper Wt")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Metal_Copper_Wt");
////								        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Metal_Copper_Wt.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Metal_Copper_Wt);
//			 } 	
//		
//			else
//			if (laptop.equalsIgnoreCase("Product Weight")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Product_Weight");
////									        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Product_Weight.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Product_Weight);
//			 } 	
//			else
//			if (laptop.equalsIgnoreCase("Bending Radius")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("Bending_Radius");
////										        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 Bending_Radius.add(cn.get_travel_text());
//		         }
//				
//				loadChild(Bending_Radius);
//			 }
//			else
//			if (laptop.equalsIgnoreCase("PLANNING_MAKE_BUY_CODE")) 
//			{
//				 List<Local_Data> contacts1 = dbvoc.getItemProductData("PLANNING_MAKE_BUY_CODE");
////										        
//		         for (Local_Data cn : contacts1) 
//		         {
//		        	 PLANNING_MAKE_BUY_CODE.add(cn.get_travel_text());
//		         }
//				
//				loadChild(PLANNING_MAKE_BUY_CODE);
//			 } 	
			
			laptopCollection.put(laptop, childList);
		}
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		 Global_Data.GLOVEL_LONG_DESC = ""; 
		 Global_Data.GLOVEL_SubCategory_Button = "";
		
		
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

	private void loadChild( List<String> laptopModels) {
		//childList = new ArrayList<String>();
	     if(!laptopModels.isEmpty())
	     {	 
	    	 childList = new ArrayList<String>(laptopModels);
	     }
//		for (String model : laptopModels)
//			childList.add(model);
	}

	private void setGroupIndicatorToRight() {
		/* Get the screen width */
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;

		ExpandList.setIndicatorBounds(width - getDipsFromPixel(35), width
				- getDipsFromPixel(5));
	}

	// Convert pixel to dip
	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

}