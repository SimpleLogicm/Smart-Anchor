package com.anchor.activities;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.Settings;
import android.text.InputFilter;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.anchor.model.Product;
import com.anchor.model.Sub_Dealer_Order_Model;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.GPSTracker;

public class SubDealer_Signature_Activity extends BaseActivity {
    private String Signature_path = "";
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath = "";
    private String pictureImagePath_new = "";
    GPSTracker gps;
    Boolean isInternetPresent = false;
    ImageView get_icon;
    Boolean B_flag;
    String strdetail1_mandate, strdetail2_mandate, strdetail4_mandate;
    Bitmap bitmap1;
    byte[] b5;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ConnectionDetector cd;
    LinearLayout mContent;
    signature mSignature;
    Button mClear, mGetSign, mCancel;
    int m_sign_flag = 0;
    DatePickerDialog.OnDateSetListener date, date1;
    ArrayList<Product> dataOrder = new ArrayList<Product>();
    public static String tempDir;
    public int count = 1;
    public String current = null;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private Bitmap mBitmap;
    View mView;
    String detail1str, detail2str, detail4str;
    int cityID, beatID, retailerID, distID;
    TextView details1, details2;
    Calendar myCalendar;
    TextView txtWelcomeUser;
    private EditText yourName, order_detail1, order_detail2, order_detail4;
    Spinner order_type, shipment_pri, order_payment_term;
    ArrayAdapter<String> dataAdapter_order_type, dataAdapter_shipment_pri, dataAdapter_order_payment_term;
    private ArrayList<String> results_order_type = new ArrayList<String>();
    private ArrayList<String> results_shipment_pri = new ArrayList<String>();
    private ArrayList<String> results_payment_term = new ArrayList<String>();

    public String order = "", retailer_code = "";

    HashMap<String, String> order_payment_type_map = new HashMap<String, String>();
    ScrollView s_container_l;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


        cd = new ConnectionDetector(getApplicationContext());

        SharedPreferences sp = this.getSharedPreferences("SimpleLogic", 0);

        order = sp.getString("order", "");
        txtWelcomeUser = findViewById(R.id.txtWelcomeUser);
        mContent = findViewById(R.id.linearLayout);
        mSignature = new signature(this, null);
        mSignature.setBackgroundColor(Color.WHITE);
        mContent.addView(mSignature, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        mClear = findViewById(R.id.clear);
        mClear.setBackgroundColor(Color.parseColor("#414042"));
        mGetSign = findViewById(R.id.getsign);
        mGetSign.setBackgroundColor(Color.parseColor("#414042"));
        mCancel = findViewById(R.id.cancel);
        mCancel.setBackgroundColor(Color.parseColor("#414042"));
        mView = mContent;

        yourName = findViewById(R.id.yourName);
        order_detail1 = findViewById(R.id.order_detail1);
        order_detail2 = findViewById(R.id.order_detail2);
        order_detail4 = findViewById(R.id.yourRemarks);
        get_icon = findViewById(R.id.get_icon);
        order_type = findViewById(R.id.order_type);
        shipment_pri = findViewById(R.id.shipment_pri);
        order_payment_term = findViewById(R.id.order_payment_term);
        s_container_l = findViewById(R.id.s_container_l);

        order_payment_term.setVisibility(View.GONE);
        order_detail2.setVisibility(View.GONE);

        String user_name = "";
        if (!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null")) {
            user_name = Global_Data.USER_FIRST_NAME.trim();
            if (!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null")) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim();
            }
        }

        txtWelcomeUser.setText(user_name + " : " + Global_Data.emp_code);
        SharedPreferences spf1 = this.getSharedPreferences("SimpleLogic", 0);


        InputFilter[] Textfilters = new InputFilter[1];
        Textfilters[0] = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (end > start) {

                    char[] acceptedChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '.'};

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
        SharedPreferences spf2 = this.getSharedPreferences("SimpleLogic", 0);
        detail2str = spf2.getString("var_detail2", "");

        SharedPreferences spf22 = this.getSharedPreferences("SimpleLogic", 0);
        detail4str = spf22.getString("var_detail4", "");

        SharedPreferences spf3 = this.getSharedPreferences("SimpleLogic", 0);
        String strdetail1_edit = spf3.getString("var_detail1_edit", "");

        SharedPreferences spf4 = this.getSharedPreferences("SimpleLogic", 0);
        strdetail1_mandate = spf4.getString("var_detail1_mandate", "");

        SharedPreferences spf5 = this.getSharedPreferences("SimpleLogic", 0);
        String strdetail1_allow = spf5.getString("var_detail1_allow", "");

        SharedPreferences spf23 = this.getSharedPreferences("SimpleLogic", 0);
        String strdetail2_edit = spf23.getString("var_detail2_edit", "");

        SharedPreferences spf233 = this.getSharedPreferences("SimpleLogic", 0);
        String strdetail4_edit = spf233.getString("var_detail4_edit", "");

        SharedPreferences spf24 = this.getSharedPreferences("SimpleLogic", 0);
        strdetail2_mandate = spf24.getString("var_detail2_mandate", "");

        SharedPreferences spf244 = this.getSharedPreferences("SimpleLogic", 0);
        strdetail4_mandate = spf244.getString("var_detail4_mandate", "");

        SharedPreferences spf25 = this.getSharedPreferences("SimpleLogic", 0);
        String strdetail2_allow = spf25.getString("var_detail2_allow", "");

        SharedPreferences spf255 = this.getSharedPreferences("SimpleLogic", 0);
        String strdetail4_allow = spf255.getString("var_detail4_allow", "");


        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                char[] chars = {'\'', '"'};
                for (int i = start; i < end; i++) {
                    if (new String(chars).contains(String.valueOf(source.charAt(i)))) {
                        return "";
                    }
                }
                return null;
            }
        };
        order_detail4.setFilters(new InputFilter[]{filter});


        order_detail1.setHint("Need By Date");

        order_detail1.setEnabled(true);
        order_detail1.setFocusableInTouchMode(false);
        order_detail1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerDialog picker = new DatePickerDialog(SubDealer_Signature_Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.getDatePicker().setCalendarViewShown(false);
                picker.show();

            }
        });


