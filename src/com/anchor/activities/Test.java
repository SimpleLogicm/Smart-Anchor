package com.anchor.activities;



import android.app.Activity;
import android.content.Intent;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Test extends Activity implements OnItemSelectedListener{
	private static final String TAG = Customer_Feed.class.getSimpleName();
	
	Spinner feed_spinner;
	private String[] feed_state = { "Feedback","Complaints","Claims","Media" };
	
	ImageView media_video,video_play,video_stop;
	
	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	
	ImageView imageview,media_image,imageviewr,Take_pick,Crop_pick;
	public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    
    Drawable db;
	LinearLayout media_layout,m_viveo,emageview_option;
	Boolean B_flag;
	TextView txt_label,Textview2,textview3;
	EditText new_feedback,new_complaints,claim_amount,discription;
	private Uri fileUri; // file url to store image/video
	private VideoView videoPreview;
	final int PIC_CROP = 1;
	
	Button button1,button2,button3,button4,button5,button6;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		
		
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), Customer_Stock.class);
				intent.putExtra("CP_NAME", "Stock");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
	    	}
		});
		
		
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), Customer_Feed.class);
				intent.putExtra("CP_NAME", "Image");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
				
	    	}
		});
		
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), Customer_Feed.class);
				intent.putExtra("CP_NAME", "Feedback");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
			}
		});
		
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), Customer_Feed.class);
				intent.putExtra("CP_NAME", "Claim");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
			}
		});
		
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), Customer_Feed.class);
				intent.putExtra("CP_NAME", "Complaints");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
			}
		});
		
		
		
		
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
				Intent intent = new Intent(getApplicationContext(), Customer_Feed.class);
				intent.putExtra("CP_NAME", "video");
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				
		       
				
	    	}
		});
		
          
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
