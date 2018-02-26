package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Schedule1 extends Activity{
	Schedule_Adapter schedule_adapter;
	static final String TAG_PRODUCT = "product";
	static final String TAG_Q_ORDER = "quantity_order";
	static final String TAG_Q_DELIVERED = "quantity_delivered";
	private ArrayList<HashMap<String, String>> dataArrayList;

	DataBaseHelper dbvoc = new DataBaseHelper(this);
	List<String> C_Array = new ArrayList<String>();
	private String RE_TEXT = "";
	private String RE_ID = "";
	Button but_invoice,scancel;
	TextView DISP_DATE,EST_DATE,D_CASH,DC_LIMIT,PRE_OUTSTANDING,DCU_ORDER,DU_AMOUNT;
	private String order_id_get;
	private String Order_ID;
	List<String> Product_List = new ArrayList<String>();
	List<String> Product_List_NEW = new ArrayList<String>();
	String customer_id = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule1);
		
		Intent i = getIntent(); 
		//String name = i.getStringExtra("retialer");
		try
		{
			order_id_get = i.getStringExtra("order_id");
			RE_TEXT = i.getStringExtra("RE_TEXT");
			customer_id = i.getStringExtra("customer_id");
		}
		catch(Exception ex){ex.printStackTrace();}

		if(!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOvel_CUSTOMER_ID)))
		{
			Global_Data.GLOvel_CUSTOMER_ID = customer_id;
		}
		
		//Toast.makeText(getApplicationContext(), order_id_get,Toast.LENGTH_SHORT).show();
		Order_ID = order_id_get;
		
		 ListView listView = (ListView) findViewById(R.id.DProduct_List);
		setListViewHeightBasedOnChildren(listView);


		 
		 DISP_DATE = (TextView) findViewById(R.id.DISP_DATE);
		 EST_DATE = (TextView) findViewById(R.id.EST_DATE);
		 D_CASH = (TextView) findViewById(R.id.D_CASH);
		 DC_LIMIT = (TextView) findViewById(R.id.DC_LIMIT);
		 PRE_OUTSTANDING = (TextView) findViewById(R.id.PRE_OUTSTANDING);
		 DCU_ORDER = (TextView) findViewById(R.id.DCU_ORDER);
		 DU_AMOUNT = (TextView) findViewById(R.id.DU_AMOUNT);
		
		
		//Toast.makeText(getApplicationContext(), abc[1], Toast.LENGTH_SHORT).show();
		
		//Toast.makeText(getApplicationContext(), Order_ID,
		      //    Toast.LENGTH_SHORT).show();

//		Toast toast = Toast.makeText(getApplicationContext(), Order_ID,
//				Toast.LENGTH_SHORT);
//		toast.setGravity(Gravity.CENTER, 0, 0);
//		toast.show();

		dataArrayList=new ArrayList<HashMap<String, String>>();
		
		// for credit limit vinod
