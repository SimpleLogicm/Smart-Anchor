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

public class Target_Adapter extends ArrayAdapter<HashMap<String, String>> {
    customButtonListener customListner;

    private ArrayList<HashMap<String, String>> dataArrayList;
    static final String TAG_MONTH = "month";
    static final String TAG_TARGET = "target";
    static final String TAG_ACHIEVED = "achieved";


    public interface customButtonListener {
        public void onButtonClickListner(int position, String value, View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;

    public Target_Adapter(Context context, ArrayList<HashMap<String, String>> dataItem) {
        super(context, R.layout.target_achtxt, dataItem);
        this.dataArrayList = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.target_achtxt, null);
            viewHolder = new ViewHolder();
            viewHolder.textmonth = (TextView) convertView
                    .findViewById(R.id.itemmonth);

            viewHolder.texttarget = (TextView) convertView
                    .findViewById(R.id.itemtarget);

            viewHolder.textarchieved = (TextView) convertView
                    .findViewById(R.id.itemachieved);



            // viewHolder.button = (ImageView) convertView.findViewById(R.id.childButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HashMap<String, String> getData = new HashMap<String, String>();
        getData = dataArrayList.get(position);
        try {


            if (getData.get(TAG_MONTH).toString() != null && getData.get(TAG_MONTH).toString() != "null") {
                viewHolder.textmonth.setText(getData.get(TAG_MONTH));
            }

            if (getData.get(TAG_TARGET).toString() != null && getData.get(TAG_TARGET).toString() != "null") {
                viewHolder.texttarget.setText(getData.get(TAG_TARGET));
            }

            if (getData.get(TAG_ACHIEVED).toString() != null && getData.get(TAG_ACHIEVED).toString() != "null") {
                viewHolder.textarchieved.setText(getData.get(TAG_ACHIEVED));
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
        TextView textmonth,texttarget,textarchieved;

        // ImageView button;
    }
}
