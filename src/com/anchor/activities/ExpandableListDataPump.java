package com.anchor.activities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.anchor.slidingmenu.CalendarAct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {

   static ArrayList <String> fromdatenew = new ArrayList<String>();
    static ArrayList <String> todatenew = new ArrayList<String>();
    static ArrayList <String> newvalue = new ArrayList<String>();

    static Date date1;
    static Date date2;
    static Date date3;

    public static LinkedHashMap<String, List<String>> getData(Context context) {

        DataBaseHelper dbvoc = new DataBaseHelper(context);
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();



        Calendar calendar = Calendar.getInstance();
       //   calendar.add(Calendar.MONTH, -1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //Calendar cal = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
        Formatter fmt = new Formatter();
       // fmt.format("%tB %tb %tm", calendar, calendar, calendar);

        String mm = fmt.format("%tB", calendar).toString();

        Log.d("C Month","C Month"+mm);
        Log.d("C MAXDAY","C MAXDAY"+days);
        Log.d("C year","C year"+year);

        List<Local_Data> contacts2 = dbvoc.getCalender_EventAll("NO");

        if(contacts2.size() <= 0 )
        {
            Toast.makeText(context, "No record found", Toast.LENGTH_SHORT).show();
            Intent launch = new Intent(context,CalendarAct.class);
            context.startActivity(launch);
            //context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
           // context.finish();
        }
        else
        {
           // for (Local_Data cn : contacts2) {

            for (int i=1; i<=days; i++) {
                List<String> value = new ArrayList<String>();

               // String abc[] = cn.getfrom_date().split("-");
                String finaldate = i+"-"+mm+"-"+year;
               // Log.d("split value", "split value"+abc[0]);
                Log.d("finaldate", "finaldate"+finaldate);
                List<Local_Data> contactsDetail = dbvoc.getCalender_EventValue_byFDATE(finaldate,"NO");
                for (Local_Data cnn : contactsDetail) {
                    value.add(cnn.getcalender_details());
                    fromdatenew.add(cnn.getfrom_date());
                    todatenew.add(cnn.getto_date());
                    newvalue.add(cnn.getcalender_details());
                }

                date3 = new Date(finaldate);
                for(int j=0; j<fromdatenew.size(); j++)
                {
                    if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(fromdatenew.get(j)) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(todatenew.get(j)))
                    {
                        date1 = new Date(fromdatenew.get(j));
                        date2 = new Date(todatenew.get(j));
                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal1.setTime(date1);
                        cal2.setTime(date1);

                        if(date3.compareTo(date1)>0 && date3.compareTo(date2)<=0)
                        {
                            value.add(newvalue.get(j));
                        }
                    }

                }
//                from.setText(Global_Data.select_date);
//                to.setText(cn.getto_date());
//                details.setText(cn.getcalender_details());
//
//                c_user_id = cn.getuser_email();
//                c_id = cn.getcalender_id();
                expandableListDetail.put(i+" "+mm, value);

                //return expandableListDetail;


            }

            fromdatenew.clear();
            todatenew.clear();
            newvalue.clear();

        }



        return expandableListDetail;
    }



}
