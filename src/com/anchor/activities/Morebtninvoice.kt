package com.anchor.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.App.AppController
import com.anchor.adapter.AutoSuggestAdapter
import com.anchor.adapter.Morebtninvoiceadaptor
import com.anchor.model.Morebuttoninvoicemodel
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.youtube.player.internal.l
import kotlinx.android.synthetic.main.activity_attendance.*
import kotlinx.android.synthetic.main.activity_attendance.txtWelcomeUserDcr
import kotlinx.android.synthetic.main.activity_morebtninvoice.*
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.MutableList
import kotlin.collections.Set
import kotlin.collections.toList
import kotlin.collections.toSet


class Morebtninvoice : Activity() {
    var list: MutableList<Morebuttoninvoicemodel> = ArrayList()
    var adaptor: Morebtninvoiceadaptor? = null
    var cd: ConnectionDetector? = null
    var final_response = ""
    var invoicesnumber = java.util.ArrayList<String>()
    var itemnumber = java.util.ArrayList<String>()

    var response_result = ""
    var dialog: ProgressDialog? = null
    var isInternetPresent = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morebtninvoice)

        Global_Data.context = Morebtninvoice@ this


        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()

        StrictMode.setVmPolicy(builder.build())

        cd = ConnectionDetector(Global_Data.context)


        val adapterauto = AutoSuggestAdapter(this, android.R.layout.simple_spinner_dropdown_item, invoicesnumber)


        invoice_no.setThreshold(1) // will start working from


        invoice_no.setAdapter(adapterauto) // setting the adapter


        invoice_no.setTextColor(Color.BLACK)

        invoice_no.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= invoice_no.getRight() - invoice_no.getCompoundDrawables().get(DRAWABLE_RIGHT).getBounds().width()) {
                    val view: View = this@Morebtninvoice.getCurrentFocus()!!
                    if (view != null) {
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    //autoCompleteTextView1.setText("");
                    invoice_no.showDropDown()
                    return@OnTouchListener true
                }
            }
            false
        })


        invoice_no.setOnItemClickListener { parent, view, position, id ->

            isInternetPresent = cd!!.isConnectingToInternet

            if (isInternetPresent) {
                val ss: String = intent.getStringExtra("invoice_no").toString()
                val order_no: String = invoice_no.text.toString()

                Viewitemno(ss, order_no)


            //   ViewinvoiceData(ss, order_no, "")


            } else {
                val toast = Toast.makeText(this,
                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                finish()
            }


        }


        val adapterauto2 = AutoSuggestAdapter(this, android.R.layout.simple_spinner_dropdown_item, itemnumber)


        product.setThreshold(1) // will start working from


        product.setAdapter(adapterauto2) // setting the adapter


        product.setTextColor(Color.BLACK)

        product.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= product.getRight() - product.getCompoundDrawables().get(DRAWABLE_RIGHT).getBounds().width()) {
                    val view: View = this@Morebtninvoice.getCurrentFocus()!!
                    if (view != null) {
                        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                    }
                    //autoCompleteTextView1.setText("");
                    product.showDropDown()
                    return@OnTouchListener true
                }
            }
            false
        })



        product.setOnItemClickListener { parent, view, position, id ->


            isInternetPresent = cd!!.isConnectingToInternet

            if (isInternetPresent) {
                val ss: String = intent.getStringExtra("invoice_no").toString()
                val order_no: String = invoice_no.text.toString()
                val itemno: String = product.text.toString()


                ViewinvoiceData(ss, order_no, itemno)

            } else {
                val toast = Toast.makeText(this,
                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                finish()
            }


        }


        var user_name = ""
        if (!Global_Data.USER_FIRST_NAME.equals("null", ignoreCase = true)) {
            user_name = Global_Data.USER_FIRST_NAME.trim { it <= ' ' }
            if (!Global_Data.USER_LAST_NAME.equals("null", ignoreCase = true)) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim { it <= ' ' }
            }
        }

        txtWelcomeUserDcr.setText(user_name + " : " + Global_Data.emp_code)

        isInternetPresent = cd!!.isConnectingToInternet

        if (isInternetPresent) {
            val ss: String = intent.getStringExtra("invoice_no").toString()

            Orderno(ss)

        } else {
            val toast = Toast.makeText(this,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            finish()
        }



        isInternetPresent = cd!!.isConnectingToInternet

        if (isInternetPresent) {
            val ss: String = intent.getStringExtra("invoice_no").toString()

            ViewinvoiceData(ss, "", "")

        } else {
            val toast = Toast.makeText(this,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            finish()
        }




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
            mTitleTextView.text = "INVOICE DETAIL"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp: SharedPreferences = this@Morebtninvoice.getSharedPreferences("SimpleLogic", 0)

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


    }

    private fun Viewitemno(ss: String, orderNo: String) {


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
        var url = domain + "customer_invoices/invoice_product?email=" + Global_Data.CUSTOMER_EMAIL + "&invoice_no=" + ss + "&order_number=" + orderNo
        Log.i("get_cities url", "user list url " + url)
        var stringRequest: StringRequest? = null
        stringRequest = StringRequest(url,
                Response.Listener { response ->
                    Log.d("jV", "JV length" + response.length)
                    try {
                        val json = JSONObject(JSONTokener(response))
                        try {
                            var response_result = ""
                            if (json.has("message")) {
                                response_result = json.getString("message")

                                dialog!!.hide()
                                Toast.makeText(applicationContext,
                                        response_result.toString(),
                                        Toast.LENGTH_LONG).show()
                                // Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                //finish().toString()
                                //finish()
                                onBackPressed()
                            } else {

                                itemnumber.clear()
                                // invoiceremoveduplicats.clear()
                                val summaryData = json.getJSONArray("customer_ledgers")
                                Log.i("volley", "response visits Length: " + summaryData.length())
                                Log.d("users", "visite_schedules$summaryData")
                                //   list?.clear()
                                if (summaryData.length() > 0) {

                                    // list = java.util.ArrayList<Morebuttoninvoicemodel>()
                                    for (i in 0 until summaryData.length()) {
                                        val jsonObject = summaryData.getJSONObject(i)
//                                        shopname.setText(jsonObject.getString("shop_name"))
//                                        contact_no.setText(jsonObject.getString("contact"))
//                                        date.setText(jsonObject.getString("txn_date"))
//                                        Invoice_no.setText(jsonObject.getString("invoice_no"))

                                        itemnumber.add(jsonObject.getString("item_code"))
                                        // val distinct = invoicesnumber.toSet().toList();
                                        //  invoiceremoveduplicats.add(distinct.toString())

                                        // list!!.add(Morebuttoninvoicemodel(jsonObject.getString("product"), jsonObject.getString("qty"), jsonObject.getString("unit_price"), jsonObject.getString("amount"), jsonObject.getString("order_number")))
                                    }

//                                    val s: Set<String> = LinkedHashSet<String>(invoicesnumber)
//                                    invoiceremoveduplicats.add(s.toString())

                                    runOnUiThread(Runnable {
                                        dialog!!.dismiss()


                                    })

                                } else {
                                    Toast.makeText(applicationContext,
                                            "Sorry no record found",
                                            Toast.LENGTH_LONG).show()
                                    //Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                    adaptor!!.notifyDataSetChanged()
                                    dialog!!.dismiss()
                                }
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            finish()
                            dialog!!.dismiss()
                        }
                        dialog!!.dismiss()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        //  finish();
                        dialog!!.dismiss()
                    }
                    dialog!!.dismiss()
                },
                Response.ErrorListener { error ->

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
                    //						Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
//						startActivity(launch);
                    finish()
                    dialog!!.dismiss()
                    // finish();
                })
        val requestQueue = Volley.newRequestQueue(Morebtninvoice@ this)
        val socketTimeout = 300000 //30 seconds - change to what you want
        val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = policy
        // requestQueue.se
//requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false)
        requestQueue.cache.clear()
        //requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest)


    }


    private fun Orderno(ss: String) {

//        dialog = ProgressDialog(this@Morebtninvoice)
//        dialog!!.setTitle("Smart Anchor")
//        dialog!!.setMessage("Please wait...")
//        dialog!!.show()

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
        var url = domain + "customer_invoices/invoice_order?email=" + Global_Data.CUSTOMER_EMAIL + "&invoice_no=" + ss
        Log.i("get_cities url", "user list url " + url)
        var stringRequest: StringRequest? = null
        stringRequest = StringRequest(url,
                Response.Listener { response ->
                    Log.d("jV", "JV length" + response.length)
                    try {
                        val json = JSONObject(JSONTokener(response))
                        try {
                            var response_result = ""
                            if (json.has("message")) {
                                response_result = json.getString("message")

                                dialog!!.hide()
                                Toast.makeText(applicationContext,
                                        response_result.toString(),
                                        Toast.LENGTH_LONG).show()
                                // Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                //finish().toString()
                                //finish()
                                onBackPressed()
                            } else {

                                invoicesnumber.clear()
                                // invoiceremoveduplicats.clear()
                                val summaryData = json.getJSONArray("customer_ledgers")
                                Log.i("volley", "response visits Length: " + summaryData.length())
                                Log.d("users", "visite_schedules$summaryData")
                                //   list?.clear()
                                if (summaryData.length() > 0) {

                                    // list = java.util.ArrayList<Morebuttoninvoicemodel>()
                                    for (i in 0 until summaryData.length()) {
                                        val jsonObject = summaryData.getJSONObject(i)
//                                        shopname.setText(jsonObject.getString("shop_name"))
//                                        contact_no.setText(jsonObject.getString("contact"))
//                                        date.setText(jsonObject.getString("txn_date"))
//                                        Invoice_no.setText(jsonObject.getString("invoice_no"))

                                        invoicesnumber.add(jsonObject.getString("order_number"))
                                        // val distinct = invoicesnumber.toSet().toList();
                                        //  invoiceremoveduplicats.add(distinct.toString())

                                        // list!!.add(Morebuttoninvoicemodel(jsonObject.getString("product"), jsonObject.getString("qty"), jsonObject.getString("unit_price"), jsonObject.getString("amount"), jsonObject.getString("order_number")))
                                    }

//                                    val s: Set<String> = LinkedHashSet<String>(invoicesnumber)
//                                    invoiceremoveduplicats.add(s.toString())

                                    runOnUiThread(Runnable {
                                        dialog!!.dismiss()


                                    })

                                } else {
                                    Toast.makeText(applicationContext,
                                            "Sorry no record found",
                                            Toast.LENGTH_LONG).show()
                                    //Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                    adaptor!!.notifyDataSetChanged()
                                    dialog!!.dismiss()
                                }
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            finish()
                            dialog!!.dismiss()
                        }
                        dialog!!.dismiss()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        //  finish();
                        dialog!!.dismiss()
                    }
                    dialog!!.dismiss()
                },
                Response.ErrorListener { error ->

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
                    //						Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
//						startActivity(launch);
                    finish()
                    dialog!!.dismiss()
                    // finish();
                })
        val requestQueue = Volley.newRequestQueue(Morebtninvoice@ this)
        val socketTimeout = 300000 //30 seconds - change to what you want
        val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = policy
        // requestQueue.se
//requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false)
        requestQueue.cache.clear()
        //requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest)

    }

    private fun ViewinvoiceData(ss: String, orde_no: String, itemno: String) {

        dialog = ProgressDialog(this@Morebtninvoice)
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
        var url = domain + "customer_invoices/invoice_more?email=" + Global_Data.CUSTOMER_EMAIL + "&invoice_no=" + ss + "&order_number=" + orde_no + "&product=" + itemno
        Log.i("get_cities url", "user list url " + url)
        var stringRequest: StringRequest? = null
        stringRequest = StringRequest(url,
                Response.Listener { response ->
                    Log.d("jV", "JV length" + response.length)
                    try {
                        val json = JSONObject(JSONTokener(response))
                        try {
                            var response_result = ""
                            if (json.has("message")) {
                                response_result = json.getString("message")

                                dialog!!.hide()
                                Toast.makeText(applicationContext,
                                        response_result.toString(),
                                        Toast.LENGTH_LONG).show()
                                // Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                //finish().toString()
                                //finish()
                                onBackPressed()
                            } else {

                                val summaryData = json.getJSONArray("customer_ledgers")
                                Log.i("volley", "response visits Length: " + summaryData.length())
                                Log.d("users", "visite_schedules$summaryData")
                                list?.clear()
                                if (summaryData.length() > 0) {

                                    // list = java.util.ArrayList<Morebuttoninvoicemodel>()
                                    for (i in 0 until summaryData.length()) {
                                        val jsonObject = summaryData.getJSONObject(i)
                                        shopname.setText(jsonObject.getString("shop_name"))
                                        contact_no.setText(jsonObject.getString("contact"))
                                        date.setText(jsonObject.getString("txn_date"))
                                        Invoice_no.setText(jsonObject.getString("invoice_no"))
                                        total.setText(jsonObject.getString("grand_total"))

                                        list!!.add(Morebuttoninvoicemodel(jsonObject.getString("product"), jsonObject.getString("qty"), jsonObject.getString("unit_price"), jsonObject.getString("amount"), jsonObject.getString("order_number")))
                                    }
                                    runOnUiThread(Runnable {
                                        dialog!!.dismiss()

                                        adaptor = Morebtninvoiceadaptor(this@Morebtninvoice, list)
                                        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
                                        rv?.setLayoutManager(mLayoutManager)
                                        rv?.setItemAnimator(DefaultItemAnimator())
                                        rv?.setAdapter(adaptor)

                                    })

                                } else {
                                    Toast.makeText(applicationContext,
                                            "Sorry no record found",
                                            Toast.LENGTH_LONG).show()
                                    //Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                    adaptor!!.notifyDataSetChanged()
                                    dialog!!.dismiss()
                                }
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            finish()
                            dialog!!.dismiss()
                        }
                        dialog!!.dismiss()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        //  finish();
                        dialog!!.dismiss()
                    }
                    dialog!!.dismiss()
                },
                Response.ErrorListener { error ->

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
                    //						Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
//						startActivity(launch);
                    finish()
                    dialog!!.dismiss()
                    // finish();
                })
        val requestQueue = Volley.newRequestQueue(Morebtninvoice@ this)
        val socketTimeout = 300000 //30 seconds - change to what you want
        val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = policy
        // requestQueue.se
//requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false)
        requestQueue.cache.clear()
        //requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest)
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


//
}