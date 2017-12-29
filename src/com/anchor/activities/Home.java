package com.anchor.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.anchor.animation.ActivitySwitcher;
import com.anchor.model.City;
import com.anchor.model.Product;
import com.anchor.model.Retailer;
import com.anchor.model.State;
import com.anchor.slidingmenu.AddRetailerFragment;
import com.anchor.slidingmenu.CalendarAct;
import com.anchor.slidingmenu.CalendarFragment;
import com.anchor.webservice.ConnectionDetector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Home extends Fragment {
	String SpinnerValue = "";
	Spinner spinner1,spinner2,spinner3;
	BufferedReader in = null;
	ProgressDialog dialog;
	String line;
	Cursor cursor;
	private DataBaseHelper dbHelper;
	public  SQLiteDatabase db;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	private float t_total = 0;
	private float achived_total = 0;

	private Calendar calendarn;
	private int year, month, day;
	TextView txtWelcomeUser;
	 
	//Button btnNewOrder;
	//Button buttonNewOrder,buttonPreviousOrder,buttonNoOrder,buttonReturnOrder;
	//DatabaseHandler myDbHelper;
	//ArrayList<DatabaseModel> dataCities,dataBeats,dataRetailers,dataIDs;
	 List<String> listBeat;
	 List<String> listRetailer;
	 ArrayAdapter<String> dataAdapterBeat;
	 ArrayAdapter<String> dataAdapterRetailer;
	 HashMap<String, String> retailersMap;
	 static int cityID,beatID,retailerID;
	 int data_stateid,data_cityID;
	 LoginDataBaseAdapter loginDataBaseAdapter;
	 String data_beatID;
	 DataBaseHelper dbvoc ;
	 View rootView;
	 ImageView order,calendar,custom_serve,expenses,target,schedule,logout,pricing,add_retailernew,marketing_data,syncmdata;
	 
	 ArrayList<State> dataStates = new ArrayList<State>();
     ArrayList<City> dataCities = new ArrayList<City>();
	 
	 public static  ArrayList<Retailer> dataRetaiers = new ArrayList<Retailer>();
	 public Home(){}
	 
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

		Global_Data.Stock_warehouse_flag = "";
		Global_Data.Stock_product_flag = "";
		Global_Data.Stock_product_flag_value_check = "";
		Global_Data.Stock_warehouse_flag_value_check = "";
        rootView = inflater.inflate(R.layout.fragment_logout, container, false);
               
        order=(ImageView)rootView.findViewById(R.id.order);
        calendar=(ImageView)rootView.findViewById(R.id.calendar);
        //custom_serve=(ImageView)rootView.findViewById(R.id.custom_serve);
        expenses=(ImageView)rootView.findViewById(R.id.expenses);
        target=(ImageView)rootView.findViewById(R.id.target);
		pricing=(ImageView)rootView.findViewById(R.id.pricing);
		add_retailernew=(ImageView)rootView.findViewById(R.id.add_retailernew);
		marketing_data=(ImageView)rootView.findViewById(R.id.marketing_data);
		syncmdata=(ImageView)rootView.findViewById(R.id.syncmdata);
		txtWelcomeUser=(TextView) rootView.findViewById(R.id.txtWelcomeUser);
        //schedule=(ImageView)rootView.findViewById(R.id.schedule);
        //logout=(ImageView)rootView.findViewById(R.id.logout);

		Global_Data.GLObalOrder_id = "";
		Global_Data.GLOvel_ITEM_NUMBER = "";
		Global_Data.GLOvel_GORDER_ID_RETURN = "";
		Global_Data.GLObalOrder_id_return = "";
		Global_Data.GLOvel_GORDER_ID= "";
		Global_Data.target_amount= "";
		Global_Data.target_grpby= "";

		String user_name = "";
		if(!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null"))
		{
			user_name = Global_Data.USER_FIRST_NAME.trim();
			if(!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null"))
			{
				user_name +=  " " + Global_Data.USER_LAST_NAME.trim();
			}
		}

		txtWelcomeUser.setText(user_name);

		loginDataBaseAdapter=new LoginDataBaseAdapter(getActivity());
		loginDataBaseAdapter=loginDataBaseAdapter.open();

		Global_Data.CUSTOMER_SERVICE_FLAG = "";
        
        City city1 = new City("Mumbai", "2", "N", "6", "CC002");
        City city2 = new City("Banglore", "5", "P", "12", "CD006");
        dataCities.add(city1);
        dataCities.add(city2);
        dbvoc = new DataBaseHelper(getActivity());
        cd = new ConnectionDetector(getActivity());

        order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Local_Data> contacts = dbvoc.checkCustomer();
				if(contacts.size() <= 0)
		        {

					Toast toast = Toast.makeText(getActivity(),
							"No customers found. Please try Sync or contact IT team", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

		        }
				else
				{
					Global_Data.CUSTOMER_SERVICE_FLAG = "";
					Intent intent = new Intent(getActivity(), Sales_Dash.class);
					startActivity(intent);
					getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					//getActivity().finish();
				}
    	}
		});
         
         calendar.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 				
// 				 new Thread(new Runnable() {
//					   public void run() {
//					    	 Flwg();
//					      }
//					    }).start();
 				Intent intent1 = new Intent(getActivity(), CalendarAct.class); 
 			    startActivity(intent1);
 				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				//getActivity().finish();
 	    	}
 		});
         
