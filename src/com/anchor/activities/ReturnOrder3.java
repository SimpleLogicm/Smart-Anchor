package com.anchor.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.anchor.model.Product;
import com.anchor.services.getServices;
import com.anchor.webservice.ConnectionDetector;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
 
public class ReturnOrder3 extends BaseActivity {
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    LinearLayout mContent;
    signature mSignature;
    Button mClear, mGetSign, mCancel;
    int m_sign_flag = 0;
	ArrayList<Product> dataOrder=new ArrayList<Product>();
    public static String tempDir;
    public int count = 1;
    public String current = null;
    private Bitmap mBitmap;
    View mView;
    int userID,cityID,beatID,retailerID,distID;

    Boolean isInternetPresent = false;
	
    ConnectionDetector cd; 
 
    //private String uniqueId;
    private EditText yourName;
    float totalPrice;
	public String order="",retailer_mobile="",retailer_emailID="",dist_mobile="",dist_emailID="",retailer_code="",ret_Name="";
 
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.return_order_signature);
        cd  = new ConnectionDetector(getApplicationContext());
        tempDir = Environment.getExternalStorageDirectory() + "/SimpleLogic/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
       // File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);
 
        File directory = new File(tempDir);
        
        prepareDirectory();
//        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        SharedPreferences  sp=this.getSharedPreferences("SimpleLogic", 0);

        order=sp.getString("order", "");
		String serailNo=sp.getString("SimID", "");
        //uniqueId = serailNo+"_"+getTodaysDate() + "_" + getCurrentTime();
        //current = uniqueId + ".png";
        //mypath= new File(directory,current);
 
        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = (Button)findViewById(R.id.clear);
        mClear.setBackgroundColor(Color.parseColor("#414042"));
        mGetSign = (Button)findViewById(R.id.getsign);
        mGetSign.setBackgroundColor(Color.parseColor("#414042"));
        //mGetSign.setEnabled(false);
        mCancel = (Button)findViewById(R.id.cancel);
        mCancel.setBackgroundColor(Color.parseColor("#414042"));
        mView = mContent;
 
        yourName = (EditText) findViewById(R.id.yourName);
        
        Intent i=getIntent();
        dataOrder=i.getParcelableArrayListExtra("productsList");
        
