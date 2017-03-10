package com.anchor.activities;

/**
 * Created by vinod on 04-10-2016.
 */

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.ContactInfo;


public class Schedule_List extends BaseActivity {

    int check=0;
    int check_product=0;
    int check_ProductSpec=0;



    ArrayAdapter<String> dataAdapterCategory,dataAdapterProductSpec,dataAdapterProduct;
    //ArrayList productList = new ArrayList();
    List<String> listProduct,listProductSpec;
    List<String> listScheme;

    private ArrayList<String> results = new ArrayList<String>();
    private ArrayList<String> results1 = new ArrayList<String>();
    private ArrayList<String> results2 = new ArrayList<String>();

    ArrayAdapter<String> adapter_state1;
    ArrayAdapter<String> adapter_state2;
    ArrayAdapter<String> adapter_state3;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    RecyclerView recList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my);

        setContentView(R.layout.schedule_list);


        recList = (RecyclerView) findViewById(R.id.cardList);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        SharedPreferences spf = Schedule_List.this.getSharedPreferences("SimpleLogic", 0);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("order", "new");
        editor.commit();

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Schedule");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Schedule_List.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
        try
        {
            int target  = (int) Math.round(sp.getFloat("Target",0));
            int achieved  = (int) Math.round(sp.getFloat("Achived",0));
            Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
            if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
            {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
            }else
            {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
            }

        }catch(Exception ex){ex.printStackTrace();}


        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        if(Global_Data.Schedule_FLAG.equalsIgnoreCase("CUSTOMER"))
        {
            List<Local_Data> contacts3 = dbvoc.getSchedule_List(Global_Data.GLOvel_CUSTOMER_ID);

            if (contacts3.size() <= 0) {
                // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                Toast toast = Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                Intent intent = new Intent(getApplicationContext(),
                        Order.class);
                startActivity(intent);
            } else {
                recList.setVisibility(View.VISIBLE);
                List<ContactInfo> result = new ArrayList<ContactInfo>();
                for (Local_Data cn : contacts3) {


                    ContactInfo ci = new ContactInfo();
                    ci.date1 = cn.get_shedule_delivery_date();
                    ci.order_number = cn.get_shedule_order_id();
                    ci.location = Global_Data.customer_Address;

                    result.add(ci);
                }
                Schedule_List_Adapter ca = new Schedule_List_Adapter(result);
                recList.setAdapter(ca);
                ca.notifyDataSetChanged();


            }
        }
        else
        {
            List<Local_Data> contacts3 = dbvoc.getSchedule_ListAll();

            if (contacts3.size() <= 0) {
                // Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT).show();

                Toast toast = Toast.makeText(Schedule_List.this, "Sorry No Record Found.", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            } else {
                recList.setVisibility(View.VISIBLE);
                List<ContactInfo> result = new ArrayList<ContactInfo>();
                for (Local_Data cn : contacts3) {


                    ContactInfo ci = new ContactInfo();
                    ci.date1 = cn.get_shedule_delivery_date();
                    ci.order_number = cn.get_shedule_order_id();
                    ci.location = cn.getADDRESS();

                    result.add(ci);
                }
                Schedule_List_Adapter ca = new Schedule_List_Adapter(result);
                recList.setAdapter(ca);
                ca.notifyDataSetChanged();


            }
        }


    }






    private List<ContactInfo> createList(int size) {

        List<ContactInfo> result = new ArrayList<ContactInfo>();
        for (int i=1; i <= size; i++) {
            ContactInfo ci = new ContactInfo();
            ci.name = "101"+ " " + "abc";
            ci.rp = ContactInfo.SURNAME_PREFIX + i;
            ci.mrp = ContactInfo.EMAIL_PREFIX + i + "@test.com";

            result.add(ci);

        }

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if(Global_Data.Schedule_FLAG.equalsIgnoreCase("CUSTOMER"))
        {
            Intent i=new Intent(Schedule_List.this, Order.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
        else
        {
            Intent i=new Intent(Schedule_List.this, MainActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }


    }

}
