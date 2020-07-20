package com.anchor.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.objects.Update;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cpm.simplelogic.helper.BCrypt;
import cpm.simplelogic.helper.CheckNullValue;
import cpm.simplelogic.helper.GPSTracker;

public class LoginActivity extends Activity {
    ProgressDialog progress;
    private Bitmap bitmap = null;
    Handler h;
    String abc="";
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
    Button buttonLogin, buttonReg;
    EditText editText1, editText2, emp_code;
    TelephonyManager manager;
    String simSerial = "f8a0f77d096fddc3", firstName = "", lastName = "";
    User u;
    String current_date, usremail;
    Bitmap blob_data_logo;
    LoginDataBaseAdapter loginDataBaseAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    ImageView logo_img, app_clear_dat;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    public static String CHANNEL_ID = "SmartAnchor";
    String CHANNEL_NAME = "SmartAnchor";
    String CHANNEL_DESC = "Anchor App";
    private int passwordNotVisible=1;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        /* SSL CONFIG */
        handleSSLHandshake();

        FirebaseApp.initializeApp(this);

//        String android_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
//        Log.d("Android ID","Android ID"+android_id);


        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken", newToken);
//			getActivity().getPreferences(Context.MODE_PRIVATE).edit().putString("fb", newToken).apply();
            }
        });

        //creating notification channel if android version is greater than or equals to oreo
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        logo_img = findViewById(R.id.imageView1);
        //app_clear_dat=(ImageView)findViewById(R.id.app_clear_dat);
        link_fpwd = findViewById(R.id.forget_pwd);

        SharedPreferences spf1 = this.getSharedPreferences("SimpleLogic", 0);
        String logostr = spf1.getString("logo_data", "");

        if (logostr.length() > 0) {
            byte[] decodedString = Base64.decode(logostr, Base64.DEFAULT);
            blob_data_logo = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            logo_img.setImageBitmap(blob_data_logo);
        }

        SharedPreferences pref_devid = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
        devid = pref_devid.getString("devid", "");

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

        requestGPSPermissionsigna();


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = sdf.format(c.getTime());
        Current_Date = sdf.format(c.getTime());
        h = new Handler();

        // Session Manager
        session = new SessionManager(getApplicationContext());

        arraylist1 = new ArrayList<HashMap<String, String>>();
        arraylist2 = new ArrayList<HashMap<String, String>>();

        cn = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        nf = cn.getActiveNetworkInfo();

        textViewVersion = findViewById(R.id.textViewVersion);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonReg = findViewById(R.id.buttonReg);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        emp_code = findViewById(R.id.emp_code);

        sharedpreferences = getSharedPreferences("SimpleLogic",
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Code)) {
            Global_Data.emp_code = sharedpreferences.getString(Code, "");
            emp_code.setText(Global_Data.emp_code);
        }

//        editText1.setText("PRIYA SHOP");
//        editText2.setText("PRIYANKA3073911");

//
        editText1.setText("Sadanand 1");
        editText2.setText("sadanand12345");
//        editText1.setText("Jaya");
//        editText2.setText("Jaya4861167");

//        editText1.setText("Amolfsp ");
//        editText2.setText("amol12345");

