package com.anchor.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.animation.ActivitySwitcher;
import com.anchor.model.Category;
import com.anchor.model.Product;
import com.anchor.model.Scheme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Previous_orderNew_S1 extends BaseActivity {
    int check=0;
    int checkb=0;
    int checkd=0;
    MediaPlayer mpplayer;
    int check_product=0;
    int check_ProductSpec=0;
    String CategoriesSpinner = "";
    String str_var1;
    String S_ID;
    AutoCompleteTextView Product_Variant;
    String categ_name,subcateg_name;
    String ProductSpinner = "";
    String price = "";
    String str_variant;
    String product_code = "";
    String scheme_code = "";
    String scheme_namen = "";
    LoginDataBaseAdapter loginDataBaseAdapter;
    TextView txtWelcomeUser;

    Spinner spnCategory, spnProductSpec, spnScheme, spnProduct, spnBu, spnBusinessDiv;
    TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity, txt_rp;
    EditText txtDeleiveryQuantity1;
    static int quantity = 0, deleiveryQuantity = 0;
    static float rp, mrp, totalprice,totalprice1,totalprc_scheme, productprice;
    static String scheme = "";
    EditText editTextQuantity;
    static String category, productName, productSpec, productQuantity,
            productDeleiveryQuantity, productScheme, productrp, productmrp,
            producttotalPrice;
    Button buttonAddMOre, buttonPreviewOrder;
    AutoCompleteTextView autoCompleteTextView1;
    //ArrayList<DatabaseModel> dataCategories,dataVarients;
    ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct,dataAdapterBu,dataAdapterBd;
    //ArrayList productList = new ArrayList();
    List<String> listProduct,listProductSpec;
    List<String> listScheme;
    ArrayList<String> Discount_list = new ArrayList<String>();
    ArrayAdapter<String> dataAdapterScheme;
    static int categoryID,productID,schemeID;
    HashMap<String, String> categoriesMap,productsMap;
    int dbschemeID;

    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();
    private ArrayList<String> result_product = new ArrayList<String>();
    private ArrayList<String> Scheme_array = new ArrayList<String>();
    private ArrayList<String> result_bu = new ArrayList<String>();
    private ArrayList<String> result_Business = new ArrayList<String>();

    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    ArrayAdapter<String> Discount_Adapter;

    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);

    ArrayList<Category> dataCategories = new ArrayList<Category>();
    ArrayList<Product> dataProducts = new ArrayList<Product>();
    ArrayList<Scheme> dataScheme = new ArrayList<Scheme>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.new_order_new);

        //  Global_Data.Search_business_unit_name = "";
        Global_Data.Search_Category_name = "";
        Global_Data.Search_BusinessCategory_name = "";
        Global_Data.Search_brand_name = "";
        Global_Data.Order_hashmap.clear();

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        txtPrice = (TextView) findViewById(R.id.txtPrice1);
        //txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity1);
        //txtDeleiveryQuantity1 = (EditText) findViewById(R.id.txtDeleiveryQuantity);
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnProduct = (Spinner) findViewById(R.id.spnProduct);
        //spnProductSpec = (Spinner) findViewById(R.id.spnProductSpec);
        Product_Variant = (AutoCompleteTextView) findViewById(R.id.Product_Variant);
        spnScheme = (Spinner) findViewById(R.id.spnScheme1);
        spnBu = (Spinner) findViewById(R.id.spnBu);
        spnBusinessDiv = (Spinner) findViewById(R.id.spnBusinessDiv);

        editTextRP = (TextView) findViewById(R.id.editTextRP);
        editTextMRP = (TextView) findViewById(R.id.editTextMRP);
        txt_rp = (TextView) findViewById(R.id.textRP);
        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity1);
        txtWelcomeUser=(TextView) findViewById(R.id.txtWelcomeUser);

        mpplayer = new MediaPlayer();
        // for label RP change
        SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
        String rpstr=spf1.getString("var_rp", "");

        if(rpstr.length()>0)
        {
            txt_rp.setText(rpstr);
        }else{
            txt_rp.setText("RP");
        }

        String user_name = "";
        if(!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null"))
        {
            user_name = Global_Data.USER_FIRST_NAME.trim();
            if(!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null"))
            {
                user_name +=  " " + Global_Data.USER_LAST_NAME.trim();
            }
        }

        txtWelcomeUser.setText(user_name+" : "+Global_Data.emp_code);

        SharedPreferences sound = Previous_orderNew_S1.this.getSharedPreferences("SimpleLogic", 0);
        Global_Data.sound_file = sound.getString("var_addsound", "");

        SharedPreferences spf12 = Previous_orderNew_S1.this.getSharedPreferences("SimpleLogic", 0);
        Global_Data.app_sound = spf12.getBoolean("var_addmore", false);

        //for BU
        result_bu.clear();
        //result_bu.add("Select BU");


        // Reading all
        try
        {
            if(!Global_Data.Search_business_unit_name.equalsIgnoreCase(""))
            {
                String str_bunit = "";
                result_bu.clear();
                List<Local_Data> contact_bu = dbvoc.getB_Unit_byName(Global_Data.Search_business_unit_name);
                result_bu.add("Select BU");
                for (Local_Data cn : contact_bu) {
                    str_bunit = "" + cn.getBunit();
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str_bunit)) {
                        result_bu.add(str_bunit);
                    }
                }

                dataAdapterBu = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_bu);
                dataAdapterBu.setDropDownViewResource(R.layout.spinner_item);
                spnBu.setAdapter(dataAdapterBu);

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str_bunit) && !result_bu.isEmpty())
                {
                    int s = result_bu.indexOf(str_bunit.trim());
                    spnBu.setSelection(s);
                }

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str_bunit.trim())) {
                    result_Business.clear();
                    result_Business.add("Select Business Division");

//					results_beat.clear();
//					results_beat.add("Select Beat");
                    List<Local_Data> contacts2 = dbvoc.getBdivByBunit(str_bunit.trim());
                    for (Local_Data cn : contacts2) {

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getprimary_category())) {
                            result_Business.add(cn.getprimary_category());
                        }

                    }

                    dataAdapterBd = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_Business);
                    dataAdapterBd.setDropDownViewResource(R.layout.spinner_item);
                    spnBusinessDiv.setAdapter(dataAdapterBd);


                }

            }
            else
            {
                result_bu.clear();
                List<Local_Data> contact_bu = dbvoc.getAllB_Unit();
                result_bu.add("Select BU");
                for (Local_Data cn : contact_bu) {
                    String str_bunit = "" + cn.getBunit();
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(str_bunit)) {
                        result_bu.add(str_bunit);
                    }
                }

                dataAdapterBu = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_bu);
                dataAdapterBu.setDropDownViewResource(R.layout.spinner_item);
                spnBu.setAdapter(dataAdapterBu);

                result_Business.clear();
                result_Business.add("Select Business Division");
                dataAdapterBd = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_Business);
                dataAdapterBd.setDropDownViewResource(R.layout.spinner_item);
                spnBusinessDiv.setAdapter(dataAdapterBd);
            }
        }catch(Exception ex){ex.printStackTrace();}

