package com.anchor.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;

public class Sub_Dealer_Item_Edit extends BaseActivity {
    int check = 0;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String scheme_code = "";
    String scheme_namen = "";
    private ArrayList<String> Scheme_array = new ArrayList<String>();
    String price = "";
    LoginDataBaseAdapter loginDataBaseAdapter;
    Spinner spnProductSpec, spnScheme;
    TextView editTextRP, editTextMRP, txtPrice, txt_rp, editTextSQ, textmrp;
    EditText txtDeleiveryQuantity1;
    static int quantity = 0;
    static float rp, mrp, totalprice;
    static String scheme = "";
    EditText editTextQuantity, spnProduct;
    static String category, productScheme;
    Button buttonAddMOre, buttonEditBack;
    ArrayAdapter<String> dataAdapterCategory, dataAdapterProductSpec, dataAdapterProduct;

    List<String> listProduct, listProductSpec;
    List<String> listScheme;
    ArrayList<String> Discount_list = new ArrayList<String>();
    ArrayAdapter<String> dataAdapterScheme;
    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();

    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    ArrayAdapter<String> Discount_Adapter;

    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    TextView t_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_item);

        // create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();


        SharedPreferences spf = Sub_Dealer_Item_Edit.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();


        cd = new ConnectionDetector(getApplicationContext());

        txtPrice = findViewById(R.id.txtPrice);
        editTextSQ = findViewById(R.id.editTextSQ);

        editTextSQ.setText(Global_Data.item_SL);

        txtDeleiveryQuantity1 = (EditText) findViewById(R.id.txtDeleiveryQuantity);

        txt_rp = (TextView) findViewById(R.id.textRP);
        textmrp = (TextView) findViewById(R.id.textmrp);
        t_error = (TextView) findViewById(R.id.t_error);
        // for label RP change
        SharedPreferences spf1 = this.getSharedPreferences("SimpleLogic", 0);
        String rpstr = spf1.getString("var_rp", "");
        txt_rp.setText(rpstr);

        txt_rp.setVisibility(View.GONE);
        textmrp.setVisibility(View.GONE);
        txtPrice.setVisibility(View.GONE);


        //TODO FOR SCHEME ERROR
        txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        //spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnProduct = (EditText) findViewById(R.id.spnProduct);
        spnProductSpec = (Spinner) findViewById(R.id.spnProductSpec);
        spnScheme = (Spinner) findViewById(R.id.spnScheme);

        editTextRP = (TextView) findViewById(R.id.editTextRP);
        editTextMRP = (TextView) findViewById(R.id.editTextMRP);
        editTextRP.setVisibility(View.GONE);
        editTextMRP.setVisibility(View.GONE);


        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

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
                        price = String.valueOf("");
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


        adapter_state2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, results);
        adapter_state3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, results2);

        Scheme_array.clear();
        Scheme_array.add("Select Scheme");

        List<Local_Data> scheme_name = dbvoc.getProductscheme_Name(Global_Data.item_no);
