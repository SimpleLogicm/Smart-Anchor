package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cpm.simplelogic.helper.ContactInfo;


public class Pricing_Main extends BaseActivity {
    List<ContactInfo> result;
    private ArrayList<String> resultsvarient = new ArrayList<String>();
    ArrayList<HashMap<String, String>> SwipeList;
    String categ_name, subcateg_name;
    int check=0;
    int checkb=0;
    int checkd=0;
    String str_variant;
    String product_code = "";
    int check_product=0;
    int check_ProductSpec=0;
    AutoCompleteTextView Product_Variant;
    Spinner spnCategory, spnProductSpec, spnScheme, spnProduct, spnBu, spnBusinessDiv;;
    static final String TAG_ITEMNAME = "product_name";
    static final String TAG_QTY = "total_qty";
    static final String TAG_PRICE = "MRP";
    static final String TAG_RP = "RP";
    static final String TAG_AMOUNT = "amount";
    static final String TAG_ITEM_NUMBER = "item_number";
    static final String TAG_ITEM_SQ = "SQ";
    static final String TAG_ITEM_MQ = "MQ";
    ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct,dataAdapterBu,dataAdapterBd;;
    //ArrayList productList = new ArrayList();
    List<String> listProduct,listProductSpec;
    List<String> listScheme;

    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();
    private ArrayList<String> result_product = new ArrayList<String>();
    private ArrayList<String> result_bu = new ArrayList<String>();
    private ArrayList<String> result_Business = new ArrayList<String>();

    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    RecyclerView recList;
    RelativeLayout rlout_price,rlout_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.pricing);

        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnProduct = (Spinner) findViewById(R.id.spnProduct);
        spnBu = (Spinner) findViewById(R.id.spnBu);
        spnBusinessDiv = (Spinner) findViewById(R.id.spnBusinessDiv);
        Product_Variant = (AutoCompleteTextView) findViewById(R.id.Product_Variant);
        rlout_price = (RelativeLayout) findViewById(R.id.rlout_price);
        rlout_stock = (RelativeLayout) findViewById(R.id.rlout_stock);

        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);

        Global_Data.Stock_warehouse_flag = "";
        Global_Data.Stock_product_flag = "";
        Global_Data.Stock_product_flag_value_check = "";
        Global_Data.Stock_warehouse_flag_value_check = "";
        Global_Data.Search_business_unit_name = "";
        Global_Data.Search_Category_name = "";
        Global_Data.Search_BusinessCategory_name = "";
        Global_Data.Search_brand_name = "";

        SwipeList = new ArrayList<HashMap<String, String>>();

        rlout_stock.setBackgroundResource(R.drawable.single_wtab);
        rlout_price.setBackgroundResource(R.drawable.single_btab);

        rlout_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),
                        Stock_Main.class);
                startActivity(intent);
                finish();
            }
        });


        //for BU
        result_bu.clear();
        //result_bu.add("Select BU");


        // Reading all
        List<Local_Data> contact_bu = dbvoc.getAllB_Unit();
        result_bu.add("Select BU");
        for (Local_Data cn : contact_bu) {
            String str_bunit = "" + cn.getBunit();
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str_bunit)) {
                result_bu.add(str_bunit);
            }
        }

        dataAdapterBu = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, result_bu);
        dataAdapterBu.setDropDownViewResource(R.layout.spinner_item);
        spnBu.setAdapter(dataAdapterBu);

        results1.clear();
        results1.add("Select Business Category");

        adapter_state1 = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, results1);
        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
        spnCategory.setAdapter(adapter_state1);

        result_Business.clear();
        result_Business.add("Select Business Division");
        dataAdapterBd = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, result_Business);
        dataAdapterBd.setDropDownViewResource(R.layout.spinner_item);
        spnBusinessDiv.setAdapter(dataAdapterBd);

        results.clear();
        results.add("Select Brand");
        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
        spnProduct.setAdapter(adapter_state2);

        SharedPreferences spf=Pricing_Main.this.getSharedPreferences("SimpleLogic",0);
        SharedPreferences.Editor editor=spf.edit();
        editor.putString("order", "new");
        editor.commit();

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Pricing");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Pricing_Main.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
        try
        {
            int target  = (int) Math.round(sp.getFloat("Target",0));
            int achieved  = (int) Math.round(sp.getFloat("Achived",0));
            Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
            if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
            {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
            }else
            {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
            }

        }catch(Exception ex){ex.printStackTrace();}

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        //recList.setVisibility(View.INVISIBLE);



        Product_Variant.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Product_Variant.getRight() - Product_Variant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Pricing_Main.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        Product_Variant.showDropDown();
                        return true;
                    }
                }
                return false;
            }
        });

        spnBu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub

                checkb=checkb+1;
                if(checkb>1) {
                    if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select BU")) {
                        result_Business.clear();
                        result_Business.add("Select Business Division");
                        dataAdapterBd = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, result_Business);
                        dataAdapterBd.setDropDownViewResource(R.layout.spinner_item);
                        spnBusinessDiv.setAdapter(dataAdapterBd);

                        results1.clear();
                        results1.add("Select Business Category");

                        adapter_state1 = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, results1);
                        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
                        spnCategory.setAdapter(adapter_state1);

                        results.clear();
                        results.add("Select Brand");
                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);

                        recList.setVisibility(View.INVISIBLE);