//		List<Local_Data> contactsR = dbvoc.getRetailer(RE_TEXT);
//        for (Local_Data cn : contactsR)
//        {
//
//       	  RE_ID = cn.get_Retailer_id();
//        }
        
        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOvel_CUSTOMER_ID))
        {
        	List<Local_Data> contactlimit = dbvoc.getCreditprofileData(Global_Data.GLOvel_CUSTOMER_ID);
            for (Local_Data cn : contactlimit) 
            {

				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_credit_limit()))
				{
					DC_LIMIT.setText(cn.get_credit_limit());
				}
				else
				{
					DC_LIMIT.setText("0");
				}


				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_shedule_outstanding_amount()))
				{
					PRE_OUTSTANDING.setText(cn.get_shedule_outstanding_amount());
				}
				else
				{
					PRE_OUTSTANDING.setText("0");
				}

				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getAmmount_overdue()))
				{
					DU_AMOUNT.setText(cn.getAmmount_overdue());
				}
				else
				{
					DU_AMOUNT.setText("0");
				}


			}
        	
        }
        
		// for product list 
		Product_List = new ArrayList<String>();
		Product_List_NEW = new ArrayList<String>();

		dataArrayList.clear();
		  
		List<Local_Data> contacts = dbvoc.getDeliveryProducts(Order_ID);
        for (Local_Data cn : contacts) 
        {
      	     
//        	Product_List.add("       "+cn.get_delivery_product_transporter_details()+"                       " + cn.get_delivery_product_order_quantity()+"                         " + cn.get_delivery_product_delivered_quality());

			HashMap<String, String> map = new HashMap<String, String>();



			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_stocks_product_name()))
			{
				map.put(TAG_PRODUCT, cn.get_stocks_product_name());
			}
			else
			{
				map.put(TAG_PRODUCT, " ");
			}

			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_delivery_product_order_quantity()))
			{
				map.put(TAG_Q_ORDER, cn.get_delivery_product_order_quantity());
			}
			else
			{
				map.put(TAG_Q_ORDER, "0");
			}

			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_delivery_product_delivered_quality()))
			{
				map.put(TAG_Q_DELIVERED, cn.get_delivery_product_delivered_quality());
			}
			else
			{
				map.put(TAG_Q_DELIVERED, "0");
			}

			dataArrayList.add(map);

		}


		schedule_adapter = new Schedule_Adapter(Schedule1.this, dataArrayList);
		listView.setAdapter(schedule_adapter);
		//list_target.setAdapter(target_adapter);
		//listView.deferNotifyDataSetChanged();

//		if(schedule_adapter.isEmpty())
//		{
//			list_target.setVisibility(View.VISIBLE);
//			list_text.setVisibility(View.GONE);
//		}

        // for product table data
        List<Local_Data> contactsP = dbvoc.getDeliverySchedule(Order_ID);
        for (Local_Data cn : contactsP) 
        {

			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_shedule_depatch_date()))
			{
				DISP_DATE.setText(cn.get_shedule_depatch_date());
			}
			else
			{
				DISP_DATE.setText("");
			}

			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_shedule_delivery_date()))
			{
				EST_DATE.setText(cn.get_shedule_delivery_date());
			}
			else
			{
				EST_DATE.setText("");
			}


			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_shedule_payment_mode()))
			{
				D_CASH.setText(cn.get_shedule_payment_mode());
			}
			else
			{
				D_CASH.setText("");
			}


			//PRE_OUTSTANDING.setText(cn.get_shedule_outstanding_amount());

			if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_shedule_order_amount()))
			{
				DCU_ORDER.setText(cn.get_shedule_order_amount());
			}
			else
			{
				DCU_ORDER.setText("0");
			}

        }
        
      //  dbvoc.close();
        
       // @SuppressWarnings("rawtypes")


		but_invoice=(Button)findViewById(R.id.but_invoice);
		//scancel=(Button)findViewById(R.id.scancel);
		
		 but_invoice.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if(!(Global_Data.Schedule_FLAG.equalsIgnoreCase("CUSTOMER")))
					{
						Global_Data.GLOvel_CUSTOMER_ID = "";
					}

					Intent intent = new Intent(Schedule1.this, Schedule_List.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					finish();
		    	}
			});

//		scancel.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(Schedule1.this, Schedule_List.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//				finish();
//			}
//		});
		 
		ActionBar mActionBar = getActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
       // mActionBar.setDisplayShowHomeEnabled(false);
       // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        
		
        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Delivery Schedule");
        
        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Schedule1.this.getSharedPreferences("SimpleLogic", 0);
        
        ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
        H_LOGO.setImageResource(R.drawable.timelist);
        H_LOGO.setVisibility(View.VISIBLE);
        
       
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
		if(!(Global_Data.Schedule_FLAG.equalsIgnoreCase("CUSTOMER")))
		{
			Global_Data.GLOvel_CUSTOMER_ID = "";
		}
		Intent intent = new Intent(Schedule1.this, Schedule_List.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		finish();
	}

	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null)
			return;

		int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
		int totalHeight = 0;
		View view = null;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			view = listAdapter.getView(i, view, listView);
			if (i == 0)
				view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ActionBar.LayoutParams.WRAP_CONTENT));

			view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
			totalHeight += view.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