//         custom_serve.setOnClickListener(new OnClickListener() {
//  			@Override
//  			public void onClick(View v) {
//  				Intent intent1 = new Intent(getActivity(), Customer_Service.class);
//  				startActivity(intent1);
//  				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//  	    	}
//  		});
         
         expenses.setOnClickListener(new OnClickListener() {
 			@Override
 			public void onClick(View v) {
 					Intent intent = new Intent(getActivity(), Expenses.class);
	 				startActivity(intent);
	 				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				//getActivity().finish();

 	    	}
 		});

		pricing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				//sendSMSMessage();

//				if (MobNumber != null) {
//
//					for (int i = 0; i < MobNumber.size(); i++) {
						String message ="sfsdf";
						//String tempMobileNumber = MobNumber.get(i).toString();
						String tempMobileNumber = "8454858739";
						//sendSMS(tempMobileNumber, message);
					//}


				Intent intent = new Intent(getActivity(), Pricing_Main.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				//getActivity().finish();


			}
		});

		add_retailernew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Fragment fragment = null;
//				fragment = new AddRetailerFragment();
				Fragment AddRetailerFragment = new AddRetailerFragment();
				// consider using Java coding conventions (upper first char class names!!!)
				FragmentTransaction transaction = getFragmentManager().beginTransaction();

				// Replace whatever is in the fragment_container view with this fragment,
				// and add the transaction to the back stack
				transaction.replace(R.id.frame_container , AddRetailerFragment);
				transaction.addToBackStack(null);

				// Commit the transaction
				transaction.commit();


			}
		});


		marketing_data.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), Marketing.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				//getActivity().finish();


			}
		});


		syncmdata.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getActivity(), AllOrders_Sync.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				//getActivity().finish();

			}
		});
         
         target.setOnClickListener(new OnClickListener() {
  			@Override
  			public void onClick(View v) {
  				
  				isInternetPresent = cd.isConnectingToInternet();

//				AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create(); //Read Update
//				alertDialog.setTitle("Target");
//				alertDialog.setMessage("If you want to view target offline, Please click Offline button");
//				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Online",new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.cancel();
//						if (isInternetPresent)
//						{
//							getTargetData();
//
//	//					loginDataBaseAdapter.insertTargets("","", "","2016","SEP","500",
//	//							"200", "", "");
//	//
//	//					SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);
//	//					SharedPreferences.Editor editor=spf.edit();
//	//					//editor.putString("UserID", "admin");
//	//					//editor.putString("pwd", "test");
//	//					editor.putFloat("Target", 400);
//	//					editor.putFloat("Acheived",200);
//	//					//editor.putString("SimID", simSerial);
//	//					editor.commit();
//	//
//	//					Intent intent = new Intent(getActivity(), Target.class);
//	//					startActivity(intent);
//	//					getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//						}
//						else
//						{
//							// Internet connection is not present
//							// Ask user to connect to Internet
//							// showAlertDialog(AndroidDetectInternetConnectionActivity.this, "No Internet Connection",
//							//        "You don't have internet connection.", false);
//							//Toast.makeText(getActivity(),"You don't have internet connection.",Toast.LENGTH_LONG).show();
//
//							Toast toast = Toast.makeText(getActivity(), "You don't have internet connection.", Toast.LENGTH_LONG);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//
//						}
//
//
//					}
//				});

//				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Offline",new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {

//						List<Local_Data> contacts = dbvoc.checkTargets();
//						if(contacts.size() <= 0)
//						{
//							//Toast.makeText(getActivity(), "Target not found in offline mode", Toast.LENGTH_LONG).show();
//
//							Toast toast = Toast.makeText(getActivity(), "Target not found in offline mode", Toast.LENGTH_LONG);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//
//						}
//						else
//						{
							Intent i=new Intent(getActivity(), Target_REYC_Main.class);
							startActivity(i);
							getActivity().finish();
						//}

//						dialog.cancel();
//
//					}
//				});


				//alertDialog.show();


			}
  		});
         
