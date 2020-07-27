package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import cpm.simplelogic.helper.CheckNullValue;

public class ChangePassword extends Activity {
    EditText oldPassword,newPassword,confirmPassword;
    ProgressDialog dialog;
    Button btnOk,btnCancel;
    private DataBaseHelper dbvoc = null;
    Boolean isInternetPresent = false;
    private int passwordNotVisible=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        dbvoc = new DataBaseHelper(this);

        try {
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
            mTitleTextView.setText("Change Password");

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        oldPassword = (EditText)findViewById(R.id.old_password);
        newPassword = (EditText)findViewById(R.id.new_password);
        confirmPassword = (EditText)findViewById(R.id.confirm_passwrod);
        btnOk = (Button) findViewById(R.id.button_ok);
        btnCancel = (Button)findViewById(R.id.button_cancel);

        oldPassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (oldPassword.getRight() - oldPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = ChangePassword.this.getCurrentFocus();
                        if (view != null) {
//                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        if (passwordNotVisible == 1) {
                            oldPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_black_24dp), null);
                            oldPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passwordNotVisible = 0;

                        } else {
                            oldPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_off_black_24dp), null);
                            oldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordNotVisible = 1;

                        }
                        return true;
                    }
                }
                return false;
            }
        });

        newPassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;


                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (newPassword.getRight() - newPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = ChangePassword.this.getCurrentFocus();
                        if (view != null) {
                           // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        if (passwordNotVisible == 1) {
                            newPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_black_24dp), null);
                            newPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passwordNotVisible = 0;

                        } else {
                            newPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_off_black_24dp), null);
                            newPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordNotVisible = 1;

                        }
                        return true;
                    }
                }
                return false;
            }
        });

        confirmPassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (confirmPassword.getRight() - confirmPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        View view = ChangePassword.this.getCurrentFocus();
                        if (view != null) {
                           // InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                           // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                        //autoCompleteTextView1.setText("");
                        if (passwordNotVisible == 1) {
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_black_24dp), null);
                            confirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                            passwordNotVisible = 0;

                        } else {
                            confirmPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_visibility_off_black_24dp), null);
                            confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            passwordNotVisible = 1;

                        }
                        return true;
                    }
                }
                return false;
            }
        });


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Global_Data.isNetworkAvailable(ChangePassword.this)) {

                    if (CheckNullValue.findNullValue(oldPassword.getText().toString().trim()) == true) {
                        // Toast.makeText(LoginActivity.this, "Please Enter UserName", Toast.LENGTH_SHORT).show();

                        Toast toast = Toast.makeText(ChangePassword.this, "Please Enter Old Password", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } else if (CheckNullValue.findNullValue(newPassword.getText().toString().trim()) == true) {
                        // Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                        Toast toast = Toast.makeText(ChangePassword.this, "Please Enter New Password", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else if (CheckNullValue.findNullValue(confirmPassword.getText().toString().trim()) == true) {
                        // Toast.makeText(LoginActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                        Toast toast = Toast.makeText(ChangePassword.this, "Please Enter Confirm Password", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else {

                        dialog = new ProgressDialog(ChangePassword.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
                        dialog.setMessage("Please Wait...");
                        dialog.setTitle(getResources().getString(R.string.app_name));
                        dialog.setCancelable(false);
                        dialog.show();

                        if(newPassword.length()>7)
                        {
                            if(newPassword.getText().toString().trim().equalsIgnoreCase(confirmPassword.getText().toString().trim()))
                            {
                                ChangePassword();
                            }else{
                                Toast toast = Toast.makeText(ChangePassword.this, "New password and confirm password not matched", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                dialog.dismiss();
                            }
                        }else {
                            Toast toast = Toast.makeText(ChangePassword.this, "Password should be minimum 8 characters", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            dialog.dismiss();
                        }
                    }

                }else{
                    Toast toast = Toast.makeText(ChangePassword.this, "Please Check Internet Connection", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    //dialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(ChangePassword.this,MainActivity.class);
                startActivity(a);
            }
        });

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

        Intent i = new Intent(ChangePassword.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        startActivity(i);
        finish();
    }

    public void ChangePassword()
    {
        SharedPreferences spf = ChangePassword.this.getSharedPreferences("SimpleLogic", 0);
        String user_email = spf.getString("USER_EMAIL",null);
        String Device_id = spf.getString("devid", "");

        String domain = getResources().getString(R.string.service_domain);
        RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
        String url = domain + "users/change_passowd?email=" + user_email+"&imei_no=" +Device_id;
        Log.d("Chan Pass","CHA URl"+url);
        StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        JSONObject json = null;
                        try {
                            json = new JSONObject(new JSONTokener(response));

                            String response_result = "";
                            if(json.has("result"))
                            {
                                response_result = json.getString("result");
                            }
                            else
                            {
                                response_result = "data";
                            }

                            if(response_result.equalsIgnoreCase("Password updated successfully")) {

                                SharedPreferences spf = ChangePassword.this.getSharedPreferences("SimpleLogic", 0);
                                String user_email = spf.getString("USER_EMAIL",null);

                                dbvoc.update_password(json.getString("password"),user_email);

                                Toast toast = Toast.makeText(ChangePassword.this, response_result, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent a = new Intent(ChangePassword.this,LoginActivity.class);
                                startActivity(a);
                                finish();
                                dialog.dismiss();


                            }else{

                                Toast toast = Toast.makeText(ChangePassword.this, response_result, Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                dialog.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("old_password", oldPassword.getText().toString().trim());
                params.put("new_password", newPassword.getText().toString().trim());
                params.put("confirm_password", confirmPassword.getText().toString().trim());
                return params;
            }
        };
        queue.add(strRequest);
    }
}
