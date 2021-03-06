package com.anchor.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.animation.ActivitySwitcher;
import com.anchor.model.Distributor_mine;
import com.anchor.model.Product;
import com.anchor.services.getServices;
import com.anchor.swipelistview.BaseSwipeListViewListener;
import com.anchor.swipelistview.SwipeListView;
import com.anchor.swipelistview.sample.adapters.PackageAdapter;
import com.anchor.swipelistview.sample.utils.SettingsManager;
import com.anchor.webservice.ConnectionDetector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class PreviewOrderSwipeActivity extends BaseActivity {
	String str;
	HashMap<String, String> map;
	Boolean isInternetPresent = false;
	
    ConnectionDetector cd; 
	ArrayList<HashMap<String, String>> SwipeList;
	 ArrayList<String> Amount_tp = new ArrayList<String>();
	DataBaseHelper dbvoc = new DataBaseHelper(this);
    private static final int REQUEST_CODE_SETTINGS = 0;
    private ArrayList<String> Distributer_list = new ArrayList<String>();
    private PackageAdapter adapter;
    private ArrayList<Product> dataOrder;
    LoginDataBaseAdapter loginDataBaseAdapter;
    private SwipeListView swipeListView;
    TextView textView1,tabletextview1,tabletextview2,tabletextview3;
	static TextView txttotalPreview;
    static final String TAG_ITEMNAME = "product_name";
    static final String TAG_QTY = "total_qty";
    static final String TAG_PRICE = "MRP";
    static final String TAG_AMOUNT = "amount";
    static final String TAG_ITEM_NUMBER = "item_number";
	ImageView imgView;
	static float totalPrice;
	String statusOrderActivity="";
	Button buttonPreviewCheckout,buttonPreviewCheckout1,buttonPreviewAddMOre,buttonPreviewHome,btn_subapprove;
	public static final int SIGNATURE_ACTIVITY = 1;

	ArrayList<Distributor_mine> dataDistrubutors = new ArrayList<Distributor_mine>();
	 List<String> listDistrubutor;
	 HashMap<String, String> distrubutorsMap;
	 ArrayAdapter<String> dataDistrubutorsAdapter;
	
	 boolean firstLaunch=false;

    @SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_list_view_activity);
        cd  = new ConnectionDetector(getApplicationContext());
        txttotalPreview= findViewById(R.id.txttotalPreview);
        swipeListView = findViewById(R.id.example_lv_list);
        map = new HashMap<String, String>();
        
        SwipeList=new ArrayList<HashMap<String, String>>();
        btn_subapprove= findViewById(R.id.btn_subapprove);
        
        btn_subapprove.setOnClickListener(new OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			 isInternetPresent = cd.isConnectingToInternet();
    			if (isInternetPresent)
                {
    				getServices.SYNCORDER_BYCustomerINSTI(PreviewOrderSwipeActivity.this,"");   
                }
   	        	else
   	        	{
   	        	// Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

					Toast toast = Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
   	        	}
    			   
    				
    			
    		}
    	});
    	
        buttonPreviewCheckout= findViewById(R.id.buttonPreviewCheckout);
        buttonPreviewCheckout1= findViewById(R.id.buttonPreviewCheckout1);
        
        if(Global_Data.sales_btnstring.equalsIgnoreCase("Institutional Sales"))
        {
        	btn_subapprove.setVisibility(View.VISIBLE);
        	buttonPreviewCheckout.setVisibility(View.GONE);
        	buttonPreviewCheckout1.setVisibility(View.VISIBLE);
        	//buttonPreviewCheckout.setEnabled(false);
        }else{
        	    
             }
        
        Intent i = this.getIntent();
		
		String name = i.getStringExtra("retialer");
		
		if (i.hasExtra("return")) {
			statusOrderActivity="return";
		}
		
		else if (i.hasExtra("new")) {
			statusOrderActivity="new";
		}		
		
		else if (i.hasExtra("previous")) {
			statusOrderActivity="previous";
		}

		try
		   {
			   List<Local_Data> cont1 = dbvoc.getItemName(Global_Data.GLObalOrder_id);
			   for (Local_Data cnt1 : cont1) {
				   HashMap<String, String> mapp = new HashMap<String, String>();
				   mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm());
				   mapp.put(TAG_QTY, cnt1.getQty());
				   mapp.put(TAG_PRICE, cnt1.getRP());
				   mapp.put(TAG_ITEM_NUMBER, cnt1.get_category_ids());
				   Log.d("ITEM_NUMBER N", "ITEM_NUMBER N"+cnt1.get_category_ids());
				   str += cnt1.getAmount();
				   Amount_tp.add(cnt1.getAmount());
				   //txttotalPreview.setText("Total		:		"+cnt1.getAmount());
				   //Toast.makeText(NewOrderActivity.this, "Login:"+Global_Data.order_id,Toast.LENGTH_SHORT).show();
				   SwipeList.add(mapp);
			   }
			   Double sum = 0.0;
			   for(int m=0; m<Amount_tp.size(); m++)
			   {
				   sum += Double.valueOf(Amount_tp.get(m));
			   }
			   updateSum(sum);
		   }catch(Exception ex){ex.printStackTrace();}

		

	         
		
