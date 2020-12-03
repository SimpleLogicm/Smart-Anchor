package com.anchor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.activities.RetailerTDCustomerList;
import com.anchor.model.RTODODATA;

import java.text.DecimalFormat;
import java.util.List;
import cpm.simplelogic.helper.Check_Null_Value;

public class RTODOList_Adapter extends RecyclerView.Adapter<RTODOList_Adapter.MyViewHolder> {
    private List<RTODODATA> rtododatalist;
    private Context mContext;


    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView rt_count,rt_text,rt_id;
        public RelativeLayout rtodo_container;
        public CardView rtodo_containercard;

        public MyViewHolder(View view) {
            super(view);
            rt_count = view.findViewById(R.id.rt_count);
            rt_text = view.findViewById(R.id.rt_text);
            rt_id = view.findViewById(R.id.rt_id);
            rtodo_container = view.findViewById(R.id.rtodo_container);
            rtodo_containercard = view.findViewById(R.id.rtodo_containercard);


        }
    }


    public RTODOList_Adapter(Context context, List<RTODODATA> rtododata) {
        mContext = context;
        this.rtododatalist = rtododata;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rtodo_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final RTODODATA rtododatam = rtododatalist.get(position);

        holder.rt_count.setText(rtododatam.getCount());
        holder.rt_text.setText(rtododatam.getText());
        holder.rt_id.setText(rtododatam.getId());
        holder.rt_count.setBackgroundColor(Color.parseColor(rtododatam.getCount_color_code()));
        holder.rtodo_containercard.setCardBackgroundColor(Color.parseColor(rtododatam.getCard_color_code()));


        holder.rtodo_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.rt_id.getText().toString()))
                {
                    try {
                       int count = Integer.parseInt(holder.rt_count.getText().toString());

                       if(count > 0)
                       {
                           Intent i = new Intent(mContext, RetailerTDCustomerList.class);
                           i.putExtra("id", holder.rt_id.getText().toString().trim());
                           i.putExtra("cardcolor", rtododatam.getCard_color_code());
                           i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           mContext.startActivity(i);
                       }
                       else
                       {
                           Toast toast = Toast.makeText(mContext, "Retailer Not Found.", Toast.LENGTH_SHORT);
                           toast.setGravity(Gravity.CENTER, 0, 0);
                           toast.show();
                       }
                    }catch (Exception e) {
                        e.printStackTrace();

                        Intent i = new Intent(mContext, RetailerTDCustomerList.class);
                        i.putExtra("id", holder.rt_id.getText().toString().trim());
                        i.putExtra("cardcolor", rtododatam.getCard_color_code());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(i);

                    }

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return rtododatalist.size();
    }







}
