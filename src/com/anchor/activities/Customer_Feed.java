package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.soundcloud.android.crop.Crop;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.GPSTracker;


public class Customer_Feed extends Activity implements OnItemSelectedListener,MediaScannerConnection.MediaScannerConnectionClient {
	private static final String TAG = Customer_Feed.class.getSimpleName();

	Bitmap Glovel_bitmap;
	GPSTracker gps;
	Drawable Glovel_Drawable;
	public String[] allFiles;
	String video_code = "";
	String RE_ID = "";
	String filename = "";
	String image_url = "";
	private String SCAN_PATH ;
	private MediaScannerConnection conn;
	private static final String FILE_TYPE="image/*";
	static String image_path = "";
	static String video_path = "";
	String final_media_path = "";
	Boolean isInternetPresent = false;
	String response_result = "";
	ConnectionDetector cd;
	String media_coden = "";
	String media_text = "";
	Bitmap bitmap;
	String picturePath = "";
	String video_option = "";
	String image_option = "";
	List<String> C_Array = new ArrayList<String>();
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	Spinner feed_spinner;
	private String selectedPath;
	private String[] feed_state = {"Feedback", "Complaints", "Claims", "Media"};
	Button button1;
	ImageView media_video, video_play, video_stop, Media_Takefromga;

	private Uri mImageCaptureUri;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int CROP_FROM_CAMERA = 2;
	private static final int PICK_FROM_FILE = 3;
	private static final int SELECT_VIDEO = 3;
	private static final int SELECT_IMAGE = 4;

	ImageView imageview, media_image, imageviewr, Take_pick, Crop_pick;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
	LoginDataBaseAdapter loginDataBaseAdapter;
	Drawable db;
	ProgressDialog dialog;
	LinearLayout media_layout, m_viveo, emageview_option;
	Boolean B_flag;
	TextView txt_label, Textview2, textview3;
	EditText new_feedback, new_complaints, claim_amount, discription;
	private Uri fileUri; // file url to store image/video
	private VideoView videoPreview;
	final int PIC_CROP = 1;
	private String Current_Date = "";
	PlayService_Location PlayServiceManager;

	private String name, CP_NAME, RE_TEXT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.customer_feed);

		StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
		StrictMode.setVmPolicy(builder.build());

		Intent i = getIntent();
		name = i.getStringExtra("retialer");
		CP_NAME = i.getStringExtra("CP_NAME");
		RE_TEXT = i.getStringExtra("RE_TEXT");

		Global_Data.Default_Image_Path = "";
		Global_Data.Default_video_Path = "";

		//loginDataBaseAdapter = new LoginDataBaseAdapter(Customer_Feed.this);
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();

		dialog = new ProgressDialog(Customer_Feed.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String strDate = sdf.format(c.getTime());
		Current_Date = sdf.format(c.getTime());
		cd = new ConnectionDetector(getApplicationContext());

//		Toast.makeText(getApplicationContext(),
//				CP_NAME, Toast.LENGTH_LONG).show();

		//feed_spinner = (Spinner) findViewById(R.id.feed_spinner);
		new_feedback = (EditText) findViewById(R.id.new_feedback);
		new_complaints = (EditText) findViewById(R.id.new_complaints);
		claim_amount = (EditText) findViewById(R.id.claim_amount);
		discription = (EditText) findViewById(R.id.discription);

		//Toast.makeText(getApplicationContext(), new_feedback.getText().toString(), Toast.LENGTH_LONG).show();



		/* claim list view code */
		ListView listView = (ListView) findViewById(R.id.C_list);


		/* end */

		videoPreview = (VideoView) findViewById(R.id.videoPreview);
		media_layout = (LinearLayout) findViewById(R.id.Media_Button);
		m_viveo = (LinearLayout) findViewById(R.id.Media_Video);
		emageview_option = (LinearLayout) findViewById(R.id.Emageview_option);
		Take_pick = (ImageView) findViewById(R.id.Take_pick);
		Crop_pick = (ImageView) findViewById(R.id.Crop_pick);
		video_play = (ImageView) findViewById(R.id.Media_Play);
		video_stop = (ImageView) findViewById(R.id.Media_STOP);
		media_image = (ImageView) findViewById(R.id.Media_image_Button);
		media_video = (ImageView) findViewById(R.id.Media_Video_Button);
		//Media_Takefromga = (ImageView) findViewById(R.id.Media_Takefromga);
		imageview = (ImageView) findViewById(R.id.result_image);
		imageview.setTag(R.drawable.white_background);
		imageviewr = (ImageView) findViewById(R.id.result_imager);
		button1 = (Button) findViewById(R.id.button1);
		txt_label = (TextView) findViewById(R.id.txt_label);
		Textview2 = (TextView) findViewById(R.id.textView2);
		textview3 = (TextView) findViewById(R.id.TextView01);
		txt_label.setText("Previous Feedback");


		if (CP_NAME.equals("video")) {
			media_image.setVisibility(View.GONE);
			listView.setVisibility(View.GONE);
			media_video.setVisibility(View.VISIBLE);

			// Media_Takefromga.setVisibility(View.VISIBLE);
			media_layout.setVisibility(View.VISIBLE);
			//imageview.setVisibility(View.VISIBLE);
			discription.setVisibility(View.VISIBLE);
			claim_amount.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.GONE);
			Textview2.setVisibility(View.GONE);
			textview3.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			//m_viveo.setVisibility(View.VISIBLE);
			videoPreview.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
			txt_label.setVisibility(View.GONE);
		} else if (CP_NAME.equals("Image")) {
			media_image.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			//Media_Takefromga.setVisibility(View.GONE);
			media_layout.setVisibility(View.VISIBLE);
			//imageview.setVisibility(View.VISIBLE);
			discription.setVisibility(View.VISIBLE);
			claim_amount.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.GONE);
			Textview2.setVisibility(View.GONE);
			textview3.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			//m_viveo.setVisibility(View.VISIBLE);
			videoPreview.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
			txt_label.setVisibility(View.GONE);
		} else if (CP_NAME.equals("Feedback")) {
			GetListData("Feedback");
			txt_label.setText("Previous" + " " + "Feedback");
			new_feedback.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
			new_complaints.setVisibility(View.GONE);
			Textview2.setVisibility(View.GONE);
			textview3.setVisibility(View.GONE);
			claim_amount.setVisibility(View.GONE);
			discription.setVisibility(View.GONE);
			media_image.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			//Media_Takefromga.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);

			emageview_option.setVisibility(View.GONE);

			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
		} else if (CP_NAME.equals("Complaints")) {
			GetListData("Complaints");

			txt_label.setText("Previous" + " " + "Complaints");

			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
			Textview2.setVisibility(View.GONE);
			textview3.setVisibility(View.GONE);
			claim_amount.setVisibility(View.GONE);
			discription.setVisibility(View.GONE);
			media_image.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			//Media_Takefromga.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.GONE);
			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
		} else if (CP_NAME.equals("Claim")) {
			GetListData("Claim");
			txt_label.setText("Previous" + " " + "Claims");
			claim_amount.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
			discription.setVisibility(View.VISIBLE);
			Textview2.setVisibility(View.GONE);
			textview3.setVisibility(View.GONE);
			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.GONE);
			media_image.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			//Media_Takefromga.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.GONE);
			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
		}

		ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.claim_list_view, C_Array);
		listView.setAdapter(adapter);

	/*	videoPreview.setOnTouchListener(new OnTouchListener() {
		    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
			@SuppressLint("InlinedApi")
			@Override
		    public boolean onTouch(View v, MotionEvent event) {

		    	videoPreview.stopPlayback();
		        //finish();

//		    	Toast.makeText(Customer_Feed.this,"Please Select City", Toast.LENGTH_SHORT).show();
//		    	Intent intent = new Intent(getApplicationContext(), VIDEO_DIALOG.class);
//				intent.putExtra("VIDEO_PATH", fileUri.getPath());
//				startActivity(intent);

		    	final Dialog dialog = new Dialog(Customer_Feed.this);
		    	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		    	dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		    	dialog.setContentView(R.layout.video_view);
		    	//final Button v_close = (Button) dialog.findViewById(R.id.v_close);
		    	final VideoView videoview = (VideoView) dialog.findViewById(R.id.video_pre);


		    	videoview.setVideoPath(fileUri.getPath());
		    	MediaController mediaController= new MediaController(Customer_Feed.this);
		    	//mediaController.setVisibility(View.GONE);
	            mediaController.setAnchorView(videoPreview);
	            videoview.setMediaController(mediaController);
		    	//videoview.setVideoURI(uri);
	          //  videoview.prepare();
		    	videoview.start();

		    	dialog.show();
		    	WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
		    	LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		    	lp.copyFrom(dialog.getWindow().getAttributes());
		    	dialog.getWindow().setAttributes(lp);

		    	dialog.setOnKeyListener(new Dialog.OnKeyListener() {

		            @Override
		            public boolean onKey(DialogInterface arg0, int keyCode,
		                    KeyEvent event) {
		                // TODO Auto-generated method stub
		                if (keyCode == KeyEvent.KEYCODE_BACK) {
		                   // finish();
		                	videoview.stopPlayback();
		                    dialog.dismiss();
		                }
		                return true;
		            }
		        });

//		    	v_close.setOnClickListener(new OnClickListener() {
//
//	                @Override
//	                public void onClick(View v) {
//	                	videoview.stopPlayback();
//	                	dialog.dismiss();
//	                }
//	            });



				return true;
		    }
		});*/

		videoPreview.setOnPreparedListener(new
												   MediaPlayer.OnPreparedListener() {
													   @Override
													   public void onPrepared(MediaPlayer mp) {
														   Log.i(TAG, "Duration = " +
																   videoPreview.getDuration());
														   //	videoPreview.setBackgroundDrawable(null);
													   }
												   });

		videoPreview.setOnInfoListener(new MediaPlayer.OnInfoListener() {
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {

				if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
					// Here the video starts
					videoPreview.setBackgroundDrawable(null);
					return true;
				}
				return false;
			}
		});
		media_image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				get_dialogC_Image();

//				B_flag = isDeviceSupportCamera();
//
//				if(B_flag == true)
//				{
//					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//			        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//			        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//
//			       // performCrop(fileUri);
//			        // start the image capture Intent
//			        startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
//
////					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
////
////			        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
////	                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_CAMERA);
//				}
//				else
//				{
//					Toast.makeText(getApplicationContext(), "no camera on this device", Toast.LENGTH_LONG).show();
//				}

			}
		});


		Take_pick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				get_dialogC_Image();

			}
		});

		media_video.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				requestStoragePermissionvideo();

			}
		});