//		editText1.setText("Sadanand");
//		editText2.setText("Sadanand7315903");


        SharedPreferences spf = LoginActivity.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();


        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        //Reading all
        List<Local_Data> contacts = dbvoc.getAllMain();
        for (Local_Data cn : contacts) {
            Global_Data.local_user = "" + cn.getUser();
            Global_Data.local_pwd = "" + cn.getPwd();
            System.out.println("Local Values:-" + Global_Data.local_user + "," + Global_Data.local_pwd);
            //Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
        }

        editText2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (editText2.getRight() - editText2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = LoginActivity.this.getCurrentFocus();
                        if (view != null) {
                           // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                           // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        if (passwordNotVisible == 1) {
                            editText2.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_black_24dp), null);
                            editText2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passwordNotVisible = 0;

                        } else {
                            editText2.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_off_black_24dp), null);
                            editText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordNotVisible = 1;

                        }
                        return true;
                    }
                }
                return false;
            }
        });

        buttonReg.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                isInternetPresent = cd.isConnectingToInternet();
                List<Local_Data> contacts2 = dbvoc.getAllMain();

                if (contacts2.size() > 0) {
                    // Toast.makeText(LoginActivity.this, "Your Device Already Register.", Toast.LENGTH_SHORT).show();

                    Toast toast = Toast.makeText(LoginActivity.this, "Your Device Already Register.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    if (isInternetPresent) {
                        requestPhoneStatePermission();

                    } else {
                        // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
                        Toast toast = Toast.makeText(LoginActivity.this, "You don't have internet connection.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            }
        });

        buttonLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                requestGPSPermissionsignlogin();

            }
        });
    }

    //Check internet connection
    public static boolean isOnline(Context ctx) {
        ConnectivityManager conMgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // ARE WE CONNECTED TO THE NET

        return conMgr.getActiveNetworkInfo() != null

                && conMgr.getActiveNetworkInfo().isAvailable()

                && conMgr.getActiveNetworkInfo().isConnected();
    }

    public class LoadDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {

        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog;
        /**
         * application context.
         */
        private Activity activity;

        private Context context;

        private boolean expired;

        String simSerailDB = "";

        public LoadDatabaseAsyncTask(Activity activity) {
            this.activity = activity;
            context = activity;
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
                expired = false;//myDbHelper.checkExipry(getDateTime());


            } catch (Exception e) {
                // TODO: handle exception

                Log.e("DATA", "Exception-" + e.toString());
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
            } else {
                if (!simSerial.equalsIgnoreCase(simSerailDB)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Sim card is not registered with the Application");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });

                    alertDialog.setCancelable(false);
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();

                } else if (expired) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create(); //Read Update
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("This Application is Expired");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    });

                    alertDialog.setCancelable(false);
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.show();

                } else {
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

        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog;
        /**
         * application context.
         */
        private Activity activity;

        private Context context;

        private boolean userExists;

        User registredUser;

        public CheckforUserDataOnlineAsynTask(Activity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
            userExists = false;
            registredUser = new User();
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

                registredUser = new User("1", 1, "02/02/2014", "911305401754123", "kirti.k@simplelogic.in",
                        "Kirti", "imeiNo", "Kumar", "8097885917", 1, 1, "Active", 1, "admin", "test");

                if (registredUser.getUserID() != 0) {
                    //myDbHelper.insertRegisterUser(registredUser);
                }

            } catch (Exception e) {
                // TODO: handle exception

                Log.e("DATA", "Exception-" + e.toString());
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
		*/

    /**
     * application context.
     *//*
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

        /**
         * progress dialog to show user that the backup is processing.
         */
        private ProgressDialog dialog;
        /**
         * application context.
         */
        private Activity activity;

        private Context context;

        private boolean webServiceResponse;

        public LoadUserInfoAsyncTask(Activity activity) {
            this.activity = activity;
            context = activity;
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
                Log.e("DATA", "Exception - " + e.toString());
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

            if (u.getUserID() != 0) {
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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                startActivity(i);

                Intent j = new Intent(getApplicationContext(), LocationServices.class);
                startService(j);

                //Toast.makeText(getApplicationContext(), "Login Successful", 1000).show();
				/*SendLatLongAsyncTask sendLatLongAsyncTask=new SendLatLongAsyncTask(LoginActivity.this);
				sendLatLongAsyncTask.execute();*/
                Toast toast = Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                //Toast.makeText(getApplicationContext(), "Login failed", 1000).show();

                Toast toast = Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG);
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
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

//        AppUpdater appUpdater = new AppUpdater(this);
//        appUpdater.setButtonDoNotShowAgain(null);
//        // appUpdater.setButtonDismiss(null);
//        appUpdater.setCancelable(false);
//        appUpdater.setIcon(R.drawable.anchor_logo);
//        appUpdater.start();

        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                //.setUpdateFrom(UpdateFrom.AMAZON)
                //.setUpdateFrom(UpdateFrom.GITHUB)
                //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
                //...
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        Log.d("Latest Version", update.getLatestVersion());
                        Log.d("Latest Version Code", "jk" + update.getLatestVersionCode());
                        Log.d("Release notes", update.getReleaseNotes());
                        Log.d("URL", "jk" + update.getUrlToDownload());
                        Log.d("Is update available?", Boolean.toString(isUpdateAvailable));
                        if(isUpdateAvailable)
                        {
                            AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(LoginActivity.this);
                            mAlertDialog.setCancelable(false);
                            mAlertDialog.setTitle("Smart Anchor")
                                    .setMessage("Update "+ update.getLatestVersion()+" is available to download.\n" +
                                            "By downloading the latest update you will get the latest features,improvements and bug fixes for Smart Anchor."
                                    )
                                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                            try {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                            } catch (android.content.ActivityNotFoundException anfe) {
                                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                            }

                                        }
                                    });
//                                    .setNegativeButton("Dismiss",new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            dialog.cancel();
//                                        }
//                                    });

                            AlertDialog dialogn = mAlertDialog.create();
                            //dialogn.getButton(AlertDialog.BUTTON1).setEnabled(false);
                            dialogn.show();

                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.d("AppUpdater Error", "Something went wrong");
                    }
                });
        appUpdaterUtils.start();

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            textViewVersion.setText("Mobile Sales App, v. "+version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //textViewVersion.setText("Mobile Sales App, v. 1.3.1");
        }
        // Reading all
