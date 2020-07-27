package com.anchor.activities;



import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.webservice.ConnectionDetector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("DefaultLocale")
public class Status_Act extends Activity implements OnItemSelectedListener {
	private String Re_Text = "";
	Boolean isInternetPresent = false;
	ConnectionDetector cd;

	Button  adr_button;
	Spinner city_spinner, state_spinner, beat_spinner;
	TextView selVersion;
	HttpGet httppst;
    String[] s;
	int state_flag = 0;
	ProgressDialog dialog;
	ArrayAdapter<String> adapter_state1;
	AutoCompleteTextView autoCompleteTextView1;
	ArrayAdapter<String> adapter_state2;
	ArrayAdapter<String> adapter_beat;
	ArrayAdapter<String> adapter_state3;
	LinearLayout order_view, custserve_view, schedule_view;
	RelativeLayout rlout_order, rlout_custserve, rlout_schedule;
	HttpClient httpclint;
	List<NameValuePair> nameValuePars;
	HttpPost httppost;
	HttpResponse response;
	LoginDataBaseAdapter adapter_ob;
	DataBaseHelper helper_ob;
	SQLiteDatabase db_ob;
	Cursor cursor;
	String[] from;
	ListView lv;
	SimpleCursorAdapter cursorAdapter;
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	String C_ID = "";
	String S_ID = "";
	String B_ID = "";
	String CUS_ID = "";
	String CUS_NAME = "";
	SharedPreferences sp;
	ArrayList<String> list_cities = new ArrayList<String>();
	String[] customer_array;
	Button customer_submit;
	private ArrayList<String> results = new ArrayList<String>();
	private ArrayList<String> results1 = new ArrayList<String>();
	private ArrayList<String> results2 = new ArrayList<String>();
	private ArrayList<String> results_beat = new ArrayList<String>();
	// private ArrayList<String> results3 = new ArrayList<String>();
	private String[] city_state = { "Select City", "Mumbai" };
	// private String[] beat_state = { "Select Beat",
	// "Andheri West","Andheri East","Vileparle East","Khar Road"};
	// private String[] retail_state = { "Select Retailer", "Life Care Medical",
	// "Laxmi Collection","Amar Medical" };
	private String[] list_categ = { "Select Category", "AP Deodorants",
			"AP DEO Stick 15", "Hand Sanitizer 30", "Junior Hand Sanitizer 30" };
	private String[] list_prod = { "Select Product", "TITAN AFD", "REBEL AFD",
			"ADORE AFD", "OASIS AFD", "VIVA APD" };
	private String[] list_varnt = { "Select Variant", "50 GM", "150 ML",
			"250 ML" };

	private LocationManager locationMangaer = null;
	private LocationListener locationListener = null;

	private Button btnGetLocation = null;
	private EditText editLocation = null;
	private ProgressBar pb = null;

	TextView customer_address;
	private static final String TAG = "Debug";
	private Boolean flag = false;

