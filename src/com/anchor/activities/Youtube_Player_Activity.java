package com.anchor.activities;

/**
 * Created by vinod on 25-08-2016.
 */

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import cpm.simplelogic.helper.Config;

public class Youtube_Player_Activity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view
    private YouTubePlayerView youTubeView;
    private TextView et_date,et_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.youtube_player);

        youTubeView = findViewById(R.id.youtube_view);
        et_date = findViewById(R.id.video_date);
        et_title = findViewById(R.id.video_title);

        et_date.setText("Advertisement Date "+Config.YOUTUBE_VIDEO_DATE);
        et_title.setText("Product Description   "+Config.YOUTUBE_VIDEO_DISCRIPTION);

        // Initializing video player with developer key
        youTubeView.initialize(Config.DEVELOPER_KEY, Youtube_Player_Activity.this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {try
            {
                ActionBar mActionBar = getActionBar();
                mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
                // mActionBar.setDisplayShowHomeEnabled(false);
                // mActionBar.setDisplayShowTitleEnabled(false);
                LayoutInflater mInflater = LayoutInflater.from(this);

                View mCustomView = mInflater.inflate(R.layout.action_bar, null);
                mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
                TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
                mTitleTextView.setText("Advertisement");

                TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
                todaysTarget.setVisibility(View.INVISIBLE);
                ImageView H_LOGO = mCustomView.findViewById(R.id.Header_logo);
                SharedPreferences sp = Youtube_Player_Activity.this.getSharedPreferences("SimpleLogic", 0);

                H_LOGO.setImageResource(R.drawable.video_imagenew);
                H_LOGO.setVisibility(View.VISIBLE);

//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//			todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//			todaysTarget.setText("Today's Target Acheived");
//		}

                mActionBar.setCustomView(mCustomView);
                mActionBar.setDisplayShowCustomEnabled(true);
                mActionBar.setHomeButtonEnabled(true);
                mActionBar.setDisplayHomeAsUpEnabled(true);
            }catch(Exception ex){ex.printStackTrace();}

        }



    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
          //  Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();

            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            player.loadVideo(Config.YOUTUBE_VIDEO_CODE);

            // Hiding player controls
           // player.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(Config.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }

}
