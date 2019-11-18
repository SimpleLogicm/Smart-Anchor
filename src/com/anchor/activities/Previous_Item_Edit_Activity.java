package com.anchor.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.model.Category;
import com.anchor.model.Product;
import com.anchor.model.Scheme;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cpm.simplelogic.helper.GPSTracker;

public class Previous_Item_Edit_Activity extends BaseActivity {
    int check = 0;
    String scheme_code = "";
    String scheme_namen = "";
    GPSTracker gps;


    private ArrayList<String> Scheme_array = new ArrayList<String>();
    String price = "";
    LoginDataBaseAdapter loginDataBaseAdapter;
    Spinner spnProductSpec, spnScheme;
    TextView editTextRP, editTextMRP, txtPrice, txt_rp,editTextSQ;
    EditText txtDeleiveryQuantity1;
    static int quantity = 0;
    static float rp, mrp, totalprice;
    static String scheme = "";
    EditText editTextQuantity, spnProduct;
    static String category, productName, productSpec, productQuantity,
            productDeleiveryQuantity, productScheme, productrp, productmrp,
            producttotalPrice;
    Button buttonAddMOre, buttonEditBack;

    //ArrayList<DatabaseModel> dataCategories,dataVarients;
    ArrayAdapter<String> dataAdapterCategory, dataAdapterProductSpec, dataAdapterProduct;
    //ArrayList productList = new ArrayList();
    List<String> listProduct, listProductSpec;
    List<String> listScheme;
    ArrayList<String> Discount_list = new ArrayList<String>();
    ArrayAdapter<String> dataAdapterScheme;
    static int categoryID, productID, schemeID;
    HashMap<String, String> categoriesMap, productsMap;
    int dbschemeID;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();

    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    ArrayAdapter<String> Discount_Adapter;

    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);

    ArrayList<Category> dataCategories = new ArrayList<Category>();
    ArrayList<Product> dataProducts = new ArrayList<Product>();
    ArrayList<Scheme> dataScheme = new ArrayList<Scheme>();
    TextView t_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_item);

        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        SharedPreferences spf = Previous_Item_Edit_Activity.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();


        txtPrice = findViewById(R.id.txtPrice);
        //txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity);
        txtDeleiveryQuantity1 = findViewById(R.id.txtDeleiveryQuantity);

        editTextSQ = findViewById(R.id.editTextSQ);

        editTextSQ.setText(Global_Data.item_SL);


        txt_rp = findViewById(R.id.textRP);
        t_error = findViewById(R.id.t_error);
        // for label RP change
        SharedPreferences spf1 = this.getSharedPreferences("SimpleLogic", 0);
        String rpstr = spf1.getString("var_rp", "");
        txt_rp.setText(rpstr);


        //TODO FOR SCHEME ERROR
        txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        //spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnProduct = findViewById(R.id.spnProduct);
        spnProductSpec = findViewById(R.id.spnProductSpec);
        spnScheme = findViewById(R.id.spnScheme);

        editTextRP = findViewById(R.id.editTextRP);
        editTextMRP = findViewById(R.id.editTextMRP);

        editTextQuantity = findViewById(R.id.editTextQuantity);

        editTextQuantity.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

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

            }
        });

        listProduct = new ArrayList<String>();
        dataAdapterProduct = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listProduct);

        listProductSpec = new ArrayList<String>();
        dataAdapterProductSpec = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listProductSpec);

        listScheme = new ArrayList<String>();
        dataAdapterScheme = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, listScheme);

        final List<String> listCategory = new ArrayList<String>();
        listCategory.add("Select Category");

        // adapter_state1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results1);
        adapter_state2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, results);
        adapter_state3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, results2);


        Scheme_array.clear();
        Scheme_array.add("Select Scheme");

        List<Local_Data> scheme_name = dbvoc.getProductscheme_Name(Global_Data.item_no);

        for (Local_Data s : scheme_name) {
            Scheme_array.add(s.getSche_disname());
            scheme_namen = s.getSche_disname();


        }


        Discount_Adapter = new ArrayAdapter<String>(Previous_Item_Edit_Activity.this, R.layout.spinner_item, Scheme_array);
        Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
        spnScheme.setAdapter(Discount_Adapter);


        List<Local_Data> schemeitem_name = dbvoc.Get_OrderProducts_scheme(Global_Data.GLObalOrder_id);
