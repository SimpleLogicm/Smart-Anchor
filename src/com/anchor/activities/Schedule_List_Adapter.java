package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cpm.simplelogic.helper.ContactInfo;

public class Schedule_List_Adapter extends RecyclerView.Adapter<Schedule_List_Adapter.ContactViewHolder> {

    private List<ContactInfo> contactList;

    public Schedule_List_Adapter(List<ContactInfo> contactList) {
        this.contactList = contactList;
    }


    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        ContactInfo ci = contactList.get(i);
        contactViewHolder.vName.setText("Order Number : " + ci.order_number);
        contactViewHolder.vSurname.setText("Location : " + ci.location);
        contactViewHolder.customers_id.setText("Customer Code : " + ci.Cust_Code);
        //contactViewHolder.vEmail.setText(ci.order_number);
        //contactViewHolder.txtmrp.setText(ci.location);
        contactViewHolder.vTitle.setText(" "+ci.date1);
        contactViewHolder.cche_id.setText(ci.Cust_Code);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.schedule_list_adapter, viewGroup, false);



        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;
        protected TextView txtmrp,cche_id,customers_id;

        public ContactViewHolder(View v) {
            super(v);

            vName = v.findViewById(R.id.txtName);
            vSurname = v.findViewById(R.id.txtSurname);
            //vEmail = (TextView)  v.findViewById(R.id.txtEmail);
           vTitle = v.findViewById(R.id.title);

            cche_id = v.findViewById(R.id.cche_id);

            customers_id = v.findViewById(R.id.customers_id);
            //txtmrp = (TextView) v.findViewById(R.id.txtmrp);

            v.setOnClickListener(this);
        }

        // Handles the row being being clicked
        @Override
        public void onClick(View view) {
            int position = getLayoutPosition(); // gets item position
           // ContactInfo ci = contactList.get(i);
            // We can access the data within the views
            String s = "";
            String[] s_order = vName.getText().toString().split(":");
            s = s_order[1];

            String customer_id = "";
            customer_id = cche_id.getText().toString().trim();

            // Toast.makeText(view.getContext(), s.trim(), Toast.LENGTH_SHORT).show();
            Log.d("Order id","Order id"+s.trim());

            Intent intent = new Intent(view.getContext(), Schedule1.class);
            intent.putExtra("order_id",s.trim());
            intent.putExtra("customer_id",customer_id);
            view.getContext().startActivity(intent);
            ((Activity)view.getContext()).finish();
        }


    }
}
