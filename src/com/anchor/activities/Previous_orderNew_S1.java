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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Previous_orderNew_S1 extends BaseActivity implements OnItemSelectedListener{
    int check=0;
    String categ_name,subcateg_name;
    int check_product=0;
    int check_ProductSpec=0;
    String product_code = "";
    String scheme_code = "";
    String scheme_namen = "";
    String CategoriesSpinner = "";
    String ProductSpinner = "";
    String price = "";
    String str_variant;
    LoginDataBaseAdapter loginDataBaseAdapter;

    AutoCompleteTextView Product_Variant;

    Spinner spnCategory, spnProductSpec, spnScheme, spnProduct;
    TextView editTextRP, editTextMRP, txtPrice, txtDeleiveryQuantity,txt_rp;
    EditText txtDeleiveryQuantity1;
    static int quantity = 0, deleiveryQuantity = 0;
    static float rp, mrp, totalprice,totalprice1,totalprc_scheme, productprice;
    static String scheme = "";
    EditText editTextQuantity;
    static String category, productName, productSpec, productQuantity,
            productDeleiveryQuantity, productScheme, productrp, productmrp,
            producttotalPrice;
    Button buttonAddMOre, buttonPreviewOrder;

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
    private ArrayList<String> Scheme_array = new ArrayList<String>();
    private ArrayList<String> result_product = new ArrayList<String>();
    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    ArrayAdapter<String> Discount_Adapter;
    MediaPlayer mpplayer;
    SimpleCursorAdapter cursorAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);

    ArrayList<Category> dataCategories = new ArrayList<Category>();
    ArrayList<Product> dataProducts = new ArrayList<Product>();
    ArrayList<Scheme> dataScheme = new ArrayList<Scheme>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order_new_s1);
        mpplayer = new MediaPlayer();
        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        txtPrice = (TextView) findViewById(R.id.txtPrice1);
        //txtDeleiveryQuantity = (TextView) findViewById(R.id.txtDeleiveryQuantity1);
        //txtDeleiveryQuantity1 = (EditText) findViewById(R.id.txtDeleiveryQuantity);
        spnCategory = (Spinner) findViewById(R.id.spnCategory);
        spnProduct = (Spinner) findViewById(R.id.spnProduct);
        //spnProductSpec = (Spinner) findViewById(R.id.spnProductSpec);
        spnScheme = (Spinner) findViewById(R.id.spnScheme1);
        Product_Variant = (AutoCompleteTextView) findViewById(R.id.Product_Variant);
        editTextRP = (TextView) findViewById(R.id.editTextRP);
        editTextMRP = (TextView) findViewById(R.id.editTextMRP);

        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity1);

        txt_rp = (TextView) findViewById(R.id.textRP);
        // for label RP change
        SharedPreferences spf1=this.getSharedPreferences("SimpleLogic",0);
        String rpstr=spf1.getString("var_rp", "");
        txt_rp.setText(rpstr);

        SharedPreferences spf12 = Previous_orderNew_S1.this.getSharedPreferences("SimpleLogic", 0);
        Global_Data.app_sound = spf12.getBoolean("var_addmore", false);

        SharedPreferences sound = Previous_orderNew_S1.this.getSharedPreferences("SimpleLogic", 0);
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

        Scheme_array.clear();
        Scheme_array.add("Select Scheme");

        Discount_Adapter = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, Scheme_array);
        Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
        spnScheme.setAdapter(Discount_Adapter);

        Product_Variant.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos,
                                    long id) {
                //Toast.makeText(Order.this," selected", Toast.LENGTH_LONG).show();

                Global_Data.hideSoftKeyboard(Previous_orderNew_S1.this);

                editTextQuantity.setFocusableInTouchMode(true);
                editTextQuantity.setEnabled(true);

                List<Local_Data> cont = dbvoc.getProductByCat(Product_Variant.getText().toString().trim());
                //results2.add("Select Variant");
                for (Local_Data cn1 : cont) {
                    String str_var = ""+cn1.getStateName();
                    String str_var1 = ""+cn1.getMRP();
                    String str_var2 = ""+cn1.get_Description();
                    String str_var3 = ""+cn1.get_Claims();
                    Global_Data.amnt= ""+cn1.get_Description();
                    Global_Data.amnt1= ""+cn1.get_Claims();
                    product_code = cn1.getCode();

                    editTextRP.setText(str_var);
                    editTextMRP.setText(str_var1);

                    categ_name = cn1.getCategory();
                    subcateg_name = cn1.getSubcateg();

                    txtPrice.setText("Total Price : "+"");

                    if (editTextQuantity.getText().toString().length() != 0) {
                        if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null")  && !editTextQuantity.getText().toString().equalsIgnoreCase("0.0") && !editTextMRP.getText().toString().equalsIgnoreCase("0.0"))
                        {
                            long final_mrp = (Long.valueOf(editTextMRP.getText().toString()))*(Long.valueOf(editTextQuantity.getText().toString().trim()));
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
                    }
                }

                adapter_state1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnCategory.setAdapter(adapter_state1);
                //spnCategory.setOnItemSelectedListener(NewOrderActivity.this);

                adapter_state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnProduct.setAdapter(adapter_state2);
                //spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(categ_name)){
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


                Discount_Adapter = new ArrayAdapter<String>(Previous_orderNew_S1.this, R.layout.spinner_item, Scheme_array);
                Discount_Adapter.setDropDownViewResource(R.layout.spinner_item);
                spnScheme.setAdapter(Discount_Adapter);
            }
        });

        Product_Variant.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {



            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {

//                listScheme.clear();
//                listScheme.add("Select Scheme");
//
//                dataAdapterScheme.notifyDataSetChanged();
//                dataAdapterScheme.setDropDownViewResource(R.layout.spinner_item);
//                spnScheme.setAdapter(dataAdapterScheme);
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

        //Reading all
        List<Local_Data> contacts1 = dbvoc.HSS_DescriptionITEM();
        results1.add("Select Category");
        for (Local_Data cn : contacts1)
        {
            if(!cn.getStateName().equalsIgnoreCase("") && !cn.getStateName().equalsIgnoreCase(" "))
            {
                String str_categ = ""+cn.getStateName();
                results1.add(str_categ);
            }
        }

        //Reading all
//		         List<Local_Data> contacts2 = dbvoc.HSS_DescriptionITEM1();
        results.add("Select Product");
//		          for (Local_Data cn : contacts2) {
//		        	String str_product = ""+cn.getStateName();
//		        	//Global_Data.local_pwd = ""+cn.getPwd();
//
//		        	results.add(str_product);
//		        	System.out.println("Local Values:-"+Global_Data.local_user);
//		        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//		        	                             }
//
        adapter_state2 = new ArrayAdapter<String>(this, R.layout.spinner_item, results);
        adapter_state2.setDropDownViewResource(R.layout.spinner_item);
        spnProduct.setAdapter(adapter_state2);


        //Reading all
        // List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2();
        //results2.add("Select Variant");
//			          for (Local_Data cn : contacts3) {
//			        	str_variant = ""+cn.getStateName();
//			        	//Global_Data.local_pwd = ""+cn.getPwd();
//
//			        	results2.add(str_variant);
//			        	//System.out.println("Local Values:-"+Global_Data.local_user);
//			        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//
//
//			        	                             }



//        adapter_state3 = new ArrayAdapter<String>(this, R.layout.spinner_item, results2);
//        adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//        spnProductSpec.setAdapter(adapter_state3);


//					  List<Local_Data> cont = dbvoc.getProductByCat(str_variant);
//				         //results2.add("Select Variant");
//				          for (Local_Data cn1 : cont) {
//				        	String str_var = ""+cn1.get_Claims();
//				            String str_var1 = ""+cn1.getCategory();
//
//				            editTextRP.setText(str_var);
//				            editTextMRP.setText(str_var1);
//
//				            //Global_Data.local_pwd = ""+cn.getPwd();
//
//				        	//results2.add(str_variant);
//				        	//System.out.println("Local Values:-"+Global_Data.local_user);
//				        	//Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//
//
//
//				        	                             }



//        TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//        //question_value.setTypeface(null,Typeface.BOLD);
//        welcomeUser.setText(spf.getString("FirstName", "")+" "+ spf.getString("LastName", ""));


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

        adapter_state1 = new ArrayAdapter<String>(this,R.layout.spinner_item, results1);
//		  adapter_state2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results);
//		  adapter_state3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, results2);

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

//		  txtDeleiveryQuantity1.setVisibility(View.INVISIBLE);
//		  if(Global_Data.GLOVEL_LONG_DESC != "")
//		  {
//			  spnProduct.setText(Global_Data.GLOVEL_LONG_DESC);
//			  editTextQuantity.setFocusable(true) ;
//			  editTextMRP.setText(Global_Data.GLOVEL_ITEM_MRP);
//			  spnScheme.setEnabled(true);
//			  txtPrice.setText("Total Price : "+Global_Data.GLOVEL_ITEM_MRP);
//
//		  }
//		  else
//		  {
//			  editTextQuantity.setFocusable(false) ;
//			  editTextMRP.setText("");
//			  spnScheme.setEnabled(false);
//		  }
//
        spnProduct.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//					// TODO Auto-generated method stub

                //editTextQuantity.setText("");
                if(!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))){
                    txtPrice.setText("Total Price : "+"");
                }