//results2.add("Select Variant");
        for (Local_Data ss : schemeitem_name) {

            if (!(ss.getSche_code().equalsIgnoreCase(""))) {
                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(scheme_namen)) {
                    int spinnerPosition = Discount_Adapter.getPosition(scheme_namen);
                    spnScheme.setSelection(spinnerPosition);
                }
            }
        }


        if (Global_Data.amount != "") {
            spnProduct.setText(Global_Data.product_dec);
            editTextQuantity.setText(Global_Data.total_qty);
            editTextMRP.setText(Global_Data.MRP);
            editTextRP.setText(Global_Data.RP);
            // spnScheme.setEnabled(true);
            txtPrice.setText("Total Price : " + Global_Data.amount);
            //txtDeleiveryQuantity1.setVisibility(View.VISIBLE);

            //TODO FOR SCHEME ERROR
            txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);

            if (!Global_Data.actual_discount.equalsIgnoreCase("")) {
                Log.d("Globel ", "in");


            }

        } else {
            //editTextQuantity.setFocusable(false) ;
            editTextMRP.setText("");
            editTextRP.setText("");
            // spnScheme.setEnabled(false);
            txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        }


        Intent i = getIntent();
        String name = i.getStringExtra("retialer");
        if (i.hasExtra("data")) {
            //Log.e("data", "***********productList**********");
            Global_Data.productList = i.getParcelableArrayListExtra("productsList");
        }


        buttonAddMOre = findViewById(R.id.buttonAddMOre);
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));

        buttonEditBack = findViewById(R.id.buttonEditBack);
        buttonEditBack.setBackgroundColor(Color.parseColor("#414042"));

//			buttonAddMOre.setOnTouchListener(new OnTouchListener() {
        buttonEditBack.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View b, MotionEvent event) {

                // TODO Auto-generated method stub
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));

                    Global_Data.GLOVEL_ORDER_REJECT_FLAG = "";
                    Intent intent = new Intent(Previous_Item_Edit_Activity.this, Previous_orderNew_S2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    startActivity(intent);
                    finish();

                    return true;
                }
                return false;
            }
        });

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
                    requestGPSPermissionsigna();


                    return true;
                }
                return false;
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

