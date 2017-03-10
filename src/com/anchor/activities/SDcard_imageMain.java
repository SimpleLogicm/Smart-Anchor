package com.anchor.activities;

/**
 * Created by vinod on 16-11-2016.
 */

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anchor.imageadapters.Album;
import com.anchor.imageadapters.AlbumsAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SDcard_imageMain extends BaseActivity {

    public String[] allFiles;
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private File mediaStorageDir;
    DataBaseHelper dbvoc = new DataBaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdcard_images_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        mediaStorageDir = new File(
                //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),Config.IMAGE_DIRECTORY_NAME);
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"M_PICTURE");

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();


    }

   /* Adding few albums for testing
    */
    private void prepareAlbums() {

        File folder = new File(mediaStorageDir.getPath());

        //   uriAllFiles= new Uri[allFiles.length];

        try
        {
            if (folder.isDirectory()) {
                allFiles = folder.list();

                if(allFiles.length > 0)
                {
//                    for (int i = 0; i < allFiles.length; i++) {
//                        Log.d("all file path" + i, allFiles[i] + allFiles.length);
                       // Bitmap mBitmap = Bitmap.decodeFile(mediaStorageDir.getPath()+"/"+ allFiles[i]);
                         String c_details = "";
                        List<Local_Data> details = dbvoc.getAllPICTURESDBY("Image");

                       if(details.size() > 0) {
                           for (Local_Data cn : details) {
                               c_details = cn.getcalender_details();
                               //  Album a = new Album(c_details,"",mediaStorageDir.getPath()+"/"+ allFiles[i]);
                               Album a = new Album(c_details, "", cn.getmedia_id());
                               albumList.add(a);
                               //   }


                           }
                       }
                    else
                       {
                           Toast.makeText(SDcard_imageMain.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
                           Intent i=new Intent(SDcard_imageMain.this, Customer_Feed.class);
                           i.putExtra("CP_NAME", "Image");
                           i.putExtra("RE_TEXT", "");
                           overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                           startActivity(i);
                           finish();
                       }
                }
                else
                {
                    //Toast.makeText(Customer_Feed.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                //Toast.makeText(Customer_Feed.this, "Image Not Found.", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
//        int[] covers = new int[]{
//                R.drawable.logo,
//                R.drawable.logout,
//                R.drawable.logo,
//                R.drawable.logout,
//                R.drawable.logo,
//                R.drawable.logout,
//                R.drawable.logo,
//                R.drawable.logout,
//                R.drawable.logo,
//                R.drawable.logout,
//                R.drawable.logo};
//
//        Album a = new Album("True Romance", 13, covers[0]);
//        albumList.add(a);
//
//        a = new Album("Xscpae", 8, covers[1]);
//        albumList.add(a);
//
//        a = new Album("Maroon 5", 11, covers[2]);
//        albumList.add(a);
//
//        a = new Album("Born to Die", 12, covers[3]);
//        albumList.add(a);
//
//        a = new Album("Honeymoon", 14, covers[4]);
//        albumList.add(a);
//
//        a = new Album("I Need a Doctor", 1, covers[5]);
//        albumList.add(a);
//
//        a = new Album("Loud", 11, covers[6]);
//        albumList.add(a);
//
//        a = new Album("Legend", 14, covers[7]);
//        albumList.add(a);
//
//        a = new Album("Hello", 11, covers[8]);
//        albumList.add(a);
//
//        a = new Album("Greatest Hits", 17, covers[9]);
//        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
        // super.onBackPressed();

        Intent i=new Intent(SDcard_imageMain.this, Customer_Feed.class);
        i.putExtra("CP_NAME", "Image");
        i.putExtra("RE_TEXT", "");
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        startActivity(i);
        finish();
    }
}
