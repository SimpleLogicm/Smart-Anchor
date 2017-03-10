package com.anchor.service;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

//import com.simplelogic.activities.LoginActivity.SendLatLongAsyncTask;
//import com.simplelogic.webservice.WebserviceCall;

public class LocationServices extends Service
{
	private static final String TAG = "TESTGPS";
	private LocationManager mLocationManager = null;
	private static final int LOCATION_INTERVAL = 300000;
	private static final float LOCATION_DISTANCE = 10f;

	
	public static int userID= 0;
	public static double  latitude,longitude;
	 SharedPreferences spf;      
	
	
	private class LocationListeners implements android.location.LocationListener{
	   
		Location mLastLocation;
	    public LocationListeners(String provider)
	    {
	        Log.e(TAG, "LocationListener " + provider);
	      
	        mLastLocation = new Location(provider);
	    }
	    @Override
	    public void onLocationChanged(Location location)
	    {

	    	
	    	Log.d("lat",""+location.getLatitude());
	    	Log.d("long",""+location.getLongitude());
	    	
	    	
	    	if(location!=null )
	    	{
	    		//Toast.makeText(getApplicationContext(), "Lat:"+location.getLatitude()+"\n Long:"+location.getLongitude(),Toast.LENGTH_SHORT).show();
	    		latitude=location.getLatitude();
	    		longitude=location.getLongitude();
	    		SendLatLongAsyncTask sendLatLongAsyncTask=new SendLatLongAsyncTask(getApplicationContext());
				sendLatLongAsyncTask.execute();
	    	}
	    	 
	    	
	    	
	        Log.e(TAG, "onLocationChanged: " + location);
	        mLastLocation.set(location);
	    }
	    @Override
	    public void onProviderDisabled(String provider)
	    {
	        Log.e(TAG, "onProviderDisabled: " + provider);            
	    }
	    @Override
	    public void onProviderEnabled(String provider)
	    {
	        Log.e(TAG, "onProviderEnabled: " + provider);
	    }
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras)
	    {
	        Log.e(TAG, "onStatusChanged: " + provider);
	    }
	} 
	LocationListeners[] mLocationListeners = new LocationListeners[] {
	        new LocationListeners(LocationManager.GPS_PROVIDER),
	        new LocationListeners(LocationManager.NETWORK_PROVIDER)
	};
	@Override
	public IBinder onBind(Intent arg0)
	{
	    return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
	    Log.e(TAG, "onStartCommand");
	    super.onStartCommand(intent, flags, startId);       
	    return START_STICKY;
	}
	@Override
	public void onCreate()
	{
	      initializeLocationManager();
	    try {
	    	
	    	/*mLocationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
	                mLocationListeners[0]);
	    	*/
	    	  spf=getApplicationContext().getSharedPreferences("SimpleLogic",0);
		        userID=spf.getInt("UserID", 0);
	        mLocationManager.requestLocationUpdates(
	                LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
	                mLocationListeners[1]);
	        
	        
	    } catch (java.lang.SecurityException ex) {
	        Log.i(TAG, "fail to request location update, ignore", ex);
	    } catch (IllegalArgumentException ex) {
	        Log.d(TAG, "network provider does not exist, " + ex.getMessage());
	    }
	  /*  try {
	        mLocationManager.requestLocationUpdates(
	                LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
	                mLocationListeners[0]);
	    } catch (java.lang.SecurityException ex) {
	        Log.i(TAG, "fail to request location update, ignore", ex);
	    } catch (IllegalArgumentException ex) {
	        Log.d(TAG, "gps provider does not exist " + ex.getMessage());
	    }*/
	}
	@Override
	public void onDestroy()
	{
	    Log.e(TAG, "onDestroy"); 
	    super.onDestroy();
	    if (mLocationManager != null) {
	        for (int i = 0; i < mLocationListeners.length; i++) { 
	            try {
	                mLocationManager.removeUpdates(mLocationListeners[i]);
	            } catch (Exception ex) {
	                Log.i(TAG, "fail to remove location listners, ignore", ex);
	            }
	        }
	    }
	} 
	private void initializeLocationManager() {
	    Log.e(TAG, "initializeLocationManager");
	    if (mLocationManager == null) {
	        mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
	    }
	}
	
	public class SendLatLongAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		//private ProgressDialog dialog;
		/** application context. */
		//private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;
		String aResponse="";

		public SendLatLongAsyncTask(Context activity) {
			//this.activity = activity;
			context=activity;
			//dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			/*this.dialog.setMessage("Sending Lat Long");
			this.dialog.show();*/
			

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				//WebserviceCall com = new WebserviceCall();
				  //Call Webservice class method and pass values and get response
				
				try {
					// Log.e("DATA : SendLatLongAsyncTask", "----userID-"+userID+"----latitude-"+latitude+"----longitude-"+longitude);
					 String latString=String.valueOf(latitude);
					 String longString=String.valueOf(longitude);
					 //aResponse = com.sendLatLong("GetUserLocation",userID,latString,longString);  
					// aResponse = com.sendLatLong("GetUserLocation",userID,latString,longString);
				} catch (Exception e) { 
					// TODO: handle exception
					//Log.e("SendLatLongAsyncTask Exception", e.toString());
					Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
				}
		      
		      
		      Log.e("DATA : aResponse", "----"+aResponse);
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("DATA", "SendLatLongAsyncTask - "+e.toString());
				Toast.makeText(getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();
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
