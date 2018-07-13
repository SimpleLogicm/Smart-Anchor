package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.GPSTracker;

//import com.simplelogic.database.DatabaseHandler;
//import com.simplelogic.webservice.GmailSender;

public class Previous_orderNew_S3 extends BaseActivity {
    //DataBaseHelper dbvoc;
    private String Signature_path = "";
    Boolean isInternetPresent = false;
    GPSTracker gps;
    ImageView get_icon;
    Boolean B_flag;
    String strdetail1_mandate,strdetail2_mandate,strdetail4_mandate;;
    Bitmap bitmap1;
    byte b5[];
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_FROM_CAMERA = 1;
    public static final int MEDIA_TYPE_IMAGE = 1;
    ConnectionDetector cd;
    String str_uri;
    private Uri fileUri;
    LinearLayout mContent;
    signature mSignature;
    Button mClear, mGetSign, mCancel;
    int m_sign_flag = 0;
    DatePickerDialog.OnDateSetListener date,date1;
    ArrayList<Product> dataOrder=new ArrayList<Product>();
    public static String tempDir;
    public int count = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public String current = null;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private Bitmap mBitmap;
    View mView;
    String detail1str, detail2str,detail4str;;
    int userID,cityID,beatID,retailerID,distID;
    TextView details1,details2;
    Calendar myCalendar;
    TextView txtWelcomeUser;
    //private String uniqueId;
    private EditText yourName,order_detail1,order_detail2,order_detail4;
    Spinner order_type,shipment_pri;
    ArrayAdapter<String> dataAdapter_order_type,dataAdapter_shipment_pri;
    List<String> list_order_type;
    private ArrayList<String> results_order_type = new ArrayList<String>();
    private ArrayList<String> results_shipment_pri = new ArrayList<String>();
    float totalPrice;
    public String order="",retailer_mobile="",retailer_emailID="",dist_mobile="",dist_emailID="",retailer_code="",ret_Name="";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setContentView(R.layout.activity_previous_order_new_s3);

        tempDir = Environment.getExternalStorageDirectory() + "/SimpleLogic/" + getResources().getString(R.string.external_dir) + "/";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // File directory = cw.getDir(getResources().getString(R.string.external_dir), Context.MODE_PRIVATE);

        File directory = new File(tempDir);
        cd  = new ConnectionDetector(getApplicationContext());
        prepareDirectory();
//        uniqueId = getTodaysDate() + "_" + getCurrentTime() + "_" + Math.random();
        SharedPreferences  sp=this.getSharedPreferences("SimpleLogic", 0);

        order=sp.getString("order", "");
        String serailNo=sp.getString("SimID", "");
        //uniqueId = serailNo+"_"+getTodaysDate() + "_" + getCurrentTime();
        //current = uniqueId + ".png";
        //mypath= new File(directory,current);

        txtWelcomeUser=(TextView) findViewById(R.id.txtWelcomeUser);
        mContent = (LinearLayout) findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = (Button)findViewById(R.id.clear);
        mClear.setBackgroundColor(Color.parseColor("#414042"));
        mGetSign = (Button)findViewById(R.id.getsign);
        mGetSign.setBackgroundColor(Color.parseColor("#414042"));
        // mGetSign.setEnabled(false);
        mCancel = (Button)findViewById(R.id.cancel);
        mCancel.setBackgroundColor(Color.parseColor("#414042"));
        mView = mContent;

        yourName = (EditText) findViewById(R.id.yourName);
        order_detail1 = (EditText) findViewById(R.id.order_detail1);
        order_detail2 = (EditText) findViewById(R.id.order_detail2);
        order_detail4 = (EditText) findViewById(R.id.yourRemarks);
        get_icon = (ImageView) findViewById(R.id.get_icon);
        order_type = (Spinner) findViewById(R.id.order_type);
        shipment_pri = (Spinner) findViewById(R.id.shipment_pri);
        //details1 = (TextView) findViewById(R.id.details1);
        // details2 = (TextView) findViewById(R.id.details2);
        //order_detail1.setInputType(InputType.TYPE_CLASS_NUMBER);
        //get_icon.setBackgroundColor(Color.parseColor("#414042"));
        Intent i=getIntent();
        dataOrder=i.getParcelableArrayListExtra("productsList");

//        for (Iterator iterator = dataOrder.iterator(); iterator.hasNext();) {
//			Product type = (Product) iterator.next();
//			totalPrice=totalPrice+Float.parseFloat(type.getProducttotalPrice());
//		}

        // for label change

