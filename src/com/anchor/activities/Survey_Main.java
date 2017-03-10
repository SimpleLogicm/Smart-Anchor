package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anchor.App.AppController;
import com.anchor.webservice.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cpm.simplelogic.helper.OnOptionSelected;
import cpm.simplelogic.helper.QuestionModel;

/**
 * Created by vinod on 04-10-2016.
 */

public class Survey_Main extends Activity implements OnOptionSelected {
    Button bAdd;
    String survey_code = "";
    String active_from = "";
    String active_to = "";
    ProgressDialog dialog;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private RecyclerView mRecyclerView;
    private List<QuestionModel> questionModels;
    private ElementListAdapter questionAdapter;
    private ArrayList<String> hashkey = new ArrayList<String>();
    private ArrayList<String> hashvalue = new ArrayList<String>();
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey_layout);
        mRecyclerView =(RecyclerView)findViewById(R.id.survey_List);
        bAdd = (Button) findViewById(R.id.bAdd);
        mRecyclerView.setHasFixedSize(true);

        cd = new ConnectionDetector(getApplicationContext());

        loginDataBaseAdapter=new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        questionModels=new ArrayList<QuestionModel>();

        List<Local_Data> quastions = dbvoc.Get_SURVEY_QUESTIONS();

        for (Local_Data q_data : quastions) {

            QuestionModel questionModel=new QuestionModel();
            questionModel.setQuestion(q_data.getquestion());
            questionModel.setQuestioncode(q_data.getquestion_code());

            Global_Data.quastionmap.put(q_data.getquestion_code()+":"+q_data.getquestion(),"");

            questionModel.setoption1value(q_data.getoption_1());
            questionModel.setoption2value(q_data.getoption_2());
            questionModel.setoption3value(q_data.getoption_3());
            questionModel.setoption4value(q_data.getoption_4());
            questionModel.setoption5value(q_data.getoption_5());

            survey_code = q_data.getsurvey_code();
            active_from = q_data.getactive_from();
            active_to = q_data.getactive_to();

            questionModels.add(questionModel);
        }

//        for (int i=0;i<20;i++)
//        {
//            QuestionModel questionModel=new QuestionModel();
//            questionModel.setQuestion("Question " + (i + 1));
//            questionModel.setQuestioncode("CODE " + (i + 1));
//
//            Global_Data.quastionmap.put("CODE " + (i + 1)+":"+"Question " + (i + 1),"");
//
//            questionModel.setoption1value("option 1");
//            questionModel.setoption2value("option 2");
//            questionModel.setoption3value("option 3");
//            questionModel.setoption4value("option 4");
//            questionModel.setoption5value("option 5");
//            questionModels.add(questionModel);
//        }
        questionAdapter =new ElementListAdapter(Survey_Main.this,questionModels);
        //questionAdapter.setOnOptionSelected(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      //  questionAdapter.setQuestionModels(questionModels);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(questionAdapter);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                isInternetPresent = cd.isConnectingToInternet();
