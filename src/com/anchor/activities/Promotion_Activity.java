package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.ArrayList;

public class Promotion_Activity extends Activity {
    //Button retail_sales, institute_sales;
    ImageView new_launch,market_survey,advertisement;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    // private ProgressDialog pDialog;
    ProgressDialog pDialog;
    String response_result = "";
    public DownloadManager downloadManager;
    public long refid;
    public Uri Download_Uri;
    public ArrayList<Long> list = new ArrayList<>();
    LoginDataBaseAdapter loginDataBaseAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_promotional_);

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




//    public BroadcastReceiver onComplete = new BroadcastReceiver() {
//
//        public void onReceive(Context ctxt, Intent intent) {
//
//
//
//
//            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//
//
//            Log.e("IN", "" + referenceId);
//
//            list.remove(referenceId);
//
//
//            if (list.isEmpty())
//            {
//
//                pDialog.hide();
//
//                Log.e("INSIDE", "" + referenceId);
//                NotificationCompat.Builder mBuilder =
//                        new NotificationCompat.Builder(Promotion_Activity.this)
//                                .setSmallIcon(R.drawable.anchor_logo)
//                                .setContentTitle("Anchor")
//                                .setContentText("All Download completed");
//
//
//                NotificationManager notificationManager = (NotificationManager) Promotion_Activity.this.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(455, mBuilder.build());
//
//                Intent intent1 = new Intent(Promotion_Activity.this, Image_Gellary.class);
//                // intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//               startActivity(intent1);
//
//
//            }
//
//
//        }
//
//
//    };

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

}
