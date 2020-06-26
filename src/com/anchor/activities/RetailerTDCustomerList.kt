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
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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

                                Allresult.add(RCTOData("", jsonObject!!.getString("code"), "", jsonObject!!.getString("shop_name"), jsonObject!!.getString("address"), jsonObject!!.getString("state_code"), jsonObject!!.getString("city_code"), jsonObject!!.getString("pincode"), "", jsonObject!!.getString("mobile_no"), jsonObject!!.getString("email"), "", jsonObject!!.getString("proprietor_name"), jsonObject!!.getString("gst_no"), jsonObject!!.getString("aadhar_no"), jsonObject!!.getString("pan_no"), jsonObject!!.getString("latitude"), jsonObject!!.getString("longitude"), jsonObject!!.getString("power_dealer"), jsonObject!!.getString("lighting_dealer"), jsonObject!!.getString("iaq_dealer"), jsonObject!!.getString("source_of_data"), "", "",jsonObject!!.getString("tsi_code"), coardcolor,""))


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