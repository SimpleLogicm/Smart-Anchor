package com.anchor.activities

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anchor.adapter.AttendanceAdapter
import com.anchor.adapter.DCRAdapter
import com.anchor.model.AttendanceModel
import com.anchor.model.DCRModel
import com.cooltechworks.views.shimmer.ShimmerRecyclerView
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.activity_attendance.*
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AttendanceActivity : Activity(), DatePickerDialog.OnDateSetListener {
    private val recyclerView: ShimmerRecyclerView? = null
    var attendanceAdapter: AttendanceAdapter? = null
    var attendanceModel: MutableList<AttendanceModel> = ArrayList()
    var datePickerDialog: DatePickerDialog? = null
    var click_detect_flag = ""
    var calendar: Calendar? = null
    var Year:kotlin.Int = 0
    var Month:kotlin.Int = 0
    var Day:kotlin.Int = 0
    //var dcrList: List<DCRModel> = ArrayList<DCRModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        var user_name = ""
        if (!Global_Data.USER_FIRST_NAME.equals("null", ignoreCase = true)) {
            user_name = Global_Data.USER_FIRST_NAME.trim { it <= ' ' }
            if (!Global_Data.USER_LAST_NAME.equals("null", ignoreCase = true)) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim { it <= ' ' }
            }
        }

        txtWelcomeUserDcr.setText(user_name + " : " + Global_Data.emp_code)

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
            mTitleTextView.text = "Attendance"
            val todaysTarget = mCustomView.findViewById<TextView>(R.id.todaysTarget)
            val sp: SharedPreferences = this@AttendanceActivity.getSharedPreferences("SimpleLogic", 0)

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

        attendanceModel.add(AttendanceModel("26-Nov-2020", "XYZ ABCDEF", "09:00", "09:00"))
        attendanceModel.add(AttendanceModel("26-Nov-2020", "XYZ ABCDEF", "09:00", "09:00"))
        attendanceModel.add(AttendanceModel("26-Nov-2020", "XYZ ABCDEF", "09:00", "09:00"))
        attendanceModel.add(AttendanceModel("26-Nov-2020", "XYZ ABCDEF", "09:00", "09:00"))

        attendanceAdapter = AttendanceAdapter(this, attendanceModel)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        dcrreport_recycler_view?.setLayoutManager(mLayoutManager)
        dcrreport_recycler_view?.setItemAnimator(DefaultItemAnimator())
        dcrreport_recycler_view?.setAdapter(attendanceAdapter)

