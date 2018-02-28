package com.anchor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

public class SubDealer_Home extends Activity {

    ImageView add_new_dealer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_dealer__home);

        add_new_dealer = (ImageView)findViewById(R.id.add_new_dealer);
        add_new_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SubDealer_Detail1.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();


            }
        });
    }

}
