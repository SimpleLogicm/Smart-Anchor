package com.anchor.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.Global_Data;
import com.anchor.activities.OutstandingDetails;
import com.anchor.activities.R;
import com.anchor.model.OutstandingModel;

import java.text.DecimalFormat;
import java.util.List;


public class OutstandingAdapter extends RecyclerView.Adapter<OutstandingAdapter.MyViewHolder> {
    private List<OutstandingModel> employee_modelList;
    private Context mContext;
    OutstandingModel employee_model;
    private UserAdapterListener listener;
    private UserAdapterListenernew userAdapterListenernew;
    AlertDialog alertDialog;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_bu,text_creditlimit,text_outstanding,text_overdue;

        public MyViewHolder(View view) {
            super(view);
            text_bu = view.findViewById(R.id.text_bu);
            text_creditlimit = view.findViewById(R.id.text_creditlimit);
            text_outstanding = view.findViewById(R.id.text_outstanding);
            text_overdue = view.findViewById(R.id.text_overdue);

        }
    }

    public OutstandingAdapter(Context context, List<OutstandingModel> employee_models) {
        mContext = context;
        this.employee_modelList = employee_models;
//        this.listener = listener;
//        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outstanding_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        employee_model = employee_modelList.get(position);

        holder.text_bu.setText(employee_model.getBu());

        if(!employee_model.getCreditlimit().equalsIgnoreCase(" "))
        {
            holder.text_creditlimit.setText(formatter.format(Double.parseDouble(employee_model.getCreditlimit())));
        }
        else
        {
            holder.text_creditlimit.setText("0");
        }

        if(!employee_model.getOutstanding().equalsIgnoreCase(" "))
        {
            holder.text_outstanding.setText(formatter.format(Double.parseDouble(employee_model.getOutstanding())));
        }
        else
        {
            holder.text_outstanding.setText("0");
        }

        if(!employee_model.getOverdue().equalsIgnoreCase(" "))
        {
            holder.text_overdue.setText(formatter.format(Double.parseDouble(employee_model.getOverdue())));
        }
        else
        {
            holder.text_overdue.setText("0");
        }





        holder.text_bu.setPaintFlags(holder.text_bu.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        holder.text_bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // alertDialog = new AlertDialog.Builder(mContext).create(); //Read Update
                alertDialog = new AlertDialog.Builder(view.getRootView().getContext()).create();
                alertDialog.setTitle("Dealer App");
                alertDialog.setMessage("Do you want To See Details Of " +holder.text_bu.getText().toString()+"?");
                alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Global_Data.GLOvel_BU =  holder.text_bu.getText().toString().trim();
                        Intent i = new Intent(mContext, OutstandingDetails.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(i);

                    }
                });

                alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                alertDialog.show();
            }
        });

//
//        holder.ename_value.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent c = new Intent(mContext,Employee_Details.class);
//                c.putExtra("FULL_NAME", holder.ename_value.getText().toString().trim());
//                c.addFlags(FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(c);
//                // Toast.makeText(mContext, holder.ename_value.getText().toString().trim(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (userAdapterListenernew != null) {
//                    //userAdapterListenernew.onUserClickedImag(Employee_Models.get(position),position);
//                }
//
//
//            }
//        });
//
//        holder.b_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (listener != null) {
//                    // listener.onUserClicked(Employee_Models.get(position),position);
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return employee_modelList.size();
    }



    public interface UserAdapterListener {
        // void onUserClicked(Employee_Model Employee_Model, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(Employee_Model Employee_Model, int position);
    }

}
