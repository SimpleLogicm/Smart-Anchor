package com.anchor.slidingmenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anchor.activities.AppLocationManager;
import com.anchor.activities.Check_Null_Value;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.LoginDataBaseAdapter;
import com.anchor.activities.MainActivity;
import com.anchor.activities.PlayService_Location;
import com.anchor.activities.R;
import com.anchor.model.Beat;
import com.anchor.model.City;
import com.anchor.model.Retailer;
import com.anchor.model.State;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRetailerFragment extends Fragment implements OnItemSelectedListener{
	
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	private String Current_Date = "";
//	DatabaseHandler myDbHelper;
	Button buttonAddRetailerSave,buttonAddRetailerCancel;
	Spinner spinner1,spinner2,spinner3;
	private String CAT_ID;
	ArrayList<State> dataStates = new ArrayList<State>();
	ArrayList<City> dataCities = new ArrayList<City>();
    ArrayList<Beat> dataBeats = new ArrayList<Beat>();
    ArrayList<Retailer> dataRetaiers = new ArrayList<Retailer>();
    
     String F_CITY_ID = "";
     String F_BEAT_ID = "";
     String F_STATE_ID = "";
    
	List<String> listState,listCity,listBeat;
	 static int cityID,beatID,retailerID;
	 int data_stateid,data_cityID,data_beatID,userID;

	 ArrayAdapter<String> state_Adapter,city_Adapter,beat_Adapter;
	 EditText editTextRetailerName,editTextStoreName,editTextAddress,editTextStreet,editTextContatNo1,editTextContatMail,editTextContatVatINId,editTextPin,editTextLandMark;
	 Retailer r;
	 
	 ArrayList<Retailer> newRetailer = new ArrayList<Retailer>(); 
	public AddRetailerFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		 DataBaseHelper dbvoc = new DataBaseHelper(this.getActivity());
		
		 loginDataBaseAdapter=new LoginDataBaseAdapter(this.getActivity());
	     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
	     Calendar c = Calendar.getInstance();
         SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
         String strDate = sdf.format(c.getTime());
         Current_Date = sdf.format(c.getTime());
	     
        View rootView = inflater.inflate(R.layout.fragment_add_retailer, container, false);
        r=new Retailer();
       // TextView welcomeUser=(TextView)rootView.findViewById(R.id.txtWelcomeUser);
        editTextRetailerName=(EditText)rootView.findViewById(R.id.editTextRetailerName);
        editTextStoreName=(EditText)rootView.findViewById(R.id.editTextStoreName);
        editTextAddress=(EditText)rootView.findViewById(R.id.editTextAddress);
        editTextLandMark=(EditText)rootView.findViewById(R.id.editTextLandMark);
        editTextPin=(EditText)rootView.findViewById(R.id.editTextPin);
        editTextStreet=(EditText)rootView.findViewById(R.id.editTextStreet);
        editTextContatVatINId=(EditText)rootView.findViewById(R.id.editTextContatVatINId);
        editTextContatNo1=(EditText)rootView.findViewById(R.id.editTextContatNo1);
        editTextContatMail=(EditText)rootView.findViewById(R.id.editTextContatMail);
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", 0);

        /*data_stateid=sp.getInt("StateID", 0);
        data_cityID=sp.getInt("cityID", 0);
        data_beatID=sp.getInt("beatID", 0);
        userID=sp.getInt("UserID", 0);*/
        
//        data_stateid=sp.getInt("StateID", 0);
//        data_cityID=sp.getInt("cityID", 0);
//        data_beatID=sp.getInt("beatID", 0);
//        userID=sp.getInt("UserID", 0);
		
        /*listState = new ArrayList<String>();
        listState.add("Select State");
        listState.add("Maharashtra");
        state_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listState);
        state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3=(Spinner)rootView.findViewById(R.id.spnState);
    	spinner3.setAdapter(state_Adapter);*/
        
         // create a instance of SQLite Database
	    
	     
        
        listState = new ArrayList<String>();
        listState.add("Select State");
   	   // myDbHelper = new DatabaseHandler(getActivity());
	    //myDbHelper.openDataBase();
        

//        State state = new State("MAHARASTRA", "1", "N", "SC001");
//        dataStates.add(state);
//        
//        
//        
//        for (int i = 0; i < dataStates.size(); i++) {
//        	listState.add(dataStates.get(i).getDesc());
//		}
        
	    //dataStates=(ArrayList<DatabaseModel>) myDbHelper.loadStates(data_stateid);
	  /*  for (Iterator iterator = dataStates.iterator(); iterator.hasNext();) {
			DatabaseModel databaseModel = (DatabaseModel) iterator.next();
			//Log.e("DATA", ""+databaseModel);
			listState.add(databaseModel.getName());
		}*/
    	
	    state_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listState);
        state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3=(Spinner)rootView.findViewById(R.id.spnState);
    	spinner3.setAdapter(state_Adapter);
    	spinner3.setOnItemSelectedListener(this);
    	
    	
    	
        listCity = new ArrayList<String>();
        listCity.add("Select City");
   	    //myDbHelper = new DatabaseHandler(getActivity());
	   // myDbHelper.openDataBase();


