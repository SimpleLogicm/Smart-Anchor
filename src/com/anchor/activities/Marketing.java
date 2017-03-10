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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.webservice.ConnectionDetector;

public class Marketing extends Activity implements OnItemSelectedListener{
    //Button retail_sales, institute_sales;
    ImageView new_launch,market_survey,advertisement;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);

        new_launch = (ImageView) findViewById(R.id.new_launch);
        advertisement = (ImageView) findViewById(R.id.advertisement);
        market_survey = (ImageView) findViewById(R.id.market_survey);

        cd = new ConnectionDetector(getApplicationContext());

        new_launch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent)
                {

                    Intent launch = new Intent(Marketing.this,Image_Gellary.class);
                    startActivity(launch);

                    //getServices.GetNewLaunch_Data(MainActivity.this);
                }
                else
                {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    // showAlertDialog(AndroidDetectInternetConnectionActivity.this, "No Internet Connection",
                    //        "You don't have internet connection.", false);
                   // Toast.makeText(getApplicationContext(),"You don't have internet connection",Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        advertisement.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent)
                {

                    Intent launch = new Intent(Marketing.this,Video_Main_List.class);
                    startActivity(launch);


                    //getServices.GetNewLaunch_Data(MainActivity.this);
                }
                else
                {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    // showAlertDialog(AndroidDetectInternetConnectionActivity.this, "No Internet Connection",
                    //        "You don't have internet connection.", false);
                    //Toast.makeText(getApplicationContext(),"You don't have internet connection",Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });

        market_survey.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getApplicationContext(),"Coming Soon with next version",Toast.LENGTH_LONG).show();

//                isInternetPresent = cd.isConnectingToInternet();
//                if (isInternetPresent)
//                {

                    Intent map = new Intent(Marketing.this,Survey_Home.class);
                    startActivity(map);
                    finish();
//                }
//                else
//                {
//
//                    Toast.makeText(getApplicationContext(),"You don't have internet connection",Toast.LENGTH_LONG).show();
//                }

            }
        });





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
        mTitleTextView.setText("Marketing");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Marketing.this.getSharedPreferences("SimpleLogic", 0);

//	       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	       	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			}

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
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                               long arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
        //super.onBackPressed();

        Intent i = new Intent(Marketing.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

}
