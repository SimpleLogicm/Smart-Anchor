package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.animation.ActivitySwitcher;
import com.anchor.helper.MyPeriodicwork;
import com.anchor.model.Promotional_Model;
import com.anchor.service.LocationServices;
import com.anchor.service.StartLocationAlert;
import com.anchor.services.TestJobService;
import com.anchor.slidingmenu.adapter.NavDrawerListAdapter;
import com.anchor.slidingmenu.model.NavDrawerItem;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {
    private RequestQueue requestQueue;

    String device_id;
    String response_result = "";
    static String final_response = "";
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    LoginDataBaseAdapter loginDataBaseAdapter;
    private Calendar calendarn;
    private float t_total = 0;
    private float achived_total = 0;
    private int year, month, day;
    ProgressDialog dialog;
    private ActionBarDrawerToggle mDrawerToggle;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    // nav drawer title
    private CharSequence mDrawerTitle;
    // used to store app title
    private CharSequence mTitle;
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    TextView screenname;
    boolean firstLaunch;
    View v;
    TextView screen_title;
    TextView todaysTarget;
    int fragmentPoistion;
    PlayService_Location PlayServiceManager;
    private FirebaseAnalytics mFirebaseAnalytics;


    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        if (i.hasExtra("fromCapture")) {

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }


        PlayServiceManager = new PlayService_Location(MainActivity.this);

//		if(PlayServiceManager.checkPlayServices(MainActivity.this))
//		{
//
//		}


        setContentView(R.layout.activity_menu);
        firstLaunch = false;
        screenname = findViewById(R.id.screenname);
        cd = new ConnectionDetector(getApplicationContext());

        Global_Data.GLOVEL_LONG_DESC = "";
        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
        Global_Data.Target_From_Month = "";
        Global_Data.Target_To_Month = "";
        Global_Data.Target_Product_Category = "";
        Global_Data.target_quarter = "";
        Global_Data.Target_Year = "";
        Global_Data.target_amount = "";
        Global_Data.target_grpby = "";
        Global_Data.Search_business_unit_name = "";
        Global_Data.Business_unit_code_array = "";
        Global_Data.Search_Category_name = "";
        Global_Data.Search_BusinessCategory_name = "";
        Global_Data.Search_brand_name = "";

        loginDataBaseAdapter = new LoginDataBaseAdapter(MainActivity.this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        //scheduleNotify();

        Global_Data.context = MainActivity.this;
        try {
            PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                    MyPeriodicwork.class, 15, TimeUnit.MINUTES
            ).addTag("otpValidator").build();
            WorkManager.getInstance(this).enqueueUniquePeriodicWork("Work",
                    ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);

        }catch(Exception ex) {
            ex.printStackTrace();
        }


        Calendar c1 = Calendar.getInstance();
        //System.out.println("Current time => " + c.getTime());

//		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
//		String formattedDate = df.format(c1.getTime());
//		// Obtain the FirebaseAnalytics instance.
//		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//		Bundle bundle = new Bundle();
//		bundle.putString(FirebaseAnalytics.Param.ITEM_ID, Global_Data.GLOvel_USER_EMAIL);
//		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, Global_Data.USER_FIRST_NAME+" " +Global_Data.USER_LAST_NAME);
//		bundle.putString(FirebaseAnalytics.Param.START_DATE, formattedDate);
//		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        /* Firebase Code End */


        if (Global_Data.LOCATION_SERVICE_HIT.equalsIgnoreCase("TRUE")) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // startForegroundService(new Intent(this, MyService.class));
                // startService(new Intent(this, MyService.class));
//                ComponentName jobService =
//                        new ComponentName(getPackageName(), TestJobService.class.getName());
                //  startService(new Intent(this, MyService.class));

                JobScheduler jobScheduler =
                        (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                jobScheduler.schedule(new JobInfo.Builder(1,
                        new ComponentName(this, TestJobService.class))
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .build());

            } else {
                startService(new Intent(this, MyService.class));
            }
            // startService(new Intent(this, MyService.class));
            Global_Data.LOCATION_SERVICE_HIT = "";
        }

        Global_Data.PREVIOUS_ORDER_BACK_FLAG = "";

        /* background service check delete flag table delete */
        //   dbvoc.getDeleteBACKGROUND_SERVICE_CHECK();

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            //TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = MainActivity.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//		}