//        dataAdapterBu = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_bu);
//        dataAdapterBu.setDropDownViewResource(R.layout.spinner_item);
//        spnBu.setAdapter(dataAdapterBu);

        results1.clear();
        results1.add("Select Business Category");

        adapter_state1 = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, results1);
        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
        spnCategory.setAdapter(adapter_state1);

        results.clear();
        results.add("Select Brand");
        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
        spnProduct.setAdapter(adapter_state2);

        Scheme_array.clear();
        Scheme_array.add("Select Scheme");

        Discount_Adapter = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, Scheme_array);
        Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
        spnScheme.setAdapter(Discount_Adapter);

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

        Product_Variant.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (Product_Variant.getRight() - Product_Variant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = Previous_orderNew_S1.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
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

                Global_Data.hideSoftKeyboard(Previous_orderNew_S1.this);

                if (Product_Variant.getText().toString().trim().equalsIgnoreCase("Multiselect")) {
                    Intent intent = new Intent(getApplicationContext(), ProductAll_Varients.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    //startActivityForResult(intent,SIGNATURE_ACTIVITY);
                    Previous_orderNew_S1.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                } else {
                    editTextQuantity.setFocusableInTouchMode(true);
                    editTextQuantity.setEnabled(true);

                    List<Local_Data> cont = dbvoc.getProductByCat(Product_Variant.getText().toString().trim());
                    //results2.add("Select Variant");
                    for (Local_Data cn1 : cont) {
                        String str_var = "" + cn1.getStateName();
                        str_var1 = "" + cn1.getMRP();
                        String str_var2 = "" + cn1.get_Description();
                        String str_var3 = "" + cn1.get_Claims();
                        Global_Data.amnt = "" + cn1.get_Description();
                        Global_Data.amnt1 = "" + cn1.get_Claims();
                        product_code = cn1.getCode();

                        categ_name = cn1.getCategory();
                        subcateg_name = cn1.getSubcateg();

                        editTextRP.setText(str_var);
                        editTextMRP.setText(str_var1);
                        txtPrice.setText("Total Price : " + "");

                        if (editTextQuantity.getText().toString().length() != 0) {
                            if (!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0")) {
                                long final_mrp = (Long.valueOf(editTextMRP.getText().toString())) * (Long.valueOf(editTextQuantity.getText().toString().trim()));
                                txtPrice.setText("Total Price : " + final_mrp);
                                price = String.valueOf(final_mrp);

                                // txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
                            } else {
                                if (!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase(null) && !editTextMRP.getText().toString().equalsIgnoreCase("null") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0")) {
                                    // Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
                                    // txtPrice.setText("Total Price : "+final_mrp);
                                    // price = String.valueOf(final_mrp);
                                    //txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
                                }
                            }
                        }
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

                    Scheme_array.clear();
                    Scheme_array.add("Select Scheme");

                    List<Local_Data> scheme_name = dbvoc.getProductscheme_Name(product_code.trim());
                    //results2.add("Select Variant");
                    for (Local_Data s : scheme_name) {
                        Scheme_array.add(s.getSche_disname());
                        scheme_namen = s.getSche_disname().toString();
                    }

                    //editTextQuantity.setFocusableInTouchMode(true);

                }

//					Discount_Adapter = new ArrayAdapter<String>(NewOrderActivity.this, R.layout.spinner_item, Scheme_array);
//					Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
//					spnScheme.setAdapter(Discount_Adapter);
//
//				if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(scheme_namen)){
//					int spinnerPosition = Discount_Adapter.getPosition(scheme_namen);
//					spnScheme.setSelection(spinnerPosition);
//				}

            }
        });

        Product_Variant.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //listScheme.clear();
                //listScheme.add("Select Scheme");

                //dataAdapterScheme.notifyDataSetChanged();
                //dataAdapterScheme.setDropDownViewResource(R.layout.spinner_item);
                //spnScheme.setAdapter(dataAdapterScheme);
                rp = 0.00f;
                mrp = 0.00f;
                productprice = rp;

                editTextRP.setText("" + rp);
                editTextMRP.setText("" + mrp);
                editTextQuantity.setFocusableInTouchMode(false);
                editTextQuantity.setEnabled(false);
                //txtPrice.setText("Total Price : ");

            }
        });

        editTextQuantity.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!String.valueOf(s).equalsIgnoreCase(""))
                {
                    if(Integer.parseInt(String.valueOf(s))<=0)
                    {
                        editTextQuantity.setText("");
                        txtPrice.setText("Total Price : "+"");
                        price = String.valueOf("");
                    }
                }
                else
                {
                    txtPrice.setText("Total Price : "+"");
                    //price = String.valueOf(" ");
                }
            }
        });
        Global_Data.GLOVEL_SubCategory_Button = "";

