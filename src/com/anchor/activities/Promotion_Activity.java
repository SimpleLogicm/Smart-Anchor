package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.helper.GlideApp;
import com.anchor.helper.MultipartUtility;
import com.anchor.model.Promotional_Model;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.chrisbanes.photoview.PhotoView;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.GPSTracker;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class Promotion_Activity extends Activity {

    ImageView new_launch, market_survey, advertisement;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    ProgressDialog pDialog;
    String response_result = "";
    public ArrayList<Long> list = new ArrayList<>();
    LoginDataBaseAdapter loginDataBaseAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    Button rpo_chhose_file;
    Boolean B_flag;
    private String mCurrentPhotoPath = "";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String pictureImagePath_new = "";
    //com.suke.widget.SwitchButton switchButton;
    StringBuilder str;
    TextView intime, indate, outtime, outdate;
    String inDateTime = "";
    String outDateTime = "";
    LinearLayout indt_container, outdt_container;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    static String final_response = "";
    JSONArray EVENT_JSON = null;
    HashMap<String, String> eventmap = new HashMap<String, String>();
    HashMap<String, String> eventmapNEW = new HashMap<String, String>();
    ArrayList<String> list_Events = new ArrayList<String>();
    ArrayAdapter<String> adapter_events;
    Spinner List_Of_Event_Spinner;
    Button ss_submit, ss_Reset;
    ImageView events_pick;
    EditText pro_edit;
    ImageView in, out;
    RelativeLayout rlbtn;
    GPSTracker gps;
    String event_name = "Select Events";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_promotional_);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ss_submit = findViewById(R.id.ss_submit);
        ss_Reset = findViewById(R.id.ss_Reset);
        events_pick = findViewById(R.id.events_pick);

        in = findViewById(R.id.in);
        out = findViewById(R.id.out);
        rlbtn = findViewById(R.id.rlbtn);

        pro_edit = findViewById(R.id.pro_edit);
        intime = findViewById(R.id.intime);
        indate = findViewById(R.id.indate);
        outtime = findViewById(R.id.outtime);
        outdate = findViewById(R.id.outdate);
        indt_container = findViewById(R.id.indt_container);
        outdt_container = findViewById(R.id.outdt_container);
        rpo_chhose_file = findViewById(R.id.rpo_chhose_file);
        List_Of_Event_Spinner = findViewById(R.id.List_Of_Event_Spinner);
        //   switchButton = findViewById(R.id.switch1);

        list_Events.add("Select Events");
        adapter_events = new ArrayAdapter<String>(Promotion_Activity.this,
                android.R.layout.simple_spinner_item, list_Events);
        adapter_events.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List_Of_Event_Spinner.setAdapter(adapter_events);

        in.setVisibility(View.INVISIBLE);

        loginDataBaseAdapter = new LoginDataBaseAdapter(Promotion_Activity.this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        rlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in.setVisibility(View.VISIBLE);
                out.setVisibility(View.INVISIBLE);
                infn();

            }

            private void infn() {


                if (indt_container.getVisibility() == View.GONE) {
                    indt_container.setVisibility(View.VISIBLE);
                    outdt_container.setVisibility(View.VISIBLE);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                DateFormat date_onlyn = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String daten = sdf.format(date);

                SimpleDateFormat date_form = new SimpleDateFormat("dd-MM-yyyy");
                String datenew = date_form.format(date);

                SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
                String currentDateTimeString = sdf_time.format(date);

                inDateTime = sdf.format(date);
                intime.setText("IN Time : " + currentDateTimeString);
                indate.setText("IN Date : " + datenew);
                if (!outDateTime.equalsIgnoreCase("")) {
                    outdt_container.setVisibility(View.GONE);
                }
                showDialogn("IN");


            }
        });


        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (List_Of_Event_Spinner.getSelectedItem().toString().equalsIgnoreCase("Select Events")) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Please Select Event", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();


                } else {


                    if (indt_container.getVisibility() == View.GONE) {
                        indt_container.setVisibility(View.VISIBLE);
                        outdt_container.setVisibility(View.VISIBLE);
                    }
                    in.setVisibility(View.INVISIBLE);
                    out.setVisibility(View.VISIBLE);

                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                    DateFormat date_onlyn = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date();
                    String daten = sdf.format(date);

                    SimpleDateFormat date_form = new SimpleDateFormat("dd-MM-yyyy");
                    String datenew = date_form.format(date);

                    SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
                    String currentDateTimeString = sdf_time.format(date);

                    outDateTime = sdf.format(date);
                    outtime.setText("Out Time : " + currentDateTimeString);
                    outdate.setText("Out Date : " + datenew);
                    outdt_container.setVisibility(View.VISIBLE);
                    showDialogn("OUT");

                }
            }
        });


