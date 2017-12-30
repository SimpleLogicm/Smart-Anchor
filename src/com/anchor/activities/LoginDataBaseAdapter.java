package com.anchor.activities;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoginDataBaseAdapter 
{
		static final String DATABASE_NAME = "simple_logic.db";
		static final int DATABASE_VERSION = 1;
		public static final int NAME_COLUMN = 1;
		// TODO: Create public field for each column in your table.
		// SQL Statement to create a new database.

		static final String DATABASE_CREATE_REG = "CREATE TABLE IF NOT EXISTS "+"users"+
		                    "( " +"ID"+" integer primary key autoincrement,"+ "username  text,password text," +
		                    		"date_of_joining text,mob_no text,email_id text,reporting_to text,first_name text," +
		                    		"last_name text,status text,created_by text,modified_by text,project_id text,company_id text,role_id text," +
				"device_id text,state_id text,city_id text,created_at text,updated_at text,ids text, cur_date text, login_count text);";
						
		static final String DATABASE_CREATE_TASKS = "CREATE TABLE IF NOT EXISTS "+"tasks"+
                "( " +"ID"+" integer primary key autoincrement,"+ "from_date  text,to_date text," +
                		"details text,user_id text,created_at text,updated_at text,ids text);";
		
		static final String DATABASE_CREATE_CUSTOMER = "CREATE TABLE IF NOT EXISTS "+"customers"+
                "( " +"ID"+" integer primary key autoincrement,"+ "name  text,industry_vertical_id text," +
                		"business_description text,created_at text,updated_at text,ids text);";
		
		static final String DATABASE_CREATE_INDUST_VERT = "CREATE TABLE IF NOT EXISTS "+"industry_verticals"+
                "( " +"ID"+" integer primary key autoincrement,"+ "description  text,created_at text,updated_at text,ids text);";
		
		static final String DATABASE_CREATE_PROJECTS = "CREATE TABLE IF NOT EXISTS "+"projects"+
                "( " +"ID"+" integer primary key autoincrement,"+ "name text, description text, company_id text, created_at text,updated_at text,ids text);";
		
		static final String DATABASE_CREATE_COMP_CONTACT = "CREATE TABLE IF NOT EXISTS "+"company_contacts"+
                "( " +"ID"+" integer primary key autoincrement,"+ "name text, type text, designation text, office_id text,mobile_no text,email_id text, created_at text,updated_at text,ids text);";
		
		static final String DATABASE_CREATE_COMP_OFFICE = "CREATE TABLE IF NOT EXISTS "+"company_offices"+
                "( " +"ID"+" integer primary key autoincrement,"+ "company_id text, office_id text, office_type text, address1 text, address2 text," +
                		"street text, landmark text, state_id text, city_id text, pincode text, board_no text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_CUST_POS = "CREATE TABLE IF NOT EXISTS "+"customer_pos"+
                "( " +"ID"+" integer primary key autoincrement,"+ "order_id text, customer_order_reference text, order_date text, customer_id text, project_id text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_LICENSES = "CREATE TABLE IF NOT EXISTS "+"licenses"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, stage text, type text, start_date text, end_date text, number_of_users text, number_of_instances text, billing_rate text, billing_cycle text, billing_type text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_BILL_TRANS = "CREATE TABLE IF NOT EXISTS "+"billing_transactions"+
                "( " +"ID"+" integer primary key autoincrement,"+ "bill_id text, license_id text, project_id text, start_date text, end_date text, billable_no_of_users text, billing_amount text, taxes text, total text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_ROLES = "CREATE TABLE IF NOT EXISTS "+"roles"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, company_id text, code text, desc text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_DEVICES = "CREATE TABLE IF NOT EXISTS "+"devices"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, company_id text, code text, device_type text, model text, IMEINo text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
				
		static final String DATABASE_CREATE_REGISTRATION = "CREATE TABLE IF NOT EXISTS "+"registrations"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, latlon text, created_at text, updated_at text, ids text);";

		static final String DATABASE_CREATE_STATES = "CREATE TABLE IF NOT EXISTS "+"states"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, name text, status text, created_by text, modified_by text, created_at text, ids text);";
		
		static final String DATABASE_CREATE_CITIES = "CREATE TABLE IF NOT EXISTS "+"cities"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, name text, state_id text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_BEATS = "CREATE TABLE IF NOT EXISTS "+"beats"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, name text, state_id text, city_id text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_BEAT_ALLOCATION = "CREATE TABLE IF NOT EXISTS "+"beat_allocations"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, date text, user_id text, beat_id text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_PRODUCT_CATEGORIES = "CREATE TABLE IF NOT EXISTS "+"product_categories"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, category_id text, category_code text, category_desc text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_PRODUCTS = "CREATE TABLE IF NOT EXISTS "+"products"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, desc text, category_id text, status text, created_by text, modified_by text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_PRODUCT_VARIENTS = "CREATE TABLE IF NOT EXISTS "+"product_variants"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, desc text, product_id text, MRP text, retail_price text, product_unit text, status text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_PRODUCT_PACK_SIZES = "CREATE TABLE IF NOT EXISTS "+"product_pack_sizes"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, product_variant_id text, desc text, product_unit text, product_pack_size text, status text, created_at text, updated_at text, ids text);";
		
//		static final String DATABASE_CREATE_DISTRIBUTORS = "create table "+"distributors"+
//                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, name text, shop_name text, address1 text, address2 text, street text, landmark text, state_id text, city_id text, beat_id text, " +
//                		"pincode text, landline_no text, mobile_no text, email text, status text, created_by text, modified_by text, vatin text, created_at text, updated_at text, ids text);";
		
		
		static final String DATABASE_CREATE_DISTRIBUTORS = "CREATE TABLE IF NOT EXISTS "+"distributors"+
                "( " +"ID"+" integer primary key autoincrement,"+ "code text, name text, shop_name text, address1 text, address2 text, street text, landmark text, state_id text, city_id text, " +
                		"pincode text, landline_no text, mobile_no text, email text, status text);";
		
		
		static final String DATABASE_CREATE_RETAILERS = "CREATE TABLE IF NOT EXISTS "+"retailers"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, code text, name text, shop_name text, address text, street text, landmark text, state_id text, city_id text, beat_id text, " +
                		"pincode text, landline_no text, mobile_no text, email text, status text, created_by text, modified_by text, vatin text, created_at text, updated_at text, ids text);";
		
		static final String DATABASE_CREATE_ORDERS = "CREATE TABLE IF NOT EXISTS "+"orders"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text,order_id text, customer_id text, retailer_id text, user_id text, city_id text, beat_id text, latlon text, sync text, total_order_amount text, signature_path text, distributor_id text, " +
                		"total_line_items text, created_at text, updated_at text,customer_name text,state_name text,city_name text,order_type text,latitude text,longitude text,beat_idnew text,getsign_img text,details1 text,details2 text,details3 text,details4 text,details5 text,order_category text);";

		static final String DATABASE_CREATE_ORDER_PRODUCTS = "CREATE TABLE IF NOT EXISTS "+"order_products"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, order_id text, line_number text, category_id text, product_id text, product_variant_id text, product_pack_size_id text, scheme_id text, billed_qty text, free_qty text, " +
                		"total_qty text, retail_price text, MRP text, amount text, created_at text, updated_at text,customer_name text,scheme_amount text,item_number text,actual_discount text, product_name text);";


	static final String DATABASE_CREATE_PREVIOUS_ORDERS = "CREATE TABLE IF NOT EXISTS "+"previous_orders"+
			"( " +"ID"+" integer primary key autoincrement,"+ "project_id text,order_id text, customer_id text, retailer_id text, user_id text, city_id text, beat_id text, latlon text, sync text, total_order_amount text, signature_path text, distributor_id text, " +
			"total_line_items text, created_at text, updated_at text,customer_name text,state_name text,city_name text,order_type text);";

	static final String DATABASE_CREATE_PREVIOUS_ORDER_PRODUCTS = "CREATE TABLE IF NOT EXISTS "+"previous_order_products"+
			"( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, order_id text, line_number text, category_id text, product_id text, product_variant_id text, product_pack_size_id text, scheme_id text, billed_qty text, free_qty text, " +
			"total_qty text, retail_price text, MRP text, amount text, created_at text, updated_at text,customer_name text,scheme_amount text,item_number text,actual_discount text, product_name text);";
		
		
//		static final String DATABASE_CREATE_ORDER_PRODUCTS = "create table "+"order_products"+
//                "( " +"ID"+" integer primary key autoincrement,"+ "code text, name text, primary_category text, sub_category text, " +
//                		"product_variant text, retail_price text, mrp text, qualifying_qty text, free_qty text, status text);";
		
		static final String DATABASE_CREATE_NO_ORDERS = "CREATE TABLE IF NOT EXISTS "+"no_orders"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, order_id text, user_id text, city_id text, beat_id text, latlon text, sync text, reason_id text, " +
                		" created_at text, updated_at text, reason_code text, reason_type text, latitude text, longitude text,order_number text);";
		
		static final String DATABASE_CREATE_RETURN_ORDERS = "CREATE TABLE IF NOT EXISTS "+"return_orders"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, order_id text, user_id text, city_id text, beat_id text, latlon text, sync text, signature_path text, " +
                		" distributor_id text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_RETURN_ORDER_PRODUCTS = "CREATE TABLE IF NOT EXISTS "+"return_order_products"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, order_id text, category_id text, product_id text, product_variant_id text, product_pack_size_id text, total_return_qty text, " +
                		" created_at text, updated_at text);";
		

		
		static final String DATABASE_CREATE_CALENDER_ENTRIES = "CREATE TABLE IF NOT EXISTS "+"calender_entries"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, calender_id text, type text, from_date text, to_date text, details text, latlon text, " +
                		" created_at text, updated_at text,latitude text,longitude text,delete_flag text);";
		
		static final String DATABASE_CREATE_CALENDER_LIST_VIES = "CREATE TABLE IF NOT EXISTS "+"calender_list_vies"+
                "( " +"ID"+" integer primary key autoincrement,"+ "calender_id text, calender_date text, type text, details text, " +
                		" created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_EXPENCES_TRAVELS = "CREATE TABLE IF NOT EXISTS "+"expenses_travels"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, expense_id text, " +
                		"travel_from text, travel_to text, travel_date text, travel_mode text, travel_cost text, travel_text text, approved_amount text, approved text, approved_by text, approve_text text, created_at text, updated_at text,code text);";
		
		static final String DATABASE_CREATE_EXPENCES_MISCS = "CREATE TABLE IF NOT EXISTS "+"expenses_miscs"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, expense_id text, " +
                		"misc_date text, misc_amount text, misc_text text, approved_amount text, approved text, approved_by text, approve_text text, created_at text, updated_at text,code text);";
				
		static final String DATABASE_CREATE_TARGETS = "CREATE TABLE IF NOT EXISTS "+"targets"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, year text, " +
                		"month text, target text, achieved text, created_at text, updated_at text);";
		
		/*static final String DATABASE_CREATE_TARGETS = "create table "+"targets"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, year text, " +
                		"month text, target text, achieved text, created_at text, updated_at text, quarter_text text);";*/
		
		static final String DATABASE_CREATE_CUSTOMER_SERVICES_FEEDBACKS = "CREATE TABLE IF NOT EXISTS "+"customer_service_feedbacks"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, user_id text, " +
                		"date text, text text, status text, HO_action_date text, HO_action_by text, Ho_comment text, created_at text, updated_at text,latitude text,longitude text,code text);";
			
		static final String DATABASE_CREATE_CUSTOMER_SERVICE_COMPLAINTS = "CREATE TABLE IF NOT EXISTS "+"customer_service_complaints"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, user_id text, " +
                		"date text, text text, status text, created_at text, updated_at text,latitude text,longitude text,code text);";
			
		static final String DATABASE_CREATE_COMPLAINT_ACTIONS = "CREATE TABLE IF NOT EXISTS "+"complaint_actions"+
                "( " +"ID"+" integer primary key autoincrement,"+ "complaint_id text, HO_action_date text, HO_action_by text, Ho_comment text, " +
                		"status text, created_at text, updated_at text);";
		
