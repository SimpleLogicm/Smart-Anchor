package com.anchor.adapter;

import android.content.Context;
import android.graphics.Paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.model.Ledger_Model;
import java.text.DecimalFormat;
import java.util.List;
import cpm.simplelogic.helper.Check_Null_Value;

public class Invoices_Adapter extends RecyclerView.Adapter<Invoices_Adapter.MyViewHolder> {
    private List<Ledger_Model> ledger_models;
    private Context mContext;
    Ledger_Model ledger_model;
    private UserAdapterListener listener;
    private UserAdapterListenernew userAdapterListenernew;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView n_date,n_ttype,n_invoice_no,n_pro_category,n_s_order,n_debit,n_credit;

        public MyViewHolder(View view) {
            super(view);
            n_date = view.findViewById(R.id.n_date);
            n_ttype = view.findViewById(R.id.n_ttype);
            n_invoice_no = view.findViewById(R.id.n_invoice_no);
            n_pro_category = view.findViewById(R.id.n_pro_category);
            n_s_order = view.findViewById(R.id.n_s_order);
            n_debit = view.findViewById(R.id.n_debit);
            n_credit = view.findViewById(R.id.n_credit);


        }
    }


    public Invoices_Adapter(Context context, List<Ledger_Model> ledger_models, UserAdapterListener listener, UserAdapterListenernew userAdapterListenernew) {
        mContext = context;
        this.ledger_models = ledger_models;
        this.listener = listener;
        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.invoices_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ledger_model = ledger_models.get(position);

        holder.n_date.setText(ledger_model.getDate());
        holder.n_ttype.setText(ledger_model.getTransaction_Type());

        holder.n_invoice_no.setPaintFlags(holder.n_invoice_no.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        holder.n_invoice_no.setText(ledger_model.getInvoice_No());
        holder.n_pro_category.setText(ledger_model.getCheque_No());
        holder.n_s_order.setText(ledger_model.getPerticulers());

        if(!ledger_model.getDebit().equalsIgnoreCase(" "))
        {
            holder.n_debit.setText(formatter.format(Double.parseDouble(ledger_model.getDebit())));
        }
        else
        {
            holder.n_debit.setText(ledger_model.getDebit());
        }

        if(!ledger_model.getCredit().equalsIgnoreCase(" "))
        {
            holder.n_credit.setText(formatter.format(Double.parseDouble(ledger_model.getCredit())));
        }
        else
        {
            holder.n_credit.setText(ledger_model.getCredit());
        }

        holder.n_invoice_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.n_invoice_no.getText().toString()))
                {
                    if (userAdapterListenernew != null) {
                        listener.onUserClicked(ledger_models.get(position),position);
                    }
                }

            }
        });




//        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (userAdapterListenernew != null) {
//                    //userAdapterListenernew.onUserClickedImag(ledger_models.get(position),position);
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
//                    // listener.onUserClicked(ledger_models.get(position),position);
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return ledger_models.size();
    }





    public interface UserAdapterListener {
        void onUserClicked(Ledger_Model ledger_model, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(Ledger_Model Ledger_Model, int position);
    }

}
