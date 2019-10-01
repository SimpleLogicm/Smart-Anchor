package  com.anchor.activities;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.ConnectionDetector;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;


//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;

public class MyService extends Service implements LocationListener{
	private static final String TAG = "LocationActivity";

	LoginDataBaseAdapter loginDataBaseAdapter;
	ArrayList<String> results = new ArrayList<String>();
	String user_name = "";

	ArrayList<String> AT_results = new ArrayList<String>();
	int timeOfDay;

	DataBaseHelper dbvoc = new DataBaseHelper(this);;
	//SharedPreferences spf=getSharedPreferences("SimpleLogic",0);
//	SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(this);
	private static final long INTERVAL = 1000 * 10;
	private static final long FASTEST_INTERVAL = 10000 * 6;
	TextView btnFusedLocation,cancel_loc;
	LocationRequest mLocationRequest;
	GoogleApiClient mGoogleApiClient;
	Location mCurrentLocation;
	String mLastUpdateTime;
	private FirebaseAnalytics mFirebaseAnalytics;

	protected void createLocationRequest() {
		mLocationRequest = new LocationRequest();
		mLocationRequest.setInterval(INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}

	Geocoder geocoder;
	String line;
	//PreferencesHelper Prefs;
	SharedPreferences sp ;
	BufferedReader in = null;
	String lat_val,long_val;
	//    HttpPost http_post;
//    HttpResponse http_resp;
//    HttpClient http_client;
//    List<NameValuePair> http_nmvalpair;
//    HttpEntity http_entity;
	public LocationManager locationManager;
	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;
	private String provider;
	// flag for GPS status
	boolean canGetLocation = false;
	Boolean isInternetPresent = false;

	ConnectionDetector cd;
	Location location; // location
	public double latitude; // latitude
	public double longitude; // longitude
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 0 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 35000 ; // 1 second
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Query the database and show alarm if it applies
		//	Prefs = new PreferencesHelper(this);
		sp= this.getSharedPreferences("SimpleLogic", 0);

		try {
			if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
				user_name = sp.getString("USER_EMAIL", "");
			} else {
				user_name = Global_Data.GLOvel_USER_EMAIL;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}



		cd = new ConnectionDetector(this);

		latitude = 0.0;
		longitude = 0.0;
		// I don't want this service to stay in memory, so I stop it
		// immediately after doing what I wanted it to do.

		Calendar c = Calendar.getInstance();
		timeOfDay = c.get(Calendar.HOUR_OF_DAY);

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		Date date = new Date();
		System.out.println(dateFormat.format(date));

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();

		try {

			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// If GPS enabled, get latitude/longitude using GPS Services
			if (isGPSEnabled) {
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER,
						MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.d("GPS Enabled", "GPS Enabled");

				if (locationManager != null) {
					location = locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						//isLocationAvailable = true; // setting a flag that
						// location is available

						Global_Data.GLOvel_LATITUDE =  String.valueOf(latitude);
						Global_Data.GLOvel_LONGITUDE =  String.valueOf(longitude);

						//Global_Val.lat_val = Double.toString(c);
						//Global_Val.long_val = Double.toString(d);
						SharedPreferences spf=this.getSharedPreferences("SimpleLogic",0);
						SharedPreferences.Editor editor=spf.edit();
						//editor.putString("USER_EMAIL", Global_Data.GLOvel_USER_EMAIL);
						editor.putString("LATVAL", String.valueOf(latitude));
						editor.putString("LONGVAL", String.valueOf(longitude));

						Log.d("GPS LOCATION","GPS LOCATION"+ String.valueOf(latitude)+String.valueOf(longitude));


						editor.commit();

						if(timeOfDay >= 7 && timeOfDay <= 22){

							isInternetPresent = cd.isConnectingToInternet();

							if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(latitude))) {

								if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(longitude))) {
									//String address = addressn(latitude,longitude);

									new Handler(Looper.getMainLooper()).post(new Runnable() {
										public void run() {
											LocationAddress locationAddress = new LocationAddress();
											LocationAddress.getAddressFromLocation(latitude, longitude,
													getApplicationContext(), new GeocoderHandler());
										}
									});



									loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), Global_Data.address, dateFormat.format(date), "", "");
								} else {
									loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
								}
							} else {
								loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
							}


						}



