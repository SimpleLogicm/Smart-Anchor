package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.App.AppController;
import com.anchor.helper.FileDownloader;
import com.anchor.webservice.ConnectionDetector;
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
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Promotion_Activity extends Activity {

    ImageView new_launch,market_survey,advertisement;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_promotional_);
        rpo_chhose_file = findViewById(R.id.rpo_chhose_file);

        rpo_chhose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestStoragePermission();
            }
        });

        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Promotional Activity");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Promotion_Activity.this.getSharedPreferences("SimpleLogic", 0);

            try
            {
                int target  = (int) Math.round(sp.getFloat("Target",0));
                int achieved  = (int) Math.round(sp.getFloat("Achived",0));
                Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
                if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
                {
                    int age = (int) Math.round(age_float);

                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
                }else
                {
                    int age = (int) Math.round(age_float);

                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
                }

            }catch(Exception ex){ex.printStackTrace();}
            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//	       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}


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

        Intent i = new Intent(Promotion_Activity.this,Sales_Dash.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {


        super.onDestroy();

        // unregisterReceiver(onComplete);



    }


    private class imagesync extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {


                //downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                SharedPreferences sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
                String device_id = sp.getString("devid", "");

                String domain = "";

                domain = getResources().getString(R.string.service_domain);

                Log.d("Server url","Server url"+domain+"new_launches?imei_no="+device_id);

                StringRequest stringRequest = null;
                stringRequest = new StringRequest(domain+"new_launches?imei_no="+device_id,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //showJSON(response);
                                // Log.d("jV", "JV" + response);
                                Log.d("jV", "JV length" + response.length());
                                // JSONObject person = (JSONObject) (response);
                                try {
                                    JSONObject json = new JSONObject(new JSONTokener(response));
                                    try{


                                        if(json.has("result"))
                                        {
                                            response_result = json.getString("result");
                                        }
                                        else
                                        {
                                            response_result = "data";
                                        }


                                        if(response_result.equalsIgnoreCase("No Data Found")) {

                                            Promotion_Activity.this.runOnUiThread(new Runnable() {
                                                public void run() {

                                                    pDialog.hide();

                                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();

                                                    Intent launch = new Intent(Promotion_Activity.this,MainActivity.class);
                                                    startActivity(launch);
                                                    finish();
                                                }
                                            });


                                        }
                                        else
                                        if(response_result.equalsIgnoreCase("Device not registered")) {

                                            Promotion_Activity.this.runOnUiThread(new Runnable() {
                                                public void run() {

                                                    pDialog.hide();

                                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                                    toast.show();

                                                    Intent launch = new Intent(Promotion_Activity.this,MainActivity.class);
                                                    startActivity(launch);
                                                    finish();
                                                }
                                            });



                                        }
                                        else {

                                            JSONArray launches = json.getJSONArray("launches");

                                            Log.i("volley", "response reg launches Length: " + launches.length());

                                            Log.d("users", "launches" + launches.toString());


                                            if(launches.length() > 0)
                                            {
                                                dbvoc.getDeleteTable("new_launches_new");

                                                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                                                File dir = new File(extStorageDirectory, "Anchor_NewLaunch");

                                                if (dir.isDirectory())
                                                {
                                                    String[] children = dir.list();
                                                    for (int j = 0; j < children.length; j++)
                                                    {
                                                        new File(dir, children[j]).delete();
                                                    }
                                                }
                                            }
                                            for (int i = 0; i < launches.length(); i++) {

                                                JSONObject object = launches.getJSONObject(i);
//                                        Image image = new Image();
//                                        image.setName(object.getString("name"));
//                                        image.setLarge(object.getString("large"));
//                                        image.setType(object.getString("type"));
//                                        image.setTimestamp(object.getString("date"));
//
//                                        images.add(image);


                                                String fileName = object.getString("large").trim().substring( object.getString("large").trim().lastIndexOf('/')+1, object.getString("large").trim().length());

                                                if(fileName.indexOf("?") > 0)
                                                {
                                                    String [] file_name_array = fileName.split("\\?");
                                                    fileName = file_name_array[0];
                                                }

                                                fileName = fileName.replaceAll("[%,]","");

                                                String path = Environment.getExternalStorageDirectory().toString();
                                                File file = new File(path,"Anchor_NewLaunch"+"/"+fileName);


                                                Global_Data.Download_hashmap.put(fileName,Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(object.getString("large").trim()));
                                                // dbvoc.TABLE_CREATE_NEW_LAUNCHES_NEW_CHECK("file:" + file.getAbsolutePath());


                                                loginDataBaseAdapter.insertNewLaunchesNew(object.getString("name"),"file:" + file.getAbsolutePath(),object.getString("type"),object.getString("date"));

//                                        if(!file.exists())
//                                        {
//                                            file.mkdir();
//                                        }
//
//                                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(object.getString("large").trim())));
//                                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                                        request.setAllowedOverRoaming(false);
////                                           // request.setTitle("GadgetSaint Downloading " + "Sample_" + i + ".png");
////                                            //request.setDescription("Downloading " + "Sample_" + i + ".png");
//                                        request.setVisibleInDownloadsUi(false);
//                                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, "Anchor_NewLaunch"+"/"+fileName);
//
//                                        refid = downloadManager.enqueue(request);
//
//
//                                        Log.e("OUTNM", "" + refid);
//
//                                        list.add(refid);


                                            }

                                            if(Global_Data.Download_hashmap.size() > 0)
                                            {
                                                FileDownloader.downloadFile(Promotion_Activity.this,pDialog,"");
                                            }

                                            //Intent launch = new Intent(context,Youtube_Player_Activity.class);
                                            //startActivity(launch);



                                            // mAdapter.notifyDataSetChanged();
                                            //finish();

                                        }

                                        //  finish();
                                        // }

                                        // output.setText(data);
                                    }catch(JSONException e){e.printStackTrace();

                                        Promotion_Activity.this.runOnUiThread(new Runnable() {
                                            public void run() {

                                                Toast toast = Toast.makeText(Promotion_Activity.this,
                                                        "Service Error",
                                                        Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                                Intent launch = new Intent(Promotion_Activity.this,MainActivity.class);
                                                startActivity(launch);
                                                finish();

                                                pDialog.hide();
                                            }
                                        });

                                    }



                                } catch (JSONException e) {

                                    e.printStackTrace();
                                    Promotion_Activity.this.runOnUiThread(new Runnable() {
                                        public void run() {


                                            //  finish();
                                            pDialog.dismiss();
                                        }
                                    });

                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(final VolleyError error) {
                                //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                                Promotion_Activity.this.runOnUiThread(new Runnable() {
                                    public void run() {


                                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(Image_Gellary.this,
//                                    "Network Error",
//                                    Toast.LENGTH_LONG).show();


                                            Toast toast = Toast.makeText(Promotion_Activity.this,
                                                    "Network Error",
                                                    Toast.LENGTH_LONG);
                                            toast.show();
                                        } else if (error instanceof AuthFailureError) {


                                            Toast toast = Toast.makeText(Promotion_Activity.this,
                                                    "Server AuthFailureError  Error",
                                                    Toast.LENGTH_LONG);
                                            toast.show();
                                        } else if (error instanceof ServerError) {

                                            Toast toast = Toast.makeText(Promotion_Activity.this,
                                                    "Server   Error",
                                                    Toast.LENGTH_LONG);
                                            toast.show();
                                        } else if (error instanceof NetworkError) {

                                            Toast toast = Toast.makeText(Promotion_Activity.this,
                                                    "Network   Error",
                                                    Toast.LENGTH_LONG);
                                            toast.show();
                                        } else if (error instanceof ParseError) {


                                            Toast toast = Toast.makeText(Promotion_Activity.this,
                                                    "ParseError   Error",
                                                    Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                        else
                                        {
                                            // Toast.makeText(Image_Gellary.this, error.getMessage(), Toast.LENGTH_LONG).show();

                                            Toast toast = Toast.makeText(Promotion_Activity.this, error.getMessage(), Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                        }
                                        Intent launch = new Intent(Promotion_Activity.this,MainActivity.class);
                                        startActivity(launch);
                                        finish();
                                        pDialog.dismiss();
                                    }
                                });


                                // finish();
                            }
                        });

                RequestQueue requestQueue = Volley.newRequestQueue(Promotion_Activity.this);

                int socketTimeout = 300000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                stringRequest.setRetryPolicy(policy);
                // requestQueue.se
                //requestQueue.add(jsObjRequest);
                stringRequest.setShouldCache(false);
                requestQueue.getCache().clear();
                //requestQueue.add(stringRequest);
                AppController.getInstance().addToRequestQueue(stringRequest);

            } catch (Exception e) {

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
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

                            if(B_flag == true)
                            {
                                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Promotion_Activity.this);

                                builder.setTitle("Add Photo!");

                                builder.setItems(options, new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialog, int item) {

                                        if (options[item].equals("Take Photo"))

                                        {
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

                                        }
                                        else
                                        if (options[item].equals("Choose from Gallery"))
                                        {

                                            // image_check = "gallery";
                                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                            startActivityForResult(intent, 2);


                                        } else if (options[item].equals("Cancel")) {

                                            dialog.dismiss();

                                        }

                                    }

                                });

                                builder.show();

                            }
                            else
                            {
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
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Anchor");

        if(!storageDir.exists())
        {
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

//       if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                previewCapturedImage();
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // user cancelled Image capture
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                // failed to capture image
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {


//            try {
//                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            // Bundle extras = data.getExtras();
            //  Bitmap imageBitmap = (Bitmap) extras.get("data");

            try {

                dbvoc.updateORDER_order_image(mCurrentPhotoPath,Global_Data.GLObalOrder_id);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //get_icon.setImageBitmap(imageBitmap);
        }
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                mCurrentPhotoPath = "file:" + c.getString(columnIndex);

                dbvoc.updateORDER_order_image(mCurrentPhotoPath,Global_Data.GLObalOrder_id);

                pictureImagePath_new = c.getString(columnIndex);

                c.close();

//                String imageFileName = "Anchor";
//                File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Anchor");
//
//                if (!storageDir.exists()) {
//                    storageDir.mkdir();
//                }
//
//                copyFileOrDirectory(pictureImagePath_new,storageDir.toString());
                //new Expenses.LongOperation().execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

    }

}
