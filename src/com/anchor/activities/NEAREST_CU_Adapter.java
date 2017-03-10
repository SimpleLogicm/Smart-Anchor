package com.anchor.activities;

/**
 * Created by Vinod on 18-11-2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class NEAREST_CU_Adapter extends ArrayAdapter<HashMap<String, String>> {
    DataBaseHelper dbvoc;
    public NEAREST_CU_Adapter(Context context, int resource) {
		super(context, resource);
        dbvoc= new DataBaseHelper(context);
		// TODO Auto-generated constructor stub
	}

	customButtonListener customListner;

    private ArrayList<HashMap<String, String>> dataArrayList;
    String CUSTOMER_NAME = "Customer_name";
    String CUSTOMER_ADDRESS = "Customer_address";
    String CUSTOMER_DISTANCE = "Customer_distance";
    HashMap<String, String> getData = new HashMap<String, String>();
    String order_code = "";
    String cust_mob_no;

    public interface customButtonListener {
        public void onButtonClickListner(int position, String value1,String value2,View v,String value3);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;
    private ArrayList<String> data = new ArrayList<String>();

    public NEAREST_CU_Adapter(Context context,  ArrayList<HashMap<String, String>> dataItem) {
        super(context, R.layout.nearest_cu_adapter, dataItem);
        this.dataArrayList = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.nearest_cu_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.childTextViewnew);

//            viewHolder.address = (TextView) convertView
//                    .findViewById(R.id.address);
//            
            viewHolder.distance = (TextView) convertView
                    .findViewById(R.id.distance);

            viewHolder.childButtonnew = (Button) convertView
                    .findViewById(R.id.childButtonnew);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        getData = dataArrayList.get(position);
        try {



            if (getData.get(CUSTOMER_NAME).toString() != null && getData.get(CUSTOMER_NAME).toString() != "null") {
                viewHolder.text.setText(getData.get(CUSTOMER_NAME));
            }

//            if (getData.get(CUSTOMER_ADDRESS).toString() != null && getData.get(CUSTOMER_ADDRESS).toString() != "null") {
//                viewHolder.address.setText(getData.get(CUSTOMER_ADDRESS));
//            }
//            
            if (getData.get(CUSTOMER_DISTANCE).toString() != null && getData.get(CUSTOMER_DISTANCE).toString() != "null") {
                viewHolder.distance.setText(getData.get(CUSTOMER_DISTANCE));
            }
        }
        catch(Exception et) {et.printStackTrace();}
        //viewHolder.text.setText(temp);
        order_code = viewHolder.text.getText().toString();

            viewHolder.childButtonnew.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    getData = dataArrayList.get(position);

                    if (customListner != null) {
                        customListner.onButtonClickListner(position, getData.get(CUSTOMER_ADDRESS).toString(),getData.get(CUSTOMER_DISTANCE).toString(),v,getData.get(CUSTOMER_NAME).toString());
                    }
                }
            });

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