//						LocationAddress locationAddress = new LocationAddress();
//						locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
//								getApplicationContext(), new GeocoderHandler());

					}
				}
			}

			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			try
			{

				PlayService_Location PlayServiceManager = new PlayService_Location(this);
				if(PlayServiceManager.checkPlayServices(this) && (String.valueOf(latitude).equalsIgnoreCase("0.0") || String.valueOf(latitude).equalsIgnoreCase("") || String.valueOf(longitude).equalsIgnoreCase("") || String.valueOf(longitude).equalsIgnoreCase("0.0")))
				{
					Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

					latitude = Double.valueOf(Global_Data.GLOvel_LATITUDE);
					longitude = Double.valueOf(Global_Data.GLOvel_LONGITUDE);

					if(timeOfDay >= 7 && timeOfDay <= 22){

						isInternetPresent = cd.isConnectingToInternet();

						if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(latitude))) {

							if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(longitude))) {

								new Handler(Looper.getMainLooper()).post(new Runnable() {
									public void run() {
										LocationAddress locationAddress = new LocationAddress();
										LocationAddress.getAddressFromLocation(latitude, longitude,
												getApplicationContext(), new GeocoderHandler());
									}
								});


								loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), Global_Data.address, dateFormat.format(date), "", "");
							} else {
								loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
							}
						} else {
							loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
						}
					}

				}
				else
				{
					if (isNetworkEnabled && !isGPSEnabled) {
						location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						//
						// location = locationManager.getLastKnownLocation(provider);

						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();

							Global_Data.GLOvel_LATITUDE = String.valueOf(latitude);
							Global_Data.GLOvel_LONGITUDE = String.valueOf(longitude);

							//Global_Val.lat_val = Double.toString(c);
							//Global_Val.long_val = Double.toString(d);
							SharedPreferences spf = this.getSharedPreferences("SimpleLogic", 0);
							SharedPreferences.Editor editor = spf.edit();
							//editor.putString("USER_EMAIL", Global_Data.GLOvel_USER_EMAIL);
							editor.putString("LATVAL", String.valueOf(latitude));
							editor.putString("LONGVAL", String.valueOf(longitude));

							Log.d("NETWORK LOCATION","NETWORK LOCATION"+ String.valueOf(latitude)+String.valueOf(longitude));

							editor.commit();
							if(timeOfDay >= 7 && timeOfDay <= 22){
								isInternetPresent = cd.isConnectingToInternet();

								if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(latitude))) {

									if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(longitude))) {
										//String address = addressn(latitude,longitude);

										new Handler(Looper.getMainLooper()).post(new Runnable() {
											public void run() {
												LocationAddress locationAddress = new LocationAddress();
												LocationAddress.getAddressFromLocation(latitude, longitude,
														getApplicationContext(), new GeocoderHandler());
											}
										});


										loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), Global_Data.address, dateFormat.format(date), "", "");
									} else {
										loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
									}
								} else {
									loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
								}
							}

//							LocationAddress locationAddress = new LocationAddress();
//							locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
//									getApplicationContext(), new GeocoderHandler());
						}
					}
				}
			}catch(Exception es){es.printStackTrace();}






			if (String.valueOf(latitude).equalsIgnoreCase("0.0") || String.valueOf(latitude).equalsIgnoreCase("") || String.valueOf(longitude).equalsIgnoreCase("") || String.valueOf(longitude).equalsIgnoreCase("0.0")) {
				location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				//
				// location = locationManager.getLastKnownLocation(provider);

				if (location != null) {
					latitude = location.getLatitude();
					longitude = location.getLongitude();

					Global_Data.GLOvel_LATITUDE = String.valueOf(latitude);
					Global_Data.GLOvel_LONGITUDE = String.valueOf(longitude);

					//Global_Val.lat_val = Double.toString(c);
					//Global_Val.long_val = Double.toString(d);
					SharedPreferences spf = this.getSharedPreferences("SimpleLogic", 0);
					SharedPreferences.Editor editor = spf.edit();
					//editor.putString("USER_EMAIL", Global_Data.GLOvel_USER_EMAIL);
					editor.putString("LATVAL", String.valueOf(latitude));
					editor.putString("LONGVAL", String.valueOf(longitude));

					Log.d("NETWORK LOCATION","NETWORK LOCATION"+ String.valueOf(latitude)+String.valueOf(longitude));


					editor.commit();

					if(timeOfDay >= 7 && timeOfDay <= 22){
						isInternetPresent = cd.isConnectingToInternet();

						if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(latitude))) {

							if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(longitude))) {
								//String address = addressn(latitude,longitude);

								new Handler(Looper.getMainLooper()).post(new Runnable() {
									public void run() {
										LocationAddress locationAddress = new LocationAddress();
										LocationAddress.getAddressFromLocation(latitude, longitude,
												getApplicationContext(), new GeocoderHandler());
									}
								});



								loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), Global_Data.address, dateFormat.format(date), "", "");
							} else {
								loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
							}
						} else {
							loginDataBaseAdapter.insert_geo_data(String.valueOf(latitude), String.valueOf(longitude), "", dateFormat.format(date), "", "");
						}
					}
