package com.anchor.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReturnOrder1 extends BaseActivity {
    int check = 0;
    MediaPlayer mpplayer;
    String categ_name, subcateg_name;
    int check_product = 0;
    int check_ProductSpec = 0;
    AutoCompleteTextView Product_Variant;
    String price = "";
    String str_variant;
    LoginDataBaseAdapter loginDataBaseAdapter;
    Spinner spnCategory, spnScheme, spnProduct;
    TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity, txt_rp;
    static int quantity = 0;
    static float rp, mrp, totalprice, totalprice1, totalprc_scheme, productprice;
    static String scheme = "";
    EditText editTextQuantity;
    static String category, productScheme;
    Button buttonAddMOre, buttonPreviewOrder;
    ArrayAdapter<String> dataAdapterCategory, dataAdapterProductSpec, dataAdapterProduct;
    List<String> listProduct, listProductSpec;
    List<String> listScheme;

    ArrayAdapter<String> dataAdapterScheme;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();
    private ArrayList<String> result_product = new ArrayList<String>();
    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    ArrayList<Category> dataCategories = new ArrayList<Category>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.return_order_home);
        mpplayer = new MediaPlayer();

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        txtPrice = findViewById(R.id.txtPrice1);

        spnCategory = findViewById(R.id.spnCategory);
        spnProduct = findViewById(R.id.spnProduct);
        Product_Variant = findViewById(R.id.Product_Variant);

        spnScheme = findViewById(R.id.spnScheme1);


        editTextRP = findViewById(R.id.editTextRP);
        editTextMRP = findViewById(R.id.editTextMRP);

        editTextQuantity = findViewById(R.id.editTextQuantity1);

        txt_rp = findViewById(R.id.textRP);

        // for label RP change
        SharedPreferences spf1 = this.getSharedPreferences("SimpleLogic", 0);
        String rpstr = spf1.getString("var_rp", "");
        txt_rp.setText(rpstr);

        SharedPreferences spf11 = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);
        Global_Data.app_sound = spf11.getBoolean("var_addmore", false);

        SharedPreferences sound = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);
        Global_Data.sound_file = sound.getString("var_addsound", "");

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

        Product_Variant.setAdapter(adapter);// setting the adapter

        Product_Variant.setTextColor(Color.BLACK);

        Product_Variant.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (Product_Variant.getRight() - Product_Variant.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = ReturnOrder1.this.getCurrentFocus();
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

                try
                {
                    Global_Data.hideSoftKeyboard(ReturnOrder1.this);

                    editTextQuantity.setFocusableInTouchMode(true);
                    editTextQuantity.setEnabled(true);

                    editTextQuantity.setText("");
                    txtPrice.setText("");
                    price = "";

                    List<Local_Data> cont = dbvoc.getProductByCat(Product_Variant.getText().toString().trim());
                    //results2.add("Select Variant");
                    for (Local_Data cn1 : cont) {
                        String str_var = "" + cn1.getStateName();
                        String str_var1 = "" + cn1.getMRP();
                        String str_var2 = "" + cn1.get_Description();
                        String str_var3 = "" + cn1.get_Claims();
                        Global_Data.amnt = "" + cn1.get_Description();
                        Global_Data.amnt1 = "" + cn1.get_Claims();

                        categ_name = cn1.getCategory();
                        subcateg_name = cn1.getSubcateg();

                        editTextRP.setText(str_var);
                        editTextMRP.setText(str_var1);

                        txtPrice.setText("Total Price : " + "");

//                    if (editTextQuantity.getText().toString().length() != 0) {
//                        if (!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextRP.getText().toString().equalsIgnoreCase("0.0")) {
//                            try
//                            {
//                                double final_mrp = (Double.valueOf(editTextRP.getText().toString())) * (Double.valueOf(editTextQuantity.getText().toString().trim()));
//                                txtPrice.setText("Total Price : " + final_mrp);
//                                price = String.valueOf(final_mrp);
//                            }catch (Exception ex)
//                            {
//                                ex.printStackTrace();
//                            }
//
//                            // txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
//                        } else {
//                            if (!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextRP.getText().toString().equalsIgnoreCase(null) && !editTextRP.getText().toString().equalsIgnoreCase("null") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0")) {
//                                // Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
//                                // txtPrice.setText("Total Price : "+final_mrp);
//                                // price = String.valueOf(final_mrp);
//                                //txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
//                            }
//
//                        }
//                    }
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }



                try
                {
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
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }


            }
        });

        Product_Variant.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
