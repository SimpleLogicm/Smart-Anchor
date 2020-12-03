package com.anchor.adapter;

import android.content.Context;
import android.graphics.Paint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.R;
import com.anchor.model.Scheme_Model;
import java.text.DecimalFormat;
import java.util.List;
import cpm.simplelogic.helper.Check_Null_Value;

public class Scheme_Adapter extends RecyclerView.Adapter<Scheme_Adapter.MyViewHolder> {
    private List<Scheme_Model> scheme_models;
    private Context mContext;
    Scheme_Model scheme_model;
    public UserAdapterListener listener;
    public UserAdapterListenernew userAdapterListenernew;
    DecimalFormat formatter = new DecimalFormat("#,##,##,###");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Scheme_Name,Current_Volume,Current_Eligibility,Gift_Detail,Gift_Value,Short_Volumn_For_Next_Slab,Next_Scheme_Slab,Next_Scheme_Value;

        public MyViewHolder(View view) {
            super(view);
            Scheme_Name = view.findViewById(R.id.Scheme_Name);
            Current_Volume = view.findViewById(R.id.Current_Volume);
            Current_Eligibility = view.findViewById(R.id.Current_Eligibility);
            Gift_Detail = view.findViewById(R.id.Gift_Detail);
            Gift_Value = view.findViewById(R.id.Gift_Value);
            Short_Volumn_For_Next_Slab = view.findViewById(R.id.Short_Volumn_For_Next_Slab);
            Next_Scheme_Slab = view.findViewById(R.id.Next_Scheme_Slab);
            Next_Scheme_Value = view.findViewById(R.id.Next_Scheme_Value);




        }
    }


    public Scheme_Adapter(Context context, List<Scheme_Model> scheme_Models, UserAdapterListener listener, UserAdapterListenernew userAdapterListenernew) {
        mContext = context;
        this.scheme_models = scheme_Models;
        this.listener = listener;
        this.userAdapterListenernew = userAdapterListenernew;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scheme_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        scheme_model = scheme_models.get(position);


        holder.Scheme_Name.setText(scheme_model.getScheme_Name());

        if(!scheme_model.getCurrent_Volume().equalsIgnoreCase(" "))
        {
            holder.Current_Volume.setText(formatter.format(Double.parseDouble(scheme_model.getCurrent_Volume())));
        }
        else
        {
            holder.Current_Volume.setText(scheme_model.getCurrent_Volume());
        }

        if(!scheme_model.getCurrent_Eligibility().equalsIgnoreCase(" "))
        {
            holder.Current_Eligibility.setText(formatter.format(Double.parseDouble(scheme_model.getCurrent_Eligibility())));
        }
        else
        {
            holder.Current_Eligibility.setText(scheme_model.getCurrent_Eligibility());
        }



            holder.Gift_Detail.setText(scheme_model.getGift_Detail());


        if(!scheme_model.getGift_Value().equalsIgnoreCase(" "))
        {
            holder.Gift_Value.setText(formatter.format(Double.parseDouble(scheme_model.getGift_Value())));
        }
        else
        {
            holder.Gift_Value.setText(scheme_model.getGift_Value());
        }


        if(!scheme_model.getShort_Volumn_For_Next_Slab().equalsIgnoreCase(" "))
        {
            holder.Short_Volumn_For_Next_Slab.setText(formatter.format(Double.parseDouble(scheme_model.getShort_Volumn_For_Next_Slab())));
        }
        else
        {
            holder.Short_Volumn_For_Next_Slab.setText(scheme_model.getShort_Volumn_For_Next_Slab());
        }


            holder.Next_Scheme_Slab.setText(scheme_model.getNext_Scheme_Slab());


        if(!scheme_model.getNext_Scheme_Value().equalsIgnoreCase(" "))
        {
            holder.Next_Scheme_Value.setText(formatter.format(Double.parseDouble(scheme_model.getNext_Scheme_Value())));
        }
        else
        {
            holder.Next_Scheme_Value.setText(scheme_model.getNext_Scheme_Value());
        }








        holder.Scheme_Name.setPaintFlags(holder.Scheme_Name.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        holder.Scheme_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(holder.Scheme_Name.getText().toString()))
                {
                    if (userAdapterListenernew != null) {
                        listener.onUserClicked(scheme_models.get(position),position);
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
         void onUserClicked(Scheme_Model Scheme_Model, int position);
    }

    public interface UserAdapterListenernew {
        //void onUserClickedImag(Scheme_Model Scheme_Model, int position);
    }

}
