package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import cpm.simplelogic.helper.TargetValue_info;

public class TargetCard_Adapter extends RecyclerView.Adapter<TargetCard_Adapter.ContactViewHolder> {

    private List<TargetValue_info> contactList;

    public TargetCard_Adapter(List<TargetValue_info> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        TargetValue_info ci = contactList.get(i);
        contactViewHolder.product_value.setText(ci.product_value);
        contactViewHolder.product_Sub_value.setText(ci.product_Sub_value);
        contactViewHolder.target_value.setText(ci.target_value);
        contactViewHolder.achieved_value.setText(ci.achieved_value);
        contactViewHolder.age_value.setText(ci.age_value);
        contactViewHolder.target_date.setText(ci.target_date);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.target_card, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView product_value;
        protected TextView product_Sub_value;
        protected TextView target_value;
        protected TextView achieved_value;
        protected TextView age_value;
        protected TextView target_date;

        public ContactViewHolder(View v) {
            super(v);
            product_value = v.findViewById(R.id.product_value);
            product_Sub_value = v.findViewById(R.id.product_Sub_value);
            target_value = v.findViewById(R.id.target_value);
            achieved_value = v.findViewById(R.id.achieved_value);
            age_value = v.findViewById(R.id.age_value);
            target_date = v.findViewById(R.id.target_date);
        }
    }
}