//					adapter_state1 = new ArrayAdapter<String>(NewOrderActivity.this, R.layout.spinner_item, results1);
//					adapter_state1.setDropDownViewResource(R.layout.spinner_item);
//					spnCategory.setAdapter(adapter_state1);
//
//				    adapter_state2 = new ArrayAdapter<String>(this, R.layout.spinner_item, results);
//					adapter_state2.setDropDownViewResource(R.layout.spinner_item);
//					spnProduct.setAdapter(adapter_state2);


                    } else {

                        String items = parent.getItemAtPosition(pos).toString();
                        Global_Data.Search_business_unit_name= parent.getItemAtPosition(pos).toString();
                        //String C_ID = "";
                        Log.i("Selected item : ", items);

                        Log.i("Selected item : ", items);

//				List<Local_Data> contacts = dbvoc.getBunit_id(items);
//				for (Local_Data cn : contacts) {
//
//					S_ID = cn.getSTATE_ID();
//
//				}

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(items)) {
                            result_Business.clear();
                            result_Business.add("Select Business Division");

//					results_beat.clear();
//					results_beat.add("Select Beat");
                            List<Local_Data> contacts2 = dbvoc.getBdivByBunit(items);
                            for (Local_Data cn : contacts2) {

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getprimary_category())) {
                                    result_Business.add(cn.getprimary_category());
                                }

                            }

                            dataAdapterBd = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, result_Business);
                            dataAdapterBd.setDropDownViewResource(R.layout.spinner_item);
                            spnBusinessDiv.setAdapter(dataAdapterBd);


                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spnBusinessDiv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub

                checkd=checkd+1;
                if(checkd>1) {
                    if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select BU")) {
                        results1.clear();
                        results1.add("Select Business Category");

                        adapter_state1 = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, results1);
                        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
                        spnCategory.setAdapter(adapter_state1);

                        results.clear();
                        results.add("Select Brand");
                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);


                    } else {

                        String items = parent.getItemAtPosition(pos).toString();
                        Global_Data.Search_Category_name= parent.getItemAtPosition(pos).toString();
                        //String C_ID = "";
                        Log.i("Selected item : ", items);

                        Log.i("Selected item : ", items);

//				List<Local_Data> contacts = dbvoc.getBunit_id(items);
//				for (Local_Data cn : contacts) {
//
//					S_ID = cn.getSTATE_ID();
//
//				}

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(items)) {

                            results1.clear();
                            results1.add("Select Business Category");
                            List<Local_Data> contacts2 = dbvoc.getBusinee_category_Name(spnBu.getSelectedItem().toString(), items);
                            for (Local_Data cn : contacts2) {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getbusiness_category())) {
                                    String str_categ = "" + cn.getbusiness_category();
                                    results1.add(str_categ);
                                }
                            }

                            adapter_state1 = new ArrayAdapter<String>(Pricing_Main.this, R.layout.spinner_item, results1);
                            adapter_state1.setDropDownViewResource(R.layout.spinner_item);
                            spnCategory.setAdapter(adapter_state1);


                        }
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        Product_Variant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                Global_Data.hideSoftKeyboard(Pricing_Main.this);