//        City city = new City("Mumbai", "2", "N", "6", "CC002");
//        dataCities.add(city);
        
       /* dataCities=(ArrayList<DatabaseModel>) myDbHelper.loadCities(data_cityID);
	    for (Iterator iterator = dataCities.iterator(); iterator.hasNext();) {
			DatabaseModel databaseModel = (DatabaseModel) iterator.next();
			//Log.e("DATA", ""+databaseModel);
			 listCity.add(databaseModel.getName());
		}*/
        
//        for (int i = 0; i < dataCities.size(); i++) {
//        	listCity.add(dataCities.get(i).getDesc());
//		}
        
	    city_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listCity);
	    city_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner2=(Spinner)rootView.findViewById(R.id.spnCity);
    	spinner2.setAdapter(city_Adapter);
    	spinner2.setOnItemSelectedListener(this);
    	
    	
    	listBeat = new ArrayList<String>();
    	listBeat.add("Select Beat");
   	    //myDbHelper = new DatabaseHandler(getActivity());
	   // myDbHelper.openDataBase();
	    //dataBeats=(ArrayList<DatabaseModel>) myDbHelper.loadBeats(data_cityID,data_beatID);
    	
    	 Beat beat1 = new Beat("1", "Andheri West", "12", "26", "Y");
         Beat beat2 = new Beat("2", "Andheri East", "12", "26", "Y");
         Beat beat3 = new Beat("3", "Vieparle East", "12", "26", "Y");
        
		//        dataBeats.add(beat1); 
		//        dataBeats.add(beat2);
		//        dataBeats.add(beat3);

        List<Local_Data> contacts2 = dbvoc.getAllBeats();
    	//results.add("Select Beat");
         for (Local_Data cn : contacts2) 
         {
        	 String str_beat = ""+cn.getStateName();
        	 //Global_Data.local_pwd = ""+cn.getPwd();
       	
        	 listBeat.add(str_beat);
       
       	 }

          
         dbvoc.close();
        
    	
	   /* for (Iterator iterator = dataBeats.iterator(); iterator.hasNext();) {
			DatabaseModel databaseModel = (DatabaseModel) iterator.next();
			//Log.e("DATA", ""+databaseModel);
			listBeat.add(databaseModel.getName());
		}*/
        
	    beat_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listBeat);
	    beat_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner1=(Spinner)rootView.findViewById(R.id.spnBeat);
    	spinner1.setAdapter(beat_Adapter);
    	spinner1.setOnItemSelectedListener(this);
    	
    	
    	
    	
      // welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
        buttonAddRetailerSave=(Button) rootView.findViewById(R.id.buttonAddRetailerSave);
        
        buttonAddRetailerSave.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
			        b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			        //down event
			        b.setBackgroundColor(Color.parseColor("#910505"));

					// TODO Auto-generated method stub
			        boolean validEmail=true;
			        String PhoneNo = editTextContatNo1.getText().toString();
					String pin = editTextPin.getText().toString();
					String vtn = editTextContatVatINId.getText().toString();
			        String Regex = "[^\\d]";
			        String PhoneDigits = PhoneNo.replaceAll(Regex, "");
			        
			        if (editTextRetailerName.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Retailer Name", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
			        else if (editTextStoreName.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Store Name", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
			        else if (editTextAddress.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Address", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
			        else if (editTextStreet.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Street", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
			        else if (editTextLandMark.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter LandMark", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
			        
					
			        else if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {
						Toast toast = Toast.makeText(getActivity(),"Please select beat", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
			        else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
						Toast toast = Toast.makeText(getActivity(),"Please select city", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
			        else if (spinner3.getSelectedItem().toString().equalsIgnoreCase("Select State")) {
						Toast toast = Toast.makeText(getActivity(),"Please select state", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
			        
			        else if (editTextPin.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Pin", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					else if (pin.length()!=6) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Valid Pin", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
					
					else if (editTextContatNo1.getText().length()==0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Contact Number", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
				  else if (PhoneNo.length()!=10) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Valid Contact Number", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
				 else  if (!validate(editTextContatMail.getText().toString().trim())) {

						validEmail=false;
						Toast toast = Toast.makeText(getActivity(),"Please Enter Valid Email Address", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
						
					
			        
			        else if (editTextContatVatINId.getText().length() == 0) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter VATIN Number", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}

					else if (vtn.length()!=11) {
						Toast toast = Toast.makeText(getActivity(),"Please Enter Valid VATIN Number", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
			        
			        
			        
			        
					else {
						
						if (validEmail) {
							
							 Retailer retailer10 = new Retailer(9, 12, 26, 1, "200000", "Rajesh1", "Kamdhenu", "Address9", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
							 dataRetaiers.add(retailer10);
							 
							    r.setRetailer_name(editTextRetailerName.getText().toString());
								r.setShop_name(editTextStoreName.getText().toString());
								r.setAddress(editTextAddress.getText().length()==0 ? "NA" :editTextAddress.getText().toString());
								r.setStreet(editTextStreet.getText().length()==0 ? "NA" :editTextStreet.getText().toString());
								
								r.setLandmark(editTextLandMark.getText().length()==0 ? "NA" :editTextLandMark.getText().toString());
								
								r.setPincode(editTextPin.getText().length()==0 ? "NA" :editTextPin.getText().toString());
								
								r.setBeat_id(data_beatID);
								r.setCity_id(data_cityID);
								r.setState_id(data_stateid);
								r.setVatin(editTextContatVatINId.getText().toString());
								r.setLandline_no(editTextContatNo1.getText().toString());
								r.setEmail_id(editTextContatMail.getText().length()==0 ? "NA" :editTextContatMail.getText().toString());
								
								
								
								
								AlertDialog alertDialog = new AlertDialog.Builder(AddRetailerFragment.this.getActivity()).create(); //Read Update
							    alertDialog.setTitle("Confirmation");
							    alertDialog.setMessage("Do you wish to continue?"); 
							    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub

										/*FragmentManager fragmentManager = getFragmentManager();
										fragmentManager.beginTransaction()
												.replace(R.id.frame_container, new HomeFragment()).commit();
										*/
										
										/*MainActivity activity=(MainActivity) getActivity();
										activity.displayView(0);*/
										//Toast.makeText(getActivity(), "Retailer Added", Toast.LENGTH_SHORT).show();
										
										
										newRetailer.add(r);
										InsertAddRetailerAsyncTask insertAddRetailerAsyncTask=new InsertAddRetailerAsyncTask(getActivity());
										insertAddRetailerAsyncTask.execute();
										
									}
								});

				               alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										  dialog.cancel();
									}
								});
							  

							    alertDialog.show(); 
						}
						
						
					}
					
					

				
			        
			    }
				return false;
			}
		});
       /* buttonAddRetailerSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {}
		});*/
        
        buttonAddRetailerCancel=(Button) rootView.findViewById(R.id.buttonAddRetailerCancel);
        buttonAddRetailerCancel.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
			        b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }
			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			        //down event
			        b.setBackgroundColor(Color.parseColor("#910505"));

					// TODO Auto-generated method stub
					
					
					AlertDialog alertDialog = new AlertDialog.Builder(AddRetailerFragment.this.getActivity()).create(); //Read Update
				    alertDialog.setTitle("Warning");
				    alertDialog.setMessage("This operation will discard all the values entered, Are you sure you want to continue?"); 
				    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.frame_container, new HomeFragment()).commit();
							MainActivity activity=(MainActivity) getActivity();
							activity.displayView(0);
						}
					});

	               alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							  dialog.cancel();
						}
					});
				  

				    alertDialog.show(); 
					

				
			    }
				return false;
			}
		});
       /* buttonAddRetailerCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {}
		});*/
         
        return rootView;
    }

	@Override
	public void onResume() {

		super.onResume();

		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

					AlertDialog alertDialog = new AlertDialog.Builder(AddRetailerFragment.this.getActivity()).create(); //Read Update
					alertDialog.setTitle("Warning");
					alertDialog.setMessage("This operation will discard all the values entered, Are you sure you want to continue?");
					alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub

							FragmentManager fragmentManager = getFragmentManager();
							fragmentManager.beginTransaction()
									.replace(R.id.frame_container, new HomeFragment()).commit();
							MainActivity activity=(MainActivity) getActivity();
							activity.displayView(0);
						}
					});

					alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
						}
					});


					alertDialog.show();

					return true;

				}

				return false;
			}
		});
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
			
		    
			 DataBaseHelper dbvoc = new DataBaseHelper(this.getActivity());
				
			 loginDataBaseAdapter=new LoginDataBaseAdapter(this.getActivity());
		     loginDataBaseAdapter=loginDataBaseAdapter.open();
	     
			 Spinner spinner = (Spinner) parent;
		     if(spinner.getId() == R.id.spnBeat)
		     {		
		    	
		    	   if (spinner1.getSelectedItem().toString() == "Select Beat")
		    	   {	
		    		     listCity.clear();
		    		     listCity.add("Select City");
		    		     city_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listCity);
		    			 city_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    			 spinner2.setAdapter(city_Adapter);
		    			 
		    			 listState.clear();
		    			 listState.add("Select State");
		    			 state_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listState);
		    			 state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    			 spinner3.setAdapter(state_Adapter);
		    			

		    	   }
		    	   else
		    	   {	   
			    	    String items = spinner1.getSelectedItem().toString();
		                String C_ID = "";
		                Log.i("Selected item : ", items);
		                
		                List<Local_Data> contacts = dbvoc.getBeats_CITYID(spinner1.getSelectedItem().toString());      
		                for (Local_Data cn : contacts) 
		                {
		              	     
		                	C_ID = cn.getStateName();
		                	CAT_ID = cn.getStateName();
		                	F_CITY_ID =  cn.getStateName();
		                	F_BEAT_ID =  cn.get_category_id();
		      	        }
		                
		                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(C_ID))
		                {
		                	List<Local_Data> contacts2 = dbvoc.getCity(C_ID);      
		                    for (Local_Data cn : contacts2) 
		                    {
		                  	     
		                    	listCity.add(cn.getStateName());
		                    }
		                    	city_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listCity);
		                    	city_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		                    	spinner2.setAdapter(city_Adapter);
		                }
		    	   } 
		    	   dbvoc.close();

		      }
		     else
	    	 if(spinner.getId() == R.id.spnCity)
		     {		
		    	
		    	   if (spinner2.getSelectedItem().toString() == "Select City")
		    	   {	
		    		     listState.clear();
		    			 listState.add("Select State");
		    			 state_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listState);
		    			 state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    			 spinner3.setAdapter(state_Adapter);
		    			

		    	   }
		    	   else
		    	   {	   
			    	    String items = spinner1.getSelectedItem().toString();
		                String C_ID = "";
		                Log.i("Selected item : ", items);
		                
		                List<Local_Data> contacts = dbvoc.getCityByStateID(spinner2.getSelectedItem().toString());      
		                for (Local_Data cn : contacts) 
		                {
		              	     
		                	C_ID = cn.getcity_id();
		                	CAT_ID = cn.getcity_id();
		                	F_STATE_ID = cn.getcity_id();
		      	        }
		                
		                if(Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(C_ID))
		                {
		                	List<Local_Data> contacts2 = dbvoc.getstate(C_ID);      
		                    for (Local_Data cn : contacts2) 
		                    {
		                  	     
		                    	listState.add(cn.getStateName());
		                    }
		                     state_Adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listState);
			    			 state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    			 spinner3.setAdapter(state_Adapter);
		                }
		    	   } 

		     } 	 
		   
		     dbvoc.close();	 
	       
	}
	
	
	
	public class InsertAddRetailerAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean sucess;
		float f=0.00f;

		public InsertAddRetailerAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Adding Retailer...Please wait");
			this.dialog.setCancelable(false);
			this.dialog.show();
			

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				//dataProducts=(ArrayList<DatabaseProductModel>) myDbHelper.ger(spnCategory.getSelectedItem().toString());
					if (data_stateid==0||data_cityID==0||data_beatID==0) {
						
						//loginDataBaseAdapter.insertRetailers("1", "1", "1", editTextRetailerName.getText().toString(), editTextStoreName.getText().toString() , editTextAddress.getText().toString(), editTextStreet.getText().toString(), editTextLandMark.getText().toString(),F_STATE_ID,F_CITY_ID,F_BEAT_ID,editTextPin.getText().toString(), " ", editTextContatNo1.getText().toString(), editTextContatMail.getText().toString(), "active", "user1", "user1", editTextContatVatINId.getText().toString(),Current_Date, Current_Date,"1");

						try
						{
							AppLocationManager appLocationManager = new AppLocationManager(getActivity());
							Log.d("Class LAT LOG","Class LAT LOG"+appLocationManager.getLatitude()+" "+ appLocationManager.getLongitude());
							Log.d("Service LAT LOG","Service LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

							PlayService_Location PlayServiceManager = new PlayService_Location(getActivity());

							if(PlayServiceManager.checkPlayServices(getActivity()))
							{
								Log.d("Play LAT LOG","Play LAT LOG"+Global_Data.GLOvel_LATITUDE+" "+ Global_Data.GLOvel_LONGITUDE);

							}
							else
							if(!String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase("null") && !String.valueOf(appLocationManager.getLatitude()).equalsIgnoreCase(null) && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null)  && !String.valueOf(appLocationManager.getLongitude()).equalsIgnoreCase(null))
							{
								Global_Data.GLOvel_LATITUDE = String.valueOf(appLocationManager.getLatitude());
								Global_Data.GLOvel_LONGITUDE = String.valueOf(appLocationManager.getLongitude());
							}

						}catch(Exception ex){ex.printStackTrace();}
						
						final SecureRandom random = new SecureRandom();

						getActivity().runOnUiThread(new Runnable() {
							public void run() {
								loginDataBaseAdapter.insertCustMaster(new BigInteger(130, random).toString(32),editTextRetailerName.getText().toString(), editTextStoreName.getText().toString(),editTextAddress.getText().toString(),
										editTextStreet.getText().toString(),editTextLandMark.getText().toString(), editTextPin.getText().toString(),"", editTextContatNo1.getText().toString()
										, editTextContatMail.getText().toString(), "active", F_STATE_ID,F_CITY_ID,F_BEAT_ID,editTextContatVatINId.getText().toString(),Global_Data.GLOvel_USER_EMAIL,Current_Date,Global_Data.GLOvel_LATITUDE,Global_Data.GLOvel_LONGITUDE);
							}
						});



						Calendar c = Calendar.getInstance();
						System.out.println("Current time =&gt; "+c.getTime());

						SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						final String formattedDate = df.format(c.getTime());

						String gaddress = "";
						try {
							if (Global_Data.address.equalsIgnoreCase("null")) {
								gaddress = "";
							} else {
								gaddress = Global_Data.address;
							}
						}catch(Exception ex){ex.printStackTrace();}

//						String sms_body = "Dear " + Global_Data.USER_MANAGER_NAME + " ,"  +"\n"+" A new " + editTextRetailerName.getText().toString() + " at " + editTextAddress.getText().toString() + "  has been identified at " + formattedDate +"\n\n"+ " Thank you." +"\n"+ " " + Global_Data.USER_FIRST_NAME + " " + Global_Data.USER_LAST_NAME +"\n"+ " " +gaddress;
//
//						if(!Global_Data.cus_MAnager_mobile.equalsIgnoreCase(null) && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("null")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase("")  && !Global_Data.cus_MAnager_mobile.equalsIgnoreCase(" "))
//						{
//					//		Global_Data.sendSMS(Global_Data.cus_MAnager_mobile,sms_body, getActivity());
//							// mobile_numbers.add(Global_Data.cus_MAnager_mobile);
//						}
						
						//loginDataBaseAdapter.close();
						/*data_stateid=myDbHelper.getStateID(spinner3.getSelectedItem().toString());
						data_cityID=myDbHelper.getCityID(spinner2.getSelectedItem().toString());
						data_beatID=myDbHelper.getBeatID(spinner1.getSelectedItem().toString());
						
						r.setBeat_id(data_beatID);
						r.setCity_id(data_cityID);
						r.setState_id(data_stateid);*/
					}
					//sucess=myDbHelper.insertAddRetailer(r,getDateTime(),userID);
					sucess= true;
					
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
			 if (sucess) {
				 editTextRetailerName.setText("");
			        editTextStoreName.setText("");
			        editTextAddress.setText("");
			        editTextStreet.setText("");
			        editTextLandMark.setText("");
			        editTextPin.setText("");
			        editTextContatVatINId.setText("");
			        editTextContatNo1.setText("");
			        editTextContatMail.setText("");
			        
			        editTextRetailerName.setHint("Retailer Name");
			        editTextStoreName.setHint("Store Name");
			        editTextAddress.setHint("Address");
			        editTextStreet.setHint("Street");
			        editTextLandMark.setHint("Landmark");
			        editTextPin.setHint("PIN");
			        editTextContatVatINId.setHint("VATIN");
			        editTextContatNo1.setHint("Contact Mobile Number");
			        editTextContatMail.setHint("Contact Email ID");
			        beat_Adapter.notifyDataSetChanged();
			        beat_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner1.setAdapter(beat_Adapter);
			        city_Adapter.notifyDataSetChanged();
			        city_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner2.setAdapter(city_Adapter);
					state_Adapter.notifyDataSetChanged();
					state_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner3.setAdapter(state_Adapter);








					
					Toast toast = Toast.makeText(getActivity(),"Retailer Added", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();

				 Intent a = new Intent(getActivity(),MainActivity.class);
				 startActivity(a);
				 getActivity().finish();
			}
			 
			 else {
				 Toast toast = Toast.makeText(getActivity(),"Retailer Already Exists", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
			}
		   
			 	
			
			
		}
	}
	
	
	private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
}
	
	public boolean validate(final String hex) {
		String EMAIL_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(hex);
		return matcher.matches();
 
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*public class InsertRetailerMasterAsyncTask extends AsyncTask<Void, Void, Void> {

		*//** progress dialog to show user that the backup is processing. *//*
		private ProgressDialog dialog;
		*//** application context. *//*
		private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;
		float f=0.00f;

		public InsertRetailerMasterAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Adding to Retailer Master...Please wait");
			this.dialog.show();
			

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				//dataProducts=(ArrayList<DatabaseProductModel>) myDbHelper.ger(spnCategory.getSelectedItem().toString());
					
				 
				
					
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



}
