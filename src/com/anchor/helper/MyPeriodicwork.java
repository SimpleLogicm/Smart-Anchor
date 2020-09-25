package com.anchor.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.anchor.activities.ChangePassword;
import com.anchor.activities.LoginActivity;
import com.anchor.activities.R;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MyPeriodicwork extends Worker {
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    SharedPreferences spf;

    public MyPeriodicwork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("Tag", "do work working");
        // Toast.makeText(getApplicationContext(), "sgsgsgssggsssggsgssss", Toast.LENGTH_LONG).show();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), "Something", Toast.LENGTH_SHORT);
                toast.show();
                cd = new ConnectionDetector(getApplicationContext());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    SharedPreferences spf = getApplicationContext().getSharedPreferences("SimpleLogic", 0);
                    String OTP = spf.getString("OTP", null);
                    String userEmail = spf.getString("USER_EMAIL", null);

                    getservice(OTP, userEmail);
                    //requestPhoneStatePermission();

                }


            }
        });
        return Result.success();
    }

    private void getservice(String OTP, String userEmail) {

        String domain = getApplicationContext().getResources().getString(R.string.service_domain);
        String JSON_URL = domain + "users/is_user_valid?email=" + userEmail + "&otp=" + OTP;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            if (response.contains("Invalid User")){
                                Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_SHORT).show();
                            }else if(response.contains("User not found.")){
                                Toast.makeText(getApplicationContext(), "User not found.", Toast.LENGTH_SHORT).show();
                            }else if(response.contains("OTP not found.")){
                                Toast.makeText(getApplicationContext(), "OTP not found.", Toast.LENGTH_SHORT).show();
                            }else {
                                JSONObject obj = new JSONObject(response);
                                String result= obj.getString("result");
                                String isvalidflag=obj.getString("is_valid");

                            }




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }


}

