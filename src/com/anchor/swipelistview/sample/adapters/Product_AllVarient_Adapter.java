package com.anchor.swipelistview.sample.adapters;

/**
 * Created by vinod on 16/03/17.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.anchor.activities.Check_Null_Value;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Product_AllVarient_Adapter extends ArrayAdapter<HashMap<String, String>> {

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

    public Product_AllVarient_Adapter(Context context, ArrayList<HashMap<String, String>> dataItem1, ArrayList<String> list1, ArrayList<String> list2) {
        super(context, R.layout.all_varients_adapternew, dataItem1);
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
            convertView = li.inflate(R.layout.all_varients_adapternew, parent, false);
            holder = new ViewHolder();
            holder.mWatcher1 = new MutableWatcher1();
            holder.mWatcher2 = new MutableWatcher2();
            holder.Productnamerpmrp = (TextView) convertView.findViewById(R.id.Productnamerpmrp);
            holder.pidp = (TextView) convertView.findViewById(R.id.pidp);
            holder.productquantity = (EditText) convertView.findViewById(R.id.productquantityp);
            holder.totalprice = (TextView) convertView.findViewById(R.id.totalpricep);
            holder.mrpv = (TextView) convertView.findViewById(R.id.mrpv);
            holder.mrpvs = (TextView) convertView.findViewById(R.id.mrpvs);
            holder.mrpvnew = (TextView) convertView.findViewById(R.id.mrpvnew);
            holder.rpv = (TextView) convertView.findViewById(R.id.rpv);
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
        holder.mrpv.setText(getData.get(TAG_PRICE));
        holder.rpv.setText(getData.get(TAG_RP));

        holder.mrpvnew.setText("[MRP] : " + getData.get(TAG_PRICE));
        holder.mrpvs.setText("[RP] : " + getData.get(TAG_RP));
        //holder.rpvnew.setText("RP : " + getData.get(TAG_RP));

        holder.productquantity.setText(list1.get(position), TextView.BufferType.EDITABLE);

        holder.totalprice.setText(list2.get(position), TextView.BufferType.EDITABLE);

        holder.mWatcher1.setPosition(position);
        holder.mWatcher1.setActive(true);

        holder.mWatcher2.setPosition(position);
        holder.mWatcher2.setActive(true);

        //  holder.productquantity.setId(position);

        //  holder.totalprice.setId(position);

//        //we need to update adapter once we finish with editing


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

                if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(holder.productquantity.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanew(holder.mrpv.getText().toString())) {
                    edit.put("string", s.toString());

                    Double value = Double.valueOf(holder.productquantity.getText().toString()) * Double.valueOf(holder.mrpv.getText().toString());
                    holder.totalprice.setText("PRICE : " + String.valueOf(value));

                    // Global_Data.itemmap.put(holder.pidp.getText().toString(),)

                    // sum += value;
                    //  ProductAll_Varients.txttotalPreview.setText("Total "+sum);
                    //ProductAll_Varients.updateSum(sum);
                } else {
                    holder.totalprice.setText("");
                }


            }
        });
        // holder.productquantity.setText();
        // holder.totalprice.setText(getData.get(TAG_ITEM_NUMBER));

        return convertView;
    }

    static class ViewHolder {
        TextView Productnamerpmrp, pidp, mrpv, rpv, mrpvnew, rpvnew,mrpvs;
        EditText productquantity;
        TextView totalprice;
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