//		static final String DATABASE_CREATE_CLAIMS = "create table "+"customer_service_claims"+
//                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, type text, retailer_id text, " +
//                		"user_id text, date text, text text, amount text, status text, balance text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_CUSTOMER_SERVICE_CLAIMS = "CREATE TABLE IF NOT EXISTS "+"customer_service_claims"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, type text, retailer_id text, " +
                		"user_id text, date text, text text, amount text, status text, balance text, created_at text, updated_at text,latitude text,longitude text,code text);";
		
		static final String DATABASE_CREATE_CLAIMS_ACTIONS = "CREATE TABLE IF NOT EXISTS "+"claim_actions"+
                "( " +"ID"+" integer primary key autoincrement,"+ "customer_service_claim_action text, claim_amount_approved text, claim_amount_rejected text, HO_action_date text, " +
                		"HO_action_by text, HO_comment text, status text, created_at text, updated_at text);";
		
		/*static final String DATABASE_CREATE_CUSTOMER_SERVICE_COMPETITION = "create table "+"customer_service_competition_stocks"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, user_id text, " +
                		"category_id text, product_id text, product_variant_id text, product_pack_size_id text, competition_product_name text, competition_product_quantity text, competition_product_text, created_at text, updated_at text);"*/;
		
		static final String DATABASE_CREATE_CUSTOMER_SERVICE_COMPETITION = "CREATE TABLE IF NOT EXISTS "+"customer_service_competition_stocks"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, user_id text, " +
                		"category_id text, product_id text, product_variant_id text, product_pack_size_id text, competition_product_name text, competition_product_quantity text, competition_product_text, created_at text, updated_at text,latitude text,longitude text,code text);";
		
		
		static final String DATABASE_CREATE_CUSTOMER_MEDIA = "CREATE TABLE IF NOT EXISTS "+"customer_service_media"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, media_type text, retailer_id text, " +
                		"user_id text, location text, media_text text, created_at text, updated_at text,latitude text,longitude text,media_id text,code text);";
		
		static final String DATABASE_CREATE_DELIVERY_SCHEDULES = "CREATE TABLE IF NOT EXISTS "+"delivery_schedules"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, " +
                		"order_id text, sales_person text, user_id text, depatch_date text, delivery_date text, order_amount text, " +
                		"accepted_payment_mode text, total_line_items text, collected_amount text, outstanding_amount text, created_at text, updated_at text,c_address text);";
		
		static final String DATABASE_CREATE_DELIVERY_PRODUCTS = "CREATE TABLE IF NOT EXISTS "+"delivery_products"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, order_id text, " +
                		"line_item_id text, category_id text, product_id text, product_variant_id text, product_pack_size_id text, order_quantity text, " +
                		"delivered_quality text, truck_number text, transporter_details text, created_at text, updated_at text, product_name text);";
		
		static final String DATABASE_CREATE_CREDIT_LIMIT = "CREATE TABLE IF NOT EXISTS "+"credit_limits"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, " +
                		"created_by text, credit_limit text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_OUTSTANDINGS = "CREATE TABLE IF NOT EXISTS "+"outstandings"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, " +
                		"user_id text, order_id text, order_amount text, delivery_status text, amount_collected text, payment_status text, amount_outstanding text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_INVOICES = "CREATE TABLE IF NOT EXISTS "+"invoices"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, retailer_id text, " +
                		"created_by text, order_id text, date text, tax_name_1 text, tax_name_2 text, tax_name_3 text, tax_name_4 text,  tax_name_5 text, tax_amount_1 text, tax_amount_2 text, tax_amount_3 text, tax_amount_4 text, tax_amount_5 text, total text, created_at text, updated_at text);";
			
		static final String DATABASE_CREATE_SYNC_HISTORIES = "CREATE TABLE IF NOT EXISTS "+"sync_histories"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, " +
                		"tables_synced text, latlon text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_SURVEYS = "CREATE TABLE IF NOT EXISTS "+"surveys"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, q1 text, q2 text, q3 text, q4 text, q5 text, q6 text, q7 text, " +
                		"q8 text, q9 text, q10 text, start_date text, expiry_date text, status text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_SURVEYS_RESULTS = "CREATE TABLE IF NOT EXISTS "+"survey_results"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, user_id text, retailer_id text, a1 text, a2 text, a3 text, a4 text, a5 text, " +
                		"a6 text, a7 text, a8 text, a9 text, a10 text, ans_date text, ans_GPS text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_NEW_LAUNCHES = "CREATE TABLE IF NOT EXISTS "+"new_launches"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, date text, media_path1 text, media_path2 text, media_path3 text, media_path4 text, media_path5 text, media_path6 text, " +
                		"media_path7 text, media_path8 text, media_path9 text, media_path10 text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_ADVERTISEMENTS = "CREATE TABLE IF NOT EXISTS "+"advertisements"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, date text, media_path text, created_at text, updated_at text);";
		
		static final String DATABASE_CREATE_ITEMMASTER = "CREATE TABLE IF NOT EXISTS "+"item_master"+
                "( " +"ID"+" integer primary key autoincrement,"+ "code text, name text, primary_category text, sub_category text, " +
          		"product_variant text, retail_price text, mrp text, qualifying_qty text, free_qty text, status text);";

	static final String DATABASE_CREATE_ITEMSCHEME = "CREATE TABLE IF NOT EXISTS "+"scheme"+
			"( " +"ID"+" integer primary key autoincrement,"+ "code text, name text, description text, display_name text, " +
			"product_id text, qualifying_qty text, amount text, foc_product_id text;";

	static final String DATABASE_CREATE_LABEL_CHANGES  = "CREATE TABLE IF NOT EXISTS "+"label_chnages"+
			"( " +"ID"+" integer primary key autoincrement,"+ "variable_name text, new_label text, editable text, mandatory text, allow text);";

	static final String DATABASE_CREATE_ITEMSCHEME_NEW = "CREATE TABLE IF NOT EXISTS "+"scheme_new"+
			"( " +"ID"+" integer primary key autoincrement,"+ "code text, type text, name text, description text, display_name text, " +
			"product_id text, qualifying_qty text, amount text, foc_product_id text, created_by text, modified_by text, created_at text, updated_at text, sid text, project_id text);";

	static final String DATABASE_CREATE_L1_CONTACT = "CREATE TABLE IF NOT EXISTS "+"L1_Contact"+
			"( " +"ID"+" integer primary key autoincrement,"+ "project_id text, Title text, Heading text, Sub_Heading text, " +
			"Address text, phone1 text, phone2 text, email_id1 text, email_id2 text, website text, created_at text, updated_at text, updated_by text);";

	static final String DATABASE_CREATE_WAREHOUSE = "CREATE TABLE IF NOT EXISTS "+"warehouse"+
			"( " +"ID"+" integer primary key autoincrement,"+ "project_id text, code text, type text, name text, " +
			"address text, city text, state text, latlong text, status text, detail1 text, detail2 text, detail3 text);";

	static final String DATABASE_CREATE_BACKGROUND_SERVICE_CHECK = "CREATE TABLE IF NOT EXISTS "+"background_service_check"+
			"( " +"ID"+" integer primary key autoincrement,"+ "latitude text, longitude text," +
			"location_date text);";
		

		
		static final String DATABASE_CREATE_CUST_MASTER = "CREATE TABLE IF NOT EXISTS "+"customer_master"+
                "( " +"ID"+" integer primary key autoincrement,"+ "LEGACY_CUSTOMER_CODE text," +
"CUSTOMER_NAME text,CUSTOMER_SHOPNAME text, ADDRESS text, STREET text, LANDMARK text, PIN_CODE text, LANDLINE_NO text, MOBILE_NO text, EMAIL_ADDRESS text, STATUS text, STATE text, CITY text, BEAT text,vatin text,email text,created_at text,lat text,long text);";
		
		
		static final String DATABASE_CREATE_ORDERSTATUS = "CREATE TABLE IF NOT EXISTS "+"status_master"+
                "( " +"ID"+" integer primary key autoincrement,"+ "order_id text, item_description text, item_amount text, item_number text);";
		
		
		static final String DATABASE_CREATE_RETURN_ORDER_NEW = "CREATE TABLE IF NOT EXISTS "+"returnordernew"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, order_id text, customer_id text, retailer_id text, user_id text, city_id text, beat_id text, latlon text, sync text, total_order_amount text, signature_path text, distributor_id text, " +
                		"total_line_items text, created_at text, updated_at text,customer_name text,state_name text,city_name text,order_type text,latitude text,longitude text,beat_idnew text);";

		static final String DATABASE_CREATE_RETURN_ORDER_PRODUCTS_NEW = "CREATE TABLE IF NOT EXISTS "+"returnorder_products_new"+
                "( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text, order_id text, line_number text, category_id text, product_id text, product_variant_id text, product_pack_size_id text, scheme_id text, billed_qty text, free_qty text, " +
                		"total_qty text, retail_price text, MRP text, amount text, created_at text, updated_at text,customer_name text,scheme_amount text,item_number text,actual_discount text, product_name text);";
		
		static final String DATABASE_NO_ORDER_REASON = "CREATE TABLE IF NOT EXISTS "+"noorder_reason"+
                "( " +"ID"+" integer primary key autoincrement,"+ "code text, reason_name text);";

	static final String DATABASE_ORDER_DETAILS = "CREATE TABLE IF NOT EXISTS "+"order_details"+
			"( " +"ID"+" integer primary key autoincrement,"+ "sync_time text, total_sync text);";

	static final String DATABASE_CREDIT_PROFILE = "CREATE TABLE IF NOT EXISTS "+"credit_profile"+
			"( " +"ID"+" integer primary key autoincrement,"+ "project_id text, customer_id text,retailer_id text,created_by text,created_at text,updated_at text,code text,credit_limit text,amount_outstanding text,amount_overdue text);";

	static final String DATABASE_USER_EMAIL = "CREATE TABLE IF NOT EXISTS "+"user_email"+
			"( " +"ID"+" integer primary key autoincrement,"+ "email text);";

	static final String DATABASE_GEO_DATA = "CREATE TABLE IF NOT EXISTS "+"geo_data"+
			"( " +"ID"+" integer primary key autoincrement,"+ "lati text, longi text, addressg text, datatimeg text, date1 text, time1 text);";

	static final String DATABASE_SURVEY_QUESTIONS = "CREATE TABLE IF NOT EXISTS "+"Survey_Questions"+
			"( " +"ID"+" integer primary key autoincrement,"+ "survey_code text, question_code text, active_from text, active_to text, question text, option_1 text, option_2 text, option_3 text, option_4 text, option_5 text, Q_updated_by text, Q_updated_at text);";


	static final String DATABASE_SURVEY_ANSWERS = "CREATE TABLE IF NOT EXISTS "+"Survey_Answers"+
			"( " +"ID"+" integer primary key autoincrement,"+ "user_name text, answer_date text, survey_code text, customer_code text, question_code text, customer_choice text, latitude text, longitude text,code text);";

	static final String DATABASE_DISTRIBUTORBEAT= "CREATE TABLE IF NOT EXISTS "+"distributor_beats"+
			"( " +"ID"+" integer primary key autoincrement,"+ "code text, distributor_code text, beat_cod text, status text);";

	static final String DATABASE_TARGET_DATANEW= "CREATE TABLE IF NOT EXISTS "+"target_datanew"+
			"( " +"ID"+" integer primary key autoincrement,"+ "main_heading text, product_text text, target text, achieved text,age text);";

	static final String DATABASE_ORDER_CATEGORY= "CREATE TABLE IF NOT EXISTS "+"order_category"+
			"( " +"ID"+" integer primary key autoincrement,"+ "Code text,Name text, Description text, Details1 text, Details2 text,Details3 text,Created_at text,Updated_at text,Created_by text,Updated_by text);";

	static final String DATABASE_CREATE_ATTENDENCE_F = "CREATE TABLE IF NOT EXISTS " + "attendence_f" +
			"( " + "ID" + " integer primary key autoincrement," + "name text);";

	static final String DATABASE_ATTENDANCE_DATA = "CREATE TABLE IF NOT EXISTS " + "attendance" +
			"( " + "ID" + " integer primary key autoincrement," + "user_id text, punched_on text, punched_at_latitude text, punched_at_longitude text, punched_button text, conference_id text,vertical_id text,punched_at_address text,server_flag text,current_date_only text);";

	static final String DATABASE_VERSION_INFO = "CREATE TABLE IF NOT EXISTS " + "version_info" +
			"( " + "ID" + " integer primary key autoincrement," + "version_code text, version_name text);";

//		//new product and customer for Girnar Monday Demo
//		static final String DATABASE_CREATE_NEWCUSTOMER = "create table "+"new_customers"+
//             "( " +"ID"+" integer primary key autoincrement,"+ "code text, name text, shop_name text," +
//             "address text, street text, landmarkstate text, city text, beat text, pincode text,landline_no text, mobile_no text, email text, status text);";
//		
//		static final String DATABASE_CREATE_NEWPRODUCT = "create table "+"new_products"+
//                "( " +"ID"+" integer primary key autoincrement,"+ "code text, name  text, primary_category text," +
//                		"sub_category text, product_variant text, retail_price text, mrp text, qualifying_qty text, free_qty text, status text);";
		
	 //Variable to hold the database instance.
		public  SQLiteDatabase db;
		// Context of the application using the database. 
		private final Context context;
		// Database open/upgrade helper
		private DataBaseHelper dbHelper;
		
		public  LoginDataBaseAdapter(Context _context) 
		{
			context = _context;
			dbHelper = new DataBaseHelper(context);
		}
		public LoginDataBaseAdapter open() throws SQLException 
		{
			db = dbHelper.getWritableDatabase();
			return this;
		}
		
		public void close() 
		{
			db.close();
		}
		
        public  SQLiteDatabase getDatabaseInstance()
		{
			return db;
		}
        
       // public LoginDataBaseAdapter opnToRead() {
