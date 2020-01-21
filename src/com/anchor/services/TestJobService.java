package com.anchor.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.anchor.activities.JobScheduleMyService;
import com.anchor.helper.JobSchedular_helper;

public class TestJobService extends JobService {
    private static final String TAG = "SyncService";

    @Override
    public boolean onStartJob(JobParameters params) {
        Intent service = new Intent(getApplicationContext(), JobScheduleMyService.class);
        try {
            getApplicationContext().startService(service);
        }
        catch ( Exception IllegalStateException) {
           // intent.putExtra(NEED_FOREGROUND_KEY, true)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                getApplicationContext().startForegroundService(service);
            }
            else {
                getApplicationContext().startService(service);
            }
        }

        JobSchedular_helper.scheduleJob(getApplicationContext()); // reschedule the job
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private void doMyWork() {
        Log.d("JOB","JOB");
    }
}
