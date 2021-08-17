package com.anchor.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anchor.activities.R
import com.anchor.activities.Schemedetail
import com.anchor.model.Morebuttoninvoicemodel
import com.anchor.model.Schemecirculermodel

class Morebtninvoiceadaptor(private val mContext: Activity, private val rtododatalist: List<Morebuttoninvoicemodel>) : RecyclerView.Adapter<Morebtninvoiceadaptor.MyViewHolder>() {
    private var listener: View.OnClickListener? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var product: TextView
        var qty: TextView
        var unitprice: TextView
        var amount: TextView?=null
        var orderno: TextView?=null
        //var imgbtn: ImageView

        init {
            product = view.findViewById(R.id.product)
            qty = view.findViewById(R.id.qty)
            unitprice = view.findViewById(R.id.unitprice)
            amount = view.findViewById(R.id.amount)
            orderno = view.findViewById(R.id.orderno)
            //   imgbtn = view.findViewById(R.id.imgbtn)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_moreinvoice, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = rtododatalist[position]
        holder.product.text = data.product
        holder.qty.text = data.quan
        holder.unitprice.text = data.unitprice
        holder.amount!!.text = data.amount
        holder.orderno!!.text = data.orderno


    }

    override fun getItemCount(): Int {
        return rtododatalist.size
    }

//    fun setOnClickListener(listener: View.OnClickListener) {
//        this.listener = listener
//    }

}