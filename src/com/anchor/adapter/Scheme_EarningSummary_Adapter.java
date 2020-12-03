package com.anchor.adapter;

import android.content.Context;
import android.graphics.Paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.model.SchemeDetailModel;

import java.text.DecimalFormat;
import java.util.List;

import cpm.simplelogic.helper.Check_Null_Value;


public class Scheme_EarningSummary_Adapter extends RecyclerView.Adapter<Scheme_EarningSummary_Adapter.MyViewHolder> {
    private List<SchemeDetailModel> scheme_models;
    private Context mContext;
    SchemeDetailModel scheme_model;
    public UserAdapterListener listener;
    public UserAdapterListenernew userAdapterListenernew;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Scheme_Name,Quantity,Line_Amount,Tax_Amount,Total_Amount,Gift_Amount,View_Details_Earning;

        public MyViewHolder(View view) {
            super(view);
            Scheme_Name = view.findViewById(R.id.Scheme_Name);
            Quantity = view.findViewById(R.id.Quantity);
            Line_Amount = view.findViewById(R.id.Line_Amount);
            Tax_Amount = view.findViewById(R.id.Tax_Amount);
            Total_Amount = view.findViewById(R.id.Total_Amount);
            Gift_Amount = view.findViewById(R.id.Gift_Amount);
            View_Details_Earning = view.findViewById(R.id.View_Details_Earning);

            }
    }


    public Scheme_EarningSummary_Adapter(Context context, List<SchemeDetailModel> scheme_Models, UserAdapterListener listener, UserAdapterListenernew userAdapterListenernew) {
        mContext = context;
        this.scheme_models = scheme_Models;
        this.listener = listener;
        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scheme_earning_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        scheme_model = scheme_models.get(position);


        holder.Scheme_Name.setText(scheme_model.getScheme_Name());

        if(!scheme_model.getQuantity().equalsIgnoreCase(" "))
        {
            holder.Quantity.setText(formatter.format(Double.parseDouble(scheme_model.getQuantity())));
        }
        else
        {
            holder.Quantity.setText(scheme_model.getQuantity());
        }

        if(!scheme_model.getLine_Amount().equalsIgnoreCase(" "))
        {
            holder.Line_Amount.setText(formatter.format(Double.parseDouble(scheme_model.getLine_Amount())));
        }
        else
        {
            holder.Line_Amount.setText(scheme_model.getLine_Amount());
        }


        if(!scheme_model.getTax_Amount().equalsIgnoreCase(" "))
        {
            holder.Tax_Amount.setText(formatter.format(Double.parseDouble(scheme_model.getTax_Amount())));
        }
        else
        {
            holder.Tax_Amount.setText(scheme_model.getTax_Amount());
        }


        if(!scheme_model.getTotal_Amount().equalsIgnoreCase(" "))
        {
            holder.Total_Amount.setText(formatter.format(Double.parseDouble(scheme_model.getTotal_Amount())));
        }
        else
        {
            holder.Total_Amount.setText(scheme_model.getTotal_Amount());
        }


        if(!scheme_model.getGift_Amount().equalsIgnoreCase(" "))
        {
            holder.Gift_Amount.setText(formatter.format(Double.parseDouble(scheme_model.getGift_Amount())));
        }
        else
        {
            holder.Gift_Amount.setText(scheme_model.getGift_Amount());
        }








        holder.Scheme_Name.setPaintFlags(holder.Scheme_Name.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        holder.Scheme_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.Scheme_Name.getText().toString()))
                {
                    if (userAdapterListenernew != null) {
                        listener.onUserClicked("scheme",scheme_models.get(position),position);
                    }
                }

            }
        });

        holder.View_Details_Earning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.Scheme_Name.getText().toString()))
                {
                    if (userAdapterListenernew != null) {
                        listener.onUserClicked("view",scheme_models.get(position),position);
                    }
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return scheme_models.size();
    }



    public interface UserAdapterListener {
        void onUserClicked(String action_type, SchemeDetailModel Scheme_Model, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(Scheme_Model Scheme_Model, int position);
    }

}
