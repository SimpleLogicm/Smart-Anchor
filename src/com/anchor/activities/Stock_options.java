package com.anchor.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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

public class Stock_options extends Activity implements OnItemSelectedListener {
    private String Re_Text = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;

    Button  adr_button;
    Spinner city_spinner, state_spinner, beat_spinner;
    TextView selVersion;
    HttpGet httppst;
    String s[];
    int state_flag = 0;
    ProgressDialog dialog;
    ArrayAdapter<String> adapter_state1;

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


    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;

    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb = null;


    private static final String TAG = "Debug";
    private Boolean flag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_options);
        // lv=(ListView)findViewById(R.id.listView1);
        // customer_submit = (Button) findViewById(R.id.customer_submit);

        locationMangaer = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        adr_button = (Button) findViewById(R.id.but_neworder);
        city_spinner = (Spinner) findViewById(R.id.cust_city);
        state_spinner = (Spinner) findViewById(R.id.cust_state);
        beat_spinner = (Spinner) findViewById(R.id.cust_beat);

        city_spinner.setPopupBackgroundResource(R.drawable.spinner);
        state_spinner.setPopupBackgroundResource(R.drawable.spinner);
        beat_spinner.setPopupBackgroundResource(R.drawable.spinner);

        rlout_order = (RelativeLayout) findViewById(R.id.rlout_order);
        rlout_custserve = (RelativeLayout) findViewById(R.id.rlout_customer);
        rlout_schedule = (RelativeLayout) findViewById(R.id.rlout_schedule);


        Global_Data.GLObalOrder_id_return = "";

        cd  = new ConnectionDetector(getApplicationContext());

        Global_Data.GLOvel_GORDER_ID = "";
        Global_Data.GLOVEL_LONG_DESC = "";
        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
        Global_Data.GLOVEL_ITEM_MRP = "";


        // current_locationcheck();

        adr_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if(state_spinner.getSelectedItem().toString().equalsIgnoreCase("All States"))
                {
                    Intent intent = new Intent(getApplicationContext(),
                            Stock_Main.class);
                    startActivity(intent);
                    Global_Data.Stock_warehouse_flag = "All States";
                    Global_Data.Stock_warehouse_flag_value_check = "All States";

                }
                else if (city_spinner.getSelectedItem().toString().equalsIgnoreCase("All Cities"))
                {
                    Global_Data.Stock_warehouse_flag = state_spinner.getSelectedItem().toString()+"-"+"All Cities";
                    Global_Data.Stock_warehouse_flag_value_check = "All Cities";
                    Intent intent = new Intent(getApplicationContext(),
                            Stock_Main.class);
                    startActivity(intent);
                }
                else if(!(state_spinner.getSelectedItem().toString().equalsIgnoreCase("Select State")) && !(city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City")) && beat_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Warehouse"))
                {
                    Global_Data.Stock_warehouse_flag = state_spinner.getSelectedItem().toString()+"-"+city_spinner.getSelectedItem().toString();
                    Global_Data.Stock_warehouse_flag_value_check = "All States AND All Cities";
                    Intent intent = new Intent(getApplicationContext(),
                            Stock_Main.class);
                    startActivity(intent);

                }
                else
                if(state_spinner.getSelectedItem().toString().equalsIgnoreCase("Select State"))
                {

                    Toast toast = Toast.makeText(Stock_options.this,"Please Select State",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if (city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City"))
                {

                    Toast toast = Toast.makeText(Stock_options.this,"Please Select City",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else if (beat_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Warehouse"))
                {

                    Toast toast = Toast.makeText(Stock_options.this,"Please Select Warehouse",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else
                {
                    Global_Data.Stock_warehouse_flag = beat_spinner.getSelectedItem().toString();
                    Global_Data.Stock_warehouse_flag_value_check = "warehouse";
                    Intent intent = new Intent(getApplicationContext(),
                            Stock_Main.class);
                    startActivity(intent);

                }
            }
        });








        // Reading all
        List<Local_Data> contacts1 = dbvoc.getAllState();
        results1.add("Select State");
        for (Local_Data cn : contacts1) {
            String str_state = "" + cn.getStateName();
            results1.add(str_state);
        }

//        if(contacts1.size() > 1)
//        {
            results1.add("All States");
       // }





        adapter_state1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, results1);

        // ArrayAdapter<String> adapter_state3 = new
        // ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
        // results2);

        adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(adapter_state1);
        state_spinner.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        Spinner spinner = (Spinner) arg0;

        if (spinner.getId() == R.id.cust_state) {
            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select State")) {

                results.clear();

                results.add("Select City");
                results_beat.clear();
                results_beat.add("Select Warehouse");

                adapter_state2 = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, results);
                adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                city_spinner.setAdapter(adapter_state2);
                city_spinner.setOnItemSelectedListener(this);

                list_cities.add("");
                list_cities.clear();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        list_cities);
                city_spinner.setEnabled(true);
                beat_spinner.setEnabled(true);

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
                    results_beat.add("Select Warehouse");
                    List<Local_Data> contacts2 = dbvoc.getcityByState_id(S_ID);
                    for (Local_Data cn : contacts2) {

                        results.add(cn.getCityName());
                    }

//                    if(contacts2.size() > 1)
//                    {
                        results.add("All Cities");
                    //}
                    adapter_state2 = new ArrayAdapter<String>(Stock_options.this,
                            android.R.layout.simple_spinner_item, results);
                    adapter_state2
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city_spinner.setAdapter(adapter_state2);

                    adapter_state1 = new ArrayAdapter<String>(Stock_options.this,
                            android.R.layout.simple_spinner_item, results_beat);
                    adapter_state1
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    beat_spinner.setAdapter(adapter_state1);

                    if(items.equalsIgnoreCase("All States"))
                    {
                        city_spinner.setEnabled(false);
                        beat_spinner.setEnabled(false);
                    }
                    else
                    {
                        city_spinner.setEnabled(true);
                        beat_spinner.setEnabled(true);
                    }

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

                results_beat.add("Select Warehouse");
                // /results2.clear();
                // results2.add("Select Customer");
                adapter_beat = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, results_beat);
                adapter_beat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                beat_spinner.setAdapter(adapter_beat);
                beat_spinner.setOnItemSelectedListener(this);

                list_cities.add("");
                list_cities.clear();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        list_cities);

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
					results_beat.clear();
					results_beat.add("Select Warehouse");
                    List<Local_Data> contacts2 = dbvoc.getwarehouseByCityID((C_ID));
                    for (Local_Data cn : contacts2) {

                        results_beat.add(cn.getCityName());
                    }
                    adapter_beat = new ArrayAdapter<String>(Stock_options.this,
                            android.R.layout.simple_spinner_item, results_beat);
                    adapter_beat
                            .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    beat_spinner.setAdapter(adapter_beat);

                    if(items.equalsIgnoreCase("All Cities"))
                    {
                       // city_spinner.setEnabled(false);
                        beat_spinner.setEnabled(false);
                    }
                }


            }
        }


        if(spinner.getId() == R.id.cust_beat)
        {
            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select Warehouse")) {

                list_cities.add("");
                list_cities.clear();



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

        Intent i=new Intent(Stock_options.this, Stock_Main.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }


}

