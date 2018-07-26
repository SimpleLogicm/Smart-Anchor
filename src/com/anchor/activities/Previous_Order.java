package com.anchor.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.anchor.model.Product;
import com.anchor.swipelistview.BaseSwipeListViewListener;
import com.anchor.swipelistview.SwipeListView;
import com.anchor.swipelistview.sample.utils.SettingsManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Previous_Order extends Activity{
	private ArrayList<Product> dataOrder;
	private SwipeListView swipeListView;
	private PackageAdapter1 adapter;
	ArrayList<HashMap<String, String>> SwipeList;
	static final String TAG_ORDERID = "order_id";
    static final String TAG_PRODUCTNM = "product_name";
	DataBaseHelper dbvoc = new DataBaseHelper(this);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previous_list);
		SwipeList=new ArrayList<HashMap<String, String>>();
		swipeListView = (SwipeListView) findViewById(R.id.prev_list);

        Global_Data.AmountOutstanding = "";
        Global_Data.AmountOverdue = "";
		
		//Global_Data.GLOvel_CUSTOMER_ID
		
		String orderid="";
		SwipeList.clear();
		
		//List<Local_Data> contacts = dbvoc.getOrderIdsn(Global_Data.GLOvel_CUSTOMER_ID,"Secondary Sales / Retail Sales");
        List<Local_Data> contacts = dbvoc.getOrderIdsnAll("Secondary Sales / Retail Sales");
		  for (Local_Data cn : contacts) 
        {
 			  orderid = cn.getCust_Code();
 			  
	 		  HashMap<String, String> mapp = new HashMap<String, String>();
	       	  mapp.put(TAG_ORDERID, orderid);
	       	  mapp.put(TAG_PRODUCTNM, cn.getc_name());
	       	 
       	 
		       	 //txttotalPreview.setText("Total		:		"+cnt1.getAmount());
		       	//Toast.makeText(NewOrderActivity.this, "Login:"+Global_Data.order_id,Toast.LENGTH_SHORT).show();
		       	 SwipeList.add(mapp);
			  
        }
		  
		  adapter = new PackageAdapter1(Previous_Order.this, SwipeList);
        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#910505")));
            // mActionBar.setDisplayShowHomeEnabled(false);
            // mActionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater mInflater = LayoutInflater.from(this);
            Intent i = getIntent();
            String name = i.getStringExtra("retialer");
            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color
                    .parseColor("#910505")));
            TextView mTitleTextView = (TextView) mCustomView
                    .findViewById(R.id.screenname);
            mTitleTextView.setText("Order List");

            TextView todaysTarget = (TextView) mCustomView
                    .findViewById(R.id.todaysTarget);
            SharedPreferences sp = Previous_Order.this
                    .getSharedPreferences("SimpleLogic", 0);

//		if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) >= 0) {
//			todaysTarget.setText("Today's Target : Rs "
//					+ String.format("%.2f", (sp.getFloat("Target", 0.00f) - sp
//							.getFloat("Current_Target", 0.00f))) + "");
//		}

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
            if (sp.getFloat("Target", 0.00f) - sp.getFloat("Current_Target", 0.00f) < 0) {
                // todaysTarget.setText("Today's Target Acheived: Rs "+(sp.getFloat("Current_Target",
                // 0.00f)-sp.getFloat("Target", 0.00f))+"");
                todaysTarget.setText("Today's Target Acheived");
            }

            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}

		
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
		    Intent i=new Intent(getApplicationContext(), MainActivity.class);
		    startActivity(i);
			this.finish();
		}
	 
	 public int convertDpToPixel(float dp) {
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        float px = dp * (metrics.densityDpi / 160f);
	        return (int) px;
	    }
	 
}
	