//                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(categ_name)) {
//                    int spinnerPosition = adapter_state1.getPosition(categ_name);
//                    spnCategory.setSelection(spinnerPosition);
//                }

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(spnBu.getSelectedItem().toString())&& Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(spnBusinessDiv.getSelectedItem().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(spnCategory.getSelectedItem().toString())&& Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(spnProduct.getSelectedItem().toString())) {

                    List<Local_Data> cont1 = dbvoc.getProductvarientbyname(spnBu.getSelectedItem().toString(), spnBusinessDiv.getSelectedItem().toString(), spnCategory.getSelectedItem().toString(), spnProduct.getSelectedItem().toString(),Product_Variant.getText().toString());

                    if (cont1.size() <= 0) {
                        // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                        Pricing_Main.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast toast = Toast.makeText(Pricing_Main.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent i = new Intent(Pricing_Main.this, MainActivity.class);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        });

                    } else {

                        // pp=0;
                      //  result.clear();
                        result = new ArrayList<ContactInfo>();
                        for (Local_Data cnt1 : cont1) {

                            ContactInfo ci = new ContactInfo();
                            ci.name = cnt1.getProduct_nm();
                            ci.rp = cnt1.getStateName();
                            ci.mrp = cnt1.getMRP();
                            //ci.mrp = cnt1.getMRP();

                            result.add(ci);


                        }

                        Pricing_Main.this.runOnUiThread(new Runnable() {
                            public void run() {
                                //recList.setItemsCanFocus(true);

                                ContactAdapter ca = new ContactAdapter(Pricing_Main.this, result);
                                recList.setAdapter(ca);
                                ca.notifyDataSetChanged();

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Pricing_Main.this, android.R.layout.simple_spinner_dropdown_item, resultsvarient);
                                Product_Variant.setThreshold(1);// will start working from
                                // first character
                                Product_Variant.setAdapter(adapter);// setting the adapter
                                // data into the
                                // AutoCompleteTextView
                                Product_Variant.setTextColor(Color.BLACK);
                                //txttotalPreview.setText("Total : " + pp);


                            }
                        });


                    }
                }

            }
        });

        Global_Data.GLOVEL_SubCategory_Button = "";


        listProduct = new ArrayList<String>();
        dataAdapterProduct = new ArrayAdapter<String>(
                this, R.layout.spinner_item, listProduct);

        listProductSpec = new ArrayList<String>();
        dataAdapterProductSpec = new ArrayAdapter<String>(
                this, R.layout.spinner_item, listProductSpec);

//		listScheme = new ArrayList<String>();
//		dataAdapterScheme = new ArrayAdapter<String>(
//				this, R.layout.spinner_item, listScheme);

        final List<String> listCategory = new ArrayList<String>();
        listCategory.add("Select Business Category");

        adapter_state1 = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);


        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//				// TODO Auto-generated method stub

                check=check+1;
                if(check>1)
                {
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Business Category")) {
                        categ_name = "";
                        subcateg_name = "";
                        results.clear();
                        results.add("Select Brand");
                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);
                        recList.setVisibility(View.INVISIBLE);

                        results2.clear();

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Pricing_Main.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                result_product);
                        Product_Variant.setThreshold(1);// will start working from
                        // first character
                        Product_Variant.setAdapter(adapter);// setting the adapter
                        // data into the
                        // AutoCompleteTextView
                        Product_Variant.setTextColor(Color.BLACK);
                        Product_Variant.setText("");
                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

