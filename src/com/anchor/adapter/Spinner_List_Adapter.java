package com.anchor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.Spiner_List_Model;

import java.util.List;

public class Spinner_List_Adapter extends
        RecyclerView.Adapter<Spinner_List_Adapter.ViewHolder> {

    private List<Spiner_List_Model> stList;
    Context mcontext;

    public Spinner_List_Adapter(Context context, List<Spiner_List_Model> Spiner_List_Models) {
        this.stList = Spiner_List_Models;
        this.mcontext = context;

    }

    // Create new views
    @Override
    public Spinner_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.spinner_list_item, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getName());


        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Spiner_List_Model contact = (Spiner_List_Model) cb.getTag();

                contact.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

//                Toast.makeText(
//                        v.getContext(),
//                        "Clicked on Checkbox: " + cb.getText() + " is "
//                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;

        public CheckBox chkSelected;

        public Spiner_List_Model singleSpiner_List_Model;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = itemLayoutView.findViewById(R.id.p_varient_name);

            chkSelected =  itemLayoutView.findViewById(R.id.pv_chkSelected);

        }

    }

    // method to access in activity after updating selection
    public List<Spiner_List_Model> getSpiner_List_Modelist() {
        return stList;
    }

}