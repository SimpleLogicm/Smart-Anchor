package com.anchor.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anchor.activities.R
import com.anchor.model.AttendanceModel
import java.text.DateFormatSymbols

class AttendanceAdapter(private val mContext: Activity, private val rtododatalist: List<AttendanceModel>) : RecyclerView.Adapter<AttendanceAdapter.MyViewHolder>() {
    private var listener: View.OnClickListener? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var attendance_date: TextView
        var user: TextView
        var attendance_in: TextView
        var attendance_out: TextView
        var attendance_info: ImageView

        init {
            attendance_date = view.findViewById(R.id.attendance_date)
            user = view.findViewById(R.id.user)
            attendance_in = view.findViewById(R.id.attendance_in)
            attendance_out = view.findViewById(R.id.attendance_out)
            attendance_info = view.findViewById(R.id.btn_attendanceinfo)

//            attendance_info.setOnClickListener {
//
//                val builder = AlertDialog.Builder(mContext)
//                builder.setTitle("Androidly Alert")
//                builder.setMessage("We have a message")
//                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
//
//                builder.setPositiveButton(android.R.string.yes) { dialog, which ->
//                    Toast.makeText(mContext,
//                            android.R.string.yes, Toast.LENGTH_SHORT).show()
//                }
//
//                builder.setNegativeButton(android.R.string.no) { dialog, which ->
//                    Toast.makeText(mContext,
//                            android.R.string.no, Toast.LENGTH_SHORT).show()
//                }
//
//                builder.setNeutralButton("Maybe") { dialog, which ->
//                    Toast.makeText(mContext,
//                            "Maybe", Toast.LENGTH_SHORT).show()
//                }
//                builder.show()
//            }
            }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.attendance_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = rtododatalist[position]
        holder.attendance_date.text = data.date
        holder.user.text = data.user
        holder.attendance_in.text = data.attendance_in
        holder.attendance_out.text = data.attendance_out
        //holder.attendance_info.setOnClickListener(listener)
//        holder.rtc_name.text = data.code +" - "+data.shop_name
//        holder.rtc_address.text = data.full_address
//        holder.rt_id.text = data.code
//        holder.rtc_mobile_hidden.text = data.mobile
//        holder.rtc_email_hidden.text = data.email
//        holder.rtcodo_containercard.setCardBackgroundColor(Color.parseColor(data.card_color_code))
//
//        if (com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.mobile_verified).equals("true")) {
//            holder.verifiedStatus!!.visibility=View.VISIBLE
//            //verifiedStatus!!.setImageResource(R.drawable.verified_yes)
//        }else{
//            holder.verifiedStatus!!.visibility=View.GONE
//        }
//        else {
//           // verifiedStatus!!.setImageResource(R.drawable.verified_no)
//        }

//        if (com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("approved", ignoreCase = true)) {
//            holder.approvedStatus!!.visibility=View.VISIBLE
//            holder.partiallyFilledStatus!!.visibility=View.GONE
//            holder.rejectedStatus!!.visibility=View.GONE
//            holder.pendingForApprovalStatus!!.visibility=View.GONE
//        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("partially_filled", ignoreCase = true)) {
//            holder.approvedStatus!!.visibility=View.GONE
//            holder.partiallyFilledStatus!!.visibility=View.VISIBLE
//            holder.rejectedStatus!!.visibility=View.GONE
//            holder.pendingForApprovalStatus!!.visibility=View.GONE
//        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("rejected", ignoreCase = true)) {
//            holder.approvedStatus!!.visibility=View.GONE
//            holder.partiallyFilledStatus!!.visibility=View.GONE
//            holder.rejectedStatus!!.visibility=View.VISIBLE
//            holder.pendingForApprovalStatus!!.visibility=View.GONE
//        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("pending_for_approval", ignoreCase = true)) {
//            holder.approvedStatus!!.visibility=View.GONE
//            holder.partiallyFilledStatus!!.visibility=View.GONE
//            holder.rejectedStatus!!.visibility=View.GONE
//            holder.pendingForApprovalStatus!!.visibility=View.VISIBLE
//        }else{
//            holder.approvedStatus!!.visibility=View.GONE
//            holder.partiallyFilledStatus!!.visibility=View.GONE
//            holder.rejectedStatus!!.visibility=View.GONE
//            holder.pendingForApprovalStatus!!.visibility=View.GONE
//        }
//
//        if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.latitude).equals("", ignoreCase = true) && com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.longitude).equals("", ignoreCase = true)) {
//            GPSStatus!!.visibility=View.VISIBLE
//        }else{
//            GPSStatus!!.visibility=View.INVISIBLE
//        }
//
//        holder.rtcodo_container.setOnClickListener {
//            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.rt_id.text.toString())) {
//
//                val i = Intent(mContext, TodoEditCustomer::class.java)
//                i.putExtra("code", data.code)
//                i.putExtra("shop_name", data.shop_name)
//                i.putExtra("address", data.address)
//                i.putExtra("address_line2", data.address_line2)
//                i.putExtra("pincode", data.pincode)
//                i.putExtra("landmark", data.landmark)
//                i.putExtra("state_code", data.state_code)
//                i.putExtra("city_code", data.city_code)
//                i.putExtra("mobile_no", data.mobile)
//                i.putExtra("email", data.email)
//                i.putExtra("tsi_code", data.tsi_code)
//                i.putExtra("gst_no", data.gst_no)
//                i.putExtra("aadhar_no", data.aadhar_no)
//                i.putExtra("pan_no", data.pan_no)
//                i.putExtra("latitude", data.latitude)
//                i.putExtra("longitude", data.longitude)
//                i.putExtra("power_dealer", data.power_dealer)
//                i.putExtra("lighting_dealer", data.lighting_dealer)
//                i.putExtra("iaq_dealer", data.iaq_dealer)
//                i.putExtra("source_of_data", data.source_of_data)
//                i.putExtra("district_id", data.dist_code)
//                i.putExtra("proprietor_name", data.proprietor_name)
//                i.putExtra("cardcolor", data.card_color_code)
//                i.putExtra("from_flag", "todo")
//                i.putExtra("is_approved", data.is_approved)
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                mContext.startActivity(i)
//            }
//        }

