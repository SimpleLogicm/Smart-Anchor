package com.anchor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

public class BaseActivity extends Activity {

//    public static final long DISCONNECT_TIMEOUT = 300000; // 5 min = 5 * 60 * 1000 ms
	
	//public static final long DISCONNECT_TIMEOUT = 6000; //  == > 5 sec
	
	//public static final long DISCONNECT_TIMEOUT = 72000; //  == > 1 min
	
	public static final long DISCONNECT_TIMEOUT = 1080000; //  == > 15 mins

  
    private Handler disconnectHandler = new Handler(){
        public void handleMessage(Message msg) {
        	
        }
    };

    private Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {
            // Perform any required operation on disconnect
        	
        //	Toast.makeText(getApplicationContext(), "Session timeout", Toast.LENGTH_LONG).show();

            Toast toast = Toast.makeText(getApplicationContext(), "Session timeout", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        	finish();
			Intent i=new Intent(getApplicationContext(), LoginActivity.class);
		    // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			startActivity(i);
        }
    };

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }
}