//            if (sp.getFloat("Target", 0) == 0.0) {
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    getTargetDatamain();
                }
//            } else {
//                try {
//                    int target = Math.round(sp.getFloat("Target", 0));
//                    int achieved = Math.round(sp.getFloat("Achived", 0));
//                    Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
//
//                    if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
//                        int age = Math.round(age_float);
//
//                        todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
//                    } else {
//                        int age = Math.round(age_float);
//
//                        todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
//                    }
//
//                    //	todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }

            Log.d("pre value", "Pre value" + sp.getFloat("Target", 0));

            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            // mTitleTextView.setText("My Own Title");

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        mTitle = mDrawerTitle = getTitle();
        v = getActionBar().getCustomView();
        screen_title = v.findViewById(R.id.screenname);
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
//		navMenuIcons = getResources()
//				.obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0]));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1]));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2]));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3]));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[9]));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[10]));
        // navDrawerItems.add(new NavDrawerItem(navMenuTitles[10]));
//
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7]));
//
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8]));
//
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9]));
//
//		navDrawerItems.add(new NavDrawerItem(navMenuTitles[10]));

        //navDrawerItems.add(new NavDrawerItem(navMenuTitles[4]));
		
		/*navDrawerItems.add(new NavDrawerItem(navMenuTitles[5]));
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6]));*/

        // What's hot, We  will add a counter here

        // Recycle the typed array
        //navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        if (Global_Data.SYNC_SERVICE_FLAG.equalsIgnoreCase("TRUE")) {
            Global_Data.SYNC_SERVICE_FLAG = "";
            // getServices.sendRequest(MainActivity.this);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons

                screen_title.setText(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons

                screen_title.setText("Smart Anchor");
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    public void displayView(int position) {
        // update the main content by replacing fragments

        switch (position) {
            case 0:

                fragment = new Home();
                fragmentPoistion = 0;
                break;

            case 1:

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
                    String device_id = sp.getString("devid", "");

                    try {
                        AppLocationManager appLocationManager = new AppLocationManager(MainActivity.this);
                        Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                        Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        PlayService_Location PlayServiceManager = new PlayService_Location(MainActivity.this);

                        if (PlayServiceManager.checkPlayServices(MainActivity.this)) {
                            Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                        } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    // device_id = telephonyManager.getDeviceId();
                    Log.d("device_id ", "device_id" + device_id);
                    Log.d("Address", "Address" + Global_Data.address);
                    Log.d("Latitude", "Latitude" + Global_Data.GLOvel_LATITUDE);
                    Log.d("Longitude", "Longitude" + Global_Data.GLOvel_LONGITUDE);

                    try {
                        AppLocationManager appLocationManager = new AppLocationManager(MainActivity.this);
                        Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                        Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        PlayService_Location PlayServiceManager = new PlayService_Location(MainActivity.this);

                        if (PlayServiceManager.checkPlayServices(MainActivity.this)) {
                            Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                        }

                        if (Global_Data.GLOvel_LATITUDE.equalsIgnoreCase("") || Global_Data.GLOvel_LATITUDE == null || Global_Data.GLOvel_LONGITUDE.equalsIgnoreCase("") || Global_Data.GLOvel_LONGITUDE == null || Global_Data.GLOvel_LATITUDE.equalsIgnoreCase("null") || Global_Data.GLOvel_LONGITUDE.equalsIgnoreCase("null")) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Sorry we can not find your location, Please try again. ", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else {
                            //Toast.makeText(getApplicationContext(), "not blank ", Toast.LENGTH_LONG).show();

                            //getServices.View_NearestCustomer(MainActivity.this,Global_Data.GLOvel_ADDRESS,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE);
                            Intent a = new Intent(MainActivity.this, Nearest_Customer.class);
                            startActivity(a);
                            finish();
                        }
                    } catch (Exception ex) {
                        Toast toast = Toast.makeText(getApplicationContext(), "We can not find yoor location Please try again or check your net connection or login again.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startForegroundService(new Intent(this, MyService.class));
                        } else {
                            startService(new Intent(this, MyService.class));
                        }
                        ex.printStackTrace();
                    }
                } else {
                    //Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                fragment = new Home();
                break;

            case 2:

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    Intent map = new Intent(MainActivity.this, Electrician_Map.class);
                    startActivity(map);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                fragment = new Home();
                fragmentPoistion = 2;
                break;

            case 3:
                Intent map = new Intent(MainActivity.this, BasicMapDemoActivity.class);
                startActivity(map);
                fragment = new Home();
                fragmentPoistion = 3;
                break;
            case 4:

                List<Local_Data> contcustomer = dbvoc.getOrderAll("Secondary Sales / Retail Sales");

                if (contcustomer.size() <= 0) {
                    //Toast.makeText(getApplicationContext(), "NO order found", Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getApplicationContext(), "NO order found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent a = new Intent(MainActivity.this, Previous_Order.class);
                    startActivity(a);
                    finish();
                }

                fragment = new Home();

                break;

            case 5:

                List<Local_Data> contcust = dbvoc.getOrderAllReturn("Secondary Sales / Retail Sales");

                if (contcust.size() <= 0) {
                    //Toast.makeText(getApplicationContext(), "NO Returnorder found", Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getApplicationContext(), "NO Return order found", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    Intent b = new Intent(MainActivity.this, Previous_returnOrder.class);
                    startActivity(b);
                    finish();
                }

                fragment = new Home();

                break;

            case 6:
                Intent conaa = new Intent(MainActivity.this, Sound_Setting.class);
                startActivity(conaa);
                //finish();
                fragment = new Home();
                break;
            case 7:
                Intent con = new Intent(MainActivity.this, Contact_Us.class);
                startActivity(con);
                //finish();
                fragment = new Home();
                break;
            case 8:
                Intent cona = new Intent(MainActivity.this, About_Metal.class);
                startActivity(cona);
                //finish();
                fragment = new Home();
                break;
            case 9:
                Intent ChangePass = new Intent(MainActivity.this, ChangePassword.class);
                startActivity(ChangePass);
                //finish();
                fragment = new Home();
                break;
            case 10:
                onBackPressed();
                fragment = new Home();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;


//		if (title.equals("Home") || title.equals("Sync Now") || title.equals("View Nearest Customer") || title.equals("View Institutional Sales Status")) {
        mTitle = "Dashboard";
        //screenname.setText(mTitle);
        screen_title.setText(mTitle);
        //}
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

//	public void newOrder(View v)
//	{
//		Intent i=new Intent(getApplicationContext(), NewOrderFragment.class);
//		SharedPreferences  sp=this.getSharedPreferences("SimpleLogic", 0);
//
//		i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
//		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(i);
//		//Log.e("TEST", "NEW OREDER CLICKED");
//		
//		//NewOrderFragment newFragment = new NewOrderFragment();
//		//FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//		// Replace whatever is in the fragment_container view with this fragment,
//		// and add the transaction to the back stack
//		//transaction.replace(R.id.fragment_container, newFragment);
//		//transaction.addToBackStack(null);
//
//		// Commit the transaction
//		//transaction.commit();
//	}

    @Override
    protected void onResume() {
        // animateIn this activity

        List<Promotional_Model> contacts = dbvoc.getPromotional_Activity();
        if (contacts.size() > 0) {
            local_notification();

            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
            alertDialog.setTitle("Confirmation");
            alertDialog.setMessage("You are in Promotional Activity, Do you want to complete it?");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {


                    Intent i = new Intent(getApplicationContext(), Promotion_Activity.class);
                    startActivity(i);
                    finish();
                }
            });

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.setCancelable(false);
            alertDialog.show();
        }


        if (firstLaunch) {
            // Your onResume Code Here
            //ViewGroup vg = (ViewGroup) findViewById (R.id.drawer_layout);
            //vg.invalidate();
            //getWindow().getDecorView().findViewById(android.R.id.content).invalidate();

            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            try {
                ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
                screen_title.setText("Order Booking");
            } catch (Exception e) {
                // TODO: handle exception
            }

            SharedPreferences sp = MainActivity.this.getSharedPreferences("SimpleLogic", 0);

//		        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//		        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//					todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//				}


            try {
                int target = Math.round(sp.getFloat("Target", 0));
                int achieved = Math.round(sp.getFloat("Achived", 0));
                Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
                if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
                } else {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//		        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

        } else {
            firstLaunch = true;
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        //Fragment fragment = new Home();

        try
        {
            boolean isUpstatuss = ((Home) fragment).isUpStatus();
            if (isUpstatuss == true) {
                ((Home) fragment).slideDownnew();
            } else {
                if (fragmentPoistion == 0 || fragmentPoistion == 3) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
                    alertDialog.setTitle("Confirmation");
                    alertDialog.setMessage(" Are you sure you want to logout?");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                            dbvoc.getDeleteTable("user_email");

                            loginDataBaseAdapter.insert_user_email(Global_Data.GLOvel_USER_EMAIL, "Logout");

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            startActivity(i);
                            stopService(new Intent(MainActivity.this, LocationServices.class));
                            finishAffinity();
                            finish();
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.cancel();
                        }
                    });

                    alertDialog.setCancelable(false);
                    alertDialog.show();
                }
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }




    }

    private void scheduleNotify() {
        Intent notificationIntent = new Intent(this, SendLocation.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, 1000 * 60 * 5, pendingIntent);
        SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        String device_id = sp.getString("devid", "");

        Global_Data.device_id = device_id;
    }

    public void getTargetDatamain() {
        t_total = 0;
        achived_total = 0;
        SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        //String device_id = sp.getString("devid", "");

        calendarn = Calendar.getInstance();
        year = calendarn.get(Calendar.YEAR);

        String user_email = "";

        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            String domain = getResources().getString(R.string.service_domain);

            Log.i("volley", "domain: " + domain);
            Log.i("volley", "email: " + user_email);
            Log.i("target url", "target url " + domain + "targets?imei_no=" + "" + "&email=" + user_email);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain + "targets?imei_no=" + "" + "&email=" + user_email, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());


                    try {

                        String response_result = "";
                        if (response.has("result")) {
                            response_result = response.getString("result");
                        } else {
                            response_result = "data";
                        }

                        if (response.has("rank")) {
                            SharedPreferences spf = MainActivity.this.getSharedPreferences("SimpleLogic", 0);
                            SharedPreferences.Editor editor = spf.edit();
                            editor.putString("rank", response.getString("rank"));

                            editor.commit();
                        }

                        if (response_result.equalsIgnoreCase("User doesn't exist")) {

                            //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (response_result.equalsIgnoreCase("User not registered")) {

                            //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (response_result.equalsIgnoreCase("Device not registered")) {

                            //Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else {

                            dbvoc.getDeleteTable("targets");

                            JSONArray targets = response.getJSONArray("targets");


                            Log.i("volley", "response reg targets Length: " + targets.length());

                            Log.d("States", "targets" + targets.toString());

                            if (targets.length() > 0) {
                                for (int i = 0; i < targets.length(); i++) {

                                    JSONObject jsonObject = targets.getJSONObject(i);

//	                                loginDataBaseAdapter.insertTargets(jsonObject.getString("code"),"", jsonObject.getString("user_id"),
//	                                		 jsonObject.getString("year"), jsonObject.getString("month"), jsonObject.getString("target"),
//	                                		 jsonObject.getString("achieved"), jsonObject.getString("created_at"), jsonObject.getString("update_at"));
                                    loginDataBaseAdapter.insertTargets("", "", "",
                                            jsonObject.getString("year"), jsonObject.getString("month"), jsonObject.getString("target"),
                                            jsonObject.getString("achieved"), "", "");

                                    if (jsonObject.getString("year").equalsIgnoreCase(String.valueOf(year))) {
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("target"))) {
                                            if (!jsonObject.getString("target").equalsIgnoreCase("null") && !jsonObject.getString("target").equalsIgnoreCase(null) & !jsonObject.getString("target").equalsIgnoreCase("") & !jsonObject.getString("target").equalsIgnoreCase(" ")) {
                                                t_total += Float.valueOf(jsonObject.getString("target"));
                                            } else {
                                                t_total += Float.valueOf("0.0");
                                            }

                                        }

                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("achieved"))) {


                                            if (!jsonObject.getString("achieved").equalsIgnoreCase("null") && !jsonObject.getString("achieved").equalsIgnoreCase(null) & !jsonObject.getString("achieved").equalsIgnoreCase("") & !jsonObject.getString("achieved").equalsIgnoreCase(" ")) {
                                                achived_total += Float.valueOf(jsonObject.getString("achieved"));
                                            } else {
                                                achived_total += Float.valueOf("0.0");
                                            }
                                        }
                                    }

                                }


                                SharedPreferences spf = MainActivity.this.getSharedPreferences("SimpleLogic", 0);
                                SharedPreferences.Editor editor = spf.edit();
                                //editor.putString("UserID", "admin");
                                //editor.putString("pwd", "test");
                                editor.putFloat("Target", t_total);
                                editor.putFloat("Achived", achived_total);
                                editor.commit();

                                try {
                                    int target = Math.round(t_total);
                                    int achieved = Math.round(achived_total);
                                    Float age_float = (achived_total / t_total) * 100;

                                    //int age = (int) Math.round(age_float);

                                    //	todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");

                                    if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                                        int age = Math.round(age_float);

                                        todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
                                    } else {
                                        int age = Math.round(age_float);

                                        todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {

                                try {
                                    SharedPreferences spf = MainActivity.this.getSharedPreferences("SimpleLogic", 0);
                                    SharedPreferences.Editor editor = spf.edit();
                                    //editor.putString("UserID", "admin");
                                    //editor.putString("pwd", "test");
                                    editor.putFloat("Target", 0);
                                    editor.putFloat("Achived", 0);
                                    //editor.putString("SimID", simSerial);
                                    editor.commit();
                                    todaysTarget.setText("T/A : Rs " + String.format("0" + "/" + "0" + " [" + "0") + "%" + "]");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                            }


                            //todaysTarget.setText("T/A : Rs "+t_total+"/"+achived_total);

//                            if(dialog != null || dialog.isShowing())
//                            {
//                                dialog.dismiss();
//                            }

                            //finish();

                        }


                        // }

                        // output.setText(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        if(dialog != null || dialog.isShowing())
//                        {
//                            dialog.dismiss();
//                        }
                    }