        holder.attendance_info.setOnClickListener {
            val dialognew = Dialog(mContext)
            dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialognew.setCancelable(false)
            dialognew.setContentView(R.layout.dialog_attendancedetails)

            val empCode = dialognew.findViewById<TextView>(R.id.emp_code)
            val firstName = dialognew.findViewById<TextView>(R.id.firstname)
            val lastName = dialognew.findViewById<TextView>(R.id.lastname)
            val inTime = dialognew.findViewById<TextView>(R.id.intime)
            val inTimeAddress = dialognew.findViewById<TextView>(R.id.address)
            val outTime = dialognew.findViewById<TextView>(R.id.outtime)
            val OutTimeAddress = dialognew.findViewById<TextView>(R.id.address1)
            val closeBtn = dialognew.findViewById<ImageView>(R.id.attendance_cross)

            empCode.setText(data.empcode)
            firstName.setText(data.firstname)
            lastName.setText(data.lastname)
            inTime.setText(data.attendance_in)
            inTimeAddress.setText(data.punchadd)
            outTime.setText(data.attendance_out)
            OutTimeAddress.setText(data.punchadd)

//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
//            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
//        }
//
//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
//            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
//        }

            closeBtn.setOnClickListener { dialognew.dismiss() }
            dialognew.show()
        }

//        holder.order_count_details.setOnClickListener {
//            val dialognew = Dialog(mContext)
//            dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialognew.setCancelable(false)
//            dialognew.setContentView(R.layout.dcr_dialog)
//
////            val pro_header = dialognew.findViewById<TextView>(R.id.pro_header)
////            val pro_time = dialognew.findViewById<TextView>(R.id.pro_time)
////            val pro_date = dialognew.findViewById<TextView>(R.id.pro_date)
////            val pro_address = dialognew.findViewById<TextView>(R.id.pro_address)
////            val pro_click = dialognew.findViewById<Button>(R.id.pro_click)
////
////
////            val sdf = SimpleDateFormat("dd-MM-yyyy")
////            val date = Date()
////            // String daten = sdf.format(date);
////            // String daten = sdf.format(date);
////            val datenew = sdf.format(date)
////
////            val d = Date()
////            val sdf_time = SimpleDateFormat("hh:mm a")
////            val currentDateTimeString = sdf_time.format(d)
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
//            //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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
////            //inh.setPaintFlags(inh.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
////            if (com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.address)) {
////                val str = Global_Data.address.trim { it <= ' ' }.replace("\n".toRegex(), " ")
////                pro_address.text = "Address : $str"
////            } else {
////                pro_address.text = "Address Not Found."
////            }
////
////            if (com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(datenew)) {
////                try {
////                    val timenewarray = datenew.split("-".toRegex()).toTypedArray()
////                    val month: String = getMonthForInt(timenewarray[1].toInt() - 1)
////                    pro_date.text = "Date : " + timenewarray[0] + " " + month + " " + timenewarray[2]
////                } catch (ex: java.lang.Exception) {
////                    ex.printStackTrace()
////                }
////            } else {
////                pro_date.text = "Date Not Found."
////            }
////
////            pro_time.text = "Time : $currentDateTimeString"
////
////            pro_click.setOnClickListener { dialognew.dismiss() }
//
//            dialognew.show()
//        }
    }

    override fun getItemCount(): Int {
        return rtododatalist.size
    }

//    fun setOnClickListener(listener: View.OnClickListener) {
//        this.listener = listener
//    }

    fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months = dfs.months
        if (num >= 0 && num <= 11) {
            month = months[num]
        }
        return month
    }
}