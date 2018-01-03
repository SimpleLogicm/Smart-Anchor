package com.anchor.activities;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Global_Data {

    public static String Varient_value_add_flag = "";
    public static String Search_business_unit_name = "";
    public static String Search_Category_name = "";
    public static String Search_BusinessCategory_name = "";
    public static String Search_brand_name = "";
    public static String Search_Product_name = "";
    public static Boolean app_sound = false;
    public static String New_Label="";
    public static String Var_Label="";
    public static String target_amount="";
    public static String pre_schecode="";
    public static String editable="";
    public static String mandatory="";
    public static String allow="";
    public static String emp_code="";

    public static String Stock_warehouse_flag="";
    public static String Stock_product_flag="";
    public static String Stock_product_flag_value_check="";
    public static String Stock_warehouse_flag_value_check="";

    public static String sound_file = "";
    public static String Web_view_back="";
    public static String Web_view_url="";
    public static String Web_view_Title="";
    public static String Previous_Order_ServiceOrder_ID="";
    public static String Previous_Order_UpdateOrder_ID="";

    public static String Target_From_Month="";
    public static String Target_To_Month="";
    public static String Target_Product_Category="";
    public static String Target_Year="";
    public static String target_quarter="";
    public static String target_grpby="";
    public static String SCHE_CODE="";


    public static String AmountOutstanding="";
    public static String AmountOverdue="";
	public static String customer_code="";
    public static String LOCATION_SERVICE_HIT="";
    public static String customer_MobileNumber="";
    public static String USER_FIRST_NAME="";
    public static String USER_MANAGER_NAME="";
    public static String USER_LAST_NAME="";
    public static String CUSTOMER_NAME_NEW="";
    public static String CUSTOMER_ADDRESS_NEW="";
    public static String customer_reportingTo="";
    public static String cus_MAnager_mobile="";
    public static String customer_Address="";
    public static String credit_limit_amount="";
    public static String outstandings_amount="";
    public static String item_code="";
    public static String SYNC_ORDER_COUNT="";
    public static String item_code_return="";
    public static String CUSTOMER_SERVICE_FLAG="";
	public static String pname="";
    public static String amnt="";
	public static String amnt1="";
	public static String rr_price="";
	public static String cust_str="";
    public static String retailer="";
    public static String txt_label="";
    public static String calendar_resp="";
    public static String select_date="";
    public static String calspinner="";
    public static String imei_no="";
    public static String local_user="";
    public static String local_pwd="";
    public static String local_imeino="";
    public static String login_resp="";
    
    public static String user_name="";
    public static String user_doj="";
    public static String user_mobno="";
    public static String user_email="";
    public static String user_report="";
    public static String user_fname="";
    public static String user_lname="";
    public static String user_status="";
    public static String user_createby="";
    public static String user_modifyby="";
    public static String user_project="";
    public static String user_customer="";
    public static String user_role="";
    public static String user_device="";
    public static String user_state="";
    public static String user_city="";
    public static String user_createat="";
    public static String user_updateat="";
    public static String variant_rr="";
    public static String variant_mrp="";
    
    public static String order_city="";
    public static String order_state="";
    public static String order_beat="";
    public static String order_retailer="";
    public static String order_category="";
    public static String order_product="";
    public static String order_variant="";
    public static String order_qty="";
    public static float order_amount;
    public static ArrayList<String> results3;
    public static String GLOVEL_CATEGORY_NAME="";
    public static String GLOVEL_SUBCATEGORY_NAME="";
    public static String GLOVEL_LONG_DESC="";
    public static String GLOVEL_CATEGORY_SELECTION="";
    public static String GLOVEL_SubCategory_Button="";
    public static String GLOVEL_INVOICE_VALUE="";
    
    public static String GLOVEL_RETURN_FLAG="";
    public static String GLOVEL_BY_FILTER_RETURN="";
    public static String GLOVEL_ITEM_MRP="";
    public static String GLOVEL_FILTER_FLAG="";
    public static String GLOvel_STATE="";
    public static String GLOvel_CITY="";
    public static String GLOvel_ADDRESS="";
    public static String GLOvel_LATITUDE="";
    public static String GLOvel_LONGITUDE="";
    public static String address="";
	public static String lat_val="";
	public static String device_id="";
	public static String GLObalOrder_id="";
	public static String GLObalOrder_id_return="";
	public static String long_val="";
	public static String sales_btnstring="";
	public static String GLOvel_STATE_n="";
	public static String GLOvel_CITY_n="";
	public static String GLOvel_CUSTOMER_ID="";
	public static String GLOvel_ITEM_NUMBER="";
	public static String GLOvel_ITEM_NUMBER_RETURN="";
	public static String GLOvel_GORDER_ID="";
	public static String GLOvel_GORDER_ID_RETURN="";
	public static String GLOvel_REMARK="";
	public static String GLOvel_USER_EMAIL="";
	public static  ArrayList productList = new ArrayList();
    public static String getsign_str="";
	public static String total_qty = "";
    public static String Schedule_FLAG = "";
    public static String Default_Image_Path = "";
    public static String Default_video_Path = "";
	public static String MRP = "";
	public static String RP = "";
	public static String amount = "";
	public static String scheme_amount = "";
	public static String actual_discount = "";
	public static String product_dec = "";
	public static String item_no = "";
	public static String GLOVEL_ORDER_REJECT_FLAG="";
	public static String GLOVEL_ORDER_LIST_FLAG="";
	
	public static String GLOVEL_CATEGORY_ID="";
	public static String GLOVEL_PRODUCT_ID="";
	public static String GLOVEL_PRODUCT_NAME="";
	public static String GLOVEL_USER_ID="";
	
	public static String GLOVEL_RETURNOrder_ID="";
	
	public static String PREVIOUS_ORDER_BACK_FLAG="";
	public static String PREVIOUS_ORDER_BACK_FLAG_REURN="";
	public static String CALENDER_EVENT_TYPE="";
    public static String SYNC_SERVICE_FLAG="";

    public static String CALENDER_LIST="";

    public static String CALENDER_READONLY_Address="";
    public static String CALENDER_READONLY_Date="";

    public static String GLOVEL_PREVIOUS_ORDER_FLAG="";
    public static String Glovel_BEAT_ID="";
	
	
	//public static String GLOVEL_LONG_DESC="";
    
 // create map to store
    public static  Map<String, List<String>> map = new HashMap<String, List<String>>();
    public static LinkedHashMap<String, String> quastionmap = new LinkedHashMap<>();

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

//    public static void sendSMS(String phoneNumber, String message, final Context activity) {
//        String SENT = "SMS_SENT";
//        String DELIVERED = "SMS_DELIVERED";
//
//        PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0, new Intent(
//                SENT), 0);
//
//        PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0,
//                new Intent(DELIVERED), 0);
//
//        // ---when the SMS has been sent---
//        activity.registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        ContentValues values = new ContentValues();
////						for (int i = 0; i < MobNumber.size() - 1; i++) {
////							values.put("address", MobNumber.get(i).toString());// txtPhoneNo.getText().toString());
////							values.put("body","gdfgdgd");
////						}
//
//                        //for (int i = 0; i < MobNumber.size() - 1; i++) {
//                       values.put("address", "8454858739");// txtPhoneNo.getText().toString());
//                        values.put("body","gdfgdgd");
//                        //}
//
//                        activity.getContentResolver().insert(
//                                Uri.parse("content://sms/sent"), values);
//                        Toast.makeText(activity, "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(activity, "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(activity, "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(activity, "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(activity, "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(SENT));
//
//        // ---when the SMS has been delivered---
//        activity.registerReceiver(new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode()) {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(activity, "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(activity, "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));
//
//        SmsManager sms = SmsManager.getDefault();
//        ArrayList<String> parts = sms.divideMessage(message);
//        //sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
//        sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
//    }

//    public static void sendSMS(String phoneNumber, String message,final Context activity)
//    {
//        String SENT = "SMS_SENT";
//        String DELIVERED = "SMS_DELIVERED";
//
//        PendingIntent sentPI = PendingIntent.getBroadcast(activity, 0,
//                new Intent(SENT), 0);
//
//        PendingIntent deliveredPI = PendingIntent.getBroadcast(activity, 0,
//                new Intent(DELIVERED), 0);
//
//        //---when the SMS has been sent---
//         activity.registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(activity, "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(activity, "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(activity, "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(activity, "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(activity, "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        },new IntentFilter(SENT));
//
//        //---when the SMS has been delivered---
//        activity.registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(activity, "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(activity, "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));
//
//        SmsManager sms = SmsManager.getDefault();
//        //sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
//        ArrayList<String> parts = sms.divideMessage(message);
//        sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
//    }
}
