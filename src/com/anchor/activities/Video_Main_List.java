package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anchor.App.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import cpm.simplelogic.helper.Config;
import cpm.simplelogic.helper.CustomListAdapter;
import cpm.simplelogic.helper.Movie;

public class Video_Main_List extends Activity {
	// Log tag
	private static final String TAG = MainActivity.class.getSimpleName();
	ProgressDialog dialog;
	private ProgressDialog pDialog;
	private List<Movie> movieList = new ArrayList<Movie>();
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	private ListView listView;
	private CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_main);

		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, movieList);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				TextView c = (TextView) view.findViewById(R.id.rating);
				Config.YOUTUBE_VIDEO_CODE = c.getText().toString();

				TextView d = (TextView) view.findViewById(R.id.title);
				Config.YOUTUBE_VIDEO_DISCRIPTION = d.getText().toString();

				TextView e = (TextView) view.findViewById(R.id.releaseYear);
				Config.YOUTUBE_VIDEO_DATE = e.getText().toString();

				//Toast.makeText(Video_Main_List.this,Config.YOUTUBE_VIDEO_CODE, Toast.LENGTH_SHORT).show();

				Intent launch = new Intent(Video_Main_List.this,Youtube_Player_Activity.class);
				startActivity(launch);

			}});

		ActionBar mActionBar = getActionBar();
		mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
		// mActionBar.setDisplayShowHomeEnabled(false);
		// mActionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.action_bar, null);
		mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
		TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
		mTitleTextView.setText("Advertisement");

		TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
		todaysTarget.setVisibility(View.INVISIBLE);
		ImageView H_LOGO = (ImageView) mCustomView.findViewById(R.id.Header_logo);
		SharedPreferences sp = Video_Main_List.this.getSharedPreferences("SimpleLogic", 0);

		H_LOGO.setImageResource(R.drawable.video_imagenew);
		H_LOGO.setVisibility(View.VISIBLE);

