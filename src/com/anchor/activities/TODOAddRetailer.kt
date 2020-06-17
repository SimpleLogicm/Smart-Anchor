package com.anchor.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.anchor.adapter.Todo_list_adaptor
import com.anchor.helper.VerhoeffAlgorithm
import com.anchor.model.Todo_model
import com.anchor.webservice.ConnectionDetector
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_city
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_iaqdealer
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_lightingdealer
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_powerdealer
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_state
import kotlinx.android.synthetic.main.todoadd_retailer.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern


class TODOAddRetailer : Activity() {
    var list: ArrayList<Todo_model>? = null
    var adaptor: Todo_list_adaptor? = null
    var id = "";
    var coardcolor = "";
    var cd: ConnectionDetector? = null
    var isInternetPresent = false
    var context: Context? = null
    var list_CCity: MutableList<String> = ArrayList<String>()
    var adapter_CCity: ArrayAdapter<String>? = null

    var list_CState: MutableList<String> = ArrayList<String>()
    var adapter_CState: ArrayAdapter<String>? = null

    var list_CPowerDealer: MutableList<String> = ArrayList<String>()
    var adapter_CPowerDealer: ArrayAdapter<String>? = null
    var list_CIaqDealer: MutableList<String> = ArrayList<String>()
    var adapter_CIaqDealer: ArrayAdapter<String>? = null
    var list_CLightingDealer: MutableList<String> = ArrayList<String>()
    var adapter_CLightingDealer: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.todoadd_retailer)

        context = RetailerTDCustomerList@this
        cd = ConnectionDetector(context)

        list = ArrayList<Todo_model>()



        try {
            id = intent.getStringExtra("id")
            coardcolor = intent.getStringExtra("cardcolor")

        }catch (e:Exception)
        {
            e.printStackTrace()
        }




        list_CState.add("Select State")
        list_CState.add("Maharashtra")
        list_CState.add("Madhya Pradesh")
        adapter_CState = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CState)

        adapter_CState!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_state.setAdapter(adapter_CState)
        todoe_state?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select State", ignoreCase = true)) {

                }

            }
        }


        list_CCity.add("Select City")
        list_CCity.add("Mumbai")
        list_CCity.add("Thane")
        adapter_CCity = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CCity)

        adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_city.setAdapter(adapter_CCity)
        todoe_city?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select City", ignoreCase = true)) {

                }

            }

        }

        list_CPowerDealer.add("Select Power Dealer")
        list_CPowerDealer.add("Dealer 1")
        list_CPowerDealer.add("Dealer 2")
        adapter_CPowerDealer = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CPowerDealer)

        adapter_CPowerDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_powerdealer.setAdapter(adapter_CPowerDealer)
        todoe_powerdealer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select Power Dealer", ignoreCase = true)) {

                }

            }
        }

        list_CIaqDealer.add("Select IAQ Dealer")
        list_CIaqDealer.add("Dealer 1")
        list_CIaqDealer.add("Dealer 2")
        adapter_CIaqDealer = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CIaqDealer)

        adapter_CIaqDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_iaqdealer.setAdapter(adapter_CIaqDealer)
        todoe_iaqdealer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select IAQ Dealer", ignoreCase = true)) {

                }

            }
        }

        list_CLightingDealer.add("Select Lighting Dealer")
        list_CLightingDealer.add("Dealer 1")
        list_CLightingDealer.add("Dealer 2")
        adapter_CLightingDealer = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CLightingDealer)

        adapter_CLightingDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_lightingdealer.setAdapter(adapter_CLightingDealer)
        todoe_lightingdealer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select Lighting Dealer", ignoreCase = true)) {

                }

            }
        }

        todoe_address.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

                todoe_geocordinatesa.setText("")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }
        })

        todoe_geocordinatesa.setOnClickListener {
            if (!todoe_address!!.text.toString().trim().equals(""))
            {
                getCordinates()
            }
            else
            {
                val toast = Toast.makeText(context,
                        "Please Enter Address", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }

        }

        locationimgadd.setOnClickListener {

            if (!todoe_address!!.text.toString().trim().equals(""))
            {
                getCordinates()
            }
            else
            {
                val toast = Toast.makeText(context,
                        "Please Enter Address", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        buttonnoadd_cancel.setOnClickListener {
            onBackPressed()

        }

        buttonnoaddr_save.setOnClickListener {

            if (todoe_shop_namea!!.text.toString().trim().equals(""))
            {
                val toast = Toast.makeText(context,
                        "Please Enter Shop Name", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (todoe_mobilea!!.text.toString().trim().equals("") || todoe_mobilea!!.text.length < 10)
            {
                val toast = Toast.makeText(context,
                        "Please Enter Valid Mobile No", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (todoe_gsta!!.text.toString().trim().equals("") && todoe_aadhara!!.text.toString().trim().equals("") && todoe_pana!!.text.toString().trim().equals(""))
            {
                val toast = Toast.makeText(context,
                        "Please Enter GST No. or  Aadhar No. or Pan No.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (!todoe_gsta!!.text.toString().trim().equals("") && !validateGSTNumber(todoe_gsta!!.text.toString().trim()))
            {
                val toast = Toast.makeText(context,
                        "Please Enter Valid GST No.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (!todoe_aadhara!!.text.toString().trim().equals("") && !validateAadharNumber(todoe_aadhara!!.text.toString().trim()))
            {
                val toast = Toast.makeText(context,
                        "Please Enter Valid Aadhar No.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (!todoe_pana!!.text.toString().trim().equals("") && !validatePANNumber(todoe_pana!!.text.toString().trim()))
            {
                val toast = Toast.makeText(context,
                        "Please Enter Valid Pan Card No.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (todoe_state!!.selectedItem.toString().equals("Select State"))
            {
                val toast = Toast.makeText(context,
                        "Please Select State.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (todoe_state!!.selectedItem.toString().equals("Select City"))
            {
                val toast = Toast.makeText(context,
                        "Please Select City.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (todoe_geocordinatesa!!.text.toString().trim().equals(""))
            {
                val toast = Toast.makeText(context,
                        "Please Select Gps ", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            if (todoe_powerdealer!!.selectedItem.toString().equals("Select Power Dealer") && todoe_iaqdealer!!.selectedItem.toString().equals("Select IAQ Dealer") && todoe_lightingdealer!!.selectedItem.toString().equals("Select Lighting Dealer"))
            {
                val toast = Toast.makeText(context,
                        "Please Select Power Dealer or IAQ Dealer or Lighting Dealer.", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
            {


                isInternetPresent = cd!!.isConnectingToInternet
                if (isInternetPresent) {
                    RetailerData()
                }
                else {

                    val toast = Toast.makeText(context,
                            "Internet Not Available. ", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
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
            mCustomView.setBackground(ColorDrawable(Color.parseColor("#910505")))
            val mTitleTextView = mCustomView.findViewById<TextView>(R.id.screenname)
            mTitleTextView.text = "Add Retailer"
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
        super.onBackPressed()
        var alert_dialog_flag = ""

        if (!todoe_shop_namea!!.text.toString().trim().equals(""))
        {
            alert_dialog_flag = "yes";
        }
        else
        if (!todoe_mobilea!!.text.toString().trim().equals(""))
        {
        alert_dialog_flag = "yes";
        }
        else
        if (!todoe_gsta!!.text.toString().trim().equals("") || !todoe_aadhara!!.text.toString().trim().equals("") || !todoe_pana!!.text.toString().trim().equals(""))
        {
        alert_dialog_flag = "yes";
        }
        else
        if (!todoe_state!!.selectedItem.toString().equals("Select State"))
        {
        alert_dialog_flag = "yes";
        }
        else
        if (!todoe_state!!.selectedItem.toString().equals("Select City"))
        {
        alert_dialog_flag = "yes";
        }
        else
        if (!todoe_geocordinatesa!!.text.toString().trim().equals(""))
        {
        alert_dialog_flag = "yes";
        }
        else
        if (!todoe_powerdealer!!.selectedItem.toString().equals("Select Power Dealer") || !todoe_iaqdealer!!.selectedItem.toString().equals("Select IAQ Dealer") || !todoe_lightingdealer!!.selectedItem.toString().equals("Select Lighting Dealer"))
        {
            alert_dialog_flag = "yes";
        }

        if(alert_dialog_flag.equals("yes"))
        {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Warning")
            builder.setMessage("Your enter data will be Lost")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                finish()
            }

            builder.setNeutralButton("Cancel"){dialogInterface , which ->
                dialogInterface.dismiss()
            }

            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else{
            finish()
        }

    }

    fun getCordinates()
    {
        var str: String = "";
        isInternetPresent = cd!!.isConnectingToInternet
        if (isInternetPresent) {
            try {
                val geo = Geocoder(context, Locale.getDefault())
                val addresses = geo.getFromLocationName(todoe_address.text.toString(),1)
                if (addresses.isEmpty()) { //yourtextfieldname.setText("Waiting for Location");

                } else {
                    if (addresses.size > 0) {

                        if (str.indexOf(addresses[0].locality) <= 0) {
                            str += "${String.format("%.4f", addresses[0].latitude)} , ${String.format("%.4f", addresses[0].longitude)}"
                        }

                        todoe_geocordinatesa!!.setText(str)
                    } else {
                        val toast = Toast.makeText(context,
                                "Gps Coordinates Not Found ", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        } else {

            val toast = Toast.makeText(context,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    fun RetailerData() {
        System.gc()
        val reason_code = ""
        try {
            piechart_rank_progressBarar.visibility = View.VISIBLE
            aad_bottom_layout.visibility = View.GONE
            add_container.isEnabled = false
            var domain = ""

            domain = resources.getString(R.string.service_domain)
            var jsObjRequest: JsonObjectRequest? = null
            try {
                Log.d("Server url", "Server url" + domain + "customer_service_media")
                val SURVEY = JSONArray()
                val product_value_n = JSONObject()

                product_value_n.put("email", Global_Data.GLOvel_USER_EMAIL)
                Log.d("customers Service", product_value_n.toString())
                jsObjRequest = JsonObjectRequest(Request.Method.POST, domain + "customer_service_media", product_value_n, Response.Listener { response ->
                    Log.i("volley", "response: $response")
                    Log.d("jV", "JV length" + response.length())
                    //JSONObject json = new JSONObject(new JSONTokener(response));
                    try {
                        var response_result = ""
                        response_result = if (response.has("result")) {
                            response.getString("result")
                        } else {
                            "data"
                        }
                        if (response_result.equals("success", ignoreCase = true)) {

                            piechart_rank_progressBarar.visibility = View.GONE
                            aad_bottom_layout.visibility = View.VISIBLE
                            add_container.isEnabled = true

                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            val a = Intent(context, MapsActivity::class.java)
                            startActivity(a)
                            finish()
                        }
                        else {
                            piechart_rank_progressBarar.visibility = View.GONE
                            aad_bottom_layout.visibility = View.VISIBLE
                            add_container.isEnabled = true
                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        piechart_rank_progressBarar.visibility = View.GONE
                        aad_bottom_layout.visibility = View.VISIBLE
                        add_container.isEnabled = true
                    }

                }, Response.ErrorListener { error ->
                    Log.i("volley", "error: $error")
                    val toast = Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    try {
                        val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val jsonObject = JSONObject(responseBody)
                    } catch (e: JSONException) {
                        //Handle a malformed json response
                    }
                    piechart_rank_progressBarar.visibility = View.GONE
                    aad_bottom_layout.visibility = View.VISIBLE
                    add_container.isEnabled = true
                })
                val requestQueue = Volley.newRequestQueue(context)
                val socketTimeout = 2000000 //90 seconds - change to what you want
                val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                jsObjRequest.retryPolicy = policy
                // requestQueue.se
                //requestQueue.add(jsObjRequest);
                jsObjRequest.setShouldCache(false)
                requestQueue.cache.clear()
                requestQueue.add(jsObjRequest)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                piechart_rank_progressBarar.visibility = View.GONE
                aad_bottom_layout.visibility = View.VISIBLE
                add_container.isEnabled = true
            }



        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            Log.e("DATA", e.message)
        }
    }

    fun validateAadharNumber(aadharNumber: String?): Boolean {
        val aadharPattern: Pattern = Pattern.compile("\\d{12}")
        var isValidAadhar: Boolean = aadharPattern.matcher(aadharNumber).matches()
        if (isValidAadhar) {
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber)
        }
        return isValidAadhar
    }

    fun validateGSTNumber(aadharNumber: String?): Boolean {
        val aadharPattern: Pattern = Pattern.compile("/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}\$/")
        var isValidateGST: Boolean = aadharPattern.matcher(aadharNumber).matches()

        return isValidateGST
    }

    fun validatePANNumber(aadharNumber: String?): Boolean {
        val panPattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        var isValidatePAN: Boolean = panPattern.matcher(aadharNumber).matches()

        return isValidatePAN
    }
}