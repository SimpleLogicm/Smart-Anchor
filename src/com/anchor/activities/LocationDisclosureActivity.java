package com.anchor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

public class LocationDisclosureActivity extends Activity {
    ImageView locationDisclosure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();
        setContentView(R.layout.activity_locationdisclosure);

        locationDisclosure = findViewById(R.id.close_button);

        locationDisclosure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentn = new Intent(LocationDisclosureActivity.this, MainActivity.class);
                startActivity(intentn);
                //((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });

//        new CountDownTimer(5000, 1000) { // 5000 = 5 sec
//
//            public void onTick(long millisUntilFinished) {
//            }
//
//            public void onFinish() {
//                Intent intentn = new Intent(LocationDisclosureActivity.this, MainActivity.class);
//                startActivity(intentn);
//                //((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
//                //locationDisclosure.setImageResource(R.drawable.locationdisclosure_smartanchor);
//            }
//        }.start();

    }
}
