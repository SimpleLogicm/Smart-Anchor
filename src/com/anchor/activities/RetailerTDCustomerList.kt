package com.anchor.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.adapter.RCTDAdapter
import com.anchor.helper.AutoSuggestAdapter
import com.anchor.model.RCTOData
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.reatilertdcustomerlist.*
import kotlinx.android.synthetic.main.reatilertdcustomerlist.done_btn
import kotlinx.android.synthetic.main.reatilertdcustomerlist.reset_btn
import kotlinx.android.synthetic.main.retailertdlist.*
import kotlinx.android.synthetic.main.todoadd_retailer.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

class RetailerTDCustomerList : Activity() {
    //Button retail_sales, institute_sales;

    var cd: ConnectionDetector? = null
    var isInternetPresent = false
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var ca: RCTDAdapter? = null
    var Allresult: MutableList<RCTOData> = ArrayList<RCTOData>()
    var Allresultsearch: MutableList<RCTOData> = ArrayList()
    var context: Context? = null
    var id = "";
    var filterCheck=""
    var coardcolor = "";
    var myView: RelativeLayout? = null
    var isUp = false
    var approved: String? = ""
    var rejected: String? = ""
    var pending: String? = ""
    var incomplete: String? = ""
    var incomplete1: String? = ""
    var list_Cfilter: MutableList<String> = ArrayList<String>()
    var CfilterspinnerMap = HashMap<String, String>()
    var city_id: String? = ""
    var adapter_Cfilter: ArrayAdapter<String>? = null
    var final_response = ""
    var response_result = ""
    var dbvoc = DataBaseHelper(this)
    var Retailer_List: MutableList<String> = ArrayList()
    var state_code = ""
    var todo_rv: RecyclerView? = null

    var radio_basic: RadioButton? = null
    var radio_advance: RadioButton? = null
    var todo_filter: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reatilertdcustomerlist)
//        todo_rv = findViewById(R.id.rtocustomerlist)
//        todo_rv!!.visibility=View.INVISIBLE
        radio_basic = findViewById(R.id.radio_basic)
        radio_advance = findViewById(R.id.radio_advance)
        todo_filter = findViewById(R.id.todo_filter)
        context = RetailerTDCustomerList@ this

        cd = ConnectionDetector(context)

        try {
            id = intent.getStringExtra("id").toString()
            coardcolor = intent.getStringExtra("cardcolor").toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        myView = findViewById(R.id.my_view)
        myView!!.setVisibility(View.INVISIBLE);
        // myButton.setText("Slide up");
        isUp = false;

        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,Retailer_List)
        td_Retailer_search.setThreshold(1) // will start working from
        td_Retailer_search.setAdapter(adapter) // setting the adapter
        td_Retailer_search.setTextColor(Color.BLACK)

        rtocustomerlist.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rtocustomerlist.setLayoutManager(llm)

        list_Cfilter.clear()
        CfilterspinnerMap.clear()
        list_Cfilter.add("Self")


//        val contacts2 = dbvoc.getAllCityOrderbyname()
//        for (cn in contacts2) {
//            list_Cfilter.add(cn.getName())
//            CfilterspinnerMap.put(cn.getName(), cn.getCode())
//        }

//        todo_filter1.setOnClickListener {
//            val builder = AlertDialog.Builder(this@RetailerTDCustomerList)
//            val viewGroup: ViewGroup = findViewById(android.R.id.content)
//            val dialogView: View = LayoutInflater.from(this).inflate(R.layout.retailer_filter, viewGroup, false)
//
//            builder.setView(dialogView)
//            val alertDialog = builder.create()
//            alertDialog.getWindow()!!.getAttributes().windowAnimations = R.style.DialogTheme
//            //alertDialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
//            //alertDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
//            alertDialog.show()
//        }

        todo_filter!!.setOnClickListener {
            //Toast.makeText(applicationContext, "Work in progress", Toast.LENGTH_SHORT).show()

            val builder = AlertDialog.Builder(this@RetailerTDCustomerList)
            val alertDialog = builder.create()
            if (isUp) {
                //alertDialog.dismiss()
                slideDown(myView!!)
                //todo_filter.setText("Slide up")
            } else {



                slideUp(myView!!)
                //todo_filter.setText("Slide down")
            }
            isUp = !isUp

//            intent = Intent(applicationContext, BasicActivity::class.java)
//            startActivity(intent)
        }

