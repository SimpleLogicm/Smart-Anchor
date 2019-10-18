package com.anchor.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;


public class SendLocation extends BroadcastReceiver implements LocationListener {
	 Geocoder geocoder;
	 String line;
	 BufferedReader in = null;
	 String lat_val,long_val;
	 HttpPost http_post;
	 HttpResponse http_resp;
	 HttpClient http_client;
	 List<NameValuePair> http_nmvalpair;
	 HttpEntity http_entity;
	public LocationManager locationManager;
	 // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;
    // flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
 // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 0 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 ; // 1 second
    
	 @Override
	public void onReceive(final Context mContext, Intent intent) {

	    try {
	        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

	        isGPSEnabled = locationManager
	                .isProviderEnabled(LocationManager.GPS_PROVIDER);

	        isNetworkEnabled = locationManager
	                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	        if (!isGPSEnabled && !isNetworkEnabled) {

	        } else {
	            this.canGetLocation = true;
	            if (isNetworkEnabled) {
	                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
	                if (locationManager != null) {
	                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	                    if (location != null) {
	                        latitude = location.getLatitude();
	                        longitude = location.getLongitude();
	                        Global_Data.lat_val = Double.toString(latitude);
	                        Global_Data.long_val = Double.toString(longitude);
	                        
	                        Geocoder geocoder;
	                		List<Address> addresses;
	                		
	                		geocoder = new Geocoder(mContext, Locale.getDefault());
	                		addresses = geocoder.getFromLocation(latitude, longitude, 1);
	                		Address address = addresses.get(0);
	                		Global_Data.GLOvel_STATE_n =  addresses.get(0).getAdminArea();
                            Global_Data.GLOvel_CITY_n = address.getLocality();
	                		// Here 1 represent max location result to returned, by documents it recommended 1 to 5
	                		Global_Data.address = addresses.get(0).getAddressLine(1)+" "+addresses.get(0).getAddressLine(2); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
	                    }
	                }
	            }
	         }
	  }catch (Exception e){

	                      }

	   Log.i("sendlocation","Every 5 minutes it will appear in Log Console"+latitude+" "+longitude);
	   
       new Thread(new Runnable() {
         	   
 	      public void run() {
 	    	 try{
 	    		 String  domain = mContext.getResources().getString(R.string.service_domain);
 		           HttpClient httpclient = new DefaultHttpClient();
 	               HttpGet request = new HttpGet();
 		           URI website = new URI(domain+"update_user_with_current_location?lat="+Global_Data.lat_val+"&lon="+Global_Data.long_val+"&device_id="+Global_Data.device_id+"&address="+URLEncoder.encode(Global_Data.address, "UTF-8"));
 		           request.setURI(website);
 		           HttpResponse response = httpclient.execute(request);
 		           in = new BufferedReader(new InputStreamReader(
 		                   response.getEntity().getContent()));

 		           // NEW CODE
 		           line = in.readLine();
 		           Global_Data.calendar_resp=line;
 		           System.out.println("fdgg:-------------------------->"+line);
 		      
 		       }catch(Exception e){
 		           Log.e("log_tag", "Error in http connection "+e.toString());
 		       }
 	     }
		    }).start();
	 }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
     
}