	@SuppressLint("DefaultLocale")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.status_dialog);
		// lv=(ListView)findViewById(R.id.listView1);
		// customer_submit = (Button) findViewById(R.id.customer_submit);

		locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		adr_button = findViewById(R.id.but_neworder);
		city_spinner = findViewById(R.id.cust_city);
		state_spinner = findViewById(R.id.cust_state);
		beat_spinner = findViewById(R.id.cust_beat);
		autoCompleteTextView1 = findViewById(R.id.autoCompleteTextView1);
		rlout_order = findViewById(R.id.rlout_order);
		rlout_custserve = findViewById(R.id.rlout_customer);
		rlout_schedule = findViewById(R.id.rlout_schedule);
		customer_address = findViewById(R.id.customer_address);

		Global_Data.GLObalOrder_id_return = "";

		autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int pos,
									long id) {
				//Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

				Global_Data.hideSoftKeyboard(Status_Act.this);

				String customer_name = "";
				String address_type = "";
				if(autoCompleteTextView1.getText().toString().trim().indexOf(":") > 0)
				{
					s = autoCompleteTextView1.getText().toString().trim().split(":");
					customer_name = s[0].trim();
					address_type = s[1].trim();
				}
				else
				{
					customer_name = autoCompleteTextView1.getText().toString().trim();
				}
//
//
//				Global_Data.credit_limit_amount = "";
//				Global_Data.outstandings_amount = "";

				List<Local_Data> contacts = dbvoc.getCustomerCode(customer_name);

				if(contacts.size() <= 0)
				{
					Toast toast = Toast.makeText(Status_Act.this,
							"Customer Not Found", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				else {

					for (Local_Data cn : contacts) {

						String habitnumber = "<b>" + "Address: " + "</b> " +cn.getAddress();
						customer_address.setText(Html.fromHtml(habitnumber ));
						//customer_address.setText("Address: "+cn.getAddress());

					}
				}
			}
		});

		//order_view = (LinearLayout) findViewById(R.id.order_view);
		//custserve_view = (LinearLayout) findViewById(R.id.customer_view);
		//schedule_view = (LinearLayout) findViewById(R.id.schedule_view);

		//order_view.setVisibility(View.VISIBLE);
		cd  = new ConnectionDetector(getApplicationContext());
		//rlout_custserve.setBackgroundResource(R.drawable.single_wtab);
		//rlout_schedule.setBackgroundResource(R.drawable.single_wtab);
		// rlout_order.setVisibility(View.VISIBLE);
		// order_view.setVisibility(View.VISIBLE);

		// String[] from = { helper_ob.FNAME};
		// int[] to = { R.id.tv_fname, R.id.tv_lname };
		// cursor = adapter_ob.queryName();
		// cursorAdapter = new SimpleCursorAdapter(this, R.layout.row, cursor,
		// from, to);
		Global_Data.GLOvel_GORDER_ID = "";
		Global_Data.GLOVEL_LONG_DESC = "";
		Global_Data.GLOVEL_CATEGORY_SELECTION = "";
		Global_Data.GLOVEL_ITEM_MRP = "";


		// current_locationcheck();

		adr_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if
						(state_spinner.getSelectedItem().toString().equalsIgnoreCase("Select State"))
				{

					Toast toast = Toast.makeText(Status_Act.this,"Please Select State",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				else if
						(city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City"))
				{

					Toast toast = Toast.makeText(Status_Act.this,"Please Select Beat",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}

				else if
						(autoCompleteTextView1.getText().toString().equalsIgnoreCase("")) {

					Toast toast = Toast.makeText(Status_Act.this,"Please Select Customer",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}else{

					String customer_name = "";
					String address_type = "";
					if(autoCompleteTextView1.getText().toString().trim().indexOf(":") > 0)
					{
						s = autoCompleteTextView1.getText().toString().trim().split(":");
						customer_name = s[0].trim();
						address_type = s[1].trim();
					}
					else
					{
						customer_name = autoCompleteTextView1.getText().toString().trim();
					}


					List<Local_Data> contacts = dbvoc.getCustomerCode(customer_name);

					if(contacts.size() <= 0)
					{
						Toast toast = Toast.makeText(Status_Act.this,
								"Customer Not Found", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					else
					{

						for (Local_Data cn : contacts)
						{

							Global_Data.cust_str= cn.getCust_Code();

						}


						//Global_Data.GLOVEL_ORDER_REJECT_FLAG = "FALSE";
						Global_Data.order_city = city_spinner.getSelectedItem()
								.toString();
						Global_Data.order_beat = beat_spinner.getSelectedItem()
								.toString();
						Global_Data.order_state = state_spinner.getSelectedItem()
								.toString();
						Global_Data.order_retailer = customer_name;

//						List<Local_Data> contcustomer = dbvoc.getOrderIds_return(Global_Data.GLOvel_CUSTOMER_ID);
//
//						if(contcustomer.size() <=0)
//						{
//							Toast.makeText(getApplicationContext(), "NO return order found for selected customer", Toast.LENGTH_LONG).show();
//						}
//						else
//						{
							Intent intent = new Intent(getApplicationContext(),
									Status_Activity.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.slide_out_left);
					//	}



					}
				}
			}
		});






		// customer_submit.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		//
		// // if
		// (city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City"))
		// {
		// //
		// // Toast toast =
		// Toast.makeText(Customer_Service.this,"Please Select City",
		// Toast.LENGTH_SHORT);
		// // toast.setGravity(Gravity.CENTER, 0, 0);
		// // toast.show();
		// // }
		// //
		// // else if
		// (beat_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Beat"))
		// {
		// // Toast toast =
		// Toast.makeText(Customer_Service.this,"Please Select Beat",
		// Toast.LENGTH_SHORT);
		// // toast.setGravity(Gravity.CENTER, 0, 0);
		// // toast.show();
		// // }
		// //
		// // else if
		// (retail_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Retailer"))
		// {
		// //
		// // Toast toast =
		// Toast.makeText(Customer_Service.this,"Please Select Retailer",
		// Toast.LENGTH_SHORT);
		// // toast.setGravity(Gravity.CENTER, 0, 0);
		// // toast.show();
		// // }
		// //
		// // else {
		//
		// // new Thread(new Runnable() {
		// // public void run() {
		// // Flwg();
		// // }
		// // }).start();
		//
		// Global_Data.retailer= retail_spinner.getSelectedItem().toString();
		// Intent intent = new Intent(Order.this, NewOrderActivity.class);
		// startActivity(intent);
		// // Intent intent = new Intent(Customer_Service.this,
		// Customer_Feed.class);
		// // startActivity(intent);
		// overridePendingTransition(R.anim.slide_in_right,
		// R.anim.slide_out_left);
		//
		// // }
		// }
		// });

		// //Reading all
		// List<Local_Data> contacts = dbvoc.getAllList();
		// results.add("Select City");
		// for (Local_Data cn : contacts) {
		// String str_state = ""+cn.getStateName();
		// //Global_Data.local_pwd = ""+cn.getPwd();
		//
		// results.add(str_state);
		// //System.out.println("Local Values:-"+Global_Data.local_user);
		// //Toast.makeText(LoginActivity.this,
		// "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
		// }

		// Reading all
		List<Local_Data> contacts1 = dbvoc.getAllState();
		results1.add("Select State");
		for (Local_Data cn : contacts1) {
			String str_state = "" + cn.getStateName();
			results1.add(str_state);
		}

//		 //Reading all
//		 List<Local_Data> contacts2 = dbvoc.getAllBeats();
//		 results_beat.add("Select Beat");
//		 for (Local_Data cn : contacts2) {
//		 String str_beat = ""+cn.getStateName();
//		 //Global_Data.local_pwd = ""+cn.getPwd();
//
//		 results_beat.add(str_beat);
//		 //System.out.println("Local Values:-"+Global_Data.local_user);
//		 //Toast.makeText(LoginActivity.this,
//		 //"Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//		 }

		// Reading all
		List<Local_Data> contacts3 = dbvoc.getAllRetailers();
		results2.add("Select Retailer");
		for (Local_Data cn : contacts3) {
			String str_retailer = "" + cn.getStateName();
			// Global_Data.local_pwd = ""+cn.getPwd();

			results2.add(str_retailer);
			// System.out.println("Local Values:-"+Global_Data.local_user);
			// Toast.makeText(LoginActivity.this,
			// "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
		}

//		Global_Data.results3 = new ArrayList<String>();
//		// Reading all
//		List<Local_Data> contac = dbvoc.getDistributors();
//		Global_Data.results3.add("Select Distributor");
//		for (Local_Data cn : contac) {
//			String str_city = "" + cn.getDstr();
//			// Global_Data.local_pwd = ""+cn.getPwd();
//
//			Global_Data.results3.add(str_city);
//			dbvoc.close();
//			// System.out.println("Local Values:-"+Global_Data.local_user);
//			// Toast.makeText(Order.this,
//			// "Login Invalid"+str_city,Toast.LENGTH_SHORT).show();
//		}

		adapter_state1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, results1);

		// ArrayAdapter<String> adapter_state3 = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
		// results2);

		adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		state_spinner.setAdapter(adapter_state1);
		state_spinner.setOnItemSelectedListener(this);
		// ArrayAdapter<String> adapter_state1 = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
		// results1);
		//
		// // ArrayAdapter<String> adapter_state3 = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
		// results2);
		//
		// adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// state_spinner.setAdapter(adapter_state1);
		// state_spinner.setOnItemSelectedListener(this);
		try
		{
			ActionBar mActionBar = getActionBar();
			mActionBar.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#910505")));
			// mActionBar.setDisplayShowHomeEnabled(false);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(this);
			Intent i = getIntent();
			String name = i.getStringExtra("retialer");
			View mCustomView = mInflater.inflate(R.layout.action_bar, null);
			mCustomView.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#910505")));
			TextView mTitleTextView = mCustomView
					.findViewById(R.id.screenname);
			mTitleTextView.setText("Institutional Order");

			TextView todaysTarget = mCustomView
					.findViewById(R.id.todaysTarget);
			SharedPreferences sp = Status_Act.this
					.getSharedPreferences("SimpleLogic", 0);

//		if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) >= 0) {
//			todaysTarget.setText("Today's Target : Rs "
//					+ String.format("%.2f", (sp.getFloat("Target", 0.00f) - sp
//					.getFloat("Current_Target", 0.00f))) + "");
//		}

			try
			{
				int target  = Math.round(sp.getFloat("Target",0));
				int achieved  = Math.round(sp.getFloat("Achived",0));
				Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
				if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
				{
					int age = Math.round(age_float);

					todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
				}else
				{
					int age = Math.round(age_float);

					todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
				}

			}catch(Exception ex){ex.printStackTrace();}
			if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
				// todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target",
				// 0.00f)-sp.getFloat("Target", 0.00f))+"");
				todaysTarget.setText("Today's Target Acheived");
			}

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}


		// if(!Global_Data.GLOvel_STATE.equalsIgnoreCase(""))
		// {
		// int sp_position =
		// adapter_state1.getPosition(Global_Data.GLOvel_STATE.toUpperCase());
		// state_spinner.setSelection(sp_position);
		// }
//		if (!Global_Data.GLOvel_STATE_n.equalsIgnoreCase("")) {
//			int sp_position = adapter_state1
//					.getPosition(Global_Data.GLOvel_STATE_n.trim().toUpperCase());
//			state_spinner.setSelection(sp_position);
//		} else if (!Global_Data.GLOvel_STATE.equalsIgnoreCase("")) {
//			int sp_position = adapter_state1
//					.getPosition(Global_Data.GLOvel_STATE.toUpperCase());
//			state_spinner.setSelection(sp_position);
//		} else {
//			dialog = new ProgressDialog(this,
//					AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
//			dialog.setMessage("Please Wait");
//			dialog.setTitle("Metal");
//			dialog.setCancelable(true);
//			dialog.show();
//			current_locationcheck();
//		}

		// adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// beat_spinner.setAdapter(adapter_state2);
		// beat_spinner.setOnItemSelectedListener(this);
		//
		// ArrayAdapter<String> adapter_state3 = new
		// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
		// results2);
		// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// retail_spinner.setAdapter(adapter_state3);
		// retail_spinner.setOnItemSelectedListener(this);

	}


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Spinner spinner = (Spinner) arg0;

		if (spinner.getId() == R.id.cust_state) {
			if (arg0.getItemAtPosition(arg2).toString()
					.equalsIgnoreCase("Select State")) {

				results.clear();
				customer_address.setText("");
				results.add("Select City");
				results_beat.clear();
				results_beat.add("Select Beat");

				adapter_state2 = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, results);
				adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				city_spinner.setAdapter(adapter_state2);
				city_spinner.setOnItemSelectedListener(this);

				list_cities.add("");
				list_cities.clear();
				// String []customer_array = {"Apple", "Banana", "Cherry",
				// "Date", "Grape", "Kiwi", "Mango", "Pear"};
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_dropdown_item,
						list_cities);
				autoCompleteTextView1.setThreshold(1);// will start working from
				// first character
				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
				// data into the
				// AutoCompleteTextView
				autoCompleteTextView1.setTextColor(Color.BLACK);
				autoCompleteTextView1.setText("");

				// adapter_state3 = new
				// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				// results2);
				// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// retail_spinner.setAdapter(adapter_state3);
				// retail_spinner.setOnItemSelectedListener(this);
			} else {

				String items = state_spinner.getSelectedItem().toString().trim();
				//String C_ID = "";
				Log.i("Selected item : ", items);

				Log.i("Selected item : ", items);

				List<Local_Data> contacts = dbvoc.getState_id(items);
				for (Local_Data cn : contacts)
				{

					S_ID = cn.getSTATE_ID();

				}

				if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(S_ID)) {
					results.clear();
					results.add("Select City");

					results_beat.clear();
					results_beat.add("Select Beat");
					List<Local_Data> contacts2 = dbvoc.getcityByState_id(S_ID);
					for (Local_Data cn : contacts2) {

						results.add(cn.getCityName());
					}
					adapter_state2 = new ArrayAdapter<String>(Status_Act.this,
							android.R.layout.simple_spinner_item, results);
					adapter_state2
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					city_spinner.setAdapter(adapter_state2);

					adapter_state1 = new ArrayAdapter<String>(Status_Act.this,
							android.R.layout.simple_spinner_item, results_beat);
					adapter_state1
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					beat_spinner.setAdapter(adapter_state1);

				}

				if (!Global_Data.GLOvel_CITY_n.equalsIgnoreCase("")) {
					city_spinner.setSelection(adapter_state2
							.getPosition(Global_Data.GLOvel_CITY_n
									.toUpperCase()));
				} else if (!Global_Data.GLOvel_CITY.equalsIgnoreCase("")) {
					city_spinner
							.setSelection(adapter_state2
									.getPosition(Global_Data.GLOvel_CITY
											.toUpperCase()));
				}
			}
		}

		if (spinner.getId() == R.id.cust_city) {
			if (arg0.getItemAtPosition(arg2).toString()
					.equalsIgnoreCase("Select City")) {
				results_beat.clear();
				customer_address.setText("");
				results_beat.add("Select Beat");
				// /results2.clear();
				// results2.add("Select Customer");
				adapter_beat = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, results_beat);
				adapter_beat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				beat_spinner.setAdapter(adapter_beat);
				beat_spinner.setOnItemSelectedListener(this);

				list_cities.add("");
				list_cities.clear();
				// String []customer_array = {"Apple", "Banana", "Cherry",
				// "Date", "Grape", "Kiwi", "Mango", "Pear"};
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_dropdown_item,
						list_cities);
				autoCompleteTextView1.setThreshold(1);// will start working from
				// first character
				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
				// data into the
				// AutoCompleteTextView
				autoCompleteTextView1.setTextColor(Color.BLACK);
				autoCompleteTextView1.setText("");

				// adapter_state3 = new
				// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				// results2);
				// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// retail_spinner.setAdapter(adapter_state3);
				// retail_spinner.setOnItemSelectedListener(this);
			} else {

				String items = city_spinner.getSelectedItem().toString().trim();
				//String C_ID = "";
				Log.i("Selected item : ", items);

				List<Local_Data> contacts = dbvoc.getCity_id(items);
				for (Local_Data cn : contacts)
				{

					C_ID = cn.getCITY_ID();

				}

				if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(C_ID)) {
//					results_beat.clear();
//					results_beat.add("Select Beat");
					List<Local_Data> contacts2 = dbvoc.getbeatByCityID((C_ID));
					for (Local_Data cn : contacts2) {

						results_beat.add(cn.getCityName());
					}
					adapter_beat = new ArrayAdapter<String>(Status_Act.this,
							android.R.layout.simple_spinner_item, results_beat);
					adapter_beat
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					beat_spinner.setAdapter(adapter_beat);
				}

				if (!Global_Data.GLOvel_CITY_n.equalsIgnoreCase("")) {
					beat_spinner.setSelection(adapter_beat
							.getPosition(Global_Data.GLOvel_CITY_n
									.toUpperCase()));
				} else if (!Global_Data.GLOvel_CITY.equalsIgnoreCase("")) {
					beat_spinner
							.setSelection(adapter_beat
									.getPosition(Global_Data.GLOvel_CITY
											.toUpperCase()));
				}
			}
		}


		if(spinner.getId() == R.id.cust_beat)
		{
			if (arg0.getItemAtPosition(arg2).toString()
					.equalsIgnoreCase("Select Beat")) {
				customer_address.setText("");
				list_cities.add("");
				list_cities.clear();
				// String []customer_array = {"Apple", "Banana", "Cherry", "Date",
				// "Grape", "Kiwi", "Mango", "Pear"};
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_dropdown_item, list_cities);
				autoCompleteTextView1.setThreshold(1);// will start working from
				// first character
				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
				// data into the
				// AutoCompleteTextView
				autoCompleteTextView1.setTextColor(Color.BLACK);
				autoCompleteTextView1.setText("");
				// results.clear();
				// results.add("Select City");
				// results2.clear();
				// results2.add("Select Customer");
				// // adapter_state2 = new
				// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				// results);
				// //
				// adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// // city_spinner.setAdapter(adapter_state2);
				// // city_spinner.setOnItemSelectedListener(this);
				//
				// adapter_state3 = new
				// ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
				// results2);
				// adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// retail_spinner.setAdapter(adapter_state3);
				// retail_spinner.setOnItemSelectedListener(this);

			} else {

				String items = beat_spinner.getSelectedItem().toString();
				//String C_ID = "";
				Log.i("Selected item : ", items);

				List<Local_Data> contacts = dbvoc.getBeat_id(items);
				for (Local_Data cn : contacts)
				{

					B_ID = cn.getBEAT_ID();

				}


				if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(B_ID)) {
					list_cities.clear();
					List<Local_Data> contacts2 = dbvoc.getcustomerByCityName(B_ID);
					for (Local_Data cn : contacts2) {

//					if(!cn.getPURPOSE_ADDRESS().equalsIgnoreCase(null) && !cn.getPURPOSE_ADDRESS().equalsIgnoreCase("null") && !cn.getPURPOSE_ADDRESS().equalsIgnoreCase(""))
//					{
						list_cities.add(cn.get_stocks_product_name()); //+ ":" +cn.getPURPOSE_ADDRESS());
//					}
//					else
//					{
//						list_cities.add(cn.get_stocks_product_name());
//					}

					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
							android.R.layout.simple_spinner_dropdown_item,
							list_cities);
					autoCompleteTextView1.setThreshold(1);// will start working from
					// first character
					autoCompleteTextView1.setAdapter(adapter);// setting the adapter
					// data into the
					// AutoCompleteTextView
					autoCompleteTextView1.setTextColor(Color.BLACK);
				}

			}
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();

		Intent i=new Intent(Status_Act.this, Sales_Dash.class);
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		startActivity(i);
		finish();
	}


}

