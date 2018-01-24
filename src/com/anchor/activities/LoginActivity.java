package com.anchor.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.model.User;
import com.anchor.service.LocationServices;
import com.anchor.services.getServices;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.BCrypt;
import cpm.simplelogic.helper.CheckNullValue;
import cpm.simplelogic.helper.GPSTracker;

public class LoginActivity extends Activity{
	ProgressDialog progress;
	private Bitmap bitmap = null;
	Handler h;
	public static final String Code = "codeKey";
	String devid, usr_name, pwd, usr_email;
	ArrayList<HashMap<String, String>> arraylist1, arraylist2;
	ConnectivityManager cn;
	NetworkInfo nf;
	ProgressDialog dialog;
	// Internet detector
	private String Current_Date = "";
	Boolean isInternetPresent = false;
	TextView textViewVersion, link_fpwd;
	ConnectionDetector cd;
    GPSTracker gps;
	String result = "";
	InputStream inputStream;
	SessionManager session;
	Button buttonLogin,buttonReg;
	EditText editText1,editText2,emp_code;
	TelephonyManager manager;
	String simSerial="f8a0f77d096fddc3",firstName="",lastName="";
	User u;
	String current_date, usremail;
	Bitmap blob_data_logo;
	LoginDataBaseAdapter loginDataBaseAdapter;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
    ImageView logo_img;
	SharedPreferences sharedpreferences;
	public static final String mypreference = "mypref";

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		MultiDex.install(this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

        logo_img=(ImageView)findViewById(R.id.imageView1);
		link_fpwd = (TextView) findViewById(R.id.forget_pwd);

		SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
		String logostr=spf1.getString("logo_data", "");

    if(logostr.length()>0){
	   byte[] decodedString = Base64.decode(logostr, Base64.DEFAULT);
	   blob_data_logo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	   logo_img.setImageBitmap(blob_data_logo);
    }

		SharedPreferences pref_devid = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		pref_devid.getString("reg_devid", "");
		devid = pref_devid.getString("reg_devid", "");

		if (devid.length() > 0) {
			link_fpwd.setVisibility(View.VISIBLE);
		} else {
			link_fpwd.setVisibility(View.GONE);
		}

		link_fpwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, Forget_Pwd.class));
			}
		});

		//getBitmapFromURL("http://developersfound.com/me.png");

//		Base64CODEC base64CODEC = new Base64CODEC();
//
//		try {
//			URL url = new URL("http://developersfound.com/me.png");
//			Bitmap image = BitmapFactory.decodeStream(url.openStream());
//			logo_img.setImageBitmap(image);
//		} catch(IOException e) {
//			System.out.println(e);
//		}