//
//					LocationAddress locationAddress = new LocationAddress();
//					locationAddress.getAddressFromLocation(location.getLatitude(), location.getLongitude(),
//							getApplicationContext(), new GeocoderHandler());
				}
			}




			//Toast.makeText(this,"vals:++++++++++++++++++++++++++++++++>"+latitude+" "+longitude, Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			e.printStackTrace();
			// dialogGPS(this.getContext()); // lets the user know there is a problem with the gps
		}

//        try {
//            locationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
//
//            isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            if (!isGPSEnabled && !isNetworkEnabled) {
//
//            } else {
//                this.canGetLocation = true;
//                if (isNetworkEnabled) {
//                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
//                    if (locationManager != null) {
//                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                        if (location != null) {
//                            latitude = location.getLatitude();
//                            longitude = location.getLongitude();
//                            Global_Val.lat_val = Double.toString(latitude);
//                            Global_Val.long_val = Double.toString(longitude);
//
//                            Geocoder geocoder;
//                            List<Address> addresses;
//
//                            geocoder = new Geocoder(this, Locale.getDefault());
//                            addresses = geocoder.getFromLocation(latitude, longitude, 1);
//                            Address address = addresses.get(0);
//                            Global_Val.GLOvel_STATE_n =  addresses.get(0).getAdminArea();
//                            Global_Val.GLOvel_CITY_n = address.getLocality();
//                            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//                            Global_Val.address = addresses.get(0).getAddressLine(1)+" "+addresses.get(0).getAddressLine(2); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                        }
//                    }
//                }
//            }
//        }catch (Exception e){
//
//        }

		Log.i("sendlocation","Every 5 minutes it will appear in Log Console"+latitude+" "+longitude);
		//Toast.makeText(getApplicationContext(), "Location successfully.", Toast.LENGTH_LONG).show();
		//Toast.makeText(getApplicationContext(),"ADRS:"+ Prefs.GetPreferences("ADDRESS"), Toast.LENGTH_LONG).show();
		new Thread(new Runnable() {
			public void run() {
				try
				{
					// Obtain the FirebaseAnalytics instance.
					mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseContext());
					Bundle bundle = new Bundle();
					bundle.putString(FirebaseAnalytics.Param.ITEM_ID,  sp.getString("USER_EMAIL", ""));
					bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,sp.getString("USER_NAMEs", ""));

					mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

					/* Firebase Code End */

					String domain = getResources().getString(R.string.service_domain);

//            Global_Val global_Val = new Global_Val();
//            if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//                domain = getResources().getString(R.string.service_domain);
//            }
//            else
//            {
//                domain = URL.toString();
					SharedPreferences spf= getSharedPreferences("SimpleLogic",0);
					Global_Data.device_id = spf.getString("devid", "");
//            }
					Log.i("volley", "domain: " + domain);
					Log.i("volley", "Device_id: " + Global_Data.device_id);

					isInternetPresent = cd.isConnectingToInternet();

					List<Local_Data> background = dbvoc.getBACKGROUND_SERVICE_CHECK_DATA();

					if (isInternetPresent && timeOfDay >= 7 && timeOfDay <= 22 && background.size() <= 0) {
						//Reading all
						List<Local_Data> contacts = dbvoc.getemaIL();
						for (Local_Data cn : contacts) {
							Global_Data.GLOvel_USER_EMAIL = cn.getemail();

						}




						JSONArray PICTURE = new JSONArray();
						//JSONObject product_value = new JSONObject();
						JSONObject product_value_n = new JSONObject();
						JSONArray product_imei = new JSONArray();
						JSONArray at_array = new JSONArray();

						List<Local_Data> geo = dbvoc.getGEo_DATA();
						//List<Local_Data> attendance = dbvoc.getAllAttendance_Data();
						if (geo.size() > 0)
						{
							for (final Local_Data g : geo) {

								JSONObject picture = new JSONObject();
								picture.put("latitude",g.getlatitude());
								picture.put("longitude", g.getlongitude());

								try {
									if (isInternetPresent && !(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(g.getAddress()))) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(g.getlatitude())) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(g.getlongitude()))) {
										//String address = addressn(Double.valueOf(g.getlatitude()),Double.valueOf(g.getlongitude()));

										try
										{
											new Handler(Looper.getMainLooper()).post(new Runnable() {
												public void run() {
													LocationAddress locationAddress = new LocationAddress();
													LocationAddress.getAddressFromLocation(Double.valueOf(g.getlatitude()), Double.valueOf(g.getlongitude()),
															getApplicationContext(), new GeocoderHandler());
												}
											});



											picture.put("address", Global_Data.address);
										}catch(Exception ex){
											ex.printStackTrace();
											addressn(Double.valueOf(g.getlatitude()),Double.valueOf(g.getlongitude()));
											picture.put("address", Global_Data.address);
										}
									} else {
										picture.put("address", g.getAddress());
									}
								} catch (Exception ex) {
									ex.printStackTrace();
								}



								picture.put("location_date", g.getdatetime1());
								results.add(g.getdatetime1());
								PICTURE.put(picture);

								loginDataBaseAdapter.insert_ACKGROUND_SERVICE_CHECK(g.getlatitude(), g.getlongitude(), g.getdatetime1());

							}

//							if (attendance.size() > 0) {
//								for (Local_Data a : attendance) {
//
//									JSONObject at_json = new JSONObject();
//									//at_json.put("user_id", a.getUser_id());
//									at_json.put("punched_on", a.getPunched_on());
//									at_json.put("punched_at_latitude", a.getPunched_at_latitude());
//									at_json.put("punched_at_longitude", a.getPunched_at_longitude());
//									at_json.put("punched_button", a.getPunched_button());
//
//									try {
//										if (isInternetPresent && !(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(a.getPunched_at_address()))) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(a.getPunched_at_latitude())) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(a.getPunched_at_longitude()))) {
//											try
//											{
//												LocationAddress locationAddress = new LocationAddress();
//												LocationAddress.getAddressFromLocation(Double.valueOf(a.getlatitude()), Double.valueOf(a.getlongitude()),
//														getApplicationContext(), new GeocoderHandler());
//												at_json.put("punched_at_address", Global_Data.address);
//											}catch(Exception ex){
//												ex.printStackTrace();
//												addressn(Double.valueOf(a.getlatitude()),Double.valueOf(a.getlongitude()));
//												at_json.put("punched_at_address", Global_Data.address);
//											}
//										} else {
//											at_json.put("punched_at_address", a.getPunched_at_address());
//										}
//									} catch (Exception ex) {
//										ex.printStackTrace();
//									}
//
//									AT_results.add(a.getPunched_on());
//									at_array.put(at_json);
//
//									//loginDataBaseAdapter.insert_ACKGROUND_SERVICE_CHECK(g.getlatitude(),g.getlongitude(),g.getdatetime1());
//
//								}
//							}
						}
						else
						{

							DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
							Date date = new Date();
							System.out.println(dateFormat.format(date));

							JSONObject picture = new JSONObject();
							picture.put("latitude",Global_Data.GLOvel_LATITUDE);
							picture.put("longitude", Global_Data.GLOvel_LONGITUDE);

							if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(Global_Data.GLOvel_LATITUDE)) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(Global_Data.GLOvel_LONGITUDE))) {
								try
								{
									new Handler(Looper.getMainLooper()).post(new Runnable() {
										public void run() {
											LocationAddress locationAddress = new LocationAddress();
											LocationAddress.getAddressFromLocation(Double.valueOf(Global_Data.GLOvel_LATITUDE), Double.valueOf(Global_Data.GLOvel_LONGITUDE),
													getApplicationContext(), new GeocoderHandler());
										}
									});

									picture.put("address", Global_Data.address);
								}catch(Exception ex){
									ex.printStackTrace();
									addressn(Double.valueOf(Global_Data.GLOvel_LATITUDE),Double.valueOf(Global_Data.GLOvel_LONGITUDE));
									picture.put("address", Global_Data.address);
								}
							} else {
								picture.put("address", "");
							}

							picture.put("location_date", dateFormat.format(date));
							PICTURE.put(picture);

