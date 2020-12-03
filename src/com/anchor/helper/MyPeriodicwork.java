package com.anchor.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.anchor.activities.ChangePassword;
import com.anchor.activities.Check_Null_Value;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.LoginActivity;
import com.anchor.activities.LoginDataBaseAdapter;
import com.anchor.activities.MainActivity;
import com.anchor.activities.R;
import com.anchor.service.LocationServices;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.ACTIVITY_SERVICE;


public class MyPeriodicwork extends Worker {
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    SharedPreferences spf;
    RequestQueue requestQueue;
    DataBaseHelper dbvoc = new DataBaseHelper(getApplicationContext());
    LoginDataBaseAdapter loginDataBaseAdapter;

    public MyPeriodicwork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("Tag","do work working");
       // Toast.makeText(getApplicationContext(), "sgsgsgssggsssggsgssss", Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                cd = new ConnectionDetector(getApplicationContext());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {

                    String user_email = "";
                    String OTP = "";
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("SimpleLogic", 0);

                    try {
                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                            user_email = sp.getString("USER_EMAIL", "");
                        } else {
                            user_email = Global_Data.GLOvel_USER_EMAIL;
                        }
                         OTP = sp.getString("OTP","");

                        Log.d("user","user"+user_email);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }



                    getservice(OTP,user_email);
                    //requestPhoneStatePermission();

                } else {
                    Log.d("Net not working otp","NTP");

                }


            }
        });
        return Result.success();
    }

    private void getservice(String OTP,String User_email) {


        try {

            String domain = getApplicationContext().getResources().getString(R.string.service_domain);
            String url = domain + "users/is_user_valid?email=" + User_email + "&otp=" + OTP;

            Log.i("volley", "url: " + url);

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);

                    try {

                        String response_result = "";
                        if (response.has("result")) {
                            response_result = response.getString("result");
                        } else {
                            response_result = "data";
                        }

                        if (response.has("is_valid")) {

                        }

                        if (!response_result.equalsIgnoreCase("Valid User")) {

                            Toast toast2 = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                            toast2.setGravity(Gravity.CENTER, 0, 0);
                            toast2.show();

                            dbvoc.getDeleteTable("user_email");
                            loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
                            loginDataBaseAdapter = loginDataBaseAdapter.open();

                            loginDataBaseAdapter.insert_user_email(Global_Data.GLOvel_USER_EMAIL, "Logout");

                            WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("otpValidator");
                            Global_Data.context.deleteDatabase("simple_logic.db");


//                            try {
//                                // clearing app data
//                                if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
//                                    ((ActivityManager)getApplicationContext().getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
//                                } else {
//                                    String packageName = getApplicationContext().getPackageName();
//                                    Runtime runtime = Runtime.getRuntime();
//                                    runtime.exec("pm clear "+packageName);
//                                }
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }


                            Context context =  getApplicationContext();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            getApplicationContext().startActivity(i);
                            getApplicationContext().stopService(new Intent(getApplicationContext(), LocationServices.class));
                            ((Activity)Global_Data.context).finishAffinity();
                            ((Activity)Global_Data.context).finish();


                        }
//                        else if (response_result.equalsIgnoreCase("User not registered")) {
//
//
//                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);


                }
            });

            //RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            // queue.add(jsObjRequest);


            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                Log.d("new error", "Setting a new request queue");
            }
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 050000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
