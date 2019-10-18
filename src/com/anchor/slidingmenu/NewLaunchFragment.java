package com.anchor.slidingmenu;


import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anchor.activities.R;
import com.anchor.imageslider.adapter.FullScreenImageAdapter;
import com.anchor.imageslider.helper.Utils;
import com.anchor.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class NewLaunchFragment extends Fragment {
	
	/* ImageView selectedImage;  
     private Integer[] mImageIds = {
    		 R.drawable.facwah1,R.drawable.facewah2
              
        };*/
	
	
	public Utils utils;
	public FullScreenImageAdapter adapter;
	public ViewPager viewPager;

	public NewLaunchFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_fullscreen_view, container, false);
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", 0);
//        TextView welcomeUser=(TextView)rootView.findViewById(R.id.txtWelcomeUser);
//        //question_value.setTypeface(null,Typeface.BOLD);
//       welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
        /*Gallery gallery = (Gallery)rootView. findViewById(R.id.gallery1);
        selectedImage=(ImageView)rootView.findViewById(R.id.imageView1);
        gallery.setSpacing(1);
        gallery.setAdapter(new GalleryImageAdapter(getActivity()));

         // clicklistener for Gallery
        gallery.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
                // show the selected Image
                selectedImage.setImageResource(mImageIds[position]);
            }

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				 selectedImage.setImageResource(mImageIds[position]);
			}
        });*/
        viewPager = rootView.findViewById(R.id.pager);

        LoadNewLaunchesAsyncTask loadNewLaunchesAsyncTask=new LoadNewLaunchesAsyncTask(getActivity());
        loadNewLaunchesAsyncTask.execute();
		
        return rootView;
    }
	
	
	public class LoadNewLaunchesAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		ArrayList<Product> data;
		
		String date="";
		
		boolean present=false;
		

		public LoadNewLaunchesAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading Please wait..");
			this.dialog.show();
			

		}

		@Override
		protected Void doInBackground(Void... params) {
			
			// TODO Auto-generated method stub
			
			utils = new Utils(NewLaunchFragment.this.getActivity().getApplicationContext());
			FileOutputStream outStream;
			String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
			File f;
			f=new File(extStorageDirectory + "/NAT");
			if (!f.exists()) {
				f.mkdir(); 
			}
		//	Bitmap bm1 = BitmapFactory.decodeResource( getResources(), R.drawable.facwah1);
		//	Bitmap bm2 = BitmapFactory.decodeResource( getResources(), R.drawable.facewah2);
			//Bitmap bm3 = BitmapFactory.decodeResource( getResources(), R.drawable.face3);
			//Bitmap bm4 = BitmapFactory.decodeResource( getResources(), R.drawable.face4);
			//Bitmap bm5 = BitmapFactory.decodeResource( getResources(), R.drawable.face5);
			
			File file1 = new File(extStorageDirectory+ "/NAT", "facwah1.jpg");
			File file2 = new File(extStorageDirectory+ "/NAT", "facewah2.jpg");
			//File file3 = new File(extStorageDirectory+ "/NAT", "face3.jpg");
			//File file4 = new File(extStorageDirectory+ "/NAT", "face4.jpg");
			//File file5 = new File(extStorageDirectory+ "/NAT", "face5.jpg");
			 try {
				outStream = new FileOutputStream(file1);
				//bm1.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				outStream = new FileOutputStream(file2);
			//	bm2.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				
				/*outStream = new FileOutputStream(file3);
				bm3.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				
				outStream = new FileOutputStream(file4);
				bm4.compress(Bitmap.CompressFormat.PNG, 100, outStream);
				
				outStream = new FileOutputStream(file5);
				bm5.compress(Bitmap.CompressFormat.PNG, 100, outStream);*/
				
			    outStream.flush();
			    outStream.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
				
			}
			
			Intent i = getActivity().getIntent();
			int position = i.getIntExtra("position", 0);

			adapter = new FullScreenImageAdapter(getActivity(),utils.getFilePaths());

			viewPager.setAdapter(adapter);

			// displaying selected image first
			viewPager.setCurrentItem(position);
			
		}
	}

}