//        for (Iterator iterator = dataOrder.iterator(); iterator.hasNext();) {
//			Product type = (Product) iterator.next();
//			totalPrice=totalPrice+Float.parseFloat(type.getProducttotalPrice());
//		}
        
    	SharedPreferences sp1 = ReturnOrder3.this
				.getSharedPreferences("SimpleLogic", 0);
    	
    	
    	//userID=sp1.getInt("UserID", 0);
    	cityID=sp1.getInt("CityID", 0);
    	beatID=sp1.getInt("BeatID", 0);
    	retailerID=sp1.getInt("RetailerID", 0);
    	distID=sp1.getInt("DistributorID", 0);
    	retailer_code=sp1.getString("RetailerCode", "");
    	retailer_mobile=sp1.getString("RetailerMobile", "");
    	retailer_emailID=sp1.getString("RetailerEmailId", "");
    	dist_mobile=sp1.getString("DistributorMobile", "");
    	dist_emailID=sp1.getString("DistributorEmailId", "");
    	
    /*	Log.e("DATA", "userID : "+userID );
    	Log.e("DATA", "cityID : "+cityID );
    	Log.e("DATA", "beatID : "+beatID );
    	Log.e("DATA", "retailerID : "+retailerID );*/
    	
 
        /*mClear.setOnClickListener(new OnClickListener() 
        {        
            public void onClick(View v) 
            {
                Log.v("log_tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });*/
        
        mClear.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
			        b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			        //down event
			        b.setBackgroundColor(Color.parseColor("#910505"));
			        Log.v("log_tag", "Panel Cleared");
	                mSignature.clear();
                    m_sign_flag = 0;
	               // mGetSign.setEnabled(false);
			    }
				return false;
			}
		});
         
        mGetSign.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
			        b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			        //down event
			        b.setBackgroundColor(Color.parseColor("#910505"));

	                Log.v("log_tag", "Panel Saved");
	                boolean error = captureSignature();
	                if(!error){
	                    mView.setDrawingCacheEnabled(true);

                        LinearLayout content = (LinearLayout) findViewById(R.id.linearLayout);
                        content.setDrawingCacheEnabled(true);
                        final Bitmap bitmap = content.getDrawingCache();
	                    
	                    //finish();
	                    
	                 // TODO Auto-generated method stub
						//v.setBackgroundColor(Color.parseColor("#910505"));
                        if(m_sign_flag == 0)
                        {
                            Toast toast = Toast.makeText(ReturnOrder3.this, "Please Sign.... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 105, 50);
                            toast.show();
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(ReturnOrder3.this).create(); //Read Update
                            alertDialog.setTitle("Confirmation");
                            alertDialog.setMessage("Are you sure you want to continue?");
                            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub

                                    //dbvoc = new DataBaseHelper(CaptureSignature.this);
                                    //dbvoc.getDeleteTable("order_products");
                                    //dbvoc.getDeleteTable("orders");

                                    //finish();

                                        /*Bundle b = new Bundle();
                                        b.putString("status", "done");
                                        Intent intent = new Intent();
                                        intent.putExtras(b);
                                        setResult(RESULT_OK,intent);
                                    Intent goToMainActivity = new Intent(CaptureSignature.this,MainActivity.class);
                                       goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
                                       startActivity(goToMainActivity);*/


                                    //Toast.makeText(getApplicationContext(), "Order Generated", Toast.LENGTH_LONG).show();

                                    //Toast toast = Toast.makeText(getApplicationContext(),"Order Generated at Chandivali \n"+"Lat :19.106081 & Longitude :72.896902", Toast.LENGTH_SHORT);
                                    /*Toast toast = Toast.makeText(getApplicationContext(),"Order Generated at Chandivali", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Toast toast1 = Toast.makeText(getApplicationContext(),"Lat :19.106081 & Longitude :72.896902", Toast.LENGTH_SHORT);
                                    toast1.setGravity(Gravity.CENTER, 0, 0);
                                    toast1.show();*/
                                    // myDbHelper = new DatabaseHandler(getApplicationContext());
                                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                    } else
                                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                    //								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(CaptureSignature.this);

                                    File storagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME + "/" + Global_Data.GLOvel_CUSTOMER_ID);
                                    storagePath.mkdirs();

                                    File myImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".jpg");


                                    String uploadImage = "";

                                    try {
                                        FileOutputStream out = new FileOutputStream(myImage);
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
                                        out.flush();
                                        out.close();
                                        uploadImage = getStringImage(bitmap);
                                        dbvoc.updateORDER_SIGNATURE_Return(uploadImage, Global_Data.GLObalOrder_id_return);
                                        mSignature.clear();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    try {
                                        //delete(mediaStorageDir);
                                        if (storagePath.isDirectory()) {
                                            String[] children = storagePath.list();
                                            for (int i = 0; i < children.length; i++) {
                                                new File(storagePath, children[i]).delete();
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    //								   insertOrderAsyncTask.execute();
                                    isInternetPresent = cd.isConnectingToInternet();

                                    if (isInternetPresent) {
                                        getServices.SYNCORDER_BYCustomer_Return(ReturnOrder3.this);

                                    } else {
                                        // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

                                        Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                                        get_dialog();
                                    }

                                }
                            });

                            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.cancel();
                                }
                            });

                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }
	                }
	            
			    }
				return false;
			}
		});
 
       /* mCancel.setOnClickListener(new OnClickListener() 
        {        
            public void onClick(View v) 
            {
                Log.v("log_tag", "Panel Canceled");
                Bundle b = new Bundle();
                b.putString("status", "cancel");
                Intent intent = new Intent();
                intent.putExtras(b);
                setResult(RESULT_OK,intent);  
                finish();
            }
        });*/
        
        mCancel.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
			        b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			        //down event
			        b.setBackgroundColor(Color.parseColor("#910505"));

	                Log.v("log_tag", "Panel Canceled");
	                Bundle b1 = new Bundle();
	                b1.putString("status", "cancel");
	                Intent intent = new Intent();
	                intent.putExtras(b1);
	                setResult(RESULT_OK,intent);  
	                finish();
	            
			    }
				return false;
			}
		});
 
    }
 
    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }
 
    private boolean captureSignature() {
 
        boolean error = false;
        String errorMessage = "";
 
 
        if(yourName.getText().toString().equalsIgnoreCase("")){
            errorMessage = errorMessage + "Please enter your Name\n";
            error = true;
        }   
 
        if(error){
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 105, 50);
            toast.show();
        }
 
        return error;
    }
 
    private String getTodaysDate() { 
 
        final Calendar c = Calendar.getInstance();
        int todaysDate =     (c.get(Calendar.YEAR) * 10000) + 
        ((c.get(Calendar.MONTH) + 1) * 100) + 
        (c.get(Calendar.DAY_OF_MONTH));
        Log.w("DATE:",String.valueOf(todaysDate));
        return(String.valueOf(todaysDate));
 
    }
 
    private String getCurrentTime() {
 
        final Calendar c = Calendar.getInstance();
        int currentTime =     (c.get(Calendar.HOUR_OF_DAY) * 10000) + 
        (c.get(Calendar.MINUTE) * 100) + 
        (c.get(Calendar.SECOND));
        Log.w("TIME:",String.valueOf(currentTime));
        return(String.valueOf(currentTime));
 
    }
 
 
    private boolean prepareDirectory() 
    {
        try
        {
            if (makedirs()) 
            {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) 
        {
            e.printStackTrace();
            Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }
 
    private boolean makedirs() 
    {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();
 
        if (tempdir.isDirectory()) 
        {/*
            File[] files = tempdir.listFiles();
            for (File file : files) 
            {
                if (!file.delete()) 
                {
                    System.out.println("Failed to delete " + file);
                }
            }
        */}
        return (tempdir.isDirectory());
    }
 
    public class signature extends View 
    {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();
 
        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();
 
        public signature(Context context, AttributeSet attrs) 
        {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }
 
        public void save(View v,String mypath) 
        {
            Log.e("log_tag", "Width: " + v.getWidth());
            Log.e("log_tag", "Height: " + v.getHeight());
            if(mBitmap == null)
            {
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);;
            }
            Canvas canvas = new Canvas(mBitmap);
            try
            {
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);
 
                v.draw(canvas); 
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream); 
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                Log.e("log_tag","mypath: " + mypath);
                
                Log.e("log_tag","url: " + url);
                //In case you want to delete the file
                //boolean deleted = mypath.delete();
                //Log.v("log_tag","deleted: " + mypath.toString() + deleted);
                //If you want to convert the image to string use base64 converter
 
            }
            catch(Exception e) 
            { 
                Log.v("log_tag", e.toString()); 
            } 
        }
 
        public void clear() 
        {
            path.reset();
            invalidate();
        }
 
        @Override
        protected void onDraw(Canvas canvas) 
        {
            canvas.drawPath(path, paint);
        }
 
        @Override
        public boolean onTouchEvent(MotionEvent event) 
        {
            float eventX = event.getX();
            float eventY = event.getY();
            m_sign_flag = 1;
            mGetSign.setEnabled(true);
 
            switch (event.getAction()) 
            {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                return true;
 
            case MotionEvent.ACTION_MOVE:
 
            case MotionEvent.ACTION_UP:
 
                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) 
                {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;
 
            default:
                debug("Ignored touch event: " + event.toString());
                return false;
            }
 
            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));
 
            lastTouchX = eventX;
            lastTouchY = eventY;
 
            return true;
        }
 
        private void debug(String string){
        }
 
        private void expandDirtyRect(float historicalX, float historicalY) 
        {
            if (historicalX < dirtyRect.left) 
            {
                dirtyRect.left = historicalX;
            } 
            else if (historicalX > dirtyRect.right) 
            {
                dirtyRect.right = historicalX;
            }
 
            if (historicalY < dirtyRect.top) 
            {
                dirtyRect.top = historicalY;
            } 
            else if (historicalY > dirtyRect.bottom) 
            {
                dirtyRect.bottom = historicalY;
            }
        }
 
        private void resetDirtyRect(float eventX, float eventY) 
        {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
    
    

	public class InsertOrderAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;
		float f=0.00f;
		String createdID="";
		 ConnectionDetector cd ; 
		// TelephonyManager manager;
		 int simState;

		public InsertOrderAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(activity);
			 cd  = new ConnectionDetector(getApplicationContext());
			 //manager=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
				// simState=manager.getSimState();
				
			
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Generating Order...Please wait");
			dialog.setCancelable(false);
			this.dialog.show();
			

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			/*try {
				//dataProducts=(ArrayList<DatabaseProductModel>) myDbHelper.ger(spnCategory.getSelectedItem().toString());
				
				 
					
				  if (order.equalsIgnoreCase("new")||order.equalsIgnoreCase("previous")) {
					  
					  try {
						
						  ret_Name=yourName.getText().toString();
						  createdID=myDbHelper.generateOrder(userID,cityID,beatID,retailerID,retailer_code,distID,dataOrder,totalPrice,ret_Name,getDateTime());
							myDbHelper.insertPreviousOrder(userID,cityID,beatID,retailerID,retailer_code,distID,dataOrder,totalPrice,ret_Name,getDateTime(),createdID);
							f=myDbHelper.getupdatedTarget(userID,getDateTime());
							 mSignature.save(mView,tempDir+createdID+"_"+ret_Name+".png");
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("DATA new/previous Exception", e.getMessage());
					}
					   
				  }
				  if (order.equalsIgnoreCase("return")) {
					  
					  try {
						  ret_Name=yourName.getText().toString();
						  createdID=myDbHelper.generateReturnOrder(userID,cityID,beatID,retailerID,retailer_code,distID,dataOrder,totalPrice,ret_Name,getDateTime());
						  f=myDbHelper.getupdatedTarget(userID,getDateTime());
						  mSignature.save(mView,tempDir+createdID+"_"+ret_Name+".png");
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("DATA return Exception", e.getMessage());
					}
					  
				  }
				
					if (simState==TelephonyManager.SIM_STATE_READY) {
				  if (!retailer_mobile.equalsIgnoreCase("NA")) {
					   Real Time Code Start
						//SmsManager smsManager=SmsManager.getDefault(); // kirti
						//smsManager.sendTextMessage("+91"+retailer_mobile, null, "Order ID : "+createdID+" is generated", null, null); // smssend kirti
					   Real Time Code End
					}
					}
					
					if (!dist_emailID.equalsIgnoreCase("NA")){
						createExcel(userID,retailerID,distID,createdID);
					}
					
					  if (cd.isConnectingToInternet()) {
		                    // Internet Connection is Present
		                    // make HTTP requests
						  
						  sendEmail(createdID,retailer_emailID);
						  sendEmailtoDistributor(createdID,dist_emailID,new File(Environment.getExternalStorageDirectory().toString() + "/SimpleLogic/"+"order_"+userID+".xls"));
		                   
		                }
					 
					
			} catch (Exception e) {
				// TODO: handle exception
			}*/
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			 SharedPreferences spf=ReturnOrder3.this.getSharedPreferences("SimpleLogic",0);        
		        SharedPreferences.Editor editor=spf.edit();        
		        //editor.putString("UserID", userid);
		       
		        editor.putFloat("Current_Target", f);
		        editor.commit();
		        
			 
		        
		     // create a Dialog component
				final Dialog dialog = new Dialog(context);

				//tell the Dialog to use the dialog.xml as it's layout description
				dialog.setContentView(R.layout.dialog);
				dialog.setTitle("Order Status :");

				TextView txt = (TextView) dialog.findViewById(R.id.txtOrderID);

				txt.setText("Order is generated.");
				TextView txtMessage = (TextView) dialog.findViewById(R.id.txtMessage);
				TextView txtEmail = (TextView) dialog.findViewById(R.id.txtEmail);
				
				
				/*if (simState==TelephonyManager.SIM_STATE_READY) 
				{
					if (retailer_mobile.equalsIgnoreCase("NA")) {
					txtMessage.setText("Mobile Number is not present for this retailer.");
				}
				else {
					txtMessage.setText("SMS has been sent to "+retailer_mobile+".");
				}
				}
				
				else {
					txtMessage.setText("No SIM card detected");
				}
				
				
				  if (!cd.isConnectingToInternet()) {
	                    // Internet Connection is Present
	                    // make HTTP requests
					  txtEmail.setText("No internet connection..unable to send mail.");
	                }
				  else {
					  
					  if (!dist_emailID.equalsIgnoreCase("NA")) {
						  txtEmail.setText("Mail has been sent to Admin and Distributor.");
					}
					  else {
						  txtEmail.setText("Mail has been sent to Admin.");
					}
					  
					  
				}*/
				  
			ImageView dialogButton = (ImageView) dialog.findViewById(R.id.dialogButton);
				
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
						Bundle b = new Bundle();
			            b.putString("status", "done");
			            Intent intent = new Intent();
			            intent.putExtras(b);
			            setResult(RESULT_OK,intent);  
					
			            SharedPreferences spf=ReturnOrder3.this.getSharedPreferences("SimpleLogic",0);        
				        SharedPreferences.Editor editor=spf.edit();        
				        //editor.putString("UserID", userid);
				       
				        editor.putInt("Capture", 1);
				        editor.commit();
			            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			            Intent intentn = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(intentn);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
			            finish();
			           
					}
				});

				dialog.show();
		  
			
			
		}
	}
	
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
	