//        	dbHelper = new DataBaseHelper(context,
//        			DATABASE_NAME, null, DATABASE_VERSION);
//        	  database_ob = dbHelper.getReadableDatabase();
//        	  return this;
//        	  }
//        	 
//        	 public LoginDataBaseAdapter opnToWrite() {
//        		 dbHelper = new DataBaseHelper(context,
//        				 DATABASE_NAME, null, DATABASE_VERSION);
//        	    database_ob = dbHelper.getWritableDatabase();
//        	  return this;
//        	 
//        	 }
        
        public Cursor queryName() {
        	   String[] cols = { dbHelper.KEY_ID, dbHelper.FNAME,
        			   };
        	    //opnToWrite();
        	    Cursor c = db.query(dbHelper.TABLE_STATES, cols, null, null, null, null, null);
        	   return c;
        	 }
        
        
        public void insertNewCustomer(String code, String name, String primary_category, String sub_category, 
        		String product_variant, String retail_price, String mrp, String qualifying_qty, String free_qty, String status)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("code", code);
            newValues.put("name", name);
      		newValues.put("primary_category", primary_category);
      		newValues.put("sub_category", sub_category);
      		newValues.put("product_variant", product_variant);
      		newValues.put("retail_price", retail_price);
      		newValues.put("mrp", mrp);
      		newValues.put("qualifying_qty", qualifying_qty);
      		newValues.put("free_qty", free_qty);
      		newValues.put("status", status);
      		   		
      		// Insert the row into your table
      		db.insert("company_contacts", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}

	public void insertLABEL_CHANGES(String variable_name, String new_label, String editable, String mandatory, String allow) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("variable_name", variable_name);
		newValues.put("new_label", new_label);
		newValues.put("editable", editable);
		newValues.put("mandatory", mandatory);
		newValues.put("allow", allow);
		db.insert("label_chnages", null, newValues);
	}

	public void insertVersionInfo(int version_code, String version_name) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("version_code", version_code);
		newValues.put("version_name", version_name);
		db.insert("version_info", null, newValues);
	}
        
        public void insertEntry(String username, String password, String doj, String mob_no, String email_id, String report_to,
								String first_name, String last_name, String status, String create_by, String modified, String project_id, String company_id,
								String role_id, String device_id, String state_id, String city, String create_at, String update_at, String id, String cur_date, String login_count)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("username", username);
			newValues.put("password", password);
			newValues.put("date_of_joining", doj);
			newValues.put("mob_no", mob_no);
			newValues.put("email_id", email_id);
			newValues.put("reporting_to", report_to);
			newValues.put("first_name", first_name);
			newValues.put("last_name", last_name);
			
			newValues.put("status", status);
			newValues.put("created_by", create_by);
			newValues.put("modified_by", modified);
			newValues.put("project_id", project_id);
			newValues.put("company_id", company_id);
			newValues.put("role_id", role_id);
			newValues.put("device_id", device_id);
			newValues.put("state_id", state_id);
			
			newValues.put("city_id", city);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			newValues.put("cur_date", cur_date);
			newValues.put("login_count", login_count);
			
			// Insert the row into your table
			db.insert("users", null, newValues);
		}
        
     public void insertTask(String from_date,String to_date,String details,String user_id,String created_at,String updated_at,String ids)
   	 { 
          ContentValues newValues = new ContentValues();
   		// Assign values for each row.
   		newValues.put("from_date", from_date);
   		newValues.put("to_date", to_date);
   		newValues.put("details", details);
   		newValues.put("user_id", user_id);
   		newValues.put("created_at", created_at);
   		newValues.put("updated_at", updated_at);
   		newValues.put("ids", ids);
   		   		
   		// Insert the row into your table
   		db.insert("tasks", null, newValues);
   		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
   	}
     
     public void insertCustomer(String name,String industry_vertical_id,String business_description,String created_at,String updated_at,String ids)
   	 { 
        ContentValues newValues = new ContentValues();
   		// Assign values for each row.
   		newValues.put("name", name);
   		newValues.put("industry_vertical_id", industry_vertical_id);
   		newValues.put("business_description", business_description);
   		newValues.put("created_at", created_at);
   		newValues.put("updated_at", updated_at);
   		newValues.put("ids", ids);
   		   		
   		// Insert the row into your table
   		db.insert("customers", null, newValues);
   		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
   	}
     
     public void insertIndustVert(String discription,String created_at,String updated_at,String ids)
   	 { 
        ContentValues newValues = new ContentValues();
   		// Assign values for each row.
   		newValues.put("description", discription);
   		newValues.put("created_at", created_at);
   		newValues.put("updated_at", updated_at);
   		newValues.put("ids", ids);
   		   		
   		// Insert the row into your table
   		db.insert("industry_verticals", null, newValues);
   		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
   	}
     
     public void insertProjects(String name, String discription, String company_id, String created_at,String updated_at,String ids)
   	 { 
        ContentValues newValues = new ContentValues();
   		// Assign values for each row.
        newValues.put("name", name);
   		newValues.put("description", discription);
   		newValues.put("company_id", company_id);
   		newValues.put("created_at", created_at);
   		newValues.put("updated_at", updated_at);
   		newValues.put("ids", ids);
   		   		
   		// Insert the row into your table
   		db.insert("projects", null, newValues);
   		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
   	}
     
     public void insertCompContact(String name, String type, String designation, String office_id, String mobile_no, String email_id, String created_at,String updated_at,String ids)
   	 { 
        ContentValues newValues = new ContentValues();
   		// Assign values for each row.
        newValues.put("name", name);
   		newValues.put("type", type);
   		newValues.put("designation", designation);
   		newValues.put("office_id", office_id);
   		newValues.put("mobile_no", mobile_no);
   		newValues.put("email_id", email_id);
   		newValues.put("created_at", created_at);
   		newValues.put("updated_at", updated_at);
   		newValues.put("ids", ids);
   		   		
   		// Insert the row into your table
   		db.insert("company_contacts", null, newValues);
   		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
   	}
     
     public void insertCompOffice(String company_id, String office_id, String office_type, String address1, String address2, String street, 
				String landmark, String state_id, String city_id, String pincode, String board_no,String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("company_id", company_id);
			newValues.put("office_id", office_id);
			newValues.put("office_type", office_type);
			newValues.put("address1", address1);
			newValues.put("address2", address2);
			newValues.put("street", street);
			newValues.put("landmark", landmark);
			newValues.put("state_id", state_id);
			newValues.put("city_id", city_id);
			newValues.put("pincode", pincode);
			newValues.put("board_no", board_no);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			
			// Insert the row into your table
			db.insert("company_offices", null, newValues);
		}
     
     public void insertCustPos(String order_id, String customer_order_reference, String order_date, String customer_id, String project_id, String created_at, String updated_at, String ids)
   	 { 
        ContentValues newValues = new ContentValues();
   		// Assign values for each row.
        newValues.put("order_id", order_id);
   		newValues.put("customer_order_reference", customer_order_reference);
   		newValues.put("order_date", order_date);
   		newValues.put("customer_id", customer_id);
   		newValues.put("project_id", project_id);
   		newValues.put("created_at", created_at);
   		newValues.put("updated_at", updated_at);
   		newValues.put("ids", ids);
   		   		
   		// Insert the row into your table
   		db.insert("customer_pos", null, newValues);
   		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
   	}
     
     public void insertLicense(String project_id, String stage, String type, String start_date, String end_date, String number_of_users, 
				String number_of_instances, String billing_rate, String billing_cycle, String billing_type, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("stage", stage);
			newValues.put("type", type);
			newValues.put("start_date", start_date);
			newValues.put("end_date", end_date);
			newValues.put("number_of_users", number_of_users);
			newValues.put("number_of_instances", number_of_instances);
			newValues.put("billing_rate", billing_rate);
			newValues.put("billing_cycle", billing_cycle);
			newValues.put("billing_type", billing_type);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			
			// Insert the row into your table
			db.insert("licenses", null, newValues);
		}
     
     public void insertBillTrans(String bill_id, String license_id, String project_id, String start_date, String end_date, String billable_no_of_users, 
				String billing_amount, String taxes, String total, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("bill_id", bill_id);
			newValues.put("license_id", license_id);
			newValues.put("project_id", project_id);
			newValues.put("start_date", start_date);
			newValues.put("end_date", end_date);
			newValues.put("billable_no_of_users", billable_no_of_users);
			newValues.put("billing_amount", billing_amount);
			newValues.put("taxes", taxes);
			newValues.put("total", total);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			
			// Insert the row into your table
			db.insert("billing_transactions", null, newValues);
		}
     
     public void insertRoles(String project_id, String company_id, String code, String desc, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("company_id", company_id);
			newValues.put("code", code);
			newValues.put("desc", desc);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			
			// Insert the row into your table
			db.insert("roles", null, newValues);
		}
  
     public void insertDevices(String project_id, String company_id, String code, String device_type, String model, String IMEINo, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("company_id", company_id);
			newValues.put("code", code);
			newValues.put("device_type", device_type);
			newValues.put("model", model);
			newValues.put("IMEINo", IMEINo);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("devices", null, newValues);
		}
          
        public void insertRegistration(String project_id, String customer_id, String user_id, String latlon, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("user_id", user_id);
			newValues.put("latlon", latlon);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("registrations", null, newValues);
		}
        
        public void insertStates(String project_id, String customer_id, String code, String name, String status, String created_by, 
				String modified_by, String create_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("code", code);
			newValues.put("name", name);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("states", null, newValues);
		}
        
        public void insertCities(String project_id, String customer_id, String code, String name, String state_id, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("code", code);
			newValues.put("name", name);
			newValues.put("state_id", state_id);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("cities", null, newValues);
		}
        
        public void insertBeats(String project_id, String customer_id, String code, String name, String state_id, String city_id, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("code", code);
			newValues.put("name", name);
			newValues.put("state_id", state_id);
			newValues.put("city_id", city_id);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("beats", null, newValues);
		}
        
        public void insertBeatAlloc(String project_id, String customer_id, String date, String user_id, String beat_id, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("date", date);
			newValues.put("user_id", user_id);
			newValues.put("beat_id", beat_id);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("beat_allocations", null, newValues);
		}
        
        public void insertProductCateg(String project_id, String customer_id, String category_id, String category_code, String category_desc, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("category_id", category_id);
			newValues.put("category_code", category_code);
			newValues.put("category_desc", category_desc);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("product_categories", null, newValues);
		}
        
        public void insertProducts(String project_id, String customer_id, String code, String desc, String category_id, String status, String created_by, 
				String modified_by, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("code", code);
			newValues.put("desc", desc);
			newValues.put("category_id", category_id);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("products", null, newValues);
		}
        
        
        
        public void insertProductVarients(String project_id, String customer_id, String code, String desc, String product_id, String MRP, String retail_price, 
				String product_unit, String status, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("code", code);
			newValues.put("desc", desc);
			newValues.put("product_id", product_id);
			newValues.put("MRP", MRP);
			newValues.put("retail_price", retail_price);
			newValues.put("product_unit", product_unit);
			newValues.put("status", status);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("product_variants", null, newValues);
		}
        
        public void insertProductPackSizes(String project_id, String customer_id, String product_variant_id, String desc, String product_unit, String product_pack_size,String status, String create_at, String update_at, String id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("product_variant_id", product_variant_id);
			newValues.put("desc", desc);
			newValues.put("product_unit", product_unit);
			newValues.put("product_pack_size", product_pack_size);
			newValues.put("status", status);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", id);
			// Insert the row into your table
			db.insert("product_pack_sizes", null, newValues);
		}
        
//        public void insertDistributors(String project_id, String customer_id, String code, String name, 
//        		String shop_name, String address1, String address2, String street, String landmark, String state_id,
//        		String city_id, String beat_id, String pincode, String landline_no, String mobile_no, String email, 
//        		String status, String created_by, String modified_by, String vatin, String create_at, String update_at, String id)
//		{
//	       ContentValues newValues = new ContentValues();
//			// Assign values for each row.
//			newValues.put("project_id", project_id);
//			newValues.put("customer_id", customer_id);
//			newValues.put("code", code);
//			newValues.put("name", name);
//			newValues.put("shop_name", shop_name);
//			newValues.put("address1", address1);
//			newValues.put("address2", address2);
//			newValues.put("street", street);
//			newValues.put("landmark", landmark);
//			newValues.put("state_id", state_id);
//			newValues.put("city_id", city_id);
//			newValues.put("beat_id", beat_id);
//			newValues.put("pincode", pincode);
//			newValues.put("landline_no", landline_no);
//			newValues.put("mobile_no", mobile_no);
//			newValues.put("email", email);
//			newValues.put("status", status);
//			newValues.put("created_by", created_by);
//			newValues.put("modified_by", modified_by);
//			newValues.put("vatin", vatin);
//			newValues.put("created_at", create_at);
//			newValues.put("updated_at", update_at);
//			newValues.put("ids", id);
//			// Insert the row into your table
//			db.insert("distributors", null, newValues);
//		}
        
        public void insertDistributors(String code, String name, String shop_name, String address1, String address2, String street, String landmark, String state_id, String city_id,
        		String pincode, String landline_no, String mobile_no, String email, String status)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("code", code);
			newValues.put("name", name);
			newValues.put("shop_name", shop_name);
			newValues.put("address1", address1);
			newValues.put("address2", address2);
			newValues.put("street", street);
			newValues.put("landmark", landmark);
			newValues.put("state_id", state_id);
			newValues.put("city_id", city_id);
			newValues.put("pincode", pincode);
			newValues.put("landline_no", landline_no);
			newValues.put("mobile_no", mobile_no);
			newValues.put("email", email);
			newValues.put("status", status);
			
			// Insert the row into your table
			db.insert("distributors", null, newValues);
		}
        

        public void insertRetailers(String project_id, String customer_id, String code, String name, 
        		String shop_name, String address, String street, String landmark, String state_id,
        		String city_id, String beat_id, String pincode, String landline_no, String mobile_no, String email, 
        		String status, String created_by, String modified_by, String vatin, String create_at, String update_at,String ids)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("code", code);
			newValues.put("name", name);
			newValues.put("shop_name", shop_name);
			newValues.put("address", address);
			newValues.put("street", street);
			newValues.put("landmark", landmark);
			newValues.put("state_id", state_id);
			newValues.put("city_id", city_id);
			newValues.put("beat_id", beat_id);
			newValues.put("pincode", pincode);
			newValues.put("landline_no", landline_no);
			newValues.put("mobile_no", mobile_no);
			newValues.put("email", email);
			newValues.put("status", status);
			newValues.put("created_by", created_by);
			newValues.put("modified_by", modified_by);
			newValues.put("vatin", vatin);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("ids", ids);
			
			// Insert the row into your table
			db.insert("retailers", null, newValues);
		}
        
        public void insertOrders(String project_id, String order_id,String customer_id, String retailer_id, String user_id, 
        		String city_id, String beat_id, String latlon, String sync, String total_order_amount, String signature_path, 
        		String distributor_id, String total_line_items, String create_at, String update_at,String customer_name,String state_name,String city_name,String order_type,String latitude,String longitude,String beat_idnew,String getsign_img,String details1,String details2,String details3,String details4,String details5,String order_category_id)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("order_id", order_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("city_id", city_id);
			newValues.put("beat_id", beat_id);
			newValues.put("latlon", latlon);
			newValues.put("sync", sync);
			newValues.put("total_order_amount", total_order_amount);
			newValues.put("signature_path", signature_path);
			newValues.put("distributor_id", distributor_id);
			newValues.put("total_line_items", total_line_items);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("customer_name", customer_name);
			newValues.put("state_name", state_name); 
			newValues.put("city_name", city_name);
			newValues.put("order_type", order_type);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("beat_idnew", beat_idnew);
			newValues.put("getsign_img", getsign_img);
			newValues.put("details1", details1);
			newValues.put("details2", details2);
			newValues.put("details3", details3);
			newValues.put("details4", details4);
			newValues.put("details5", details5);
			newValues.put("order_category", order_category_id);

			// Insert the row into your table
			db.insert("orders", null, newValues);
		}

	public void insertPreviousOrders(String project_id, String order_id,String customer_id, String retailer_id, String user_id,
							 String city_id, String beat_id, String latlon, String sync, String total_order_amount, String signature_path,
							 String distributor_id, String total_line_items, String create_at, String update_at,String customer_name,String state_name,String city_name,String order_type)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("project_id", project_id);
		newValues.put("order_id", order_id);
		newValues.put("customer_id", customer_id);
		newValues.put("retailer_id", retailer_id);
		newValues.put("user_id", user_id);
		newValues.put("city_id", city_id);
		newValues.put("beat_id", beat_id);
		newValues.put("latlon", latlon);
		newValues.put("sync", sync);
		newValues.put("total_order_amount", total_order_amount);
		newValues.put("signature_path", signature_path);
		newValues.put("distributor_id", distributor_id);
		newValues.put("total_line_items", total_line_items);
		newValues.put("created_at", create_at);
		newValues.put("updated_at", update_at);
		newValues.put("customer_name", customer_name);
		newValues.put("state_name", state_name);
		newValues.put("city_name", city_name);
		newValues.put("order_type", order_type);


		// Insert the row into your table
		db.insert("previous_orders", null, newValues);
	}
        
