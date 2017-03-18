package com.anchor.activities;


import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class BasicMapDemoActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker currLocationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ap_demo);

        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        mFragment.getMapAsync(this);

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Google Map");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        todaysTarget.setVisibility(View.INVISIBLE);
        ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
        SharedPreferences sp = BasicMapDemoActivity.this.getSharedPreferences("SimpleLogic", 0);

        H_LOGO.setImageResource(R.drawable.video_imagenew);
        H_LOGO.setVisibility(View.INVISIBLE);

//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//			todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//			todaysTarget.setText("Today's Target Acheived");
//		}

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        try {
        mGoogleMap = gMap;
       // mGoogleMap.setMyLocationEnabled(true);

        buildGoogleApiClient();

        mGoogleApiClient.connect();

    }catch(Exception ex){ex.printStackTrace();}

    }

    protected synchronized void buildGoogleApiClient() {
       // Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        try {

            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.setTrafficEnabled(true);
            mGoogleMap.setIndoorEnabled(true);
            mGoogleMap.setBuildingsEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }catch(Exception ex){ex.printStackTrace();}
    }

    @Override
    public void onConnected(Bundle bundle) {
        try
        {

       // Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            //mGoogleMap.clear();
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);

            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);
        }

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }catch(Exception ex){ex.printStackTrace();}
    }

    @Override
    public void onConnectionSuspended(int i) {
       // Toast.makeText(this,"onConnectionSuspended",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Toast.makeText(this,"onConnectionFailed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);

        try {
            Geocoder geo = new Geocoder(BasicMapDemoActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.isEmpty()) {
                //yourtextfieldname.setText("Waiting for Location");
                markerOptions.title("Waiting for Location");
               // markerOptions.title("Current Position");
            }
            else {
                if (addresses.size() > 0) {
                    Address returnAddress = addresses.get(0);
                    String localityString = returnAddress.getLocality();
                    String city = returnAddress.getCountryName();
                    String region_code = returnAddress.getCountryCode();
                    String zipcode = returnAddress.getPostalCode();
                    StringBuilder  str = new StringBuilder();
                    str.append(addresses.get(0).getAddressLine(1)+" "+addresses.get(0).getAddressLine(2) + " ");
                    str.append(localityString + " ");
                    str.append(city + " ");
                    str.append(region_code + " ");
                    str.append(zipcode + " ");
//                    yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                    markerOptions.title(str.toString());
                    //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                }
            }

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mGoogleMap.addMarker(markerOptions);

        //Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();

        Log.d("Location change event","Location Change");

        //zoom to current position:
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng).zoom(16).build();

        mGoogleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        }
        catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

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
        Intent i = new Intent(BasicMapDemoActivity.this, MainActivity.class);
        startActivity(i);
       // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }

}