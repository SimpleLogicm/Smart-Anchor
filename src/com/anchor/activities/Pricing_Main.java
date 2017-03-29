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
import java.util.List;

import cpm.simplelogic.helper.ContactInfo;


public class Pricing_Main extends BaseActivity {
    String categ_name, subcateg_name;
    int check=0;
    String str_variant;
    String product_code = "";
    int check_product=0;
    int check_ProductSpec=0;
    AutoCompleteTextView Product_Variant;
    Spinner spnCategory, spnProductSpec, spnScheme, spnProduct;

    ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct;
    //ArrayList productList = new ArrayList();
    List<String> listProduct,listProductSpec;
    List<String> listScheme;

    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();
    private ArrayList<String> result_product = new ArrayList<String>();

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

//        //Reading all
//        List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
//        results1.add("Select Category");
//        for (Local_Data cn : contacts1)
//        {
//            if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
//            {
//                String str_categ = ""+cn.getStateName();
//                results1.add(str_categ);
//            }
//        }
//
//        dataAdapterCategory = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);
//        dataAdapterCategory.setDropDownViewResource(R.layout.spinner_item);
//        spnCategory.setAdapter(dataAdapterCategory);

//        results.add("Select Product");
//
//        adapter_state2 = new ArrayAdapter<String>(this, R.layout.spinner_item, results);
//        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
//        spnProduct.setAdapter(adapter_state2);

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

        recList.setVisibility(View.INVISIBLE);

