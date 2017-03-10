package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.anchor.webservice.ConnectionDetector;

public class About_Metal extends Activity implements OnItemSelectedListener{
    //Button retail_sales, institute_sales;
    TextView phonenext,phonenext2,email1,email2,webtext;
    Bitmap blob_data_logo;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    ImageView thumbnail;
    private WebView mWebvjkljliew;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_metal);

        phonenext = (TextView) findViewById(R.id.phonenext);
        phonenext2 = (TextView) findViewById(R.id.phonenext2);
        email1 = (TextView) findViewById(R.id.email1);
        email2 = (TextView) findViewById(R.id.email2);
        webtext = (TextView) findViewById(R.id.webtext);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);



        cd = new ConnectionDetector(getApplicationContext());



        phonenext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phonenext.getText().toString().trim()));
                startActivity(callIntent );
            }
        });

        webtext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(About_Metal.this,Webview_Activity.class);
                Global_Data.Web_view_back = "About";
                Global_Data.Web_view_url = "http://www.simplelogic.in";
                Global_Data.Web_view_Title = "Simple Logic";
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                startActivity(i);
                finish();


            }
        });

        phonenext2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+phonenext2.getText().toString().trim()));
                startActivity(callIntent );
            }
        });

        email1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email1.getText().toString().trim() });
                    sendIntent.setData(Uri.parse("email@gmail.com"));
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    sendIntent.setType("plain/text");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(sendIntent);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        email2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email2.getText().toString().trim() });
                    sendIntent.setData(Uri.parse("email@gmail.com"));
                    sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    sendIntent.setType("plain/text");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "");
                    startActivity(sendIntent);
                }catch(Exception ex) {
                    ex.printStackTrace();
                }
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
        mTitleTextView.setText("About Metal");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = About_Metal.this.getSharedPreferences("SimpleLogic", 0);

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

        Intent i = new Intent(About_Metal.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

}
