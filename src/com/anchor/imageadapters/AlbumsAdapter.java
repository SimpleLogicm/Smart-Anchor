package com.anchor.imageadapters;

/**
 * Created by vinod on 16-11-2016.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.activities.Customer_Feed;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import cpm.simplelogic.helper.Upload;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    public String[] allFiles;
    private File mediaStorageDir;
    private List<Album> albumList;
    DataBaseHelper dbvoc;
    ProgressDialog dialog;
    String response_result = "";


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sdcard_medialist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText("");



        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showPopupMenu(holder.overflow,position);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view,final int posi) {
        // inflate menu

        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(posi));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int pos;

        public MyMenuItemClickListener(int posi) {
            pos = posi;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {


          //  int listPosition = getAdapterPosition();
            dbvoc = new DataBaseHelper(mContext);
           String image_url =  albumList.get(pos).getThumbnail();

            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    //Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();

                    CallService(mContext,image_url,pos);
                    //uploadVideo(mContext,image_url);

                    return true;
                case R.id.action_play_next:

                    //String animal = albumList.get(position);
                    try {


                        File file = new File(image_url);
                        if (file.exists()) {
//                            if (Global_Data.Default_Image_Path.equalsIgnoreCase(image_url)) {
//                                Toast.makeText(mContext, "You can not delete current capture image.", Toast.LENGTH_SHORT).show();
//                            } else {
                                file.delete();
                                albumList.remove(pos);
                                notifyDataSetChanged();
                                dbvoc.getDeleteMediaBYID(image_url);


                           // }

                        }
                    }catch(Exception ex){ex.printStackTrace();}

                    mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_PICTURE");
                   // Toast.makeText(mContext, image_url, Toast.LENGTH_SHORT).show();
                    File folder = new File(mediaStorageDir.getPath());
                    try
                    {
                        if (folder.isDirectory()) {
                            allFiles = folder.list();

                            if(allFiles.length <= 0)
                            {
                                Toast.makeText(mContext, "All files deleted", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(mContext, Customer_Feed.class);
                                i.putExtra("CP_NAME", "Image");
                                i.putExtra("RE_TEXT", "");
                                mContext.startActivity(i);
                                ((Activity)mContext).finish();
                            }


                        }


                    }catch (Exception ex){
                        ex.printStackTrace();
                    }

                    List<Local_Data> details = dbvoc.getAllPICTURESDBY("Image");

                    if(details.size() <= 0) {

                        Toast.makeText(mContext, "All files deleted", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(mContext, Customer_Feed.class);
                        i.putExtra("CP_NAME", "Image");
                        i.putExtra("RE_TEXT", "");
                        mContext.startActivity(i);
                        ((Activity)mContext).finish();
                    }

                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public String gethbase64Code(String url)
    {

        File f = new File(url);
        Uri fileUri = Uri.fromFile(f);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String encodedImage = "";
        try
        {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            // String encodedImage =imageBytes.toString();

            return encodedImage;
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        return encodedImage;
    }

    public void CallService(final Context context, final String imageurl,final int posi)
    {
       String filename = imageurl.substring(imageurl.lastIndexOf("/") + 1);
        System.gc();
        dialog = new ProgressDialog(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        dialog.setMessage("Please wait....");
        dialog.setTitle("Smart Anchor App");
        dialog.setCancelable(false);
        dialog.show();
        String reason_code = "";
        try {

            String hashcode = gethbase64Code(imageurl);
            String domain = "";
            String device_id = "";

            SharedPreferences sp = context.getSharedPreferences("SimpleLogic", Context.MODE_PRIVATE);
            device_id = sp.getString("devid", "");

            domain = context.getResources().getString(R.string.service_domain);



            JsonObjectRequest jsObjRequest = null;
            try
            {



                Log.d("Server url","Server url"+domain+"customer_service_media");


                JSONArray CUSTOMERSN = new JSONArray();
                JSONArray PICTURE = new JSONArray();
                //JSONObject product_value = new JSONObject();
                JSONObject product_value_n = new JSONObject();
                JSONArray product_imei = new JSONArray();

                final DataBaseHelper dbvoc = new DataBaseHelper(context);

                List<Local_Data> contacts = dbvoc.getAllRetailer_cre();

                for (Local_Data cn : contacts)
                {
                    JSONObject product_value = new JSONObject();
                    product_value.put("user_email", cn.getemail());
                    product_value.put("code", cn.getLEGACY_CUSTOMER_CODE());
                    product_value.put("name", cn.getCUSTOMER_NAME());
                    product_value.put("shop_name", cn.getCUSTOMER_SHOPNAME());
                    product_value.put("address", cn.getADDRESS());
                    product_value.put("street", cn.getSTREET());
                    product_value.put("landmark", cn.getLANDMARK());
                    product_value.put("pincode", cn.getPIN_CODE());
                    product_value.put("mobile_no", cn.getMOBILE_NO());
                    product_value.put("email", cn.getEMAIL_ADDRESS());
                    product_value.put("status", cn.getSTATUS());
                    product_value.put("state_code", cn.getSTATE_ID());
                    product_value.put("city_code", cn.getCITY_ID());
                    product_value.put("beat_code", cn.getBEAT_ID());
                    product_value.put("vatin", cn.getvatin());
                    product_value.put("latitude", cn.getlatitude());
                    product_value.put("longitude", cn.getlongitude());
                    CUSTOMERSN.put(product_value);

                }

                List<Local_Data> imagevalue = dbvoc.getAllPICTURESDBYID(imageurl);

                for (Local_Data cn : imagevalue)
                {
                    JSONObject picture = new JSONObject();
                    picture.put("code", cn.getCode());
                    picture.put("customer_code", cn.getCust_Code());
                    picture.put("user_email",  cn.getEMAIL_ADDRESS());
                    picture.put("media_type",  cn.get_mediaType());
                    picture.put("media_text", cn.getcalender_details());
                    picture.put("filename",  filename);
                    picture.put("media_data", hashcode);

                    PICTURE.put(picture);
                }


                product_value_n.put("customers", CUSTOMERSN);
                product_value_n.put("media", PICTURE);
                product_value_n.put("imei_no", Global_Data.device_id);
                Log.d("customers Service",product_value_n.toString());

                jsObjRequest = new JsonObjectRequest(Request.Method.POST, domain+"customer_service_media", product_value_n, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("volley", "response: " + response);

                        Log.d("jV", "JV length" + response.length());
                        //JSONObject json = new JSONObject(new JSONTokener(response));
                        try{


                            if(response.has("result"))
                            {
                                response_result = response.getString("result");
                            }
                            else
                            {
                                response_result = "data";
                            }

                            if(response_result.equalsIgnoreCase("success")) {


                                String val = "";
                                dbvoc.updateCustomerby_CreateAt(val);

                                try {


                                    File file = new File(imageurl);
                                    if (file.exists()) {
//                            if (Global_Data.Default_Image_Path.equalsIgnoreCase(image_url)) {
//                                Toast.makeText(mContext, "You can not delete current capture image.", Toast.LENGTH_SHORT).show();
//                            } else {
                                        file.delete();
                                        albumList.remove(posi);
                                        notifyDataSetChanged();
                                        dbvoc.getDeleteMediaBYID(imageurl);
                                        // }

                                    }
                                }catch(Exception ex){ex.printStackTrace();}

                                mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_PICTURE");
                               // Toast.makeText(mContext, imageurl, Toast.LENGTH_SHORT).show();
                                File folder = new File(mediaStorageDir.getPath());
                                try
                                {
                                    if (folder.isDirectory()) {
                                        allFiles = folder.list();

                                        if(allFiles.length <= 0)
                                        {
                                           // Toast.makeText(mContext, "All files deleted", Toast.LENGTH_SHORT).show();
                                            Intent i=new Intent(mContext, Customer_Feed.class);
                                            i.putExtra("CP_NAME", "Image");
                                            i.putExtra("RE_TEXT", "");
                                            mContext.startActivity(i);
                                            ((Activity)mContext).finish();
                                        }


                                    }


                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }

                                Toast toast = Toast.makeText(context, "Media Upload Successfully.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                List<Local_Data> details = dbvoc.getAllPICTURESDBY("Image");

                                if(details.size() <= 0) {

                                   // Toast.makeText(mContext, "All files deleted", Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(mContext, Customer_Feed.class);
                                    i.putExtra("CP_NAME", "Image");
                                    i.putExtra("RE_TEXT", "");
                                    mContext.startActivity(i);
                                    ((Activity)mContext).finish();
                                }

                                dialog.dismiss();

                            }
                            else
                            {


                                        dialog.dismiss();
                                        Toast toast = Toast.makeText(context,response_result, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();

                            }

                            //  finish();
                            // }

                            // output.setText(data);
                        }catch(JSONException e){e.printStackTrace();


                                    dialog.dismiss();

                        }


                        dialog.dismiss();

                        //dialog.dismiss();
                        //dialog.dismiss();




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("volley", "error: " + error);
                        //Toast.makeText(Customer_Feed.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();


                                Toast toast = Toast.makeText(context, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();




                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8" );
                            JSONObject jsonObject = new JSONObject( responseBody );
                        }
                        catch ( JSONException e ) {
                            //Handle a malformed json response
                        } catch (UnsupportedEncodingException errorn){

                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }

                        dialog.dismiss();


                    }
                });



                RequestQueue requestQueue = Volley.newRequestQueue(context);

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

    private void uploadVideo(final Context con, final String url) {

        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(con, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                // textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));
                // textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.upLoad2Server(url);
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }
}

