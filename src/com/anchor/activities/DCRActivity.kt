package com.anchor.activities

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.*
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.activities.Global_Data.context
import com.anchor.adapter.DCRAdapter
import com.anchor.model.DCRModel
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.opencsv.CSVWriter
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_d_c_r.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DCRActivity : Activity() {
    private val recyclerView: ShimmerRecyclerView? = null
    var dcrAdapter: DCRAdapter? = null
    var dcrList: MutableList<DCRModel> = ArrayList()
    var datePickerDialog: DatePickerDialog? = null
    var click_detect_flag = ""
    var item: String? = null
    var dialog: ProgressDialog? = null
    var final_response = ""
    var response_result = ""
    var dataAdapterusername: ArrayAdapter<String>? = null
    private val username = ArrayList<String>()
    var calendar: Calendar? = null
    var cd: ConnectionDetector? = null
    var isInternetPresent = false

    //var dcrList: List<DCRModel> = ArrayList<DCRModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_c_r)

        cd = ConnectionDetector(this@DCRActivity)

        var user_name = ""
        if (!Global_Data.USER_FIRST_NAME.equals("null", ignoreCase = true)) {
            user_name = Global_Data.USER_FIRST_NAME.trim { it <= ' ' }
            if (!Global_Data.USER_LAST_NAME.equals("null", ignoreCase = true)) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim { it <= ' ' }
            }
        }

        txtWelcomeUserDcr.setText(user_name + " : " + Global_Data.emp_code)

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
            mTitleTextView.text = "Daily Call Report"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp: SharedPreferences = this@DCRActivity.getSharedPreferences("SimpleLogic", 0)

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

//        dcrList.add(DCRModel("31-Jul-20", "375018", "Distributor", "0", "0", "Details", "Details"))
//        dcrList.add(DCRModel("31-Jul-20", "375018", "Distributor", "0", "0", "Details", "Details"))
//        dcrList.add(DCRModel("31-Jul-20", "375018", "Distributor", "0", "0", "Details", "Details"))
//        dcrList.add(DCRModel("31-Jul-20", "375018", "Distributor", "0", "0", "Details", "Details"))

//        dcrreport_recycler_view?.showShimmerAdapter()
        // dcrreport_recycler_view?.hideShimmerAdapter()

