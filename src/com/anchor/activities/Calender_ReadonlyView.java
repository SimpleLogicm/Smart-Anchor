package com.anchor.activities;

/**
 * Created by vinod on 02-09-2016.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.anchor.slidingmenu.CalendarAct;
import com.anchor.webservice.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Calender_ReadonlyView extends BaseActivity{
    EditText details;
    Date date1;
    Date date2;
    private String Current_Date = "";
    Button submit,submit_details_save;
    ProgressDialog dialog;
    LoginDataBaseAdapter loginDataBaseAdapter;
    TextView from,to,event_name;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private DatePickerDialog fromDatePickerDialog,fromDatePickerDialog1;
    private SimpleDateFormat dateFormatter;
    private static final String tag = "Calendar_Event";
    String popUpContents[];
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    PopupWindow popupWindowDogs;
    Button buttonShowDropDown;
    private TextView currentMonth;
    private Button selectedDayMonthYearButton;
    private ImageView prevMonth;
    private ImageView nextMonth;
    private GridView calendarView;
    private CalendarAct.GridCellAdapter adapter;
    private Calendar _calendar;

    String c_user_id = "";
    String c_id = "";
    String c_latlong = "";

    String update_flag = "";


    @SuppressLint("NewApi")
    private int month, year;
    @SuppressWarnings("unused")
    @SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
    //private final DateFormat dateFormatter = new DateFormat();
    //private static final String dateTemplate = "MMMM yyyy";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        setContentView(R.layout.calender_readonlylayout);
        from=(TextView)findViewById(R.id.from_details);
        event_name=(TextView)findViewById(R.id.event_name);
        to=(TextView)findViewById(R.id.to_details);
        details=(EditText)findViewById(R.id.details);
        submit=(Button)findViewById(R.id.submit_details);
        submit_details_save = (Button)findViewById(R.id.submit_details_save);

        details.setFilters(new InputFilter[]{filter});

        cd = new ConnectionDetector(Calender_ReadonlyView.this);
        dateFormatter = new SimpleDateFormat("MMMM-yyyy", Locale.US);


        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String strDate = sdf.format(c.getTime());
        Current_Date = sdf.format(c.getTime());

        Calendar newCalendar = Calendar.getInstance();

        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        List<Local_Data> contacts2 = dbvoc.getCalender_EventValueReadonly(Global_Data.CALENDER_READONLY_Address ,"");

        if(contacts2.size() <= 0 )
        {
           // Toast.makeText(getApplicationContext(),"No Record Found.", Toast.LENGTH_LONG).show();
            Toast toast = Toast.makeText(getApplicationContext(),"No Record Found.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
        else
        {
            for (Local_Data cn : contacts2) {

                from.setText(cn.getfrom_date());
                to.setText(cn.getto_date());
                details.setText(cn.getcalender_details());
                event_name.setText(cn.getcalender_type());
                c_user_id = cn.getuser_email();
                c_id = cn.getcalender_id();
                //	c_user_id = cn.getuser_email();


            }
            //submit_details_save.setText("Update");
           // update_flag = "TRUE";
        }


        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String yr_reg=Integer.toString(year);
                String mnth_reg=Integer.toString(monthOfYear+1);
                String date_reg=Integer.toString(dayOfMonth);

                from.setText(date_reg+"- "+(dateFormatter.format(newDate.getTime())));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String yr_reg=Integer.toString(year);
                String mnth_reg=Integer.toString(monthOfYear+1);
                String date_reg=Integer.toString(dayOfMonth);

                to.setText(date_reg+"-"+(dateFormatter.format(newDate.getTime())));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


        from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.getWindow().getAttributes().verticalMargin = 0.5F;
                fromDatePickerDialog.show();
            }
        });

        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog1.getWindow().getAttributes().verticalMargin = 0.5F;
                fromDatePickerDialog1.show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();



            }
        });

        submit_details_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(from.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(to.getText().toString()))
                {
                    date1 = new Date(from.getText().toString());
                    date2 = new Date(to.getText().toString());
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(date1);
                    cal2.setTime(date1);
                }

                if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(from.getText().toString()))
                {
                    //Toast.makeText(getApplicationContext(),"Please Select From Date", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getApplicationContext(),"Please Select From Date", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(to.getText().toString()))
                {
                    //Toast.makeText(getApplicationContext(),"Please Select To Date", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getApplicationContext(),"Please Select To Date", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                if(date1.compareTo(date2)>0)
                {
                    //System.out.println("Date1 is after Date2");
                    //Toast.makeText(getApplicationContext(),"To Date not a valid date.", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getApplicationContext(), "To Date not a valid date.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(details.getText().toString()))
                {
                    //Toast.makeText(getApplicationContext(),"Please Enter Travel Details", Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getApplicationContext(),"Please Enter Travel Details", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
                else
                {


                    if(update_flag.equalsIgnoreCase("TRUE"))
                    {
                        dbvoc.getDeleteTablecalender_event(c_id);
                        loginDataBaseAdapter.insertCalenderEntries("", "", c_user_id,c_id,Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"");

                        //Toast.makeText(getApplicationContext(),"Update Successfully.",Toast.LENGTH_LONG).show();

                        Toast toast = Toast.makeText(getApplicationContext(),"Update Successfully.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else
                    {
                        SecureRandom random = new SecureRandom();
                        loginDataBaseAdapter.insertCalenderEntries("", "", Global_Data.GLOvel_USER_EMAIL, new BigInteger(130,random).toString(32),Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"");


                        Toast toast = Toast.makeText(getApplicationContext(),"\"Save Successfully.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }


                    Intent a = new Intent(Calender_ReadonlyView.this,MainActivity.class);
                    startActivity(a);
                    finish();



//					isInternetPresent = cd.isConnectingToInternet();
//
//					if (isInternetPresent)
//					{
//						call_service_Calender_Event();
//					}
//					else
//					{
//						Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//					}


//						 Toast.makeText(getApplicationContext(),"Your Data Submit Successfuly", Toast.LENGTH_LONG).show();
//
//						 Intent intent = new Intent(Expenses.this, Order.class);
//						 startActivity(intent);
//						 overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//						 finish();
                }



            }
        });

       // ActionBar mActionBar = getActionBar();
        //mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        Intent i = getIntent();
//        String name = i.getStringExtra("retialer");
//        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
//        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
//        mTitleTextView.setText(Global_Data.calspinner);
//
//        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
//
//        ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
//        H_LOGO.setImageResource(R.drawable.cal);
//        H_LOGO.setVisibility(View.VISIBLE);
//
//        SharedPreferences sp = Calender_ReadonlyView.this.getSharedPreferences("SimpleLogic", 0);
//
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//            todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//        }
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//            todaysTarget.setText("Today's Target Acheived");
//        }
//
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
//        mActionBar.setHomeButtonEnabled(true);
//        mActionBar.setDisplayHomeAsUpEnabled(true);
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

    public void call_service_Calender_Event()
    {
        System.gc();
        String reason_code = "";
        try {

//			DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
//			DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//			Date date1 = originalFormat.parse(getDateTime());
//			String formattedDate = targetFormat.format(date1);




            dialog = new ProgressDialog(Calender_ReadonlyView.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please wait....");
            dialog.setTitle("Metal");
            dialog.setCancelable(false);
            dialog.show();

            String domain = "";
            String device_id = "";


            TelephonyManager telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            device_id = telephonyManager.getDeviceId();

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


                // SecureRandom random = new SecureRandom();

                String code = "";

                if(update_flag.equalsIgnoreCase("TRUE"))
                {
                    dbvoc.getDeleteTablecalender_event(c_id);
                    loginDataBaseAdapter.insertCalenderEntries("", "", c_user_id,c_id,Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"");

                    code =  c_id;

                    // Toast.makeText(getApplicationContext(),"Update Successfully.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    SecureRandom random = new SecureRandom();
                    loginDataBaseAdapter.insertCalenderEntries("", "", Global_Data.GLOvel_USER_EMAIL, new BigInteger(130,random).toString(32),Global_Data.CALENDER_EVENT_TYPE, from.getText().toString().trim(), to.getText().toString().trim(), details.getText().toString().trim(),  Global_Data.lat_val+","+Global_Data.long_val, Current_Date, Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,"");

                    code =  new BigInteger(130,random).toString(32);

                    // Toast.makeText(getApplicationContext(),"Save Successfully.",Toast.LENGTH_LONG).show();
                }

                Log.d("Server url","Server url"+domain+"calendars/create_calender_entry");


                JSONArray order = new JSONArray();
                JSONObject product_value = new JSONObject();
                JSONObject product_value_n = new JSONObject();
                JSONArray product_imei = new JSONArray();

                product_value.put("code", code);
                product_value.put("user_email", Global_Data.GLOvel_USER_EMAIL);
                product_value.put("entry_type", Global_Data.CALENDER_EVENT_TYPE);
                product_value.put("from_date",from.getText().toString().trim());
                product_value.put("to_date", to.getText().toString().trim());
                product_value.put("details", details.getText().toString().trim());
                // product_value.put("latlon", Global_Data.lat_val+","+Global_Data.long_val);

                // product_value.put("imei_no", Global_Data.device_id);

                order.put(product_value);
                // product_imei.put(Global_Data.device_id);
                product_value_n.put("calender_entries", order);
                product_value_n.put("imei_no", Global_Data.device_id);

                Log.d("calender_entries",product_value_n.toString());
                //Log.d("expenses_travels",product_value_n.toString());

//
//
//				 //product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//				// product_value.put("email", Global_Data.GLOvel_USER_EMAIL);
//
                jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"calendars/create_calender_entry", product_value_n, new Response.Listener<JSONObject>() {
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


                            if(response_result.equalsIgnoreCase("Calender Entry created successfully.")) {

                                dialog.dismiss();

                                Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent a = new Intent(Calender_ReadonlyView.this,CalendarAct.class);
                                startActivity(a);
                                finish();
                            }
                            else
                            {

                                dialog.dismiss();
                                // dbvoc.getDeleteTablecalender_event(c_id);
                                // Global_Data.SYNC_SERVICE_FLAG  = "TRUE";
                                // Toast.makeText(context.getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
                                Toast toast = Toast.makeText(Calender_ReadonlyView.this,response_result, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent a = new Intent(Calender_ReadonlyView.this,CalendarAct.class);
                                startActivity(a);
                                finish();

                            }

                            //  finish();
                            // }

                            // output.setText(data);
                        }catch(JSONException e){e.printStackTrace();
                            Intent a = new Intent(Calender_ReadonlyView.this,CalendarAct.class);
                            startActivity(a);
                            finish();

                            dialog.dismiss(); }


                        dialog.dismiss();
                        dialog.dismiss();




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Intent a = new Intent(Calender_ReadonlyView.this,CalendarAct.class);
                        startActivity(a);
                        finish();
                        Log.i("volley", "error: " + error);
                      //  Toast.makeText(Calender_ReadonlyView.this, "Some server error occur or Your net not working", Toast.LENGTH_LONG).show();
                        Toast toast = Toast.makeText(Calender_ReadonlyView.this, "Some server error occurred or your internet not working", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        dialog.dismiss();
                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(Calender_ReadonlyView.this);

                int socketTimeout = 200000;//30 seconds - change to what you want
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

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }

    public static String getlength(int len){
        int number = len;
        int length = (int) Math.log10(number) + 1;
        if(length == 1)
        {
            return "0"+len;
        }
        else
        {
            return ""+len;
        }

    }

    InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                //System.out.println("Type : " + type);
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    };

}