        results2.clear();
        List<Local_Data> contacts2 = dbvoc.getAllVariant();
        for (Local_Data cn : contacts2) {
            results2.add(cn.getStateName());
            result_product.add(cn.getStateName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,
                results2);
        Product_Variant.setThreshold(1);// will start working from
        // first character
        Product_Variant.setAdapter(adapter);// setting the adapter
        // data into the
        // AutoCompleteTextView
        Product_Variant.setTextColor(Color.BLACK);

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

        Product_Variant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                Global_Data.hideSoftKeyboard(Pricing_Main.this);
//                editTextQuantity.setFocusableInTouchMode(true);
//                editTextQuantity.setEnabled(true);

                List<Local_Data> cont = dbvoc.getProductByCat(Product_Variant.getText().toString().trim());
                //results2.add("Select Variant");
                for (Local_Data cn1 : cont) {
                    String str_var = "" + cn1.getStateName();
                    //str_var1 = ""+cn1.getMRP();
                    String str_var2 = "" + cn1.get_Description();
                    String str_var3 = "" + cn1.get_Claims();
                    Global_Data.amnt = "" + cn1.get_Description();
                    Global_Data.amnt1 = "" + cn1.get_Claims();
                    product_code = cn1.getCode();

                    categ_name = cn1.getCategory();
                    subcateg_name = cn1.getSubcateg();

//                    editTextRP.setText(str_var);
//                    editTextMRP.setText(str_var1);
//                    txtPrice.setText("Total Price : "+"");
//
//                    if (editTextQuantity.getText().toString().length() != 0) {
//                        if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")  && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
//                        {
//                            long final_mrp = (Long.valueOf(editTextMRP.getText().toString()))*(Long.valueOf(editTextQuantity.getText().toString().trim()));
//                            txtPrice.setText("Total Price : "+final_mrp);
//                            price = String.valueOf(final_mrp);
//
//                            // txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
//                        }
//                        else
//                        {
//                            if(!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase(null) && !editTextMRP.getText().toString().equalsIgnoreCase("null")  && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
//                            {
//                                // Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
//                                // txtPrice.setText("Total Price : "+final_mrp);
//                                // price = String.valueOf(final_mrp);
//                                //txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
//                            }
//                        }
//                    }
                }

                adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategory.setAdapter(adapter_state1);
                //spnCategory.setOnItemSelectedListener(NewOrderActivity.this);

                adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnProduct.setAdapter(adapter_state2);
                //spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(categ_name)) {
                    int spinnerPosition = adapter_state1.getPosition(categ_name);
                    spnCategory.setSelection(spinnerPosition);
                }
            }
        });

        Global_Data.GLOVEL_SubCategory_Button = "";


        //Reading all
        List<Local_Data> contacts12 = dbvoc.HSS_DescriptionITEM();
        results1.add("Select Category");
        for (Local_Data cn : contacts12) {
            if (!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" ")) {
                String str_categ = "" + cn.getStateName();
                results1.add(str_categ);
            }
        }

        adapter_state1 = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);
        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
        spnCategory.setAdapter(adapter_state1);
        //	spnCategory.setOnItemSelectedListener(NewOrderActivity.this);

        if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase("")) {
            Log.d("Globel cat", "in");
            spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
            Global_Data.GLOVEL_CATEGORY_SELECTION = "";

        }


        results.add("Select Product");
        adapter_state2 = new ArrayAdapter<String>(this, R.layout.spinner_item, results);
        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
        spnProduct.setAdapter(adapter_state2);


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
        listCategory.add("Select Category");

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
                            .equalsIgnoreCase("Select Category")) {
                        categ_name = "";
                        subcateg_name = "";
                        results.clear();
                        results.add("Select Product");
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

                        Toast toast = Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                    else
                    {
                        Global_Data.GLOVEL_CATEGORY_SELECTION = parent.getItemAtPosition(pos).toString();
                        //Intent intent = new Intent(getApplicationContext(), Filter_List.class);
                        Global_Data.GLOVEL_CATEGORY_NAME = parent.getItemAtPosition(pos).toString();

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
                        List<Local_Data> contacts22 = dbvoc.HSS_DescriptionITEM1_category_name(parent.getItemAtPosition(pos).toString().trim());
                        results.add("Select Product");
                        for (Local_Data cn : contacts22) {
                            String str_product = ""+cn.getStateName();
                            //Global_Data.local_pwd = ""+cn.getPwd();

                            results.add(str_product);
                            System.out.println("Local Values:-"+Global_Data.local_user);
                            //Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
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

                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category"))
                    {

                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

                        Toast toast = Toast.makeText(getApplicationContext(),  "Please Select Category", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                    else
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Product"))
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

                    }
                    else
                    {

                        results2.clear();

                        List<Local_Data> contacts33 = dbvoc.HSS_DescriptionITEM1_IDD(parent.getItemAtPosition(pos).toString().trim());
                        for (Local_Data cn : contacts33) {
                            Global_Data.GLOVEL_PRODUCT_ID = cn.getCust_Code();
                        }

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOVEL_PRODUCT_ID)) {

                            //List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_ID(Global_Data.GLOVEL_PRODUCT_ID);
                            List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_BYNAME(spnCategory.getSelectedItem().toString().trim(), parent.getItemAtPosition(pos).toString().trim());
                            // results2.add("Select Variant");
                            for (Local_Data cn : contacts3) {
                                str_variant = "" + cn.getStateName();

                                results2.add(str_variant);

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Pricing_Main.this, android.R.layout.simple_spinner_dropdown_item, results2);
                            Product_Variant.setThreshold(1);// will start working from
                            // first character
                            Product_Variant.setAdapter(adapter);// setting the adapter
                            // data into the
                            // AutoCompleteTextView
                            Product_Variant.setTextColor(Color.BLACK);


                        }


                        List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_BYNAMENEW(spnCategory.getSelectedItem().toString().trim(), parent.getItemAtPosition(pos).toString().trim(),Product_Variant.getText().toString().trim());

                        if(contacts3.size() <= 0 && !Product_Variant.getText().toString().trim().equalsIgnoreCase(""))
                        {
                            // Toast.makeText(Pricing_Main.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

//                            Toast toast = Toast.makeText(getApplicationContext(),  "Sorry No Record Found.", Toast.LENGTH_LONG);
//                            toast.setGravity(Gravity.CENTER, 0, 0);
//                            toast.show();

                            Product_Variant.setTextColor(Color.BLACK);
                            Product_Variant.setText("");
                            //Toast.makeText(getApplicationContext(), "Please Select Product", Toast.LENGTH_LONG).show();

                            recList.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            if (Product_Variant.length() > 0) {
                                recList.setVisibility(View.VISIBLE);
                                List<ContactInfo> result = new ArrayList<ContactInfo>();
                                for (Local_Data cn : contacts3) {

                                    ContactInfo ci = new ContactInfo();
                                    ci.name = cn.get_product_desc();
                                    ci.rp = cn.getRP();
                                    ci.mrp = cn.getMRP();

                                    result.add(ci);
                                }

                                ContactAdapter ca = new ContactAdapter(Pricing_Main.this, result);
                                recList.setAdapter(ca);
                                ca.notifyDataSetChanged();
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
