package com.anchor.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.anchor.imageadapters.Image;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper
{
	// Database Name
		static final String DATABASE_NAME = "simple_logic.db";
		static final int DATABASE_VERSION = 7;


		// Contacts table name
         public static final String KEY_ID = "_id";
		 public static final String FNAME = "name";
		 private static final String TABLE_REG = "users";
		 private static final String TABLE_TASKS = "tasks";
		 private static final String TABLE_CUSTOMER = "customers";
		 private static final String TABLE_INDUST_VERT = "industry_verticals";
		 private static final String TABLE_PROJECT = "projects";
		 private static final String TABLE_COMP_CONTACT = "company_contacts";
		 private static final String TABLE_COMP_OFFICE = "company_offices";
		 private static final String TABLE_CUST_POS = "customer_pos";
		 private static final String TABLE_LICENSE = "licenses";
		 private static final String TABLE_BILL_TRANS = "billing_transactions";
		 private static final String TABLE_ROLES = "roles";
		 private static final String TABLE_DEVICES = "devices";
		 private static final String TABLE_REGISTRATION = "registrations";
		 public static final String TABLE_STATES = "states";
		 private static final String TABLE_CITIES = "cities";
		 private static final String TABLE_BEATS = "beats";
		 private static final String TABLE_BEAT_ALLOC = "beat_allocations";
		 private static final String TABLE_PRODUCT_CATEG = "product_categories";
		 private static final String TABLE_PRODUCT = "products";
		 private static final String TABLE_PRODUCT_VARIENTS = "product_variants";
		 private static final String TABLE_PRODUCT_PACK_SIZES = "product_pack_sizes";
		 private static final String TABLE_DISTRIBUTORS = "distributors";
		 private static final String TABLE_RETAILERS = "retailers";
		 private static final String TABLE_ORDERS = "orders";
		 private static final String TABLE_ORDER_PRODUCTS = "order_products";
		 private static final String TABLE_NO_ORDER = "no_order";
		 private static final String TABLE_RETURN_ORDER = "return_order";
		 private static final String TABLE_RETURN_ORDER_PRODUCTS = "return_order_products";
		 private static final String TABLE_PREVIOUS_ORDERS = "previous_orders";
		 private static final String TABLE_PREVIOUS_ORDER_PRODUCTS = "previous_order_products";
		 private static final String TABLE_CALENDER_ENTRIES = "calender_entries";
		 private static final String TABLE_CALENDER_LIST_VIES = "calender_list_vies";
		 private static final String TABLE_EXPENCES_TRAVELSS = "expenses_travels";
		 private static final String TABLE_EXPENCES_MISCS = "expenses_miscs";
		 private static final String TABLE_TARGETS = "targets";
		 private static final String TABLE_CUSTOMER_SERVICES_FEEDBACKS = "customer_service_feedbacks";
		 private static final String TABLE_CUSTOMER_SERVICES_COMPLAINTS = "customer_service_complaints";
		 private static final String TABLE_COMPLAINT_ACTIONS = "complaint_actions";
		 private static final String TABLE_CUSTOMER_SERVICE_CLAIMS = "customer_service_claims";
		 private static final String CLAIM_ACTIONS = "claim_actions";
		 private static final String TABLE_CUSTOMER_SERVICE_COMPETITION_STOCKS = "customer_service_competition_stocks";
		 private static final String CUSTOMER_SERVICE_MEDIA = "customer_service_media";
		 private static final String TABLE_DELIVERY_SCHEDULES = "delivery_schedules";
		 private static final String TABLE_DELIVERY_PRODUCTS = "delivery_products";
		 private static final String TABLE_CREDIT_LIMITS = "credit_limits";
		 private static final String TABLE_OUTSTANDINGS = "outstandings";
		 private static final String TABLE_INVOICES = "invoices";
		 private static final String TABLE_SYNC_HISTORIES = "sync_histories";
		 private static final String TABLE_SURVEYS = "surveys";
		 private static final String TABLE_SURVEY_RESULTS = "survey_results";
		 private static final String TABLE_NEW_LAUNCHES = "new_launches";
		 private static final String TABLE_ADVERTISEMENTS = "advertisements"; 
		 private static final String TABLE_ITEM_MASTER = "item_master";
		 private static final String TABLE_CUSTOMER_MASTER = "customer_master";
         private static final String TABLE_LABEL_CHANGES = "label_chnages";
         private static final String TABLE_ORDER_DETAILS = "order_details";
         private static final String TABLE_VERSION_INFO = "version_info";
		 
		 private static final String DATABASE_CREATE_ORDERSTATUS = "status_master";
		 
		 private static final String TABLE_RETURNORDERNEW = "returnordernew";
		 private static final String TABLE_RETURNORDERNEW_PRODUCT = "returnorder_products_new";

        private static final String TABLE_NO_ORDER_REASON = "noorder_reason";
        private static final String TABLE_ITEM_SCHEME = "scheme";
        private static final String TABLE_ITEM_SCHEME_NEW = "scheme_new";
        private static final String TABLE_CREDIT_PROFILE = "credit_profile";
        private static final String TABLE_USER_EMAIL= "user_email";
        private static final String TABLE_GEO_DATA= "geo_data";
         private static final String TABLE_SURVEY_QUESTIONS= "Survey_Questions";
        private static final String TABLE_SURVEY_ANSWERS= "Survey_Answers";
        private static final String TABLE_NO_ORDERS= "no_orders";
        private static final String TABLE_DISTRIBUTORBEAT= "distributor_beats";
        private static final String TABLE_WAREHOUSE= "warehouse";

        private static final String TABLE_L1_CONTACT= "L1_Contact";
        private static final String TABLE_ORDER_CATEGORY= "order_category";
        private static final String TABLE_BACKGROUND_SERVICE_CHECK= "background_service_check";
        private static final String TABLE_ATTENDANCE_DATA = "attendance";
        private static final String TABLE_CREATE_ATTENDENCE_F = "attendence_f";
        private static final String TABLE_CREATE_NEW_LAUNCHES_NEW = "new_launches_new";
        private static final String TABLE_CREATE_ASSET_CODE= "asset_code";

		 
	    static SQLiteDatabase db;
		// Contacts Table Columns names
	    private static final String USERNAME = "username";
	    private static final String PASSWORD = "password";
	    private static final String IMEI_NO = "imei_no";
	    private static final String PROJECT_ID = "project_id";
	    private static final String USER_ID = "user_id";
	    private static final String CLIENT_ID = "client_id";
	    private static final String CURRENT_DATE = "cur_date";
	    private static final String CURRENT_TIME = "cur_time";
	    private static final String VARANT = "desc";
	    private static final String ORDER_ID = "order_id";
	    private static final String RET_ID = "retailer_id";
	     
	     private static final String ORDERSTATUS_ID = "order_id";

   // public static final String TABLE_TEAM = "team";
    public static final String COLUMN_COACH = "coach";
    public static final String COLUMN_STADIUM = "stadium";
    public static final String COLUMN_STADIUM2 = "stadium2";


    private static final String DATABASE_ALTER_TEAM_TO_V2 = "ALTER TABLE "
            + TABLE_CUSTOMER_MASTER + " ADD COLUMN " + COLUMN_COACH + " TEXT;";

    private static final String DATABASE_ALTER_TEAM_TO_V3 = "ALTER TABLE "
            + TABLE_CUSTOMER_MASTER + " ADD COLUMN " + COLUMN_STADIUM + " TEXT;";

    private static final String DATABASE_ALTER_TEAM_TO_V33 = "ALTER TABLE "
            + TABLE_CUSTOMER_MASTER + " ADD COLUMN " + COLUMN_STADIUM2 + " TEXT;";
	    
	public DataBaseHelper(Context context) 
    {
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase _db) 
	{
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_REG);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_TASKS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUSTOMER);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_INDUST_VERT);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PROJECTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_COMP_CONTACT);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_COMP_OFFICE);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUST_POS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ORDER_PRODUCTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_LICENSES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_BILL_TRANS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ROLES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_DEVICES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_REGISTRATION);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_STATES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CITIES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_BEATS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_BEAT_ALLOCATION);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PRODUCT_CATEGORIES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PRODUCTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PRODUCT_VARIENTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PRODUCT_PACK_SIZES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_DISTRIBUTORS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_RETAILERS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ORDERS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_NO_ORDERS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_RETURN_ORDERS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_RETURN_ORDER_PRODUCTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PREVIOUS_ORDERS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_PREVIOUS_ORDER_PRODUCTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CALENDER_ENTRIES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CALENDER_LIST_VIES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_EXPENCES_TRAVELS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_EXPENCES_MISCS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_TARGETS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUSTOMER_SERVICES_FEEDBACKS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUSTOMER_SERVICE_COMPLAINTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_COMPLAINT_ACTIONS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUSTOMER_SERVICE_CLAIMS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CLAIMS_ACTIONS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUSTOMER_SERVICE_COMPETITION);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUSTOMER_MEDIA);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_DELIVERY_SCHEDULES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_DELIVERY_PRODUCTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CREDIT_LIMIT);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_OUTSTANDINGS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_INVOICES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_SYNC_HISTORIES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_SURVEYS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_SURVEYS_RESULTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_NEW_LAUNCHES);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ADVERTISEMENTS);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ITEMMASTER);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_CUST_MASTER);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_RETURN_ORDER_NEW);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_RETURN_ORDER_PRODUCTS_NEW);
			_db.execSQL(LoginDataBaseAdapter.DATABASE_NO_ORDER_REASON);
			
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ORDERSTATUS);

            _db.execSQL(LoginDataBaseAdapter.DATABASE_ORDER_DETAILS);
          // _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ITEMSCHEME);

            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREDIT_PROFILE);

            _db.execSQL(LoginDataBaseAdapter.DATABASE_USER_EMAIL);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_GEO_DATA);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_VERSION_INFO);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_SURVEY_QUESTIONS);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_SURVEY_ANSWERS);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_DISTRIBUTORBEAT);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_L1_CONTACT);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ITEMSCHEME_NEW);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_LABEL_CHANGES);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_WAREHOUSE);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_ORDER_CATEGORY);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_BACKGROUND_SERVICE_CHECK);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_ATTENDENCE_F);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_ATTENDANCE_DATA);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_NEW_LAUNCHES_NEW);
            _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE_TABLE_ASSET_CODE);

            }
	
	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) 
	{
			// Log the version upgrade.
			Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
	        
			_db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");

        switch (_newVersion)
        {
            case 1:
                //upgrade from version 1 to 2
               // _db.execSQL(DATABASE_ALTER_TEAM_TO_V2);
            case 2:
                //upgrade from version 2 to 3
              //  _db.execSQL(DATABASE_ALTER_TEAM_TO_V3);

            case 5:
                //upgrade from version 2 to 3
              //  _db.execSQL(DATABASE_ALTER_TEAM_TO_V33);
        }
        // Create a new one.
			onCreate(_db);
	}

    // Getting VersionInfo
    public List<Local_Data> getVersioninfo() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT * FROM " + TABLE_VERSION_INFO + "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVersioncode(cursor.getInt(1));
                contact.setVersionname(cursor.getString(2));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getAllB_Unit() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT b_unit FROM " + TABLE_ITEM_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setBunit(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getB_Unit_byName(String BUSINESS_UNIT) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT b_unit FROM " + TABLE_ITEM_MASTER + " WHERE b_unit = '" +  BUSINESS_UNIT + "'" + " GROUP BY name ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setBunit(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getB_Unit_byproduct_id(String product_code) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT b_unit,primary_category,b_business_c, sub_category FROM " + TABLE_ITEM_MASTER + " WHERE code = '" +  product_code + "'" + " GROUP BY name ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setBunit(cursor.getString(0));
                contact.setprimary_category(cursor.getString(1));
                contact.setbusiness_category(cursor.getString(2));
                contact.setProduct(cursor.getString(3));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> TABLE_CREATE_NEW_LAUNCHES_NEW_CHECK(String file_path) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT file_path FROM " + TABLE_CREATE_NEW_LAUNCHES_NEW + " WHERE file_path = '" +  file_path + "'" + " GROUP BY file_path ORDER BY file_path";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setImage_path(cursor.getString(0));

                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Image> TABLE_CREATE_NEW_LAUNCHES_NEW_Data() {
        List<Image> contactList1 = new ArrayList<Image>();
        // Select All Query
        String selectQuery1 = "SELECT name,file_path,file_type,date FROM " + TABLE_CREATE_NEW_LAUNCHES_NEW + " GROUP BY name ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Image contact = new Image();
                contact.setName(cursor.getString(0));
                contact.setLarge(cursor.getString(1));
                contact.setType(cursor.getString(2));
                contact.setTimestamp(cursor.getString(3));

                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getBdivByBunit(String BUSINESS_UNIT) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT primary_category FROM " + TABLE_ITEM_MASTER + " WHERE b_unit = '" +  BUSINESS_UNIT + "'" + " GROUP BY name ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setprimary_category(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getBunit_id(String bunit) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT code FROM " + TABLE_ITEM_MASTER + " WHERE b_unit = '" +  bunit + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code FROM item_master WHERE b_unit = ? GROUP BY name ORDER BY name",
                new String[] { bunit });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setSTATE_ID(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Account Data
    public List<Local_Data> getAllLabels() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT * FROM " + TABLE_LABEL_CHANGES + "";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVarLabel_account(cursor.getString(1));
                contact.setNewLabel_account(cursor.getString(2));
                contact.setEditable(cursor.getString(3));
                contact.setMandatory(cursor.getString(4));
                contact.setAllow(cursor.getString(5));
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getAllMain() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REG;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setUser(cursor.getString(1));
                contact.setPwd(cursor.getString(2));
                contact.setemail(cursor.getString(5));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }

    // Getting All Local_Data
    public List<Local_Data> getL1_Contact() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT Title,Heading,Sub_Heading,Address,phone1,phone2,email_id1,email_id2,website FROM " + TABLE_L1_CONTACT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setTitle(cursor.getString(0));
                contact.setHeading(cursor.getString(1));
                contact.setSub_Heading(cursor.getString(2));
                contact.setL1_Address(cursor.getString(3));
                contact.setL1_phone1(cursor.getString(4));
                contact.setL1_phone2(cursor.getString(5));
                contact.setL1_email_id1(cursor.getString(6));
                contact.setL1_email_id2(cursor.getString(7));
                contact.setL1_website(cursor.getString(8));


                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data
    public List<Local_Data> getemaIL() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  email FROM " + TABLE_USER_EMAIL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setemail(cursor.getString(0));

                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }
    
    // Getting All Local_Data REASON
    public List<Local_Data> getAllnoorder_reason() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  code,reason_name FROM " + TABLE_NO_ORDER_REASON + " ORDER BY reason_name";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setreason_code(cursor.getString(0));
                contact.setreason_name(cursor.getString(1));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }

    // Getting All Local_Data REASON
    public List<Local_Data> getorder_category() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT Name,Description FROM " + TABLE_ORDER_CATEGORY + " ORDER BY Name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrder_type_name(cursor.getString(0));
                contact.setOrder_type_desc(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data REASON
    public List<Local_Data> getorder_payment_term_data() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT code,name FROM " + TABLE_CREATE_ASSET_CODE + " ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCode(cursor.getString(0));
                contact.setName(cursor.getString(1));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data REASON
    public List<Local_Data> get_order_category_code(String name) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select Code,Name FROM order_category WHERE Name = ?;",
                new String[] { name });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrder_type_code(cursor.getString(0));
                contact.setOrder_type_name(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data REASON
    public List<Local_Data> get_order_category_name(String code) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select Code,Name FROM order_category WHERE Code = ?;",
                new String[] { code });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrder_type_code(cursor.getString(0));
                contact.setOrder_type_name(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data REASON
    public List<Local_Data> get_order_assetcode_name(String code) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select code,name FROM asset_code WHERE code = ?;",
                new String[] { code });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCode(cursor.getString(0));
                contact.setName(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data REASON
    public List<Local_Data> getOrder_details() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  sync_time,total_sync FROM " + TABLE_ORDER_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setsync_time(cursor.getString(0));
                contact.settotal_sync(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }


    // Getting All Local_Data REASON
    public List<Local_Data> getNoOrders() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  customer_id,user_id,reason_code,reason_type,latitude,longitude,order_number FROM " + TABLE_NO_ORDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setLEGACY_CUSTOMER_CODE(cursor.getString(0));
                contact.setuser_email(cursor.getString(1));
                contact.setreason_code(cursor.getString(2));
                contact.setreason_type(cursor.getString(3));
                contact.setlatitude(cursor.getString(4));
                contact.setlongitude(cursor.getString(5));
                contact.setorder_number(cursor.getString(6));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }
    
    // Getting All Local_Data REASON
    public List<Local_Data> get_reason_code(String reason_name) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  code,reason_name FROM " + TABLE_NO_ORDER_REASON + " WHERE reason_name = '" +  reason_name + "'";
        
 
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select code,reason_name FROM noorder_reason WHERE reason_name = ?;",
                new String[] { reason_name });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setreason_code(cursor.getString(0));
                contact.setreason_name(cursor.getString(1));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }
    
  // Getting All Local_Data
    public List<Local_Data> getGEo_DATA() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  lati,longi,addressg,datatimeg,date1,time1 FROM " + TABLE_GEO_DATA + " ORDER BY datatimeg LIMIT 100" ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setlatitude(cursor.getString(0));
                contact.setlongitude(cursor.getString(1));
                contact.setAddress(cursor.getString(2));
                contact.setdatetime1(cursor.getString(3));
                contact.setdate1(cursor.getString(4));
                contact.settime1(cursor.getString(5));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getBACKGROUND_SERVICE_CHECK_DATA() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  latitude,longitude FROM " + TABLE_BACKGROUND_SERVICE_CHECK ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setlatitude(cursor.getString(0));
                contact.setlongitude(cursor.getString(1));

                contactList1.add(contact);
            } while (cursor.moveToNext());
        }


        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getUSERBY_Name(String name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
//        String selectQuery1 = "SELECT  username,password,email_id,ids,reporting_to,first_name,last_name FROM " + TABLE_REG + " WHERE username = '" +  name + "'" + " GROUP BY username ORDER BY username";

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);


        Cursor cursor = db.rawQuery("select username,password,email_id,ids,reporting_to,first_name,last_name,emp_code FROM users WHERE username = ? GROUP BY username ORDER BY username",
                new String[] {name});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setUser(cursor.getString(0));
                contact.setPwd(cursor.getString(1));
                contact.setuser_email(cursor.getString(2));
                contact.set_shedule_order_id(cursor.getString(3));
                contact.setreporting_to(cursor.getString(4));
                contact.setfirst_name(cursor.getString(5));
                contact.setlast_name(cursor.getString(6));
                contact.setEmp_code(cursor.getString(7));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

       // db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getUSERAddressBY_Email(String email) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
