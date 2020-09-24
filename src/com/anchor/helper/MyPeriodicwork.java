package com.anchor.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class MyPeriodicwork extends Worker {


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
            }
        });
        return Result.success();
    }
}