//        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
//
//                if (indt_container.getVisibility() == View.GONE) {
//                    indt_container.setVisibility(View.VISIBLE);
//                    outdt_container.setVisibility(View.VISIBLE);
//                }
//
//
//                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
//                DateFormat date_onlyn = new SimpleDateFormat("yyyy-MM-dd");
//                Date date = new Date();
//                String daten = sdf.format(date);
//
//                SimpleDateFormat date_form = new SimpleDateFormat("dd-MM-yyyy");
//                String datenew = date_form.format(date);
//
//                SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
//                String currentDateTimeString = sdf_time.format(date);
//
//
//                if (isChecked) {
//                    if (List_Of_Event_Spinner.getSelectedItem().toString().equalsIgnoreCase("Select Events")) {
//
//
//                        Toast toast = Toast.makeText(getApplicationContext(), "Please Select Event", Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();
//
//                    } else {
//                        outDateTime = sdf.format(date);
//                        outtime.setText("Out Time : " + currentDateTimeString);
//                        outdate.setText("Out Date : " + datenew);
//                        outdt_container.setVisibility(View.VISIBLE);
//                        showDialogn("OUT");
//                    }
//
//
//                } else {
//                    inDateTime = sdf.format(date);
//                    intime.setText("IN Time : " + currentDateTimeString);
//                    indate.setText("IN Date : " + datenew);
//                    if (!outDateTime.equalsIgnoreCase("")) {
//                        outdt_container.setVisibility(View.GONE);
//                    }
//                    showDialogn("IN");