//	public void sendEmail(String createdID, String emailID2) {
//		// TODO Auto-generated method stub
//
//		try {
//            GmailSender sender = new GmailSender("test.simple.logic@gmail.com", "simplelogic123456789");
//
//
//            /* Real Time Code Start*/
//            if (!emailID2.equalsIgnoreCase("NA")) {
//            	 sender.sendMail("Order Status",
//                         "Order ID : "+createdID+" is generated.",
//                         "test.simple.logic@gmail.com",
//                         emailID2+",test.simple.logic@gmail.com,krunal.gujarathi@simplelogic.in");
//			}
//
//            else {
//            	 sender.sendMail("Order Status",
//                         "Order ID : "+createdID+" is generated.",
//                         "test.simple.logic@gmail.com",
//                         "test.simple.logic@gmail.com,krunal.gujarathi@simplelogic.in");
//			}
//
//            /* Real Time Code End*/
//
//            /*sender.sendMail("Order Status",
//                    "Order ID : "+createdID+" is generated.",
//                    "test.simple.logic@gmail.com",
//                    "test.simple.logic@gmail.com,krunal.gujarathi@simplelogic.in"); */
//
//        } catch (Exception e) {
//            Log.e("SendMail", e.getMessage(), e);
//        }
//
//	}
	