//                    if(dialog != null || dialog.isShowing())
//                    {
//                        dialog.dismiss();
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);
                    //Toast.makeText(MainActivity.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(MainActivity.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//                    if(dialog != null || dialog.isShowing())
//                    {
//                        dialog.dismiss();
//                    }

                }
            });

            //RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            // queue.add(jsObjRequest);

            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(MainActivity.this);
                Log.d("new error", "Setting a new request queue");
            }
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 050000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();
//            if(dialog != null || dialog.isShowing())
//            {
//                dialog.dismiss();
//            }


        }
    }

    private class scheduleoperation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("result")) {
                    response_result = response.getString("result");
                } else {
                    response_result = "data";
                }


                if (response_result.equalsIgnoreCase("Schedule doesn't exist")) {


                    //Toast.makeText(Order.this, response_result, Toast.LENGTH_LONG).show();

                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {


                            dialog.dismiss();
                            //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(MainActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });


                } else {

                    //dbvoc.getDeleteTable("delivery_products");
                    //dbvoc.getDeleteTable("delivery_schedules");
                    //dbvoc.getDeleteTable("credit_profile");
                    dbvoc.getDeletedelivery_schedulesAll();
                    dbvoc.getDeletedelivery_productsAll();
                    dbvoc.getDeletecredit_limitsAll();

                    JSONArray delivery_products = response.getJSONArray("delivery_products");
                    JSONArray delivery_schedules = response.getJSONArray("delivery_schedules");
                    JSONArray credit_profile = response.getJSONArray("credit_profiles");


                    Log.i("volley", "response reg delivery_products Length: " + delivery_products.length());
                    Log.i("volley", "response reg delivery_schedules Length: " + delivery_schedules.length());
                    Log.i("volley", "response reg credit_profile Length: " + credit_profile.length());

                    Log.d("volley", "delivery_products" + delivery_products.toString());
                    Log.d("volley", "delivery_schedules" + delivery_schedules.toString());
                    Log.d("volley", "credit_profile" + credit_profile.toString());

                    //
                    if (delivery_schedules.length() <= 0) {

                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                dialog.dismiss();
                                //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(MainActivity.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        });
                    } else {

                        Global_Data.GLOvel_CUSTOMER_ID = "";
                        for (int i = 0; i < delivery_products.length(); i++) {

                            JSONObject jsonObject = delivery_products.getJSONObject(i);

                            loginDataBaseAdapter.insertDeliveryProducts("", jsonObject.getString("customer_id"), jsonObject.getString("order_number"), "", "", "", "", "", jsonObject.getString("order_quantity"), jsonObject.getString("delivered_quantity"), jsonObject.getString("truck_number"), jsonObject.getString("transporter_details"), "", "", jsonObject.getString("product_name") + "" + "" + "");


                        }

                        for (int i = 0; i < delivery_schedules.length(); i++) {

                            JSONObject jsonObject = delivery_schedules.getJSONObject(i);

                            loginDataBaseAdapter.insertDeliverySchedule("", jsonObject.getString("customer_id"), jsonObject.getString("customer_code"), jsonObject.getString("order_number"), "", jsonObject.getString("user_email"), jsonObject.getString("dispatch_date"), jsonObject.getString("delivery_date"), jsonObject.getString("order_amount"), jsonObject.getString("accepted_payment_mode"), "", jsonObject.getString("collected_amount"), jsonObject.getString("outstanding_amount"), "", "", jsonObject.getString("customer_address"));


                        }


                        for (int i = 0; i < credit_profile.length(); i++) {

                            JSONObject jsonObject = credit_profile.getJSONObject(i);

                            loginDataBaseAdapter.insert_credit_profile("", jsonObject.getString("customer_id"), jsonObject.getString("customer_code"), "", "", "", "", jsonObject.getString("credit_limit"), jsonObject.getString("amount_outstanding"), jsonObject.getString("amount_overdue"), jsonObject.getString("business_unit"));


                        }

                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                Global_Data.Schedule_FLAG = "ALLCUSTOMER";
                                Intent intent = new Intent(MainActivity.this, Schedule_List.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                finish();
                                dialog.dismiss();
                            }
                        });

                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
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


                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        dialog.dismiss();
                    }
                });

            }


            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    dialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            MainActivity.this.runOnUiThread(new Runnable() {
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

    private void requestGPSPermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            cd = new ConnectionDetector(getApplicationContext());
                            isInternetPresent = cd.isConnectingToInternet();
                            if (isInternetPresent) {
                                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                                if (statusOfGPS) {
                                    Intent invoices_i = new Intent(getApplicationContext(), Electrician_Map.class);
                                    startActivity(invoices_i);
                                } else {
                                    Activity mContext = MainActivity.this; //change this your activity name
                                    StartLocationAlert startLocationAlert = new StartLocationAlert(mContext);
                                }


                            } else {
                                Toast toast = Toast.makeText(MainActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
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

    public void local_notification() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);

        //PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(MainActivity.this);
        b.setOngoing(true);
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                //.setSmallIcon(R.drawable.anchor_logo)
                .setColor(getResources().getColor(R.color.mdtp_red))
                .setTicker("Anchor")
                .setContentTitle("Promotional Activity")
                .setContentText("You are in Promotional Activity")
                .setDefaults(Notification.DEFAULT_LIGHTS)
                // .setContentIntent(contentIntent)
                .setContentInfo("Info");

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            b.setSmallIcon(R.drawable.notifi);
            b.setColor(getResources().getColor(R.color.darkorrange));
        } else {
            b.setSmallIcon(R.drawable.notifi);
            b.setColor(getResources().getColor(R.color.darkorrange));
        }


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());

    }

    private void enableAutoStart() {
        final Intent[] POWERMANAGER_INTENTS = {
                new Intent().setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")),
                new Intent().setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity")),
                new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity")),
                new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity")),
                new Intent().setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity")),
                new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")),
                new Intent().setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity")),
                new Intent().setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")),
                new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity")),
                new Intent().setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager")),
                new Intent().setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")),
                new Intent().setComponent(new ComponentName("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity")),
                new Intent().setComponent(new ComponentName("com.htc.pitroad", "com.htc.pitroad.landingpage.activity.LandingPageActivity")),
                new Intent().setComponent(new ComponentName("com.asus.mobilemanager", "com.asus.mobilemanager.MainActivity"))
        };

        for (Intent intent : POWERMANAGER_INTENTS)
            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage(" Are you sure you want to logout?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (Intent intent : POWERMANAGER_INTENTS)
                            if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
                                startActivity(intent);
                                break;
                            }
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });

                alertDialog.setCancelable(false);
                alertDialog.show();
                break;
            }
    }


}
