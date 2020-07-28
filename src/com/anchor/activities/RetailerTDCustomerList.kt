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
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.anchor.adapter.RCTDAdapter
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
import org.json.JSONException
import org.json.JSONObject
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
    var coardcolor = "";

    var list_Cfilter: MutableList<String> = ArrayList<String>()
    var CfilterspinnerMap = HashMap<String, String>()
    var city_id: String? = "";
    var adapter_Cfilter: ArrayAdapter<String>? = null
    var final_response = ""
    var response_result = ""
    var dbvoc = DataBaseHelper(this)
    var Retailer_List: MutableList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reatilertdcustomerlist)

        context = RetailerTDCustomerList@ this
        cd = ConnectionDetector(context)

        try {
            id = intent.getStringExtra("id")
            coardcolor = intent.getStringExtra("cardcolor")
        } catch (e: Exception) {
            e.printStackTrace()
        }


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
        val contacts2 = dbvoc.getAllCityOrderbyname()
        for (cn in contacts2) {
            list_Cfilter.add(cn.getName())
            CfilterspinnerMap.put(cn.getName(), cn.getCode())
        }
        adapter_Cfilter = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_Cfilter)

        adapter_Cfilter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rtocustomerlist_filter.setAdapter(adapter_Cfilter)
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
                    val view: View = this@RetailerTDCustomerList.getCurrentFocus()
                    if (view != null) {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    if (!td_Retailer_search.getText().toString().equals("", ignoreCase = true)) {
                        td_Retailer_search.setText("")
                        td_Retailer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0)
                    }

                    //autoCompleteTextView1.setText("");
                    td_Retailer_search.showDropDown()
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
                if (name.equals(Allresult[i].shop_name, ignoreCase = true)) {
                    Allresultsearch.add(RCTOData("", Allresult[i].code, "", Allresult[i].shop_name, Allresult[i].address, Allresult[i].state_code, Allresult[i].city_code, Allresult[i].pincode,
                            "", Allresult[i].mobile, Allresult[i].email, Allresult[i].status, Allresult[i].proprietor_name
                            , Allresult[i].gst_no, Allresult[i].aadhar_no, Allresult[i].pan_no, Allresult[i].latitude, Allresult[i].longitude,
                            Allresult[i].power_dealer, Allresult[i].lighting_dealer, Allresult[i].iaq_dealer,
                            Allresult[i].source_of_data, "", "", Allresult[i].tsi_code, Allresult[i].card_color_code, Allresult[i].distance, Allresult[i].address_line2, Allresult[i].landmark, Allresult[i].full_address))

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
            mActionBar.setBackgroundDrawable(ColorDrawable(Color.parseColor("#910505")))
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
            mActionBar.customView = mCustomView
            mActionBar.setDisplayShowCustomEnabled(true)
            mActionBar.setHomeButtonEnabled(true)
            mActionBar.setDisplayHomeAsUpEnabled(true)
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

        if (id.equals("1")) {
            url = domain + "retailers/to_do_red_list?email=" + user_email + "&city_code=" + city_code
        } else
            if (id.equals("2")) {
                url = domain + "retailers/to_do_yellow_list?email=" + user_email + "&city_code=" + city_code
            } else
                if (id.equals("3")) {
                    url = domain + "retailers/to_do_light_green_list?email=" + user_email + "&city_code=" + city_code
                } else
                    if (id.equals("4")) {
                        url = domain + "retailers/to_do_dark_green_list?email=" + user_email + "&city_code=" + city_code
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
                                    full_address += " " + jsonObject!!.getString("address_line2")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("landmark")))
                                {
                                    full_address += " " + jsonObject!!.getString("landmark")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("state")))
                                {
                                    full_address += " " + jsonObject!!.getString("state")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("city")))
                                {
                                    full_address += " " + jsonObject!!.getString("city")
                                }

                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("pincode")))
                                {
                                    full_address += " " + jsonObject!!.getString("pincode")
                                }

                                Allresult.add(RCTOData("", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("code")), "",
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("shop_name")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("address_line1")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("state_code")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("city_code")),
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
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("landmark")),full_address))


                            }
                        }
                        runOnUiThread {
                            todolist_progress_customer.visibility = View.GONE
                            rtocustomerlist.visibility = View.VISIBLE
                            ca = RCTDAdapter(context!!, Allresult);
                            rtocustomerlist.setAdapter(ca);
                            ca!!.notifyDataSetChanged();

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


}