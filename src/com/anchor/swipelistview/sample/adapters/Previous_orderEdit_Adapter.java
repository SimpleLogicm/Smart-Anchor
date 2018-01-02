package com.anchor.swipelistview.sample.adapters;

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

import com.anchor.activities.AppLocationManager;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.LoginDataBaseAdapter;
import com.anchor.activities.Order;
import com.anchor.activities.PlayService_Location;
import com.anchor.activities.Previous_Item_Edit_Activity;
import com.anchor.activities.Previous_orderNew_S2;
import com.anchor.activities.R;
import com.anchor.swipelistview.SwipeListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Previous_orderEdit_Adapter extends ArrayAdapter<HashMap<String, String>>{

    customButtonListener customListner;
    LoginDataBaseAdapter loginDataBaseAdapter;
    ViewHolder viewHolder;
    ArrayList<String> Amount_tpp = new ArrayList<String>();
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

    public Previous_orderEdit_Adapter(Context context, ArrayList<HashMap<String, String>> dataItem1) {
        super(context, R.layout.previous_orderedit_adapter, dataItem1);
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
            convertView = li.inflate(R.layout.previous_orderedit_adapter, parent, false);
            holder = new ViewHolder();
            holder.tvTitle = (TextView) convertView.findViewById(R.id.example_row_tv_title);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.example_row_tv_description);
            holder.tvPriece = (TextView) convertView.findViewById(R.id.example_row_tv_price);
            holder.order_idn = (TextView) convertView.findViewById(R.id.order_idn);
            holder.bAction1 = (Button) convertView.findViewById(R.id.example_row_b_action_1);
            holder.bAction2 = (Button) convertView.findViewById(R.id.example_row_b_action_2);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ((SwipeListView)parent).recycle(convertView, position);

        getData = dataAray.get(position);

        holder.tvTitle.setText(getData.get(TAG_ITEMNAME));
        holder.tvDescription.setText(getData.get(TAG_QTY));
        holder.tvPriece.setText(getData.get(TAG_PRICE));
        holder.order_idn.setText(getData.get(TAG_ITEM_NUMBER));

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
                Log.d("ITEM_NUMBER", "ITEM_NUMBER"+getData.get(TAG_ITEM_NUMBER).toString());

//				 String total_qty = "";
//				 String MRP = "";
//				 String amount = "";
//				 String scheme_amount = "";
//				 String actual_discount = "";
//				 String product_dec = "";
                dbvoc = new DataBaseHelper(context);

                if(Global_Data.Previous_Order_ServiceOrder_ID.equalsIgnoreCase(Global_Data.Previous_Order_UpdateOrder_ID)) {
                    List<Local_Data> cont1 = dbvoc.Get_OrderProducts_BYITEM_NUMBERPre(getData.get(TAG_ITEM_NUMBER).toString(), Global_Data.Previous_Order_ServiceOrder_ID);
                    for (Local_Data cnp : cont1) {

                        // tem.put("order_number", cnp.get_category_code());
                        //item.put("item_number", cnp.get_custadr());
                        Global_Data.item_no = cnp.get_delivery_product_id();
                        Global_Data.total_qty = cnp.get_stocks_product_quantity();
                        Global_Data.MRP = cnp.getMRP();
                        Global_Data.RP = cnp.getRP();
                        Global_Data.amount = cnp.get_Claims_amount();
                        Global_Data.scheme_amount = cnp.get_Target_Text();
                        Global_Data.actual_discount = cnp.get_stocks_product_text();
                        Global_Data.product_dec = cnp.get_product_status();
                        Global_Data.pre_schecode = cnp.getSche_code();

                    }
                }
                else
                {

                    List<Local_Data> cont1 = dbvoc.Get_OrderProducts_BYITEM_NUMBER(getData.get(TAG_ITEM_NUMBER).toString(), Global_Data.GLObalOrder_id);
                    for (Local_Data cnp : cont1) {

                        // tem.put("order_number", cnp.get_category_code());
                        //item.put("item_number", cnp.get_custadr());
                        Global_Data.item_no = cnp.get_delivery_product_id();
                        Global_Data.total_qty = cnp.get_stocks_product_quantity();
                        Global_Data.MRP = cnp.getMRP();
                        Global_Data.RP = cnp.getRP();
                        Global_Data.amount = cnp.get_Claims_amount();
                        Global_Data.scheme_amount = cnp.get_Target_Text();
                        Global_Data.actual_discount = cnp.get_stocks_product_text();
                        Global_Data.product_dec = cnp.get_product_status();
                        Global_Data.pre_schecode = cnp.getSche_code();

                    }

                }


                Global_Data.GLOVEL_ORDER_REJECT_FLAG = "";
                Intent goToNewOrderActivity = new Intent(context,Previous_Item_Edit_Activity.class);
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
                        Log.d("ITEM_NUMBER", "ITEM_NUMBER"+getData.get(TAG_ITEM_NUMBER).toString());

                        dbvoc = new DataBaseHelper(context);

                        loginDataBaseAdapter=new LoginDataBaseAdapter(context);
                        loginDataBaseAdapter=loginDataBaseAdapter.open();

                        if(Global_Data.Previous_Order_ServiceOrder_ID.equalsIgnoreCase(Global_Data.Previous_Order_UpdateOrder_ID)) {
                            List<Local_Data> pcheck = dbvoc.getItemNamePrevious_OrderRecordCheck();
                            if(pcheck.size() == 1)
                            {
                                Toast toast = Toast.makeText(context,"You can not delete single item.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            else
                            {
                                dbvoc.getDeleteTablePreviousorderproduct_byITEM_NUMBER(getData.get(TAG_ITEM_NUMBER).toString(),Global_Data.Previous_Order_UpdateOrder_ID);
                                dataAray.remove(position);
                                notifyDataSetChanged();

                                Long randomPIN = System.currentTimeMillis();
                                String PINString = String.valueOf(randomPIN);

                                Global_Data.Previous_Order_UpdateOrder_ID = "Ord"+PINString;

                                Global_Data.GLObalOrder_id = "Ord"+PINString;
                                Global_Data.GLOvel_GORDER_ID = "Ord"+PINString;
                                // Global_Data.GLOvel_GORDER_ID = "Ord"+PINString;

                                try
                                {
                                    AppLocationManager appLocationManager = new AppLocationManager(context);
                                    Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
                                    Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);
                                    PlayService_Location PlayServiceManager = new PlayService_Location(context);

                                    if(PlayServiceManager.checkPlayServices(context))
                                    {
                                        Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

                                    }
                                    else
                                    if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
                                    {
                                        Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
                                        Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
                                    }

                                }catch(Exception ex){ex.printStackTrace();}

                                dbvoc.getDeleteTable("previous_orders");
                                loginDataBaseAdapter.insertOrders("",  Global_Data.Previous_Order_UpdateOrder_ID, Global_Data.GLOvel_CUSTOMER_ID, Global_Data.order_retailer, Global_Data.GLOvel_USER_EMAIL, Global_Data.order_city, Global_Data.order_beat, "", "", "", "", "", "", "", "",Global_Data.order_retailer,Global_Data.order_state,Global_Data.order_city,Global_Data.sales_btnstring,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,Global_Data.Glovel_BEAT_ID,"","","","","","","","");

                                List<Local_Data> cont1 = dbvoc.getItemNamePrevious_OrderChecknew(Global_Data.Previous_Order_ServiceOrder_ID);
                                for (Local_Data cnt1 : cont1) {

                                    loginDataBaseAdapter.insertOrderProducts("", "", Global_Data.GLOvel_GORDER_ID, "","","","", "",cnt1.getSche_code().trim(), " ", "",cnt1.getQty().trim(),cnt1.getRP().trim(),cnt1.getPrice().trim(), cnt1.getAmount().trim(), "", "",Global_Data.order_retailer," ",cnt1.get_category_ids()," ",cnt1.getProduct_nm());
                                }

                                dbvoc.getDeleteTable("previous_order_products");

                                Double sum = 0.0;
                                //dbvoc = new DataBaseHelper(context);
                                List<Local_Data> cont2 = dbvoc.getItemName(Global_Data.GLObalOrder_id);
                                for (Local_Data cnt1 : cont2) {

                                    Amount_tpp.add(cnt1.getAmount());
                                    //txttotalPreview.setText("Total		:		"+cnt1.getAmount());
                                    //Toast.makeText(NewOrderActivity.this, "Login:"+Global_Data.order_id,Toast.LENGTH_SHORT).show();
                                    //SwipeList.add(mapp);
                                }

                                for(int m=0; m<Amount_tpp.size(); m++)
                                {
                                    sum += Double.valueOf(Amount_tpp.get(m));
                                }
                                //Float a = 0.0f;
                               // Previous_orderNew_S2.updateSum(sum);

                               // PreviewOrderSwipeActivity.updateSum(sum);
                                Intent goToNewOrderActivity = new Intent(context,Previous_orderNew_S2.class);
                                // ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                //Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
                                context.startActivity(goToNewOrderActivity);
                            }
                        }
                        else
                        {

                            dbvoc.getDeleteTableorderproduct_byITEM_NUMBER(getData.get(TAG_ITEM_NUMBER).toString(),Global_Data.GLObalOrder_id);
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
                                Double sum = 0.0;
                                //dbvoc = new DataBaseHelper(context);
                                List<Local_Data> cont1 = dbvoc.getItemName(Global_Data.GLObalOrder_id);
                                for (Local_Data cnt1 : cont1) {

                                    Amount_tpp.add(cnt1.getAmount());
                                    //txttotalPreview.setText("Total		:		"+cnt1.getAmount());
                                    //Toast.makeText(NewOrderActivity.this, "Login:"+Global_Data.order_id,Toast.LENGTH_SHORT).show();
                                    //SwipeList.add(mapp);
                                }

                                for(int m=0; m<Amount_tpp.size(); m++)
                                {
                                    sum += Double.valueOf(Amount_tpp.get(m));
                                }
                                //Float a = 0.0f;
                              //  Previous_orderNew_S2.updateSum(sum);
                                Intent goToNewOrderActivity = new Intent(context,Previous_orderNew_S2.class);
                                // ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                //Intent order_home = new Intent(context,PreviewOrderSwipeActivity.class);
                                context.startActivity(goToNewOrderActivity);

                            }


                            Toast toast = Toast.makeText(context,"Item Deleted", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }



                        String order="";
                        SharedPreferences sp = context.getSharedPreferences("SimpleLogic", 0);

                        order=sp.getString("order", "");
                        dialog.cancel();
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
        TextView tvTitle,order_idn;
        TextView tvDescription;
        TextView tvPriece;
        Button bAction1;
        Button bAction2;

    }



}
