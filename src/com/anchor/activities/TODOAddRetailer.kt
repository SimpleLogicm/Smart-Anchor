package com.anchor.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.os.AsyncTask
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
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
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

    var statespinnerMap = HashMap<String, String>()
    var cityspinnerMap = HashMap<String, String>()
    var powerspinnerMap = HashMap<String, String>()
    var iaqspinnerMap = HashMap<String, String>()
    var lightingspinnerMap = HashMap<String, String>()
    var final_response = ""
    var response_result = ""
    var state_id:String? = "";
    var city_id:String? = "";
    var dpower_id:String? = "";
    var diaq_id:String? = "";
    var dlighting_id:String? = "";
    var latitude:String? = "";
    var longitude:String? = "";

    val GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}"
    val GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"


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
        adapter_CState = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CState)

        adapter_CState!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_state.setAdapter(adapter_CState)
        todoe_state?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                list_CCity.clear()
                list_CCity.add("Select City")
                cityspinnerMap.clear()

                list_CPowerDealer.clear()
                list_CPowerDealer.add("Select Power Dealer")
                powerspinnerMap.clear()

                list_CIaqDealer.clear()
                list_CIaqDealer.add("Select IAQ Dealer")
                iaqspinnerMap.clear()

                list_CLightingDealer.clear()
                list_CLightingDealer.add("Select Lighting Dealer")
                lightingspinnerMap.clear()


                adapter_CCity = ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, list_CCity)
                adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_city.setAdapter(adapter_CCity)

                adapter_CPowerDealer = ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, list_CPowerDealer)
                adapter_CPowerDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_powerdealer.setAdapter(adapter_CPowerDealer)

                adapter_CIaqDealer = ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, list_CIaqDealer)
                adapter_CIaqDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_iaqdealer.setAdapter(adapter_CIaqDealer)

                adapter_CLightingDealer = ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, list_CLightingDealer)
                adapter_CLightingDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_lightingdealer.setAdapter(adapter_CLightingDealer)


                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select State", ignoreCase = true)) {

                    state_id = "";
                }
                else
                {
                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {

                        try {
                            state_id = statespinnerMap.get(parent!!.getItemAtPosition(position).toString())
                            state_id?.let { citydealer_details(it) }
                        }catch (e:Exception)
                        {
                            state_id = "";
                            e.printStackTrace()
                        }

                    }
                    else {

                        val toast = Toast.makeText(context,
                                "Internet Not Available. ", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            }
        }


        list_CCity.add("Select City")
        adapter_CCity = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CCity)

        adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_city.setAdapter(adapter_CCity)
        todoe_city?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

               // Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select City", ignoreCase = true)) {
                    city_id = "";
                }
                else
                {
                    try {
                        city_id = cityspinnerMap.get(parent!!.getItemAtPosition(position).toString())

                    }catch (e:Exception)
                    {
                        city_id = "";
                        e.printStackTrace()
                    }
                }

            }

        }

        list_CPowerDealer.add("Select Power Dealer")
        adapter_CPowerDealer = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CPowerDealer)

        adapter_CPowerDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_powerdealer.setAdapter(adapter_CPowerDealer)
        todoe_powerdealer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

               // Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select Power Dealer", ignoreCase = true)) {
                    dpower_id = "";
                }
                else
                {
                    try {
                        dpower_id = powerspinnerMap.get(parent!!.getItemAtPosition(position).toString())

                    }catch (e:Exception)
                    {
                        dpower_id = "";
                        e.printStackTrace()
                    }
                }

            }
        }

        list_CIaqDealer.add("Select IAQ Dealer")
        adapter_CIaqDealer = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CIaqDealer)

        adapter_CIaqDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_iaqdealer.setAdapter(adapter_CIaqDealer)
        todoe_iaqdealer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

               // Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select IAQ Dealer", ignoreCase = true)) {

                    diaq_id = "";
                }
                else
                {
                    try {
                        diaq_id = iaqspinnerMap.get(parent!!.getItemAtPosition(position).toString())

                    }catch (e:Exception)
                    {
                        diaq_id = "";
                        e.printStackTrace()
                    }
                }

            }
        }

        list_CLightingDealer.add("Select Lighting Dealer")
        adapter_CLightingDealer = ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, list_CLightingDealer)

        adapter_CLightingDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_lightingdealer.setAdapter(adapter_CLightingDealer)
        todoe_lightingdealer?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

               // Toast.makeText(context,parent!!.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select Lighting Dealer", ignoreCase = true)) {
                    dlighting_id = "";
                }
                else
                {
                    try {
                        dlighting_id = powerspinnerMap.get(parent!!.getItemAtPosition(position).toString())

                    }catch (e:Exception)
                    {
                        dlighting_id = "";
                        e.printStackTrace()
                    }
                }

            }
        }

        isInternetPresent = cd!!.isConnectingToInternet
        if (isInternetPresent) {
            states_details()
        }
        else {

            val toast = Toast.makeText(context,
                    "Internet Not Available. ", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
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

                            try {
                                latitude =String.format("%.4f", addresses[0].latitude)
                                longitude = String.format("%.4f", addresses[0].longitude)
                                str += "${String.format("%.4f", addresses[0].latitude)} , ${String.format("%.4f", addresses[0].longitude)}"
                            }catch (e:Exception)
                            {
                                e.printStackTrace()
                            }

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
            var domain = ""
            var url = ""
            domain = resources.getString(R.string.service_domain)
            var jsObjRequest: JsonObjectRequest? = null
            try {
                url = domain + "retailers/create_retailers"
                Log.d("Server url", "Server url" + url)
                val SURVEY = JSONArray()
                val product_value_n = JSONObject()
                val retailer_object = JSONObject()

                product_value_n.put("email", user_email)
                product_value_n.put("state_code", state_id)
                product_value_n.put("city_code", city_id)
                product_value_n.put("power_dealer", dpower_id)
                product_value_n.put("iaq_dealer", diaq_id)
                product_value_n.put("lighting_dealer", dlighting_id)
                product_value_n.put("shop_name", todoe_shop_namea.text.toString())
                product_value_n.put("mobile_no", todoe_mobilea.text.toString())
                product_value_n.put("gst_no", todoe_gsta.text.toString())
                product_value_n.put("aadhar_no", todoe_aadhara.text.toString())
                product_value_n.put("pan_no", todoe_pana.text.toString())
                product_value_n.put("address", todoe_address.text.toString())
                product_value_n.put("latitude", latitude)
                product_value_n.put("longitude", longitude)

                retailer_object.put("retailer",product_value_n)


                Log.d("Retailer save Service", retailer_object.toString())
                jsObjRequest = JsonObjectRequest(Request.Method.POST,url , retailer_object, Response.Listener { response ->
                    Log.i("volley", "response: $response")
                    Log.d("jV", "JV length" + response.length())
                    //JSONObject json = new JSONObject(new JSONTokener(response));
                    try {
                        var response_result = ""
                        response_result = if (response.has("message")) {
                            response.getString("message")
                        } else {
                            "data"
                        }
                        if (response_result.equals("Retailer created successfully.", ignoreCase = true)) {

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

    fun validateGSTNumber(gstNumber: String?): Boolean {
        val aadharPattern: Pattern = Pattern.compile("/^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}\$/")
        var isValidateGST: Boolean = aadharPattern.matcher(gstNumber).matches()

        if (validGSTIN(gstNumber!!))
            return  true
        else
            return false
       // return isValidateGST
    }

    fun validatePANNumber(panNumber: String?): Boolean {
        val panPattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        var isValidatePAN: Boolean = panPattern.matcher(panNumber).matches()


        return isValidatePAN
    }

    fun states_details() {
        citys_loader.visibility = View.VISIBLE
        val domain = resources.getString(R.string.service_domain)
        Log.i("volley", "domain: $domain")
        var url = domain+"retailers/get_all_states"
        Log.i("user list url", "user list url " +url)
        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            getAllSates().execute(response)
        },
                Response.ErrorListener { error ->
                    //dialog.dismiss()

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
                    citys_loader.visibility = View.GONE

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

    public inner class getAllSates : AsyncTask<String?, Void?, String>() {
         override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")

                    runOnUiThread(Runnable {
                        citys_loader.visibility = View.GONE
                        //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                        val toast = Toast.makeText(context, "States doesn't exist", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                }
                else {
                        val states: JSONArray = response.getJSONArray("states")
                        Log.i("volley", "response states Length: " + states.length())
                        Log.d("volley", "states$states")
                        list_CState.clear()
                        list_CState.add("Select State")
                        statespinnerMap.clear()

                        for (i in 0 until states.length()) {
                            val jsonObject = states.getJSONObject(i)
                            try {
                                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(states.getString(i))) {
                                    run {
                                        list_CState.add(jsonObject.getString("name"))
                                        statespinnerMap.put(jsonObject.getString("name"),jsonObject.getString("code"))
                                    }
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        }

                       runOnUiThread(Runnable {

                           adapter_CState = ArrayAdapter<String>(context,
                                   android.R.layout.simple_spinner_item, list_CState)

                           adapter_CState!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                           todoe_state.setAdapter(adapter_CState)
                           // dialog.dismiss()
                        })
                       runOnUiThread(Runnable {
                           citys_loader.visibility = View.GONE
                       })

                    }


                    // }

                    // output.setText(data);

            } catch (e: JSONException) {
                e.printStackTrace()
               runOnUiThread(Runnable {
                   citys_loader.visibility = View.GONE
               })
            }
           runOnUiThread(Runnable {
               citys_loader.visibility = View.GONE
           })
            return "Executed"
        }

        override fun onPostExecute(result: String) {
           runOnUiThread(Runnable {
               citys_loader.visibility = View.GONE
           })
        }

        override fun onPreExecute() {}

    }

    fun citydealer_details(state_id:String) {
        citys_loader.visibility = View.VISIBLE
        val domain = resources.getString(R.string.service_domain)
        Log.i("volley", "domain: $domain")
        var url = domain+"retailers/get_statewise_cities?state_code="+state_id
        Log.i("user list url", "user list url " +url)
        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            getstatewise_CityDealer().execute(response)
        },
                Response.ErrorListener { error ->
                    //dialog.dismiss()

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
                    citys_loader.visibility = View.GONE

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

    public inner class getstatewise_CityDealer : AsyncTask<String?, Void?, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")

                    runOnUiThread(Runnable {
                        citys_loader.visibility = View.GONE
                        //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                        val toast = Toast.makeText(context, "States doesn't exist", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                }
                else {
                    val cities: JSONArray = response.getJSONArray("cities")
                    Log.i("volley", "response cities Length: " + cities.length())
                    Log.d("volley", "cities$cities")


                    val p_dealer: JSONArray = response.getJSONArray("power_dealers")
                    Log.i("volley", "response p_dealer Length: " + cities.length())
                    Log.d("volley", "p_dealer$cities")

                    val i_dealer: JSONArray = response.getJSONArray("iaq_dealers")
                    Log.i("volley", "response i_dealer Length: " + cities.length())
                    Log.d("volley", "i_dealer$cities")

                    val l_delaer: JSONArray = response.getJSONArray("lighting_dealers")
                    Log.i("volley", "response l_delaer Length: " + l_delaer.length())
                    Log.d("volley", "l_delaer$cities")


                    list_CCity.clear()
                    list_CCity.add("Select City")
                    cityspinnerMap.clear()

                    list_CPowerDealer.clear()
                    list_CPowerDealer.add("Select Power Dealer")
                    powerspinnerMap.clear()

                    list_CIaqDealer.clear()
                    list_CIaqDealer.add("Select IAQ Dealer")
                    iaqspinnerMap.clear()

                    list_CLightingDealer.clear()
                    list_CLightingDealer.add("Select Lighting Dealer")
                    lightingspinnerMap.clear()

                    for (i in 0 until cities.length()) {
                        val jsonObject = cities.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cities.getString(i))) {
                                run {
                                    list_CCity.add(jsonObject.getString("name"))
                                    cityspinnerMap.put(jsonObject.getString("name"),jsonObject.getString("code"))
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    for (i in 0 until p_dealer.length()) {
                        val jsonObject = p_dealer.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(p_dealer.getString(i))) {
                                run {
                                    list_CPowerDealer.add(jsonObject.getString("shop_name"))
                                    powerspinnerMap.put(jsonObject.getString("shop_name"),jsonObject.getString("code"))
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    for (i in 0 until i_dealer.length()) {
                        val jsonObject = i_dealer.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(i_dealer.getString(i))) {
                                run {
                                    list_CIaqDealer.add(jsonObject.getString("shop_name"))
                                    iaqspinnerMap.put(jsonObject.getString("shop_name"),jsonObject.getString("code"))
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    for (i in 0 until l_delaer.length()) {
                        val jsonObject = l_delaer.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(l_delaer.getString(i))) {
                                run {
                                    list_CLightingDealer.add(jsonObject.getString("shop_name"))
                                    lightingspinnerMap.put(jsonObject.getString("shop_name"),jsonObject.getString("code"))
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    runOnUiThread(Runnable {

                        adapter_CCity = ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, list_CCity)
                        adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_city.setAdapter(adapter_CCity)

                        adapter_CPowerDealer = ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, list_CPowerDealer)
                        adapter_CPowerDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_powerdealer.setAdapter(adapter_CPowerDealer)

                        adapter_CIaqDealer = ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, list_CIaqDealer)
                        adapter_CIaqDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_iaqdealer.setAdapter(adapter_CIaqDealer)

                        adapter_CLightingDealer = ArrayAdapter<String>(context,
                                android.R.layout.simple_spinner_item, list_CLightingDealer)
                        adapter_CLightingDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_lightingdealer.setAdapter(adapter_CLightingDealer)


                    })
                    runOnUiThread(Runnable {
                        citys_loader.visibility = View.GONE
                    })

                }




            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread(Runnable {
                    citys_loader.visibility = View.GONE
                })
            }
            runOnUiThread(Runnable {
                citys_loader.visibility = View.GONE
            })
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread(Runnable {
                citys_loader.visibility = View.GONE
            })
        }

        override fun onPreExecute() {}

    }

    @Throws(java.lang.Exception::class)
    private fun validGSTIN(gstin: String): Boolean {
        var isValidFormat = false
        if (checkPattern(gstin, GSTINFORMAT_REGEX)) {
            isValidFormat = verifyCheckDigit(gstin)
        }
        return isValidFormat
    }

    @Throws(java.lang.Exception::class)
    private fun verifyCheckDigit(gstinWCheckDigit: String): Boolean {
        var isCDValid = false
        val newGstninWCheckDigit: String? = getGSTINWithCheckDigit(
                gstinWCheckDigit.substring(0, gstinWCheckDigit.length - 1))
        if (gstinWCheckDigit.trim { it <= ' ' } == newGstninWCheckDigit) {
            isCDValid = true
        }
        return isCDValid
    }

    fun checkPattern(inputval: String, regxpatrn: String?): Boolean {
        var result = false
        if (inputval.trim { it <= ' ' }.matches(regxpatrn!!.toRegex())) {
            result = true
        }
        return result
    }



    /**
     * Method to get the check digit for the gstin (without checkdigit)
     *
     * @param gstinWOCheckDigit
     * @return : GSTIN with check digit
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    fun getGSTINWithCheckDigit(gstinWOCheckDigit: String?): String? {
        var factor = 2
        var sum = 0
        var checkCodePoint = 0
        var cpChars: CharArray?
        var inputChars: CharArray?
        return try {
            if (gstinWOCheckDigit == null) {
                throw java.lang.Exception("GSTIN supplied for checkdigit calculation is null")
            }
            cpChars = GSTN_CODEPOINT_CHARS.toCharArray()
            inputChars = gstinWOCheckDigit.trim { it <= ' ' }.toUpperCase().toCharArray()
            val mod = cpChars.size
            for (i in inputChars.indices.reversed()) {
                var codePoint = -1
                for (j in cpChars.indices) {
                    if (cpChars[j] == inputChars[i]) {
                        codePoint = j
                    }
                }
                var digit = factor * codePoint
                factor = if (factor == 2) 1 else 2
                digit = digit / mod + digit % mod
                sum += digit
            }
            checkCodePoint = (mod - sum % mod) % mod
            gstinWOCheckDigit + cpChars[checkCodePoint]
        } finally {
            inputChars = null
            cpChars = null
        }
    }

}