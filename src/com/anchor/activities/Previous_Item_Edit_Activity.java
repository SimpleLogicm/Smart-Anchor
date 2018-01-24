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

import com.anchor.model.Category;
import com.anchor.model.Product;
import com.anchor.model.Scheme;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Previous_Item_Edit_Activity extends BaseActivity {
    int check=0;
    String scheme_code = "";
    String scheme_namen = "";


    private ArrayList<String> Scheme_array = new ArrayList<String>();

    int activity_load_flag = 0;
    String CategoriesSpinner = "";
    String ProductSpinner = "";
    String price = "";
    LoginDataBaseAdapter loginDataBaseAdapter;
    Spinner spnProductSpec, spnScheme;
    TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity,txt_rp;
    EditText txtDeleiveryQuantity1;
    static int quantity = 0, deleiveryQuantity = 0;
    static float rp, mrp, totalprice, productprice;
    static String scheme = "";
    EditText editTextQuantity,spnProduct;
    static String category, productName, productSpec, productQuantity,
            productDeleiveryQuantity, productScheme, productrp, productmrp,
            producttotalPrice;
    Button buttonAddMOre,buttonEditBack;

    //ArrayList<DatabaseModel> dataCategories,dataVarients;
    ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct;
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
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        // Global_Data.GLOVEL_SubCategory_Button = "";

//		//Reading all
//	         List<Local_Data> contacts = dbvoc.getAllMain();
//	          for (Local_Data cn : contacts) {
//	        	Global_Data.local_user = ""+cn.getUser();
//	        	Global_Data.local_pwd = ""+cn.getPwd();
//	        	System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
//	        	//Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//	        	                             }

        SharedPreferences spf=Previous_Item_Edit_Activity.this.getSharedPreferences("SimpleLogic",0);
        SharedPreferences.Editor editor=spf.edit();
        editor.putString("order", "new");
        editor.commit();

        //Reading all
//	         List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
//	         results1.add("Select Category");
//	          for (Local_Data cn : contacts1)
//	          {
//	        	  if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
//	        	  {
//	        		  String str_categ = ""+cn.getStateName();
//	        		  results1.add(str_categ);
//	        	  }
//
//	        	//Global_Data.local_pwd = ""+cn.getPwd();
//
//
//	        	//System.out.println("Local Values:-"+Global_Data.local_user);
//	        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//
//	          }


        TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
        //question_value.setTypeface(null,Typeface.BOLD);
        // welcomeUser.setText(spf.getString("FirstName", "")+" "+ spf.getString("LastName", ""));

        txtPrice = (TextView) findViewById(R.id.txtPrice);
        //txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity);
        txtDeleiveryQuantity1 = (EditText) findViewById(R.id.txtDeleiveryQuantity);

        txt_rp = (TextView) findViewById(R.id.textRP);
        t_error = (TextView) findViewById(R.id.t_error);
        // for label RP change
        SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
        String rpstr=spf1.getString("var_rp", "");
        txt_rp.setText(rpstr);


        //TODO FOR SCHEME ERROR
        txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        //spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnProduct = (EditText) findViewById(R.id.spnProduct);
        spnProductSpec = (Spinner) findViewById(R.id.spnProductSpec);
        spnScheme = (Spinner) findViewById(R.id.spnScheme);

        editTextRP = (TextView) findViewById(R.id.editTextRP);
        editTextMRP = (TextView) findViewById(R.id.editTextMRP);

        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);

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
        adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results);
        adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results2);
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
            scheme_namen = s.getSche_disname().toString();


        }


        Discount_Adapter = new ArrayAdapter<String>(Previous_Item_Edit_Activity.this, R.layout.spinner_item, Scheme_array);
        Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
        spnScheme.setAdapter(Discount_Adapter);


        List<Local_Data> schemeitem_name = dbvoc.Get_OrderProducts_scheme(Global_Data.GLObalOrder_id);
