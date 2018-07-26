package com.anchor.slidingmenu;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.anchor.activities.BaseActivity;
import com.anchor.activities.Calendar_Event;
import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Expended_List_Main;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.MainActivity;
import com.anchor.activities.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@TargetApi(3)
public class CalendarAct extends BaseActivity implements OnClickListener {
	private static final String tag = "CalendarAct";
	String popUpContents[];
	PopupWindow popupWindowDogs;
	Date date1;
	Date date2;
	String datenew;
	String s[];
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	Button buttonShowDropDown,listevent_btn;
	private TextView currentMonth;
	private Button selectedDayMonthYearButton;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private GridCellAdapter adapter;
	private Calendar _calendar;
	@SuppressLint("NewApi")
	private int month, year;
	@SuppressWarnings("unused")
	@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi" })
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_calendar);
		
		listevent_btn=(Button)findViewById(R.id.listevent_btn);

		    SharedPreferences spf=CalendarAct.this.getSharedPreferences("SimpleLogic",0);        
	        SharedPreferences.Editor editor=spf.edit();        
	        editor.putString("order", "new");
	        editor.commit();
	
	         listevent_btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(CalendarAct.this, Expended_List_Main.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		    	}
			});


		Calendar calendar = Calendar.getInstance();

		int yearnew = calendar.get(Calendar.YEAR);
		int monthnew = calendar.get(Calendar.MONTH);
		int daynew = calendar.get(Calendar.DAY_OF_MONTH);

		month = calendar.get(Calendar.MONTH);
		year = calendar.get(Calendar.YEAR);

		Formatter fmt = new Formatter();
		// fmt.format("%tB %tb %tm", calendar, calendar, calendar);

		String mm = fmt.format("%tB", calendar).toString();


		Calendar calendarold = Calendar.getInstance();
		calendarold.add(calendarold.MONTH, -1);
		int yearold = calendarold.get(calendarold.YEAR);
		int monthold = calendarold.get(calendarold.MONTH);
		int dayold = calendarold.get(calendarold.DAY_OF_MONTH);

		Formatter fmtt = new Formatter();
		// fmt.format("%tB %tb %tm", calendar, calendar, calendar);

		String mmm = fmtt.format("%tB", calendarold).toString();

		Log.d("year", "YEAR"+yearnew);
		Log.d("yearold", "YEAROLD"+yearold);
		Log.d("month", "MONTH"+monthnew);
		Log.d("monthold", "MONTHOLD"+monthold);
		Log.d("month String", "MONTH String"+mm);
		Log.d("monthold String", "MONTHOLD String"+mmm);

		String cureent_month = mm+"-"+yearnew;
		String old_month = mmm+"-"+yearold;

		Date current = new Date();
		//create a date one day after current date



		try
		{
			List<Local_Data> contacts2 = dbvoc.getAllCalender_EventValue();

			if(contacts2.size() != 0 )
			{
				for (Local_Data cn : contacts2) {

					String from_date = cn.getfrom_date();


					//create date object
					Date next = new Date(from_date);

//					if(next.after(current)){
//						System.out.println("The date is future day");
//					} else {
//						if(from_date.contains("-"))
//						{
//							String from_date_Array [] = from_date.split("-");
//							String final_fromdate = from_date_Array[1]+"-"+from_date_Array[2];
//
//							if(final_fromdate.equalsIgnoreCase(cureent_month) || final_fromdate.equalsIgnoreCase(old_month))
//							{
//								Log.d("From Data", "From Date"+cn.getfrom_date());
//							}
//							else
//							{
//								dbvoc.getDeleteTableCalenderEntity(cn.getfrom_date());
//							}
//
//						}
				//	}



				}
			}

		}
		catch(Exception ex){
			ex.printStackTrace();
		}


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
			mTitleTextView.setText("Calender");

			TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = CalendarAct.this.getSharedPreferences("SimpleLogic", 0);

			ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
			H_LOGO.setImageResource(R.drawable.cal);
			H_LOGO.setVisibility(View.VISIBLE);

			try
			{
				int target  = (int) Math.round(sp.getFloat("Target",0));
				int achieved  = (int) Math.round(sp.getFloat("Achived",0));
				Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
				if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
				{
					int age = (int) Math.round(age_float);

					todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
				}else
				{
					int age = (int) Math.round(age_float);

					todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
				}

			}catch(Exception ex){ex.printStackTrace();}


