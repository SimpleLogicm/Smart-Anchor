package com.anchor.activities;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.anchor.model.DistanceData;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.ConnectionDetector;
import cpm.simplelogic.helper.GPSTracker;
import cpm.simplelogic.helper.RetrofitMaps;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class BasicMapDemoActivity extends FragmentActivity implements
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    static String g_lat,g_long;
    String map_firstvisit_flag= "true";
    GPSTracker gps;
    String datenn;
    String in_out_flag = "";
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    private Toolbar toolbar;
    LatLng latLng;
    GoogleMap mGoogleMap;
    SupportMapFragment mFragment;
    Marker currLocationMarker;
    ProgressDialog dialog;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Double lat;
    Double longi;
    StringBuilder str;
    Button at_in, at_out;
    TextView show_distance_time,show_time;
    LoginDataBaseAdapter loginDataBaseAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    //public PrefManager prefManager;
    static String final_response = "";
    String response_result = "";
    ImageView Morelocationdetail;
    RelativeLayout distance_la;
    String user_address = "";
    View mapView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ap_demo);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        // prefManager = new PrefManager(this);
        cd = new ConnectionDetector(getApplicationContext());
        dialog = new ProgressDialog(BasicMapDemoActivity.this);
        at_in = (Button) findViewById(R.id.at_in);
        at_out = (Button) findViewById(R.id.at_out);
        distance_la = (RelativeLayout) findViewById(R.id.distance_la);
        show_distance_time = (TextView) findViewById(R.id.show_distance_time);
        show_time = (TextView) findViewById(R.id.show_time);

        str = new StringBuilder();
        str.append(" ");

        android.app.ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        Intent i = getIntent();
        String name = i.getStringExtra("retialer");
        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Location On Map");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = BasicMapDemoActivity.this.getSharedPreferences("SimpleLogic", 0);

//           try
//           {
//               List<Local_Data> contacts2 = dbvoc.getUSERAddressBY_Email(Global_Data.GLOvel_USER_EMAIL);
//
//               if(contacts2.size() > 0)
//               {
//                   for (Local_Data cn : contacts2) {
//                       user_address = cn.getAddress();
//                   }
//
//               }
//           }catch (Exception ex){ex.printStackTrace();}