//		List<Local_Data> swipedata = dbvoc.getAportunityBy_opid(cn.geopportunity_id());
//        for (Local_Data swipe : swipedata) {
//            map.put(TAG_OPPORTUNITY, swipe.getdescription());
//            map.put(TAG_PRODUCTID, swipe.getPRODUCT_ID());
//            map.put(TAG_VALUE, swipe.getexpected_value());
//           // total =total+Integer.parseInt(swipe.getexpected_value());
//        }
		
		/*ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8A0808")));
		
		actionBar.setDisplayShowHomeEnabled(false);

		actionBar.setTitle(name);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		*/
		 Global_Data.GLOVEL_LONG_DESC = ""; 
		 Global_Data.GLOVEL_CATEGORY_SELECTION = "";
		 Global_Data.GLOVEL_ITEM_MRP = "";
		 //Global_Data.productList.clear();
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
			mTitleTextView.setText(Global_Data.order_retailer +" "+"("+Global_Data.AmountOutstanding+"/"+Global_Data.AmountOverdue+")");
			TextView todaysTarget = mCustomView.findViewById(R.id.todaysTarget);
			SharedPreferences sp = PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic", 0);
			//Log.e("DATA", "PreviewOrderSwipeActivity-"+sp.getString("order", ""));


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
//         welcomeUser.setText(sp.getString("FirstName", "")+" "+ sp.getString("LastName", ""));
		
		 dataOrder=i.getParcelableArrayListExtra("productsList");
		 
		 
       // dataOrder = new ArrayList<Product>();

        //adapter = new PackageAdapter(this, dataOrder);
        adapter = new PackageAdapter(PreviewOrderSwipeActivity.this, SwipeList);
      
        
        totalPrice=0.00f;
        //for (@SuppressWarnings("rawtypes")