//         schedule.setOnClickListener(new OnClickListener() {
//   			@Override
//   			public void onClick(View v) {
//   				Intent intent = new Intent(getActivity(), Schedule.class);
//   				startActivity(intent);
//   				getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//   	    	}
//   		});
         
//         logout.setOnClickListener(new OnClickListener() {
//    			@Override
//    			public void onClick(View v) {
//    				AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create(); //Read Update
//    			    alertDialog.setTitle("Confirmation");
//    			    alertDialog.setMessage(" Are you sure you want to logout?");
//    			    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
//
//    					@Override
//    					public void onClick(DialogInterface dialog, int which) {
//    						// TODO Auto-generated method stub
//    						 //finish();
//    						 Intent i=new Intent(getActivity(), LoginActivity.class);
//    					     startActivity(i);
//							getActivity().finish();
//
//    					     // overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//    						 //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    						// stopService(new Intent(Home.this, LocationServices.class));
//    					}
//    				});
//
//    		       alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
//
//    					@Override
//    					public void onClick(DialogInterface dialog, int which) {
//    						// TODO Auto-generated method stub
//    						  dialog.cancel();
//    					}
//    				});
//
//    		       alertDialog.setCancelable(false);
//    			   alertDialog.show();
//    	    	}
//    		});
        return rootView;
    }
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		
//		buttonNewOrder.setBackgroundColor(Color.parseColor("#414042"));
//		buttonPreviousOrder.setBackgroundColor(Color.parseColor("#414042"));
//		
//		buttonNoOrder.setBackgroundColor(Color.parseColor("#414042"));
//		buttonReturnOrder.setBackgroundColor(Color.parseColor("#414042"));
	}

