package com.anchor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.OutstandingDetails_Model;

import java.text.DecimalFormat;
import java.util.List;

public class OutstandingDetails_Adapter extends RecyclerView.Adapter<OutstandingDetails_Adapter.MyViewHolder> {
    private List<OutstandingDetails_Model> outstandingDetails_models;
    private Context mContext;
    OutstandingDetails_Model outstandingDetails_model;
    private UserAdapterListener listener;
    private UserAdapterListenernew userAdapterListenernew;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Reference_No,invoice_no,Date_1,Type,Product_Category,Value,Due_Amount,Due_Date,Total_No_Of_Days;

        public MyViewHolder(View view) {
            super(view);
            Reference_No = view.findViewById(R.id.Reference_No);
            invoice_no = view.findViewById(R.id.invoice_no);
            Date_1 = view.findViewById(R.id.Date);
            Type = view.findViewById(R.id.Type);
            Product_Category = view.findViewById(R.id.Product_Category);
            Value = view.findViewById(R.id.Value);
            Due_Amount = view.findViewById(R.id.Due_Amount);
            Due_Date = view.findViewById(R.id.Due_Date);
            Total_No_Of_Days = view.findViewById(R.id.Total_No_Of_Days);

        }
    }


    public OutstandingDetails_Adapter(Context context, List<OutstandingDetails_Model> outstandingDetails_models, UserAdapterListener listener, UserAdapterListenernew userAdapterListenernew) {
        mContext = context;
        this.outstandingDetails_models = outstandingDetails_models;
        this.listener = listener;
        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.outstandingdetails_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        outstandingDetails_model = outstandingDetails_models.get(position);

        holder.Reference_No.setText(outstandingDetails_model.getReference_No());
        holder.invoice_no.setText(outstandingDetails_model.getInvoice_no());
        holder.Date_1.setText(outstandingDetails_model.getDate());
        holder.Type.setText(outstandingDetails_model.getType());
        holder.Product_Category.setText(outstandingDetails_model.getProduct_Category());

        if(!outstandingDetails_model.getValue().equalsIgnoreCase(" "))
        {
            holder.Value.setText(formatter.format(Double.parseDouble(outstandingDetails_model.getValue())));
        }
        else
        {
            holder.Value.setText("0");
        }

        if(!outstandingDetails_model.getDue_Amount().equalsIgnoreCase(" "))
        {
            holder.Due_Amount.setText(formatter.format(Double.parseDouble(outstandingDetails_model.getDue_Amount())));
        }
        else
        {
            holder.Due_Amount.setText("0");
        }




        holder.Due_Date.setText(outstandingDetails_model.getDue_Date());
        holder.Total_No_Of_Days.setText(outstandingDetails_model.getTotal_No_Of_Days());





//        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (userAdapterListenernew != null) {
//                    //userAdapterListenernew.onUserClickedImag(OutstandingDetails_Models.get(position),position);
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
//                    // listener.onUserClicked(OutstandingDetails_Models.get(position),position);
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return outstandingDetails_models.size();
    }



    public interface UserAdapterListener {
        // void onUserClicked(OutstandingDetails_Model OutstandingDetails_Model, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(OutstandingDetails_Model OutstandingDetails_Model, int position);
    }

}