//		Iterator iterator = dataOrder.iterator(); iterator.hasNext();) {
//			Product product = (Product) iterator.next();
//			totalPrice=totalPrice+Float.parseFloat(product.getProducttotalPrice());
//			
//		}
       

        if (Build.VERSION.SDK_INT >= 11) {
            swipeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            swipeListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                      long id, boolean checked) {
                    mode.setTitle("Selected (" + swipeListView.getCountSelected() + ")");
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    int id = item.getItemId();
                    if (id == R.id.menu_delete) {
                        swipeListView.dismissSelected();
                        return true;
                    }
                    return false;
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_choice_items, menu);
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    swipeListView.unselectedChoiceStates();
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }
            });
        }

         swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
            @Override
            public void onOpened(int position, boolean toRight) {
            	
            }

            @Override
            public void onClosed(int position, boolean fromRight) {
            }

            @Override
            public void onListChanged() {
            }

            @Override
            public void onMove(int position, float x) {
            }

            @Override
            public void onStartOpen(int position, int action, boolean right) {
                Log.d("swipe", String.format("onStartOpen %d - action %d", position, action));
            }

            @Override
            public void onStartClose(int position, boolean right) {
                Log.d("swipe", String.format("onStartClose %d", position));
            }

            @Override
            public void onClickFrontView(int position) {
                Log.d("swipe", String.format("onClickFrontView %d", position));
            }

            @Override
            public void onClickBackView(int position) {
                Log.d("swipe", String.format("onClickBackView %d", position));
            }

            @Override
            public void onDismiss(int[] reverseSortedPositions) {
                for (int position : reverseSortedPositions) {
                    dataOrder.remove(position);
                }
                adapter.notifyDataSetChanged();
            }

        });

        swipeListView.setAdapter(adapter);

        reload();

       
	
		  buttonPreviewCheckout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP)
			    {
			        //up event
					b.setBackgroundColor(Color.parseColor("#414042"));
			        return true;
			    }

			    if(event.getAction() == MotionEvent.ACTION_DOWN)
			    {
			    	firstLaunch=true;
			    	
			    	 SharedPreferences sp = PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic", 0);

			         @SuppressWarnings("unused")
					int data_stateid=sp.getInt("StateID", 0);
			         @SuppressWarnings("unused")
					int data_cityID=sp.getInt("cityID", 0);
			         @SuppressWarnings("unused")
					int data_beatID=sp.getInt("BeatID", 0);
			        			         
			    	 // create a Dialog component
					final Dialog dialog = new Dialog(PreviewOrderSwipeActivity.this);
					//distrubutorsMap=new HashMap<String, String>();
					//tell the Dialog to use the dialog.xml as it's layout description
					dialog.setContentView(R.layout.distributor_dilogue);
					dialog.setTitle("Warehouse");
					//myDbHelper = new DatabaseHandler(PreviewOrderSwipeActivity.this);
//					listDistrubutors = new ArrayList<String>();
//					listDistrubutors.clear();
//					listDistrubutors.add("Select distributor");
					
					//dataDistrubutors=myDbHelper.loadDistrubutors(data_stateid,data_cityID,data_beatID);
				 
					/*int i=1;
					for (Iterator iterator = dataDistrubutors.iterator(); iterator.hasNext();) {
						DatabaseModel databaseModel = (DatabaseModel) iterator.next();
						//Log.e("DATA", ""+databaseModel);
						distrubutorsMap.put(""+i, ""+databaseModel.getId());
						i++;
						
						listDistrubutors.add(databaseModel.getName());
					}*/
					
//					Distributor_mine distributor_mine1 = new Distributor_mine("Mr. Naresh", 1);
//					Distributor_mine distributor_mine2 = new Distributor_mine("Mr. Mangesh",2);
//					
//					dataDistrubutors.add(distributor_mine1);
//					dataDistrubutors.add(distributor_mine2);
//					
//					for (int i=0;i<dataDistrubutors.size();i++){
//						
//						listDistrubutors.add(dataDistrubutors.get(i).getDistributorName());
//					}
//					
//					
//					if (listDistrubutors.size()==0) {
//						
//						listDistrubutors.add("No Distributor allocated");
//					}
					
//					
					Distributer_list.clear();
					Distributer_list.add("Select Warehouse");
					String beat_id = "";
					String dis_id = "";

					List<Local_Data> getbeatid = dbvoc.GetOrders_beatID(Global_Data.GLOvel_GORDER_ID);
					for (Local_Data cn : getbeatid)
					{
						beat_id = cn.getBEAT_ID();
					}



					List<Local_Data> getdistri_code = dbvoc.getDistributors_code(beat_id);
					for (Local_Data cn : getdistri_code)
					{
						dis_id = cn.getDISTRIBUTER_ID();
						List<Local_Data> contacts1 = dbvoc.getDistributors_BYID(dis_id);
						for (Local_Data cnn : contacts1)
						{
							if(!cnn.getStateName().equalsIgnoreCase("") && !cnn.getStateName().equalsIgnoreCase(" "))
							{
								String str_categ = ""+cnn.getStateName();
								Distributer_list.add(str_categ);
							}
						}
					}
					
					// List<Local_Data> contacts1 = dbvoc.getDistributors();

			          
					final Spinner spnDistributor = dialog.findViewById(R.id.spnDistributor);
					//dataDistrubutorsAdapter = new ArrayAdapter<String>(PreviewOrderSwipeActivity.this,android.R.layout.simple_spinner_item, Global_Data.results3);
					dataDistrubutorsAdapter = new ArrayAdapter<String>(PreviewOrderSwipeActivity.this,android.R.layout.simple_spinner_item,Distributer_list);
					dataDistrubutorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spnDistributor.setAdapter(dataDistrubutorsAdapter);

					try
					{
						if(Distributer_list.size() > 1)
						{
							String s = Distributer_list.get(1);
							spnDistributor.setSelection(Distributer_list.indexOf(s));
						}
					}catch (Exception ex){ex.printStackTrace();}

					
					spnDistributor.setOnItemSelectedListener(new OnItemSelectedListener() {
					   @Override
						  public void onItemSelected(AdapterView<?> parent, View arg1,
								int pos, long arg3) {
							int distID=0;
							
							//Log.e("DATA", "parent.getSelectedItemId() :"+parent.getSelectedItemId());
//							
//							if (parent.getSelectedItemId()!=0) {
//								
//								distID=dataDistrubutors.get(pos-1).getDistributorId();
//								//distID=Integer.parseInt(distrubutorsMap.get(""+parent.getSelectedItemId()));
//							}
//							
//							/*if (!parent.getSelectedItem().toString().equalsIgnoreCase("No Distributor allocated")||parent.getSelectedItemId()!=0) {
//								distID=Integer.parseInt(distrubutorsMap.get(""+parent.getSelectedItemId()));
//								
//							}*/
//							//Log.e("DATA", "distID:"+distID);
//							 SharedPreferences spf=PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic",0);        
//						     SharedPreferences.Editor editor=spf.edit();  
//						     editor.putInt("DistributorID", distID);
//						     editor.commit();
//							
							//Log.e("DATA", "Retailer ID :"+retailerID);
							
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							
						}
					});
					
					  Button dialogButtonOk = dialog.findViewById(R.id.dialogButtonOk);
					   dialogButtonOk.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							
							if (spnDistributor.getSelectedItem().toString().equalsIgnoreCase("Select Warehouse")) {
								Toast toast = Toast.makeText(PreviewOrderSwipeActivity.this,"Please Select Warehouse", Toast.LENGTH_SHORT);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
							}
							
							else {

								String id = "";
								List<Local_Data> cont1 = dbvoc.Get_distributer_id(spnDistributor.getSelectedItem().toString().trim());
								for (Local_Data cnt1 : cont1) {
									id = cnt1.getDISTRIBUTER_ID();
								}

								dbvoc.updateORDER_DISTRIBUTER(id,Global_Data.GLObalOrder_id);
								dialog.dismiss();
								SharedPreferences sp=PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic",0);
								 
						         @SuppressWarnings("unused")
								int data_cityID=sp.getInt("cityID", 0);
						         @SuppressWarnings("unused")
								int data_beatID=sp.getInt("BeatID", 0);
								@SuppressWarnings("unused")
								int distID=sp.getInt("DistributorID", 0);
								//ArrayList<String> contacts = myDbHelper.loadDistributorMobileEmailIds(data_cityID, data_beatID, distID);
								
								SharedPreferences.Editor editor=sp.edit();        
						        editor.putString("DistributorMobile", "1234567890");
						        editor.putString("DistributorEmailId", "abc@gmail.com");
						        editor.commit();
						     
						        
								Intent intent = new Intent(getApplicationContext(), CaptureSignature.class); 
						    	intent.putParcelableArrayListExtra("productsList", dataOrder);
						    	intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				                //startActivityForResult(intent,SIGNATURE_ACTIVITY);
						    	PreviewOrderSwipeActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
						    	startActivity(intent);
							}
			 			}
					});
					
					Button dialogButtonCancel = dialog.findViewById(R.id.dialogButtonCancel);
					dialogButtonCancel.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
					
					dialog.setCancelable(false);
					dialog.show();
			    	
	               return true;
			    	}
			    return false;
			}
		});
		  
		buttonPreviewHome= findViewById(R.id.buttonPreviewHome);
		buttonPreviewHome.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View b, MotionEvent event) {
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
				    	  AlertDialog alertDialog = new AlertDialog.Builder(PreviewOrderSwipeActivity.this).create(); //Read Update
						    alertDialog.setTitle("Warning");
						    alertDialog.setMessage(" Are you sure you want to cancel?"); 
						    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// create a instance of SQLite Database
									dbvoc = new DataBaseHelper(PreviewOrderSwipeActivity.this);
									dbvoc.getDeleteTableorder_byOID(Global_Data.GLObalOrder_id);
									dbvoc.getDeleteTableorderproduct_byOID(Global_Data.GLObalOrder_id);
									Global_Data.GLOvel_GORDER_ID = "";
									Global_Data.GLObalOrder_id = "";
									Toast.makeText(PreviewOrderSwipeActivity.this, "Order Canceled Successfully", Toast.LENGTH_SHORT).show();
									 Intent order_home = new Intent(getApplicationContext(),Order.class);
									  startActivity(order_home);
									 finish();
									 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
								

								}
							});

			               alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									
									  dialog.cancel();
									 
								}
							});
						  

						    alertDialog.show(); 
							
				        return true;
				    }
				    return false;
			}
		});
		
		buttonPreviewAddMOre= findViewById(R.id.buttonPreviewAddMOre);
		buttonPreviewCheckout.setBackgroundColor(Color.parseColor("#414042"));
		buttonPreviewHome.setBackgroundColor(Color.parseColor("#414042"));
		buttonPreviewAddMOre.setBackgroundColor(Color.parseColor("#414042"));
		buttonPreviewAddMOre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//v.setBackgroundColor(Color.parseColor("#910505"));
