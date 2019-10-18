package com.anchor.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Status_Adapter extends ArrayAdapter<HashMap<String, String>> {
    public Status_Adapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	customButtonListener customListner;

    private ArrayList<HashMap<String, String>> dataArrayList;
    String ORDER_ID = "order_id";
	String MANAGER_REMARK = "manager_remarks";
	String ORDER_STATUS = "order_status";
	String ITEM_DESC = "item_desc";
	String ITEM_AMOUNT = "item_tamount";
	
    HashMap<String, String> getData = new HashMap<String, String>();
    String order_code = "";

    public interface customButtonListener {
        void onButtonClickListner(int position, String value1, String value2, View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;
    private ArrayList<String> data = new ArrayList<String>();

    public Status_Adapter(Context context,  ArrayList<HashMap<String, String>> dataItem) {
        super(context, R.layout.status_list_txt, dataItem);
        this.dataArrayList = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.status_list_txt, null);
            viewHolder = new ViewHolder();
            viewHolder.text = convertView
                    .findViewById(R.id.ordername);

            viewHolder.address = convertView
                    .findViewById(R.id.status);
            
            viewHolder.distance = convertView
                    .findViewById(R.id.remark);

//            viewHolder.childButtonnew = (Button) convertView
//                    .findViewById(R.id.childButtonnew);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        getData = dataArrayList.get(position);
        try {


            if (getData.get(ORDER_ID) != null && getData.get(ORDER_ID) != "null") {
                viewHolder.text.setText(getData.get(ORDER_ID));
            }

            if (getData.get(MANAGER_REMARK) != null && getData.get(MANAGER_REMARK) != "null") {
                viewHolder.address.setText(getData.get(MANAGER_REMARK));
            }
            
            if (getData.get(ORDER_STATUS) != null && getData.get(ORDER_STATUS) != "null") {
                viewHolder.distance.setText(getData.get(ORDER_STATUS));
            }
        }
        catch(Exception et) {et.printStackTrace();}
        //viewHolder.text.setText(temp);
        order_code = viewHolder.text.getText().toString();


//
//            viewHolder.childButtonnew.setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    getData = dataArrayList.get(position);
//
//                    if (customListner != null) {
//                        customListner.onButtonClickListner(position, getData.get(CUSTOMER_ADDRESS).toString(),getData.get(CUSTOMER_DISTANCE).toString(),v);
//                    }
//
//                }
//            });




        return convertView;
    }

    public class ViewHolder {
        TextView text;
        TextView address;
        TextView distance;
       // Button editorder;
        Button childButtonnew;
    }
}