//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//			todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
//		if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
////	        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
//			todaysTarget.setText("Today's Target Acheived");
//		}

		mActionBar.setCustomView(mCustomView);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);

		dialog = new ProgressDialog(Video_Main_List.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		GetNewLaunch_Datan();
//		pDialog = new ProgressDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
//		// Showing progress dialog before making http request
//		pDialog.setMessage("Please wait....");
//		pDialog.setTitle("Metal");
//		pDialog.setCancelable(false);
//		pDialog.show();
//		String domain = getResources().getString(R.string.service_domain);

		// changing action bar color

		// Creating volley request obj
//		JsonArrayRequest movieReq = new JsonArrayRequest(domain+"menus/sync_masters?imei_no",
//				new Response.Listener<JSONArray>() {
//					@Override
//					public void onResponse(JSONArray response) {
//						Log.d(TAG, response.toString());
//						hidePDialog();
//
//						// Parsing json
//						for (int i = 0; i < response.length(); i++) {
//							try {
//
//								JSONObject obj = response.getJSONObject(i);
//								Movie movie = new Movie();
//								movie.setTitle(obj.getString("title"));
//								movie.setThumbnailUrl(obj.getString("image"));
//								movie.setRating(obj.getString("rating"));
//
//								movie.setYear(obj.getString("releaseYear"));
//
//								// Genre is json array
////								JSONArray genreArry = obj.getJSONArray("genre");
////								ArrayList<String> genre = new ArrayList<String>();
////								for (int j = 0; j < genreArry.length(); j++) {
////									genre.add((String) genreArry.get(j));
////								}
////								movie.setGenre(genre);
//
//								// adding movie to movies array
//								movieList.add(movie);
//
//							} catch (JSONException e) {
//								pDialog.dismiss();
//								e.printStackTrace();
//							}
//
//						}
//						pDialog.dismiss();
//						// notifying list adapter about data changes
//						// so that it renders the list view with updated data
//						adapter.notifyDataSetChanged();
//					}
//				}, new Response.ErrorListener() {
//			@Override
//			public void onErrorResponse(VolleyError error) {
//				//Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();
//
//				if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//					Toast.makeText(Video_Main_List.this,
//							"Network Error",
//							Toast.LENGTH_LONG).show();
//				} else if (error instanceof AuthFailureError) {
//					Toast.makeText(Video_Main_List.this,
//							"Server AuthFailureError  Error",
//							Toast.LENGTH_LONG).show();
//				} else if (error instanceof ServerError) {
//					Toast.makeText(Video_Main_List.this,
//							"Server   Error",
//							Toast.LENGTH_LONG).show();
//				} else if (error instanceof NetworkError) {
//					Toast.makeText(Video_Main_List.this,
//							"Network   Error",
//							Toast.LENGTH_LONG).show();
//				} else if (error instanceof ParseError) {
//					Toast.makeText(Video_Main_List.this,
//							"ParseError   Error",
//							Toast.LENGTH_LONG).show();
//				}
//				else
//				{
//					Toast.makeText(Video_Main_List.this, error.getMessage(), Toast.LENGTH_LONG).show();
//				}
//				Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
//				startActivity(launch);
//				finish();
//				pDialog.dismiss();
//				// finish();
//			}
//		});
//
//		// Adding request to request queue
//		AppController.getInstance().addToRequestQueue(movieReq);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
		//dialog.dismiss();;
	}

	private void hidePDialog() {
		if (dialog != null) {
			dialog.dismiss();
			dialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	public  void GetNewLaunch_Datan()
	{

		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		String device_id = telephonyManager.getDeviceId();
		//context = contextn;

		//loginDataBaseAdapter=new LoginDataBaseAdapter(Video_Main_List.this);
		//loginDataBaseAdapter=loginDataBaseAdapter.open();


		//PreferencesHelper Prefs = new PreferencesHelper(context);
		//String URL = Prefs.GetPreferences("URL");
		String domain = "";

		//dialog = new ProgressDialog(Video_Main_List.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
		dialog.setMessage("Please wait....");
		dialog.setTitle("Metal");
		dialog.setCancelable(false);
		dialog.show();

		domain = getResources().getString(R.string.service_domain);

		// Global_Val global_Val = new Global_Val();
//        if(URL.equalsIgnoreCase(null) || URL.equalsIgnoreCase("null") || URL.equalsIgnoreCase("") || URL.equalsIgnoreCase(" ")) {
//            domain = context.getResources().getString(R.string.service_domain);
//        }
//        else
//        {
//            domain = URL.toString();
//        }F



		Log.d("Server url","Server url"+domain+"advertisements/send_advertisements?imei_no="+device_id);

		StringRequest stringRequest = null;
		stringRequest = new StringRequest(domain+"advertisements/send_advertisements?imei_no="+device_id,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						//showJSON(response);
						// Log.d("jV", "JV" + response);
						Log.d("jV", "JV length" + response.length());
						// JSONObject person = (JSONObject) (response);
						try {
							JSONObject json = new JSONObject(new JSONTokener(response));
							try{

								String response_result = "";
								if(json.has("result"))
								{
									response_result = json.getString("result");
								}
								else
								{
									response_result = "data";
								}


								if(response_result.equalsIgnoreCase("No Data Found")) {
									dialog.hide();
									//Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

									Toast toast = Toast.makeText(getApplicationContext(),response_result, Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();

									Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
									startActivity(launch);
									finish();

								}
								else
								if(response_result.equalsIgnoreCase("Device not registered")) {
									dialog.hide();
									//Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG).show();

									Toast toast = Toast.makeText(getApplicationContext(), response_result, Toast.LENGTH_LONG);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();

									Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
									startActivity(launch);
									finish();

								}
								else {

									JSONArray ads = json.getJSONArray("ads");
//
									Log.i("volley", "response reg ads Length: " + ads.length());
//
									Log.d("users", "ads" + ads.toString());
//
									//

									for (int i = 0; i < ads.length(); i++) {

										JSONObject jsonObject = ads.getJSONObject(i);

										//Config.YOUTUBE_VIDEO_CODE = jsonObject.getString("user_name");

										//Config.YOUTUBE_VIDEO_DATE = jsonObject.getString("user_name");
										//Config.YOUTUBE_VIDEO_DISCRIPTION = jsonObject.getString("user_name");

										Movie movie = new Movie();
										movie.setTitle(jsonObject.getString("description"));
										movie.setThumbnailUrl("http://img.youtube.com/vi/"+jsonObject.getString("youtube_id")+"/0.jpg");
										movie.setRating(jsonObject.getString("youtube_id"));

										movie.setYear(jsonObject.getString("date"));


										movieList.add(movie);

//



									}

									//Intent launch = new Intent(context,Youtube_Player_Activity.class);
									//startActivity(launch);

									dialog.dismiss();
									adapter.notifyDataSetChanged();
									//finish();

								}

								//  finish();
								// }

								// output.setText(data);
							}catch(JSONException e){e.printStackTrace();
								Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
								startActivity(launch);
								finish();

								dialog.dismiss(); }


							dialog.dismiss();
						} catch (JSONException e) {
							e.printStackTrace();
							//  finish();
							dialog.dismiss();
						}
						dialog.dismiss();

					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						//Toast.makeText(GetData.this, error.getMessage(), Toast.LENGTH_LONG).show();

						if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//							Toast.makeText(Video_Main_List.this,
//									"Network Error",
//									Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(Video_Main_List.this,
									"Network Error",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						} else if (error instanceof AuthFailureError) {
//							Toast.makeText(Video_Main_List.this,
//									"Server AuthFailureError  Error",
//									Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(Video_Main_List.this,
									"Server AuthFailureError  Error",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();

						} else if (error instanceof ServerError) {
//							Toast.makeText(Video_Main_List.this,
//									"Server   Error",
//									Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(Video_Main_List.this,
									"Server   Error",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();

						} else if (error instanceof NetworkError) {
//							Toast.makeText(Video_Main_List.this,
//									"Network   Error",
//									Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(Video_Main_List.this,
									"Network Error",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						} else if (error instanceof ParseError) {
//							Toast.makeText(Video_Main_List.this,
//									"ParseError   Error",
//									Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(Video_Main_List.this,
									"ParseError   Error",
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
						else
						{
							//Toast.makeText(Video_Main_List.this, error.getMessage(), Toast.LENGTH_LONG).show();

							Toast toast = Toast.makeText(Video_Main_List.this,
									error.getMessage(),
									Toast.LENGTH_LONG);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
						}
						Intent launch = new Intent(Video_Main_List.this,MainActivity.class);
						startActivity(launch);
						finish();
						dialog.dismiss();
						// finish();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(Video_Main_List.this);

		int socketTimeout = 300000;//30 seconds - change to what you want
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		stringRequest.setRetryPolicy(policy);
		// requestQueue.se
		//requestQueue.add(jsObjRequest);
		stringRequest.setShouldCache(false);
		requestQueue.getCache().clear();
		//requestQueue.add(stringRequest);
		AppController.getInstance().addToRequestQueue(stringRequest);
	}
}
