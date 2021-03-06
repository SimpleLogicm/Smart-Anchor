package com.anchor.swipelistview.sample.adapters;

/**
 * Created by vinod on 16/03/17.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.activities.Check_Null_Value;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.NewOrderActivity;
import com.anchor.activities.Order;
import com.anchor.activities.ProductAll_Varients;
import com.anchor.activities.R;
import com.anchor.activities.Schemedetail;
import com.anchor.model.Spiner_List_Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Product_AllVarient_Adapter extends ArrayAdapter<HashMap<String, String>> {

    public TextView tx;
    String sch_name;
    String productname;
    String sch_description;
    String sch_displayname;
    String sch_qualify_quan;
    String sch_amount;

    String scheame_name;
    String product_name;
    String qualify_amouont;
    String qualify_quan;
    String description;
    String discount_in_dp_amount;
    String discount_in_dp_percent;
    String foc_qan;
    String product_details;
    String is_discount_scheme;

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
    static final String SCHEAME_CODE = "scheame_code";
    HashMap<String, String> getData = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> dataAray;
    HashMap<String, String> dataIthjem;
    DataBaseHelper dbvoc = new DataBaseHelper(getContext());


    public interface customButtonListener {
        void onButtonClickListner(int position, String value, View v);
    }

    public void setCustomButtonListner(customButtonListener listener) {
        this.customListner = listener;
    }

    private Context context;

    public Product_AllVarient_Adapter(Context context, ArrayList<HashMap<String, String>> dataItem1, ArrayList<String> list1, ArrayList<String> list2) {
        super(context, R.layout.new_productvarient_raw, dataItem1);
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
            convertView = li.inflate(R.layout.new_productvarient_raw, parent, false);
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
            holder.schem_box = convertView.findViewById(R.id.schem_box);
            holder.schemename = convertView.findViewById(R.id.schemename);
            // holder.rpvnew = (TextView) convertView.findViewById(R.id.rpvnew);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.productquantity.setRawInputType(Configuration.KEYBOARD_QWERTY);
        holder.productquantity.addTextChangedListener(holder.mWatcher1);
        holder.mWatcher1.setActive(false);

        holder.totalprice.addTextChangedListener(holder.mWatcher2);
        holder.mWatcher2.setActive(false);


        getData = dataAray.get(position);

        holder.Productnamerpmrp.setText(getData.get(TAG_ITEMNAME));
        holder.pidp.setText(getData.get(TAG_ITEM_NUMBER));
        holder.pcode_new.setText("CODE : " + getData.get(TAG_ITEM_NUMBER));
        holder.mrpv.setText(getData.get(TAG_PRICE));
        holder.rpv.setText(getData.get(TAG_RP));
        holder.SMP_FLAG.setText(getData.get(TAG_SMP_FLAG));

        holder.mrpvnew.setText("[MRP] : " + getData.get(TAG_PRICE));
        holder.mrpvs.setText("[DLP] : " + getData.get(TAG_RP));

        holder.PSQ.setText("Minimum order qty : " + getData.get(TAG_ITEM_SQ));
        holder.PMQ.setText("MQ : " + getData.get(TAG_ITEM_MQ));
        //  holder.PSTOCK.setText("Stock : " + getData.get(TAG_STOCK));
        holder.PSQVALUE.setText(getData.get(TAG_ITEM_SQ));
        holder.PMQVALUE.setText(getData.get(TAG_ITEM_MQ));
        //holder.rpvnew.setText("RP : " + getData.get(TAG_RP));


        List<Local_Data> scheme_name = dbvoc.getProductscheme_Name(getData.get(SCHEAME_CODE));
        for (Local_Data s : scheme_name) {


            String chekscheame = s.getSche_discription();

            if (chekscheame.equalsIgnoreCase("")) {

                holder.schem_box.setVisibility(View.INVISIBLE);


            } else {
                holder.schem_box.setVisibility(View.VISIBLE);
                holder.schemename.setText(s.getSche_disname());
                sch_name = s.getSche_type();
                productname=s.getSche_name();
                sch_description = s.getSche_discription();
                sch_displayname = s.getSche_disname();
                sch_qualify_quan = s.getQualifying_qty();
                sch_amount = s.getAmount();

                scheame_name = s.getSche_disname();
                product_name= s.getProduct_schm();
                qualify_amouont=s.getAmount();
                qualify_quan= s.getQualifying_qty();
                description = s.getSche_discription();
                discount_in_dp_amount= s.getDiscount_in_dp_amount();
                discount_in_dp_percent= s.getDiscount_in_dp_percent();
                foc_qan= s.getFoc_qty();
                product_details= s.getProduct_schm();
                is_discount_scheme= s.getIs_discount_scheme();



            }


        }

        holder.schem_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Schemedetail.class);
                Global_Data.scheame_type="product";
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("scheame_name", scheame_name);
                i.putExtra("product_name", product_name);
                i.putExtra("qualify_amount", qualify_amouont);
                i.putExtra("qualify_quan", qualify_quan);

                if (description.equals("")){
                    i.putExtra("description","NA");
                }else {
                    i.putExtra("description",description);
                }

                 if (is_discount_scheme .equals("true")){
                     i.putExtra("foc", "DISCOUNT");
                 }else {
                     i.putExtra("foc", "FOC");
                 }

                i.putExtra("discount_in_dp_amount",discount_in_dp_amount);
                i.putExtra("discount_in_dp_percent",discount_in_dp_percent);
                i.putExtra("foc_qan",foc_qan);

                i.putExtra("product_details", product_details);
                getContext().startActivity(i);

                Log.d("Android","scheame_name "+scheame_name);
                Log.d("Android","product_name "+product_name);
                Log.d("Android","qualify_amount "+qualify_amouont);
                Log.d("Android","qualify_quan "+qualify_quan);
                Log.d("Android","description "+description);
                Log.d("Android","is_discount_scheme "+is_discount_scheme);
                Log.d("Android","discount_in_dp_amount "+discount_in_dp_amount);
                Log.d("Android","discount_in_dp_percent "+discount_in_dp_percent);
                Log.d("Android","foc_qan "+foc_qan);
                Log.d("Android","product_details "+product_details);

            }
        });


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

                    Log.d("VVVVVV", "VVVVVV" + holder.SMP_FLAG.getText().toString());
                    Log.d("VR", "VR" + getData.get(TAG_EDIT_CLICK));
                    if (holder.SMP_FLAG.getText().toString().equalsIgnoreCase("true") && getData.get(TAG_EDIT_CLICK).equalsIgnoreCase("false")) {
                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("Message");
                        alertDialog.setMessage("You are Selecting Slow moving Item for which there is No Sales in Last 6 month.");
                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.SMP_FLAG.setText("false");

                            }
                        });

                        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                holder.totalprice.setText("");
                                holder.quantity_error.setText("");
                                //MutableWatcher1.
                                // holder.productquantity.setText("");
                                Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(), "");
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

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(holder.productquantity.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(holder.rpv.getText().toString()) && Integer.parseInt(String.valueOf(s)) > 0) {
                    edit.put("string", s.toString());

                    try {
                        int SQMO_Validator = Integer.parseInt(holder.productquantity.getText().toString()) % Integer.parseInt(holder.PSQVALUE.getText().toString());
                        if (SQMO_Validator == 0) {
                            Double value = Double.valueOf(holder.productquantity.getText().toString()) * Double.valueOf(holder.rpv.getText().toString());
                            holder.totalprice.setText("PRICE : " + value);
                            holder.quantity_error.setText("");
                            Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(), s.toString() + "pq" + value + "pprice" + holder.Productnamerpmrp.getText().toString() + "pmrp" + holder.mrpv.getText().toString() + "prp" + holder.rpv.getText().toString());

                        } else {
                            if (holder.productquantity.getText().toString().trim().equalsIgnoreCase("") || holder.productquantity.getText().toString().equalsIgnoreCase("0")) {
                                holder.totalprice.setText("");
                                holder.quantity_error.setText("");
                            } else {


                                holder.totalprice.setText("");
                                holder.quantity_error.setText("Entered Value Not A Multiple Of Item Minimum Order Quantity.");
                            }

                            Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(), "");


                            //  Toast.makeText(context, "Enter Value Not A Multiple Of Item SQ Value.", Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }


                    // Global_Data.itemmap.put(holder.pidp.getText().toString(),)

                    // sum += value;
                    //  ProductAll_Varients.txttotalPreview.setText("Total "+sum);
                    //ProductAll_Varients.updateSum(sum);
                } else {
                    holder.totalprice.setText("");
                    holder.quantity_error.setText("");
                    Global_Data.Order_hashmap.put(position + "&" + holder.pidp.getText().toString(), "");

                }
            }


        });
        // holder.productquantity.setText();
        // holder.totalprice.setText(getData.get(TAG_ITEM_NUMBER));

        return convertView;
    }

    static class ViewHolder {
        TextView Productnamerpmrp, pidp, mrpv, rpv, mrpvnew, rpvnew, mrpvs, PSTOCK, SMP_FLAG;
        TextView PSQ, PMQ, PSQVALUE, PMQVALUE, quantity_error;
        EditText productquantity;
        TextView totalprice, pcode_new, schemename;
        public MutableWatcher1 mWatcher1;
        public MutableWatcher2 mWatcher2;
        LinearLayout schem_box;


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
            if (mActive) {
                list1.set(mPosition, s.toString());
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