//				if(statusOrderActivity.equalsIgnoreCase("previous")){
					final Intent i=new Intent(PreviewOrderSwipeActivity.this, NewOrderActivity.class);
					i.putExtra("data", "data");
					 i.putParcelableArrayListExtra("productsList", dataOrder);
					 
					// Log.e("DATA", "Starting out NewOrderFragment...");
					 SharedPreferences  sp=PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic", 0);

						i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
						//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						
						//finish();
						//this.startActivity(i);
						
						i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
						ActivitySwitcher.animationOut(findViewById(R.id.containerPreview), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
							@Override
							public void onAnimationFinished() {
								//Log.e("DATA", "Starting in NewOrderFragment...");
								startActivity(i);
								finish();
							}
						});
//				}
//				else {
//					onBackPressed();
//				}
				
			}
		});
		

        

    }

    private void reload() {
        SettingsManager settings = SettingsManager.getInstance();
        swipeListView.setSwipeMode(settings.getSwipeMode());
        swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
        swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
        swipeListView.setOffsetLeft(convertDpToPixel(settings.getSwipeOffsetLeft()));
        swipeListView.setOffsetRight(convertDpToPixel(settings.getSwipeOffsetRight()));
        swipeListView.setAnimationTime(settings.getSwipeAnimationTime());
        swipeListView.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
    }

    public int convertDpToPixel(float dp) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            //finish();
            onBackPressed();
        } 
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SETTINGS:
                reload();
        }
    }

    @Override
	protected void onResume() {
		super.onResume();
		if (firstLaunch) {
			 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		}
		 SharedPreferences spf=PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic",0);   
		if (spf.getInt("Capture", 0)==1) {
			SharedPreferences.Editor editor = spf.edit();
			editor.putInt("Capture", 2);
			editor.commit();
			finish();
			
		}
	}

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			//this.finish();
			onBackPressed();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
    
    @Override
	public void onBackPressed() {
		//super.onBackPressed();
		if (Global_Data.PREVIOUS_ORDER_BACK_FLAG.equalsIgnoreCase("TRUE")) {
			
			final Intent i=new Intent(getApplicationContext(), Previous_Order.class);
			i.putExtra("data", "data");
			// i.putParcelableArrayListExtra("productsList", dataOrder);
			 
    		 //SharedPreferences  sp=PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic", 0);

				//i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				//finish();
				//this.startActivity(i);
				
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				ActivitySwitcher.animationOut(findViewById(R.id.containerPreview), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						startActivity(i);
						finish();
					}
				});
		}
		
		else if(Global_Data.PREVIOUS_ORDER_BACK_FLAG.equalsIgnoreCase("")){
			final Intent i=new Intent(PreviewOrderSwipeActivity.this, NewOrderActivity.class);
			i.putExtra("data", "data");
			 i.putParcelableArrayListExtra("productsList", dataOrder);
			 
			// Log.e("DATA", "Starting out NewOrderFragment...");
			 SharedPreferences  sp=PreviewOrderSwipeActivity.this.getSharedPreferences("SimpleLogic", 0);

				i.putExtra("retialer", ""+sp.getString("RetailerName", ""));
				//i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				//finish();
				//this.startActivity(i);
				
				i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				ActivitySwitcher.animationOut(findViewById(R.id.containerPreview), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
					@Override
					public void onAnimationFinished() {
						//Log.e("DATA", "Starting in NewOrderFragment...");
						startActivity(i);
						finish();
					}
				});
		}
		
     else if(statusOrderActivity.equalsIgnoreCase("previous")){
			
    	 AlertDialog alertDialog = new AlertDialog.Builder(PreviewOrderSwipeActivity.this).create(); //Read Update
		    alertDialog.setTitle("Warning");
		    alertDialog.setMessage(" Are you sure you want to cancel?"); 
		    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					 finish();
					 overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
					
				}
			});

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					  dialog.cancel();
					 
				}
			});
		    alertDialog.show(); 
		}
	}
    
    public static void updateSum(Double sum){
		DecimalFormat df = new DecimalFormat("#0.00");
    	 txttotalPreview.setText("Total		:		"+df.format(sum));
      } 
}
