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

}