package com.anchor.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.anchor.adapter.Todo_list_adaptor
import com.anchor.helper.VerhoeffAlgorithm
import com.anchor.model.Todo_model
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_todo_editcustomer.*
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_address
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_city
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_dealer_container
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_iaqdealer
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_lightingdealer
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_powerdealer
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_state
import kotlinx.android.synthetic.main.activity_todo_editcustomer.todoe_tsi_code
import kotlinx.android.synthetic.main.custom_dialog_todolist.*
import kotlinx.android.synthetic.main.reatilertdcustomerlist.*
import kotlinx.android.synthetic.main.todoadd_retailer.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.regex.Pattern


class TodoEditCustomer : Activity() {
    var list: ArrayList<Todo_model>? = null
    var adaptor: Todo_list_adaptor? = null
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

    var statespinnerMapReverse = HashMap<String, String>()
    var cityspinnerMapReverse = HashMap<String, String>()
    var powerspinnerMapReverse = HashMap<String, String>()
    var iaqspinnerMapReverse = HashMap<String, String>()
    var lightingspinnerMapReverse = HashMap<String, String>()

    var final_response = ""
    var response_result = ""
    var state_id:String? = "";
    var city_id:String? = "";
    var dpower_id:String? = "";
    var diaq_id:String? = "";
    var dlighting_id:String? = "";
    var latitude:String? = "";
    var longitude:String? = "";

    var code = "";
    var shop_name = "";
    var address = "";
    var state_code = "";
    var city_code = "";
    var mobile_no = "";
    var email = "";
    var gst_no = "";
    var aadhar_no = "";
    var pan_no = "";
    var latitudes = "";
    var longitudes = "";
    var power_dealer = "";
    var lighting_dealer = "";
    var iaq_dealer = "";
    var source_of_data = "";
    var tsi_code = "";

