package com.anchor.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.services.getServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderStatus_Adapter extends ArrayAdapter<HashMap<String, String>> {
    public OrderStatus_Adapter(Context context, int resource) {
		super(context, resource);
		// TODO Auto-generated constructor stub
	}

	customButtonListener customListner;
	 DataBaseHelper dbvoc;
    private ArrayList<HashMap<String, String>> dataArrayList;
    String ITEM_DESC = "item_desc";
	String ITEM_AMOUNT = "item_tamount";
	String ITEM_NUMBER = "item_number";
    HashMap<String, String> getData = new HashMap<String, String>();
    String order_code = "";

    public interface customButtonListener {
        public void onButtonClickListner(int position, String value1,String value2,View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;
    private ArrayList<String> data = new ArrayList<String>();

    public OrderStatus_Adapter(Context context,  ArrayList<HashMap<String, String>> dataItem) {
        super(context, R.layout.status_clicktxt, dataItem);
        this.dataArrayList = dataItem;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.status_clicktxt, null);
            viewHolder = new ViewHolder();
            
            viewHolder.text1 = (TextView) convertView
                    .findViewById(R.id.stat_txt1); 

            viewHolder.text2 = (TextView) convertView
                    .findViewById(R.id.stat_txt2);
            
            viewHolder.text3 = (TextView) convertView
                    .findViewById(R.id.item_no);
            
            viewHolder.edit = (Button) convertView
                    .findViewById(R.id.edit_btn);
            
            viewHolder.delete = (Button) convertView
                    .findViewById(R.id.delete_btn);
            
            if(Global_Data.GLOvel_REMARK.equals("ordered") || Global_Data.GLOvel_REMARK.equals("lost"))
            {
            	viewHolder.edit.setEnabled(false);
            	viewHolder.edit.setBackgroundResource(android.R.drawable.btn_default);
            	
            	viewHolder.delete.setEnabled(false);
            	viewHolder.delete.setBackgroundResource(android.R.drawable.btn_default);
            	
            }
            else
            {
            	
            	
            	viewHolder.edit.setEnabled(true);
            	viewHolder.delete.setEnabled(true);
            }
            
            convertView.setTag(viewHolder);
        } else {
                 viewHolder = (ViewHolder) convertView.getTag();
               }

        getData = dataArrayList.get(position);
        try {

            if (getData.get(ITEM_DESC).toString() != null && getData.get(ITEM_DESC).toString() != "null") {
                viewHolder.text1.setText(getData.get(ITEM_DESC));
            }

            if (getData.get(ITEM_AMOUNT).toString() != null && getData.get(ITEM_AMOUNT).toString() != "null") {
                viewHolder.text2.setText(getData.get(ITEM_AMOUNT));
            }
            
            if (getData.get(ITEM_NUMBER).toString() != null && getData.get(ITEM_NUMBER).toString() != "null") {
                viewHolder.text3.setText(getData.get(ITEM_NUMBER));
            }
            
//            if (getData.get(CUSTOMER_DISTANCE).toString() != null && getData.get(CUSTOMER_DISTANCE).toString() != "null") {
//                viewHolder.distance.setText(getData.get(CUSTOMER_DISTANCE));
//            }
            
           }
        catch(Exception et) {et.printStackTrace();}
        
        viewHolder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//viewHolder.tvTitle.setText(getData.get());
            	//getListViewnew(holder.tvTitle.getText().toString());
            	//Toast.makeText(context, "ddd:"+getData.get(TAG_ITEMNAME),Toast.LENGTH_SHORT).show(); 
            	
            	final Dialog dialognew = new Dialog(context);
       	        dialognew.setCancelable(false);
       	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
       	        //tell the Dialog to use the dialog.xml as it's layout description
       	        dialognew.setContentView(R.layout.onclick_dialog);

       	        final EditText userInput = (EditText) dialognew
       	                .findViewById(R.id.prod_desc);
       	        
       	        final TextView headertext = (TextView) dialognew
       	                .findViewById(R.id.item_description);
       	        
       	        headertext.setText("PRODUCT DESCRIPTION");

       	         userInput.setText(getData.get(ITEM_DESC));
       	         
       	                final Button Submit = (Button) dialognew
       	                        .findViewById(R.id.desc_ok);
       	                
       	                final Button update_cancel = (Button) dialognew
       	                        .findViewById(R.id.adr_cancel);
       	                
       	                update_cancel.setVisibility(View.GONE);

       	        Submit.setOnClickListener(new View.OnClickListener() {
       	            @Override
       	            public void onClick(View v) {
       	         	
       	                    dialognew.dismiss();

       	            }
       	        });

       	        dialognew.show();
            }
        });
        
        
        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            	 dbvoc = new DataBaseHelper(context);
            	 List<Local_Data> cont1 = dbvoc.Get_OrderProducts_BYITEM_NUMBER(getData.get(ITEM_NUMBER).toString(),Global_Data.GLObalOrder_id);      
	   	          for (Local_Data cnp : cont1) 
	   	          {
	   	        	 
	   	        	// tem.put("order_number", cnp.get_category_code());
	                 //item.put("item_number", cnp.get_custadr());
	   	        	Global_Data.item_no = cnp.get_delivery_product_id();
	   	        	Global_Data.total_qty = cnp.get_stocks_product_quantity();
	   	        	Global_Data.MRP = cnp.getMRP();
	   	        	Global_Data.amount = cnp.get_Claims_amount();
	   	        	Global_Data.scheme_amount = cnp.get_Target_Text();
	   	        	Global_Data.actual_discount = cnp.get_stocks_product_text();
	   	        	Global_Data.product_dec = cnp.get_product_status();
	                 
	   	          }
			 
	   	       Global_Data.GLOVEL_ORDER_REJECT_FLAG = "TRUE";   
			  Intent goToNewOrderActivity = new Intent(context,Item_Edit_Activity.class);
          	  ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
          	  //Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
          	  context.startActivity(goToNewOrderActivity); 
            }
        });
        
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (isPlayStoreInstalled()) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + item.getPackageName())));
                } */
            	
            	AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update
			    alertDialog.setTitle("Warning");
			    alertDialog.setMessage(" Are you sure you want to delete?"); 
			    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						
						 getData = dataArrayList.get(position);
						 Log.d("ITEM_NUMBER", "ITEM_NUMBER"+getData.get(ITEM_NUMBER).toString());
						

						 
	                      if(dataArrayList.size() == 1)
	                      {
							  getServices.SYNCORDER_BYCustomerINSTI_NEW(context,"lost");
	                    	  Intent goToNewOrderActivity = new Intent(context,Status_Activity.class);
	                    	  ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	                    	  //Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
	                    	   context.startActivity(goToNewOrderActivity);

	                      }
						  else
						  {
							  dbvoc = new DataBaseHelper(context);
							  dbvoc.getDeleteTableorderproduct_byITEM_NUMBER(getData.get(ITEM_NUMBER).toString(),Global_Data.GLObalOrder_id);
							  dataArrayList.remove(position);
							  notifyDataSetChanged();
						  }
	                      
						 
						   Toast toast = Toast.makeText(context,"Item Deleted", Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							
						
					     dialog.cancel();
					 
							
						
					}
				});

               alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						  dialog.cancel();
					}
				});
			  

			    alertDialog.show(); 
            }
        });
        //viewHolder.text.setText(temp);
        order_code = viewHolder.text1.getText().toString();

//            viewHolder.childButtonnew.setOnClickListener(new OnClickListener() {
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
        TextView text1,text2,text3;
        TextView address;
        TextView distance;
       // Button editorder;
        Button childButtonnew,edit,delete;
    }
}