//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	//todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//			todaysTarget.setText("T/A : Rs "+String.format(sp.getFloat("Target",0)+"/"+sp.getFloat("Achived", 0)));
//		}
//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//        	todaysTarget.setText("Today's Target Acheived");
//		}

			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}

		
		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) + 1;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);

		selectedDayMonthYearButton = (Button) this
				.findViewById(R.id.selectedDayMonthYear);
		selectedDayMonthYearButton.setText("Selected: ");

		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (TextView) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);

		// Initialised
		adapter = new GridCellAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		
		 List<String> dogsList = new ArrayList<String>();
	        dogsList.add("Travel Planner::1");
	        dogsList.add("Leave Management::2");
	        dogsList.add("Task::3");
	       // dogsList.add("Tibetan Spaniel::4");
	 
	        // convert to simple array
	        popUpContents = new String[dogsList.size()];
	        dogsList.toArray(popUpContents);
	 
	        // initialize pop up window
	        popupWindowDogs = popupWindowDogs();
	}
	
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new GridCellAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v == prevMonth) {
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
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
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
			    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
				this.finish();
	}
	
	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View ...");
		super.onDestroy();
	}

	// Inner Class
	public class GridCellAdapter extends BaseAdapter implements OnClickListener {
		private static final String tag = "GridCellAdapter";
		private final Context _context;

		private final List<String> list;
		private static final int DAY_OFFSET = 1;
		private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
				"Wed", "Thu", "Fri", "Sat" };
		private final String[] months = { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		private int daysInMonth;
		private int currentDayOfMonth;
		private int currentWeekDay;
		private Button gridcell;
		private TextView num_events_per_day;
		private final HashMap<String, Integer> eventsPerMonthMap;
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"dd-MMM-yyyy");

		// Days in Current Month
		public GridCellAdapter(Context context, int textViewResourceId,
				int month, int year) {
			super();
			this._context = context;
			this.list = new ArrayList<String>();
			Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
					+ "Year: " + year);
			Calendar calendar = Calendar.getInstance();
			setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
			Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
			Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
			Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

			// Print Month
			printMonth(month, year);

			// Find Number of Events
			eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
		}

		private String getMonthAsString(int i) {
			return months[i];
		}

		private String getWeekDayAsString(int i) {
			return weekdays[i];
		}

		private int getNumberOfDaysOfMonth(int i) {
			return daysOfMonth[i];
		}

		public String getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		/**
		 * Prints Month
		 * 
		 * @param mm
		 * @param yy
		 */
		private void printMonth(int mm, int yy) {
			Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
			int trailingSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			int currentMonth = mm - 1;
			String currentMonthName = getMonthAsString(currentMonth);
			daysInMonth = getNumberOfDaysOfMonth(currentMonth);

			Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
					+ daysInMonth + " days.");

			GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
			Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

			if (currentMonth == 11) {
				prevMonth = currentMonth - 1;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
				Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			} else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
				Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			} else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			}

			int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;

			Log.d(tag, "Week Day:" + currentWeekDay + " is "
					+ getWeekDayAsString(currentWeekDay));
			Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
			Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

			if (cal.isLeapYear(cal.get(Calendar.YEAR)))
				if (mm == 2)
					++daysInMonth;
				else if (mm == 3)
					++daysInPrevMonth;

			// Trailing Month days
			for (int i = 0; i < trailingSpaces; i++) {
				Log.d(tag,
						"PREV MONTH:= "
								+ prevMonth
								+ " => "
								+ getMonthAsString(prevMonth)
								+ " "
								+ String.valueOf((daysInPrevMonth
										- trailingSpaces + DAY_OFFSET)
										+ i));
				list.add(String
						.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
								+ i)
						+ "-GREY"
						+ "-"
						+ getMonthAsString(prevMonth)
						+ "-"
						+ prevYear);
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {
				Log.d(currentMonthName, String.valueOf(i) + " "
						+ getMonthAsString(currentMonth) + " " + yy);
				if (i == getCurrentDayOfMonth()) {
					list.add(String.valueOf(i) + "-BLUE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				} else {
					list.add(String.valueOf(i) + "-WHITE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				}
			}

			// Leading Month days
			for (int i = 0; i < list.size() % 7; i++) {
				Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
				list.add(String.valueOf(i + 1) + "-GREY" + "-"
						+ getMonthAsString(nextMonth) + "-" + nextYear);
			}
		}

		/**
		 * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
		 * ALL entries from a SQLite database for that month. Iterate over the
		 * List of All entries, and get the dateCreated, which is converted into
		 * day.
		 * 
		 * @param year
		 * @param month
		 * @return
		 */
		private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
				int month) {
			HashMap<String, Integer> map = new HashMap<String, Integer>();

			return map;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) _context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.screen_gridcell, parent, false);
			}

			// Get a reference to the Day gridcell
			gridcell = (Button) row.findViewById(R.id.calendar_day_gridcell);
			gridcell.setOnClickListener(this);

			// ACCOUNT FOR SPACING

			Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
			String[] day_color = list.get(position).split("-");
			String theday = day_color[0];
			String themonth = day_color[2];
			String theyear = day_color[3];
			if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
				if (eventsPerMonthMap.containsKey(theday)) {
					num_events_per_day = (TextView) row
							.findViewById(R.id.num_events_per_day);
					Integer numEvents = (Integer) eventsPerMonthMap.get(theday);
					num_events_per_day.setText(numEvents.toString());
				}
			}

			// Set the Day GridCell
			gridcell.setText(theday);
			gridcell.setTag(theday + "-" + themonth + "-" + theyear);
			Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
					+ theyear);

			if (day_color[1].equals("GREY")) {
				gridcell.setTextColor(getResources()
						.getColor(R.color.lightgray));
			}
			
			if (day_color[1].equals("WHITE")) {
				gridcell.setTextColor(getResources().getColor(
						R.color.lightgray02));
			}
			
			if (day_color[1].equals("BLUE")) {
				gridcell.setTextColor(getResources().getColor(R.color.orrange));
			}
			
			return row;
		}

		@Override
		public void onClick(View view) {
			popupWindowDogs.showAsDropDown(view, -3, 0);
			String date_month_year = (String) view.getTag();
			selectedDayMonthYearButton.setText(date_month_year);
			Global_Data.select_date=selectedDayMonthYearButton.getText().toString();
			//Toast.makeText(CalendarAct.this, "date:"+date_month_year, Toast.LENGTH_SHORT).show();
			Log.e("Selected date", date_month_year);
			try {
				Date parsedDate = dateFormatter.parse(date_month_year);
				Log.d(tag, "Parsed Date: " + parsedDate.toString());

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		public int getCurrentDayOfMonth() {
			return currentDayOfMonth;
		}

		private void setCurrentDayOfMonth(int currentDayOfMonth) {
			this.currentDayOfMonth = currentDayOfMonth;
		}

		public void setCurrentWeekDay(int currentWeekDay) {
			this.currentWeekDay = currentWeekDay;
		}

		public int getCurrentWeekDay() {
			return currentWeekDay;
		}
	}
	
	 public PopupWindow popupWindowDogs() {
		 
	        // initialize a pop up window type
	        PopupWindow popupWindow = new PopupWindow(this);
	 
	        // the drop down list is a list view
	        ListView listViewDogs = new ListView(this);
	         
	        // set our adapter and pass our pop up window contents
	        listViewDogs.setAdapter(dogsAdapter(popUpContents));
	         
	        // set the item click listener
	        listViewDogs.setOnItemClickListener(new DogsDropdownOnItemClickListener());
	 
	        // some other visual settings
	        popupWindow.setFocusable(true);
	        popupWindow.setWidth(400);
	        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
	         
	        // set the list view as pop up window content
	        popupWindow.setContentView(listViewDogs);
	 
	        return popupWindow;
	    }
	 
	 private ArrayAdapter<String> dogsAdapter(String dogsArray[]) {
		 
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dogsArray) {
	 
	            @Override
	            public View getView(int position, View convertView, ViewGroup parent) {
	 
	                // setting the ID and text for every items in the list
	                String item = getItem(position);
	                String[] itemArr = item.split("::");
	                String text = itemArr[0];
	                String id = itemArr[1];
	 
	                // visual settings for the list item
	                TextView listItem = new TextView(CalendarAct.this);
	 
	                listItem.setText(text);
	                listItem.setTag(id);
	                listItem.setTextSize(18);
	                listItem.setPadding(10, 10, 10, 10);
	                listItem.setTextColor(Color.BLACK);
	                listItem.setBackgroundColor(Color.LTGRAY);
	                 
	                return listItem;
	            }
	        };
	         
	        return adapter;
	    }
	 
	 public class DogsDropdownOnItemClickListener implements OnItemClickListener {
		 
		    String TAG = "DogsDropdownOnItemClickListener.java";
		     
		    @Override
		    public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
		 
		        // get the context and main activity to access variables
		        Context mContext = v.getContext();
		        CalendarAct mainActivity = ((CalendarAct) mContext);
		         
		        // add some animation when a list item was clicked
		        Animation fadeInAnimation = AnimationUtils.loadAnimation(v.getContext(), android.R.anim.fade_in);
		        fadeInAnimation.setDuration(10);
		        v.startAnimation(fadeInAnimation);
		         
		        // dismiss the pop up
		        mainActivity.popupWindowDogs.dismiss();
		         
		        // get the text and set it as the button text
		        String selectedItemText = ((TextView) v).getText().toString();
		        Global_Data.calspinner = ((TextView) v).getText().toString();
		        //mainActivity.buttonShowDropDown.setText(selectedItemText);
		         
		        // get the id
		        String selectedItemTag = ((TextView) v).getTag().toString();
		       // Toast.makeText(mContext, "Calender ID is: " + selectedItemTag+","+selectedItemText, Toast.LENGTH_SHORT).show();
		        
		        Log.d("Calender ID is:", "Calender ID is: "+selectedItemText);

				Calendar calendar = Calendar.getInstance();
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);

