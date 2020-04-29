package com.anchor.activities;

/**
 * Created by vinod on 25-11-2016.
 */

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;



/**
 * Created by vinod on 24-11-2016.
 */



public class PlayService_Location implements LocationListener {

    private LocationManager locationManager;
    private String latitude;
    private String longitude;
    private Criteria criteria;
    private String provider;
    Context c_context;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    private LocationRequest mLocationRequest;


    private double fusedLatitude = 0.0;
    private  double fusedLongitude = 0.0;
    //DataBaseHelper dbvoc;

    public PlayService_Location(Context context) {

        c_context = context;
        MultiDex.install(c_context);
      //  dbvoc = new DataBaseHelper(c_context);
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);


        if (checkPlayServices(c_context)) {

            try
            {
                startFusedLocation();
                registerRequestUpdate(this);
            }
            catch(Exception ex){ex.printStackTrace();}

        }

    }

    private void setMostRecentLocation(Location lastKnownLocation) {

        double lon = lastKnownLocation.getLongitude();/// * 1E6);
        double lat = lastKnownLocation.getLatitude();// * 1E6);

//      int lontitue = (int) lon;
//      int latitute = (int) lat;
        latitude = lat + "";
        longitude = lon + "";

    }

    public String getLatitude() {
        return String.valueOf(fusedLatitude);
    }

    public String getLongitude() {
        return String.valueOf(fusedLongitude);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onLocationChanged(android.location.
     * Location)
     */
//    @Override
//    public void onLocationChanged(Location location) {
//        double lon = (double) (location.getLongitude());/// * 1E6);
//        double lat = (double) (location.getLatitude());// * 1E6);
//
////      int lontitue = (int) lon;
////      int latitute = (int) lat;
//        latitude = lat + "";
//        longitude = lon + "";
//
//    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onProviderDisabled(java.lang.String)
     */


    // check if google play services is installed on the device
    public  boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                Toast.makeText(context,
//                        "This device is supported. Please download google play services", Toast.LENGTH_LONG)
//                        .show();
                Log.d("CHECK PLAY SERVICE","CHECK PLAY "+"This device is supported. Please download google play services");

            } else {
//                Toast.makeText(context,
//                        "This device is not supported.", Toast.LENGTH_LONG)
//                        .show();
                Log.d("CHECK PLAY SERVICE","CHECK PLAY "+"This device is not supported.");

            }
            return false;
        }
        return true;
    }

    public void startFusedLocation() {

        try
        {
            if (mGoogleApiClient == null) {
                mGoogleApiClient = new GoogleApiClient.Builder(c_context).addApi(LocationServices.API)

                        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                            @Override
                            public void onConnectionSuspended(int cause) {
                            }

                            @Override
                            public void onConnected(Bundle connectionHint) {

                            }
                        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {

                            @Override
                            public void onConnectionFailed(ConnectionResult result) {

                            }
                        }).build();
                mGoogleApiClient.connect();
            } else {
                mGoogleApiClient.connect();
            }
        }catch(Exception e){e.printStackTrace();}

    }

    public void stopFusedLocation() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }


    public void registerRequestUpdate(final com.google.android.gms.location.LocationListener listener) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // every second

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, listener);
                } catch (SecurityException e) {
                   // dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                   // dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();
                    if (!isGoogleApiClientConnected()) {
                        mGoogleApiClient.connect();
                    }
                    registerRequestUpdate(listener);
                }
            }
        }, 1000);
    }

    public boolean isGoogleApiClientConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    @Override
    public void onLocationChanged(Location location) {
        setFusedLatitude(location.getLatitude());
        setFusedLongitude(location.getLongitude());

        latitude = location.getLatitude() + "";
        longitude = location.getLongitude() + "";

        Global_Data.GLOvel_LATITUDE = String.valueOf(location.getLatitude());
        Global_Data.GLOvel_LONGITUDE = String.valueOf(location.getLongitude());

        //  Toast.makeText(c_context, "NEW LOCATION RECEIVED", Toast.LENGTH_LONG).show();
        //Toast.makeText(c_context, "LOCATION"+getFusedLatitude()+" "+getFusedLongitude(), Toast.LENGTH_LONG).show();

        //Log.d("NEW LOCATION RECEIVED","NEW LOCATION RECEIVED");
        //Log.d("LOCATION","LOCATION"+getFusedLatitude()+" "+getFusedLongitude());


    }

    public void setFusedLatitude(double lat) {
        fusedLatitude = lat;
    }

    public void setFusedLongitude(double lon) {
        fusedLongitude = lon;
    }

    public double getFusedLatitude() {
        return fusedLatitude;
    }

    public double getFusedLongitude() {
        return fusedLongitude;
    }

}