//                        Toast toast = Toast.makeText(getApplicationContext(), "Please Select Business Category", Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();

                    }
                    else
                    {
                        Global_Data.GLOVEL_CATEGORY_SELECTION = parent.getItemAtPosition(pos).toString();
                        //Intent intent = new Intent(getApplicationContext(), Filter_List.class);
                        Global_Data.GLOVEL_CATEGORY_NAME = parent.getItemAtPosition(pos).toString();
                        Global_Data.Search_BusinessCategory_name= parent.getItemAtPosition(pos).toString();
                        List<Local_Data> contacts2 = dbvoc.HSS_DescriptionITEM_ID(parent.getItemAtPosition(pos).toString().trim());
                        //results.add("Select Product");
                        for (Local_Data cn : contacts2) {
                            Global_Data.GLOVEL_CATEGORY_ID = cn.getCust_Code();


                            //Global_Data.local_pwd = ""+cn.getPwd();

                            //results.add(str_product);
                            //System.out.println("Local Values:-"+Global_Data.local_user);
                            //Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
                        }

                        results.clear();
                        //List<Local_Data> contacts22 = dbvoc.HSS_DescriptionITEM1_ID(Global_Data.GLOVEL_CATEGORY_ID);
                        List<Local_Data> contacts22 = dbvoc.getBusinee_subcategory_Name(spnBu.getSelectedItem().toString(),spnBusinessDiv.getSelectedItem().toString(),parent.getItemAtPosition(pos).toString().trim());
                        results.add("Select Brand");
                        for (Local_Data cn : contacts22) {

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getSubcateg())) {

                                String str_product = ""+cn.getSubcateg();
                                //Global_Data.local_pwd = ""+cn.getPwd();

                                results.add(str_product);
                                System.out.println("Local Values:-"+Global_Data.local_user);
                            }


                        }

                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);
                        // spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name)) {
                            int spinnerPosition = adapter_state2.getPosition(subcateg_name);
                            spnProduct.setSelection(spinnerPosition);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        spnProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub
                check_product=check_product+1;
                if(check_product>1)
                {

                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Business Category"))
                    {

                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

//                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Select Business Category", Toast.LENGTH_LONG);
//                        toast.setGravity(Gravity.CENTER, 0, 0);
//                        toast.show();

                    }
                    else
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Brand"))
                    {
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Pricing_Main.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                result_product);
                        Product_Variant.setThreshold(1);// will start working from
                        // first character
                        Product_Variant.setAdapter(adapter);// setting the adapter
                        // data into the
                        // AutoCompleteTextView
                        Product_Variant.setTextColor(Color.BLACK);
                        Product_Variant.setText("");
                        recList.setVisibility(View.INVISIBLE);
                        //recList.setVisibility(View.INVISIBLE);

                    }
                    else
                    {
                        Global_Data.Search_brand_name=parent.getItemAtPosition(pos).toString().trim();

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_business_unit_name) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.Search_Category_name)) {

                            List<Local_Data> cont1 = dbvoc.getProductvarientbycategoryandproduct(Global_Data.Search_business_unit_name,Global_Data.Search_Category_name,Global_Data.Search_BusinessCategory_name,Global_Data.Search_brand_name);

                            if (cont1.size() <= 0) {


                                Pricing_Main.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast toast = Toast.makeText(Pricing_Main.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();


                                    }
                                });

                            } else {
                                resultsvarient.clear();
                               // SwipeList.clear();
                               // pp=0;
                               // result.clear();
                                result = new ArrayList<ContactInfo>();
                                for (Local_Data cnt1 : cont1) {
                                    ContactInfo ci = new ContactInfo();
                                    ci.name = cnt1.getProduct_nm();
                                    ci.rp = cnt1.getStateName();
                                    ci.mrp = cnt1.getMRP();

                                    result.add(ci);
                                    resultsvarient.add(cnt1.getProduct_variant());


                                }

                                Pricing_Main.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        //recList.setItemsCanFocus(true);

                                        recList.setVisibility(View.VISIBLE);
                                        ContactAdapter ca = new ContactAdapter(Pricing_Main.this, result);
                                        recList.setAdapter(ca);
                                        ca.notifyDataSetChanged();

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Pricing_Main.this,android.R.layout.simple_spinner_dropdown_item,resultsvarient);
                                        Product_Variant.setThreshold(1);// will start working from
                                        // first character
                                        Product_Variant.setAdapter(adapter);// setting the adapter
                                        // data into the
                                        // AutoCompleteTextView
                                        Product_Variant.setTextColor(Color.BLACK);
                                        //txttotalPreview.setText("Total : " + pp);


                                    }
                                });



                            }
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i <= size; i++) {
            ContactInfo ci = new ContactInfo();
            ci.name = "101"+ " " + "abc";
            ci.rp = ContactInfo.SURNAME_PREFIX + i;
            ci.mrp = ContactInfo.EMAIL_PREFIX + i + "@test.com";

            result.add(ci);

        }

        return result;
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

        Intent i=new Intent(Pricing_Main.this, MainActivity.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

        }

    }