//							if (attendance.size() > 0) {
//								for (Local_Data a : attendance) {
//
//									JSONObject at_json = new JSONObject();
//									//at_json.put("user_id", a.getUser_id());
//									at_json.put("punched_on", a.getPunched_on());
//									at_json.put("punched_at_latitude", a.getPunched_at_latitude());
//									at_json.put("punched_at_longitude", a.getPunched_at_longitude());
//									at_json.put("punched_button", a.getPunched_button());
//
//									try {
//										if (isInternetPresent && !(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(a.getPunched_at_address()))) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(a.getPunched_at_latitude())) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(a.getPunched_at_longitude()))) {
//											try
//											{
//												LocationAddress locationAddress = new LocationAddress();
//												LocationAddress.getAddressFromLocation(Double.valueOf(a.getlatitude()), Double.valueOf(a.getlongitude()),
//														getApplicationContext(), new GeocoderHandler());
//												at_json.put("punched_at_address", Global_Data.address);
//											}catch(Exception ex){
//												ex.printStackTrace();
//												addressn(Double.valueOf(a.getlatitude()),Double.valueOf(a.getlongitude()));
//												at_json.put("punched_at_address", Global_Data.address);
//											}
//										} else {
//											at_json.put("punched_at_address", a.getPunched_at_address());
//										}
//									} catch (Exception ex) {
//										ex.printStackTrace();
//									}
//
//									AT_results.add(a.getPunched_on());
//									at_array.put(at_json);
//
//									//loginDataBaseAdapter.insert_ACKGROUND_SERVICE_CHECK(g.getlatitude(),g.getlongitude(),g.getdatetime1());
//
//								}
//							}

							loginDataBaseAdapter.insert_ACKGROUND_SERVICE_CHECK(Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, dateFormat.format(date));

						}



						product_value_n.put("user_location_histories", PICTURE);
						//product_value_n.put("attendances", at_array);
						product_value_n.put("imei_no", Global_Data.device_id);
						product_value_n.put("email", user_name);
						Log.d("user_location_histories",product_value_n.toString());