//                    listScheme.clear();
//                    listScheme.add("Select Scheme");
//
//                    dataAdapterScheme.notifyDataSetChanged();
//                    dataAdapterScheme.setDropDownViewResource(R.layout.spinner_item);
//                    spnScheme.setAdapter(dataAdapterScheme);
//                    rp = 0.00f;
//                    mrp = 0.00f;
//                    productprice = rp;
//
//                    editTextRP.setText("" + rp);
//                    editTextMRP.setText("" + mrp);
//                    editTextQuantity.setFocusableInTouchMode(false);
//                    editTextQuantity.setEnabled(false);
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }

                //txtPrice.setText("Total Price : ");

            }
        });

        editTextQuantity.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try
                {
                    if (!String.valueOf(s).equalsIgnoreCase("")) {
                        if (Integer.parseInt(String.valueOf(s)) <= 0) {
                            editTextQuantity.setText("");
                            txtPrice.setText("Total Price : " + "");
                            price = "";
                        }
                    } else {
                        txtPrice.setText("Total Price : " + "");
                        //price = String.valueOf(" ");
                    }
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }


            }
        });


        Global_Data.GLOVEL_SubCategory_Button = "";


        SharedPreferences spf = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();

        //Reading all
        List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
        results1.add("Select Category");
        for (Local_Data cn : contacts1) {
            if (!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" ")) {
                String str_categ = "" + cn.getStateName();
                results1.add(str_categ);
            }
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

        listScheme = new ArrayList<String>();
        dataAdapterScheme = new ArrayAdapter<String>(
                this, R.layout.spinner_item, listScheme);

        final List<String> listCategory = new ArrayList<String>();
        listCategory.add("Select Category");

        adapter_state1 = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);
        spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {

                if (!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))) {
                    txtPrice.setText("Total Price : " + "");
                }


//
                check_product = check_product + 1;
                if (check_product > 1) {

                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {

                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

                        categ_name = "";
                        subcateg_name = "";
                        Toast toast = Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Product")) {
//					    	 	results2.clear();
//								results2.add("Select Variant");
//								adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//						        adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//								spnProductSpec.setAdapter(adapter_state3);

                        categ_name = "";
                        subcateg_name = "";
                        results2.add("");
                        results2.clear();

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReturnOrder1.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                result_product);
                        Product_Variant.setThreshold(1);// will start working from
                        // first character
                        Product_Variant.setAdapter(adapter);// setting the adapter
                        // data into the
                        // AutoCompleteTextView
                        Product_Variant.setTextColor(Color.BLACK);
                        Product_Variant.setText("");

                        //Toast.makeText(getApplicationContext(), "Please Select Product", Toast.LENGTH_LONG).show();

                    } else {

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
                                //Global_Data.local_pwd = ""+cn.getPwd();

                                results2.add(str_variant);
                                //System.out.println("Local Values:-"+Global_Data.local_user);
                                //Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();


                            }