//		try {
//			//bitmap = base64CODEC.Base64ImageFromURL("http://developersfound.com/me.png");
//			String base64String = base64CODEC.convertToBase64(bitmap);
//			//logo_img = (ImageView) findViewById(R.id.imgViewDestination);
//			Bitmap imgViewDestinationBitmap = base64CODEC.convertToBitmap(base64String);
//			logo_img.setImageBitmap(imgViewDestinationBitmap);
//		}
//		catch (NullPointerException e) {
//			e.printStackTrace();
//		}

		 dialog = new ProgressDialog(LoginActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		 cd = new ConnectionDetector(getApplicationContext());
		 gps = new GPSTracker(LoginActivity.this);

		 Global_Data.LOCATION_SERVICE_HIT = "TRUE";

		 if(!gps.canGetLocation()){
			// can't get location
	        	// GPS or Network is not enabled
	        	// Ask user to enable GPS/network in settings
	        	gps.showSettingsAlert();
		 }

    	 Calendar c = Calendar.getInstance();
         SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
         String strDate = sdf.format(c.getTime());
         Current_Date = sdf.format(c.getTime());
         h = new Handler();

		 // Session Manager
         session = new SessionManager(getApplicationContext());
        
         arraylist1 = new ArrayList<HashMap<String,String>>();
         arraylist2 = new ArrayList<HashMap<String,String>>();
        
           cn=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
           nf=cn.getActiveNetworkInfo();

		  TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		  Global_Data.imei_no = tm.getDeviceId();

		        textViewVersion =(TextView) findViewById(R.id.textViewVersion);
				buttonLogin=(Button) findViewById(R.id.buttonLogin);
				buttonReg=(Button) findViewById(R.id.buttonReg);
				editText1=(EditText) findViewById(R.id.editText1);
				editText2=(EditText) findViewById(R.id.editText2);
		        emp_code=(EditText) findViewById(R.id.emp_code);

		sharedpreferences = getSharedPreferences("SimpleLogic",
				Context.MODE_PRIVATE);
		if (sharedpreferences.contains(Code)) {
			Global_Data.emp_code=sharedpreferences.getString(Code, "");
			emp_code.setText(Global_Data.emp_code);
		}

//				editText1.setText("swatiyamgar");
//				editText2.setText("swati12345");

		   //     editText1.setText("aakash");
			//	editText2.setText("aakash12345");
				PackageInfo pInfo = null;
				try {
					pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
					String version = pInfo.versionName;
					textViewVersion.setText("Mobile Sales App, v. "+version);
				} catch (PackageManager.NameNotFoundException e) {
					e.printStackTrace();
					textViewVersion.setText("Mobile Sales App, v. 1.3.1");
				}

			    SharedPreferences spf=LoginActivity.this.getSharedPreferences("SimpleLogic",0);        
		        SharedPreferences.Editor editor=spf.edit();        
		       // editor.putString("UserID", "admin");
		       // editor.putString("pwd", "test");
		       // editor.putFloat("Target", 5000);
		        //editor.putString("SimID", simSerial);
		        editor.commit();

//		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
//		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//		// Check if enabled and if not send user to the GPS settings
//		if (!enabled) {
//			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

//			startActivity(intent);
//		}

		         // create a instance of SQLite Database
			     loginDataBaseAdapter=new LoginDataBaseAdapter(this);
			     loginDataBaseAdapter=loginDataBaseAdapter.open();
			     
			     //Reading all 
	   	         List<Local_Data> contacts = dbvoc.getAllMain();      
	   	          for (Local_Data cn : contacts) {
	   	        	Global_Data.local_user = ""+cn.getUser();
	   	        	Global_Data.local_pwd = ""+cn.getPwd();
	   	        	System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
	   	        	//Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
	   	        	                             }
		//manager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				//int simState=manager.getSimState();

//		SharedPreferences pref_usrnm = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//		usr_email=pref_usrnm.getString("login_email", "");
//
//		if(devid.length()>0)
//		{
//			link_fpwd.setVisibility(View.VISIBLE);
//		}else{
//			link_fpwd.setVisibility(View.GONE);
//		}

//		link_fpwd.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View view)
//			{
//				if (isInternetPresent)
//				{
//
//				}
//				else
//				{
//					// Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(LoginActivity.this,"You don't have internet connection.", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//
////				Boolean compare_computed = BCrypt.checkpw(usr_name, pwd);
////				//Boolean compare_computed = BCrypt.checkpw(test_passwd, test_hash);
////				String s = String.valueOf(compare_computed);
////
//////				String phoneNo = textPhoneNo.getText().toString();
//////				String sms = "Your Password is "+password;
////
////				try {
////					SmsManager smsManager = SmsManager.getDefault();
////					//smsManager.sendTextMessage(phoneNo, null, sms, null, null);
////					Toast.makeText(getApplicationContext(), "SMS Sent!",
////							Toast.LENGTH_LONG).show();
////				} catch (Exception e) {
////					Toast.makeText(getApplicationContext(),
////							"SMS faild, please try again later!",
////							Toast.LENGTH_LONG).show();
////					e.printStackTrace();
////				}
//			}
//		});

	   	       buttonReg.setOnClickListener(new OnClickListener() {
		   	         public void onClick(View view) 
		   	         {
		   	        	isInternetPresent = cd.isConnectingToInternet();
		   	        	List<Local_Data> contacts2 = dbvoc.getAllMain();

		                if(contacts2.size() > 0)
		                {
		                   // Toast.makeText(LoginActivity.this, "Your Device Already Register.", Toast.LENGTH_SHORT).show();

							Toast toast = Toast.makeText(LoginActivity.this, "Your Device Already Register.", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
		                }
		                else
		                {
		                	if (isInternetPresent)
		                    {
			   	        	  getserviceData();
		                    }
			   	        	else
			   	        	{
			   	        	// Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
								Toast toast = Toast.makeText(LoginActivity.this,"You don't have internet connection.", Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
			   	        	}
		                }
		   	         }
	   	       });   
			   	          
	   	       buttonLogin.setOnClickListener(new OnClickListener() {
	   	         public void onClick(View view) {

					 gps = new GPSTracker(LoginActivity.this);
					 if(!gps.canGetLocation()){
//						 Toast toast = Toast.makeText(LoginActivity.this,"Your GPS is off,Please on it.", Toast.LENGTH_LONG);
//						 toast.setGravity(Gravity.CENTER, 0, 0);
//						 toast.show();
						 gps.showSettingsAlertnew();
					 }
					 else
					 if (CheckNullValue.findNullValue(editText1.getText().toString().trim()) == true) {
	                   // Toast.makeText(LoginActivity.this, "Please Enter UserName", Toast.LENGTH_SHORT).show();

						Toast toast = Toast.makeText(LoginActivity.this,"Please Enter UserName", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
	                }
	                else
	                if(CheckNullValue.findNullValue(editText2.getText().toString().trim()) == true)
	                {
	                   // Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

						Toast toast = Toast.makeText(LoginActivity.this,"Please Enter Password", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
	                }
//					else
//					if(CheckNullValue.findNullValue(emp_code.getText().toString().trim()) == true)
//					{
//						// Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
//
//						Toast toast = Toast.makeText(LoginActivity.this,"Please Enter Employee Code", Toast.LENGTH_LONG);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
	                else
	                {
//	                    if(switchCompat.isChecked())
//	                    {
//	                        Prefs.SavePreferences("USERNAME", user_name.getText().toString());
//	                        Prefs.SavePreferences("PASSWORD", password.getText().toString());
//	                    }
//	                    else
//	                    {
//	                        Prefs.SavePreferences("USERNAME", "");
//	                        Prefs.SavePreferences("PASSWORD", "");
//	                    }

	                    List<Local_Data> contacts2 = dbvoc.getUSERBY_Device(Global_Data.imei_no);

	                    if(contacts2.size() > 0)
	                    {
	                        Validate_Email_Pass(editText1.getText().toString().trim(),editText2.getText().toString().trim());
	                    }
	                    else
	                    {
	                      //  Toast.makeText(LoginActivity.this, "Your Device id not found in database, Please register first.", Toast.LENGTH_SHORT).show();

							Toast toast = Toast.makeText(LoginActivity.this,"Your Device id not found in database, Please register first.", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
	                    }
	                }
				 }
		   	       });
	}

	//Check internet connection
		public static boolean isOnline(Context ctx) {
			ConnectivityManager conMgr = (ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			// ARE WE CONNECTED TO THE NET

			if (conMgr.getActiveNetworkInfo() != null

			&& conMgr.getActiveNetworkInfo().isAvailable()

			&& conMgr.getActiveNetworkInfo().isConnected()) {
				return true;
			}else {
				    return false;
			      }
		}
	
  public class LoadDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean expired;
		
		String simSerailDB="";

		public LoadDatabaseAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Please wait");
			this.dialog.setCancelable(false);
			//this.dialog.show();
		
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				
				//simSerailDB=myDbHelper.createDataBase(simSerial,getDateTime());
				//myDbHelper.openDataBase();
				expired=false;//myDbHelper.checkExipry(getDateTime());
				
					
			} catch (Exception e) {
				// TODO: handle exception
				
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
			/*Log.e("DATA", "simSerial-"+simSerial);
			Log.e("DATA", "simSerailDB-"+simSerailDB);*/
			
			/* TESTING Start*/
			if (simSerailDB.equalsIgnoreCase("installation")) {
				//Log.e("DATA", "simSerailDB.equalsIgnoreCase simSerailDB-"+simSerailDB);
				
			//	new CheckforUserAsynTask(LoginActivity.this).execute();
			}
			
			else {
				if (!simSerial.equalsIgnoreCase(simSerailDB)) {
				 AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
				    alertDialog.setTitle("Warning");
				    alertDialog.setMessage("Sim card is not registered with the Application"); 
				    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.exit(0);
						}
					});

				    alertDialog.setCancelable(false);
				    alertDialog.setCanceledOnTouchOutside(false);
				    alertDialog.show(); 
			     	
			}
				else if (expired) {
				 AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
				    alertDialog.setTitle("Warning");
				    alertDialog.setMessage("This Application is Expired"); 
				    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							System.exit(0);
						}
					});

				    alertDialog.setCancelable(false);
				    alertDialog.setCanceledOnTouchOutside(false);
				    alertDialog.show(); 
			     	
			}
				
				else  {
					  // new CheckforUserAsynTask(LoginActivity.this).execute();
				      }
			}
		}
	}
	
