package com.anchor.activities;


import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.webservice.ConnectionDetector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("DefaultLocale")
public class Target_REYC_Main extends Activity implements OnItemSelectedListener {
    private String Re_Text = "";
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    int check_duration=0;
    int check_monthn=0;
    int check_year=0;
    int check_month=0;
    String product_category_code = "";

    Button view_target,targetS_Cancel;
    Spinner target_year, target_quarter, target_month,target_product_category,target_amount,target_grpby;
    String[] s;
    ProgressDialog dialog;
    ArrayAdapter<String> adapter_target_quarter;
    EditText target_month_to;
    ArrayAdapter<String> adapter_target_year;
    ArrayAdapter<String> adapter_target_month;
    ArrayAdapter<String> adapter_product_category;
    ArrayAdapter<String> adapter_target_amount;
    ArrayAdapter<String> adapter_target_grpby;
    HttpClient httpclint;
    List<NameValuePair> nameValuePars;
    HttpPost httppost;
    HttpResponse response;
    LoginDataBaseAdapter adapter_ob;
    DataBaseHelper helper_ob;
    SQLiteDatabase db_ob;

    String[] from;

    DataBaseHelper dbvoc = new DataBaseHelper(this);
    SharedPreferences sp;
    ArrayList<String> list_cities = new ArrayList<String>();
    String[] customer_array;
    Button customer_submit;
    private ArrayList<String> quarter_list = new ArrayList<String>();
    private ArrayList<String> year_list = new ArrayList<String>();
    private ArrayList<String> month_list = new ArrayList<String>();
    private ArrayList<String> product_category_list = new ArrayList<String>();
    private ArrayList<String> target_amount_list = new ArrayList<String>();
    private ArrayList<String> target_grpby_list = new ArrayList<String>();