//					Log.i("volley", "Service url: " + domain+"update_user_with_current_location?lat="+sp.getString("LATVAL",null)+"&lon="+sp.getString("LONGVAL",null)+"&device_id="+Global_Data.device_id+"&address="+URLEncoder.encode(Global_Data.address, "UTF-8")+"&email="+Global_Data.GLOvel_USER_EMAIL);

						Log.i("volley", "Service url: " + domain+"update_user_with_current_location");
//
//					JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,domain+"update_user_with_current_location?lat="+sp.getString("LATVAL",null)+"&lon="+sp.getString("LONGVAL",null)+"&device_id="+Global_Data.device_id+"&address="+URLEncoder.encode(Global_Data.address, "UTF-8")+"&email="+Global_Data.GLOvel_USER_EMAIL, product_value_n, new Response.Listener<JSONObject>() {
//
//						@Override
//						public void onResponse(JSONObject response) {

						JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,domain+"update_user_with_current_location", product_value_n, new Response.Listener<JSONObject>() {

							@Override
							public void onResponse(JSONObject response) {

								Log.i("volley", "response: " + response.toString());
								//  Log.i("volley", "response reg Length: " + response.length());

								try{
									//   for (int a = 0; a < response.length(); a++) {

//                        JSONObject person = (JSONObject) response.getJSONArray(response);
//
									//   String name = response.getString("result44");

									String response_result = "";
									if(response.has("result"))
									{
										response_result = response.getString("result");
										// dbvoc.getDeleteTable("geo_data");
									}
									else
									{
										response_result = "data";
									}


									if(response_result.equalsIgnoreCase("Device not found")) {

										//Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
										Log.d("LOC RESULT","LOC RESULT"+response_result);

									}
									else {

										if(!results.isEmpty())
										{
											for(int i=0; i<results.size(); i++)
											{
												dbvoc.getDeleteLocationData(results.get(i));
											}
											results.clear();
										}

										if (!AT_results.isEmpty()) {
											for (int i = 0; i < AT_results.size(); i++) {
												dbvoc.getDeleteattendance_da(AT_results.get(i));
											}
											AT_results.clear();
										}

										dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();

										//dbvoc.getDeleteTable("geo_data");
										Log.d("LOC RESULT","LOC RESULT"+response_result);
										LocationAddress locationAddress = new LocationAddress();

//
									}
									// }

									// output.setText(data);
								}catch(JSONException e) {
									dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();
									e.printStackTrace();

									//dialog.dismiss();
									//finish();
								}


								// dialog.dismiss();
							}
						},  new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								//Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

								dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();

								if (error instanceof TimeoutError || error instanceof NoConnectionError) {

									Log.d("BACK Error","Network Error");
								} else if (error instanceof AuthFailureError) {

									Log.d("BACK Error","Server AuthFailureError");
								} else if (error instanceof ServerError) {
									Log.d("BACK Error","Server   Error");
								} else if (error instanceof NetworkError) {
									Log.d("BACK Error","Network Error");

								} else if (error instanceof ParseError) {
									Log.d("BACK Error","ParseError   Error");
								}
								else
								{
									Log.d("BACK Error",error.getMessage());
								}
								//dialog.dismiss();
								// finish();
							}
						});

						RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
						// queue.add(jsObjRequest);
						jsObjRequest.setShouldCache(false);
						int socketTimeout = 400000;//30 seconds - change to what you want
						RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
						jsObjRequest.setRetryPolicy(policy);
						requestQueue.add(jsObjRequest);
					}



				} catch (Exception e) {
					e.printStackTrace();
					dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();
					//dialog.dismiss();
				}
			}
		}).start();

		Log.d("service start","service start");
		//Toast.makeText(this, " SaleService Created ", Toast.LENGTH_LONG).show();
		stopSelf();

		return START_NOT_STICKY;
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
				Global_Data.address = locationAddress;
				Log.d("GLOBEL ADDRESS G", "V" + locationAddress);

			} else {
				Global_Data.address = "";
				Log.d("GLOBEL ADDRESS G", "address not found.");
			}


		}
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