//	public class CheckforUserAsynTask extends AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		private boolean userExists;
//
//		public CheckforUserAsynTask(Activity activity) {
//			this.activity = activity;
//			context=activity;
//			dialog = new ProgressDialog(context);
//			userExists=false;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Please wait");
//			this.dialog.setCancelable(false);
//			this.dialog.show();
//   	    }
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try{
//				  userExists=myDbHelper.checkUserRegistration("911305401754123");  //   manager.getDeviceId() OWN
//    		   }catch (Exception e){
//				                       // TODO: handle exception
//    			                       Log.e("DATA", "Exception-"+e.toString());
//	                        	   }
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (dialog.isShowing()) {
//				dialog.dismiss();
//
//			}
//
//			if (!userExists) {
//				AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
//			    alertDialog.setTitle("Warning");
//			    alertDialog.setMessage("Application is not registerd with this Device.Please enable data connection for registration.");
//			    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						 ConnectionDetector cd = new ConnectionDetector(LoginActivity.this.getApplicationContext());
//						 if (!cd.isConnectingToInternet()) {
//			                    // Internet Connection is Present
//			                    // make HTTP requests
//							    Toast toast = Toast.makeText(LoginActivity.this.getApplicationContext(),"No internet connection", Toast.LENGTH_LONG);
//								toast.setGravity(Gravity.CENTER, 0, 0);
//								toast.show();
//								System.exit(0);
//
//			                }
//						 else {
//							 dialog.cancel();
//							 new CheckforUserDataOnlineAsynTask(LoginActivity.this).execute();
//						}
//					}
//				});
//
//			    alertDialog.setCancelable(false);
//			    alertDialog.setCanceledOnTouchOutside(false);
//			    alertDialog.show();
//			}
//    	}
//	}
	
	
	public class CheckforUserDataOnlineAsynTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean userExists;
		
		User registredUser;
	
		public CheckforUserDataOnlineAsynTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
			userExists=false;
			registredUser=new User();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Please wait");
			this.dialog.setCancelable(false);
			this.dialog.show();
    	}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				/*HttpClient httpclient = new DefaultHttpClient();
				 
				HttpResponse response = httpclient.execute(new HttpGet("http://114.143.196.137/MS/XML/user.xml"));
	             HttpEntity entity = response.getEntity();
				 String responseString = EntityUtils.toString(entity, "UTF-8");
				 userParsing up = new userParsing();
				 up.parsingUserXMLData(responseString);
				 ArrayList<user> listUser  = up.getListUser();
				 for (Iterator iterator = listUser.iterator(); iterator
						.hasNext();) {
					user u = (user) iterator.next();
					if (u.getIMEINo().equalsIgnoreCase(manager.getDeviceId())) {
						registredUser=u;
						break;
					}
					
				}*/
				 
				registredUser = new User("1",1,"02/02/2014","911305401754123","kirti.k@simplelogic.in",
						"Kirti","imeiNo","Kumar","8097885917",1,1,"Active",1,"admin","test");
				
				 if (registredUser.getUserID()!=0) {
					//myDbHelper.insertRegisterUser(registredUser);
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				
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
			
//			if (registredUser.getUserID()==0) {
//				AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
//			    alertDialog.setTitle("Warning");
//			    alertDialog.setMessage("Application is not registerd with this Device."); 
//			    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						System.exit(0);
//					}
//				});
//
//			    alertDialog.setCancelable(false);
//			    alertDialog.setCanceledOnTouchOutside(false);
//			    alertDialog.show(); 
//			
//			}
			
			/*else {
				new InsertRegistredUserAsycTask(LoginActivity.this).execute();
			}*/
			
		}
	}
	
	/*public class InsertRegistredUserAsycTask extends AsyncTask<Void, Void, Void> {

		*//** progress dialog to show user that the backup is processing. *//*
		private ProgressDialog dialog;
		*//** application context. *//*
		private Activity activity;
		
		private Context context;
		
		private boolean userExists;
		
		user registredUser;
		
	

		public InsertRegistredUserAsycTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
			userExists=false;
			registredUser=new user();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Please wait");
			this.dialog.setCancelable(false);
			this.dialog.show();
			

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				HttpClient httpclient = new DefaultHttpClient();
				 
				 Read User Start
				HttpResponse response = httpclient.execute(new HttpGet("http://114.143.196.137/MS/XML/Test/user.xml"));
	             HttpEntity entity = response.getEntity();
				 String responseString = EntityUtils.toString(entity, "UTF-8");
				 userParsing up = new userParsing();
				 up.parsingUserXMLData(responseString);
				 ArrayList<user> listUser  = up.getListUser();
				 for (Iterator iterator = listUser.iterator(); iterator
						.hasNext();) {
					user u = (user) iterator.next();
					if (u.getIMEINo().equalsIgnoreCase(manager.getDeviceId())) {
						registredUser=u;
						break;
					}
					
				}
					
			} catch (Exception e) {
				// TODO: handle exception
				
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
			
			if (registredUser.getUserId()==0) {
				AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
			    alertDialog.setTitle("Warning");
			    alertDialog.setMessage("Application is not registerd with this Device."); 
			    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Continue",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				});

			  
			    alertDialog.setCancelable(false);
			    alertDialog.setCanceledOnTouchOutside(false);
			    alertDialog.show(); 
			}
			
			else {
				new InsertRegistredUserAsycTask(LoginActivity.this).execute();
			}
		}
	}*/
	
	public class LoadUserInfoAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;

		public LoadUserInfoAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Please wait");
			this.dialog.show();
	   }
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				//Log.e("DATA", "manager.getDeviceId() : "+manager.getDeviceId());
				
				//u=myDbHelper.cheeckUserdetails(editText1.getText().toString(),editText2.getText().toString(),"911305401754123",getDateTime());//manager.getDeviceId() = 911305401754123
				//Log.e("DATA", u.toString());
					
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("DATA", "Exception - "+e.toString());
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
			
	     	if (u.getUserID()!=0) {
	     		finish();
//	     		
//	     		 SharedPreferences spf=LoginActivity.this.getSharedPreferences("SimpleLogic",0);        
//			        SharedPreferences.Editor editor=spf.edit();        
//			        //editor.putString("UserID", userid);
//			        editor.putInt("UserID", u.getUserID());
//			        editor.putInt("StateID", u.getStateID());
//			        editor.putInt("cityID", u.getCityID());
//			        //editor.putInt("beatID", u.getBeatID());
//			        editor.putString("userbeatIDs", u.getBeatID());
//			        editor.putString("SimID", u.getSimID());
//			        editor.putString("FirstName", u.getFirstName());
//			        editor.putString("LastName", u.getLastName());
//			        editor.putFloat("Target", u.getTarget());
//			        editor.putFloat("Current_Target", u.getCurrent_target());
//			        editor.commit();
			        Intent i=new Intent(getApplicationContext(), MainActivity.class);
			       // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				    startActivity(i);

				Intent j = new Intent(getApplicationContext(), LocationServices.class);
				startService(j);
			        
				//Toast.makeText(getApplicationContext(), "Login Successful", 1000).show();
				/*SendLatLongAsyncTask sendLatLongAsyncTask=new SendLatLongAsyncTask(LoginActivity.this);
				sendLatLongAsyncTask.execute();*/
				Toast toast = Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
	     	
	     	else {
				  //Toast.makeText(getApplicationContext(), "Login failed", 1000).show();
				
				  Toast toast = Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG);
				  toast.setGravity(Gravity.CENTER, 0, 0);
				  toast.show();
			      }
			
			/* for blank*/
			/*finish();
     		
    		 SharedPreferences spf=LoginActivity.this.getSharedPreferences("SimpleLogic",0);        
		        SharedPreferences.Editor editor=spf.edit();        
		        //editor.putString("UserID", userid);
		        editor.putInt("UserID", 1);
		        editor.commit();
		        Intent i=new Intent(getApplicationContext(), MainActivity.class);
		       // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			startActivity(i);*/
			/* for blank*/
			
			
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		android.os.Process.killProcess(android.os.Process.myPid()); System.exit(1);
	}
	@Override
    protected void onResume(){ 
    	// TODO Auto-generated method stub
    	super.onResume();
    	 // Reading all 
	         List<Local_Data> contacts = dbvoc.getAllMain();      
	          for (Local_Data cn : contacts) {   
	        	Global_Data.local_user = ""+cn.getUser();
	        	Global_Data.local_pwd = ""+cn.getPwd();
	        	//Global_Data.local_imeino = ""+cn.getImei();
	        	System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
	        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
	        	                             }
	}
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
        		"dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;
		inputStream.close();
		return result;
	}

	public void getserviceData() {
	        dialog.setMessage("Please wait Data Sync....");
	        dialog.setTitle("Metal App");
	        dialog.setCancelable(false);
	        dialog.show();

	        try
	        {
	        	TelephonyManager tm = (TelephonyManager)LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);
                //final String Device_id =  tm.getDeviceId();
				final String Device_id =  tm.getDeviceId();
	            String domain = getResources().getString(R.string.service_domain);

				SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = pref.edit();
				edit.putString("reg_devid", Device_id);
				edit.commit();

				SharedPreferences pref_devid = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
				pref_devid.getString("reg_devid", "");
				devid = pref_devid.getString("reg_devid", "");

				if (devid.length() > 0) {
					link_fpwd.setVisibility(View.VISIBLE);
				} else {
					link_fpwd.setVisibility(View.GONE);
				}

//	            Global_Val global_Val = new Global_Val();
//	            if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//	                domain = getResources().getString(R.string.service_domain);
//	            }
//	            else
//	            {
//	                domain = URL.toString();
//	            }

	            Log.i("volley", "domain: " + domain);
	            Log.i("volley", "Device_id: " + Device_id);
	           // Log.i("volley", "Sim_Number: " + Global_Val.Sim_Number);
	            Log.i("volley", "Service url: " + domain+"/menus/registration?imei_no="+ URLEncoder.encode(Device_id, "UTF-8"));

	            JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"/menus/registration?imei_no="+ URLEncoder.encode(Device_id, "UTF-8"),null, new Response.Listener<JSONObject>() {
	            	// JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"/menus/registration?imei_no="+ URLEncoder.encode("911305401754123", "UTF-8"),null, new Response.Listener<JSONObject>() {

	                @Override
	                public void onResponse(JSONObject response) {
	                    Log.i("volley", "response: " + response.toString());
	                    //  Log.i("volley", "response reg Length: " + response.length());

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


	                        if(response_result.equalsIgnoreCase("Device not registered")) {

	                           // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

								Toast toast = Toast.makeText(LoginActivity.this,response_result, Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

	                        }
	                        else {

	                            dbvoc.getDeleteTable("USERS");
	                            
	                            JSONArray users = response.getJSONArray("users");
//	                           
	                            Log.i("volley", "response reg users Length: " + users.length());

								if(users.length() <=0)
								{
									dialog.dismiss();
									//Toast.makeText(LoginActivity.this, "User not found, Please contact with it team.", Toast.LENGTH_SHORT).show();
									Toast toast = Toast.makeText(LoginActivity.this,"User not found, Please contact with it team.", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
								else
								{
									Log.d("users", "users" + users.toString());

									for (int i = 0; i < users.length(); i++) {

										JSONObject jsonObject = users.getJSONObject(i);

										SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
										SharedPreferences.Editor edit = pref.edit();
										edit.putString("login_usernm", jsonObject.getString("user_name"));
										edit.putString("login_pwd", jsonObject.getString("encrypted_password"));
										edit.putString("login_dtofjn", jsonObject.getString("date_of_joining"));
										edit.putString("login_mobno", jsonObject.getString("mob_no"));
										edit.putString("login_email", jsonObject.getString("email"));
										edit.putString("login_repto", jsonObject.getString("reporting_to"));
										edit.putString("login_firstnm", jsonObject.getString("first_name"));
										edit.putString("login_lastnm", jsonObject.getString("last_name"));
										edit.putString("login_devid", Device_id);
										edit.putString("login_adrs", jsonObject.getString("address"));
										edit.commit();

										loginDataBaseAdapter.insertEntry(jsonObject.getString("user_name"), jsonObject.getString("encrypted_password"), jsonObject.getString("date_of_joining"), jsonObject.getString("mob_no"), jsonObject.getString("email"), jsonObject.getString("reporting_to"),
												jsonObject.getString("first_name"), jsonObject.getString("last_name"),"", "", "", "", "",
												"", Device_id, "", jsonObject.getString("address"), "", "", "", "", "",jsonObject.getString("emp_code"));
									}

									//Toast.makeText(getApplicationContext(), "Register successfully.", Toast.LENGTH_LONG).show();


									Toast toast = Toast.makeText(LoginActivity.this, "Register successfully.", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
									dialog.dismiss();
								}
//	                          	                            //finish();
							}

	                        // }

	                        // output.setText(data);
	                    }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); finish();}


	                    dialog.dismiss();
	                }
	            }, new Response.ErrorListener() {
	                @Override
	                public void onErrorResponse(VolleyError error) {
	                    Log.i("volley", "error: " + error);
	                   // Toast.makeText(getApplicationContext(), "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
						Toast toast = Toast.makeText(LoginActivity.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
	                    dialog.dismiss();

	                }
	            });

	            RequestQueue requestQueue = Volley.newRequestQueue(this);
	            // queue.add(jsObjRequest);
	            jsObjRequest.setShouldCache(false);
	            int socketTimeout = 300000;//30 seconds - change to what you want
	            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	            jsObjRequest.setRetryPolicy(policy);
	            requestQueue.add(jsObjRequest);

	        } catch (Exception e) {
	            e.printStackTrace();
	            dialog.dismiss();
	        }
	    }

	public void Validate_Email_Pass(String username, String passwordnew) {
	        dialog.setMessage("Please wait....");
	        dialog.setTitle("Metal App");
	        dialog.setCancelable(false);
	        dialog.show();



			new LOGINOperation().execute(username,passwordnew);
	    }

	private class LOGINOperation extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... response) {

			List<Local_Data> contacts2 = dbvoc.getUSERBY_Name(response[0]);

			if(contacts2.size() > 0)
			{
				int emp_code_flag = 0;
				for (Local_Data cn : contacts2) {
					String str_beat = "" + cn.getUser();

					SharedPreferences spf=LoginActivity.this.getSharedPreferences("SimpleLogic",0);
					SharedPreferences.Editor editor=spf.edit();
					editor.putString("USER_EMAIL", cn.getuser_email());
					//editor.commit();

					Global_Data.GLOvel_USER_EMAIL = cn.getuser_email();
					Global_Data.GLOVEL_USER_ID = cn.get_shedule_order_id();
					Global_Data.customer_reportingTo = cn.getreporting_to();
					Global_Data.USER_FIRST_NAME = cn.getfirst_name();
					Global_Data.USER_LAST_NAME = cn.getlast_name();

					if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getEmp_code())) {
						String empl_code=cn.getEmp_code();
						Global_Data.emp_code =empl_code;

						LoginActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								emp_code.setText(Global_Data.emp_code);

							}
						});

						emp_code_flag =1;
						editor.putString(Code, Global_Data.emp_code );
						editor.commit();
					}

					if(emp_code_flag == 1)
					{
						List<Local_Data> cont = dbvoc.getManager_mobile(Global_Data.customer_reportingTo);

						if(cont.size() <= 0)
						{
							Global_Data.cus_MAnager_mobile = "";
						}
						else
						{
							for (Local_Data cnn : cont) {

								if(!cnn.getMOBILE_NO().equalsIgnoreCase(null) && !cnn.getMOBILE_NO().equalsIgnoreCase("null") && !cnn.getMOBILE_NO().equalsIgnoreCase("") && !cnn.getMOBILE_NO().equalsIgnoreCase(" "))
								{
									Global_Data.cus_MAnager_mobile = cnn.getMOBILE_NO();
									Global_Data.USER_MANAGER_NAME = cnn.getfirst_name() + " " + cnn.getlast_name();
								}
								else
								{
									Global_Data.cus_MAnager_mobile = "";
								}
							}
						}

						Log.d("user email", "user email"+cn.getuser_email());
						Log.d("user id", "user id"+cn.get_shedule_order_id());

						dbvoc.getDeleteTable("user_email");

						loginDataBaseAdapter.insert_user_email(cn.getuser_email());
						//Global_Data.local_pwd = ""+cn.getPwd();

						//String test_passwd = "password";
						//String test_hash = "$2a$10$qvO88dxBtHvZ2jAeyhLuMenX97XRxspL4zV3DAU.3ZQyeexZxBu86";

						try {
							if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getPwd()))
							{
								Boolean compare_computed = BCrypt.checkpw(response[1], cn.getPwd());
								//Boolean compare_computed = BCrypt.checkpw(test_passwd, test_hash);
								String s = String.valueOf(compare_computed);

								Log.d("check authente", "value" + s);

								if (s.equalsIgnoreCase("true") || s.equalsIgnoreCase("TRUE")) {

									loginDataBaseAdapter.open();

									LoginActivity.this.runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											editText1.setText("");
											editText2.setText("");
										}
									});

