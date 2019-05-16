package com.anchor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.anchor.activities.Global_Data;
import com.anchor.activities.R;
import com.anchor.helper.PrefManager;
import com.anchor.model.CustomerServiceModel;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import cpm.simplelogic.helper.Check_Null_Value;

/**
 * Simple example of ListAdapter for using with Folding Cell
 * Adapter holds indexes of unfolded elements for correct work with default reusable views behavior
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class FoldingCellListAdapter extends ArrayAdapter<CustomerServiceModel> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View.OnClickListener defaultRequestBtnClickListener;
    private PrefManager prefManager;

    public FoldingCellListAdapter(Context context, List<CustomerServiceModel> objects) {
        super(context, 0, objects);
        prefManager = new PrefManager(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        CustomerServiceModel item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.title_time_label = cell.findViewById(R.id.title_time_label);
            viewHolder.product_name = cell.findViewById(R.id.product_name);
            viewHolder.type_text = cell.findViewById(R.id.type_text);
            viewHolder.status_text = cell.findViewById(R.id.status_text);
            //viewHolder.contentRequestBtn = cell.findViewById(R.id.content_request_btn);

            viewHolder.title_product = cell.findViewById(R.id.header_product);
            viewHolder.title_product2 = cell.findViewById(R.id.header_product2);
            viewHolder.product_title = cell.findViewById(R.id.product_title);
            viewHolder.header_product2_text = cell.findViewById(R.id.header_product2_text);
            viewHolder.response = cell.findViewById(R.id.Response);
            viewHolder.respondent_name = cell.findViewById(R.id.Respondent);
            viewHolder.action_date = cell.findViewById(R.id.r_date);
            viewHolder.description_value = cell.findViewById(R.id.r_desription_value);
            viewHolder.description_text = cell.findViewById(R.id.r_desription_text);

            viewHolder.description_value2 = cell.findViewById(R.id.r_desription_value2);
            viewHolder.description_text2 = cell.findViewById(R.id.r_desription_text2);

            viewHolder.description_value3 = cell.findViewById(R.id.r_desription_value3);
            viewHolder.description_text3 = cell.findViewById(R.id.r_desription_text3);

            viewHolder.description_value4 = cell.findViewById(R.id.r_desription_value4);
            viewHolder.description_text4 = cell.findViewById(R.id.r_desription_text4);

            viewHolder.description_value5 = cell.findViewById(R.id.r_desription_value5);
            viewHolder.description_text5 = cell.findViewById(R.id.r_desription_text5);

            viewHolder.description_value6 = cell.findViewById(R.id.r_desription_value6);
            viewHolder.description_text6 = cell.findViewById(R.id.r_desription_text6);

            viewHolder.description_value7 = cell.findViewById(R.id.r_desription_value7);
            viewHolder.description_text7 = cell.findViewById(R.id.r_desription_text7);

            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder
        //viewHolder.title_time_label.setText(item.getOuter_date());

        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("feedbacks"))
        {
            viewHolder.product_title.setText("Feedback Type");
            viewHolder.header_product2_text.setText(" ");
            viewHolder.title_product2.setText(item.getProduct_name());
            viewHolder.product_name.setText(item.getType());
            viewHolder.header_product2_text.setVisibility(View.GONE);
            viewHolder.title_product2.setVisibility(View.GONE);

            viewHolder.description_text.setText("Description");
            viewHolder.description_value.setText(item.getDescription());

            viewHolder.description_text2.setText("");
            viewHolder.description_value2.setText("");
            viewHolder.description_text2.setVisibility(View.VISIBLE);
            viewHolder.description_value2.setVisibility(View.VISIBLE);

            viewHolder.description_text3.setText("");
            viewHolder.description_value3.setText("");
            viewHolder.description_text3.setVisibility(View.VISIBLE);
            viewHolder.description_value3.setVisibility(View.VISIBLE);

            viewHolder.description_text4.setVisibility(View.GONE);
            viewHolder.description_value4.setVisibility(View.GONE);

            viewHolder.description_text5.setVisibility(View.GONE);
            viewHolder.description_value5.setVisibility(View.GONE);

            viewHolder.description_text6.setVisibility(View.GONE);
            viewHolder.description_value6.setVisibility(View.GONE);

            viewHolder.description_text7.setVisibility(View.GONE);
            viewHolder.description_value7.setVisibility(View.GONE);

            viewHolder.title_product.setText(item.getType());

            viewHolder.header_product2_text.setVisibility(View.VISIBLE);
            viewHolder.title_product2.setVisibility(View.VISIBLE);
        }
        else
        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("claims"))
        {

            viewHolder.product_title.setText("Claim Type");
            viewHolder.header_product2_text.setText("Item Description");
            viewHolder.title_product2.setText(item.getProduct_name());
            viewHolder.product_name.setText(item.getType());
            viewHolder.header_product2_text.setVisibility(View.GONE);
            viewHolder.title_product2.setVisibility(View.GONE);

            viewHolder.description_text.setText("Reference 1 (Invoice No)");
            viewHolder.description_value.setText(item.getDescription());

            viewHolder.description_text2.setText("Reference 2 (Scheme)");
            viewHolder.description_value2.setText(item.getDescription2());

            viewHolder.description_text3.setText("Reference 3");
            viewHolder.description_value3.setText(item.getDescription3());

            viewHolder.description_text2.setVisibility(View.VISIBLE);
            viewHolder.description_value2.setVisibility(View.VISIBLE);

            viewHolder.description_text3.setVisibility(View.VISIBLE);
            viewHolder.description_value3.setVisibility(View.VISIBLE);

            viewHolder.description_text4.setVisibility(View.GONE);
            viewHolder.description_value4.setVisibility(View.GONE);

            viewHolder.description_text5.setVisibility(View.GONE);
            viewHolder.description_value5.setVisibility(View.GONE);

            viewHolder.description_text6.setVisibility(View.GONE);
            viewHolder.description_value6.setVisibility(View.GONE);

            viewHolder.description_text7.setVisibility(View.GONE);
            viewHolder.description_value7.setVisibility(View.GONE);

            viewHolder.title_product.setText(item.getType());
        }
        else
        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("logistics"))
        {
            viewHolder.product_title.setText("Logistics Type");
            viewHolder.header_product2_text.setText("Item Description");
            viewHolder.title_product2.setText(item.getProduct_name());
            viewHolder.product_name.setText(item.getType());
            viewHolder.header_product2_text.setVisibility(View.VISIBLE);
            viewHolder.title_product2.setVisibility(View.VISIBLE);

            viewHolder.description_text.setText("Invoice No");
            viewHolder.description_value.setText(item.getDescription());

            viewHolder.description_text2.setText("Quantity");
            viewHolder.description_value2.setText(item.getDescription2());

            viewHolder.description_text3.setText("Remarks");
            viewHolder.description_value3.setText(item.getDescription3());

            viewHolder.description_text2.setVisibility(View.VISIBLE);
            viewHolder.description_value2.setVisibility(View.VISIBLE);

            viewHolder.description_text3.setVisibility(View.VISIBLE);
            viewHolder.description_value3.setVisibility(View.VISIBLE);

            viewHolder.description_text4.setVisibility(View.GONE);
            viewHolder.description_value4.setVisibility(View.GONE);

            viewHolder.description_text5.setVisibility(View.GONE);
            viewHolder.description_value5.setVisibility(View.GONE);

            viewHolder.description_text6.setVisibility(View.GONE);
            viewHolder.description_value6.setVisibility(View.GONE);

            viewHolder.description_text7.setVisibility(View.GONE);
            viewHolder.description_value7.setVisibility(View.GONE);

            viewHolder.title_product.setText(item.getType());
        }
        else
        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("marketing tool"))
        {
            viewHolder.product_title.setText("Request Type");
            viewHolder.header_product2_text.setText("Item Description");
            viewHolder.title_product2.setText(item.getProduct_name());
            viewHolder.product_name.setText(item.getType());
            viewHolder.header_product2_text.setVisibility(View.VISIBLE);
            viewHolder.title_product2.setVisibility(View.VISIBLE);


            viewHolder.description_text.setText("Quantity");
            viewHolder.description_value.setText(item.getDescription());

            viewHolder.description_text2.setText("");
            viewHolder.description_value2.setText("");
            viewHolder.description_text2.setVisibility(View.VISIBLE);
            viewHolder.description_value2.setVisibility(View.VISIBLE);

            viewHolder.description_text3.setText("");
            viewHolder.description_value3.setText("");
            viewHolder.description_text3.setVisibility(View.VISIBLE);
            viewHolder.description_value3.setVisibility(View.VISIBLE);

            viewHolder.description_text4.setVisibility(View.GONE);
            viewHolder.description_value4.setVisibility(View.GONE);

            viewHolder.description_text5.setVisibility(View.GONE);
            viewHolder.description_value5.setVisibility(View.GONE);

            viewHolder.description_text6.setVisibility(View.GONE);
            viewHolder.description_value6.setVisibility(View.GONE);

            viewHolder.description_text7.setVisibility(View.GONE);
            viewHolder.description_value7.setVisibility(View.GONE);

            viewHolder.title_product.setText(item.getType());

            viewHolder.header_product2_text.setVisibility(View.VISIBLE);
            viewHolder.title_product2.setVisibility(View.VISIBLE);
        }
        else
        if(prefManager.getCustomer_service_type().toLowerCase().equalsIgnoreCase("Product Quality"))
        {
            viewHolder.product_title.setText("Area");
            viewHolder.header_product2_text.setText(" ");
            viewHolder.title_product2.setText(item.getProduct_name());
            viewHolder.product_name.setText(item.getType());
            viewHolder.header_product2_text.setVisibility(View.VISIBLE);
            viewHolder.title_product2.setVisibility(View.VISIBLE);


            viewHolder.description_text.setText("Area");
            viewHolder.description_value.setText(item.getDescription());
            viewHolder.description_text.setVisibility(View.GONE);
            viewHolder.description_value.setVisibility(View.GONE);

            viewHolder.description_text2.setText("Customer Type");
            viewHolder.description_value2.setText(item.getDescription());
            viewHolder.description_text2.setVisibility(View.VISIBLE);
            viewHolder.description_value2.setVisibility(View.VISIBLE);

            viewHolder.description_text3.setText("Product Segment");
            viewHolder.description_value3.setText(item.getDescription2());
            viewHolder.description_text3.setVisibility(View.VISIBLE);
            viewHolder.description_value3.setVisibility(View.VISIBLE);

            viewHolder.description_text4.setText("Brand");
            viewHolder.description_value4.setText(item.getDescription3());
            viewHolder.description_text4.setVisibility(View.VISIBLE);
            viewHolder.description_value4.setVisibility(View.VISIBLE);

            viewHolder.description_text5.setText("Product_Type");
            viewHolder.description_value5.setText(item.getDescription4());
            viewHolder.description_text5.setVisibility(View.VISIBLE);
            viewHolder.description_value5.setVisibility(View.VISIBLE);

            viewHolder.description_text6.setText("Call Type");
            viewHolder.description_value6.setText(item.getDescription5());
            viewHolder.description_text6.setVisibility(View.VISIBLE);
            viewHolder.description_value6.setVisibility(View.VISIBLE);

            viewHolder.description_text7.setText("Remarks");
            viewHolder.description_value7.setText(item.getDescription6());
            viewHolder.description_text7.setVisibility(View.VISIBLE);
            viewHolder.description_value7.setVisibility(View.VISIBLE);

            viewHolder.title_product.setText(item.getType());

            viewHolder.header_product2_text.setVisibility(View.VISIBLE);
            viewHolder.title_product2.setVisibility(View.VISIBLE);
        }






        if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(item.getOuter_date()))
        {
            viewHolder.title_time_label.setText("");
        }else{
            viewHolder.title_time_label.setText(item.getOuter_date());
        }



        if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(item.getComplaint_type()))
        {
            viewHolder.type_text.setText("");
        }else{
            viewHolder.type_text.setText(item.getComplaint_type());
        }

        if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(item.getResponse()))
        {
            viewHolder.response.setText("");
        }else{
            viewHolder.response.setText(item.getResponse());
        }

        if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(item.getRespondant_name()))
        {
            viewHolder.respondent_name.setText("");
        }else{
            viewHolder.respondent_name.setText(item.getRespondant_name());
        }

        if(!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(item.getInner_actiondate()))
        {
            viewHolder.action_date.setText("");
        }else{
            viewHolder.action_date.setText(item.getInner_actiondate());
        }






        String s = Global_Data.p_cus_services_status_check.get(position);
        if(s.equalsIgnoreCase("open"))
        {
            viewHolder.status_text.setText(s);
            viewHolder.status_text.setTextColor(Color.RED);
        }
        else
        if(s.equalsIgnoreCase("closed"))
        {
            viewHolder.status_text.setText(s);
            viewHolder.status_text.setTextColor(Color.GREEN);
        }
        else
        if(s.equalsIgnoreCase("on hold"))
        {
            viewHolder.status_text.setText(s);
            viewHolder.status_text.setTextColor(Color.BLUE);
        }
        else
        {
            viewHolder.status_text.setTextColor(Color.parseColor("#adafb1"));
            viewHolder.status_text.setText(s);
        }

        // set custom btn handler for list item from that item
//        if (item.getRequestBtnClickListener() != null) {
//            viewHolder.contentRequestBtn.setOnClickListener(item.getRequestBtnClickListener());
//        } else {
//            // (optionally) add "default" handler if no handler found in item
//            viewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
//        }

        return cell;
    }

    // simple methods for register cell state changes
    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    // View lookup cache
    private static class ViewHolder {
        TextView title_time_label;
        TextView product_name,product_title,header_product2_text;
        TextView type_text;
        TextView status_text;
        //TextView contentRequestBtn;

        TextView title_product,title_product2;
        TextView response;
        TextView respondent_name;
        TextView action_date;
        TextView description_value;
        TextView description_text;
        TextView description_value2;
        TextView description_text2;
        TextView description_value3;
        TextView description_text3;
        TextView description_value4;
        TextView description_text4;
        TextView description_value5;
        TextView description_text5;
        TextView description_value6;
        TextView description_text6;
        TextView description_value7;
        TextView description_text7;


    }
}