//results2.add("Select Variant");
        for (Local_Data ss : schemeitem_name) {

            if(!(ss.getSche_code().toString().equalsIgnoreCase("")))
            {
                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(scheme_namen)){
                    int spinnerPosition = Discount_Adapter.getPosition(scheme_namen);
                    spnScheme.setSelection(spinnerPosition);
                }
            }
        }




        if(Global_Data.amount != "")
        {
            spnProduct.setText(Global_Data.product_dec);
            editTextQuantity.setText(Global_Data.total_qty);
            editTextMRP.setText(Global_Data.MRP);
            editTextRP.setText(Global_Data.RP);
            // spnScheme.setEnabled(true);
            txtPrice.setText("Total Price : "+Global_Data.amount);
            //txtDeleiveryQuantity1.setVisibility(View.VISIBLE);

            //TODO FOR SCHEME ERROR
            txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);

            if (!Global_Data.actual_discount.equalsIgnoreCase(""))
            {
                Log.d("Globel ", "in");
                //spnScheme.setSelection(Discount_Adapter.getPosition(Global_Data.actual_discount));
                //Global_Data.GLOVEL_CATEGORY_SELECTION = "";

                //txtDeleiveryQuantity1.setText(Global_Data.scheme_amount);

            }

        }
        else
        {
            //editTextQuantity.setFocusable(false) ;
            editTextMRP.setText("");
            editTextRP.setText("");
            // spnScheme.setEnabled(false);
            txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
        }

//		  spnProduct.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View view) {
//
//	            	if(!spnProduct.getText().toString().trim().equalsIgnoreCase(""))
//	            	{
//	            		getListViewnew(spnProduct.getText().toString().trim());
//	            	}
//
//
//	            }
//		  });
//



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

//		ActionBar mActionBar = getActionBar();
//		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//       // mActionBar.setDisplayShowHomeEnabled(false);
//       // mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//
//        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
//        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
//        mTitleTextView.setText("Order Booking");
//
//        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
//        SharedPreferences sp = Previous_Item_Edit_Activity.this.getSharedPreferences("SimpleLogic", 0);
//
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//        	todaysTarget.setText("Today's Target Acheived");
//		}
//
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
//        mActionBar.setHomeButtonEnabled(true);
//        mActionBar.setDisplayHomeAsUpEnabled(true);
       /* mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);*/

        buttonAddMOre = (Button) findViewById(R.id.buttonAddMOre);
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));

        buttonEditBack = (Button) findViewById(R.id.buttonEditBack);
        buttonEditBack.setBackgroundColor(Color.parseColor("#414042"));

