package com.anchor.slidingmenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.anchor.activities.NewOrderActivity;
import com.anchor.activities.NoOrderActivity;
import com.anchor.activities.PreviousOrderActivity_New;
import com.anchor.activities.R;
import com.anchor.activities.ReturnOrderActivity;
import com.anchor.animation.ActivitySwitcher;
import com.anchor.model.Beat;
import com.anchor.model.City;
import com.anchor.model.Product;
import com.anchor.model.Retailer;
import com.anchor.model.State;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class HomeFragment extends Fragment {
	String SpinnerValue = "";
	Spinner spinner1,spinner2,spinner3;
	Button btnNewOrder;
	Button buttonNewOrder,buttonPreviousOrder,buttonNoOrder,buttonReturnOrder;

	//ArrayList<DatabaseModel> dataCities,dataBeats,dataRetailers,dataIDs;
	 List<String> listBeat;
	 List<String> listRetailer ;
	 ArrayAdapter<String> dataAdapterBeat;
	 ArrayAdapter<String> dataAdapterRetailer;
	 HashMap<String, String> retailersMap;
	 static int cityID,beatID,retailerID;
	 int data_stateid,data_cityID;
	 String data_beatID;
	 View rootView;
	 
	 ArrayList<State> dataStates = new ArrayList<State>();
     ArrayList<City> dataCities = new ArrayList<City>();
     ArrayList<Beat> dataBeats = new ArrayList<Beat>();
    public static  ArrayList<Retailer> dataRetaiers = new ArrayList<Retailer>();
	 
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //retailersMap=new HashMap<String, String>();
    
        // This is for Adding Stait Record in database
        
        
     // Fake Variables
//        String USER = "<Users><User><id>1</id><name>bharats</name><EmailId>bharat.shah@zodhita.com</EmailId><MobNo>9821691155</MobNo><Date_of_Joining>02/07/2012 00:00:00</Date_of_Joining><password>YWRtaW4=</password><StateID>12</StateID><CityID>26</CityID><BeatID>1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16</BeatID><RoleID>2</RoleID><IMEINo>359778023609074</IMEINo><DeviceID>1</DeviceID><First_Name>Bharat</First_Name><Last_Name>Shah</Last_Name><status>Active</status></User></Users>";
//        String STATE = "<States><State><code>SC001</code><id>1</id><desc>ANDHRA PRADESH</desc><status>N</status></State></States>";
//        String CITY = "<Cities><City><code>CC002</code><id>2</id><desc>Gurgaon</desc><stateid>6</stateid><status>N</status></City></Cities>";
//        String BEATS = "<Beats><Beat><code>B001</code><id>1</id><desc>Crowford Market</desc><cityid>26</cityid><stateid>12</stateid><status>Y</status></Beat></Beats>";
//        String RETAILER = "<Retailers><Retailer><code>R001</code><id>1</id><Name>XYZ</Name><Email>bharat.shah@zodhita.com</Email><ShopName>Rr Beauty World </ShopName><Address>Shop No: 26-29 Barla Building 1 Flr Carnac Road Crowford Market Mumbai 400003</Address><Street></Street><Landmark></Landmark><StateID>12</StateID><CityID>26</CityID><BeatID>1</BeatID><PinCode>100000</PinCode><LandLineNo>022-66540974</LandLineNo><MobileNo>9821691155</MobileNo><status>Y</status></Retailer></Retailers>";
//        String CATEGORIES = "<Categories><Category><code>C001</code><id>1</id><desc>AP Deodorants</desc><parentid></parentid><status>Active</status></Category></Categories>";
//        String PRODUCTS = "<Products><Product><code>P001</code><id>1</id><desc>RHYTHM APD</desc><status>Active</status><scheme>1</scheme><categoryid>1</categoryid><parentid></parentid><ProductVariant><VariantId>1</VariantId><Specs>150ML</Specs><MRP>210.00</MRP><RP>178.50</RP><unit>PCS</unit></ProductVariant></Product></Products>";
//        String SCHEMA = "<Schemes><Scheme><code>S001</code><id>1</id><desc>Buy 6 and Get 2 Free</desc><status>Y</status></Scheme></Schemes>";
//        String REASON = "<Reasons><Reason><code>RC001</code><id>1</id><desc>Seasonality</desc><status>Y</status></Reason></Reasons>";
//        String DISTRIUTOR = "<Distributors><Distributor><code>DR001</code><id>7</id><Name>XYZ</Name><Email>bharat.shah@zodhita.com</Email><ShopName>Payal Sales Corporation</ShopName><Address>21/33,chavgale wala chal,mugbhat.X.lane,girgoum thakur dwar.mumbai-40004.</Address><Street></Street><Landmark></Landmark><StateID>12</StateID><CityID>26</CityID><BeatID></BeatID><PinCode>100000</PinCode><LandLineNo></LandLineNo><MobileNo>9769744189</MobileNo><status>Y</status></Distributor></Distributors>";


        City city = new City("Mumbai", "2", "N", "6", "CC002");
        dataCities.add(city);
        
        State state = new State("MAHARASTRA", "1", "N", "SC001");
        dataStates.add(state);
        
        // Add Beat 
        Beat beat1 = new Beat("1", "Andheri West", "12", "26", "Y");
        Beat beat2 = new Beat("2", "Andheri East", "12", "26", "Y");
        Beat beat3 = new Beat("3", "Vieparle East", "12", "26", "Y");
       // Beat beat4 = new Beat("4", "Vieparle West", "12", "26", "Y");
        //Beat beat5 = new Beat("5", "Andheri West", "12", "26", "Y");
        Beat beat6 = new Beat("4", "Khar West", "12", "26", "Y");
       // Beat beat7 = new Beat("7", "Andheri West", "12", "26", "Y");
       // Beat beat8 = new Beat("8", "Vieparle East", "12", "26", "Y");
        //Beat beat9 = new Beat("9", "Andheri West", "12", "26", "Y");
       // Beat beat10 = new Beat("10", "Khar West", "12", "26", "Y");
        //Beat beat11 = new Beat("11", "Andheri West", "12", "26", "Y");
        
        dataBeats.add(beat1); 
        dataBeats.add(beat2);
        dataBeats.add(beat3);
        //dataBeats.add(beat4);
        //dataBeats.add(beat5);
        dataBeats.add(beat6);
       // dataBeats.add(beat7);
       // dataBeats.add(beat8);
        //dataBeats.add(beat9);
       // dataBeats.add(beat10);
       // dataBeats.add(beat11);
        
        
        // Add Reatailer
        Retailer retailer1 = new Retailer(0, 12, 26, 1, "100000", "Rajesh", "Memsaab Central", "Shop No: 26-29 Barla Building 1 Flr Carnac Road Crowford Market Mumbai 400003", "", "", "022-66540974", "bharat.shah@zodhita.com", "", "", "Y", "", "R001");
        
        Retailer retailer2 = new Retailer(1, 12, 26, 2, "200000", "Raj", "Life Care Medical", "Address1", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer3 = new Retailer(2, 12, 26, 3, "200000", "Rajesh", "Memsaab", "Address2", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer4 = new Retailer(3, 12, 26, 1, "200000", "Ramesh", "Image Corner", "Address3", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer5 = new Retailer(4, 12, 26,2, "200000", "Rajesh", "Laxmi Collection", "Address4", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer6 = new Retailer(5, 12, 26, 3, "200000", "Raj", "Care-N-Care-Chemist", "Address5", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer7 = new Retailer(6, 12, 26, 1, "200000", "Rajesh", "Libety Gen Store", "Address6", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer8 = new Retailer(7, 12, 26, 2, "200000", "Raj", "Amar Medical", "Address7", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer9 = new Retailer(8, 12, 26, 4, "200000", "Patil", "Aradhana Medical", "Address8", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer10 = new Retailer(9, 12, 26, 1, "200000", "Rajesh", "Kamdhenu", "Address9", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        
        /* Retailer retailer11 = new Retailer(10, 12, 26, 10, "200000", "Raj", "Stawberry", "Address10", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer12 = new Retailer(11, 12, 26, 5, "200000", "Ramesh", "Wellness Forver", "Address11", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer13 = new Retailer(12, 12, 26, 1, "200000", "Raj", "Citizen Collection", "Address12", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer14 = new Retailer(13, 12, 26, 2, "200000", "Raj", "Mahavir S/M", "Address13", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer15 = new Retailer(14, 12, 26, 2, "200000", "Ramesh", "Pratibha Medical", "Address14", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer16 = new Retailer(15, 12, 26, 2, "200000", "Raj", "Super traders", "Address15", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer17 = new Retailer(16, 12, 26, 5, "200000", "Raj", "Madorassa Medical", "Address16", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer18 = new Retailer(17, 12, 26, 2, "200000", "Ramesh", "Shifa Medical", "Address17", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer19 = new Retailer(18, 12, 26, 5, "200000", "Raj", "Prince Medical", "Address18", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer20 = new Retailer(19, 12, 26, 10, "200000", "Raj", "Juvenile", "Address19", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer21 = new Retailer(20, 12, 26, 2, "200000", "Ramesh", "Mohan Medicure", "Address20", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer22 = new Retailer(21, 12, 26, 10, "200000", "Raj", "Roman Super Market", "Address21", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer23 = new Retailer(22, 12, 26, 11, "200000", "Raj", "Mohan Chemist", "Address22", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer24 = new Retailer(23, 12, 26, 10, "200000", "Raj", "Rex Gen Stores", "Address23", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer25 = new Retailer(24, 12, 26, 2, "200000", "Ramesh", "Mohan Medical", "Address24", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer26 = new Retailer(25, 12, 26,5, "200000", "Rajesh", "Right Choice", "Address25", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer27 = new Retailer(26, 12, 26, 2, "200000", "Raj", "Gurukrupa", "Address26", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer28 = new Retailer(27, 12, 26, 5, "200000", "Ramesh", "Roshni Medical", "Address27", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer29 = new Retailer(28, 12, 26, 10, "200000", "Raj", "Ramdev S/M", "Address28", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer30 = new Retailer(29, 12, 26, 2, "200000", "Raj", "New Life Medical", "Address29", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer31 = new Retailer(30, 12, 26, 5, "200000", "Raj", "Dial For Health", "Address30", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer32 = new Retailer(31, 12, 26, 5, "200000", "Raj", "Ganesh Stationary", "Address31", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer33 = new Retailer(32, 12, 26, 2, "200000", "Raj", "Shivraj S/M", "Address32", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer34 = new Retailer(33, 12, 26, 10, "200000", "Raj", "Laxmi S/M", "Address33", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer35 = new Retailer(34, 12, 26, 10, "200000", "Raj", "Surekha Beauty Corner", "Address34", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
        Retailer retailer36 = new Retailer(35, 12, 26, 2, "200000", "Raj", "First Chouce Gallaria", "Address35", "Street", "LandMark1", "1234567890", "abc@gmail.com", "Naresh", "", "Y", "", "R002");
   */
        
        
        if(!dataRetaiers.isEmpty())
        	dataRetaiers.clear();
        
        
        dataRetaiers.add(retailer1);
        dataRetaiers.add(retailer2);
        dataRetaiers.add(retailer3);
        dataRetaiers.add(retailer4);
        dataRetaiers.add(retailer5);
        dataRetaiers.add(retailer6);
        dataRetaiers.add(retailer7);
        dataRetaiers.add(retailer8);
        dataRetaiers.add(retailer9);
        dataRetaiers.add(retailer10);
        /*dataRetaiers.add(retailer11);
        dataRetaiers.add(retailer12);
        dataRetaiers.add(retailer13);
        dataRetaiers.add(retailer14);
        dataRetaiers.add(retailer15);
        dataRetaiers.add(retailer16);
        dataRetaiers.add(retailer17);
        dataRetaiers.add(retailer18);
        dataRetaiers.add(retailer19);
        dataRetaiers.add(retailer20);
        dataRetaiers.add(retailer21);
        dataRetaiers.add(retailer22);
        dataRetaiers.add(retailer23);
        dataRetaiers.add(retailer24);
        dataRetaiers.add(retailer25);
        dataRetaiers.add(retailer26);
        dataRetaiers.add(retailer27);
        dataRetaiers.add(retailer28);
        dataRetaiers.add(retailer29);
        dataRetaiers.add(retailer30);
        dataRetaiers.add(retailer31);
        dataRetaiers.add(retailer32);
        dataRetaiers.add(retailer33);
        dataRetaiers.add(retailer34);
        dataRetaiers.add(retailer35);
        dataRetaiers.add(retailer36);*/
        
        
        
        
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", 0);

         data_stateid=sp.getInt("StateID", 0);
         data_cityID=sp.getInt("cityID", 0);
         data_beatID=sp.getString("userbeatIDs", ""); 
		
		 List<String> list = new ArrayList<String>();
	     list.add("Select City");
	     
	    
		// myDbHelper = new DatabaseHandler(getActivity());
		// myDbHelper.openDataBase();
		 
		// myDbHelper.updateMaster(dataStates, dataCities, dataBeats, dataRetaiers);
		 
		// dataCities=(ArrayList<DatabaseModel>) myDbHelper.loadCities(data_cityID);
		
		for (Iterator iterator = dataCities.iterator(); iterator.hasNext();) {
			City databaseModel = (City) iterator.next();
			//Log.e("DATA", ""+databaseModel);
			 list.add(databaseModel.getDesc());
		}
	
        spinner1 = (Spinner) rootView. findViewById(R.id.spnCity); 
        
    	//list.add("City2");
    	//list.add("City3");
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner1.setAdapter(dataAdapter);
    	
        spinner2 = (Spinner)rootView. findViewById(R.id.spnBeat);
        
        
        //TextView welcomeUser=(TextView)rootView.findViewById(R.id.txtWelcomeUser);
        //question_value.setTypeface(null,Typeface.BOLD);
//        welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
        
        buttonNewOrder=(Button) rootView.findViewById(R.id.buttonNewOrder);
        
        buttonPreviousOrder=(Button) rootView.findViewById(R.id.buttonPreviousOrder);
        buttonNoOrder=(Button) rootView.findViewById(R.id.buttonNoOrder);
        buttonReturnOrder=(Button) rootView.findViewById(R.id.buttonReturnOrder);
        
        buttonNewOrder.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviousOrder.setBackgroundColor(Color.parseColor("#414042"));
        buttonNoOrder.setBackgroundColor(Color.parseColor("#414042"));
        buttonReturnOrder.setBackgroundColor(Color.parseColor("#414042"));
        
        buttonNewOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
					
					Toast toast = Toast.makeText(getActivity(),"Please Select City", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
				
				else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {
					
						Toast toast = Toast.makeText(getActivity(),"Please Select Beat", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}
				
				else if (spinner3.getSelectedItem().toString().equalsIgnoreCase("Select Retailer")) {
					
					Toast toast = Toast.makeText(getActivity(),"Please Select Retailer", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			
				else {
					/*LoadIDsAsyncTask loadIDsAsyncTask=new LoadIDsAsyncTask(getActivity());
					loadIDsAsyncTask.execute();*/
					   SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);        
				        SharedPreferences.Editor editor=spf.edit();        
				        editor.putString("RetailerName", spinner3.getSelectedItem().toString());
				        editor.putInt("RetailerID", retailerID);
				        editor.putInt("CityID", cityID);
				        editor.putInt("BeatID", beatID);
				        editor.commit();
				        
					v.setBackgroundColor(Color.parseColor("#910505"));
					
					animatedStartActivityNewOrder(rootView);

				}
			}
		});
        
        
        buttonPreviousOrder.setOnTouchListener(new OnTouchListener() {
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
			    	 
			    	 
			    	 if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
							Toast toast = Toast.makeText(getActivity(),"Please Select City", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
						
						else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {
								Toast toast = Toast.makeText(getActivity(),"Please Select Beat", Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
						
						else if (spinner3.getSelectedItem().toString().equalsIgnoreCase("Select Retailer")) {
							Toast toast = Toast.makeText(getActivity(),"Please Select Retailer", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
					
						
						else {
							
							/*LoadIDsAsyncTask loadIDsAsyncTask=new LoadIDsAsyncTask(getActivity());
							loadIDsAsyncTask.execute();*/
							   SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);        
						        SharedPreferences.Editor editor=spf.edit();        
						        editor.putString("RetailerName", spinner3.getSelectedItem().toString());
						        editor.putInt("RetailerID", retailerID);
						        editor.putInt("CityID", cityID);				
						        editor.putInt("BeatID", beatID);
						        editor.commit();
						        
						        
							
							//LoadLastOrderAsyncTask loadLastOrderAsyncTask=new LoadLastOrderAsyncTask(getActivity());
							//loadLastOrderAsyncTask.execute();
						}
			    	 return true;
			    }
			    return true;					
			}
		});
       
 
        buttonNoOrder.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*if (spinner1.getSelectedItem().toString()
					.equalsIgnoreCase("Select City")
					|| spinner2.getSelectedItem().toString()
							.equalsIgnoreCase("Select Beat")
					|| spinner3.getSelectedItem().toString()
							.equalsIgnoreCase("Select Retailer")
					) {

//				Toast.makeText(getActivity(),
//						"Please Fill details ", Toast.LENGTH_SHORT).show();
				
				Toast toast = Toast.makeText(getActivity(),"Please Fill details ", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
			}*/
			if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
				Toast toast = Toast.makeText(getActivity(),"Please Select City", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {
					Toast toast = Toast.makeText(getActivity(),"Please Select Beat", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			
			else if (spinner3.getSelectedItem().toString().equalsIgnoreCase("Select Retailer")) {
				Toast toast = Toast.makeText(getActivity(),"Please Select Retailer", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		
			else {
				
				/*LoadIDsAsyncTask loadIDsAsyncTask=new LoadIDsAsyncTask(getActivity());
				loadIDsAsyncTask.execute();*/
				
				   SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);        
				   String newRetailer=spf.getString("NewRetailer", "");
				   
				  // if (newRetailer.equalsIgnoreCase("0")) {
					   SharedPreferences.Editor editor=spf.edit();        
				        editor.putString("RetailerName", spinner3.getSelectedItem().toString());
				        editor.putInt("RetailerID", retailerID);
				        editor.putInt("CityID", cityID);
				        editor.putInt("BeatID", beatID);
				        editor.commit();
				        
					v.setBackgroundColor(Color.parseColor("#910505"));
					animatedStartActivityNoOrder(rootView);
				//}
//				   
//				   else {
//					   Toast toast = Toast.makeText(getActivity(),"Please Sync information", Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//				}
			        
				/*Intent i=new Intent(getActivity().getApplicationContext(), NoOrderFragment.class);
				SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);

				i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);*/
			}
		}
	});
 
 
   buttonReturnOrder.setOnClickListener(new OnClickListener() {
	  @Override
		public void onClick(View v) {
			
			// TODO Auto-generated method stub
			/*if (spinner1.getSelectedItem().toString()
					.equalsIgnoreCase("Select City")
					|| spinner2.getSelectedItem().toString()
							.equalsIgnoreCase("Select Beat")
					|| spinner3.getSelectedItem().toString()
							.equalsIgnoreCase("Select Retailer")
					) {

//				Toast.makeText(getActivity(),
//						"Please Fill details ", Toast.LENGTH_SHORT).show();
				
				Toast toast = Toast.makeText(getActivity(),"Please Fill details ", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
			}*/
			
			if (spinner1.getSelectedItem().toString().equalsIgnoreCase("Select City")) {
				Toast toast = Toast.makeText(getActivity(),"Please Select City", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
			
			else if (spinner2.getSelectedItem().toString().equalsIgnoreCase("Select Beat")) {
					Toast toast = Toast.makeText(getActivity(),"Please Select Beat", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				}
			
			else if (spinner3.getSelectedItem().toString().equalsIgnoreCase("Select Retailer")) {
				Toast toast = Toast.makeText(getActivity(),"Please Select Retailer", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
			}
		
			
			else {
				
				/*LoadIDsAsyncTask loadIDsAsyncTask=new LoadIDsAsyncTask(getActivity());
				loadIDsAsyncTask.execute();*/
				   SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);     
				   
				   String newRetailer=spf.getString("NewRetailer", "");
				   
				   //if (newRetailer.equalsIgnoreCase("0")) {
					   SharedPreferences.Editor editor=spf.edit();        
				        editor.putString("RetailerName", spinner3.getSelectedItem().toString());
				        editor.putInt("RetailerID", retailerID);
				        editor.putInt("CityID", cityID);
				        editor.putInt("BeatID", beatID);
				        editor.commit();
					// TODO Auto-generated method stub
					v.setBackgroundColor(Color.parseColor("#910505"));
					animatedStartActivityReturnOrder(rootView);
					
				/*}
				   
				   else {
					   Toast toast = Toast.makeText(getActivity(),"No orders exist for selected retailer.", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
				}*/
			        

				/*Intent i=new Intent(getActivity().getApplicationContext(), ReturnOrderActivity.class);
				SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);

				i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);*/
			}
		
		}

		
	});

        listBeat = new ArrayList<String>();
        dataAdapterBeat = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listBeat);
        dataAdapterBeat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3 = (Spinner) rootView. findViewById(R.id.spnRetailer);
    	listRetailer = new ArrayList<String>();
    	dataAdapterRetailer = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, listRetailer);
    	dataAdapterRetailer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?>parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				//Toast.makeText(parent.getContext(), "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),Toast.LENGTH_SHORT).show();
				
				
				if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select City")) {
					listBeat.clear();
					listBeat.add("Select Beat");
			     	dataAdapterBeat.notifyDataSetChanged();
			      	spinner2.setAdapter(dataAdapterBeat);
			      	
			      	listRetailer.clear();
			      	listRetailer.add("Select Retailer");
			      	dataAdapterRetailer.notifyDataSetChanged();
			    	dataAdapterRetailer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		     	    spinner3.setAdapter(dataAdapterRetailer);
				}
				
				else {
					LoadBeatsAsyncTask loadBeatsAsyncTask=new LoadBeatsAsyncTask(getActivity());
					loadBeatsAsyncTask.execute();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				
				if (parent.getItemAtPosition(pos).toString().equalsIgnoreCase("Select Beat")) {
					
					listRetailer.clear();
			      	listRetailer.add("Select Retailer");
			      	dataAdapterRetailer.notifyDataSetChanged();
			    	dataAdapterRetailer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		     	    spinner3.setAdapter(dataAdapterRetailer);
		     	
					
				}
				
				else {
					   SpinnerValue = parent.getItemAtPosition(pos).toString();
					   LoadRetailersAsyncTask loadRetailersAsyncTask=new LoadRetailersAsyncTask(getActivity());
					   loadRetailersAsyncTask.execute();
				     }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
      	
     
    	spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View arg1,
					int pos, long arg3) {
				// TODO Auto-generated method stub
				
				//Log.e("DATA", "parent.getSelectedItemId() :"+parent.getSelectedItemId());
				if (parent.getSelectedItemId()!=0) {
					//retailerID=Integer.parseInt(retailersMap.get(""+parent.getSelectedItemId()));
					retailerID = dataRetaiers.get(pos-1).getRetailer_id();
					LoadMobileEmailIdsAsyncTask loadMobileEmailIdsAsyncTask=new LoadMobileEmailIdsAsyncTask(getActivity());
					loadMobileEmailIdsAsyncTask.execute();
				}
				
				//Log.e("DATA", "Retailer ID :"+retailerID);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
      	
        return rootView;
    }
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		buttonNewOrder.setBackgroundColor(Color.parseColor("#414042"));
		buttonPreviousOrder.setBackgroundColor(Color.parseColor("#414042"));
		
		buttonNoOrder.setBackgroundColor(Color.parseColor("#414042"));
		buttonReturnOrder.setBackgroundColor(Color.parseColor("#414042"));
	}
	
	
	public class LoadBeatsAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		private boolean webServiceResponse;

		public LoadBeatsAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading Beats");
			this.dialog.show();
			listBeat.clear();
			listBeat.add("Select Beat");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				//dataBeats=(ArrayList<DatabaseModel>) myDbHelper.loadBeats(spinner1.getSelectedItem().toString(),data_beatID);
					
				for(int i=0;i<dataBeats.size();i++){
					
					listBeat.add(dataBeats.get(i).getBeat_name());
				}
				
				
					/*for (Iterator iterator = dataBeats.iterator(); iterator.hasNext();) {
						DatabaseModel databaseModel = (DatabaseModel) iterator.next();
						//Log.e("DATA", ""+databaseModel);
						cityID=databaseModel.getParentId();
						listBeat.add(databaseModel.getName());
					}*/
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
			
	     	dataAdapterBeat.notifyDataSetChanged();
	      
	      	spinner2.setAdapter(dataAdapterBeat);
			
			
		}
	}


	public class LoadRetailersAsyncTask extends AsyncTask<Void, Void, Void> {

		/** progress dialog to show user that the backup is processing. */
		private ProgressDialog dialog;
		/** application context. */
		private Activity activity;
		
		private Context context;
		
		public LoadRetailersAsyncTask(Activity activity) {
			this.activity = activity;
			context=activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			this.dialog.setMessage("Loading Retailers");
			this.dialog.show();
			listRetailer.clear();
			listRetailer.add("Select Retailer");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			 try {
				/*//dataRetailers=(ArrayList<DatabaseModel>) myDbHelper.loadRetailers(spinner2.getSelectedItem().toString());
				//int i=1;
					for (Iterator iterator = dataRetailers.iterator(); iterator.hasNext();) {
						DatabaseModel databaseModel = (DatabaseModel) iterator.next();
						//Log.e("DATA", ""+databaseModel);
						retailersMap.put(""+i, ""+databaseModel.getId());
						i++;
						beatID=databaseModel.getParentId();
						listRetailer.add(databaseModel.getName());
					}*/
				 
				 
				 
				 for(int i = 0 ; i < dataRetaiers.size();i++){
					 
					 if (dataRetaiers.get(i).getBeat_id()==1 && SpinnerValue.equalsIgnoreCase("Andheri West")){
						 listRetailer.add(dataRetaiers.get(i).getShop_name());
					 }else if (dataRetaiers.get(i).getBeat_id()==2 && SpinnerValue.equalsIgnoreCase("Andheri East")){
						 listRetailer.add(dataRetaiers.get(i).getShop_name());
						  
					 } else if (dataRetaiers.get(i).getBeat_id()==3 && SpinnerValue.equalsIgnoreCase("Vieparle East")){
						 listRetailer.add(dataRetaiers.get(i).getShop_name());
					 } else if (dataRetaiers.get(i).getBeat_id()==4 && SpinnerValue.equalsIgnoreCase("Khar West")){
						 listRetailer.add(dataRetaiers.get(i).getShop_name());
					 } 
					 
				 }
				 
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
			
	     	dataAdapterRetailer.notifyDataSetChanged();
	     	dataAdapterRetailer.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	      	spinner3.setAdapter(dataAdapterRetailer);
			
			
		}
	}

	
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
	
	public class LoadMobileEmailIdsAsyncTask extends AsyncTask<Void, Void, Void> {

	/** progress dialog to show user that the backup is processing. */
	private ProgressDialog dialog;
	/** application context. */
	private Activity activity;
	
	private Context context;
	
	SharedPreferences spf;        
    SharedPreferences.Editor editor;  
	

	public LoadMobileEmailIdsAsyncTask(Activity activity) {
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
			//ArrayList<String> contacts= myDbHelper.loadRetailerMobileEmailIds(cityID,beatID,retailerID);
				
				 SharedPreferences spf=getActivity().getSharedPreferences("SimpleLogic",0);        
			        SharedPreferences.Editor editor=spf.edit();        
			        editor.putString("RetailerMobile", dataRetaiers.get(retailerID).getLandline_no());
			        editor.putString("RetailerEmailId", dataRetaiers.get(retailerID).getEmail_id());
			        editor.putString("RetailerCode", dataRetaiers.get(retailerID).getRetailer_code());
			        //editor.putString("NewRetailer", dataRetaiers.get(retailerID).get);
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
//			present = true; // Jugad kela
//
//			if (present) {
//				/*Intent i = new Intent(context,
//						PreviousOrderActivity.class);
//				i.putParcelableArrayListExtra("previousList", data);
//				i.putExtra("date", date);
//
//				SharedPreferences sp = activity.getSharedPreferences("SimpleLogic", 0);
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
//				    Toast toast = Toast.makeText(activity,"No Previous Order", Toast.LENGTH_SHORT);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//
//			}
//		}
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
		
		
		final Intent intent = new Intent(getActivity().getApplicationContext(), NewOrderActivity.class);
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
		} );
		
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
		
		final Intent intent = new Intent(getActivity().getApplicationContext(), ReturnOrderActivity.class);
		SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);
		// disable default animation for new intent
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("retialer", ""+sp.getString("RetailerName", ""));
		
		ActivitySwitcher.animationOut(rootView.findViewById(R.id.container), getActivity().getWindowManager(),new ActivitySwitcher.AnimationFinishedListener() {
			
			@Override
			public void onAnimationFinished() {
				// TODO Auto-generated method stub
				startActivity(intent);
			}
		});
	}
}
