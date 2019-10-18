package com.anchor.activities;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Service;  
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;  
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
@SuppressLint("CommitPrefEdits")
public class LocationService extends Service {  
// MediaPlayer myPlayer;  
	 private LocationManager locationMangaer=null;
	 private LocationListener locationListener=null; 
	 
	 private Button btnGetLocation = null;
	 private EditText editLocation = null; 
	 private ProgressBar pb =null;
	 int state_flag = 0;
	 private static final String TAG = "Debug";
	 private Boolean flag = false;
 @Override  
 public IBinder onBind(Intent intent) {  
  return null;  
 }  
 @Override  
 public void onCreate() {  
  Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();  
   
 // myPlayer = MediaPlayer.create(this, R.raw.sun);  
 // myPlayer.setLooping(false); // Set looping  
  locationMangaer = (LocationManager) 
			 getSystemService(Context.LOCATION_SERVICE);
 }  
 @Override  
 public void onStart(Intent intent, int startid) {  
 // Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();  
 // myPlayer.start();  
	 //current_locationcheck();
	 
 }  
 @Override  
 public void onDestroy() {  
  Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();  
 // myPlayer.stop();  
 }  
 
// @Override
// public int onStartCommand(Intent intent, int flags, int startId) {
//   //TODO do something useful
//	 current_locationcheck();
//   return Service.START_NOT_STICKY;
// }

 
 
 /*----Method to Check GPS is enable or disable ----- */
 private Boolean displayGpsStatus() {
  ContentResolver contentResolver = getBaseContext()
  .getContentResolver();
  boolean gpsStatus = Settings.Secure
  .isLocationProviderEnabled(contentResolver, 
  LocationManager.GPS_PROVIDER);
     return gpsStatus;
 }
 
 /*----------Method to create an AlertBox ------------- */
 protected void alertbox(String title, String mymessage) {
  AlertDialog.Builder builder = new AlertDialog.Builder(this);
  builder.setMessage("Your Device's GPS is Disable")
  .setCancelable(false)
  .setTitle("** Gps Status **")
  .setPositiveButton("Gps On",
   new DialogInterface.OnClickListener() {
   public void onClick(DialogInterface dialog, int id) {
   // finish the current activity
   // AlertBoxAdvance.this.finish();
   Intent myIntent = new Intent(
   Settings.ACTION_SECURITY_SETTINGS);
   startActivity(myIntent);
      dialog.cancel();
   }
   })
   .setNegativeButton("Cancel",
   new DialogInterface.OnClickListener() {
   public void onClick(DialogInterface dialog, int id) {
    // cancel the dialog box
    dialog.cancel();
    }
   });
  AlertDialog alert = builder.create();
  alert.show();
 }
 
 /*----------Listener class to get coordinates ------------- */
 private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
          
           // editLocation.setText("");
           // pb.setVisibility(View.INVISIBLE);
            Toast.makeText(getBaseContext(),"Location changed : Lat: " +
   loc.getLatitude()+ " Lng: " + loc.getLongitude(),
   Toast.LENGTH_SHORT).show();
            String longitude = "Longitude: " +loc.getLongitude();  
      //Log.v(TAG, longitude);
      String latitude = "Latitude: " +loc.getLatitude();
    //  Log.v(TAG, latitude);
          
    /*----------to get City-Name from coordinates ------------- */
      String cityName=null; 
      String StateName=null; 
      String CountryName=null; 
      
      Geocoder gcd = new Geocoder(getBaseContext(), 
   Locale.getDefault());             
      List<Address>  addresses;  
      try {  
      addresses = gcd.getFromLocation(loc.getLatitude(), loc
   .getLongitude(), 1);  
      if (addresses.size() > 0)  
         System.out.println(addresses.get(0).getLocality());  
         cityName=addresses.get(0).getLocality();  
         StateName= addresses.get(0).getAdminArea();
         
         CountryName = addresses.get(0).getCountryName();
        } catch (Exception e) {
        e.printStackTrace();  
      } 
          
      String s = longitude+"\n"+latitude +
   "\n\nMy Currrent City is: "+cityName + "STATE NAME" + StateName + "COUNTRY NAME" + CountryName;
           //editLocation.setText(s);
          showstates(StateName,cityName);
          
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub         
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub         
        }

        @Override
        public void onStatusChanged(String provider, 
  int status, Bundle extras) {
            // TODO Auto-generated method stub         
        }
    }
 
public void current_locationcheck()
{
	flag = displayGpsStatus();
	  if (flag) {
	   
	   Log.v(TAG, "onClick");  
	   
	   //editLocation.setText("Please!! move your device to"+
	  // " see the changes in coordinates."+"\nWait..");
	   
	   //pb.setVisibility(View.VISIBLE);
	   locationListener = new MyLocationListener();

	   locationMangaer.requestLocationUpdates(LocationManager
	   .GPS_PROVIDER, 300, 3,locationListener);
	   
	   } else {
	   alertbox("Gps Status!!", "Your GPS is: OFF");
	  }
}
 
 public void showstates( final String state_name, final String cityname)
 {
	 //SharedPreferences spf=this.getSharedPreferences("SimpleLogic",0);        
     //SharedPreferences.Editor editor=spf.edit();        
     
     
 	    if(state_flag == 0)
 	    {
 	    	Global_Data.GLOvel_STATE = state_name;
 		    Global_Data.GLOvel_CITY = cityname;
 		    //editor.putString("GLOvel_STATE", state_name);
 		    //editor.putString("GLOvel_CITY", cityname);
 		    state_flag++;
 	    }
 	    else
 	    {
 	    	new Timer().scheduleAtFixedRate(new TimerTask(){
 			    @Override
 			    public void run(){
 			    	 Global_Data.GLOvel_STATE = state_name;
 			 	    Global_Data.GLOvel_CITY = cityname;
 			 	   // editor.putString("GLOvel_STATE", state_name);
 		 		   // editor.putString("GLOvel_CITY", cityname);
 			    }
 			},0,2000);
 	    }
	   
	    
	 
	 
	   
// 		List<Local_Data> contacts1 = dbvoc.getAllStatebyState_Name(state_name.toUpperCase());
// 		 //results1.add("Select State");
// 		  for (Local_Data cn : contacts1) 
// 		  {
// 			String str_state = ""+cn.getStateName();
// 			results1.add(str_state);
// 			
// 		  }
// 		  
// 		  
// 		   ArrayAdapter<String> adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results1);
// 	
// 			//ArrayAdapter<String> adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results2);
// 				  
// 			adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// 			state_spinner.setAdapter(adapter_state1);
// 			state_spinner.setOnItemSelectedListener(this);
 	  
  }
}  
