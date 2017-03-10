package com.anchor.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

public class List_Events extends Activity{
	ListView event_list;
	CustomListAdapter adapter;
	JSONObject jsonobject;
	JSONArray jsonarray;
	String str,str1;
	String line;
	BufferedReader in = null;
	ArrayList<HashMap<String, String>> arraylist1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_events);
		event_list=(ListView)findViewById(R.id.event_list);
		
		new Thread(new Runnable() {
			   public void run() {
			    	 Flwg();
			      }
			    }).start();
		
//		ActionBar mActionBar = getActionBar();
//		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//       // mActionBar.setDisplayShowHomeEnabled(false);
//       // mActionBar.setDisplayShowTitleEnabled(false);
//        LayoutInflater mInflater = LayoutInflater.from(this);
//        Intent i = getIntent();
//		String name = i.getStringExtra("retialer");
//        View mCustomView = mInflater.inflate(R.layout.action_bar, null);
//        mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
//        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
//        mTitleTextView.setText("List Events");
//        
//        TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
//        SharedPreferences sp = List_Events.this.getSharedPreferences("SimpleLogic", 0);
//       
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//        	todaysTarget.setText("Today's Target Acheived");
//		}
//        
//        mActionBar.setCustomView(mCustomView);
//        mActionBar.setDisplayShowCustomEnabled(true);
//        mActionBar.setHomeButtonEnabled(true);
//        mActionBar.setDisplayHomeAsUpEnabled(true);
//		
	}
	
	void Flwg()
    {
		
//		try{
//	           HttpClient httpclient = new DefaultHttpClient();
//               HttpGet request = new HttpGet();
//	           URI website = new URI("http://19e3fb71.ngrok.com/fieldsales/user/3/tasks/2015-09-15/all");
//	           request.setURI(website);
//	           HttpResponse response = httpclient.execute(request);
//	           in = new BufferedReader(new InputStreamReader(
//	                   response.getEntity().getContent()));
//
//	           // NEW CODE
//	           line = in.readLine();
//	           Global_Data.calendar_resp=line;
//	           System.out.println("fdgg:-------------------------->"+line);
//	           new DownloadJSON1().execute();
//	         //  Toast.makeText(MainActivity.this, "dzdd"+response, Toast.LENGTH_SHORT).show();
////	           textv.append(" First line: " + line);
////	           // END OF NEW CODE
////
////	           textv.append(" Connected ");
//	       }catch(Exception e){
//	           Log.e("log_tag", "Error in http connection "+e.toString());
//	       }
 }
	
	// DownloadJSON AsyncTask
		 	public class DownloadJSON1 extends AsyncTask<Void, Void, Void> {
		      	
		 		@Override
		 		 protected void onPreExecute() {
		 			super.onPreExecute();
		         }
		      	
		 		 @Override
		 		 protected Void doInBackground(Void... params) {
		 			// Create the array 
		 			arraylist1 = new ArrayList<HashMap<String,String>>();
		 			
		 			try {
		 				jsonobject = new JSONObject(Global_Data.calendar_resp);
		 				// Locate the array name
		 				jsonarray = jsonobject.getJSONArray("tasks");

		 				for (int i = 0; i < jsonarray.length(); i++) {
		 					HashMap<String, String> map = new HashMap<String, String>();
		 					jsonobject = jsonarray.getJSONObject(i);
		 					
		 			        map.put("details", jsonobject.getString("details"));
		 					map.put("id", jsonobject.getString("id"));
		 						 				
		 					// Set the JSON Objects into the array
		 					arraylist1.add(map);
		 				}
		 				
		 			}catch(JSONException e){
		 				Log.e("Error", e.getMessage());
		 				e.printStackTrace();
		 			} 
		 			return null;
		 		}

		 	  @Override
		 		protected void onPostExecute(Void args) {
		 			// Locate the listview in listview_main.xml
		 			adapter = new CustomListAdapter(List_Events.this, arraylist1);
		 			event_list.setAdapter(adapter);
		 	     	}
		     }
		 	
		 	public class CustomListAdapter extends BaseAdapter
			{
				ArrayList<HashMap<String, String>> myData;
				Context con;
				HashMap<String, String> resultp = new HashMap<String, String>();
				public CustomListAdapter(Context context,ArrayList<HashMap<String, String>> arraylist) {
					this.con = context;
					myData= arraylist;
				}
				    
				@Override
				public int getCount() {
					return myData.size();
				}

				@Override
				public Object getItem(int position) {
					return myData.get(position);
				}

				@Override
				public long getItemId(int position) {
					return position;
				}
		
				//class for caching the views in a row  
				private class ViewHolder
				{
					TextView name;
					//ImageView img;
				}

				//ArrayList<ViewHolder> viewMaster;
				ViewHolder viewHolder;

				@SuppressLint("InflateParams")
				@Override
				public View getView(final int position, View convertView, final ViewGroup parent) {
					//ImageLoader imageLoader = new ImageLoader(con);
					if(convertView==null)
					{        
						//inflate the custom layout   
						LayoutInflater inflater = LayoutInflater.from(con);
						convertView=inflater.inflate(R.layout.cal_list, null);
						viewHolder=new ViewHolder();

						//cache the views
						viewHolder.name = (TextView) convertView.findViewById(R.id.textView1);				 
						//viewHolder.img  = (ImageView) convertView.findViewById(R.id.icon);
						
						//link the cached views to the convertview
						convertView.setTag(viewHolder);
					}
					else
					{
						viewHolder=(ViewHolder) convertView.getTag();
					}
					
//					str="http://192.168.0.127/vblive_new/CreateAlbumThumbnail.php?image_request_type=user_image_medium&acid="+myData.get(position).get("encrypted_account_id").toString();
//					
//					viewHolder.name.setOnClickListener(new OnClickListener() {
//						 @Override
//						   public void onClick(View v) {
////							    resultp = myData.get(position);
////							    str1="http://192.168.0.121/vblive_new/CreateAlbumThumbnail.php?image_request_type=club_micro_photo&photo_id="+resultp.get("photo_id").toString();
////								SharedData.club_name=(resultp.get("name").toString());
////								SharedData.myclub_id=(resultp.get("ID").toString());
////								SharedData.myclub_photoid=(resultp.get("photo_id").toString());
////								SharedData.club_image=(str1);
////							    //Toast.makeText(MyClub.this,"id:"+str, Toast.LENGTH_SHORT).show();
////							    startActivity(new Intent(Follower.this,MyClubClick.class));
////							    overridePendingTransition(R.anim.slide_left,R.anim.slide_out);
//						  }
//					});
							
					//set the data to be displayed
					viewHolder.name.setText(myData.get(position).get("details").toString());
				
					//ImageLoader.getInstance().displayImage(str,viewHolder.img);
					//ImageLoader.getInstance().displayImage(myData.get(position).get("flag").toString(),viewHolder.flag);
					//return the view to be displayed
					return convertView;
				}
			}
}
