package com.anchor.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.NewOrderActivity;
import com.anchor.activities.Order;
import com.anchor.activities.PreviewOrderSwipeActivity;
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

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getProduct_variant());


        viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        viewHolder.chkSelected.setTag(stList.get(position));


        viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                Log.d("VVVVVV","VVVVVV"+stList.get(pos).getSmp_flag());
                Log.d("VR","VR"+stList.get(pos).isSelected());
                if(stList.get(pos).getSmp_flag() != null && stList.get(pos).getSmp_flag().equalsIgnoreCase("true") &&  !stList.get(pos).isSelected())
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(mcontext).create();
                    alertDialog.setTitle("Message");
                    alertDialog.setMessage("You are Selecting Slow moving Item for which there is No Sales in Last 6 month.");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            CheckBox cb = (CheckBox) v;
                            Spiner_List_Model contact = (Spiner_List_Model) cb.getTag();

                            contact.setSelected(cb.isChecked());
                            stList.get(pos).setSelected(cb.isChecked());
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CheckBox cb = (CheckBox) v;
                            cb.setChecked(false);
                            dialog.cancel();
                        }
                    });

                    alertDialog.show();
                }
                else
                {
                    CheckBox cb = (CheckBox) v;
                    Spiner_List_Model contact = (Spiner_List_Model) cb.getTag();

                    contact.setSelected(cb.isChecked());
                    stList.get(pos).setSelected(cb.isChecked());
                }
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