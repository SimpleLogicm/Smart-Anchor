package com.anchor.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by vinod on 27-10-2016.
 */

public class Webview_Activity extends Activity {
    private WebView myWebView;
    ImageButton imageb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        myWebView = findViewById(R.id.webView1);
        imageb = findViewById(R.id.imageb);
        // Configure related browser settings

        final ProgressDialog pd = new ProgressDialog(Webview_Activity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pd.setMessage("Please wait web page loading....");
        pd.setTitle(Global_Data.Web_view_Title);
        pd.setCancelable(true);

        pd.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pd.dismiss();

                if(Global_Data.Web_view_back.equalsIgnoreCase("About"))
                {
                    Intent i = new Intent(Webview_Activity.this,About_Metal.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(Webview_Activity.this,Contact_Us.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(i);
                    finish();
                }

            }
        });

        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.getSettings().setBuiltInZoomControls(true);
        // Configure the client to use when opening URLs
        myWebView.setWebViewClient(new MyBrowser());
        // Load the initial URL

        myWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(Webview_Activity.this, description, Toast.LENGTH_SHORT).show();

                if(Global_Data.Web_view_back.equalsIgnoreCase("About"))
                {
                    Intent i = new Intent(Webview_Activity.this,About_Metal.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(Webview_Activity.this,Contact_Us.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)

            {
                pd.show();
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                pd.dismiss();

                String webUrl = myWebView.getUrl();

            }

        });



            myWebView.loadUrl(Global_Data.Web_view_url);



        imageb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Global_Data.Web_view_back.equalsIgnoreCase("About"))
                {
                    Intent i = new Intent(Webview_Activity.this,About_Metal.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i = new Intent(Webview_Activity.this,Contact_Us.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

    // Manages the behavior when URLs are loaded
    private class MyBrowser extends WebViewClient {
        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }



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

        if(Global_Data.Web_view_back.equalsIgnoreCase("About"))
        {
            Intent i = new Intent(Webview_Activity.this,About_Metal.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            startActivity(i);
            finish();
        }
        else
        {
            Intent i = new Intent(Webview_Activity.this,Contact_Us.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            startActivity(i);
            finish();
        }
    }
}