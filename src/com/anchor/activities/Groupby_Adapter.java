package com.anchor.activities;

/**
 * Created by sujit on 1/4/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import cpm.simplelogic.helper.TargetValue_info;

public class Groupby_Adapter extends RecyclerView.Adapter<Groupby_Adapter.ContactViewHolder> {

    private List<TargetValue_info> contactList;

    public Groupby_Adapter(List<TargetValue_info> contactList) {
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        TargetValue_info ci = contactList.get(i);

        contactViewHolder.month_value.setText(ci.monthgrp_str);
        contactViewHolder.target_value.setText(ci.targetgrp_str);
        contactViewHolder.achieved_value.setText(ci.achievedgrp_str);
        contactViewHolder.age_value.setText(ci.agegrp_str);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.target_grpby_txt, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView month_value;
        protected TextView target_value;
        protected TextView achieved_value;
        protected TextView age_value;

        public ContactViewHolder(View v) {
            super(v);
            month_value = v.findViewById(R.id.month_grpby);
            target_value = v.findViewById(R.id.target_grpby);
            achieved_value = v.findViewById(R.id.achievd_grpby);
            age_value = v.findViewById(R.id.age_grpby);
       }
    }
}
