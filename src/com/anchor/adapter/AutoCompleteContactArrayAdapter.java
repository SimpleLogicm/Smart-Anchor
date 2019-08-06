package com.anchor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.SubDealerModel;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.Check_Null_Value;


public class AutoCompleteContactArrayAdapter extends
        ArrayAdapter  {
    private Context mContext;
    ArrayList<SubDealerModel> mContactList;

    private ListFilter listFilter = new ListFilter();
    private List<SubDealerModel> dataListAllItems;
    private int itemLayout;

    public AutoCompleteContactArrayAdapter(Context context,int resource,
                                           ArrayList<SubDealerModel> objects) {
        super(context, resource, objects);
        mContext = context;
        mContactList = objects;
        itemLayout = resource;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public String getItem(int position) {
        Log.d("CustomListAdapter",
                mContactList.get(position).shop_name);
        return mContactList.get(position).shop_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(itemLayout, parent,
                false);
        TextView aushop_name =  rowView.findViewById(R.id.aushop_name);
        TextView aufirm_name =  rowView.findViewById(R.id.aufirm_name);
        TextView auproname =  rowView.findViewById(R.id.auproname);
        TextView aumobile =  rowView.findViewById(R.id.aumobile);
        TextView auemail =  rowView.findViewById(R.id.auemail);
        TextView auproname2 =  rowView.findViewById(R.id.auproname2);
        TextView aumobile2 =  rowView.findViewById(R.id.aumobile2);
        TextView auemai2 =  rowView.findViewById(R.id.auemail2);
        TextView aucity =  rowView.findViewById(R.id.aucity);


        if(mContactList.size() > position)
        {
            SubDealerModel a = mContactList.get(position);

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getShop_name()))
            {
                aushop_name.setVisibility(View.VISIBLE);
                aushop_name.setText("Shop Name : "+a.getShop_name());
            }
            else
            {
                aushop_name.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getFirm_name()))
            {
                aufirm_name.setVisibility(View.VISIBLE);
                aufirm_name.setText("Firm Name : "+a.getFirm_name());
            }
            else
            {
                aufirm_name.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getProprietor_name1()))
            {
                auproname.setVisibility(View.VISIBLE);
                auproname.setText("proprietor Name : "+a.getProprietor_name1());
            }
            else
            {
                auproname.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getProprietor_mobile1()))
            {
                aumobile.setVisibility(View.VISIBLE);
                aumobile.setText("Proprietor Mobile : "+a.getProprietor_mobile1());
            }
            else
            {
                aumobile.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getProprietor_email1()))
            {
                auemail.setVisibility(View.VISIBLE);
                auemail.setText("proprietor Email : "+a.getProprietor_email1());
            }
            else
            {
                auemail.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getProprietor_name2()))
            {
                auproname2.setVisibility(View.VISIBLE);
                auproname2.setText("proprietor Name 2 : "+a.getProprietor_name2());
            }
            else
            {
                auproname2.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getProprietor_mobile2()))
            {
                aumobile2.setVisibility(View.VISIBLE);
                aumobile2.setText("Proprietor Mobile 2 : "+a.getProprietor_mobile2());
            }
            else
            {
                aumobile2.setVisibility(View.GONE);
            }

            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getProprietor_email2()))
            {
                auemai2.setVisibility(View.VISIBLE);
                auemai2.setText("proprietor Email 2 : "+a.getProprietor_email1());
            }
            else
            {
                auemai2.setVisibility(View.GONE);
            }

