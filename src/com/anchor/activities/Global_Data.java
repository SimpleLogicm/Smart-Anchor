package com.anchor.activities;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.anchor.model.RCTOData;
import com.anchor.model.Spiner_List_Model;
import com.anchor.model.SubDealerModel;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Global_Data {
    public static Context context = null;
    public static String rank_service_call_flag = "";
    public static String year_value = "";
    public static String usernamefilter="";
    public static String scheame_name="";
    public static String scheame_type="";
    //public static ArrayList usernameArray = new ArrayList();
    public static List<String> usernameArray = new ArrayList<String>();
    public static List<String> datear = new ArrayList<String>();
    public static String halfyear_value = "";
    public static String quartly_value = "";
    public static String monthly_value = "";
    public static String scheme_from_date = "";
    public static String scheme_to_date = "";
    public static String close_scheme_name = "";
    public static String target_product_cate_value = "";
    public static String target_product_Bu_value = "";
    public static String target_product_division_value = "";
    public static String target_product_brand_value = "";
    public static String Quantity_click_flag = "";
    public static String Value_click_flag = "";
    public static String close_click_flag = "";
    public static String Ongoing_click_flag = "";
    public static String filterValue = "";
    public static String GLOvel_BU = "";
    public static String CUSTOMER_EMAIL="";
    public static HashMap<Integer,String> p_cus_services_status_check = new HashMap<>();
    public static String Varient_value_add_flag = "";
    public static HashMap<String,String> Order_hashmap = new HashMap<String,String>();
    public static String item_SL = "";
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
    public static String topHeader="";
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
    public static String syncStatus="";

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
    public static String Business_unit_code_array="";
    public static String order_category_code_array="";
    public static int selectedPosition=-1;
    public static List<Marker> mMarkers = new ArrayList<Marker>();
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
    public static String G_BEAT_IDC = "";
    public static String G_BEAT_service_flag = "";
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
    public static String GLOvel_SUB_GORDER_ID="";
    public static String statusOrderActivity="";
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
    public static String Scheme_report_Type="";
    public static List<Spiner_List_Model> spiner_list_modelList =new ArrayList<Spiner_List_Model>();
    public static List<Local_Data> spiner_list_modelListn =new ArrayList<Local_Data>();
    public static List<String> array_of_pVarient =new ArrayList<>();
    public static HashMap<String,String> Download_hashmap = new HashMap<String,String>();

    public static List<String> Customers =new ArrayList<>();
    public static HashMap<String,String> Customers_map = new HashMap<String,String>();

    public static List<RCTOData> AllresultSubDealer = new ArrayList<RCTOData>();
    public static List<String> SubDealer_List = new ArrayList<>();
    public static ArrayList<String> Some_list = new ArrayList<String>();

    public static String Sub_Dealer_Code="";
    public static String Product_Array="";
    public static String Sub_Dealer_name="";
    public static String Sub_Dealer_address="";
    public static String Sub_Email="";
    public static String SUB_Mobile="";
    public static String Dealer_Code="";
    public static String Dealer_EMAIL="";
    public static String state_code="";
    public static String click_flag="";
    public static String Sub_shop_name="";
    public static String merge_retailer_code="";
	
	//public static String GLOVEL_LONG_DESC="";
    
 // create map to store
    public static  Map<String, List<String>> map = new HashMap<String, List<String>>();
    public static LinkedHashMap<String, String> quastionmap = new LinkedHashMap<>();

//    public static void hideSoftKeyboard(Activity activity) {
//        InputMethodManager inputMethodManager =
//                (InputMethodManager) activity.getSystemService(
//                        Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
//    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        /*
         * If no view is focused, an NPE will be thrown
         *
         * Maxim Dmitriev
         */
        if (focusedView != null) {
            inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
