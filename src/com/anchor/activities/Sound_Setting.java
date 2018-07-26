package com.anchor.activities;

/**
 * Created by sujit on 2/27/2017.
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class Sound_Setting extends Activity implements OnItemSelectedListener {
    Switch switch_appsound;
    Boolean status_addmore = false;
    Button browse_btn;
    int music_column_index;
    MediaPlayer mp;
    Cursor cursor;
    int count;
    ArrayList<String> song_result = new ArrayList<String>();
    private int currentSongIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_setting);
        cursor = null;
        switch_appsound = (Switch) findViewById(R.id.switch_appsound);
        browse_btn = (Button) findViewById(R.id.browse_btn);
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
            mTitleTextView.setText("Setting");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Sound_Setting.this.getSharedPreferences("SimpleLogic", 0);

            mp = new MediaPlayer();

            browse_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Sound_Setting.this, Sound_Act.class));
                }
            });

            switch_appsound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                    if (bChecked) {
                        browse_btn.setVisibility(View.VISIBLE);
                        status_addmore = false;
                        SharedPreferences snd = Sound_Setting.this.getSharedPreferences("SimpleLogic", 0);
                        SharedPreferences.Editor edt_snd = snd.edit();
                        edt_snd.putBoolean("var_addmore", status_addmore);
                        edt_snd.commit();
                        //textView.setText(switchOn);
                    } else {
                        browse_btn.setVisibility(View.INVISIBLE);
                        status_addmore = true;
                        SharedPreferences snd = Sound_Setting.this.getSharedPreferences("SimpleLogic", 0);
                        SharedPreferences.Editor edt_snd = snd.edit();
                        edt_snd.putBoolean("var_addmore", status_addmore);
                        edt_snd.commit();
                        //textView.setText(switchOff);
                    }
                }
            });

            SharedPreferences spf1 = Sound_Setting.this.getSharedPreferences("SimpleLogic", 0);
            Global_Data.app_sound = spf1.getBoolean("var_addmore", false);

            if (Global_Data.app_sound == false) {

                switch_appsound.setChecked(true);

            } else {

                switch_appsound.setChecked(false);
            }

            try {
                int target = (int) Math.round(sp.getFloat("Target", 0));
                int achieved = (int) Math.round(sp.getFloat("Achived", 0));
                Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
                if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                    int age = (int) Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
                } else {
                    int age = (int) Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
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
        Intent i = new Intent(Sound_Setting.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                try {
                    String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
                    String[] projection = {
                            MediaStore.Audio.Media.TITLE,
                            MediaStore.Audio.Media.ARTIST,
                            MediaStore.Audio.Media.DATA,
                            MediaStore.Audio.Media.DISPLAY_NAME,
                            MediaStore.Audio.Media.DURATION};
                    final String sortOrder = MediaStore.Audio.AudioColumns.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
                    // Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    //Cursor cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);

                    try {
                        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        //cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);
//                        cursor = managedQuery(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                                projection, null, null, null);

//                        int music_column_index = cursor
//                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                        cursor.moveToNext();
                        String filename = cursor.getString(music_column_index);
                        Log.d("", "" + filename);
                    } catch (Exception e) {
                        Log.e("TAG", e.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String _getRealPathFromURI(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
//while i am getting song from device, playing all songs but i want to play song which selected