//		Media_Takefromga.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				Intent intent = new Intent();
//				intent.setType("video/*");
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);
//			}
//		});

		video_play.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				videoPreview.start();
				videoPreview.setBackgroundDrawable(null);
			}
		});

		video_stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				videoPreview.pause();
			}
		});


		Crop_pick.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (image_option.equalsIgnoreCase("image")) {
					try {
						if (Uri.fromFile(new File(fileUri.getPath())) != null) {
							db = imageview.getDrawable();
							Glovel_Drawable = imageview.getDrawable();
							imageview.setImageDrawable(null);
							//Glovel_Drawable = imageview.getDrawable();
							//Crop.pickImage(Customer_Feed.this);
							Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
							Crop.of(Uri.fromFile(new File(fileUri.getPath())), Uri.fromFile(new File(fileUri.getPath()))).asSquare().start(Customer_Feed.this);
						} else {
							//Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}

					} catch (Exception e)

					{
						e.printStackTrace();
						//Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG).show();
						Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					}
				} else {
					try {
						if (Uri.fromFile(new File(picturePath)) != null) {
							db = imageview.getDrawable();
							Glovel_Drawable = imageview.getDrawable();
							imageview.setImageDrawable(null);

							//Crop.pickImage(Customer_Feed.this);
							Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
							Crop.of(Uri.fromFile(new File(picturePath)), Uri.fromFile(new File(picturePath))).asSquare().start(Customer_Feed.this);
						} else {
							//Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}

					} catch (Exception e)

					{
						e.printStackTrace();
						//Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					}
				}


			}
		});

		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				requestGPSPermissionsigna();

			}
		});
		
          /*ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, feed_state);
		  adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		  feed_spinner.setAdapter(adapter_state);
		  feed_spinner.setOnItemSelectedListener(this);*/
		try
		{
			ActionBar mActionBar = getActionBar();
			mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
			// mActionBar.setDisplayShowHomeEnabled(false);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(this);

			View mCustomView = mInflater.inflate(R.layout.action_bar, null);
			mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
			TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText(CP_NAME);

			TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
			ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
			SharedPreferences sp = Customer_Feed.this.getSharedPreferences("SimpleLogic", 0);

			H_LOGO.setImageResource(R.drawable.list);
			H_LOGO.setVisibility(View.VISIBLE);

//	        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//				todaysTarget.setText("Target/Acheived : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//			}
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

			if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
				todaysTarget.setText("Today's Target Acheived");
			}

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}


	}

	/**
	 * Here we store the file url as it will be null after returning from camera
	 * app
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on screen orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	private void launchUploadActivity(boolean isImage) {
//    	Intent i = new Intent(Customer_Feed.this, UploadActivity.class);
//        i.putExtra("filePath", fileUri.getPath());
//        i.putExtra("isImage", isImage);
//        startActivity(i);
	}

	private void recordVideo() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

		//fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

		fileUri = getOutputMediaFileUrinew(MEDIA_TYPE_VIDEO);


		// set video quality
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 3);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file

		Global_Data.Default_video_Path = fileUri.getPath();



//		try {
//			//delete(mediaStorageDir);
//			if (mediaStorageDir.isDirectory()) {
//				String[] children = mediaStorageDir.list();
//				for (int i = 0; i < children.length; i++) {
//					new File(mediaStorageDir, children[i]).delete();
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		// start the video capture Intent
		videoPreview.start();
		videoPreview.setBackgroundDrawable(null);
		startActivityForResult(intent, CAMERA_CAPTURE_VIDEO_REQUEST_CODE);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
							   long arg3) {
		// TODO Auto-generated method stub
		txt_label.setText("Previous" + " " + feed_spinner.getSelectedItem().toString());

		if (feed_spinner.getSelectedItem().toString().equalsIgnoreCase("Feedback")) {
			new_feedback.setVisibility(View.VISIBLE);
			new_complaints.setVisibility(View.GONE);
			Textview2.setVisibility(View.VISIBLE);
			textview3.setVisibility(View.VISIBLE);
			claim_amount.setVisibility(View.GONE);
			discription.setVisibility(View.GONE);
			media_image.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);

			emageview_option.setVisibility(View.GONE);

			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
		} else if (feed_spinner.getSelectedItem().toString().equalsIgnoreCase("Complaints")) {
			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.VISIBLE);
			Textview2.setVisibility(View.VISIBLE);
			textview3.setVisibility(View.VISIBLE);
			claim_amount.setVisibility(View.GONE);
			discription.setVisibility(View.GONE);
			media_image.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.GONE);
			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
		} else if (feed_spinner.getSelectedItem().toString().equalsIgnoreCase("Claims")) {
			claim_amount.setVisibility(View.VISIBLE);
			discription.setVisibility(View.VISIBLE);
			Textview2.setVisibility(View.VISIBLE);
			textview3.setVisibility(View.VISIBLE);
			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.GONE);
			media_image.setVisibility(View.GONE);
			media_video.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.GONE);
			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
		} else if (feed_spinner.getSelectedItem().toString().equalsIgnoreCase("Media")) {

			media_image.setVisibility(View.VISIBLE);
			media_video.setVisibility(View.VISIBLE);
			media_layout.setVisibility(View.VISIBLE);
			//imageview.setVisibility(View.VISIBLE);
			discription.setVisibility(View.VISIBLE);
			claim_amount.setVisibility(View.GONE);
			imageviewr.setVisibility(View.GONE);
			new_feedback.setVisibility(View.GONE);
			new_complaints.setVisibility(View.GONE);
			Textview2.setVisibility(View.GONE);
			textview3.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			m_viveo.setVisibility(View.GONE);
			videoPreview.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
		}
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
		C_Array.clear();

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
		fileUri = getOutputMediaFileUrinew(MEDIA_TYPE_IMAGE);


		if (CP_NAME.equals("Image")) {

			Object tag = imageview.getTag();

			if (!tag.equals(R.drawable.white_background)) {
				if (fileUri.getPath().equalsIgnoreCase("null") || fileUri.getPath() != null) {
					get_dialogC_Back();
				} else {
					overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					this.finish();
				}
			} else {

				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
			}

		} else if (CP_NAME.equals("video")) {

			if (imageview.getVisibility() == View.GONE) {
				get_dialogC_Back();
			} else {
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
			}

		} else {
			File mediaStorageDir = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					Config.IMAGE_DIRECTORY_NAME + "/" + "CUSTOMER_SERVICES");

			try {
				//delete(mediaStorageDir);
				if (mediaStorageDir.isDirectory()) {
					String[] children = mediaStorageDir.list();
					for (int i = 0; i < children.length; i++) {
						new File(mediaStorageDir, children[i]).delete();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			this.finish();
		}


		//Toast.makeText(getApplicationContext(), "back", Toast.LENGTH_LONG).show();

	}


	@Override
	public void onStop() {
		super.onStop();
		C_Array.clear();

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		C_Array.clear();

		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * ------------ Helper Methods ----------------------
	 * */

	/**
	 * Creating file uri to store image/video
	 */
	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	public Uri getOutputMediaFileUrinew(int type) {
		try
		{
			return Uri.fromFile(getOutputMediaFilenew(type));
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * Checking device has camera hardware or not
	 */
	private boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			// no camera on this device
			return false;
		}
	}

//    private void beginCrop(Uri source) {
//        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
//        Crop.of(source, destination).asSquare().start(this);
//    }

