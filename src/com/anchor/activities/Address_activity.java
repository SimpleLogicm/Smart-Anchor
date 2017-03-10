package com.anchor.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Address_activity extends Activity {

    TextView textview;
    Button buttonx,ghgfh;
    GPSTracker_n gpsTracker = new GPSTracker_n(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_layout);

        // check if GPS enabled
      

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            textview = (TextView)findViewById(R.id.fieldLatitude);
            buttonx = (Button)findViewById(R.id.buttonx);
            textview.setText(stringLatitude);

            
            buttonx.setOnClickListener(new OnClickListener() {
	   	         public void onClick(View view) {
	   	        	 
	   	        	 String stringLongitude = String.valueOf(gpsTracker.longitude);
	   	            textview = (TextView)findViewById(R.id.fieldLongitude);
	   	            textview.setText(stringLongitude);

	   	            String country = gpsTracker.getCountryName(Address_activity.this);
	   	            textview = (TextView)findViewById(R.id.fieldCountry);
	   	            textview.setText(country);

	   	            String city = gpsTracker.getLocality(Address_activity.this);
	   	            textview = (TextView)findViewById(R.id.fieldCity);
	   	            textview.setText(city);

	   	            String postalCode = gpsTracker.getPostalCode(Address_activity.this);
	   	            textview = (TextView)findViewById(R.id.fieldPostalCode);
	   	            textview.setText(postalCode);

	   	            String addressLine = gpsTracker.getAddressLine(Address_activity.this);
	   	            textview = (TextView)findViewById(R.id.fieldAddressLine);
	   	            textview.setText(addressLine);
	   	        	 
	   	         }
            });

           
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.varna_lab_geo_locations, menu);
        return true;
    }
}