//                if (isInternetPresent)
//                {

                    hashkey.clear();
                    hashvalue.clear();
                    Iterator myVeryOwnIterator = Global_Data.quastionmap.keySet().iterator();
                    while(myVeryOwnIterator.hasNext()) {
                        String key=(String)myVeryOwnIterator.next();
                        String value=(String)Global_Data.quastionmap.get(key);
                        Log.d("map key","Map key"+key);
                        Log.d("map value","Map value"+value);
                        hashkey.add(key);
                        hashvalue.add(value);
                    }

                    if(hashvalue.contains(""))
                    {
                        for(int i=0; i< hashkey.size(); i++)
                        {
                            if(hashvalue.get(i).equalsIgnoreCase(""))
                            {
                                String code_value [] = hashkey.get(i).split(":");
                               // Toast.makeText(Survey_Main.this, "Please select at least one option of " +code_value[1] , Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(Survey_Main.this, "Please select at least one option of " +code_value[1] , Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                break;
                            }
                        }
                    }
                    else
                    {
                        //SyncSurveyDataData();

                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        //get current date time with Date()
                        Date date = new Date();
                        System.out.println(dateFormat.format(date));

                        try
                        {
                            AppLocationManager appLocationManager = new AppLocationManager(Survey_Main.this);
                            Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
                            Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

                            PlayService_Location PlayServiceManager = new PlayService_Location(Survey_Main.this);

                            if(PlayServiceManager.checkPlayServices(Survey_Main.this))
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

                        for(int i=0; i< hashkey.size(); i++)
                        {
                            String code_value [] = hashkey.get(i).split(":");

                            Long randomPIN = System.currentTimeMillis();
                            String PINString = String.valueOf(randomPIN);

                            loginDataBaseAdapter.insert_Survey_Answers(Global_Data.GLOvel_USER_EMAIL,dateFormat.format(date),survey_code, Global_Data.cust_str,code_value[0],hashvalue.get(i),Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE,PINString);
                        }

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Survey Data Save Successfully", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        Intent intentn = new Intent(getApplicationContext(), Marketing.class);
                        startActivity(intentn);
                        finish();
                    }

               // }
//                else
//                {
//                    // Internet connection is not present
//                    // Ask user to connect to Internet
//                    // showAlertDialog(AndroidDetectInternetConnectionActivity.this, "No Internet Connection",
//                    //        "You don't have internet connection.", false);
//                    Toast.makeText(getApplicationContext(),"You don't have internet connection",Toast.LENGTH_LONG).show();
//                }
            }
        });

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        Intent i = getIntent();
        String name = i.getStringExtra("retialer");
        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("Market Survey");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Survey_Main.this.getSharedPreferences("SimpleLogic", 0);