//				SimpleDateFormat dates = new SimpleDateFormat("yyyyMMdd");
//				String current_date = year + "" + getlength(month+1) + "" + getlength(day);
//
//				try {
//					date1 = (Date)dates.parse(current_date);
//					Log.d("current_date","current_date"+current_date );
//					Log.d("date1", "date1" + date1);
//				}
//				catch (java.text.ParseException e) {
//					e.printStackTrace();
//				}
//				catch(Exception ex){
//					ex.printStackTrace();
//				}
//
//
//				if(Global_Data.select_date.toString().indexOf(String.valueOf("-"))>1)
//				{
//					s =  Global_Data.select_date.split("-");
//					datenew  = s[2] + "" + monthconverter(s[1]) + "" + getlength(Integer.parseInt((s[0])));
//
//				}
//
//				try {
//					date2 = (Date) dates.parse(datenew);
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//				long difference = Math.abs(date1.getTime() - date2.getTime());
//				long differenceDates = difference / (1000 * 60 * 60 * 24);
//
//				if(date2.after(date1) && differenceDates <= 30 )
//				{
//                      Log.d("diffrence in","diffrence in");
//				}
//				else
//				{
//					Log.d("diffrence out","diffrence out");
//				}

		        Global_Data.CALENDER_EVENT_TYPE = selectedItemText.trim();

