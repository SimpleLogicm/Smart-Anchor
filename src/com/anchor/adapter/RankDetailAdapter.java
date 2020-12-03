package com.anchor.adapter;

import android.content.Context;
import android.graphics.Paint;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.model.RankDetailModel;

import java.util.ArrayList;

public class RankDetailAdapter extends RecyclerView.Adapter<RankDetailAdapter.MyViewHolder> {

    private ArrayList<RankDetailModel> dataSet;
    private Context mContext;

    DialogViewListener listener;

    public interface DialogViewListener {
        void onClickDialog(String data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView r_parameter_value;
        TextView r_weighttage_value;
        TextView r_obtained_value;
        TextView r_calculated_value;
        LinearLayout r_detail_adapter_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.r_parameter_value = (TextView) itemView.findViewById(R.id.r_parameter_value);
            this.r_weighttage_value = (TextView) itemView.findViewById(R.id.r_weighttage_value);
            this.r_obtained_value = (TextView) itemView.findViewById(R.id.r_obtained_value);
            this.r_calculated_value = (TextView) itemView.findViewById(R.id.r_calculated_value);
            this.r_detail_adapter_container = (LinearLayout) itemView.findViewById(R.id.r_adapter_container);
        }
    }

    public RankDetailAdapter(ArrayList<RankDetailModel> data,Context context,DialogViewListener listener) {
        this.dataSet = data;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_rank_detail_adapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.r_parameter_value.setText(dataSet.get(listPosition).getR_parameter_value());
        holder.r_weighttage_value.setText(dataSet.get(listPosition).getR_weighttage_value());
        holder.r_obtained_value.setText(dataSet.get(listPosition).getR_obtained_value());
        holder.r_calculated_value.setText(dataSet.get(listPosition).getR_calculated_value());
        //holder.r_parameter_value.setMovementMethod(LinkMovementMethod.getInstance());
        holder.r_parameter_value.setPaintFlags(holder.r_parameter_value.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

        holder.r_parameter_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDialog(dataSet.get(listPosition).getR_parameter_value());

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
