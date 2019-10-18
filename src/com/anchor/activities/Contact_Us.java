package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.webservice.ConnectionDetector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class Contact_Us extends Activity implements OnItemSelectedListener{
    //Button retail_sales, institute_sales;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    String url = "";
    TextView phonenext,phonenext2,email1,email2,webtext,title,header,sub_heading,address;
    Bitmap blob_data_logo;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    ImageView thumbnail;
    private WebView mWebview ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);

        title = findViewById(R.id.Metal_Header);
        header = findViewById(R.id.Metal_Header_descri);
        sub_heading = findViewById(R.id.title);
        address = findViewById(R.id.discriptionnew);

        phonenext = findViewById(R.id.phonenext);
        phonenext2 = findViewById(R.id.phonenext2);
        email1 = findViewById(R.id.email1);
        email2 = findViewById(R.id.email2);
        webtext = findViewById(R.id.webtext);
        thumbnail = findViewById(R.id.thumbnail);

        SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
        String logostr=spf1.getString("logo_data", "");

        if(logostr.length()>0){
            byte[] decodedString = Base64.decode(logostr, Base64.DEFAULT);
            blob_data_logo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            thumbnail.setImageBitmap(blob_data_logo);
        }


        List<Local_Data> contacts1 = dbvoc.getL1_Contact();

        if(contacts1.size() > 0)
        {
            for (Local_Data cn : contacts1)
            {
                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getTitle()))
                {
                    title.setText(cn.getTitle());
                }
                else
                {
                    title.setVisibility(View.GONE);
                }

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getHeading()))
                {
                    header.setText(cn.getHeading());
                }
                else
                {
                    header.setVisibility(View.GONE);
                }

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getSub_Heading()))
                {
                    sub_heading.setText(cn.getSub_Heading());
                }
                else
                {
                    sub_heading.setVisibility(View.GONE);
                }

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getL1_Address()))
                {
                    String addressn = "<b>" + "Address: " + "</b> " +cn.getL1_Address();
                    address.setText(Html.fromHtml(addressn));
                }
                else
                {
                    address.setVisibility(View.GONE);
                }


                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getL1_phone1()))
                {
                    phonenext.setText(cn.getL1_phone1());
                }
                else
                {
                    phonenext.setText("");
                }


                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getL1_phone2()))
                {
                    phonenext2.setText(cn.getL1_phone2());
                }
                else
                {
                    phonenext2.setText("");
                }

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getL1_email_id1()))
                {
                    email1.setText(cn.getL1_email_id1());
                }
                else
                {
                    email1.setText("");
                }


                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getL1_email_id2()))
                {
                    email2.setText(cn.getL1_email_id2());
                }
                else
                {
                    email2.setText("");
                }

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getL1_website()))
                {
                    //webtext.setText(cn.getL1_website());

                    url = cn.getL1_website();
                }
                else
                {
                    webtext.setVisibility(View.GONE);
                    url = "";
                }



            }
        }
        else
        {
            Toast.makeText(this, "Contact detail not found.", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Contact_Us.this,MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            startActivity(i);
            finish();
        }


        cd = new ConnectionDetector(getApplicationContext());



        phonenext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPhoneCallPermission(phonenext.getText().toString().trim());

            }
        });

        webtext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Global_Data.Web_view_back = "Contact";

                if(url.trim().indexOf(":") > 0)
                {
                    Global_Data.Web_view_url = url.trim();
                }
                else
                {
                  //  myWebView.loadUrl("http://"+Global_Data.Web_view_url);
                    Global_Data.Web_view_url = "http://"+url.trim()+"/";
                }


                Global_Data.Web_view_Title = title.getText().toString().trim();
                Intent i = new Intent(Contact_Us.this,Webview_Activity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                startActivity(i);
                finish();


            }
        });

        phonenext2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPhoneCallPermission(phonenext2.getText().toString().trim());
            }
        });

        email1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", email1.getText().toString().trim(), null));
                    // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
                    startActivity(Intent.createChooser(emailIntent, null));
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
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto", email2.getText().toString().trim(), null));
                    // emailIntent.putExtra(Intent.EXTRA_SUBJECT, "This is my subject text");
                    startActivity(Intent.createChooser(emailIntent, null));
                }catch(Exception ex) {
                    ex.printStackTrace();
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
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Contact Us");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Contact_Us.this.getSharedPreferences("SimpleLogic", 0);

//	       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	       	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			}

            try
            {
                int target  = Math.round(sp.getFloat("Target",0));
                int achieved  = Math.round(sp.getFloat("Achived",0));
                Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
                if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
                {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
                }else
                {
                    int age = Math.round(age_float);

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
        }catch(Exception ex){ex.printStackTrace();}


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

        Intent i = new Intent(Contact_Us.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }
    /**
     * Requesting GPS permission
     * This uses single permission model from dexter
     * Once the permission granted, opens the camera
     * On permanent denial opens settings dialog
     */
    private void requestPhoneCallPermission(final String mobile_number) {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + mobile_number));
                        startActivity(callIntent);

                        return;

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Contact_Us.this);
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}