//        username.clear()
        username.add("Select User")

        val hashSet = HashSet<String>()
        hashSet.addAll(username)
        username.clear()
        username.addAll(hashSet)

        dataAdapterusername = ArrayAdapter<String>(
                this@DCRActivity, R.layout.spinner_item, username!!)
        dataAdapterusername!!.setDropDownViewResource(R.layout.spinner_item)
        spindcr.adapter = dataAdapterusername

        spindcr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                item = parent.getItemAtPosition(position).toString()
                if (!selectedItem.equals("Select User")) {

                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {
                        getdata(item!!)
                    } else {
                        val toast = Toast.makeText(this@DCRActivity,
                                "Internet Not Available. ", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        finish()
                    }

//                    val toast = Toast.makeText(this@DCRActivity,
//                                "Internet Not Available. $item", Toast.LENGTH_SHORT)
//                        toast.setGravity(Gravity.CENTER, 0, 0)
//                        toast.show()

//                    val hashSet = HashSet<String>()
//                        hashSet.addAll(username)
//                        username.clear()
//                        username.addAll(hashSet)
//
//                        dataAdapterusername = ArrayAdapter<String>(
//                                this@DCRActivity, R.layout.spinner_item, username!!)
//                        dataAdapterusername!!.setDropDownViewResource(R.layout.spinner_item)
//                        spindcr.adapter = dataAdapterusername

                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        // ConnectFTP();
        dcr_from.setOnClickListener {
            Global_Data.hideSoftKeyboard(this@DCRActivity)
            click_detect_flag = "from_date"

            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]
            //datePickerDialog = DatePickerDialog.newInstance(this@AttendanceActivity, mDay, mMonth, mYear)

            datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val mFormat = DecimalFormat("00")
                val date = mFormat.format(java.lang.Double.valueOf(dayOfMonth.toDouble())) + "-" + mFormat.format(java.lang.Double.valueOf(monthOfYear + 1.toDouble())) + "-" + year

                if (click_detect_flag.equals("from_date", ignoreCase = true)) {
                    if (!dcr_to.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(date, dcr_to.text.toString())
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_from.setText(date)

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_from.setText("")
                            val toast = Toast.makeText(applicationContext, "From Date should be less Than To Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_from.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select To Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                } else if (click_detect_flag.equals("to_date", ignoreCase = true)) {
                    if (!dcr_from.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(dcr_from.text.toString(), date)
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_to.setText(date)

                            isInternetPresent = cd!!.isConnectingToInternet

                            if (isInternetPresent) {
                                getdata("")

                            } else {
                                val toast = Toast.makeText(this,
                                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                                finish()
                            }

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_to.setText("")
                            val toast = Toast.makeText(applicationContext, "To Date should be Greater Than from Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_to.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select From Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            }, mYear, mMonth, mDay)

            //datePickerDialog = DatePickerDialog.newInstance(this@DCRActivity, Day, Month, Year)
            datePickerDialog?.setThemeDark(false)
            datePickerDialog?.showYearPickerFirst(false)
            // datePickerDialog?.setYearRange(2017, Year)
            datePickerDialog?.setAccentColor(Color.parseColor("#303F9F"))
            datePickerDialog?.setTitle("Select From Date")
            datePickerDialog?.show(fragmentManager, "Smart Anchor App")
        }

        dcr_to.setOnClickListener {
            Global_Data.hideSoftKeyboard(this@DCRActivity)
            click_detect_flag = "to_date"
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]

            datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mFormat = DecimalFormat("00")
                val date = mFormat.format(java.lang.Double.valueOf(dayOfMonth.toDouble())) + "-" + mFormat.format(java.lang.Double.valueOf(monthOfYear + 1.toDouble())) + "-" + year

                if (click_detect_flag.equals("from_date", ignoreCase = true)) {
                    if (!dcr_to.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(date, dcr_to.text.toString())
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_from.setText(date)

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_from.setText("")
                            val toast = Toast.makeText(applicationContext, "From Date should be less Than To Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_from.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select To Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                } else if (click_detect_flag.equals("to_date", ignoreCase = true)) {
                    if (!dcr_from.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(dcr_from.text.toString(), date)
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_to.setText(date)

                            isInternetPresent = cd!!.isConnectingToInternet

                            if (isInternetPresent) {
                                getdata("")

                            } else {
                                val toast = Toast.makeText(this,
                                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()
                                finish()
                            }

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_to.setText("")
                            val toast = Toast.makeText(applicationContext, "To Date should be Greater Than from Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_to.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select From Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            }, mYear, mMonth, mDay)
            datePickerDialog?.setThemeDark(false)
            //datePickerDialog?.setYearRange(2017, Year)
            datePickerDialog?.showYearPickerFirst(false)
            datePickerDialog?.setAccentColor(Color.parseColor("#303F9F"))
            datePickerDialog?.setTitle("Select To Date")
            datePickerDialog?.show(fragmentManager, "Dealer App")
        }

        iv_download.setOnClickListener {
            showPopupMenu(iv_download)
        }

        isInternetPresent = cd!!.isConnectingToInternet

        if (isInternetPresent) {
            getdata("")

        } else {
            val toast = Toast.makeText(this,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            finish()
        }
    }

    private fun getdata(items: String) {
        dialog = ProgressDialog(this@DCRActivity)
        dialog!!.setTitle("Smart Anchor")
        dialog!!.setMessage("Please wait...")
        dialog!!.show()
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

        var url: String? = null
//        if (!item.equals("Select User")) {
//            url = domain + "reports/view_dcr_report?email=" + user_email + "&from_date=" + dcr_from.text.toString() + "&to_date=" + dcr_to.text.toString() + "&user_name=" + item
//
//        } else {
//
//        }
        if (items.length > 0) {
            url = domain + "reports/view_dcr_report?email=" + user_email + "&from_date=" + dcr_from.text.toString() + "&to_date=" + dcr_to.text.toString() + "&user_name=" + items

        } else {
            url = domain + "reports/view_dcr_report?email=" + user_email + "&from_date=" + dcr_from.text.toString() + "&user_name=" + "&to_date=" + dcr_to.text.toString()
        }




        Log.i("get_cities url", "user list url " + url)
        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            getDcrData().execute(response)
        },
                Response.ErrorListener { error ->
                    //dialog.dismiss()

                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val jsonObject = JSONObject(responseBody)

                        var response_result = ""
                        if (jsonObject.has("message")) {
                            response_result = jsonObject.getString("message")
                            dialog!!.dismiss()
                            //  todolist_progress_customer.visibility = View.GONE
                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                        } else {
                            if (error is TimeoutError || error is NoConnectionError) {
                                dialog!!.dismiss()

                                Toast.makeText(applicationContext,
                                        "Network Error",
                                        Toast.LENGTH_LONG).show()

                            } else if (error is AuthFailureError) {
                                dialog!!.dismiss()

                                Toast.makeText(applicationContext,
                                        "Server AuthFailureError  Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is ServerError) {
                                dialog!!.dismiss()

                                Toast.makeText(applicationContext,
                                        "Server   Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is NetworkError) {
                                dialog!!.dismiss()

                                Toast.makeText(applicationContext,
                                        "Network   Error",
                                        Toast.LENGTH_LONG).show()
                            } else if (error is ParseError) {
                                dialog!!.dismiss()

                                Toast.makeText(applicationContext,
                                        "ParseError   Error",
                                        Toast.LENGTH_LONG).show()
                            } else {
                                dialog!!.dismiss()

                                Toast.makeText(applicationContext, error.message, Toast.LENGTH_LONG).show()
                            }
                        }

                    } catch (e: JSONException) {
                        //Handle a malformed json response
                        dialog!!.dismiss()

                    }
                    //  todolist_progress_customer.visibility = View.GONE

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

    public inner class getDcrData : AsyncTask<String?, Void?, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")

                    runOnUiThread(Runnable {

                        dialog!!.dismiss()

                        dcrreport_recycler_view.hideShimmerAdapter()

                        //  todolist_progress_customer.visibility = View.GONE
                        //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                } else {

                    val cities: JSONArray = response.getJSONArray("data")
                    Log.i("volley", "response cities Length: " + cities.length())
                    Log.d("volley", "cities$cities")
                    runOnUiThread(Runnable {
                        if (cities.length() <= 0) {
                            dialog!!.dismiss()

                            Toast.makeText(this@DCRActivity, "DCR not found.", Toast.LENGTH_SHORT).show()
                            dcrreport_recycler_view.hideShimmerAdapter()
                            val i = Intent(this@DCRActivity, ReportsActivity::class.java)
                            startActivity(i)
//                             Toast toast =
//                                     var toast :Toast?=null
//                             Toast.makeText(Customer_Service.this,"Please Select City",
//                             Toast.LENGTH_SHORT);
//                              toast.setGravity(Gravity.CENTER, 0, 0);
//                              toast.show();
                        }
                    })

                    dcrList.clear()
                    username.clear()
                    username.add("Select User")
                    //list_CCity.add("Select City")
                    //cityspinnerMap.clear()

                    for (i in 0 until cities.length()) {
                        val jsonObject = cities.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cities.getString(i))) {
                                run {
                                    jsonObject.getString("user_name")
                                    username.add(jsonObject.getString("user_name"))
                                    //     val s: Set<String> = LinkedHashSet<String>(username)

                                    dcrList.add(DCRModel(jsonObject.getString("date"), jsonObject.getString("code"), jsonObject.getString("customer_type"), jsonObject.getString("feedbacks"), jsonObject.getString("claims"), "Details", "Details", jsonObject.getString("promotional_type"), jsonObject.getString("in_time"), jsonObject.getString("out_time"), jsonObject.getString("return_orders"), jsonObject.getString("no_orders"), jsonObject.getString("order_taken")))

                                }
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    runOnUiThread(Runnable {
                        val hashSet = HashSet<String>()
                        hashSet.addAll(username)
                        username.clear()
                        username.addAll(hashSet)
//
//                        dataAdapterusername = ArrayAdapter<String>(
//                                this@DCRActivity, R.layout.spinner_item, username!!)
//                        dataAdapterusername!!.setDropDownViewResource(R.layout.spinner_item)
//                        spindcr.adapter = dataAdapterusername

                        dcrAdapter = DCRAdapter(this@DCRActivity, dcrList)
                        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
                        dcrreport_recycler_view?.setLayoutManager(mLayoutManager)
                        dcrreport_recycler_view?.setItemAnimator(DefaultItemAnimator())
                        dcrreport_recycler_view?.setAdapter(dcrAdapter)
                        dcrAdapter!!.notifyDataSetChanged()
                        dialog!!.dismiss()

                    })

                    runOnUiThread(Runnable {
                        //  todolist_progress_customer.visibility = View.GONE
                    })

                }

            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread(Runnable {
                    dialog!!.dismiss()

                    // todolist_progress_customer.visibility = View.GONE
                })
            }
            runOnUiThread(Runnable {
                // todolist_progress_customer.visibility = View.GONE
                dialog!!.dismiss()

            })
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread(Runnable {
                // todolist_progress_customer.visibility = View.GONE
                dialog!!.dismiss()

            })
        }

        override fun onPreExecute() {}

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
        // TODO Auto-generated method stub
        //super.onBackPressed();
        val i = Intent(this@DCRActivity, ReportsActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(i)
        finish()
    }

//    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
//        val mFormat = DecimalFormat("00")
//        val date = mFormat.format(java.lang.Double.valueOf(dayOfMonth.toDouble())) + "-" + mFormat.format(java.lang.Double.valueOf(monthOfYear + 1.toDouble())) + "-" + year
//
//        if (click_detect_flag.equals("from_date", ignoreCase = true)) {
//            if (!dcr_to.text.toString().equals("", ignoreCase = true)) {
//                val s = CheckDates(date, dcr_to.text.toString())
//                if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
//                    dcr_from.setText(date)
//
//                } else if (s.equals("t", ignoreCase = true)) {
//                    dcr_from.setText("")
//                    val toast = Toast.makeText(applicationContext, "From Date should be less Than To Date ", Toast.LENGTH_LONG)
//                    toast.setGravity(Gravity.CENTER, 0, 0)
//                    toast.show()
//                }
//            } else {
//                dcr_from.setText(date)
//                val toast = Toast.makeText(applicationContext, "Please Select To Date ", Toast.LENGTH_LONG)
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
//            }
//        } else if (click_detect_flag.equals("to_date", ignoreCase = true)) {
//            if (!dcr_from.text.toString().equals("", ignoreCase = true)) {
//                val s = CheckDates(dcr_from.text.toString(), date)
//                if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
//                    dcr_to.setText(date)
//
//                    isInternetPresent = cd!!.isConnectingToInternet
//
//                    if (isInternetPresent) {
//                        getdata()
//
//                    } else {
//                        val toast = Toast.makeText(this,
//                                "Internet Not Available. ", Toast.LENGTH_SHORT)
//                        toast.setGravity(Gravity.CENTER, 0, 0)
//                        toast.show()
//                        finish()
//                    }
//
//                } else if (s.equals("t", ignoreCase = true)) {
//                    dcr_to.setText("")
//                    val toast = Toast.makeText(applicationContext, "To Date should be Greater Than from Date ", Toast.LENGTH_LONG)
//                    toast.setGravity(Gravity.CENTER, 0, 0)
//                    toast.show()
//                }
//            } else {
//                dcr_to.setText(date)
//                val toast = Toast.makeText(applicationContext, "Please Select From Date ", Toast.LENGTH_LONG)
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
//            }
//        }
//    }

    fun CheckDates(from_date: String?, to_date: String?): String? {
        val dfDate = SimpleDateFormat("dd-MM-yyyy")
        var b = ""
        try {
            b = if (dfDate.parse(from_date).before(dfDate.parse(to_date))) {
                "f" //If start date is before end date
            } else if (dfDate.parse(from_date) == dfDate.parse(to_date)) {
                "a" //If two dates are equal
            } else {
                "t" //If start date is after the end date
            }
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return b
    }

    private fun showPopupMenu(view: View) {
        // inflate menu
        // string=view.title.toString()
        val popup = PopupMenu(this, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_dcr, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener())
        popup.show()


    }

    internal inner class MyMenuItemClickListener : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.action_xlsx -> {
                    //    status="resource"
                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {
                        // ExpenseGraphResult(status)
                        requestStoragePermission()
                    } else {
                        Toast.makeText(this@DCRActivity, "You don't have internet connection.", Toast.LENGTH_SHORT).show()
                    }
                    return true
                }
                R.id.action_pdf -> {

                    dialog!!.show()
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

                    var pdfurl: String? = null

                    if (!item.equals("Select User")) {
                        pdfurl = domain + "reports/view_dcr_report?email=" + user_email + "&from_date=" + dcr_from.text.toString() + "&to_date=" + dcr_to.text.toString() + "&user_name=" + item + "&from=pdf"

                    } else {
                        pdfurl = domain + "reports/view_dcr_report?email=" + user_email + "&from_date=" + dcr_from.text.toString() + "&to_date=" + dcr_to.text.toString() + "&user_name=&from=pdf"

                    }


                    //      var pdfurl = domain + "reports/view_dcr_report?email=" + user_email + "&from_date=" + dcr_from.text.toString() + "&to_date=" + dcr_to.text.toString() + "&from=" + "pdf"
                    Log.i("pdfurl", "pdfurl" + pdfurl)
                    dialog!!.dismiss()


                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pdfurl))
                    browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(browserIntent)
                    return true
                }
            }
            return false
        }
    }

    private fun requestStoragePermission() {
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {


                            try {
                                exportEmailInCSV()
                            } catch (e: IOException) {
                                e.printStackTrace()
                            }

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?, p1: PermissionToken?) {
                        p1?.continuePermissionRequest()
                    }


                }).withErrorListener { error ->
                    Toast.makeText(applicationContext, "Error occurred! $error", Toast.LENGTH_SHORT).show()
//                    Global_Data.Custom_Toast(applicationContext, "Error occurred! $error","")
                }
                .onSameThread()
                .check()
    }


    @Throws(IOException::class)
    fun exportEmailInCSV() {
        run {

            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            val folder = File(path, "AnchorMetal")

            if (!folder.exists()) {
                folder.mkdir()
            }

            val `var` = false


            println("" + `var`)


            // val current = LocalDateTime.now()
            val randomPIN = System.currentTimeMillis()
            val PINString = randomPIN.toString()

            val c = Calendar.getInstance()
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val formattedDate = df.format(c.time)

            val dff = SimpleDateFormat("yyyy-MM-dd")
            val formattedDatef = dff.format(c.time)


            var purchaseid = PINString

            val filename = folder.toString() + "/" + "DCRreportxls" + purchaseid + ".csv"

            // show waiting screen
            val contentTitle = getString(R.string.app_name)
            val progDailog = ProgressDialog.show(
                    this@DCRActivity, contentTitle, "Generated...",
                    true)//please wait
            val handler = object : Handler() {
                override fun handleMessage(msg: Message) {

                    val yourFile = File(folder, "DCRreport" + purchaseid + ".csv")

                    try {
                        openFile(yourFile)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }
            }

            object : Thread() {
                override fun run() {
                    try {

                        var writer: CSVWriter? = null
                        // writer = CSVWriter(FileWriter(filename), ',')
                        writer = CSVWriter(FileWriter(filename), ',')
                        val entries = "Date#CC Code#Customer Type#Feedback#Cliams#Order Count Details#Promotional Activity".split("#") // array of your values
                        writer!!.writeNext(entries.toTypedArray())


                        if (dcrList.size > 0) {
                            for (i in dcrList.indices) {
                                val list_items = dcrList[i]

                                val s = list_items.date + "#" + list_items.cc_code + "#" + list_items.customer_type + "#" + list_items.feedback + "#" + list_items.claim + "#" + list_items.order_count_details + "#" + list_items.promotional_activity
                                val entriesdata = s.split("#") // array of your values
                                writer!!.writeNext(entriesdata.toTypedArray())

                            }
                        }


                        writer!!.close()

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    handler.sendEmptyMessage(0)
                    progDailog.dismiss()
                }
            }.start()

        }

    }

    private fun openFile(url: File) {

        try {

            val uri = Uri.fromFile(url)

            val intent = Intent(Intent.ACTION_VIEW)
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword")
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf")
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel")

            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain")
            } else if (url.toString().contains(".mp4") || url.toString().contains(".AVI") || url.toString().contains(".FLV") || url.toString().contains(".WMV") || url.toString().contains(".MOV")) {
                // Text file
                intent.setDataAndType(uri, "video/mp4")

            } else {
                intent.setDataAndType(uri, "text/csv")
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(applicationContext, "No application found which can open the file", Toast.LENGTH_SHORT).show()
//            Global_Data.Custom_Toast(applicationContext, "No application found which can open the file","")
        }

    }


    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this@DCRActivity)
        builder.setTitle("Need Permissions")
        builder.setCancelable(false)
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
            openSettings()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        builder.show()

    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }

//   fun dcrDialog() {
//       val dialognew = Dialog(this@DCRActivity)
//       dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE)
//       dialognew.setCancelable(false)
//       dialognew.setContentView(R.layout.promotion_dialog)
//
//       val pro_header = dialognew.findViewById<TextView>(R.id.pro_header)
//       val pro_time = dialognew.findViewById<TextView>(R.id.pro_time)
//       val pro_date = dialognew.findViewById<TextView>(R.id.pro_date)
//       val pro_address = dialognew.findViewById<TextView>(R.id.pro_address)
//       val pro_click = dialognew.findViewById<Button>(R.id.pro_click)
//
//
//       val sdf = SimpleDateFormat("dd-MM-yyyy")
//       val date = Date()
//       // String daten = sdf.format(date);
//       // String daten = sdf.format(date);
//       val datenew = sdf.format(date)
//
//       val d = Date()
//       val sdf_time = SimpleDateFormat("hh:mm a")
//       val currentDateTimeString = sdf_time.format(d)
//
////        if (flag.equals("OUT", ignoreCase = true)) {
////            pro_header.text = "OUT TIME"
////        } else {
////            pro_header.text = "IN TIME"
////            outDateTime = ""
////        }
//
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
////            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
////        }
////
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
////            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
////        }
//
//
//       //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//
//
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
////            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
////        }
////
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
////            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
////        }
//
//
//       //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//       if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.address)) {
//           val str = Global_Data.address.trim { it <= ' ' }.replace("\n".toRegex(), " ")
//           pro_address.text = "Address : $str"
//       } else {
//           pro_address.text = "Address Not Found."
//       }
//
//       if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(datenew)) {
//           try {
//               val timenewarray = datenew.split("-".toRegex()).toTypedArray()
//               val month: String = getMonthForInt(timenewarray[1].toInt() - 1)
//               pro_date.text = "Date : " + timenewarray[0] + " " + month + " " + timenewarray[2]
//           } catch (ex: java.lang.Exception) {
//               ex.printStackTrace()
//           }
//       } else {
//           pro_date.text = "Date Not Found."
//       }
//
//       pro_time.text = "Time : $currentDateTimeString"
//
//       pro_click.setOnClickListener { dialognew.dismiss() }
//
//       dialognew.show()
//   }
}