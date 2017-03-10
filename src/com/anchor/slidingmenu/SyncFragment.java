//package com.simplelogic.slidingmenu;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Locale;
//import java.util.Map;
//
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.SharedPreferences;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.simplelogic.URL.WebServicesUrl;
//import com.simplelogic.activities.CaptureSignature;
//import com.simplelogic.activities.MainActivity;
//import com.simplelogic.activities.R;
//import com.simplelogic.database.DatabaseHandler;
//import com.simplelogic.model.NoOrder;
//import com.simplelogic.model.Order;
//import com.simplelogic.model.OrderItem;
//import com.simplelogic.model.Retailer;
//import com.simplelogic.parser.CategoryXMLParser;
//import com.simplelogic.parser.CityXMLParser;
//import com.simplelogic.parser.ProductXMLParser;
//import com.simplelogic.parser.ReasonsXMLParser;
//import com.simplelogic.parser.SchemeXMLParser;
//import com.simplelogic.parser.StateXMLParser;
//import com.simplelogic.webservice.ConnectionDetector;
//import com.simplelogic.webservice.WebserviceCall;
//import com.simplelogic.xml.AddRetailersXMLWriterDOM;
//import com.simplelogic.xml.NoOrderXMLWriterDOM;
//import com.simplelogic.xml.ReturnOrderXMLWriterDOM;
//
//import com.simplelogic.zip.Compress;
//
//public class SyncFragment extends Fragment implements WebServicesUrl{
//
//	Button buttonUploadOrders, buttonUpdateSchems;
//	TextView editTextLastSyncDate, editTextOrders;
//	boolean success;
//	String filePath = "";
//	DatabaseHandler myDbHelper;
//	ConnectionDetector cd;
//	int userID, total_count, no_order_count, order_count,
//			return_order_count = 0;
//	HttpClient httpclient;
//	HttpResponse response;
//	HttpEntity entity;
//	String responseString;
//
//	boolean ordersExists;
//	boolean no_ordersExists;
//	boolean return_ordersExists;
//	boolean newRetailers_Exists;
//
//	boolean FlagFirstMasterSynch;
//
//
//	//varialb
//
//	ArrayList<Order> orders = new ArrayList<Order>();
//
//
//	public SyncFragment() {
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//
//		View rootView = inflater.inflate(R.layout.fragment_sync, container,
//				false);
//		buttonUploadOrders = (Button) rootView
//				.findViewById(R.id.buttonUploadOrders);
//		editTextLastSyncDate = (TextView) rootView
//				.findViewById(R.id.editTextLastSyncDate);
//		editTextOrders = (TextView) rootView.findViewById(R.id.editTextOrders);
//		SharedPreferences spf = SyncFragment.this.getActivity()
//				.getSharedPreferences("SimpleLogic", 0);
//
//		editTextLastSyncDate.setText("" + spf.getString("SyncDate", ""));
//		editTextOrders.setText("" + spf.getString("SyncCount", ""));
//
//		TextView welcomeUser = (TextView) rootView
//				.findViewById(R.id.txtWelcomeUser);
//		// question_value.setTypeface(null,Typeface.BOLD);
//		SharedPreferences sp = getActivity().getSharedPreferences(
//				"SimpleLogic", 0);
//		userID = sp.getInt("UserID", 0);
//		welcomeUser.setText(sp.getString("FirstName", "") + " "
//				+ sp.getString("LastName", ""));
//		//myDbHelper = new DatabaseHandler(getActivity().getApplicationContext());
//		//myDbHelper.openDataBase();
//		cd = new ConnectionDetector(getActivity().getApplicationContext());
//		/*
//		 * CheckNewRetailersAsyncTask checkNewRetailersAsyncTask=new
//		 * CheckNewRetailersAsyncTask(getActivity());
//		 * checkNewRetailersAsyncTask.execute();
//		 */
//		buttonUploadOrders.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				//if(FlagFirstMasterSynch==true) {
//
//				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//						.create(); // Read Update
//				alertDialog.setTitle("Confirmation");
//				alertDialog
//						.setMessage(" Are you sure you want to upload orders?");
//				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//
//								dialog.cancel();
//
//								/*if (!cd.isConnectingToInternet()) {
//									// Internet Connection is Present
//									// make HTTP requests
//									Toast toast = Toast.makeText(getActivity()
//											.getApplicationContext(),
//											"No internet connection",
//											Toast.LENGTH_SHORT);
//									toast.setGravity(Gravity.CENTER, 0, 0);
//									toast.show();
//
//								}
//
//								else {*/
//									new CreateXMLFilesUploadAsyncTask(
//											getActivity()).execute();
//								//}
//
//								// editTextTimeStamp.setText(hour+":");
//
//							}
//						});
//
//				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								dialog.cancel();
//							}
//						});
//
//				alertDialog.show();
//
//
//			}
//			//}
//		});
//		buttonUpdateSchems = (Button) rootView
//				.findViewById(R.id.buttonUpdateSchems);
//		/*
//		 * buttonUpdateSchems.setOnClickListener(new OnClickListener() {
//		 *
//		 * @Override public void onClick(View v) { // TODO Auto-generated method
//		 * stub Toast toast =
//		 * Toast.makeText(getActivity().getApplicationContext(
//		 * ),"Updated database", Toast.LENGTH_SHORT);
//		 * toast.setGravity(Gravity.CENTER, 0, 0); toast.show(); } });
//		 */
//		buttonUpdateSchems.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View b, MotionEvent event) {
//				// TODO Auto-generated method stub
//
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					// down event
//
//					new CheckNewRetailersAsyncTask(getActivity())
//					.execute();
//
//					/*DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//					Date date1 = new Date();
//
//					String formattedDate = targetFormat.format(date1);
//
//					// Log.e("DATA", formattedDate);
//					SharedPreferences spf = SyncFragment.this.getActivity()
//							.getSharedPreferences("SimpleLogic", 0);
//					String updatesDate = spf.getString("UpdatesDate", "");
//					if (formattedDate.equalsIgnoreCase(updatesDate)) {
//						Toast toast = Toast.makeText(getActivity()
//								.getApplicationContext(), "Data is upto date",
//								Toast.LENGTH_SHORT);
//						toast.setGravity(Gravity.CENTER, 0, 0);
//						toast.show();
//					}
//
//					else {
//						if (!cd.isConnectingToInternet()) {
//							// Internet Connection is Present
//							// make HTTP requests
//							Toast toast = Toast.makeText(getActivity()
//									.getApplicationContext(),
//									"No internet connection",
//									Toast.LENGTH_SHORT);
//							toast.setGravity(Gravity.CENTER, 0, 0);
//							toast.show();
//
//						}
//
//						else {
//							new CheckNewRetailersAsyncTask(getActivity())
//									.execute();
//						}
//					}*/
//
//				}
//				return false;
//			}
//		});
//		return rootView;
//	}
//
//	public class CreateXMLFilesUploadAsyncTask extends
//			AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		public CreateXMLFilesUploadAsyncTask(Activity activity) {
//			this.activity = activity;
//			context = activity;
//			dialog = new ProgressDialog(context);
//			dialog.setCancelable(false);
//			ordersExists = false;
//			return_ordersExists = false;
//			no_ordersExists = false;
//			newRetailers_Exists = false;
//			total_count = 0;
//			order_count = 0;
//			no_order_count = 0;
//			return_order_count = 0;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Please wait");
//			this.dialog.setCancelable(false);
//			this.dialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//
//
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			/*try {
//				newRetailers_Exists = createRetailersXML();
//				ordersExists = createExcel();
//				no_ordersExists = createNoOrderXML();
//				return_ordersExists = createReturnOrderXML();
//
//			} catch (Exception e) {
//				// TODO: handle exception
//
//				Log.e("CreateExcelFileUploadAsyncTask Exception", e.toString());
//			}*/
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
//			Toast toast = Toast.makeText(getActivity()
//					.getApplicationContext(), "orders upload Sucessfully",
//					Toast.LENGTH_LONG);
//			toast.setGravity(Gravity.CENTER, 0, 0);
//			toast.show();
//
//			/*//if (newRetailers_Exists) {
//
//				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//						.create(); // Read Update
//				alertDialog.setTitle("Warning");
//				alertDialog
//						.setMessage("New Retailers has been added.Please upload new retailers info.");
//				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//
//								new DirectRetailersXMLFileUploadAsyncTask(
//										getActivity()).execute();
//							}
//						});
//
//				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								dialog.cancel();
//							}
//						});
//
//				alertDialog.setCancelable(false);
//				alertDialog.show();
//			//}
//
//			else {
//
//				if (ordersExists || no_ordersExists || return_ordersExists) {
//					new DirectXMLFileUploadAsyncTask(getActivity()).execute();
//				}
//
//				else {
//					ordersExists = false;
//					no_ordersExists = false;
//					return_ordersExists = false;
//					total_count = 0;
//					order_count = 0;
//					no_order_count = 0;
//					return_order_count = 0;
//					Toast toast = Toast.makeText(getActivity()
//							.getApplicationContext(), "No orders to upload",
//							Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//				}
//
//			}*/
//
//		}
//	}
//
//	public class DirectXMLFileUploadAsyncTask extends
//			AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		private boolean webServiceResponse;
//		private boolean order_webServiceResponse;
//		private boolean noorder_webServiceResponse;
//		private boolean return_order_webServiceResponse;
//
//		public DirectXMLFileUploadAsyncTask(Activity activity) {
//			this.activity = activity;
//			context = activity;
//			dialog = new ProgressDialog(context);
//
//			dialog.setCancelable(false);
//			order_webServiceResponse = false;
//			noorder_webServiceResponse = false;
//			return_order_webServiceResponse = false;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Upload in Progress");
//			this.dialog.setCancelable(false);
//			this.dialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				if (ordersExists) {
//					webServiceResponse = doOrderFileUpload();
//					if (webServiceResponse) { // upload sucess ??
//						order_webServiceResponse = true;
//						total_count = total_count + order_count;
//						myDbHelper.removeOrders();
//					}
//				}
//				if (no_ordersExists) {
//
//					webServiceResponse = doNoOrderXMLFileUpload();
//					if (webServiceResponse) {
//						noorder_webServiceResponse = true;
//						total_count = total_count + no_order_count;
//						myDbHelper.removeNoOrders();
//					}
//				}
//
//				if (return_ordersExists) {
//					webServiceResponse = doReturnOrderXMLFileUpload();
//					if (webServiceResponse) {
//						return_order_webServiceResponse = true;
//						total_count = total_count + return_order_count;
//						myDbHelper.removeReturnOrders();
//					}
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//
//				Log.e("webServiceResponse Exception", e.toString());
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
//			if (order_webServiceResponse || noorder_webServiceResponse
//					|| return_order_webServiceResponse) {
//
//				String mydate = java.text.DateFormat.getDateTimeInstance()
//						.format(Calendar.getInstance().getTime());
//
//				editTextLastSyncDate.setText(mydate);
//				editTextOrders.setText("" + total_count);
//
//				SharedPreferences spf = SyncFragment.this.getActivity()
//						.getSharedPreferences("SimpleLogic", 0);
//				SharedPreferences.Editor editor = spf.edit();
//				editor.putString("SyncDate", mydate);
//				editor.putString("SyncCount", "" + total_count);
//				editor.commit();
//
//				Toast toast = Toast.makeText(getActivity()
//						.getApplicationContext(),
//						"Orders uploaded successfully", Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
//				filePath = "";
//				webServiceResponse = false;
//
//			} else {
//
//				Toast toast = Toast.makeText(getActivity()
//						.getApplicationContext(),
//						"Error occured..Please try again", Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
//
//			}
//
//		}
//	}
//
//	public class DirectRetailersXMLFileUploadAsyncTask extends
//			AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		private boolean webServiceResponse;
//		private boolean order_webServiceResponse;
//		private boolean noorder_webServiceResponse;
//		private boolean return_order_webServiceResponse;
//
//		public DirectRetailersXMLFileUploadAsyncTask(Activity activity) {
//			this.activity = activity;
//			context = activity;
//			dialog = new ProgressDialog(context);
//
//			dialog.setCancelable(false);
//			order_webServiceResponse = false;
//			noorder_webServiceResponse = false;
//			return_order_webServiceResponse = false;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Upload in Progress");
//			this.dialog.setCancelable(false);
//			this.dialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				webServiceResponse = doNewRetailersXMLFileUpload();
//				if (webServiceResponse) {
//					myDbHelper.resetAllRetailersFlag();
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//
//				Log.e("DirectRetailersXMLFileUploadAsyncTask Exception",
//						e.toString());
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
//			if (webServiceResponse) {
//				myDbHelper.resetAllRetailersFlag();
//				AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//						.create(); // Read Update
//				alertDialog.setTitle("Success ");
//				alertDialog
//						.setMessage("New Retailers are uploaded successfully.Do you wish to upload orders now?");
//				alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//
//								new CreateXMLFilesUploadAsyncTask(getActivity())
//										.execute();
//							}
//						});
//
//				alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",
//						new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// TODO Auto-generated method stub
//								dialog.cancel();
//							}
//						});
//
//				alertDialog.setCancelable(false);
//				alertDialog.show();
//
//			} else {
//
//				Toast toast = Toast.makeText(getActivity()
//						.getApplicationContext(),
//						"Error occured..Please try again", Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
//
//			}
//
//		}
//	}
//
//	public class CheckNewRetailersAsyncTask extends AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		private boolean newRetailersExists;
//
//		public CheckNewRetailersAsyncTask(Activity activity) {
//			this.activity = activity;
//			context = activity;
//			dialog = new ProgressDialog(context);
//
//			dialog.setCancelable(false);
//			newRetailersExists = false;
//
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Please wait");
//			this.dialog.setCancelable(false);
//			this.dialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//
//
//				Thread.sleep(5000);
//
//
//				/*ArrayList<Retailer> retList = myDbHelper
//						.loadNewRetailerMaster();
//				if (retList.size() != 0) {
//
//					newRetailersExists = true;
//
//					String extStorageDirectory = Environment
//							.getExternalStorageDirectory().toString();
//					File f = new File(extStorageDirectory + "/SimpleLogic");
//					if (!f.exists()) {
//						f.mkdir();
//					}
//					f = new File(extStorageDirectory + "/SimpleLogic/"
//							+ "new_retailers.xml");
//					if (!f.exists()) {
//						f.createNewFile();
//					}
//
//					SharedPreferences spf = getActivity()
//							.getApplicationContext().getSharedPreferences(
//									"SimpleLogic", 0);
//					int userID = spf.getInt("UserID", 0);
//					AddRetailersXMLWriterDOM addRetailersXMLWriterDOM = new AddRetailersXMLWriterDOM(
//							extStorageDirectory
//									+ "/SimpleLogic/new_retailers.xml",
//							retList, userID);
//					// NoOrderXMLWriterDOM noOrderXMLWriterDOM=new
//					// NoOrderXMLWriterDOM("\\49.248.121.50\\MS\\XML\\no_order.xml",no_orders,userID);
//				}*/
//
//			} catch (Exception e) {
//				// TODO: handle exception
//
//				Log.e("CheckNewRetailersAsyncTask Exception", e.toString());
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
//			Toast toast = Toast.makeText(getActivity()
//					.getApplicationContext(),
//					"DataBase is Up To Date ", Toast.LENGTH_LONG);
//			toast.setGravity(Gravity.CENTER, 0, 0);
//			toast.show();
//
//
//
//			/*if (newRetailersExists) {
//
//				new SyncRetailersXMLFileUploadAsyncTask(getActivity())
//						.execute();
//
//
//				 * AlertDialog alertDialog = new
//				 * AlertDialog.Builder(getActivity()).create(); //Read Update
//				 * alertDialog.setTitle("Warning"); alertDialog.setMessage(
//				 * "New Retailers has been added.Please upload new retailers info."
//				 * ); alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new
//				 * DialogInterface.OnClickListener() {
//				 *
//				 * @Override public void onClick(DialogInterface dialog, int
//				 * which) { // TODO Auto-generated method stub
//				 *
//				 * new
//				 * SyncRetailersXMLFileUploadAsyncTask(getActivity()).execute();
//				 * } });
//				 *
//				 * alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel",new
//				 * DialogInterface.OnClickListener() {
//				 *
//				 * @Override public void onClick(DialogInterface dialog, int
//				 * which) { // TODO Auto-generated method stub dialog.cancel();
//				 * } });
//				 *
//				 * alertDialog.setCancelable(false); alertDialog.show();
//
//
//			} else {
//				new XMLParsingAsyncTask(getActivity()).execute();
//			}*/
//
//		}
//	}
//
//	public class SyncRetailersXMLFileUploadAsyncTask extends
//			AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		private boolean webServiceResponse;
//
//		public SyncRetailersXMLFileUploadAsyncTask(Activity activity) {
//			this.activity = activity;
//			context = activity;
//			dialog = new ProgressDialog(context);
//
//			dialog.setCancelable(false);
//			webServiceResponse = false;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Uploading Retailers");
//			this.dialog.setCancelable(false);
//			this.dialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			try {
//
//				webServiceResponse = doNewRetailersXMLFileUpload();
//				if (webServiceResponse) {
//					myDbHelper.resetAllRetailersFlag();
//				}
//
//			} catch (Exception e) {
//				// TODO: handle exception
//
//				Log.e("SyncRetailersXMLFileUploadAsyncTask Exception",
//						e.toString());
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
//			if (webServiceResponse) {
//				myDbHelper.resetAllRetailersFlag();
//				new CheckNewRetailersAsyncTask(getActivity()).execute();
//				/*
//				 * AlertDialog alertDialog = new
//				 * AlertDialog.Builder(getActivity()).create(); //Read Update
//				 * alertDialog.setTitle("Success ");
//				 * alertDialog.setMessage("New Retailers are uploaded successfully."
//				 * ); alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Ok",new
//				 * DialogInterface.OnClickListener() {
//				 *
//				 * @Override public void onClick(DialogInterface dialog, int
//				 * which) { // TODO Auto-generated method stub
//				 *
//				 * dialog.cancel(); } });
//				 *
//				 * alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new
//				 * DialogInterface.OnClickListener() {
//				 *
//				 * @Override public void onClick(DialogInterface dialog, int
//				 * which) { // TODO Auto-generated method stub dialog.cancel();
//				 * } });
//				 *
//				 * alertDialog.setCancelable(false); alertDialog.show();
//				 */
//
//			} else {
//
//				Toast toast = Toast.makeText(getActivity()
//						.getApplicationContext(),
//						"Error occured..Please try again", Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
//
//			}
//
//		}
//	}
//
//	private boolean doOrderFileUpload() {
//
//		success = false;
//		String extStorageDirectory = Environment.getExternalStorageDirectory()
//				.toString();
//		// String upLoadServerUri = "http://.../scanner/upload.php";
//		String fileName = extStorageDirectory + "/SimpleLogic/" + "order_"+userID+".xls";  // kirti
//		String aResponse = "";
//		WebserviceCall com = new WebserviceCall();
//		// Call Webservice class method and pass values and get response
//
//		try {
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			aResponse = com.uploadExcelSheet("GetExcelOrderFile", fileName,
//					 userID);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("doOrderFileUpload Exception", e.toString());
//		}
//
//		// Log.e("DATA : aResponse", "----"+aResponse);
//
//		if (aResponse.equalsIgnoreCase("0")) {
//			success = true;
//		}
//
//		return success;
//
//	}
//
//	private boolean doNoOrderXMLFileUpload() {
//
//		success = false;
//		String extStorageDirectory = Environment.getExternalStorageDirectory()
//				.toString();
//		// String upLoadServerUri = "http://.../scanner/upload.php";
//		String fileName = extStorageDirectory + "/SimpleLogic/"
//				+ "no_order.xml";
//		String aResponse = "";
//		WebserviceCall com = new WebserviceCall();
//		// Call Webservice class method and pass values and get response
//
//		try {
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			aResponse = com.uploadXML("GetNoOrderFile", fileName, getActivity()
//					.getResources(), userID);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("doNoOrderXMLFileUpload Exception", e.toString());
//		}
//
//		// Log.e("DATA : doXMLFileUpload aResponse", "----"+aResponse);
//
//		if (aResponse.equalsIgnoreCase("0")) {
//			success = true;
//		}
//
//		return success;
//
//	}
//
//	private boolean doReturnOrderXMLFileUpload() {
//
//		success = false;
//		String extStorageDirectory = Environment.getExternalStorageDirectory()
//				.toString();
//		// String upLoadServerUri = "http://.../scanner/upload.php";
//		String fileName = extStorageDirectory + "/SimpleLogic/"
//				+ "return_order.xml";
//		String aResponse = "";
//		WebserviceCall com = new WebserviceCall();
//		// Call Webservice class method and pass values and get response
//
//		try {
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			aResponse = com.uploadXML("SyncReturnOrders", fileName,
//					getActivity().getResources(), userID);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("doReturnOrderXMLFileUpload Exception", e.toString());
//		}
//
//		// Log.e("DATA : doXMLFileUpload aResponse", "----"+aResponse);
//
//		if (aResponse.equalsIgnoreCase("0")) {
//			success = true;
//		}
//
//		return success;
//
//	}
//
//	private boolean doNewRetailersXMLFileUpload() {
//
//		success = false;
//		String extStorageDirectory = Environment.getExternalStorageDirectory()
//				.toString();
//		// String upLoadServerUri = "http://.../scanner/upload.php";
//		String fileName = extStorageDirectory + "/SimpleLogic/"
//				+ "new_retailers.xml";
//		String aResponse = "";
//		WebserviceCall com = new WebserviceCall();
//		// Call Webservice class method and pass values and get response
//
//		try {
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			aResponse = com.uploadXML("GetRetailerFile", fileName,
//					getActivity().getResources(), userID);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("doNewRetailersXMLFileUpload Exception", e.toString());
//		}
//
//		// Log.e("DATA : doNewRetailersXMLFileUpload aResponse",
//		// "----"+aResponse);
//
//		if (aResponse.equalsIgnoreCase("0")) {
//			success = true;
//		}
//
//		return success;
//
//	}
//
//	private boolean doSingaturesUpload() {
//
//		success = false;
//		String extStorageDirectory = Environment.getExternalStorageDirectory()
//				.toString();
//		// String upLoadServerUri = "http://.../scanner/upload.php";
//		String fileName = extStorageDirectory + "/SimpleLogic/Signatures.zip";
//		String aResponse = "";
//		WebserviceCall com = new WebserviceCall();
//		// Call Webservice class method and pass values and get response
//
//		try {
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			aResponse = com.uploadXML("GetSignatures", fileName, getActivity()
//					.getResources(), userID);
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("doSingaturesUpload Exception", e.toString());
//		}
//
//		// Log.e("DATA : doXMLFileUpload aResponse", "----"+aResponse);
//
//		if (aResponse.equalsIgnoreCase("0")) {
//			success = true;
//		}
//
//		return success;
//
//	}
//
//
//	/** NO USE
//	 * Remove a row by its index
//	 * @param sheet a Excel sheet
//	 * @param rowIndex a 0 based index of removing row
//	 */
//
//
//
//
//
//
//
//	public boolean createRetailersXML() throws IOException {
//		// TODO Auto-generated method stub
//
//		boolean newRetailersExists = false;
//
//		try {
//
//			ArrayList<Retailer> retList = myDbHelper.loadNewRetailerMaster();
//			// Log.e("DATA retList.size()",""+retList.size());
//			if (retList.size() != 0) {
//				newRetailersExists = true;
//
//				String extStorageDirectory = Environment
//						.getExternalStorageDirectory().toString();
//				File f = new File(extStorageDirectory + "/SimpleLogic");
//				if (!f.exists()) {
//					f.mkdir();
//				}
//				f = new File(extStorageDirectory + "/SimpleLogic/"
//						+ "new_retailers.xml");
//				if (!f.exists()) {
//					f.createNewFile();
//				}
//
//				SharedPreferences spf = getActivity().getApplicationContext()
//						.getSharedPreferences("SimpleLogic", 0);
//				int userID = spf.getInt("UserID", 0);
//				AddRetailersXMLWriterDOM addRetailersXMLWriterDOM = new AddRetailersXMLWriterDOM(
//						extStorageDirectory + "/SimpleLogic/new_retailers.xml",
//						retList, userID);
//				// NoOrderXMLWriterDOM noOrderXMLWriterDOM=new
//				// NoOrderXMLWriterDOM("\\49.248.121.50\\MS\\XML\\no_order.xml",no_orders,userID);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			Log.e("DATA createRetailersXML", "" + e.getMessage());
//		}
//
//		// Log.e("DATA no_ordersExists",""+no_ordersExists);
//		return newRetailersExists;
//	}
//
//	public boolean createNoOrderXML() throws IOException {
//		// TODO Auto-generated method stub
//
//		boolean no_ordersExists = false;
//
//		ArrayList<NoOrder> no_orders = myDbHelper.loadAllNoOrdersMaster();
//		// Log.e("DATA no_orders.size()",""+no_orders.size());
//		if (no_orders.size() != 0) {
//			no_ordersExists = true;
//			no_order_count = no_orders.size();
//			String extStorageDirectory = Environment
//					.getExternalStorageDirectory().toString();
//			File f = new File(extStorageDirectory + "/SimpleLogic");
//			if (!f.exists()) {
//				f.mkdir();
//			}
//			f = new File(extStorageDirectory + "/SimpleLogic/" + "no_order.xml");
//			if (!f.exists()) {
//				f.createNewFile();
//			}
//
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			NoOrderXMLWriterDOM noOrderXMLWriterDOM = new NoOrderXMLWriterDOM(
//					extStorageDirectory + "/SimpleLogic/no_order.xml",
//					no_orders, userID);
//			// NoOrderXMLWriterDOM noOrderXMLWriterDOM=new
//			// NoOrderXMLWriterDOM("\\49.248.121.50\\MS\\XML\\no_order.xml",no_orders,userID);
//		}
//		// Log.e("DATA no_ordersExists",""+no_ordersExists);
//		return no_ordersExists;
//	}
//
//	public boolean createReturnOrderXML() throws IOException {
//		// TODO Auto-generated method stub
//
//		boolean return_ordersExists = false;
//
//		ArrayList<Order> return_orders = myDbHelper.loadAllReturnOrdersMaster();
//		ArrayList<OrderItem> return_orders_items = myDbHelper
//				.loadAllReturnOrdersItemsMaster();
//
//		if (return_orders.size() != 0) {
//			return_ordersExists = true;
//			return_order_count = return_orders.size();
//			String extStorageDirectory = Environment
//					.getExternalStorageDirectory().toString();
//			File f = new File(extStorageDirectory + "/SimpleLogic");
//			if (!f.exists()) {
//				f.mkdir();
//			}
//			f = new File(extStorageDirectory + "/SimpleLogic/"
//					+ "return_order.xml");
//			if (!f.exists()) {
//				f.createNewFile();
//			}
//
//			SharedPreferences spf = getActivity().getApplicationContext()
//					.getSharedPreferences("SimpleLogic", 0);
//			int userID = spf.getInt("UserID", 0);
//			ReturnOrderXMLWriterDOM ReturnOrderXMLWriterDOM = new ReturnOrderXMLWriterDOM(
//					extStorageDirectory + "/SimpleLogic/" + "return_order.xml",
//					return_orders, return_orders_items, userID);
//		}
//
//		return return_ordersExists;
//	}
//
//
//
//	public class InsertUpdatesAsyncTask extends AsyncTask<Void, Void, Void> {
//
//		/** progress dialog to show user that the backup is processing. */
//		private ProgressDialog dialog;
//		/** application context. */
//		private Activity activity;
//
//		private Context context;
//
//		private boolean serviceResponse;
//
//		public InsertUpdatesAsyncTask(Activity activity) {
//			this.activity = activity;
//			context = activity;
//			dialog = new ProgressDialog(context);
//			dialog.setCancelable(false);
//			serviceResponse = false;
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			this.dialog.setMessage("Updating database");
//			this.dialog.show();
//
//		}
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//
//			try {
//				int[] userStateCityIDs = myDbHelper.getUserStateCityID(userID);
//				myDbHelper.updateUser(getRegisteredUser(listUser, userID));
//				myDbHelper.updateState(getUserState(listState,
//						userStateCityIDs[0]));
//				myDbHelper
//						.updateCity(getUserCity(listCity, userStateCityIDs[1]));
//				ArrayList<Integer> userBeatIDs = myDbHelper
//						.getUserBeatIDs(userID);
//				myDbHelper.updateBeat(getUserBeats(listBeat, userBeatIDs));
//
//				myDbHelper.updateRetailer(getUserRetailers(listRetailer,
//						userBeatIDs));
//				myDbHelper.updateDistributors(getUserDistributors(
//						listDistributors, userStateCityIDs[1]));
//				myDbHelper.updateCategory(listCategory);
//				myDbHelper.updateProducts(listProduct, mapProductVariant);
//				myDbHelper.updateScheme(listSchemes);
//				myDbHelper.updateReason(listReason);
//
//				serviceResponse = true;
//
//			} catch (Exception e) {
//				// TODO: handle exception
//				serviceResponse = false;
//				Log.e("InsertUpdatesAsyncTask Exception", e.toString());
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
//			if (serviceResponse) {
//
//				/* Test Start */
//				DateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
//				Date date1 = new Date();
//
//				String formattedDate = targetFormat.format(date1);
//
//				// Log.e("DATA", formattedDate);
//				SharedPreferences spf = SyncFragment.this.getActivity()
//						.getSharedPreferences("SimpleLogic", 0);
//				SharedPreferences.Editor editor = spf.edit();
//				editor.putString("UpdatesDate", formattedDate);
//				editor.commit();
//				/* Test End */
//
//				Toast toast = Toast.makeText(getActivity()
//						.getApplicationContext(), "Check updates successful",
//						Toast.LENGTH_LONG);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
//
//			}
//
//		}
//	}
//
//	public boolean checkPresence(String name, ArrayList<String> myList) {
//		for (String s : myList)
//			if (s.equalsIgnoreCase(name))
//				return true;
//		return false;
//	}
//
//	public static boolean deleteDirectory(File path) {
//		if (path.exists()) {
//			File[] files = path.listFiles();
//			if (files == null) {
//				return true;
//			}
//			for (int i = 0; i < files.length; i++) {
//				if (files[i].isDirectory()) {
//					deleteDirectory(files[i]);
//				} else {
//					files[i].delete();
//				}
//			}
//		}
//		return (path.delete());
//	}
//
//	public ArrayList<user> getRegisteredUser(ArrayList<user> listUsers,
//			int userID) {
//		// TODO Auto-generated method stub
//
//		ArrayList<user> regUser = new ArrayList<user>();
//
//		for (Iterator iterator = listUsers.iterator(); iterator.hasNext();) {
//			user u = (user) iterator.next();
//			if (u.getUserId() == userID) {
//				regUser.add(u);
//				break;
//			}
//
//		}
//		return regUser;
//	}
//
//	public ArrayList<state> getUserState(ArrayList<state> listState, int stateID) {
//		// TODO Auto-generated method stub
//		ArrayList<state> userState = new ArrayList<state>();
//		for (Iterator iterator = listState.iterator(); iterator.hasNext();) {
//			state s = (state) iterator.next();
//			if (s.getIntStateId() == stateID) {
//				userState.add(s);
//				break;
//			}
//
//		}
//		return userState;
//	}
//
//	public ArrayList<city> getUserCity(ArrayList<city> listCity, int cityID) {
//		// TODO Auto-generated method stub
//		ArrayList<city> userCity = new ArrayList<city>();
//		for (Iterator iterator = listCity.iterator(); iterator.hasNext();) {
//			city city = (city) iterator.next();
//			if (city.getIntCityId() == cityID) {
//				userCity.add(city);
//				break;
//			}
//
//		}
//		return userCity;
//	}
//
//	public ArrayList<beat> getUserBeats(ArrayList<beat> listBeats,
//			ArrayList<Integer> userBeatIDs) {
//		// TODO Auto-generated method stub
//		ArrayList<beat> usersBeats = new ArrayList<beat>();
//
//		for (Iterator iterator = listBeats.iterator(); iterator.hasNext();) {
//			beat beat = (beat) iterator.next();
//
//			for (Iterator iterator2 = userBeatIDs.iterator(); iterator2
//					.hasNext();) {
//				Integer i = (Integer) iterator2.next();
//				if (beat.getIntBeatId() == i) {
//					usersBeats.add(beat);
//				}
//
//			}
//
//		}
//
//		return usersBeats;
//	}
//
//	public ArrayList<retailer> getUserRetailers(
//			ArrayList<retailer> listRetailers, ArrayList<Integer> userBeatIDs) {
//		// TODO Auto-generated method stub
//		ArrayList<retailer> userRetailers = new ArrayList<retailer>();
//
//		for (Iterator iterator = listRetailers.iterator(); iterator.hasNext();) {
//			retailer retailer = (retailer) iterator.next();
//
//			for (Iterator iterator2 = userBeatIDs.iterator(); iterator2
//					.hasNext();) {
//				Integer i = (Integer) iterator2.next();
//				if (retailer.getBeatId() == i) {
//					userRetailers.add(retailer);
//				}
//
//			}
//
//		}
//		return userRetailers;
//	}
//
//	public ArrayList<distributors> getUserDistributors(
//			ArrayList<distributors> listDistributors, int cityID) {
//		// TODO Auto-generated method stub
//		ArrayList<distributors> userDist = new ArrayList<distributors>();
//		for (Iterator iterator = listDistributors.iterator(); iterator
//				.hasNext();) {
//			distributors distributors = (distributors) iterator.next();
//			if (distributors.getIntCityId() == cityID) {
//				userDist.add(distributors);
//			}
//
//		}
//		return userDist;
//	}
//}
