package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cpm.simplelogic.helper.ContactInfo;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactInfo> contactList;
    private Context mContext;
    public ContactAdapter(Context context, List<ContactInfo> contactList) {
        mContext = context;
        this.contactList = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ContactInfo ci = contactList.get(i);

        // for label RP change
        SharedPreferences spf1=mContext.getSharedPreferences("SimpleLogic",0);
        String rpstr=spf1.getString("var_rp", "");
        //txt_rp.setText(rpstr);

        contactViewHolder.vName.setText(rpstr);
        contactViewHolder.vSurname.setText("MRP");
        contactViewHolder.vEmail.setText(ci.rp);
        contactViewHolder.txtmrp.setText(ci.mrp);
        contactViewHolder.vTitle.setText(" "+ci.name);
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.pricing_adapter, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;
        protected TextView txtmrp;

        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.txtName);
            vSurname = (TextView)  v.findViewById(R.id.txtSurname);
            vEmail = (TextView)  v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.title);
            txtmrp = (TextView) v.findViewById(R.id.txtmrp);
        }
    }
}