//        String selectQuery1 = "SELECT  username,password,email_id,ids,reporting_to,first_name,last_name FROM " + TABLE_REG + " WHERE username = '" +  name + "'" + " GROUP BY username ORDER BY username";

        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery1, null);


        Cursor cursor = db.rawQuery("select username,city_id FROM users WHERE email_id = ? GROUP BY email_id ORDER BY email_id",
                new String[] {email});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setUser(cursor.getString(0));
                contact.setAddress(cursor.getString(1));

                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getManager_mobile(String reporting_to) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT mob_no,first_name,last_name FROM " + TABLE_REG + " WHERE email_id = '" +  reporting_to + "'" + " GROUP BY username ORDER BY username";


        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select mob_no,first_name,last_name FROM users WHERE email_id = ? GROUP BY username ORDER BY username",
                new String[] {reporting_to});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setMOBILE_NO(cursor.getString(0));
                contact.setfirst_name(cursor.getString(1));
                contact.setlast_name(cursor.getString(2));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        //db.close();
        // return contact list?
        return contactList1;
    }
    
   // Getting All Local_Data
    public List<Local_Data> getOrderIds(String custids) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_ORDERS + " WHERE customer_id  = '" +  custids + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getOrderAll(String order_type) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_ORDERS + " WHERE order_type"+ " ='"+ order_type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getOrderAllReturn(String order_type) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_RETURNORDERNEW + " WHERE order_type"+ " ='"+ order_type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getOrderIdsn(String custids,String order_type) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_ORDERS + " WHERE customer_id  = '" +  custids + "'" + " AND order_type"+ " ='"+ order_type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getOrderIdsnAll(String order_type) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_ORDERS + " WHERE order_type  = '" +  order_type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getOrderIdsnAllReturn(String order_type) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_RETURNORDERNEW + " WHERE order_type  = '" +  order_type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }
    
    
    
    
 // Getting All Local_Data
    public List<Local_Data> getOrderIds_return(String custids) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,customer_name FROM " + TABLE_RETURNORDERNEW + " WHERE customer_id  = '" +  custids + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setc_name(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getUSERBY_Device(String name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  device_id FROM " + TABLE_REG + " WHERE device_id = '" +  name + "'" + " GROUP BY username ORDER BY username";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setDistr(cursor.getString(0));
                //contact.setPwd(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
     //   db.close();
        // return contact list?
        return contactList1;
    }

    public List<Local_Data> getUSER_PASSBY_Device(String devid) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT username,password FROM " + TABLE_REG + " WHERE device_id = '" + devid + "'" + " GROUP BY username ORDER BY username";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setUser(cursor.getString(0));
                contact.setPwd(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    public void update_user_createDate(String cur_date,String email_id)
    {

        String selectQuery = "UPDATE " + TABLE_REG + " SET cur_date = '" +  cur_date  + "'" + " WHERE email_id = '" +  email_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    // Getting All Local_Data
    public List<Local_Data> getDateBY_Device(String device_id,String email_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT cur_date FROM " + TABLE_REG + " WHERE device_id = '" + device_id + "'" + " AND email_id"+ " ='"+ email_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCur_date(cursor.getString(0));
                //contact.setPwd(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

       // db.close();
        // return contact list?
        return contactList1;
    }//

    // Getting All Local_Data
    public List<Local_Data> get_email(String device_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT email_id FROM " + TABLE_REG + " WHERE device_id = '" + device_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setemail(cursor.getString(0));
                //contact.setPwd(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }//


    // Getting All Local_Data
    public void getDeleteTable(String tablename) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " + tablename;
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteLocationData(String datatimeg) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " + TABLE_GEO_DATA + " WHERE datatimeg = '" + datatimeg + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }
    
    
   // Getting All Local_Data
    public void getDeleteTable_bycolumn(String tablename,String id) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " + tablename + " WHERE ids = '" +  id + "'";;
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteMediaBYID(String media_id) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " + CUSTOMER_SERVICE_MEDIA + " WHERE media_id = '" + media_id + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }
    
   // Getting All Local_Data
    public void getDeleteTableorder_byOID(String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
       String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeletePRODUCT(String code) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ITEM_MASTER+ " WHERE code " + " = '"+ code + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }


    // Getting All Local_Data
    public void getDeleteNOOrder(String code) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_NO_ORDER_REASON+ " WHERE code " + " = '"+ code + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTableCalenderEntity(String from_date) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_CALENDER_ENTRIES+ " WHERE from_date " + " ='"+ from_date + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTablecalender_event(String calender_id) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_CALENDER_ENTRIES+ " WHERE calender_id " + " ='"+ calender_id + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTablecalender_event_BYFLAG(String delete_flag) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_CALENDER_ENTRIES+ " WHERE delete_flag " + " ='"+ delete_flag + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeletedelivery_schedules(String customer_id) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_DELIVERY_SCHEDULES+ " WHERE customer_id " + " ='"+ customer_id + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }


    // Getting All Local_Data
    public void getDeletedelivery_schedulesAll() {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_DELIVERY_SCHEDULES;

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    public void getDeleteBACKGROUND_SERVICE_CHECK() {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_BACKGROUND_SERVICE_CHECK;

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeletedelivery_products(String customer_id) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_DELIVERY_PRODUCTS+ " WHERE customer_id " + " ='"+ customer_id + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeletedelivery_productsAll() {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_DELIVERY_PRODUCTS ;

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeletecredit_limits(String customer_id) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_CREDIT_PROFILE+ " WHERE customer_id " + " ='"+ customer_id + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeletecredit_limitsAll() {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_CREDIT_PROFILE ;

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }
    
    
 // Getting All Local_Data
    public void getDeleteTableorder_byOID_return(String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
       String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
   // Getting All Local_Data
    public void getDeleteTableorderproduct_byOID(String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorderproduct_byOID_return(String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW_PRODUCT+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
  // Getting All Local_Data
    public void getDeleteTableorderproduct_byITEM_NUMBER(String item_number,String order_id) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE item_number " + " ='"+ item_number + "'" + " AND order_id"+ " ='"+ order_id + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTablePreviousorderproduct_byITEM_NUMBER(String item_number,String order_id) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_PREVIOUS_ORDER_PRODUCTS+ " WHERE item_number " + " ='"+ item_number + "'" + " AND order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorderproduct_byITEM_NUMBER_return(String item_number,String order_id) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW_PRODUCT+ " WHERE item_number " + " ='"+ item_number + "'" + " AND order_id"+ " ='"+ order_id + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    // Getting All Local_Data
    public void getDeleteTableorder_bycustomer(String customer_name,String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
      // String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE order_id " + " ='"+ order_number + "'";
        String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE order_id = '" +  order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTableorder(String order_typer) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE order_type = '" +  order_typer + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorder_bycustomer_return(String customer_name,String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
//       String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_id " + " ='"+ order_number + "'";

        String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
   // Getting All Local_Data
    public void getDeleteTableorder_byORDER_ID(String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
       String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorder_byORDER_ID_return(String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
       String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
   // Getting All Local_Data
    public void getDeleteTableorderproduct_bycustomer(String customer_name,String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
//        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_id " + " ='"+ order_number + "'";

        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE order_id = '" +  order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTableorderproduct_byOrder_id(String order_number) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE order_id = '" +  order_number + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public void getDeleteTableorderproduct(String order_number) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE order_id = '" +  order_number  + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorderproduct_bycustomer_return(String customer_name,String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
      //  String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW_PRODUCT+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_id " + " ='"+ order_number + "'";

        String selectQuery = "DELETE FROM " +TABLE_RETURNORDERNEW_PRODUCT+ " WHERE order_id = '" +  order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
    
    // Getting All Local_Data
    public void getDeleteTableorder_bycustomer_PRE(String customer_name,String order_type) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
       String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_type " + " ='"+ order_type + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorderproduct_bycustomer_PRE(String customer_name,String order_type) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_type " + " ='"+ order_type + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
  // Getting All Local_Data
    public void getDeleteTableorder_bycustomer_IN(String customer_name,String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
 // Getting All Local_Data
    public void getDeleteTableorder_bycustomer_INN(String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDERS+ " WHERE order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
   // Getting All Local_Data
    public void getDeleteTableorderproduct_bycustomer_IN(String customer_name,String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE customer_name = '" +  customer_name + "'" + " AND order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
    // Getting All Local_Data
    public void getDeleteTableorderproduct_bycustomer_INN(String order_type,String order_number) {
       // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " +TABLE_ORDER_PRODUCTS+ " WHERE  order_id " + " ='"+ order_number + "'";
       
       SQLiteDatabase db= this.getWritableDatabase();
 
       db.execSQL(selectQuery);
    }
    
   // Getting All Local_Data
    public List<Local_Data> getAllList() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_STATES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getstate(String State_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_STATES + " WHERE ids = '" +  State_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getstateby_id(String State_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  name FROM " + TABLE_STATES + " WHERE code = '" +  State_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getmob_byshop(String shop) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT MOBILE_NO FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_SHOPNAME = '" +  shop + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_mob(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }






    // Getting All Local_Data
    public List<Local_Data> getAllCity() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CITIES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data
    public List<Local_Data> getAllState() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT name FROM " + TABLE_STATES + " GROUP BY name ORDER BY name";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
    
 // Getting All Local_Data
    public List<Local_Data> getAllRetailer_cre() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT LEGACY_CUSTOMER_CODE,CUSTOMER_NAME,CUSTOMER_SHOPNAME,ADDRESS,STREET,LANDMARK,PIN_CODE,MOBILE_NO,EMAIL_ADDRESS,STATUS,STATE,CITY,BEAT,vatin,email,lat,long FROM " + TABLE_CUSTOMER_MASTER + " WHERE created_at != '" + "" + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setLEGACY_CUSTOMER_CODE(cursor.getString(0));
                contact.setCUSTOMER_NAME(cursor.getString(1));
                contact.setCUSTOMER_SHOPNAME(cursor.getString(2));
                contact.setADDRESS(cursor.getString(3));
                contact.setSTREET(cursor.getString(4));
                contact.setLANDMARK(cursor.getString(5));
                contact.setPIN_CODE(cursor.getString(6));
                contact.setMOBILE_NO(cursor.getString(7));
                contact.setEMAIL_ADDRESS(cursor.getString(8));
                contact.setSTATUS(cursor.getString(9));
                contact.setSTATE_ID(cursor.getString(10));
                contact.setCITY_ID(cursor.getString(11));
                contact.setBEAT_ID(cursor.getString(12));
                contact.setvatin(cursor.getString(13));
                contact.setemail(cursor.getString(14));
                contact.setlatitude(cursor.getString(15));
                contact.setlongitude(cursor.getString(16));
               
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }



    public void updateCustomerby_CreateAt(String value)
    {

        String selectQuery = "UPDATE " + TABLE_CUSTOMER_MASTER + " SET created_at = '" +  value    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }
    
    
// // Getting All Local_Data
//    public List<Local_Data> getCustomerAddress(String Customer_name,String order_type) {
//        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
//        // Select All Query
//        String selectQuery1 = "SELECT  ADDRESS FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_NAME = '" +  Customer_name + "'" + " AND PURPOSE_ADDRESS = '" +  order_type + "'"+ " GROUP BY ADDRESS1 ORDER BY ADDRESS1";
// 
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery1, null);
// 
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Local_Data contact = new Local_Data();
//                contact.setStateName(cursor.getString(0));
//                //contact.setPwd(cursor.getString(2));
//                //contact.setImei(cursor.getString(3));
//                
//                // Adding contact to list
//                contactList1.add(contact);
//            } while (cursor.moveToNext());
//        }
//        
//        db.close();
//        // return contact list?
//        return contactList1;
//    }
    
    
 // Getting All Local_Data
    public List<Local_Data> getDistributors() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
       // String selectQuery1 = "SELECT name FROM " + TABLE_DISTRIBUTORS + " WHERE CUSTOMER_CATEGORY = '" +  CUSTOMER_CATEGORY + "'" + " GROUP BY CUSTOMER_NAME ORDER BY CUSTOMER_NAME";
        String selectQuery1 = "SELECT name FROM " + TABLE_DISTRIBUTORS;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getDistributors_BYID(String distributor_code) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        // String selectQuery1 = "SELECT name FROM " + TABLE_DISTRIBUTORS + " WHERE CUSTOMER_CATEGORY = '" +  CUSTOMER_CATEGORY + "'" + " GROUP BY CUSTOMER_NAME ORDER BY CUSTOMER_NAME";
        String selectQuery1 = "SELECT name FROM " + TABLE_DISTRIBUTORS + " WHERE code = '" +  distributor_code + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getDistributors_code(String beat_code) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        String selectQuery1 = "SELECT distributor_code FROM " + TABLE_DISTRIBUTORBEAT + " WHERE beat_cod = '" +  beat_code + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setDISTRIBUTER_ID(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }
    
    
// // Getting All Local_Data
//    public List<Local_Data> getDistributors() {
//        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
//        // Select All Query
//        String selectQuery1 = "SELECT CUSTOMER_NAME FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_CATEGORY = '" +  CUSTOMER_CATEGORY + "'" + " GROUP BY CUSTOMER_NAME ORDER BY CUSTOMER_NAME";
// 
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery1, null);
// 
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Local_Data contact = new Local_Data();
//                contact.setStateName(cursor.getString(0));
//                //contact.setPwd(cursor.getString(2));
//                //contact.setImei(cursor.getString(3));
//                
//                // Adding contact to list
//                contactList1.add(contact);
//            } while (cursor.moveToNext());
//        }
//        
//        db.close();
//        // return contact list?
//        return contactList1;
//    }
    
 // Getting All Local_Data
    public List<Local_Data> getCustomerName(String Customer_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT CUSTOMER_NAME,LEGACY_CUSTOMER_CODE FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_NAME = '" +  Customer_name + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select CUSTOMER_NAME,LEGACY_CUSTOMER_CODE FROM customer_master WHERE CUSTOMER_NAME = ?;",
                new String[] { Customer_name });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                contact.setCust_Code(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getCustomer_BYID(String Customer_name,String shope_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query


        Customer_name = Customer_name.replace("'","\'");
        shope_name = Customer_name.replace("'","\'");

        String selectQuery1 = "SELECT * FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_NAME = '" +  Customer_name + "'" + " AND CUSTOMER_SHOPNAME= '" +  shope_name + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = null;

        try {
             cursor = db.rawQuery("select * from customer_master WHERE CUSTOMER_NAME = ? AND CUSTOMER_SHOPNAME = ?",
                    new String[] {Customer_name,shope_name});

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Local_Data contact = new Local_Data();
                    contact.setStateName(cursor.getString(0));
                    contact.setCust_Code(cursor.getString(1));
                    //contact.setPwd(cursor.getString(2));
                    //contact.setImei(cursor.getString(3));

                    // Adding contact to list
                    contactList1.add(contact);
                } while (cursor.moveToNext());
            }
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }

        //db.close();
        // return contact list?
        return contactList1;
    }

    public void deletesalesupdatebyID(String CUSTOMER_NAME,String CUSTOMER_SHOPNAME) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();

        // Select All Query
//        String selectQuery = "DELETE FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_NAME = '" +  CUSTOMER_NAME + "'" + " AND CUSTOMER_SHOPNAME= '" +  CUSTOMER_SHOPNAME + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.delete(TABLE_CUSTOMER_MASTER,"CUSTOMER_NAME=? and CUSTOMER_SHOPNAME=?",new String[]{CUSTOMER_NAME,CUSTOMER_SHOPNAME});

//        db.delete(TABLE_CUSTOMER_MASTER, CUSTOMER_NAME + " = ?", new String[] { CUSTOMER_NAME });
//        db.delete(TABLE_CUSTOMER_MASTER, CUSTOMER_SHOPNAME + " = ?", new String[] { CUSTOMER_SHOPNAME });



       // db.execSQL(selectQuery);
       // db.close();
    }

   // Getting All Local_Data
    public List<Local_Data> getCustomerCode(String Customer_Name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT LEGACY_CUSTOMER_CODE,ADDRESS,MOBILE_NO,CUSTOMER_NAME,STATE,CITY,BEAT,CUSTOMER_SHOPNAME,lat,long FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_SHOPNAME = '" +  Customer_Name + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select LEGACY_CUSTOMER_CODE,ADDRESS,MOBILE_NO,CUSTOMER_NAME,STATE,CITY,BEAT,CUSTOMER_SHOPNAME,lat,long from customer_master WHERE CUSTOMER_SHOPNAME = ?",
                new String[] {Customer_Name});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setAddress(cursor.getString(1));
                contact.setMOBILE_NO(cursor.getString(2));
                contact.setCUSTOMER_NAME(cursor.getString(3));
                contact.setSTATE_ID(cursor.getString(4));
                contact.setCITY_ID(cursor.getString(5));
                contact.setBEAT_ID(cursor.getString(6));
                contact.setCUSTOMER_SHOPNAME(cursor.getString(7));
                contact.setlatitude(cursor.getString(8));
                contact.setlongitude(cursor.getString(9));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getCustomername(String LEGACY_CUSTOMER_CODE) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT LEGACY_CUSTOMER_CODE,ADDRESS,MOBILE_NO,CUSTOMER_NAME,CUSTOMER_SHOPNAME FROM " + TABLE_CUSTOMER_MASTER + " WHERE LEGACY_CUSTOMER_CODE = '" +  LEGACY_CUSTOMER_CODE + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setAddress(cursor.getString(1));
                contact.setMOBILE_NO(cursor.getString(2));
                contact.setCUSTOMER_NAME(cursor.getString(3));
                contact.setCUSTOMER_SHOPNAME(cursor.getString(4));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getCustomersForList() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT LEGACY_CUSTOMER_CODE,ADDRESS,MOBILE_NO,CUSTOMER_NAME,STATE,CITY,BEAT,CUSTOMER_SHOPNAME,lat,long,CITY,BEAT FROM " + TABLE_CUSTOMER_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setAddress(cursor.getString(1));
                contact.setMOBILE_NO(cursor.getString(2));
                contact.setCUSTOMER_NAME(cursor.getString(3));
                contact.setSTATE_ID(cursor.getString(4));
                contact.setCITY_ID(cursor.getString(5));
                contact.setBEAT_ID(cursor.getString(6));
                contact.setCUSTOMER_SHOPNAME(cursor.getString(7));
                contact.setlatitude(cursor.getString(8));
                contact.setlongitude(cursor.getString(9));
                contact.setCITY_ID(cursor.getString(10));
                contact.setBEAT_ID(cursor.getString(11));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> checkCustomer() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT * FROM " + TABLE_CUSTOMER_MASTER ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    //Getting All Local_Data
    public List<Local_Data> checkTargets() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT * FROM " + TABLE_TARGETS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_Target_Text(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }
    
   /// Getting All Local_Data
    public List<Local_Data> getItemCode(String pro_categ, String pro_product, String pro_variant) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT code,name FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = '" +  pro_variant + "'" + " AND primary_category " + " ='"+ pro_categ + "'"+ " AND sub_category"+ " ='" + pro_product + "'";;

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code,name FROM item_master WHERE product_variant = ? AND primary_category = ? AND sub_category = ?",
                new String[] {pro_variant,pro_categ,pro_product});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setItem_Code(cursor.getString(0));
                contact.setProdname(cursor.getString(1));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    /// Getting All Local_Data
    public List<Local_Data> getAllitemList() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT code,name FROM " + TABLE_ITEM_MASTER +" ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setItem_Code(cursor.getString(0));
                contact.setProdname(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    /// Getting All Local_Data
    public List<Local_Data> getAllwarehouseList() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT code,name FROM " + TABLE_WAREHOUSE +" ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setWare_code(cursor.getString(0));
                contact.setWare_name(cursor.getString(1));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    
    
  /// Getting All Local_Data
    public List<Local_Data> getItemCode_return(String pro_categ, String pro_product, String pro_variant) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT code,name FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = '" +  pro_variant + "'" + " AND primary_category " + " ='"+ pro_categ + "'"+ " AND sub_category"+ " ='" + pro_product + "'";;

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code,name FROM item_master WHERE product_variant = ? AND primary_category = ? AND sub_category = ?",
                new String[] {pro_variant,pro_categ,pro_product});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setItem_Code(cursor.getString(0));
                contact.setProdname(cursor.getString(1));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
 

    
    // Getting All Local_Data
    public List<Local_Data> getAllStatebyState_Name(String state_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  STATE FROM " + TABLE_CUSTOMER_MASTER + " WHERE STATE = '" +  state_name + "'" + " GROUP BY STATE ORDER BY STATE";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
   // Getting All Local_Data
    public List<Local_Data> getcityByStateName(String State_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  CITY FROM " + TABLE_CUSTOMER_MASTER + " WHERE STATE = '" +  State_name + "'" + " GROUP BY CITY ORDER BY CITY";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCityName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getcityByState_id(String State_ID) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  name FROM " + TABLE_CITIES + " WHERE state_id = '" +  State_ID + "'" + " GROUP BY name ORDER BY name";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCityName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getcityByState_idn(String State_ID) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  name FROM " + TABLE_CITIES + " WHERE code = '" +  State_ID + "'" + " GROUP BY name ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCityName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getbeatByCityIDn(String City_ID) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT name FROM " + TABLE_BEATS + " WHERE code = '" +  City_ID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCityName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }
    // Getting All Local_Data
    public List<Local_Data> getState_id(String State_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  code FROM " + TABLE_STATES + " WHERE name = '" +  State_name + "'" + " GROUP BY name ORDER BY name";
 
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code FROM states WHERE name = ? GROUP BY name ORDER BY name",
                new String[] { State_name });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setSTATE_ID(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data
    public List<Local_Data> getCity_id(String city_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  code FROM " + TABLE_CITIES + " WHERE name = '" +  city_name + "'" + " GROUP BY name ORDER BY name";
 
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code FROM cities WHERE name = ? GROUP BY name ORDER BY name",
                new String[] { city_name });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCITY_ID(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getBeat_id(String beat_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  code FROM " + TABLE_BEATS + " WHERE name = '" +  beat_name + "'" + " GROUP BY name ORDER BY name";
 
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code FROM beats WHERE name = ? GROUP BY name ORDER BY name",
                new String[] { beat_name });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setBEAT_ID(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data
    public List<Local_Data> getcustomerByCityName(String BEAT_ID) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT CUSTOMER_SHOPNAME FROM " + TABLE_CUSTOMER_MASTER + " WHERE BEAT = '" +  BEAT_ID + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_stocks_product_name(cursor.getString(0));
                //contact.setPURPOSE_ADDRESS(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getAllCustomer() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT CUSTOMER_SHOPNAME FROM " + TABLE_CUSTOMER_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_stocks_product_name(cursor.getString(0));
                //contact.setPURPOSE_ADDRESS(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getCalender_EventValue(String Fromdate,String Event_Type,String delete_flag) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,calender_id,type,from_date,to_date,details,latlon FROM " + TABLE_CALENDER_ENTRIES + " WHERE from_date = '" +  Fromdate + "'" + " AND type = '" +  Event_Type + "'" + " AND delete_flag = '" +  delete_flag + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setcalender_id(cursor.getString(1));
                contact.setcalender_type(cursor.getString(2));
                contact.setfrom_date(cursor.getString(3));
                contact.setto_date(cursor.getString(4));
                contact.setcalender_details(cursor.getString(5));
                contact.setlatlon(cursor.getString(6));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }


    public List<Local_Data> getAllCalender_EventValue() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,calender_id,type,from_date,to_date,details,latlon FROM " + TABLE_CALENDER_ENTRIES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setcalender_id(cursor.getString(1));
                contact.setcalender_type(cursor.getString(2));
                contact.setfrom_date(cursor.getString(3));
                contact.setto_date(cursor.getString(4));
                contact.setcalender_details(cursor.getString(5));
                contact.setlatlon(cursor.getString(6));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getCalender_EventValueReadonly(String detail,String from_date ) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,calender_id,type,from_date,to_date,details,latlon FROM " + TABLE_CALENDER_ENTRIES + " WHERE details = '" +  detail + "'";

      //  + " AND type = '" +  Event_Type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select user_id,calender_id,type,from_date,to_date,details,latlon FROM calender_entries WHERE details = ?;",
                new String[] { detail });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setcalender_id(cursor.getString(1));
                contact.setcalender_type(cursor.getString(2));
                contact.setfrom_date(cursor.getString(3));
                contact.setto_date(cursor.getString(4));
                contact.setcalender_details(cursor.getString(5));
                contact.setlatlon(cursor.getString(6));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getCalender_EventAll(String delete_flag) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,calender_id,type,from_date,to_date,details,latlon FROM " + TABLE_CALENDER_ENTRIES + " WHERE delete_flag = '" +  delete_flag + "'" + " ORDER BY from_date";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setcalender_id(cursor.getString(1));
                contact.setcalender_type(cursor.getString(2));
                contact.setfrom_date(cursor.getString(3));
                contact.setto_date(cursor.getString(4));
                contact.setcalender_details(cursor.getString(5));
                contact.setlatlon(cursor.getString(6));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
       // db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getCalender_EventAllNEW() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,calender_id,type,from_date,to_date,details,delete_flag,latitude,longitude FROM " + TABLE_CALENDER_ENTRIES + " WHERE created_at != '" + "" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setcalender_id(cursor.getString(1));
                contact.setcalender_type(cursor.getString(2));
                contact.setfrom_date(cursor.getString(3));
                contact.setto_date(cursor.getString(4));
                contact.setcalender_details(cursor.getString(5));
                contact.setcalender_delete_flag(cursor.getString(6));
                contact.setlatitude(cursor.getString(7));
                contact.setlongitude(cursor.getString(8));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        // db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getCalender_EventValue_byFDATE(String Fromdate,String delete_flag) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,calender_id,type,from_date,to_date,details,latlon FROM " + TABLE_CALENDER_ENTRIES + " WHERE from_date = '" +  Fromdate + "'" + " AND delete_flag = '" +  delete_flag + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setcalender_id(cursor.getString(1));
                contact.setcalender_type(cursor.getString(2));
                contact.setfrom_date(cursor.getString(3));
                contact.setto_date(cursor.getString(4));
                contact.setcalender_details(cursor.getString(5));
                contact.setlatlon(cursor.getString(6));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getbeatByCityName(String City_name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT BEAT FROM " + TABLE_CUSTOMER_MASTER + " WHERE CITY = '" +  City_name + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setPwd(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getbeatByCityID(String City_ID) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT name FROM " + TABLE_BEATS + " WHERE city_id = '" +  City_ID + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCityName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getwarehouseByCityID(String City_ID) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT name FROM " + TABLE_WAREHOUSE + " WHERE city = '" +  City_ID + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCityName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data
    public List<Local_Data> getwarehouseByname(String name) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT code FROM " + TABLE_WAREHOUSE + " WHERE name = '" +  name + "'";



        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select code FROM warehouse WHERE name = ?;",
                new String[] { name });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setWare_code(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data
    public List<Local_Data> getCity(String city_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT * FROM " + TABLE_CITIES + " WHERE ids = '" +  city_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                contact.setcity_id(cursor.getString(11));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
         db.close();
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getCityByStateID(String namae) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CITIES + " WHERE name = '" +  namae + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select * FROM cities WHERE name = ?;",
                new String[] { namae });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                contact.setcity_id(cursor.getString(5));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
         db.close();
        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data for Feedback
    public List<Local_Data> getAllFeedback() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CUSTOMER_SERVICES_FEEDBACKS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setC_Date(cursor.getString(5));
                contact.set_Description(cursor.getString(6));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for Feedback
    public List<Local_Data> getAllFeedback_BYCUSTOMERID(String customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CUSTOMER_SERVICES_FEEDBACKS + " WHERE customer_id = '" + customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setC_Date(cursor.getString(5));
                contact.set_Description(cursor.getString(6));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for Feedback
    public List<Local_Data> getAllFeedback_CREATEDATE() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,date,text,latitude,longitude,code FROM " + TABLE_CUSTOMER_SERVICES_FEEDBACKS + " WHERE created_at != '" + "" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.setC_Date(cursor.getString(2));
                contact.set_Description(cursor.getString(3));
                contact.setlatitude(cursor.getString(4));
                contact.setlongitude(cursor.getString(5));
                contact.setCode(cursor.getString(6));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data for Variants
    public List<Local_Data> getAllProductPackSizes() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_PACK_SIZES;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_PackSizes_variant_id(cursor.getString(3));
                contact.set_PackSizes_desc(cursor.getString(4));
                contact.set_PackSizes_product_unit(cursor.getString(5));
                contact.set_PackSizes_product_pack_size(cursor.getString(6));
                contact.set_PackSizes_status(cursor.getString(7));
                contact.set_PackSizes_ids(cursor.getString(10));
              
                
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }    
    
 // Getting All Local_Data for Variants
    public List<Local_Data> getAllVariants() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_VARIENTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setC_Date(cursor.getString(5));
                contact.set_Description(cursor.getString(6));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
    
    // Getting All Local_Data for Credit Limit
    public List<Local_Data> getCreditLimit(String retailer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CREDIT_LIMITS + " WHERE retailer_id = '" + retailer_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_credit_limit(cursor.getString(5));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data for Credit Limit
    public List<Local_Data> getCreditprofileData(String customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  credit_limit,amount_outstanding,amount_overdue FROM " + TABLE_CREDIT_PROFILE + " WHERE customer_id = '" + customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_credit_limit(cursor.getString(0));
                contact.set_shedule_outstanding_amount(cursor.getString(1));
                contact.setAmmount_overdue(cursor.getString(2));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data for Credit Limit
    public List<Local_Data> getCreditLimitC(String customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  credit_limit FROM " + TABLE_CREDIT_LIMITS + " WHERE customer_id = '" + customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_credit_limit(cursor.getString(0));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for Credit Limit
    public List<Local_Data> getOUTSTANDINGSC(String customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  amount_outstanding FROM " + TABLE_OUTSTANDINGS + " WHERE customer_id = '" + customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_outstanding_amount(cursor.getString(0));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data for Variants
    public List<Local_Data> getVariants(String product_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_VARIENTS + " WHERE product_id = " + product_id;;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_variants_code(cursor.getString(3));
                contact.set_variants_desc(cursor.getString(4));
                contact.set_variants_MRP(cursor.getString(6));
                contact.set_variants_retail_price(cursor.getString(7));
                contact.set_variants_product_unit(cursor.getString(8));
                contact.set_variants_status(cursor.getString(9));
                contact.set_variants_ids(cursor.getString(12));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for Variants
    public List<Local_Data> getSchedule_List(String customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,delivery_date,customer_id FROM " + TABLE_DELIVERY_SCHEDULES + " WHERE customer_id = '" + customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(0));
                contact.set_shedule_delivery_date(cursor.getString(1));
                contact.setCust_Code(cursor.getString(2));
                //contact.setAddress(cursor.getString(2));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for Variants
    public List<Local_Data> getSchedule_ListAll() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT order_id,delivery_date,c_address,customer_id FROM " + TABLE_DELIVERY_SCHEDULES ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(0));
                contact.set_shedule_delivery_date(cursor.getString(1));
                contact.setADDRESS(cursor.getString(2));
                contact.setCust_Code(cursor.getString(3));
                //contact.setAddress(cursor.getString(2));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data for insertDeliveryScheduleJoin
    public List<Local_Data> getDeliveryScheduleJoin() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_DELIVERY_SCHEDULES + " a INNER JOIN " + TABLE_DELIVERY_PRODUCTS + " b ON a.order_id=b.order_id";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(4));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_delivery_date(cursor.getString(8));
                contact.set_shedule_order_amount(cursor.getString(9));
                contact.set_shedule_payment_mode(cursor.getString(10));
                contact.set_shedule_outstanding_amount(cursor.getString(13));
                
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data for insertDeliveryScheduleJoin
    public List<Local_Data> getDistributerJoin() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  a.name,a.code FROM " + TABLE_DISTRIBUTORS + " a INNER JOIN " + TABLE_DISTRIBUTORBEAT + " b ON a.order_id=b.order_id";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(4));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_delivery_date(cursor.getString(8));
                contact.set_shedule_order_amount(cursor.getString(9));
                contact.set_shedule_payment_mode(cursor.getString(10));
                contact.set_shedule_outstanding_amount(cursor.getString(13));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for insertDeliveryScheduleJoin
    public List<Local_Data> getDeliveryScheduleWITHCUSTOMERJoin() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  a.order_id,a.delivery_date,b.ADDRESS FROM " + TABLE_DELIVERY_SCHEDULES + " a INNER JOIN " + TABLE_CUSTOMER_MASTER + " b ON a.customer_id=b.LEGACY_CUSTOMER_CODE";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(0));
                contact.set_shedule_delivery_date(cursor.getString(1));
                contact.setAddress(cursor.getString(2));
               // contact.set_shedule_depatch_date(cursor.getString(7));
               // contact.set_shedule_depatch_date(cursor.getString(7));
                //contact.set_shedule_delivery_date(cursor.getString(8));
                //contact.set_shedule_order_amount(cursor.getString(9));
               // contact.set_shedule_payment_mode(cursor.getString(10));
               // contact.set_shedule_outstanding_amount(cursor.getString(13));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }




    // Getting All Local_Data for insertDeliveryScheduleJoin
    public List<Local_Data> getcreditlimit_UOTS_Join() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CREDIT_LIMITS + " a INNER JOIN " + TABLE_OUTSTANDINGS + " b ON a.order_id=b.order_id";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(4));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_delivery_date(cursor.getString(8));
                contact.set_shedule_order_amount(cursor.getString(9));
                contact.set_shedule_payment_mode(cursor.getString(10));
                contact.set_shedule_outstanding_amount(cursor.getString(13));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

   // Getting All Local_Data for insertDeliverySchedule
    public List<Local_Data> getDeliverySchedule(String order_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_DELIVERY_SCHEDULES + " WHERE order_id = '" + order_id +"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_shedule_order_id(cursor.getString(4));
                contact.set_shedule_depatch_date(cursor.getString(7));
                contact.set_shedule_delivery_date(cursor.getString(8));
                contact.set_shedule_order_amount(cursor.getString(9));
                contact.set_shedule_payment_mode(cursor.getString(10));
                contact.set_shedule_outstanding_amount(cursor.getString(13));
                
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data for Delivery Products  
    public List<Local_Data> getDeliveryProducts(String order_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  order_id,product_id,order_quantity,delivered_quality,transporter_details,product_name FROM " + TABLE_DELIVERY_PRODUCTS + " WHERE order_id = '" + order_id +"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data(); 
                contact.set_shedule_order_id(cursor.getString(0));
                contact.set_delivery_product_id(cursor.getString(1));
                contact.set_delivery_product_order_quantity(cursor.getString(2));
                contact.set_delivery_product_delivered_quality(cursor.getString(3));
                contact.set_delivery_product_transporter_details(cursor.getString(4));
                contact.set_stocks_product_name(cursor.getString(5));
                
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getVarianty(String Category_text) {
        List<Local_Data> contactList4 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_VARIENTS + " WHERE desc = '" + Category_text + "'";
      //  querySelect = "SELECT name FROM " + TABLE_NAME + " WHERE username = '" + username + "'";
       // String selectQuery1 = "SELECT  * FROM " + TABLE_RETAILERS + " WHERE name = " + RE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select * FROM product_variants WHERE desc = ?;",
                new String[] { Category_text });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                
                Local_Data contact = new Local_Data();
                contact.set_variants_code(cursor.getString(3));
                contact.set_variants_desc(cursor.getString(4));
                contact.set_variants_MRP(cursor.getString(6));
                contact.set_variants_retail_price(cursor.getString(7));
                contact.set_variants_product_unit(cursor.getString(8));
                contact.set_variants_status(cursor.getString(9));
                contact.set_variants_ids(cursor.getString(12));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList4.add(contact);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        // return contact list?
        return contactList4;
    }
    
   // Getting All Local_Data for claims
    public List<Local_Data> getAllClaims() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CUSTOMER_SERVICE_CLAIMS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setC_Date(cursor.getString(6));
                contact.set_Claims(cursor.getString(7));
                contact.set_Claims_amount(cursor.getString(8));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for claims
    public List<Local_Data> getAllClaims_BYCUSTOMERID(String customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CUSTOMER_SERVICE_CLAIMS + " WHERE customer_id = '" + customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setC_Date(cursor.getString(6));
                contact.set_Claims(cursor.getString(7));
                contact.set_Claims_amount(cursor.getString(8));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data for claims
    public List<Local_Data> getAllComplaints() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_CUSTOMER_SERVICES_COMPLAINTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setC_Date(cursor.getString(5));
                contact.set_complaints(cursor.getString(6));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }



    // Getting All Local_Data for claims
    public List<Local_Data> getAllComplaints_BYCUSTOMERID() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,date,text,latitude,longitude,code FROM " + TABLE_CUSTOMER_SERVICES_COMPLAINTS + " WHERE created_at != '" + "" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.setC_Date(cursor.getString(2));
                contact.set_Description(cursor.getString(3));
                contact.setlatitude(cursor.getString(4));
                contact.setlongitude(cursor.getString(5));
                contact.setCode(cursor.getString(6));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data for claims
    public List<Local_Data> getAllComplaints_BYCUSTOMERIDN(String Customer_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,date,text FROM " + TABLE_CUSTOMER_SERVICES_COMPLAINTS + " WHERE customer_id = '" + Customer_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.setC_Date(cursor.getString(2));
                contact.set_complaints(cursor.getString(3));

                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for claims
    public List<Local_Data> getAllClaims_BYCUSTOMERID() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,date,text,amount,latitude,longitude,code FROM " + TABLE_CUSTOMER_SERVICE_CLAIMS + " WHERE created_at != '" + "" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.setC_Date(cursor.getString(2));
                contact.set_Description(cursor.getString(3));
                contact.set_Claims_amount(cursor.getString(4));
                contact.setlatitude(cursor.getString(5));
                contact.setlongitude(cursor.getString(6));
                contact.setCode(cursor.getString(7));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for claims
    public List<Local_Data> getAllPICTURESD_BYCUSTOMERID() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,media_type,media_text,location,latitude,longitude,media_id FROM " + CUSTOMER_SERVICE_MEDIA + " WHERE created_at != '" + "" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.set_mediaType(cursor.getString(2));
                contact.set_mediaUrl(cursor.getString(4));
                contact.set_mediaDisc(cursor.getString(3));


                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for claims
    public List<Local_Data> getAllPICTURESDBYID(String media_id) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,media_type,media_text,location,latitude,longitude,media_id,code FROM " + CUSTOMER_SERVICE_MEDIA + " WHERE media_id = '" + media_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.set_mediaType(cursor.getString(2));
                contact.setcalender_details(cursor.getString(3));
                contact.set_mediaUrl(cursor.getString(4));
                contact.set_mediaDisc(cursor.getString(3));
                contact.setCode(cursor.getString(8));



                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data for claims
    public List<Local_Data> getAllPICTURESDBY(String media_type) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,media_type,media_text,location,latitude,longitude,media_id FROM " + CUSTOMER_SERVICE_MEDIA + " WHERE media_type = '" + media_type + "'";;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.set_mediaType(cursor.getString(2));
                contact.setcalender_details(cursor.getString(3));
                contact.set_mediaUrl(cursor.getString(4));
                contact.set_mediaDisc(cursor.getString(3));
                contact.setmedia_id(cursor.getString(7));


                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }


    // Getting All Local_Data for claims
    public List<Local_Data> getAllCOMPETITION_STOCKS_BYCUSTOMERID() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  customer_id,user_id,category_id,product_id,product_variant_id,competition_product_text,competition_product_name,latitude,longitude,code FROM " + TABLE_CUSTOMER_SERVICE_COMPETITION_STOCKS + " WHERE created_at != '" + "" + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
                contact.setEMAIL_ADDRESS(cursor.getString(1));
                contact.set_category_id(cursor.getString(2));
                contact.set_product_code(cursor.getString(3));
                contact.set_variants_code(cursor.getString(4));
                contact.set_Description(cursor.getString(5));
                contact.set_stocks_product_quantity(cursor.getString(6));
                contact.setlatitude(cursor.getString(7));
                contact.setlongitude(cursor.getString(8));
                contact.setCode(cursor.getString(9));


                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList1;
    }
    
    // Getting All Local_Data for claims
    public List<Local_Data> getAllMediaData() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + CUSTOMER_SERVICE_MEDIA;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_mediaType(cursor.getString(3));
                contact.set_mediaUrl(cursor.getString(6));
                contact.set_mediaDisc(cursor.getString(7));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
// // Getting All Local_Data
//    public List<Local_Data> getcustaddBycustName(String custName,String order_type) {
//        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
//        // Select All Query
//        String selectQuery1 = "SELECT ADDRESS FROM " + TABLE_CUSTOMER_MASTER + " WHERE CUSTOMER_NAME = '" +  custName + "'" + " AND PURPOSE_ADDRESS = '" +  order_type + "'";
// 
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery1, null);
// 
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Local_Data contact = new Local_Data();
//                contact.set_custadr(cursor.getString(0));
//                //contact.setPwd(cursor.getString(2));
//                //contact.setImei(cursor.getString(3));
//                
//                // Adding contact to list
//                contactList1.add(contact);
//            } while (cursor.moveToNext());
//        }
//        
//        db.close();
//        // return contact list?
//        return contactList1;
//    }
    
    // Getting All Local_Data for TravelExpenses
    public List<Local_Data> getAllTravelExpenses() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,travel_from,travel_to,travel_date,travel_mode,travel_cost,travel_text,code FROM " + TABLE_EXPENCES_TRAVELSS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.set_travel_from(cursor.getString(1));
                contact.set_travel_to(cursor.getString(2));
                contact.set_travel_date(cursor.getString(3));
                contact.set_travel_mode(cursor.getString(4));
                contact.set_travel_cost(cursor.getString(5));
                contact.set_travel_text(cursor.getString(6));
                contact.setCode(cursor.getString(7));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
               // Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
 // Getting All Local_Data for MisceExpenses
    public List<Local_Data> getAllMisceExpenses() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,misc_date,misc_amount,misc_text,code FROM " + TABLE_EXPENCES_MISCS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setmisc_date(cursor.getString(1));
                contact.setmisc_cost(cursor.getString(2));
                contact.setmisc_text(cursor.getString(3));
                contact.setCode(cursor.getString(4));
                
                // Adding contact to list
                contactList1.add(contact);
                //Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
    /*// Getting Local_Data for targets
    public List<Local_Data> getTarget(String quarter) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_TARGETS + " WHERE TRIM(quarter_text) = '"+quarter.trim()+"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_Target_Year(cursor.getString(4));
                contact.set_Target_Text(cursor.getString(6));
                contact.set_Target_Achieved(cursor.getString(7));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }*/
    
    // Getting Local_Data for targets
    public List<Local_Data> getTarget() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  year,month,target,achieved FROM " + TABLE_TARGETS + " ORDER BY year DESC";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_Target_Year(cursor.getString(0));
                contact.set_Target_Month(cursor.getString(1));
                contact.set_Target_Text(cursor.getString(2));
                contact.set_Target_Achieved(cursor.getString(3));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
    // Getting Local_Data for targets
    public List<Local_Data> getTargetByYear() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT year,SUM(target) as t_target,SUM(achieved)as t_achieved FROM " + TABLE_TARGETS + " GROUP BY year" + " ORDER BY year DESC"; 
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_Target_Year(cursor.getString(0));
              //  contact.set_Target_Month(cursor.getString(5));
                contact.set_Target_Text(cursor.getString(1));
                contact.set_Target_Achieved(cursor.getString(2));
               
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
                Log.d("ConTACT", "ConTACT"+contactList1);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList1;
    }
    
    
 // Getting All Local_Data
    public List<Local_Data> getAllBeats() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT name FROM " + TABLE_BEATS + " GROUP BY name ORDER BY name";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList1;
    }
    
    
    
//    // Getting All Local_Data
//    public List<Local_Data> getAllBeats() {
//        List<Local_Data> contactList2 = new ArrayList<Local_Data>();
//        // Select All Query
//        String selectQuery1 = "SELECT BEAT FROM " + TABLE_BEATS;
// 
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery1, null);
// 
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Local_Data contact = new Local_Data();
//                contact.setStateName(cursor.getString(0));
//                //contact.setPwd(cursor.getString(2));
//                //contact.setImei(cursor.getString(3));
//                
//                // Adding contact to list
//                contactList2.add(contact);
//            } while (cursor.moveToNext());
//        }
//        
//        db.close();
//        // return contact list?
//        return contactList2;
//    }
    
 // Getting beat id
    public List<Local_Data> getBeats_CITYID(String name) {
        List<Local_Data> contactList2 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_BEATS + " WHERE name = '" + name + "'";;
 
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("select * FROM beats WHERE name = ?;",
                new String[] { name });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(6));
                contact.set_category_id(cursor.getString(12));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList2.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList2;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getAllRetailers() {
        List<Local_Data> contactList3 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_RETAILERS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);



 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(5));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList3.add(contact);
            } while (cursor.moveToNext());
        }
        
        db.close();
        // return contact list?
        return contactList3;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getRetailer(String RE_NAME) {
        List<Local_Data> contactList3 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_RETAILERS + " WHERE shop_name = '" + RE_NAME + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery1, null);


        Cursor cursor = db.rawQuery("select * FROM retailers WHERE shop_name = ?;",
                new String[] { RE_NAME });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_Retailer_id(cursor.getString(22));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList3.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList3;
    }
    
    // Getting All Local_Data
    public List<Local_Data> getAllCategory() {
        List<Local_Data> contactList4 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_CATEG;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(5));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList4.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        cursor.close();
        db.close();
        return contactList4;
    }
    
   // Getting All Local_Data
    public List<Local_Data> getCategoryy(String Category_text) {
        List<Local_Data> contactList4 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_CATEG + " WHERE category_desc = '" + Category_text + "'";
      //  querySelect = "SELECT name FROM " + TABLE_NAME + " WHERE username = '" + username + "'";
       // String selectQuery1 = "SELECT  * FROM " + TABLE_RETAILERS + " WHERE name = " + RE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_id(cursor.getString(3));
                contact.set_category_code(cursor.getString(4));
                contact.set_ccategory_desc(cursor.getString(5));
                contact.set_category_status(cursor.getString(6));
                contact.set_category_id(cursor.getString(3));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList4.add(contact);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        // return contact list?
        return contactList4;
    }
    
   // Getting All Local_Data
    public List<Local_Data> getCategory() {
        List<Local_Data> contactList4 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_CATEG;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_id(cursor.getString(3));
                contact.set_category_code(cursor.getString(4));
                contact.set_ccategory_desc(cursor.getString(5));
                contact.set_category_status(cursor.getString(6));
                contact.set_category_id(cursor.getString(3));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList4.add(contact);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        // return contact list?
        return contactList4;
    }
    
   // Getting All Local_Data
    public List<Local_Data> getAllProduct() {
        List<Local_Data> contactList5 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList5.add(contact);
            } while (cursor.moveToNext());
        }
        
        // return contact list?
        return contactList5;
    }
    
   // / Getting All Local_Data
    public List<Local_Data> getProducty(String Product_text) {
        List<Local_Data> contactList4 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE desc = '" + Product_text + "'";
      //  querySelect = "SELECT name FROM " + TABLE_NAME + " WHERE username = '" + username + "'";
       // String selectQuery1 = "SELECT  * FROM " + TABLE_RETAILERS + " WHERE name = " + RE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_product_code(cursor.getString(3));
                contact.set_product_desc(cursor.getString(4));
                contact.set_product_status(cursor.getString(6));
                contact.set_product_ids(cursor.getString(11));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList4.add(contact);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        // return contact list?
        return contactList4;
    }
    
   // Getting All Local_Data
    public List<Local_Data> getProduct(String Category_id) {
        List<Local_Data> contactList5 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT + " WHERE category_id = " + Category_id ;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_product_code(cursor.getString(3));
                contact.set_product_desc(cursor.getString(4));
                contact.set_product_status(cursor.getString(6));
                contact.set_product_ids(cursor.getString(11));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList5.add(contact);
            } while (cursor.moveToNext());
        }
        
        // return contact list?
        return contactList5;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getAllVarient() {
        List<Local_Data> contactList6 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT  * FROM " + TABLE_PRODUCT_VARIENTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(4));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                contactList6.add(contact);
            } while (cursor.moveToNext());
        }

    // return contact list?
    return contactList6;
}
    
// // Getting All Local_Data
    public List<Local_Data> getRR() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_VARIENTS +" where " + VARANT + "='" + Global_Data.order_variant +"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setRR(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }
    
 // // Getting All Local_Data
    public List<Local_Data> getitem_no() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT code FROM " + TABLE_PRODUCT_VARIENTS +" where " + VARANT + "='" + Global_Data.order_variant +"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setRR(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }
    
// // Getting product data
//    public List<Local_Data> getDistributor() {
//        List<Local_Data> contactList10 = new ArrayList<Local_Data>();
//        // Select All Query
//        String selectQuery = "SELECT  * FROM " + TABLE_DISTRIBUTORS;
// 
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
// 
//        // looping through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Local_Data contact = new Local_Data();
//                contact.setDistr(cursor.getString(4));
//                
//                // Adding contact to list
//                contactList10.add(contact);
//            } while (cursor.moveToNext());
//        }
//        // return contact list?
//        return contactList10;
//    }
    
 // Getting product data
    public List<Local_Data> HSS_DescriptionITEM() {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT primary_category FROM " + TABLE_ITEM_MASTER + "";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
   // Getting product data
    public List<Local_Data> HSS_DescriptionITEM_ID(String categ) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT code FROM " + TABLE_ITEM_MASTER + " WHERE primary_category"+ " ='"+ categ + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);


        Cursor cursor = db.rawQuery("select code FROM item_master WHERE primary_category = ?;",
                new String[] { categ });
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
//                contact.setCategory(cursor.getString(1));
//                contact.set_Description(cursor.getString(2));
//                contact.set_Claims(cursor.getString(3));
//                contact.setProduct_nm(cursor.getString(4));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    
    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM1() {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT sub_category FROM " + TABLE_ITEM_MASTER + "";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM1_category_name(String primary_category) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT DISTINCT sub_category FROM " + TABLE_ITEM_MASTER + " WHERE primary_category"+ " ='"+ primary_category + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select DISTINCT sub_category FROM item_master WHERE primary_category = ?;",
                new String[] { primary_category });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
//                contact.setCategory(cursor.getString(1));
//                contact.set_Description(cursor.getString(2));
//                contact.set_Claims(cursor.getString(3));
//                contact.setProduct_nm(cursor.getString(4));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> VARIENT_ID(String primary_category) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();

        String selectQuery = "SELECT code FROM " + TABLE_ITEM_MASTER + " WHERE product_variant"+ " ='"+ primary_category + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select code FROM item_master WHERE product_variant = ?;",
                new String[] { primary_category });


        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setItem_Code(cursor.getString(0));

                contactList14.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList14;
    }


    // Getting product data
    public List<Local_Data> PRODUCT_ID(String product_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();

        String selectQuery = "SELECT code FROM " + TABLE_ITEM_MASTER + " WHERE name"+ " ='"+ product_name + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select code FROM item_master WHERE name = ?;",
                new String[] { product_name });



        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setItem_Code(cursor.getString(0));

                contactList14.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList14;
    }

    // Getting product data HSS_DescriptionITEM1_ID
    public List<Local_Data> HSS_DescriptionITEM1_ID(String code) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT DISTINCT sub_category FROM " + TABLE_ITEM_MASTER + " WHERE code"+ " ='"+ code + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
//                contact.setCategory(cursor.getString(1));
//                contact.set_Description(cursor.getString(2));
//                contact.set_Claims(cursor.getString(3));
//                contact.setProduct_nm(cursor.getString(4));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM1_IDD(String sub_category) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT DISTINCT code FROM " + TABLE_ITEM_MASTER + " WHERE sub_category"+ " ='"+ sub_category + "'" ;
        
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select DISTINCT code FROM item_master WHERE sub_category = ?;",
                new String[] { sub_category });
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCust_Code(cursor.getString(0));
//                contact.setCategory(cursor.getString(1));
//                contact.set_Description(cursor.getString(2));
//                contact.set_Claims(cursor.getString(3));
//                contact.setProduct_nm(cursor.getString(4));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM2() {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT product_variant FROM " + TABLE_ITEM_MASTER + "";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    
    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM2_ID(String code) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT DISTINCT product_variant FROM " + TABLE_ITEM_MASTER + " WHERE code"+ " ='"+ code + "'" ;
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
//                contact.setCategory(cursor.getString(1));
//                contact.set_Description(cursor.getString(2));
//                contact.set_Claims(cursor.getString(3));
//                contact.setProduct_nm(cursor.getString(4));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM2_BYNAME(String category_name,String product_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT DISTINCT product_variant FROM " + TABLE_ITEM_MASTER + " WHERE primary_category"+ " ='"+ category_name + "'" + " AND sub_category"+ " ='"+ product_name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);


        Cursor cursor = db.rawQuery("select DISTINCT product_variant FROM item_master WHERE primary_category = ? AND sub_category = ?",
                new String[] {category_name,product_name});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
//                contact.setCategory(cursor.getString(1));
//                contact.set_Description(cursor.getString(2));
//                contact.set_Claims(cursor.getString(3));
//                contact.setProduct_nm(cursor.getString(4));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting All Local_Data
    public List<Local_Data> getAllVariant() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT DISTINCT product_variant FROM " + TABLE_ITEM_MASTER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                //contact.setPURPOSE_ADDRESS(cursor.getString(1));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));

                // Adding contact to list
                contactList1.add(contact);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting product data
    public List<Local_Data> HSS_DescriptionITEM2_BYNAMENEW(String category_name,String product_name,String product_variant) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT retail_price,mrp,name  FROM " + TABLE_ITEM_MASTER + " WHERE primary_category"+ " ='"+ category_name + "'" + " AND sub_category"+ " ='"+ product_name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select retail_price,mrp,name FROM item_master WHERE primary_category = ? AND sub_category = ? AND product_variant = ?",
                new String[] {category_name,product_name,product_variant});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setRP(cursor.getString(0));
                contact.setMRP(cursor.getString(1));
                contact.set_product_desc(cursor.getString(2));
//
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> getBusinee_category_Name(String business_unit,String primary_categore) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);


        Cursor cursor = db.rawQuery("select DISTINCT b_business_c FROM item_master WHERE b_unit = ? AND primary_category = ?",
                new String[] {business_unit,primary_categore});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setbusiness_category(cursor.getString(0));
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> getBusinee_subcategory_Name(String business_unit,String primary_categore,String business_category) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);


        Cursor cursor = db.rawQuery("select DISTINCT sub_category FROM item_master WHERE b_unit = ? AND primary_category = ? AND b_business_c = ?",
                new String[] {business_unit,primary_categore,business_category});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setSubcateg(cursor.getString(0));
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    
    // Getting product data
    public List<Local_Data> getProductByCat(String categ) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT retail_price, mrp, qualifying_qty, free_qty, name,code,primary_category,sub_category FROM " + TABLE_ITEM_MASTER + " WHERE product_variant"+ " ='"+ categ + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select retail_price, mrp, qualifying_qty, free_qty, name,code,primary_category,sub_category FROM item_master WHERE product_variant = ?;",
                new String[] { categ });
        

 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                contact.setMRP(cursor.getString(1));
                contact.set_Description(cursor.getString(2));
                contact.set_Claims(cursor.getString(3));
                contact.setProduct_nm(cursor.getString(4));
                contact.setCode(cursor.getString(5));
                contact.setCategory(cursor.getString(6));
                contact.setSubcateg(cursor.getString(7));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> getProductscheme_Name(String product_code) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT code,name,type,description,display_name FROM " + TABLE_ITEM_SCHEME_NEW + " WHERE product_id"+ " ='"+ product_code + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCode(cursor.getString(0));
                contact.setSche_name(cursor.getString(1));
                contact.setSche_type(cursor.getString(2));
                contact.setSche_discription(cursor.getString(3));
                contact.setSche_disname(cursor.getString(4));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> getProductscheme_code(String display_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT sub_category FROM " + TABLE_ITEM_MASTER + "";
        //String selectQuery = "SELECT mrp FROM " + TABLE_ITEM_MASTER + " WHERE product_variant = " + categ ;
        String selectQuery = "SELECT code FROM " + TABLE_ITEM_SCHEME_NEW + " WHERE display_name"+ " ='"+ display_name + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select code FROM scheme_new WHERE display_name = ?;",
                new String[] { display_name });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setCode(cursor.getString(0));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    
   // Getting product data
    public List<Local_Data> Primary_CategoryITEM(String categary_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  Primary_Category FROM " + TABLE_ITEM_MASTER + " WHERE HSS_Description"+ " ='"+ categary_name + "'" + " GROUP BY Primary_Category ORDER BY Primary_Category";
 
        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select Primary_Category FROM item_master WHERE HSS_Description = ? GROUP BY Primary_Category ORDER BY Primary_Category",
                new String[] {categary_name});
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting product data
    public List<Local_Data> Primary_SubCategoryITEM(String pcategary_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT Secondary_Category,Secondary_Sub_Category,Size1,Size2,Voltage_Watts_Amps,Colour,Metal_Aluminum_Wt,Metal_Copper_Wt,Product_Weight,Bending_Radius,PLANNING_MAKE_BUY_CODE FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Category"+ " ='"+ pcategary_name + "'" + " GROUP BY Primary_Category ORDER BY Primary_Category";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_travel_text(cursor.getString(0));
                contact.set_category_code(cursor.getString(1));
                contact.set_ccategory_desc(cursor.getString(2));
                contact.set_category_ids(cursor.getString(3));
                contact.set_product_code(cursor.getString(4));
                contact.set_product_desc(cursor.getString(5));
                contact.set_product_status(cursor.getString(6));
                contact.set_variants_code(cursor.getString(7));
                contact.set_variants_desc(cursor.getString(8));
                contact.set_variants_MRP(cursor.getString(9));
                contact.set_variants_retail_price(cursor.getString(10));
                
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting product data
    public List<Local_Data> size(String pcategary_name,String invoice_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT "+ pcategary_name  + " FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ invoice_name + "'" + " GROUP BY '"+ pcategary_name + "'" + " ORDER BY '"+ pcategary_name + "'";
        String selectQuery = "SELECT "+ pcategary_name  + " FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ invoice_name + "'" + " ORDER BY '"+ pcategary_name + "'";;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_travel_text(cursor.getString(0));
               
                
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
   // Getting product data
    public List<Local_Data> getItemProductData(String pcategary_name,String invoice_name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        //String selectQuery = "SELECT "+ pcategary_name  + " FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ invoice_name + "'" + " GROUP BY '"+ pcategary_name + "'" + " ORDER BY '"+ pcategary_name + "'";
 
        String selectQuery = "SELECT "+ pcategary_name  + " FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ invoice_name + "'" + " ORDER BY '"+ pcategary_name + "'";;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_travel_text(cursor.getString(0));
               
                
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
   // Getting product data
    public List<Local_Data> ITEM_List(String subcategary) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  Unit_Rate,Primary_Sub_Category FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Category"+ " ='"+ subcategary + "'" + " GROUP BY Primary_Sub_Category ORDER BY Primary_Sub_Category";
 
        SQLiteDatabase db = this.getWritableDatabase();
      //  Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select  Unit_Rate,Primary_Sub_Category FROM item_master WHERE Primary_Category = ? GROUP BY Primary_Sub_Category ORDER BY Primary_Sub_Category",
                new String[] {subcategary});
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVariant(cursor.getString(0));
                contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> ITEM_List_INVOICE(String subcategary) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
       // String selectQuery = "SELECT Invoice_Description1,Item_Number FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ subcategary + "'" + " GROUP BY Invoice_Description1 ORDER BY Invoice_Description1";
 
        String selectQuery = "SELECT Invoice_Description1,Item_Number FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ subcategary + "'" + " ORDER BY Invoice_Description1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVariant(cursor.getString(0));
                contact.setImei(cursor.getString(1));
                //contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting product data
    public List<Local_Data> ITEM_List_INVOICEBYFILTER(String subcategary,String Secondary_Category_f,String Secondary_Sub_Category_f,String Size1_f,String Size2_f,String Voltage_Watts_Amps_f,String Colour_f,String Metal_Aluminum_Wt_f,String Metal_Copper_Wt_f,String Product_Weight_f,String Bending_Radius_f,String PLANNING_MAKE_BUY_CODE_f) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
  
        String selectQuery = "SELECT Invoice_Description1,Item_Number FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " = '"+ subcategary + "'";
         if(!Secondary_Category_f.equalsIgnoreCase(""))
         {
        	 if(Secondary_Category_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Secondary_Category_f.split(",");
					selectQuery += " AND Secondary_Category IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Secondary_Category IN ('" +  Secondary_Category_f +"')";
				}
 
         }
         
         if(!Secondary_Sub_Category_f.equalsIgnoreCase(""))
         {
        	 if(Secondary_Sub_Category_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Secondary_Sub_Category_f.split(",");
					selectQuery += " AND Secondary_Sub_Category IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Secondary_Sub_Category IN ('" +  Secondary_Sub_Category_f +"')";
				}
 
         }
         
         if(!Size1_f.equalsIgnoreCase(""))
         {
        	 if(Size1_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Size1_f.split(",");
					selectQuery += " AND Size1 IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Size1 IN ('" +  Size1_f +"')";
				}
	     }
         
         if(!Size2_f.equalsIgnoreCase(""))
         {
        	 if(Size2_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Size2_f.split(",");
					selectQuery += " AND Size2 IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Size2 IN ('" +  Size2_f +"')";
				}
 
         }
         
         if(!Voltage_Watts_Amps_f.equalsIgnoreCase(""))
         {
        	 if(Voltage_Watts_Amps_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Voltage_Watts_Amps_f.split(",");
					selectQuery += " AND Voltage_Watts_Amps IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Voltage_Watts_Amps IN ('" +  Voltage_Watts_Amps_f +"')";
				}
 
         }
         
         if(!Colour_f.equalsIgnoreCase(""))
         {
        	 if(Colour_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Colour_f.split(",");
					selectQuery += " AND Colour IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Colour IN ('" +  Colour_f +"')";
				}
 
         }
         
         if(!Metal_Aluminum_Wt_f.equalsIgnoreCase(""))
         {
        	 if(Metal_Aluminum_Wt_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Metal_Aluminum_Wt_f.split(",");
					selectQuery += " AND Metal_Aluminum_Wt IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Metal_Aluminum_Wt IN ('" +  Metal_Aluminum_Wt_f +"')";
				}
 
         }
         
         if(!Metal_Copper_Wt_f.equalsIgnoreCase(""))
         {
        	 if(Metal_Copper_Wt_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Metal_Copper_Wt_f.split(",");
					selectQuery += " AND Metal_Copper_Wt IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Metal_Copper_Wt IN ('" +  Metal_Copper_Wt_f +"')";
				}
 
         }
         
         if(!Product_Weight_f.equalsIgnoreCase(""))
         {
        	 if(Product_Weight_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Product_Weight_f.split(",");
					selectQuery += " AND Product_Weight IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Product_Weight IN ('" +  Product_Weight_f +"')";
				}
 
         }
         
         if(!Bending_Radius_f.equalsIgnoreCase(""))
         {
        	 if(Bending_Radius_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = Bending_Radius_f.split(",");
					selectQuery += " AND Bending_Radius IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND Bending_Radius IN ('" +  Bending_Radius_f +"')";
				}
 
         }
         
         if(!PLANNING_MAKE_BUY_CODE_f.equalsIgnoreCase(""))
         {
        	 if(PLANNING_MAKE_BUY_CODE_f.indexOf(",") > 0)
				{
					//temp = new JSONArray(Size1_f);
					String[]  mArray = PLANNING_MAKE_BUY_CODE_f.split(",");
					selectQuery += " AND PLANNING_MAKE_BUY_CODE IN ("; 
					for(int i=0;i<mArray.length;i++)
					{
						selectQuery=selectQuery+"'"+mArray[i]+"'";
						if(i<mArray.length-1)
						{
							selectQuery=selectQuery+",";    //No , after last values
						} 

				   }
					selectQuery+=")";
				}
				else
				{
					selectQuery += " AND PLANNING_MAKE_BUY_CODE IN ('" +  PLANNING_MAKE_BUY_CODE_f +"')";
				}
 
         }
         
         selectQuery += " ORDER BY Invoice_Description1";
         
         //String selectQuery = "SELECT Invoice_Description1 FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " = '"+ subcategary + "'"+ " AND Secondary_Category IN ('" +  Secondary_Category_f +"')" + " AND Secondary_Sub_Category IN ('" +  Secondary_Sub_Category_f +"')" + " AND Size1 IN ('" +  Size1_f +"')" + " AND Size2 IN ('" +  Size2_f +"')" + " AND Voltage_Watts_Amps IN ('" +  Voltage_Watts_Amps_f +"')" + " AND Colour IN ('" +  Colour_f +"')" + " AND Metal_Aluminum_Wt IN ('" +  Metal_Aluminum_Wt_f +"')" + " AND Metal_Copper_Wt IN ('" +  Metal_Copper_Wt_f +"')" + " AND Product_Weight IN ('" +  Product_Weight_f +"')" + " AND Bending_Radius IN ('" +  Bending_Radius_f +"')" + " AND PLANNING_MAKE_BUY_CODE IN ('" +  PLANNING_MAKE_BUY_CODE_f +"')" +" GROUP BY Invoice_Description1 ORDER BY Invoice_Description1";

        //String selectQuery = "SELECT Invoice_Description1 FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ subcategary + "'";
        //String selectQuery = "SELECT Invoice_Description1 FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " = '"+ subcategary + "'"+ " AND Secondary_Category IN ('" +  Secondary_Category_f +"')";
        //String selectQuery = "SELECT Invoice_Description1 FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " = '"+ subcategary + "'"+ " AND Secondary_Category IN ('" +  Secondary_Category_f +"')";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVariant(cursor.getString(0));
                contact.setImei(cursor.getString(1));
                //contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> ITEM_description(String subcategary) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  Long_Description,Primary_Sub_Category FROM " + TABLE_ITEM_MASTER + " WHERE Primary_Sub_Category"+ " ='"+ subcategary + "'" + " GROUP BY Primary_Sub_Category ORDER BY Primary_Sub_Category";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVariant(cursor.getString(0));
                contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> GetOrders(String order_type,String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT order_id,customer_name,order_type,customer_id,signature_path,distributor_id,latitude,longitude,getsign_img,details1,details2,details3,order_category,shipmet_priority,details4,asset_code FROM " + TABLE_ORDERS + " WHERE order_type"+ " ='"+ order_type + "'" + " AND order_id " + " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.set_category_id(cursor.getString(3));
                contact.setSignature_image(cursor.getString(4));
                contact.setDISTRIBUTER_ID(cursor.getString(5));
                contact.setlatitude(cursor.getString(6));
                contact.setlongitude(cursor.getString(7));
                contact.setimg_ordersign(cursor.getString(8));
                contact.setOrder_detail1(cursor.getString(9));
                contact.setOrder_detail2(cursor.getString(10));
                contact.setOrder_detail3(cursor.getString(11));
                contact.setOrder_category_type(cursor.getString(12));
                contact.setshipment_pri(cursor.getString(13));
                contact.setOrder_detail4(cursor.getString(14));
                contact.setAsset_code(cursor.getString(15));
                //contact.setStateName(cursor.getString(1));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> GetOrders_details(String order_type,String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT details1,details2,details3,order_category,shipmet_priority,details4,asset_code FROM " + TABLE_ORDERS + " WHERE order_type"+ " ='"+ order_type + "'" + " AND order_id " + " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrder_detail1(cursor.getString(0));
                contact.setOrder_detail2(cursor.getString(1));
                contact.setOrder_detail3(cursor.getString(2));
                contact.setOrder_category_type(cursor.getString(3));
                contact.setshipment_pri(cursor.getString(4));
                contact.setOrder_detail4(cursor.getString(5));
                contact.setAsset_code(cursor.getString(6));
                //contact.setStateName(cursor.getString(1));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> GetreturnOrders_beatID(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT beat_idnew FROM " + TABLE_RETURNORDERNEW + " WHERE order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setBEAT_ID(cursor.getString(0));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> GetOrders_beatID(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT beat_idnew FROM " + TABLE_ORDERS + " WHERE order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setBEAT_ID(cursor.getString(0));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> GetOrdersBYORDER_ID(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,signature_path,distributor_id FROM " + TABLE_ORDERS + " WHERE order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.setLEGACY_CUSTOMER_CODE(cursor.getString(3));
                contact.setSignature_image(cursor.getString(4));
                contact.setDISTRIBUTER_ID(cursor.getString(5));
                //contact.setStateName(cursor.getString(1));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> GetOrdersBYORDER_IDRETURN(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,signature_path,distributor_id FROM " + TABLE_RETURNORDERNEW + " WHERE order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.setLEGACY_CUSTOMER_CODE(cursor.getString(3));
                contact.setSignature_image(cursor.getString(4));
                contact.setDISTRIBUTER_ID(cursor.getString(5));
                //contact.setStateName(cursor.getString(1));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> GetAllOrders(String order_type) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String s= "";
        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,signature_path,distributor_id,user_id,latitude,longitude,getsign_img,details1,details2,details3,order_category,shipmet_priority,details4,asset_code FROM " + TABLE_ORDERS + " WHERE order_type"+ " ='"+ order_type + "'" + " AND shipmet_priority " + " !='"+ s + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.set_category_id(cursor.getString(3));
                contact.setSignature_image(cursor.getString(4));
                contact.setDISTRIBUTER_ID(cursor.getString(5));
                contact.setuser_email(cursor.getString(6));
                contact.setlatitude(cursor.getString(7));
                contact.setlongitude(cursor.getString(8));
                contact.setimg_ordersign(cursor.getString(9));
                contact.setOrder_detail1(cursor.getString(10));
                contact.setOrder_detail2(cursor.getString(11));
                contact.setOrder_detail3(cursor.getString(12));
                contact.setOrder_category_type(cursor.getString(13));
                contact.setshipment_pri(cursor.getString(14));
                contact.setOrder_detail4(cursor.getString(15));
                contact.setOrder_detail4(cursor.getString(16));
                //contact.setStateName(cursor.getString(1));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting product data
    public List<Local_Data> GetOrders_return(String order_type,String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,signature_path,distributor_id,latitude,longitude FROM " + TABLE_RETURNORDERNEW + " WHERE order_type"+ " ='"+ order_type + "'" + " AND order_id " + " ='"+ order_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.set_category_id(cursor.getString(3));
                contact.setSignature_image(cursor.getString(4));
                contact.setDISTRIBUTER_ID(cursor.getString(5));
                contact.setlatitude(cursor.getString(6));
                contact.setlongitude(cursor.getString(7));
                //contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> GetOrders_return_All(String order_type) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,signature_path,distributor_id,latitude,longitude FROM " + TABLE_RETURNORDERNEW + " WHERE order_type"+ " ='"+ order_type + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.set_category_id(cursor.getString(3));
                contact.setSignature_image(cursor.getString(4));
                contact.setDISTRIBUTER_ID(cursor.getString(5));
                contact.setlatitude(cursor.getString(6));
                contact.setlongitude(cursor.getString(7));
                //contact.setStateName(cursor.getString(1));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 
    // Getting product data
    public List<Local_Data> GetPREVIOUSOrders_BY_ORDER_ID(String order_id,String item_number) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount FROM " + TABLE_PREVIOUS_ORDERS + " WHERE order_id"+ " ='"+ order_id + "'" + " AND item_number"+ " ='"+ item_number + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> GetOrders_BY_ORDER_ID(String order_id,String item_number) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount FROM " + TABLE_ORDER_PRODUCTS + " WHERE order_id"+ " ='"+ order_id + "'" + " AND item_number"+ " ='"+ item_number + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        try {

            cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Local_Data contact = new Local_Data();
                    contact.set_category_code(cursor.getString(0));
                    contactList14.add(contact);
                } while (cursor.moveToNext());
            }

        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }

        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> GetOrder_Product_BY_ORDER_ID(String order_id,String item_number) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT total_qty,amount FROM " + TABLE_ORDER_PRODUCTS + " WHERE order_id"+ " ='"+ order_id + "'" + " AND item_number"+ " ='"+ item_number + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor =  db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    Local_Data contact = new Local_Data();
                    contact.set_delivery_product_order_quantity(cursor.getString(0));
                    contact.setAmount(cursor.getString(1));
                    contactList14.add(contact);
                } while (cursor.moveToNext());
            }
        } finally {

            if(cursor != null)
                cursor.close();
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting product data
    public List<Local_Data> GetOrders_BY_ORDER_ID_RETURN(String order_id,String item_number) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount FROM " + TABLE_RETURNORDERNEW_PRODUCT + " WHERE order_id"+ " ='"+ order_id + "'" + " AND item_number"+ " ='"+ item_number + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getOrderId(String retailer) {
        List<Local_Data> contactList = new ArrayList<Local_Data>(); 
        
        // Select All Query
        String selectQuery = "SELECT order_id FROM " + TABLE_ORDERS + " WHERE "+ RET_ID + "='" + retailer +"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrderId(cursor.getString(0));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }
    
    // Getting All Local_Data
    public List<Local_Data> getItemName(String orderid) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();  
        // Select All Query
        String selectQuery = "SELECT product_id,total_qty,MRP,amount,item_number,product_name FROM " + TABLE_ORDER_PRODUCTS + " WHERE " + ORDER_ID +"='" + orderid +"'";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";
            if (cursor.moveToFirst()) {
                do {
                    Local_Data contact = new Local_Data();
                    contact.setProductId(cursor.getString(0));
                    contact.setQty(cursor.getString(1));
                    contact.setPrice(cursor.getString(2));
                    contact.setAmount(cursor.getString(3));
                    contact.set_category_ids(cursor.getString(4));
                    contact.setProduct_nm(cursor.getString(5));

                    // Adding contact to list

                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }

       // Cursor cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";

        // looping through all rows and adding to list // " WHERE order_id = " + orderid ;


        // return contact list?
        return contactList;
    }

    // Getting All Local_Data
    public List<Local_Data> getItemNamePrevious_Order(String orderid) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT product_id,total_qty,MRP,amount,item_number,product_name FROM " + TABLE_PREVIOUS_ORDER_PRODUCTS + " WHERE " + ORDER_ID +"='" + orderid +"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";

        // looping through all rows and adding to list // " WHERE order_id = " + orderid ;
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setProductId(cursor.getString(0));
                contact.setQty(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setAmount(cursor.getString(3));
                contact.set_category_ids(cursor.getString(4));
                contact.setProduct_nm(cursor.getString(5));

                // Adding contact to list

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data
    public List<Local_Data> getItemNamePrevious_OrderRecordCheck() {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT product_id,total_qty,MRP,amount,item_number,product_name FROM " + TABLE_PREVIOUS_ORDER_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";

        // looping through all rows and adding to list // " WHERE order_id = " + orderid ;
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setProductId(cursor.getString(0));
                contact.setQty(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setAmount(cursor.getString(3));
                contact.set_category_ids(cursor.getString(4));
                contact.setProduct_nm(cursor.getString(5));

                // Adding contact to list

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }

    // Getting All Local_Data
    public List<Local_Data> getItemNamePrevious_OrderCheck(String orderid,String item_number) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT product_id,total_qty,MRP,amount,item_number,product_name,retail_price,scheme_id FROM " + TABLE_PREVIOUS_ORDER_PRODUCTS + " WHERE " + ORDER_ID +"='" + orderid +"'" + " AND item_number"+ " !='" + item_number + "'";;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = null;
        try {

            cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";

            // looping through all rows and adding to list // " WHERE order_id = " + orderid ;
            if (cursor.moveToFirst()) {
                do {
                    Local_Data contact = new Local_Data();
                    contact.setProductId(cursor.getString(0));
                    contact.setQty(cursor.getString(1));
                    contact.setPrice(cursor.getString(2));
                    contact.setAmount(cursor.getString(3));
                    contact.set_category_ids(cursor.getString(4));
                    contact.setProduct_nm(cursor.getString(5));
                    contact.setRP(cursor.getString(6));
                    contact.setSche_code(cursor.getString(7));

                    // Adding contact to list

                    contactList.add(contact);
                } while (cursor.moveToNext());
            }
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }


        // return contact list?
        return contactList;
    }


    // Getting All Local_Data
    public List<Local_Data> getItemNamePrevious_OrderChecknew(String orderid) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT product_id,total_qty,MRP,amount,item_number,product_name,retail_price,scheme_id FROM " + TABLE_PREVIOUS_ORDER_PRODUCTS + " WHERE " + ORDER_ID +"='" + orderid +"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";

        // looping through all rows and adding to list // " WHERE order_id = " + orderid ;
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setProductId(cursor.getString(0));
                contact.setQty(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setAmount(cursor.getString(3));
                contact.set_category_ids(cursor.getString(4));
                contact.setProduct_nm(cursor.getString(5));
                contact.setRP(cursor.getString(6));
                contact.setSche_code(cursor.getString(7));

                // Adding contact to list

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }





    
 // Getting All Local_Data
    public List<Local_Data> getItemName_return(String orderid) {
        List<Local_Data> contactList = new ArrayList<Local_Data>();  
        // Select All Query
        String selectQuery = "SELECT product_id,total_qty,MRP,amount,item_number,product_name FROM " + TABLE_RETURNORDERNEW_PRODUCT + " WHERE " + ORDER_ID +"='" + orderid +"'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);// " WHERE " + ORDER_ID +"='" + orderid +"'";

        // looping through all rows and adding to list // " WHERE order_id = " + orderid ;
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setProductId(cursor.getString(0));
                contact.setQty(cursor.getString(1));
                contact.setPrice(cursor.getString(2));
                contact.setAmount(cursor.getString(3)); 
                contact.set_category_ids(cursor.getString(4)); 
                contact.setProduct_nm(cursor.getString(5)); 
                
                // Adding contact to list
                
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list?
        return contactList;
    }
    
    
 // Getting product data
    public List<Local_Data> GetOrdersInSI(String Customer_name,String Order_id,String order_type) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,latitude,longitude FROM " + TABLE_ORDERS + " WHERE customer_name"+ " ='"+ Customer_name + "'" + " AND order_type " + " ='"+ order_type + "'"+ " AND order_id"+ " ='" + Order_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select order_id,customer_name,order_type,customer_id,latitude,longitude FROM orders WHERE customer_name = ? AND order_type = ? AND order_id = ?",
                new String[] {Customer_name,order_type,Order_id});
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_shedule_payment_mode(cursor.getString(2));
                contact.set_category_id(cursor.getString(3));
                contact.setlatitude(cursor.getString(4));
                contact.setlongitude(cursor.getString(5));
                //contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> checkOrderExist(String Customer_id,String Order_id) {
    List<Local_Data> contactList14 = new ArrayList<Local_Data>();
    // Select All Query
//        String selectQuery = "SELECT  order_id,customer_name,order_type,customer_id,latitude,longitude FROM " + TABLE_ORDERS + " WHERE customer_name"+ " ='"+ Customer_name + "'" + " AND order_type " + " ='"+ order_type + "'"+ " AND order_id"+ " ='" + Order_id + "'";

    SQLiteDatabase db = this.getWritableDatabase();
    //Cursor cursor = db.rawQuery(selectQuery, null);

    Cursor cursor = null;
    try {

        cursor = db.rawQuery("select order_id FROM orders WHERE customer_id = ? AND order_id = ?",
                new String[] {Customer_id,Order_id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));

                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
    }
    finally {
        // this gets called even if there is an exception somewhere above
        if(cursor != null)
            cursor.close();
    }


    // return contact list?
    return contactList14;
}
    
 // Getting product data
    public List<Local_Data> Getcustomer_email(String Customer_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT EMAIL_ADDRESS FROM " + TABLE_CUSTOMER_MASTER + " WHERE LEGACY_CUSTOMER_CODE "+ " ='"+ Customer_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_Description(cursor.getString(0));
                
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> Get_OrderProducts(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount,scheme_id FROM " + TABLE_ORDER_PRODUCTS + " WHERE order_id"+ " ='"+ order_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_stocks_product_quantity(cursor.getString(2));
                contact.setMRP(cursor.getString(3));
                contact.set_Claims_amount(cursor.getString(4));
                contact.set_Target_Text(cursor.getString(5));
                contact.set_delivery_product_id(cursor.getString(6));
                contact.set_stocks_product_text(cursor.getString(7));
                contact.setSche_code(cursor.getString(8));


                //contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> Get_OrderProducts_scheme(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT scheme_id FROM " + TABLE_ORDER_PRODUCTS + " WHERE order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setSche_code(cursor.getString(0));

                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    
    // Getting product data
    public List<Local_Data> Get_OrderProducts_return(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount FROM " + TABLE_RETURNORDERNEW_PRODUCT + " WHERE order_id"+ " ='"+ order_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_stocks_product_quantity(cursor.getString(2));
                contact.setMRP(cursor.getString(3));
                contact.set_Claims_amount(cursor.getString(4));
                contact.set_Target_Text(cursor.getString(5));
                contact.set_delivery_product_id(cursor.getString(6));
                contact.set_stocks_product_text(cursor.getString(7));
                //contact.setStateName(cursor.getString(1));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    // Getting product data
    public List<Local_Data> Get_OrderProducts_BYITEM_NUMBER(String Item_id,String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount,product_name,retail_price,scheme_id FROM " + TABLE_ORDER_PRODUCTS + " WHERE item_number"+ " ='"+ Item_id + "'" + " AND order_id"+ " ='"+ order_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_stocks_product_quantity(cursor.getString(2));
                contact.setMRP(cursor.getString(3));
                contact.set_Claims_amount(cursor.getString(4));
                contact.set_Target_Text(cursor.getString(5));
                contact.set_delivery_product_id(cursor.getString(6));
                contact.set_stocks_product_text(cursor.getString(7));
                contact.set_product_status(cursor.getString(8));
                contact.setRP(cursor.getString(9));
                contact.setSche_code(cursor.getString(10));

                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> Get_OrderProducts_BYITEM_NUMBERPre(String Item_id,String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount,product_name,retail_price,scheme_id FROM " + TABLE_PREVIOUS_ORDER_PRODUCTS + " WHERE item_number"+ " ='"+ Item_id + "'" + " AND order_id"+ " ='"+ order_id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_stocks_product_quantity(cursor.getString(2));
                contact.setMRP(cursor.getString(3));
                contact.set_Claims_amount(cursor.getString(4));
                contact.set_Target_Text(cursor.getString(5));
                contact.set_delivery_product_id(cursor.getString(6));
                contact.set_stocks_product_text(cursor.getString(7));
                contact.set_product_status(cursor.getString(8));
                contact.setRP(cursor.getString(9));
                contact.setSche_code(cursor.getString(10));

                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting product data
    public List<Local_Data> Get_SURVEY_QUESTIONS() {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT survey_code,question_code,active_from,active_to,question,option_1,option_2,option_3,option_4,option_5 FROM " + TABLE_SURVEY_QUESTIONS + " GROUP BY question";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setsurvey_code(cursor.getString(0));
                contact.setquestion_code(cursor.getString(1));
                contact.setactive_from(cursor.getString(2));
                contact.setactive_to(cursor.getString(3));
                contact.setquestion(cursor.getString(4));
                contact.setoption_1(cursor.getString(5));
                contact.setoption_2(cursor.getString(6));
                contact.setoption_3(cursor.getString(7));
                contact.setoption_4(cursor.getString(8));
                contact.setoption_5(cursor.getString(9));


                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> Get_SURVEY_ANSWERS() {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT user_name,answer_date,survey_code,customer_code,question_code,customer_choice,latitude,longitude,code FROM " + TABLE_SURVEY_ANSWERS ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setuser_email(cursor.getString(0));
                contact.setanswer_date(cursor.getString(1));
                contact.setsurvey_code(cursor.getString(2));
                contact.setCust_Code(cursor.getString(3));
                contact.setquestion_code(cursor.getString(4));
                contact.setcustomer_choice(cursor.getString(5));
                contact.setlatitude(cursor.getString(6));
                contact.setlongitude(cursor.getString(7));
                contact.setCode(cursor.getString(8));



                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    
 // Getting product data
    public List<Local_Data> Get_OrderProducts_BYITEM_NUMBER_RETURN(String Item_id,String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT order_id,customer_name,total_qty,MRP,amount,scheme_amount,item_number,actual_discount,product_name,retail_price FROM " + TABLE_RETURNORDERNEW_PRODUCT + " WHERE item_number"+ " ='"+ Item_id + "'" + " AND order_id"+ " ='"+ order_id + "'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.set_category_code(cursor.getString(0));
                contact.set_custadr(cursor.getString(1));
                contact.set_stocks_product_quantity(cursor.getString(2));
                contact.setMRP(cursor.getString(3));
                contact.set_Claims_amount(cursor.getString(4));
                contact.set_Target_Text(cursor.getString(5));
                contact.set_delivery_product_id(cursor.getString(6));
                contact.set_stocks_product_text(cursor.getString(7));
                contact.set_product_status(cursor.getString(8));
                contact.setRP(cursor.getString(9));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    public void update_item(String total_qty,String MRP,String amount,String scheme_amount,String actual_discount,String item_no,String order_id,String scheme_id) {

        String selectQuery = "";
        SQLiteDatabase db= this.getWritableDatabase();
        db.beginTransactionNonExclusive();
        
        selectQuery = "UPDATE " + TABLE_ORDER_PRODUCTS + " SET total_qty = '"  +  total_qty + "'," + "amount = '"  +  amount + "'," + "scheme_id = '"  +  scheme_id + "'," + "actual_discount = '"  +  actual_discount + "'" + " WHERE item_number = '" +  item_no    + "'" + " AND order_id"+ " ='"+ order_id + "'";
        db.execSQL(selectQuery);
        
        db.setTransactionSuccessful();
        db.endTransaction();
   }

    public void updateCustomerby_Createid(String CODE)
    {

        String selectQuery = "UPDATE " + TABLE_CUSTOMER_MASTER + " SET created_at = '"  +  " " + "'" + " WHERE LEGACY_CUSTOMER_CODE = '" +  CODE    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
       // db.close();
    }


    public void updateORDER_SIGNATURE(String signature,String order_id)
    {

        String selectQuery = "UPDATE " + TABLE_ORDERS + " SET signature_path = '" +  signature  + "'" + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateUserEMPNO_BY_EMILID(String emp_code,String email_id,String address)
    {

       // address = address.replaceAll("'","\'");



        //String selectQuery = "UPDATE " + TABLE_REG + " SET emp_code = '" +  emp_code  + "'," + "city_id = '"  +  address + "'" + " WHERE email_id = '" +  email_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        //db.execSQL(selectQuery);

        ContentValues cv = new ContentValues();
        cv.put("emp_code",emp_code); //These Fields should be your String values of actual column names
        cv.put("city_id",address);
        db.update(TABLE_REG, cv, "email_id" + " = ?", new String[]{email_id});


        // db.close();
    }


    public void updateORDER_SIGNATURENEW(String signature,String order_id,String order_detail1_text,String order_detail2_text,String details3,String order_detail4_text,String order_category,String shipment_pri,String asset_code)
    {
        String   selectQuery = "UPDATE " + TABLE_ORDERS + " SET signature_path = '"  +  signature + "'," + "details1 = '"  +  order_detail1_text + "'," + "details2 = '"  +  order_detail2_text + "'," + "details3 = '"  +  details3 + "'," + "details4 = '"  +  order_detail4_text + "'," + "order_category = '"  +  order_category + "'," + "shipmet_priority = '"  +  shipment_pri + "'," + "asset_code = '"  +  asset_code + "'" + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_SIGNATURENEW_WITHLATLONG(String signature,String order_id,String order_detail1_text,String order_detail2_text,String details3,String order_detail4_text,String order_category,String shipment_pri,String lat,String longs,String asset_code)
    {
        String   selectQuery = "UPDATE " + TABLE_ORDERS + " SET signature_path = '"  +  signature + "'," + "details1 = '"  +  order_detail1_text + "'," + "details2 = '"  +  order_detail2_text + "'," + "details3 = '"  +  details3 + "'," + "details4 = '"  +  order_detail4_text + "'," + "order_category = '"  +  order_category + "'," + "shipmet_priority = '"  +  shipment_pri + "'," + "latitude = '"  +  lat  + "'," + "longitude = '"  +  longs + "'," + "asset_code = '"  +  asset_code + "'" + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_order_image(String signature,String order_id)
    {

        String selectQuery = "UPDATE " + TABLE_ORDERS + " SET getsign_img = '" +  signature  + "'" + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateCalendervalue(String delete_flag,String calender_id)
    {

        String selectQuery = "UPDATE " + TABLE_CALENDER_ENTRIES + " SET delete_flag = '" +  delete_flag    + "'"  + " WHERE calender_id = '" +  calender_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_SIGNATUREnew(String created_at,String calender_id)
    {

        String selectQuery = "UPDATE " + TABLE_CALENDER_ENTRIES + " SET created_at = '" +  created_at    + "'"  + " WHERE calender_id = '" +  calender_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateCalenderEn_DeleteFlag(String signature,String order_id)
    {

        String selectQuery = "UPDATE " + TABLE_ORDERS + " SET signature_path = '" +  signature    + "'"  + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }


    public void updateORDER_SIGNATURE_Return(String signature,String order_id)
    {

        String selectQuery = "UPDATE " + TABLE_RETURNORDERNEW + " SET signature_path = '" +  signature    + "'"  + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_feedback(String value)
    {

        String selectQuery = "UPDATE " + TABLE_CUSTOMER_SERVICES_FEEDBACKS + " SET created_at = '" +  value    + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void update_password(String value) {

        String selectQuery = "UPDATE " + TABLE_REG + " SET password = '" + value + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void update_password(String pwd, String email) {

        String selectQuery = "UPDATE " + TABLE_REG + " SET password = '" + pwd + "'" + " WHERE email_id = '" + email + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_claims(String value)
    {

        String selectQuery = "UPDATE " + TABLE_CUSTOMER_SERVICE_CLAIMS + " SET created_at = '" +  value    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }


    public void updateORDER_complaints(String value)
    {

        String selectQuery = "UPDATE " + TABLE_CUSTOMER_SERVICES_COMPLAINTS + " SET created_at = '" +  value    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_stocks(String value)
    {

        String selectQuery = "UPDATE " + TABLE_CUSTOMER_SERVICE_COMPETITION_STOCKS + " SET created_at = '" +  value    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_calenderevent(String value)
    {

        String selectQuery = "UPDATE " + TABLE_CALENDER_ENTRIES + " SET created_at = '" +  value    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_DISTRIBUTER(String code,String order_id)
    {

        String selectQuery = "UPDATE " + TABLE_ORDERS + " SET distributor_id = '" +  code    + "'" + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }

    public void updateORDER_DISTRIBUTER_return(String code,String order_id)
    {

        String selectQuery = "UPDATE " + TABLE_RETURNORDERNEW + " SET distributor_id = '" +  code    + "'" + " WHERE order_id = '" +  order_id    + "'";

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);
        // db.close();
    }


    // Getting product data
    public List<Local_Data> Get_distributer_id(String name) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT code FROM " + TABLE_DISTRIBUTORS + " WHERE name"+ " ='"+ name + "'";

        SQLiteDatabase db = this.getWritableDatabase();
       // Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select code FROM distributors WHERE name = ?;",
                new String[] { name });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setDISTRIBUTER_ID(cursor.getString(0));

                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    public void update_item_return(String total_qty,String MRP,String amount,String scheme_amount,String actual_discount,String item_no,String order_id) {

        String selectQuery = "";
        SQLiteDatabase db= this.getWritableDatabase();
        db.beginTransactionNonExclusive();
        
        selectQuery = "UPDATE " + TABLE_RETURNORDERNEW_PRODUCT + " SET total_qty = '"  +  total_qty + "'," + "amount = '"  +  amount + "'," + "scheme_amount = '"  +  scheme_amount + "'," + "actual_discount = '"  +  actual_discount + "'" + " WHERE item_number = '" +  item_no    + "'" + " AND order_id"+ " ='"+ order_id + "'";
        db.execSQL(selectQuery);
        
        db.setTransactionSuccessful();
        db.endTransaction();
   }
    
    public List<Local_Data> ITEM_description_byINVOICE(String Invoice_Description1) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT Long_Description,Primary_Sub_Category,Unit_Rate FROM " + TABLE_ITEM_MASTER + " WHERE Invoice_Description1"+ " ='"+ Invoice_Description1 + "'" + " GROUP BY Long_Description ORDER BY Long_Description";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVariant(cursor.getString(0));
                contact.setStateName(cursor.getString(1));
                contact.setMRP(cursor.getString(2));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
    public List<Local_Data> ITEM_description_byINVOICEID(String Invoice_Description1) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT  Long_Description,Primary_Sub_Category,Unit_Rate FROM " + TABLE_ITEM_MASTER + " WHERE Item_Number"+ " ='"+ Invoice_Description1 + "'" + " GROUP BY Long_Description ORDER BY Long_Description";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setVariant(cursor.getString(0));
                contact.setStateName(cursor.getString(1));
                contact.setMRP(cursor.getString(2));
                
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }
    
 // Getting All Local_Data
    public List<Local_Data> getStatusOrderId(String orderid) {
        List<Local_Data> contactList = new ArrayList<Local_Data>(); 
        
        // Select All Query
        String selectQuery = "SELECT item_description,item_amount,item_number FROM " + DATABASE_CREATE_ORDERSTATUS + " WHERE "+ ORDERSTATUS_ID + "='" + orderid +"'";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrderStatus(cursor.getString(0));
                contact.setOrderAmount(cursor.getString(1));
                contact.setorder_number(cursor.getString(2));
                //contact.setPwd(cursor.getString(2));
                //contact.setImei(cursor.getString(3));
                
                // Adding contact to list
                
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
 
        // return contact list?
        return contactList;
    }
    
 // Getting product data
    public List<Local_Data> getStatusOrderId_BYORDERPRODUCT(String order_id) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "SELECT product_id,amount,item_number FROM " + TABLE_ORDER_PRODUCTS + " WHERE order_id"+ " ='"+ order_id + "'";
        
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setOrderStatus(cursor.getString(0));
                contact.setOrderAmount(cursor.getString(1));
                contact.setorder_number(cursor.getString(2));
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }


    // Getting All Local_Data
    public List<Local_Data> getAllAttendance_Data() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT user_id,punched_on,punched_at_latitude,punched_at_longitude,punched_button,punched_at_address,server_flag,current_date_only FROM " + TABLE_ATTENDANCE_DATA + " ORDER BY punched_at_address";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

//		Cursor cursor = db.rawQuery("SELECT code,name FROM " + TABLE_USERS  + " GROUP BY name ORDER BY name",
//				new String[] { });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setUser_id(cursor.getString(0));
                contact.setPunched_on(cursor.getString(1));
                contact.setPunched_at_latitude(cursor.getString(2));
                contact.setPunched_at_longitude(cursor.getString(3));
                contact.setPunched_button(cursor.getString(4));

                contact.setPunched_at_address(cursor.getString(5));
                contact.setServer_flag(cursor.getString(6));
                contact.setCurrent_date_only(cursor.getString(7));

                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public List<Local_Data> getAllAttendance_Data_bydate(String current_date) {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query

        SQLiteDatabase db = this.getWritableDatabase();
        // Cursor cursor = db.rawQuery(selectQuery1, null);

        Cursor cursor = db.rawQuery("SELECT user_id,punched_on,punched_at_latitude,punched_at_longitude,punched_button,punched_at_address,server_flag,current_date_only FROM attendance WHERE current_date_only = ?;",
                new String[]{current_date});


        //Cursor cursor = db.rawQuery(selectQuery1, null);

//		Cursor cursor = db.rawQuery("SELECT code,name FROM " + TABLE_USERS  + " GROUP BY name ORDER BY name",
//				new String[] { });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setUser_id(cursor.getString(0));
                contact.setPunched_on(cursor.getString(1));
                contact.setPunched_at_latitude(cursor.getString(2));
                contact.setPunched_at_longitude(cursor.getString(3));
                contact.setPunched_button(cursor.getString(4));
                contact.setPunched_at_address(cursor.getString(5));
                contact.setServer_flag(cursor.getString(6));
                contact.setCurrent_date_only(cursor.getString(7));

                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public void getDeleteattendance_da(String c_date) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " + TABLE_ATTENDANCE_DATA + " WHERE punched_on = '" + c_date + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting All Local_Data
    public List<Local_Data> getAllAttendanceF_Data() {
        List<Local_Data> contactList1 = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery1 = "SELECT name FROM " + TABLE_CREATE_ATTENDENCE_F + " ORDER BY name";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery1, null);

//		Cursor cursor = db.rawQuery("SELECT code,name FROM " + TABLE_USERS  + " GROUP BY name ORDER BY name",
//				new String[] { });

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setName(cursor.getString(0));


                contactList1.add(contact);
            } while (cursor.moveToNext());
        }

        db.close();
        // return contact list?
        return contactList1;
    }

    // Getting All Local_Data
    public void getDeleteattendance_daten(String c_date, String in_out_flag) {
        // List<Local_Data> contactList = new ArrayList<Local_Data>();
        // Select All Query
        String selectQuery = "DELETE FROM " + TABLE_ATTENDANCE_DATA + " WHERE punched_on = '" + c_date + "'" + " AND punched_button" + " ='" + in_out_flag + "'";

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(selectQuery);
    }

    // Getting product data
    public List<Local_Data> getProductvarientbycategoryandproduct(String business_unit, String primary_category, String business_category, String sub_category) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select retail_price, mrp, qualifying_qty, free_qty, name,code,primary_category,sub_category,product_variant,sq,mq FROM item_master WHERE b_unit = ? AND primary_category = ? AND b_business_c = ? AND sub_category = ? GROUP BY product_variant",
                new String[]{business_unit, primary_category,business_category,sub_category});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                contact.setMRP(cursor.getString(1));
                contact.set_Description(cursor.getString(2));
                contact.set_Claims(cursor.getString(3));
                contact.setProduct_nm(cursor.getString(4));
                contact.setCode(cursor.getString(5));
                contact.setCategory(cursor.getString(6));
                contact.setSubcateg(cursor.getString(7));
                contact.setProduct_variant(cursor.getString(8));
                contact.setSQ(cursor.getString(9));
                contact.setMQ(cursor.getString(10));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> getSelectedorder_downs_values(String business_unit, String primary_category, String business_category, String sub_category) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select retail_price, mrp, qualifying_qty, free_qty, name,code,primary_category,sub_category,product_variant,sq,mq FROM item_master WHERE b_unit = ? AND primary_category = ? AND b_business_c = ? AND sub_category = ? GROUP BY product_variant",
                new String[]{business_unit, primary_category,business_category,sub_category});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                contact.setMRP(cursor.getString(1));
                contact.set_Description(cursor.getString(2));
                contact.set_Claims(cursor.getString(3));
                contact.setProduct_nm(cursor.getString(4));
                contact.setCode(cursor.getString(5));
                contact.setCategory(cursor.getString(6));
                contact.setSubcateg(cursor.getString(7));
                contact.setProduct_variant(cursor.getString(8));
                contact.setSQ(cursor.getString(9));
                contact.setMQ(cursor.getString(10));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> getSL_BYPRODUCT_Id(String P_ID) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select sq FROM item_master WHERE code = ? GROUP BY code",
                new String[]{P_ID});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();

                contact.setSQ(cursor.getString(0));

                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> getProductvarientbyname(String business_unit, String primary_category, String business_category, String sub_category,String varient) {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select retail_price, mrp, qualifying_qty, free_qty, name,code,primary_category,sub_category,sq,mq FROM item_master WHERE b_unit = ? AND primary_category = ? AND b_business_c = ? AND sub_category = ? AND product_variant = ? GROUP BY name",
                new String[]{business_unit, primary_category,business_category,sub_category,varient});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                contact.setMRP(cursor.getString(1));
                contact.set_Description(cursor.getString(2));
                contact.set_Claims(cursor.getString(3));
                contact.setProduct_nm(cursor.getString(4));
                contact.setCode(cursor.getString(5));
                contact.setCategory(cursor.getString(6));
                contact.setSubcateg(cursor.getString(7));
                contact.setSQ(cursor.getString(8));
                contact.setMQ(cursor.getString(9));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    // Getting product data
    public List<Local_Data> getProductvarient() {
        List<Local_Data> contactList14 = new ArrayList<Local_Data>();
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.rawQuery("select retail_price, mrp, qualifying_qty, free_qty, name,code,primary_category,sub_category,sq,mq FROM item_master;",
                new String[]{});


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Local_Data contact = new Local_Data();
                contact.setStateName(cursor.getString(0));
                contact.setMRP(cursor.getString(1));
                contact.set_Description(cursor.getString(2));
                contact.set_Claims(cursor.getString(3));
                contact.setProduct_nm(cursor.getString(4));
                contact.setCode(cursor.getString(5));
                contact.setCategory(cursor.getString(6));
                contact.setSubcateg(cursor.getString(7));
                contact.setSQ(cursor.getString(8));
                contact.setMQ(cursor.getString(9));
                // Adding contact to list
                contactList14.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list?
        return contactList14;
    }

    public void update_itemamountandquantity(String total_qty, String amount, String item_no, String order_id) {

        String selectQuery = "";
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransactionNonExclusive();

        selectQuery = "UPDATE " + TABLE_ORDER_PRODUCTS + " SET total_qty = '" + total_qty + "'," + "amount = '" + amount + "'" + " WHERE item_number = '" + item_no + "'" + " AND order_id" + " ='" + order_id + "'";
        db.execSQL(selectQuery);

        db.setTransactionSuccessful();
        db.endTransaction();
    }




    public boolean isColumnExists(String table, String column) {
        SQLiteDatabase db = this.getWritableDatabase();
        //SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void alter_Columns(String table_name,String column_name) {

        String selectQuery = "";
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransactionNonExclusive();

        selectQuery = "ALTER TABLE "
                + table_name + " ADD COLUMN " + column_name + " TEXT;";

        db.execSQL(selectQuery);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    
}
