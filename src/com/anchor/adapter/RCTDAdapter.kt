package com.anchor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.anchor.activities.R
import com.anchor.activities.RetailerTDCustomerList
import com.anchor.activities.TodoEditCustomer
import com.anchor.model.RCTOData
import cpm.simplelogic.helper.Check_Null_Value
import java.text.DecimalFormat

class RCTDAdapter(private val mContext: Context, private val rtododatalist: List<RCTOData>) : RecyclerView.Adapter<RCTDAdapter.MyViewHolder>() {
    var formatter = DecimalFormat("#,##,##,###.00")

    var GPSStatus: TextView? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rtc_name: TextView
        var rtc_address: TextView
        var rt_id: TextView
        var rtc_mobile_hidden: TextView
        var rtc_email_hidden: TextView
        var rtc_mobile: ImageView
        var rtcodo_container: RelativeLayout
        var rtcodo_containercard: CardView
        var approvedStatus: ImageView? = null
        var verifiedStatus: ImageView? = null
        var pendingForApprovalStatus: ImageView? = null
        var partiallyFilledStatus: ImageView? = null
        var rejectedStatus: ImageView? = null
        var incompleteStatus: ImageView? = null

        init {
            rtc_name = view.findViewById(R.id.rtc_name)
            rtc_address = view.findViewById(R.id.rtc_address)
            rtc_mobile = view.findViewById(R.id.rtc_mobile)
            rt_id = view.findViewById(R.id.rt_id)
            rtc_mobile_hidden = view.findViewById(R.id.rtc_mobile_hidden)
            rtc_email_hidden = view.findViewById(R.id.rtc_email_hidden)
            rtcodo_container = view.findViewById(R.id.rtcodo_container)
            rtcodo_containercard = view.findViewById(R.id.rtcodo_containercard)
            verifiedStatus = view.findViewById(R.id.verified_status_icon)
            approvedStatus = view.findViewById(R.id.approved_icon)
            pendingForApprovalStatus = view.findViewById(R.id.pendingforapproval_icon)
            partiallyFilledStatus = view.findViewById(R.id.partiallyfilled_icon)
            rejectedStatus = view.findViewById(R.id.rejected_icon)
            incompleteStatus = view.findViewById(R.id.incomplete_icon)
            GPSStatus = view.findViewById(R.id.gps_status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.rctdadapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = rtododatalist[position]
        holder.rtc_name.text = data.code +" - "+data.shop_name
        holder.rtc_address.text = data.full_address
        holder.rt_id.text = data.code
        holder.rtc_mobile_hidden.text = data.mobile
        holder.rtc_email_hidden.text = data.email
        holder.rtcodo_containercard.setCardBackgroundColor(Color.parseColor(data.card_color_code))

        if (com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.mobile_verified).equals("true")) {
            holder.verifiedStatus!!.visibility=View.VISIBLE
            //verifiedStatus!!.setImageResource(R.drawable.verified_yes)
        }else{
            holder.verifiedStatus!!.visibility=View.GONE
        }
//        else {
//           // verifiedStatus!!.setImageResource(R.drawable.verified_no)
//        }

        if (com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("approved", ignoreCase = true)) {
            holder.approvedStatus!!.visibility=View.VISIBLE
            holder.partiallyFilledStatus!!.visibility=View.GONE
            holder.rejectedStatus!!.visibility=View.GONE
            holder.pendingForApprovalStatus!!.visibility=View.GONE
            holder.incompleteStatus!!.visibility=View.GONE
        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("partially_filled", ignoreCase = true)) {
            holder.approvedStatus!!.visibility=View.GONE
            holder.partiallyFilledStatus!!.visibility=View.VISIBLE
            holder.rejectedStatus!!.visibility=View.GONE
            holder.pendingForApprovalStatus!!.visibility=View.GONE
            holder.incompleteStatus!!.visibility=View.GONE
        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("rejected", ignoreCase = true)) {
            holder.approvedStatus!!.visibility=View.GONE
            holder.partiallyFilledStatus!!.visibility=View.GONE
            holder.rejectedStatus!!.visibility=View.VISIBLE
            holder.pendingForApprovalStatus!!.visibility=View.GONE
            holder.incompleteStatus!!.visibility=View.GONE
        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("pending_for_approval", ignoreCase = true)) {
            holder.approvedStatus!!.visibility=View.GONE
            holder.partiallyFilledStatus!!.visibility=View.GONE
            holder.rejectedStatus!!.visibility=View.GONE
            holder.pendingForApprovalStatus!!.visibility=View.VISIBLE
            holder.incompleteStatus!!.visibility=View.GONE
        }else if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.bucket_name).equals("incomplete", ignoreCase = true)) {
            holder.approvedStatus!!.visibility=View.GONE
            holder.partiallyFilledStatus!!.visibility=View.GONE
            holder.rejectedStatus!!.visibility=View.GONE
            holder.pendingForApprovalStatus!!.visibility=View.GONE
            holder.incompleteStatus!!.visibility=View.VISIBLE
        }else{
            holder.approvedStatus!!.visibility=View.GONE
            holder.partiallyFilledStatus!!.visibility=View.GONE
            holder.rejectedStatus!!.visibility=View.GONE
            holder.pendingForApprovalStatus!!.visibility=View.GONE
            holder.incompleteStatus!!.visibility=View.GONE
        }

        if(com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.latitude).equals("", ignoreCase = true) && com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck(data.longitude).equals("", ignoreCase = true)) {
            GPSStatus!!.visibility=View.VISIBLE
        }else{
            GPSStatus!!.visibility=View.INVISIBLE
        }

        holder.rtcodo_container.setOnClickListener {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.rt_id.text.toString())) {

                val i = Intent(mContext, TodoEditCustomer::class.java)
                i.putExtra("code", data.code)
                i.putExtra("shop_name", data.shop_name)
                i.putExtra("address", data.address)
                i.putExtra("address_line2", data.address_line2)
                i.putExtra("pincode", data.pincode)
                i.putExtra("landmark", data.landmark)
                i.putExtra("state_code", data.state_code)
                i.putExtra("city_code", data.city_code)
                i.putExtra("mobile_no", data.mobile)
                i.putExtra("email", data.email)
                i.putExtra("tsi_code", data.tsi_code)
                i.putExtra("gst_no", data.gst_no)
                i.putExtra("aadhar_no", data.aadhar_no)
                i.putExtra("pan_no", data.pan_no)
                i.putExtra("latitude", data.latitude)
                i.putExtra("longitude", data.longitude)
                i.putExtra("power_dealer", data.power_dealer)
                i.putExtra("lighting_dealer", data.lighting_dealer)
                i.putExtra("iaq_dealer", data.iaq_dealer)
                i.putExtra("source_of_data", data.source_of_data)
                i.putExtra("district_id", data.dist_code)
                i.putExtra("proprietor_name", data.proprietor_name)
                i.putExtra("cardcolor", data.card_color_code)
                i.putExtra("from_flag", "todo")
                i.putExtra("is_approved", data.is_approved)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                mContext.startActivity(i)
            }
        }

        holder.rtc_mobile.setOnClickListener {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.rtc_mobile_hidden.text.toString())) {

                try {
                    (mContext as RetailerTDCustomerList).CaalF(holder.rtc_mobile_hidden.text.toString())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                Toast.makeText(mContext, "Contact No Not Found.", Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun getItemCount(): Int {
        return rtododatalist.size
    }

}