//        public void insertOrderProducts(String code, String name, String primary_category, String sub_category, 
//        		String product_variant, String retail_price, String mrp, String qualifying_qty, String free_qty, String status)
//		{
//	       ContentValues newValues = new ContentValues();
//			// Assign values for each row.
//			newValues.put("code", code);
//			newValues.put("name", name);
//			newValues.put("primary_category", primary_category);
//			newValues.put("sub_category", sub_category);
//			newValues.put("product_variant", product_variant);
//			newValues.put("retail_price", retail_price);
//			newValues.put("mrp", mrp);
//			newValues.put("qualifying_qty", qualifying_qty);
//			newValues.put("free_qty", free_qty);
//			newValues.put("status", status);
//			
//			// Insert the row into your table
//			db.insert("order_products", null, newValues);
//		}
        
        
        public void insertOrderProducts(String project_id, String customer_id, String order_id, String line_number, 
        		String category_id, String product_id, String product_variant_id, String product_pack_size_id, String scheme_id,
        		String billed_qty, String free_qty, String total_qty, String retail_price, String MRP, String amount, 
        		String create_at, String update_at,String customer_name,String scheme_amount,String item_number,String actual_discount,String product_name)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("order_id", order_id);
			newValues.put("line_number", line_number);
			newValues.put("category_id", category_id);
			newValues.put("product_id", product_id);
			newValues.put("product_variant_id", product_variant_id);
			newValues.put("product_pack_size_id", product_pack_size_id);
			newValues.put("scheme_id", scheme_id);
			newValues.put("billed_qty", billed_qty);
			newValues.put("free_qty", free_qty);
			newValues.put("total_qty", total_qty);
			newValues.put("retail_price", retail_price);
			newValues.put("MRP", MRP);
			newValues.put("amount", amount);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("customer_name", customer_name);
			newValues.put("scheme_amount", scheme_amount);
			newValues.put("item_number", item_number);
			newValues.put("actual_discount", actual_discount);
			newValues.put("product_name", product_name);
			// Insert the row into your table
			db.insert("order_products", null, newValues);
		}

	public void insertPreviousOrderProducts(String project_id, String customer_id, String order_id, String line_number,
									String category_id, String product_id, String product_variant_id, String product_pack_size_id, String scheme_id,
									String billed_qty, String free_qty, String total_qty, String retail_price, String MRP, String amount,
									String create_at, String update_at,String customer_name,String scheme_amount,String item_number,String actual_discount,String product_name)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("project_id", project_id);
		newValues.put("customer_id", customer_id);
		newValues.put("order_id", order_id);
		newValues.put("line_number", line_number);
		newValues.put("category_id", category_id);
		newValues.put("product_id", product_id);
		newValues.put("product_variant_id", product_variant_id);
		newValues.put("product_pack_size_id", product_pack_size_id);
		newValues.put("scheme_id", scheme_id);
		newValues.put("billed_qty", billed_qty);
		newValues.put("free_qty", free_qty);
		newValues.put("total_qty", total_qty);
		newValues.put("retail_price", retail_price);
		newValues.put("MRP", MRP);
		newValues.put("amount", amount);
		newValues.put("created_at", create_at);
		newValues.put("updated_at", update_at);
		newValues.put("customer_name", customer_name);
		newValues.put("scheme_amount", scheme_amount);
		newValues.put("item_number", item_number);
		newValues.put("actual_discount", actual_discount);
		newValues.put("product_name", product_name);
		// Insert the row into your table
		db.insert("previous_order_products", null, newValues);
	}
        
        public void insertNoOrder(String project_id, String customer_id, String retailer_id, String order_id, 
        		String user_id, String city_id, String beat_id, String latlon, String sync,
        		String reason_id, String create_at, String update_at, String reason_code, String reason_type, String latitude, String longitude,String order_number)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("order_id", order_id);
			newValues.put("user_id", user_id);
			newValues.put("city_id", city_id);
			newValues.put("beat_id", beat_id);
			newValues.put("latlon", latlon);
			newValues.put("sync", sync);
			newValues.put("reason_id", reason_id);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("reason_code", reason_code);
			newValues.put("reason_type", reason_type);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("order_number", order_number);
			
			// Insert the row into your table
			db.insert("no_orders", null, newValues);
		}
        
        public void insertReturnOrder(String project_id, String customer_id, String retailer_id, String order_id,
        		String user_id, String city_id, String beat_id, String latlon, String sync, String signature_path,
        		String distributor_id, String create_at, String update_at)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("order_id", order_id);
			newValues.put("user_id", user_id);
			newValues.put("city_id", city_id);
			newValues.put("beat_id", beat_id);
			newValues.put("latlon", latlon);
			newValues.put("sync", sync);
			newValues.put("signature_path", signature_path);
			newValues.put("distributor_id", distributor_id);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);

			// Insert the row into your table
			db.insert("return_orders", null, newValues);
		}

        public void insertReturnOrderProduct(String project_id, String customer_id, String order_id,
        		String category_id, String product_id, String product_variant_id, String product_pack_size_id,
        		String total_return_qty, String create_at, String update_at)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("order_id", order_id);
			newValues.put("category_id", category_id);
			newValues.put("product_id", product_id);
			newValues.put("product_variant_id", product_variant_id);
			newValues.put("product_pack_size_id", product_pack_size_id);
			newValues.put("total_return_qty", total_return_qty);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);

			// Insert the row into your table
			db.insert("return_order_products", null, newValues);
		}
        
//        public void insertPreviousOrders(String project_id, String customer_id, String retailer_id, String order_id,
//        		String user_id, String city_id, String beat_id, String latlon, String sync, String total_order_amount, String signature_path,
//        		String distributor_id, String create_at, String update_at)
//		{
//	       ContentValues newValues = new ContentValues();
//			// Assign values for each row.
//			newValues.put("project_id", project_id);
//			newValues.put("customer_id", customer_id);
//			newValues.put("retailer_id", retailer_id);
//			newValues.put("order_id", order_id);
//			newValues.put("user_id", user_id);
//			newValues.put("city_id", city_id);
//			newValues.put("beat_id", beat_id);
//			newValues.put("latlon", latlon);
//			newValues.put("sync", sync);
//			newValues.put("total_order_amount", total_order_amount);
//			newValues.put("signature_path", signature_path);
//			newValues.put("distributor_id", distributor_id);
//			newValues.put("created_at", create_at);
//			newValues.put("updated_at", update_at);
//
//			// Insert the row into your table
//			db.insert("previous_orders", null, newValues);
//		}
        
//        public void insertPreviousOrderProducts(String project_id, String customer_id, String order_id,
//        		String category_id, String product_id, String product_variant_id, String product_pack_size_id, String scheme_id, String billed_qty, String free_qty,
//        		String total_qty, String retail_price, String MRP, String amount, String create_at, String update_at)
//		{
//	       ContentValues newValues = new ContentValues();
//			// Assign values for each row.
//			newValues.put("project_id", project_id);
//			newValues.put("customer_id", customer_id);
//			newValues.put("order_id", order_id);
//			newValues.put("category_id", category_id);
//			newValues.put("product_id", product_id);
//			newValues.put("product_variant_id", product_variant_id);
//			newValues.put("product_pack_size_id", product_pack_size_id);
//			newValues.put("scheme_id", scheme_id);
//			newValues.put("billed_qty", billed_qty);
//			newValues.put("free_qty", free_qty);
//			newValues.put("total_qty", total_qty);
//			newValues.put("retail_price", retail_price);
//			newValues.put("MRP", MRP);
//			newValues.put("amount", amount);
//			newValues.put("created_at", create_at);
//			newValues.put("updated_at", update_at);
//
//			// Insert the row into your table
//			db.insert("previous_order_products", null, newValues);
//		}
        
        public void insertCalenderEntries(String project_id, String customer_id, String user_id, 
        		String calender_id, String type, String from_date, String to_date, String details, String latlon, 
        		String create_at, String update_at,String latitude,String longitude,String delete_flag)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("user_id", user_id);
			newValues.put("calender_id", calender_id);
			newValues.put("type", type);
			newValues.put("from_date", from_date);
			newValues.put("to_date", to_date);
			newValues.put("details", details);
			newValues.put("latlon", latlon);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("delete_flag", delete_flag);
			// Insert the row into your table
			db.insert("calender_entries", null, newValues);
		}
        
        public void insertCalenderListVies(String calender_id, String calender_date, String type, 
        		String details, String create_at, String update_at)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("calender_id", calender_id);
			newValues.put("calender_date", calender_date);
			newValues.put("type", type);
			newValues.put("details", details);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			
			// Insert the row into your table
			db.insert("calender_list_vies", null, newValues);
		}
        
        public void insertExpenceTravels(String project_id, String customer_id, String user_id, 
        		String expense_id, String travel_from, String travel_to, String travel_date, String travel_mode, String travel_cost, String travel_text,
        		String approved_amount, String approved, String approved_by, String approve_text, String create_at, String update_at,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("user_id", user_id);
			newValues.put("expense_id", expense_id);
			newValues.put("travel_from", travel_from);
			newValues.put("travel_to", travel_to);
			newValues.put("travel_date", travel_date);
			newValues.put("travel_mode", travel_mode);
			newValues.put("travel_cost", travel_cost);
			newValues.put("travel_text", travel_text);
			newValues.put("approved_amount", approved_amount);
			newValues.put("approved", approved);
			newValues.put("approved_by", approved_by);
			newValues.put("approve_text", approve_text);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("code", code);
			
			// Insert the row into your table
			db.insert("expenses_travels", null, newValues);
		}
        
        public void insertExpencesMiscs(String project_id, String customer_id, String user_id, 
        		String expense_id, String misc_date, String misc_amount, String misc_text, String approved_amount, String approved, String approved_by,
        		String approve_text, String create_at, String update_at,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("user_id", user_id);
			newValues.put("expense_id", expense_id);
			newValues.put("misc_date", misc_date);
			newValues.put("misc_amount", misc_amount);
			newValues.put("misc_text", misc_text);
			newValues.put("approved_amount", approved_amount);
			newValues.put("approved", approved);
			newValues.put("approved_by", approved_by);
			newValues.put("approve_text", approve_text);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("code", code);
			
			// Insert the row into your table
			db.insert("expenses_miscs", null, newValues);
		}
        
