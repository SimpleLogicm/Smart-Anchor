package com.anchor.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.anchor.activities.R
import com.anchor.activities.RetailerTDCustomerList
import com.anchor.activities.TodoEditCustomer
import com.anchor.model.RCTOData
import cpm.simplelogic.helper.Check_Null_Value
import java.text.DecimalFormat

class RCTDAdapter(private val mContext: Context, private val rtododatalist: List<RCTOData>) : RecyclerView.Adapter<RCTDAdapter.MyViewHolder>() {
    var formatter = DecimalFormat("#,##,##,###.00")

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rtc_name: TextView
        var rtc_address: TextView
        var rt_id: TextView
        var rtc_mobile_hidden: TextView
        var rtc_email_hidden: TextView
        var rtc_mobile: ImageView
        var rtcodo_container: RelativeLayout
        var rtcodo_containercard: CardView

        init {
            rtc_name = view.findViewById(R.id.rtc_name)
            rtc_address = view.findViewById(R.id.rtc_address)
            rtc_mobile = view.findViewById(R.id.rtc_mobile)
            rt_id = view.findViewById(R.id.rt_id)
            rtc_mobile_hidden = view.findViewById(R.id.rtc_mobile_hidden)
            rtc_email_hidden = view.findViewById(R.id.rtc_email_hidden)
            rtcodo_container = view.findViewById(R.id.rtcodo_container)
            rtcodo_containercard = view.findViewById(R.id.rtcodo_containercard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.rctdadapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val (id, name, address, card_color_code, mobile, email) = rtododatalist[position]
        holder.rtc_name.text = name
        holder.rtc_address.text = address
        holder.rt_id.text = id
        holder.rtc_mobile_hidden.text = mobile
        holder.rtc_email_hidden.text = email
        holder.rtcodo_containercard.setCardBackgroundColor(Color.parseColor(card_color_code))
        holder.rtcodo_container.setOnClickListener {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.rt_id.text.toString())) {

                val i = Intent(mContext, TodoEditCustomer::class.java)
                i.putExtra("id", holder.rt_id.text.toString().trim { it <= ' ' })
                i.putExtra("cardcolor", card_color_code)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                mContext.startActivity(i)
            }
        }

        holder.rtcodo_container.setOnClickListener {
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