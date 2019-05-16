package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anchor.helper.PrefManager;

public class CustomerServicesActivity extends Activity {

    RelativeLayout productQuality,logiStics,commercialClaim,marketingTool,feedBack;
    private PrefManager prefManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_services);

        productQuality=(RelativeLayout)findViewById(R.id.product_quality);
        logiStics = (RelativeLayout) findViewById(R.id.logistics);
        commercialClaim = (RelativeLayout) findViewById(R.id.commercial_claim);
        marketingTool = (RelativeLayout) findViewById(R.id.marketing_support);
        feedBack = (RelativeLayout) findViewById(R.id.feedback);
        prefManager = new PrefManager(this);

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
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText("Customer Services");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = CustomerServicesActivity.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//		}
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
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}


    }

    public void oncommercial_claimButtonClicked(View view) {
        // startActivity(new Intent(this, ComplaintsActivity.class));

        prefManager.setCustomer_service_type("Claims");
        startActivity(new Intent(this, Previous_Customer_Services.class));
    }

    public void onCproduct_qualityButtonClicked(View view) {
        // startActivity(new Intent(this, ComplaintsActivity.class));

        prefManager.setCustomer_service_type("Product Quality");
        startActivity(new Intent(this, Previous_Customer_Services.class));
    }

    public void onFeedbackButtonClicked(View view) {
        //startActivity(new Intent(this, FeedBackActivity.class));

        prefManager.setCustomer_service_type("Feedbacks");
        startActivity(new Intent(this, Previous_Customer_Services.class));
    }

    public void onlogisticsButtonClicked(View view) {
        //  startActivity(new Intent(this, ClaimsActivity.class));

        prefManager.setCustomer_service_type("Logistics");
        startActivity(new Intent(this, Previous_Customer_Services.class));
    }

    public void onRmarketing_supportButtonClicked(View view) {
        //  startActivity(new Intent(this, ClaimsActivity.class));

        prefManager.setCustomer_service_type("Marketing Tool");
        startActivity(new Intent(this, Previous_Customer_Services.class));
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

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }


}

