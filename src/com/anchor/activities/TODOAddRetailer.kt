package com.anchor.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.*
import com.anchor.adapter.Todo_list_adaptor
import com.anchor.model.Todo_model
import com.anchor.webservice.ConnectionDetector
import kotlinx.android.synthetic.main.activity_todo_editcustomer.*
import kotlinx.android.synthetic.main.custom_dialog_todolist.*
import kotlinx.android.synthetic.main.reatilertdcustomerlist.*
import java.util.ArrayList


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

        locationimg.setOnClickListener {

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}