    @SuppressLint("DefaultLocale")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.target_reyc_main);
        // lv=(ListView)findViewById(R.id.listView1);
        // customer_submit = (Button) findViewById(R.id.customer_submit);

        view_target = findViewById(R.id.view_target);
        targetS_Cancel = findViewById(R.id.targetS_Cancel);
        target_year = findViewById(R.id.target_year);
        target_quarter = findViewById(R.id.target_quarter);
        target_month = findViewById(R.id.target_month);
        target_product_category = findViewById(R.id.target_product_category);
        target_month_to = findViewById(R.id.target_month_to);
        target_amount = findViewById(R.id.target_amount);
        target_grpby = findViewById(R.id.target_grpby);

        quarter_list.add("Please Select Duration");
        quarter_list.add("Monthly");
        quarter_list.add("Quarterly");
        quarter_list.add("Half Yearly");
        quarter_list.add("Yearly");

        target_amount_list.add("Please Select Amount");
        target_amount_list.add("In Crores");
        target_amount_list.add("In Lakhs");
        target_amount_list.add("In Thousands");
        target_amount_list.add("In Ruppes");

        //target_amount_list.add("Please Select Amount");
        target_grpby_list.add("By Date");
        target_grpby_list.add("By Product");

        cd  = new ConnectionDetector(getApplicationContext());

        view_target.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (target_quarter.getSelectedItem().toString().equalsIgnoreCase("Please Select Duration"))
                {

                    Toast toast = Toast.makeText(Target_REYC_Main.this,"Please Select Duration",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                else if(target_year.getSelectedItem().toString().equalsIgnoreCase("Please Select Year"))
                {

                    Toast toast = Toast.makeText(Target_REYC_Main.this,"Please Select Year",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                else if(target_month.getSelectedItem().toString().equalsIgnoreCase("Please Select From Month"))
                {

                    Toast toast = Toast.makeText(Target_REYC_Main.this,"Please Select From Month",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                else if(target_month_to.getText().toString().equalsIgnoreCase("")) {

                    Toast toast = Toast.makeText(Target_REYC_Main.this,"Please Select To Month",
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
//                else if(target_product_category.getSelectedItem().toString().equalsIgnoreCase("Select Product Category"))
//                {
//                    Toast toast = Toast.makeText(Target_REYC_Main.this,"Select Product Category",
//                            Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER, 0, 0);
//                    toast.show();
//                }
                else{

//                    List<Local_Data> contacts2 = dbvoc.HSS_DescriptionITEM_ID(target_product_category.getSelectedItem().toString().trim());
//                    //results.add("Select Product");
//                    for (Local_Data cn : contacts2)
//                    {
//                        product_category_code = cn.getCust_Code();
//                    }

                    Intent intent = new Intent(getApplicationContext(),
                            Target_Summary1.class);

                    intent.putExtra("Target_From_Month",target_month.getSelectedItem().toString().trim());
                    intent.putExtra("Target_To_Month",target_month_to.getText().toString().trim());
                   /// intent.putExtra("Target_Product_Category",target_product_category.getSelectedItem().toString().trim());
                    intent.putExtra("Target_Year",target_year.getSelectedItem().toString().trim());

                    Global_Data.Target_From_Month = target_month.getSelectedItem().toString().trim();
                    Global_Data.Target_To_Month = target_month_to.getText().toString().trim();
                    Global_Data.Target_Product_Category = target_product_category.getSelectedItem().toString().trim();
                    Global_Data.target_quarter = target_quarter.getSelectedItem().toString().trim();
                    Global_Data.Target_Year = target_year.getSelectedItem().toString().trim();
                    Global_Data.target_amount = target_amount.getSelectedItem().toString().trim();
                    Global_Data.target_grpby = target_grpby.getSelectedItem().toString().trim();
                    startActivity(intent);
                   // finish();
                }
            }
        });

        targetS_Cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                    startActivity(intent);
                    finish();
            }
        });

        adapter_target_quarter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, quarter_list);
        adapter_target_quarter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_quarter.setAdapter(adapter_target_quarter);
        target_quarter.setOnItemSelectedListener(this);

        adapter_target_amount = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, target_amount_list);
        adapter_target_amount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_amount.setAdapter(adapter_target_amount);
        target_amount.setSelection(3);
        target_amount.setOnItemSelectedListener(this);



        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.target_amount)) {
            int spinnerPosition = adapter_target_amount.getPosition(Global_Data.target_amount);
            target_amount.setSelection(spinnerPosition);
        }

        adapter_target_grpby = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, target_grpby_list);
        adapter_target_grpby.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_grpby.setAdapter(adapter_target_grpby);
        target_grpby.setOnItemSelectedListener(this);

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.target_grpby)) {
            int spinnerPosition = adapter_target_grpby.getPosition(Global_Data.target_grpby);
            target_grpby.setSelection(spinnerPosition);
        }
        else
        {
            int spinnerPosition = adapter_target_grpby.getPosition("By Product");
            target_grpby.setSelection(spinnerPosition);
        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.target_quarter)) {
            int spinnerPosition = adapter_target_quarter.getPosition(Global_Data.target_quarter);
            target_quarter.setSelection(spinnerPosition);
        }
        else
        {
            int spinnerPosition = adapter_target_quarter.getPosition("Monthly");
            target_quarter.setSelection(spinnerPosition);
        }

        product_category_list.clear();
        List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
        product_category_list.add("Select Product Category");
        for (Local_Data cn : contacts1)
        {
            if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
            {
                String str_categ = ""+cn.getStateName();
                product_category_list.add(str_categ);
            }
        }

        if(product_category_list.size() > 2) {
            product_category_list.add("All Product Category");
        }
        adapter_product_category = new ArrayAdapter<String>(this, R.layout.spinner_item, product_category_list);
        adapter_product_category.setDropDownViewResource(R.layout.spinner_item);
        target_product_category.setAdapter(adapter_product_category);

        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Target_Product_Category)){
            int spinnerPosition = adapter_product_category.getPosition(Global_Data.Target_Product_Category);
            target_product_category.setSelection(spinnerPosition);
        }
        else
        {
            if(product_category_list.contains("All Product Category"))
            {
                int spinnerPosition = adapter_product_category.getPosition("All Product Category");
                target_product_category.setSelection(spinnerPosition);
            }
        }

        year_list.clear();
        month_list.clear();
        target_month_to.setText("");

        year_list.add("Please Select Year");
        month_list.add("Please Select From Month");

        adapter_target_year = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, year_list);
        adapter_target_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_year.setAdapter(adapter_target_year);
        target_year.setOnItemSelectedListener(this);

        adapter_target_month = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, month_list);
        adapter_target_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_month.setAdapter(adapter_target_month);
        target_month.setOnItemSelectedListener(this);

        target_quarter.setEnabled(false);
        target_quarter.setClickable(false);
        target_year.setEnabled(false);
        target_year.setClickable(false);
        target_month.setEnabled(false);
        target_month.setClickable(false);

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
            mTitleTextView.setText("Target");

            TextView todaysTarget = mCustomView
                    .findViewById(R.id.todaysTarget);
            SharedPreferences sp = Target_REYC_Main.this
                    .getSharedPreferences("SimpleLogic", 0);


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


    }


    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        Spinner spinner = (Spinner) arg0;

        check_duration=check_duration+1;
       // if(check_duration>1 || Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.target_quarter)) {

            if (spinner.getId() == R.id.target_quarter) {
                if (arg0.getItemAtPosition(arg2).toString()
                        .equalsIgnoreCase("Please Select Duration")) {

                    Global_Data.Target_From_Month = "";
                    Global_Data.Target_To_Month = "";
                    Global_Data.Target_Product_Category = "";
                    Global_Data.target_quarter = "";
                    Global_Data.Target_Year = "";

                    year_list.clear();
                    month_list.clear();
                    target_month_to.setText("");

                    year_list.add("Please Select Year");
                    month_list.add("Please Select From Month");

                    adapter_target_year = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, year_list);
                    adapter_target_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    target_year.setAdapter(adapter_target_year);
                    target_year.setOnItemSelectedListener(this);

                    adapter_target_month = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, month_list);
                    adapter_target_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    target_month.setAdapter(adapter_target_month);
                    target_month.setOnItemSelectedListener(this);


                } else {

                    if(check_duration>1)
                    {
                        Global_Data.Target_From_Month = "";
                        Global_Data.Target_To_Month = "";
                        Global_Data.Target_Product_Category = "";
                        Global_Data.target_quarter = "";
                        Global_Data.Target_Year = "";
                    }
                    year_list.clear();
                    month_list.clear();
                    month_list.add("Please Select From Month");

                    adapter_target_month = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, month_list);
                    adapter_target_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    target_month.setAdapter(adapter_target_month);
                    target_month.setOnItemSelectedListener(this);


                    year_list.add("Please Select Year");
                    int start_year = 2015;
                    int current_year = Calendar.getInstance().get(Calendar.YEAR)+1;

                    int current_yearn = Calendar.getInstance().get(Calendar.YEAR);

                    while (start_year <= current_year) {
                        year_list.add(String.valueOf(start_year++));
                    }

                    adapter_target_year = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, year_list);
                    adapter_target_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    target_year.setAdapter(adapter_target_year);
                    target_year.setOnItemSelectedListener(this);

                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Target_Year)){
                        int spinnerPosition = adapter_target_year.getPosition(Global_Data.Target_Year);
                        target_year.setSelection(spinnerPosition);
                    }
                    else
                    {
                        if(check_duration<=1)
                        {
                            int spinnerPosition = adapter_target_year.getPosition(String.valueOf(current_yearn));
                            target_year.setSelection(spinnerPosition);
                        }
                        else
                        {
                            check_monthn = 1;
                        }

                    }
                }
            }
            if (spinner.getId() == R.id.target_year) {
                if (arg0.getItemAtPosition(arg2).toString()
                        .equalsIgnoreCase("Please Select Year")) {

                    Global_Data.Target_From_Month = "";
                    Global_Data.Target_To_Month = "";
                    Global_Data.Target_Product_Category = "";
                    Global_Data.target_quarter = "";
                    Global_Data.Target_Year = "";

                    month_list.clear();
                    target_month_to.setText("");

                    month_list.add("Please Select From Month");

                    adapter_target_month = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, month_list);
                    adapter_target_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    target_month.setAdapter(adapter_target_month);
                    target_month.setOnItemSelectedListener(this);
                }


                else {

//                    Format formatter = new SimpleDateFormat("MMMM");
//                    String current_month_name = formatter.format(new Date());

                    month_list.clear();
                    month_list.add("Please Select From Month");
                    String[] months = new DateFormatSymbols().getMonths();
                    for (int i = 0; i < months.length; i++) {
                        String month = months[i];
                        System.out.println("month = " + month);
                        month_list.add(months[i]);
                    }

                    adapter_target_month = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, month_list);
                    adapter_target_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    target_month.setAdapter(adapter_target_month);
                    target_month.setOnItemSelectedListener(this);

                    Calendar cal=Calendar.getInstance();
                    SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                    String month_name = month_date.format(cal.getTime());

                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Target_From_Month)){
                        int spinnerPosition = adapter_target_month.getPosition(Global_Data.Target_From_Month);
                        target_month.setSelection(spinnerPosition);
                    }
                    else
                    {
                        if(check_monthn== 0) {
                            int spinnerPosition = adapter_target_month.getPosition(month_name);
                            target_month.setSelection(spinnerPosition);
                        }

                    }
                }
            }
            if (spinner.getId() == R.id.target_month) {
                if (arg0.getItemAtPosition(arg2).toString()
                        .equalsIgnoreCase("Please Select From Month")) {

                    Global_Data.Target_From_Month = "";
                    Global_Data.Target_To_Month = "";
                    Global_Data.Target_Product_Category = "";
                    Global_Data.target_quarter = "";
                    Global_Data.Target_Year = "";

                    target_month_to.setText("");

                } else {

                    if(target_quarter.getSelectedItem().toString().equalsIgnoreCase("Monthly"))
                    {
                        target_month_to.setText(arg0.getItemAtPosition(arg2).toString()+" " + target_year.getSelectedItem().toString());
                    }
                    else
                    if(target_quarter.getSelectedItem().toString().equalsIgnoreCase("Quarterly"))
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        try {
                            calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                            int monthInt = calendar.get(Calendar.MONTH);

                            calendar.set(Calendar.MONTH, monthInt);
                            calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                            Date date1 = calendar.getTime();

                            System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                                    + "-"
                                    + calendar.get(Calendar.DATE)
                                    + "-"
                                    + calendar.get(Calendar.YEAR));

                            calendar.add(Calendar.MONTH,2);

                            System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                                    + "-"
                                    + calendar.get(Calendar.DATE)
                                    + "-"
                                    + calendar.get(Calendar.YEAR));

                            Date date2 = calendar.getTime();

                            target_month_to.setText(new SimpleDateFormat("MMMM").format(date2) + " " + calendar.get(Calendar.YEAR));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    if(target_quarter.getSelectedItem().toString().equalsIgnoreCase("Half Yearly"))
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        try {
                            calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                            int monthInt = calendar.get(Calendar.MONTH);

                            calendar.set(Calendar.MONTH, monthInt);
                            calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                            Date date1 = calendar.getTime();

                            System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                                    + "-"
                                    + calendar.get(Calendar.DATE)
                                    + "-"
                                    + calendar.get(Calendar.YEAR));

                            calendar.add(Calendar.MONTH,5);

                            System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                                    + "-"
                                    + calendar.get(Calendar.DATE)
                                    + "-"
                                    + calendar.get(Calendar.YEAR));

                            Date date2 = calendar.getTime();

                            target_month_to.setText(new SimpleDateFormat("MMMM").format(date2) + " " + calendar.get(Calendar.YEAR));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    if(target_quarter.getSelectedItem().toString().equalsIgnoreCase("Yearly"))
                    {
                        Calendar calendar = Calendar.getInstance();
                        calendar.clear();
                        try {
                            calendar.setTime(new SimpleDateFormat("MMM").parse(arg0.getItemAtPosition(arg2).toString()));
                            int monthInt = calendar.get(Calendar.MONTH);

                            calendar.set(Calendar.MONTH, monthInt);
                            calendar.set(Calendar.YEAR, Integer.parseInt(target_year.getSelectedItem().toString()));
                            Date date1 = calendar.getTime();

                            System.out.println("Current date : " + (calendar.get(Calendar.MONTH))
                                    + "-"
                                    + calendar.get(Calendar.DATE)
                                    + "-"
                                    + calendar.get(Calendar.YEAR));

                            calendar.add(Calendar.MONTH,11);

                            System.out.println("date after 12 months : " + (calendar.get(Calendar.MONTH))
                                    + "-"
                                    + calendar.get(Calendar.DATE)
                                    + "-"
                                    + calendar.get(Calendar.YEAR));

                            Date date2 = calendar.getTime();

                            target_month_to.setText(new SimpleDateFormat("MMMM").format(date2) + " " + calendar.get(Calendar.YEAR));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }

                }


            }

//            if (spinner.getId() == R.id.target_product_category) {
//                if (arg0.getItemAtPosition(arg2).toString().equalsIgnoreCase("Select Product Category")) {
//
//                    product_category_code = "";
//                }
//                else
//                {
//                    List<Local_Data> contacts2 = dbvoc.HSS_DescriptionITEM_ID(arg0.getItemAtPosition(arg2).toString().trim());
//                    //results.add("Select Product");
//                    for (Local_Data cn : contacts2)
//                    {
//                        product_category_code = cn.getCust_Code();
//
//                    }
//                }
//
//
//            }
        //}

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

        Intent i=new Intent(Target_REYC_Main.this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }


}

