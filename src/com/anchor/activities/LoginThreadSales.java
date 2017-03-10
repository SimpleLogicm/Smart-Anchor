package com.anchor.activities;

import android.content.Context;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;

public class LoginThreadSales extends Thread {
	private static String TAG="LogThreadSales";
	Context c;
	Handler h;
	public interface LoginInterfaceSales
	{
		public void onLogThreadDataReturned(boolean isSuccess,String msg);
		public void onLogThreadErrorReturned();
	}

	private LoginInterfaceSales mLogThreadInterface;
	private String User,Pwd,Imei,Cur_Date,Cur_Time;
	int mCountryId;
	
	public LoginThreadSales(LoginInterfaceSales n, String username, String pwd, String imei, String cur_date, String cur_time) {

		this.mLogThreadInterface=n;
		this.User=username;
		this.Pwd=pwd;
		this.Imei=imei;
		this.Cur_Date=cur_date;
		this.Cur_Time=cur_time;
	}
	
//	@Override
//	public void run() {
//		super.run();
//		HttpGet httpget;
//		InputStream inputStream;
//		String result = "";
//		// Create a new HttpClient and Post Header
//		try
//		{
//		    httpget = new HttpGet("http://5e7f412a.ngrok.com/fieldsales/users/populate_mobile_database?user_name=swatiyamgar&password=password&imei_no=123456789012345");
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpResponse httpResponse = httpclient.execute(httpget);
//			//	Log.d("post parmaetrs:",nameValuePairs.toString());
//			inputStream = httpResponse.getEntity().getContent();
//
//			// Execute HTTP Post Request
//			if(inputStream != null)
//			{
//				Global_Data.login_resp = convertInputStreamToString(inputStream);
//				System.out.println("asf=============== >>>>>"+Global_Data.login_resp);
//				//Log.d(TAG+"InputStream", SharedData.str_results);
//				if(!Global_Data.login_resp.equalsIgnoreCase("Invalid User"))
//				{
//
//				 JSONObject arr=new JSONObject(Global_Data.login_resp);
//				 boolean resultVal=Global_Data.login_resp.toString() != null;
//				//SharedData.len=object1.length();
//
//				//SharedData.getshare_list = new ArrayList<HashMap<String,String>>();
//				//JSONArray jsonarr=arr.getJSONArray("user");
//				//Log.i("length", "");
//				if(resultVal)
//				{
//					//if(jsonarr.length()>=0)
//					//{
//						//SharedData.mLinkMessage=new LinkMessageData[SharedData.jsonarr.length()];
//						//for(int i=0;i<jsonarr.length();i++)
//						//{
//							JSONObject obj=arr.getJSONObject("user");
//
//							Global_Data.user_name=obj.getString("user_name");
//							Global_Data.user_email=obj.getString("email");
//							Global_Data.user_doj=obj.getString("date_of_joining");
//							Global_Data.user_report=obj.getString("reporting_to");
//							Global_Data.user_fname=obj.getString("first_name");
//							Global_Data.user_lname=obj.getString("last_name");
//							Global_Data.user_status=obj.getString("status");
//							Global_Data.user_createby=obj.getString("created_by");
//							Global_Data.user_modifyby=obj.getString("modified_by");
//							Global_Data.user_project=obj.getString("project_id");
//							Global_Data.user_customer=obj.getString("customer_id");
//							Global_Data.user_device=obj.getString("device_id");
//							Global_Data.user_role=obj.getString("role_id");
//							Global_Data.user_state=obj.getString("state_id");
//							Global_Data.user_city=obj.getString("city_id");
//							Global_Data.user_createat=obj.getString("created_at");
//							Global_Data.user_updateat=obj.getString("updated_at");
//
//// 						    SharedData.mLinkMessage[i]=new LinkMessageData();
////						    SharedData.mLinkMessage[i].doParseJSONData(obj);
//						//}
//						//mLogThreadInterface.onLogThreadDataReturned(resultVal,null);
//					//}
//					//else
//						//mLogThreadInterface.onLogThreadDataReturned(resultVal,arr.getString("msg"));
//				}
//			}else{
//				 h.post(new Runnable(){
//					   public void run(){
//						                  Toast.makeText(c, "Invalid Login", Toast.LENGTH_SHORT).show();
//						                }
//					                      });
//				    System.out.println("Invalid Userrrrrrr");
//			     }
//			 }else
//			  {
//				result = "Did not work!";
//				//mLogThreadInterface.onLogThreadErrorReturned();
//			  }
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			Log.d("InputStream", e.getLocalizedMessage());
//			//mLogThreadInterface.onLogThreadErrorReturned();
//		}
//	}
	
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;
		inputStream.close();
		return result;
	}   
}


