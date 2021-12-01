package com.anchor.activities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Toast
import com.anchor.adapter.RCTDAdapter
import com.anchor.helper.AutoSuggestAdapter
import com.anchor.model.RCTOData
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.reatilertdcustomerlist.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder
import java.util.ArrayList
import java.util.HashMap

class BasicActivity : Activity() {
    var myView: RelativeLayout? = null
    var id = "";
    var filterCheck=""
    var approved: String? = ""
    var rejected: String? = ""
    var pending: String? = ""
    var incomplete: String? = ""
    var incomplete1: String? = ""
    var final_response = ""
    var response_result = ""
    var context: Context? = null
    var Allresult: MutableList<RCTOData> = ArrayList<RCTOData>()
    var Retailer_List: MutableList<String> = ArrayList()
    var coardcolor = "";
    var ca: RCTDAdapter? = null
    var cd: ConnectionDetector? = null

    var list_Cfilter: MutableList<String> = ArrayList<String>()
    var CfilterspinnerMap = HashMap<String, String>()
    var city_id: String? = ""
    var adapter_Cfilter: ArrayAdapter<String>? = null

    var dbvoc = DataBaseHelper(this)
    var state_code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)
        myView = findViewById(R.id.my_view)
        context = BasicActivity@ this
        cd = ConnectionDetector(context)

//        try {
//            id = intent.getStringExtra("id")
//            coardcolor = intent.getStringExtra("cardcolor")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

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
    }

    fun getTODOCustomerListData(city_code: String) {

       // Retailer_List.clear()
      //  td_Retailer_search.setText("");
       // td_Retailer_search.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.search_icon, 0)

        todolist_progress_customer.visibility = View.VISIBLE
       // rtocustomerlist.visibility = View.GONE
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
               // rtocustomerlist.visibility = View.GONE
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
                      //  rtocustomerlist.visibility = View.GONE
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
                           // rtocustomerlist.visibility = View.GONE
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

                                Allresult.add(
                                    RCTOData("", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("code")), "",
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
                                    ,Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("district_id")), "",Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("is_approved")),Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("bucket_name")),Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject.getString("mobile_verified")))
                                )

                                Retailer_List.add(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("code"))+" - "+Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("shop_name")))

                            }
                        }
                        runOnUiThread {
                            todolist_progress_customer.visibility = View.GONE
                           // rtocustomerlist.visibility = View.VISIBLE
                            ca = RCTDAdapter(context!!, Allresult);
                           // rtocustomerlist.setAdapter(ca);
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
            }
            catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread {
                    todolist_progress_customer.visibility = View.GONE
                    //rtocustomerlist.visibility = View.GONE
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



    }
