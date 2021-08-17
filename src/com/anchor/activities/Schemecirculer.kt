package com.anchor.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.App.AppController
import com.anchor.adapter.Morebtninvoiceadaptor
import com.anchor.adapter.Schemecirculeradaptor
import com.anchor.model.Morebuttoninvoicemodel
import com.anchor.model.Schemecirculermodel
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_attendance.*
import kotlinx.android.synthetic.main.activity_attendance.dcrreport_recycler_view
import kotlinx.android.synthetic.main.activity_attendance.txtWelcomeUserDcr
import kotlinx.android.synthetic.main.activity_morebtninvoice.*
import kotlinx.android.synthetic.main.activity_schemecirculer.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.nio.charset.StandardCharsets

class Schemecirculer : Activity() {
    var list: MutableList<Schemecirculermodel> = ArrayList()
    var schemecirculeradaptor: Schemecirculeradaptor? = null
    var cd: ConnectionDetector? = null
    var final_response = ""
    var response_result = ""
    var dialog: ProgressDialog? = null
    var isInternetPresent = false
    var list_product_name: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemecirculer)

        Global_Data.context = Schemecirculer@ this
        list_product_name = ArrayList<String>()

        list_product_name!!.clear()
//        list_product_name!!.add("")

        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()

        StrictMode.setVmPolicy(builder.build())

        cd = ConnectionDetector(Global_Data.context)

        var user_name = ""
        if (!Global_Data.USER_FIRST_NAME.equals("null", ignoreCase = true)) {
            user_name = Global_Data.USER_FIRST_NAME.trim { it <= ' ' }
            if (!Global_Data.USER_LAST_NAME.equals("null", ignoreCase = true)) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim { it <= ' ' }
            }
        }


        txtWelcomeUserDcr.setText(user_name + " : " + Global_Data.emp_code)

        isInternetPresent = cd!!.isConnectingToInternet


        val adapter = ArrayAdapter<String>(this,
                R.layout.autocomplete, list_product_name!!)
        autoCompleteTextView1!!.setThreshold(1)// will start working from
        // first character
        autoCompleteTextView1!!.setAdapter(adapter)// setting the adapter
        // data into the
        // AutoCompleteTextView


        autoCompleteTextView1!!.setOnTouchListener(View.OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= autoCompleteTextView1!!.getRight() - autoCompleteTextView1!!.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {

                    val view = this@Schemecirculer.getCurrentFocus()
                    if (view != null) {
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
                    }
                    //autoCompleteTextView1.setText("");
                    autoCompleteTextView1!!.showDropDown()
                    return@OnTouchListener true
                }
            }
            false
        })


        autoCompleteTextView1!!.setOnItemClickListener(AdapterView.OnItemClickListener { parent, arg1, pos, id ->

            // list!!.get()


            var text = parent.getItemAtPosition(pos).toString()

            var filterlist: ArrayList<Schemecirculermodel>? = null
            filterlist = ArrayList<Schemecirculermodel>()

            for (i in 0 until list!!.size) {
                if (list!!.get(i).display_name.equals(text)) {
                    filterlist!!.add(Schemecirculermodel(list!!.get(i).date,
                            list!!.get(i).schemename,
                            list!!.get(i).productname,
                            list!!.get(i).code, list.get(i).description, list.get(i).discount_in_dp_amount, list.get(i).discount_in_dp_percent,
                            list.get(i).display_name, list.get(i).foc_qty, list.get(i).is_discount_scheme, list.get(i).foc_prod, list.get(i).qualifying_quantity,
                            list.get(i).qualify_amount, list.get(i).status, list.get(i).valid_till_date))
                    //   list!!.get(i).
                }

            }

            schemecirculeradaptor = Schemecirculeradaptor(this, filterlist as java.util.ArrayList<Schemecirculermodel>)

            val mLayoutManager = LinearLayoutManager(this)
            dcrreport_recycler_view!!.layoutManager = mLayoutManager
            dcrreport_recycler_view!!.itemAnimator = DefaultItemAnimator()
            dcrreport_recycler_view!!.adapter = schemecirculeradaptor
            adapter.notifyDataSetChanged()

        })




        att_mapat.setOnClickListener {

            att_mapat.setTextColor(Color.parseColor("#FFFFFF"))
            att_log.setTextColor(Color.parseColor("#000000"))
            att_shftro.setTextColor(Color.parseColor("#000000"))

            att_mapat.setBackgroundColor(Color.parseColor("#8A0808"))
            att_log.setBackgroundColor(Color.parseColor("#FFFFFF"))
            att_shftro.setBackgroundColor(Color.parseColor("#FFFFFF"))

            if (isInternetPresent) {
                ViewSchemeData("all")

            } else {
                val toast = Toast.makeText(this,
                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                finish()
            }


        }

        att_log.setOnClickListener {

            att_mapat.setTextColor(Color.parseColor("#000000"))
            att_log.setTextColor(Color.parseColor("#FFFFFF"))
            att_shftro.setTextColor(Color.parseColor("#000000"))


            att_mapat.setBackgroundColor(Color.parseColor("#FFFFFF"))
            att_log.setBackgroundColor(Color.parseColor("#8A0808"))
            att_shftro.setBackgroundColor(Color.parseColor("#FFFFFF"))

            if (isInternetPresent) {
                ViewSchemeData("discount")

            } else {
                val toast = Toast.makeText(this,
                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                finish()
            }


        }

        att_shftro.setOnClickListener {

            att_mapat.setTextColor(Color.parseColor("#000000"))
            att_log.setTextColor(Color.parseColor("#000000"))
            att_shftro.setTextColor(Color.parseColor("#FFFFFF"))


            att_mapat.setBackgroundColor(Color.parseColor("#FFFFFF"))
            att_log.setBackgroundColor(Color.parseColor("#FFFFFF"))
            att_shftro.setBackgroundColor(Color.parseColor("#8A0808"))

            if (isInternetPresent) {
                ViewSchemeData("foc")

            } else {
                val toast = Toast.makeText(this,
                        "Internet Not Available. ", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                finish()
            }


        }


        if (isInternetPresent) {
            ViewSchemeData("all")

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
            mTitleTextView.text = "SCHEME CIRCULATION"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp: SharedPreferences = this@Schemecirculer.getSharedPreferences("SimpleLogic", 0)

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

    private fun ViewSchemeData(s: String) {
        dialog = ProgressDialog(this@Schemecirculer)
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
        var url = domain + "schemes/curicular_list?type=" + s;
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

                                list_product_name!!.clear()

                                val summaryData = json.getJSONArray("schemes")
                                Log.i("volley", "response visits Length: " + summaryData.length())
                                Log.d("users", "visite_schedules$summaryData")
                                list?.clear()
                                if (summaryData.length() > 0) {

                                    // list = java.util.ArrayList<Morebuttoninvoicemodel>()
                                    for (i in 0 until summaryData.length()) {
                                        val jsonObject = summaryData.getJSONObject(i)
                                        list!!.add(Schemecirculermodel(jsonObject.getString("created_at"), jsonObject.getString("scheme_type"),
                                                jsonObject.getString("product"), jsonObject.getString("code"),
                                                jsonObject.getString("description"), jsonObject.getString("discount_in_dp_amount"),
                                                jsonObject.getString("discount_in_dp_percent"), jsonObject.getString("display_name"),
                                                jsonObject.getString("foc_qty"), jsonObject.getString("is_discount_scheme"),
                                                jsonObject.getString("foc_product"), jsonObject.getString("qualifying_quantity"),
                                                jsonObject.getString("qualify_amount"), jsonObject.getString("status"),
                                                jsonObject.getString("valid_till_date")
                                        ))


                                        list_product_name!!.add(jsonObject.getString("display_name"))
                                    }
                                    runOnUiThread(Runnable {
                                        dialog!!.dismiss()

                                        schemecirculeradaptor = Schemecirculeradaptor(this@Schemecirculer, list)
                                        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
                                        dcrreport_recycler_view?.setLayoutManager(mLayoutManager)
                                        dcrreport_recycler_view?.setItemAnimator(DefaultItemAnimator())
                                        dcrreport_recycler_view?.setAdapter(schemecirculeradaptor)

                                    })

                                } else {
                                    Toast.makeText(applicationContext,
                                            "Sorry no record found",
                                            Toast.LENGTH_LONG).show()
                                    //Global_Data.Custom_Toast(this, resources.getString(R.string.Sorry_No_Record_Found), "Yes")
                                    schemecirculeradaptor!!.notifyDataSetChanged()
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

    override fun onBackPressed() {
        val i = Intent(this@Schemecirculer, Sales_Dash::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(i)
    }
}