//			    	  if(!txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase("") && !txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase(null) && !txtDeleiveryQuantity1.getText().toString().equalsIgnoreCase("null"))
//			    	  {
//
//
//				    	  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
//				    	  {
//				    		  int final_mrp = (Integer.parseInt(editTextMRP.getText().toString()))*(Integer.parseInt(String.valueOf(s)));
//				    		  int final_discount = Integer.parseInt(String.valueOf(s));
//				    		  // int final_discount = (Integer.parseInt(txtDeleiveryQuantity1.getText().toString()))*(Integer.parseInt(String.valueOf(s)));
//				    		 // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
//				    		  int price1 = (final_mrp)-(final_discount);
//				    		  txtPrice.setText("Total Price : "+price1);
//				    		  price = String.valueOf(price1);
//				    	  }
//				    	  else
//			    		  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
//				    	  {
//			    			   Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(String.valueOf(s)));
//			    			   int final_discount = Integer.parseInt(String.valueOf(s));
//			    			   //Float final_discount = (Float.valueOf((txtDeleiveryQuantity1.getText().toString())))*(Float.valueOf(String.valueOf(s)));
//			//		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
//			//		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);
//
//			    			   Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
//			    			   Float price1 = (final_mrp)-(per_value);
//
//				    		  txtPrice.setText("Total Price : "+price1);
//				    		  price = String.valueOf(price1);
//				    	  }
//
//			    	  }
//			    	  else
//			    	  {
                    if (!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") && !editTextRP.getText().toString().equalsIgnoreCase("") && !editTextRP.getText().toString().equalsIgnoreCase("null") && !editTextRP.getText().toString().equalsIgnoreCase(null)) {

                        try {
                            int SQMO_Validator = Integer.parseInt(editTextQuantity.getText().toString().trim()) % Integer.parseInt(Global_Data.item_SL);
                            if (SQMO_Validator == 0) {
                                Double final_mrp = Double.valueOf(editTextRP.getText().toString()) * Double.valueOf(editTextQuantity.getText().toString().trim());
                                txtPrice.setText("Total Price : " + final_mrp);
                                price = String.valueOf(final_mrp);
                                t_error.setText("");
                            } else {
                                txtPrice.setText("Total Price : " + "");
                                price = "";
                                t_error.setText("Entered Value Not A Multiple Of Item Minimum Order Quantity.");
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        txtPrice.setText("Total Price : " + "");
                        price = "0";
                        t_error.setText("");
                    }

                    //}


                } else {
                    t_error.setText("");
                }
                //Field2.setText("");
            }
        });

        txtDeleiveryQuantity1.addTextChangedListener(new TextWatcher() {

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

                    if (!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")) {
                        if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees")) {
                            long final_mrp = (Long.valueOf(editTextRP.getText().toString())) * (Long.valueOf(editTextQuantity.getText().toString().trim()));
                            long final_discount = Long.parseLong(String.valueOf(s));
                            //int final_discount = (Integer.parseInt(String.valueOf(s)))*(Integer.parseInt(editTextQuantity.getText().toString()));
                            // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
                            long price1 = (final_mrp) - (final_discount);
                            txtPrice.setText("Total Price : " + price1);
                            price = String.valueOf(price1);
                        } else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage")) {
                            Float final_mrp = (Float.valueOf(editTextRP.getText().toString())) * (Float.valueOf(editTextQuantity.getText().toString()));
                            int final_discount = Integer.parseInt(String.valueOf(s));
                            //Float final_discount = (Float.valueOf((String.valueOf(s))))*(Float.valueOf(editTextQuantity.getText().toString()));
                            //		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
                            //		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);

                            Float per_value = (final_mrp) * (Float.valueOf((final_discount))) / (100);
                            Float price1 = (final_mrp) - (per_value);

                            txtPrice.setText("Total Price : " + price1);
                            price = String.valueOf(price1);
                        }

                    }


                }
                //Field2.setText("");
            }
        });

        spnScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                productScheme = parent.getItemAtPosition(pos).toString();
                //Toast.makeText(NewOrderActivity.this, "click appear.", Toast.LENGTH_SHORT).show();
                if (parent.getItemAtPosition(pos).toString()
                        .equalsIgnoreCase("Select Scheme")) {


                } else {


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

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        //super.onBackPressed();

        this.finish();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stubs
        super.onResume();
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
        //buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
    }


    public void getListViewnew(final String value) {
        final Dialog dialognew = new Dialog(Previous_Item_Edit_Activity.this);
        dialognew.setCancelable(false);
        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //tell the Dialog to use the dialog.xml as it's layout description
        dialognew.setContentView(R.layout.update_dialog_edit);

        final EditText userInput = dialognew
                .findViewById(R.id.update_textdialog);

        final TextView headertext = dialognew
                .findViewById(R.id.item_description);
        headertext.setText("");
        headertext.setText("ITEM DESCRIPTION");

        final Button Submit = dialognew
                .findViewById(R.id.update_textdialogclick);

        final Button update_cancel = dialognew
                .findViewById(R.id.update_cancel);

        update_cancel.setVisibility(View.GONE);

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value)) {
            userInput.setText(value);
        }

//	        final Button cancel = (Button) dialognew
//	                .findViewById(R.id.cancel);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(userInput.getText().toString())) {
                    spnProduct.setText(userInput.getText().toString());
                }


                dialognew.dismiss();


            }
        });



        dialognew.show();
    }

    private void requestGPSPermissionsigna() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //down event


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
//					if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
//						Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Please Select Category", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

//					else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
//							Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Please Select Product", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}

