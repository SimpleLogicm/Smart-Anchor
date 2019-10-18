package com.anchor.activities;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.model.Product;

import java.util.ArrayList;
import java.util.Iterator;

public class PreviousOrderActivity extends BaseActivity {
	TextView textView1,tabletextview1,txtPreviousOrder;
	ImageView imgView;
	Button buttonPreviousAddMOre,buttonPreviousPreview;
	//int userID,cityID,beatID,retailerID;

	TableLayout tl;
	String date="";
	ArrayList<Product> data;
	ArrayList<Product> newOrderList;
	LinearLayout linearPreviousOrderstatus;
	boolean firstLaunch;
	 SharedPreferences sp ;
	 int size;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previousorder);
		firstLaunch=false;

		 SharedPreferences spf=PreviousOrderActivity.this.getSharedPreferences("SimpleLogic",0);        
	        SharedPreferences.Editor editor=spf.edit();        
	        editor.putString("order", "previous");
	        editor.commit();
		Intent i = getIntent();
		String name = i.getStringExtra("retialer");
		
		 data=new ArrayList<Product>();
		 newOrderList=new ArrayList<Product>();
		 data=i.getParcelableArrayListExtra("previousList");
		 date=i.getStringExtra("date");
		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8A0808")));

		actionBar.setTitle(name);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		*/
		try
		{
			ActionBar mActionBar = getActionBar();
			mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
			// mActionBar.setDisplayShowHomeEnabled(false);
			// mActionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater mInflater = LayoutInflater.from(this);

			View mCustomView = mInflater.inflate(R.layout.action_bar, null);
			mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
			TextView mTitleTextView = mCustomView.findViewById(R.id.screenname);
			mTitleTextView.setText(name);
			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			sp = PreviousOrderActivity.this.getSharedPreferences("SimpleLogic", 0);

//        if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)>=0) {
//        	todaysTarget.setText("Today's Target : Rs "+String.format("%.2f", (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)))+"");
//		}
			try
			{
				int target  = Math.round(sp.getFloat("Target",0));
				int achieved  = Math.round(sp.getFloat("Achived",0));
				Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
				if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
				{
					int age = Math.round(age_float);

					todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
				}else
				{
					int age = Math.round(age_float);

					todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
				}

			}catch(Exception ex){ex.printStackTrace();}
			if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//        	todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target", 0.00f)-sp.getFloat("Target", 0.00f))+"");
				todaysTarget.setText("Today's Target Acheived");
			}
			mActionBar.setCustomView(mCustomView);
			mActionBar.setDisplayShowCustomEnabled(true);
			mActionBar.setHomeButtonEnabled(true);
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}catch(Exception ex){ex.printStackTrace();}

      
//        TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//        //question_value.setTypeface(null,Typeface.BOLD);
//        welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
        
		SharedPreferences sp1 = PreviousOrderActivity.this.getSharedPreferences("SimpleLogic", 0);
    