//		       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//		       //	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//				   todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//				}
        try {
            int target = (int) Math.round(sp.getFloat("Target", 0));
            int achieved = (int) Math.round(sp.getFloat("Achived", 0));
            Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
            if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
            } else {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
            //       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
            todaysTarget.setText("Today's Target Acheived");
        }

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);


        // List<Local_Data> contacts2 = dbvoc.getAllAttendance_Data();

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a yyyy-MM-dd");
        DateFormat date_onlyn = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String daten = sdf.format(date);
        datenn = sdf.format(date);
        String date_only_ss = date_onlyn.format(date);
        Date new_current_daten = null;
        try {
            new_current_daten = date_onlyn.parse(date_only_ss);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millisecondn = new_current_daten.getTime();

//        if(contacts2.size() > 0) {
//            for (Local_Data cn : contacts2) {
//                if (cn.getServer_flag().equalsIgnoreCase("true")) {
//                    dbvoc.getDeleteattendance_datebyserver_flag("true");
//                }
//            }
//        }

        // List<Local_Data> newone = dbvoc.getAllAttendance_Data_bydate(date_only_s);

        if (!(DateUtils.isToday(millisecondn))) {
            dbvoc.getDeleteTable("attendence_f");
            loginDataBaseAdapter.insertattendence_flag("false");

        }

        mFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);

        mapView = mFragment.getView();
        mFragment.getMapAsync(this);






        at_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(BasicMapDemoActivity.this);
                if(!gps.canGetLocation()){
//						 Toast toast = Toast.makeText(LoginActivity.this,"Your GPS is off,Please on it.", Toast.LENGTH_LONG);
//						 toast.setGravity(Gravity.CENTER, 0, 0);
//						 toast.show();
                    gps.showSettingsAlertnew();
                }
                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a yyyy-MM-dd");
                    DateFormat date_only = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String daten = sdf.format(date);
                    datenn = sdf.format(date);
                    String date_only_s = date_only.format(date);
                    String a_check_datan = "";

                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {


                        List<Local_Data> a_checkn = dbvoc.getAllAttendanceF_Data();
                        if (a_checkn.size() > 0) {
                            for (Local_Data cn : a_checkn) {
                                a_check_datan = cn.getName();
                            }
                        } else {
                            loginDataBaseAdapter.insertattendence_flag("false");
                            a_check_datan = "false";
                        }



                        List<Local_Data> contadfg = dbvoc.getAllAttendance_Data_bydate(date_only_s);

//                        if (contadfg.size() <= 0 && a_check_datan.equalsIgnoreCase("false") && !user_address.equalsIgnoreCase("") &&  !Global_Data.address.equalsIgnoreCase("")) {
                        if (contadfg.size() <= 0 && a_check_datan.equalsIgnoreCase("false")) {

                            dialog.setMessage("Please wait....");
                            dialog.setTitle("Anchor App");
                            dialog.setCancelable(false);
                            dialog.show();

                            getatte_Data();

                           // build_retrofit_and_get_response("driving",user_address,"Attendance");
                        }
                        else
                        {
//                            if(user_address.equalsIgnoreCase(""))
//                            {
//                                Toast.makeText(BasicMapDemoActivity.this, "User address not found in database.", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            if(Global_Data.address.equalsIgnoreCase(""))
//                            {
//                                Toast.makeText(BasicMapDemoActivity.this, "Current location address not found.", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {
                                Toast.makeText(BasicMapDemoActivity.this, "You Have Already Punched Your Attendance", Toast.LENGTH_SHORT).show();
                           // }

                        }

                    }
                    else
                    {
                        Toast.makeText(BasicMapDemoActivity.this, " Internet not available.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        at_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, BasicMapDemoActivity.this);
                    try {

//                        String user_email = prefManager.get_USERNAME().trim();
//                        String conference_id = prefManager.get_ORDER_TYPE_ID().trim();
//                        String vertical_id = prefManager.get_BRAND_ID().trim();

                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a yyyy-MM-dd");
                        DateFormat date_only = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String daten = sdf.format(date);
                        String date_only_s = date_only.format(date);
                        String datenn = sdf.format(date);

                        String a_check_data = "";
                        List<Local_Data> a_check = dbvoc.getAllAttendanceF_Data();
                        if (a_check.size() > 0) {
                            for (Local_Data cn : a_check) {
                                a_check_data = cn.getName();
                            }
                        }

                        if (a_check_data.equalsIgnoreCase("true")) {
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {


                                dialog.setMessage("Please wait....");
                                dialog.setTitle("Metal App");
                                dialog.setCancelable(false);
                                dialog.show();

                                in_out_flag = "OUT";
                                Attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "OUT", Global_Data.address);
                                //break;
                            } else {
                                in_out_flag = "OUT";
                                loginDataBaseAdapter.insert_attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "OUT", Global_Data.address, "false", date_only_s);
                                Toast.makeText(BasicMapDemoActivity.this, "Out successfully.", Toast.LENGTH_SHORT).show();

                                dbvoc.getDeleteTable("attendence_f");
                                loginDataBaseAdapter.insertattendence_flag("false");

                                showDialogn(daten, str.toString());
                                // break;
                            }
                        } else {
                            Toast.makeText(BasicMapDemoActivity.this, "Please Press IN Button", Toast.LENGTH_SHORT).show();
                            // prefManager.setATTENDANCE_FLAG("false");
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            }
        });
    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        try {
            mGoogleMap = gMap;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mGoogleMap.setMyLocationEnabled(true);

            if (mapView != null &&
                    mapView.findViewById(Integer.parseInt("1")) != null) {
                // Get the button view
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                // and next place it, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        locationButton.getLayoutParams();
                // position on right bottom
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,  RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                layoutParams.setMargins(0, 110, 10, 30);
            }

            buildGoogleApiClient();

            mGoogleApiClient.connect();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        // Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        try {

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

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
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

            lat = location.getLatitude();
            longi = location.getLongitude();

            Global_Data.GLOvel_LATITUDE = String.valueOf(lat);
            Global_Data.GLOvel_LONGITUDE = String.valueOf(longi);

//            prefManager.setLATITUDE(String.valueOf(lat));
//            prefManager.setLONGITUDE(String.valueOf(longi));

            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
                try {
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
                            BasicMapDemoActivity.this, new BasicMapDemoActivity.GeocoderHandler());
                    Geocoder geo = new Geocoder(BasicMapDemoActivity.this.getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses.isEmpty()) {
                        //yourtextfieldname.setText("Waiting for Location");
                        markerOptions.title("Waiting for Location");
                        // markerOptions.title("Current Position");
                    } else {
                        if (addresses.size() > 0) {
                            Address returnAddress = addresses.get(0);
                            String localityString = returnAddress.getLocality();
                            String city = returnAddress.getCountryName();
                            String region_code = returnAddress.getCountryCode();
                            String zipcode = returnAddress.getPostalCode();
                            str = new StringBuilder();
                            str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getAddressLine(0))+" ");
                            if(!(str.indexOf(addresses.get(0).getLocality()) > 0))
                            {
                                str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getLocality())+" ");
                            }

                            if(!(str.indexOf(addresses.get(0).getAdminArea()) > 0))
                            {
                                str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getAdminArea())+" ");
                            }

                            if(!(str.indexOf(addresses.get(0).getCountryName()) > 0))
                            {
                                str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getCountryName())+" ");
                            }

                            if(!(str.indexOf(addresses.get(0).getPostalCode()) > 0))
                            {
                                str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getPostalCode())+" ");
                            }

                            str.append("\n");

                            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str.toString()))
                            {
                                Global_Data.address =  str.toString();


//                                if(map_firstvisit_flag.equalsIgnoreCase("true"))
//                                {
//
//                                }
                              //  if(map_firstvisit_flag.equalsIgnoreCase("true") && !user_address.equalsIgnoreCase(""))
//                                if(map_firstvisit_flag.equalsIgnoreCase("true"))
//                                {
//                                    map_firstvisit_flag = "false";
//                                    dialog.setMessage("Please wait....");
//                                    dialog.setTitle("Metal App");
//                                    dialog.setCancelable(false);
//                                    dialog.show();
//                                    getatte_Data();
//
//                                   // build_retrofit_and_get_response("driving",user_address,"location");
//                                }
                            }
                            else
                            {
                                Global_Data.address = "";
                              //  Toast.makeText(BasicMapDemoActivity.this, "Current location address not found.", Toast.LENGTH_SHORT).show();
                            }

                            //prefManager.setAddress(str.toString());
