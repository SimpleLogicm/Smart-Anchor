package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.model.Reason;
import com.anchor.webservice.ConnectionDetector;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.GPSTracker;

public class NoOrderActivity extends BaseActivity {
    Spinner spinner1;
    GPSTracker gps;
    String Noorder_res = "";
    ProgressDialog dialog;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    Button buttonnoOrderSave, buttonnoOrdercancel;
    EditText edittextNoOrderreason;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    List<String> listReasons;
    ArrayAdapter<String> dataAdapter;
    ArrayList<Reason> dataReasons = new ArrayList<Reason>();
    LoginDataBaseAdapter loginDataBaseAdapter;
    HashMap<String, String> reasonsMap;
    static int cityID, beatID, retailerID, reasonID;
    String reasonOther = "", mobile = "", emailID = "", retailer_code = "";
    TextView no_order_head;
    String response_result;

    String reason_name;
    String reason_codes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_order);
        spinner1 = (Spinner) findViewById(R.id.spnReason);
        edittextNoOrderreason = (EditText) findViewById(R.id.edittextNoOrderreason);
        no_order_head = (TextView) findViewById(R.id.editTextTarget);
        Intent i = getIntent();
        String name = i.getStringExtra("retialer");
        cd = new ConnectionDetector(getApplicationContext());

        loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        SharedPreferences spf1 = this.getSharedPreferences("SimpleLogic", 0);
        String norderstr = spf1.getString("var_norder", "");


        if (norderstr.length() > 0) {
            no_order_head.setText(norderstr);
        } else {
            no_order_head.setText("No Order");
        }


        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            if (!Global_Data.Sub_Dealer_name.equalsIgnoreCase("")) {
                mTitleTextView.setText(Global_Data.Sub_Dealer_name);
                todaysTarget.setVisibility(View.GONE);
            } else {
                mTitleTextView.setText(Global_Data.order_retailer + " " + "(" + Global_Data.AmountOutstanding + "/" + Global_Data.AmountOverdue + ")");
                todaysTarget.setVisibility(View.VISIBLE);
            }


            SharedPreferences sp = NoOrderActivity.this.getSharedPreferences("SimpleLogic", 0);
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
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

