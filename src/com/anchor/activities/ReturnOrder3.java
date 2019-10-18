package com.anchor.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anchor.model.Product;
import com.anchor.services.getServices;
import com.anchor.webservice.ConnectionDetector;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

        SharedPreferences  sp=this.getSharedPreferences("SimpleLogic", 0);

        order=sp.getString("order", "");

 
        mContent = findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = findViewById(R.id.clear);
        mClear.setBackgroundColor(Color.parseColor("#414042"));
        mGetSign = findViewById(R.id.getsign);
        mGetSign.setBackgroundColor(Color.parseColor("#414042"));
        //mGetSign.setEnabled(false);
        mCancel = findViewById(R.id.cancel);
        mCancel.setBackgroundColor(Color.parseColor("#414042"));
        mView = mContent;
 
        yourName = findViewById(R.id.yourName);
        
        Intent i=getIntent();
        dataOrder=i.getParcelableArrayListExtra("productsList");
        

        
    	SharedPreferences sp1 = ReturnOrder3.this
				.getSharedPreferences("SimpleLogic", 0);
    	
    	

        
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
                        requestStoragePermissionsave();
	                }
	            
			    }
				return false;
			}
		});
 

        
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
                mBitmap =  Bitmap.createBitmap (mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
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

    /**
     * Requesting multiple permissions (storage and location) at once
     * This uses multiple permission model from dexter
     * On permanent denial opens settings dialog
     */
    private void requestStoragePermissionsave() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            mView.setDrawingCacheEnabled(true);

                            LinearLayout content = findViewById(R.id.linearLayout);
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

                                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                        } else
                                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                                        //								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(CaptureSignature.this);

                                        File storagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME + "/" + Global_Data.GLOvel_CUSTOMER_ID);
                                        storagePath.mkdirs();

                                        File myImage = new File(storagePath, System.currentTimeMillis() + ".jpg");


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
        AlertDialog.Builder builder = new AlertDialog.Builder(ReturnOrder3.this);
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
}