//								        dialog.dismiss();
//        								startActivity(new Intent(LoginActivity.this, MainActivity.class));
//										overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//										finish();

									List<Local_Data> conta = dbvoc.getDateBY_Device(Global_Data.imei_no);
									for (Local_Data cn1 : conta) {
										current_date = cn1.getCur_date();
										//current_date="28/02/2017";
									}

									if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(current_date)) {
										SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
										Date date1 = sdf.parse(current_date);

										Calendar c = Calendar.getInstance();
										SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
										String formattedDate = df.format(c.getTime());
										Date to_ddd = df.parse(formattedDate);

										if (to_ddd.compareTo(date1) < 0) {
//												Toast.makeText(LoginActivity.this, "to_ddd is after date1", Toast.LENGTH_SHORT).show();
//											loginDataBaseAdapter.insertEntry("", "", "", "", "", "",
//													"", "","", "", "", "", "",
//													"", "", "", "","","","",formattedDate,"1");
//
//											startActivity(new Intent(LoginActivity.this, MainActivity.class));
//											overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//											finish();
											isInternetPresent = cd.isConnectingToInternet();
											if (isInternetPresent) {
												SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

												loginDataBaseAdapter.updateEntry(pref.getString("login_usernm", ""), pref.getString("login_pwd", ""), pref.getString("login_dtofjn", ""), pref.getString("login_mobno", ""), pref.getString("login_email", ""), pref.getString("login_repto", ""),
														pref.getString("login_firstnm", ""), pref.getString("login_lastnm", ""), "", "", "", "", "",
														"", pref.getString("login_devid", ""), "", pref.getString("login_adrs", ""), "", "", "", formattedDate, "1");

//													loginDataBaseAdapter.insertEntry("", "", "", "", "", "",
//															"", "","", "", "", "", "",
//															"", "", "", "","","","",formattedDate,"1");

												LoginActivity.this.runOnUiThread(new Runnable() {
													public void run() {
														getServices.sendRequestnew(LoginActivity.this, "First Login of the day, Please wait for Data download...");
													}
												});
											} else {
												LoginActivity.this.runOnUiThread(new Runnable() {
													public void run() {
														Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
														toast.setGravity(Gravity.CENTER, 0, 0);
														toast.show();
													}
												});

											}
										} else {
											dialog.dismiss();
											startActivity(new Intent(LoginActivity.this, MainActivity.class));
											overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
											finish();
										}
//									  }catch (ParseException e)
//									   {
//										 e.printStackTrace();
//									   }
									} else {
										isInternetPresent = cd.isConnectingToInternet();

										Calendar c = Calendar.getInstance();
										SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
										String formattedDate = df.format(c.getTime());

										if (isInternetPresent) {
											SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

											loginDataBaseAdapter.updateEntry(pref.getString("login_usernm", ""), pref.getString("login_pwd", ""), pref.getString("login_dtofjn", ""), pref.getString("login_mobno", ""), pref.getString("login_email", ""), pref.getString("login_repto", ""),
													pref.getString("login_firstnm", ""), pref.getString("login_lastnm", ""), "", "", "", "", "",
													"", pref.getString("login_devid", ""), "", pref.getString("login_adrs", ""), "", "", "", formattedDate, "1");

											LoginActivity.this.runOnUiThread(new Runnable() {
												public void run() {
													getServices.sendRequestnew(LoginActivity.this, "First Login of the day, Please wait for Data download...");
												}
											});
										} else {
											LoginActivity.this.runOnUiThread(new Runnable() {
												public void run() {
													Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
													toast.setGravity(Gravity.CENTER, 0, 0);
													toast.show();
												}
											});
										}
									}

								} else {

									LoginActivity.this.runOnUiThread(new Runnable() {
										public void run() {
											dialog.dismiss();
											editText2.setText("");
											Toast toast = Toast.makeText(LoginActivity.this, "Your Password doesnot match Please Try Again.", Toast.LENGTH_SHORT);
											toast.setGravity(Gravity.CENTER, 0, 0);
											toast.show();
										}
									});
								}
							}else
							{
								LoginActivity.this.runOnUiThread(new Runnable() {
									public void run() {
										dialog.dismiss();
										editText1.setText("");
										editText2.setText("");

										Toast toast = Toast.makeText(LoginActivity.this, "Your Credential doesnot match Please Try Again.", Toast.LENGTH_SHORT);
										toast.setGravity(Gravity.CENTER, 0, 0);
										toast.show();
									}
								});
							}

						} catch (ParseException ex) {
							ex.printStackTrace();
							LoginActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									dialog.dismiss();
									editText1.setText("");
									editText2.setText("");
									//Toast.makeText(LoginActivity.this, "Your Credential doesnot match Please Try Again.", Toast.LENGTH_SHORT).show();

									Toast toast = Toast.makeText(LoginActivity.this, "Your Credential doesnot match Please Try Again.", Toast.LENGTH_SHORT);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
							});

						}
					}
					else
					{
						LoginActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								dialog.dismiss();
								editText1.setText("");
								editText2.setText("");
								emp_code.setText("");
								SharedPreferences spf=LoginActivity.this.getSharedPreferences("SimpleLogic",0);
								SharedPreferences.Editor editor=spf.edit();
								editor.putString(Code, "");
								editor.commit();
								//Toast.makeText(LoginActivity.this, "Your Credential doesnot match Please Try Again.", Toast.LENGTH_SHORT).show();

								Toast toast = Toast.makeText(LoginActivity.this, "Your employee code not found in databse,Please contact with admin.", Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						});
					}


					dialog.dismiss();

				}
			}
			else
			{
				LoginActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						dialog.dismiss();
						editText1.setText("");
						editText2.setText("");
						//Toast.makeText(LoginActivity.this, "Your Credential doesnot match Please Try Again.", Toast.LENGTH_SHORT).show();

						Toast toast = Toast.makeText(LoginActivity.this, "Your username doesnot match Please Try Again.", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				});

			}
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result) {

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
			//dialog.dismiss();
			LoginActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					dialog.dismiss();
				}
			});

		}

		@Override
		protected void onPreExecute() {}

		@Override
		protected void onProgressUpdate(Void... values) {}
	}

	@Override
	protected void onDestroy() {
		try {
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

//	public String decryption(String strEncryptedText){
//		String seedValue = "YourSecKey";
//		String strDecryptedText="";
//		try {
//			strDecryptedText = AESHelper.decrypt(seedValue, strEncryptedText);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return strDecryptedText;
//	}

}
