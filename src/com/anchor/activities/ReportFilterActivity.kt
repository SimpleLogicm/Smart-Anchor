package com.anchor.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.anchor.webservice.ConnectionDetector
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_report_filter.*
import kotlinx.android.synthetic.main.activity_todo_editcustomer.*
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ReportFilterActivity : Activity() {
    var datePickerDialog: DatePickerDialog? = null
    var click_detect_flag = ""
    var item: String? = null
    var calendar: Calendar? = null
    var cd: ConnectionDetector? = null
    var isInternetPresent = false
    var dataAdapterusername: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_filter)

        var user_name = ""
        if (!Global_Data.USER_FIRST_NAME.equals("null", ignoreCase = true)) {
            user_name = Global_Data.USER_FIRST_NAME.trim { it <= ' ' }
            if (!Global_Data.USER_LAST_NAME.equals("null", ignoreCase = true)) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim { it <= ' ' }
            }
        }

        txtWelcomeUser.setText(user_name + " : " + Global_Data.emp_code)

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
            mTitleTextView.text = "Reports"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp: SharedPreferences = this@ReportFilterActivity.getSharedPreferences("SimpleLogic", 0)

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
//        Global_Data.usernameArray.clear()
        Global_Data.usernameArray.add("Select User")

        val hashSet = HashSet<String>()
        hashSet.addAll(Global_Data.usernameArray)
        Global_Data.usernameArray.clear()
        Global_Data.usernameArray.addAll(hashSet)

        dataAdapterusername = ArrayAdapter<String>(this@ReportFilterActivity, R.layout.spinner_item, Global_Data.usernameArray!!)
        dataAdapterusername!!.setDropDownViewResource(R.layout.spinner_item)
        spindcr.adapter = dataAdapterusername

        spindcr.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                item = parent.getItemAtPosition(position).toString()

                if (!selectedItem.equals("Select User")) {
                    item = parent.getItemAtPosition(position).toString()
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        dcr_from.setOnClickListener {
            Global_Data.hideSoftKeyboard(this@ReportFilterActivity)
            click_detect_flag = "from_date"

            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]
            //datePickerDialog = DatePickerDialog.newInstance(this@AttendanceActivity, mDay, mMonth, mYear)

            datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val mFormat = DecimalFormat("00")
                val date = mFormat.format(java.lang.Double.valueOf(dayOfMonth.toDouble())) + "-" + mFormat.format(java.lang.Double.valueOf(monthOfYear + 1.toDouble())) + "-" + year

                if (click_detect_flag.equals("from_date", ignoreCase = true)) {
                    if (!dcr_to.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(date, dcr_to.text.toString())
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_from.setText(date)

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_from.setText("")
                            val toast = Toast.makeText(applicationContext, "From Date should be less Than To Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_from.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select To Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                } else if (click_detect_flag.equals("to_date", ignoreCase = true)) {
                    if (!dcr_from.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(dcr_from.text.toString(), date)
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_to.setText(date)

                            isInternetPresent = cd!!.isConnectingToInternet

//                            if (isInternetPresent) {
//                                getdata("")
//
//                            } else {
//                                val toast = Toast.makeText(this,
//                                        "Internet Not Available. ", Toast.LENGTH_SHORT)
//                                toast.setGravity(Gravity.CENTER, 0, 0)
//                                toast.show()
//                                finish()
//                            }

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_to.setText("")
                            val toast = Toast.makeText(applicationContext, "To Date should be Greater Than from Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_to.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select From Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            }, mYear, mMonth, mDay)

            //datePickerDialog = DatePickerDialog.newInstance(this@DCRActivity, Day, Month, Year)
            datePickerDialog?.setThemeDark(false)
            datePickerDialog?.showYearPickerFirst(false)
            // datePickerDialog?.setYearRange(2017, Year)
            datePickerDialog?.setAccentColor(Color.parseColor("#303F9F"))
            datePickerDialog?.setTitle("Select From Date")
            datePickerDialog?.show(fragmentManager, "Smart Anchor App")
        }

        dcr_to.setOnClickListener {
            Global_Data.hideSoftKeyboard(this@ReportFilterActivity)
            click_detect_flag = "to_date"
            val c = Calendar.getInstance()
            val mYear = c[Calendar.YEAR]
            val mMonth = c[Calendar.MONTH]
            val mDay = c[Calendar.DAY_OF_MONTH]

            datePickerDialog = DatePickerDialog.newInstance(DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mFormat = DecimalFormat("00")
                val date = mFormat.format(java.lang.Double.valueOf(dayOfMonth.toDouble())) + "-" + mFormat.format(java.lang.Double.valueOf(monthOfYear + 1.toDouble())) + "-" + year

                if (click_detect_flag.equals("from_date", ignoreCase = true)) {
                    if (!dcr_to.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(date, dcr_to.text.toString())
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_from.setText(date)

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_from.setText("")
                            val toast = Toast.makeText(applicationContext, "From Date should be less Than To Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_from.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select To Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                } else if (click_detect_flag.equals("to_date", ignoreCase = true)) {
                    if (!dcr_from.text.toString().equals("", ignoreCase = true)) {
                        val s = CheckDates(dcr_from.text.toString(), date)
                        if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                            dcr_to.setText(date)

//                            isInternetPresent = cd!!.isConnectingToInternet
//
//                            if (isInternetPresent) {
//                                getdata("")
//
//                            } else {
//                                val toast = Toast.makeText(this,
//                                        "Internet Not Available. ", Toast.LENGTH_SHORT)
//                                toast.setGravity(Gravity.CENTER, 0, 0)
//                                toast.show()
//                                finish()
//                            }

                        } else if (s.equals("t", ignoreCase = true)) {
                            dcr_to.setText("")
                            val toast = Toast.makeText(applicationContext, "To Date should be Greater Than from Date ", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }
                    } else {
                        dcr_to.setText(date)
                        val toast = Toast.makeText(applicationContext, "Please Select From Date ", Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            }, mYear, mMonth, mDay)
            datePickerDialog?.setThemeDark(false)
            //datePickerDialog?.setYearRange(2017, Year)
            datePickerDialog?.showYearPickerFirst(false)
            datePickerDialog?.setAccentColor(Color.parseColor("#303F9F"))
            datePickerDialog?.setTitle("Select To Date")
            datePickerDialog?.show(fragmentManager, "Smart Anchor App")

        }

        btn_ok.setOnClickListener {

            if(dcr_from.text.toString().equals("")) {
                val toast = Toast.makeText(applicationContext, "Please Select From Date", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }else if(dcr_to.text.toString().equals("")) {
                val toast = Toast.makeText(applicationContext, "Please Select To Date", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }else if(item.toString().equals("Select User")){
                val toast = Toast.makeText(applicationContext, "Please Select User", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()

            }else{
                val intent = Intent(this@ReportFilterActivity, DCRActivity::class.java)
                intent.putExtra("DCR_FROMDATE", dcr_from.text.toString())
                intent.putExtra("DCR_TODATE", dcr_to.text.toString())
                intent.putExtra("DCR_USER", item)
                startActivity(intent)
            }
        }

        btn_clear.setOnClickListener {
            dcr_from.setText("")
            dcr_to.setText("")
            //Global_Data.usernameArray.add("Select User")

//                val hashSet = HashSet<String>()
//                hashSet.addAll(Global_Data.usernameArray)
//                Global_Data.usernameArray.clear()
//                Global_Data.usernameArray.addAll(hashSet)
//
//                dataAdapterusername = ArrayAdapter<String>(this@ReportFilterActivity, R.layout.spinner_item, Global_Data.usernameArray!!)
//                dataAdapterusername!!.setDropDownViewResource(R.layout.spinner_item)
//                spindcr.adapter = dataAdapterusername
//                dataAdapterusername!!.notifyDataSetChanged()
            //spindcr.setSelection(Global_Data.usernameArray.get(0))
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
        // TODO Auto-generated method stub
        //super.onBackPressed();
        val i = Intent(this@ReportFilterActivity, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(i)
        finish()
    }

    fun CheckDates(from_date: String?, to_date: String?): String? {
        val dfDate = SimpleDateFormat("dd-MM-yyyy")
        var b = ""
        try {
            b = if (dfDate.parse(from_date).before(dfDate.parse(to_date))) {
                "f" //If start date is before end date
            } else if (dfDate.parse(from_date) == dfDate.parse(to_date)) {
                "a" //If two dates are equal
            } else {
                "t" //If start date is after the end date
            }
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return b
    }
}