//results2.add("Select Variant");
        for (Local_Data s : scheme_name) {
            Scheme_array.add(s.getSche_disname());
            scheme_namen = s.getSche_disname().toString();


        }


        Discount_Adapter = new ArrayAdapter<String>(Sub_Dealer_Item_Edit.this, R.layout.spinner_item, Scheme_array);
        Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
        spnScheme.setAdapter(Discount_Adapter);


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


        } else {

            editTextMRP.setText("");
            editTextRP.setText("");
            // spnScheme.setEnabled(false);
            txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        }

        buttonAddMOre = (Button) findViewById(R.id.buttonAddMOre);
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));

        buttonEditBack = (Button) findViewById(R.id.buttonEditBack);
        buttonEditBack.setBackgroundColor(Color.parseColor("#414042"));

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
                    Intent intent = new Intent(Sub_Dealer_Item_Edit.this, SubDealer_PreviewActivity.class);
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
                    //down event
                    b.setBackgroundColor(Color.parseColor("#910505"));


                    int SQMO_Validator = 0;

                    if (editTextQuantity.length() != 0) {
                        SQMO_Validator = Integer.parseInt(editTextQuantity.getText().toString().trim()) % Integer.parseInt(Global_Data.item_SL);
                    }


                    if (editTextQuantity.length() == 0) {
                        Toast toast = Toast.makeText(Sub_Dealer_Item_Edit.this, "Please enter Quantity", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (SQMO_Validator != 0) {
                        Toast toast = Toast.makeText(Sub_Dealer_Item_Edit.this, "Entered Value Not A Multiple Of Item SQ Value.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        editTextQuantity.setText("");
                    } else {
                        loginDataBaseAdapter = loginDataBaseAdapter.open();

                        String s_price[] = txtPrice.getText().toString().split(":");
                        String discount_type = "";
                        String discount_amount = "";

                        if (Global_Data.GLOvel_SUB_GORDER_ID.equalsIgnoreCase("") || Global_Data.statusOrderActivity.equalsIgnoreCase("Yes")) {

                            try {
                                String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());
                                cd = new ConnectionDetector(Sub_Dealer_Item_Edit.this);
                                isInternetPresent = cd.isConnectingToInternet();
                                AppLocationManager appLocationManager = new AppLocationManager(Sub_Dealer_Item_Edit.this);
                                Log.d("Class LAT LOG", "Class LAT LOG" + appLocationManager.getLatitude() + " " + appLocationManager.getLongitude());
                                Log.d("Service LAT LOG", "Service LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);
                                PlayService_Location PlayServiceManager = new PlayService_Location(Sub_Dealer_Item_Edit.this);

                                if (PlayServiceManager.checkPlayServices(Sub_Dealer_Item_Edit.this)) {
                                    Log.d("Play LAT LOG", "Play LAT LOG" + Global_Data.GLOvel_LATITUDE + " " + Global_Data.GLOvel_LONGITUDE);

                                } else if (!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)) {
                                    Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                    Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                                }

                                if (isInternetPresent && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck_b(Global_Data.GLOvel_LATITUDE) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck_b(Global_Data.GLOvel_LONGITUDE)) {
                                    LocationAddress locationAddress = new LocationAddress();
                                    LocationAddress.getAddressFromLocation(Double.valueOf(Global_Data.GLOvel_LATITUDE), Double.valueOf(Global_Data.GLOvel_LONGITUDE),
                                            getApplicationContext(), new Sub_Dealer_Item_Edit.GeocoderHandler());
                                }

                                Global_Data.GLOvel_SUB_GORDER_ID = "R" + PINString;


                                loginDataBaseAdapter.insertSUBOrders("", Global_Data.GLOvel_SUB_GORDER_ID, Global_Data.GLOvel_USER_EMAIL, Global_Data.Sub_Dealer_Code, Global_Data.SUB_Mobile, Global_Data.Sub_Email, Global_Data.Dealer_Code, Global_Data.address, Global_Data.Sub_shop_name, Global_Data.GLOvel_LATITUDE, Global_Data.GLOvel_LONGITUDE, "", "", "", "", "", "", "", "", "", "", "");

                                if (Global_Data.statusOrderActivity.equalsIgnoreCase("Yes")) {
                                    //dbvoc.getDeleteTable("sub_order_products");

//                                    loginDataBaseAdapter.insertSUb_OrderProducts(" ", " ", Global_Data.GLOvel_SUB_GORDER_ID, "", Global_Data.Search_Category_name, Global_Data.Search_Product_name, Global_Data.product_dec, " ", "", " ", "", editTextQuantity.getText().toString().trim(), editTextRP.getText().toString().trim(), editTextMRP.getText().toString().trim(), s_price[1].trim(), "", "", Global_Data.order_retailer, " ", Global_Data.item_no, " ", Global_Data.product_dec);

                                    dbvoc.update_ORDER_ID_SUBDEALER(Global_Data.GLOvel_SUB_GORDER_ID);
                                }


                            } catch (Exception ex) {
                                ex.printStackTrace();

                            }

                        }


                        dbvoc.update_item_SUBDEALER(editTextQuantity.getText().toString().trim(), editTextMRP.getText().toString().trim(), s_price[1].trim(), discount_amount, discount_type, Global_Data.item_no, Global_Data.GLOvel_SUB_GORDER_ID, scheme_code);


                        // Toast.makeText(Sub_Dealer_Item_Edit.this, "Item Update Successfully",Toast.LENGTH_LONG).show();

                        Toast toast = Toast.makeText(Sub_Dealer_Item_Edit.this, "Item Update Successfully", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Global_Data.statusOrderActivity = "";
                        Intent intent = new Intent(Sub_Dealer_Item_Edit.this, SubDealer_PreviewActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        startActivity(intent);
                        finish();

                        return true;

                    }

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
                                t_error.setText("Entered Value Not A Multiple Of Item Item Minimum Order Quantity.");
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        txtPrice.setText("Total Price : " + "");
                        price = String.valueOf("0");
                        t_error.setText("");
                    }


                } else {
                    t_error.setText("");
                }

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
                            long price1 = (final_mrp) - (final_discount);
                            txtPrice.setText("Total Price : " + price1);
                            price = String.valueOf(price1);
                        } else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage")) {
                            Float final_mrp = (Float.valueOf(editTextRP.getText().toString())) * (Float.valueOf(editTextQuantity.getText().toString()));
                            int final_discount = Integer.parseInt(String.valueOf(s));
                            Float per_value = (final_mrp) * (Float.valueOf((final_discount))) / (100);
                            Float price1 = (final_mrp) - (per_value);

                            txtPrice.setText("Total Price : " + price1);
                            price = String.valueOf(price1);
                        }

                    }


                }
            }
        });

        spnScheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                productScheme = parent.getItemAtPosition(pos).toString();

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
        final Dialog dialognew = new Dialog(Sub_Dealer_Item_Edit.this);
        dialognew.setCancelable(false);
        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //tell the Dialog to use the dialog.xml as it's layout description
        dialognew.setContentView(R.layout.update_dialog_edit);

        final EditText userInput = (EditText) dialognew
                .findViewById(R.id.update_textdialog);

        final TextView headertext = (TextView) dialognew
                .findViewById(R.id.item_description);
        headertext.setText("");
        headertext.setText("ITEM DESCRIPTION");

        final Button Submit = (Button) dialognew
                .findViewById(R.id.update_textdialogclick);

        final Button update_cancel = (Button) dialognew
                .findViewById(R.id.update_cancel);

        update_cancel.setVisibility(View.GONE);

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value)) {
            userInput.setText(value);
        }

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

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress = "";
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    //locationAddress = " ";
            }
            //  LOCATION.setText(locationAddress);


            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(locationAddress)) {
                Global_Data.address = locationAddress;
                Log.d("GLOBEL ADDRESS G", "V" + locationAddress);

            } else {
                Global_Data.address = "";
                Log.d("GLOBEL ADDRESS G", "address not found.");
            }


        }
    }

}
