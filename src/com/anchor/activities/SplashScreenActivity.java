package com.anchor.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

import java.util.List;

import cpm.simplelogic.helper.OnClearFromRecentService;

public class SplashScreenActivity extends Activity {

	DataBaseHelper dbvoc = new DataBaseHelper(this);
	ImageView splash_logo;
	Bitmap blob_data_logo;
	static SQLiteDatabase db;
	LoginDataBaseAdapter loginDataBaseAdapter;
	int ver_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

		splash_logo= findViewById(R.id.splash_logo);

		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();





		SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
		String logostr=spf1.getString("splash_data", "");

		if(logostr.length()>0){
			byte[] decodedString = Base64.decode(logostr, Base64.DEFAULT);
			blob_data_logo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			splash_logo.setImageBitmap(blob_data_logo);
		}

		PackageInfo pinfo = null;
		try {
			pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		int versionCode = pinfo.versionCode;
		String versionName = pinfo.versionName;
		int version_seven_check = 0;
		int version_nine_check = 0;
		int version_fifteen_check = 0;
		int version_c_check = 0;
		int version_ninteen_check = 0;
		int version_twintee_check = 0;
		int version_twintee_one_check = 0;
		int version_twintee_two_check = 0;
		int version_forty_two_check = 0;

// Reading all
		List<Local_Data> contacts1 = dbvoc.getVersioninfo();
//results1.add("Select State");
		if(contacts1.size() <=0)
		{
			loginDataBaseAdapter.insertVersionInfo(versionCode, versionName);
		}
		else
		{

			for (Local_Data cn : contacts1) {
				ver_code = cn.getVersioncode();
				String ver_name = "" + cn.getVersionname();

				if(ver_code == 22)
				{
					version_twintee_two_check = 1;
				}

				if(ver_code == 42)
				{
					version_forty_two_check = 1;
				}

				if(ver_code == versionCode)
				{
					version_c_check = 1;
				}
			}



			if(version_twintee_two_check != 1)
			{
				check_Columns_order_category_code_customer_master();
			}

			if(version_forty_two_check != 1)
			{
				check_Columns_smp_flag_item_master();
			}

			if(version_c_check != 1)
			{
				loginDataBaseAdapter.insertVersionInfo(versionCode, versionName);
			}

		}

		startService(new Intent(getBaseContext(), OnClearFromRecentService.class));


		//startService(new Intent(getBaseContext(), LocationService.class));
        
       

		 Handler h=new Handler();
	        h.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					finish();

					Intent i=new Intent(getApplicationContext(), LoginActivity.class);
					i.putExtra("splash", "splash");
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

					startActivity(i);
				}
			}, 2000);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    	finish();
    	System.exit(0);
    }

    public void check_Columns_order_category_code_customer_master()
	{
		boolean column_check = dbvoc.isColumnExists("customer_master","order_category_code_array");
		if(!column_check)
		{
			try {
				dbvoc.alter_Columns("customer_master","order_category_code_array");
			} catch (SQLiteException ex) {
				Log.w("Alter Table", "customer_master " + "order_category_code_array" + ": " + ex.getMessage());
			}
		}


	}

	public void check_Columns_smp_flag_item_master()
	{
		boolean column_check = dbvoc.isColumnExists("item_master","smp_flag");
		if(!column_check)
		{
			try {
				dbvoc.alter_Columns("item_master","smp_flag");
			} catch (SQLiteException ex) {
				Log.w("Alter Table", "item_master " + "smp_flag" + ": " + ex.getMessage());
			}
		}


	}


    
}
