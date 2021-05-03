package com.anchor.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.anchor.activities.Global_Data
import com.anchor.activities.R
import com.anchor.model.DCRModel
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class DCRAdapter(private val mContext: Activity, private val rtododatalist: List<DCRModel>) : RecyclerView.Adapter<DCRAdapter.MyViewHolder>() {

     inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var dcr_date: TextView
        var cc_code: TextView
        var customer_type: TextView
        var feedback: TextView
        var claim: TextView
        var order_count_details: TextView
        var promotional_activity: TextView

        init {
            dcr_date = view.findViewById(R.id.dcr_date)
            cc_code = view.findViewById(R.id.cc_code)
            customer_type = view.findViewById(R.id.customer_type)

            feedback = view.findViewById(R.id.feedback)
            claim = view.findViewById(R.id.claim)
            order_count_details = view.findViewById(R.id.order_count_details)
            promotional_activity = view.findViewById(R.id.promotional_activity)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.d_c_r_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = rtododatalist[position]
        holder.dcr_date.text = data.date
        holder.cc_code.text = data.shop_name
        holder.customer_type.text = data.customer_type
        holder.feedback.text = data.feedback

        holder.claim.text = data.claim
        holder.order_count_details.text = data.order_count_details
        holder.promotional_activity.text = data.promotional_activity

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

//        holder.rtc_mobile.setOnClickListener {
//            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.rtc_mobile_hidden.text.toString())) {
//
//                try {
//                    (mContext as RetailerTDCustomerList).CaalF(holder.rtc_mobile_hidden.text.toString())
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//
//            } else {
//                Toast.makeText(mContext, "Contact No Not Found.", Toast.LENGTH_LONG).show()
//            }
//
//        }



        holder.order_count_details.setOnClickListener {
//            if (data.ordertaken.equals("0") || data.returnorder.equals("0") || data.noorder.equals("0")){
//                Toast.makeText(mContext,"Record not available",Toast.LENGTH_SHORT).show()

            var ordertaken=data.ordertaken
            var returnorder=data.returnorder
            var noorder=data.noorder

 if((ordertaken.equals("0")) && (returnorder.equals("0")) && (noorder.equals("0")))
 {
     Toast.makeText(mContext,"Record not available",Toast.LENGTH_SHORT).show()

            }else{
                val dialognew = Dialog(mContext)
                dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialognew.setCancelable(false)
                dialognew.setContentView(R.layout.dcr_report2)

                val ordertakan = dialognew.findViewById<TextView>(R.id.ordertakan)
                val returnorder = dialognew.findViewById<TextView>(R.id.returnorder)
                val noorder = dialognew.findViewById<TextView>(R.id.noorder)
                val closeBtn = dialognew.findViewById<ImageView>(R.id.dcr_cross)



                ordertakan.setText(data.ordertaken)
                returnorder.setText(data.returnorder)
                noorder.setText(data.noorder)

     closeBtn.setOnClickListener { dialognew.dismiss() }
     dialognew.show()

//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
//            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
//        }
//
//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
//            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
//        }

                
            }



        }

        holder.promotional_activity.setOnClickListener {
            var promotionalType=data.promotionalType
            var Intime=data.Intime
            var Outtime=data.Outtime

           // if (data.promotionalType.equals("0") && data.promotionalType.equals("") &&  data.Intime.equals("0") &&data.Intime.equals("") && data.Outtime.equals("0") && data.Outtime.equals("")){
            if((promotionalType.equals("")) && (Intime.equals("")) && (Outtime.equals("")))
            {
                Toast.makeText(mContext,"Record not available",Toast.LENGTH_SHORT).show()

            }else{
                val dialognew = Dialog(mContext)
                dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialognew.setCancelable(false)
                dialognew.setContentView(R.layout.dcr_dialog)

                val promotioanltype = dialognew.findViewById<TextView>(R.id.promotioanltype)
                val intime = dialognew.findViewById<TextView>(R.id.intime)
                val outtime = dialognew.findViewById<TextView>(R.id.outtime)
                val closeBtn = dialognew.findViewById<ImageView>(R.id.dcr_cross)

                promotioanltype.setText(data.promotionalType)
                intime.setText(data.Intime)
                outtime.setText(data.Outtime)

                closeBtn.setOnClickListener { dialognew.dismiss() }
                dialognew.show()
            }


//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LATITUDE)) {
//            att_lat.setText("Latitude : " + Global_Data.GLOvel_LATITUDE);
//        }
//
//        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(Global_Data.GLOvel_LONGITUDE)) {
//            att_long.setText("Longitude : " + Global_Data.GLOvel_LONGITUDE);
//        }



        }
    }

    override fun getItemCount(): Int {
        return rtododatalist.size
    }
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