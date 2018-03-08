package cpm.simplelogic.helper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.LoginDataBaseAdapter;

/**
 * Created by admin on 08-03-2018.
 */


public class OnClearFromRecentService extends Service {
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("ClearFromRecentService", "Service Started");
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ClearFromRecentService", "Service Destroyed");
        dbvoc.getDeleteTable("user_email");

        loginDataBaseAdapter.insert_user_email(Global_Data.GLOvel_USER_EMAIL,"Logout");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("ClearFromRecentService", "END");
        //Code here
        stopSelf();
    }
}