//			buttonAddMOre.setOnTouchListener(new OnTouchListener() {
        buttonEditBack.setOnTouchListener(new OnTouchListener() {
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
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    //up event
                    b.setBackgroundColor(Color.parseColor("#414042"));
                    return true;
                }
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                {
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

                    int SQMO_Validator = Integer.parseInt(editTextQuantity.getText().toString().trim())%Integer.parseInt(Global_Data.item_SL);

                    if (editTextQuantity.getText().toString().length() == 0) {
                        Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Please enter Quantity", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else
                    if(SQMO_Validator != 0)
                    {
                        Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Entered Value Not A Multiple Of Item SQ Value.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        editTextQuantity.setText("");
                    }


//					else if (spnScheme.getSelectedItem().toString().equalsIgnoreCase("Select Discount Type")) {
//						Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Please Select Discount Type", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}

                    else{

                        // TODO Auto-generated method stub



                        loginDataBaseAdapter=loginDataBaseAdapter.open();

                        String s_price[] = txtPrice.getText().toString().split(":");

//	//
//				   	        	                             }
                        String discount_type = "";
                        String discount_amount = "";
//
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

                       // Long randomPIN = System.currentTimeMillis();
                        String PINString = new SimpleDateFormat("yyMdHms").format(Calendar.getInstance().getTime());

                        if(Global_Data.Previous_Order_ServiceOrder_ID.equalsIgnoreCase(Global_Data.Previous_Order_UpdateOrder_ID))
                        {
                            Global_Data.Previous_Order_UpdateOrder_ID = PINString;

                            Global_Data.GLObalOrder_id = PINString;
                            Global_Data.GLOvel_GORDER_ID = PINString;
                            // Global_Data.GLOvel_GORDER_ID = "Ord"+PINString;

                            dbvoc.getDeleteTable("previous_orders");

                            try
                            {
                                AppLocationManager appLocationManager = new AppLocationManager(Previous_Item_Edit_Activity.this);
                                Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
                                Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
                                PlayService_Location PlayServiceManager = new PlayService_Location(Previous_Item_Edit_Activity.this);

                                if(PlayServiceManager.checkPlayServices(Previous_Item_Edit_Activity.this))
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

                            loginDataBaseAdapter.insertOrders("",  Global_Data.Previous_Order_UpdateOrder_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "",Global_Data.order_retailer,Global_Data.order_state,Global_Data.order_city,Global_Data.sales_btnstring,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Glovel_BEAT_ID,"","","","","","","","");

                            List<Local_Data> cont1 = dbvoc.getItemNamePrevious_OrderCheck(Global_Data.Previous_Order_ServiceOrder_ID,Global_Data.item_no);
                            for (Local_Data cnt1 : cont1) {

                                loginDataBaseAdapter.insertOrderProducts("", "", Global_Data.GLOvel_GORDER_ID, "","","","", "", cnt1.getSche_code().trim(), " ", "",cnt1.getQty().trim(),cnt1.getRP().trim(),cnt1.getPrice().trim(), cnt1.getAmount().trim(), "", "",Global_Data.order_retailer," ",cnt1.get_category_ids()," ",cnt1.getProduct_nm());
                            }

                            dbvoc.getDeleteTable("previous_order_products");

//                            List<Local_Data> check_order_product = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLOvel_GORDER_ID,Global_Data.item_no);
//
//                            if(check_order_product.size() < 0)
//                            {
                            loginDataBaseAdapter.insertOrderProducts(" ", " ",  Global_Data.GLOvel_GORDER_ID, "", "" , "" ,"", " " , scheme_code , " ", "", editTextQuantity.getText().toString() ,editTextRP.getText().toString().trim(), editTextMRP.getText().toString().trim(), s_price[1].trim(), "", "",Global_Data.order_retailer," ", Global_Data.item_no," ",spnProduct.getText().toString().trim());
                            // }

                        }
                        else
                        {
                            dbvoc.update_item(editTextQuantity.getText().toString().trim(),editTextMRP.getText().toString().trim(),s_price[1].trim(),discount_amount,discount_type,Global_Data.item_no,Global_Data.GLObalOrder_id,scheme_code);
                        }

                        Toast toast = Toast.makeText(Previous_Item_Edit_Activity.this,"Item Update Successfully", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                            Intent intent = new Intent(Previous_Item_Edit_Activity.this, Previous_orderNew_S2.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            startActivity(intent);
                            finish();


//
                        return true;
                        //}
                    }

                    return true;
                }
                return false;
            }
        });

//		buttonPreviewOrder = (Button) findViewById(R.id.buttonPreviewOrder);
//		buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
//		buttonPreviewOrder.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View b, MotionEvent event) {
//				// TODO Auto-generated method stub
//				if(event.getAction() == MotionEvent.ACTION_UP)
//			    {
//			        //up event
//					 b.setBackgroundColor(Color.parseColor("#414042"));
//			        return true;
//			    }
//			    if(event.getAction() == MotionEvent.ACTION_DOWN)
//			    {
//			        //down event
//			    	 b.setBackgroundColor(Color.parseColor("#910505"));
//						// TODO Auto-generated method stub
//
//						if (Global_Data.productList.size()!=0) {
//							/*final Intent i = new Intent(getApplicationContext(),
//									PreviewOrderActivity.class);*/
//							final Intent i = new Intent(getApplicationContext(),
//									PreviewOrderSwipeActivity.class);
//							i.putParcelableArrayListExtra("productsList", Global_Data.productList);
//							i.putExtra("new","new");
//							SharedPreferences sp = Previous_Item_Edit_Activity.this
//									.getSharedPreferences("SimpleLogic", 0);
//
//							i.putExtra("retialer",
//									"" + sp.getString("RetailerName", ""));
//							//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//							Global_Data.GLOVEL_LONG_DESC = "";
//							 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
//							 Global_Data.GLOVEL_ITEM_MRP = "";
//
//							i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//							ActivitySwitcher.animationOut(findViewById(R.id.containerNewOrder), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
//								@Override
//								public void onAnimationFinished() {
//									startActivity(i);
//									finish();
//								}
//							});
//							//NewOrderFragment.this.startActivity(i);
//						}
//
//						else {
//							//Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();
//
//							Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//						}
//
//			        return true;
//			    }
//			    return false;
//			}
//		});
//		buttonPreviewOrder.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//
//
//				if (productList.size()!=0) {
//					Intent i = new Intent(getApplicationContext(),
//							PreviewOrderActivity.class);
//					i.putParcelableArrayListExtra("productsList", productList);
//
//					SharedPreferences sp = NewOrderFragment.this
//							.getSharedPreferences("SimpleLogic", 0);
//
//					i.putExtra("retialer",
//							"" + sp.getString("RetailerName", ""));
//					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//					finish();
//					NewOrderFragment.this.startActivity(i);
//				}
//
//				else {
//					//Toast.makeText(getBaseContext(), "No Items Added", Toast.LENGTH_SHORT).show();
//
//					Toast toast = Toast.makeText(getApplicationContext(),"No Items Added", Toast.LENGTH_SHORT);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//
//			}
//		});
//		editTextQuantity.setText("");
//
//
//		editTextQuantity.setOnKeyListener(new OnKeyListener() {
//
//			@Override
//			public boolean onKey(View v, int keyCode, KeyEvent event) {
//				// TODO Auto-generated method stub
//				if (editTextQuantity.getText().toString().length() != 0) {
//					quantity = Integer.parseInt(editTextQuantity.getText()
//							.toString());
//					totalprice = productprice * quantity;
//					Global_Data.order_amount=totalprice;
//					txtPrice.setText("Total Price : " + String.format("%.2f", totalprice));
//					//deleiveryQuantity = quantity;
////					txtDeleiveryQuantity.setText("Delivery Quantity : "
////							+ deleiveryQuantity);
//					if (scheme.equalsIgnoreCase("Select Scheme")) {
//
//						// Toast.makeText(parent.getContext(),
//						// "OnItemSelectedListener : " +
//						// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//						scheme = "Select Scheme";
//						schemeID=0;
//						if (editTextQuantity.getText().toString().length()!=0) {
//							quantity=Integer.parseInt(editTextQuantity.getText().toString());
//							deleiveryQuantity = quantity;
//							txtDeleiveryQuantity.setText("Delivery Quantity : "
//									+ deleiveryQuantity);
//						}
//
//						/*LoadProductIDsTask loadProductIDsTask=new LoadProductIDsTask(NewOrderFragment.this);
//						loadProductIDsTask.execute();*/
//						//txtDeleiveryQuantity.setText("Delivery Quantity : ");
//
//					}
//
//					else {
//
//						schemeID=dbschemeID;
//						String [] aray=scheme.split("and");
//						int buy=Integer.parseInt(aray[0].replaceAll("[\\D]", ""));
//						int get=Integer.parseInt(aray[1].replaceAll("[\\D]", ""));
//						if (editTextQuantity.getText().toString().length()!=0) {
//							quantity=Integer.parseInt(editTextQuantity.getText().toString());
//							int extra = quantity / buy;
//							deleiveryQuantity = extra*get + quantity;
//							txtDeleiveryQuantity.setText("Delivery Quantity : "
//									+ deleiveryQuantity);
//						}
//					}
//				}
//				return false;
//			}
//		});



//		spnCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View arg1,
//					int pos, long arg3) {
////				// TODO Auto-generated method stub
//
//
//
////				// Toast.makeText(parent.getContext(),
////				// "OnItemSelectedListener : " +
////				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
////				editTextQuantity.setFocusableInTouchMode(false);
////				editTextQuantity.setEnabled(false);
////				txtPrice.setText("Total Price : ");
////
//				Global_Data.GLOVEL_ITEM_MRP = "";
////				category = parent.getItemAtPosition(pos).toString();
//				Log.d("Globel categary selection", Global_Data.GLOVEL_CATEGORY_SELECTION);
//
//				 check=check+1;
//			   if(check>1)
//			   {
//
////				if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
////				{
////					Log.d("Globel categary selection", "in");
////					spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
////
////
////				}
////				else
////				{
////                 if (Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
////                 {
//					if (parent.getItemAtPosition(pos).toString()
//							.equalsIgnoreCase("Select Category")) {
//
//						Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();
//
//					}
//					else
//					{
//
//						Global_Data.GLOVEL_CATEGORY_SELECTION = parent.getItemAtPosition(pos).toString();
//						Intent intent = new Intent(getApplicationContext(), Filter_List.class);
//						Global_Data.GLOVEL_CATEGORY_NAME = parent.getItemAtPosition(pos).toString();
//						startActivity(intent);
//						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//
//					}
////                 }
////                 else
////                 {
////                	 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
////                 }
//
//
//				//}
//			   }
////
////				else {
////
////					//categoryID = Integer.parseInt(categoriesMap.get(""+parent.getSelectedItemId()));
////					categoryID =dataCategories.get(pos-1).getId();
////					CategoriesSpinner = parent.getItemAtPosition(pos).toString();
////					LoadProductsAsyncTask loadProductsAsyncTask=new LoadProductsAsyncTask(Previous_Item_Edit_Activity.this);
////					loadProductsAsyncTask.execute();
////
////				}
//
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});

