package com.anchor.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.anchor.activities.R;
import com.anchor.model.Ledger_Model;
import java.text.DecimalFormat;
import java.util.List;

public class Ledger_Adapter extends RecyclerView.Adapter<Ledger_Adapter.MyViewHolder> {
    private List<Ledger_Model> ledger_models; 
    private Context mContext;
    Ledger_Model ledger_model;
    private UserAdapterListener listener;
    private UserAdapterListenernew userAdapterListenernew;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###.00");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ld_value,lt_value,li_value,lc_value,lp_value,lde_value,lcr_value,lcr_value_hide;
        public LinearLayout ledger_l_container;

        public MyViewHolder(View view) {
            super(view);
            ld_value = view.findViewById(R.id.ld_value);
            lt_value = view.findViewById(R.id.lt_value);
            li_value = view.findViewById(R.id.li_value);
            lc_value = view.findViewById(R.id.lc_value);
            lp_value = view.findViewById(R.id.lp_value);
            lde_value = view.findViewById(R.id.lde_value);
            lcr_value = view.findViewById(R.id.lcr_value);
            lcr_value_hide = view.findViewById(R.id.lcr_value_hide);
            ledger_l_container = view.findViewById(R.id.ledger_l_container);
            

        }
    }


    public Ledger_Adapter(Context context, List<Ledger_Model> ledger_models, UserAdapterListener listener, UserAdapterListenernew userAdapterListenernew) {
        mContext = context;
        this.ledger_models = ledger_models;
        this.listener = listener;
        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ledger_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ledger_model = ledger_models.get(position);

//        if(position == 0 || position == ledger_models.size()-1)
//        {
//            holder.ledger_l_container.setBackgroundColor(mContext.getResources().getColor(R.color.placeholder_bg));
//        }
//        else
//        {
//            holder.ledger_l_container.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//        }
        holder.ld_value.setText(ledger_model.getDate());
        holder.lt_value.setText(ledger_model.getTransaction_Type());
        holder.li_value.setText(ledger_model.getInvoice_No());
        holder.lc_value.setText(ledger_model.getCheque_No());
        holder.lcr_value_hide.setText(ledger_model.getPerticulers());


        String s = ledger_model.getPerticulers();

        if (s.length()>50) {
            String s1 =  s.substring(0,50)+"...";
            holder.lp_value.setText(Html.fromHtml(s1+"<font color='blue'> <u>View More</u></font>"));

        }
        else
        {
            holder.lp_value.setText(ledger_model.getPerticulers());
        }
        //holder.lp_value.setText(ledger_model.getPerticulers());

        if(!ledger_model.getDebit().equalsIgnoreCase(" "))
        {
            holder.lde_value.setText(formatter.format(Double.parseDouble(ledger_model.getDebit())));
        }
        else
        {
            holder.lde_value.setText(ledger_model.getDebit());
        }

        if(!ledger_model.getCredit().equalsIgnoreCase(" "))
        {
            holder.lcr_value.setText(formatter.format(Double.parseDouble(ledger_model.getCredit())));
        }
        else
        {
            holder.lcr_value.setText(ledger_model.getCredit());
        }





        holder.lp_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if( holder.lp_value.getText().toString().contains("View More"))
               {
                  // Toast.makeText(mContext, "click", Toast.LENGTH_SHORT).show();
                   ShowFullText(mContext,holder.lcr_value_hide.getText().toString());
               }


            }
        });
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
        // void onUserClicked(Ledger_Model Ledger_Model, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(Ledger_Model Ledger_Model, int position);
    }

    public void ShowFullText(Context context,String text)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));

        //AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(text)
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