//							 adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//							 adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//							 spnProductSpec.setAdapter(adapter_state3);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReturnOrder1.this, android.R.layout.simple_spinner_dropdown_item, results2);
                            Product_Variant.setThreshold(1);// will start working from
                            // first character
                            Product_Variant.setAdapter(adapter);// setting the adapter
                            // data into the
                            // AutoCompleteTextView
                            Product_Variant.setTextColor(Color.BLACK);


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


        for (int i = 0; i < dataCategories.size(); i++) {
            listCategory.add(dataCategories.get(i).getDesc());
        }

        dataAdapterCategory = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);
        dataAdapterCategory.setDropDownViewResource(R.layout.spinner_item);
        spnCategory.setAdapter(adapter_state1);

        if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase("")) {
            Log.d("Globel ", "in");
            spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
            Global_Data.GLOVEL_CATEGORY_SELECTION = "";

        }

        Intent i = getIntent();
        String name = i.getStringExtra("retialer");
        if (i.hasExtra("data")) {
            //Log.e("data", "***********productList**********");
            Global_Data.productList = i.getParcelableArrayListExtra("productsList");
        }

        try {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            mTitleTextView.setText(Global_Data.order_retailer + " " + "(" + Global_Data.AmountOutstanding + "/" + Global_Data.AmountOverdue + ")");

            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = ReturnOrder1.this.getSharedPreferences("SimpleLogic", 0);


            try {
                int target = Math.round(sp.getFloat("Target", 0));
                int achieved = Math.round(sp.getFloat("Achived", 0));
                Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
                if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
                } else {
                    int age = Math.round(age_float);

                    todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


        buttonAddMOre = findViewById(R.id.buttonAddMOre);
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));

        buttonAddMOre.setOnTouchListener(new OnTouchListener() {


            @Override
            public boolean onTouch(View b, MotionEvent event) {

                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    b.setBackgroundColor(Color.parseColor("#910505"));


                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                        Toast toast = Toast.makeText(ReturnOrder1.this, "Please Select Category", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
                        Toast toast = Toast.makeText(ReturnOrder1.this, "Please Select Product", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (Product_Variant.getText().toString().trim().equalsIgnoreCase("")) {
                        Toast toast = Toast.makeText(ReturnOrder1.this, "Please Select Variant", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (editTextQuantity.getText().toString().length() == 0) {
                        Toast toast = Toast.makeText(ReturnOrder1.this, "Please enter Quantity", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else {

                        Global_Data.order_category = spnCategory.getSelectedItem().toString();


                        loginDataBaseAdapter = loginDataBaseAdapter.open();

                        //					     //Reading all
                        String item_name = "";
                        List<Local_Data> contacts1 = dbvoc.getItemCode_return(spnCategory.getSelectedItem().toString().trim(), spnProduct.getSelectedItem().toString().trim(), Product_Variant.getText().toString().trim());
                        for (Local_Data cn1 : contacts1) {

                            Global_Data.item_code_return = cn1.getItem_Code();
                            Global_Data.GLOvel_ITEM_NUMBER_RETURN = cn1.getItem_Code();
                            item_name = cn1.getProdname();

                        }
                        if (Global_Data.GLOVEL_ORDER_REJECT_FLAG.equalsIgnoreCase("TRUE")) {
                            List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLObalOrder_id_return, Global_Data.GLOvel_ITEM_NUMBER);

                            if (contactsn.size() > 0) {
                                ///Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT).show();

                                Toast toast = Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                // Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG).show();

                                if (Global_Data.app_sound == true) {
                                    mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
                                    mpplayer.stop();
                                } else {
                                    if (Global_Data.sound_file.length() > 0) {
                                        //Toast.makeText(NewOrderActivity.this, "sndfile:"+Global_Data.sound_file, Toast.LENGTH_SHORT).show();
                                        //mpplayer = new MediaPlayer();
                                        try {
                                            if (mpplayer.isPlaying()) {
                                                mpplayer.reset();
                                            }
                                            mpplayer = MediaPlayer.create(ReturnOrder1.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Audio/" + Global_Data.sound_file));
////                                    mp.start();
                                            //mMediaPlayer.setDataSource(filename);
                                            mpplayer.start();
                                            mpplayer.prepare();
                                        } catch (Exception e) {

                                        }

                                    } else {
                                        mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
                                        mpplayer.start();
                                    }

                                }


                                Toast toast = Toast.makeText(ReturnOrder1.this, "Item add successfully", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                //loginDataBaseAdapter.insertOrderProducts("", "",  Global_Data.GLObalOrder_id, "", spnCategory.getSelectedItem().toString() , spnProduct.getText().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,editTextMRP.getText().toString(), price, "", "",Global_Data.order_retailer,discount_amount,Global_Data.GLOvel_ITEM_NUMBER,discount_type);//Reading all
                            }
                        } else {


                            String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
                            Global_Data.variant_rr = editTextRP.getText().toString();
                            Global_Data.variant_mrp = editTextMRP.getText().toString();
                            Global_Data.order_qty = editTextQuantity.getText().toString();
                            String strAmount = String.valueOf(Global_Data.order_amount);

                            if (Global_Data.GLOvel_GORDER_ID_RETURN.equalsIgnoreCase("")) {
                                if (Global_Data.sales_btnstring.equalsIgnoreCase("Secondary Sales / Retail Sales")) {
                                    Global_Data.GLObalOrder_id_return = PINString;
                                    Global_Data.GLOvel_GORDER_ID_RETURN = PINString;
                                } else {
                                    Global_Data.GLObalOrder_id_return = PINString;
                                    Global_Data.GLOvel_GORDER_ID_RETURN = PINString;
                                }

                                try {
                                    AppLocationManager appLocationManager = new AppLocationManager(ReturnOrder1.this);
                                    Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                                    Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                                    PlayService_Location PlayServiceManager = new PlayService_Location(ReturnOrder1.this);

                                    if (PlayServiceManager.checkPlayServices(ReturnOrder1.this)) {
                                        Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                                    } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                                        Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                        Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                                    }

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }

                                loginDataBaseAdapter.insertReturnOrders("", Global_Data.GLOvel_GORDER_ID_RETURN, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "", Global_Data.order_retailer, Global_Data.order_state, Global_Data.order_city, Global_Data.sales_btnstring, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, Global_Data.Glovel_BEAT_ID);
                            }

                            //loginDataBaseAdapter.insertOrders("", "", Global_Data.order_retailer, "", Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "");

                            //loginDataBaseAdapter.insertOrderProducts("", "", "0"+PINString, "", Global_Data.order_category , Global_Data.order_product , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", Global_Data.order_qty , Global_Data.variant_rr, Global_Data.variant_mrp, strAmount, "", "",Global_Data.order_retailer);//Reading all


                            List<Local_Data> contactsn = dbvoc.GetOrders_BY_ORDER_ID_RETURN(Global_Data.GLObalOrder_id_return, Global_Data.GLOvel_ITEM_NUMBER_RETURN);

                            if (contactsn.size() > 0) {
                                //Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT).show();

                                Toast toast = Toast.makeText(ReturnOrder1.this, "You already add this item in order", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            } else {
                                if (Global_Data.app_sound == true) {
                                    mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
                                    mpplayer.stop();
                                } else {
                                    if (Global_Data.sound_file.length() > 0) {
                                        //Toast.makeText(NewOrderActivity.this, "sndfile:"+Global_Data.sound_file, Toast.LENGTH_SHORT).show();
                                        //mpplayer = new MediaPlayer();
                                        try {
                                            if (mpplayer.isPlaying()) {
                                                mpplayer.reset();
                                            }
                                            mpplayer = MediaPlayer.create(ReturnOrder1.this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Audio/" + Global_Data.sound_file));
////                                    mp.start();
                                            //mMediaPlayer.setDataSource(filename);
                                            mpplayer.start();
                                            mpplayer.prepare();
                                        } catch (Exception e) {

                                        }

                                    } else {
                                        mpplayer = MediaPlayer.create(ReturnOrder1.this, R.raw.cheer_8k);
                                        mpplayer.start();
                                    }
                                }

                                Toast toast = Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                //loginDataBaseAdapter.insertOrderProducts("", "",  Global_Data.GLOvel_GORDER_ID, "", spnCategory.getSelectedItem().toString() , spnProduct.getText().toString() , Global_Data.order_variant , " " , spnScheme.getSelectedItem().toString() , " ", "", editTextQuantity.getText().toString() , Global_Data.variant_rr,editTextMRP.getText().toString(), price, "", "",Global_Data.order_retailer,discount_amount,Global_Data.GLOvel_ITEM_NUMBER,discount_type);//Reading all
                                loginDataBaseAdapter.insertReturnOrderProducts(" ", " ", Global_Data.GLOvel_GORDER_ID_RETURN, "", spnCategory.getSelectedItem().toString(), spnProduct.getSelectedItem().toString(), Global_Data.order_variant, " ", "", " ", "", editTextQuantity.getText().toString(), Global_Data.variant_rr, Global_Data.variant_mrp, price, "", "", Global_Data.order_retailer, " ", Global_Data.item_code_return, " ", item_name);//Reading all
                            }
                        }


                        check_product = 0;
                        check_ProductSpec = 0;
                        check = 0;
                        editTextQuantity.setText("");
                        editTextQuantity.setEnabled(false);
                        spnCategory.setSelection(adapter_state1.getPosition("Select Category"));
                        // spnProductSpec.setSelection(adapter_state3.getPosition("Select Variant"));
                        Product_Variant.setText("");
                        spnProduct.setSelection(adapter_state2.getPosition("Select Product"));
//	    					 spnScheme.setSelection(Discount_Adapter.getPosition("Select Scheme"));
//	    					 spnScheme.setEnabled(false);
                        editTextMRP.setText("");
                        txtPrice.setText("");
                        editTextRP.setText("");

                        return true;

                    }

                    return true;
                }
                return false;
            }
        });

        buttonPreviewOrder = findViewById(R.id.buttonPreviewOrder);
        buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));

        buttonPreviewOrder.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View b, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (Global_Data.app_sound == false) {
                        mpplayer.stop();
                    }
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));
                    // TODO Auto-generated method stub
                    if (Global_Data.GLOVEL_ORDER_REJECT_FLAG.equalsIgnoreCase("TRUE")) {
                        Intent i = new Intent(ReturnOrder1.this, Status_Activity.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    } else {
                        if (!Global_Data.GLOvel_GORDER_ID_RETURN.equalsIgnoreCase("")) {
									/*final Intent i = new Intent(getApplicationContext(),
											PreviewOrderActivity.class);*/
                            final Intent i = new Intent(getApplicationContext(),
                                    ReturnOrder2.class);
                            i.putParcelableArrayListExtra("productsList", Global_Data.productList);
                            i.putExtra("new", "new");
                            SharedPreferences sp = ReturnOrder1.this
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
                        } else {
                            //Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();

                            Toast toast = Toast.makeText(getApplicationContext(), "No Items Added", Toast.LENGTH_SHORT);
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
                Log.d("Globel categary", Global_Data.GLOVEL_CATEGORY_SELECTION);

                check = check + 1;
                if (check > 1) {

                    // editTextQuantity.setText("");

                    if (!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))) {
                        txtPrice.setText("Total Price : " + "");
                    }


//				if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
//				{
//					Log.d("Globel categary selection", "in");
//					spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
//
//
//				}
//				else
//				{
//                 if (Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
//                 {
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Category")) {

                        categ_name = "";
                        subcateg_name = "";

                        results.clear();
                        results.add("Select Product");
                        adapter_state2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results);
                        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                        spnProduct.setAdapter(adapter_state2);

                        results2.clear();

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReturnOrder1.this,
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

                        Toast toast = Toast.makeText(ReturnOrder1.this, "Please Select Category", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    } else {

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
                            String str_product = "" + cn.getStateName();
                            //Global_Data.local_pwd = ""+cn.getPwd();

                            results.add(str_product);
                            System.out.println("Local Values:-" + Global_Data.local_user);
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
                        //startActivity(intent);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {

                    try {
                        if (editTextQuantity.getText().toString().length() != 0) {

                            if (!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextRP.getText().toString().equalsIgnoreCase("0.0")) {

                                try
                                {
                                    double final_mrp = (Double.valueOf(editTextRP.getText().toString())) * (Double.valueOf(editTextQuantity.getText().toString().trim()));
                                    txtPrice.setText("Total Price : " + final_mrp);
                                    price = String.valueOf(final_mrp);
                                }catch (Exception ex)
                                {
                                    ex.printStackTrace();
                                }


                                // txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
                            } else {
                                if (!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextRP.getText().toString().equalsIgnoreCase(null) && !editTextRP.getText().toString().equalsIgnoreCase("null") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0")) {
                                    // Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
                                    // txtPrice.setText("Total Price : "+final_mrp);
                                    // price = String.valueOf(final_mrp);
                                    //txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
                                }

                            }
//
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });


        spnScheme.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                productScheme = parent.getItemAtPosition(pos).toString();

//                if (parent.getItemAtPosition(pos).toString()
//                        .equalsIgnoreCase("Select Scheme")) {
//
//
//                    if (!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextRP.getText().toString().equalsIgnoreCase("0.0")) {
//                        double final_mrp = (Double.valueOf(editTextRP.getText().toString())) * (Double.valueOf(editTextQuantity.getText().toString().trim()));
//                        txtPrice.setText("Total Price : " + final_mrp);
//                        price = String.valueOf(final_mrp);
//
//                        // txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
//                    } else {
//                        if (!editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextRP.getText().toString().equalsIgnoreCase(null) && !editTextRP.getText().toString().equalsIgnoreCase("null") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0")) {
//                            // Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()));
//                            // txtPrice.setText("Total Price : "+final_mrp);
//                            // price = String.valueOf(final_mrp);
//                            //txtDeleiveryQuantity.setText("Delivery Quantity:"+editTextQuantity.getText().toString());
//                        }
//
//                    }
//
//                } else {
//
//                    productprice = Float.valueOf(editTextMRP.getText().toString());
//
//                    totalprice = productprice * quantity;
//
//                    Global_Data.order_amount = totalprice;
//                    //Toast.makeText(NewOrderActivity.this, ""+Global_Data.order_amount ,Toast.LENGTH_SHORT).show();
//
//                    int aaa = Integer.parseInt(Global_Data.amnt);
//
//                    totalprc_scheme = ((quantity / productprice) * aaa);
//
//                    totalprice1 = (quantity + ((quantity / productprice) * aaa));
//
//                    txtPrice.setText("Total Price : " + String.format("%.2f", totalprc_scheme));
//
//                    txtDeleiveryQuantity.setText("Delivery Quantity : " + String.format("%.2f", totalprice1));
//
//
//                }


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
                if (Global_Data.app_sound == false) {
                    mpplayer.stop();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();
        if (Global_Data.app_sound == false) {
            mpplayer.stop();
        }
        Global_Data.GLOVEL_LONG_DESC = "";
        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
        Global_Data.GLOVEL_ITEM_MRP = "";
        // Global_Data.productList.clear();

        if (Global_Data.PREVIOUS_ORDER_BACK_FLAG_REURN.equalsIgnoreCase("TRUE")) {
            Intent i = new Intent(ReturnOrder1.this, ReturnOrder2.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            startActivity(i);
            finish();
        } else {
            if (!Global_Data.GLOvel_GORDER_ID_RETURN.equalsIgnoreCase("")) {

                AlertDialog alertDialog = new AlertDialog.Builder(ReturnOrder1.this).create(); //Read Update
                alertDialog.setTitle("Warning");
                alertDialog.setMessage(" Are you sure you want to back without order sync.?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        if (!Global_Data.GLObalOrder_id_return.equalsIgnoreCase("")) {
                            //Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        Intent i = new Intent(ReturnOrder1.this, Order.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(i);
                        finish();
                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.cancel();
                    }
                });


                alertDialog.show();
            } else {

                if (Global_Data.PREVIOUS_ORDER_BACK_FLAG_REURN.equalsIgnoreCase("TRUE")) {
                    if (!Global_Data.GLObalOrder_id_return.equalsIgnoreCase("")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    Intent i = new Intent(ReturnOrder1.this, ReturnOrder2.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                    finish();
                } else {
                    if (!Global_Data.GLObalOrder_id_return.equalsIgnoreCase("")) {
                        //Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG).show();
                        Toast toast = Toast.makeText(getApplicationContext(), "Order save successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                    Intent i = new Intent(ReturnOrder1.this, Order.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(i);
                    finish();
                }

            }
        }

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stubs
        super.onResume();
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
    }
}