//    @SuppressWarnings("unused")
//	private void handleCrop(int resultCode, Intent result) {
//        if (resultCode == RESULT_OK) {
//        	
//        	Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
//        } else if (resultCode == Crop.RESULT_ERROR) {
//            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

	/**
	 * Receiving activity result method will be called after closing the camera
	 * */

	/**
	 * Receiving activity result method will be called after closing the camera
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// if the result is capturing Image
//		try {


		if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
			beginCrop(data.getData());
		} else if (requestCode == Crop.REQUEST_CROP) {

			try
			{
				if(data == null)
				{
					imageview.setVisibility(View.VISIBLE);
					imageview.setImageDrawable(Glovel_Drawable);
					// imageview.setTag(Glovel_bitmap);
					//imageviewr.setImageBitmap(Glovel_bitmap);
				}
				else
				{
					handleCrop(resultCode, data,data.getData());
				}
			}catch(Exception ex){ex.printStackTrace();
				imageview.setVisibility(View.VISIBLE);
				imageview.setImageDrawable(Glovel_Drawable);
				// imageview.setTag(Glovel_bitmap);
				// imageviewr.setImageBitmap(Glovel_bitmap);

			}


		}

		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// successfully captured the image
				// display it in image view


//					if (data == null) {
//						Toast.makeText(Customer_Feed.this, "Please Select Image File.", Toast.LENGTH_LONG).show();
//					} else {
				if (image_option.equalsIgnoreCase("image")) {
					previewCapturedImage();
				} else {
					if (data == null) {
						Log.d(TAG, "Data is null");
						//Toast.makeText(Customer_Feed.this, "Image Not Selected", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(Customer_Feed.this, "Image Not Selected", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					} else {
						previewCapturedImageGellary(data.getData());
					}
				}

				//}


			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		} else if (requestCode == CAMERA_CAPTURE_VIDEO_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// video successfully recorded
				// preview the recorded video
				//Uri selectedImageUri = data.getData();
				//selectedPath = getPath(selectedImageUri);
				//previewVideonew();
				previewVideo();
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled recording
				Toast.makeText(getApplicationContext(),
						"User cancelled video recording", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to record video
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to record video", Toast.LENGTH_SHORT)
						.show();
			}
		} else if (requestCode == SELECT_VIDEO) {
			System.out.println("SELECT_VIDEO");

			if (data == null) {
				Log.d(TAG, "Data is null");
				//Toast.makeText(Customer_Feed.this, "Video Not Selected", Toast.LENGTH_LONG).show();

				Toast toast = Toast.makeText(Customer_Feed.this, "Video Not Selected", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			} else {
				Log.d(TAG, "Data: " + data);
				// the intent has data, so set the media uri
				Uri selectedImageUri = data.getData();


				if (data == null) {
					//Toast.makeText(Customer_Feed.this, "Please Select Video File.", Toast.LENGTH_LONG).show();

					Toast toast = Toast.makeText(Customer_Feed.this, "Please Select Video File.", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				} else {
					//selectedPath = getPath(selectedImageUri);
					selectedPath = getPathFromURI(Customer_Feed.this,selectedImageUri);
					previewVideonew();
				}

			}

			//fileUri = getOutputMediaFileUri(selectedPath);
			//textView.setText(selectedPath);
		}
//		}catch (Exception ex){ex.printStackTrace();
//			Toast.makeText(Customer_Feed.this, "Please Select Media File.", Toast.LENGTH_LONG).show();
//		}
	}

	public static String getPathFromURI(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] {
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {
			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection,
									   String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
					null);
			if (cursor != null && cursor.moveToFirst()) {
				final int column_index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(column_index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	public String getPath(Uri uri) {
		String path = "";
		try {
			Cursor cursor = getContentResolver().query(uri, null, null, null, null);
			cursor.moveToFirst();
			String document_id = cursor.getString(0);
			document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
			cursor.close();

			cursor = getContentResolver().query(
					MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
					null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
			cursor.moveToFirst();
			path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
			cursor.close();
		}catch(Exception ex)
		{
			ex.printStackTrace();

			String id = uri.getLastPathSegment().split(":")[1];
			final String[] imageColumns = {MediaStore.Video.Media.DATA };
			final String imageOrderBy = null;

			Uri urib = getUri();
			String selectedImagePath = "path";

			Cursor imageCursor = managedQuery(urib, imageColumns,
					MediaStore.Video.Media._ID + "="+id, null, imageOrderBy);

			if (imageCursor.moveToFirst()) {
				selectedImagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			imageCursor.close();
		}

		return path;
	}

	private void doCrop() {
		final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");

		List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);

		int size = list.size();

		if (size == 0) {
			//Toast.makeText(this, "Can not find image crop app", Toast.LENGTH_SHORT).show();
			Toast toast = Toast.makeText(Customer_Feed.this,"Can not find image crop app", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

			return;
		} else {

			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images


			options.inSampleSize = 8;
			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);
			intent.setData(Uri.fromFile(new File(fileUri.getPath())));
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			intent.putExtra("return-data", true);

			if (size == 1) {
				Intent i = new Intent(intent);
				ResolveInfo res = list.get(0);

				i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

				startActivityForResult(i, CROP_FROM_CAMERA);
			} else {
				for (ResolveInfo res : list) {
					final CropOption co = new CropOption();

					co.title = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
					co.icon = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
					co.appIntent = new Intent(intent);
					co.appIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
					cropOptions.add(co);
				}

				startActivityForResult(intent, CROP_FROM_CAMERA);


			}
		}
	}


	private void previewVideo() {
		try {
			// hide image preview
			imageview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
			m_viveo.setVisibility(View.VISIBLE);
			videoPreview.setVisibility(View.VISIBLE);
			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(fileUri.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
			videoPreview.setBackgroundDrawable(bitmapDrawable);
			videoPreview.setVideoPath(fileUri.getPath());
			// start playing
			videoPreview.requestFocus();
			videoPreview.suspend();

			MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(videoPreview);

			addVideoToGallery(fileUri.getPath(),Customer_Feed.this);

			//specify the location of media file
			// Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

			//Setting MediaController and URI, then starting the videoView
			videoPreview.setMediaController(mediaController);
			//  videoPreview.setVideoURI(uri);
			videoPreview.requestFocus();
			// videoPreview.setZOrderOnTop(true);
			videoPreview.pause();

			new MediaperationBackGroundVideo().execute(fileUri.getPath());

//			int id = videoPreview.getId();
//			
//			ContentResolver crThumb = getContentResolver();
//			BitmapFactory.Options options=new BitmapFactory.Options();
//			options.inSampleSize = 1;
//			Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id, MediaStore.Video.Thumbnails.MICRO_KIND, options);
//			imageview.setImageBitmap(curThumb);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void previewVideonew() {
		try {
			// hide image preview
			imageview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			emageview_option.setVisibility(View.GONE);
			m_viveo.setVisibility(View.VISIBLE);
			videoPreview.setVisibility(View.VISIBLE);
			Bitmap thumb = ThumbnailUtils.createVideoThumbnail(selectedPath, MediaStore.Video.Thumbnails.MINI_KIND);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(thumb);
			videoPreview.setBackgroundDrawable(bitmapDrawable);
			videoPreview.setVideoPath(selectedPath);
			// start playing
			videoPreview.requestFocus();
			videoPreview.suspend();

			MediaController mediaController = new MediaController(this);
			mediaController.setAnchorView(videoPreview);

			//specify the location of media file
			// Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

			//Setting MediaController and URI, then starting the videoView
			videoPreview.setMediaController(mediaController);
			//  videoPreview.setVideoURI(uri);
			videoPreview.requestFocus();
			// videoPreview.setZOrderOnTop(true);
			videoPreview.pause();

			new MediaperationBackGroundVideo().execute(selectedPath);

//			int id = videoPreview.getId();
//
//			ContentResolver crThumb = getContentResolver();
//			BitmapFactory.Options options=new BitmapFactory.Options();
//			options.inSampleSize = 1;
//			Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(crThumb, id, MediaStore.Video.Thumbnails.MICRO_KIND, options);
//			imageview.setImageBitmap(curThumb);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
     * Display image from a path to ImageView
     */
	private void previewCapturedImage() {
		try {
			// hide video preview
			videoPreview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			emageview_option.setVisibility(View.VISIBLE);

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;


			bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);

			addImageToGallery(fileUri.getPath(),Customer_Feed.this);

			imageview.setImageBitmap(bitmap);
			imageview.setTag(bitmap);
			imageviewr.setImageBitmap(bitmap);

			new MediaperationBackGround().execute(bitmap);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	private void previewCapturedImageGellary(Uri data) {
		try {
			// hide video preview
			videoPreview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			emageview_option.setVisibility(View.VISIBLE);

			// bimatp factory
			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;


			Uri selectedImage = data;
			String[] filePath = {MediaStore.Images.Media.DATA};
			Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePath[0]);
			//picturePath = c.getString(columnIndex);
			picturePath = getPathfgdg(data);
			c.close();
			//Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

			//viewImage.setImageBitmap(thumbnail);

			bitmap = (BitmapFactory.decodeFile(picturePath));

			imageview.setImageBitmap(bitmap);
			imageview.setTag(bitmap);
			imageviewr.setImageBitmap(bitmap);

			Glovel_bitmap = bitmap;

			new MediaperationBackGround().execute(bitmap);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public String getPathfgdg(Uri uri) {
		if (uri == null) {
			return null;
		}
		String[] projection = {MediaStore.Images.Media.DATA};
		Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}
		return uri.getPath();
	}



	private void performCrop(Uri picUri) {
		try {

			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			// indicate image type and Uri
			cropIntent.setDataAndType(picUri, "image/*");
			// set crop properties
			cropIntent.putExtra("crop", "true");
			// indicate aspect of desired crop
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			// indicate output X and Y
			cropIntent.putExtra("outputX", 128);
			cropIntent.putExtra("outputY", 128);
			// retrieve data on return
			cropIntent.putExtra("return-data", true);
			// start the activity - we handle returning in onActivityResult
			startActivityForResult(cropIntent, PIC_CROP);
		}
		// respond to users whose devices do not support the crop action
		catch (ActivityNotFoundException anfe) {
			// display an error message
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	private void beginCrop(Uri source) {
		Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
		Crop.of(source, destination).asSquare().start(this);




	}

	private void handleCrop(int resultCode, Intent result,Uri data) {
		if(resultCode == 0)
		{
			//db

			imageview.setImageDrawable(imageviewr.getDrawable());
			videoPreview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			emageview_option.setVisibility(View.VISIBLE);
		}
		if (resultCode == RESULT_OK) {
			imageview.setImageURI(Crop.getOutput(result));
			imageviewr.setImageURI(Crop.getOutput(result));

			videoPreview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			emageview_option.setVisibility(View.VISIBLE);

			//String picturePath = getPathfgdg(data);
			//bitmap = (BitmapFactory.decodeFile(picturePath));
			//new MediaperationBackGround().execute(bitmap);

		} else if (resultCode == Crop.RESULT_ERROR) {
			Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
			videoPreview.setVisibility(View.GONE);
			media_layout.setVisibility(View.GONE);
			imageview.setVisibility(View.VISIBLE);
			emageview_option.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				Config.IMAGE_DIRECTORY_NAME+"/"+"CUSTOMER_SERVICES");

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(TAG, "Oops! Failed create "
						+ Config.IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}


	private static File getOutputMediaFilenew(int type) {

		File mediaStorageDir;
		// External sdcard location
		if (type == MEDIA_TYPE_IMAGE) {
			mediaStorageDir = new File(
					//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Config.IMAGE_DIRECTORY_NAME);
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_PICTURE");




			image_path = mediaStorageDir.getPath();
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d(TAG, "Oops! Failed create "
							+ Config.IMAGE_DIRECTORY_NAME + " directory");
					return null;
				}
				else
				{
					mediaStorageDir.mkdirs();
				}
			}

			// Create a media file name
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
					Locale.getDefault()).format(new Date());
			File mediaFile;
			if (type == MEDIA_TYPE_IMAGE) {
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ "IMG_" + timeStamp + ".jpg");

			} else if (type == MEDIA_TYPE_VIDEO) {
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ "VID_" + timeStamp + ".mp4");
			} else {
				return null;
			}

			return mediaFile;
		}
		else if (type == MEDIA_TYPE_VIDEO) {
			mediaStorageDir = new File(
					Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_VIDEO");

			video_path = mediaStorageDir.getPath();
			if (!mediaStorageDir.exists()) {
				if (!mediaStorageDir.mkdirs()) {
					Log.d(TAG, "Oops! Failed create "
							+ Config.IMAGE_DIRECTORY_NAME + " directory");
					return null;
				}
			}

			// Create a media file name
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
					Locale.getDefault()).format(new Date());
			File mediaFile;
			if (type == MEDIA_TYPE_IMAGE) {
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ "IMG_" + timeStamp + ".jpg");



			} else if (type == MEDIA_TYPE_VIDEO) {
				mediaFile = new File(mediaStorageDir.getPath() + File.separator
						+ "VID_" + timeStamp + ".mp4");
			} else {
				return null;
			}

			return mediaFile;
		}


		// Create the storage directory if it does not exist
		return null;
	}


	public void GetListData(String Product_type)
	{
		if(Product_type.equals("Feedback"))
		{

			// List<Local_Data> contacts = dbvoc.getAllFeedback();
			List<Local_Data> contacts = dbvoc.getAllFeedback_BYCUSTOMERID(Global_Data.GLOvel_CUSTOMER_ID );
			for (Local_Data cn : contacts)
			{
				C_Array.add(cn.getC_Date()+" : "+cn.get_Description());
				//Global_Data.local_user = ""+cn.getUser();
				//Global_Data.local_pwd = ""+cn.getPwd();
				//System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);

			}
		}
		else
		if(Product_type.equals("Claim"))
		{
			// List<Local_Data> contacts = dbvoc.getAllClaims();
			List<Local_Data> contacts = dbvoc.getAllClaims_BYCUSTOMERID(Global_Data.GLOvel_CUSTOMER_ID );
			for (Local_Data cn : contacts)
			{
				C_Array.add(cn.getC_Date()+" :  Rs. "+cn.get_Claims_amount()+"  "+cn.get_Claims());
				//Global_Data.local_user = ""+cn.getUser();
				//Global_Data.local_pwd = ""+cn.getPwd();
				//System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);

			}
		}
		else
		if(Product_type.equals("Complaints"))
		{
			//List<Local_Data> contacts = dbvoc.getAllComplaints();
			List<Local_Data> contacts = dbvoc.getAllComplaints_BYCUSTOMERIDN(Global_Data.GLOvel_CUSTOMER_ID);
			for (Local_Data cn : contacts)
			{
				// C_Array.add(cn.getC_Date()+":"+cn.get_complaints());
				C_Array.add(cn.getC_Date()+" : "+cn.get_complaints());
				//Global_Data.local_user = ""+cn.getUser();
				//Global_Data.local_pwd = ""+cn.getPwd();
				//System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);

			}
		}
	}
	MediaPlayer.OnCompletionListener myVideoViewCompletionListener = new MediaPlayer.OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			mp.reset();
			mp.release();
		}
	};

	public void call_service_FORCUSS(String Service_name)
	{
		System.gc();
		String reason_code = "";
		try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);





			dialog.setMessage("Please wait....");
			dialog.setTitle("Smart Anchor App");
			dialog.setCancelable(false);
			dialog.show();

			String domain = "";
			String device_id = "";

			SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
			device_id = sp.getString("devid", "");

			domain = this.getResources().getString(R.string.service_domain);

			// Global_Val global_Val = new Global_Val();