//				if(Global_Data.select_date)
//				{
//
//				}

//				if(date2.after(date1) && dayDifference <= 30 && in > 50)
//				{
//
//				}

				Calendar c = Calendar.getInstance();
				System.out.println("Current time => " + c.getTime());

				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
				String formattedDate = df.format(c.getTime());
				date1 = new Date(formattedDate);
				date2 = new Date(selectedDayMonthYearButton.getText().toString());

				Log.e("Today Date", "Today Date"+formattedDate);

				Log.e("Selected Date", "Selected Date"+selectedDayMonthYearButton.getText().toString());

				if(date1.compareTo(date2)>0)
				{
					get_dialogDate();
				}
				else
				{
					Intent intent = new Intent(mainActivity, Calendar_Event.class);
					startActivity(intent);

					overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				}



		        
		    }
		 
		}

	  public static String getlength(int len){
		int number = len;
		int length = (int) Math.log10(number) + 1;
		if(length == 1)
		{
			return "0"+len;
		}
		else
		{
			return ""+len;
		}

	}

	public String monthconverter(String month_name)
	{

		String monthString;
		switch (month_name) {
			case "January":  monthString = "01";       break;
			case "February":  monthString = "02";      break;
			case "March":  monthString = "03";         break;
			case "April":  monthString = "04";         break;
			case "May":  monthString = "05";           break;
			case "June":  monthString = "06";          break;
			case "July":  monthString = "07";          break;
			case "August":  monthString = "08";        break;
			case "September":  monthString = "09";     break;
			case "October": monthString = "10";       break;
			case "November": monthString = "11";      break;
			case "December": monthString = "12";      break;
			default: monthString = "Invalid month"; break;
		}

		return monthString;

	}

	public void get_dialogDate()
	{
		AlertDialog alertDialog = new AlertDialog.Builder(CalendarAct.this).create(); //Read Update
		alertDialog.setTitle("Confirmation");
		alertDialog.setMessage("Select date is a past date, do you want to continue with this ?");
		alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(CalendarAct.this, Calendar_Event.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			}
		});

		alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});

		alertDialog.setCancelable(false);
		alertDialog.show();
	}
}