        String user_name = "";
        if(!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null"))
        {
            user_name = Global_Data.USER_FIRST_NAME.trim();
            if(!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null"))
            {
                user_name +=  " " + Global_Data.USER_LAST_NAME.trim();
            }
        }

        txtWelcomeUser.setText(user_name+" : "+Global_Data.emp_code);
        SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
        detail1str=spf1.getString("var_detail1", "");

        if(detail1str.length()>0)
        {
            order_detail1.setHint(detail1str);
        }else{
            order_detail1.setText("Detail 1");
        }

        InputFilter[] Textfilters = new InputFilter[1];
        Textfilters[0] = new InputFilter(){
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {

                    char[] acceptedChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','.'};

                    for (int index = start; index < end; index++) {
                        if (!new String(acceptedChars).contains(String.valueOf(source.charAt(index)))) {
                            return "";
                        }
                    }
                }
                return null;
            }
        };

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }
        };

        // for label change
        SharedPreferences spf2=this.getSharedPreferences("SimpleLogic",0);
        detail2str=spf2.getString("var_detail2", "");

        SharedPreferences spf22=this.getSharedPreferences("SimpleLogic",0);
        detail4str=spf22.getString("var_detail4", "");

        SharedPreferences spf3=this.getSharedPreferences("SimpleLogic",0);
        String strdetail1_edit=spf3.getString("var_detail1_edit", "");

        SharedPreferences spf4=this.getSharedPreferences("SimpleLogic",0);
        strdetail1_mandate=spf4.getString("var_detail1_mandate", "");

        SharedPreferences spf5=this.getSharedPreferences("SimpleLogic",0);
        String strdetail1_allow=spf5.getString("var_detail1_allow", "");

        SharedPreferences spf23=this.getSharedPreferences("SimpleLogic",0);
        String strdetail2_edit=spf23.getString("var_detail2_edit", "");

        SharedPreferences spf233=this.getSharedPreferences("SimpleLogic",0);
        String strdetail4_edit=spf233.getString("var_detail4_edit", "");

        SharedPreferences spf24=this.getSharedPreferences("SimpleLogic",0);
        strdetail2_mandate=spf24.getString("var_detail2_mandate", "");

        SharedPreferences spf244=this.getSharedPreferences("SimpleLogic",0);
        strdetail4_mandate=spf244.getString("var_detail4_mandate", "");

        SharedPreferences spf25=this.getSharedPreferences("SimpleLogic",0);
        String strdetail2_allow=spf25.getString("var_detail2_allow", "");

        SharedPreferences spf255=this.getSharedPreferences("SimpleLogic",0);
        String strdetail4_allow=spf255.getString("var_detail4_allow", "");

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                char[] chars = {'\'','"'};
                for (int i = start; i < end; i++) {
                    if (new String(chars).contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };
        order_detail4.setFilters(new InputFilter[] { filter });

        if(strdetail1_edit.equalsIgnoreCase("true"))
        {
            order_detail1.setEnabled(true);

            if(strdetail1_allow.equalsIgnoreCase("Text"))
            {
                order_detail1.setInputType(InputType.TYPE_CLASS_TEXT);
            }else if(strdetail1_allow.equalsIgnoreCase("Integer")){
                order_detail1.setInputType(InputType.TYPE_CLASS_NUMBER);
            }else{
                order_detail1.setFocusableInTouchMode(false);
                order_detail1.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        DatePickerDialog picker = new DatePickerDialog(Previous_orderNew_S3.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH));
                        picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                        picker.show();

                    }
                });

                //order_detail1.setInputType(InputType.TYPE_CLASS_DATETIME);
            }
        }
        if(strdetail2_edit.equalsIgnoreCase("true"))
        {
            order_detail2.setEnabled(true);

            if(strdetail2_allow.equalsIgnoreCase("Text"))
            {
                order_detail2.setInputType(InputType.TYPE_CLASS_TEXT);
            }else if(strdetail2_allow.equalsIgnoreCase("Integer")){
                order_detail2.setInputType(InputType.TYPE_CLASS_NUMBER);
            }else{
                order_detail2.setFocusableInTouchMode(false);
                order_detail2.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(Previous_orderNew_S3.this, date1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        }

        if(detail2str.length()>0)
        {
            order_detail2.setHint(detail2str);
        }else{
            order_detail2.setHint("Detail 2");
        }


        if(strdetail4_edit.equalsIgnoreCase("true"))
        {
            order_detail4.setEnabled(true);

            if(strdetail4_allow.equalsIgnoreCase("Text"))
            {
                order_detail4.setInputType(InputType.TYPE_CLASS_TEXT);
            }else if(strdetail4_allow.equalsIgnoreCase("Integer")){
                order_detail4.setInputType(InputType.TYPE_CLASS_NUMBER);
            }else{
                order_detail4.setFocusableInTouchMode(false);
                order_detail4.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        new DatePickerDialog(Previous_orderNew_S3.this, date1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        }

        if(detail4str.length()>0)
        {
            order_detail4.setHint(detail4str);
        }else{
            order_detail4.setHint("Remarks");
        }

        SharedPreferences sp1 = Previous_orderNew_S3.this
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


        results_order_type.clear();
        List<Local_Data> contacts1 = dbvoc.getorder_category();
        results_order_type.add("Select Order Type");
        for (Local_Data cn : contacts1)
        {
            if(!cn.getOrder_type_name().equalsIgnoreCase("") && !cn.getOrder_type_name().equalsIgnoreCase(" "))
            {
                String str_categ = ""+cn.getOrder_type_name();
                results_order_type.add(str_categ);
            }
        }

        dataAdapter_order_type = new ArrayAdapter<String>(this, R.layout.spinner_item, results_order_type);
        dataAdapter_order_type.setDropDownViewResource(R.layout.spinner_item);
        order_type.setAdapter(dataAdapter_order_type);

        results_shipment_pri.clear();

        results_shipment_pri.add("Shipment Priority");
        results_shipment_pri.add("High");
        results_shipment_pri.add("Standard");
        results_shipment_pri.add("Low");

        dataAdapter_shipment_pri = new ArrayAdapter<String>(this, R.layout.spinner_item, results_shipment_pri);
        dataAdapter_shipment_pri.setDropDownViewResource(R.layout.spinner_item);
        shipment_pri.setAdapter(dataAdapter_shipment_pri);


        String cc_code = "";

        List<Local_Data> contacts = dbvoc.GetOrders_details("Secondary Sales / Retail Sales", Global_Data.GLOvel_GORDER_ID);

        if(contacts.size() > 0)
        {
            for (Local_Data cn : contacts)
            {
                yourName.setText(cn.getOrder_detail3());
                order_detail1.setText(cn.getOrder_detail1());
                order_detail2.setText(cn.getOrder_detail2());
                cc_code = cn.getOrder_category_type().trim();
            }
        }

        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cc_code)){


            List<Local_Data> cont = dbvoc.get_order_category_name(cc_code);

            for (Local_Data cn : cont)
            {
                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getOrder_type_name().trim())){
                    int spinnerPosition = dataAdapter_order_type.getPosition(cn.getOrder_type_name().trim());
                    order_type.setSelection(spinnerPosition);
                }
            }
        }

        get_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                B_flag = isDeviceSupportCamera();

                if(B_flag == true)
                {
//                    //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                   // fileUri = getOutputMediaFileUrinew(MEDIA_TYPE_IMAGE);
//                    Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    //startActivityForResult(intent1, MEDIA_TYPE_IMAGE);
//                    startActivityForResult(intent1, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "no camera on this device", Toast.LENGTH_LONG).show();
                }

            }
        });

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
                    //  mGetSign.setEnabled(false);

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
                    boolean error = Previous_orderNew_S3();
                    // boolean error1 = Previous_orderNew_S31();

                    if(!error) {
                        // if(!error1) {
                        mView.setDrawingCacheEnabled(true);
                        LinearLayout content = (LinearLayout) findViewById(R.id.linearLayout);
                        content.setDrawingCacheEnabled(true);
                        final Bitmap bitmap = content.getDrawingCache();
                        //finish();

                        // TODO Auto-generated method stub
                        //v.setBackgroundColor(Color.parseColor("#910505"));

                        if (m_sign_flag == 0) {
                            Toast toast = Toast.makeText(Previous_orderNew_S3.this, "Please Sign.... ", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 105, 50);
                            toast.show();
                        } else {
                            gps = new GPSTracker(Previous_orderNew_S3.this);
                            if(!gps.canGetLocation()){

                                gps.showSettingsAlertnew();
                            }
                            else
                            {
                                AlertDialog alertDialog = new AlertDialog.Builder(Previous_orderNew_S3.this).create(); //Read Update
                                alertDialog.setTitle("Confirmation");
                                alertDialog.setMessage(" Are you sure you want to continue?");
                                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub

                                        String order_detail1_text = "";
                                        String order_detail2_text = "";
                                        String order_detail4_text = "";
                                        String order_type_text = "";
                                        String order_type_name = "";
                                        String order_type_code = "";
                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(order_detail1.getText().toString().trim())) {

                                            order_detail1_text = order_detail1.getText().toString().trim();
                                        }

                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(order_detail2.getText().toString().trim())) {

                                            order_detail2_text = order_detail2.getText().toString().trim();
                                        }

                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(order_detail4.getText().toString().trim())) {

                                            order_detail4_text = order_detail4.getText().toString().trim();
                                        }

                                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(yourName.getText().toString().trim())) {

                                            order_type_name = yourName.getText().toString().trim();
                                        }

                                        if (!(order_type.getSelectedItem().toString().equalsIgnoreCase("Select Order Type"))) {

                                            order_type_text = order_type.getSelectedItem().toString();
                                            List<Local_Data> contacts1 = dbvoc.get_order_category_code(order_type_text);

                                            for (Local_Data cn : contacts1) {

                                                order_type_code = cn.getOrder_type_code();
                                            }

                                        }

                                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                        } else
                                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(Previous_orderNew_S3.this);
