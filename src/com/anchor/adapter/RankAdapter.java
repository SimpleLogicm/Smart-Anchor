package com.anchor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anchor.activities.Home;
import com.anchor.activities.R;
import com.anchor.model.RankDataModel;

import java.util.ArrayList;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.MyViewHolder> {

    private ArrayList<RankDataModel> dataSet;
    private Context mContext;

    EventListener listener;

    public interface EventListener {
        void onEvent(String data,String rank);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView r_valuedate;
        TextView r_valuescore;
        TextView r_valuerank;
        LinearLayout r_adapter_container;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.r_valuedate = (TextView) itemView.findViewById(R.id.r_valuedate);
            this.r_valuescore = (TextView) itemView.findViewById(R.id.r_valuescore);
            this.r_valuerank = (TextView) itemView.findViewById(R.id.r_valuerank);
            this.r_adapter_container = (LinearLayout) itemView.findViewById(R.id.r_adapter_container);
        }
    }

    public RankAdapter(ArrayList<RankDataModel> data,Context context,EventListener listener) {
        this.dataSet = data;
        this.mContext = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_rank_adapter, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView r_valuedate = holder.r_valuedate;
        TextView r_valuescore = holder.r_valuescore;
        TextView r_valuerank = holder.r_valuerank;


        r_valuedate.setText(dataSet.get(listPosition).getDate());
        r_valuescore.setText(dataSet.get(listPosition).getScore());
        r_valuerank.setText(dataSet.get(listPosition).getRank());

        holder.r_adapter_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onEvent(dataSet.get(listPosition).getDate(),dataSet.get(listPosition).getRank());

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
