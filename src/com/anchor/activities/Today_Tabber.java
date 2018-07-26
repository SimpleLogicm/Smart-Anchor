package com.anchor.activities;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
 
public class Today_Tabber extends TabActivity {
	HttpPost httppst;
	HttpClient httpclint;
	List<NameValuePair> nameValuePars;
	HttpPost httppost;
	HttpResponse response;
	ImageView img_back;
	TextView txt_header;
	TabHost tabHost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.today_tab);
        try
        {
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
            mTitleTextView.setText("Today");

            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
            SharedPreferences sp = Today_Tabber.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//        	todaysTarget.setText("Today's Target Acheived");
//		}

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            String strDate = sdf.format(c.getTime());

            todaysTarget.setText("Date :- "+strDate);
        }catch(Exception ex){ex.printStackTrace();}

        
//        img_back=(ImageView)findViewById(R.id.tab_back);
//	    txt_header=(TextView)findViewById(R.id.follow_veb);
        
//	    if(SharedData.ttl_fwlr.equalsIgnoreCase("0"))
//	    {
//	      txt_header.setText("Followers");		
//	    }else{
//	    	   txt_header.setText(SharedData.ttl_fwlr+" "+"Followers");
//	         }
	    
//	    img_back.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
////				Intent i=new Intent(Add_Member.this,CamColorEffect.class);
////				startActivity(i);
//				finish();
//				overridePendingTransition(R.anim.in_from_left,R.anim.no_change);
//			}
//		});
        
        tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
 
//        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
//    		@Override 
//    			public void onTabChanged(String tabId) {
//    			   
//                   if(tabId.equalsIgnoreCase("flwr")){
//                	   new Thread(new Runnable() {
//    					   public void run() {
//    					    	 Flwr();
//    					    	// Flwg();
//    					      }
//    					    }).start();
//    			  }
//                   
//                  if(tabId.equalsIgnoreCase("flwng")){
//                	  new Thread(new Runnable() {
//   					   public void run() {
//   					    	 Flwr();
//   					    	// Flwg();
//   					      }
//   					    }).start();
//                	
//    				   				                }
//                   
//                           		}
//    		};
    		
        //tabHost.setOnTabChangedListener(tabChangeListener);
    	
        intent = new Intent().setClass(this, List_Events.class);
        View tabView = createTabView(this, "Task");
        spec = tabHost.newTabSpec("flwr").setIndicator(tabView).setContent(intent);
        tabHost.addTab(spec);
 
        intent = new Intent().setClass(this, List_Events.class);
        tabView = createTabView(this, "Travel Planner");
        spec = tabHost.newTabSpec("flwng").setIndicator(tabView).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, List_Events.class);
        tabView = createTabView(this, "Leave Management");
        spec = tabHost.newTabSpec("flwng").setIndicator(tabView).setContent(intent);
        tabHost.addTab(spec);
        tabHost.setCurrentTab(0);
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
		
			    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
    
//    void Flwr()
//    {
//   	 try
//		   {
//   		        httpclint=new DefaultHttpClient();
//			    httppst=new HttpPost("http://192.168.0.121/vblive_new/api/getFollowings?account_id="+SharedData.login_name+"&end_limit=28");
//			    nameValuePars=new ArrayList<NameValuePair>();
//			    httppst.setEntity(new UrlEncodedFormEntity(nameValuePars));
//				ResponseHandler<String> responseHandler = new BasicResponseHandler();
// 			    final String response = httpclint.execute(httppst, responseHandler);
// 			    //SharedData.Fwr_Resp=httpclint.execute(httppst, responseHandler);
// 			    System.out.println("follwr_resp===============================>"+response);	
// 			    //finish();
// 			    //startActivity(new Intent(ProfileViewStream.this,FollowTabber.class));
//				overridePendingTransition(R.anim.slide_left,R.anim.slide_out);
//				runOnUiThread(new Runnable() {
//				    public void run() {
//				    
//				    }
//				});
//				
//			}catch(Exception e){
//				                 System.out.println("Exception:"+e.getMessage());
//			                   }
//   }
//
//	void Flwg()
//    {
//   	 try
//		   {
//   		        httpclint=new DefaultHttpClient();
//			    httppst=new HttpPost("http://192.168.0.121/vblive_new/api/getFollower?account_id="+SharedData.login_name+"&end_limit=28");
//			    nameValuePars=new ArrayList<NameValuePair>();
//			    httppst.setEntity(new UrlEncodedFormEntity(nameValuePars));
//				ResponseHandler<String> responseHandler = new BasicResponseHandler();
// 			    final String response = httpclint.execute(httppst, responseHandler);
// 			    SharedData.Fwg_Resp=httpclint.execute(httppst, responseHandler);
// 			    System.out.println("follwg_resp===============================>"+response);	
// 			    //finish();
//				//startActivity(new Intent(ProfileLifeStream.this,FollowTabber.class));
//				runOnUiThread(new Runnable() {
//				    public void run() {
//				    
//				    }
//				});
//				
//			}catch(Exception e){
//				                 System.out.println("Exception:"+e.getMessage());
//			                   }
//   }
//    
    private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.follow_tabbg, null);
		TextView tv = (TextView) view.findViewById(R.id.follow_txt);
		tv.setText(text);
		return view;
	}
}