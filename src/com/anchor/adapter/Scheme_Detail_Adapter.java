package com.anchor.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.model.SchemeDetailModel;

import java.text.DecimalFormat;
import java.util.List;

public class Scheme_Detail_Adapter extends RecyclerView.Adapter<Scheme_Detail_Adapter.MyViewHolder> {
    private List<SchemeDetailModel> schemeDetailModels;
    private Context mContext;
    SchemeDetailModel schemeDetailModel;
    private UserAdapterListener listener;
    private UserAdapterListenernew userAdapterListenernew;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Invoice_No,Invoice_Date,Order_No,Order_Date,Order_Type,Item_Code,Item_Name,Quantity;
        public TextView Line_Amount,Tax_Amount,Total_Amount,Eligible_Volumn,CN1,CN2,CN3,CN4,Gift_Amount;

        public MyViewHolder(View view) {
            super(view);
            Invoice_No = view.findViewById(R.id.Invoice_No);
            Invoice_Date = view.findViewById(R.id.Invoice_Date);
            Order_No = view.findViewById(R.id.Order_No);
            Order_Date = view.findViewById(R.id.Order_Date);
            Order_Type = view.findViewById(R.id.Order_Type);
            Item_Code = view.findViewById(R.id.Item_Code);
            Quantity = view.findViewById(R.id.Quantity);
            Item_Name = view.findViewById(R.id.Item_Name);

            Line_Amount = view.findViewById(R.id.Line_Amount);
            Tax_Amount = view.findViewById(R.id.Tax_Amount);
            Total_Amount = view.findViewById(R.id.Total_Amount);
            Eligible_Volumn = view.findViewById(R.id.Eligible_Volumn);
            CN1 = view.findViewById(R.id.CN1);
            CN2 = view.findViewById(R.id.CN2);
            CN3 = view.findViewById(R.id.CN3);
            CN4 = view.findViewById(R.id.CN4);
            Gift_Amount = view.findViewById(R.id.Gift_Amount);




        }
    }


    public Scheme_Detail_Adapter(Context context, List<SchemeDetailModel> schemeDetailModels, UserAdapterListener listener, UserAdapterListenernew userAdapterListenernew) {
        mContext = context;
        this.schemeDetailModels = schemeDetailModels;
        this.listener = listener;
        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scheme_detail_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        schemeDetailModel = schemeDetailModels.get(position);

        holder.Invoice_No.setText(schemeDetailModel.getInvoice_No());

        holder.Invoice_Date.setText(schemeDetailModel.getInvoice_Date());

        holder.Order_No.setText(schemeDetailModel.getOrder_No());

        holder.Order_Date.setText(schemeDetailModel.getOrder_Date());

        holder.Order_Type.setText(schemeDetailModel.getOrder_Type());

        holder.Item_Code.setText(schemeDetailModel.getItem_Code());

        holder.Item_Name.setText(schemeDetailModel.getItem_Name());

        if(!schemeDetailModel.getQuantity().equalsIgnoreCase(" "))
        {
            holder.Quantity.setText(formatter.format(Double.parseDouble(schemeDetailModel.getQuantity())));
        }
        else
        {
            holder.Quantity.setText(schemeDetailModel.getQuantity());
        }

        if(!schemeDetailModel.getLine_Amount().equalsIgnoreCase(" "))
        {
            holder.Line_Amount.setText(formatter.format(Double.parseDouble(schemeDetailModel.getLine_Amount())));
        }
        else
        {
            holder.Line_Amount.setText(schemeDetailModel.getLine_Amount());
        }

        if(!schemeDetailModel.getTax_Amount().equalsIgnoreCase(" "))
        {
            holder.Tax_Amount.setText(formatter.format(Double.parseDouble(schemeDetailModel.getTax_Amount())));
        }
        else
        {
            holder.Tax_Amount.setText(schemeDetailModel.getTax_Amount());
        }


        if(!schemeDetailModel.getTotal_Amount().equalsIgnoreCase(" "))
        {
            holder.Total_Amount.setText(formatter.format(Double.parseDouble(schemeDetailModel.getTotal_Amount())));
        }
        else
        {
            holder.Total_Amount.setText(schemeDetailModel.getTotal_Amount());
        }

        if(!schemeDetailModel.getEligible_Volumn().equalsIgnoreCase(" "))
        {
            holder.Eligible_Volumn.setText(formatter.format(Double.parseDouble(schemeDetailModel.getEligible_Volumn())));
        }
        else
        {
            holder.Eligible_Volumn.setText(schemeDetailModel.getEligible_Volumn());
        }


        if(!schemeDetailModel.getCN1().equalsIgnoreCase(" "))
        {
            holder.CN1.setText(formatter.format(Double.parseDouble(schemeDetailModel.getCN1())));
        }
        else
        {
            holder.CN1.setText(schemeDetailModel.getCN1());
        }

        if(!schemeDetailModel.getCN2().equalsIgnoreCase(" "))
        {
            holder.CN2.setText(formatter.format(Double.parseDouble(schemeDetailModel.getCN2())));
        }
        else
        {
            holder.CN2.setText(schemeDetailModel.getCN2());
        }

        if(!schemeDetailModel.getCN3().equalsIgnoreCase(" "))
        {
            holder.CN3.setText(formatter.format(Double.parseDouble(schemeDetailModel.getCN3())));
        }
        else
        {
            holder.CN3.setText(schemeDetailModel.getCN3());
        }

        if(!schemeDetailModel.getCN4().equalsIgnoreCase(" "))
        {
            holder.CN4.setText(formatter.format(Double.parseDouble(schemeDetailModel.getCN4())));
        }
        else
        {
            holder.CN4.setText(schemeDetailModel.getCN4());
        }


        if(!schemeDetailModel.getGift_Amount().equalsIgnoreCase(" "))
        {
            holder.Gift_Amount.setText(formatter.format(Double.parseDouble(schemeDetailModel.getGift_Amount())));
        }
        else
        {
            holder.Gift_Amount.setText(schemeDetailModel.getGift_Amount());
        }




      



//        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (userAdapterListenernew != null) {
//                    //userAdapterListenernew.onUserClickedImag(SchemeDetailModels.get(position),position);
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
//                    // listener.onUserClicked(SchemeDetailModels.get(position),position);
//                }
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return schemeDetailModels.size();
    }



    public interface UserAdapterListener {
        // void onUserClicked(SchemeDetailModel SchemeDetailModel, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(SchemeDetailModel SchemeDetailModel, int position);
    }

}
