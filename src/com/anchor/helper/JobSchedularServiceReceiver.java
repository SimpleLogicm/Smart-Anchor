package com.anchor.helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class JobSchedularServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        JobSchedular_helper.scheduleJob(context);
    }
}
