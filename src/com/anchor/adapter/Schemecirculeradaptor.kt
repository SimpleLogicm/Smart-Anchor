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
import com.anchor.activities.Global_Data
import com.anchor.activities.R
import com.anchor.activities.Schemedetail
import com.anchor.model.Schemecirculermodel

class Schemecirculeradaptor(private val mContext: Activity, private val rtododatalist: List<Schemecirculermodel>) : RecyclerView.Adapter<Schemecirculeradaptor.MyViewHolder>() {
    private var listener: View.OnClickListener? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var date: TextView
        var schemename: TextView
        var productname: TextView
        var mainll: LinearLayout? = null
        //var imgbtn: ImageView

        init {
            date = view.findViewById(R.id.date)
            schemename = view.findViewById(R.id.schemename)
            productname = view.findViewById(R.id.productname)
            mainll = view.findViewById(R.id.main)
            //   imgbtn = view.findViewById(R.id.imgbtn)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.rawschemecirculer, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val data = rtododatalist[position]
        holder.date.text = data.valid_till_date
        holder.schemename.text = data.schemename
        holder.productname.text = data.display_name

        holder.mainll!!.setOnClickListener {
            Global_Data.scheame_type = "schem"
            val i = Intent(mContext, Schemedetail::class.java)
            i.putExtra("scheame_name", data.schemename)
            i.putExtra("product_name", data.display_name)
            i.putExtra("qualify_amount", data.qualify_amount)
            i.putExtra("qualify_quan", data.qualifying_quantity)
            i.putExtra("description", data.description)
            i.putExtra("foc", data.foc_prod)
            i.putExtra("product_details", data.productname)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)


            mContext.startActivity(i)

        }

    }

    override fun getItemCount(): Int {
        return rtododatalist.size
    }

//    fun setOnClickListener(listener: View.OnClickListener) {
//        this.listener = listener
//    }

}