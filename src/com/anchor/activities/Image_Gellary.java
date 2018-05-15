package com.anchor.activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anchor.App.AppController;
import com.anchor.imageadapters.GalleryAdapter;
import com.anchor.imageadapters.Image;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;


public class Image_Gellary extends FragmentActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_main);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

         recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);


                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");



               // ft.add(ft, newFragment).commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        ActionBar mActionBar = getActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        // mActionBar.setDisplayShowHomeEnabled(false);
        // mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);

        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
        mTitleTextView.setText("New Launches");

        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
        todaysTarget.setVisibility(View.INVISIBLE);
        ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
        SharedPreferences sp = Image_Gellary.this.getSharedPreferences("SimpleLogic", 0);

        H_LOGO.setImageResource(R.drawable.video_imagenew);
        H_LOGO.setVisibility(View.INVISIBLE);

//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//			todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//			todaysTarget.setText("Today's Target Acheived");
//		}

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

       // fetchImages();
        GetNewLaunch_Datann();
    }

    private void fetchImages() {

        pDialog.setMessage("Please wait...");
       // pDialog.setCancelable(false);
        pDialog.show();

        JsonArrayRequest req = new JsonArrayRequest(endpoint,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(object.getString("name"));

                                JSONObject url = object.getJSONObject("url");
                                image.setSmall(url.getString("small"));
                                image.setMedium(url.getString("medium"));
                                image.setLarge(url.getString("large"));
                                image.setTimestamp(object.getString("timestamp"));

                                images.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Image_Gellary.this);
        int socketTimeout = 300000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        // Adding request to request queue
        requestQueue.add(req);
       // AppController.getInstance().addToRequestQueue(req);
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        this.finish();
    }

    public  void GetNewLaunch_Datann()
    {

        TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = telephonyManager.getDeviceId();
        //context = contextn;

        //loginDataBaseAdapter=new LoginDataBaseAdapter(Video_Main_List.this);
        //loginDataBaseAdapter=loginDataBaseAdapter.open();


        //PreferencesHelper Prefs = new PreferencesHelper(context);
        //String URL = Prefs.GetPreferences("URL");
        String domain = "";

        //dialog = new ProgressDialog(Video_Main_List.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        pDialog.setMessage("Please wait....");
        pDialog.setTitle("Anchor");
        pDialog.setCancelable(false);
        pDialog.show();

        domain = getResources().getString(R.string.service_domain);

        Log.d("Server url","Server url"+domain+"new_launches?imei_no="+device_id);

        StringRequest stringRequest = null;
        stringRequest = new StringRequest(domain+"new_launches?imei_no="+device_id,
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
                                    pDialog.hide();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent launch = new Intent(Image_Gellary.this,MainActivity.class);
                                    startActivity(launch);
                                    finish();

                                }
                                else
                                if(response_result.equalsIgnoreCase("Device not registered")) {
                                    pDialog.hide();
                                   // Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

                                    Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                    Intent launch = new Intent(Image_Gellary.this,MainActivity.class);
                                    startActivity(launch);
                                    finish();

                                }
                                else {

                                    JSONArray launches = json.getJSONArray("launches");
//
                                    Log.i("volley", "response reg launches Length: " + launches.length());
//
                                    Log.d("users", "launches" + launches.toString());
//
                                    //
                                    images.clear();
                                    for (int i = 0; i < launches.length(); i++) {

                                        JSONObject object = launches.getJSONObject(i);
                                        Image image = new Image();
                                        image.setName(object.getString("name"));

                                       // JSONObject url = object.getJSONObject("url");
                                        image.setSmall(object.getString("small"));
                                        image.setMedium(object.getString("small"));
                                        image.setLarge(object.getString("medium"));
                                        image.setTimestamp(object.getString("date"));

                                        images.add(image);


                                    }

                                    //Intent launch = new Intent(context,Youtube_Player_Activity.class);
                                    //startActivity(launch);

                                    pDialog.hide();
                                    mAdapter.notifyDataSetChanged();
                                    //finish();

                                }

                                //  finish();
                                // }

                                // output.setText(data);
                            }catch(JSONException e){e.printStackTrace();


                                Toast toast = Toast.makeText(Image_Gellary.this,
                                        "Service Error",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                Intent launch = new Intent(Image_Gellary.this,MainActivity.class);
                                startActivity(launch);
                                finish();

                                pDialog.hide(); }


                            pDialog.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            //  finish();
                            pDialog.dismiss();
                        }
                        pDialog.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                            Toast.makeText(Image_Gellary.this,
//                                    "Network Error",
//                                    Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Image_Gellary.this,
                                    "Network Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof AuthFailureError) {


                            Toast toast = Toast.makeText(Image_Gellary.this,
                                    "Server AuthFailureError  Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof ServerError) {

                            Toast toast = Toast.makeText(Image_Gellary.this,
                                    "Server   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof NetworkError) {

                            Toast toast = Toast.makeText(Image_Gellary.this,
                                    "Network   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else if (error instanceof ParseError) {


                            Toast toast = Toast.makeText(Image_Gellary.this,
                                    "ParseError   Error",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else
                        {
                           // Toast.makeText(Image_Gellary.this, error.getMessage(), Toast.LENGTH_LONG).show();

                            Toast toast = Toast.makeText(Image_Gellary.this, error.getMessage(), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        Intent launch = new Intent(Image_Gellary.this,MainActivity.class);
                        startActivity(launch);
                        finish();
                        pDialog.dismiss();
                        // finish();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(Image_Gellary.this);

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

}