//        public void insertTargets(String project_id, String customer_id, String user_id, 
//        		String year, String month, String target, String achieved, 
//        		String create_at, String update_at)
//		{
//	       ContentValues newValues = new ContentValues();
//			// Assign values for each row.
//			newValues.put("project_id", project_id);
//			newValues.put("customer_id", customer_id);
//			newValues.put("user_id", user_id);
//			newValues.put("year", year);
//			newValues.put("month", month);
//			newValues.put("target", target);
//			newValues.put("achieved", achieved);
//			newValues.put("created_at", create_at);
//			newValues.put("updated_at", update_at);
//			
//			// Insert the row into your table
//			db.insert("targets", null, newValues);
//		}
        
        
        public void insertTargets(String project_id, String customer_id, String user_id, 
        		String year, String month, String target, String achieved, 
        		String create_at, String update_at)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("user_id", user_id);
			newValues.put("year", year);
			newValues.put("month", month);
			newValues.put("target", target);
			newValues.put("achieved", achieved);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			
			
			// Insert the row into your table
			db.insert("targets", null, newValues);
		}
        
        public void insertCustomer_Service_Feedbacks(String project_id, String customer_id, String retailer_id ,String user_id, 
        		String date, String text, String status, String HO_action_date, String HO_action_by, String Ho_comment,
        		String create_at, String update_at,String latitude,String longitude,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("date", date);
			newValues.put("text", text);
			newValues.put("status", status);
			newValues.put("HO_action_date", HO_action_date);
			newValues.put("HO_action_by", HO_action_by);
			newValues.put("Ho_comment", Ho_comment);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("code", code);
			
			// Insert the row into your table
			db.insert("customer_service_feedbacks", null, newValues);
		}
        
        public void insertCustomer_Service_Complaints(String project_id, String customer_id, String retailer_id ,String user_id, 
        		String date, String text, String status, String create_at, String update_at,String latitude,String longitude,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("date", date);
			newValues.put("text", text);
			newValues.put("status", status);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("code", code);
			
			// Insert the row into your table
			db.insert("customer_service_complaints", null, newValues);
		}
        
        public void insertComplaintAction(String complaint_id, String HO_action_date, String HO_action_by, 
        		String Ho_comment, String status, String create_at, String update_at)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("complaint_id", complaint_id);
			newValues.put("HO_action_date", HO_action_date);
			newValues.put("HO_action_by", HO_action_by);
			newValues.put("Ho_comment", Ho_comment);
			newValues.put("status", status);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			
			// Insert the row into your table
			db.insert("complaint_actions", null, newValues);
		}
        
        public void insertCustomerServiceClaims(String project_id, String customer_id, String type, String retailer_id,
        		String user_id, String date, String text, String amount, String status, String balance, String create_at, String update_at,String latitude,String longitude,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("type", type);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("date", date);
			newValues.put("text", text);
			newValues.put("amount", amount);
			newValues.put("status", status);
			newValues.put("balance", balance);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("code", code);
			
			// Insert the row into your table
			db.insert("customer_service_claims", null, newValues);
		}
        
        public void insertClaim_Actions(String customer_service_claim_action, String claim_amount_approved, String claim_amount_rejected, String HO_action_date,
        		String HO_action_by, String HO_comment, String status, String create_at, String update_at)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("customer_service_claim_action", customer_service_claim_action);
			newValues.put("claim_amount_approved", claim_amount_approved);
			newValues.put("claim_amount_rejected", claim_amount_rejected);
			newValues.put("HO_action_date", HO_action_date);
			newValues.put("HO_action_by", HO_action_by);
			newValues.put("HO_comment", HO_comment);
			newValues.put("status", status);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			
			// Insert the row into your table
			db.insert("claim_actions", null, newValues);
		}
        
        public void insertCustomerServiceCompetitionStock(String project_id, String customer_id, String retailer_id, String user_id,
        		String category_id, String product_id, String product_variant_id, String product_pack_size_id, String competition_product_name, String competition_product_quantity, String competition_product_text, String create_at, String update_at,String latitude,String longitude,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("category_id", category_id);
			newValues.put("product_id", product_id);
			newValues.put("product_variant_id", product_variant_id);
			newValues.put("product_pack_size_id", product_pack_size_id);
			newValues.put("competition_product_name", competition_product_quantity);
			newValues.put("competition_product_text", competition_product_text);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("code", code);
			
			// Insert the row into your table
			db.insert("customer_service_competition_stocks", null, newValues);
		}
        
        public void insertCustomerServiceMedia(String project_id, String customer_id, String media_type, String retailer_id, String user_id,
        		String location, String media_text, String create_at, String update_at,String latitude,String longitude,String media_id,String code)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("media_type", media_type);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("location", location);
			newValues.put("media_text", media_text);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("media_id", media_id);
			newValues.put("code", code);
			// Insert the row into your table
			db.insert("customer_service_media", null, newValues);
		}
        
        
        public void insertDeliverySchedule(String project_id, String customer_id, String retailer_id, String order_id, String sales_person, String user_id,
        		String depatch_date, String delivery_date, String order_amount, String accepted_payment_mode, String total_line_items, String collected_amount, String outstanding_amount, String create_at, String update_at,String c_address)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("order_id", order_id);
			newValues.put("sales_person", sales_person);
			newValues.put("user_id", user_id);
			newValues.put("depatch_date", depatch_date);
			newValues.put("delivery_date", delivery_date);
			newValues.put("order_amount", order_amount);
			newValues.put("accepted_payment_mode", accepted_payment_mode);
			newValues.put("total_line_items", total_line_items);
			newValues.put("collected_amount", collected_amount);
			newValues.put("outstanding_amount", outstanding_amount);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("c_address", c_address);
			
			// Insert the row into your table
			db.insert("delivery_schedules", null, newValues);
		}
        
       
        
        public void insertDeliveryProducts(String project_id, String customer_id, String order_id, String line_item_id, String category_id,
        		String product_id, String product_variant_id, String product_pack_size_id, String order_quantity, String delivered_quality,
        		String truck_number, String transporter_details, String create_at, String update_at,String product_name)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("order_id", order_id);
			newValues.put("line_item_id", line_item_id);
			newValues.put("category_id", category_id);
			newValues.put("product_id", product_id);
			newValues.put("product_variant_id", product_variant_id);
			newValues.put("product_pack_size_id", product_pack_size_id);
			newValues.put("order_quantity", order_quantity);
			newValues.put("delivered_quality", delivered_quality);
			newValues.put("truck_number", truck_number);
			newValues.put("transporter_details", transporter_details);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("product_name", product_name);
			
			// Insert the row into your table
			db.insert("delivery_products", null, newValues);
		}
        
        public void insertCreditLimit(String project_id, String customer_id, String retailer_id, String created_by, 
        		String credit_limit, String created_at,String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
           newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("retailer_id", retailer_id);
      		newValues.put("created_by", created_by);
      		newValues.put("credit_limit", credit_limit);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("credit_limits", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertOutstandings(String project_id, String customer_id, String retailer_id, String user_id, String order_id,String order_amount, String delivery_status, String amount_collected, String payment_status, String amount_outstanding, String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
           newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("retailer_id", retailer_id);
      		newValues.put("user_id", user_id);
      		newValues.put("order_id", order_id);
      		newValues.put("order_amount", order_amount);
      		newValues.put("delivery_status", delivery_status);
      		newValues.put("amount_collected", amount_collected);
      		newValues.put("payment_status", payment_status);
      		newValues.put("amount_outstanding", amount_outstanding);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("outstandings", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertInvoices(String project_id, String customer_id, String retailer_id, String created_by, String order_id,
        		String date, String tax_name_1, String tax_name_2, String tax_name_3, String tax_name_4, String tax_name_5, String tax_amount_1, String tax_amount_2, String tax_amount_3, String tax_amount_4, String tax_amount_5, String total, String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("retailer_id", retailer_id);
      		newValues.put("created_by", created_by);
      		newValues.put("order_id", order_id);
      		newValues.put("date", date);
      		newValues.put("tax_name_1", tax_name_1);
      		newValues.put("tax_name_2", tax_name_2);
      		newValues.put("tax_name_3", tax_name_3);
      		newValues.put("tax_name_4", tax_name_4);
      		newValues.put("tax_name_5", tax_name_5);
      		newValues.put("tax_amount_1", tax_amount_1);
      		newValues.put("tax_amount_2", tax_amount_2);
      		newValues.put("tax_amount_3", tax_amount_3);
      		newValues.put("tax_amount_4", tax_amount_4);
      		newValues.put("tax_amount_5", tax_amount_5);
      		newValues.put("total", total);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("invoices", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertSyncHistories(String project_id, String customer_id, String user_id, String tables_synced, 
        		String latlon, String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("user_id", user_id);
      		newValues.put("tables_synced", tables_synced);
      		newValues.put("latlon", latlon);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("sync_histories", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertSurveys(String project_id, String customer_id, String q1, String q2, String q3, String q4, String q5, String q6, 
        		String q7, String q8, String q9, String q10, String start_date, String expiry_date, String status, String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("q1", q1);
      		newValues.put("q2", q2);
      		newValues.put("q3", q3);
      		newValues.put("q4", q4);
      		newValues.put("q5", q5);
      		newValues.put("q6", q6);
      		newValues.put("q7", q7);
      		newValues.put("q8", q8);
      		newValues.put("q9", q9);
      		newValues.put("q10", q10);
      		newValues.put("start_date", start_date);
      		newValues.put("expiry_date", expiry_date);
      		newValues.put("status", status);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("surveys", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertSurveyResults(String project_id, String customer_id, String user_id, String retailer_id, String a1, String a2, String a3, String a4, String a5, String a6, 
        		String a7, String a8, String a9, String a10, String ans_date, String ans_GPS, String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("user_id", user_id);
      		newValues.put("retailer_id", retailer_id);
      		newValues.put("a1", a1);
      		newValues.put("a2", a2);
      		newValues.put("a3", a3);
      		newValues.put("a4", a4);
      		newValues.put("a5", a5);
      		newValues.put("a6", a6);
      		newValues.put("a7", a7);
      		newValues.put("a8", a8);
      		newValues.put("a9", a9);
      		newValues.put("a10", a10);
      		newValues.put("ans_date", ans_date);
      		newValues.put("ans_GPS", ans_GPS);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("survey_results", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertNewLaunches(String project_id, String customer_id, String date, String media_path1, String media_path2, String media_path3, String media_path4, String media_path5, String media_path6, String media_path7,
        		String media_path8, String media_path9, String media_path10, String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("date", date);
      		newValues.put("media_path1", media_path1);
      		newValues.put("media_path2", media_path2);
      		newValues.put("media_path3", media_path3);
      		newValues.put("media_path4", media_path4);
      		newValues.put("media_path5", media_path5);
      		newValues.put("media_path6", media_path6);
      		newValues.put("media_path7", media_path7);
      		newValues.put("media_path8", media_path8);
      		newValues.put("media_path9", media_path9);
      		newValues.put("media_path10", media_path10);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("new_launches", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public void insertAdevertisements(String project_id, String customer_id, String date, String media_path, 
        		String created_at, String updated_at)
      	 { 
           ContentValues newValues = new ContentValues();
      		// Assign values for each row.
            newValues.put("project_id", project_id);
      		newValues.put("customer_id", customer_id);
      		newValues.put("date", date);
      		newValues.put("media_path", media_path);
      		newValues.put("created_at", created_at);
      		newValues.put("updated_at", updated_at);
      		     		   		
      		// Insert the row into your table
      		db.insert("advertisements", null, newValues);
      		//Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
      	}
        
        public int deleteEntry(String username)
		{
			//String id=String.valueOf(ID);
		    String where="username=?";
		    int numberOFEntriesDeleted= db.delete("users", where, new String[]{username}) ;
	       // Toast.makeText(context, "Number of Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
	        return numberOFEntriesDeleted;
		}
        
		public String getSinlgeEntry(String username)
		{
			Cursor cursor=db.query("users", null, " username=?", new String[]{username}, null, null, null);
	        if(cursor.getCount()<1) // UserName Not Exist
	        {
	           cursor.close();
	           return "NOT EXIST";
	        }
		    cursor.moveToFirst();
			String password= cursor.getString(cursor.getColumnIndex("password"));
			//Toast.makeText(context, "Password : "+password, Toast.LENGTH_LONG).show();
			cursor.close();
			return password;	
		}
		
		public void updateAdevertisements(String project_id, String customer_id, String date, String media_path, 
        		String created_at, String updated_at)
      	 { 
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("date", date);
			updatedValues.put("media_path", media_path);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("advertisements",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateNewLaunches(String project_id, String customer_id, String date, String media_path1, String media_path2, String media_path3, String media_path4, String media_path5, String media_path6, String media_path7,
        		String media_path8, String media_path9, String media_path10, String created_at, String updated_at)
      	 { 
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("date", date);
			updatedValues.put("media_path1", media_path1);
			updatedValues.put("media_path2", media_path2);
			updatedValues.put("media_path3", media_path3);
			updatedValues.put("media_path4", media_path4);
			updatedValues.put("media_path5", media_path5);
			updatedValues.put("media_path6", media_path6);
			updatedValues.put("media_path7", media_path7);
			updatedValues.put("media_path8", media_path8);
			updatedValues.put("media_path9", media_path9);
			updatedValues.put("media_path10",media_path10);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("new_launches",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateSurveyResults(String project_id, String customer_id, String user_id, String retailer_id, String a1, String a2, String a3, String a4, String a5, String a6, 
        		String a7, String a8, String a9, String a10, String ans_date, String ans_GPS, String created_at, String updated_at)
      	 { 
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("a1", a1);
			updatedValues.put("a2", a2);
			updatedValues.put("a3", a3);
			updatedValues.put("a4", a4);
			updatedValues.put("a5", a5);
			updatedValues.put("a6", a6);
			updatedValues.put("a7", a7);
			updatedValues.put("a8", a8);
			updatedValues.put("a9", a9);
			updatedValues.put("a10", a10);
			updatedValues.put("ans_date", ans_date);
			updatedValues.put("ans_GPS", ans_GPS);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("survey_results",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateSurveys(String project_id, String customer_id, String q1, String q2, String q3,
        		String q4, String q5, String q6, String q7, String q8, String q9, String q10, String start_date, String expiry_date, String status, String created_at, String updated_at)
      	 { 
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("q1", q1);
			updatedValues.put("q2", q2);
			updatedValues.put("q3", q3);
			updatedValues.put("q4", q4);
			updatedValues.put("q5", q5);
			updatedValues.put("q6", q6);
			updatedValues.put("q7", q7);
			updatedValues.put("q8", q8);
			updatedValues.put("q9", q9);
			updatedValues.put("q10", q10);
			updatedValues.put("start_date", start_date);
			updatedValues.put("expiry_date", expiry_date);
			updatedValues.put("status", status);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("surveys",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateSyncHistories(String project_id, String customer_id, String user_id, String tables_synced, 
        		String latlon, String created_at,String updated_at)
      	 { 
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("tables_synced", tables_synced);
			updatedValues.put("latlon", latlon);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("sync_histories",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateInvoices(String project_id, String customer_id, String retailer_id, String created_by, String order_id,
        		String date, String tax_name_1, String tax_name_2, String tax_name_3, String tax_name_4, String tax_name_5, String tax_amount_1, String tax_amount_2, String tax_amount_3, String tax_amount_4, String tax_amount_5, String total, String created_at, String updated_at)
      	 { 
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("created_by", created_by);
			updatedValues.put("order_id", order_id);
			updatedValues.put("date", date);
			updatedValues.put("tax_name_1", tax_name_1);
			updatedValues.put("tax_name_2", tax_name_2);
			updatedValues.put("tax_name_3", tax_name_3);
			updatedValues.put("tax_name_4", tax_name_4);
			updatedValues.put("tax_name_5", tax_name_5);
			updatedValues.put("tax_amount_1", tax_amount_1);
			updatedValues.put("tax_amount_2", tax_amount_2);
			updatedValues.put("tax_amount_3", tax_amount_3);
			updatedValues.put("tax_amount_4", tax_amount_4);
			updatedValues.put("tax_amount_5", tax_amount_5);
			updatedValues.put("total", total);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("outstandings",updatedValues, where, new String[]{project_id});			   
		}
		
		 public void updateOutstandings(String project_id, String customer_id, String retailer_id, String user_id, String order_id,
	        		String order_amount, String delivery_status, String amount_collected, String payment_status, String amount_outstanding, String created_at, String updated_at)
	      	 { 
				// Define the updated row content.
				ContentValues updatedValues = new ContentValues();
				// Assign values for each row.
				updatedValues.put("project_id", project_id);
				updatedValues.put("customer_id", customer_id);
				updatedValues.put("retailer_id", retailer_id);
				updatedValues.put("user_id", user_id);
				updatedValues.put("order_id", order_id);
				updatedValues.put("order_amount", order_amount);
				updatedValues.put("delivery_status", delivery_status);
				updatedValues.put("amount_collected", amount_collected);
				updatedValues.put("payment_status", payment_status);
				updatedValues.put("amount_outstanding", amount_outstanding);
				updatedValues.put("created_at", created_at);
				updatedValues.put("updated_at", updated_at);
				
				String where="project_id = ?";
			    db.update("outstandings",updatedValues, where, new String[]{project_id});			   
			}
		
		 public void updateCreditLimit(String project_id, String customer_id, String retailer_id, String created_by, 
	        		String credit_limit, String created_at,String updated_at)
	      	 { 
				// Define the updated row content.
				ContentValues updatedValues = new ContentValues();
				// Assign values for each row.
				updatedValues.put("project_id", project_id);
				updatedValues.put("customer_id", customer_id);
				updatedValues.put("retailer_id", retailer_id);
				updatedValues.put("created_by", created_by);
				updatedValues.put("credit_limit", credit_limit);
				updatedValues.put("created_at", created_at);
				updatedValues.put("updated_at", updated_at);
				
				String where="project_id = ?";
			    db.update("credit_limits",updatedValues, where, new String[]{project_id});			   
			}
		
		public void updateDeliveryProducts(String project_id, String customer_id, String order_id, String line_item_id, String category_id,
        		String product_id, String product_variant_id, String product_pack_size_id, String order_quantity, String delivered_quality,
        		String truck_number, String transporter_details, String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("line_item_id", line_item_id);
			updatedValues.put("category_id", category_id);
			updatedValues.put("product_id", product_id);
			updatedValues.put("product_variant_id", product_variant_id);
			updatedValues.put("product_pack_size_id", product_pack_size_id);
			updatedValues.put("order_quantity", order_quantity);
			updatedValues.put("delivered_quality", delivered_quality);
			updatedValues.put("truck_number", truck_number);
			updatedValues.put("transporter_details", transporter_details);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("delivery_products",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateDeliverySchedule(String project_id, String customer_id, String retailer_id, String order_id, String sales_person, String user_id,
        		String depatch_date, String delivery_date, String order_amount, String accepted_payment_mode, String total_line_items, String collected_amount, String outstanding_amount, String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("sales_person", sales_person);
			updatedValues.put("user_id", user_id);
			updatedValues.put("depatch_date", depatch_date);
			updatedValues.put("delivery_date", delivery_date);
			updatedValues.put("order_amount", order_amount);
			updatedValues.put("accepted_payment_mode", accepted_payment_mode);
			updatedValues.put("total_line_items", total_line_items);
			updatedValues.put("collected_amount", collected_amount);
			updatedValues.put("outstanding_amount", outstanding_amount);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("delivery_schedules",updatedValues, where, new String[]{project_id});			   
		}
		
        public void updateCustomerServiceMedia(String project_id, String customer_id, String media_type, String retailer_id, String user_id,
        		String location, String media_text, String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("media_type", media_type);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("location", location);
			updatedValues.put("media_text", media_text);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("customer_service_media",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateCustomerServiceCompetitionStock(String project_id, String customer_id, String retailer_id, String user_id,
        		String category_id, String product_id, String product_variant_id, String product_pack_size_id, String competition_product_name, String competition_product_quantity, String competition_product_text, String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("category_id", category_id);
			updatedValues.put("product_id", product_id);
			updatedValues.put("product_variant_id", product_variant_id);
			updatedValues.put("product_pack_size_id", product_pack_size_id);
			updatedValues.put("competition_product_name", competition_product_name);
			updatedValues.put("competition_product_quantity", competition_product_quantity);
			updatedValues.put("competition_product_text", competition_product_text);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("customer_service_competition_stocks",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateClaimActions(String customer_service_claim_action, String claim_amount_approved, String claim_amount_rejected, String HO_action_date,
        		String HO_action_by, String HO_comment, String status, String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("customer_service_claim_action", customer_service_claim_action);
			updatedValues.put("claim_amount_approved", claim_amount_approved);
			updatedValues.put("claim_amount_rejected", claim_amount_rejected);
			updatedValues.put("HO_action_date", HO_action_date);
			updatedValues.put("HO_action_by", HO_action_by);
			updatedValues.put("HO_comment", HO_comment);
			updatedValues.put("status", status);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="customer_service_claim_action = ?";
		    db.update("claim_actions",updatedValues, where, new String[]{customer_service_claim_action});			   
		}
		
		public void updateCustomer_Service_Claims(String project_id, String customer_id, String type ,String retailer_id, 
        		String user_id, String date, String text, String amount, String status, String balance,
        		String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("type", type);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("date", date);
			updatedValues.put("text", text);
			updatedValues.put("amount", amount);
			updatedValues.put("status", status);
			updatedValues.put("balance", balance);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("customer_service_claims",updatedValues, where, new String[]{project_id});			   
		}
				
		 public void updateComplaintAction(String complaint_id, String HO_action_date, String HO_action_by, 
	        		String Ho_comment, String status, String create_at, String update_at)
			{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("complaint_id", complaint_id);
			updatedValues.put("HO_action_date", HO_action_date);
			updatedValues.put("HO_action_by", HO_action_by);
			updatedValues.put("Ho_comment", Ho_comment);
			updatedValues.put("status", status);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="complaint_id = ?";
		    db.update("complaint_actions",updatedValues, where, new String[]{complaint_id});			   
		}
		
		public void updateCustomer_Service_Complaints(String project_id, String customer_id, String retailer_id ,String user_id, 
        		String date, String text, String status, String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("date", date);
			updatedValues.put("text", text);
			updatedValues.put("status", status);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("customer_service_complaints",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateCustomer_Service_Feedbacks(String project_id, String customer_id, String retailer_id ,String user_id, 
        		String date, String text, String status, String HO_action_date, String HO_action_by, String Ho_comment,
        		String create_at, String update_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("date", date);
			updatedValues.put("text", text);
			updatedValues.put("status", status);
			updatedValues.put("HO_action_date", HO_action_date);
			updatedValues.put("HO_action_by", HO_action_by);
			updatedValues.put("Ho_comment", Ho_comment);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			
			String where="project_id = ?";
		    db.update("customer_service_feedbacks",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateTarget(String project_id, String customer_id, String user_id, String year, 
				String month, String target, String achieved, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("year", year);
			updatedValues.put("month", month);
			updatedValues.put("target", target);
			updatedValues.put("achieved", achieved);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("targets",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateExpencesMiscs(String project_id, String customer_id, String user_id, String expense_id, 
				String misc_date, String misc_amount, String misc_text, String approved_amount, String approved, String approved_by, String approve_text, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("expense_id", expense_id);
			updatedValues.put("misc_date", misc_date);
			updatedValues.put("misc_amount", misc_amount);
			updatedValues.put("misc_text", misc_text);
			updatedValues.put("approved_amount", approved_amount);
			updatedValues.put("approved", approved);
			updatedValues.put("approved_by", approved_by);
			updatedValues.put("approve_text", approve_text);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("expenses_miscs",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateExpenceTravels(String project_id, String customer_id, String user_id, String expense_id, 
				String travel_from, String travel_to, String travel_date, String travel_mode, String travel_cost, String travel_text, String approved_amount, String approved, String approved_by, String approve_text, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("expense_id", expense_id);
			updatedValues.put("travel_from", travel_from);
			updatedValues.put("travel_to", travel_to);
			updatedValues.put("travel_date", travel_date);
			updatedValues.put("travel_mode", travel_mode);
			updatedValues.put("travel_cost", travel_cost);
			updatedValues.put("travel_text", travel_text);
			updatedValues.put("approved_amount", approved_amount);
			updatedValues.put("approved", approved);
			updatedValues.put("approved_by", approved_by);
			updatedValues.put("approve_text", approve_text);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("expenses_travels",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateCalenderListVies(String calender_id, String calender_date, String type, String details, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("calender_id", calender_id);
			updatedValues.put("calender_date", calender_date);
			updatedValues.put("type", type);
			updatedValues.put("details", details);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="calender_id = ?";
		    db.update("calender_list_vies",updatedValues, where, new String[]{calender_id});			   
		}
		
		
		public void updateCalenderEntries(String project_id, String customer_id, String user_id, String calender_id, 
				String type, String from_date, String to_date, String details, String latlon, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("calender_id", calender_id);
			updatedValues.put("type", type);
			updatedValues.put("from_date", from_date);
			updatedValues.put("to_date", to_date);
			updatedValues.put("details", details);
			updatedValues.put("latlon", latlon);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("calender_entries",updatedValues, where, new String[]{project_id});			   
		}
		
		
		public void updatePreviousOrderProducts(String project_id, String customer_id, String order_id, String category_id, 
				String product_id, String product_variant_id, String product_pack_size_id, String scheme_id, String billed_qty, String free_qty, String total_qty, String retail_price, String MRP, String amount, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("category_id", category_id);
			updatedValues.put("product_id", product_id);
			updatedValues.put("product_variant_id", product_variant_id);
			updatedValues.put("product_pack_size_id", product_pack_size_id);
			updatedValues.put("scheme_id", scheme_id);
			updatedValues.put("billed_qty", billed_qty);
			updatedValues.put("free_qty", free_qty);
			updatedValues.put("total_qty", total_qty);
			updatedValues.put("retail_price", retail_price);
			updatedValues.put("MRP", MRP);
			updatedValues.put("amount", amount);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("previous_order_products",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updatePreviousOrders(String project_id, String customer_id, String retailer_id, String order_id, String user_id, 
				String city_id, String beat_id, String latlon, String sync, String total_order_amount, String signature_path, String distributor_id, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("city_id", city_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("latlon", latlon);
			updatedValues.put("sync", sync);
			updatedValues.put("total_order_amount", total_order_amount);
			updatedValues.put("signature_path", signature_path);
			updatedValues.put("distributor_id", distributor_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("previous_orders",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateReturnOrderProduct(String project_id, String customer_id, String order_id, String category_id, 
				String product_id, String product_variant_id, String product_pack_size_id, String total_return_qty, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("order_id", order_id);
		    updatedValues.put("category_id", category_id);
			updatedValues.put("product_id", product_id);
			updatedValues.put("product_variant_id", product_variant_id);
			updatedValues.put("product_pack_size_id", product_pack_size_id);
			updatedValues.put("total_return_qty", total_return_qty);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("return_order_products",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateReturnOrder(String project_id, String customer_id, String retailer_id, String order_id, String user_id, 
				String city_id, String beat_id, String latlon, String sync, String signature_path, String distributor_id, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("city_id", city_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("latlon", latlon);
			updatedValues.put("sync", sync);
			updatedValues.put("signature_path", signature_path);
			updatedValues.put("distributor_id", distributor_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("return_orders",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateNoOrder(String project_id, String customer_id, String retailer_id, String order_id, String user_id, 
				String city_id, String beat_id, String latlon, String sync, String reason_id, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("city_id", city_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("latlon", latlon);
			updatedValues.put("sync", sync);
			updatedValues.put("reason_id", reason_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("no_orders",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateOrderProducts(String project_id, String customer_id, String order_id, String line_number, String category_id, 
				String product_id, String product_variant_id, String product_pack_size_id, String scheme_id, String billed_qty, String free_qty,
				String total_qty, String retail_price, String MRP, String amount, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("order_id", order_id);
			updatedValues.put("line_number", line_number);
			updatedValues.put("category_id", category_id);
			updatedValues.put("product_id", product_id);
			updatedValues.put("product_variant_id", product_variant_id);
			updatedValues.put("product_pack_size_id", product_pack_size_id);
			updatedValues.put("scheme_id", scheme_id);
			updatedValues.put("billed_qty", billed_qty);
			updatedValues.put("free_qty", free_qty);
			updatedValues.put("total_qty", total_qty);
			updatedValues.put("retail_price", retail_price);
			updatedValues.put("MRP", MRP);
			updatedValues.put("amount", amount);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("order_products",updatedValues, where, new String[]{project_id});			   
		}
		
		
		public void updateOrders(String project_id, String customer_id, String retailer_id, String user_id, String city_id, 
				String beat_id, String latlon, String sync, String total_order_amount, String signature_path, String distributor_id,
				String total_line_items, String created_at, String updated_at)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("retailer_id", retailer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("city_id", city_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("latlon", latlon);
			updatedValues.put("sync", sync);
			updatedValues.put("total_order_amount", total_order_amount);
			updatedValues.put("signature_path", signature_path);
			updatedValues.put("distributor_id", distributor_id);
			updatedValues.put("total_line_items", total_line_items);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			
			String where="project_id = ?";
		    db.update("orders",updatedValues, where, new String[]{project_id});			   
		}
		
		
		public void updateRetailers(String project_id, String customer_id, String code, String name, String shop_name, 
				String address, String street, String landmark, String state_id, String city_id, String beat_id,
				String pincode, String landline_no, String mobile_no, String email, String status, String created_by,
				String modified_by, String vatin, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("code", code);
			updatedValues.put("name", name);
			updatedValues.put("shop_name", shop_name);
			updatedValues.put("address", address);
			updatedValues.put("street", street);
			updatedValues.put("landmark", landmark);
			updatedValues.put("state_id", state_id);
			updatedValues.put("city_id", city_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("pincode", pincode);
			updatedValues.put("landline_no", landline_no);
			updatedValues.put("mobile_no", mobile_no);
			updatedValues.put("email", email);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("vatin", vatin);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("retailers",updatedValues, where, new String[]{project_id});			   
		}
		
//		public void updateDistributors(String project_id, String customer_id, String code, String name, String shop_name, 
//				String address1, String address2, String street, String landmark, String state_id, String city_id, String beat_id,
//				String pincode, String landline_no, String mobile_no, String email, String status, String created_by,
//				String modified_by, String vatin, String created_at, String updated_at, String ids)
//		{
//			// Define the updated row content.
//			ContentValues updatedValues = new ContentValues();
//			// Assign values for each row.
//			updatedValues.put("project_id", project_id);
//			updatedValues.put("customer_id", customer_id);
//			updatedValues.put("code", code);
//			updatedValues.put("name", name);
//			updatedValues.put("shop_name", shop_name);
//			updatedValues.put("address1", address1);
//			updatedValues.put("address2", address2);
//			
//			updatedValues.put("street", street);
//			updatedValues.put("landmark", landmark);
//			updatedValues.put("state_id", state_id);
//			updatedValues.put("city_id", city_id);
//			updatedValues.put("beat_id", beat_id);
//			updatedValues.put("pincode", pincode);
//			updatedValues.put("landline_no", landline_no);
//			
//			updatedValues.put("mobile_no", mobile_no);
//			updatedValues.put("email", email);
//			updatedValues.put("status", status);
//			
//			updatedValues.put("created_by", created_by);
//			updatedValues.put("modified_by", modified_by);
//			updatedValues.put("vatin", vatin);
//			updatedValues.put("created_at", created_at);
//			updatedValues.put("updated_at", updated_at);
//			updatedValues.put("ids", ids);
//			String where="project_id = ?";
//		    db.update("distributors",updatedValues, where, new String[]{project_id});			   
//		}
		
		public void updateProductPackSizes(String project_id, String customer_id, String product_variant_id, String desc, String product_unit, String product_pack_size, String status, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("product_variant_id", product_variant_id);
			updatedValues.put("desc", desc);
			updatedValues.put("product_unit", product_unit);
			updatedValues.put("product_pack_size", product_pack_size);
			updatedValues.put("status", status);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("product_pack_sizes",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateProductVarients(String project_id, String customer_id, String code, String desc, String product_id, String MRP, String retail_price, String product_unit, String status, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("code", code);
			updatedValues.put("desc", desc);
			updatedValues.put("product_id", product_id);
			updatedValues.put("MRP", MRP);
			updatedValues.put("retail_price", retail_price);
			updatedValues.put("product_unit", product_unit);
			updatedValues.put("status", status);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("product_variants",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateProduct(String project_id, String customer_id, String code, String desc, String category_id, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("code", code);
			updatedValues.put("desc", desc);
			updatedValues.put("category_id", category_id);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("products",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateProductCateg(String project_id, String customer_id, String category_id, String category_code, String category_desc, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("category_id", category_id);
			updatedValues.put("category_code", category_code);
			updatedValues.put("category_desc", category_desc);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("product_categories",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateBeatAlloc(String project_id, String customer_id, String date, String user_id, String beat_id, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("date", date);
			updatedValues.put("user_id", user_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("beat_allocations",updatedValues, where, new String[]{project_id});			   
		}
		
		
		public void updateBeats(String project_id, String customer_id, String date, String user_id, String beat_id, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("date", date);
			updatedValues.put("user_id", user_id);
			updatedValues.put("beat_id", beat_id);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("beats",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateCities(String project_id, String customer_id, String code, String name, String state_id, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("code", code);
			updatedValues.put("name", name);
			updatedValues.put("state_id", state_id);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("cities",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateStates(String project_id, String customer_id, String code, String name, String status, String created_by, String modified_by, String created_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("code", code);
			updatedValues.put("name", name);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("states",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateRegistration(String project_id, String customer_id, String user_id, String latlon, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("user_id", user_id);
			updatedValues.put("latlon", latlon);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("registrations",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateDevices(String project_id, String company_id, String code, String device_type, String model, String IMEINo, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("company_id", company_id);
			updatedValues.put("code", code);
			updatedValues.put("device_type", device_type);
			updatedValues.put("model", model);
			updatedValues.put("IMEINo", IMEINo);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("devices",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateRoles(String project_id, String company_id, String code, String desc, String status, String created_by, String modified_by, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("company_id", company_id);
			updatedValues.put("code", code);
			updatedValues.put("desc", desc);
			updatedValues.put("status", status);
			updatedValues.put("created_by", created_by);
			updatedValues.put("modified_by", modified_by);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("roles",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateBillTrans(String bill_id, String license_id, String project_id, String start_date, String end_date, String billable_no_of_users, String billing_amount, String taxes, String total, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("bill_id", bill_id);
			updatedValues.put("license_id", license_id);
			updatedValues.put("project_id", project_id);
			updatedValues.put("start_date", start_date);
			updatedValues.put("end_date", end_date);
			updatedValues.put("billable_no_of_users", billable_no_of_users);
			updatedValues.put("billing_amount", billing_amount);
			updatedValues.put("taxes", taxes);
			updatedValues.put("total", total);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="bill_id = ?";
		    db.update("billing_transactions",updatedValues, where, new String[]{bill_id});			   
		}
		
		public void updateLicense(String project_id, String stage, String type, String start_date, String end_date, String number_of_users, String number_of_instances, String billing_rate, String billing_cycle, String billing_type, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("project_id", project_id);
			updatedValues.put("stage", stage);
			updatedValues.put("type", type);
			updatedValues.put("start_date", start_date);
			updatedValues.put("end_date", end_date);
			updatedValues.put("number_of_users", start_date);
			updatedValues.put("number_of_instances", number_of_instances);
			updatedValues.put("billing_rate", billing_rate);
			updatedValues.put("billing_cycle", billing_cycle);
			updatedValues.put("billing_type", billing_type);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="project_id = ?";
		    db.update("licenses",updatedValues, where, new String[]{project_id});			   
		}
		
		public void updateCustPos(String order_id, String customer_order_reference, String order_date, String customer_id, String project_id, String created_at,String updated_at,String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("order_id", order_id);
			updatedValues.put("customer_order_reference", customer_order_reference);
			updatedValues.put("order_date", order_date);
			updatedValues.put("customer_id", customer_id);
			updatedValues.put("project_id", project_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="order_id = ?";
		    db.update("customer_pos",updatedValues, where, new String[]{order_id});			   
		}
		
		public void updateCompOffice(String company_id, String office_id, String office_type, String address1, String address2, String street, String landmark, String state_id, String city_id, String pincode, String board_no, String created_at, String updated_at, String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("company_id", company_id);
			updatedValues.put("office_id", office_id);
			updatedValues.put("office_type", office_type);
			updatedValues.put("address1", address1);
			updatedValues.put("address2", address2);
			updatedValues.put("street", street);
			updatedValues.put("landmark", landmark);
			updatedValues.put("state_id", state_id);
			updatedValues.put("city_id", city_id);
			updatedValues.put("pincode", pincode);
			updatedValues.put("board_no", board_no);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="company_id = ?";
		    db.update("company_offices",updatedValues, where, new String[]{company_id});			   
		}
		
		public void updateCompContact(String name, String type, String designation, String office_id, String mobile_no, String email_id, String created_at,String updated_at,String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("name", name);
			updatedValues.put("type", type);
			updatedValues.put("designation", designation);
			updatedValues.put("office_id", office_id);
			updatedValues.put("mobile_no", mobile_no);
			updatedValues.put("email_id", email_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="name = ?";
		    db.update("company_contacts",updatedValues, where, new String[]{name});			   
		}
		
		public void updateProjects(String name, String discription, String company_id, String created_at,String updated_at,String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("name", name);
			updatedValues.put("description", discription);
			updatedValues.put("company_id", company_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="name = ?";
		    db.update("projects",updatedValues, where, new String[]{name});			   
		}
		
		public void updateIndustVert(String discription,String created_at,String updated_at,String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("description", discription);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="description = ?";
		    db.update("industry_verticals",updatedValues, where, new String[]{discription});			   
		}
		
		public void updateCustomer(String name,String industry_vertical_id,String business_description,String created_at,String updated_at,String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("name", name);
			updatedValues.put("industry_vertical_id", industry_vertical_id);
			updatedValues.put("business_description", business_description);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
			String where="name = ?";
		    db.update("customers",updatedValues, where, new String[]{name});			   
		}
		
		public void updateTasks(String from_date,String to_date,String details,String user_id,String created_at,String updated_at,String ids)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("from_date", from_date);
			updatedValues.put("to_date", to_date);
			updatedValues.put("details", details);
			updatedValues.put("user_id", user_id);
			updatedValues.put("created_at", created_at);
			updatedValues.put("updated_at", updated_at);
			updatedValues.put("ids", ids);
						
			String where="from_date = ?";
		    db.update("tasks",updatedValues, where, new String[]{from_date});			   
		}
		
		public void updateEntry(String username, String password, String doj, String mob_no, String email_id, String report_to,
								String first_name, String last_name, String status, String create_by, String modified, String project_id, String company_id,
								String role_id, String device_id, String state_id, String city, String create_at, String update_at, String id, String cur_date, String login_count)
		{
			// Define the updated row content.
			ContentValues updatedValues = new ContentValues();
			// Assign values for each row.
			updatedValues.put("username", username);
			updatedValues.put("password", password);
			updatedValues.put("date_of_joining", doj);
			updatedValues.put("mob_no", mob_no);
			updatedValues.put("email_id", email_id);
			updatedValues.put("reporting_to", report_to);
			updatedValues.put("first_name", first_name);
			updatedValues.put("last_name", last_name);
			updatedValues.put("status", status);
			updatedValues.put("created_by", create_by);
			updatedValues.put("modified_by", modified);
			updatedValues.put("project_id", project_id);
			updatedValues.put("company_id", company_id);
			updatedValues.put("role_id", role_id);
			updatedValues.put("device_id", device_id);
			updatedValues.put("state_id", state_id);
			updatedValues.put("city_id", city);
			updatedValues.put("created_at", create_at);
			updatedValues.put("updated_at", update_at);
			updatedValues.put("ids", id);
			updatedValues.put("cur_date", cur_date);
			updatedValues.put("login_count", login_count);
			
			String where="username = ?";
		    db.update("users",updatedValues, where, new String[]{username});			   
		}

	public void insertEntryITEM_MASTER(String code, String name, String primary_category, String sub_category,
	        		String product_variant, String retail_price, String mrp, String qualifying_qty, String free_qty, String status)
			{
		       ContentValues newValues = new ContentValues();
				// Assign values for each row.
				newValues.put("code", code);
				newValues.put("name", name);
				newValues.put("primary_category", primary_category);
				newValues.put("sub_category", sub_category);
				newValues.put("product_variant", product_variant);
				newValues.put("retail_price", retail_price);
				newValues.put("mrp", mrp);
				newValues.put("qualifying_qty", qualifying_qty);
				newValues.put("free_qty", free_qty);
				newValues.put("status", status);
		
				// Insert the row into your table
				db.insert("item_master", null, newValues);
			}
		
		
		
//		//insert customer master function
//		public void insertEntryITEM_MASTER(String Item_Number,	String Unit_Rate, String Organization_Code,	String Organization_Name, String Lot_Control, String Lot_Status, String	Inspection_Required, String Lot_Divisible,	String UOM, String Inventory_Type,	String Business_Vertical, String Primary_Category,	String Primary_Sub_Category, String Secondary_Category,	String Secondary_Sub_Category, String Description,	String Long_Description, String Size1, String Size2, String Voltage_Watts_Amps,	String IS_Code, String Colour,	String Invoice_Description1, String Invoice_Description2, String Old_Item_Code,	String Metal_Aluminum_Wt, String Metal_Copper_Wt, String Metal_Lead_Wt, String Packing_Quantity, String Packing_UOM, String Specification_Code,	String HSS_Code,String HSS_Description,	String Product_OD, String Product_Weight, String Bending_Radius, String Flange,	String Traverse, String Barrel,	String Drum_Carrying_Capacity,String Drum_Type, String Drum_Category,	String PLANNER_CODE,String PLANNING_MAKE_BUY_CODE,String WIP_SUPPLY_TYPE,String DEFAULT_LOT_STATUS_ID, String DEFAULT_LOT_STATUS, String LOT_PREFIX,	String START_AUTO_LOT_NUMBER, String EXPENSE_ACCOUNT, String COST_OF_SALES_ACCOUNT,	String SALES_ACCOUNT, String UNIT_WEIGHT, String UNIT_VOLUME, String UNIT_LENGTH, String UNIT_WIDTH,String UNIT_HEIGHT)
//		{
//			// Define the updated row content.
//			//ContentValues updatedValues = new ContentValues();
//			ContentValues updatedValues = new ContentValues();
//			// Assign values for each row.
//			updatedValues.put("Item_Number", Item_Number);
//			updatedValues.put("Unit_Rate", Unit_Rate);
//			updatedValues.put("Organization_Code", Organization_Code);
//			updatedValues.put("Organization_Name", Organization_Name);
//			updatedValues.put("Lot_Control", Lot_Control);
//			updatedValues.put("Lot_Status", Lot_Status);
//			updatedValues.put("Inspection_Required", Inspection_Required);
//			updatedValues.put("Lot_Divisible", Lot_Divisible);
//			updatedValues.put("UOM", UOM);
//			updatedValues.put("Inventory_Type", Inventory_Type);
//			updatedValues.put("Business_Vertical", Business_Vertical);
//			updatedValues.put("Primary_Category", Primary_Category);
//			updatedValues.put("Primary_Sub_Category", Primary_Sub_Category);
//			updatedValues.put("Secondary_Category", Secondary_Category);
//			updatedValues.put("Secondary_Sub_Category", Secondary_Sub_Category);
//			updatedValues.put("Description", Description);
//			updatedValues.put("Long_Description", Long_Description);
//			updatedValues.put("Size1", Size1);
//			updatedValues.put("Size2", Size2);
//			updatedValues.put("Voltage_Watts_Amps", Voltage_Watts_Amps);
//			updatedValues.put("IS_Code", IS_Code);
//			updatedValues.put("Colour", Colour);
//			updatedValues.put("Invoice_Description1", Invoice_Description1);
//			updatedValues.put("Invoice_Description2", Invoice_Description2);
//			updatedValues.put("Old_Item_Code", Old_Item_Code);
//			updatedValues.put("Metal_Aluminum_Wt", Metal_Aluminum_Wt);
//			updatedValues.put("Metal_Copper_Wt", Metal_Copper_Wt);
//			updatedValues.put("Metal_Lead_Wt", Metal_Lead_Wt);
//			updatedValues.put("Packing_Quantity", Packing_Quantity);
//			updatedValues.put("Packing_UOM", Packing_UOM);
//			updatedValues.put("Specification_Code", Specification_Code);
//			updatedValues.put("HSS_Code", HSS_Code);
//			updatedValues.put("HSS_Description", HSS_Description);
//			updatedValues.put("Product_OD", Product_OD);
//			updatedValues.put("Product_Weight", Product_Weight);
//			updatedValues.put("Bending_Radius", Bending_Radius);
//			updatedValues.put("Flange", Flange);
//			updatedValues.put("Traverse", Traverse);
//			updatedValues.put("Barrel", Barrel);
//			updatedValues.put("Drum_Carrying_Capacity", Drum_Carrying_Capacity);
//			updatedValues.put("Drum_Type", Drum_Type);
//			updatedValues.put("Drum_Category", Drum_Category);
//			updatedValues.put("PLANNER_CODE", PLANNER_CODE);
//			updatedValues.put("PLANNING_MAKE_BUY_CODE", PLANNING_MAKE_BUY_CODE);
//			updatedValues.put("WIP_SUPPLY_TYPE", WIP_SUPPLY_TYPE);
//			updatedValues.put("DEFAULT_LOT_STATUS_ID", DEFAULT_LOT_STATUS_ID);
//			updatedValues.put("DEFAULT_LOT_STATUS", DEFAULT_LOT_STATUS);
//			updatedValues.put("LOT_PREFIX", LOT_PREFIX);
//			updatedValues.put("START_AUTO_LOT_NUMBER", START_AUTO_LOT_NUMBER);
//			updatedValues.put("EXPENSE_ACCOUNT", EXPENSE_ACCOUNT);
//			updatedValues.put("COST_OF_SALES_ACCOUNT", COST_OF_SALES_ACCOUNT);
//			updatedValues.put("SALES_ACCOUNT", SALES_ACCOUNT);
//			updatedValues.put("UNIT_WEIGHT", UNIT_WEIGHT);
//			updatedValues.put("UNIT_VOLUME", UNIT_VOLUME);
//			updatedValues.put("UNIT_LENGTH", UNIT_LENGTH);
//			updatedValues.put("UNIT_WIDTH", UNIT_VOLUME);
//			updatedValues.put("UNIT_HEIGHT", UNIT_HEIGHT);
//			
//			// Insert the row into your table
//			db.insert("item_master", null, updatedValues);			   
//		}
		
		//insert customer master function
		  public void insertCustMaster(String LEGACY_CUSTOMER_CODE, String CUSTOMER_NAME, String CUSTOMER_SHOPNAME,String ADDRESS,
			String STREET, String LANDMARK, String PIN_CODE, String LANDLINE_NO, String MOBILE_NO
			, String EMAIL_ADDRESS, String STATUS, String STATE, String CITY, String BEAT,String vatin,String email,String created_at,String lat,String longi)
	{
        ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("LEGACY_CUSTOMER_CODE", LEGACY_CUSTOMER_CODE);
		newValues.put("CUSTOMER_NAME", CUSTOMER_NAME);
		newValues.put("CUSTOMER_SHOPNAME", CUSTOMER_SHOPNAME);
		newValues.put("ADDRESS", ADDRESS);
		newValues.put("STREET", STREET);
		newValues.put("LANDMARK", LANDMARK);
		newValues.put("PIN_CODE", PIN_CODE);
		newValues.put("LANDLINE_NO", LANDLINE_NO);
		newValues.put("MOBILE_NO", MOBILE_NO);
		newValues.put("EMAIL_ADDRESS", EMAIL_ADDRESS);
		newValues.put("STATUS", STATUS);
		newValues.put("STATE", STATE);
		newValues.put("CITY", CITY);
  	    newValues.put("BEAT", BEAT);
  	    newValues.put("vatin", vatin);
  	    newValues.put("email", email);
  	    newValues.put("created_at", created_at);
		newValues.put("lat", lat);
		newValues.put("long", longi);
		// Insert the row into your table
		db.insert("customer_master", null, newValues);
	}
	
		
	public void Order_Status(String order_id, String item_description, String item_amount,String item_number)
	{
		// Define the updated row content.
		 ContentValues newValues = new ContentValues();
		// Assign values for each row.
		 newValues.put("order_id", order_id);
		 newValues.put("item_description", item_description);
		 newValues.put("item_amount", item_amount);
		 newValues.put("item_number", item_number);
		
		db.insert("status_master", null, newValues);
	}
	
	 public void insertReturnOrders(String project_id, String order_id,String customer_id, String retailer_id, String user_id, 
     		String city_id, String beat_id, String latlon, String sync, String total_order_amount, String signature_path, 
     		String distributor_id, String total_line_items, String create_at, String update_at,String customer_name,String state_name,String city_name,String order_type,String latitude,String longitude,String beat_idnew)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("order_id", order_id);
			newValues.put("customer_id", customer_id);
			newValues.put("retailer_id", retailer_id);
			newValues.put("user_id", user_id);
			newValues.put("city_id", city_id);
			newValues.put("beat_id", beat_id);
			newValues.put("latlon", latlon);
			newValues.put("sync", sync);
			newValues.put("total_order_amount", total_order_amount);
			newValues.put("signature_path", signature_path);
			newValues.put("distributor_id", distributor_id);
			newValues.put("total_line_items", total_line_items);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("customer_name", customer_name);
			newValues.put("state_name", state_name); 
			newValues.put("city_name", city_name);
			newValues.put("order_type", order_type);
			newValues.put("latitude", latitude);
			newValues.put("longitude", longitude);
			newValues.put("beat_idnew", beat_idnew);
			
			// Insert the row into your table
			db.insert("returnordernew", null, newValues);
		}
     
     public void insertReturnOrderProducts(String project_id, String customer_id, String order_id, String line_number, 
     		String category_id, String product_id, String product_variant_id, String product_pack_size_id, String scheme_id,
     		String billed_qty, String free_qty, String total_qty, String retail_price, String MRP, String amount, 
     		String create_at, String update_at,String customer_name,String scheme_amount,String item_number,String actual_discount,String product_name)
		{
	       ContentValues newValues = new ContentValues();
			// Assign values for each row.
			newValues.put("project_id", project_id);
			newValues.put("customer_id", customer_id);
			newValues.put("order_id", order_id);
			newValues.put("line_number", line_number);
			newValues.put("category_id", category_id);
			newValues.put("product_id", product_id);
			newValues.put("product_variant_id", product_variant_id);
			newValues.put("product_pack_size_id", product_pack_size_id);
			newValues.put("scheme_id", scheme_id);
			newValues.put("billed_qty", billed_qty);
			newValues.put("free_qty", free_qty);
			newValues.put("total_qty", total_qty);
			newValues.put("retail_price", retail_price);
			newValues.put("MRP", MRP);
			newValues.put("amount", amount);
			newValues.put("created_at", create_at);
			newValues.put("updated_at", update_at);
			newValues.put("customer_name", customer_name);
			newValues.put("scheme_amount", scheme_amount);
			newValues.put("item_number", item_number);
			newValues.put("actual_discount", actual_discount);
			newValues.put("product_name", product_name);
			// Insert the row into your table
			db.insert("returnorder_products_new", null, newValues);
		}
     
     
     public void insertno_orderReason(String code, String reason_name)
 		{
 	       ContentValues newValues = new ContentValues();
 			// Assign values for each row.
 			newValues.put("code", code);
 			newValues.put("reason_name", reason_name);
 			
 			
 			// Insert the row into your table
 			db.insert("noorder_reason", null, newValues);
 		}


	public void insert_order_details(String sync_time, String total_sync)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("sync_time", sync_time);
		newValues.put("total_sync", total_sync);
		// Insert the row into your table
		db.insert("order_details", null, newValues);
	}

	public void insert_itemScheme(String code,String name,String description,String display_name,String product_id,String qualifying_qty,String amount,String foc_product_id)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("code", code);
		newValues.put("name", name);
		newValues.put("description", description);
		newValues.put("display_name", display_name);
		newValues.put("product_id", product_id);
		newValues.put("qualifying_qty", qualifying_qty);
		newValues.put("amount", amount);
		newValues.put("foc_product_id", foc_product_id);

		// Insert the row into your table
		db.insert("scheme", null, newValues);
	}


	public void insert_itemSchemenew(String code,String name,String type,String description,String display_name,String product_id,String qualifying_qty,String amount,String foc_product_id,String created_by,String modified_by,String created_at,String updated_at,String sid,String project_id)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("code", code);
		newValues.put("name", name);
		newValues.put("type", type);
		newValues.put("description", description);
		newValues.put("display_name", display_name);
		newValues.put("product_id", product_id);
		newValues.put("qualifying_qty", qualifying_qty);
		newValues.put("amount", amount);
		newValues.put("foc_product_id", foc_product_id);
		newValues.put("created_by", created_by);
		newValues.put("modified_by", modified_by);
		newValues.put("created_at", created_at);
		newValues.put("updated_at", updated_at);
		newValues.put("sid", sid);
		newValues.put("project_id", project_id);

		// Insert the row into your table
		db.insert("scheme_new", null, newValues);
	}

	public void insert_credit_profile(String project_id,String customer_id,String retailer_id,String created_by,String created_at,String updated_at,String code,String credit_limit,String amount_outstanding,String amount_overdue)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("project_id", project_id);
		newValues.put("customer_id", customer_id);
		newValues.put("retailer_id", retailer_id);
		newValues.put("created_by", created_by);
		newValues.put("created_at", created_at);
		newValues.put("updated_at", updated_at);
		newValues.put("code", code);
		newValues.put("credit_limit", credit_limit);
		newValues.put("amount_outstanding", amount_outstanding);
		newValues.put("amount_overdue", amount_overdue);

		// Insert the row into your table
		db.insert("credit_profile", null, newValues);
	}

	public void insert_user_email(String email)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("email", email);
		// Insert the row into your table
		db.insert("user_email", null, newValues);
	}

	public void insert_geo_data(String lati,String longi,String addressg,String datatimeg,String date1,String time1)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("lati", lati);
		newValues.put("longi", longi);
		newValues.put("addressg", addressg);
		newValues.put("datatimeg", datatimeg);
		newValues.put("date1", date1);
		newValues.put("time1", time1);

		// Insert the row into your table
		db.insert("geo_data", null, newValues);
	}


	public void insert_Survey_Questions(String survey_code,String question_code,String active_from,String active_to,String question,String option_1,String option_2,String option_3,String option_4,String option_5,String Q_updated_by,String Q_updated_at)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("survey_code", survey_code);
		newValues.put("question_code", question_code);
		newValues.put("active_from", active_from);
		newValues.put("active_to", active_to);
		newValues.put("question", question);
		newValues.put("option_1", option_1);
		newValues.put("option_2", option_2);
		newValues.put("option_3", option_3);
		newValues.put("option_4", option_4);
		newValues.put("option_5", option_5);
		newValues.put("Q_updated_by", Q_updated_by);
		newValues.put("Q_updated_at", Q_updated_at);

		// Insert the row into your table
		db.insert("Survey_Questions", null, newValues);
	}

	public void insert_Survey_Answers(String user_name,String answer_date,String survey_code,String customer_code,String question_code,String customer_choice,String latitude,String longitude,String code)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("user_name", user_name);
		newValues.put("answer_date", answer_date);
		newValues.put("survey_code", survey_code);
		newValues.put("customer_code", customer_code);
		newValues.put("question_code", question_code);
		newValues.put("customer_choice", customer_choice);
		newValues.put("latitude", latitude);
		newValues.put("longitude", longitude);
		newValues.put("code", code);

		// Insert the row into your table
		db.insert("Survey_Answers", null, newValues);
	}

	public void insert_DistriButorBeat(String code,String distributor_code,String beat_cod,String status)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("code", code);
		newValues.put("distributor_code", distributor_code);
		newValues.put("beat_cod", beat_cod);
		newValues.put("status", status);


		// Insert the row into your table
		db.insert("distributor_beats", null, newValues);
	}

	public void insert_target_datanew(String main_heading,String product_text,String target,String achieved,String age)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("main_heading", main_heading);
		newValues.put("product_text", product_text);
		newValues.put("target", target);
		newValues.put("achieved", achieved);
		newValues.put("age", age);


		// Insert the row into your table
		db.insert("target_datanew", null, newValues);
	}

	public void insert_L1_CONTACT(String project_id,String Title,String Heading,String Sub_Heading,String Address,String phone1,String phone2,String email_id1,String email_id2,String website,String created_at,String updated_at,String updated_by)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("project_id", project_id);
		newValues.put("Title", Title);
		newValues.put("Heading", Heading);
		newValues.put("Sub_Heading", Sub_Heading);
		newValues.put("Address", Address);
		newValues.put("phone1", phone1);
		newValues.put("phone2", phone2);
		newValues.put("email_id1", email_id1);
		newValues.put("email_id2", email_id2);
		newValues.put("website", website);
		newValues.put("created_at", created_at);
		newValues.put("updated_at", updated_at);
		newValues.put("updated_by", updated_by);

		// Insert the row into your table
		db.insert("L1_Contact", null, newValues);
	}

	public void insert_Warehouse(String project_id,String code,String type,String name,String address,String city,String state,String latlong,String status,String detail1,String detail2,String detail3)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("project_id", project_id);
		newValues.put("code", code);
		newValues.put("type", type);
		newValues.put("name", name);
		newValues.put("address", address);
		newValues.put("city", city);
		newValues.put("state", state);
		newValues.put("latlong", latlong);
		newValues.put("status", status);
		newValues.put("detail1", detail1);
		newValues.put("detail2", detail2);
		newValues.put("detail3", detail3);


		// Insert the row into your table
		db.insert("warehouse", null, newValues);
	}

	public void insert_ORDER_CATEGORY(String Code,String Name,String Description,String Details1,String Details2,String Details3)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("Code", Code);
		newValues.put("Name", Name);
		newValues.put("Description", Description);
		newValues.put("Details1", Details1);
		newValues.put("Details2", Details2);
		newValues.put("Details3", Details3);


		// Insert the row into your table
		db.insert("order_category", null, newValues);
	}

	public void insert_ACKGROUND_SERVICE_CHECK(String latitude,String longitude,String location_date)
	{
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("latitude", latitude);
		newValues.put("longitude", longitude);
		newValues.put("location_date", location_date);



		// Insert the row into your table
		db.insert("background_service_check", null, newValues);
	}

	public void insertattendence_flag(String name) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("name", name);


		db.insert("attendence_f", null, newValues);

	}

	public void insert_attendance_data(String user_id, String punched_on, String punched_at_latitude, String punched_at_longitude, String punched_button, String punched_at_address, String server_flag, String current_date_only) {
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put("user_id", user_id);
		newValues.put("punched_on", punched_on);
		newValues.put("punched_at_latitude", punched_at_latitude);
		newValues.put("punched_at_longitude", punched_at_longitude);
		newValues.put("punched_button", punched_button);
//		newValues.put("conference_id", conference_id);
//		newValues.put("vertical_id", vertical_id);
		newValues.put("punched_at_address", punched_at_address);
		newValues.put("server_flag", server_flag);
		newValues.put("current_date_only", current_date_only);

		// Insert the row into your table
		db.insert("attendance", null, newValues);
	}
		
}