//		//Reading all
//	         List<Local_Data> contacts = dbvoc.getAllMain();
//	          for (Local_Data cn : contacts) {
//	        	Global_Data.local_user = ""+cn.getUser();
//	        	Global_Data.local_pwd = ""+cn.getPwd();
//	        	System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
//	        	//Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//	        	                             }

        SharedPreferences spf=Previous_orderNew_S1.this.getSharedPreferences("SimpleLogic",0);
        SharedPreferences.Editor editor=spf.edit();
        editor.putString("order", "new");
        editor.commit();






        listProduct = new ArrayList<String>();
        dataAdapterProduct = new ArrayAdapter<String>(
                this, R.layout.spinner_item, listProduct);

        listProductSpec = new ArrayList<String>();
        dataAdapterProductSpec = new ArrayAdapter<String>(
                this, R.layout.spinner_item, listProductSpec);


        spnBu.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub

                checkb=checkb+1;
                if(checkb>1) {
                    if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select BU")) {
                        result_Business.clear();
                        result_Business.add("Select Business Division");
                        dataAdapterBd = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_Business);
                        dataAdapterBd.setDropDownViewResource(R.layout.spinner_item);
                        spnBusinessDiv.setAdapter(dataAdapterBd);

                        results1.clear();
                        results1.add("Select Business Category");

                        adapter_state1 = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, results1);
                        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
                        spnCategory.setAdapter(adapter_state1);

                        results.clear();
                        results.add("Select Brand");
                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);