//    @Override
//    public void onLocationChanged(Location arg0) {
//        // TODO Auto-generated method stub
//
//    }

	@Override
	public void onDestroy() {
		// I want to restart this service again in one hour
		AlarmManager alarm = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarm.set(
				alarm.RTC_WAKEUP,
				System.currentTimeMillis() + (1000 * 60 * 5),
				PendingIntent.getService(this, 0, new Intent(this, MyService.class), 0)
		);
	}

	@Override
	public void onLocationChanged(Location location) {

		//int lat = (int) (location.getLatitude());
		//int lng = (int) (location.getLongitude());

		Global_Data.GLOvel_LATITUDE =  String.valueOf(location.getLatitude());
		Global_Data.GLOvel_LONGITUDE =  String.valueOf(location.getLongitude());

		Log.d("Location change","CHANGE "+Global_Data.GLOvel_LATITUDE+" "+Global_Data.GLOvel_LONGITUDE);

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

//    @Override
//    public void onConnected(Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(ConnectionResult connectionResult) {
//
//    }

	public String addressn(Double lat, Double longi) {
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(this, Locale.getDefault());
		StringBuilder sb = new StringBuilder();
		sb.append("");

		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getAddressLine(0))+" ");
			if(!(sb.indexOf(addresses.get(0).getLocality()) > 0))
			{
				sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getLocality())+" ");
			}

			if(!(sb.indexOf(addresses.get(0).getAdminArea()) > 0))
			{
				sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getAdminArea())+" ");
			}

			if(!(sb.indexOf(addresses.get(0).getCountryName()) > 0))
			{
				sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getCountryName())+" ");
			}

			if(!(sb.indexOf(addresses.get(0).getPostalCode()) > 0))
			{
				sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addresses.get(0).getPostalCode())+" ");
			}
			//String knownName = addresses.get(0).getFeatureName();

			Global_Data.address = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		startForeground(1,new Notification());
	}


}