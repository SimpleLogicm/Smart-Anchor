package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by sujit on 3/8/2017.
 */

public class Forget_Pwd extends Activity {
    EditText email_id;
    Button forgetpwd_btn;
    String devid, str_email;
    ProgressDialog dialog;
    String resultstr;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_pwd);
        forgetpwd_btn = (Button) findViewById(R.id.forgetpwd_btn);
        email_id = (EditText) findViewById(R.id.edit_email);
        str_email = email_id.getText().toString().trim();
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        dialog = new ProgressDialog(Forget_Pwd.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

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
        mTitleTextView.setText("Forget Password");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        SharedPreferences sp = Forget_Pwd.this.getSharedPreferences("SimpleLogic", 0);

        SharedPreferences pref_devid = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        pref_devid.getString("reg_devid", "");
        devid = pref_devid.getString("reg_devid", "");

        forgetpwd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fun_Fpwd();
            }
        });

//    //Reading all
//    List<Local_Data> contacts = dbvoc.getAllMain();
//    for (Local_Data cn : contacts) {
//        Global_Data.local_user = ""+cn.getUser();
//        Global_Data.local_pwd = ""+cn.getemail();
//        System.out.println("Local Values:-"+Global_Data.local_user+","+Global_Data.local_pwd);
//        //Toast.makeText(LoginActivity.this, "Login:"+Global_Data.local_user,Toast.LENGTH_SHORT).show();
//    }


        try {
            int target = (int) Math.round(sp.getFloat("Target", 0));
            int achieved = (int) Math.round(sp.getFloat("Achived", 0));
            Float age_float = (sp.getFloat("Achived", 0) / sp.getFloat("Target", 0)) * 100;
            if (String.valueOf(age_float).equalsIgnoreCase("infinity")) {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + "infinity") + "%" + "]");
            } else {
                int age = (int) Math.round(age_float);

                todaysTarget.setText("T/A : Rs " + String.format(target + "/" + achieved + " [" + age) + "%" + "]");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
//	       	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
            todaysTarget.setText("Today's Target Acheived");
        }

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void Fun_Fpwd() {
        dialog.setMessage("Please wait...");
        dialog.setTitle("Metal App");
        dialog.setCancelable(false);
        dialog.show();

        if (email_id.getText().toString().length() > 0) {
            try {
                String domain = getResources().getString(R.string.service_domain);
                JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain + "/users/forgot_password?imei_no=" + URLEncoder.encode(devid, "UTF-8") + "&email=" + email_id.getText().toString(), null, new Response.Listener<JSONObject>() {
                    // JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"/menus/registration?imei_no="+ URLEncoder.encode("911305401754123", "UTF-8"),null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("volley", "response: " + response.toString());
                        //  Log.i("volley", "response reg Length: " + response.length());

//                        try {
//                            String response_result = "";
//                            if (response.has("result")) {
//                                response_result = response.getString("result");
//                                if(response_result.equalsIgnoreCase("Device not found."))
//                                {
//                                    Toast toast = Toast.makeText(Forget_Pwd.this, response_result, Toast.LENGTH_LONG);
//                                    toast.setGravity(Gravity.CENTER, 0, 0);
//                                    toast.show();
//                                }
//                            } else {
//                                response_result = "data";
//                            }
//                            dialog.dismiss();
//                            Log.d("response_result","response_result"+response_result);
//                            Log.d("response","response"+response);
//
//                            Toast.makeText(getApplicationContext(), "Register successfully."+response, Toast.LENGTH_LONG).show();


                        if (response.has("result")) {
                            String response_result = "";
                            try {
                                resultstr = response.getString("result");
                                if (resultstr.equalsIgnoreCase("Device not found.")) {
                                    dialog.dismiss();
                                    // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(Forget_Pwd.this, resultstr, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else if (resultstr.equalsIgnoreCase("User does not exist")) {
                                    Toast toast = Toast.makeText(Forget_Pwd.this, resultstr, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else {
                                    try {
                                        resultstr = response.getString("result");
                                        if (resultstr.equalsIgnoreCase("Password updated successfully")) {
                                            String password = response.getString("password");
                                            dbvoc.update_password(password, email_id.getText().toString().trim());

                                            dialog.dismiss();
                                            Toast toast = Toast.makeText(Forget_Pwd.this, resultstr, Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER, 0, 0);
                                            toast.show();
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

//                                for (int i = 0; i < users.length(); i++) {
//
//                                    JSONObject jsonObject = users.getJSONObject(i);
//
//                                    SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor edit = pref.edit();
//                                    edit.putString("login_usernm", jsonObject.getString("user_name"));
//                                    edit.putString("login_pwd", jsonObject.getString("encrypted_password"));
//                                    edit.putString("login_dtofjn", jsonObject.getString("date_of_joining"));
//                                    edit.putString("login_mobno", jsonObject.getString("mob_no"));
//                                    edit.putString("login_email", jsonObject.getString("email"));
//                                    edit.putString("login_repto", jsonObject.getString("reporting_to"));
//                                    edit.putString("login_firstnm", jsonObject.getString("first_name"));
//                                    edit.putString("login_lastnm", jsonObject.getString("last_name"));
//                                    edit.putString("login_devid", Device_id);
//                                    edit.putString("login_adrs", jsonObject.getString("address"));
//                                    edit.commit();
//

//                                    loginDataBaseAdapter.updaEntry(jsonObject.getString("user_name"), jsonObject.getString("encrypted_password"), jsonObject.getString("date_of_joining"), jsonObject.getString("mob_no"), jsonObject.getString("email"), jsonObject.getString("reporting_to"),
//                                            jsonObject.getString("first_name"), jsonObject.getString("last_name"),"", "", "", "", "",
//                                            "", Device_id, "", jsonObject.getString("address"), "", "", "", "", "");
//                                }


//                            String response_result = "";
//                            if (response_result.equalsIgnoreCase("Device not found.")) {
//
//                                // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();
//
//                                Toast toast = Toast.makeText(Forget_Pwd.this, response_result, Toast.LENGTH_LONG);
//                                toast.setGravity(Gravity.CENTER, 0, 0);
//                                toast.show();
//
//                            } else {
//                                Toast.makeText(getApplicationContext(), "Register successfully.", Toast.LENGTH_LONG).show();
//                                //dbvoc.getDeleteTable("USERS");
//
////                                JSONArray users = response.getJSONArray("users");
//////
////                                Log.i("volley", "response reg users Length: " + users.length());
////
////                                if (users.length() <= 0) {
////                                    dialog.dismiss();
////                                    //Toast.makeText(LoginActivity.this, "User not found, Please contact with it team.", Toast.LENGTH_SHORT).show();
////                                    Toast toast = Toast.makeText(Forget_Pwd.this, "User not found, Please contact with it team.", Toast.LENGTH_LONG);
////                                    toast.setGravity(Gravity.CENTER, 0, 0);
////                                    toast.show();
////                                } else {
//////                                    Log.d("users", "users" + users.toString());
//////
//////
//////                                    for (int i = 0; i < users.length(); i++) {
//////
//////                                        JSONObject jsonObject = users.getJSONObject(i);
//////
//////                                        SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//////                                        SharedPreferences.Editor edit = pref.edit();
//////                                        edit.putString("login_usernm", jsonObject.getString("user_name"));
//////                                        edit.putString("login_pwd", jsonObject.getString("encrypted_password"));
//////
//////                                        edit.commit();
//////
////////                                loginDataBaseAdapter.insertEntry(jsonObject.getString("user_name"), jsonObject.getString("encrypted_password"), jsonObject.getString("date_of_joining"), jsonObject.getString("mob_no"), jsonObject.getString("email"), jsonObject.getString("reporting_to"),
////////                                        jsonObject.getString("first_name"), jsonObject.getString("last_name"),"", "", "", "", "",
////////                                        "", Device_id, "", jsonObject.getString("address"), "", "", "", "", "");
//////                                    }
////
////                                    //Toast.makeText(getApplicationContext(), "Register successfully.", Toast.LENGTH_LONG).show();
////
////
////                                    Toast toast = Toast.makeText(Forget_Pwd.this, "Register successfully.", Toast.LENGTH_LONG);
////                                    toast.setGravity(Gravity.CENTER, 0, 0);
////                                    toast.show();
////                                    dialog.dismiss();
////                                }
//	                          	                            //finish();
//                            }

                        // }

                        // output.setText(data);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            dialog.dismiss();
//                            finish();
//                        }


                        dialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("volley", "error: " + error);
                        // Toast.makeText(getApplicationContext(), "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
                        Toast toast = Toast.makeText(Forget_Pwd.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        dialog.dismiss();

                    }
                });

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                jsObjRequest.setShouldCache(false);
                int socketTimeout = 300000;//30 seconds - change to what you want
                RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                jsObjRequest.setRetryPolicy(policy);
                requestQueue.add(jsObjRequest);

            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();
            }
        } else {
            dialog.dismiss();
            Toast.makeText(this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
        }
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
        Intent i = new Intent(Forget_Pwd.this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }
}
