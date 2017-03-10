package com.anchor.activities;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import cpm.simplelogic.helper.TargetValue_info;

public class GrpbyMore_Adapter extends RecyclerView.Adapter<GrpbyMore_Adapter.ContactViewHolder> {

    int a=0;
    String month_value = "";
    private List<TargetValue_info> contactList;
    private List<TargetValue_info> contactListnew;
    public GrpbyMore_Adapter(List<TargetValue_info> contactList) {
        this.contactList = contactList;
        this.contactListnew = contactList;
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i)
    {

//        ++a;
//        if(a <= contactListnew.size()) {

            TargetValue_info ci = contactList.get(i);
//            if (contactViewHolder != null) {
                contactViewHolder.setIsRecyclable(false);
                contactViewHolder.prdcatg_more.setText(ci.prdcatg_morestr);
                contactViewHolder.month_more.setText(ci.monthgrpmore_str);
                contactViewHolder.target_more.setText(ci.targetgrpmore_str);
                contactViewHolder.achieved_more.setText(ci.achievedgrpmore_str);
                contactViewHolder.age_more.setText(ci.agegrpmore_str);

//                if (contactViewHolder.dfgnew.getVisibility() == View.VISIBLE) {
//                    contactViewHolder.dfgnew.setVisibility(View.VISIBLE);
//                } else {
//                    contactViewHolder.dfgnew.setVisibility(View.GONE);
//                }
//
//                if (contactViewHolder.product_header2.getVisibility() == View.VISIBLE) {
//                    contactViewHolder.product_header2.setVisibility(View.VISIBLE);
//                } else {
//                    contactViewHolder.product_header2.setVisibility(View.GONE);
//                }
//
//

                if (i > 0 && !(contactList.get(i).monthgrpmore_str.equalsIgnoreCase(contactList.get(i-1).monthgrpmore_str))) {
                    month_value = ci.monthgrpmore_str;
                    contactViewHolder.dfgnew.setVisibility(View.VISIBLE);
                    contactViewHolder.product_header2.setVisibility(View.VISIBLE);
                } else {
                    if(i != 0)
                    {
                        contactViewHolder.dfgnew.setVisibility(View.GONE);
                        contactViewHolder.product_header2.setVisibility(View.GONE);
                    }


                }


                if (Global_Data.target_grpby.equalsIgnoreCase("By Product")) {
                    contactViewHolder.month_grpby.setText("Product Sub Category");
                }
           // }
       // }
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = null;

        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.summary2_adapter, viewGroup, false);

            return new ContactViewHolder(itemView);


    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView prdcatg_more;
        protected TextView month_more;
        protected TextView target_more;
        protected TextView achieved_more;
        protected TextView age_more;
        protected TextView month_grpby;
        protected LinearLayout dfgnew,product_header2;

        public ContactViewHolder(View v) {
            super(v);
            prdcatg_more =  (TextView) v.findViewById(R.id.prdcatg_more);
            month_grpby =  (TextView) v.findViewById(R.id.month_grpby);
            target_more = (TextView)  v.findViewById(R.id.target_grpby);
            achieved_more = (TextView) v.findViewById(R.id.achievd_grpby);
            age_more = (TextView) v.findViewById(R.id.age_grpby);
            month_more = (TextView) v.findViewById(R.id.month_top);
            dfgnew = (LinearLayout) v.findViewById(R.id.dfgnew);
            product_header2 = (LinearLayout) v.findViewById(R.id.product_header2);
        }
    }
}
