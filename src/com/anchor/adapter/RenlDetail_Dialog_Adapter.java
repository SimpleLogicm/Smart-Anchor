package com.anchor.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.RenkDialogModel;

import java.util.ArrayList;

public class RenlDetail_Dialog_Adapter extends RecyclerView.Adapter<RenlDetail_Dialog_Adapter.RenlDetailViewHolder>  {
    private ArrayList<RenkDialogModel> dataSet;


    public RenlDetail_Dialog_Adapter(ArrayList<RenkDialogModel> data) {
        this.dataSet = data;

    }

    @NonNull
    @Override
    public RenlDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.renldetail_dialog_adapter, parent, false);
        RenlDetailViewHolder vh = new RenlDetailViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull RenlDetailViewHolder fruitViewHolder, int i) {
        fruitViewHolder.detailskey_text.setText(dataSet.get(i).getKey()+" : ");
        fruitViewHolder.detailskey_value.setText(dataSet.get(i).getValue());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }



    public  class RenlDetailViewHolder extends RecyclerView.ViewHolder {

        public TextView detailskey_text,detailskey_value;

        public RenlDetailViewHolder(View v) {
            super(v);
            detailskey_text =  v.findViewById(R.id.detailskey_text);
            detailskey_value =  v.findViewById(R.id.detailskey_value);

        }


    }


}