//        dcrreport_recycler_view?.showShimmerAdapter()
        // dcrreport_recycler_view?.hideShimmerAdapter()


        // ConnectFTP();
        dcr_from.setOnClickListener {
            Global_Data.hideSoftKeyboard(this@AttendanceActivity)
            click_detect_flag = "from_date"
            datePickerDialog = DatePickerDialog.newInstance(this@AttendanceActivity, Year, Month, Day)
            datePickerDialog?.setThemeDark(false)
            datePickerDialog?.showYearPickerFirst(false)
            //datePickerDialog?.setYearRange(2017, Year)
            datePickerDialog?.setAccentColor(Color.parseColor("#303F9F"))
            datePickerDialog?.setTitle("Select From Date")
            datePickerDialog?.show(fragmentManager, "Smart Anchor App")
        }

        dcr_to.setOnClickListener {
            Global_Data.hideSoftKeyboard(this@AttendanceActivity)
            click_detect_flag = "to_date"
            datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day)
            datePickerDialog?.setThemeDark(false)
            //datePickerDialog?.setYearRange(2017, Year)
            datePickerDialog?.showYearPickerFirst(false)
            datePickerDialog?.setAccentColor(Color.parseColor("#303F9F"))
            datePickerDialog?.setTitle("Select To Date")
            datePickerDialog?.show(fragmentManager, "Dealer App")
        }

        iv_download.setOnClickListener {
            showPopupMenu(iv_download)
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
        val i = Intent(this@AttendanceActivity, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        startActivity(i)
        finish()
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val mFormat = DecimalFormat("00")
        val date = mFormat.format(java.lang.Double.valueOf(dayOfMonth.toDouble())) + "-" + mFormat.format(java.lang.Double.valueOf(monthOfYear + 1.toDouble())) + "-" + year

        if (click_detect_flag.equals("from_date", ignoreCase = true)) {
            if (!dcr_to.text.toString().equals("", ignoreCase = true)) {
                val s = CheckDates(date, dcr_to.text.toString())
                if (s.equals("f", ignoreCase = true) || s.equals("a", ignoreCase = true)) {
                    dcr_from.setText(date)
//                    button.setEnabled(false)
//                    button.setClickable(false)
//                    recyclerView!!.showShimmerAdapter()
//                    cd = ConnectionDetector(applicationContext)
//                    if (cd.isConnectedToInternet()) {
//                        InvoicesList_Result()
//                    } else {
//                        recyclerView!!.showShimmerAdapter()
//                        val toast = Toast.makeText(this@DCRActivity, "You don't have internet connection.", Toast.LENGTH_LONG)
//                        toast.setGravity(Gravity.CENTER, 0, 0)
//                        toast.show()
//                    }
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
//                    recyclerView!!.showShimmerAdapter()
//                    button.setEnabled(false)
//                    button.setClickable(false)
//                    cd = ConnectionDetector(applicationContext)
//                    if (cd.isConnectedToInternet()) {
//                        InvoicesList_Result()
//                    } else {
//                        val toast = Toast.makeText(this@DCRActivity, "You don't have internet connection.", Toast.LENGTH_LONG)
//                        toast.setGravity(Gravity.CENTER, 0, 0)
//                        toast.show()
//                    }
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

    private fun showPopupMenu(view: View) {
        // inflate menu
        // string=view.title.toString()
        val popup = PopupMenu(this, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_dcr, popup.menu)
        popup.setOnMenuItemClickListener(MyMenuItemClickListener())
        popup.show()
    }

    internal inner class MyMenuItemClickListener : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.action_xlsx -> {
//                    status="resource"
//                    isInternetPresent = cd!!.isConnectingToInternet
//                    if (isInternetPresent) {
//                        ExpenseGraphResult(status)
//                    } else {
//                        Toast.makeText(this@DCRActivity, "You don't have internet connection.", Toast.LENGTH_SHORT).show()
//                    }
                    return true
                }
                R.id.action_pdf -> {
//                    status="type"
//                    isInternetPresent = cd!!.isConnectingToInternet
//                    if (isInternetPresent) {
//                        ExpenseGraphResult(status)
//                    } else {
//                        Toast.makeText(this@DCRActivity, "You don't have internet connection.", Toast.LENGTH_SHORT).show()
//                    }
                    return true
                }
            }
            return false
        }
    }

//   fun dcrDialog() {
//       val dialognew = Dialog(this@DCRActivity)
//       dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE)
//       dialognew.setCancelable(false)
//       dialognew.setContentView(R.layout.promotion_dialog)
//
//       val pro_header = dialognew.findViewById<TextView>(R.id.pro_header)
//       val pro_time = dialognew.findViewById<TextView>(R.id.pro_time)
//       val pro_date = dialognew.findViewById<TextView>(R.id.pro_date)
//       val pro_address = dialognew.findViewById<TextView>(R.id.pro_address)
//       val pro_click = dialognew.findViewById<Button>(R.id.pro_click)
//
//
//       val sdf = SimpleDateFormat("dd-MM-yyyy")
//       val date = Date()
//       // String daten = sdf.format(date);
//       // String daten = sdf.format(date);
//       val datenew = sdf.format(date)
//
//       val d = Date()
//       val sdf_time = SimpleDateFormat("hh:mm a")
//       val currentDateTimeString = sdf_time.format(d)
//
////        if (flag.equals("OUT", ignoreCase = true)) {
////            pro_header.text = "OUT TIME"
////        } else {
////            pro_header.text = "IN TIME"
////            outDateTime = ""
////        }
//
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
////            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
////        }
////
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
////            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
////        }
//
//
//       //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//
//
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
////            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
////        }
////
////        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
////            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
////        }
//
//
//       //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
//       if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.address)) {
//           val str = Global_Data.address.trim { it <= ' ' }.replace("\n".toRegex(), " ")
//           pro_address.text = "Address : $str"
//       } else {
//           pro_address.text = "Address Not Found."
//       }
//
//       if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(datenew)) {
//           try {
//               val timenewarray = datenew.split("-".toRegex()).toTypedArray()
//               val month: String = getMonthForInt(timenewarray[1].toInt() - 1)
//               pro_date.text = "Date : " + timenewarray[0] + " " + month + " " + timenewarray[2]
//           } catch (ex: java.lang.Exception) {
//               ex.printStackTrace()
//           }
//       } else {
//           pro_date.text = "Date Not Found."
//       }
//
//       pro_time.text = "Time : $currentDateTimeString"
//
//       pro_click.setOnClickListener { dialognew.dismiss() }
//
//       dialognew.show()
//   }
}