//		        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//		            domain = context.getResources().getString(R.string.service_domain);
//		        }
//		        else
//		        {
//		            domain = URL.toString();
//		        }
			// StringRequest stringRequest = null;

			JsonObjectRequest jsObjRequest = null;
			try
			{



				Log.d("Server url","Server url"+domain+"expenses_miscs/save_misc_expenses");


				JSONArray order = new JSONArray();
				JSONObject product_value = new JSONObject();
				JSONObject product_imei = new JSONObject();
				JSONObject product_value_n = new JSONObject();

				product_value.put("user_email", Global_Data.GLOvel_USER_EMAIL);


				order.put(product_value);

				product_value_n.put("expenses_miscs", order);
				product_value_n.put("imei_no", Global_Data.device_id);
				Log.d("expenses_miscs",product_value_n.toString());

//				 
//				
//				 //product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//				// product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//			      
				jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"expenses_miscs/save_misc_expenses", product_value_n, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i("volley", "response: " + response);

						Log.d("jV", "JV length" + response.length());
						//JSONObject json = new JSONObject(new JSONTokener(response));
						try{

							String response_result = "";
							if(response.has("message"))
							{
								response_result = response.getString("message");
							}
							else
							{
								response_result = "data";
							}


							if(response_result.equalsIgnoreCase("Misc Expenses created successfully."))
							{

								dialog.dismiss();
								//Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

								Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

								Intent a = new Intent(Customer_Feed.this,MainActivity.class);
								startActivity(a);
								finish();
							}
							else
							{

								dialog.dismiss();
								// Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
								Toast toast = Toast.makeText(Customer_Feed.this,response_result, Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								// Intent a = new Intent(Expenses.this,MainActivity.class);
								//startActivity(a);
								// finish();

							}

							//  finish();
							// }

							// output.setText(data);
						}catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


						dialog.dismiss();
						dialog.dismiss();




					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("volley", "error: " + error);
						//Toast.makeText(Customer_Feed.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(Customer_Feed.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

						dialog.dismiss();
					}
				});

				RequestQueue requestQueue = Volley.newRequestQueue(Customer_Feed.this);

				int socketTimeout = 300000;//30 seconds - change to what you want
				RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
				jsObjRequest.setRetryPolicy(policy);
				// requestQueue.se
				//requestQueue.add(jsObjRequest);
				jsObjRequest.setShouldCache(false);
				requestQueue.getCache().clear();
				requestQueue.add(jsObjRequest);

			}catch(Exception e)
			{
				e.printStackTrace();
				dialog.dismiss();
			}





			//createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
			//createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}
			
			
			  
			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests
                   
                }
			 */

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DATA", e.getMessage());
		}
	}

	void delete(File f) throws IOException {
		if (f.isDirectory()) {
			for (File c : f.listFiles()) {
				delete(c);
			}
		} else if (f.getAbsolutePath().endsWith("CUSTOMER_SERVICES")) {
			if (!f.delete()) {
				new FileNotFoundException("Failed to delete file: " + f);
			}
		}
	}

	public String getStringImage(Bitmap bmp){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String encodedImage = "";
		try
		{
			bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] imageBytes = baos.toByteArray();
			encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
			// String encodedImage =imageBytes.toString();

			return encodedImage;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return encodedImage;
	}

	public void get_dialogC()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update
		alertDialog.setTitle("Confirmation");
		alertDialog.setMessage("Do you want to add more images ?");
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub


				if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(CaptureSignature.this);
//								   insertOrderAsyncTask.execute();


				fileUri = null;
				discription.setText("");
				imageview.setImageResource(R.drawable.white_background);
				dialog.cancel();

			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Customer_Feed.this, Order.class);

				//Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();

				Toast toast = Toast.makeText(Customer_Feed.this,"Your Data Submit Successfuly",Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();

				if(fileUri.getPath() != null)
				{
					intent.putExtra("filePath", fileUri.getPath());
				}
				else
				{
					intent.putExtra("filePath", "");
				}

				fileUri = null;
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
				dialog.cancel();
			}
		});

		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	public void get_dialogC_Back()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update
		alertDialog.setTitle("Confirmation");
		if(CP_NAME.equals("video")) {
			alertDialog.setMessage("Do you want to Back without saving this video ?");
		}
		else
		{
			alertDialog.setMessage("Do you want to Back without saving this image ?");
		}

		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub


				if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				} else
					setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(CaptureSignature.this);
//								   insertOrderAsyncTask.execute();

				Intent intent = new Intent(Customer_Feed.this, Order.class);
//				if(fileUri.getPath() != null)
//				{
				intent.putExtra("filePath", fileUri.getPath());


				fileUri = null;
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				finish();
				dialog.cancel();

//				}
//				else
//				{
//					intent.putExtra("filePath", "");
//				}

			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {


				//Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();


				dialog.cancel();
			}
		});

		alertDialog.setCancelable(false);
		alertDialog.show();
	}


	public void get_dialogC_ImageNew()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update
		alertDialog.setTitle("Confirmation");
//		if(CP_NAME.equals("video")) {
//			alertDialog.setMessage("Do you want to Back without saving this video ?");
//		}
//		else
//		{
		alertDialog.setMessage("Do you want to Back without saving this image ?");
		//}

		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub




			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {


				//Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();


				dialog.cancel();
			}
		});

		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	public void get_dialogC_Video()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update
		alertDialog.setTitle("Video");

		alertDialog.setMessage("Select option");

		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "View Save Video Gallery",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				video_option = "Gallery";


				File folder = new File(video_path);

				//   uriAllFiles= new Uri[allFiles.length];

