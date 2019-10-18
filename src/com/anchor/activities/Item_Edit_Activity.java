package com.anchor.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class Item_Edit_Activity extends BaseActivity {
    int check = 0;
    String scheme_code = "";
    String scheme_namen = "";
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


        SharedPreferences spf = Item_Edit_Activity.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();


        txtPrice =  findViewById(R.id.txtPrice);
        editTextSQ = findViewById(R.id.editTextSQ);

        editTextSQ.setText(Global_Data.item_SL);

        txtDeleiveryQuantity1 = findViewById(R.id.txtDeleiveryQuantity);

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
//
//		  Discount_list.add("Select Discount Type");
//		  Discount_list.add("Rupees");
//		  Discount_list.add("Percentage");
//
//		  Discount_Adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Discount_list);
//		  Discount_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//		  spnScheme.setAdapter(Discount_Adapter);

//		  adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  city_spinner.setAdapter(adapter_state1);
//		  city_spinner.setOnItemSelectedListener(this);
//		  
//		  adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  beat_spinner.setAdapter(adapter_state2);
//		  beat_spinner.setOnItemSelectedListener(this);
//		  
//		  adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		  retail_spinner.setAdapter(adapter_state3);
//		  retail_spinner.setOnItemSelectedListener(this);

        Scheme_array.clear();
        Scheme_array.add("Select Scheme");

        List<Local_Data> scheme_name = dbvoc.getProductscheme_Name(Global_Data.item_no);
//results2.add("Select Variant");
        for (Local_Data s : scheme_name) {
            Scheme_array.add(s.getSche_disname());
            scheme_namen = s.getSche_disname();


        }


        Discount_Adapter = new ArrayAdapter<String>(Item_Edit_Activity.this, R.layout.spinner_item, Scheme_array);
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
                //spnScheme.setSelection(Discount_Adapter.getPosition(Global_Data.actual_discount));
                //Global_Data.GLOVEL_CATEGORY_SELECTION = "";

                //txtDeleiveryQuantity1.setText(Global_Data.scheme_amount);

            }

        } else {
            //editTextQuantity.setFocusable(false) ;
            editTextMRP.setText("");
            editTextRP.setText("");
            // spnScheme.setEnabled(false);
            txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        }

        buttonAddMOre = findViewById(R.id.buttonAddMOre);
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));

        buttonEditBack = findViewById(R.id.buttonEditBack);
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
                    Intent intent = new Intent(Item_Edit_Activity.this, PreviewOrderSwipeActivity.class);
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
//						Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Category", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

//					else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
//							Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Product", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}

//					else if (spnProductSpec.getSelectedItem().toString().equalsIgnoreCase("Select Variant")) {
//						Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Variant", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

                    int SQMO_Validator = 0;

                    if(editTextQuantity.length() != 0)
                    {
                         SQMO_Validator = Integer.parseInt(editTextQuantity.getText().toString().trim()) % Integer.parseInt(Global_Data.item_SL);
                    }




                    if (editTextQuantity.length() == 0) {
                        Toast toast = Toast.makeText(Item_Edit_Activity.this, "Please enter Quantity", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (SQMO_Validator != 0) {
                        Toast toast = Toast.makeText(Item_Edit_Activity.this, "Entered Value Not A Multiple Of Item SQ Value.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        editTextQuantity.setText("");
                    }


//					else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")) {
//						Toast toast = Toast.makeText(Item_Edit_Activity.this,"Please Select Discount Type", Toast.LENGTH_SHORT);
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

                        dbvoc.update_item(editTextQuantity.getText().toString().trim(), editTextMRP.getText().toString().trim(), s_price[1].trim(), discount_amount, discount_type, Global_Data.item_no, Global_Data.GLObalOrder_id, scheme_code);
                        // Toast.makeText(Item_Edit_Activity.this, "Item Update Successfully",Toast.LENGTH_LONG).show();

                        Toast toast = Toast.makeText(Item_Edit_Activity.this, "Item Update Successfully", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        if (Global_Data.GLOVEL_ORDER_REJECT_FLAG == "") {
                            Intent intent = new Intent(Item_Edit_Activity.this, PreviewOrderSwipeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(Item_Edit_Activity.this, Status_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            startActivity(intent);
                            finish();
                        }

//							
                        return true;
                        //}
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
                                t_error.setText("Entered Value Not A Multiple Of Item Item Minimum Order Quantity.");
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
        final Dialog dialognew = new Dialog(Item_Edit_Activity.this);
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

}