//	public void sendEmailtoDistributor(String createdID, String emailID2,File f) {
//		// TODO Auto-generated method stub
//
//		try {
//            GmailSender sender = new GmailSender("test.simple.logic@gmail.com", "simplelogic123456789");
//
//            if (!emailID2.equalsIgnoreCase("NA")) {
//            	 /* Real Time Code Start*/
//            	  sender.sendMailAttachement("Order Status",
//                          "Hi \n Order ID : "+createdID+" is generated.Please find attachment.",
//                          "test.simple.logic@gmail.com",
//                          emailID2+",test.simple.logic@gmail.com,krunal.gujarathi@simplelogic.in",f);
//            	 /* Real Time Code End*/
//
//            	/* sender.sendMailAttachement("Order Status",
//                         "Hi \n Order ID : "+createdID+" is generated.Please find attachment.",
//                         "test.simple.logic@gmail.com",
//                         "test.simple.logic@gmail.com,krunal.gujarathi@simplelogic.in",f);  */
//			}
//
//            else {
//
//			}
//
//        } catch (Exception e) {
//            Log.e("SendMail", e.getMessage(), e);
//        }
//	}

	
//	public boolean createExcel(int userID,int retailerID,int distID,String createdID) throws IOException {
//		// TODO Auto-generated method stub
//
//		boolean ordersExists=false;
//		ArrayList<DistributorOrder> orders =myDbHelper.loadDistributorOrder(createdID,retailerID);
//
//
//        if (orders.size()!=0) {
//        	String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
//    		File f=new File(extStorageDirectory + "/SimpleLogic");
//    		if (!f.exists()) {
//    			f.mkdir();
//    		}
//    		 f=new File(extStorageDirectory + "/SimpleLogic/"+"order_"+userID+".xls");
//    		if (!f.exists()) {
//    			f.createNewFile();
//    		}
//
//    		HSSFWorkbook workbook = new HSSFWorkbook();
//
//    		//***order_master Sheet Start***//*
//    		HSSFSheet sheet = workbook.createSheet("Order");
//
//    		HSSFRow rowhead=   sheet.createRow((short)0);
//
//            rowhead.createCell((short) 0).setCellValue("Order ID");
//            rowhead.createCell((short) 1).setCellValue("Category");
//            rowhead.createCell((short) 2).setCellValue("Product");
//            rowhead.createCell((short) 3).setCellValue("Product Spec");
//            rowhead.createCell((short) 4).setCellValue("Delivery Quantity");
//            rowhead.createCell((short) 5).setCellValue("Retailer");
//            rowhead.createCell((short) 6).setCellValue("Address");
//            int i=1;
//            for (Iterator iterator = orders.iterator(); iterator.hasNext();) {
//    			DistributorOrder distributorOrder = (DistributorOrder) iterator
//    					.next();
//    			 //Log.e("DATA", distributorOrder.toString());
//    			HSSFRow row=   sheet.createRow((short)i);
//				row.createCell((short) 0).setCellValue(distributorOrder.getOrderID());
//				row.createCell((short) 1).setCellValue(distributorOrder.getCategory());
//				row.createCell((short) 2).setCellValue(distributorOrder.getProduct());
//				row.createCell((short) 3).setCellValue(distributorOrder.getProductSpec());
//				row.createCell((short) 4).setCellValue(distributorOrder.getDelQuantity());
//				row.createCell((short) 5).setCellValue(distributorOrder.getRetailerName());
//				row.createCell((short) 6).setCellValue(distributorOrder.getRetailerAddress());
//				 i++;
//    		}
//
//
//            FileOutputStream out = new FileOutputStream(f);
//    	    workbook.write(out);
//    	    out.close();
//    	    Log.e("STATUS", "EXCEL READY");
//		}
//
//        return ordersExists;
//	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

    public void get_dialog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(ReturnOrder3.this).create(); //Read Update
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Your internet not working, Do you want to save order offline ?");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Save",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub


                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(CaptureSignature.this);
//								   insertOrderAsyncTask.execute();

              //  Toast.makeText(getApplicationContext(),"Order generate successfully.",Toast.LENGTH_LONG).show();

                Toast toast = Toast.makeText(getApplicationContext(),"Order generate successfully.",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                Intent intent = new Intent(getApplicationContext(),
                        com.anchor.activities.Order.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);

            }
        });

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.cancel();
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        // String encodedImage =imageBytes.toString();

        return encodedImage;
    }
}