//                    str.append(city + " ");
//                    str.append(region_code + " ");
//                    str.append(zipcode + " ");
//                    yourtextfieldname.setText(addresses.get(0).getFeatureName() + ", " + addresses.get(0).getLocality() +", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName());
                            if(str.equals(Global_Data.address))
                            {
                                markerOptions.title(str.toString());
                            }
                            else
                            {
                                markerOptions.title(Global_Data.address);
                            }

                            //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
            else
            {
                Global_Data.address = "";
            }


            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            currLocationMarker = mGoogleMap.addMarker(markerOptions);

            //Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();

            Log.d("Location change event", "Location Change");

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
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();

    }

    @Override
    public void onResume() {
        mFragment.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
        mFragment.onPause();
        mGoogleMap.clear();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        try {
            dialog.dismiss();
            mGoogleMap.clear();
            mFragment.onDestroy();
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        SupportMapFragment f = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googleMap);
        if (!BasicMapDemoActivity.this.isFinishing()) {
            if (f != null) {
                mFragment.onDestroy();
                mGoogleMap.clear();
                // LocationServices.FusedLocationApi.removeLocationUpdates()
            }
        }


    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mFragment.onLowMemory();
        mGoogleMap.clear();
    }

    public void showDialogn(String time, String address) {
        final Dialog dialognew = new Dialog(BasicMapDemoActivity.this);
        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialognew.setCancelable(false);
        dialognew.setContentView(R.layout.attendance_dialog);

        TextView inh = (TextView) dialognew.findViewById(R.id.inh);
        TextView att_time = (TextView) dialognew.findViewById(R.id.att_time);
        TextView att_address = (TextView) dialognew.findViewById(R.id.att_address);
        TextView att_lat = (TextView) dialognew.findViewById(R.id.att_lat);
        TextView att_long = (TextView) dialognew.findViewById(R.id.att_long);
        Button atok = (Button) dialognew.findViewById(R.id.atok);


        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a yyyy-MM-dd");
        Date date = new Date();
        // String daten = sdf.format(date);
        String datenew = sdf.format(date);

        if (in_out_flag.equalsIgnoreCase("OUT")) {
            inh.setText("OUT DETAILS");
        } else {
            inh.setText("IN DETAILS");
        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
        }


        inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.address)) {
            String str = Global_Data.address.trim().replaceAll("\n", " ");
            att_address.setText("Place : " + str);
        } else {
            att_address.setText("Place Not Found.");
        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(datenew)) {
            try {
                String timenewarray[] = datenew.split("-");
                String month = getMonthForInt(Integer.parseInt(timenewarray[1]) - 1);
                att_time.setText("Time : " + timenewarray[0] + "-" + month + "-" + timenewarray[2]);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            att_time.setText("Time Not Found.");
        }

        atok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialognew.dismiss();

            }
        });

        dialognew.show();

    }


    public void Attendance_data(String user_email, String daten, String lat, String longi, String IN, String addresss) {
        String domain = getResources().getString(R.string.service_domain);
//        String domain = prefManager.get_SERVER_DOMAIN();

//        Log.i("volley", "domain: " + domain);
//        Log.i("volley", "email: " + user_email);
//        Log.i("user list url", "order list url " + domain + "customers/send_customer_address?imei_no=" + prefManager.get_DEVICE_ID() +"&email="+user_email+"&conference_code="+prefManager.get_ORDER_TYPE_ID().trim());


        JSONArray order = new JSONArray();
        JSONObject product_valuenew = new JSONObject();

        JSONObject product_value = new JSONObject();


        try {
            //  product_value.put("order_number", Global_Data.Order_Id);

            // product_value.put("user_id", user_email);
            product_value.put("punched_on", daten);
            product_value.put("punched_at_latitude", lat);
            product_value.put("punched_at_longitude", longi);
            product_value.put("punched_button", IN);
//            product_value.put("conference_code", conference_id);
//            product_value.put("business_vertical_code", vertical_id);
            product_value.put("punched_at_address", addresss);

            order.put(product_value);

            //  product_valuenew.put("order_number", Global_Data.Order_Id);
            product_valuenew.put("attendance", product_value);


            Log.d("attendances value", product_valuenew.toString());

            String service_domain = domain + "attendances?imei_no=" + Global_Data.device_id + "&email=" + user_email;

            Log.i("volley", "domain: " + service_domain);


            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, service_domain, product_valuenew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    final_response = response.toString();

                    new BasicMapDemoActivity.getINAttendanceresponse().execute(final_response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();


//                        Intent intent = new Intent(Attendance_Map.this, Attendance_Map.class);
//                        startActivity(intent);
//                        finish();
                            //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(),
                                        "Network Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(),
                                        "Server AuthFailureError  Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getApplicationContext(),
                                        "Server   Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(),
                                        "Network   Error",
                                        Toast.LENGTH_LONG).show();
                            } else if (error instanceof ParseError) {
                                Toast.makeText(getApplicationContext(),
                                        "ParseError   Error",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                            // finish();
                        }
                    });


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            int socketTimeout = 300000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            // requestQueue.se
            //requestQueue.add(jsObjRequest);
            jsObjRequest.setShouldCache(true);
            //requestQueue.getCache().clear();
            requestQueue.add(jsObjRequest);
            //  Volley.newRequestQueue(this).add(jsObjRequest);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class getINAttendanceresponse extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                } else {
                    response_result = "data";
                }


                if (response_result.equalsIgnoreCase("User doesn't exist")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            dialog.dismiss();
                            // recyclerView.setVisibility(View.INVISIBLE);
                            Toast toast = Toast.makeText(BasicMapDemoActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else if (response_result.equalsIgnoreCase("User not registered")) {

                    //Toast.makeText(Attendance_Map.this, response_result, Toast.LENGTH_LONG).show();

                    BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(BasicMapDemoActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });

                } else if (response_result.equalsIgnoreCase("Device not registered")) {

                    //Toast.makeText(Attendance_Map.this, response_result, Toast.LENGTH_LONG).show();

                    BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();


                            Toast toast = Toast.makeText(BasicMapDemoActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else if (response_result.equalsIgnoreCase("Conference not found")) {

                    //Toast.makeText(Attendance_Map.this, response_result, Toast.LENGTH_LONG).show();

                    BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();


                            Toast toast = Toast.makeText(BasicMapDemoActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        }
                    });


                } else {

                    BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();


                            Toast toast = Toast.makeText(BasicMapDemoActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                            showDialogn(datenn, str.toString());


                        }
                    });


                    //dbvoc.getDeleteTable("attendance");
                    dbvoc.getDeleteattendance_daten(datenn, in_out_flag);

                    if (in_out_flag.equalsIgnoreCase("IN")) {
                        dbvoc.getDeleteTable("attendence_f");
                        loginDataBaseAdapter.insertattendence_flag("true");
                    } else {
                        dbvoc.getDeleteTable("attendence_f");
                        loginDataBaseAdapter.insertattendence_flag("false");
                    }


                    BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            dialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();


                BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });

            }


            BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            BasicMapDemoActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });


        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress = "";
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    //locationAddress = " ";
            }
            //  LOCATION.setText(locationAddress);


            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(locationAddress)) {
                str = new StringBuilder();
                //prefManager.setAddress(locationAddress);
                Log.d("GLOBEL ADDRESS G", "V" + locationAddress);
                str.append("");
                str.append(locationAddress);

            } else {
                str = new StringBuilder();
                Log.d("GLOBEL ADDRESS G", "address not found.");
                //prefManager.setAddress("");
                str.append("");
            }


        }
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {

            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .showInputMethodPicker();
            Toast.makeText(this, "Barcode Scanner detected. Please turn OFF Hardware/Physical keyboard to enable softkeyboard to function.", Toast.LENGTH_LONG).show();
        }
    }

    private void build_retrofit_and_get_response(String type, final String user_address, final String att_flags) {

        String url = "https://maps.googleapis.com/maps/";

        final String att_flag =att_flags;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitMaps service = retrofit.create(RetrofitMaps.class);

//        Call<DistanceData> call = service.getDistanceDuration("metric", "19.310472" + "," +"72.854041","19.079024" + "," +" 72.908012", type);


        Global_Data.address =Global_Data.address.trim().replaceAll("\n", " ");
        Call<DistanceData> call = service.getDistanceDuration("metric", Global_Data.address,user_address, type);

        call.enqueue(new Callback<DistanceData>() {
            @Override
            public void onResponse(retrofit.Response<DistanceData> response, Retrofit retrofit) {

                try {
                    //Remove previous line from map
//                    if (line != null) {
//                        line.remove();
//                    }
                    // This loop will go through all the results and add marker on each location.



                    if(response.body().getRoutes().size() <=0)
                    {
                        try
                        {
                            Location locationA = new Location(Global_Data.address);
                            locationA.setLatitude(Float.valueOf(Global_Data.GLOvel_LATITUDE));
                            locationA.setLongitude(Float.valueOf(Global_Data.GLOvel_LONGITUDE));

                            Location locationB = new Location(user_address);

                            Geocoder coder = new Geocoder(getApplicationContext(), getResources().getConfiguration().locale);
                            List<Address> address;
                            // GeoPoint p1 = null;

                            try {
                                address = coder.getFromLocationName(user_address, 5);
                                if (address == null) {
                                    // return null;

                                }
                                Address location = address.get(0);
                                location.getLatitude();
                                location.getLongitude();

                                locationB.setLatitude( location.getLatitude());
                                locationB.setLongitude(location.getLongitude());
                                float distancedfgdf = locationA.distanceTo(locationB)/1000;

                                distance_la.setVisibility(View.VISIBLE);
                                show_distance_time.setText("Distance Covered : " + distancedfgdf);
                                show_time.setText("");

                                int retval = Double.compare(distancedfgdf, 0.5);

                                if(retval > 0) {
                                    // Toast.makeText(BasicMapDemoActivity.this, "greater .5 ", Toast.LENGTH_SHORT).show();
                                    getatte_Data();
                                }
                                else
                                {
                                    dialog.dismiss();
//                                        Toast.makeText(BasicMapDemoActivity.this, "Please punch attendance after 0.5 km ", Toast.LENGTH_SHORT).show();
                                    AlertDialog alertDialog = new AlertDialog.Builder(BasicMapDemoActivity.this).create(); //Read Update
                                    alertDialog.setTitle("Metal");
                                    alertDialog.setMessage("You should be at least 0.5 KM away from your base address- "+user_address);
                                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    //  alertDialog.setCancelable(false);
                                    //  alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();

                                }


                            }
                            catch(Exception ex){ex.printStackTrace();

                                g_lat = "";
                                g_long = "";
                                Thread thread = new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        try  {
                                            getLatLongFromGivenAddress(user_address);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                                thread.start();


                                if(!g_lat.equalsIgnoreCase("") && !g_long.equalsIgnoreCase(""))
                                {
                                    locationB.setLatitude(Float.valueOf(g_lat));
                                    locationB.setLongitude(Float.valueOf(g_long));
                                    float distancedfgdf = locationA.distanceTo(locationB)/1000;

                                    distance_la.setVisibility(View.VISIBLE);
                                    show_distance_time.setText("Distance Covered : " + distancedfgdf);
                                    show_time.setText("");
                                    g_lat = "";
                                    g_long = "";

                                    int retval = Double.compare(distancedfgdf, 0.5);

                                    if(retval > 0) {
                                        // Toast.makeText(BasicMapDemoActivity.this, "greater .5 ", Toast.LENGTH_SHORT).show();
                                        getatte_Data();
                                    }
                                    else
                                    {
                                        dialog.dismiss();
//                                        Toast.makeText(BasicMapDemoActivity.this, "Please punch attendance after 0.5 km ", Toast.LENGTH_SHORT).show();
                                        AlertDialog alertDialog = new AlertDialog.Builder(BasicMapDemoActivity.this).create(); //Read Update
                                        alertDialog.setTitle("Metal");
                                        alertDialog.setMessage("You should be at least 0.5 KM away from your base address- "+user_address);
                                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        //  alertDialog.setCancelable(false);
                                        //  alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();

                                    }
                                }
                                else
                                {
                                    distance_la.setVisibility(View.GONE);
                                    show_distance_time.setText("");
                                    show_time.setText("");
                                    Toast.makeText(BasicMapDemoActivity.this, "Distance not found. ", Toast.LENGTH_SHORT).show();
                                }



                            }




                        }catch(Exception ex){
                            ex.printStackTrace();

                            Toast.makeText(BasicMapDemoActivity.this, "Distance not found. ", Toast.LENGTH_SHORT).show();
                            distance_la.setVisibility(View.GONE);
                            show_distance_time.setText("");
                            show_time.setText("");
                        }
                        dialog.dismiss();



                    }
                    else
                    {
                        for (int i = 0; i < response.body().getRoutes().size(); i++) {

                            try
                            {
                                distance_la.setVisibility(View.VISIBLE);
                                String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                                String distance_value = String.valueOf(response.body().getRoutes().get(i).getLegs().get(i).getDistance().getValue());
                                String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                                show_distance_time.setText("Distance Covered : " + distance);
                                show_time.setText("Duration : " + time);

                                Double distancen = Double.valueOf(distance_value)/1000.0;

                                int retval = Double.compare(distancen, 0.5);

                                if(att_flag.equalsIgnoreCase("Attendance"))
                                {
                                    if(retval > 0) {
                                       // Toast.makeText(BasicMapDemoActivity.this, "greater .5 ", Toast.LENGTH_SHORT).show();
                                        getatte_Data();
                                    }
                                    else
                                    {
                                        dialog.dismiss();
//                                        Toast.makeText(BasicMapDemoActivity.this, "Please punch attendance after 0.5 km ", Toast.LENGTH_SHORT).show();
                                        AlertDialog alertDialog = new AlertDialog.Builder(BasicMapDemoActivity.this).create(); //Read Update
                                        alertDialog.setTitle("Metal");
                                        alertDialog.setMessage("You should be at least 0.5 KM away from your base address- "+user_address);
                                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                               dialog.dismiss();
                                            }
                                        });

                                      //  alertDialog.setCancelable(false);
                                      //  alertDialog.setCanceledOnTouchOutside(false);
                                        alertDialog.show();

                                    }
                                }
                                else
                                {
                                    dialog.dismiss();
                                }


                            }catch(Exception ex){
                                ex.printStackTrace();
                            }




//                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
//                        List<LatLng> list = decodePoly(encodedString);
//                        line = mMap.addPolyline(new PolylineOptions()
//                                .addAll(list)
//                                .width(20)
//                                .color(Color.RED)
//                                .geodesic(true)
//                        );
                        }
                    }

                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                    dialog.dismiss();
                }


            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
                dialog.dismiss();
            }
        });

    }

    public void getatte_Data()
    {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, BasicMapDemoActivity.this);
            try {

//                        String user_email = prefManager.get_USERNAME().trim();
//                        String conference_id = prefManager.get_ORDER_TYPE_ID().trim();
//                        String vertical_id = prefManager.get_BRAND_ID().trim();

                //android.text.format.DateFormat df = new android.text.format.DateFormat();
                // String daten = df.format("hh:mm a yyyy-MM-dd", new java.util.Date()).toString();

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a yyyy-MM-dd");
                DateFormat date_only = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String daten = sdf.format(date);
                datenn = sdf.format(date);
                String date_only_s = date_only.format(date);
//
//                String a_check_datan = "";
//                List<Local_Data> a_checkn = dbvoc.getAllAttendanceF_Data();
//                if (a_checkn.size() > 0) {
//                    for (Local_Data cn : a_checkn) {
//                        a_check_datan = cn.getName();
//                    }
//                } else {
//                    loginDataBaseAdapter.insertattendence_flag("false");
//                    a_check_datan = "false";
//                }
//
//                List<Local_Data> contacts2 = dbvoc.getAllAttendance_Data_bydate(date_only_s);

               // if (contacts2.size() <= 0 && a_check_datan.equalsIgnoreCase("false")) {

                    isInternetPresent = cd.isConnectingToInternet();
                    if (isInternetPresent) {


                        in_out_flag = "IN";

                        Attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "IN", Global_Data.address);
                    } else {
                        in_out_flag = "IN";
                        Toast.makeText(BasicMapDemoActivity.this, "You don't have internet connection.Your Intime save successfully in offline mode.", Toast.LENGTH_SHORT).show();
                        //loginDataBaseAdapter.insert_attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "IN","false",date_only_s);
                        loginDataBaseAdapter.insert_attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "IN", Global_Data.address, "false", date_only_s);


                        dbvoc.getDeleteTable("attendence_f");
                        loginDataBaseAdapter.insertattendence_flag("true");
                        Toast.makeText(BasicMapDemoActivity.this, "Attendance punch successfully.", Toast.LENGTH_SHORT).show();
                        showDialogn(daten, str.toString());
                    }