//	public class LoadRetailersAsyncTask extends AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//		private Context context;
//		
//		public LoadRetailersAsyncTask(Activity activity) {
//			this.activity = activity;
//			context=activity;
//			dialog = new ProgressDialog(context);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Loading Retailers");
//			this.dialog.show();
//			listRetailer.clear();
//			listRetailer.add("Select Retailer");
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			 try {
//				/*//dataRetailers=(ArrayList<DatabaseModel>) myDbHelper.loadRetailers(spinner2.getSelectedItem().toString());
//				//int i=1;
//					for (Iterator iterator = dataRetailers.iterator(); iterator.hasNext();) {
//						DatabaseModel databaseModel = (DatabaseModel) iterator.next();
//						//Log.e("DATA", ""+databaseModel);
//						retailersMap.put(""+i, ""+databaseModel.getId());
//						i++;
//						beatID=databaseModel.getParentId();
//						listRetailer.add(databaseModel.getName());
//					}*/
//				 
//				 for(int i = 0 ; i < dataRetaiers.size();i++){
//					 
//					 if (dataRetaiers.get(i).getBeat_id()==1 && SpinnerValue.equalsIgnoreCase("Andheri West")){
//						 listRetailer.add(dataRetaiers.get(i).getShop_name());
//					 }else if (dataRetaiers.get(i).getBeat_id()==2 && SpinnerValue.equalsIgnoreCase("Andheri East")){
//						 listRetailer.add(dataRetaiers.get(i).getShop_name());
//						  
//					 } else if (dataRetaiers.get(i).getBeat_id()==3 && SpinnerValue.equalsIgnoreCase("Vieparle East")){
//						 listRetailer.add(dataRetaiers.get(i).getShop_name());
//					 } else if (dataRetaiers.get(i).getBeat_id()==4 && SpinnerValue.equalsIgnoreCase("Khar West")){
//						 listRetailer.add(dataRetaiers.get(i).getShop_name());
//					 } 
//					 				                         }
//				 
//			} catch (Exception e) {
//				// TODO: handle exception
//			} 
//			
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (dialog.isShowing()) {
//				dialog.dismiss();
//	    	}
//			
//	     	dataAdapterRetailer.notifyDataSetChanged();
//	     	dataAdapterRetailer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//	      	spinner3.setAdapter(dataAdapterRetailer);
//			
//		}
//	}

	
	/*public class LoadIDsAsyncTask extends AsyncTask<Void, Void, Void> {

		*//** progress dialog to show user that the backup is processing. *//*
		private ProgressDialog dialog;
		*//** application context. *//*
		private Activity activity;
		
		private Context context;
		
		SharedPreferences spf;        
        SharedPreferences.Editor editor;  
		

		public LoadIDsAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading Details");
			this.dialog.show();
			spf=activity.getSharedPreferences("SimpleLogic",0); 
			editor=spf.edit();  
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				retailerID= myDbHelper.loadIDs(cityID,beatID,spinner3.getSelectedItem().toString());
					//Log.e("DATA", "retailerID : "+retailerID);
					editor.putInt("RetailerID", retailerID);			        
			        editor.commit();
	
			} catch (Exception e) {
				// TODO: handle exception
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
		}
	}
	*/
	
//	
//	public class LoadMobileEmailIdsAsyncTask extends AsyncTask<Void, Void, Void> {
//
//	/** progress dialog to show user that the backup is processing. */
//	private ProgressDialog dialog;
//	/** application context. */
//	private Activity activity;
//	
//	private Context context;
//	
//	SharedPreferences spf;        
//    SharedPreferences.Editor editor;  
//	
//
//	public LoadMobileEmailIdsAsyncTask(Activity activity) {
//		this.activity = activity;
//		context=activity;
//		dialog = new ProgressDialog(context);
//	}
//
//	@Override
//	protected void onPreExecute() {
//		// TODO Auto-generated method stub
//		super.onPreExecute();
//		this.dialog.setMessage("Loading Details");
//		this.dialog.show();
//		spf=activity.getSharedPreferences("SimpleLogic",0); 
//		editor=spf.edit();  
//		
//
//	}
//
//	@Override
//	protected Void doInBackground(Void... params) {
//		// TODO Auto-generated method stub
//		try {
//			//ArrayList<String> contacts= myDbHelper.loadRetailerMobileEmailIds(cityID,beatID,retailerID);
//				
//				 SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);        
//			        SharedPreferences.Editor editor=spf.edit();        
//			        editor.putString("RetailerMobile", dataRetaiers.get(retailerID).getLandline_no());
//			        editor.putString("RetailerEmailId", dataRetaiers.get(retailerID).getEmail_id());
//			        editor.putString("RetailerCode", dataRetaiers.get(retailerID).getRetailer_code());
//			        //editor.putString("NewRetailer", dataRetaiers.get(retailerID).get);
//			        editor.commit();
//				  
//			        
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		return null;
//	}
//
//	@Override
//	protected void onPostExecute(Void result) {
//		// TODO Auto-generated method stub
//		super.onPostExecute(result);
//		if (dialog.isShowing()) {
//			dialog.dismiss();
//			
//		}
//			
//	}
//}

//	public class LoadLastOrderAsyncTask extends AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//		
//		private Context context;
//		
//		ArrayList<Product> data;
//		
//		String date="";
//		
//		boolean present=false;
//		
//
//		public LoadLastOrderAsyncTask(Activity activity) {
//			this.activity = activity;
//			context=activity;
//			dialog = new ProgressDialog(context);
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Loading Previous Order Details..");
//			this.dialog.show();
//			
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//				
//				try { 
//					
//					//Log.e("DATA", "cityID,beatID,retailerID"+cityID+","+beatID+","+retailerID);
//					 data=myDbHelper.loadPreviousOrder(cityID,beatID,retailerID);
//					 
//					 
//					 if (data.size()!=0) {
//						 date=myDbHelper.loadPreviousOrderDate(cityID, beatID, retailerID);
//						 present=true;
//					}
//					 
//				} catch (Exception e) {
//					// TODO: handle exception
//					//Log.e("DATA", "Exception:"+e.getMessage());
//				}
//				
//				
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//			
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(Void result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//			if (dialog.isShowing()) {
//				dialog.dismiss();
//				
//			}
//			
//			present = true; // 
//			
//			if (present) {
//				/*Intent i = new Intent(context,
//						PreviousOrderActivity.class);
//				i.putParcelableArrayListExtra("previousList", data);
//				i.putExtra("date", date);
//
//				SharedPreferences sp = activity.getSharedPreferences(" ", 0);
//
//				i.putExtra("retialer","" + sp.getString("RetailerName", ""));
//				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//				//activity.finish();
//				activity.startActivity(i);*/
//				animatedStartActivityPreviousOrder(rootView,data,date);
//				
//			}
//			
//			else {
//		    		 Toast toast = Toast.makeText(activity,"No Previous Order", Toast.LENGTH_SHORT);
//			  		 toast.setGravity(Gravity.CENTER, 0, 0);
//					 toast.show();
//	     		 }
//      	  }
//	}

	private void animatedStartActivityNewOrder(View rootView) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to implement it.
		
		/*Intent i=new Intent(getActivity().getApplicationContext(), NewOrderFragment.class);
		SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);

		i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		getActivity().overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
		getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		startActivity(i);*/
		
		final Intent intent = new Intent(getActivity().getApplicationContext(), CalendarFragment.class);
		//SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		//intent.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		
		/*getActivity().ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				startActivity(intent);
			}
		});*/
		
		ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(),new ActivitySwitcher.AnimationFinishedListener() {
			
			@Override
			public void onAnimationFinished() {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		});
	}
	
	private void animatedStartActivityPreviousOrder(View rootView,ArrayList<Product> data,String date) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to implement it.
		
		/*Intent i=new Intent(getActivity().getApplicationContext(), NewOrderFragment.class);
		SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);

		i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		getActivity().overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
		getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		startActivity(i);*/
		
		//chk here
		final Intent intent = new Intent(getActivity().getApplicationContext(), PreviousOrderActivity_New.class);//PreviousOrderActivity.class
				
		intent.putParcelableArrayListExtra("previousList", data);
		intent.putExtra("date", date);
		
		
		SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		
		/*getActivity().ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				startActivity(intent);
			}
		});*/
		
		ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(),new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		});
    }
	
	private void animatedStartActivityNoOrder(View rootView) {
		// we only animateOut this activity here.
		// The new activity will animateIn from its onResume() - be sure to implement it.
		
		/*Intent i=new Intent(getActivity().getApplicationContext(), NewOrderFragment.class);
		SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);

		i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		getActivity().overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
		getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		startActivity(i);*/
		
		
		final Intent intent = new Intent(getActivity().getApplicationContext(), NoOrderActivity.class);
		SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		
		/*getActivity().ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
			@Override
			public void onAnimationFinished() {
				startActivity(intent);
			}
		});*/
		
		ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(),new ActivitySwitcher.AnimationFinishedListener() {
		  @Override
			public void onAnimationFinished() {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		});
	}
	
	private void animatedStartActivityReturnOrder(View rootView) {
		   SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);     
		   String newRetailer=spf.getString("NewRetailer", "");
		   
		   //if (newRetailer.equalsIgnoreCase("0")) {
			    SharedPreferences.Editor editor=spf.edit();        
		        editor.putString("RetailerName", "Amar Medical");
		        editor.putInt("RetailerID", retailerID);
		        editor.putInt("CityID", cityID);
		        editor.putInt("BeatID", beatID);
		        editor.commit();
			// TODO Auto-generated method stub
		        rootView.setBackgroundColor(Color.parseColor("#910505"));
			    animatedStartActivityReturnOrder(rootView);
   }
	
	  public void getTargetData()
	    {
			TelephonyManager telephonyManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = telephonyManager.getDeviceId();
			calendarn = Calendar.getInstance();
			year = calendarn.get(Calendar.YEAR);
		    loginDataBaseAdapter=new LoginDataBaseAdapter(getActivity());
			loginDataBaseAdapter=loginDataBaseAdapter.open();
		    dialog = new ProgressDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
	        dialog.setMessage("Please wait Target Sync....");
	        dialog.setTitle("Sales App");
	        dialog.setCancelable(false);
	        dialog.show();

	        try
	        {

	            String  domain = getActivity().getResources().getString(R.string.service_domain);

	            Log.i("volley", "domain: " + domain);
				Log.i("volley", "email: " + Global_Data.GLOvel_USER_EMAIL);
				Log.i("target url", "target url " + domain+"targets?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL);
				JsonObjectRequest jsObjRequest = new JsonObjectRequest(domain+"targets?imei_no="+device_id+"&email="+Global_Data.GLOvel_USER_EMAIL,null, new Response.Listener<JSONObject>() {

	                @Override
	                public void onResponse(JSONObject response) {
	                    Log.i("volley", "response: " + response);
	                    //  Log.i("volley", "response reg Length: " + response.length());


	                    try{


	                        //   for (int a = 0; a < response.length(); a++) {

//	                        JSONObject person = (JSONObject) response.getJSONArray(response);
	//
	                        //   String name = response.getString("result44");

	                        String response_result = "";
	                        if(response.has("result"))
	                        {
	                            response_result = response.getString("result");
	                        }
	                        else
	                        {
	                            response_result = "data";
	                        }


	                        if(response_result.equalsIgnoreCase("User doesn't exist")) {

	                           // Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG).show();

								Toast toast = Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();

	                        }
	                        else {

	                            dbvoc.getDeleteTable("targets");

	                            JSONArray targets = response.getJSONArray("targets");


	                            Log.i("volley", "response reg targets Length: " + targets.length());

	                            Log.d("States", "targets" + targets.toString());

								if(targets.length() <= 0)
								{
									dialog.dismiss();
									//Toast.makeText(getActivity(), "Target not found.", Toast.LENGTH_LONG).show();

									Toast toast = Toast.makeText(getActivity(), "Target not found.", Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
								}
								else
								{
									for (int i = 0; i < targets.length(); i++) {

										JSONObject jsonObject = targets.getJSONObject(i);

//	                                loginDataBaseAdapter.insertTargets(jsonObject.getString("code"),"", jsonObject.getString("user_id"),
//	                                		 jsonObject.getString("year"), jsonObject.getString("month"), jsonObject.getString("target"),
//	                                		 jsonObject.getString("achieved"), jsonObject.getString("created_at"), jsonObject.getString("update_at"));
										loginDataBaseAdapter.insertTargets("","", "",
												jsonObject.getString("year"), jsonObject.getString("month"), jsonObject.getString("target"),
												jsonObject.getString("achieved"), "", "");

										if(jsonObject.getString("year").equalsIgnoreCase(String.valueOf(year)))
										{
											if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("target").toString()))
											{
												if(!jsonObject.getString("target").equalsIgnoreCase("null") && !jsonObject.getString("target").equalsIgnoreCase(null) & !jsonObject.getString("target").equalsIgnoreCase("") & !jsonObject.getString("target").equalsIgnoreCase(" "))
												{
													t_total +=Float.valueOf(jsonObject.getString("target").toString());
												}
												else
												{
													t_total +=Float.valueOf("0.0");
												}
											}

											if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(jsonObject.getString("achieved").toString()))
											{
												if(!jsonObject.getString("achieved").equalsIgnoreCase("null") && !jsonObject.getString("achieved").equalsIgnoreCase(null) & !jsonObject.getString("achieved").equalsIgnoreCase("") & !jsonObject.getString("achieved").equalsIgnoreCase(" "))
												{
													achived_total +=Float.valueOf(jsonObject.getString("achieved").toString());
												}
												else
												{
													achived_total +=Float.valueOf("0.0");
												}
											}
										}

									}


									SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);
									SharedPreferences.Editor editor=spf.edit();
									//editor.putString("UserID", "admin");
									//editor.putString("pwd", "test");
									editor.putFloat("Target", t_total);
									editor.putFloat("Achived", achived_total);
									//editor.putString("SimID", simSerial);
									editor.commit();

									Intent intent = new Intent(getActivity(), Target.class);
									startActivity(intent);
									getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
									getActivity().finish();
									dialog.dismiss();
								}

	                       dialog.dismiss();

	                            //finish();

	                        }


	                        // }

	                        // output.setText(data);
	                    }catch(JSONException e){e.printStackTrace(); dialog.dismiss(); }


	                    dialog.dismiss();
	                }
	            }, new Response.ErrorListener() {
	                @Override
	                public void onErrorResponse(VolleyError error) {
	                    Log.i("volley", "error: " + error);
	                   // Toast.makeText(getActivity(), "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();

						Toast toast = Toast.makeText(getActivity(), "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();

	                    dialog.dismiss();

	                }
	            });

	            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
	            // queue.add(jsObjRequest);
	            jsObjRequest.setShouldCache(false);
	            int socketTimeout = 200000;//30 seconds - change to what you want
	            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	            jsObjRequest.setRetryPolicy(policy);
	            requestQueue.add(jsObjRequest);

	        } catch (Exception e) {
	            e.printStackTrace();
	            dialog.dismiss();



	        }
	    }




	 
}
