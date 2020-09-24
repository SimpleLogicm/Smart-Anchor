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
import com.anchor.webservice.ConnectionDetector;


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
        Log.i("Tag","do work working");
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
                    String OTP = spf.getString("OTP",null);

                    getservice(OTP);
                    //requestPhoneStatePermission();

                } else {
                    // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
                    Toast toast2 = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast2.setGravity(Gravity.CENTER, 0, 0);
                    toast2.show();
                }


            }
        });
        return Result.success();
    }

    private void getservice(String OTP) {



    }
}