//					adapter_state1 = new ArrayAdapter<String>(NewOrderActivity.this, R.layout.spinner_item, results1);
//					adapter_state1.setDropDownViewResource(R.layout.spinner_item);
//					spnCategory.setAdapter(adapter_state1);
//
//				    adapter_state2 = new ArrayAdapter<String>(this, R.layout.spinner_item, results);
//					adapter_state2.setDropDownViewResource(R.layout.spinner_item);
//					spnProduct.setAdapter(adapter_state2);


                    } else {

                        String items = parent.getItemAtPosition(pos).toString();
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

                            dataAdapterBd = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, result_Business);
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

        spnBusinessDiv.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub

                checkd=checkd+1;
                if(checkd>1) {
                    if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select BU")) {
                        results1.clear();
                        results1.add("Select Business Category");

                        adapter_state1 = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, results1);
                        adapter_state1.setDropDownViewResource(R.layout.spinner_item);
                        spnCategory.setAdapter(adapter_state1);

                        results.clear();
                        results.add("Select Brand");
                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);


                    } else {

                        String items = parent.getItemAtPosition(pos).toString();
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

                            adapter_state1 = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, results1);
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



        spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub

                //editTextQuantity.setText("");
                if(!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))){
                    txtPrice.setText("Total Price : "+"");
                }

                check_product=check_product+1;
                if(check_product>1)
                {

                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Business Category")) {

                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

                        categ_name = "";
                        subcateg_name = "";
//							 Toast toast = Toast.makeText(getApplicationContext(),"Please Select Business Category",Toast.LENGTH_LONG);
//							 toast.setGravity(Gravity.CENTER, 0, 0);
//							 toast.show();

                    }
                    else
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Brand"))
                    {

                        categ_name = "";
                        subcateg_name = "";
                        results2.add("");
                        results2.clear();

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Previous_orderNew_S1.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                result_product);
                        Product_Variant.setThreshold(1);// will start working from
                        // first character
                        Product_Variant.setAdapter(adapter);// setting the adapter
                        // data into the
                        // AutoCompleteTextView
                        Product_Variant.setTextColor(Color.BLACK);
                        Product_Variant.setText("");
                    }
                    else
                    {
                        results2.clear();

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(parent.getItemAtPosition(pos).toString().trim())) {

                            Global_Data.Search_business_unit_name = spnBu.getSelectedItem().toString().trim();
                            Global_Data.Search_Category_name = spnBusinessDiv.getSelectedItem().toString().trim();
                            Global_Data.Search_BusinessCategory_name = spnCategory.getSelectedItem().toString().trim();
                            Global_Data.Search_brand_name = parent.getItemAtPosition(pos).toString().trim();

                            Global_Data.Order_hashmap.clear();
                            Intent intent = new Intent(getApplicationContext(), Preorder_Proall_Varient.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            //startActivityForResult(intent,SIGNATURE_ACTIVITY);
                            Previous_orderNew_S1.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            startActivity(intent);

//


                        }


                    }
//
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        Intent i = getIntent();
        String name = i.getStringExtra("retialer");
        if (i.hasExtra("data")) {
            //Log.e("data", "***********productList**********");
            Global_Data.productList=i.getParcelableArrayListExtra("productsList");
        }
		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8A0808")));

		actionBar.setTitle(name);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