//								   insertOrderAsyncTask.execute();

//                                        File storagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Config.IMAGE_DIRECTORY_NAME + "/" + Global_Data.GLOvel_CUSTOMER_ID);
//                                        storagePath.mkdirs();
//
//                                        File myImage = new File(storagePath, Long.toString(System.currentTimeMillis()) + ".jpg");
//
//
//                                        String uploadImage = "";

                                        try
                                        {
                                            AppLocationManager appLocationManager = new AppLocationManager(Previous_orderNew_S3.this);
                                            Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
                                            Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
                                            PlayService_Location PlayServiceManager = new PlayService_Location(Previous_orderNew_S3.this);

                                            if(PlayServiceManager.checkPlayServices(Previous_orderNew_S3.this))
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

                                        try {
//                                            FileOutputStream out = new FileOutputStream(myImage);
//                                            bitmap.compress(Bitmap.CompressFormat.PNG, 10, out);
//                                            out.flush();
//                                            out.close();
//                                            uploadImage = getStringImage(bitmap);

                                            SaveImage(bitmap,"SI"+Global_Data.GLObalOrder_id);

                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {

                                                dbvoc.updateORDER_SIGNATURENEW_WITHLATLONG(Signature_path, Global_Data.GLObalOrder_id, order_detail1_text, order_detail2_text,order_type_name,order_detail4_text, order_type_code,shipment_pri.getSelectedItem().toString(),Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE);
                                            }
                                            else
                                            {
                                                dbvoc.updateORDER_SIGNATURENEW(Signature_path, Global_Data.GLObalOrder_id, order_detail1_text, order_detail2_text,order_type_name,order_detail4_text, order_type_code,shipment_pri.getSelectedItem().toString());
                                            }
                                            mSignature.clear();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }




                                        isInternetPresent = cd.isConnectingToInternet();
                                        if (isInternetPresent) {
                                            getServices.SYNCORDER_BYCustomer(Previous_orderNew_S3.this, Global_Data.GLOvel_GORDER_ID);
                                        } else {
                                            //Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

                                            Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                                            //toast.setGravity(Gravity.CENTER, 0, 0);
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

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText(Global_Data.order_retailer + " " + "(" + Global_Data.AmountOutstanding + "/" + Global_Data.AmountOverdue + ")");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        // SharedPreferences sp = Previous_orderNew_S3.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
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
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
            todaysTarget.setText("Today's Target Acheived");
        }

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

    private boolean Previous_orderNew_S3() {
        boolean error = false;
        String errorMessage = "";
        String message_flag = "";

        if(order_type.getSelectedItem().toString().equalsIgnoreCase("Select Order Type"))
        {

            errorMessage = errorMessage + "Please Select Order Type.";
            error = true;
        }
        else
        if(strdetail1_mandate.equalsIgnoreCase("true") && order_detail1.getText().toString().equalsIgnoreCase(""))
        {
            errorMessage = errorMessage + "Please Enter " + detail1str;
            error = true;

        }
        else
        if(strdetail2_mandate.equalsIgnoreCase("true") && order_detail2.getText().toString().equalsIgnoreCase(""))
        {
            errorMessage = errorMessage + "Please Enter " + detail2str;
            error = true;

        }
        else
        if(strdetail2_mandate.equalsIgnoreCase("true") && order_detail2.getText().length() < 6)
        {
            errorMessage = errorMessage + detail2str+ " should be 6 digit number.";
            error = true;

        }
        else
        if(shipment_pri.getSelectedItem().toString().equalsIgnoreCase("Shipment Priority"))
        {

            errorMessage = errorMessage + "Please Select Shipment Priority";
            error = true;
        }
        else
        if(yourName.getText().toString().equalsIgnoreCase("")){
            errorMessage = errorMessage + "Please Enter your Name";
            error = true;
        }
        else
        if(strdetail4_mandate.equalsIgnoreCase("true") && order_detail4.getText().toString().equalsIgnoreCase(""))
        {
            errorMessage = errorMessage + "Please Enter " + detail4str;
            error = true;

        }

//        }else if((strdetail1_mandate.equalsIgnoreCase("true")) || (strdetail2_mandate.equalsIgnoreCase("true"))){
//
//            if(strdetail1_mandate.equalsIgnoreCase("true")) {
//                if (order_detail1.getText().toString().equalsIgnoreCase("")) {
//                    errorMessage = errorMessage + "Please Enter " + detail1str;
//                    error = true;
//                }
//            }
//
//            if(strdetail2_mandate.equalsIgnoreCase("true"))
//            {
//                if(order_detail2.getText().toString().equalsIgnoreCase("")){
//                    errorMessage = errorMessage + "Please Enter "+detail2str;
//                    error = true;
//                }
//            }
//        }

        if(error){
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 105, 50);
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

//    public Uri getOutputMediaFileUrinew(int type) {
//        try
//        {
//            return Uri.fromFile(getOutputMediaFilenew(type));
//        }
//        catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return null;
//    }

//    private static File getOutputMediaFilenew(int type) {
//        File mediaStorageDir;
//        // External sdcard location
//        if (type == MEDIA_TYPE_IMAGE) {
//            mediaStorageDir = new File(
//                    //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Config.IMAGE_DIRECTORY_NAME);
//                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_PICTURE");
//
//            image_path = mediaStorageDir.getPath();
//            if (!mediaStorageDir.exists()) {
//                if (!mediaStorageDir.mkdirs()) {
//                    Log.d(TAG, "Oops! Failed create "
//                            + Config.IMAGE_DIRECTORY_NAME + " directory");
//                    return null;
//                }
//                else
//                {
//                    mediaStorageDir.mkdirs();
//                }
//            }
//
//            // Create a media file name
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
//                    Locale.getDefault()).format(new Date());
//            File mediaFile;
//            if (type == MEDIA_TYPE_IMAGE) {
//                mediaFile = new File(mediaStorageDir.getPath() + File.separator
//                        + "IMG_" + timeStamp + ".jpg");
//
//            }  else {
//                return null;
//            }
//
//            return mediaFile;
//        }
//
//
//        // Create the storage directory if it does not exist
//        return null;
//    }

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
            //Toast.makeText(this, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();

            Toast toast = Toast.makeText(getApplicationContext(),"Could not initiate File System.. Is Sdcard mounted properly?",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

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
            SharedPreferences spf=Previous_orderNew_S3.this.getSharedPreferences("SimpleLogic",0);
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

                    SharedPreferences spf=Previous_orderNew_S3.this.getSharedPreferences("SimpleLogic",0);
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




    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void get_dialog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(Previous_orderNew_S3.this).create(); //Read Update
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
//								   InsertOrderAsyncTask insertOrderAsyncTask =new InsertOrderAsyncTask(Previous_orderNew_S3.this);
//								   insertOrderAsyncTask.execute();

                //  Toast.makeText(getApplicationContext(),"Order generate successfully.",Toast.LENGTH_LONG).show();

                Toast toast = Toast.makeText(getApplicationContext(),"Order generate successfully.",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                Intent intent = new Intent(getApplicationContext(),
                        Order.class);
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





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//       if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                previewCapturedImage();
//
//            } else if (resultCode == RESULT_CANCELED) {
//                // user cancelled Image capture
//                Toast.makeText(getApplicationContext(),
//                        "User cancelled image capture", Toast.LENGTH_SHORT)
//                        .show();
//            } else {
//                // failed to capture image
//                Toast.makeText(getApplicationContext(),
//                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream bos5 = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos5);
            b5 = bos5.toByteArray();

            String getsign_str=Base64.encodeToString(b5,Base64.DEFAULT);

            try {

                dbvoc.updateORDER_order_image(getsign_str,Global_Data.GLObalOrder_id);

            } catch (Exception e) {
                e.printStackTrace();
            }

            //get_icon.setImageBitmap(imageBitmap);
        }

    }

    private void SaveImage(Bitmap finalBitmap,String name) {

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File myDir = new File(path,"Anchor_Signature");
        myDir.mkdirs();

        String fname = name +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Signature_path = "file:" + file.getAbsolutePath();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Call this whn the user has chosen the date and set the Date in the EditText in format that you wish
    private void updateLabel() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        order_detail1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        order_detail2.setText(sdf.format(myCalendar.getTime()));
    }
}