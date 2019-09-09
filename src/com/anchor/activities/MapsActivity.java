package com.anchor.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.anchor.adapter.Sub_DealerMap_Adapter;
import com.anchor.helper.PrefManager;
import com.anchor.model.InfoWindowData;
import com.anchor.model.SubDealerModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;
import static com.anchor.activities.Global_Data.AllresultSubDealer;
import static com.anchor.activities.Global_Data.SubDealer_List;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnInfoWindowClickListener, LocationListener {
    List<LatLng> locations;
    static int ab = 0;
    JSONObject jsonObject;
    ArrayList<String> address = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> distance = new ArrayList<>();
    ArrayList<String> mobile = new ArrayList<>();
    ArrayList<String> code = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    static String final_response = "";
    String response_result = "";
    ProgressDialog dialog;
    private GoogleMap mMap;
    StringBuilder str;
    LatLng myPosition;
    View mapView;
    LatLng latLng;
    GoogleApiClient mGoogleApiClient;
    Marker currLocationMarker;
    LocationRequest mLocationRequest;
    Toast toastMessage;
    List<String> s = new ArrayList<String>();
    private PrefManager prefManager;
    Marker m;
    RecyclerView marker_rview;
    Sub_DealerMap_Adapter ca;
    List<SubDealerModel> Allresult = new ArrayList<SubDealerModel>();
    LinearLayoutManager llm;
    private int visibleItemCount, totalItemCount, firstVisibleItemPosition, lastVisibleItem;
    public Marker marker;
    String click_flag = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global_Data.selectedPosition = -1;
        Global_Data.mMarkers.clear();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.content_maps);

        marker_rview = findViewById(R.id.marker_rview);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        prefManager = new PrefManager(this);

        // marker_rview.setHasFixedSize(true);


        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        llm.setReverseLayout(true);
        marker_rview.setLayoutManager(llm);

        marker_rview.addOnScrollListener(onScrollListener);


        // map_add_toast_flag = "";

        cd = new ConnectionDetector(getApplicationContext());


    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = llm.getChildCount();
            totalItemCount = llm.getItemCount();
            firstVisibleItemPosition = llm.findFirstVisibleItemPosition();
            lastVisibleItem = firstVisibleItemPosition + visibleItemCount;

            String code = Allresult.get(lastVisibleItem - 1).getCode();
            String lati = Allresult.get(lastVisibleItem - 1).getLati();
            String longi = Allresult.get(lastVisibleItem - 1).getLongi();
            String name = Allresult.get(lastVisibleItem - 1).getName();
            String distance = Allresult.get(lastVisibleItem - 1).getDescription();

            Log.d("Last_Visible", "Last_Visible " + lastVisibleItem);
            Log.d("First_Visible", "First_Visible " + firstVisibleItemPosition);
            Log.d("CODE", "CODE " + code);

            if (click_flag.equalsIgnoreCase("")) {
                LatLng mLatLng = new LatLng(Double.valueOf(lati), Double.valueOf(longi));

                if (marker != null && marker.isVisible()) {
                    marker.remove();
                }
                marker = mMap.addMarker(new MarkerOptions().position(mLatLng).title(name).snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon)));

                //hashMapMarker.put(code, marker);
                LatLngBounds.Builder buildern = new LatLngBounds.Builder();

                try {
                    buildern.include(marker.getPosition());
                    Global_Data.mMarkers.get(lastVisibleItem - 1).showInfoWindow();
                    //infowindow.open(map,marker);
                } catch (Exception ex) {

                }


                try {

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : Global_Data.mMarkers) {
                        builder.include(marker.getPosition());
                    }

                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                    mMap.moveCamera(cu);