//        if (strdetail2_edit.equalsIgnoreCase("true")) {
//            order_detail2.setEnabled(true);
//
//            if (strdetail2_allow.equalsIgnoreCase("Text")) {
//                order_detail2.setInputType(InputType.TYPE_CLASS_TEXT);
//            } else if (strdetail2_allow.equalsIgnoreCase("Integer")) {
//                order_detail2.setInputType(InputType.TYPE_CLASS_NUMBER);
//            } else {
//                order_detail2.setFocusableInTouchMode(false);
//                order_detail2.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        new DatePickerDialog(SubDealer_Signature_Activity.this, date1, myCalendar
//                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                    }
//                });
//            }
//        }
//
//        if (detail2str.length() > 0) {
//            order_detail2.setHint(detail2str);
//        } else {
//            order_detail2.setHint("Detail 2");
//        }


//        if (strdetail4_edit.equalsIgnoreCase("true")) {
//            order_detail4.setEnabled(true);
//
//            if (strdetail4_allow.equalsIgnoreCase("Text")) {
//                order_detail4.setInputType(InputType.TYPE_CLASS_TEXT);
//            } else if (strdetail4_allow.equalsIgnoreCase("Integer")) {
//                order_detail4.setInputType(InputType.TYPE_CLASS_NUMBER);
//            } else {
//                order_detail4.setFocusableInTouchMode(false);
//                order_detail4.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        // TODO Auto-generated method stub
//                        new DatePickerDialog(SubDealer_Signature_Activity.this, date1, myCalendar
//                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//                    }
//                });
//            }
//        }

//        if (detail4str.length() > 0) {
//            order_detail4.setHint(detail4str);
//        } else {
//            order_detail4.setHint("Remarks");
//        }

        SharedPreferences sp1 = SubDealer_Signature_Activity.this
                .getSharedPreferences("SimpleLogic", 0);

        results_order_type.clear();

