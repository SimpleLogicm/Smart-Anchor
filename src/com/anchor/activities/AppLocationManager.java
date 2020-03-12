package com.anchor.activities;

/**
 * Created by vinod on 24-11-2016.
 */

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class AppLocationManager implements LocationListener {

    private LocationManager locationManager;
    private String latitude;
    private String longitude;
    private Criteria criteria;
    private String provider;

    public AppLocationManager(Context context) {
        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                0, this);
        setMostRecentLocation(locationManager.getLastKnownLocation(provider));

    }

    private void setMostRecentLocation(Location lastKnownLocation) {

        try{

            if (!String.valueOf(lastKnownLocation.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(lastKnownLocation.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(lastKnownLocation.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(lastKnownLocation.getLongitude()).equalsIgnoreCase(null))
            {
                double lon = lastKnownLocation.getLongitude();/// * 1E6);
                double lat = lastKnownLocation.getLatitude();// * 1E6);

                latitude = lat + "";
                longitude = lon + "";
            }
            else
            {
                latitude = "";
                longitude = "";
            }

        }catch(Exception ex){ex.printStackTrace();}

    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onLocationChanged(android.location.
     * Location)
     */
    @Override
    public void onLocationChanged(Location location) {
        double lon = location.getLongitude();/// * 1E6);
        double lat = location.getLatitude();// * 1E6);

//      int lontitue = (int) lon;
//      int latitute = (int) lat;
        latitude = lat + "";
        longitude = lon + "";

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onProviderDisabled(java.lang.String)
     */
    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * android.location.LocationListener#onProviderEnabled(java.lang.String)
     */
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see android.location.LocationListener#onStatusChanged(java.lang.String,
     * int, android.os.Bundle)
     */
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }

}
