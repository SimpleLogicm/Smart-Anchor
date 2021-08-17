package com.anchor.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.os.AsyncTask
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.Html
import android.text.InputFilter
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.adapter.Todo_list_adaptor
import com.anchor.helper.VerhoeffAlgorithm
import com.anchor.model.RCTOData
import com.anchor.webservice.ConnectionDetector
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import cpm.simplelogic.helper.CheckNullValue
import cpm.simplelogic.helper.GPSTracker
import kotlinx.android.synthetic.main.activity_todo_editcustomer.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class TodoEditCustomer : Activity() {
    private var gpsTracker: GPSTracker? = null
    var sub_otp: EditText? = null
    var otp_mobileno: EditText? = null
    var otp_time_remaining: TextView? = null
    var timer: CountDownTimer? = null
    // lateinit var merge_retailer_code: Any
    var Allresult: MutableList<RCTOData> = ArrayList<RCTOData>()
    var adaptor: Todo_list_adaptor? = null
    var coardcolor = "";
    var dialognew: Dialog? = null
    var cd: ConnectionDetector? = null
    var isInternetPresent = false
    var context: Context? = null
    var otp_verify_time_flag = ""
    var otp_hit_validator = HashMap<String, Int>()

    var otp_submit: Button? = null
    var otp_Resend:android.widget.Button? = null
    var otp_Cancel:android.widget.Button? = null
    var otp_progressBarar: ProgressBar? = null
    var otp_bottom_layout: LinearLayout? = null
    var list_CCity: MutableList<String> = ArrayList<String>()
    var adapter_CCity: ArrayAdapter<String>? = null
    var list_CDist: MutableList<String> = ArrayList<String>()
    var adapter_CDist: ArrayAdapter<String>? = null
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
    var distspinnerMap = HashMap<String, String>()
    var powerspinnerMap = HashMap<String, String>()
    var iaqspinnerMap = HashMap<String, String>()
    var lightingspinnerMap = HashMap<String, String>()

    var statespinnerMapReverse = HashMap<String, String>()
    var distspinnerMapReverse = HashMap<String, String>()
    var cityspinnerMapReverse = HashMap<String, String>()
    var powerspinnerMapReverse = HashMap<String, String>()
    var iaqspinnerMapReverse = HashMap<String, String>()
    var lightingspinnerMapReverse = HashMap<String, String>()

    var final_response = ""
    var response_result = ""
    var state_id:String? = "";
    var city_id:String? = "";
    var dist_id:String? = "";
    var dpower_id:String? = "";
    var diaq_id:String? = "";
    var dlighting_id:String? = "";
    var latitude:String? = "";
    var longitude:String? = "";

    var code = "";
    var shop_name = "";
    var proprietor_name = "";
    var address = "";
    var address_line2 = "";
    var pincode = "";
    var landmark = "";
    var state_code = "";
    var city_code = "";
    var dist_code = "";
    var state_codeback = "";
    var city_codeback = "";
    var dist_codeback = "";
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
    var power_dealerback = "";
    var lighting_dealerback = "";
    var iaq_dealerback = "";
    var source_of_data = "";
    var tsi_code = "";
    var from_flag = ""
    var id = ""
    var is_approved = ""
    var dialog: ProgressDialog? = null

    val GSTINFORMAT_REGEX = "[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z]{1}[1-9A-Za-z]{1}[Z]{1}[0-9a-zA-Z]{1}"
    val GSTN_CODEPOINT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_editcustomer)

        context = TodoEditCustomer@this
        cd = ConnectionDetector(context)

        todoe_gst.setFilters(arrayOf<InputFilter>(InputFilter.AllCaps()))
        todoe_pan.setFilters(arrayOf<InputFilter>(InputFilter.AllCaps()))

       try {
            Global_Data.merge_retailer_code = ""
            code = intent.getStringExtra("code").toString()
            shop_name = intent.getStringExtra("shop_name").toString()
            proprietor_name = intent.getStringExtra("proprietor_name").toString()
            address = intent.getStringExtra("address").toString()
            address_line2 = intent.getStringExtra("address_line2").toString()
            pincode = intent.getStringExtra("pincode").toString()
            landmark = intent.getStringExtra("landmark").toString()
            state_code = intent.getStringExtra("state_code").toString()
            city_code = intent.getStringExtra("city_code").toString()
            dist_code = intent.getStringExtra("district_id").toString()
            state_codeback = intent.getStringExtra("state_code").toString()
            city_codeback = intent.getStringExtra("city_code").toString()
            dist_codeback = intent.getStringExtra("district_id").toString()
            mobile_no = intent.getStringExtra("mobile_no").toString()
            email = intent.getStringExtra("email").toString()
            gst_no = intent.getStringExtra("gst_no").toString()
            aadhar_no = intent.getStringExtra("aadhar_no").toString()
            pan_no = intent.getStringExtra("pan_no").toString()
            latitudes = intent.getStringExtra("latitude").toString()
            longitudes = intent.getStringExtra("longitude").toString()
            power_dealer = intent.getStringExtra("power_dealer").toString()
            lighting_dealer = intent.getStringExtra("lighting_dealer").toString()
            iaq_dealer = intent.getStringExtra("iaq_dealer").toString()
            power_dealerback = intent.getStringExtra("power_dealer").toString()
            lighting_dealerback = intent.getStringExtra("lighting_dealer").toString()
            iaq_dealerback = intent.getStringExtra("iaq_dealer").toString()
            source_of_data = intent.getStringExtra("source_of_data").toString()
            tsi_code = intent.getStringExtra("tsi_code").toString()
            coardcolor = intent.getStringExtra("cardcolor").toString()

            from_flag = intent.getStringExtra("from_flag").toString()

            is_approved = intent.getStringExtra("is_approved").toString()

           val shopName = "<font color=#FF73AACC>Shop Name </font> <font color=#cc0029>*</font>"
           txt_shopname.setText(Html.fromHtml(shopName))

           val mobile = "<font color=#FF73AACC>Mobile </font> <font color=#cc0029>*</font>"
           txt_mobile.setText(Html.fromHtml(mobile))

           val address1 = "<font color=#FF73AACC>Shop No, Building, Company, Apartment </font> <font color=#cc0029>*</font>"
           txt_address.setText(Html.fromHtml(address1))

           val state = "<font color=#FF73AACC>State </font> <font color=#cc0029>*</font>"
           txt_state.setText(Html.fromHtml(state))

           val district = "<font color=#FF73AACC>District </font> <font color=#cc0029>*</font>"
           txt_district.setText(Html.fromHtml(district))

           val city_h = "<font color=#FF73AACC>City </font> <font color=#cc0029>*</font>"
           txt_city.setText(Html.fromHtml(city_h))

           val lightingdealer = "<font color=#FF73AACC>Lighting Dealer </font> <font color=#cc0029>*</font>"
           txt_lightingdealer.setText(Html.fromHtml(lightingdealer))

            if(!shop_name.equals(""))
            {
                todoe_shop_name.setText(shop_name.trim())

                if(is_approved.equals("true"))
                {
                    todoe_shop_name.isEnabled = false
                    todoe_shop_name.isClickable = false
                }
            }

            if(!proprietor_name.equals(""))
            {
                todoe_proprietor_name.setText(proprietor_name.trim())

                if(is_approved.equals("true"))
                {
                    todoe_proprietor_name.isEnabled = false
                    todoe_proprietor_name.isClickable = false
                }
            }

            if(!mobile_no.equals(""))
            {
                todoe_mobile.setText(mobile_no.trim())

                if(is_approved.equals("true"))
                {
                    todoe_mobile.isEnabled = false
                    todoe_mobile.isClickable = false
                }
            }

            if(!gst_no.equals(""))
            {
                todoe_gst.setText(gst_no.trim())

                if(is_approved.equals("true"))
                {
                    todoe_gst.isEnabled = false
                    todoe_gst.isClickable = false
                }
            }

            if(!aadhar_no.equals(""))
            {
                todoe_aadhar.setText(aadhar_no.trim())

                if(is_approved.equals("true"))
                {
                    todoe_aadhar.isEnabled = false
                    todoe_aadhar.isClickable = false
                }
            }

            if(!pan_no.equals(""))
            {
                todoe_pan.setText(pan_no.trim())

                if(is_approved.equals("true"))
                {
                    todoe_pan.isEnabled = false
                    todoe_pan.isClickable = false
                }
            }

            if(!address.equals(""))
            {
                todoe_address.setText(address.trim())

                if(is_approved.equals("true"))
                {
                    todoe_address.isEnabled = false
                    todoe_address.isClickable = false
                }
            }

            if(!address_line2.equals(""))
            {
                todoe_area.setText(address_line2.trim())

                if(is_approved.equals("true"))
                {
                    todoe_area.isEnabled = false
                    todoe_area.isClickable = false
                }
            }

            if(!landmark.equals(""))
            {
                todoe_landmark.setText(landmark.trim())

                if(is_approved.equals("true"))
                {
                    todoe_landmark.isEnabled = false
                    todoe_landmark.isClickable = false
                }
            }

            if(!pincode.equals(""))
            {
                todoe_pincode.setText(pincode.trim())

                if(is_approved.equals("true"))
                {
                    todoe_pincode.isEnabled = false
                    todoe_pincode.isClickable = false
                }
            }

            if(!latitudes.equals("") && !longitudes.equals(""))
            {
                todoe_geocordinates.setText(latitudes.trim()+","+longitudes.trim())

                if(is_approved.equals("true"))
                {
                    locationimg.isEnabled = false
                    locationimg.isClickable = false
                }
            }

            if(!tsi_code.equals(""))
            {
                todoe_tsi_code.setText(tsi_code.trim())
            }

            if(!source_of_data.equals("") && source_of_data.equals("DMS"))
            {

                try {
                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {
                        dialog = ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT)
                        dialog!!.setMessage("Please wait....")
                        dialog!!.setTitle("Smart Anchor App")
                        dialog!!.setCancelable(false)
                        dialog!!.show()
                        getNearestRetailer()
                    } else {
                        Toast.makeText(context, "You don't have internet connection.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } catch (ex: java.lang.Exception) {
                    ex.printStackTrace()
                }
            }

            if(coardcolor.equals("#D8AB1E"))
            {
                id = "2"
                todoe_shop_name.setBackgroundResource(R.drawable.todo_back)
                todoe_proprietor_name.setBackgroundResource(R.drawable.todo_back)
                todoe_mobile.setBackgroundResource(R.drawable.todo_back)
                todoe_gst.setBackgroundResource(R.drawable.todo_back)
                todoe_aadhar.setBackgroundResource(R.drawable.todo_back)
                todoe_pan.setBackgroundResource(R.drawable.todo_back)
                todoe_dealer_container.setBackgroundResource(R.drawable.todo_back)
                todoe_address.setBackgroundResource(R.drawable.todo_back)
                todoe_area.setBackgroundResource(R.drawable.todo_back)
                todoe_landmark.setBackgroundResource(R.drawable.todo_back)
                todoe_pincode.setBackgroundResource(R.drawable.todo_back)

                todoe_state.setBackgroundResource(R.drawable.spinner_background_yellow)
                todo_dist.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_city.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_yellow)
                todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_yellow)

                todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_yellow)
                todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_yellow)
            }
            else
                if(coardcolor.equals("#BB2B20") || coardcolor.equals("red"))
                {
                    id = "1"
                    todoe_shop_name.setBackgroundResource(R.drawable.todored)
                    todoe_proprietor_name.setBackgroundResource(R.drawable.todored)
                    todoe_mobile.setBackgroundResource(R.drawable.todored)
                    todoe_gst.setBackgroundResource(R.drawable.todored)
                    todoe_aadhar.setBackgroundResource(R.drawable.todored)
                    todoe_pan.setBackgroundResource(R.drawable.todored)
                    todoe_dealer_container.setBackgroundResource(R.drawable.todored)
                    todoe_address.setBackgroundResource(R.drawable.todored)
                    todoe_area.setBackgroundResource(R.drawable.todored)
                    todoe_landmark.setBackgroundResource(R.drawable.todored)
                    todoe_pincode.setBackgroundResource(R.drawable.todored)

                    todoe_state.setBackgroundResource(R.drawable.spinner_background_red)
                    todo_dist.setBackgroundResource(R.drawable.spinner_background_red)
                    todoe_city.setBackgroundResource(R.drawable.spinner_background_red)
                    todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_red)
                    todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_red)
                    todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_red)

                    todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_red)
                    todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_red)


                }
                else
                    if(coardcolor.equals("#3A921A") || coardcolor.equals("green"))
                    {
                        id = "3"

                        todoe_shop_name.setBackgroundResource(R.drawable.todogreen)
                        todoe_proprietor_name.setBackgroundResource(R.drawable.todogreen)
                        todoe_mobile.setBackgroundResource(R.drawable.todogreen)
                        todoe_gst.setBackgroundResource(R.drawable.todogreen)
                        todoe_aadhar.setBackgroundResource(R.drawable.todogreen)
                        todoe_pan.setBackgroundResource(R.drawable.todogreen)
                        todoe_dealer_container.setBackgroundResource(R.drawable.todogreen)
                        todoe_address.setBackgroundResource(R.drawable.todogreen)
                        todoe_area.setBackgroundResource(R.drawable.todogreen)
                        todoe_landmark.setBackgroundResource(R.drawable.todogreen)
                        todoe_pincode.setBackgroundResource(R.drawable.todogreen)

                        todoe_state.setBackgroundResource(R.drawable.spinner_background_green)
                        todo_dist.setBackgroundResource(R.drawable.spinner_background_green)
                        todoe_city.setBackgroundResource(R.drawable.spinner_background_green)
                        todoe_powerdealer.setBackgroundResource(R.drawable.spinner_background_green)
                        todoe_iaqdealer.setBackgroundResource(R.drawable.spinner_background_green)
                        todoe_lightingdealer.setBackgroundResource(R.drawable.spinner_background_green)

                        todoe_geocordinates.setBackgroundResource(R.drawable.todo_back_for_editablefalse_green)
                        todoe_tsi_code.setBackgroundResource(R.drawable.todo_back_for_editablefalse_green)
                    }
                    else
                        if(coardcolor.equals("#26600B") || coardcolor.equals("darkgreen"))
                        {

                            id = "4"
                            todoe_shop_name.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_proprietor_name.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_mobile.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_gst.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_aadhar.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_pan.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_dealer_container.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_address.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_area.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_landmark.setBackgroundResource(R.drawable.todarkgreen)
                            todoe_pincode.setBackgroundResource(R.drawable.todarkgreen)

                            todoe_state.setBackgroundResource(R.drawable.spinner_background_darkgreen)
                            todo_dist.setBackgroundResource(R.drawable.spinner_background_darkgreen)
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

        btn_generateotp.setOnClickListener {
            showDialogs(todoe_mobile.text.toString())
        }

        merge_icon.setOnClickListener {
            val dialog = Dialog(this@TodoEditCustomer)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_dialog_todolist)
            dialog.show()

            val yesBtn = dialog.findViewById(R.id.buttonnoOrderSave) as Button
            val noBtn = dialog.findViewById(R.id.buttonnoOrdercancel) as Button
            val progressBarMerge = dialog.findViewById(R.id.progressBarMerge) as ProgressBar
            val btnlayout = dialog.findViewById(R.id.btnlayout) as LinearLayout

            var rv=dialog.findViewById(R.id.rv) as RecyclerView
            rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)


            adaptor = Todo_list_adaptor(this, Allresult!!)
            rv.adapter = adaptor

            noBtn.setOnClickListener {
                Global_Data.merge_retailer_code = ""
                dialog.dismiss()
            }


            yesBtn.setOnClickListener {

                isInternetPresent = cd!!.isConnectingToInternet
                if (isInternetPresent) {
                    if(Global_Data.merge_retailer_code.toString().equals(""))
                    {
                        val toast = Toast.makeText(context,
                                "Please Select Retailer. ", Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                    else
                    {
                        RetailerMerge(Merge_retailercode=Global_Data.merge_retailer_code.toString(),progress_bar = progressBarMerge,submit_button = btnlayout)

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


        list_CState.add("Select State")
        adapter_CState = ArrayAdapter<String>(context!!,
                android.R.layout.simple_spinner_item, list_CState)

        adapter_CState!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todoe_state.setAdapter(adapter_CState)
        todoe_state?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                list_CDist.clear()
                list_CDist.add("Select District")
                distspinnerMap.clear()

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

                adapter_CDist = ArrayAdapter<String>(context!!,
                        android.R.layout.simple_spinner_item, list_CDist)
                adapter_CDist!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todo_dist.setAdapter(adapter_CDist)


                adapter_CCity = ArrayAdapter<String>(context!!,
                        android.R.layout.simple_spinner_item, list_CCity)
                adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_city.setAdapter(adapter_CCity)

                adapter_CPowerDealer = ArrayAdapter<String>(context!!,
                        android.R.layout.simple_spinner_item, list_CPowerDealer)
                adapter_CPowerDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_powerdealer.setAdapter(adapter_CPowerDealer)

                adapter_CIaqDealer = ArrayAdapter<String>(context!!,
                        android.R.layout.simple_spinner_item, list_CIaqDealer)
                adapter_CIaqDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_iaqdealer.setAdapter(adapter_CIaqDealer)

                adapter_CLightingDealer = ArrayAdapter<String>(context!!,
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
                            state_id?.let { distdealer_details(it) }
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

        list_CDist.add("Select District")
        adapter_CDist = ArrayAdapter<String>(context!!,
                android.R.layout.simple_spinner_item, list_CDist)

        adapter_CDist!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        todo_dist.setAdapter(adapter_CDist)
        todo_dist?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                list_CCity.clear()
                list_CCity.add("Select City")
                cityspinnerMap.clear()


                adapter_CCity = ArrayAdapter<String>(context!!,
                        android.R.layout.simple_spinner_item, list_CCity)
                adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                todoe_city.setAdapter(adapter_CCity)


                if (parent!!.getItemAtPosition(position).toString()
                                .equals("Select District", ignoreCase = true)) {

                    dist_id = "";
                }
                else
                {
                    isInternetPresent = cd!!.isConnectingToInternet
                    if (isInternetPresent) {

                        try {
                            dist_id = distspinnerMap.get(parent!!.getItemAtPosition(position).toString())
                            dist_id?.let { citydealer_details(it) }
                        }catch (e:Exception)
                        {
                            dist_id = "";
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
        adapter_CCity = ArrayAdapter<String>(context!!,
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
        adapter_CPowerDealer = ArrayAdapter<String>(context!!,
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
        adapter_CIaqDealer = ArrayAdapter<String>(context!!,
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
        adapter_CLightingDealer = ArrayAdapter<String>(context!!,
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
                        dlighting_id = lightingspinnerMap.get(parent!!.getItemAtPosition(position).toString())

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
            mActionBar!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#910505")))
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
            mActionBar!!.customView = mCustomView
            mActionBar!!.setDisplayShowCustomEnabled(true)
            mActionBar!!.setHomeButtonEnabled(true)
            mActionBar!!.setDisplayHomeAsUpEnabled(true)
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
//            if (!todoe_address!!.text.toString().trim().equals(""))
//            {
//                if (!todoe_address!!.text.toString().trim().equals("") || !todoe_area!!.text.toString().trim().equals(""))
//                {
//                    var address = ""
//
//                    if (!todoe_address!!.text.toString().trim().equals(""))
//                    {
//                        address += " " +todoe_address!!.text.toString().trim()
//                    }
//
//                    if (!todoe_area!!.text.toString().trim().equals(""))
//                    {
//                        address += " " + todoe_area!!.text.toString().trim()
//                    }
//
////                    if (!todoe_landmark!!.text.toString().trim().equals(""))
////                    {
////                        address += " " + todoe_landmark!!.text.toString().trim()
////                    }
//
//                    if (!todoe_state!!.selectedItem.toString().equals("Select State"))
//                    {
//                        address += " " + todoe_state!!.selectedItem.toString().trim()
//                    }
//
//                    if (!todoe_city!!.selectedItem.toString().equals("Select City"))
//                    {
//                        address += " " + todoe_city!!.selectedItem.toString().trim()
//                    }
//
//                    if (!todoe_pincode!!.text.toString().trim().equals(""))
//                    {
//                        address += " " + todoe_pincode!!.text.toString().trim()
//                    }
//
//                    getCordinates(address)
//                }
//                else
//                {
//                    val toast = Toast.makeText(context,
//                            "Please Enter Address", Toast.LENGTH_SHORT)
//                    toast.setGravity(Gravity.CENTER, 0, 0)
//                    toast.show()
//                }
//            }
//            else
//            {
//                val toast = Toast.makeText(context,
//                        "Please Enter Address", Toast.LENGTH_SHORT)
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
//            }

            gpsTracker = GPSTracker(this@TodoEditCustomer)
            if (gpsTracker!!.canGetLocation()) {
                latitude = gpsTracker!!.latitude.toString()
                longitude = gpsTracker!!.longitude.toString()
//                tvLatitude.setText(latitude.toString())
//                tvLongitude.setText(longitude.toString())
                todoe_geocordinates!!.setText(latitude.toString()+" , "+longitude.toString())
            } else {
                gpsTracker!!.showSettingsAlert()
            }

        }

        locationimg.setOnClickListener {

            gpsTracker = GPSTracker(this@TodoEditCustomer)
            if (gpsTracker!!.canGetLocation()) {
                latitude = gpsTracker!!.latitude.toString()
                longitude = gpsTracker!!.longitude.toString()
//                val latitude = gpsTracker!!.latitude
//                val longitude = gpsTracker!!.longitude
//                tvLatitude.setText(latitude.toString())
//                tvLongitude.setText(longitude.toString())
                todoe_geocordinates!!.setText(latitude.toString()+" , "+longitude.toString())
            } else {
                gpsTracker!!.showSettingsAlert()
            }

//            if (!todoe_address!!.text.toString().trim().equals(""))
//            {
//                if (!todoe_address!!.text.toString().trim().equals("") || !todoe_area!!.text.toString().trim().equals(""))
//                {
//                    var address = ""
//
//                    if (!todoe_address!!.text.toString().trim().equals(""))
//                    {
//                        address += " " +todoe_address!!.text.toString().trim()
//                    }
//
//                    if (!todoe_area!!.text.toString().trim().equals(""))
//                    {
//                        address += " " + todoe_area!!.text.toString().trim()
//                    }
//
////                    if (!todoe_landmark!!.text.toString().trim().equals(""))
////                    {
////                        address += " " + todoe_landmark!!.text.toString().trim()
////                    }
//
//                    if (!todoe_state!!.selectedItem.toString().equals("Select State"))
//                    {
//                        address += " " + todoe_state!!.selectedItem.toString().trim()
//                    }
//
//                    if (!todoe_city!!.selectedItem.toString().equals("Select City"))
//                    {
//                        address += " " + todoe_city!!.selectedItem.toString().trim()
//                    }
//
//                    if (!todoe_pincode!!.text.toString().trim().equals(""))
//                    {
//                        address += " " + todoe_pincode!!.text.toString().trim()
//                    }
//
//                    getCordinates(address)
//                }
//                else
//                {
//                    val toast = Toast.makeText(context,
//                            "Please Enter Address", Toast.LENGTH_SHORT)
//                    toast.setGravity(Gravity.CENTER, 0, 0)
//                    toast.show()
//                }
//            }
//            else
//            {
//                val toast = Toast.makeText(context,
//                        "Please Enter Address", Toast.LENGTH_SHORT)
//                toast.setGravity(Gravity.CENTER, 0, 0)
//                toast.show()
//            }
        }

        buttontodocecancel.setOnClickListener {
            onBackPressed()

        }

        buttontodoedit.setOnClickListener {
            todoe_shop_name.isEnabled = true
            todoe_shop_name.isClickable = true

            todoe_proprietor_name.isEnabled = true
            todoe_proprietor_name.isClickable = true
            todoe_mobile.isEnabled = true
            todoe_mobile.isClickable = true
            todoe_gst.isEnabled = true
            todoe_gst.isClickable = true
            todoe_aadhar.isEnabled = true
            todoe_aadhar.isClickable = true
            todoe_pan.isEnabled = true
            todoe_pan.isClickable = true
            todoe_address.isEnabled = true
            todoe_address.isClickable = true
            todoe_area.isEnabled = true
            todoe_area.isClickable = true
            todoe_landmark.isEnabled = true
            todoe_landmark.isClickable = true
            todoe_pincode.isEnabled = true
            todoe_pincode.isClickable = true
            locationimg.isEnabled = true
            locationimg.isClickable = true
            todoe_tsi_code.isEnabled = true
            todoe_tsi_code.isClickable = true

            todoe_state.isEnabled = true
            todoe_state.isClickable = true
            todo_dist.isEnabled = true
            todo_dist.isClickable = true
            todoe_city.isEnabled = true
            todoe_city.isClickable = true
            todoe_powerdealer.isEnabled = true
            todoe_powerdealer.isClickable = true
            todoe_iaqdealer.isEnabled = true
            todoe_iaqdealer.isClickable = true
            todoe_lightingdealer.isEnabled = true
            todoe_lightingdealer.isClickable = true

            val toast = Toast.makeText(context,
                    "Fields available for edit", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
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
                                        if (todo_dist!!.selectedItem.toString().equals("Select District"))
                                        {
                                            val toast = Toast.makeText(context,
                                                    "Please Select District.", Toast.LENGTH_SHORT)
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
                    if (!state_id.equals("") && !state_id.equals(state_codeback))
                    {
                        alert_dialog_flag = "yes";
                    }
                    else
                        if (!dist_id.equals("") && !dist_id.equals(dist_codeback))
                        {
                            alert_dialog_flag = "yes";
                        }
                        else
                            if (!city_id.equals("") && !city_id.equals(city_codeback))
                            {
                                alert_dialog_flag = "yes";
                            }
                            else
                                if (!latitude.toString().trim().equals("") || !longitude.toString().trim().equals(""))
                                {
                                    alert_dialog_flag = "yes";
                                }
                                else
                                    if ((!dpower_id.equals("") && !dpower_id.equals(power_dealerback)) || (!diaq_id.toString().equals("") && !diaq_id.equals(iaq_dealerback))|| (!dlighting_id.equals("") && !dlighting_id.equals(lighting_dealerback)))
                                    {
                                        alert_dialog_flag = "yes";
                                    }

        if(alert_dialog_flag.equals("yes"))
        {
            val alertDialog = AlertDialog.Builder(context).create() //Read Update

            alertDialog.setTitle("Confirmation")
            alertDialog.setMessage(" Are you sure you want to Cancel?")
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes") { dialog, which -> // TODO Auto-generated method stub
                if(from_flag.equals("map"))
                {
                    val i = Intent(context, MapsActivity::class.java)
                    startActivity(i)
                    finish()
                }else
                {
                    val i = Intent(context, RetailerTDCustomerList::class.java)
                    i.putExtra("id",id)
                    i.putExtra("cardcolor",coardcolor)
                    startActivity(i)
                    finish()
                }
            }

            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No") { dialog, which -> // TODO Auto-generated method stub
                dialog.cancel()
            }

            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        else{
            if(from_flag.equals("map"))
            {
                val i = Intent(context, MapsActivity::class.java)
                startActivity(i)
                finish()
            }
            else
            {
                val i = Intent(context, RetailerTDCustomerList::class.java)
                i.putExtra("id",id)
                i.putExtra("cardcolor",coardcolor)
                startActivity(i)
                finish()
            }

        }
    }

    fun getCordinates(addresss:String)
    {
        var str: String = "";
        isInternetPresent = cd!!.isConnectingToInternet
        if (isInternetPresent) {
            try {
                val geo = Geocoder(context, Locale.getDefault())
                val addresses = geo.getFromLocationName(addresss.trim(),1)
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

                        todoe_geocordinates!!.setText(str)
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
                                    statespinnerMap.put(jsonObject.getString("name"),jsonObject.getString("id"))
                                    statespinnerMapReverse.put(jsonObject.getString("id"),jsonObject.getString("name"))

                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    runOnUiThread(Runnable {

                        adapter_CState = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CState)

                        adapter_CState!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_state.setAdapter(adapter_CState)


                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(state_code)) {
                                var s_name = statespinnerMapReverse.get(state_code)
                                todoe_state.setSelection(adapter_CState!!.getPosition(s_name))

                                if(is_approved.equals("true"))
                                {
                                    todoe_state.isEnabled = false
                                    todoe_state.isClickable = false
                                }

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

    fun distdealer_details(state_id:String) {
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
        var url = domain+"retailers/get_statewise_districts?state_id="+state_id+"&email="+user_email
        Log.i("user list url", "user list url " +url)
        var jsObjRequest: StringRequest? = null
        jsObjRequest = StringRequest(url, Response.Listener { response ->
            Log.i("volley", "response: $response")
            final_response = response
            getstatewise_DistDealer().execute(response)
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

    public inner class getstatewise_DistDealer : AsyncTask<String?, Void?, String>() {
        override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")

                    runOnUiThread(Runnable {
                        citys_loaderedit.visibility = View.GONE
                        //Toast.makeText(Order.this, "Delivery Schedule Not Found.", Toast.LENGTH_LONG).show();
                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                }
                else {
                    val districts: JSONArray = response.getJSONArray("districts")
                    Log.i("volley", "response districts Length: " + districts.length())
                    Log.d("volley", "districts$districts")


                    val p_dealer: JSONArray = response.getJSONArray("power_dealers")
                    Log.i("volley", "response p_dealer Length: " + p_dealer.length())
                    Log.d("volley", "p_dealer$p_dealer")

                    val i_dealer: JSONArray = response.getJSONArray("iaq_dealers")
                    Log.i("volley", "response i_dealer Length: " + i_dealer.length())
                    Log.d("volley", "i_dealer$i_dealer")

                    val l_delaer: JSONArray = response.getJSONArray("lighting_dealers")
                    Log.i("volley", "response l_delaer Length: " + l_delaer.length())
                    Log.d("volley", "l_delaer$l_delaer")


                    list_CDist.clear()
                    list_CDist.add("Select District")
                    distspinnerMap.clear()
                    distspinnerMapReverse.clear()

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

                    for (i in 0 until districts.length()) {
                        val jsonObject = districts.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(districts.getString(i))) {
                                run {
                                    list_CDist.add(jsonObject.getString("name"))
                                    distspinnerMap.put(jsonObject.getString("name"),jsonObject.getString("id"))
                                    distspinnerMapReverse.put(jsonObject.getString("id"),jsonObject.getString("name"))

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

                        adapter_CDist = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CDist)
                        adapter_CDist!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todo_dist.setAdapter(adapter_CDist)

                        adapter_CCity = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CCity)
                        adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_city.setAdapter(adapter_CCity)

                        adapter_CPowerDealer = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CPowerDealer)
                        adapter_CPowerDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_powerdealer.setAdapter(adapter_CPowerDealer)

                        adapter_CIaqDealer = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CIaqDealer)
                        adapter_CIaqDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_iaqdealer.setAdapter(adapter_CIaqDealer)

                        adapter_CLightingDealer = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CLightingDealer)
                        adapter_CLightingDealer!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_lightingdealer.setAdapter(adapter_CLightingDealer)

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(dist_code)) {
                                var c_name = distspinnerMapReverse.get(dist_code)
                                todo_dist.setSelection(adapter_CDist!!.getPosition(c_name))

                                if(is_approved.equals("true"))
                                {
                                    todo_dist.isEnabled = false
                                    todo_dist.isClickable = false
                                }

                                dist_code = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(power_dealer)) {
                                var p_name = powerspinnerMapReverse.get(power_dealer)
                                todoe_powerdealer.setSelection(adapter_CPowerDealer!!.getPosition(p_name))

                                if(is_approved.equals("true"))
                                {
                                    todoe_powerdealer.isEnabled = false
                                    todoe_powerdealer.isClickable = false
                                }

                                power_dealer = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(iaq_dealer)) {
                                var iaq_name = iaqspinnerMapReverse.get(iaq_dealer)
                                todoe_iaqdealer.setSelection(adapter_CIaqDealer!!.getPosition(iaq_name))

                                if(is_approved.equals("true"))
                                {
                                    todoe_iaqdealer.isEnabled = false
                                    todoe_iaqdealer.isClickable = false
                                }

                                iaq_dealer = ""
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(lighting_dealer)) {
                                var l_name = lightingspinnerMapReverse.get(lighting_dealer)
                                todoe_lightingdealer.setSelection(adapter_CLightingDealer!!.getPosition(l_name))

                                if(is_approved.equals("true"))
                                {
                                    todoe_lightingdealer.isEnabled = false
                                    todoe_lightingdealer.isClickable = false
                                }

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

    fun citydealer_details(dist_id:String) {
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
        var url = domain+"retailers/get_districtwise_cities?district_id="+dist_id+"&email="+user_email
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
                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    })
                }
                else {
                    val cities: JSONArray = response.getJSONArray("cities")
                    Log.i("volley", "response cities Length: " + cities.length())
                    Log.d("volley", "cities$cities")


                    list_CCity.clear()
                    list_CCity.add("Select City")
                    cityspinnerMap.clear()
                    cityspinnerMapReverse.clear()


                    for (i in 0 until cities.length()) {
                        val jsonObject = cities.getJSONObject(i)
                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cities.getString(i))) {
                                run {
                                    list_CCity.add(jsonObject.getString("name"))
                                    cityspinnerMap.put(jsonObject.getString("name"),jsonObject.getString("id"))
                                    cityspinnerMapReverse.put(jsonObject.getString("id"),jsonObject.getString("name"))

                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    runOnUiThread(Runnable {

                        adapter_CCity = ArrayAdapter<String>(context!!,
                                android.R.layout.simple_spinner_item, list_CCity)
                        adapter_CCity!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        todoe_city.setAdapter(adapter_CCity)



                        try {
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(city_code)) {
                                var c_name = cityspinnerMapReverse.get(city_code)
                                todoe_city.setSelection(adapter_CCity!!.getPosition(c_name))

                                if(is_approved.equals("true"))
                                {
                                    todoe_city.isEnabled = false
                                    todoe_city.isClickable = false
                                }

                                city_code = ""
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
                if(!todo_dist.selectedItem.toString().equals("Select District"))
                {
                    dist_id = distspinnerMap.get(todo_dist.selectedItem.toString())

                }
                else
                {
                    dist_id = ""
                }

            }catch (e:Exception)
            {
                dist_id = "";
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
                url = domain + "retailers/update_retailer"
                Log.d("Server url", "Server url" + url)
                val SURVEY = JSONArray()
                val product_value_n = JSONObject()
                val retailer_object = JSONObject()

                //product_value_n.put("email", user_email)
                product_value_n.put("code", code)
                product_value_n.put("state_id", state_id)
                product_value_n.put("district_id", dist_id)
                product_value_n.put("city_id", city_id)

                if(dpower_id?.length!! > 0)
                {
                    product_value_n.put("power_dealer", dpower_id)
                }
                if(diaq_id?.length!! > 0)
                {
                    product_value_n.put("iaq_dealer", diaq_id)
                }
                if(dlighting_id?.length!! > 0)
                {
                    product_value_n.put("lighting_dealer", dlighting_id)
                }

                product_value_n.put("shop_name", todoe_shop_name.text.toString())
                product_value_n.put("proprietor_name", todoe_proprietor_name.text.toString())
                product_value_n.put("mobile_no", todoe_mobile.text.toString())
                product_value_n.put("gst_no", todoe_gst.text.toString())
                product_value_n.put("aadhar_no", todoe_aadhar.text.toString())
                product_value_n.put("pan_no", todoe_pan.text.toString())
                product_value_n.put("address_line1", todoe_address.text.toString())
                product_value_n.put("address_line2", todoe_area.text.toString())
                product_value_n.put("landmark", todoe_landmark.text.toString())
                product_value_n.put("pincode", todoe_pincode.text.toString())

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(latitude) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(latitude)) {
                    product_value_n.put("latitude", latitude)
                    product_value_n.put("longitude", longitude)
                }
                else{
                    product_value_n.put("latitude", latitudes)
                    product_value_n.put("longitude", longitudes)
                }

                retailer_object.put("retailer",product_value_n)
                retailer_object.put("email",user_email)


                Log.d("Retailer update Service", retailer_object.toString())
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
                        if (response_result.equals("Retailer updated successfully.", ignoreCase = true)) {

                            piechart_rank_progressBararredit.visibility = View.GONE
                            aad_bottom_layoutredit.visibility = View.VISIBLE
                            add_containerredit.isEnabled = true

                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                            if(from_flag.equals("map"))
                            {
                                val i = Intent(context, MapsActivity::class.java)
                                startActivity(i)
                                finish()
                            }else
                            {
                                val i = Intent(context, RetailerTDList::class.java)
                                startActivity(i)
                                finish()
                            }

                        }
//                        else
//                            if (response_result.equals("Retailer Duplicate record found.", ignoreCase = true)) {
//
//                                piechart_rank_progressBararredit.visibility = View.GONE
//                                aad_bottom_layoutredit.visibility = View.VISIBLE
//                                add_containerredit.isEnabled = true
//
//                                var retailer_code = response.getString("retailer_code")
//
//                                val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
//                                toast.setGravity(Gravity.CENTER, 0, 0)
//                                toast.show()
//
//                                val alertDialog = AlertDialog.Builder(context).create() //Read Update
//
//                                alertDialog.setTitle("Confirmation")
//                                alertDialog.setMessage("If you want to merge, Please click on yes button Or if you want to delete, Please click on no button  ")
//                                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes") { dialog, which -> // TODO Auto-generated method stub
//                                    dialog.cancel()
//                                    RetailerDuplicateData(retailer_code,"Yes")
//                                }
//
//                                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No") { dialog, which -> // TODO Auto-generated method stub
//                                    dialog.cancel()
//                                    RetailerDuplicateData(retailer_code,"No")
//                                }
//
//                                alertDialog.setCancelable(false)
//                                alertDialog.show()
//                            }
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
            Log.e("DATA", e.message.toString())
        }
    }


    fun getNearestRetailer() {
        val sp = getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE)
        val domain = resources.getString(R.string.service_domain)
        Log.i("volley", "domain: $domain")

        try {
            var jsObjRequest: StringRequest? = null
            var service_url = ""

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
            //service_url = "https://mumuatsmadms01.anchor-group.in/metal/api/v1/retailers/get_nearest_retailers_for_megre?email=priyanka.konka@simplelogic.in&latitude=19.1123&longitude=72.9332&retailer_code=MHMCTES0021"
            service_url = (domain + "retailers/get_nearest_retailers_for_megre?email=" + user_email + "&latitude="
                    + Global_Data.GLOvel_LATITUDE + "&longitude="
                    + Global_Data.GLOvel_LONGITUDE)+"&retailer_code="+code
            Log.i("user list url", "user list url $service_url")
            jsObjRequest = StringRequest(service_url, Response.Listener { response ->
                Log.i("volley", "response: $response")
                final_response = response
                getNearestRetailerResponse().execute(response)
            },
                    Response.ErrorListener { error ->
                        dialog!!.dismiss()
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
                        dialog!!.dismiss()
                        // finish();
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
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    public inner class getNearestRetailerResponse : AsyncTask<String?, Void?, String>() {
        protected override fun doInBackground(vararg p0: String?): String? {
            try {
                val response = JSONObject(final_response)
                if (response.has("message")) {
                    response_result = response.getString("message")
                    runOnUiThread {
                        merge_icon.visibility = View.GONE
                        try {
                            dialog!!.dismiss()
                        }catch (e:Exception)
                        {
                            e.printStackTrace()
                        }
//                        todolist_progress_customer.visibility = View.GONE
//                        rtocustomerlist.visibility = View.GONE
//                        val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
//                        toast.setGravity(Gravity.CENTER, 0, 0)
//                        toast.show()
                        //finish()
                    }.toString()

                } else { //dbvoc.getDeleteTable("delivery_products");
                    val retailers = response.getJSONArray("retailers")
                    Log.i("volley", "response retailers Length: " + retailers.length())
                    Log.d("volley", "retailers$retailers")
                    //
                    if (retailers.length() <= 0) {
                        runOnUiThread {
                            merge_icon.visibility = View.GONE
                            try {
                                dialog!!.dismiss()
                            }catch (e:Exception)
                            {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        Allresult.clear()
                        for (i in 0 until retailers.length()) {
                            var user_cirname = ""
                            val jsonObject = retailers.getJSONObject(i)
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject!!.getString("code"))) {

                                Allresult.add(RCTOData("", Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("code")), "",
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
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("data_source_master_id")),
                                        "", "",Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(""),
                                        coardcolor,"",Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("address_line2")),
                                        Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(jsonObject!!.getString("landmark")),"","","","","",""))


                            }
                        }
                        runOnUiThread {
                            merge_icon.visibility = View.VISIBLE
                            // ca = RCTDAdapter(context!!, Allresult);
                            //rtocustomerlist.setAdapter(ca);
                            //ca!!.notifyDataSetChanged();

                        }.toString()

                        runOnUiThread {
                            try {
                                dialog!!.dismiss()
                            }catch (e:Exception)
                            {
                                e.printStackTrace()
                            }
                        }

                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                runOnUiThread {
                    try {
                        dialog!!.dismiss()
                    }catch (e:Exception)
                    {
                        e.printStackTrace()
                    }
                    //todolist_progress_customer.visibility = View.GONE
                    // rtocustomerlist.visibility = View.GONE
                }
            }
            runOnUiThread {
                try {
                    dialog!!.dismiss()
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
            return "Executed"
        }

        override fun onPostExecute(result: String) {
            runOnUiThread {
                try {
                    if(dialog != null)
                    {
                        dialog!!.dismiss()
                    }
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }

        override fun onPreExecute() {}

    }

    override fun onDestroy() {
        super.onDestroy()

        try {
            if(dialog != null)
            {
                dialog!!.dismiss()
            }
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    fun RetailerMerge(Merge_retailercode:String,progress_bar:ProgressBar,submit_button:LinearLayout) {
        System.gc()
        val reason_code = ""
        try {
            progress_bar.visibility = View.VISIBLE
            submit_button.visibility = View.GONE
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
                url = domain + "retailers/merge_retailer"
                Log.d("Server url", "Server url" + url)
                val SURVEY = JSONArray()
                val product_value_n = JSONObject()
                val retailer_object = JSONObject()

                //product_value_n.put("email", user_email)
                product_value_n.put("code", code)
                product_value_n.put("state_id", state_id)
                product_value_n.put("city_id", city_id)
//
//                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(latitude) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(latitude)) {
//                    product_value_n.put("latitude", latitude)
//                    product_value_n.put("longitude", longitude)
//                }
//                else{
//                    product_value_n.put("latitude", latitudes)
//                    product_value_n.put("longitude", longitudes)
//                }

                retailer_object.put("current_retailer",code)
                retailer_object.put("existing_retailer",Merge_retailercode)
                retailer_object.put("email",user_email)


                Log.d("Retailer update Service", retailer_object.toString())
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
                        if (response_result.equals("Retailer Merge successfully.", ignoreCase = true)) {

                            progress_bar.visibility = View.GONE
                            submit_button.visibility = View.VISIBLE

                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                            if(from_flag.equals("map"))
                            {
                                val i = Intent(context, MapsActivity::class.java)
                                startActivity(i)
                                finish()
                            }else
                            {
                                val i = Intent(context, RetailerTDList::class.java)
                                startActivity(i)
                                finish()
                            }

                        }
                        else {
                            progress_bar.visibility = View.GONE
                            submit_button.visibility = View.VISIBLE
                            val toast = Toast.makeText(context, response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        progress_bar.visibility = View.GONE
                        submit_button.visibility = View.VISIBLE
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
                    progress_bar.visibility = View.GONE
                    submit_button.visibility = View.VISIBLE
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
                progress_bar.visibility = View.GONE
                submit_button.visibility = View.VISIBLE
            }



        } catch (e: java.lang.Exception) {
            progress_bar.visibility = View.GONE
            submit_button.visibility = View.VISIBLE
            Log.e("DATA", e.message.toString())
        }
    }

    fun showDialogs(otpmobileno: String?) {
        dialognew = Dialog(this@TodoEditCustomer)
        dialognew!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialognew!!.setCancelable(false)
        dialognew!!.setContentView(R.layout.retailer_otp_verification)
        sub_otp = dialognew!!.findViewById(R.id.sub_otp)
        otp_mobileno = dialognew!!.findViewById(R.id.otp_mobile)
        otp_time_remaining = dialognew!!.findViewById(R.id.otp_time_remaining)
        otp_mobileno!!.setText(otpmobileno)

        // otp_mobile_value.setText(input_mobno1.getText().toString());
        otp_verify_time_flag = "yes"
        otp_progressBarar = dialognew!!.findViewById(R.id.otp_progressBarar)
        otp_bottom_layout = dialognew!!.findViewById(R.id.otp_bottom_layout)
        otp_submit = dialognew!!.findViewById(R.id.otp_submit)
        otp_Resend = dialognew!!.findViewById(R.id.otp_Resend)
        otp_Cancel = dialognew!!.findViewById(R.id.otp_Cancel)
        try {
            val format = SimpleDateFormat("MM/dd/yyyy HH:mm:ss")
            val today1 = format.format(Date())
            val today = Date(today1)
            val currentTime = today.time

            // String today1 = format.format(System.currentTimeMillis()+15*60*1000);
            val service_plusf = format.parse(today1)
            val cal = Calendar.getInstance()
            cal.time = service_plusf
            cal.add(Calendar.MINUTE, 2)
            val new_date = cal.time
            val s_time = new_date.time
            val expiryTime = s_time - currentTime
            timer = object : CountDownTimer(expiryTime, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    otp_time_remaining!!.setText("" + String.format("%d:%d Mins Remaining",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))))
                }

                override fun onFinish() {
                    otp_time_remaining!!.setText("00:00:00")
                    if (otp_verify_time_flag.equals("yes", ignoreCase = true)) {
                        otp_Resend!!.setVisibility(View.VISIBLE)
                        otp_Resend!!.setText("Resend OTP")
                        otp_submit!!.setVisibility(View.GONE)
                        sub_otp!!.setVisibility(View.GONE)
                        sub_otp!!.setText("")
                        otp_verify_time_flag = ""
                    } else {
                        otp_verify_time_flag = ""
                    }
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        otp_Resend!!.setOnClickListener(View.OnClickListener {
            if (CheckNullValue.findNullValue(otp_mobileno!!.getText().toString().trim({ it <= ' ' })) == true) {
                val toast = Toast.makeText(this@TodoEditCustomer, "Please Enter Mobile No", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else {
                otp_progressBarar!!.setVisibility(View.VISIBLE)
                otp_bottom_layout!!.setVisibility(View.GONE)
                Generate_Otp("resend", otp_mobileno!!.getText().toString())
            }
        })
        otp_submit!!.setOnClickListener(View.OnClickListener { //                if (otp_verify_time_flag.equalsIgnoreCase("yes")) {
//                    if (!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(sub_otp.getText().toString())) {
//                        sub_otp.requestFocus();
//                        //Globel_Data.Custom_Toast(ShopDetails.this, "Please Enter OTP", "");
//
//                    } else {
//
//                    }
//                } else {
//
//
//                }
            if (CheckNullValue.findNullValue(otp_mobileno!!.getText().toString().trim({ it <= ' ' })) == true) {
                val toast = Toast.makeText(this@TodoEditCustomer, "Please Enter UserName", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else if (CheckNullValue.findNullValue(sub_otp!!.getText().toString().trim({ it <= ' ' })) == true) {
                val toast = Toast.makeText(this@TodoEditCustomer, "Please Enter OTP", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            } else {
                otp_progressBarar!!.setVisibility(View.VISIBLE)
                otp_bottom_layout!!.setVisibility(View.GONE)
                val otp: String = sub_otp!!.getText().toString()
                val spf: SharedPreferences = this@TodoEditCustomer.getSharedPreferences("SimpleLogic", 0)
                val editor = spf.edit()
                editor.putString("OTP", otp)
                editor.commit()
                submit_OTP(otp_mobileno!!.getText().toString(), sub_otp!!.getText().toString(), dialognew!!)
            }
        })
        otp_Cancel!!.setOnClickListener(View.OnClickListener {
            otp_hit_validator.clear()
            timer!!.cancel()
            dialognew!!.dismiss()
        })
        dialognew!!.show()
    }

    fun Generate_Otp(Click_Flag: String?, mobileno: String?) {
        System.gc()
        val reason_code = ""
        try {
            var jsObjRequest: JsonObjectRequest? = null
            try {
                val domain = resources.getString(R.string.service_domain)
                val url = domain + "retailers/generate_otp"
                Log.d("Server url", "Server url$url")
                val SINOBJECT = JSONObject()

                SINOBJECT.put("email", Global_Data.GLOvel_USER_EMAIL)
                SINOBJECT.put("retailer_code", code)
                Log.d("user_dealer Service", SINOBJECT.toString())
                jsObjRequest = JsonObjectRequest(Request.Method.POST, url, SINOBJECT, Response.Listener { response ->
                    Log.i("volle y", "response: $response")
                    Log.d("jV", "JV length" + response.length())
                    try {
                        var response_result = ""
                        response_result = if (response.has("result")) {
                            response.getString("result")
                        } else {
                            "data"
                        }
                        if (response_result.equals("OTP Sent Successfully.", ignoreCase = true)) {

                            // dialog.dismiss();
                            otp_verify_time_flag = "yes"
                            otp_Resend!!.text = "Resend OTP"
                            otp_progressBarar!!.visibility = View.GONE
                            otp_bottom_layout!!.visibility = View.VISIBLE
                            otp_submit!!.visibility = View.VISIBLE
                            sub_otp!!.visibility = View.VISIBLE
                            otp_Resend!!.visibility = View.GONE
                            val toast = Toast.makeText(this@TodoEditCustomer,
                                    response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            timer!!.start()
                            otp_hit_validator.clear()
                        } else {
                            otp_progressBarar!!.visibility = View.GONE
                            otp_bottom_layout!!.visibility = View.VISIBLE
                            val toast = Toast.makeText(this@TodoEditCustomer,
                                    response_result, Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        otp_progressBarar!!.visibility = View.GONE
                        otp_bottom_layout!!.visibility = View.VISIBLE
                    }
                }, Response.ErrorListener { error ->
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
                    otp_progressBarar!!.visibility = View.GONE
                    otp_bottom_layout!!.visibility = View.VISIBLE
                    // finish();
                })
                val requestQueue = Volley.newRequestQueue(this@TodoEditCustomer)
                val socketTimeout = 300000 //90 seconds - change to what you want
                val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
                jsObjRequest.retryPolicy = policy
                // requestQueue.se
                //requestQueue.add(jsObjRequest);
                jsObjRequest.setShouldCache(false)
                requestQueue.cache.clear()
                requestQueue.add(jsObjRequest)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                dialog!!.dismiss()
            }
        } catch (e: java.lang.Exception) {
            // TODO: handle exception
            Log.e("DATA", e.message)
        }
    }

    fun submit_OTP(mobileno: String, otp: String, dialogdis: Dialog) {
        try {
            val domain = resources.getString(R.string.service_domain)
            val url = domain + "retailers/verify_otp?retailer_code=" + code +"&email="+Global_Data.GLOvel_USER_EMAIL+ "&otp=" + otp
            Log.i("volley", "url: $url")
            Log.i("volley", "mobileno: $mobileno")
            Log.i("volley", "otp: $otp")
            val jsObjRequest = JsonObjectRequest(url, null, Response.Listener { response ->

                // JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"/menus/registration?imei_no="+ URLEncoder.encode("911305401754123", "UTF-8"),null, new Response.Listener<JSONObject>() {
                Log.i("volley", "response: $response")
                try {
                    var response_result = ""
                    response_result = if (response.has("message")) {
                        response.getString("message")
                    } else {
                        "data"
                    }
                    if (response_result.equals("Retailer Verified Successfully.", ignoreCase = true)) {

//                        //List<Local_Data> conta = dbvoc.getAllMain();
//                        val conta: List<Local_Data> = dbvoc.getSyncDate(user_name)
//                        if (conta.size > 0) {
//                            Log.d("Existing User", "Existing U")
//                            val spf: SharedPreferences = this@LoginActivity.getSharedPreferences("SimpleLogic", 0)
//                            val editor = spf.edit()
//                            editor.putString("TCODE", "Yes")
//                            editor.putString("FirstLogin", "Yes")
//                            editor.commit()
//                            val c = Calendar.getInstance()
//                            val df = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
//                            val formattedDate = df.format(c.time)
//                            dbvoc.update_user_createD(formattedDate, user_name)
//                            timer!!.cancel()
//                            val toast = Toast.makeText(this@TodoEditCustomer, response_result, Toast.LENGTH_LONG)
//                            toast.setGravity(Gravity.CENTER, 0, 0)
//                            toast.show()
//                            dialogdis.dismiss()
//                        } else {
//                            Log.d("New User", "New U")
//                            Log.d("Existing User", "Existing U")
//                            timer!!.cancel()
//                            getserviceData(user_name, dialogdis)
//                        }
                        timer!!.cancel()
                        val toast = Toast.makeText(this@TodoEditCustomer, response_result, Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        dialogdis.dismiss()
                        dialognew!!.dismiss()
                    } else {
                        otp_progressBarar!!.visibility = View.GONE
                        otp_bottom_layout!!.visibility = View.VISIBLE
                        val toast = Toast.makeText(this@TodoEditCustomer, response_result, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    otp_progressBarar!!.visibility = View.GONE
                    otp_bottom_layout!!.visibility = View.VISIBLE
                }
            }, Response.ErrorListener { error ->
                Log.i("volley", "error: $error")
                // Toast.makeText(getApplicationContext(), "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
                val toast = Toast.makeText(this@TodoEditCustomer, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                otp_progressBarar!!.visibility = View.GONE
                otp_bottom_layout!!.visibility = View.VISIBLE
            })
            val requestQueue = Volley.newRequestQueue(this)
            // queue.add(jsObjRequest);
            jsObjRequest.setShouldCache(false)
            val socketTimeout = 3000000 //3000 seconds - change to what you want
            val policy: RetryPolicy = DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            jsObjRequest.retryPolicy = policy
            requestQueue.add(jsObjRequest)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            otp_progressBarar!!.visibility = View.GONE
            otp_bottom_layout!!.visibility = View.VISIBLE
        }
    }
}