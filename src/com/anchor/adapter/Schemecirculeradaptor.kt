package com.anchor.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Paint
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

        var details: TextView
        var mainll: LinearLayout? = null
        //var imgbtn: ImageView

        init {
            date = view.findViewById(R.id.date)
            schemename = view.findViewById(R.id.schemename)
            productname = view.findViewById(R.id.productname)
            mainll = view.findViewById(R.id.main)

            details = view.findViewById(R.id.details)
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
        holder.schemename.text = data.display_name
        holder.productname.text = data.productname

        holder.details.setPaintFlags(holder.details.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)


        holder.details!!.setOnClickListener {
            Global_Data.scheame_type = "schem"
            val i = Intent(mContext, Schemedetail::class.java)
            i.putExtra("scheame_name", data.display_name)
            i.putExtra("product_name", data.productname)
            i.putExtra("qualify_amount", data.qualify_amount)
            i.putExtra("qualify_quan", data.qualifying_quantity)
            if(data.description.equals("")){
                i.putExtra("description", "NA")

            }else{
                i.putExtra("description", data.description)

            }

            if (data.is_discount_scheme=="true"){
                i.putExtra("foc", "DISCOUNT")
            }else{
                i.putExtra("foc", "FOC")
            }
            i.putExtra("discount_in_dp_amount", data.discount_in_dp_amount)
            i.putExtra("discount_in_dp_percent", data.discount_in_dp_percent)
            i.putExtra("foc_qan", data.foc_qty)

            i.putExtra("product_details", data.foc_prod)
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