//                    LatLngBounds bounds = buildern.build();
//
//                    int width = getResources().getDisplayMetrics().widthPixels;
//                    int height = getResources().getDisplayMetrics().heightPixels;
//                    int padding = (int) (width * 0.005); // offset from edges of the map 10% of
//                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                    mMap.animateCamera(cu);


                } catch (Exception ex) {

                }
            } else {
                click_flag = "";
            }


        }
    };

    @Override
    public void onInfoWindowClick(Marker marker) {

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();


        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(infoWindowData.getCmobile())) {
            requestPHONEPermission(infoWindowData.getCmobile());
        }
    }

    public Marker getmarker() {
        return marker;
    }

    public void setmarker(Marker markers) {
        this.marker = markers;
    }

    public GoogleMap getmap() {
        return mMap;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.8708488, 151.1879368);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    } */

  /*  @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(MapsActivity.this,R.drawable.my_dp,"Yasir Ameen"))).position(sydney).title("Marker is near in Sydney"));
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sydney, 13);
        mMap.animateCamera(update);
    }*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // mGoogleMap = gMap;
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
            mMap.setMyLocationEnabled(true);

            if (mapView != null &&
                    mapView.findViewById(Integer.parseInt("1")) != null) {
                // Get the button view
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                // and next place it, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        locationButton.getLayoutParams();
                // position on right bottom
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);

                layoutParams.setMargins(0, 110, 10, 30);
            }

            buildGoogleApiClient();

            mGoogleApiClient.connect();

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    marker_rview.setVisibility(View.VISIBLE);
                    String code = String.valueOf(marker.getTag());
                    Log.d("id", "code" + code);


                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(code)) {
                        int positionn = -1;
                        String lati = "";
                        String longi = "";
                        for (int i = 0; i < Allresult.size(); i++) {
                            if (Allresult.get(i).getCode() == code) {
                                positionn = i;

                                lati = Allresult.get(i).getLati();
                                longi = Allresult.get(i).getLongi();

                                break;  // uncomment to get the first instance
                            }
                        }

                        if (positionn != -1) {
                            Global_Data.selectedPosition = positionn;
                            marker_rview.getLayoutManager().scrollToPosition(positionn);
                        }

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(lati) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(longi)) {

                            LatLng mLatLng = new LatLng(Double.valueOf(lati), Double.valueOf(longi));

                            if (MapsActivity.this.marker != null && MapsActivity.this.marker.isVisible()) {
                                MapsActivity.this.marker.remove();
                            }
                            MapsActivity.this.marker = mMap.addMarker(new MarkerOptions().position(mLatLng).title("").snippet("").icon(BitmapDescriptorFactory.fromResource(R.drawable.star_icon)));

                            //hashMapMarker.put(code, marker);
                            LatLngBounds.Builder buildern = new LatLngBounds.Builder();

                            try {
                                buildern.include(MapsActivity.this.marker.getPosition());
                                //infowindow.open(map,marker);
                            } catch (Exception ex) {

                            }

                            try {
                                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                for (Marker markers : Global_Data.mMarkers) {
                                    builder.include(markers.getPosition());
                                }

                                LatLngBounds bounds = builder.build();
                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                                mMap.moveCamera(cu);

//                                LatLngBounds bounds = buildern.build();
//
//                                int width = getResources().getDisplayMetrics().widthPixels;
//                                int height = getResources().getDisplayMetrics().heightPixels;
//                                int padding = (int) (width * 0.005); // offset from edges of the map 10% of
//                                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                                mMap.animateCamera(cu);


                            } catch (Exception ex) {

                            }

                        }


                        click_flag = "yes";


                    }
                    return false;
                }
            });

//            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//
//                @Override
//                public void onInfoWindowClick(Marker arg0) {
//
//                    // InfoWindowData infoWindowData = (InfoWindowData) arg0.getTag();
//                    // arg0.hideInfoWindow();
//                    marker_rview.setVisibility(View.VISIBLE);
////                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(infoWindowData.getS_code())) {
////                        String code = infoWindowData.getS_code();
////                        int position = -1;
////                        for (int i = 0; i < Allresult.size(); i++) {
////                            if (Allresult.get(i).getCode() == code) {
////                                position = i;
////                                 break;  // uncomment to get the first instance
////                            }
////                        }
////
////                        if(position != -1)
////                        {
////                            marker_rview.getLayoutManager().scrollToPosition(position);
////                        }
////
////
////                    }
//                    Log.d("id", "gfhgfh" + arg0.getId());
//
//
//                }
//            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //When Map Loads Successfully
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Your code where exception occurs goes here...

                if (cd.isConnectedToInternet()) {
                    dialog = new ProgressDialog(MapsActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
                    dialog.setMessage("Please wait....");
                    dialog.setTitle("Smart Anchor App");
                    dialog.setCancelable(false);
                    dialog.show();

                    getUserGeoData();
                } else {

                    Toast.makeText(MapsActivity.this, "You don't have internet connection.", Toast.LENGTH_SHORT).show();
                    finish();
                }
//                List<LatLng> locations = new ArrayList<>();
//                locations.add(new LatLng(24.821730, 67.024680));
//                locations.add(new LatLng(24.823327, 67.028414));
//                locations.add(new LatLng(24.823288, 67.031568));
//                locations.add(new LatLng(24.824677, 67.033982));
//                locations.add(new LatLng(24.823093, 67.035559));
//                locations.add(new LatLng(24.822489, 67.036632));

//                int a = 0;
//                for (LatLng latLng : locations) {
//
//                    mMap.addMarker(new MarkerOptions().position(latLng).title(s.get(a++)));
//                }
//
//                //LatLngBound will cover all your marker on Google Maps
//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                builder.include(locations.get(0)); //Taking Point A (First LatLng)
//                builder.include(locations.get(locations.size() - 1)); //Taking Point B (Second LatLng)
//                LatLngBounds bounds = builder.build();
//                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
//                mMap.moveCamera(cu);
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);

                //   Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.marker_icon, null);
                //  Bitmap markerBitmap = ((BitmapDrawable)myDrawable).getBitmap();

//                // Add a marker in Sydney and move the camera
//                LatLng sydney = new LatLng(24.821730, 67.024680);
//                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)).position(sydney).title("hey vinod"));
//                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(sydney, 13);
//                mMap.animateCamera(update);
//
//                // Add a marker in Sydney and move the camera
//                LatLng sydney1 = new LatLng(24.823327, 67.028414);
//                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(markerBitmap)).position(sydney1).title("hey Manoj"));
//                CameraUpdate update1 = CameraUpdateFactory.newLatLngZoom(sydney1, 13);
//                mMap.animateCamera(update1);


            }
        });


    }

//    public static Bitmap createCustomMarker(Context context, @DrawableRes int resource, String _name) {
//
//        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
//
//        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
//        markerImage.setImageResource(resource);
//        TextView txt_name = (TextView) marker.findViewById(R.id.name);
//        txt_name.setText(_name);
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
//        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
//        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
//        marker.buildDrawingCache();
//        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        marker.draw(canvas);
//
//        return bitmap;
//    }

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
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);
            mMap.setIndoorEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

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
    public void onLocationChanged(Location location) {

        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }


        try {

            latLng = new LatLng(location.getLatitude(), location.getLongitude());
            // InfoWindowData info = new InfoWindowData();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            // info.setCdistance("");
            //  info.setCmobile("");

            // lat = location.getLatitude();
//            longi = location.getLongitude();
//
//            Global_Data.GLOvel_LATITUDE = String.valueOf(lat);
//            Global_Data.GLOvel_LONGITUDE = String.valueOf(longi);

//            prefManager.setLATITUDE(String.valueOf(lat));
//            prefManager.setLONGITUDE(String.valueOf(longi));

//            isInternetPresent = cd.isConnectingToInternet();
//            if (isInternetPresent) {
            try {
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
                        MapsActivity.this, new GeocoderHandler());
                Geocoder geo = new Geocoder(MapsActivity.this.getApplicationContext(), Locale.getDefault());
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
                        str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getAddressLine(0)) + " ");
                        if (!(str.indexOf(addresses.get(0).getLocality()) > 0)) {
                            str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getLocality()) + " ");
                        }

                        if (!(str.indexOf(addresses.get(0).getAdminArea()) > 0)) {
                            str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getAdminArea()) + " ");
                        }

                        if (!(str.indexOf(addresses.get(0).getCountryName()) > 0)) {
                            str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getCountryName()) + " ");
                        }

                        if (!(str.indexOf(addresses.get(0).getPostalCode()) > 0)) {
                            str.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getPostalCode()) + " ");
                        }

                        str.append("\n");


                        markerOptions.title(str.toString());


                        //Toast.makeText(getApplicationContext(), "Address:- " + addresses.get(0).getFeatureName() + addresses.get(0).getAdminArea() + addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // }


            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            // CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MapsActivity.this);
            //  mMap.setInfoWindowAdapter(customInfoWindow);
            currLocationMarker = mMap.addMarker(markerOptions);
            // Marker m = mMap.addMarker(markerOptions);
            // currLocationMarker.setTag(info);

            //Toast.makeText(this,"Location Changed",Toast.LENGTH_SHORT).show();

            Log.d("Location change event", "Location Change");

            if (m != null) {
                LatLngBounds.Builder buildern = new LatLngBounds.Builder();

                try {
                    buildern.include(m.getPosition());
                } catch (Exception ex) {

                }


                try {

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : Global_Data.mMarkers) {
                        builder.include(marker.getPosition());
                    }

                    LatLngBounds bounds = builder.build();
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                    mMap.moveCamera(cu);

                    // mMap.setPadding(10, 10, 10, 10);

//                    LatLngBounds bounds = buildern.build();
//
//                    int width = getResources().getDisplayMetrics().widthPixels;
//                    int height = getResources().getDisplayMetrics().heightPixels;
//                    int padding = (int) (width * 0.005); // offset from edges of the map 10% of
//                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                    mMap.animateCamera(cu);


                } catch (Exception ex) {

                }
            } else {
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng).zoom(12).build();

                mMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(cameraPosition));
            }


            //zoom to current position:


        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {

            // Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
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
            Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                //place marker at current position
                //mGoogleMap.clear();
                //  InfoWindowData info = new InfoWindowData();
                latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);

                // info.setCdistance("");
                //  info.setCmobile("");

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOvel_ADDRESS)) {
                    markerOptions.title(Global_Data.GLOvel_ADDRESS);
                } else {
                    markerOptions.title("Current Position");
                }

                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                //  CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MapsActivity.this);
                // mMap.setInfoWindowAdapter(customInfoWindow);
                currLocationMarker = mMap.addMarker(markerOptions);
                // Marker m = mMap.addMarker(markerOptions);
                // currLocationMarker.setTag(info);
            }

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(80000); //5 seconds
            mLocationRequest.setFastestInterval(80000); //3 seconds
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            //mLocationRequest.setSmallestDisplacement(0.1F); //1/10 meter

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

    public void getUserGeoData() {
        String domain = getResources().getString(R.string.service_domain_sub_dealer);

        Log.i("volley", "domain: " + domain);
        Log.i("volley", "email: " + prefManager.getUser_Email());


        try {

            StringRequest jsObjRequest = null;
            String service_url = "";


            service_url = domain + "sub_dealers/get_nearest_sub_dealers?lat="
                    + Global_Data.GLOvel_LATITUDE + "&lon="
                    + Global_Data.GLOvel_LONGITUDE;


            Log.i("user list url", "user list url " + service_url);

            jsObjRequest = new StringRequest(service_url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.i("volley", "response: " + response);
                    final_response = response;

                    new GetReportedUserData().execute(response);

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            dialog.dismiss();

                            finish();
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
            jsObjRequest.setShouldCache(false);
            requestQueue.getCache().clear();
            requestQueue.add(jsObjRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class GetReportedUserData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                    MapsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();


                            Toast toast = Toast.makeText(MapsActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            finish();
                        }
                    });

                } else {
                    response_result = "data";
                    JSONArray data = response.getJSONArray("sub_dealers");
                    Log.i("volley", "response data Length: " + data.length());
                    Log.d("volley", "users" + data.toString());


                    //
                    if (data.length() <= 0) {

                        MapsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                                //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(MapsActivity.this, "Record doesn't exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

//                                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
//                                startActivity(intent);
                                finish();
                            }
                        });
                    } else {

                        Allresult.clear();
                        locations = new ArrayList<>();
                        AllresultSubDealer.clear();
                        SubDealer_List.clear();

                        for (int i = 0; i < data.length(); i++) {

                            jsonObject = data.getJSONObject(i);

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject.getString("latitude")) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject.getString("longitude"))) {

                                SubDealerModel di = new SubDealerModel();

                                locations.add(new LatLng(Double.valueOf(jsonObject.getString("latitude")), Double.valueOf(jsonObject.getString("longitude"))));

                                address.add(jsonObject.getString("customer_address"));
                                name.add(jsonObject.getString("customer_name"));
                                distance.add(jsonObject.getString("distance"));
                                mobile.add(jsonObject.getString("mobile_no"));
                                code.add(jsonObject.getString("code"));
                                //status.add("Approved");


                                //di.name = jsonObject.getString("customer_name").trim();
                                di.proprietor_mobile1 = jsonObject.getString("mobile_no").trim();
                                //  di.proprietor_name1 = jsonObject.getString("customer_address").trim();
                                di.proprietor_email1 = jsonObject.getString("customer_address").trim();
                                di.address = jsonObject.getString("customer_address").trim();
                                //  di.proprietor_mobile2 = jsonObject.getString("mobile_no").trim();
                                // di.proprietor_name2 = jsonObject.getString("distance").trim();
                                // di.proprietor_email2 = jsonObject.getString("distance").trim();
                                di.shop_name = jsonObject.getString("customer_name").trim();
                                di.distance = jsonObject.getString("distance").trim();
                                di.code = jsonObject.getString("code").trim();

                                di.lati = jsonObject.getString("latitude").trim();
                                di.longi = jsonObject.getString("longitude").trim();

                                ArrayList<String> names = new ArrayList<>();
                                names.add(di.shop_name);
                                Allresult.add(di);
                                AllresultSubDealer.add(di);
                                SubDealer_List.add(jsonObject.getString("customer_name"));


                            }


                        }


                        MapsActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();

                                if (locations.size() > 0) {
                                    int a = 0;
                                    for (LatLng latLng : locations) {

                                        //   mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(MapsActivity.this,"Name : "+name.get(a),
//                                                "Address : "+address.get(a)));

//                                        Marker marker = mMap.addMarker(new MarkerOptions()
//                                                .position(latLng)
//                                                .title("Name : "+name.get(a))
//                                                .snippet("Address : "+address.get(a)));

                                        MarkerOptions markerOptions = new MarkerOptions();

                                        markerOptions.position(latLng)
                                                .title("Name : " + name.get(a))
                                                .snippet("Address : " + address.get(a))
                                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));


                                        // InfoWindowData info = new InfoWindowData();
                                        // info.setCdistance("Distance : " + distance.get(a));
                                        // info.setCmobile("Mobile No : " + mobile.get(a));
                                        // info.setCmobilenew(mobile.get(a));
                                        // info.setS_code(code.get(a));

                                        //CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MapsActivity.this);
                                        //  mMap.setInfoWindowAdapter(customInfoWindow);

                                        m = mMap.addMarker(markerOptions);
                                        m.setTag(code.get(a));
                                        Global_Data.mMarkers.add(m);


                                        //  m.showInfoWindow();

                                        //.icon(BitmapDescriptorFactory
                                        //   .fromResource(R.drawable.marker)));

                                        //  marker.showInfoWindow();

                                        // mMap.addMarker(new MarkerOptions().position(latLng).title("Name : "+name.get(a)+ " Address : "+address.get(a)));
                                        a++;
                                    }


                                    LatLngBounds.Builder buildern = new LatLngBounds.Builder();

                                    try {
                                        buildern.include(m.getPosition());
                                    } catch (Exception ex) {

                                    }


                                    try {

                                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                        for (Marker marker : Global_Data.mMarkers) {
                                            builder.include(marker.getPosition());
                                        }

                                        LatLngBounds bounds = builder.build();
                                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 100);
                                        mMap.moveCamera(cu);

//                                        LatLngBounds bounds = buildern.build();
//
//                                        int width = getResources().getDisplayMetrics().widthPixels;
//                                        int height = getResources().getDisplayMetrics().heightPixels;
//                                        int padding = (int) (width * 0.005); // offset from edges of the map 10% of
//                                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
//                                        mMap.animateCamera(cu);


                                    } catch (Exception ex) {

                                    }


                                    ca = new Sub_DealerMap_Adapter(Allresult, MapsActivity.this, MapsActivity.this);
                                    marker_rview.setAdapter(ca);
                                    ca.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MapsActivity.this, "Sub Dealer Not Found.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }


                            }
                        });


                    }

                    MapsActivity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            dialog.dismiss();
                        }
                    });
                    //	dialog.dismiss();

                    //finish();

                }


                // }

                // output.setText(data);
            } catch (JSONException e) {
                e.printStackTrace();

                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                MapsActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });

            }


            MapsActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            MapsActivity.this.runOnUiThread(new Runnable() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (dialog != null) {
            dialog.dismiss();
        }

    }

    @Override
    public void onBackPressed() {

        Intent i = new Intent(MapsActivity.this, Sales_Dash.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();

    }


    /**
     * Requesting GPS permission
     * This uses single permission model from dexter
     * Once the permission granted, opens the camera
     * On permanent denial opens settings dialog
     */
    public void requestPHONEPermission(final String mobile_number) {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile_number));
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}