//        TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//        //question_value.setTypeface(null,Typeface.BOLD);
//        welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        reasonsMap = new HashMap<String, String>();
        listReasons = new ArrayList<String>();
        //myDbHelper = new DatabaseHandler(getApplicationContext());
        LoadReasonsAsyncTask loadReasonsAsyncTask = new LoadReasonsAsyncTask(NoOrderActivity.this);
        loadReasonsAsyncTask.execute();

        SharedPreferences sp1 = NoOrderActivity.this.getSharedPreferences("SimpleLogic", 0);


        //userID=sp1.getInt("UserID", 0);
        cityID = sp1.getInt("CityID", 0);
        beatID = sp1.getInt("BeatID", 0);
        retailerID = sp1.getInt("RetailerID", 0);
        retailer_code = sp1.getString("RetailerCode", "");
        mobile = sp1.getString("Mobile", "");
        emailID = sp1.getString("EmailId", "");

    	/*listReasons.add("Product not moving");
    	listReasons.add("Already stocked");
    	listReasons.add("Seasonality");
    	listReasons.add("Other");*/

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listReasons);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);

        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                //Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();


                if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Other")) {
                    edittextNoOrderreason.setVisibility(View.VISIBLE);
                    reasonID = 0;

                } else {
                    if (parent.getSelectedItemId() != 0) {
                        //reasonID=dataReasons.get(pos-1).getReason_Id();
                        reasonID = 0;
                        edittextNoOrderreason.setVisibility(View.INVISIBLE);
                    }

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        buttonnoOrderSave = (Button) findViewById(R.id.buttonnoOrderSave);
        buttonnoOrderSave.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View b, MotionEvent event) {
                // TODO Auto-generated method stub

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));
                    requestGPSPermissionsigna();


                }
                return false;
            }
        });
    	/*buttonnoOrderSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.parseColor("#910505"));
				
				if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Other")) {
					reasonOther=edittextNoOrderreason.getText().toString();
					InsertOrderAsyncTask insertOrderAsyncTask=new InsertOrderAsyncTask(NoOrderFragment.this);
					insertOrderAsyncTask.execute();
				}
				if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select Reason")) {
					Toast toast1 = Toast.makeText(getApplicationContext(),"Please select reason", Toast.LENGTH_SHORT);
					toast1.setGravity(Gravity.CENTER, 0, 0);
					toast1.show();
				}
				
			}
		});*/
        buttonnoOrdercancel = (Button) findViewById(R.id.buttonnoOrdercancel);

        buttonnoOrdercancel.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View b, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));
                    onBackPressed();
                }
                return false;
            }
        });
    	/*buttonnoOrdercancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(Color.parseColor("#910505"));
				
			}
		});*/

    }

    private void showDialogueBox() {
        AlertDialog alertDialog = new AlertDialog.Builder(NoOrderActivity.this)
                .create(); // Read Update
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage(" Are you sure you want to continue?");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
//						InsertOrderAsyncTask insertOrderAsyncTask = new InsertOrderAsyncTask(
//								NoOrderActivity.this);
//						insertOrderAsyncTask.execute();

                        call_service();
                        dialog.cancel();


                    }
                });

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();


        AlertDialog alertDialog = new AlertDialog.Builder(NoOrderActivity.this).create(); //Read Update
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure you want to go previous page?");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                NoOrderActivity.this.finish();
            }
        });

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });


        alertDialog.show();
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


    public class LoadReasonsAsyncTask extends AsyncTask<Void, Void, Void> {

        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog;
        /**
         * application context.
         */
        private Activity activity;

        private Context context;


        public LoadReasonsAsyncTask(Activity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.dialog.setMessage("Loading Reasons");
            this.dialog.show();
            listReasons.clear();
            listReasons.add("Select Reason");

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                String REASON = "<Reasons><Reason><code>RC001</code><id>1</id><desc>Seasonality</desc><status>Y</status></Reason></Reasons>";

                Reason reason1 = new Reason(1, "Seasonality", "Y", "RC001");
                Reason reason2 = new Reason(1, "In Stock", "Y", "RC001");
                Reason reason3 = new Reason(1, "Not Good Product", "Y", "RC001");

                dataReasons.add(reason1);
                dataReasons.add(reason2);
                dataReasons.add(reason3);

                //dataReasons=(ArrayList<DatabaseModel>) myDbHelper.loadReasons();
                List<Local_Data> contacts = dbvoc.getAllnoorder_reason();

                if (contacts.size() <= 0) {
                    Toast toast = Toast.makeText(NoOrderActivity.this,
                            "Reason Not Found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    for (Local_Data cn : contacts) {

                        //Global_Data.GLOvel_CUSTOMER_ID = cn.getCust_Code();
                        if (!cn.getreason_name().equalsIgnoreCase("Other")) {
                            listReasons.add(cn.getreason_name());
                        }

                    }
                }


//				for (int i = 0; i < dataReasons.size(); i++) {
//					listReasons.add(dataReasons.get(i).getReason_desc());
//				}
				
					/*int i=1;
					for (Iterator iterator = dataReasons.iterator(); iterator.hasNext();) {
						DatabaseModel databaseModel = (DatabaseModel) iterator.next();
						//Log.e("DATA", ""+databaseModel);
						reasonsMap.put(""+i, ""+databaseModel.getId());
						i++;
						
						listReasons.add(databaseModel.getName());
					}*/
                listReasons.add("Other");
            } catch (Exception e) {
                // TODO: handle exception
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (dialog.isShowing()) {
                dialog.dismiss();

            }

            dataAdapter.notifyDataSetChanged();
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner1.setAdapter(dataAdapter);

        }
    }

    protected void onPause() {
        super.onPause();
        System.gc();
    }


    public void call_service() {
        System.gc();

        Calendar c = Calendar.getInstance();
        System.out.println("Current time =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        final String formattedDaten = df.format(c.getTime());

        String reason_code = "";
        try {

            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date1 = originalFormat.parse(getDateTime());

            if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                Noorder_res = edittextNoOrderreason.getText().toString();
            } else {

                Noorder_res = spinner1.getSelectedItem().toString().trim();
                List<Local_Data> contacts = dbvoc.get_reason_code(spinner1.getSelectedItem().toString().trim());

                if (contacts.size() <= 0) {
                    Toast toast = Toast.makeText(NoOrderActivity.this,
                            "Reason code Not Found", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {

                    for (Local_Data cn : contacts) {
                        reason_code = cn.getreason_code();

                    }
                }
            }

            String domain = "";
            String device_id = "";


            SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
            device_id = sp.getString("devid", "");

            domain = this.getResources().getString(R.string.service_domain);

            JsonObjectRequest jsObjRequest = null;
            try {


                if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                    Log.d("Server url", "Server url" + domain + "no_orders/save_no_orders?customer_code=" + Global_Data.GLOvel_CUSTOMER_ID + "&reason_name=" + Noorder_res + "&user_email=" + Global_Data.GLOvel_USER_EMAIL);

                    try {
                        AppLocationManager appLocationManager = new AppLocationManager(NoOrderActivity.this);
                        Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                        Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        PlayService_Location PlayServiceManager = new PlayService_Location(NoOrderActivity.this);

                        if (PlayServiceManager.checkPlayServices(NoOrderActivity.this)) {
                            Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (Global_Data.Sub_Dealer_name.equalsIgnoreCase("")) {
                        String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());

                        loginDataBaseAdapter.insertNoOrder("", Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_CUSTOMER_ID, "",
                                Global_Data.GLOvel_USER_EMAIL, "", "", "", "no", "", "", "", Noorder_res, "Other", Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, PINString);

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "No Order Save Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Intent a = new Intent(NoOrderActivity.this, Order.class);
                        startActivity(a);
                        finish();
                    } else {

                        reason_name = Noorder_res;
                        reason_codes = "";
                        new NoOrderActivity.Varientsave().execute();
                    }


                } else {

                    try {
                        AppLocationManager appLocationManager = new AppLocationManager(NoOrderActivity.this);
                        Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                        Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        PlayService_Location PlayServiceManager = new PlayService_Location(NoOrderActivity.this);

                        if (PlayServiceManager.checkPlayServices(NoOrderActivity.this)) {
                            Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                        } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    if (Global_Data.Sub_Dealer_name.equalsIgnoreCase("")) {
                        String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
                        loginDataBaseAdapter.insertNoOrder("", Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_CUSTOMER_ID, "",
                                Global_Data.GLOvel_USER_EMAIL, "", "", "", "no", "", "", "", reason_code, "", Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, PINString);


                        Toast toast = Toast.makeText(getApplicationContext(),
                                "No Order Save Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Intent a = new Intent(NoOrderActivity.this, Order.class);
                        startActivity(a);
                        finish();
                    } else {

                        reason_name = "";
                        reason_codes = reason_code;
                        new NoOrderActivity.Varientsave().execute();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                //dialog.dismiss();
            }


        } catch (Exception e) {
            // TODO: handle exception
            Log.e("DATA", e.getMessage());
        }
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void requestGPSPermissionsigna() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            gps = new GPSTracker(NoOrderActivity.this);
                            if (!gps.canGetLocation()) {

                                gps.showSettingsAlertnew();
                            } else {
                                if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select Reason")) {
                                    Toast toast1 = Toast.makeText(getApplicationContext(), "Please select reason", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();
                                } else {
                                    if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Other")) {
                                        reasonOther = edittextNoOrderreason.getText().toString();
                                        if (reasonOther.length() == 0) {
                                            Toast toast1 = Toast.makeText(getApplicationContext(), "Please Enter reason", Toast.LENGTH_SHORT);
                                            toast1.setGravity(Gravity.CENTER, 0, 0);
                                            toast1.show();
                                        } else {
//									isInternetPresent = cd.isConnectingToInternet();
//									if (isInternetPresent)
//				                    {
                                            showDialogueBox();

                                            //call_service();
//				                    }
//					   	        	else
//					   	        	{
//					   	        	 //Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//										Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
//										toast.setGravity(Gravity.CENTER, 0, 0);
//										toast.show();
//					   	        	}

                                        }

                                    } else {
//							  isInternetPresent = cd.isConnectingToInternet();
//								if (isInternetPresent)
//			                    {
                                        showDialogueBox();

                                        //call_service();
//			                    }
//				   	        	else
//				   	        	{
//
//									Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
//									toast.setGravity(Gravity.CENTER, 0, 0);
//									toast.show();
//				   	        	}
                                    }


                                }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
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

    private class Varientsave extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            cd = new ConnectionDetector(NoOrderActivity.this);
            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {

                runOnUiThread(new Runnable() {
                    public void run() {

                        try {
                            AppLocationManager appLocationManager = new AppLocationManager(NoOrderActivity.this);
                            Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                            Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                            PlayService_Location PlayServiceManager = new PlayService_Location(NoOrderActivity.this);

                            if (PlayServiceManager.checkPlayServices(NoOrderActivity.this)) {
                                Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                            } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                                Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }


                    }
                });



                JSONArray no_order = new JSONArray();
                JSONObject product_value = new JSONObject();
                JSONObject product_valuenew = new JSONObject();


                JSONObject no_order_object = new JSONObject();

                try {
                    String PINStrings = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
                    no_order_object.put("order_number", PINStrings);
                    no_order_object.put("sub_dealer_code", Global_Data.Sub_Dealer_Code);
                    no_order_object.put("sub_dealer_mobile", Global_Data.SUB_Mobile);
                    no_order_object.put("sub_dealer_email", Global_Data.Sub_Email);
                    no_order_object.put("dealer_id", Global_Data.Dealer_Code);

                    if (!reason_name.equalsIgnoreCase("")) {
                        no_order_object.put("reason", reason_name);
                        no_order_object.put("reason_code", "");
                    } else {
                        no_order_object.put("reason_code", reason_codes);
                        no_order_object.put("reason", "");
                    }
                    no_order_object.put("email", Global_Data.GLOvel_USER_EMAIL);
                    no_order_object.put("latitude", Global_Data.GLOvel_LATITUDE);
                    no_order_object.put("longitude", Global_Data.GLOvel_LONGITUDE);

                    no_order.put(no_order_object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Double pp = 0.0;
                try {


                    product_valuenew.put("sub_dealer_no_order", no_order);
                    product_valuenew.put("imei_no", Global_Data.device_id);
                    Log.d("sub_dealer_no_order", no_order.toString());
                    Log.d("product_valuenew", product_valuenew.toString());

                    String domain = NoOrderActivity.this.getResources().getString(R.string.service_domain);
                    Log.i("volley", "domain: " + domain);
                    JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain + "sub_dealers/create_sub_delaer_no_order", product_valuenew, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("volley", "response: " + response);


                            response_result = "";
                            //if (response.has("result")) {
                            try {
                                response_result = response.getString("message");
                            } catch (JSONException e) {
                                e.printStackTrace();

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        dialog.dismiss();
                                    }
                                });
                            }

                            if (response_result.equalsIgnoreCase("Device not found.")) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast toast = Toast.makeText(NoOrderActivity.this, "Device Not Found", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        dialog.dismiss();


                                    }
                                });

                            } else {

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this, response_result, Toast.LENGTH_LONG).show();
                                        finish();

                                        dialog.dismiss();


                                    }
                                });


                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(final VolleyError error) {
                            //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this,
                                                "your internet connection is not working, saving locally. Please sync when Internet is available",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else if (error instanceof AuthFailureError) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this,
                                                "Server AuthFailureError  Error",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else if (error instanceof ServerError) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this,
                                                "Server   Error",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else if (error instanceof NetworkError) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this,
                                                "your internet connection is not working, saving locally. Please sync when Internet is available",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else if (error instanceof ParseError) {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this,
                                                "ParseError   Error",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        Toast.makeText(NoOrderActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                            runOnUiThread(new Runnable() {
                                public void run() {

                                    dialog.dismiss();
                                }
                            });

                            // finish();
                        }
                    });

                    RequestQueue requestQueue = Volley.newRequestQueue(NoOrderActivity.this);
                    // queue.add(jsObjRequest);
                    int socketTimeout = 30000;//30 seconds - change to what you want
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    jsObjRequest.setRetryPolicy(policy);
                    requestQueue.add(jsObjRequest);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                dialog.dismiss();
                Toast.makeText(NoOrderActivity.this, "You don't have internet connection.", Toast.LENGTH_SHORT).show();
            }




            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();
            NoOrderActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });



        }

        @Override
        protected void onPreExecute() {


            NoOrderActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    if (dialog == null)
                        dialog = new ProgressDialog(NoOrderActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                    dialog.setMessage("Please wait....");
                    dialog.setTitle("Smart Anchor App");
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
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
}