//        radio_advance!!.setOnClickListener {
//            intent = Intent(applicationContext, AdvanceActivity::class.java)
//            startActivity(intent)
//
//        }
//


        done_btn.setOnClickListener {

            if (id.equals("1")) {
                filterCheck="red"
            } else
                if (id.equals("2")) {
                    filterCheck="yellow"
                } else
                    if (id.equals("3")) {
                        filterCheck="light_green"
                    } else
                        if (id.equals("4")) {
                            filterCheck="dark_green"
                        }

            if(checkbox_approved.isChecked)
            {
                approved="approved"
            }
            if(checkbox_rejected.isChecked)
            {
                rejected="rejected"
            }
            if(checkbox_pending.isChecked)
            {
                pending="pending_for_approval"
            }
            if(checkbox_incomplete.isChecked)
            {
                incomplete="partially_filled"
            }
            if(checkbox_incomplete1.isChecked)
            {
                incomplete1="incomplete"
            }
           // getTODOCustomerListData("")
            if (isUp) {
                slideDown(myView!!)
                //todo_filter.setText("Slide up")
            } else {
                slideUp(myView!!)
                //todo_filter.setText("Slide down")
            }
            isUp = !isUp
            getTODOCustomerListData("")

        }

        reset_btn.setOnClickListener {
            approved=""
            incomplete=""
            incomplete1=""
            rejected=""
            pending=""
            Global_Data.filterValue=""
            checkbox_approved.isChecked=false
            checkbox_incomplete.isChecked=false
            checkbox_incomplete1.isChecked=false
            checkbox_rejected.isChecked=false
            checkbox_pending.isChecked=false
        }
        // customer_submit.setOnClickListener(new OnClickListener() {
        // @Override
        // public void onClick(View v) {
        //
        // // if
        // (city_spinner.getSelectedItem().toString().equalsIgnoreCase("Select City"))
        // {
        // //
        // // Toast toast =
        // Toast.makeText(Customer_Service.this,"Please Select City",
        // Toast.LENGTH_SHORT);
        // // toast.setGravity(Gravity.CENTER, 0, 0);
        // // toast.show();
        // // }
        // //
        // // else if
        // (beat_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Beat"))
        // {
        // // Toast toast =
        // Toast.makeText(Customer_Service.this,"Please Select Beat",
        // Toast.LENGTH_SHORT);
        // // toast.setGravity(Gravity.CENTER, 0, 0);
        // // toast.show();
        // // }
        // //
        // // else if
        // (retail_spinner.getSelectedItem().toString().equalsIgnoreCase("Select Retailer"))
        // {
        // //
        // // Toast toast =
        // Toast.makeText(Customer_Service.this,"Please Select Retailer",
        // Toast.LENGTH_SHORT);
        // // toast.setGravity(Gravity.CENTER, 0, 0);
        // // toast.show();
        // // }
        // //
        // // else {
        //
        // // new Thread(new Runnable() {
        // // public void run() {
        // // Flwg();
        // // }
        // // }).start();
        //
        // Global_Data.retailer= retail_spinner.getSelectedItem().toString();
        // Intent intent = new Intent(Order.this, NewOrderActivity.class);
        // startActivity(intent);
        // // Intent intent = new Intent(Customer_Service.this,
        // Customer_Feed.class);
        // // startActivity(intent);
        // overridePendingTransition(R.anim.slide_in_right,
        // R.anim.slide_out_left);
        //
        // // }
        // }
        // });

        // //Reading all
        // List<Local_Data> contacts = dbvoc.getAllList();
        // results.add("Select City");
        // for (Local_Data cn : contacts) {
        // String str_state = ""+cn.getStateName();
        // //Global_Data.local_pwd = ""+cn.getPwd();
        //
        // results.add(str_state);
        // //System.out.println("Local Values:-"+Global_Data.local_user);
        // //Toast.makeText(LoginActivity.this,
        // "Login Invalid"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
        // }

        // Reading all
        val contacts1 = dbvoc.allState
        for (cn in contacts1) {

            state_code =  cn.code
            Log.d("State Name","S Name "+cn.stateName)
            Log.d("State Code","S Code "+cn.code)

        }
        isInternetPresent = cd!!.isConnectingToInternet
        if (isInternetPresent) {
            userStateCities();
        } else {

            val toast = Toast.makeText(context,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            finish()
        }


//        adapter_Cfilter = ArrayAdapter<String>(context,
//                android.R.layout.simple_spinner_item, list_Cfilter)
//
//        adapter_Cfilter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        rtocustomerlist_filter.setAdapter(adapter_Cfilter)
        rtocustomerlist_filter?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Self", ignoreCase = true)) {

                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {
                        getTODOCustomerListData("")
                    } else {

                        val toast = Toast.makeText(context,
                                "Internet Not Available. ", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        finish()
                    }

                } else {
                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {

                        try {
                            val city_code = CfilterspinnerMap.get(parent!!.getItemAtPosition(position).toString())
                            getTODOCustomerListData(city_code!!)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    } else {

                        val toast = Toast.makeText(context,
                                "Internet Not Available. ", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        finish()
                    }
                }
            }
        }

        td_Retailer_search.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= td_Retailer_search.getRight() - td_Retailer_search.getCompoundDrawables().get(DRAWABLE_RIGHT).getBounds().width()) {
                    val view: View = this@RetailerTDCustomerList.getCurrentFocus()!!
                    if (view != null) {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    if (!td_Retailer_search.getText().toString().equals("", ignoreCase = true)) {
                        td_Retailer_search.setText("")
                        td_Retailer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0)
                    }
                    else
                    {
                        td_Retailer_search.showDropDown()

                    }

                    //autoCompleteTextView1.setText("");
                    return@OnTouchListener true
                }
            }
            false
        })

        td_Retailer_search.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (td_Retailer_search.getText().toString().trim({ it <= ' ' }).length == 0) {
                    try {
                        ca = RCTDAdapter(context!!, Allresult);
                        rtocustomerlist.setAdapter(ca);
                        ca!!.notifyDataSetChanged();
                    } catch (ex: java.lang.Exception) {
                        ex.printStackTrace()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (td_Retailer_search.getText().toString().trim({ it <= ' ' }).length == 0) {
                } else {
                    td_Retailer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.close_product, 0)
                }
            }
        })

        td_Retailer_search.setOnItemClickListener(OnItemClickListener { parent, arg1, pos, id ->
            Global_Data.hideSoftKeyboard(this@RetailerTDCustomerList)
            val name: String = td_Retailer_search.getText().toString()
            Allresultsearch.clear()
            todolist_progress_customer.visibility = View.VISIBLE
            rtocustomerlist.visibility = View.GONE
            for (i in Allresult.indices) {
                if (name.equals(Allresult[i].code+" - "+Allresult[i].shop_name, ignoreCase = true)) {
                    Allresultsearch.add(RCTOData("", Allresult[i].code, "", Allresult[i].shop_name, Allresult[i].address, Allresult[i].state_code, Allresult[i].city_code, Allresult[i].pincode,
                            "", Allresult[i].mobile, Allresult[i].email, Allresult[i].status, Allresult[i].proprietor_name
                            , Allresult[i].gst_no, Allresult[i].aadhar_no, Allresult[i].pan_no, Allresult[i].latitude, Allresult[i].longitude,
                            Allresult[i].power_dealer, Allresult[i].lighting_dealer, Allresult[i].iaq_dealer,
                            Allresult[i].source_of_data, "", "", Allresult[i].tsi_code, Allresult[i].card_color_code, Allresult[i].distance, Allresult[i].address_line2, Allresult[i].landmark, Allresult[i].full_address,Allresult[i].dist_code,Allresult[i].dist_name,Allresult[i].is_approved,Allresult[i].bucket_name,Allresult[i].mobile_verified))

                    todolist_progress_customer.visibility = View.GONE
                    rtocustomerlist.visibility = View.VISIBLE
                    ca = RCTDAdapter(context!!, Allresultsearch);
                    rtocustomerlist.setAdapter(ca);
                    ca!!.notifyDataSetChanged();
                    break
                }
            }
        })


        try {
            val mActionBar = actionBar
            mActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#910505")))
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            val mInflater = LayoutInflater.from(this)
            val i = intent
            val name = i.getStringExtra("retialer")
            val mCustomView = mInflater.inflate(R.layout.action_bar, null)
            mCustomView.setBackgroundDrawable(ColorDrawable(Color.parseColor("#910505")))
            val mTitleTextView = mCustomView.findViewById<TextView>(R.id.screenname)
            mTitleTextView.text = "TO DO List"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp = getSharedPreferences("SimpleLogic", 0)

//	       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	       	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			}
            try {
                val target = Math.round(sp.getFloat("Target", 0f))
                val achieved = Math.round(sp.getFloat("Achived", 0f))
                val age_float = sp.getFloat("Achived", 0f) / sp.getFloat("Target", 0f) * 100
                if (age_float.toString().equals("infinity", ignoreCase = true)) {
                    val age = Math.round(age_float)
                    todaysTarget.text = "T/A : Rs " + String.format("$target/$achieved [infinity") + "%" + "]"
                } else {
                    val age = Math.round(age_float)
                    todaysTarget.text = "T/A : Rs " + String.format("$target/$achieved [$age") + "%" + "]"
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//	       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.text = "Today's Target Acheived"
            }
            mActionBar!!.customView = mCustomView
            mActionBar!!.setDisplayShowCustomEnabled(true)
            mActionBar!!.setHomeButtonEnabled(true)
            mActionBar!!.setDisplayHomeAsUpEnabled(true)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

//        isInternetPresent = cd!!.isConnectingToInternet
//        if (isInternetPresent) {
//            getTODOCustomerListData("")
//        }
//        else {
//
//            val toast = Toast.makeText(context,
//                    "Internet Not Available. ", Toast.LENGTH_SHORT)
//            toast.setGravity(Gravity.CENTER, 0, 0)
//            toast.show()
//            finish()
//        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        val i = Intent(this@RetailerTDCustomerList, RetailerTDList::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(i)
        finish()
    }

    private fun requestPhoneCallPermission(mobile_number: String) {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CALL_PHONE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        val callIntent = Intent(Intent.ACTION_CALL)
                        callIntent.data = Uri.parse("tel:$mobile_number")
                        startActivity(callIntent)
                        return
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {
                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied) {
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                        token.continuePermissionRequest()
                    }
                }).check()
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@RetailerTDCustomerList)
        builder.setTitle("Need Permissions")
        builder.setCancelable(false)
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

    fun getTODOCustomerListData(city_code: String) {

        Retailer_List.clear()
        td_Retailer_search.setText("");
        td_Retailer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0)

        todolist_progress_customer.visibility = View.VISIBLE
        rtocustomerlist.visibility = View.GONE
        var user_email: String? = ""
        val sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE)
        try {
            user_email = if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(sp.getString("USER_EMAIL", "").toString())) {
                sp.getString("USER_EMAIL", "")
            } else {
                Global_Data.GLOvel_USER_EMAIL
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

        val domain = resources.getString(R.string.service_domain)
        var url = ""

        if (approved!!.length>0 || rejected!!.length>0 || pending!!.length>0 || incomplete!!.length>0 || incomplete1!!.length>0) {

            if(approved!!.length>0 && rejected!!.length>0 && pending!!.length>0 && incomplete!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=approved+","+rejected+","+pending+","+incomplete+","+incomplete1
            }else if(rejected!!.length>0 && pending!!.length>0 && incomplete!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=rejected+","+pending+","+incomplete+","+incomplete1
            }else if(pending!!.length>0 && incomplete!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=pending+","+incomplete+","+incomplete1
            }else if(incomplete!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=incomplete+","+incomplete1
            }else if(pending!!.length>0 && incomplete!!.length>0)
            {
                Global_Data.filterValue=pending+","+incomplete
            }else if(pending!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=pending+","+incomplete1
            }else if(approved!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=approved+","+incomplete1
            }else if(rejected!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=rejected+","+incomplete1
            }
            else if(approved!!.length>0 && rejected!!.length>0)
            {
                Global_Data.filterValue=approved+","+rejected
            }else if(approved!!.length>0 && pending!!.length>0)
            {
                Global_Data.filterValue=approved+","+pending
            }else if(approved!!.length>0 && incomplete!!.length>0)
            {
                Global_Data.filterValue=approved+","+incomplete
            }else if(rejected!!.length>0 && pending!!.length>0)
            {
                Global_Data.filterValue=rejected+","+pending
            }else if(rejected!!.length>0 && incomplete!!.length>0)
            {
                Global_Data.filterValue=rejected+","+incomplete
            }else if(pending!!.length>0 && incomplete!!.length>0)
            {
                Global_Data.filterValue=pending+","+incomplete
            }else if(approved!!.length>0 && rejected!!.length>0 && pending!!.length>0)
            {
                Global_Data.filterValue=approved+","+rejected+","+pending
            }else if(approved!!.length>0 && pending!!.length>0 && incomplete!!.length>0)
            {
                Global_Data.filterValue=approved+","+pending+","+incomplete
            }
            else if(approved!!.length>0 && pending!!.length>0 && incomplete!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=approved+","+pending+","+incomplete+","+incomplete1
            }else if(approved!!.length>0 && pending!!.length>0 && incomplete1!!.length>0)
            {
                Global_Data.filterValue=approved+","+pending+","+incomplete
            }
            else if(incomplete!!.length>0)
            {
                Global_Data.filterValue=incomplete
            }else if(incomplete1!!.length>0)
            {
                Global_Data.filterValue=incomplete1
            }else if(pending!!.length>0)
            {
                Global_Data.filterValue=pending
            }else if(rejected!!.length>0)
            {
                Global_Data.filterValue=rejected
            }else if(approved!!.length>0)
            {
                Global_Data.filterValue=approved
            }


            url = domain + "retailers/filtered_retailers?email=" + user_email + "&filters=" + Global_Data.filterValue + "&to_do_list_title=" + filterCheck
            approved=""
            incomplete=""
            incomplete1=""
            rejected=""
            pending=""
            Global_Data.filterValue=""
        } else {

        if (id.equals("1")) {
            url = domain + "retailers/to_do_red_list?email=" + user_email + "&city_code=" + URLEncoder.encode(city_code, "UTF-8")
        } else
            if (id.equals("2")) {
                url = domain + "retailers/to_do_yellow_list?email=" + user_email + "&city_code=" + URLEncoder.encode(city_code, "UTF-8")
            } else
                if (id.equals("3")) {
                    url = domain + "retailers/to_do_light_green_list?email=" + user_email + "&city_code=" + URLEncoder.encode(city_code, "UTF-8")
                } else
                    if (id.equals("4")) {
                        url = domain + "retailers/to_do_dark_green_list?email=" + user_email + "&city_code=" + URLEncoder.encode(city_code, "UTF-8")
                    }
    }

        Log.i("volley", "URL: $url")
        Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL)

        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            GetTODOCustomerResponseData().execute(response)
        },
                Response.ErrorListener { error ->
                    //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    if (error is TimeoutError || error is NoConnectionError) {
                        Toast.makeText(applicationContext,
                                "Network Error",
                                Toast.LENGTH_LONG).show()
                    } else if (error is AuthFailureError) {
                        Toast.makeText(applicationContext,
                                "Server AuthFailureError  Error",
                                Toast.LENGTH_LONG).show()
                    } else if (error is ServerError) {
                        Toast.makeText(applicationContext,
                                "Server   Error",
                                Toast.LENGTH_LONG).show()
                    } else if (error is NetworkError) {
                        Toast.makeText(applicationContext,
                                "Network   Error",
                                Toast.LENGTH_LONG).show()
                    } else if (error is ParseError) {
                        Toast.makeText(applicationContext,
                                "ParseError   Error",
                                Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
                    }
                    todolist_progress_customer.visibility = View.GONE
                    rtocustomerlist.visibility = View.GONE
                    finish()

                })
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val socketTimeout = 300000 //30 seconds - change to what you want
        val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        jsObjRequest.retryPolicy = policy
        jsObjRequest.setShouldCache(false)
        requestQueue.cache.clear()
        requestQueue.add(jsObjRequest)
    }

    public inner class GetTODOCustomerResponseData : AsyncTask<String?, Void?, String>() {
        protected override fun doInBackground(vararg p0: String?): String? {
            try {

                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")
                    runOnUiThread {
                        todolist_progress_customer.visibility = View.GONE
                        rtocustomerlist.visibility = View.GONE
                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        //finish()
                    }.toString()

                } else { //dbvoc.getDeleteTable("delivery_products");
                    val retailers = response.getJSONArray("retailers")
                    Log.i("volley", "response retailers Length: " + retailers.length())
                    Log.d("volley", "retailers$retailers")
                    //
                    if (retailers.length() <= 0) {
                        runOnUiThread {
                            todolist_progress_customer.visibility = View.GONE
                            rtocustomerlist.visibility = View.GONE
                            val toast = Toast.makeText(context, "Record doesn't exist", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            // finish()
                        }
                    } else {
                        Allresult.clear()
                        for (i in 0 until retailers.length()) {
                            var user_cirname = ""
                            val jsonObject = retailers.getJSONObject(i)
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("code"))) {

                                var full_address = ""

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("address_line1")))
                                {
                                    full_address += " " + jsonObject!!.getString("address_line1")
                                }
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("address_line2")))
                                {
                                    full_address += "," + jsonObject!!.getString("address_line2")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("landmark")))
                                {
                                    full_address += "," + jsonObject!!.getString("landmark")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("state")))
                                {
                                    full_address += "," + jsonObject!!.getString("state")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("city")))
                                {
                                    full_address += "," + jsonObject!!.getString("city")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("pincode")))
                                {
                                    full_address += "," + jsonObject!!.getString("pincode")
                                }

                                try {
                                    full_address = if (full_address.startsWith(",")) full_address.substring(1) else full_address
                                    full_address = if (full_address.endsWith(",")) full_address.substring(full_address.length-1) else full_address
                                }catch (e:Exception) {e.printStackTrace()}

                                Allresult.add(RCTOData("", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("code")), "",
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("shop_name")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("address_line1")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("state_id")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("city_id")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("pincode")),
                                        "", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("mobile_no")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("email")), "",
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("proprietor_name")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("gst_no")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("aadhar_no")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("pan_no")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("latitude")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("longitude")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("power_dealer")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("lighting_dealer")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("iaq_dealer")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("source_of_data")),
                                        "", "", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("tsi_code")),
                                        coardcolor, "", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("address_line2")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("landmark")),full_address
                                        ,Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("district_id")), "",Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("is_approved")),Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("bucket_name")),Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("mobile_verified"))))

                                Retailer_List.add(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("code"))+" - "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("shop_name")))

                            }
                        }
                        runOnUiThread {
                            todolist_progress_customer.visibility = View.GONE
                            rtocustomerlist.visibility = View.VISIBLE
                            ca = RCTDAdapter(context!!, Allresult);
                            rtocustomerlist.setAdapter(ca);
                            ca!!.notifyDataSetChanged();

                            val adapterauto = AutoSuggestAdapter(context, android.R.layout.simple_spinner_dropdown_item, Retailer_List)
                            //rtocustomerlist_filter.setThreshold(1) // will start working from

                            // val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,Retailer_List)
                            td_Retailer_search.setThreshold(1) // will start working from
                            td_Retailer_search.setAdapter(adapterauto) // setting the adapter
                            td_Retailer_search.setTextColor(Color.BLACK)

                        }.toString()

                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread {
                    todolist_progress_customer.visibility = View.GONE
                    rtocustomerlist.visibility = View.GONE
                }
            }
            runOnUiThread {
                todolist_progress_customer.visibility = View.GONE
            }
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread {
                todolist_progress_customer.visibility = View.GONE
            }
        }

        override fun onPreExecute() {}

    }

    fun CaalF(Mobilenumber: String) {
        requestPhoneCallPermission(Mobilenumber.trim({ it <= ' ' }))
    }

    fun userStateCities() {
        todolist_progress_customer.visibility = View.VISIBLE

        var user_email: String? = ""
        val sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE)
        try {
            user_email = if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(sp.getString("USER_EMAIL", "").toString())) {
                sp.getString("USER_EMAIL", "")
            } else {
                Global_Data.GLOvel_USER_EMAIL
            }
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        }

        val domain = resources.getString(R.string.service_domain)
        Log.i("volley", "domain: $domain")
        var url = domain+"retailers/get_statewise_cities?state_id="+state_code+"&email="+user_email
        Log.i("get_cities url", "user list url " +url)
        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            getstatewise_City().execute(response)
        },
                Response.ErrorListener { error ->
                    //dialog.dismiss()

                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val jsonObject = JSONObject(responseBody)

                        var response_result = ""
                        if (jsonObject.has("message")) {
                            response_result = jsonObject.getString("message")

                            todolist_progress_customer.visibility = View.GONE
                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                        }
                        else
                        {
                            if (error is TimeoutError || error is NoConnectionError) {
                                Toast.makeText(applicationContext,
                                        "Network Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is AuthFailureError) {
                                Toast.makeText(applicationContext,
                                        "Server AuthFailureError  Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is ServerError) {
                                Toast.makeText(applicationContext,
                                        "Server   Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is NetworkError) {
                                Toast.makeText(applicationContext,
                                        "Network   Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is ParseError) {
                                Toast.makeText(applicationContext,
                                        "ParseError   Error",
                                        Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
                            }
                        }

                    } catch (e: JSONException) {
                        //Handle a malformed json response
                    }
                    todolist_progress_customer.visibility = View.GONE

                })
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val socketTimeout = 300000 //30 seconds - change to what you want
        val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        jsObjRequest.retryPolicy = policy
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        jsObjRequest.setShouldCache(false)
        requestQueue.cache.clear()
        requestQueue.add(jsObjRequest)
    }

    public inner class getstatewise_City : AsyncTask<String?, Void?, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")

                    runOnUiThread(Runnable {
                        todolist_progress_customer.visibility = View.GONE
                        //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                }
                else {
                    val cities: JSONArray = response.getJSONArray("cities")
                    Log.i("volley", "response cities Length: " + cities.length())
                    Log.d("volley", "cities$cities")

                    //list_CCity.clear()
                    //list_CCity.add("Select City")
                    //cityspinnerMap.clear()



                    for (i in 0 until cities.length()) {
                        val jsonObject = cities.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cities.getString(i))) {
                                run {
                                    list_Cfilter.add(jsonObject.getString("name"))
                                    CfilterspinnerMap.put(jsonObject.getString("name"),jsonObject.getString("code"))
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    runOnUiThread(Runnable {

                        adapter_Cfilter = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_Cfilter)

                        adapter_Cfilter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        rtocustomerlist_filter.setAdapter(adapter_Cfilter)

                    })
                    runOnUiThread(Runnable {
                        todolist_progress_customer.visibility = View.GONE
                    })

                }

            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread(Runnable {
                    todolist_progress_customer.visibility = View.GONE
                })
            }
            runOnUiThread(Runnable {
                todolist_progress_customer.visibility = View.GONE
            })
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread(Runnable {
                todolist_progress_customer.visibility = View.GONE
            })
        }

        override fun onPreExecute() {}

    }

    // slide the view from below itself to the current position
    fun slideUp(view: View) {
//        rtocustomerlist.isClickable=false
        rtocustomerlist.visibility=View.GONE
//        rtocustomerlist.setClickable(false)
//        rtocustomerlist.setEnabled(false)
        //rtocustomerlist.focusable=false
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
                view.height.toFloat(),  // fromXDelta
                0F,  // toXDelta
                0F,  // fromYDelta
                0F) // toYDelta
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
//        rtocustomerlist.isClickable=true
        rtocustomerlist.visibility=View.VISIBLE
//        rtocustomerlist.setClickable(true)
//        rtocustomerlist.setEnabled(true)

        val animate = TranslateAnimation(
                0F,  // fromXDelta
                view.height.toFloat(),  // toXDelta
                0F,  // fromYDelta
                0F) // toYDelta
        animate.setDuration(500)
        animate.setFillAfter(true)
        view.startAnimation(animate)
    }

    fun onSlideViewButtonClick(view: View?) {
        val builder = AlertDialog.Builder(this@RetailerTDCustomerList)
        val alertDialog = builder.create()
        if (isUp) {
            //alertDialog.dismiss()
            slideDown(myView!!)
            //todo_filter.setText("Slide up")
        } else {

//            val viewGroup: ViewGroup = findViewById(android.R.id.content)
//            val dialogView: View = LayoutInflater.from(this).inflate(R.layout.retailer_filter, viewGroup, false)
//
//            builder.setView(dialogView)
//
//            alertDialog.getWindow()!!.getAttributes().windowAnimations = R.style.DialogTheme
//            alertDialog.show()

            slideUp(myView!!)
            //todo_filter.setText("Slide down")
        }
        isUp = !isUp
    }
}