//					 Toast.makeText(parent.getContext(),
//					 "OnItemSelectedListener : " +
//					 parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//					editTextQuantity.setFocusableInTouchMode(false);
//					editTextQuantity.setEnabled(false);
//
//					txtPrice.setText("Total Price : ");
                check_product=check_product+1;
                if(check_product>1)
                {
                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category"))
                    {

                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

                        categ_name = "";
                        subcateg_name = "";
                        Toast toast = Toast.makeText(getApplicationContext(),"Please Select Category",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    else
                    if (parent.getItemAtPosition(pos).toString()
                            .equalsIgnoreCase("Select Product"))
                    {
//					    	 	results2.clear();
//								results2.add("Select Variant");
//								adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//						        adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//								spnProductSpec.setAdapter(adapter_state3);

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

                        //Toast.makeText(getApplicationContext(), "Please Select Product", Toast.LENGTH_LONG).show();

                    }
                    else
                    {

                        results2.clear();

                        List<Local_Data> contacts33 = dbvoc.HSS_DescriptionITEM1_IDD(parent.getItemAtPosition(pos).toString().trim());
                        for (Local_Data cn : contacts33)
                        {
                            Global_Data.GLOVEL_PRODUCT_ID = cn.getCust_Code();
                        }

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(Global_Data.GLOVEL_PRODUCT_ID)) {

                            //List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_ID(Global_Data.GLOVEL_PRODUCT_ID);
                            List<Local_Data> contacts3 = dbvoc.HSS_DescriptionITEM2_BYNAME(spnCategory.getSelectedItem().toString().trim(),parent.getItemAtPosition(pos).toString().trim());
                            // results2.add("Select Variant");
                            for (Local_Data cn : contacts3)
                            {
                                str_variant = ""+cn.getStateName();
                                //Global_Data.local_pwd = ""+cn.getPwd();

                                results2.add(str_variant);
                                //System.out.println("Local Values:-"+Global_Data.local_user);
                                //Toast.makeText(LoginActivity.this, "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();


                            }



//							 adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//							 adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//							 spnProductSpec.setAdapter(adapter_state3);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Previous_orderNew_S1.this,android.R.layout.simple_spinner_dropdown_item,results2);
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


        for(int i = 0 ; i < dataCategories.size();i++){
            listCategory.add(dataCategories.get(i).getDesc());
        }

        dataAdapterCategory = new ArrayAdapter<String>(this, R.layout.spinner_item, results1);
        dataAdapterCategory.setDropDownViewResource(R.layout.spinner_item);
        spnCategory.setAdapter(adapter_state1);

        if (!Global_Data.GLOVEL_CATEGORY_SELECTION.equalsIgnoreCase(""))
        {
            Log.d("Globel cat", "in");
            spnCategory.setSelection(adapter_state1.getPosition(Global_Data.GLOVEL_CATEGORY_SELECTION));
            Global_Data.GLOVEL_CATEGORY_SELECTION = "";

        }

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
                    if (spnCategory.getSelectedItem().toString().equalsIgnoreCase("Select Category")) {
                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Please Select Category", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                    else if (spnProduct.getSelectedItem().toString().equalsIgnoreCase("Select Product")) {
                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"Please Select Product", Toast.LENGTH_SHORT);
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

//
                        Global_Data.order_category = spnCategory.getSelectedItem().toString();
                        //Global_Data.order_product = spnProduct.getSelectedItem().toString();
                        // Global_Data.order_variant = spnProductSpec.getSelectedItem().toString();
                        //Global_Data.order_variant = spnProduct.getText().toString().trim();

                        loginDataBaseAdapter=loginDataBaseAdapter.open();

                        String item_name = "";
                        List<Local_Data> contacts1 = dbvoc.getItemCode(spnCategory.getSelectedItem().toString().trim(),spnProduct.getSelectedItem().toString().trim(),Product_Variant.getText().toString().trim());
                        for (Local_Data cn1 : contacts1)
                        {

                            Global_Data.item_code = cn1.getItem_Code();
                            Global_Data.GLOvel_ITEM_NUMBER = cn1.getItem_Code();
                            item_name = cn1.getProdname();

                        }

//				   	          if(Global_Data.sales_btnstring.equalsIgnoreCase("Secondary Sales / Retail Sales"))
//							  {
//					   	       dbvoc.getDeleteTableorder_bycustomer_PRE(Global_Data.order_retailer.trim(),"Secondary Sales / Retail Sales");
//							   dbvoc.getDeleteTableorderproduct_bycustomer_PRE(Global_Data.order_retailer.trim(),"Secondary Sales / Retail Sales");
//							  }





                            Long randomPIN = System.currentTimeMillis();
                            String PINString = String.valueOf(randomPIN);
                            Global_Data.variant_rr="";
                            Global_Data.variant_mrp=editTextMRP.getText().toString();
                            Global_Data.order_qty=editTextQuantity.getText().toString();
                            String strAmount=String.valueOf(Global_Data.order_amount);

                            if(Global_Data.Previous_Order_ServiceOrder_ID.equalsIgnoreCase(Global_Data.Previous_Order_UpdateOrder_ID))
                            {
                                Global_Data.Previous_Order_UpdateOrder_ID = "Ord"+PINString;

                                Global_Data.GLObalOrder_id = "Ord"+PINString;
                                Global_Data.GLOvel_GORDER_ID = "Ord"+PINString;
                                   // Global_Data.GLOvel_GORDER_ID = "Ord"+PINString;

                                dbvoc.getDeleteTable("previous_orders");

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

                                loginDataBaseAdapter.insertOrders("",  Global_Data.Previous_Order_UpdateOrder_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "",Global_Data.order_retailer,Global_Data.order_state,Global_Data.order_city,Global_Data.sales_btnstring,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Glovel_BEAT_ID,"","","","","","","");

                                List<Local_Data> cont1 = dbvoc.getItemNamePrevious_OrderCheck(Global_Data.Previous_Order_ServiceOrder_ID,Global_Data.GLOvel_ITEM_NUMBER);
                                for (Local_Data cnt1 : cont1) {

                                    loginDataBaseAdapter.insertOrderProducts("", "", Global_Data.GLOvel_GORDER_ID, "","","","", "",cnt1.getSche_code().trim(), " ", "",cnt1.getQty().trim(),cnt1.getRP().trim(),cnt1.getPrice().trim(), cnt1.getAmount().trim(), "", "",Global_Data.order_retailer," ",cnt1.get_category_ids()," ",cnt1.getProduct_nm());
                                }

                                dbvoc.getDeleteTable("previous_order_products");
                            }

//                        List<Local_Data> contactsn = dbvoc.GetPREVIOUSOrders_BY_ORDER_ID(Global_Data.Previous_Order_UpdateOrder_ID,Global_Data.GLOvel_ITEM_NUMBER);
//
//                            if(contactsn.size() > 0)
//                            {
//                                Toast.makeText(Previous_orderNew_S1.this, "You already have a this item in your order", Toast.LENGTH_SHORT).show();
//                            }
//                            else
//                            {


                        List<Local_Data> check_order_product = dbvoc.GetOrders_BY_ORDER_ID(Global_Data.GLOvel_GORDER_ID,Global_Data.item_code);

                        if(check_order_product.size() > 0)
                        {
                            Toast.makeText(Previous_orderNew_S1.this, "You already have a this item in your order", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
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

                            loginDataBaseAdapter.insertOrderProducts(" ", " ",  Global_Data.GLOvel_GORDER_ID, "", spnCategory.getSelectedItem().toString() , spnProduct.getSelectedItem().toString() , Global_Data.order_variant , " " , scheme_code , " ", "", editTextQuantity.getText().toString() ,editTextRP.getText().toString().trim(), Global_Data.variant_mrp, price, "", "",Global_Data.order_retailer," ",Global_Data.item_code," ",item_name);//Reading all
                            Toast toast = Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                                //Toast.makeText(getApplicationContext(), "Item add successfully", Toast.LENGTH_LONG).show();

                           // }

                        check = 0;
                        check_product=0;
                        check_ProductSpec=0;
                        editTextQuantity.setText("");
                        editTextQuantity.setEnabled(false);
                        spnCategory.setSelection(adapter_state1.getPosition("Select Category"));
                        // spnProductSpec.setSelection(adapter_state3.getPosition("Select Variant"));
                        Product_Variant.setText("");
                        spnProduct.setSelection(adapter_state2.getPosition("Select Product"));
                        spnScheme.setSelection(Discount_Adapter.getPosition("Select Scheme"));
                      //  spnScheme.setEnabled(false);
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

                            final Intent i = new Intent(getApplicationContext(),
                                    Previous_orderNew_S2.class);
                            i.putParcelableArrayListExtra("productsList", Global_Data.productList);
                            i.putExtra("new","new");
                            SharedPreferences sp = Previous_orderNew_S1.this
                                    .getSharedPreferences("SimpleLogic", 0);
                            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            ActivitySwitcher.animationOut(findViewById(R.id.containerNewOrder), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
                                @Override
                                public void onAnimationFinished() {
                                    startActivity(i);
                                    finish();
                                }
                            });
                            //NewOrderFragment.this.startActivity(i);






                    return true;
                }
                return false;
            }
        });




        spnCategory.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
//				// TODO Auto-generated method stub



//				// Toast.makeText(parent.getContext(),
//				// "OnItemSelectedListener : " +
//				// parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
//				editTextQuantity.setFocusableInTouchMode(false);
//				editTextQuantity.setEnabled(false);
//				txtPrice.setText("Total Price : ");
//
                Global_Data.GLOVEL_ITEM_MRP = "";

//				category = parent.getItemAtPosition(pos).toString();
                Log.d("Globel categary", Global_Data.GLOVEL_CATEGORY_SELECTION);

                check=check+1;
                if(check>1)
                {

                    // editTextQuantity.setText("");

                    if(!(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name))){
                        txtPrice.setText("Total Price : "+"");
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
                        //spnProduct.setOnItemSelectedListener(NewOrderActivity.this);

//						results2.clear();
//						results2.add("Select Variant");
//						adapter_state3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, results2);
//				        adapter_state3.setDropDownViewResource(R.layout.spinner_item);
//						spnProductSpec.setAdapter(adapter_state3);

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

                        //Toast.makeText(getApplicationContext(), "Please Select Category", Toast.LENGTH_LONG).show();

                        Toast toast = Toast.makeText(Previous_orderNew_S1.this, "Please Select Category", Toast.LENGTH_LONG);
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
                        for (Local_Data cn : contacts2)
                        {
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

                        if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(subcateg_name)){
                            int spinnerPosition = adapter_state2.getPosition(subcateg_name);
                            spnProduct.setSelection(spinnerPosition);
                        }
                        //startActivity(intent);
                        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }
//                 }
//                 else
//                 {
//                	 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
//                 }


                    //}
                }