//                } else {
////                            for (Local_Data cn : contacts2)
////                            {
//                    //  String database_date = cn.getCurrent_date_only();
//                    // String server_flag = cn.getServer_flag();
//                    // String button_flag = cn.getPunched_button();
//                    // Date date_check_date = date_only.parse(database_date);
//                    Date new_current_date = date_only.parse(date_only_s);
//                    //Date newdate = format.parse(daten);
//                    long millisecond = new_current_date.getTime();
//                    //Date newdate = format.parse(daten);
//
//                    String a_check_data = "";
//                    List<Local_Data> a_check = dbvoc.getAllAttendanceF_Data();
//                    if (a_check.size() > 0) {
//                        for (Local_Data cn : a_check) {
//                            a_check_data = cn.getName();
//                        }
//                    }
//
//                    // Toast.makeText(Attendance_Map.this, ""+a_check_data, Toast.LENGTH_SHORT).show();
//
//                    if (DateUtils.isToday(millisecond) && a_check_data.equalsIgnoreCase("true")) {
////                                    Toast.makeText(BasicMapDemoActivity.this, "You already punch attendance at "+database_date, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(BasicMapDemoActivity.this, "You Have Already Punched Your Attendance", Toast.LENGTH_SHORT).show();
//
//                    } else if (DateUtils.isToday(millisecond) && a_check_data.equalsIgnoreCase("false")) {
//
//                        isInternetPresent = cd.isConnectingToInternet();
//                        if (isInternetPresent) {
//
//                            dialog.setMessage("Please wait....");
//                            dialog.setTitle("Siyaram App");
//                            dialog.setCancelable(false);
//                            dialog.show();
//
//                            in_out_flag = "IN";
//                            Attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "IN", Global_Data.address);
//                        } else {
//                            in_out_flag = "IN";
//                            Toast.makeText(BasicMapDemoActivity.this, "You don't have internet connection.Your Intime save successfully in offline mode.", Toast.LENGTH_SHORT).show();
//                            loginDataBaseAdapter.insert_attendance_data(Global_Data.GLOvel_USER_EMAIL, daten, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "IN", Global_Data.address, "false", date_only_s);
//
//                            dbvoc.getDeleteTable("attendence_f");
//                            loginDataBaseAdapter.insertattendence_flag("true");
//                            Toast.makeText(BasicMapDemoActivity.this, "Attendance punch successfully.", Toast.LENGTH_SHORT).show();
//                            showDialogn(daten, str.toString());
//                        }
//                    }
//                    //}
//                }

//
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void getLatLongFromGivenAddress(String youraddress) {
        String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
                youraddress + "&sensor=false";
        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            g_lat = String.valueOf(((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng"));

            g_long = String.valueOf(((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat"));

            Log.d("latitude", g_lat);
            Log.d("longitude", g_long);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}