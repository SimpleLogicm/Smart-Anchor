package com.anchor.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;

public class Expended_List_Main extends BaseActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;
    DataBaseHelper dbvoc = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex_main);
        Global_Data.CALENDER_READONLY_Address = "";
        Global_Data.CALENDER_READONLY_Date = "";
        expandableListView = findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData(Expended_List_Main.this);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        for(int i=0; i < expandableListAdapter.getGroupCount(); i++) {
            expandableListView .expandGroup(i);
        }

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                String[] st = expandableListTitle.get(groupPosition).split(" ");
                String finaldate = st[0]+"-"+st[1]+"-"+year;
                List<Local_Data> contactsDetail = dbvoc.getCalender_EventValue_byFDATE(finaldate,"NO");

                if(contactsDetail.size() <=0)
                {
                  //  Toast.makeText(Expended_List_Main.this, "No event found.", Toast.LENGTH_SHORT).show();

                   // Toast toast = Toast.makeText(Expended_List_Main.this, "No event found.", Toast.LENGTH_SHORT);
                   // toast.setGravity(Gravity.CENTER, 0, 0);
                  //  toast.show();

                }
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();





            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + expandableListDetail.get(
//                                expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();

                Global_Data.CALENDER_READONLY_Address = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition).trim();
                Global_Data.CALENDER_READONLY_Date = expandableListTitle.get(groupPosition);
                Intent intent = new Intent(getApplicationContext(), Calender_ReadonlyView.class);
                startActivity(intent);
               // finish();
                return false;
            }
        });
        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            // int month = calendar.get(Calendar.MONTH);
            // int day = calendar.get(Calendar.DAY_OF_MONTH);

            //Calendar cal = Calendar.getInstance();
            //int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
            Formatter fmt = new Formatter();
            // fmt.format("%tB %tb %tm", calendar, calendar, calendar);

            String mm = fmt.format("%tB", calendar).toString();
            mTitleTextView.setText("Calendar - "+mm+" "+year);
            mTitleTextView.setTextSize(15);


            TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
            todaysTarget.setVisibility(View.INVISIBLE);
            SharedPreferences sp = Expended_List_Main.this.getSharedPreferences("SimpleLogic", 0);

            ImageView H_LOGO = mCustomView.findViewById(R.id.Header_logo);
            H_LOGO.setImageResource(R.drawable.cal);
            H_LOGO.setVisibility(View.VISIBLE);


            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}

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
        // TODO Auto-generated method stub
        //super.onBackPressed();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }
}
