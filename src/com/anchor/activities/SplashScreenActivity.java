package com.anchor.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Menu;
import android.widget.ImageView;

public class SplashScreenActivity extends Activity {

	ImageView splash_logo;
	Bitmap blob_data_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

		splash_logo=(ImageView)findViewById(R.id.splash_logo);

		SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
		String logostr=spf1.getString("splash_data", "");

		if(logostr.length()>0){
			byte[] decodedString = Base64.decode(logostr, Base64.DEFAULT);
			blob_data_logo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			splash_logo.setImageBitmap(blob_data_logo);
		}

        //startService(new Intent(getBaseContext(), LocationService.class));
        
       

		 Handler h=new Handler();
	        h.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					finish();
					// TODO Auto-generated method stub
//					 GPSService mGPSService = new GPSService(getApplicationContext());
//						mGPSService.getLocation();
//						
//						if (mGPSService.isLocationAvailable == false) {
//
//							// Here you can ask the user to try again, using return; for that
//							Toast.makeText(getApplicationContext(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
//							return;
//
//							// Or you can continue without getting the location, remove the return; above and uncomment the line given below
//							// address = "Location not available";
//
//						}
//						else {
//
//							// Getting location co-ordinates
//							double latitude = mGPSService.getLatitude();
//							double longitude = mGPSService.getLongitude();
//							Toast.makeText(getApplicationContext(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();
//
//
//
//							Toast.makeText(getApplicationContext(), "Your address is: " +  mGPSService.getLocationAddress(), Toast.LENGTH_SHORT).show();
//						}
					Intent i=new Intent(getApplicationContext(), LoginActivity.class);
					i.putExtra("splash", "splash");
					
					
					//overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					//overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
				//overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
					//overridePendingTransition(R.anim.grow_from_middle,R.anim.shrink_to_middle);
					// overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
					startActivity(i);
				}
			}, 2000);
		

		
        
//        Intent i= new Intent(getApplicationContext(), LocationService.class);
//	     // potentially add data to the intent
//	     i.putExtra("KEY1", "Value to be used by the service");
//	     startService(i);	
       // DatabaseHandler myDbHelper = new DatabaseHandler(this);
        
 /*
        try {
 
        	//myDbHelper.createDataBase();
 
 	} catch (IOException ioe) {
 
 		throw new Error("Unable to create database");
 
 	}
 
 	try {
 
 		//myDbHelper.openDataBase();
 
 	}catch(SQLException sqle){
 
 		throw sqle;
 
 	}*/
 	
 	
       
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	super.onBackPressed();
    	finish();
    	System.exit(0);
    }
    
}