    val GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}"
    val GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_editcustomer)

        context = RetailerTDCustomerList@this
        cd = ConnectionDetector(context)

        list = ArrayList<Todo_model>()



        try {
            code = intent.getStringExtra("code")
            shop_name = intent.getStringExtra("shop_name")
            address = intent.getStringExtra("address")
            state_code = intent.getStringExtra("state_code")
            city_code = intent.getStringExtra("city_code")
            mobile_no = intent.getStringExtra("mobile_no")
            email = intent.getStringExtra("email")
            gst_no = intent.getStringExtra("gst_no")
            aadhar_no = intent.getStringExtra("aadhar_no")
            pan_no = intent.getStringExtra("pan_no")
            latitudes = intent.getStringExtra("latitude")
            longitudes = intent.getStringExtra("longitude")
            power_dealer = intent.getStringExtra("power_dealer")
            lighting_dealer = intent.getStringExtra("lighting_dealer")
            iaq_dealer = intent.getStringExtra("iaq_dealer")
            source_of_data = intent.getStringExtra("source_of_data")
            tsi_code = intent.getStringExtra("tsi_code")
            coardcolor = intent.getStringExtra("cardcolor")

            todoe_shop_name.setText(shop_name.trim())
            todoe_mobile.setText(mobile_no.trim())
            todoe_gst.setText(gst_no.trim())
            todoe_aadhar.setText(aadhar_no.trim())
            todoe_pan.setText(pan_no.trim())
            todoe_address.setText(address.trim())
            todoe_geocordinates.setText(latitudes.trim()+","+longitudes.trim())
            todoe_tsi_code.setText(tsi_code.trim())


            if(coardcolor.equals("#D8AB1E"))
            {
                todoe_shop_name.setBackgroundResource(R.drawable.todo_back)
                todoe_mobile.setBackgroundResource(R.drawable.todo_back)
                todoe_gst.setBackgroundResource(R.drawable.todo_back)
                todoe_aadhar.setBackgroundResource(R.drawable.todo_back)
                todoe_pan.setBackgroundResource(R.drawable.todo_back)
                todoe_dealer_container.setBackgroundResource(R.drawable.todo_back)
                todoe_address.setBackgroundResource(R.drawable.todo_back)

                todoe_state.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_city.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_yellow)

                todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_yellow)
                todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_yellow)
            }
            else
            if(coardcolor.equals("#BB2B20"))
            {
                todoe_shop_name.setBackgroundResource(R.drawable.todored)
                todoe_mobile.setBackgroundResource(R.drawable.todored)
                todoe_gst.setBackgroundResource(R.drawable.todored)
                todoe_aadhar.setBackgroundResource(R.drawable.todored)
                todoe_pan.setBackgroundResource(R.drawable.todored)
                todoe_dealer_container.setBackgroundResource(R.drawable.todored)
                todoe_address.setBackgroundResource(R.drawable.todored)

                todoe_state.setBackgroundResource(R.drawable.spinner_background_red)
                todoe_city.setBackgroundResource(R.drawable.spinner_background_red)
                todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_red)
                todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_red)
                todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_red)

                todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_red)
                todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_red)


            }
            else
            if(coardcolor.equals("#3A921A"))
            {
                todoe_shop_name.setBackgroundResource(R.drawable.todogreen)
                todoe_mobile.setBackgroundResource(R.drawable.todogreen)
                todoe_gst.setBackgroundResource(R.drawable.todogreen)
                todoe_aadhar.setBackgroundResource(R.drawable.todogreen)
                todoe_pan.setBackgroundResource(R.drawable.todogreen)
                todoe_dealer_container.setBackgroundResource(R.drawable.todogreen)
                todoe_address.setBackgroundResource(R.drawable.todogreen)

                todoe_state.setBackgroundResource(R.drawable.spinner_background_green)
                todoe_city.setBackgroundResource(R.drawable.spinner_background_green)
                todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_green)
                todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_green)
                todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_green)

                todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_green)
                todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_green)
            }
            else
            if(coardcolor.equals("#26600B"))
            {


                todoe_shop_name.setBackgroundResource(R.drawable.todarkgreen)
                todoe_mobile.setBackgroundResource(R.drawable.todarkgreen)
                todoe_gst.setBackgroundResource(R.drawable.todarkgreen)
                todoe_aadhar.setBackgroundResource(R.drawable.todarkgreen)
                todoe_pan.setBackgroundResource(R.drawable.todarkgreen)
                todoe_dealer_container.setBackgroundResource(R.drawable.todarkgreen)
                todoe_address.setBackgroundResource(R.drawable.todarkgreen)

                todoe_state.setBackgroundResource(R.drawable.spinner_background_darkgreen)
                todoe_city.setBackgroundResource(R.drawable.spinner_background_darkgreen)
                todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_darkgreen)
                todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_darkgreen)
                todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_darkgreen)

                todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_darkgreen)
                todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_darkgreen)
            }
        }catch (e:Exception)
        {
            e.printStackTrace()
        }

        locationimg.setOnClickListener {
            val dialog = Dialog(this@TodoEditCustomer)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_todolist)
            dialog.show()

            val yesBtn = dialog.findViewById(R.id.buttonnoOrderSave) as Button
            val noBtn = dialog.findViewById(R.id.buttonnoOrdercancel) as Button
            var rv=dialog.findViewById(R.id.rv) as RecyclerView
            rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)


            list!!.add(Todo_model("Abc fksdjfksjdfksh kjhsfhkshfsdfffdffsf,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc dsdsgdsgdsgsg,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))
            list!!.add(Todo_model("Abc,67786677"))

            adaptor = Todo_list_adaptor(this, list!!)
            rv.adapter = adaptor
            noBtn.setOnClickListener {
                dialog.dismiss()
            }

            yesBtn.setOnClickListener {
                Toast.makeText(this@TodoEditCustomer,"submit",Toast.LENGTH_SHORT).show()
            }
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

                todoe_geocordinates.setText("")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


            }
        })

        todoe_geocordinates.setOnClickListener {
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

        locationimg.setOnClickListener {

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

        buttontodocecancel.setOnClickListener {
            onBackPressed()

        }

        buttontodoceSave.setOnClickListener {

            if (todoe_shop_name!!.text.toString().trim().equals(""))
            {
                val toast = Toast.makeText(context,
                        "Please Enter Shop Name", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
            else
                if (todoe_mobile!!.text.toString().trim().equals("") || todoe_mobile!!.text.length < 10 || todoe_mobile!!.text.startsWith("0"))
                {
                    val toast = Toast.makeText(context,
                    "Please Enter Valid Mobile No", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else
                if (todoe_gst!!.text.toString().trim().equals("") && todoe_aadhar!!.text.toString().trim().equals("") && todoe_pan!!.text.toString().trim().equals(""))
                {
                    val toast = Toast.makeText(context,
                    "Please Enter GST No. or  Aadhar No. or Pan No.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else
                if (!todoe_gst!!.text.toString().trim().equals("") && !validateGSTNumber(todoe_gst!!.text.toString().trim()))
                {
                    val toast = Toast.makeText(context,
                    "Please Enter Valid GST No.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else
                if (!todoe_aadhar!!.text.toString().trim().equals("") && !validateAadharNumber(todoe_aadhar!!.text.toString().trim()))
                {
                    val toast = Toast.makeText(context,
                    "Please Enter Valid Aadhar No.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else
                if (!todoe_pan!!.text.toString().trim().equals("") && !validatePANNumber(todoe_pan!!.text.toString().trim()))
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
                if (todoe_city!!.selectedItem.toString().equals("Select City"))
                {
                    val toast = Toast.makeText(context,
                    "Please Select City.", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                }
                else
                if (todoe_geocordinates!!.text.toString().trim().equals(""))
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
                        RetailerUpdateData()
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

    override fun onBackPressed() {
        //super.onBackPressed()
        var alert_dialog_flag = ""

        if (!todoe_shop_name!!.text.toString().trim().equals(shop_name) && !todoe_shop_name!!.text.toString().trim().equals(""))
        {
            alert_dialog_flag = "yes";
        }
        else
        if (!todoe_mobile!!.text.toString().trim().equals(mobile_no) && !todoe_mobile!!.text.toString().trim().equals(""))
        {
            alert_dialog_flag = "yes";
        }
        else
        if ((!todoe_gst!!.text.toString().trim().equals(gst_no) &&!todoe_gst!!.text.toString().trim().equals("")) || (!todoe_aadhar!!.text.toString().trim().equals(aadhar_no) && !todoe_aadhar!!.text.toString().trim().equals("")) || (!todoe_pan!!.text.toString().trim().equals(pan_no) &&!todoe_pan!!.text.toString().trim().equals("")))
        {
          alert_dialog_flag = "yes";
        }
        else
        if (!state_id.equals(""))
        {
          alert_dialog_flag = "yes";
        }
        else
        if (!city_id.equals(""))
        {
          alert_dialog_flag = "yes";
        }
        else
        if (!latitude.toString().trim().equals("") || !longitudes.toString().trim().equals(""))
        {
          alert_dialog_flag = "yes";
        }
        else
        if (!dpower_id.equals("") || !diaq_id.toString().equals("") || !dlighting_id.equals(""))
        {
            alert_dialog_flag = "yes";
        }

        if(alert_dialog_flag.equals("yes"))
        {
            val alertDialog = AlertDialog.Builder(context).create() //Read Update

            alertDialog.setTitle("Confirmation")
            alertDialog.setMessage(" Are you sure you want to Cancel?")
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes") { dialog, which -> // TODO Auto-generated method stub
                finish()
            }

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No") { dialog, which -> // TODO Auto-generated method stub
                dialog.cancel()
            }

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

    fun states_details() {
        citys_loaderedit.visibility = View.VISIBLE

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
        var url = domain+"retailers/get_all_states?email="+user_email
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
                    citys_loaderedit.visibility = View.GONE

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
                        citys_loaderedit.visibility = View.GONE
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
                    statespinnerMapReverse.clear()

                    for (i in 0 until states.length()) {
                        val jsonObject = states.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(states.getString(i))) {
                                run {
                                    list_CState.add(jsonObject.getString("name"))
                                    statespinnerMap.put(jsonObject.getString("name"),jsonObject.getString("code"))
                                    statespinnerMapReverse.put(jsonObject.getString("code"),jsonObject.getString("name"))

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


                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(state_code)) {
                                var s_name = statespinnerMapReverse.get(state_code)
                                todoe_state.setSelection(adapter_CState!!.getPosition(s_name))

                                state_code = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    })
                    runOnUiThread(Runnable {
                        citys_loaderedit.visibility = View.GONE
                    })

                }
            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread(Runnable {
                    citys_loaderedit.visibility = View.GONE
                })
            }
            runOnUiThread(Runnable {
                citys_loaderedit.visibility = View.GONE
            })
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread(Runnable {
                citys_loaderedit.visibility = View.GONE
            })
        }

        override fun onPreExecute() {}

    }

    fun citydealer_details(state_id:String) {
        citys_loaderedit.visibility = View.VISIBLE
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
        var url = domain+"retailers/get_statewise_cities?state_code="+state_id+"&email="+user_email
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
                    citys_loaderedit.visibility = View.GONE

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
                        citys_loaderedit.visibility = View.GONE
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
                    cityspinnerMapReverse.clear()

                    list_CPowerDealer.clear()
                    list_CPowerDealer.add("Select Power Dealer")
                    powerspinnerMap.clear()
                    powerspinnerMapReverse.clear()

                    list_CIaqDealer.clear()
                    list_CIaqDealer.add("Select IAQ Dealer")
                    iaqspinnerMap.clear()
                    iaqspinnerMapReverse.clear()

                    list_CLightingDealer.clear()
                    list_CLightingDealer.add("Select Lighting Dealer")
                    lightingspinnerMap.clear()
                    lightingspinnerMapReverse.clear()

                    for (i in 0 until cities.length()) {
                        val jsonObject = cities.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cities.getString(i))) {
                                run {
                                    list_CCity.add(jsonObject.getString("name"))
                                    cityspinnerMap.put(jsonObject.getString("name"),jsonObject.getString("code"))
                                    cityspinnerMapReverse.put(jsonObject.getString("code"),jsonObject.getString("name"))

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
                                    powerspinnerMapReverse.put(jsonObject.getString("code"),jsonObject.getString("shop_name"))

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
                                    iaqspinnerMapReverse.put(jsonObject.getString("code"),jsonObject.getString("shop_name"))
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
                                    lightingspinnerMapReverse.put(jsonObject.getString("code"),jsonObject.getString("shop_name"))

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

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(city_code)) {
                                var c_name = cityspinnerMapReverse.get(city_code)
                                todoe_city.setSelection(adapter_CCity!!.getPosition(c_name))

                                city_code = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(power_dealer)) {
                                var p_name = powerspinnerMapReverse.get(power_dealer)
                                todoe_powerdealer.setSelection(adapter_CPowerDealer!!.getPosition(p_name))

                                power_dealer = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(iaq_dealer)) {
                                var iaq_name = iaqspinnerMapReverse.get(iaq_dealer)
                                todoe_iaqdealer.setSelection(adapter_CIaqDealer!!.getPosition(iaq_name))

                                iaq_dealer = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(lighting_dealer)) {
                                var l_name = lightingspinnerMapReverse.get(lighting_dealer)
                                todoe_lightingdealer.setSelection(adapter_CLightingDealer!!.getPosition(l_name))

                                lighting_dealer = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }


                    })
                    runOnUiThread(Runnable {
                        citys_loaderedit.visibility = View.GONE
                    })

                }




            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread(Runnable {
                    citys_loaderedit.visibility = View.GONE
                })
            }
            runOnUiThread(Runnable {
                citys_loaderedit.visibility = View.GONE
            })
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread(Runnable {
                citys_loaderedit.visibility = View.GONE
            })
        }

        override fun onPreExecute() {}

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
        val aadharPattern: Pattern = Pattern.compile("^([0]{1}[1-9]{1}|[1-2]{1}[0-9]{1}|[3]{1}[0-7]{1})([a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9a-zA-Z]{1}[zZ]{1}[0-9a-zA-Z]{1})+\$")
        var isValidateGST: Boolean = aadharPattern.matcher(gstNumber).matches()

//        if (validGSTIN(gstNumber!!))
//            return  true
//        else
//            return false
        return isValidateGST
    }

    fun validatePANNumber(panNumber: String?): Boolean {
        val panPattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")
        var isValidatePAN: Boolean = panPattern.matcher(panNumber).matches()


        return isValidatePAN
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

    fun RetailerUpdateData() {
        System.gc()
        val reason_code = ""
        try {
            piechart_rank_progressBararredit.visibility = View.VISIBLE
            aad_bottom_layoutredit.visibility = View.GONE
            add_containerredit.isEnabled = false

            try {
                if(!todoe_state.selectedItem.toString().equals("Select State"))
                {
                    state_id = statespinnerMap.get(todoe_state.selectedItem.toString())

                }
                else
                {
                    state_id = ""
                }

            }catch (e:Exception)
            {
                state_id = "";
                e.printStackTrace()
            }

            try {
                if(!todoe_city.selectedItem.toString().equals("Select City"))
                {
                    city_id = cityspinnerMap.get(todoe_city.selectedItem.toString())

                }
                else
                {
                    city_id = ""
                }

            }catch (e:Exception)
            {
                city_id = "";
                e.printStackTrace()
            }

            try {
                if(!todoe_powerdealer.selectedItem.toString().equals("Select Power Dealer"))
                {
                    dpower_id = powerspinnerMap.get(todoe_powerdealer.selectedItem.toString())

                }
                else
                {
                    dpower_id = ""
                }

            }catch (e:Exception)
            {
                dpower_id = "";
                e.printStackTrace()
            }

            try {
                if(!todoe_iaqdealer.selectedItem.toString().equals("Select IAQ Dealer"))
                {
                    diaq_id = iaqspinnerMap.get(todoe_iaqdealer.selectedItem.toString())

                }
                else
                {
                    diaq_id = ""
                }

            }catch (e:Exception)
            {
                diaq_id = "";
                e.printStackTrace()
            }

            try {
                if(!todoe_lightingdealer.selectedItem.toString().equals("Select Lighting Dealer"))
                {
                    dlighting_id = lightingspinnerMap.get(todoe_lightingdealer.selectedItem.toString())

                }
                else
                {
                    dlighting_id = ""
                }

            }catch (e:Exception)
            {
                dlighting_id = "";
                e.printStackTrace()
            }


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

                //product_value_n.put("email", user_email)
                product_value_n.put("state_code", state_id)
                product_value_n.put("city_code", city_id)
                product_value_n.put("power_dealer", dpower_id)
                product_value_n.put("iaq_dealer", diaq_id)
                product_value_n.put("lighting_dealer", dlighting_id)
                product_value_n.put("shop_name", todoe_shop_name.text.toString())
                product_value_n.put("mobile_no", todoe_mobile.text.toString())
                product_value_n.put("gst_no", todoe_gst.text.toString())
                product_value_n.put("aadhar_no", todoe_aadhar.text.toString())
                product_value_n.put("pan_no", todoe_pan.text.toString())
                product_value_n.put("address", todoe_address.text.toString())
                product_value_n.put("latitude", latitude)
                product_value_n.put("longitude", longitude)
                retailer_object.put("retailer",product_value_n)
                retailer_object.put("email",user_email)


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

                            piechart_rank_progressBararredit.visibility = View.GONE
                            aad_bottom_layoutredit.visibility = View.VISIBLE
                            add_containerredit.isEnabled = true

                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            val a = Intent(context, MapsActivity::class.java)
                            startActivity(a)
                            finish()
                        }
                        else
                            if (response_result.equals("Retailer Duplicate record found.", ignoreCase = true)) {

                                piechart_rank_progressBararredit.visibility = View.GONE
                                aad_bottom_layoutredit.visibility = View.VISIBLE
                                add_containerredit.isEnabled = true

                                var retailer_code = response.getString("retailer_code")

                                val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()

                                val alertDialog = AlertDialog.Builder(context).create() //Read Update

                                alertDialog.setTitle("Confirmation")
                                alertDialog.setMessage("If you want to merge, Please click on yes button Or if you want to delete, Please click on no button  ")
                                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes") { dialog, which -> // TODO Auto-generated method stub
                                    dialog.cancel()
                                    RetailerDuplicateData(retailer_code,"Yes")
                                }

                                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No") { dialog, which -> // TODO Auto-generated method stub
                                    dialog.cancel()
                                    RetailerDuplicateData(retailer_code,"No")
                                }

                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                            else {
                                piechart_rank_progressBararredit.visibility = View.GONE
                                aad_bottom_layoutredit.visibility = View.VISIBLE
                                add_containerredit.isEnabled = true
                                val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
                                toast.setGravity(Gravity.CENTER, 0, 0)
                                toast.show()

                            }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        piechart_rank_progressBararredit.visibility = View.GONE
                        aad_bottom_layoutredit.visibility = View.VISIBLE
                        add_containerredit.isEnabled = true
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
                    piechart_rank_progressBararredit.visibility = View.GONE
                    aad_bottom_layoutredit.visibility = View.VISIBLE
                    add_containerredit.isEnabled = true
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
                piechart_rank_progressBararredit.visibility = View.GONE
                aad_bottom_layoutredit.visibility = View.VISIBLE
                add_containerredit.isEnabled = true
            }



        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            Log.e("DATA", e.message)
        }
    }

    fun RetailerDuplicateData(retailer_code:String,flag:String) {
        System.gc()
        val reason_code = ""
        try {
            piechart_rank_progressBararredit.visibility = View.VISIBLE
            aad_bottom_layoutredit.visibility = View.GONE
            add_containerredit.isEnabled = false

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
                url = domain + "retailers/merge_duplicate_retailer"
                Log.d("Server url", "Server url" + url)
                val SURVEY = JSONArray()
                val product_value_n = JSONObject()
                val retailer_object = JSONObject()


                retailer_object.put("retailer_code", retailer_code)
                retailer_object.put("flag", flag.toLowerCase())
                retailer_object.put("email",user_email)

                Log.d("Retailer merge Service", retailer_object.toString())
                jsObjRequest = JsonObjectRequest(Request.Method.POST,url , retailer_object, Response.Listener { response ->
                    Log.i("volley", "response: $response")
                    Log.d("jV", "JV length" + response.length())
                    //JSONObject json = new JSONObject(new JSONTokener(response));
                    try {
                        var response_result = ""
                        if (response.has("message")) {
                            response_result = response.getString("message")

                            piechart_rank_progressBararredit.visibility = View.GONE
                            aad_bottom_layoutredit.visibility = View.VISIBLE
                            add_containerredit.isEnabled = true
                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            finish()
                        }
//                        if (response_result.equals("Retailer created successfully.", ignoreCase = true)) {
//
//                            piechart_rank_progressBararredit.visibility = View.GONE
//                            aad_bottom_layoutredit.visibility = View.VISIBLE
//                            add_containerredit.isEnabled = true
//
//                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
//                            toast.setGravity(Gravity.CENTER, 0, 0)
//                            toast.show()
//                            val a = Intent(context, MapsActivity::class.java)
//                            startActivity(a)
//                            finish()
//                        }
//
//                        else {
//                            piechart_rank_progressBararredit.visibility = View.GONE
//                            aad_bottom_layoutredit.visibility = View.VISIBLE
//                            add_containerredit.isEnabled = true
//                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
//                            toast.setGravity(Gravity.CENTER, 0, 0)
//                            toast.show()
//
//                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        piechart_rank_progressBararredit.visibility = View.GONE
                        aad_bottom_layoutredit.visibility = View.VISIBLE
                        add_containerredit.isEnabled = true
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
                    piechart_rank_progressBararredit.visibility = View.GONE
                    aad_bottom_layoutredit.visibility = View.VISIBLE
                    add_containerredit.isEnabled = true
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
                piechart_rank_progressBararredit.visibility = View.GONE
                aad_bottom_layoutredit.visibility = View.VISIBLE
                add_containerredit.isEnabled = true
            }



        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            Log.e("DATA", e.message)
        }
    }
}