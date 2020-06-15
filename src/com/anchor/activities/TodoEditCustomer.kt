package com.anchor.activities

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.anchor.webservice.ConnectionDetector
import kotlinx.android.synthetic.main.activity_todo_editcustomer.*
import kotlinx.android.synthetic.main.reatilertdcustomerlist.*
import java.util.ArrayList


class TodoEditCustomer : Activity() {

    var id = "";
    var coardcolor = "";
    var cd: ConnectionDetector? = null
    var isInternetPresent = false
    var context: Context? = null
    var list_CCity: MutableList<String> = ArrayList<String>()
    var adapter_CCity: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_editcustomer)

        context = RetailerTDCustomerList@this
        cd = ConnectionDetector(context)

        try {
            id = intent.getStringExtra("id")
            coardcolor = intent.getStringExtra("cardcolor")
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
    }
}