//
//				else {
//
//					//categoryID = Integer.parseInt(categoriesMap.get(""+parent.getSelectedItemId()));
//					categoryID =dataCategories.get(pos-1).getId();
//					CategoriesSpinner = parent.getItemAtPosition(pos).toString();
//					LoadProductsAsyncTask loadProductsAsyncTask=new LoadProductsAsyncTask(NewOrderActivity.this);
//					loadProductsAsyncTask.execute();
//
//				}

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
                    }}


                //Field2.setText("");
            }
        });


        //editTextQuantity.setFocusable(false) ;


//
//		txtDeleiveryQuantity1.addTextChangedListener(new TextWatcher() {
//
//			   @Override
//			   public void afterTextChanged(Editable s) {}
//
//			   @Override
//			   public void beforeTextChanged(CharSequence s, int start,
//			     int count, int after) {
//			   }
//
//			   @Override
//			   public void onTextChanged(CharSequence s, int start,
//			     int before, int count) {
//			      if(s.length() != 0)
//			      {
//			    	  if(!editTextQuantity.getText().toString().equalsIgnoreCase("") && !editTextQuantity.getText().toString().equalsIgnoreCase(null) && !editTextQuantity.getText().toString().equalsIgnoreCase("null"))
//			    	  {
//
//
//			    			  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Rupees"))
//					    	  {
//					    		  int final_mrp = (Integer.parseInt(editTextMRP.getText().toString()))*(Integer.parseInt(editTextQuantity.getText().toString()));
//					    		 // int final_discount = (Integer.parseInt(String.valueOf(s)))*(Integer.parseInt(editTextQuantity.getText().toString()));
//					    		  int final_discount = Integer.parseInt(String.valueOf(s));
//					    		  // int price1 = (Integer.parseInt(editTextMRP.getText().toString()))-(Integer.parseInt((String.valueOf(s))));
//					    		  int price1 = (final_mrp)-(final_discount);
//					    		  txtPrice.setText("Total Price : "+price1);
//					    		  price = String.valueOf(price1);
//					    	  }
//					    	  else
//				    		  if(spnScheme.getSelectedItem().toString().equalsIgnoreCase("Percentage"))
//					    	  {
//				    			  if(Integer.parseInt(String.valueOf(s)) >=100)
//					    		  {
//				   	        		   Toast toast = Toast.makeText(getApplicationContext(),"Discount not more than 99",1000);
//				   	        		 toast.setGravity(Gravity.CENTER, 0, 0);
//									   toast.show();
//
//					    		  }
//				    			  else
//				    			  {
//				    				  Float final_mrp = (Float.valueOf(editTextMRP.getText().toString()))*(Float.valueOf(editTextQuantity.getText().toString()));
//					    			   int final_discount = Integer.parseInt(String.valueOf(s));
//					    			   // Float final_discount = (Float.valueOf((String.valueOf(s))))*(Float.valueOf(editTextQuantity.getText().toString()));
//			//		    			   Float per_value = (Float.valueOf((editTextMRP.getText().toString())))*(Float.valueOf((String.valueOf(s))))/(100);
//			//		    			   Float price1 = (Float.valueOf(editTextMRP.getText().toString()))-(per_value);
//
//					    			   Float per_value = (final_mrp)*(Float.valueOf((final_discount)))/(100);
//					    			   Float price1 = (final_mrp)-(per_value);
//
//						    		  txtPrice.setText("Total Price : "+price1);
//						    		  price = String.valueOf(price1);
//				    			  }
//
//					    	  }
//
//
//
//			    	  }
//
//
//			      }
//
//
//			        //Field2.setText("");
//			   }
//			  });

        spnScheme.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                productScheme = parent.getItemAtPosition(pos).toString();
               // Toast.makeText(Previous_orderNew_S1.this, "click appear.", Toast.LENGTH_SHORT).show();
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

        Intent intentn = new Intent(getApplicationContext(), Previous_orderNew_S2.class);
        startActivity(intentn);
        finish();

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stubs
        super.onResume();
        buttonAddMOre.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviewOrder.setBackgroundColor(Color.parseColor("#414042"));
    }










    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        Spinner spinner = (Spinner) arg0;

        if (spinner.getId() == R.id.spnCategory) {

            if (arg0.getItemAtPosition(arg2).toString()
                    .equalsIgnoreCase("Select Category")) {

                results.clear();
                results.add("Select Product");
                results2.clear();
                results2.add("Select Variant");



                adapter_state2 = new ArrayAdapter<String>(this,
                        R.layout.spinner_item, results);
                adapter_state2.setDropDownViewResource(R.layout.spinner_item);
                spnProduct.setAdapter(adapter_state2);
                spnProduct.setOnItemSelectedListener(this);

//				list_cities.add("");
//				list_cities.clear();
//				// String []customer_array = {"Apple", "Banana", "Cherry",
//				// "Date", "Grape", "Kiwi", "Mango", "Pear"};
//				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//						android.R.layout.simple_spinner_dropdown_item,
//						list_cities);
//				autoCompleteTextView1.setThreshold(1);// will start working from
//														// first character
//				autoCompleteTextView1.setAdapter(adapter);// setting the adapter
//															// data into the
//															// AutoCompleteTextView
//				autoCompleteTextView1.setTextColor(Color.BLACK);
//				autoCompleteTextView1.setText("");

                // adapter_state3 = new
                // ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                // results2);
                // adapter_state3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // retail_spinner.setAdapter(adapter_state3);
                // retail_spinner.setOnItemSelectedListener(this);
            } else {

                String items = spnCategory.getSelectedItem().toString();
                String C_ID = "";
                Log.i("Selected item : ", items);

                // List<Local_Data> contacts =
                // dbvoc.getBeats_CITYID(state_spinner.getSelectedItem().toString());
                // for (Local_Data cn : contacts)
                // {
                //
                // C_ID = cn.getStateName();
                // //CAT_ID = cn.getStateName();
                // //F_CITY_ID = cn.getStateName();
                // //F_BEAT_ID = cn.get_category_id();
                // }

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(items)) {
//					results.clear();
//					results.add("Select Product");

                    results2.clear();
                    results2.add("Select Variant");
                    List<Local_Data> contacts2 = dbvoc.getProductByCat(items);
                    for (Local_Data cn : contacts2) {

                        results.add(cn.getStateName());
                        Global_Data.pname=cn.getProduct_nm();
                        //Toast.makeText(Previous_orderNew_S1.this,"val:"+Global_Data.pname,Toast.LENGTH_SHORT).show();

                        Toast toast = Toast.makeText(Previous_orderNew_S1.this,"val:"+Global_Data.pname,Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                    adapter_state2 = new ArrayAdapter<String>(Previous_orderNew_S1.this,
                            R.layout.spinner_item, results);
                    adapter_state2
                            .setDropDownViewResource(R.layout.spinner_item);
                    spnProduct.setAdapter(adapter_state2);

//					adapter_state1 = new ArrayAdapter<String>(NewOrderActivity.this,
//							android.R.layout.simple_spinner_item, results_beat);
//					adapter_state1
//							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//					spnProductSpec.setAdapter(adapter_state1);

                }

//				if (!Global_Data.GLOvel_CITY_n.equalsIgnoreCase("")) {
//					spnProduct.setSelection(adapter_state2
//							.getPosition(Global_Data.GLOvel_CITY_n
//									.toUpperCase()));
//				} else if (!Global_Data.GLOvel_CITY.equalsIgnoreCase("")) {
//					spnProductSpec
//							.setSelection(adapter_state2
//									.getPosition(Global_Data.GLOvel_CITY
//											.toUpperCase()));
//				}
            }
        }


//
//

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

}