//		spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View arg1,
//					int pos, long arg3) {
//				// TODO Auto-generated method stub
//				editTextQuantity.setFocusableInTouchMode(false);
//				editTextQuantity.setEnabled(false);
//				txtPrice.setText("Total Price : ");
//
//				productName = parent.getItemAtPosition(pos).toString();
//
//
//				if (parent.getItemAtPosition(pos).toString()
//						.equalsIgnoreCase("Select Product")) {
//
//					listProductSpec.clear();
//					listProductSpec.add("Select Variant");
//					dataAdapterProductSpec.notifyDataSetChanged();
//					dataAdapterProductSpec
//							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//					spnProductSpec.setAdapter(adapter_state3);
//
//					listScheme.clear();
//					listScheme.add("Select Scheme");
//
//					dataAdapterScheme.notifyDataSetChanged();
//					dataAdapterScheme
//							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//					spnScheme.setAdapter(dataAdapterScheme);
//
//					/*editTextRP.setText("" + rp);
//					editTextMRP.setText("" + mrp);
//					txtPrice.setText("Total Price : ");
//					rp = 0.00f;
//					mrp = 0.00f;
//					totalprice = 0.00f;
//					deleiveryQuantity = 0;
//					editTextQuantity.setText("");
//					txtDeleiveryQuantity.setText("Delivery Quantity :");*/
//
//					rp = 0.00f;
//					mrp = 0.00f;
//					productprice = rp;
//
//					editTextRP.setText("" + rp);
//					editTextMRP.setText("" + mrp);
//
//				}
//
//				else {
//
//					//productID= Integer.parseInt(productsMap.get(""+parent.getSelectedItemId()));;
//					productID = dataProducts.get(pos-1).getProductID();
//					ProductSpinner =parent.getItemAtPosition(pos).toString();
//					LoadProductVarientsAsyncTask loadProductVarientsAsyncTask=new LoadProductVarientsAsyncTask(Previous_Item_Edit_Activity.this);
//					loadProductVarientsAsyncTask.execute();
//				}
//	    	}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});

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
                    if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null") &&  !editTextMRP.getText().toString().equalsIgnoreCase("") && !editTextMRP.getText().toString().equalsIgnoreCase("null") && !editTextMRP.getText().toString().equalsIgnoreCase(null))
                    {

                        try
                        {
                            int SQMO_Validator = Integer.parseInt(editTextQuantity.getText().toString().trim())%Integer.parseInt(Global_Data.item_SL);
                            if(SQMO_Validator == 0)
                            {
                                Double final_mrp = Double.valueOf(editTextMRP.getText().toString()) * Double.valueOf(editTextQuantity.getText().toString().trim());
                                txtPrice.setText("Total Price : "+final_mrp);
                                price = String.valueOf(final_mrp);
                                t_error.setText("");
                            }
                            else
                            {
                                txtPrice.setText("Total Price : "+"");
                                price = "";
                                t_error.setText("Entered Value Not A Multiple Of Item SQ Value.");
                            }

                        }catch(Exception ex){ex.printStackTrace();}

                    }
                    else
                    {
                        txtPrice.setText("Total Price : " + "");
                        price = String.valueOf("0");
                        t_error.setText("");
                    }

                    //}


                }
                else
                {
                    t_error.setText("");
                }
                //Field2.setText("");
            }
        });

        txtDeleiveryQuantity1.addTextChangedListener(new TextWatcher() {

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

                    if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null"))
                    {
                        if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
                        {
                            long final_mrp = (Long.valueOf(editTextMRP.getText().toString()))*(Long.valueOf(editTextQuantity.getText().toString().trim()));
                            long final_discount = Long.parseLong(String.valueOf(s));
                            //int final_discount = (Integer.parseInt(String.valueOf(s)))*(Integer.parseInt(editTextQuantity.getText().toString()));
                            // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
                            long price1 = (final_mrp)-(final_discount);
                            txtPrice.setText("Total Price : "+price1);
                            price = String.valueOf(price1);
                        }
                        else
                        if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
                        {
                            Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(editTextQuantity.getText().toString()));
                            int final_discount = Integer.parseInt(String.valueOf(s));
                            //Float final_discount = (Float.valueOf((String.valueOf(s))))*(Float.valueOf(editTextQuantity.getText().toString()));
                            //		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
                            //		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);

                            Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
                            Float price1 = (final_mrp)-(per_value);

                            txtPrice.setText("Total Price : "+price1);
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






    public void getListViewnew(final String value)
    {
        final Dialog dialognew = new Dialog(Previous_Item_Edit_Activity.this);
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

        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(value))
        {
            userInput.setText(value);
        }

//	        final Button cancel = (Button) dialognew
//	                .findViewById(R.id.cancel);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(userInput.getText().toString()))
                {
                    spnProduct.setText(userInput.getText().toString());
                }


                dialognew.dismiss();


            }
        });

//	        cancel.setOnClickListener(new View.OnClickListener() {
//	            @Override
//	            public void onClick(View v) {
//	                dialognew.dismiss();
        //
        //
//	            }
//	        });

        dialognew.show();
    }

}
