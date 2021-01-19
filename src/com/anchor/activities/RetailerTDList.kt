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
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.adapter.RTODOList_Adapter
import com.anchor.model.RTODODATA
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
import kotlinx.android.synthetic.main.retailertdlist.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class RetailerTDList : Activity() {
    //Button retail_sales, institute_sales;

    var cd: ConnectionDetector? = null
    var isInternetPresent = false
    var mLayoutManager: RecyclerView.LayoutManager? = null
    var ca: RTODOList_Adapter? = null
    var Allresult: MutableList<RTODODATA> = ArrayList<RTODODATA>()
    var context: Context? = null
    var final_response = ""
    var response_result = ""
//    var myView: View? = null
//    var isUp = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.retailertdlist)

        context = RetailerTDList@ this
        cd = ConnectionDetector(context)

        rtolist.setHasFixedSize(true)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rtolist.setLayoutManager(llm)

        ca = RTODOList_Adapter(TicketListActivity@ this, Allresult);
        rtolist.setAdapter(ca);
        ca!!.notifyDataSetChanged();

//        myView = findViewById(R.id.my_view)
//        myView!!.setVisibility(View.INVISIBLE);
//        // myButton.setText("Slide up");
//        isUp = false;

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
                todaysTarget.text = "Today's Target Acheived"
            }
            mActionBar!!.customView = mCustomView
            mActionBar!!.setDisplayShowCustomEnabled(true)
            mActionBar!!.setHomeButtonEnabled(true)
            mActionBar!!.setDisplayHomeAsUpEnabled(true)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        isInternetPresent = cd!!.isConnectingToInternet
        if (isInternetPresent) {
            getTODOListData()
        } else {
            val toast = Toast.makeText(context,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            finish()
        }

//        done_btn.setOnClickListener {
//
//        }
//
//        reset_btn.setOnClickListener {
//
//        }
//        Global_Data.filterValue
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

        val i = Intent(this@RetailerTDList, Sales_Dash::class.java)
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
        val builder = AlertDialog.Builder(this@RetailerTDList)
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

    fun getTODOListData() {
        todolist_progress.visibility = View.VISIBLE
        val domain = resources.getString(R.string.service_domain)

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

        val url = domain + "retailers/to_do_list_count?email=" + user_email
        Log.i("volley", "URL: $url")
        Log.i("volley", "email: " + user_email)

        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            GetTODOResponseData().execute(response)
        },
                Response.ErrorListener { error ->
                    todolist_progress.visibility = View.GONE
                    finish()
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
                    todolist_progress.visibility = View.GONE

                })
        val requestQueue = Volley.newRequestQueue(applicationContext)
        val socketTimeout = 300000 //30 seconds - change to what you want
        val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        jsObjRequest.retryPolicy = policy
        jsObjRequest.setShouldCache(false)
        requestQueue.cache.clear()
        requestQueue.add(jsObjRequest)
    }

    inner class GetTODOResponseData : AsyncTask<String?, Void?, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")
                    runOnUiThread {
                        todolist_progress.visibility = View.GONE
                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        finish()
                    }.toString()

                } else {

                    Allresult.clear()

                    Allresult.add(RTODODATA("1", response.getString("red_list_count"), "PRIMARY DATA : INCOMPLETE \n PARENT MAPPING : INCOMPLETE", "#831A14", "#BB2B20","pending","yes"))
                    Allresult.add(RTODODATA("2", response!!.getString("yellow_list_count"), "PRIMARY DATA : COMPLETE \n GPS : INCOMPLETE", "#BF9003", "#D8AB1E","approved","no"))
                    Allresult.add(RTODODATA("3", response!!.getString("light_green_list_count"), "PRIMARY DATA : COMPLETE \n GPS : COMPLETE \n PARENT MAPPING : INCOMPLETE", "#28720D", "#3A921A","rejected","yes"))
                    Allresult.add(RTODODATA("4", response!!.getString("dark_green_list_count"), "PRIMARY DATA : COMPLETE \n GPS : COMPLETE  \n PARENT MAPPING : COMPLETE", "#1C4908", "#26600B","incomplete","no"))

                    runOnUiThread {

                        todolist_progress.visibility = View.GONE

                        runOnUiThread {

                            todolist_progress.visibility = View.GONE
                            ca = RTODOList_Adapter(context, Allresult);
                            rtolist.setAdapter(ca);
                            ca!!.notifyDataSetChanged();


                        }


                    }


                }
            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread { todolist_progress.visibility = View.GONE }
            }
            runOnUiThread { todolist_progress.visibility = View.GONE }
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread { todolist_progress.visibility = View.GONE }
        }

        override fun onPreExecute() {}

    }

//    // slide the view from below itself to the current position
//    fun slideUp(view: View) {
//        view.visibility = View.VISIBLE
//        val animate = TranslateAnimation(
//                view.height.toFloat(),  // fromXDelta
//                0F,  // toXDelta
//                0F,  // fromYDelta
//                0F) // toYDelta
//        animate.setDuration(500)
//        animate.setFillAfter(true)
//        view.startAnimation(animate)
//    }
//
//    // slide the view from its current position to below itself
//    fun slideDown(view: View) {
//        val animate = TranslateAnimation(
//                0F,  // fromXDelta
//                view.height.toFloat(),  // toXDelta
//                0F,  // fromYDelta
//                0F) // toYDelta
//        animate.setDuration(500)
//        animate.setFillAfter(true)
//        view.startAnimation(animate)
//    }
//
//    fun onSlideViewButtonClick(view: View?) {
//        if (isUp) {
//            slideDown(myView!!)
//            //todo_filter.setText("Slide up")
//        } else {
//            slideUp(myView!!)
//            //todo_filter.setText("Slide down")
//        }
//        isUp = !isUp
//    }
}