//    	userID=sp1.getInt("UserID", 0);
//    	cityID=sp1.getInt("CityID", 0);
//    	beatID=sp1.getInt("BeatID", 0);
//    	retailerID=sp1.getInt("RetailerID", 0);
    	

    	tl= findViewById(R.id.tablePrevious);
    	linearPreviousOrderstatus= findViewById(R.id.linearPreviousOrderstatus);
    	size=0;
    	for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			final Product product = (Product) iterator.next();
			
			
			final TableRow tr1 = new TableRow(PreviousOrderActivity.this);
			final View view = new View(PreviousOrderActivity.this);
			tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			tabletextview1=new TextView(PreviousOrderActivity.this);
			tabletextview1.setText(product.getProductName());
			tabletextview1.setWidth(200);
			tabletextview1.setMinHeight(60);
			tr1.addView(tabletextview1);
			tabletextview1=new TextView(PreviousOrderActivity.this);
			tabletextview1.setText(product.getProductQuantity());
			tr1.addView(tabletextview1);
			
			imgView=new ImageView(PreviousOrderActivity.this);
			imgView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.reorder));
			imgView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					
					newOrderList.add(product);
					//data.remove(product);
					tl.removeView(tr1);
					tl.removeView(view);
					size=size+1;
					if (data.size()-size==0) {
						Toast toast = Toast.makeText(PreviousOrderActivity.this,"All Items added", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						tl.setVisibility(View.INVISIBLE);
						linearPreviousOrderstatus.setVisibility(View.VISIBLE);
					}
					return false;
				}
			});
			tr1.addView(imgView);
			
			tl.addView(tr1);
			
			
			view.setBackgroundResource(R.drawable.line);
			
			tl.addView(view);
			
    	}
			buttonPreviousAddMOre= findViewById(R.id.buttonPreviousAddMOre);
			txtPreviousOrder= findViewById(R.id.txtPreviousOrder);
			 //String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	     
			txtPreviousOrder.setText("Order Date : "+date);
		
			buttonPreviousAddMOre.setOnTouchListener(new OnTouchListener() {
				
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
				        
				        final Intent i = new Intent(getApplicationContext(),NewOrderActivity.class);
						i.putParcelableArrayListExtra("productsList", newOrderList);
						i.putExtra("data", "data");
						SharedPreferences sp = PreviousOrderActivity.this
								.getSharedPreferences("SimpleLogic", 0);

						i.putExtra("retialer",
								"" + sp.getString("RetailerName", ""));
						  SharedPreferences.Editor editor1=sp.edit();        
					        editor1.putString("order", "new");
					        editor1.commit();
					        
						startActivity(i);
						overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
				    }
					return false;
				}
			});
			
			buttonPreviousPreview= findViewById(R.id.buttonPreviousPreview);
			buttonPreviousAddMOre.setBackgroundColor(Color.parseColor("#414042"));
			buttonPreviousPreview.setBackgroundColor(Color.parseColor("#414042"));
			buttonPreviousPreview.setOnTouchListener(new OnTouchListener() {
				
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
				        if (newOrderList.size()!=0) {
				        	/*Toast toast = Toast.makeText(PreviousOrderActivity.this,"Items in the Cart"+newOrderList.size() +" & Old cart Size : "+data.size()
				        			, Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();*/
				        	
				        	/*final Intent i = new Intent(getApplicationContext(),PreviewOrderActivity.class);*/
				        	final Intent i = new Intent(getApplicationContext(),PreviewOrderSwipeActivity.class);
							i.putParcelableArrayListExtra("productsList", newOrderList);
							SharedPreferences sp = PreviousOrderActivity.this
									.getSharedPreferences("SimpleLogic", 0);

							i.putExtra("retialer",
									"" + sp.getString("RetailerName", ""));
							i.putExtra("previous","previous");
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						}
				        else
				        {
				        	Toast toast = Toast.makeText(PreviousOrderActivity.this,"No Items Added", Toast.LENGTH_SHORT);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
				        }
				    }
					return false;
				}
			});
			
			
		
				
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			//onBackPressed();
		   
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	@Override
	protected void onResume() {
		// animateIn this activity
		
		
		
		if(firstLaunch){
		    // Your onResume Code Here
			//ViewGroup vg = (ViewGroup) findViewById (R.id.drawer_layout);
			//vg.invalidate();
			//getWindow().getDecorView().findViewById(android.R.id.content).invalidate();
			
			 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			 newOrderList.clear();
			
			 setContentView(R.layout.previousorder);
			 size=0;
//			 TextView welcomeUser=(TextView)findViewById(R.id.txtWelcomeUser);
//		        //question_value.setTypeface(null,Typeface.BOLD);
//		       welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
		        
			 tl= findViewById(R.id.tablePrevious);
		    linearPreviousOrderstatus= findViewById(R.id.linearPreviousOrderstatus);
			 for (Iterator iterator = data.iterator(); iterator.hasNext();) {
					final Product product = (Product) iterator.next();
					
					
					final TableRow tr1 = new TableRow(PreviousOrderActivity.this);
					final View view = new View(PreviousOrderActivity.this);
					tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
					
					tabletextview1=new TextView(PreviousOrderActivity.this);
					tabletextview1.setText(product.getProductName());
					tabletextview1.setWidth(200);
					tabletextview1.setMinHeight(60);
					tr1.addView(tabletextview1);
					tabletextview1=new TextView(PreviousOrderActivity.this);
					tabletextview1.setText(product.getProductQuantity());
					tr1.addView(tabletextview1);
					
					imgView=new ImageView(PreviousOrderActivity.this);
					imgView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.reorder));
					imgView.setOnTouchListener(new OnTouchListener() {
						
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							
							newOrderList.add(product);
							//data.remove(product);
							tl.removeView(tr1);
							tl.removeView(view);
							size=size+1;
							if (data.size()-size==0) {
								Toast toast = Toast.makeText(PreviousOrderActivity.this,"All Items added", Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								tl.setVisibility(View.INVISIBLE);
								linearPreviousOrderstatus.setVisibility(View.VISIBLE);
							}
							return false;
						}
					});
					tr1.addView(imgView);
					
					tl.addView(tr1);
					
					
					view.setBackgroundResource(R.drawable.line);
					
					tl.addView(view);
					
		    	}
			 
			 buttonPreviousAddMOre= findViewById(R.id.buttonPreviousAddMOre);
				txtPreviousOrder= findViewById(R.id.txtPreviousOrder);
				 //String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		     
				txtPreviousOrder.setText("Order Date : "+date);
			
				buttonPreviousAddMOre.setOnTouchListener(new OnTouchListener() {
					
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
					        
					        final Intent i = new Intent(getApplicationContext(),NewOrderActivity.class);
							i.putParcelableArrayListExtra("productsList", newOrderList);
							i.putExtra("data", "data");
							SharedPreferences sp = PreviousOrderActivity.this
									.getSharedPreferences("SimpleLogic", 0);

							i.putExtra("retialer",
									"" + sp.getString("RetailerName", ""));
							  SharedPreferences.Editor editor1=sp.edit();        
						        editor1.putString("order", "new");
						        editor1.commit();
						        
							startActivity(i);
							overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
					    }
						return false;
					}
				});
				
				buttonPreviousPreview= findViewById(R.id.buttonPreviousPreview);
				buttonPreviousAddMOre.setBackgroundColor(Color.parseColor("#414042"));
				buttonPreviousPreview.setBackgroundColor(Color.parseColor("#414042"));
				buttonPreviousPreview.setOnTouchListener(new OnTouchListener() {
					
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
					        if (newOrderList.size()!=0) {
					        	/*Toast toast = Toast.makeText(PreviousOrderActivity.this,"Items in the Cart"+newOrderList.size() +" & Old cart Size : "+data.size()
					        			, Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();*/
					        	
					        	/*final Intent i = new Intent(getApplicationContext(),PreviewOrderActivity.class);*/
					        	final Intent i = new Intent(getApplicationContext(),PreviewOrderSwipeActivity.class);
								i.putParcelableArrayListExtra("productsList", newOrderList);
								SharedPreferences sp = PreviousOrderActivity.this
										.getSharedPreferences("SimpleLogic", 0);

								i.putExtra("retialer",
										"" + sp.getString("RetailerName", ""));
								i.putExtra("previous","previous");
								startActivity(i);
								overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
							}
					        else
					        {
					        	Toast toast = Toast.makeText(PreviousOrderActivity.this,"No Items Added", Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
					        }
					    }
						return false;
					}
				});
				
				
			 
		  } else{
		     firstLaunch = true;
		  }
		super.onResume();
	}
	
	
}
