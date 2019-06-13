package com.anchor.activities;

import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.imageadapters.GalleryAdapter;
import com.anchor.imageadapters.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Image_Gellary extends FragmentActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    public DownloadManager downloadManager;
    public long refid;
    public Uri Download_Uri;
    public ArrayList<Long> list = new ArrayList<>();
    LoginDataBaseAdapter loginDataBaseAdapter;
    DataBaseHelper dbvoc = new DataBaseHelper(this);
    Intent target;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_main);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        loginDataBaseAdapter=new LoginDataBaseAdapter(Image_Gellary.this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();

        //pDialog = new ProgressDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
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

                Image image = images.get(position);

                String type = image.getType();
                String image_url = image.getLarge();
              //  File file = new File(Uri.parse(image_url));

                if(type.equalsIgnoreCase("application/pdf"))
                {
                    if(!image_url.isEmpty()) {
                        Intent target = new Intent(Intent.ACTION_VIEW);
                        target.setDataAndType(Uri.parse(image_url), "application/pdf");
                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        Intent intent = Intent.createChooser(target, "Open File");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // Instruct the user to install a PDF reader here, or something
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
                }
                else
                if(type.equalsIgnoreCase("video/mp4"))
                {
                    if(!image_url.isEmpty()) {

                        String strNew = image_url.toString();
                        File yourFile = new File(strNew.trim());
                        Uri uri = Uri.fromFile(yourFile);

//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        intent.setDataAndType(Uri.parse(image_url), "video/mp4");
//                        startActivity(intent);

                        String fileName =image_url.substring(image_url.lastIndexOf('/')+1, image_url.length());

                        Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW);
                        Uri data = Uri.parse("file://"+Environment.getExternalStorageDirectory().getPath()+"/Anchor_NewLaunch"
                                + "/"+fileName);
                        intent1.setDataAndType(data, "video/mp4");
                        startActivity(intent1);

//                        Intent intent = Intent.createChooser(target, "Open File");
//                        try {
//                            startActivity(intent);
//                        } catch (ActivityNotFoundException e) {
//                            // Instruct the user to install a PDF reader here, or something
//                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
                }
                else
                if(type.equalsIgnoreCase("text/plain"))
                {
                    if(!image_url.isEmpty()) {
                        Intent target = new Intent(Intent.ACTION_VIEW);
                        target.setDataAndType(Uri.parse(image_url), "text/csv");
                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                        Intent intent = Intent.createChooser(target, "Open File");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            // Instruct the user to install a PDF reader here, or something
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
                }
                else
                if(type.equalsIgnoreCase("audio/mpeg"))
                {
                    if(!image_url.isEmpty()) {

                        String fileName =image_url.substring(image_url.lastIndexOf('/')+1, image_url.length());

                        Intent intent1 = new Intent(android.content.Intent.ACTION_VIEW);
                        Uri data = Uri.parse("file://"+Environment.getExternalStorageDirectory().getPath()+"/Anchor_NewLaunch"
                                + "/"+fileName);
                        intent1.setDataAndType(data, "audio/*");
                        startActivity(intent1);

//                        Intent target = new Intent(Intent.ACTION_VIEW);
//                        target.setDataAndType(Uri.parse(image_url), "audio/*");
//                        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                        Intent intent = Intent.createChooser(target, "Open File");
//                        try {
//                            startActivity(intent);
//                        } catch (ActivityNotFoundException e) {
//                            // Instruct the user to install a PDF reader here, or something
//                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();
                }
                else
                {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                    newFragment.setArguments(bundle);
                    newFragment.show(ft, "slideshow");
                }











               // ft.add(ft, newFragment).commit();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
       try{
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


           mActionBar.setCustomView(mCustomView);
           mActionBar.setDisplayShowCustomEnabled(true);
           mActionBar.setHomeButtonEnabled(true);
           mActionBar.setDisplayHomeAsUpEnabled(true);
       }catch(Exception ex){ex.printStackTrace();}


       // fetchImages();

        new Image_Gellary.LongOperation().execute();
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





    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                List<Image> dealer_check_details1 = dbvoc.TABLE_CREATE_NEW_LAUNCHES_NEW_Data();

                if(dealer_check_details1.size() > 0) {
                    for (Image details : dealer_check_details1) {

                        Image image = new Image();
                        image.setName(details.getName());
                        image.setLarge(details.getLarge());
                        image.setType(details.getType());
                        image.setTimestamp(details.getTimestamp());

                        images.add(image);
                    }

                    mAdapter.notifyDataSetChanged();
                }

            } catch (Exception e) {

            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private void openFile(File url) {

        try {

            Uri uri = Uri.fromFile(url);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if (url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if (url.toString().contains(".jpg")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".png")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".jpeg")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".gif")) {
                // PDF file
                intent.setDataAndType(uri, "image/*");
            } else if (url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if (url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");

            } else if (url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");

            } else if (url.toString().contains(".mp4") || url.toString().contains(".AVI") || url.toString().contains(".FLV") || url.toString().contains(".WMV") || url.toString().contains(".MOV")) {
                // Text file
                intent.setDataAndType(uri, "video/mp4");

            } else {
                intent.setDataAndType(uri, "text/csv");
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No application found which can open the file", Toast.LENGTH_SHORT).show();
        }
    }

}