//
//                }
//            }
//
//        });


        rpo_chhose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestStoragePermission();
            }
        });


        try {


            cd = new ConnectionDetector(Promotion_Activity.this);
            progressDialog = new ProgressDialog(Promotion_Activity.this, ProgressDialog.THEME_HOLO_LIGHT);

            isInternetPresent = cd.isConnectingToInternet();
            if (isInternetPresent) {
//            if (progressDialog != null && progressDialog.isShowing())
//                progressDialog.dismiss();

                progressDialog.setMessage("Please wait....");
                progressDialog.setTitle("Smart Anchor App");
                progressDialog.setCancelable(false);
                progressDialog.show();

                try {

                    Events_OnlineData();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }


            } else {

                Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Promotional Activity");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Promotion_Activity.this.getSharedPreferences("SimpleLogic", 0);

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
//	       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        cd = new ConnectionDetector(getApplicationContext());
        getAddress();


        ss_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gps = new GPSTracker(Promotion_Activity.this);
                if (!gps.canGetLocation()) {

                    gps.showSettingsAlertnew();
                } else {
                    if (inDateTime.equalsIgnoreCase("")) {

                        Toast toast = Toast.makeText(Promotion_Activity.this, "Please select In",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (List_Of_Event_Spinner.getSelectedItem().toString().equalsIgnoreCase("Select Events")) {

                        Toast toast = Toast.makeText(Promotion_Activity.this, "Please Select Events",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (outDateTime.equalsIgnoreCase("")) {

                        Toast toast = Toast.makeText(Promotion_Activity.this, " Please select Out",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Promotion_Activity.this).create();
                        alertDialog.setTitle("Confirmation");
                        alertDialog.setMessage("Sure do you wish to submit the Promotional Activity ?");
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                cd = new ConnectionDetector(getApplicationContext());
                                if (cd.isConnectingToInternet()) {
                                    new doFileUpload().execute();
                                } else {

                                    Toast toast = Toast.makeText(Promotion_Activity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                }
                            }
                        });

                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        alertDialog.show();


                    }
                }


            }
        });

        ss_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(Promotion_Activity.this).create();
                alertDialog.setTitle("Confirmation");
                alertDialog.setMessage("Sure do you wish to reset the Promotional Activity ?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        outDateTime = "";
                        inDateTime = "";
                        mCurrentPhotoPath = "";
                        outtime.setText("");
                        outdate.setText("");
                        intime.setText("");
                        indate.setText("");
                        pro_edit.setText("");
                        List_Of_Event_Spinner.setSelection(0);
                        events_pick.setImageResource(R.drawable.vector_camera_icon);
                        in.setVisibility(View.INVISIBLE);
                        out.setVisibility(View.VISIBLE);
                        dbvoc.getDeleteTable("promotion_activity");

                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancelAll();

                        rlbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                in.setVisibility(View.VISIBLE);
                                out.setVisibility(View.INVISIBLE);
                                infn();

                            }

                            private void infn() {


                                if (indt_container.getVisibility() == View.GONE) {
                                    indt_container.setVisibility(View.VISIBLE);
                                    outdt_container.setVisibility(View.VISIBLE);
                                }

                                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                                DateFormat date_onlyn = new SimpleDateFormat("yyyy-MM-dd");
                                Date date = new Date();
                                String daten = sdf.format(date);

                                SimpleDateFormat date_form = new SimpleDateFormat("dd-MM-yyyy");
                                String datenew = date_form.format(date);

                                SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
                                String currentDateTimeString = sdf_time.format(date);

                                inDateTime = sdf.format(date);
                                intime.setText("IN Time : " + currentDateTimeString);
                                indate.setText("IN Date : " + datenew);
                                if (!outDateTime.equalsIgnoreCase("")) {
                                    outdt_container.setVisibility(View.GONE);
                                }
                                showDialogn("IN");


                            }
                        });


                        in.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (List_Of_Event_Spinner.getSelectedItem().toString().equalsIgnoreCase("Select Events")) {

                                    Toast toast = Toast.makeText(getApplicationContext(), "Please Select Event", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                } else {


                                    if (indt_container.getVisibility() == View.GONE) {
                                        indt_container.setVisibility(View.VISIBLE);
                                        outdt_container.setVisibility(View.VISIBLE);
                                    }
                                    in.setVisibility(View.INVISIBLE);
                                    out.setVisibility(View.VISIBLE);

                                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                                    DateFormat date_onlyn = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = new Date();
                                    String daten = sdf.format(date);

                                    SimpleDateFormat date_form = new SimpleDateFormat("dd-MM-yyyy");
                                    String datenew = date_form.format(date);

                                    SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
                                    String currentDateTimeString = sdf_time.format(date);

                                    outDateTime = sdf.format(date);
                                    outtime.setText("Out Time : " + currentDateTimeString);
                                    outdate.setText("Out Date : " + datenew);
                                    outdt_container.setVisibility(View.VISIBLE);
                                    showDialogn("OUT");

                                }
                            }
                        });

                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();


                //  switchButton.setChecked(true);
            }
        });

        events_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mCurrentPhotoPath.equalsIgnoreCase("")) {

                    showImage(mCurrentPhotoPath);


                }
            }
        });


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


        dbvoc.getDeleteTable("promotion_activity");
        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(inDateTime))) {

            String event_id = "";
            try {
                event_id = eventmap.get(List_Of_Event_Spinner.getSelectedItem().toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            loginDataBaseAdapter.insert_promotion_activity_table_data(Global_Data.GLOvel_USER_EMAIL, List_Of_Event_Spinner.getSelectedItem().toString(), inDateTime, outDateTime, pro_edit.getText().toString(), Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, Global_Data.address, "", "", "", "", "", "", "",mCurrentPhotoPath);

            local_notification();
        }

        Intent i = new Intent(Promotion_Activity.this, Sales_Dash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

//        AlertDialog alertDialog = new AlertDialog.Builder(Promotion_Activity.this).create();
//        alertDialog.setTitle("Confirmation");
//        alertDialog.setMessage("Do you want to discard your meeting ?");
//        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//
//            }
//        });
//
//        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        alertDialog.show();


    }

    @Override
    protected void onDestroy() {


        super.onDestroy();

        if (progressDialog != null) {
            progressDialog.dismiss();
        }


    }


    private boolean isDeviceSupportCamera() {
        // this device has a camera
        // no camera on this device
        return getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            B_flag = isDeviceSupportCamera();

                            if (B_flag == true) {
                                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


                                AlertDialog.Builder builder = new AlertDialog.Builder(Promotion_Activity.this);

                                builder.setTitle("Add Photo!");

                                builder.setItems(options, new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialog, int item) {

                                        if (options[item].equals("Take Photo")) {
                                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                                // Create the File where the photo should go
                                                File photoFile = null;
                                                try {
                                                    photoFile = createImageFile();
                                                } catch (Exception ex) {
                                                    // Error occurred while creating the File
                                                    Log.i("Image TAG", "IOException");
                                                    mCurrentPhotoPath = "";
                                                }
                                                // Continue only if the File was successfully created
                                                if (photoFile != null) {
                                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                                                }
                                            }

                                        } else if (options[item].equals("Choose from Gallery")) {

                                            // image_check = "gallery";
                                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                            startActivityForResult(intent, 2);


                                        } else if (options[item].equals("Cancel")) {

                                            dialog.dismiss();

                                        }

                                    }

                                });

                                builder.show();

                            } else {
                                Toast.makeText(getApplicationContext(), "no camera on this device", Toast.LENGTH_LONG).show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Promotion_Activity.this);
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "Anchor";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AnchorEvents");

        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        // mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            try {

                progressDialog = new ProgressDialog(Promotion_Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("Please wait....");
                progressDialog.setTitle("Smart Anchor App");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //  }

                new ActivityResultTask().execute();


            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                mCurrentPhotoPath = "file:" + c.getString(columnIndex);

                progressDialog = new ProgressDialog(Promotion_Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
                progressDialog.setMessage("Please wait....");
                progressDialog.setTitle("Smart Anchor App");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //  }

                new ActivityResultTask().execute();

                pictureImagePath_new = c.getString(columnIndex);

                c.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

    }

    public void showDialogn(String flag) {
        gps = new GPSTracker(Promotion_Activity.this);
        if (!gps.canGetLocation()) {
            indt_container.setVisibility(View.GONE);
            outdt_container.setVisibility(View.GONE);
            in.setVisibility(View.INVISIBLE);
            out.setVisibility(View.VISIBLE);
            intime.setText("");
            indate.setText("");
            inDateTime ="";
            outDateTime = "";

            outtime.setText("");
            outdate.setText("");
            gps.showSettingsAlertnew();
        } else {
            final Dialog dialognew = new Dialog(Promotion_Activity.this);
            dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialognew.setCancelable(false);
            dialognew.setContentView(R.layout.promotion_dialog);

            TextView pro_header = dialognew.findViewById(R.id.pro_header);
            TextView pro_time = dialognew.findViewById(R.id.pro_time);
            TextView pro_date = dialognew.findViewById(R.id.pro_date);
            TextView pro_address = dialognew.findViewById(R.id.pro_address);
            Button pro_click = dialognew.findViewById(R.id.pro_click);


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            // String daten = sdf.format(date);
            String datenew = sdf.format(date);

            Date d = new Date();
            SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
            String currentDateTimeString = sdf_time.format(d);

            if (flag.equalsIgnoreCase("OUT")) {
                pro_header.setText("OUT TIME");
            } else {
                pro_header.setText("IN TIME");
                outDateTime = "";
            }

//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
//            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
//        }
//
//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
//            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
//        }


            //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.address)) {
                String str = Global_Data.address.trim().replaceAll("\n", " ");
                pro_address.setText("Address : " + str);
            } else {
                pro_address.setText("Address Not Found.");
            }

            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(datenew)) {
                try {
                    String[] timenewarray = datenew.split("-");
                    String month = getMonthForInt(Integer.parseInt(timenewarray[1]) - 1);
                    pro_date.setText("Date : " + timenewarray[0] + " " + month + " " + timenewarray[2]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                pro_date.setText("Date Not Found.");
            }

            pro_time.setText("Time : " + currentDateTimeString);

            pro_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialognew.dismiss();

                }
            });

            dialognew.show();
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

    public void getAddress() {

        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
                try {

                    LocationAddress locationAddress = new LocationAddress();
                    LocationAddress.getAddressFromLocation(Double.valueOf(Global_Data.GLOvel_LATITUDE), Double.valueOf(Global_Data.GLOvel_LONGITUDE),
                            Promotion_Activity.this, new GeocoderHandler());
                    Geocoder geo = new Geocoder(Promotion_Activity.this.getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = geo.getFromLocation(Double.valueOf(Global_Data.GLOvel_LATITUDE), Double.valueOf(Global_Data.GLOvel_LONGITUDE), 1);
                    if (addresses.isEmpty()) {
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

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str.toString())) {
                                Global_Data.address = str.toString();


                            } else {
                                Global_Data.address = "";

                            }


                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                Global_Data.address = "";
            }


        } else {
            Global_Data.address = "";
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

    public void Events_OnlineData() {

        String user_email = "";
        sp = this.getSharedPreferences("SimpleLogic", 0);
        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String domain = getResources().getString(R.string.service_domain);
        String service_domain = domain + "nukkad_meets/get_all_events";


        Log.i("user list url", "order list url " + service_domain);

        StringRequest jsObjRequest = null;

        jsObjRequest = new StringRequest(Request.Method.GET, service_domain, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("volley", "response: " + response);
                final_response = response;

                //  auto_searchcust.setText(namemm);
                new getevents_Data().execute(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();


//                        Intent intent = new Intent(ShopProfile.this, ShopProfile.class);
//                        startActivity(intent);
//                        finish();
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof AuthFailureError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server AuthFailureError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof ParseError) {

                            Toast toast = Toast.makeText(getApplicationContext(), "ParseError Error", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();


                        } else {

                            Toast toast = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }
                        progressDialog.dismiss();
                        //  State_Offline_Data();
                        // finish();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        int socketTimeout = 300000;//3 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(true);
        //requestQueue.getCache().clear();
        requestQueue.add(jsObjRequest);
    }


    private class getevents_Data extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... responsenew) {


            try {
                JSONObject response = new JSONObject(final_response);
                if (response.has("message")) {
                    response_result = response.getString("message");
                    Promotion_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });
                } else {
                    response_result = "data";
                    try {

                        EVENT_JSON = response.getJSONArray("events");
                        // City = response.getJSONArray("business_product_categories");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.i("volley", "response EVENT_JSON Length: " + EVENT_JSON.length());
                    Log.d("volley", "EVENT_JSON" + EVENT_JSON.toString());

                    if (EVENT_JSON.length() <= 0) {

                        Promotion_Activity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                                Toast toast = Toast.makeText(getApplicationContext(), "Events Record doesn't exist", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();


                            }
                        });
                    } else {

                        eventmap.clear();
                        eventmapNEW.clear();
                        list_Events.clear();
                        list_Events.add("Select Events");
                        for (int i = 0; i < EVENT_JSON.length(); i++) {

                            JSONObject jsonObject = null;
                            try {
                                jsonObject = EVENT_JSON.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(EVENT_JSON.getString(i))) {
                                    {
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("name").trim())) {

                                            if (!list_Events.contains(jsonObject.getString("name").trim())) {
                                                list_Events.add(jsonObject.getString("name").trim());
                                                eventmap.put(jsonObject.getString("name").trim(), jsonObject.getString("id").trim());
                                                eventmapNEW.put(jsonObject.getString("id").trim(), jsonObject.getString("name").trim());
                                            }

                                        }
                                    }


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                        Promotion_Activity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                try {

                                    adapter_events = new ArrayAdapter<String>(Promotion_Activity.this,
                                            android.R.layout.simple_spinner_item, list_Events);
                                    adapter_events.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    List_Of_Event_Spinner.setAdapter(adapter_events);

                                    try
                                    {
                                        int spinnerPosition = adapter_events.getPosition(event_name);
                                        List_Of_Event_Spinner.setSelection(spinnerPosition);
                                    }catch (Exception ex)
                                    {
                                        ex.printStackTrace();
                                    }

                                    progressDialog.dismiss();
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                            }
                        });


                        Promotion_Activity.this.runOnUiThread(new Runnable() {
                            public void run() {

                                progressDialog.dismiss();
                            }
                        });
                        //	dialog.dismiss();

                        //finish();

                    }


                    Promotion_Activity.this.runOnUiThread(new Runnable() {
                        public void run() {

                            progressDialog.dismiss();
                        }
                    });

                }
            } catch (JSONException e) {
                e.printStackTrace();


                Promotion_Activity.this.runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                });

            }


            Promotion_Activity.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
                }
            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Promotion_Activity.this.runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
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

    private class ActivityResultTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            try {

                if (!mCurrentPhotoPath.equalsIgnoreCase("")) {
                    try {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GlideApp.with(Promotion_Activity.this)
                                            .load(Uri.parse(mCurrentPhotoPath))
                                            .thumbnail(0.5f)
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .into(events_pick);


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                        // reduce_img_Qaulity(Uri.parse(outletsignboard_mCurrentPhotoPath));
                        // compressImage(outletsignboard_mCurrentPhotoPath);
                        String filePath = getRealPathFromURI(Uri.parse(mCurrentPhotoPath).getPath());
                        Bitmap scaledBitmap = null;
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        Bitmap b = BitmapFactory.decodeFile(Uri.parse(mCurrentPhotoPath).getPath(), options);
                        int actualHeight = options.outHeight;
                        int actualWidth = options.outWidth;

                        float maxHeight = 816.0f;
                        float maxWidth = 612.0f;
                        float imgRatio = actualWidth / actualHeight;
                        float maxRatio = maxWidth / maxHeight;

                        if (actualHeight > maxHeight || actualWidth > maxWidth) {
                            if (imgRatio < maxRatio) {
                                imgRatio = maxHeight / actualHeight;
                                actualWidth = (int) (imgRatio * actualWidth);
                                actualHeight = (int) maxHeight;
                            } else if (imgRatio > maxRatio) {
                                imgRatio = maxWidth / actualWidth;
                                actualHeight = (int) (imgRatio * actualHeight);
                                actualWidth = (int) maxWidth;
                            } else {
                                actualHeight = (int) maxHeight;
                                actualWidth = (int) maxWidth;

                            }
                        }
                        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
                        options.inJustDecodeBounds = false;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        options.inTempStorage = new byte[16 * 1024];
                        try {
//          load the bitmap from its path
                            b = BitmapFactory.decodeFile(Uri.parse(mCurrentPhotoPath).getPath(), options);

                        } catch (OutOfMemoryError exception) {
                            exception.printStackTrace();

                        }
                        try {
                            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
                        } catch (OutOfMemoryError exception) {
                            exception.printStackTrace();

                        }
                        float ratioX = actualWidth / (float) options.outWidth;
                        float ratioY = actualHeight / (float) options.outHeight;
                        float middleX = actualWidth / 2.0f;
                        float middleY = actualHeight / 2.0f;

                        Matrix scaleMatrix = new Matrix();
                        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
                        Canvas canvas = new Canvas(scaledBitmap);
                        canvas.setMatrix(scaleMatrix);
                        canvas.drawBitmap(b, middleX - b.getWidth() / 2, middleY - b.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(Uri.parse(mCurrentPhotoPath).getPath());

                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);
                            Matrix matrix = new Matrix();
                            if (orientation == 6) {
                                matrix.postRotate(90);

                            } else if (orientation == 3) {
                                matrix.postRotate(180);

                            } else if (orientation == 8) {
                                matrix.postRotate(270);

                            }
                            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                                    true);


                        } catch (IOException e) {
                            e.printStackTrace();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        GlideApp.with(Promotion_Activity.this)
                                                .load(Uri.parse(mCurrentPhotoPath))
                                                .thumbnail(0.5f)
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(events_pick);


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }


//                            Bitmap bmRotated = rotateBitmap(out, orientation);
                        File file = new File(Uri.parse(mCurrentPhotoPath).getPath());
//                            FileOutputStream fOut;
                        FileOutputStream out = null;


                        try {
                            out = new FileOutputStream(file);

//          write the compressed bitmap at the destination specified by filename.
                            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

//                                fOut = new FileOutputStream(file);
//                                bmRotated.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                                fOut.flush();
//                                fOut.close();
//                                out.recycle();
//                                b.recycle();
//                                bmRotated.recycle();
                        } catch (Exception e) {
                        }


                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                Bitmap mImageBitmap = null;
                                try {
                                    mImageBitmap = MediaStore.Images.Media.getBitmap(Promotion_Activity.this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                                    events_pick.setImageBitmap(mImageBitmap);
//

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                        // outlet_signboard_pick.setRotation(90);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();


//                }
//            });

            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            // might want to change "executed" for the returned string passed
            // into onPostExecute() but that is upto you
            //dialog.dismiss();
            Promotion_Activity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
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

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public void showImage(String bitmap) {
        final Dialog dialognew = new Dialog(Promotion_Activity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // dialognew.setCancelable(false);
        dialognew.setContentView(R.layout.image_custom_dialog);

        final PhotoView zoom_image = dialognew.findViewById(R.id.zoom_image);

        // String final_url = bitmap.replace("file:", "");
        // zoom_image.setImage(ImageSource.uri(final_url));
//
//        Picasso.get().load(Uri.parse(bitmap)) // web image url
//                .fit().centerInside()
//                //.transform(transformation)
//                // .rotate(90)
//                .error(R.drawable.img_not_found)
//                .placeholder(R.drawable.jz_loading)
//                .into(zoom_image);

        GlideApp.with(Promotion_Activity.this)
                .load(Uri.parse(bitmap))
                .thumbnail(0.5f)
                //.centerCrop()
                .placeholder(R.drawable.img_not_found)
                .error(R.drawable.img_not_found)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(zoom_image);


        Button cancelf = dialognew.findViewById(R.id.cancelf);
        cancelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialognew.dismiss();
            }
        });

        dialognew.show();

    }

    public class doFileUpload extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... response) {

            String event_id = "";

            try {
                event_id = eventmap.get(List_Of_Event_Spinner.getSelectedItem().toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            String domain = getResources().getString(R.string.service_domain);
            Log.i("volley", "domain: " + domain);

            Uri uri1 = Uri.parse(mCurrentPhotoPath);
            final File file1 = new File(uri1.getPath());


            String urlString = domain + "nukkad_meets/create_nukkad_meet";
            try {

                String charset = "UTF-8";
                //File uploadFile1 = new File("/sdcard/myvideo.mp4");

                MultipartUtility multipart = new MultipartUtility(urlString, charset);

//            multipart.addHeaderField("User-Agent", "CodeJava");
//            multipart.addHeaderField("Test-Header", "Header-Value");

                multipart.addFormField("email", Global_Data.GLOvel_USER_EMAIL);
                multipart.addFormField("event_id", event_id);
                multipart.addFormField("meet_in", inDateTime);
                multipart.addFormField("meet_out", outDateTime);
                multipart.addFormField("description", pro_edit.getText().toString());
                multipart.addFormField("latitude", Global_Data.GLOvel_LATITUDE);
                multipart.addFormField("longitude", Global_Data.GLOvel_LONGITUDE);
                multipart.addFormField("address", Global_Data.address);


                if (!mCurrentPhotoPath.equalsIgnoreCase("")) {
                    multipart.addFilePart("image", file1);
                }

                List<String> response1 = multipart.finish();

                Log.v("rht", "SERVER REPLIED:");

                runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                    }
                });

                for (String line : response1) {
                    Log.v("rht", "Line : " + line);
                    response_result = line;
                    runOnUiThread(new Runnable() {
                        public void run() {

                            try {

                                JSONObject obj = new JSONObject(response_result);
                                progressDialog.dismiss();
                                //Successcul message issue on submit. Message should be "Promotional activity submitted successfully"
                                if (obj.getString("message").equalsIgnoreCase("Promotional activity submitted successfully.")) {
                                    Toast toast = Toast.makeText(Promotion_Activity.this, obj.getString("message"),
                                            Toast.LENGTH_SHORT);
                                    outDateTime = "";
                                    inDateTime = "";
                                    mCurrentPhotoPath = "";
                                    outtime.setText("");
                                    outdate.setText("");
                                    intime.setText("");
                                    indate.setText("");
                                    pro_edit.setText("");
                                    List_Of_Event_Spinner.setSelection(0);
                                    events_pick.setImageResource(R.drawable.vector_camera_icon);
                                    in.setVisibility(View.INVISIBLE);
                                    out.setVisibility(View.VISIBLE);

                                    dbvoc.getDeleteTable("promotion_activity");

                                    NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                    notificationManager.cancelAll();


                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    Intent i = new Intent(Promotion_Activity.this, Sales_Dash.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                    finish();


                                } else {
                                    Toast toast = Toast.makeText(Promotion_Activity.this, obj.getString("message"),
                                            Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    outDateTime = "";
                                    inDateTime = "";
                                    mCurrentPhotoPath = "";
                                    outtime.setText("");
                                    outdate.setText("");
                                    intime.setText("");
                                    indate.setText("");
                                    pro_edit.setText("");
                                    List_Of_Event_Spinner.setSelection(0);
                                    events_pick.setImageResource(R.drawable.vector_camera_icon);
                                    in.setVisibility(View.INVISIBLE);
                                    out.setVisibility(View.VISIBLE);

                                }

                                Log.d("My App", obj.toString());

                            } catch (Throwable t) {
                                Log.e("My App", "Could not parse malformed JSON: \"" + response_result + "\"");

                                runOnUiThread(new Runnable() {
                                    public void run() {

                                        progressDialog.dismiss();
                                        Toast toast = Toast.makeText(Promotion_Activity.this, "Something went wrong,Please try again.",
                                                Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();


                                    }
                                });
                            }


                        }
                    });


                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {

                        progressDialog.dismiss();
                        Toast toast = Toast.makeText(Promotion_Activity.this, "Something went wrong,Please try again.",
                                Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
            }


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            runOnUiThread(new Runnable() {
                public void run() {

                    progressDialog.dismiss();
                    // Sub_Dealer_Approval_Stage(context,sub_dealer_approval_stag_val);
                }
            });

        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Promotion_Activity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            progressDialog.setMessage("Sync in Progress, Please Wait");
            progressDialog.setTitle("Smart Anchor App");
            progressDialog.setCancelable(false);
            progressDialog.show();

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    public void local_notification() {
        Intent intent = new Intent(Promotion_Activity.this, MainActivity.class);

        //PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(Promotion_Activity.this);
        b.setOngoing(true);
        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                //.setSmallIcon(R.drawable.anchor_logo)
                .setColor(getResources().getColor(R.color.mdtp_red))
                .setTicker("Anchor")
                .setContentTitle("Promotional Activity")
                .setContentText("You are in Promotional Activity")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
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

    @Override
    protected void onResume() {
        super.onResume();



        List<Promotional_Model> contacts = dbvoc.getPromotional_Activity();
        if (contacts.size() > 0) {

            indt_container.setVisibility(View.VISIBLE);
            outdt_container.setVisibility(View.GONE);
            in.setVisibility(View.VISIBLE);
            out.setVisibility(View.GONE);

            for (Promotional_Model cn : contacts) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(cn.getEvent_id()))) {

                    event_name = cn.getEvent_id();

                }

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(cn.getMeet_in()))) {

                    inDateTime = cn.getMeet_in();

                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                        Date date = sdf.parse(inDateTime);

                        SimpleDateFormat date_form = new SimpleDateFormat("dd-MM-yyyy");
                        String datenew = date_form.format(date);

                        SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm a");
                        String currentDateTimeString = sdf_time.format(date);

                        intime.setText("IN Time : " + currentDateTimeString);
                        indate.setText("IN Date : " + datenew);
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }


                }

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(cn.getDescription()))) {

                    pro_edit.setText(cn.getDescription());

                }

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(cn.getImage_url()))) {

                    mCurrentPhotoPath = cn.getImage_url();
                    try {
                        GlideApp.with(Promotion_Activity.this)
                                .load(Uri.parse(mCurrentPhotoPath))
                                .thumbnail(0.5f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(events_pick);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }



            }

            try
            {
                int spinnerPosition = adapter_events.getPosition(event_name);
                List_Of_Event_Spinner.setSelection(spinnerPosition);
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }



        }


    }
}