//            if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(a.getCity()))
//            {
//                aucity.setVisibility(View.VISIBLE);
//                aucity.setText("City : "+a.getCity());
//            }
//            else
//            {
//                aucity.setVisibility(View.GONE);
//            }




        }

        //typeView.setText(contactMap.get("type"));

        return rowView;
    }


    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) { FilterResults results = new FilterResults();
            if (dataListAllItems == null) {
                synchronized (lock) {
                    dataListAllItems = new ArrayList<SubDealerModel>(mContactList);
                }
            }


            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = dataListAllItems;
                    results.count = dataListAllItems.size();
                }
            } else {
                final String searchStrLowerCase = prefix.toString();

                ArrayList<SubDealerModel> matchValues = new ArrayList<SubDealerModel>();

                // int position = 0;
                for (SubDealerModel dataItem : dataListAllItems) {
                    if (dataItem.shop_name.toLowerCase().startsWith(searchStrLowerCase) || dataItem.proprietor_mobile1.toLowerCase().startsWith(searchStrLowerCase) || dataItem.proprietor_mobile2.toLowerCase().startsWith(searchStrLowerCase) || dataItem.name.toLowerCase().startsWith(searchStrLowerCase) || dataItem.proprietor_name1.toLowerCase().startsWith(searchStrLowerCase) || dataItem.proprietor_name2.toLowerCase().startsWith(searchStrLowerCase) || dataItem.proprietor_email1.toLowerCase().startsWith(searchStrLowerCase) || dataItem.proprietor_email2.toLowerCase().startsWith(searchStrLowerCase) || dataItem.city.toLowerCase().startsWith(searchStrLowerCase)) {
                        SubDealerModel a = new SubDealerModel();
                        a.shop_name = dataItem.shop_name;
                        a.name = dataItem.name;
                        a.proprietor_mobile1 = dataItem.proprietor_mobile1;
                        a.proprietor_name1 = dataItem.proprietor_name1;
                        a.proprietor_email1 = dataItem.proprietor_email1;
                        a.proprietor_mobile2 = dataItem.proprietor_mobile2;
                        a.proprietor_name2 = dataItem.proprietor_name2;
                        a.proprietor_email2 = dataItem.proprietor_email2;
                        a.city = dataItem.city;

                        matchValues.add(a);

                    }

//                    if (dataItem.name.toLowerCase().startsWith(searchStrLowerCase) && !matchValues.get(position).getShop_name().equalsIgnoreCase(dataItem.shop_name)) {
//                        SubDealerModel a = new SubDealerModel();
//                        a.shop_name = dataItem.shop_name;
//                        a.name = dataItem.name;
//                        a.proprietor_mobile1 = dataItem.proprietor_mobile1;
//                        a.proprietor_name1 = dataItem.proprietor_name1;
//                        a.proprietor_email1 = dataItem.proprietor_email1;
//                        a.city = dataItem.city;
//                        matchValues.add(a);
//                    }
//
//                    if (dataItem.proprietor_mobile1.toLowerCase().startsWith(searchStrLowerCase)) {
//                        SubDealerModel a = new SubDealerModel();
//                        a.shop_name = dataItem.shop_name;
//                        a.name = dataItem.name;
//                        a.proprietor_mobile1 = dataItem.proprietor_mobile1;
//                        a.proprietor_name1 = dataItem.proprietor_name1;
//                        a.proprietor_email1 = dataItem.proprietor_email1;
//                        a.city = dataItem.city;
//                        matchValues.add(a);
//                    }
//
//                    if (dataItem.proprietor_name1.toLowerCase().startsWith(searchStrLowerCase)) {
//                        SubDealerModel a = new SubDealerModel();
//                        a.shop_name = dataItem.shop_name;
//                        a.name = dataItem.name;
//                        a.proprietor_mobile1 = dataItem.proprietor_mobile1;
//                        a.proprietor_name1 = dataItem.proprietor_name1;
//                        a.proprietor_email1 = dataItem.proprietor_email1;
//                        a.city = dataItem.city;
//                        matchValues.add(a);
//                    }
//
//                    if (dataItem.proprietor_email1.toLowerCase().startsWith(searchStrLowerCase)) {
//                        SubDealerModel a = new SubDealerModel();
//                        a.shop_name = dataItem.shop_name;
//                        a.name = dataItem.name;
//                        a.proprietor_mobile1 = dataItem.proprietor_mobile1;
//                        a.proprietor_name1 = dataItem.proprietor_name1;
//                        a.proprietor_email1 = dataItem.proprietor_email1;
//                        a.city = dataItem.city;
//                        matchValues.add(a);
//                    }
//
//                    if (dataItem.city.toLowerCase().startsWith(searchStrLowerCase)) {
//                        SubDealerModel a = new SubDealerModel();
//                        a.shop_name = dataItem.shop_name;
//                        a.name = dataItem.name;
//                        a.proprietor_mobile1 = dataItem.proprietor_mobile1;
//                        a.proprietor_name1 = dataItem.proprietor_name1;
//                        a.proprietor_email1 = dataItem.proprietor_email1;
//                        a.city = dataItem.city;
//                        matchValues.add(a);
//                    }
//
//                    position++;
                }

                results.values = matchValues;
                results.count = matchValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                mContactList = (ArrayList<SubDealerModel>)results.values;
            } else {
                mContactList = null;
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}
