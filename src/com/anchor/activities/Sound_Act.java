package com.anchor.activities;

/**
 * Created by sujit on 3/6/2017.
 */

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Sound_Act extends Activity {
    ListView musiclist;
    Cursor musiccursor;
    int music_column_index;
    int count;
    MediaPlayer mMediaPlayer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sound_Act.this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sound_act);

        init_phone_music_grid();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//        }
    }

    private void init_phone_music_grid() {
        System.gc();

        ContentResolver cr = this.getContentResolver();

        Uri uri = MediaStore.Files.getContentUri("external");

        String[] projection = null;

        String sortOrder = null;

        String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";

        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("mp3");

        String[] selectionArgsMp3 = new String[]{mimeType};

        musiccursor = cr.query(uri, projection, selectionMimeType, selectionArgsMp3, sortOrder);


//        String path = Environment.getExternalStorageDirectory().getPath();
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " !=" + 0
//                + " AND " + MediaStore.Audio.Media.DATA + " LIKE '" + path
//                + "/audio/%'";
//        String[] proj = {MediaStore.Audio.Media._ID,
//                MediaStore.Audio.Media.DATA,
//                MediaStore.Audio.Media.DISPLAY_NAME,
//                MediaStore.Video.Media.SIZE};
//        musiccursor = managedQuery(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                proj, null, null, null);
//        musiccursor = this.getContentResolver().query(
//                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, proj, selection,
//                null, null);

        count = musiccursor.getCount();
        musiclist = (ListView) findViewById(R.id.PhoneMusicList);

        if (count > 0) {
            musiclist.setAdapter(new MusicAdapter(getApplicationContext()));
            musiclist.setOnItemClickListener(musicgridlistener);
            mMediaPlayer = new MediaPlayer();
        } else {
            Toast.makeText(this, "Sound file not Found!", Toast.LENGTH_SHORT).show();
        }

    }

    private OnItemClickListener musicgridlistener = new OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position,
                                long id) {

            System.gc();
            music_column_index = musiccursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
            musiccursor.moveToPosition(position);
            Global_Data.sound_file = musiccursor.getString(music_column_index);

            SharedPreferences snd = Sound_Act.this.getSharedPreferences("SimpleLogic", 0);
            SharedPreferences.Editor edt_snd = snd.edit();
            edt_snd.putString("var_addsound", Global_Data.sound_file);
            edt_snd.commit();

            startActivity(new Intent(Sound_Act.this, Sound_Setting.class));

//            try {
//                if (mMediaPlayer.isPlaying()) {
//                    mMediaPlayer.reset();
//                }
//                // mMediaPlayer = MediaPlayer.create(MainActivity.this, Uri.parse(Environment.getExternalStorageDirectory().getPath()+ "/Audio/"+filename));
//////                                    mp.start();
//                mMediaPlayer.setDataSource(filename);
//                mMediaPlayer.start();
//                mMediaPlayer.prepare();
//            } catch (Exception e) {
//
//            }
        }
    };

    public class MusicAdapter extends BaseAdapter {
        private Context mContext;

        public MusicAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return count;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            System.gc();
            TextView tv = new TextView(mContext.getApplicationContext());
            tv.setTextSize(17);
            tv.setTextColor(Color.BLACK);
            tv.setPadding(20, 50, 20, 50);

            String id = null;
            if (convertView == null) {
                music_column_index = musiccursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME);
                musiccursor.moveToPosition(position);
                id = musiccursor.getString(music_column_index);
                music_column_index = musiccursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                musiccursor.moveToPosition(position);
                id += " Size(KB):" + musiccursor.getString(music_column_index);
                tv.setText(id);
            } else
                tv = (TextView) convertView;
            return tv;
        }
    }
}