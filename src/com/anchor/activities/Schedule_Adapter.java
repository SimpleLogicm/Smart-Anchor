package com.anchor.activities;

/**
 * Created by Vinod on 18-11-2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Schedule_Adapter extends ArrayAdapter<HashMap<String, String>> {
    customButtonListener customListner;

    private ArrayList<HashMap<String, String>> dataArrayList;
    static final String TAG_PRODUCT = "product";
    static final String TAG_Q_ORDER = "quantity_order";
    static final String TAG_Q_DELIVERED = "quantity_delivered";


    public interface customButtonListener {
        void onButtonClickListner(int position, String value, View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;

    public Schedule_Adapter(Context context, ArrayList<HashMap<String, String>> dataItem) {
        super(context, R.layout.schedule_achtext, dataItem);
        this.dataArrayList = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.schedule_achtext, null);
            viewHolder = new ViewHolder();
            viewHolder.textproduct = convertView
                    .findViewById(R.id.product);

            viewHolder.textordered = convertView
                    .findViewById(R.id.q_order);

            viewHolder.textdilivered = convertView
                    .findViewById(R.id.q_dilivered);



            // viewHolder.button = (ImageView) convertView.findViewById(R.id.childButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap<String, String> getData = new HashMap<String, String>();
        getData = dataArrayList.get(position);
        try {

//
            if (getData.get(TAG_PRODUCT) != null && getData.get(TAG_PRODUCT) != "null") {
                viewHolder.textproduct.setText(getData.get(TAG_PRODUCT));
            }

            if (getData.get(TAG_Q_ORDER) != null && getData.get(TAG_Q_ORDER) != "null") {
                viewHolder.textordered.setText(getData.get(TAG_Q_ORDER));
            }

            if (getData.get(TAG_Q_DELIVERED) != null && getData.get(TAG_Q_DELIVERED) != "null") {
                viewHolder.textdilivered.setText(getData.get(TAG_Q_DELIVERED));
            }


        }
        catch(Exception et) {et.printStackTrace();}



//        viewHolder.button.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                if (customListner != null) {
//                    customListner.onButtonClickListner(position,temp,v);
//                }
//
//            }
//        });

        return convertView;
    }

    public class ViewHolder {
        TextView textproduct,textordered,textdilivered;

        // ImageView button;
    }
}