//					else if (spnProductSpec.getSelectedItem().toString().equalsIgnoreCase("Select Variant")) {
//						Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Please Select Variant", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

                            gps = new GPSTracker(Previous_Item_Edit_Activity.this);
                            if (!gps.canGetLocation()) {

                                gps.showSettingsAlertnew();
                            } else {
                                int SQMO_Validator = 0;
                                if(editTextQuantity.length() != 0)
                                {
                                    SQMO_Validator = Integer.parseInt(editTextQuantity.getText().toString().trim()) % Integer.parseInt(Global_Data.item_SL);
                                }

                                if (editTextQuantity.getText().toString().length() == 0) {
                                    Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this, "Please enter Quantity", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else if (SQMO_Validator != 0) {
                                    Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this, "Entered Value Not A Multiple Of Item Minimum Order Quantity.", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    editTextQuantity.setText("");
                                }


//					else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")) {
//						Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Please Select Discount Type", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

                                else {

                                    // TODO Auto-generated method stub


                                    loginDataBaseAdapter = loginDataBaseAdapter.open();

                                    String[] s_price = txtPrice.getText().toString().split(":");

//	//
//				   	        	                             }
                                    String discount_type = "";
                                    String discount_amount = "";
//
                                    List<Local_Data> scheme_name = dbvoc.getProductscheme_code(spnScheme.getSelectedItem().toString().trim());
//results2.add("Select Variant");
                                    if (scheme_name.size() > 0) {
                                        for (Local_Data s : scheme_name) {
                                            scheme_code = s.getCode();
                                        }
                                    } else {
                                        scheme_code = "";
                                    }

                                    // Long randomPIN = System.currentTimeMillis();
                                    String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());

                                    if (Global_Data.Previous_Order_ServiceOrder_ID.equalsIgnoreCase(Global_Data.Previous_Order_UpdateOrder_ID)) {
                                        Global_Data.Previous_Order_UpdateOrder_ID = PINString;

                                        Global_Data.GLObalOrder_id = PINString;
                                        Global_Data.GLOvel_GORDER_ID = PINString;
                                        // Global_Data.GLOvel_GORDER_ID = "Ord"+PINString;

                                        dbvoc.getDeleteTable("previous_orders");

                                        try {
                                            AppLocationManager appLocationManager = new AppLocationManager(Previous_Item_Edit_Activity.this);
                                            Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                                            Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                                            PlayService_Location PlayServiceManager = new PlayService_Location(Previous_Item_Edit_Activity.this);

                                            if (PlayServiceManager.checkPlayServices(Previous_Item_Edit_Activity.this)) {
                                                Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                                            } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                                                Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                                Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                                            }

                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }

                                        loginDataBaseAdapter.insertOrders("", Global_Data.Previous_Order_UpdateOrder_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "", Global_Data.order_retailer, Global_Data.order_state, Global_Data.order_city, Global_Data.sales_btnstring, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, Global_Data.Glovel_BEAT_ID, "", "", "", "", "", "", "", "", "");

                                        List<Local_Data> cont1 = dbvoc.getItemNamePrevious_OrderCheck(Global_Data.Previous_Order_ServiceOrder_ID, Global_Data.item_no);
                                        for (Local_Data cnt1 : cont1) {

                                            loginDataBaseAdapter.insertOrderProducts("", "", Global_Data.GLOvel_GORDER_ID, "", "", "", "", "", cnt1.getSche_code().trim(), " ", "", cnt1.getQty().trim(), cnt1.getRP().trim(), cnt1.getPrice().trim(), cnt1.getAmount().trim(), "", "", Global_Data.order_retailer, " ", cnt1.get_category_ids(), " ", cnt1.getProduct_nm());
                                        }

                                        dbvoc.getDeleteTable("previous_order_products");

//                            List<Local_Data> check_order_product = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLOvel_GORDER_ID,Global_Data.item_no);
//
//                            if(check_order_product.size() < 0)
//                            {
                                        loginDataBaseAdapter.insertOrderProducts(" ", " ", Global_Data.GLOvel_GORDER_ID, "", "", "", "", " ", scheme_code, " ", "", editTextQuantity.getText().toString(), editTextRP.getText().toString().trim(), editTextMRP.getText().toString().trim(), s_price[1].trim(), "", "", Global_Data.order_retailer, " ", Global_Data.item_no, " ", spnProduct.getText().toString().trim());
                                        // }

                                    } else {
                                        dbvoc.update_item(editTextQuantity.getText().toString().trim(), editTextMRP.getText().toString().trim(), s_price[1].trim(), discount_amount, discount_type, Global_Data.item_no, Global_Data.GLObalOrder_id, scheme_code);
                                    }

                                    Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this, "Item Update Successfully", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();


                                    Intent intent = new Intent(Previous_Item_Edit_Activity.this, Previous_orderNew_S2.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    startActivity(intent);
                                    finish();


//

                                    //}
                                }
                            }
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Need Permissions");
        builder.setCancelable(false);
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}