//				if (!Global_Data.Default_video_Path.equalsIgnoreCase("")) {
//
//					if (discription.getText().toString() == null || discription.getText().toString().equals("")) {
//						//Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG).show();
//
//						Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
//					else
//					{
//						try
//						{
//							loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME, RE_ID, Global_Data.GLOvel_USER_EMAIL,
//									"", discription.getText().toString(),Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Default_video_Path);
//
//							if (folder.isDirectory()) {
//								allFiles = folder.list();
//
//								if(allFiles.length > 0)
//								{
////							for (int i = 0; i < allFiles.length; i++) {
////								Log.d("all file path" + i, allFiles[i] + allFiles.length);
////							}
//
//									Intent map = new Intent(Customer_Feed.this,SDcard_VideoMain.class);
//									startActivity(map);
//									finish();
//								}
//								else
//								{
//									Toast.makeText(Customer_Feed.this, "Video Not Found.", Toast.LENGTH_SHORT).show();
//								}
//
//							}
//							else
//							{
//								Toast.makeText(Customer_Feed.this, "Video Not Found.", Toast.LENGTH_SHORT).show();
//							}
//
//							//Global_Data.Default_Image_Path = fileUri.getPath();
//
//
//						}catch (Exception ex){
//							ex.printStackTrace();
//						}
//
//
//						SCAN_PATH=image_path;
//						System.out.println(" SCAN_PATH  " +SCAN_PATH);
//
//						Log.d("SCAN PATH", "Scan Path " + SCAN_PATH);
//					}
//
//				}
//				else
//				{
				try
				{
					File  mediaStorageDir = new File(
							//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Config.IMAGE_DIRECTORY_NAME);
							Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_VIDEO");

					File foldern = new File(mediaStorageDir.getPath());
					if (foldern.isDirectory()) {
						allFiles = foldern.list();

						if(allFiles.length > 0)
						{
//							for (int i = 0; i < allFiles.length; i++) {
//								Log.d("all file path" + i, allFiles[i] + allFiles.length);
//							}

							Intent map = new Intent(Customer_Feed.this,SDcard_VideoMain.class);
							startActivity(map);
							finish();
						}
						else
						{
							Toast.makeText(Customer_Feed.this, "Video Not Found.", Toast.LENGTH_SHORT).show();
						}

					}
					else
					{
						Toast.makeText(Customer_Feed.this, "Video Not Found.", Toast.LENGTH_SHORT).show();
					}

					//Global_Data.Default_Image_Path = fileUri.getPath();


				}catch (Exception ex){
					ex.printStackTrace();
				}


				SCAN_PATH=image_path;
				System.out.println(" SCAN_PATH  " +SCAN_PATH);

				Log.d("SCAN PATH", "Scan Path " + SCAN_PATH);
				//}
				//Intent intent = new Intent();
				//Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//				Intent intent;
//				if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
//				{
//					intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//				}
//				else
//				{
//					intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI);
//				}
//				intent.setType("video/*");
//				intent.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

				dialog.cancel();

//

			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take Video",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				video_option = "Record";
				recordVideo();
				dialog.cancel();
			}
		});

		//alertDialog.setCancelable(false);
		alertDialog.show();
	}

	/**
	 * Uploading the file to server
	 * */
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			//progressBar.setProgress(0);
			dialog = new ProgressDialog(Customer_Feed.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			dialog.setTitle("Smart Anchor App");
			dialog.setCancelable(false);
			dialog.show();
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Integer... progress) {
			// Making progress bar visible
			//	progressBar.setVisibility(View.VISIBLE);

			// updating progress bar value
			//progressBar.setProgress(progress[0]);

			// updating percentage value
			//txtPercentage.setText(String.valueOf(progress[0]) + "%");
		}

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			String responseString = null;
			String  domain = getResources().getString(R.string.service_domain);
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(domain+"orders/save_orders");

			try {
//				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
//						new AndroidMultiPartEntity.ProgressListener() {
//
//
//							public void transferred(long num) {
//								//publishProgress((int) ((num / (float) totalSize) * 100));
//							}
//						});

				File sourceFile = new File(fileUri.getPath());
				MultipartEntity entity = new MultipartEntity();
//				loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME,Global_Data.GLOvel_CUSTOMER_ID,Global_Data.GLOvel_USER_EMAIL,
//						image_url,discription.getText().toString(), Current_Date, Current_Date);

				// Adding file data to http body
				entity.addPart("video", new FileBody(sourceFile));

				// Extra parameters if you want to pass to server
				entity.addPart("customer_code",
						new StringBody(Global_Data.GLOvel_CUSTOMER_ID));
				entity.addPart("media_type", new StringBody("video"));
				entity.addPart("user_code", new StringBody(Global_Data.GLOvel_USER_EMAIL));
				entity.addPart("media_text", new StringBody(discription.getText().toString()));

				//totalSize = entity.getContentLength();
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
					dialog.dismiss();
				}

			} catch (ClientProtocolException e) {
				responseString = e.toString();
				dialog.dismiss();
			} catch (IOException e) {
				responseString = e.toString();
				dialog.dismiss();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String result) {
			Log.e(TAG, "Response from server: " + result);

			// showing the server response in an alert dialog
			//showAlert(result);
			dialog.dismiss();
			super.onPostExecute(result);
		}

	}



	public  void executeMultipartPost(){
		try {
			String  domain = getResources().getString(R.string.service_domain);

			//HttpPost httppost = new HttpPost(domain+"orders/save_orders");
			HttpClient client = new DefaultHttpClient();
			HttpPost poster = new HttpPost(domain+"orders/save_orders");

			File sourceFile = new File(fileUri.getPath());

			//File image = new File(imgPath);  //get the actual file from the device
			MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			entity.addPart("video", new FileBody(sourceFile));

			// Extra parameters if you want to pass to server
			entity.addPart("customer_id",
					new StringBody(Global_Data.GLOvel_CUSTOMER_ID));
			entity.addPart("media_type", new StringBody("video"));
			entity.addPart("user_id", new StringBody(Global_Data.GLOvel_USER_EMAIL));
			entity.addPart("media_text", new StringBody(discription.getText().toString()));
			poster.setEntity(entity );

			client.execute(poster, new ResponseHandler<Object>() {
				public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					HttpEntity respEntity = response.getEntity();
					String responseString = EntityUtils.toString(respEntity);
					// do something with the response string
					return null;
				}
			});
		} catch (Exception e){
			//do something with the error
		}
	}

	//	//android upload file to server
	public int uploadFile(){

		//File sourceFile = new File(fileUri.getPath());
		final String selectedFilePath = fileUri.getPath().toString();
		int serverResponseCode = 0;

		HttpURLConnection connection;
		DataOutputStream dataOutputStream;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		String  domain = getResources().getString(R.string.service_domain);
		//HttpClient httpclient = new DefaultHttpClient();
		//HttpPost httppost = new HttpPost(domain+"orders/save_orders");

		int bytesRead,bytesAvailable,bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File selectedFile = new File(selectedFilePath);


		String[] parts = selectedFilePath.split("/");
		final String fileName = parts[parts.length-1];

		if (!selectedFile.isFile()){
			dialog.dismiss();

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
					Log.d("Source file path","Source File Doesn't Exist: " + selectedFilePath);
				}
			});
			return 0;
		}else{
			try{
				FileInputStream fileInputStream = new FileInputStream(selectedFile);
				URL url = new URL(domain+"orders/save_orders");
				connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);//Allow Inputs
				connection.setDoOutput(true);//Allow Outputs
				connection.setUseCaches(false);//Don't use a cached Copy
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Connection", "Keep-Alive");
				connection.setRequestProperty("ENCTYPE", "multipart/form-data");
				connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				connection.setRequestProperty("uploaded_file",selectedFilePath);

				//creating new dataoutputstream
				dataOutputStream = new DataOutputStream(connection.getOutputStream());

				//writing bytes to data outputstream
				dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
				dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ selectedFilePath + "\"" + lineEnd);

				dataOutputStream.writeBytes(lineEnd);

				//returns no. of bytes present in fileInputStream
				bytesAvailable = fileInputStream.available();
				//selecting the buffer size as minimum of available bytes or 1 MB
				bufferSize = Math.min(bytesAvailable,maxBufferSize);
				//setting the buffer as byte array of size of bufferSize
				buffer = new byte[bufferSize];

				//reads bytes from FileInputStream(from 0th index of buffer to buffersize)
				bytesRead = fileInputStream.read(buffer,0,bufferSize);

				//loop repeats till bytesRead = -1, i.e., no bytes are left to read
				while (bytesRead > 0){
					//write the bytes read from inputstream
					dataOutputStream.write(buffer,0,bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable,maxBufferSize);
					bytesRead = fileInputStream.read(buffer,0,bufferSize);
				}

				dataOutputStream.writeBytes(lineEnd);
				dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				serverResponseCode = connection.getResponseCode();
				String serverResponseMessage = connection.getResponseMessage();

				Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);

				//response code of 200 indicates the server status OK
				if(serverResponseCode == 200){
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
//							tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
						}
					});
				}

				//closing the input and output streams
				fileInputStream.close();
				dataOutputStream.flush();
				dataOutputStream.close();



			} catch (FileNotFoundException e) {
				e.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(Customer_Feed.this,"File Not Found",Toast.LENGTH_SHORT).show();
					}
				});
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Toast.makeText(Customer_Feed.this, "URL error!", Toast.LENGTH_SHORT).show();

			} catch (IOException e) {
				e.printStackTrace();
				Toast.makeText(Customer_Feed.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();
			return serverResponseCode;
		}

	}

	public class LoadDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */


		final String selectedFilePath = final_media_path;
		int serverResponseCode = 0;

		HttpURLConnection connection;
		DataOutputStream dataOutputStream;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		String  domain = getResources().getString(R.string.service_domain);
		//HttpClient httpclient = new DefaultHttpClient();
		//HttpPost httppost = new HttpPost(domain+"orders/save_orders");

		int bytesRead,bytesAvailable,bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File selectedFile = new File(selectedFilePath);


		String[] parts = selectedFilePath.split("/");
		final String fileName = parts[parts.length-1];



		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(Customer_Feed.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
			dialog.setTitle("Smart Anchor App");
			dialog.setCancelable(false);
			dialog.show();

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {

				if (!selectedFile.isFile()){
					//dialog.dismiss();

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							//tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
							Log.d("Source file path","Source File Doesn't Exist: " + selectedFilePath);
						}
					});
					//return 0;
				}else{
					try{
						FileInputStream fileInputStream = new FileInputStream(selectedFile);
						URL url = new URL(domain+"customer_service_media");
						connection = (HttpURLConnection) url.openConnection();
						connection.setDoInput(true);//Allow Inputs
						connection.setDoOutput(true);//Allow Outputs
						connection.setUseCaches(false);//Don't use a cached Copy
						connection.setRequestMethod("POST");
						connection.setRequestProperty("Connection", "Keep-Alive");
						connection.setRequestProperty("ENCTYPE", "multipart/form-data");
						connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
						connection.setRequestProperty("customer_code",Global_Data.GLOvel_CUSTOMER_ID);
						connection.setRequestProperty("user_email",Global_Data.GLOvel_USER_EMAIL);
						connection.setRequestProperty("media_type",CP_NAME.toString().trim());
						connection.setRequestProperty("media_text", media_text);
						connection.setRequestProperty("location",selectedFilePath);

						//creating new dataoutputstream
						dataOutputStream = new DataOutputStream(connection.getOutputStream());

						//writing bytes to data outputstream
						dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
						dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
								+ selectedFilePath + "\"" + lineEnd);

						dataOutputStream.writeBytes(lineEnd);

						//returns no. of bytes present in fileInputStream
						bytesAvailable = fileInputStream.available();
						//selecting the buffer size as minimum of available bytes or 1 MB
						bufferSize = Math.min(bytesAvailable,maxBufferSize);
						//setting the buffer as byte array of size of bufferSize
						buffer = new byte[bufferSize];

						//reads bytes from FileInputStream(from 0th index of buffer to buffersize)
						bytesRead = fileInputStream.read(buffer,0,bufferSize);

						//loop repeats till bytesRead = -1, i.e., no bytes are left to read
						while (bytesRead > 0){
							//write the bytes read from inputstream
							dataOutputStream.write(buffer,0,bufferSize);
							bytesAvailable = fileInputStream.available();
							bufferSize = Math.min(bytesAvailable,maxBufferSize);
							bytesRead = fileInputStream.read(buffer,0,bufferSize);
						}

						dataOutputStream.writeBytes(lineEnd);
						dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

						serverResponseCode = connection.getResponseCode();
						final String serverResponseMessage = connection.getResponseMessage();

						Log.i(TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);


						//response code of 200 indicates the server status OK
						if(serverResponseCode == 200){
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									dialog.dismiss();
									//Toast.makeText(Customer_Feed.this, serverResponseMessage, Toast.LENGTH_SHORT).show();

									Toast toast = Toast.makeText(Customer_Feed.this, serverResponseMessage, Toast.LENGTH_SHORT);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
//							tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
								}
							});
						}

						//closing the input and output streams
						fileInputStream.close();
						dataOutputStream.flush();
						dataOutputStream.close();



					} catch (FileNotFoundException e) {
						e.printStackTrace();
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								dialog.dismiss();
								//Toast.makeText(Customer_Feed.this,"File Not Found",Toast.LENGTH_SHORT).show();
								Toast toast = Toast.makeText(Customer_Feed.this,"File Not Found",Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						});
					} catch (MalformedURLException e) {
						e.printStackTrace();
						dialog.dismiss();
						//Toast.makeText(Customer_Feed.this, "URL error!", Toast.LENGTH_SHORT).show();

						Toast toast = Toast.makeText(Customer_Feed.this,"URL error!",Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

					} catch (IOException e) {
						dialog.dismiss();
						e.printStackTrace();
						//Toast.makeText(Customer_Feed.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();

						Toast toast = Toast.makeText(Customer_Feed.this,"Cannot Read/Write File!",Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					//dialog.dismiss();
					//return serverResponseCode;
				}


			} catch (Exception e) {
				// TODO: handle exception
				dialog.dismiss();
				Log.e("DATA", "Exception-"+e.toString());
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();

			}


		}
	}

	private void selectImage() {
		final CharSequence[] items = { "Take Photo", "Choose from Library",
				"Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(Customer_Feed.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Take Photo")) {

					//cameraIntent();
				} else if (items[item].equals("Choose from Library")) {
					//userChoosenTask="Choose from Library";

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	public void get_dialogC_Image()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update
		alertDialog.setTitle("Photo");

		alertDialog.setMessage("Select option");

		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "View Save Image Gallery",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				image_option = "Gallery";
//				Intent intent = new Intent();
				//	fileUri = getOutputMediaFileUrinew(MEDIA_TYPE_IMAGE);
//


				File folder = new File(image_path);

				//   uriAllFiles= new Uri[allFiles.length];

				if (!Global_Data.Default_Image_Path.equalsIgnoreCase("")) {

					if (discription.getText().toString() == null || discription.getText().toString().equals("")) {
						//Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					else
					{
						try
						{

							try
							{
								AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
								Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
								Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
								PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

								if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
								{
									Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

								}
								else
								if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
								{
									Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
									Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
								}

							}catch(Exception ex){ex.printStackTrace();}

							Long randomPIN = System.currentTimeMillis();
							String PINString = String.valueOf(randomPIN);

							loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME, RE_ID, Global_Data.GLOvel_USER_EMAIL,
									"", discription.getText().toString(),Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Default_Image_Path,PINString);

							if (folder.isDirectory()) {
								allFiles = folder.list();

								if(allFiles.length > 0)
								{
//							for (int i = 0; i < allFiles.length; i++) {
//								Log.d("all file path" + i, allFiles[i] + allFiles.length);
//							}

									Intent map = new Intent(Customer_Feed.this,SDcard_imageMain.class);
									startActivity(map);
									finish();
								}
								else
								{
									Toast.makeText(Customer_Feed.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
								}

							}
							else
							{
								Toast.makeText(Customer_Feed.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
							}

							//Global_Data.Default_Image_Path = fileUri.getPath();


						}catch (Exception ex){
							ex.printStackTrace();
						}


						SCAN_PATH=image_path;
						System.out.println(" SCAN_PATH  " +SCAN_PATH);

						Log.d("SCAN PATH", "Scan Path " + SCAN_PATH);
					}

				}
				else
				{
					try
					{
						File  mediaStorageDir = new File(
								//Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Config.IMAGE_DIRECTORY_NAME);
								Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_PICTURE");

						File foldern = new File(mediaStorageDir.getPath());
						if (foldern.isDirectory()) {
							allFiles = foldern.list();

							if(allFiles.length > 0)
							{
//							for (int i = 0; i < allFiles.length; i++) {
//								Log.d("all file path" + i, allFiles[i] + allFiles.length);
//							}

								Intent map = new Intent(Customer_Feed.this,SDcard_imageMain.class);
								startActivity(map);
								finish();
							}
							else
							{
								Toast.makeText(Customer_Feed.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
							}

						}
						else
						{
							Toast.makeText(Customer_Feed.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
						}

						//Global_Data.Default_Image_Path = fileUri.getPath();


					}catch (Exception ex){
						ex.printStackTrace();
					}


					SCAN_PATH=image_path;
					System.out.println(" SCAN_PATH  " +SCAN_PATH);

					Log.d("SCAN PATH", "Scan Path " + SCAN_PATH);
				}



				//File file = new File(SCAN_PATH);


//				Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//				i.setDataAndType(Uri.fromFile(new File(fileUri.getPath())), "image/*");
//				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//				startActivityForResult(i, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
				dialog.cancel();
//
			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Take Photo",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				image_option = "image";

				requestStoragePermission();

				dialog.cancel();
			}
		});

		//alertDialog.setCancelable(false);
		alertDialog.show();
	}

	public String encodeVideoFile(String filepath)
	{
		File tempFile = new File(filepath);
		String encodedString = null;

		StringBuilder sb = new StringBuilder();


		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(tempFile);

			byte[] bytes;
			int bSize = 3 * 512;
			byte[] buffer = new byte[bSize];
			int bytesRead;
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			try {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			bytes = output.toByteArray();
			encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);


		} catch (Exception e) {
			// TODO: handle exception
		}




		return encodedString;
	}

	private String encodeVideoFiletest(String fileName)
			throws IOException {

		File file = new File(fileName);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encode(bytes,Base64.DEFAULT);
		String encodedString = new String(encoded);

		return encodedString;
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int)length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "+file.getName());
		}

		is.close();
		return bytes;
	}

	private  class MediaperationBackGround extends AsyncTask<Bitmap, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(Bitmap... response) {
			System.gc();

			media_coden = getStringImage(response[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
			Customer_Feed.this.runOnUiThread(new Runnable() {
				public void run() {
					//dialog.dismiss();
				}
			});
			//dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {}

		@Override
		protected void onProgressUpdate(Void... values) {}
	}

	private  class MediaperationBackGroundVideo extends AsyncTask<String, Void, String> {



		@Override
		protected String doInBackground(String... response) {
			System.gc();

			try {
				media_coden = encodeVideoFile(response[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
			Customer_Feed.this.runOnUiThread(new Runnable() {
				public void run() {
					//dialog.dismiss();
				}
			});
			//dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {}

		@Override
		protected void onProgressUpdate(Void... values) {}
	}

	public  class Mediaperation extends AsyncTask<String, Void, String> {



		@Override
		protected String doInBackground(String... response) {
			System.gc();
			String reason_code = "";
			try {

				Customer_Feed.this.runOnUiThread(new Runnable() {
					public void run() {
						button1.setClickable(false);
						button1.setEnabled(false);
					}
				});




				String domain = "";
				String device_id = "";


//				if(response[0].equalsIgnoreCase("Image"))
//				{
//
//					Customer_Feed.this.runOnUiThread(new Runnable() {
//						public void run() {
//							media_coden = getStringImage(bitmap);
//						}
//					});
//				}
//				else
//				{
//					Customer_Feed.this.runOnUiThread(new Runnable() {
//						public void run() {
//							media_coden = encodeVideoFile(final_media_path);
//						}
//					});
//
//				}




				SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
				device_id = sp.getString("devid", "");

				  domain = getResources().getString(R.string.service_domain);



				JsonObjectRequest jsObjRequest = null;
				try
				{



					Log.d("Server url","Server url"+domain+"customer_service_media");


					JSONArray CUSTOMERSN = new JSONArray();
					JSONArray PICTURE = new JSONArray();
					//JSONObject product_value = new JSONObject();
					JSONObject product_value_n = new JSONObject();
					JSONArray product_imei = new JSONArray();

					final DataBaseHelper dbvoc = new DataBaseHelper(Customer_Feed.this);

					List<Local_Data> contacts = dbvoc.getAllRetailer_cre();

					for (Local_Data cn : contacts)
					{
						JSONObject product_value = new JSONObject();
						product_value.put("user_email", cn.getemail());
						product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
						product_value.put("name", cn.getCUSTOMER_NAME());
						product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
						product_value.put("address", cn.getADDRESS());
						product_value.put("street", cn.getSTREET());
						product_value.put("landmark", cn.getLANDMARK());
						product_value.put("pincode", cn.getPIN_CODE());
						product_value.put("mobile_no", cn.getMOBILE_NO());
						product_value.put("email", cn.getEMAIL_ADDRESS());
						product_value.put("status", cn.getSTATUS());
						product_value.put("state_code", cn.getSTATE_ID());
						product_value.put("city_code", cn.getCITY_ID());
						product_value.put("beat_code", cn.getBEAT_ID());
						product_value.put("vatin", cn.getvatin());
						product_value.put("latitude", cn.getlatitude());
						product_value.put("longitude", cn.getlongitude());
						CUSTOMERSN.put(product_value);

					}

					Long randomPIN = System.currentTimeMillis();
					String PINString = String.valueOf(randomPIN);

					JSONObject picture = new JSONObject();
					picture.put("code", PINString);
					picture.put("customer_code",response[3]);
					picture.put("user_email", response[4]);
					picture.put("media_type", response[0]);
					picture.put("media_text", response[2]);
					picture.put("media_data", media_coden);
					picture.put("filename", response[5]);
					PICTURE.put(picture);

					product_value_n.put("customers", CUSTOMERSN);
					product_value_n.put("media", PICTURE);
					product_value_n.put("imei_no", Global_Data.device_id);
					Log.d("customers Service",product_value_n.toString());

					jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"customer_service_media", product_value_n, new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject response) {
							Log.i("volley", "response: " + response);

							Log.d("jV", "JV length" + response.length());
							//JSONObject json = new JSONObject(new JSONTokener(response));
							try{


								if(response.has("result"))
								{
									response_result = response.getString("result");
								}
								else
								{
									response_result = "data";
								}

								//return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));


								if(response_result.equalsIgnoreCase("success")) {

									Customer_Feed.this.runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											button1.setClickable(true);
											button1.setEnabled(true);
										}
									});

									//Toast.makeText(Customer_Feed.this, "Media Upload Successfully.", Toast.LENGTH_LONG).show();

									String val = "";
									dbvoc.updateCustomerby_CreateAt(val);

									try {


										File file = new File(Global_Data.Default_Image_Path);
										if (file.exists()) {
//
											file.delete();
											dbvoc.getDeleteMediaBYID(Global_Data.Default_Image_Path);


										}
									}catch(Exception ex){ex.printStackTrace();}

									Customer_Feed.this.runOnUiThread(new Runnable() {
										public void run() {
											Toast toast = Toast.makeText(Customer_Feed.this, "Media Upload Successfully.", Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();
										}
									});


									Intent a = new Intent(Customer_Feed.this,Order.class);
									startActivity(a);
									finish();


								}
								else
								{

									Customer_Feed.this.runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											button1.setClickable(true);
											button1.setEnabled(true);
											Toast toast = Toast.makeText(Customer_Feed.this,response_result, Toast.LENGTH_SHORT);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();
										}
									});


//                                    Intent a = new Intent(context,Order.class);
//                                    context.startActivity(a);
//                                    ((Activity)context).finish();
								}

								//  finish();
								// }

								// output.setText(data);
							}catch(JSONException e){e.printStackTrace();

								Customer_Feed.this.runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
									}
								});
							}


							Customer_Feed.this.runOnUiThread(new Runnable() {
								public void run() {
									dialog.dismiss();
								}
							});
							//dialog.dismiss();
							//dialog.dismiss();




						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							Log.i("volley", "error: " + error);
							//Toast.makeText(Customer_Feed.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

							Customer_Feed.this.runOnUiThread(new Runnable() {
								public void run() {
									Toast toast = Toast.makeText(Customer_Feed.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
							});



							try {
								String responseBody = new String(error.networkResponse.data, "utf-8" );
								JSONObject jsonObject = new JSONObject( responseBody );
							}
							catch ( JSONException e ) {
								//Handle a malformed json response
							} catch (UnsupportedEncodingException errorn){

							}
							catch(Exception ex){
								ex.printStackTrace();
							}


							Customer_Feed.this.runOnUiThread(new Runnable() {
								public void run() {
									dialog.dismiss();
									button1.setClickable(true);
									button1.setEnabled(true);
								}
							});

						}
					});



					RequestQueue requestQueue = Volley.newRequestQueue(Customer_Feed.this);

					int socketTimeout = 2000000;//90 seconds - change to what you want
					RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
					jsObjRequest.setRetryPolicy(policy);
					// requestQueue.se
					//requestQueue.add(jsObjRequest);
					jsObjRequest.setShouldCache(false);
					requestQueue.getCache().clear();
					requestQueue.add(jsObjRequest);

				}catch(Exception e)
				{
					e.printStackTrace();

					Customer_Feed.this.runOnUiThread(new Runnable() {
						public void run() {
							dialog.dismiss();
						}
					});

				}





				//createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
				//createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}



			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests

                }
			 */

			} catch (Exception e) {
				// TODO: handle exception
				Log.e("DATA", e.getMessage());
			}




			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
			Customer_Feed.this.runOnUiThread(new Runnable() {
				public void run() {
					//dialog.dismiss();
				}
			});
			//dialog.dismiss();
		}

		@Override
		protected void onPreExecute() {}

		@Override
		protected void onProgressUpdate(Void... values) {}
	}



	public  void SyncMediaData(String media_type,String media_code,String discription,String CUSTOMER_ID,String GLOvel_USER_EMAIL,String file_name)
	{
		System.gc();
		String reason_code = "";
		try {

			button1.setClickable(false);
			button1.setEnabled(false);

//			dialog = new ProgressDialog(Customer_Feed.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
//			dialog.setMessage("Please wait....");
//			dialog.setTitle("Metal");
//			dialog.setCancelable(false);
//			dialog.show();

			String domain = "";
			String device_id = "";


//			if(media_type.equalsIgnoreCase("Image"))
//			{
//
//				Customer_Feed.this.runOnUiThread(new Runnable() {
//					public void run() {
//						media_coden = getStringImage(bitmap);
//					}
//				});
//			}
//			else
//			{
//				Customer_Feed.this.runOnUiThread(new Runnable() {
//					public void run() {
//						media_coden = encodeVideoFile(final_media_path);
//					}
//				});
//
//			}



			SharedPreferences sp = getSharedPreferences("SimpleLogic", MODE_PRIVATE);
			device_id = sp.getString("devid", "");

			domain = getResources().getString(R.string.service_domain);



			JsonObjectRequest jsObjRequest = null;
			try
			{



				Log.d("Server url","Server url"+domain+"customer_service_media");


				JSONArray CUSTOMERSN = new JSONArray();
				JSONArray PICTURE = new JSONArray();
				//JSONObject product_value = new JSONObject();
				JSONObject product_value_n = new JSONObject();
				JSONArray product_imei = new JSONArray();

				final DataBaseHelper dbvoc = new DataBaseHelper(Customer_Feed.this);

				List<Local_Data> contacts = dbvoc.getAllRetailer_cre();

				for (Local_Data cn : contacts)
				{
					JSONObject product_value = new JSONObject();
					product_value.put("user_email", cn.getemail());
					product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
					product_value.put("name", cn.getCUSTOMER_NAME());
					product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
					product_value.put("address", cn.getADDRESS());
					product_value.put("street", cn.getSTREET());
					product_value.put("landmark", cn.getLANDMARK());
					product_value.put("pincode", cn.getPIN_CODE());
					product_value.put("mobile_no", cn.getMOBILE_NO());
					product_value.put("email", cn.getEMAIL_ADDRESS());
					product_value.put("status", cn.getSTATUS());
					product_value.put("state_code", cn.getSTATE_ID());
					product_value.put("city_code", cn.getCITY_ID());
					product_value.put("beat_code", cn.getBEAT_ID());
					product_value.put("vatin", cn.getvatin());
					product_value.put("latitude", cn.getlatitude());
					product_value.put("longitude", cn.getlongitude());
					CUSTOMERSN.put(product_value);
				}

				Long randomPIN = System.currentTimeMillis();
				String PINString = String.valueOf(randomPIN);

				JSONObject picture = new JSONObject();
				picture.put("code",PINString);
				picture.put("customer_code",CUSTOMER_ID);
				picture.put("user_email", GLOvel_USER_EMAIL);
				picture.put("media_type", media_type);
				picture.put("media_text", discription);
				picture.put("media_data", media_coden);
				picture.put("filename", file_name);
				PICTURE.put(picture);

				product_value_n.put("customers", CUSTOMERSN);
				product_value_n.put("media", PICTURE);
				product_value_n.put("imei_no", Global_Data.device_id);
				Log.d("customers Service",product_value_n.toString());

				jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"customer_service_media", product_value_n, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.i("volley", "response: " + response);

						Log.d("jV", "JV length" + response.length());
						//JSONObject json = new JSONObject(new JSONTokener(response));
						try{

							String response_result = "";
							if(response.has("result"))
							{
								response_result = response.getString("result");
							}
							else
							{
								response_result = "data";
							}


							if(response_result.equalsIgnoreCase("success")) {

								dialog.dismiss();
								button1.setClickable(true);
								button1.setEnabled(true);
								//Toast.makeText(Customer_Feed.this, "Media Upload Successfully.", Toast.LENGTH_LONG).show();

								String val = "";
								dbvoc.updateCustomerby_CreateAt(val);

								try {
									File file = new File(Global_Data.Default_video_Path);
									if (file.exists()) {

										file.delete();
										dbvoc.getDeleteMediaBYID(Global_Data.Default_video_Path);
									}
								}catch(Exception ex){ex.printStackTrace();}


								Toast toast = Toast.makeText(Customer_Feed.this, "Media Upload Successfully.", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

								Intent a = new Intent(Customer_Feed.this,Order.class);
								startActivity(a);
								finish();


							}
							else
							{

								dialog.dismiss();
								button1.setClickable(true);
								button1.setEnabled(true);
								Toast toast = Toast.makeText(Customer_Feed.this,response_result, Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
//                                    Intent a = new Intent(context,Order.class);
//                                    context.startActivity(a);
//                                    ((Activity)context).finish();
							}

							//  finish();
							// }

							// output.setText(data);
						}catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


						dialog.dismiss();
						dialog.dismiss();




					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.i("volley", "error: " + error);
						//Toast.makeText(Customer_Feed.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(Customer_Feed.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();


						try {
							String responseBody = new String(error.networkResponse.data, "utf-8" );
							JSONObject jsonObject = new JSONObject( responseBody );
						}
						catch ( JSONException e ) {
							//Handle a malformed json response
						} catch (UnsupportedEncodingException errorn){

						}
						catch(Exception ex){
							ex.printStackTrace();
						}


						dialog.dismiss();
						button1.setClickable(true);
						button1.setEnabled(true);
					}
				});



				RequestQueue requestQueue = Volley.newRequestQueue(Customer_Feed.this);

				int socketTimeout = 2000000;//90 seconds - change to what you want
				RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
				jsObjRequest.setRetryPolicy(policy);
				// requestQueue.se
				//requestQueue.add(jsObjRequest);
				jsObjRequest.setShouldCache(false);
				requestQueue.getCache().clear();
				requestQueue.add(jsObjRequest);

			}catch(Exception e)
			{
				e.printStackTrace();
				dialog.dismiss();
			}





			//createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
			//createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}



			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests

                }
			 */

		} catch (Exception e) {
			// TODO: handle exception
			Log.e("DATA", e.getMessage());
		}
	}

	public static void addImageToGallery(final String filePath, final Context context) {

		ContentValues values = new ContentValues();

		values.put(MediaStore.Images.Media.DATE_TAKEN, "IMG_"+System.currentTimeMillis());
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		values.put(MediaStore.MediaColumns.DATA, filePath);

		context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}

	public static void addVideoToGallery(final String filePath, final Context context) {

		ContentValues values = new ContentValues();

		values.put(MediaStore.Images.Media.DATE_TAKEN, "VID_"+System.currentTimeMillis());
		values.put(MediaStore.Images.Media.MIME_TYPE, "video/.mp4");
		values.put(MediaStore.MediaColumns.DATA, filePath);

		context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}

	private Uri getUri() {
		String state = Environment.getExternalStorageState();
		if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
			return MediaStore.Video.Media.INTERNAL_CONTENT_URI;

		return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	}

	private void startScan()
	{
		Log.d("Connected","success"+conn);
		if(conn!=null)
		{
			conn.disconnect();
		}
		conn = new MediaScannerConnection(this,this);
		conn.connect();
	}

	@Override
	public void onMediaScannerConnected() {
		Log.d("onMediaScannerConnected","success"+conn);
		conn.scanFile(SCAN_PATH, FILE_TYPE);
	}
	@Override
	public void onScanCompleted(String path, Uri uri) {
		try {
			//Log.d("onScanCompleted",uri + "success"+conn);
			System.out.println("URI " + uri);
			if (uri != null)
			{
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				intent.setData(uri);
//				startActivity(intent);

				Intent i=new Intent();
				i.setAction(Intent.ACTION_VIEW);
				i.setDataAndType(uri, "image/*");
				startActivity(i);




			}
		} finally
		{
			//conn.disconnect();
			//conn = null;
		}
	}

	/**
	 * Requesting multiple permissions (storage and location) at once
	 * This uses multiple permission model from dexter
	 * On permanent denial opens settings dialog
	 */
	private void requestStoragePermission() {

		Dexter.withActivity(this)
				.withPermissions(
						Manifest.permission.CAMERA,
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						// check if all permissions are granted
						if (report.areAllPermissionsGranted()) {
							B_flag = isDeviceSupportCamera();

							if(B_flag == true)
							{
								Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

								fileUri = getOutputMediaFileUrinew(MEDIA_TYPE_IMAGE);

								Global_Data.Default_Image_Path = fileUri.getPath();

								intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

								// performCrop(fileUri);
								// start the image capture Intent
								startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

//					File mediaStorageDir = new File(
//							Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
//							Config.IMAGE_DIRECTORY_NAME+"/"+"CUSTOMER_SERVICES");
//
//					try {
//						//delete(mediaStorageDir);
//						if (mediaStorageDir.isDirectory())
//						{
//							String[] children = mediaStorageDir.list();
//							for (int i = 0; i < children.length; i++)
//							{
//								new File(mediaStorageDir, children[i]).delete();
//							}
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}

//					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//					fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//
//			        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//	                startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_CAMERA);
							}
							else
							{
								Toast.makeText(getApplicationContext(), "no camera on this device", Toast.LENGTH_LONG).show();
							}
						}

						// check for permanent denial of any permission
						if (report.isAnyPermissionPermanentlyDenied()) {
							// show alert dialog navigating to Settings
							showSettingsDialog();
						}
					}

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
						token.continuePermissionRequest();
					}
				}).
				withErrorListener(new PermissionRequestErrorListener() {
					@Override
					public void onError(DexterError error) {
						Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
					}
				})
				.onSameThread()
				.check();
	}

	private void requestStoragePermissionvideo() {

		Dexter.withActivity(this)
				.withPermissions(
						Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						// check if all permissions are granted
						if (report.areAllPermissionsGranted()) {

							get_dialogC_Video();

						}

						// check for permanent denial of any permission
						if (report.isAnyPermissionPermanentlyDenied()) {
							// show alert dialog navigating to Settings
							showSettingsDialog();
						}
					}

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
						token.continuePermissionRequest();
					}
				}).
				withErrorListener(new PermissionRequestErrorListener() {
					@Override
					public void onError(DexterError error) {
						Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
					}
				})
				.onSameThread()
				.check();
	}

	/**
	 * Showing Alert Dialog with Settings option
	 * Navigates user to app settings
	 * NOTE: Keep proper title and message depending on your app
	 */
	private void showSettingsDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(Customer_Feed.this);
		builder.setTitle("Need Permissions");
		builder.setCancelable(false);
		builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
		builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				openSettings();
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();

	}

	// navigating user to app settings
	private void openSettings() {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		Uri uri = Uri.fromParts("package", getPackageName(), null);
		intent.setData(uri);
		startActivityForResult(intent, 101);
	}

	private void requestGPSPermissionsigna() {

		Dexter.withActivity(this)
				.withPermissions(
						Manifest.permission.ACCESS_FINE_LOCATION
				)
				.withListener(new MultiplePermissionsListener() {
					@Override
					public void onPermissionsChecked(MultiplePermissionsReport report) {
						// check if all permissions are granted
						if (report.areAllPermissionsGranted()) {

							gps = new GPSTracker(Customer_Feed.this);
							if(!gps.canGetLocation()){

								gps.showSettingsAlertnew();
							}
							else
							{
								List<Local_Data> contacts = dbvoc.getRetailer(RE_TEXT);
								for (Local_Data cn : contacts) {

									RE_ID = cn.get_Retailer_id();
								}

								if (CP_NAME.equals("video") || CP_NAME.equals("Image")) {

									try {
										if (CP_NAME.equals("video")) {

											if (video_option.equalsIgnoreCase("Gallery") && (selectedPath == null || selectedPath.equals(""))) {
												//Toast.makeText(getApplicationContext(), "Please Capture Media First", Toast.LENGTH_LONG).show();
												Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Video First", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();
											} else if(fileUri.getPath() == null || fileUri.getPath().equals("")) {
												//Toast.makeText(getApplicationContext(), "Please Capture Media First", Toast.LENGTH_LONG).show();

												Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Video First", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();
											} else if (discription.getText().toString() == null || discription.getText().toString().equals("")) {
												//Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG).show();

												Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Description.", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();

											} else {

												//Toast.makeText(getApplicationContext(), discription.getText().toString(), Toast.LENGTH_LONG).show();

												//image_url = getStringImage(bitmap);
												//new UploadFileToServer().execute();

												//executeMultipartPost();LoadDatabaseAsyncTask
												media_text = discription.getText().toString();

												if (video_option.equalsIgnoreCase("Gallery")) {
													final_media_path = selectedPath;
												} else {
													final_media_path = fileUri.getPath();
												}

												filename = final_media_path.substring(final_media_path.lastIndexOf("/") + 1);


												AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update

												alertDialog.setMessage("If you want to save Video offline press Save Offline Button ?");
//}

												alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Save Offline ",new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialogn, int which) {
														// TODO Auto-generated method stub

														try
														{
															AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
															Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
															Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
															PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

															if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
															{
																Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

															}
															else
															if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
															{
																Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
																Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
															}

														}catch(Exception ex){ex.printStackTrace();}


														Long randomPIN = System.currentTimeMillis();
														String PINString = String.valueOf(randomPIN);

														loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME, RE_ID, Global_Data.GLOvel_USER_EMAIL,
																"", discription.getText().toString(),Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Default_video_Path,PINString);

														Intent intent = new Intent(Customer_Feed.this, Order.class);
														startActivity(intent);
														overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
														finish();

														Toast toast = Toast.makeText(getApplicationContext(), "Save Successfully.", Toast.LENGTH_LONG);
														toast.setGravity(Gravity.CENTER, 0, 0);
														toast.show();

														dialogn.cancel();

													}
												});

												alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Online Sync",new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialogn, int which) {


														isInternetPresent = cd.isConnectingToInternet();
														if (isInternetPresent)
														{
															button1.setClickable(false);
															button1.setEnabled(false);

															response_result = "";

															runOnUiThread(new Runnable()
															{
																public void run()
																{
																	dialog = new ProgressDialog(Customer_Feed.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
																	dialog.setMessage("Please wait....");
																	dialog.setTitle("Smart Anchor App");
																	dialog.setCancelable(false);
																	dialog.show();
																}
															});



															//media_coden = encodeVideoFile(final_media_path);

															SyncMediaData(CP_NAME, video_code, discription.getText().toString(), Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_USER_EMAIL, filename);

//									new Mediaperation().execute(CP_NAME, video_code, discription.getText().toString(), Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_USER_EMAIL, filename);
														}
														else
														{

															try
															{
																AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
																Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
																Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
																PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

																if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
																{
																	Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

																}
																else
																if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
																{
																	Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
																	Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
																}

															}catch(Exception ex){ex.printStackTrace();}


															Long randomPIN = System.currentTimeMillis();
															String PINString = String.valueOf(randomPIN);

															loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME, RE_ID, Global_Data.GLOvel_USER_EMAIL,
																	"", discription.getText().toString(),Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Default_video_Path,PINString);

															Toast toast = Toast.makeText(getApplicationContext(), "No Internet. Data save on your phone. Please Sync when network available.", Toast.LENGTH_LONG);
															toast.setGravity(Gravity.CENTER, 0, 0);
															toast.show();

															Intent intent = new Intent(Customer_Feed.this, Order.class);
															startActivity(intent);
															overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
															finish();


														}


														dialogn.cancel();
													}
												});

												alertDialog.setCancelable(false);
												alertDialog.show();

												//new LoadDatabaseAsyncTask().execute();
												//uploadFile();





											}

										} else {
											if (image_option.equalsIgnoreCase("Gallery") && (picturePath == null || picturePath.equals(""))) {
												//	Toast.makeText(getApplicationContext(), "Please Capture Media First", Toast.LENGTH_LONG).show();

												Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();

											} else if (fileUri.getPath() == null || fileUri.getPath().equals("") || fileUri.equals("")) {
												//Toast.makeText(getApplicationContext(), "Please Capture Media First", Toast.LENGTH_LONG).show();
												Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Image First", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();

											} else if (discription.getText().toString() == null || discription.getText().toString().equals("")) {
												//Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG).show();

												Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();
											} else {

												//Toast.makeText(getApplicationContext(), discription.getText().toString(), Toast.LENGTH_LONG).show();
												image_url = "";


//									loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME,Global_Data.GLOvel_CUSTOMER_ID,Global_Data.GLOvel_USER_EMAIL,
//											image_url,discription.getText().toString(), Current_Date, Current_Date);
//									//get_dialogC();


												media_text = discription.getText().toString();
												if (image_option.equalsIgnoreCase("Gallery")) {
													final_media_path = picturePath;
												} else {
													final_media_path = fileUri.getPath();
												}

												filename = final_media_path.substring(final_media_path.lastIndexOf("/") + 1);


												AlertDialog alertDialog = new AlertDialog.Builder(Customer_Feed.this).create(); //Read Update

												alertDialog.setMessage("If you want to save image offline press Save Offline Button ?");
												//}

												alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Save Offline ",new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialogn, int which) {
														// TODO Auto-generated method stub

														try
														{
															AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
															Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
															Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
															PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

															if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
															{
																Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

															}
															else
															if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
															{
																Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
																Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
															}

														}catch(Exception ex){ex.printStackTrace();}

														Long randomPIN = System.currentTimeMillis();
														String PINString = String.valueOf(randomPIN);

														loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME, RE_ID, Global_Data.GLOvel_USER_EMAIL,"", discription.getText().toString(),Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Default_Image_Path,PINString);

														Intent intent = new Intent(Customer_Feed.this, Order.class);
														startActivity(intent);
														overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
														finish();

														Toast toast = Toast.makeText(getApplicationContext(), "Save Successfully.", Toast.LENGTH_LONG);
														toast.setGravity(Gravity.CENTER, 0, 0);
														toast.show();
														dialogn.cancel();

													}
												});

												alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Online Sync",new DialogInterface.OnClickListener() {

													@Override
													public void onClick(DialogInterface dialogn, int which) {


														isInternetPresent = cd.isConnectingToInternet();
														if (isInternetPresent)
														{
															button1.setClickable(false);
															button1.setEnabled(false);
															response_result = "";

															dialog = new ProgressDialog(Customer_Feed.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
															dialog.setMessage("Please wait....");
															dialog.setTitle("Smart Anchor App");
															dialog.setCancelable(false);
															dialog.show();


															//media_coden = getStringImage(bitmap);





															new Mediaperation().execute(CP_NAME, image_url, discription.getText().toString(), Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_USER_EMAIL, filename);

//									SyncMediaData(CP_NAME, image_url, discription.getText().toString(), Global_Data.GLOvel_CUSTOMER_ID, Global_Data.GLOvel_USER_EMAIL, filename);
														}
														else
														{
															//Toast.makeText(getApplicationContext(),"No Internet. Data saved on your phone. Please Sync when network available.",Toast.LENGTH_LONG).show();

															try
															{
																AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
																Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
																Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
																PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

																if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
																{
																	Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

																}
																else
																if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
																{
																	Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
																	Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
																}

															}catch(Exception ex){ex.printStackTrace();}

															Long randomPIN = System.currentTimeMillis();
															String PINString = String.valueOf(randomPIN);

															loginDataBaseAdapter.insertCustomerServiceMedia("",Global_Data.GLOvel_CUSTOMER_ID,CP_NAME, RE_ID, Global_Data.GLOvel_USER_EMAIL,
																	"", discription.getText().toString(),Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Default_Image_Path,PINString);

															Intent intent = new Intent(Customer_Feed.this, Order.class);
															startActivity(intent);
															overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
															finish();
															Toast toast = Toast.makeText(getApplicationContext(), "No Internet. Data save on your phone. Please Sync when network available.", Toast.LENGTH_LONG);
															toast.setGravity(Gravity.CENTER, 0, 0);
															toast.show();
														}


														dialogn.cancel();
													}
												});

												alertDialog.setCancelable(false);
												alertDialog.show();




												//new LoadDatabaseAsyncTask().execute();


											}
										}


									} catch (Exception e) {
										e.printStackTrace();
										//Toast.makeText(getApplicationContext(), "Please Capture Media First", Toast.LENGTH_LONG).show();

										Toast toast = Toast.makeText(getApplicationContext(), "Please Capture Media First", Toast.LENGTH_LONG);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();

//						Intent intent = new Intent(Customer_Feed.this, Order.class);
//						intent.putExtra("filePath", "");
//
//						startActivity(intent);
//						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
									}
								} else {
									//discription.setVisibility(View.VISIBLE);
									//Toast.makeText(getApplicationContext(), new_feedback.getText().toString(), Toast.LENGTH_LONG).show();
									if (CP_NAME.equals("Feedback")) {

										if (new_feedback.getText().toString() == null || new_feedback.getText().toString().equals("")) {
											//Toast.makeText(getApplicationContext(), "Please Enter Feedback Description", Toast.LENGTH_LONG).show();


											Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Feedback Description", Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();

										} else {
											try {

												try
												{

													Log.d("Play LAT LOG","Play LAT LOG"+PlayServiceManager.getLatitude()+" "+ PlayServiceManager.getLongitude());


													AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
													Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
													Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
													PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

													if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
													{
														Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

													}
													else
													if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
													{
														Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
														Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
													}

												}catch(Exception ex){ex.printStackTrace();}

												Long randomPIN = System.currentTimeMillis();
												String PINString = String.valueOf(randomPIN);
												loginDataBaseAdapter.insertCustomer_Service_Feedbacks("1", Global_Data.GLOvel_CUSTOMER_ID, RE_ID, Global_Data.GLOvel_USER_EMAIL,
														Current_Date, new_feedback.getText().toString(), "Active", Current_Date, "User1", "User1",
														Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,PINString);

												String gaddress = "";
												try {
													if (Global_Data.address.equalsIgnoreCase("null")) {
														gaddress = "";
													} else {
														gaddress = Global_Data.address;
													}
												}catch(Exception ex){ex.printStackTrace();}

												String sms_body = "";
												sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" There is a feedback from  " + Global_Data.CUSTOMER_NAME_NEW + " about  " +  new_feedback.getText().toString() + ". This is to keep you updated."+"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;

												if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
												{
													//	Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, Customer_Feed.this);
													// mobile_numbers.add(Global_Data.cus_MAnager_mobile);
												}

												//call_service_FORCUSS("Feedback");

												//Toast.makeText(getApplicationContext(), new_feedback.getText().toString(), Toast.LENGTH_LONG).show();
												//Toast.makeText(getApplicationContext(), "Your Data Save Successfuly", Toast.LENGTH_LONG).show();
												Toast toast = Toast.makeText(getApplicationContext(), "Your Data Save Successfuly", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();

												Intent intent = new Intent(Customer_Feed.this, Order.class);
												startActivity(intent);
												overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
												finish();

											} catch (Exception e) {
												e.printStackTrace();
											}
											//String D_TEXT = new_feedback.getText().toString();


										}
									} else if (CP_NAME.equals("Claim")) {

										if (claim_amount.getText().toString() == null || claim_amount.getText().toString().equals("")) {
											//Toast.makeText(getApplicationContext(), "Please Enter Claim Amount", Toast.LENGTH_LONG).show();
											Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Claim Amount", Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();
										} else if (discription.getText().toString() == null || discription.getText().toString().equals("")) {
											//Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG).show();

											Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Description", Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();

										} else {
											try {

												try
												{
													AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
													Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
													Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
													PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

													if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
													{
														Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

													}
													else
													if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
													{
														Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
														Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
													}

												}catch(Exception ex){ex.printStackTrace();}

												Long randomPIN = System.currentTimeMillis();
												String PINString = String.valueOf(randomPIN);
												loginDataBaseAdapter.insertCustomerServiceClaims("1", Global_Data.GLOvel_CUSTOMER_ID, "Customer", RE_ID,
														Global_Data.GLOvel_USER_EMAIL, Current_Date, discription.getText().toString(), claim_amount.getText().toString(), "Active", "10000", Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,PINString);

												//Toast.makeText(getApplicationContext(), new_feedback.getText().toString(), Toast.LENGTH_LONG).show();

												String gaddress = "";
												try {
													if (Global_Data.address.equalsIgnoreCase("null")) {
														gaddress = "";
													} else {
														gaddress = Global_Data.address;
													}
												}catch(Exception ex){ex.printStackTrace();}

												String sms_body = "";
												sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" There is a claim from  " + Global_Data.CUSTOMER_NAME_NEW + " for Rs.  " + claim_amount.getText().toString() + " regarding " + discription.getText().toString() + ". This is to keep you updated."+"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;

												if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
												{
													//				Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, Customer_Feed.this);
													// mobile_numbers.add(Global_Data.cus_MAnager_mobile);
												}

												//	Toast.makeText(getApplicationContext(), "Your Data Save Successfuly", Toast.LENGTH_LONG).show();

												Toast toast = Toast.makeText(getApplicationContext(), "Your Data Save Successfuly", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();

												Intent intent = new Intent(Customer_Feed.this, Order.class);
												startActivity(intent);
												overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
												finish();

											} catch (Exception e) {
												e.printStackTrace();
											}
											//String D_TEXT = new_feedback.getText().toString();


										}
									} else if (CP_NAME.equals("Complaints")) {

										if (new_complaints.getText().toString() == null || new_complaints.getText().toString().equals("")) {
											//Toast.makeText(getApplicationContext(), "Please Enter Complaints", Toast.LENGTH_LONG).show();

											Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Complaints", Toast.LENGTH_LONG);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();

										} else {
											try {

												try
												{
													AppLocationManager appLocationManager = new AppLocationManager(Customer_Feed.this);
													Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
													Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
													PlayService_Location PlayServiceManager = new PlayService_Location(Customer_Feed.this);

													if(PlayServiceManager.checkPlayServices(Customer_Feed.this))
													{
														Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

													}
													else
													if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
													{
														Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
														Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
													}

												}catch(Exception ex){ex.printStackTrace();}

												Long randomPIN = System.currentTimeMillis();
												String PINString = String.valueOf(randomPIN);

												loginDataBaseAdapter.insertCustomer_Service_Complaints("1", Global_Data.GLOvel_CUSTOMER_ID, RE_ID, Global_Data.GLOvel_USER_EMAIL, Current_Date, new_complaints.getText().toString(), "Active", Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,PINString);

												//Toast.makeText(getApplicationContext(), new_complaints.getText().toString(), Toast.LENGTH_LONG).show();

												String gaddress = "";
												try {
													if (Global_Data.address.equalsIgnoreCase("null")) {
														gaddress = "";
													} else {
														gaddress = Global_Data.address;
													}
												}catch(Exception ex){ex.printStackTrace();}

												String sms_body = "";
												sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" There is a complaint  from  " + Global_Data.CUSTOMER_NAME_NEW + " about  " +  new_complaints.getText().toString() + ". This is to keep you updated."+"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;

												if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
												{
													//				Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, Customer_Feed.this);
													// mobile_numbers.add(Global_Data.cus_MAnager_mobile);
												}

												//Toast.makeText(getApplicationContext(), "Your Data Save Successfuly", Toast.LENGTH_LONG).show();
												Toast toast = Toast.makeText(getApplicationContext(), "Your Data Save Successfuly", Toast.LENGTH_LONG);
												toast.setGravity(Gravity.CENTER, 0, 0);
												toast.show();

												Intent intent = new Intent(Customer_Feed.this, Order.class);
												startActivity(intent);
												overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
												finish();

											} catch (Exception e) {
												e.printStackTrace();
											}
											//String D_TEXT = new_feedback.getText().toString();


										}
									}


								}
							}
						}

						// check for permanent denial of any permission
						if (report.isAnyPermissionPermanentlyDenied()) {
							// show alert dialog navigating to Settings
							showSettingsDialog();
						}
					}

					@Override
					public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
						token.continuePermissionRequest();
					}
				}).
				withErrorListener(new PermissionRequestErrorListener() {
					@Override
					public void onError(DexterError error) {
						Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
					}
				})
				.onSameThread()
				.check();
	}





}
