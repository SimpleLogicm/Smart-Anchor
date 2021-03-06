package com.anchor.adapter;

/**
 * Created by vinod on 16/03/17.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.anchor.activities.Check_Null_Value;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.R;
import com.anchor.activities.Sub_Dealer_ProductList;

import java.util.ArrayList;
import java.util.HashMap;

public class Sub_Dealer_ProductList_Adapter extends ArrayAdapter<HashMap<String, String>> {

    public TextView tx;
    static Double sum = 0.0;
    customButtonListener customListner;
    ArrayList<String> list1 = new ArrayList<String>();
    ArrayList<String> list2 = new ArrayList<String>();
    ViewHolder viewHolder;
    ArrayList<String> Amount_tpp = new ArrayList<String>();
    static final String TAG_ITEMNAME = "product_name";
    static final String TAG_QTY = "total_qty";
    static final String TAG_PRICE = "MRP";
    static final String TAG_RP = "RP";
    static final String TAG_AMOUNT = "amount";
    static final String TAG_ITEM_NUMBER = "item_number";
    static final String TAG_ITEM_SQ = "SQ";
    static final String TAG_ITEM_MQ = "MQ";
    static final String TAG_STOCK = "PRODUCT_STOCK";
    static final String TAG_SMP_FLAG = "Smp_Flag";
    static final String TAG_EDIT_CLICK = "Edit_Click";
    HashMap<String, String> getData = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> dataAray;
    HashMap<String, String> dataIthjem;
    DataBaseHelper dbvoc;

    public interface customButtonListener {
        void onButtonClickListner(int position, String value, View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;

    public Sub_Dealer_ProductList_Adapter(Context context, ArrayList<HashMap<String, String>> dataItem1, ArrayList<String> list1, ArrayList<String> list2) {
        super(context, R.layout.sub_dealer_productlist_adapter, dataItem1);
        this.dataAray = dataItem1;
        this.list1 = list1;
        this.list2 = list2;


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
            convertView = li.inflate(R.layout.sub_dealer_productlist_adapter, parent, false);
            holder = new ViewHolder();
            holder.mWatcher1 = new MutableWatcher1();
            holder.mWatcher2 = new MutableWatcher2();
            holder.Productnamerpmrp = convertView.findViewById(R.id.Productnamerpmrp);
            holder.pidp = convertView.findViewById(R.id.pidp);
            holder.pcode_new = convertView.findViewById(R.id.pcode_new);
            holder.productquantity = convertView.findViewById(R.id.productquantityp);
            holder.totalprice = convertView.findViewById(R.id.totalpricep);
            holder.mrpv = convertView.findViewById(R.id.mrpv);
            holder.mrpvs = convertView.findViewById(R.id.mrpvs);
            holder.mrpvnew = convertView.findViewById(R.id.mrpvnew);
            holder.rpv = convertView.findViewById(R.id.rpv);
            holder.PSQ = convertView.findViewById(R.id.PSQ);
            holder.PMQ = convertView.findViewById(R.id.PMQ);
            holder.PSQVALUE = convertView.findViewById(R.id.PSQVALUE);
            holder.PMQVALUE = convertView.findViewById(R.id.PMQVALUE);
            holder.PSTOCK = convertView.findViewById(R.id.PSTOCK);
            holder.quantity_error = convertView.findViewById(R.id.quantity_error);
            holder.SMP_FLAG = convertView.findViewById(R.id.SMP_FLAG);
            // holder.rpvnew = (TextView) convertView.findViewById(R.id.rpvnew);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        getData = dataAray.get(position);
        holder.productquantity.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.productquantity.addTextChangedListener(holder.mWatcher1);
        holder.mWatcher1.setActive(false);
       // holder.mDetailEditText.setText(dataAray.get(position), TextView.BufferType.EDITABLE);
        holder.mWatcher1.setPosition(position);
        holder.mWatcher1.setActive(true);

        holder.totalprice.addTextChangedListener(holder.mWatcher2);
        holder.mWatcher2.setActive(false);




        holder.Productnamerpmrp.setText(getData.get(TAG_ITEMNAME));
        holder.pidp.setText(getData.get(TAG_ITEM_NUMBER));
        holder.pcode_new.setText("CODE : "+getData.get(TAG_ITEM_NUMBER));
        holder.mrpv.setText(getData.get(TAG_PRICE));
        holder.rpv.setText(getData.get(TAG_RP));

        holder.mrpvnew.setText("[MRP] : " + getData.get(TAG_PRICE));
        holder.mrpvs.setText("[DLP] : " + getData.get(TAG_RP));

        holder.PSQ.setText("Minimum order qty : " + getData.get(TAG_ITEM_SQ));
        holder.PMQ.setText("MQ : " + getData.get(TAG_ITEM_MQ));
        //  holder.PSTOCK.setText("Stock : " + getData.get(TAG_STOCK));
        holder.PSQVALUE.setText(getData.get(TAG_ITEM_SQ));
        holder.PMQVALUE.setText(getData.get(TAG_ITEM_MQ));
        //holder.rpvnew.setText("RP : " + getData.get(TAG_RP));

        holder.productquantity.setText(list1.get(position), TextView.BufferType.EDITABLE);

        holder.totalprice.setText(list2.get(position), TextView.BufferType.EDITABLE);

        holder.mWatcher1.setPosition(position);
        holder.mWatcher1.setActive(true);

        holder.mWatcher2.setPosition(position);
        holder.mWatcher2.setActive(true);


        holder.productquantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {

                    Log.d("VVVVVV","VVVVVV"+holder.SMP_FLAG.getText().toString());
                    Log.d("VR","VR"+getData.get(TAG_EDIT_CLICK));
                    if(holder.SMP_FLAG.getText().toString().equalsIgnoreCase("true") &&  getData.get(TAG_EDIT_CLICK).equalsIgnoreCase("false"))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage("You are Selecting Slow moving Item for which there is No Sales in Last 6 month.");
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.SMP_FLAG.setText("false");

                            }
                        });

                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel",new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                holder.totalprice.setText("");
                                holder.quantity_error.setText("");
                                //MutableWatcher1.
                                // holder.productquantity.setText("");
                                Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(),"");
                            }
                        });

                        alertDialog.show();
                    }
                } else {
                    // Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.productquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                HashMap<String, String> edit = new HashMap<>();

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(holder.productquantity.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(holder.rpv.getText().toString()) && Integer.parseInt(String.valueOf(s))>0) {
                    edit.put("string", s.toString());

                    try
                    {
                        int SQMO_Validator = Integer.parseInt(holder.productquantity.getText().toString())%Integer.parseInt(holder.PSQVALUE.getText().toString());
                        if(SQMO_Validator == 0)
                        {
                            Double value = Double.valueOf(holder.productquantity.getText().toString()) * Double.valueOf(holder.rpv.getText().toString());
                            holder.totalprice.setText("PRICE : " + String.format("%.2f", value));
                            holder.quantity_error.setText("");
                           // Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(), s.toString() +"pq" + String.valueOf(value)+"pprice"+holder.Productnamerpmrp.getText().toString()+"pmrp"+holder.mrpv.getText().toString()+"prp"+holder.rpv.getText().toString());
                           // Global_Data.Some_list.set(position,holder.totalprice.getText().toString());
                        }
                        else
                        {
                            if(holder.productquantity.getText().toString().trim().equalsIgnoreCase("") || holder.productquantity.getText().toString().equalsIgnoreCase("0"))
                            {
                                holder.totalprice.setText("");
                                holder.quantity_error.setText("");
                            }
                            else
                            {


                                holder.totalprice.setText("");
                                holder.quantity_error.setText("Entered Value Not A Multiple Of Item Minimum Order Quantity.");
                            }

                            //Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(),"");
                            //Global_Data.Some_list.set(position,String.valueOf(0));


                            //  Toast.makeText(context, "Enter Value Not A Multiple Of Item SQ Value.", Toast.LENGTH_LONG).show();
                        }


                    }catch (Exception exception){
                        exception.printStackTrace();
                    }


                    // Global_Data.itemmap.put(holder.pidp.getText().toString(),)

                    // sum += value;
                    //  ProductAll_Varients.txttotalPreview.setText("Total "+sum);
//                    Sub_Dealer_ProductList.updateSum(sum);
                } else {
                    holder.totalprice.setText("");
                    holder.quantity_error.setText("");
                   // Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(),"");
                   // Global_Data.Some_list.set(position,String.valueOf(0));
                   // Sub_Dealer_ProductList.updateSum(sum);

                }


            }
        });
        // holder.productquantity.setText();
        // holder.totalprice.setText(getData.get(TAG_ITEM_NUMBER));

        return convertView;
    }

    static class ViewHolder {
        TextView Productnamerpmrp, pidp, mrpv, rpv, mrpvnew, rpvnew,mrpvs,PSTOCK,SMP_FLAG;
        TextView PSQ,PMQ,PSQVALUE,PMQVALUE,quantity_error;
        EditText productquantity;
        TextView totalprice,pcode_new;
        public MutableWatcher1 mWatcher1;
        public MutableWatcher2 mWatcher2;


    }

    class MutableWatcher1 implements TextWatcher {
        private int mPosition;
        private boolean mActive;

        void setPosition(int position) {
            mPosition = position;
        }

        void setActive(boolean active) {
            mActive = active;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            Double total = 0.0;
            if (mActive) {
                list1.set(mPosition, s.toString());

                getData = dataAray.get(mPosition);

                HashMap<String, String> edit = new HashMap<>();

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(s.toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(getData.get(TAG_RP)) && Integer.parseInt(s.toString())>0) {
                    edit.put("string", s.toString());

                    try
                    {
                        int SQMO_Validator = Integer.parseInt(s.toString())%Integer.parseInt(getData.get(TAG_ITEM_SQ));
                        if(SQMO_Validator == 0)
                        {
                            Double value = Double.valueOf(s.toString()) * Double.valueOf(getData.get(TAG_RP));
                          //  holder.totalprice.setText("PRICE : " + String.valueOf(value));
                          //  holder.quantity_error.setText("");
                            Global_Data.Order_hashmap.put(mPosition + "&" + getData.get(TAG_ITEM_NUMBER), s.toString() +"pq" + value +"pprice"+getData.get(TAG_ITEMNAME)+"pmrp"+getData.get(TAG_PRICE)+"prp"+getData.get(TAG_RP));
                            // Global_Data.Some_list.set(position,holder.totalprice.getText().toString());
                        }
                        else
                        {


                            Global_Data.Order_hashmap.put(mPosition + "&" + getData.get(TAG_ITEM_NUMBER),"");
                            //Global_Data.Some_list.set(position,String.valueOf(0));


                            //  Toast.makeText(context, "Enter Value Not A Multiple Of Item SQ Value.", Toast.LENGTH_LONG).show();
                        }


                    }catch (Exception exception){
                        exception.printStackTrace();
                    }


                    // Global_Data.itemmap.put(holder.pidp.getText().toString(),)

                    // sum += value;
                    //  ProductAll_Varients.txttotalPreview.setText("Total "+sum);
                   Sub_Dealer_ProductList.updateSum(sum);
                } else {
                  //  holder.totalprice.setText("");
                   // holder.quantity_error.setText("");
                    Global_Data.Order_hashmap.put(mPosition + "&" + getData.get(TAG_ITEM_NUMBER),"");
                    // Global_Data.Some_list.set(position,String.valueOf(0));
                     Sub_Dealer_ProductList.updateSum(sum);

                }
            }



        }
    }

    class MutableWatcher2 implements TextWatcher {
        private int mPosition;
        private boolean mActive;

        void setPosition(int position) {
            mPosition = position;
        }

        void setActive(boolean active) {
            mActive = active;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mActive) {
                list2.set(mPosition, s.toString());
            }
        }
    }


}