//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.order_category_code_array)) {
//
//            List<Local_Data> contacts1 = dbvoc.getorder_category_bycodeArray(Global_Data.order_category_code_array);
//            results_order_type.add("Select Order Type");
//            for (Local_Data cn : contacts1) {
//                if (!cn.getOrder_type_name().equalsIgnoreCase("") && !cn.getOrder_type_name().equalsIgnoreCase(" ")) {
//                    String str_categ = "" + cn.getOrder_type_name();
//                    results_order_type.add(str_categ);
//                }
//            }
//        } else {
        List<Local_Data> contacts1 = dbvoc.getorder_category();
        results_order_type.add("Select Order Type");
        for (Local_Data cn : contacts1) {
            if (!cn.getOrder_type_name().equalsIgnoreCase("") && !cn.getOrder_type_name().equalsIgnoreCase(" ")) {
                String str_categ = "" + cn.getOrder_type_name();
                results_order_type.add(str_categ);
            }
        }
        // }


        dataAdapter_order_type = new ArrayAdapter<String>(this, R.layout.spinner_item, results_order_type);
        dataAdapter_order_type.setDropDownViewResource(R.layout.spinner_item);
        order_type.setAdapter(dataAdapter_order_type);
        order_type.setVisibility(View.GONE);

        results_shipment_pri.clear();

        results_shipment_pri.add("Shipment Priority");
        results_shipment_pri.add("High");
        results_shipment_pri.add("Standard");
        results_shipment_pri.add("Low");

        dataAdapter_shipment_pri = new ArrayAdapter<String>(this, R.layout.spinner_item, results_shipment_pri);
        dataAdapter_shipment_pri.setDropDownViewResource(R.layout.spinner_item);
        shipment_pri.setAdapter(dataAdapter_shipment_pri);

        results_payment_term.clear();
        order_payment_type_map.clear();
        List<Local_Data> contacts_payment = dbvoc.getorder_payment_term_data();
        results_payment_term.add("Select Payment Term");
        for (Local_Data cn : contacts_payment) {
            if (!cn.getName().equalsIgnoreCase("") && !cn.getName().equalsIgnoreCase(" ")) {
                results_payment_term.add(cn.getName());
                order_payment_type_map.put(cn.getName(), cn.getCode());
            }
        }

        dataAdapter_order_payment_term = new ArrayAdapter<String>(this, R.layout.spinner_item, results_payment_term);
        dataAdapter_order_payment_term.setDropDownViewResource(R.layout.spinner_item);
        order_payment_term.setAdapter(dataAdapter_order_payment_term);


        String cc_code = "";

        List<Sub_Dealer_Order_Model> contacts = dbvoc.GetSubOrders(Global_Data.GLOvel_SUB_GORDER_ID);

        String s_pri = "";
        String asset_code = "";
        if (contacts.size() > 0) {

            for (Sub_Dealer_Order_Model cn : contacts) {
                yourName.setText(cn.getName());
                order_detail1.setText(cn.getNeed_by_date());
                //order_detail2.setText(cn.getOrder_detail2());
                order_detail4.setText(cn.getRemarks());
                cc_code = cn.getOrder_type_code().trim();
                // asset_code = cn.getAsset_code().trim();
                s_pri = cn.getShipment_pr_code();
            }

            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(s_pri)) {
                int spinnerPosition = dataAdapter_shipment_pri.getPosition(s_pri);
                shipment_pri.setSelection(spinnerPosition);
            }


        }


        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cc_code)) {


            List<Local_Data> cont = dbvoc.get_order_category_name(cc_code);

            for (Local_Data cn : cont) {
                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getOrder_type_name().trim())) {
                    int spinnerPosition = dataAdapter_order_type.getPosition(cn.getOrder_type_name().trim());
                    order_type.setSelection(spinnerPosition);
                }
            }
        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(asset_code)) {

            List<Local_Data> cont = dbvoc.get_order_assetcode_name(asset_code);

            if (cont.size() > 0) {
                for (Local_Data cn : cont) {
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getName().trim())) {
                        int spinnerPosition = dataAdapter_order_payment_term.getPosition(cn.getName().trim());
                        order_payment_term.setSelection(spinnerPosition);
                    }
                }
            }
        }

        get_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                requestStoragePermission();
            }
        });

        mClear.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View b, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
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
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event

                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));

                    Log.v("log_tag", "Panel Saved");
                    boolean error = SubDealer_Signature_Activity();
                    // boolean error1 = SubDealer_Signature_Activity1();

                    if (!error) {

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
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));

                    Log.v("log_tag", "Panel Canceled");
                    Bundle b1 = new Bundle();
                    b1.putString("status", "cancel");
                    Intent intent = new Intent();
                    intent.putExtras(b1);
                    setResult(RESULT_OK, intent);
                    Intent i = new Intent(SubDealer_Signature_Activity.this, SubDealer_PreviewActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                    finish();

                }
                return false;
            }
        });
        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
             mTitleTextView.setText("Retailer Order");
            todaysTarget.setText(Global_Data.Sub_Dealer_name);

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        Log.w("GetSignature", "onDestory");
        super.onDestroy();
    }

    private boolean SubDealer_Signature_Activity() {
        String message_flag = "";
        boolean error = false;
        String errorMessage = "";


//        if (order_type.getSelectedItem().toString().equalsIgnoreCase("Select Order Type")) {
//            s_container_l.smoothScrollTo(txtWelcomeUser.getScrollX(), txtWelcomeUser.getScrollY());
//            order_type.requestFocus();
//            errorMessage = errorMessage + "Please Select Order Type.";
//            error = true;
//        } else

            if (strdetail1_mandate.equalsIgnoreCase("true") && order_detail1.getText().toString().equalsIgnoreCase("")) {
            s_container_l.smoothScrollTo(txtWelcomeUser.getScrollX(), txtWelcomeUser.getScrollY());
            order_detail1.requestFocus();
            errorMessage = errorMessage + "Please Select Need By Date";
            error = true;

        }

//        else if (strdetail2_mandate.equalsIgnoreCase("true") && order_detail2.getText().toString().equalsIgnoreCase("")) {
//            s_container_l.smoothScrollTo(txtWelcomeUser.getScrollX(), txtWelcomeUser.getScrollY());
//            order_detail2.requestFocus();
//            errorMessage = errorMessage + "Please Enter " + detail2str;
//            error = true;
//
//        } else if (strdetail2_mandate.equalsIgnoreCase("true") && order_detail2.getText().length() < 6) {
//            s_container_l.smoothScrollTo(txtWelcomeUser.getScrollX(), txtWelcomeUser.getScrollY());
//            order_detail2.requestFocus();
//            errorMessage = errorMessage + detail2str + " should be 6 digit number.";
//            error = true;
//
//        }

        else if (shipment_pri.getSelectedItem().toString().equalsIgnoreCase("Shipment Priority")) {
            s_container_l.smoothScrollTo(txtWelcomeUser.getScrollX(), txtWelcomeUser.getScrollY());
            shipment_pri.requestFocus();
            errorMessage = errorMessage + "Please Select Shipment Priority";
            error = true;
        }

//        else if (order_payment_term.getSelectedItem().toString().equalsIgnoreCase("Select Payment Term")) {
//
//            order_payment_term.requestFocus();
//            errorMessage = errorMessage + "Please Select Payment Term";
//            error = true;
//        }

        else if (yourName.getText().toString().equalsIgnoreCase("")) {
            yourName.requestFocus();
            errorMessage = errorMessage + "Please Enter your Name.";
            error = true;
        } else if (strdetail4_mandate.equalsIgnoreCase("true") && order_detail4.getText().toString().equalsIgnoreCase("")) {
            order_detail4.requestFocus();
            errorMessage = errorMessage + "Please Enter Remarks";
            error = true;

        }


        if (error) {
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 105, 50);
            toast.show();
        }

        return error;
    }


    private String getCurrentTime() {

        final Calendar c = Calendar.getInstance();
        int currentTime = (c.get(Calendar.HOUR_OF_DAY) * 10000) +
                (c.get(Calendar.MINUTE) * 100) +
                (c.get(Calendar.SECOND));
        Log.w("TIME:", String.valueOf(currentTime));
        return (String.valueOf(currentTime));

    }

    private boolean isDeviceSupportCamera() {
        // this device has a camera
        // no camera on this device
        return getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }

    private boolean prepareDirectory() {
        try {
            return makedirs();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getApplicationContext(), "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            return false;
        }
    }

    private boolean makedirs() {
        File tempdir = new File(tempDir);
        if (!tempdir.exists())
            tempdir.mkdirs();

        if (tempdir.isDirectory()) {

        }
        return (tempdir.isDirectory());
    }

    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String mypath) {
            Log.e("log_tag", "Width: " + v.getWidth());
            Log.e("log_tag", "Height: " + v.getHeight());
            if (mBitmap == null) {
                mBitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(mBitmap);
            try {
                FileOutputStream mFileOutStream = new FileOutputStream(mypath);

                v.draw(canvas);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();
                String url = Images.Media.insertImage(getContentResolver(), mBitmap, "title", null);
                Log.e("log_tag", "mypath: " + mypath);

                Log.e("log_tag", "url: " + url);


            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            m_sign_flag = 1;
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
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

        private void debug(String string) {
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SubDealer_Signature_Activity.this, SubDealer_PreviewActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        // String encodedImage =imageBytes.toString();

        return encodedImage;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            try {

                dbvoc.updateSUbORDER_order_image(mCurrentPhotoPath, Global_Data.GLOvel_SUB_GORDER_ID);
                new ActivityResultTask().execute();

            } catch (Exception e) {
                e.printStackTrace();
            }



        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();

                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);

                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);

                mCurrentPhotoPath = "file:" + c.getString(columnIndex);

                dbvoc.updateSUbORDER_order_image(mCurrentPhotoPath, Global_Data.GLOvel_SUB_GORDER_ID);

                pictureImagePath_new = c.getString(columnIndex);
                new ActivityResultTask().execute();

                c.close();


            } catch (Exception ex) {
                ex.printStackTrace();
            }


        }

    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "Anchor";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Anchor");

        if (!storageDir.exists()) {
            storageDir.mkdir();
        }

        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );


        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return image;
    }

    private void SaveImage(Bitmap finalBitmap, String name) {

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File myDir = new File(path, "Anchor_Signature");
        myDir.mkdirs();

        String fname = name + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
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

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        order_detail1.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel1() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        order_detail2.setText(sdf.format(myCalendar.getTime()));
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

                            if (B_flag == true) {
                                final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};


                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SubDealer_Signature_Activity.this);

                                builder.setTitle("Add Photo!");

                                builder.setItems(options, new DialogInterface.OnClickListener() {

                                    @Override

                                    public void onClick(DialogInterface dialog, int item) {

                                        if (options[item].equals("Take Photo")) {
                                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                            if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                                                // Create the File where the photo should go
                                                File photoFile = null;
                                                try {
                                                    photoFile = createImageFile();
                                                } catch (Exception ex) {
                                                    // Error occurred while creating the File
                                                    Log.i("Image TAG", "IOException");
                                                    mCurrentPhotoPath = "";
                                                }
                                                // Continue only if the File was successfully created
                                                if (photoFile != null) {
                                                    Uri photoURI = FileProvider.getUriForFile(SubDealer_Signature_Activity.this,
                                                            BuildConfig.APPLICATION_ID + ".provider",
                                                            photoFile);
                                                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                    //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                                                }
                                            }

                                        } else if (options[item].equals("Choose from Gallery")) {

                                            // image_check = "gallery";
                                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            startActivityForResult(intent, 2);


                                        } else if (options[item].equals("Cancel")) {

                                            dialog.dismiss();

                                        }

                                    }

                                });

                                builder.show();

                            } else {
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
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
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

                            if (m_sign_flag == 0) {
                                Toast toast = Toast.makeText(SubDealer_Signature_Activity.this, "Please Sign.... ", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 105, 50);
                                toast.show();
                            } else {
                                gps = new GPSTracker(SubDealer_Signature_Activity.this);
                                if (!gps.canGetLocation()) {

                                    gps.showSettingsAlertnew();
                                } else {
                                    AlertDialog alertDialog = new AlertDialog.Builder(SubDealer_Signature_Activity.this).create(); //Read Updat
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
                                            String order_asset_name = "";
                                            String order_asset_code = "";
                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(order_detail1.getText().toString().trim())) {

                                                order_detail1_text = order_detail1.getText().toString().trim();
                                            }

                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(order_detail2.getText().toString().trim())) {

                                                order_detail2_text = order_detail2.getText().toString().trim();
                                            }

                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(order_detail4.getText().toString().trim())) {
                                                try {
                                                    order_detail4_text = order_detail4.getText().toString().trim();
                                                    order_detail4_text = order_detail4_text.replaceAll("'", "");
                                                    order_detail4_text = order_detail4_text.replaceAll("\"", "");
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();

                                                    order_detail4_text = order_detail4.getText().toString().trim();
                                                }

                                            }

                                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(yourName.getText().toString().trim())) {

                                                order_type_name = yourName.getText().toString().trim();
                                            }

