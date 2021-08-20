package com.anchor.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_attendance.*
import kotlinx.android.synthetic.main.activity_attendance.txtWelcomeUserDcr
import kotlinx.android.synthetic.main.activity_schemedetail.*

class Schemedetail : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemedetail)


        var user_name = ""
        if (!Global_Data.USER_FIRST_NAME.equals("null", ignoreCase = true)) {
            user_name = Global_Data.USER_FIRST_NAME.trim { it <= ' ' }
            if (!Global_Data.USER_LAST_NAME.equals("null", ignoreCase = true)) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim { it <= ' ' }
            }
        }

        txtWelcomeUserDcr.setText(user_name + " : " + Global_Data.emp_code)

   //     var strUser: String = intent.dataString

        val scheame_name: String = intent.getStringExtra("scheame_name").toString()
        val product_name: String = intent.getStringExtra("product_name").toString()
        val qualify_amount: String = intent.getStringExtra("qualify_amount").toString()
        val qualify_quan: String = intent.getStringExtra("qualify_quan").toString()
        val descriptionn: String = intent.getStringExtra("description").toString()
        val focn: String = intent.getStringExtra("foc").toString()
        val product_details: String = intent.getStringExtra("product_details").toString()
        val discount_in_dp_amount: String = intent.getStringExtra("discount_in_dp_amount").toString()
        val discount_in_dp_percent: String = intent.getStringExtra("discount_in_dp_percent").toString()
        val foc_qan: String = intent.getStringExtra("foc_qan").toString()


        Log.d("Android", "focnsc $focn")


        if (focn.equals("FOC")){
            focprodde.visibility=View.VISIBLE
            focquanll.visibility=View.VISIBLE


        }else if (focn.equals("DISCOUNT")){
            dis_percenrll.visibility=View.VISIBLE
            dis_amountll.visibility=View.VISIBLE
        }



        discountpercent.setText(discount_in_dp_percent)
        discountamount.setText(discount_in_dp_amount)
        foc_qty.setText(foc_qan)
        schemename.setText(scheame_name)
        productname.setText(product_name)
        amount.setText(qualify_amount)
        qty.setText(qualify_quan)
        description.setText(descriptionn)
        foc.setText(focn)
        productdetails.setText(product_details)

        close.setOnClickListener {
            onBackPressed()
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
            mTitleTextView.text = "SCHEME DETAIL"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp: SharedPreferences = this@Schemedetail.getSharedPreferences("SimpleLogic", 0)

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
        if(Global_Data.scheame_type.equals("product")){
            val i = Intent(this@Schemedetail, ProductAll_Varients::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(i)

        }else if(Global_Data.scheame_type.equals("schem")){
            val i = Intent(this@Schemedetail, Schemecirculer::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            startActivity(i)

        }

           }
}