//        List<Local_Data> contacts = dbvoc.getAllMain();
//        for (Local_Data cn : contacts) {
//            Global_Data.local_user = "" + cn.getUser();
//            Global_Data.local_pwd = "" + cn.getPwd();
//            //Global_Data.local_imeino = ""+cn.getImei();
//            System.out.println("Local Values:-" + Global_Data.local_user + "," + Global_Data.local_pwd);
//            //Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//
//
//        }
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }

    public void getserviceData() {
        dialog.setMessage("Please wait Data Sync....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();

        try {
            SharedPreferences pref_devid = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
            devid = pref_devid.getString("devid", "");
            final String Device_id = pref_devid.getString("devid", "");

            String domain = getResources().getString(R.string.service_domain);


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
            Log.i("volley", "Service url: " + domain + "menus/registration?imei_no=" + URLEncoder.encode(Device_id, "UTF-8"));

            JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain + "menus/registration?imei_no=" + URLEncoder.encode(Device_id, "UTF-8"), null, new Response.Listener<JSONObject>() {
                // JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"/menus/registration?imei_no="+ URLEncoder.encode("911305401754123", "UTF-8"),null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response.toString());

                    try {
                        String response_result = "";
                        if (response.has("result")) {
                            response_result = response.getString("result");
                        } else {
                            response_result = "data";
                        }


                        if (response_result.equalsIgnoreCase("Device not registered")) {

                            Toast toast = Toast.makeText(LoginActivity.this, response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else {

                            dbvoc.getDeleteTable("users");

                            JSONArray users = response.getJSONArray("users");
                            Log.i("volley", "response reg users Length: " + users.length());

                            if (users.length() <= 0) {
                                dialog.dismiss();
                                //Toast.makeText(LoginActivity.this, "User not found, Please contact with it team.", Toast.LENGTH_SHORT).show();
                                Toast toast = Toast.makeText(LoginActivity.this, "User not found, Please contact with it team.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                Log.d("users", "users" + users.toString());

                                /* IDS column used for BU Head */

                                for (int i = 0; i < users.length(); i++) {

                                    JSONObject jsonObject = users.getJSONObject(i);

                                    loginDataBaseAdapter.insertEntry(jsonObject.getString("user_name"), jsonObject.getString("encrypted_password"), jsonObject.getString("date_of_joining"), jsonObject.getString("mob_no"), jsonObject.getString("email"), jsonObject.getString("reporting_to"),
                                            jsonObject.getString("first_name"), jsonObject.getString("last_name"), "", "", "", "", "",
                                            "", Device_id, "", jsonObject.getString("address"), "", "", jsonObject.getString("BU_heads"), "", "", jsonObject.getString("emp_code"));
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
                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        finish();
                    }


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
            int socketTimeout = 3000000;//3000 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }

    public void Validate_Email_Pass(String username, String passwordnew) {

        requestStoragegpsPermissionsigna(username, passwordnew);


    }

    private class LOGINOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            List<Local_Data> contacts2 = dbvoc.getUSERBY_Name(response[0]);

            if (contacts2.size() > 0) {
                int emp_code_flag = 0;
                for (Local_Data cn : contacts2) {
                    String str_beat = "" + cn.getUser();

//					SharedPreferences prefs = getSharedPreferences("anchorde",Context.MODE_WORLD_READABLE);
//					SharedPreferences.Editor editor1 = prefs.edit();
//					editor1.putString("USER_LOGIN_CHECK", "Login");
//					editor1.commit();


                    SharedPreferences spf = LoginActivity.this.getSharedPreferences("SimpleLogic", 0);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("USER_EMAIL", cn.getuser_email());
                    editor.putString("USER_NAMEs", cn.getfirst_name() + " " + cn.getlast_name());

                    //editor.commit();

                    Global_Data.GLOvel_USER_EMAIL = cn.getuser_email();
                    Global_Data.GLOVEL_USER_ID = cn.get_shedule_order_id();
                    Global_Data.customer_reportingTo = cn.getreporting_to();
                    Global_Data.USER_FIRST_NAME = cn.getfirst_name();
                    Global_Data.USER_LAST_NAME = cn.getlast_name();

                    dbvoc.getDeleteTable("user_email");

                    loginDataBaseAdapter.insert_user_email(cn.getuser_email(), "Login");

                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(cn.getEmp_code())) {
                        String empl_code = cn.getEmp_code();
                        Global_Data.emp_code = empl_code;

                        LoginActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                emp_code.setText(Global_Data.emp_code);

                            }
                        });

                        emp_code_flag = 1;
                        editor.putString(Code, Global_Data.emp_code);
                        editor.commit();
                    }

                    if (emp_code_flag == 1) {
                        List<Local_Data> cont = dbvoc.getManager_mobile(Global_Data.customer_reportingTo);

                        if (cont.size() <= 0) {
                            Global_Data.cus_MAnager_mobile = "";
                        } else {
                            for (Local_Data cnn : cont) {

                                if (!cnn.getMOBILE_NO().equalsIgnoreCase(null) && !cnn.getMOBILE_NO().equalsIgnoreCase("null") && !cnn.getMOBILE_NO().equalsIgnoreCase("") && !cnn.getMOBILE_NO().equalsIgnoreCase(" ")) {
                                    Global_Data.cus_MAnager_mobile = cnn.getMOBILE_NO();
                                    Global_Data.USER_MANAGER_NAME = cnn.getfirst_name() + " " + cnn.getlast_name();
                                } else {
                                    Global_Data.cus_MAnager_mobile = "";
                                }
                            }
                        }

                        Log.d("user email", "user email" + cn.getuser_email());
                        Log.d("user id", "user id" + cn.get_shedule_order_id());


                        //Global_Data.local_pwd = ""+cn.getPwd();

                        //String test_passwd = "password";
                        //String test_hash = "$2a$10$qvO88dxBtHvZ2jAeyhLuMenX97XRxspL4zV3DAU.3ZQyeexZxBu86";

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getPwd())) {
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

                                    List<Local_Data> conta = dbvoc.getDateBY_Device(Global_Data.imei_no, Global_Data.GLOvel_USER_EMAIL);
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
                                        //String formattedDate = df.format(c.getTime());

                                        if (to_ddd.compareTo(date1) > 0) {
//											finish();
                                            isInternetPresent = cd.isConnectingToInternet();
                                            if (isInternetPresent) {

                                                dbvoc.update_user_createDate(formattedDate, Global_Data.GLOvel_USER_EMAIL);

                                                LoginActivity.this.runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        getServices.sendRequestnew(LoginActivity.this, "First Login of the day, Please wait for Data download...");
                                                    }
                                                });
                                            } else {
                                                LoginActivity.this.runOnUiThread(new Runnable() {
                                                    public void run() {
                                                        dialog.dismiss();
                                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                                        finish();
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

                                            dbvoc.update_user_createDate(formattedDate, Global_Data.GLOvel_USER_EMAIL);


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
                            } else {
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
                    } else {
                        LoginActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                dialog.dismiss();
                                editText1.setText("");
                                editText2.setText("");
                                emp_code.setText("");
                                SharedPreferences spf = LoginActivity.this.getSharedPreferences("SimpleLogic", 0);
                                SharedPreferences.Editor editor = spf.edit();
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
            } else {
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
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
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

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    private void requestStoragegpsPermissionsigna(final String username, final String passwordnew) {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            dialog.setMessage("Please wait....");
                            dialog.setTitle("Smart Anchor App");
                            dialog.setCancelable(false);
                            dialog.show();


                            new LOGINOperation().execute(username, passwordnew);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(LoginActivity.this);
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

    private void requestPhoneStatePermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.READ_PHONE_STATE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        TelephonyManager tm = (TelephonyManager) LoginActivity.this.getSystemService(Context.TELEPHONY_SERVICE);

                        String Device_id = tm.getDeviceId();

                        SharedPreferences pref = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = pref.edit();
                        edit.putString("devid", Device_id);
                        edit.commit();

                        Global_Data.device_id = Device_id;

                        Global_Data.imei_no = Device_id;

                        getserviceData();
                        return;


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog(LoginActivity.this);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void requestGPSPermissionsigna() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            gps = new GPSTracker(LoginActivity.this);

                            Global_Data.LOCATION_SERVICE_HIT = "TRUE";
                            Global_Data.rank_service_call_flag = "TRUE";

                            if (!gps.canGetLocation()) {
                                // can't get location
                                // GPS or Network is not enabled
                                // Ask user to enable GPS/network in settings
                                gps.showSettingsAlert();
                            }

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(LoginActivity.this);
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

    private void requestGPSPermissionsignlogin() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            gps = new GPSTracker(LoginActivity.this);
                            if (!gps.canGetLocation()) {
//						 Toast toast = Toast.makeText(LoginActivity.this,"Your GPS is off,Please on it.", Toast.LENGTH_LONG);
//						 toast.setGravity(Gravity.CENTER, 0, 0);
//						 toast.show();
                                gps.showSettingsAlertnew();
                            } else if (CheckNullValue.findNullValue(editText1.getText().toString().trim()) == true) {
                                // Toast.makeText(LoginActivity.this, "Please Enter UserName", Toast.LENGTH_SHORT).show();

                                Toast toast = Toast.makeText(LoginActivity.this, "Please Enter UserName", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else if (CheckNullValue.findNullValue(editText2.getText().toString().trim()) == true) {
                                // Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                                Toast toast = Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_LONG);
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
                            else {

                                SharedPreferences pref_devid = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
                                String Device_id = pref_devid.getString("devid", "");
                                Global_Data.device_id = Device_id;
                                Global_Data.imei_no = Device_id;

                                List<Local_Data> contacts2 = dbvoc.getUSERBY_Device(Global_Data.imei_no);

                                if (contacts2.size() > 0) {
                                    Validate_Email_Pass(editText1.getText().toString().trim(), editText2.getText().toString().trim());
                                } else {
                                    Toast toast = Toast.makeText(LoginActivity.this, "Your Device id not found in database, Please register first.", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog(LoginActivity.this);
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
    private void showSettingsDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
//				@Override
//				public boolean verify(String arg0, SSLSession arg1) {
//					return true;
//				}
//			});
        } catch (Exception ignored) {
        }
    }

}
