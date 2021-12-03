package com.anchor.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.Local_Data;
import com.anchor.activities.R;
import com.anchor.model.Spiner_List_Model;
import com.anchor.model.StateModel;

import java.util.ArrayList;
import java.util.List;

public class StateSpinnerList_Adapter extends
        RecyclerView.Adapter<StateSpinnerList_Adapter.ViewHolder> {

    private List<StateModel> stList ;
    Context mcontext;

    public StateSpinnerList_Adapter(Context mcontext, List<StateModel> stList) {
//        if (itemsData != null)
//            stList.addAll(itemsData);
//        this.mcontext = context;
        this.mcontext=mcontext;
        this.stList=stList;

    }

    // Create new views
    @Override
    public StateSpinnerList_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.spinner_list_item, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
//        final Local_Data mItem = stList.get(position);
//        viewHolder.tvName.setText(mItem.getStateName());
//
//        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mItem.isSelected) {
//                    viewHolder.chkSelected.setChecked(false);
//                    mItem.isSelected = false;
//                }
//            }
//        });
//
//        if (mItem.isSelected) {
//            viewHolder.chkSelected.setChecked(true);
//        } else {
//            viewHolder.chkSelected.setChecked(false);
//        }


//        viewHolder.chkSelected.setText("Checkbox " + position);
//        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());
        viewHolder.tvName.setText(stList.get(position).getName());
        viewHolder.chkSelected.setTag(position);

        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) viewHolder.chkSelected.getTag();
                Toast.makeText(mcontext, stList.get(pos).getName() + " clicked!", Toast.LENGTH_SHORT).show();

                if (stList.get(pos).isSelected()) {
                    stList.get(pos).setSelected(false);
                }
                else {
                    stList.get(pos).setSelected(true);
                }
            }
        });


    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public List<StateModel> getList() {
        return stList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvEmailId;

        public CheckBox chkSelected;
        CardView card_view;
        public Spiner_List_Model singleSpiner_List_Model;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = itemLayoutView.findViewById(R.id.p_varient_name);

            chkSelected = itemLayoutView.findViewById(R.id.pv_chkSelected);
            card_view = itemLayoutView.findViewById(R.id.card_view);

        }

    }

    // method to access in activity after updating selection
    public List<StateModel> getSpiner_List_Modelist() {
        return stList;
    }

}