//	       if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//	       	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			}

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
        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//	       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
            todaysTarget.setText("Today's Target Acheived");
        }

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

       // GetSurveey_Datan();
    }


    @Override
    public void onOptionSelected(int position, int itemSelected) {
        questionModels.get(position).setSeleectedAnswerPosition(itemSelected);
        switch (itemSelected){
            case 1:
                ((QuestionModel)questionModels.get(position)).setOp1Sel(true);
                break;

            case 2:
                ((QuestionModel)questionModels.get(position)).setOp2Sel(true);
                break;
            case 3:
                ((QuestionModel)questionModels.get(position)).setOp3Sel(true);
                break;
            case 4:
                ((QuestionModel)questionModels.get(position)).setOp4Sel(true);
                break;
            case 5:
                ((QuestionModel)questionModels.get(position)).setOp5Sel(true);
                break;
        }
        //questionAdapter.setQuestionModels(questionModels);
        questionAdapter =new ElementListAdapter(Survey_Main.this,questionModels);
        questionAdapter.notifyDataSetChanged();
        // mRecyclerView.setAdapter(questionAdapter);



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

        AlertDialog alertDialog = new AlertDialog.Builder(Survey_Main.this).create(); //Read Update
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Are you sure you want to move previous page without submit survey data ?");
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intentn = new Intent(getApplicationContext(), Marketing.class);
                startActivity(intentn);
                finish();
                //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });
        alertDialog.show();
    }

    public  void GetSurveey_Datan()
    {

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = telephonyManager.getDeviceId();
        String domain = "";

        dialog = new ProgressDialog(Survey_Main.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Metal");
        dialog.setCancelable(false);
        dialog.show();

        domain = getResources().getString(R.string.service_domain);

        Log.d("Server url","Server url"+domain+"advertisements/send_advertisements?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL);

        StringRequest stringRequest = null;
        stringRequest = new StringRequest(domain+"advertisements/send_advertisements?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //showJSON(response);
                        // Log.d("jV", "JV" + response);
                        Log.d("jV", "JV length" + response.length());
                        // JSONObject person = (JSONObject) (response);
                        try {
                            JSONObject json = new JSONObject(new JSONTokener(response));
                            try{

                                String response_result = "";
                                if(json.has("result"))
                                {
                                    response_result = json.getString("result");
                                }
                                else
                                {
                                    response_result = "data";
                                }


                                if(response_result.equalsIgnoreCase("No Data Found")) {
                                    dialog.hide();
                                    //Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent launch = new Intent(Survey_Main.this,Marketing.class);
                                    startActivity(launch);
                                    finish();

                                }
                                else
                                if(response_result.equalsIgnoreCase("Device not registered")) {
                                    dialog.hide();
                                   // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent launch = new Intent(Survey_Main.this,Marketing.class);
                                    startActivity(launch);
                                    finish();

                                }
                                else {

                                    JSONArray ads = json.getJSONArray("survey");
//
                                    Log.i("volley", "response reg ads Length: " + ads.length());
//
                                    Log.d("users", "ads" + ads.toString());
//
                                    //

                                    for (int i = 0; i < ads.length(); i++) {

                                        JSONObject jsonObject = ads.getJSONObject(i);

                                        QuestionModel questionModel=new QuestionModel();
                                        questionModel.setQuestion(jsonObject.getString("description"));
                                        questionModel.setQuestioncode(jsonObject.getString("description"));
                                        Global_Data.quastionmap.put(jsonObject.getString("description")+":"+jsonObject.getString("description"),"");questionModel.setoption1value(jsonObject.getString("description"));
                                        questionModel.setoption2value(jsonObject.getString("description"));
                                        questionModel.setoption3value(jsonObject.getString("description"));
                                        questionModel.setoption4value(jsonObject.getString("description"));
                                        questionModel.setoption5value(jsonObject.getString("description"));
                                        questionModels.add(questionModel);

                                    }

                                    //Intent launch = new Intent(context,Youtube_Player_Activity.class);
                                    //startActivity(launch);

                                    dialog.dismiss();
                                    questionAdapter =new ElementListAdapter(Survey_Main.this,questionModels);
                                    //questionAdapter.setOnOptionSelected(this);
                                    LinearLayoutManager layoutManager = new LinearLayoutManager(Survey_Main.this);
                                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                    //  questionAdapter.setQuestionModels(questionModels);
                                    mRecyclerView.setLayoutManager(layoutManager);
                                    mRecyclerView.setAdapter(questionAdapter);

                                    questionAdapter.notifyDataSetChanged();
                                    //finish();

                                }

                                //  finish();
                                // }

                                // output.setText(data);
                            }catch(JSONException e){e.printStackTrace();
                                Intent launch = new Intent(Survey_Main.this,Marketing.class);
                                startActivity(launch);
                                finish();

                                dialog.dismiss(); }


                            dialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  finish();
                            dialog.dismiss();
                        }
                        dialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(Survey_Main.this,
//                                    "Network Error",
//                                    Toast.LENGTH_LONG).show();
                            Toast toast = Toast.makeText(Survey_Main.this,
                                    "Network Error",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof AuthFailureError) {
//                            Toast.makeText(Survey_Main.this,
//                                    "Server AuthFailureError  Error",
//                                    Toast.LENGTH_LONG).show();
                            Toast toast = Toast.makeText(Survey_Main.this,
                                    "Server AuthFailureError  Error",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (error instanceof ServerError) {
//                            Toast.makeText(Survey_Main.this,
//                                    "Server   Error",
//                                    Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Survey_Main.this,
                                    "Server   Error",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else if (error instanceof NetworkError) {
//                            Toast.makeText(Survey_Main.this,
//                                    "Network   Error",
//                                    Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Survey_Main.this,
                                    "Network   Error",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        } else if (error instanceof ParseError) {
//                            Toast.makeText(Survey_Main.this,
//                                    "ParseError   Error",
//                                    Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Survey_Main.this,
                                    "ParseError   Error",
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                           // Toast.makeText(Survey_Main.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Survey_Main.this,
                                    error.getMessage(),
                                    Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        Intent launch = new Intent(Survey_Main.this,Marketing.class);
                        startActivity(launch);
                        finish();
                        dialog.dismiss();
                        // finish();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Survey_Main.this);

        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        // requestQueue.se
        //requestQueue.add(jsObjRequest);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().clear();
        //requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }


    public  void SyncSurveyDataData()
    {
        System.gc();
        String reason_code = "";
        try {

            dialog = new ProgressDialog(Survey_Main.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please wait....");
            dialog.setTitle("Metal");
            dialog.setCancelable(false);
            dialog.show();

            String domain = "";
            String device_id = "";

            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            device_id = telephonyManager.getDeviceId();

            domain = getResources().getString(R.string.service_domain);



            JsonObjectRequest jsObjRequest = null;
            try
            {



                Log.d("Server url","Server url"+domain+"customer_service_media");



                JSONArray SURVEY = new JSONArray();
                //JSONObject product_value = new JSONObject();
                JSONObject product_value_n = new JSONObject();
                JSONArray product_imei = new JSONArray();

                DataBaseHelper dbvoc = new DataBaseHelper(Survey_Main.this);

                for(int i=0; i< hashkey.size(); i++)
                {
                    String code_value [] = hashkey.get(i).split(":");

                    JSONObject picture = new JSONObject();
                    picture.put("quastion_code",code_value[0]);
                    picture.put("option_value", hashvalue.get(i));

                    SURVEY.put(picture);
                }




                product_value_n.put("survey", SURVEY);
                product_value_n.put("imei_no", Global_Data.device_id);
                product_value_n.put("email", Global_Data.GLOvel_USER_EMAIL);
                Log.d("customers Service",product_value_n.toString());

                jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"customer_service_media", product_value_n, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("volley", "response: " + response);

                        Log.d("jV", "JV length" + response.length());
                        //JSONObject json = new JSONObject(new JSONTokener(response));
                        try{

                            String response_result = "";
                            if(response.has("result"))
                            {
                                response_result = response.getString("result");
                            }
                            else
                            {
                                response_result = "data";
                            }


                            if(response_result.equalsIgnoreCase("success")) {

                                dialog.dismiss();
                                //Toast.makeText(Survey_Main.this, "Survey Data Successfully.", Toast.LENGTH_LONG).show();

                                Toast toast = Toast.makeText(Survey_Main.this,"Survey Data Successfully.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent a = new Intent(Survey_Main.this,Marketing.class);
                                startActivity(a);
                                finish();


                            }
                            else
                            {

                                dialog.dismiss();

                                Toast toast = Toast.makeText(Survey_Main.this,response_result, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                    Intent a = new Intent(Survey_Main.this,Marketing.class);
                                    startActivity(a);
                                    finish();
                            }

                            //  finish();
                            // }

                            // output.setText(data);
                        }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


                        dialog.dismiss();
                        dialog.dismiss();




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("volley", "error: " + error);
                       // Toast.makeText(Survey_Main.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

                        Toast toast = Toast.makeText(Survey_Main.this,"Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8" );
                            JSONObject jsonObject = new JSONObject( responseBody );
                        } catch ( JSONException e ) {
                            //Handle a malformed json response
                        } catch (UnsupportedEncodingException errorn){

                        }

                        dialog.dismiss();

                    }
                });



                RequestQueue requestQueue = Volley.newRequestQueue(Survey_Main.this);

                int socketTimeout = 2000000;//90 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsObjRequest.setRetryPolicy(policy);
                // requestQueue.se
                //requestQueue.add(jsObjRequest);
                jsObjRequest.setShouldCache(false);
                requestQueue.getCache().clear();
                requestQueue.add(jsObjRequest);

            }catch(Exception e)
            {
                e.printStackTrace();
                dialog.dismiss();
            }





            //createdID=myDbHelper.generateNoOrder(userID,cityID,beatID,retailerID,retailer_code,reasonID,reasonOther,formattedDate);
            //createdID=1;
			/*if (!mobile.equalsIgnoreCase("NA")) {
				SmsManager smsManager=SmsManager.getDefault();
				smsManager.sendTextMessage("mobile", null, "Order ID : "+createdID+" is generated", null, null);
			}



			  if (cd.isConnectingToInternet()) {
                    // Internet Connection is Present
                    // make HTTP requests

                }
			 */

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("DATA", e.getMessage());
        }
    }
}