//                                            if (!(order_type.getSelectedItem().toString().equalsIgnoreCase("Select Order Type"))) {
//
//                                                order_type_text = order_type.getSelectedItem().toString();
//                                                List<Local_Data> contacts1 = dbvoc.get_order_category_code(order_type_text);
//
//                                                for (Local_Data cn : contacts1) {
//
//                                                    order_type_code = cn.getOrder_type_code();
//                                                }
//
//                                            }

                                            try {
                                                if (!(order_payment_term.getSelectedItem().toString().equalsIgnoreCase("Select Payment Term"))) {

                                                    order_asset_name = order_payment_term.getSelectedItem().toString();

                                                    order_asset_code = order_payment_type_map.get(order_asset_name);

                                                }
                                            } catch (Exception Ex) {
                                                Ex.printStackTrace();
                                            }


                                            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                                            } else
                                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//

                                            try {
                                                AppLocationManager appLocationManager = new AppLocationManager(SubDealer_Signature_Activity.this);
                                                Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                                                Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                                                PlayService_Location PlayServiceManager = new PlayService_Location(SubDealer_Signature_Activity.this);

                                                if (PlayServiceManager.checkPlayServices(SubDealer_Signature_Activity.this)) {
                                                    Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                                                } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                                                    Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                                    Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                                                }

                                            } catch (Exception ex) {
                                                ex.printStackTrace();
                                            }

                                            try {

                                                SaveImage(bitmap, "SI" + Global_Data.GLOvel_SUB_GORDER_ID);

                                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {

                                                    dbvoc.updateSub_ORDER_SIGNATURENEW_WITHLATLONG(Signature_path, Global_Data.GLOvel_SUB_GORDER_ID, order_detail1_text, order_type_name, order_detail4_text, order_type_code, shipment_pri.getSelectedItem().toString(), Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE);
                                                } else {
                                                    dbvoc.update_SUB_ORDER_SIGNATURENEW(Signature_path, Global_Data.GLOvel_SUB_GORDER_ID, order_detail1_text, order_type_name, order_detail4_text, order_type_code, shipment_pri.getSelectedItem().toString());
                                                }


                                                mSignature.clear();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            isInternetPresent = cd.isConnectingToInternet();


                                            if (isInternetPresent) {
                                                onDestroy();
                                                getServices.SYNSUBDEALERCORDER(SubDealer_Signature_Activity.this, Global_Data.GLOvel_SUB_GORDER_ID);
                                            } else {


                                                Toast toast = Toast.makeText(getApplicationContext(), "You don't have internet connection.", Toast.LENGTH_LONG);
                                                toast.show();


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
        AlertDialog.Builder builder = new AlertDialog.Builder(SubDealer_Signature_Activity.this);
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

    public static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }


    private class ActivityResultTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... response) {

            try {

                if (!mCurrentPhotoPath.equalsIgnoreCase("")) {
                    try {
                        // reduce_img_Qaulity(Uri.parse(outletsignboard_mCurrentPhotoPath));
                        // compressImage(outletsignboard_mCurrentPhotoPath);
                        String filePath = getRealPathFromURI(Uri.parse(mCurrentPhotoPath).getPath());
                        Bitmap scaledBitmap = null;
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        Bitmap b = BitmapFactory.decodeFile(Uri.parse(mCurrentPhotoPath).getPath(), options);
                        int actualHeight = options.outHeight;
                        int actualWidth = options.outWidth;

                        float maxHeight = 816.0f;
                        float maxWidth = 612.0f;
                        float imgRatio = actualWidth / actualHeight;
                        float maxRatio = maxWidth / maxHeight;

                        if (actualHeight > maxHeight || actualWidth > maxWidth) {
                            if (imgRatio < maxRatio) {
                                imgRatio = maxHeight / actualHeight;
                                actualWidth = (int) (imgRatio * actualWidth);
                                actualHeight = (int) maxHeight;
                            } else if (imgRatio > maxRatio) {
                                imgRatio = maxWidth / actualWidth;
                                actualHeight = (int) (imgRatio * actualHeight);
                                actualWidth = (int) maxWidth;
                            } else {
                                actualHeight = (int) maxHeight;
                                actualWidth = (int) maxWidth;

                            }
                        }
                        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
                        options.inJustDecodeBounds = false;
                        options.inPurgeable = true;
                        options.inInputShareable = true;
                        options.inTempStorage = new byte[16 * 1024];
                        try {
//          load the bitmap from its path
                            b = BitmapFactory.decodeFile(Uri.parse(mCurrentPhotoPath).getPath(), options);

                        } catch (OutOfMemoryError exception) {
                            exception.printStackTrace();

                        }
                        try {
                            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
                        } catch (OutOfMemoryError exception) {
                            exception.printStackTrace();
                        }
                        float ratioX = actualWidth / (float) options.outWidth;
                        float ratioY = actualHeight / (float) options.outHeight;
                        float middleX = actualWidth / 2.0f;
                        float middleY = actualHeight / 2.0f;

                        Matrix scaleMatrix = new Matrix();
                        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);
                        Canvas canvas = new Canvas(scaledBitmap);
                        canvas.setMatrix(scaleMatrix);
                        canvas.drawBitmap(b, middleX - b.getWidth() / 2, middleY - b.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

                        ExifInterface exif = null;
                        try {
                            exif = new ExifInterface(Uri.parse(mCurrentPhotoPath).getPath());

                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);
                            Matrix matrix = new Matrix();
                            if (orientation == 6) {
                                matrix.postRotate(90);

                            } else if (orientation == 3) {
                                matrix.postRotate(180);

                            } else if (orientation == 8) {
                                matrix.postRotate(270);

                            }
                            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                                    true);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }


//                            Bitmap bmRotated = rotateBitmap(out, orientation);
                        File file = new File(Uri.parse(mCurrentPhotoPath).getPath());
//                            FileOutputStream fOut;
                        FileOutputStream out = null;


                        try {
                            out = new FileOutputStream(file);

//          write the compressed bitmap at the destination specified by filename.
                            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

//                                fOut = new FileOutputStream(file);
//                                bmRotated.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                                fOut.flush();
//                                fOut.close();
//                                out.recycle();
//                                b.recycle();
//                                bmRotated.recycle();
                        } catch (Exception e) {
                        }


//                        runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//
//                                Bitmap mImageBitmap = null;
//                                try {
//                                    mImageBitmap = MediaStore.Images.Media.getBitmap(SubDealer_Signature_Activity.this.getContentResolver(), Uri.parse(outletsignboard_mCurrentPhotoPath));
//                                    outlet_signboard_pick.setImageBitmap(mImageBitmap);
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        });

                        // outlet_signboard_pick.setRotation(90);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    try {
//                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(mCurrentPhotoPath)) {
//
//
//                            Uri uri = Uri.parse(mCurrentPhotoPath);
//                            File fdelete = new File(uri.getPath());
//                            if (fdelete.exists()) {
//                                if (fdelete.delete()) {
//                                    System.out.println("file Deleted :" + mCurrentPhotoPath);
//                                    mCurrentPhotoPath = "";
//                                } else {
//                                    System.out.println("file not Deleted :" + mCurrentPhotoPath);
//                                }
//                            }
//                        }
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
                }



        } catch (Exception e) {
            e.printStackTrace();
        }
//        if (dialog1.isShowing() || dialog1 != null) {
//
//            dialog1.dismiss();
//        }


//                }
//            });

        return "Executed";
    }

        @Override
        protected void onPostExecute(String result) {

//        SubDealer_Signature_Activity.this.runOnUiThread(new Runnable() {
//            public void run() {
//                if (dialog1.isShowing() || dialog1 != null) {
//                    dialog1.dismiss();
//                }
//            }
//        });

    }

        @Override
        protected void onPreExecute() {


    }

        @Override
        protected void onProgressUpdate(Void... values) {
    }
    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
}