package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.anchor.helper.MyPeriodicwork;
import com.anchor.services.getServices;
import com.anchor.webservice.ConnectionDetector;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 06-09-2016.
 */
public class AllOrders_Sync  extends Activity  {

    Boolean isInternetPresent = false;
    LoginDataBaseAdapter loginDataBaseAdapter;
    ConnectionDetector cd;
    Button angry_btn1,angry_btn2,angry_btn3,angry_btn4;
    TextView lastsyncon,Totalsosync;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private String Current_Date = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.order_syncs);

        Global_Data.context = AllOrders_Sync.this;
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                MyPeriodicwork.class, 15, TimeUnit.MINUTES
        ).addTag("otpValidator").build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("Work",
                ExistingPeriodicWorkPolicy.REPLACE,periodicWorkRequest);

        lastsyncon= findViewById(R.id.lastsyncon);
        Totalsosync= findViewById(R.id.Totalsosync);
        angry_btn1= findViewById(R.id.angry_btn1);
        angry_btn2= findViewById(R.id.angry_btn2);
        angry_btn3= findViewById(R.id.angry_btn3);
        angry_btn4= findViewById(R.id.angry_btn4);

        cd = new ConnectionDetector(AllOrders_Sync.this);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = sdf.format(c.getTime());
        Current_Date = sdf.format(c.getTime());

        List<Local_Data> contacts1 = dbvoc.getOrder_details();

        for (Local_Data cn : contacts1) {
            lastsyncon.setText("Last Sync Date : "+cn.getsync_time());
            Totalsosync.setText("Last Sync Time  : "+cn.gettotal_sync());
        }

        angry_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();

                if (Global_Data.isNetworkAvailable(AllOrders_Sync.this))
                {
//                    AlertDialog alertDialog = new AlertDialog.Builder(AllOrders_Sync.this).create(); //Read Update
//                    alertDialog.setTitle("Confirmation");
//                    alertDialog.setMessage("Are you sure want to upload orders ?");
//                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//
//
//                            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                            } else
//                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(CaptureSignature.this);
//								   insertOrderAsyncTask.execute();

//                            List<Local_Data> contacts = dbvoc.GetAllOrders("Secondary Sales / Retail Sales");
//
//                            if(contacts.size() <=0)
//                            {

//                                Toast toast = Toast.makeText(getApplicationContext(),"No order found for sync",Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//                            }
//                            else
//                            {
                                getServices.SYNCORDER_AllOrders(AllOrders_Sync.this);
                            //}



                            //lastsyncon.setText("Last Sync On : "+cn.getsync_time());
                            //lastsyncon.setText("Total Orders Synced : "+cn.gettotal_sync());


//                        }
//                    });
//
//                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // TODO Auto-generated method stub
//                            dialog.cancel();
//                        }
//                    });
//
//                    alertDialog.setCancelable(false);
//                    alertDialog.show();
                }
                else
                {

                    Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        angry_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();

                if (Global_Data.isNetworkAvailable(AllOrders_Sync.this))
                {

                    getServices.sendRequestnew(AllOrders_Sync.this, "Please wait...");
                    //getServices.sendRequest(AllOrders_Sync.this);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        angry_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();

                if (Global_Data.isNetworkAvailable(AllOrders_Sync.this))
                {

                    getServices.SyncDataToServercommon(AllOrders_Sync.this);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        angry_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();

                if (Global_Data.isNetworkAvailable(AllOrders_Sync.this))
                {

                    getServices.SyncDataToServerCustomer(AllOrders_Sync.this);
                }
                else
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });
        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Sync Data");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            todaysTarget.setVisibility(View.INVISIBLE);
            ImageView H_LOGO = mCustomView.findViewById(R.id.Header_logo);
            SharedPreferences sp = AllOrders_Sync.this.getSharedPreferences("SimpleLogic", 0);

            H_LOGO.setImageResource(R.drawable.video_imagenew);
            H_LOGO.setVisibility(View.INVISIBLE);

//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//			todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//			todaysTarget.setText("Today's Target Acheived");
//		}

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
        // TODO Auto-generated method stub
        // super.onBackPressed();

        Intent i=new Intent(AllOrders_Sync.this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }
}
