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
import android.widget.TextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

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

//                currentSongIndex = data.getExtras().getInt("songIndex");
//                playSong(currentSongIndex);

//                //get song
//                Song playSong = songs.get(songPosn);
////get id
//                long currSong = playSong.getID();
//set uri
//                Uri trackUri = ContentUris.withAppendedId(
//                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                        currSong);
//                int music_column_index = cursor
//                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
//                cursor.moveToPosition(count);
                //String id = cursor.getString(music_column_index);
//                music_column_index = cursor
//                        .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                // musiccursor.moveToPosition(position);

                //System.gc();
//                int music_column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//                cursor.moveToPosition(count);
//                String filename = cursor.getString(music_column_index);
//
//                Log.d("","ddd"+filename);
//
//                try {
//                    if (mp.isPlaying()) {
//                        mp.reset();
//                    }
//                    mp.setDataSource(filename);
//                    mp.prepare();
//                    mp.start();
//                } catch (Exception e) {
//
//                }

                //mp= new MediaPlayer();
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
                        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        //cursor = getContentResolver().query(uri, projection, selection, null, sortOrder);
//                        cursor = managedQuery(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                                projection, null, null, null);

//                        int music_column_index = cursor
//                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                        cursor.moveToNext();
                        String filename = cursor.getString(music_column_index);
                        Log.d("", "" + filename);
//                        if( cursor != null) {
//                            cursor.moveToFirst();
//                            while (!cursor.isAfterLast()) {
//                                int music_column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
//
//                                String title = cursor.getString(0);
//                                String artist = cursor.getString(1);
//                                String path = cursor.getString(2);
//                                String displayName = cursor.getString(3);
//                                String songDuration = cursor.getString(4);
//                                Log.d("","audio name"+path+""+title+music_column_index);
//                                cursor.moveToNext();
//                                if (path != null && path.endsWith(".mp3")) {
//                                    String filename = cursor.getString(music_column_index);
//                                    try {
//                                        if (mp.isPlaying()) {
//                                            mp.reset();
//                                        }
//                                        mp.setDataSource(filename);
//                                        mp.prepare();
//                                        mp.start();
//                                    } catch (Exception e) {
//
//                                    }
//
////                                    //mp3Files.add(path);
////                                    //cursor.moveToPosition(1);
////                                    String filename = cursor.getString(music_column_index);
////                                    Log.d("","audio name"+filename);
////                                    mp = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/Audio/"+filename));
////                                    mp.start();
//                                }
//                           }
//                       }
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

//    public void  playSong(int songIndex){
//        // Play song
//        try {
//            mp.reset();
//            mp.setDataSource(songsList.get(songIndex).get("songPath"));
//            mp.prepare();
//            mp.start();
//            // Displaying Song title
////            String songTitle = songsList.get(songIndex).get("songTitle");
////            songTitleLabel.setText(songTitle);
//
//            // Changing Button Image to pause image
//            //btnPlay.setImageResource(R.drawable.btn_pause);
//
//            // set Progress bar values
////            songProgressBar.setProgress(0);
////            songProgressBar.setMax(100);
////
////            // Updating progress bar
////            updateProgressBar();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void init_phone_music_grid() {
        System.gc();
        String[] proj = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE};
        cursor = managedQuery(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                proj, null, null, null);
        music_column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        count = cursor.getCount();
//        musiclist = (ListView) findViewById(R.id.PhoneMusicList);
//        musiclist.setAdapter(new MusicAdapter(getApplicationContext()));
//
//        musiclist.setOnItemClickListener(musicgridlistener);
        mp = new MediaPlayer();

    }
}
//while i am getting song from device, playing all songs but i want to play song which selected