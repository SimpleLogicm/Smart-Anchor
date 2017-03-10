package com.anchor.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.MediaController;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemSelectedListener;

public class VIDEO_DIALOG extends Activity implements OnItemSelectedListener
{
	private VideoView videoPreview;
	private String VIDEO_PATH;
	
	@SuppressLint("CutPasteId")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_view);
		
		videoPreview = (VideoView)findViewById(R.id.video_pre);
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
		Intent i = getIntent();
	  
		VIDEO_PATH = i.getStringExtra("VIDEO_PATH");
		
		videoPreview.setVideoPath(VIDEO_PATH);
		// start playing
		videoPreview.requestFocus();
		//videoPreview.suspend();
		
		  MediaController mediaController= new MediaController(this);  
           mediaController.setAnchorView(videoPreview);          
         
              //specify the location of media file  
          // Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");          
                
              //Setting MediaController and URI, then starting the videoView  
           videoPreview.setMediaController(mediaController);  
         // videoPreview.setVideoURI(uri);          
           videoPreview.requestFocus();  
          // videoPreview.setZOrderOnTop(true);
           videoPreview.start();
         
		
	}	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
			    //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				//videoPreview.stopPlayback();
		        videoPreview.stopPlayback();
				finish();
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

}