*/
        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText(Global_Data.order_retailer +" "+"("+Global_Data.AmountOutstanding+"/"+Global_Data.AmountOverdue+")");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Previous_orderNew_S1.this.getSharedPreferences("SimpleLogic", 0);

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
            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}

       /* mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);*/

        buttonAddMOre = (Button) findViewById(R.id.buttonAddMOre);
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));

        buttonAddMOre.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View b, MotionEvent event) {

                // TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    mpplayer = new MediaPlayer();
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));

					/*if (spnCategory.getSelectedItem().toString()
							.equalsIgnoreCase("Select Category")
							|| spnProduct.getSelectedItem().toString()
									.equalsIgnoreCase("Select Product")
							|| spnCategory.getSelectedItem().toString()
									.equalsIgnoreCase("Select Variant")
							|| editTextQuantity.getText().toString().length() == 0) {

						//Toast.makeText(getApplicationContext(),"Please Fill details ", Toast.LENGTH_SHORT).setGravity(Gravity.CENTER, 0, 0).show();
						Toast toast = Toast.makeText(getApplicationContext(),"Please Fill details ", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					*/
                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Business Category")) {
                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Please Select Business Category", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Brand")) {
                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Please Select Brand", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    else if (Product_Variant.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Please Select Variant", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    else if (editTextQuantity.getText().toString().length() == 0) {
                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Please enter Quantity", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

//					else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")) {
//						Toast toast = Toast.makeText(NewOrderActivity.this,"Please Select Discount Type", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

                    else{


                        List<Local_Data> scheme_name = dbvoc.getProductscheme_code(spnScheme.getSelectedItem().toString().trim());
                        //results2.add("Select Variant");
                        if(scheme_name.size() > 0)
                        {
                            for (Local_Data s : scheme_name) {
                                scheme_code= s.getCode();
                            }
                        }
                        else
                        {
                            scheme_code = "";
                        }


                        Global_Data.order_category = spnCategory.getSelectedItem().toString();
                        //Global_Data.order_product = spnProduct.getSelectedItem().toString();
                        // Global_Data.order_variant = spnProductSpec.getSelectedItem().toString();
                        //Global_Data.order_variant = spnProduct.getText().toString().trim();

                        loginDataBaseAdapter=loginDataBaseAdapter.open();

                        String item_name = "";
                        List<Local_Data> contacts1 = dbvoc.getItemCode(spnCategory.getSelectedItem().toString().trim(),spnProduct.getSelectedItem().toString().trim(),Product_Variant.getText().toString().trim());

                        if(contacts1.size() <= 0)
                        {
                            Toast toast = Toast.makeText(Previous_orderNew_S1.this,
                                    "Variant Not Found", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else {


                            for (Local_Data cn1 : contacts1) {

                                Global_Data.item_code = cn1.getItem_Code();
                                Global_Data.GLOvel_ITEM_NUMBER = cn1.getItem_Code();
                                item_name = cn1.getProdname();

                            }

//				   	          if(Global_Data.sales_btnstring.equalsIgnoreCase("Secondary Sales / Retail Sales"))
//							  {
//					   	       dbvoc.getDeleteTableorder_bycustomer_PRE(Global_Data.order_retailer.trim(),"Secondary Sales / Retail Sales");
//							   dbvoc.getDeleteTableorderproduct_bycustomer_PRE(Global_Data.order_retailer.trim(),"Secondary Sales / Retail Sales");
//							  }

                            if (Global_Data.GLOVEL_ORDER_REJECT_FLAG.equalsIgnoreCase("TRUE")) {
                                List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLObalOrder_id, Global_Data.GLOvel_ITEM_NUMBER);

                                if (contactsn.size() > 0) {
                                    //	Toast.makeText(NewOrderActivity.this, "You already add this item in order", Toast.LENGTH_SHORT).show();
                                    Toast toast = Toast.makeText(Previous_orderNew_S1.this, "You already add this item in order", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else {
                                    //Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG).show();
                                    if (Global_Data.app_sound == true) {
                                        mpplayer = MediaPlayer.create(Previous_orderNew_S1.this, R.raw.cheer_8k);
                                        mpplayer.stop();
                                    } else {
                                        if (Global_Data.sound_file.length() > 0) {
                                            //Toast.makeText(NewOrderActivity.this, "sndfile:"+Global_Data.sound_file, Toast.LENGTH_SHORT).show();
                                            //mpplayer = new MediaPlayer();
                                            try {
                                                if (mpplayer.isPlaying()) {
                                                    mpplayer.reset();
                                                }
                                                mpplayer = MediaPlayer.create(Previous_orderNew_S1.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Audio/" + Global_Data.sound_file));
////                                    mp.start();
                                                //mMediaPlayer.setDataSource(filename);
                                                mpplayer.start();
                                                mpplayer.prepare();
                                            } catch (Exception e) {

                                            }

                                        } else {
                                            mpplayer = MediaPlayer.create(Previous_orderNew_S1.this, R.raw.cheer_8k);
                                            mpplayer.start();
                                        }
                                    }

                                    Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Item add successfully", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
//									  loginDataBaseAdapter.insertOrderProducts("", "",  Global_Data.GLObalOrder_id, "", spnCategory.getSelectedItem().toString() , spnProduct.getText().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,editTextMRP.getText().toString(), price, "", "",Global_Data.order_retailer,discount_amount,Global_Data.GLOvel_ITEM_NUMBER,discount_type);//Reading all

                                    loginDataBaseAdapter.insertOrderProducts(" ", " ", Global_Data.GLObalOrder_id, "", spnCategory.getSelectedItem().toString(), spnProduct.getSelectedItem().toString(), Global_Data.order_variant, " ",scheme_code, " ", "", editTextQuantity.getText().toString(), editTextRP.getText().toString().trim(), Global_Data.variant_mrp, price, "", "", Global_Data.order_retailer, " ", Global_Data.GLOvel_ITEM_NUMBER, " ", item_name);
                                }
                            } else {


                                //Long randomPIN = System.currentTimeMillis();
                                String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
                                Global_Data.variant_rr = "";
                                Global_Data.variant_mrp = editTextMRP.getText().toString();
                                Global_Data.order_qty = editTextQuantity.getText().toString();
                                String strAmount = String.valueOf(Global_Data.order_amount);

                                if (Global_Data.GLOvel_GORDER_ID.equalsIgnoreCase("")) {
                                    if (Global_Data.sales_btnstring.equalsIgnoreCase("Secondary Sales / Retail Sales")) {
                                        Global_Data.GLObalOrder_id = PINString;
                                        Global_Data.GLOvel_GORDER_ID = PINString;
                                    } else {
                                        Global_Data.GLObalOrder_id = PINString;
                                        Global_Data.GLOvel_GORDER_ID = PINString;
                                    }

                                    try
                                    {
                                        AppLocationManager appLocationManager = new AppLocationManager(Previous_orderNew_S1.this);
                                        Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
                                        Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
                                        PlayService_Location PlayServiceManager = new PlayService_Location(Previous_orderNew_S1.this);

                                        if(PlayServiceManager.checkPlayServices(Previous_orderNew_S1.this))
                                        {
                                            Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

                                        }
                                        else
                                        if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
                                        {
                                            Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                            Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                                        }

                                    }catch(Exception ex){ex.printStackTrace();}

                                    loginDataBaseAdapter.insertOrders("", Global_Data.GLOvel_GORDER_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "", Global_Data.order_retailer, Global_Data.order_state, Global_Data.order_city, Global_Data.sales_btnstring,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Glovel_BEAT_ID,"","","","","","","","","");
                                }

                                //loginDataBaseAdapter.insertOrders("", "", Global_Data.order_retailer, "", Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "");

                                //loginDataBaseAdapter.insertOrderProducts("", "", "0"+PINString, "", Global_Data.order_category , Global_Data.order_product , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", Global_Data.order_qty , Global_Data.variant_rr, Global_Data.variant_mrp, strAmount, "", "",Global_Data.order_retailer);//Reading all

                                List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLObalOrder_id, Global_Data.GLOvel_ITEM_NUMBER);

                                if (contactsn.size() > 0) {
                                    //Toast.makeText(NewOrderActivity.this, "You already add this item in order", Toast.LENGTH_SHORT).show();

                                    Toast toast = Toast.makeText(Previous_orderNew_S1.this, "You already add this item in order", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else {
                                    loginDataBaseAdapter.insertOrderProducts(" ", " ", Global_Data.GLOvel_GORDER_ID, "", spnCategory.getSelectedItem().toString(), spnProduct.getSelectedItem().toString(), Global_Data.order_variant, " ",scheme_code, " ", "", editTextQuantity.getText().toString(), editTextRP.getText().toString().trim(), Global_Data.variant_mrp, price, "", "", Global_Data.order_retailer, " ", Global_Data.item_code, " ", item_name);//Reading all

                                    //Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG).show();

                                    if (Global_Data.app_sound == true) {
                                        mpplayer = MediaPlayer.create(Previous_orderNew_S1.this, R.raw.cheer_8k);
                                        mpplayer.stop();
                                    } else {
                                        if (Global_Data.sound_file.length() > 0) {
                                            //Toast.makeText(NewOrderActivity.this, "sndfile:"+Global_Data.sound_file, Toast.LENGTH_SHORT).show();
                                            //mpplayer = new MediaPlayer();
                                            try {
                                                if (mpplayer.isPlaying()) {
                                                    mpplayer.reset();
                                                }
                                                mpplayer = MediaPlayer.create(Previous_orderNew_S1.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Audio/" + Global_Data.sound_file));
////                                    mp.start();
                                                //mMediaPlayer.setDataSource(filename);
                                                mpplayer.start();
                                                mpplayer.prepare();
                                            } catch (Exception e) {

                                            }

                                        } else {
                                            mpplayer = MediaPlayer.create(Previous_orderNew_S1.this, R.raw.cheer_8k);
                                            mpplayer.start();
                                        }
                                    }
                                    //mpplayer.start();
                                    Toast toast = Toast.makeText(Previous_orderNew_S1.this, "Item add successfully", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    //loginDataBaseAdapter.insertOrderProducts("", "",  Global_Data.GLOvel_GORDER_ID, "", spnCategory.getSelectedItem().toString() , spnProduct.getText().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,editTextMRP.getText().toString(), price, "", "",Global_Data.order_retailer,discount_amount,Global_Data.GLOvel_ITEM_NUMBER,discount_type);//Reading all
                                }
                            }

                            check = 0;
                            check_product = 0;
                            check_ProductSpec = 0;
                            editTextQuantity.setText("");
                            editTextQuantity.setEnabled(false);
                            spnCategory.setSelection(adapter_state1.getPosition("Select Business Category"));
                            // spnProductSpec.setSelection(adapter_state3.getPosition("Select Variant"));
                            Product_Variant.setText("");
                            spnProduct.setSelection(adapter_state2.getPosition("Select Brand"));
                            spnScheme.setSelection(Discount_Adapter.getPosition("Select Scheme"));
//									spnScheme.setEnabled(false);
                            editTextMRP.setText("");
                            txtPrice.setText("");
                            editTextRP.setText("");

                        }
                        // spnProduct.setText("");

//							spnCategory.setAdapter(adapter_state1);
//
//							listProduct.clear();
//							listProduct.add("Select Product");
//
//							dataAdapterProduct.notifyDataSetChanged();
//							dataAdapterProduct
//									.setDropDownViewResource(android.R.layout.simple_spinner_item);
//							//spnProduct.setAdapter(adapter_state2);
//
//							listProductSpec.clear();
//							listProductSpec.add("Select Variant");
//							dataAdapterProductSpec.notifyDataSetChanged();
//							dataAdapterProductSpec
//									.setDropDownViewResource(android.R.layout.simple_spinner_item);
//							spnProductSpec.setAdapter(adapter_state3);
//
//							listScheme.clear();
//							listScheme.add("Select Scheme");
//
//							dataAdapterScheme.notifyDataSetChanged();
//							dataAdapterScheme
//									.setDropDownViewResource(android.R.layout.simple_spinner_item);
//							spnScheme.setAdapter(dataAdapterScheme);
//
//							editTextRP.setText("" + rp);
//							editTextMRP.setText("" + mrp);
//							txtPrice.setText("Total Price : ");
//							rp = 0.00f;
//							mrp = 0.00f;
//							totalprice = 0.00f;
//							deleiveryQuantity = 0;
//							editTextQuantity.setText("");
                        //txtDeleiveryQuantity.setText("Delivery Quantity :");
                        return true;
                        //}
                    }

                    return true;
                }
                return false;
            }
        });

        buttonPreviewOrder = (Button) findViewById(R.id.buttonPreviewOrder);
        buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));

        buttonPreviewOrder.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View b, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    mpplayer.stop();
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));
                    // TODO Auto-generated method stub
                    if(Global_Data.GLOVEL_ORDER_REJECT_FLAG.equalsIgnoreCase("TRUE"))
                    {
                        Intent i=new Intent(Previous_orderNew_S1.this, Status_Activity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        if (!Global_Data.GLOvel_GORDER_ID.equalsIgnoreCase("")) {
									/*final Intent i = new Intent(getApplicationContext(),
											PreviewOrderActivity.class);*/
                            final Intent i = new Intent(getApplicationContext(),
                                    PreviewOrderSwipeActivity.class);
                            i.putParcelableArrayListExtra("productsList", Global_Data.productList);
                            i.putExtra("new","new");
                            SharedPreferences sp = Previous_orderNew_S1.this
                                    .getSharedPreferences("SimpleLogic", 0);

                            i.putExtra("retialer",
                                    "" + sp.getString("RetailerName", ""));
                            //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Global_Data.GLOVEL_LONG_DESC = "";
                            Global_Data.GLOVEL_CATEGORY_SELECTION = "";
                            Global_Data.GLOVEL_ITEM_MRP = "";

                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            ActivitySwitcher.animationOut(findViewById(R.id.containerNewOrder), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
                                @Override
                                public void onAnimationFinished() {
                                    startActivity(i);
                                    finish();
                                }
                            });
                            //NewOrderFragment.this.startActivity(i);
                        }

                        else {
                            //Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();

                            Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }



                    return true;
                }
                return false;
            }
        });

        spnCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {


                Global_Data.GLOVEL_ITEM_MRP = "";

//				category = parent.getItemAtPosition(pos).toString();
                Log.d("Globel categary", Global_Data.GLOVEL_CATEGORY_SELECTION);

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


                        results2.clear();


//						Toast toast = Toast.makeText(NewOrderActivity.this, "Please Select Business Category", Toast.LENGTH_LONG);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();

                    }
                    else
                    {

                        Global_Data.GLOVEL_CATEGORY_SELECTION = parent.getItemAtPosition(pos).toString();
                        //Intent intent = new Intent(getApplicationContext(), Filter_List.class);
                        Global_Data.GLOVEL_CATEGORY_NAME = parent.getItemAtPosition(pos).toString();


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

                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        editTextQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                {
                    try
                    {
                        if (editTextQuantity.getText().toString().length() != 0) {

                            if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")  && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
                            {
                                double final_mrp = (Double.valueOf(editTextMRP.getText().toString()))*(Double.valueOf(editTextQuantity.getText().toString().trim()));
                                txtPrice.setText("Total Price : "+final_mrp);
                                price = String.valueOf(final_mrp);

                                // txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
                            }
                            else
                            {
                                if(!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase(null) && !editTextMRP.getText().toString().equalsIgnoreCase("null")  && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
                                {
                                    // Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
                                    // txtPrice.setText("Total Price : "+final_mrp);
                                    // price = String.valueOf(final_mrp);
                                    //txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
                                }

                            }
//
                        }

                    }catch(Exception ex){ex.printStackTrace();}
                }


                //Field2.setText("");
            }
        });







        spnScheme.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                productScheme = parent.getItemAtPosition(pos).toString();
                //Toast.makeText(NewOrderActivity.this, "click appear.", Toast.LENGTH_SHORT).show();
                if (parent.getItemAtPosition(pos).toString()
                        .equalsIgnoreCase("Select Scheme")) {



                }
                else{




                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                mpplayer.stop();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        Global_Data.GLOVEL_LONG_DESC = "";
        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
        Global_Data.GLOVEL_ITEM_MRP = "";
        Global_Data.Search_business_unit_name = "";
        Global_Data.Search_Category_name = "";
        Global_Data.Search_BusinessCategory_name = "";
        Global_Data.Search_brand_name = "";

        Intent i=new Intent(Previous_orderNew_S1.this, Previous_orderNew_S2.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();


    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stubs
        super.onResume();
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
    }


}
