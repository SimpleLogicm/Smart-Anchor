 package com.anchor.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class ReturnOrder_PackageAdapter extends ArrayAdapter<HashMap<String, String>>{

	customButtonListener customListner;
    ViewHolder viewHolder;
    ArrayList<String> Amount_tpp = new ArrayList<String>();
    
    static final String TAG_ORDERID = "order_id";
    static final String TAG_PRODUCTNM = "product_name";
    static final String TAG_ITEMNAME = "product_name";
    static final String TAG_QTY = "total_qty";
    static final String TAG_PRICE = "MRP";
    static final String TAG_AMOUNT = "amount";
    static final String TAG_ITEM_NUMBER = "item_number";
    HashMap<String, String> getData = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> dataAray;
    HashMap<String, String> dataIthjem;
    DataBaseHelper dbvoc;
    
    public interface customButtonListener {
        public void onButtonClickListner(int position, String value, View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;

    public ReturnOrder_PackageAdapter(Context context, ArrayList<HashMap<String, String>> dataItem1) {
        super(context, R.layout.prev_text, dataItem1);
        this.dataAray = dataItem1;
        this.context = context;
    }

//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public Product getItem(int position) {
//        return data.get(position);
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public boolean isEnabled(int position) {
//        if (position == 2) {
//            return false;
//        } else {
//            return true;
//        }
//    }

    @SuppressLint("ShowToast")
	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //final Product item = getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.prev_text, parent, false);
            holder = new ViewHolder();
            holder.tvtext1 = (TextView) convertView.findViewById(R.id.txt1_prev); 
            holder.tvtext2 = (TextView) convertView.findViewById(R.id.txt2_prev);
//            holder.tvPriece = (TextView) convertView.findViewById(R.id.example_row_tv_price);
//            holder.order_idn = (TextView) convertView.findViewById(R.id.order_idn);
            holder.bAction1 = (Button) convertView.findViewById(R.id.example_row_b_action_1);
            holder.bAction2 = (Button) convertView.findViewById(R.id.example_row_b_action_2);
           
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);
        
        getData = dataAray.get(position);

        holder.tvtext1.setText(getData.get(TAG_ORDERID));
        holder.tvtext2.setText(getData.get(TAG_PRODUCTNM));
        
        
//        holder.tvPriece.setText(getData.get(TAG_PRICE));
//        holder.order_idn.setText(getData.get(TAG_ITEM_NUMBER));
        
//        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            	holder.tvTitle.setText(getData.get(TAG_ITEMNAME));
//            	//getListViewnew(holder.tvTitle.getText().toString());
//            	//Toast.makeText(context, "ddd:"+getData.get(TAG_ITEMNAME),Toast.LENGTH_SHORT).show(); 
//            	
//            	final Dialog dialognew = new Dialog(context);
//       	        dialognew.setCancelable(false);
//       	        dialognew.requestWindowFeature(Window.FEATURE_NO_TITLE);
//       	        //tell the Dialog to use the dialog.xml as it's layout description
//       	        dialognew.setContentView(R.layout.onclick_dialog);
//
//       	        final EditText userInput = (EditText) dialognew
//       	                .findViewById(R.id.prod_desc);
//       	        
//       	        final TextView headertext = (TextView) dialognew
//       	                .findViewById(R.id.item_description);
//       	        
//       	        headertext.setText("PRODUCT DESCRIPTION");
//
//       	         userInput.setText(getData.get(TAG_ITEMNAME));
//       	         
//       	                final Button Submit = (Button) dialognew
//       	                        .findViewById(R.id.desc_ok);
//       	                
//       	                final Button update_cancel = (Button) dialognew
//       	                        .findViewById(R.id.adr_cancel);
//       	                
//       	                update_cancel.setVisibility(View.GONE);
//
//       	        Submit.setOnClickListener(new View.OnClickListener() {
//       	            @Override
//       	            public void onClick(View v) {
//       	         	
//       	                    dialognew.dismiss();
//
//       	            }
//       	        });
//
//       	        dialognew.show();
//            }
//        });
        
        holder.bAction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	getData = dataAray.get(position);
            	
				 Log.d("EDIT ORDER ID", "EDIT ORDER ID"+getData.get(TAG_ORDERID).toString());
				 
//				 String total_qty = "";
//				 String MRP = "";
//				 String amount = "";
//				 String scheme_amount = "";
//				 String actual_discount = "";
//				 String product_dec = "";
//				 dbvoc = new DataBaseHelper(context);
//				 List<Local_Data> cont1 = dbvoc.Get_OrderProducts_BYITEM_NUMBER(getData.get(TAG_ITEM_NUMBER).toString(),Global_Data.GLObalOrder_id);      
//	   	          for (Local_Data cnp : cont1) 
//	   	          {
//	   	        	 
//	   	        	// tem.put("order_number", cnp.get_category_code());
//	                 //item.put("item_number", cnp.get_custadr());
//	   	        	Global_Data.item_no = cnp.get_delivery_product_id();
//	   	        	Global_Data.total_qty = cnp.get_stocks_product_quantity();
//	   	        	Global_Data.MRP = cnp.getMRP();
//	   	        	Global_Data.RP = cnp.getRRP();
//	   	        	Global_Data.amount = cnp.get_Claims_amount();
//	   	        	Global_Data.scheme_amount = cnp.get_Target_Text();
//	   	        	Global_Data.actual_discount = cnp.get_stocks_product_text();
//	   	        	Global_Data.product_dec = cnp.get_product_status();
//	                 
//	   	          }
              dbvoc = new DataBaseHelper(context);
	   	      Global_Data.GLOVEL_ORDER_REJECT_FLAG = "";  
	   	      Global_Data.GLObalOrder_id_return = getData.get(TAG_ORDERID).toString();
	   	  
	   	      Global_Data.GLOvel_GORDER_ID_RETURN = getData.get(TAG_ORDERID).toString();

                List<Local_Data> contacts = dbvoc.GetOrdersBYORDER_IDRETURN(Global_Data.GLOvel_GORDER_ID_RETURN);
                //List<Local_Data> contacts = dbvoc.getAllOrderby_cusID("1012");

                for (Local_Data cn : contacts)
                {
                    Global_Data.GLOvel_CUSTOMER_ID = cn.getLEGACY_CUSTOMER_CODE();
                }

                List<Local_Data> contactnew = dbvoc.getCustomername(Global_Data.GLOvel_CUSTOMER_ID);
                for (Local_Data cn : contactnew)
                {
                    Global_Data.customer_MobileNumber = cn.getMOBILE_NO();
                    Global_Data.CUSTOMER_NAME_NEW = cn.getCUSTOMER_NAME();
                    Global_Data.CUSTOMER_ADDRESS_NEW = cn.getAddress();
                    Global_Data.order_retailer =  cn.getCUSTOMER_SHOPNAME();

                }

                try {


                    List<Local_Data> contactlimit = dbvoc.getCreditprofileData(Global_Data.GLOvel_CUSTOMER_ID);
                    if (contactlimit.size() > 0) {

                        for (Local_Data cn : contactlimit) {

//
                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.get_shedule_outstanding_amount())) {


                                Global_Data.AmountOutstanding = cn.get_shedule_outstanding_amount();
                            } else {


                                Global_Data.AmountOutstanding = "0.0";
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(cn.getAmmount_overdue())) {


                                Global_Data.AmountOverdue = cn.getAmmount_overdue();
                            } else {

                                Global_Data.AmountOverdue = "0.0";
                            }


                        }
                    } else {
                        Global_Data.AmountOutstanding = "0.0";
                        Global_Data.AmountOverdue = "0.0";

                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                Global_Data.GLOVEL_ORDER_REJECT_FLAG = "FALSE";
	   	      
	   	      Global_Data.PREVIOUS_ORDER_BACK_FLAG_REURN = "TRUE";
	   	  
			  Intent goToNewOrderActivity = new Intent(context,ReturnOrder2.class);
           	  ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
           	  //Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
           	  context.startActivity(goToNewOrderActivity); 
           	
            }
        });

        holder.bAction2.setOnClickListener(new View.OnClickListener() {
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
						
						
						 getData = dataAray.get(position);
						 Log.d("DELETE ORDER ID", "DELETE ORDER ID"+getData.get(TAG_ORDERID).toString());
						
						 dbvoc = new DataBaseHelper(context);
						 dbvoc.getDeleteTableorder_byORDER_ID_return(getData.get(TAG_ORDERID).toString());
						 dataAray.remove(position);
	                      notifyDataSetChanged();
						 
	                      if(dataAray.size() <= 0)
	                      {
	                    	  Intent goToNewOrderActivity = new Intent(context,Order.class);
	                    	  ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	                    	  //Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
	                    	   context.startActivity(goToNewOrderActivity);  
	                      }
	                      else
	                      {
	                    	  Toast toast = Toast.makeText(context,"Item Deleted", Toast.LENGTH_LONG);
							  toast.setGravity(Gravity.CENTER, 0, 0);
							  toast.show();
								
							  String order="";
							  SharedPreferences sp = context.getSharedPreferences("SimpleLogic", 0);

						     order=sp.getString("order", "");
						     dialog.cancel();
	                      }
						 
						    
					    // ((Activity) context).finish();
//							Intent goToNewOrderActivity = new Intent(context,NewOrderActivity.class);
//							goToNewOrderActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
							//((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						   //  Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
						   //  context.startActivity(order_home);
						   //  ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
						    // Log.e("DATA", "order-"+order);
//							if (data.size()==0) {
//								
//								
//							    
//							    if (order.equalsIgnoreCase("new")) {
//							    	
//							    	((Activity) context).finish();
//									Intent goToNewOrderActivity = new Intent(context,NewOrderActivity.class);
//									goToNewOrderActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
//									((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//									
//
//									goToNewOrderActivity.putExtra("retialer",
//											"" + sp.getString("RetailerName", ""));
//									//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//									
//									context.startActivity(goToNewOrderActivity);
//								}
//							    
//							    
//							    if (order.equalsIgnoreCase("return")) {
//							    	
//							    	((Activity) context).finish();
//									Intent goToNewOrderActivity = new Intent(context,ReturnOrderActivity.class);
//									goToNewOrderActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
//									((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//									
//
//									goToNewOrderActivity.putExtra("retialer",
//											"" + sp.getString("RetailerName", ""));
//									//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//									
//									context.startActivity(goToNewOrderActivity);
//								}
//								
//							    if (order.equalsIgnoreCase("previous")) {
//							    	
//							    	((Activity) context).finish();
//									
//									((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//									
//									
//									
//									 
//								}
//								
//								
//							   
//							}
//							else {
//								((Activity) context).finish();
//								Intent goTorefreshActivity = new Intent(context,PreviewOrderSwipeActivity.class);
//								//Log.e("DATA", "order-"+order);
//								 if (order.equalsIgnoreCase("return")) {
//									 goTorefreshActivity.putExtra("return", "return");
//								}
//								
//								 else if (order.equalsIgnoreCase("new")) {
//								    	
//									 goTorefreshActivity.putExtra("new", "new");
//								    	
//								    }
//								 
//								if (order.equalsIgnoreCase("previous")) {
//									
//									goTorefreshActivity.putExtra("previous", "previous");
//								}
//								
//								goTorefreshActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
//								//((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//								goTorefreshActivity.putParcelableArrayListExtra("productsList", data);
//
//								 sp = context.getSharedPreferences("SimpleLogic", 0);
//
//								goTorefreshActivity.putExtra("retialer",
//										"" + sp.getString("RetailerName", ""));
//								//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//								
//								context.startActivity(goTorefreshActivity);
//							}
							
						
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

        return convertView;
    }

    static class ViewHolder {
        TextView tvtext1,tvtext2;
        TextView tvDescription;
        TextView tvPriece;
        Button bAction1;
        Button bAction2;
      
    }

}
