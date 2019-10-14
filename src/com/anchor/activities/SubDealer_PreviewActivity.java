
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.animation.ActivitySwitcher;
import com.anchor.model.Product;
import com.anchor.swipelistview.BaseSwipeListViewListener;
import com.anchor.swipelistview.SwipeListView;
import com.anchor.swipelistview.sample.adapters.PackageAdapter;
import com.anchor.swipelistview.sample.utils.SettingsManager;
import com.anchor.webservice.ConnectionDetector;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class SubDealer_PreviewActivity extends BaseActivity {
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
    ArrayAdapter<String> dataDistrubutorsAdapter;
    boolean firstLaunch=false;

    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subdealer_previeactivity);
        cd  = new ConnectionDetector(getApplicationContext());
        txttotalPreview=(TextView) findViewById(R.id.txttotalPreview);
        swipeListView = (SwipeListView) findViewById(R.id.example_lv_list);
        map = new HashMap<String, String>();

        SwipeList=new ArrayList<HashMap<String, String>>();
        btn_subapprove=(Button) findViewById(R.id.btn_subapprove);


        buttonPreviewCheckout=(Button) findViewById(R.id.buttonPreviewCheckout);
        buttonPreviewCheckout1=(Button) findViewById(R.id.buttonPreviewCheckout1);
        try
        {
            List<Local_Data> cont1 = dbvoc.getItemName_SubDealer(Global_Data.GLOvel_SUB_GORDER_ID);
            for (Local_Data cnt1 : cont1) {
                HashMap<String, String> mapp = new HashMap<String, String>();
                mapp.put(TAG_ITEMNAME, cnt1.getProduct_nm());
                mapp.put(TAG_QTY, cnt1.getQty());
                mapp.put(TAG_PRICE, cnt1.getRP());
                mapp.put(TAG_ITEM_NUMBER, cnt1.get_category_ids());
                Log.d("ITEM_NUMBER N", "ITEM_NUMBER N"+cnt1.get_category_ids());
                str += cnt1.getAmount();
                Amount_tp.add(cnt1.getAmount());
                SwipeList.add(mapp);
            }
            Double sum = 0.0;
            for(int m=0; m<Amount_tp.size(); m++)
            {
                sum += Double.valueOf(Amount_tp.get(m));
            }
            updateSum(sum);
        }catch(Exception ex){ex.printStackTrace();}

        Global_Data.GLOVEL_LONG_DESC = "";
        Global_Data.GLOVEL_CATEGORY_SELECTION = "";
        Global_Data.GLOVEL_ITEM_MRP = "";
        try
        {
            ActionBar mActionBar = getActionBar();
            mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            LayoutInflater mInflater = LayoutInflater.from(this);

            View mCustomView = mInflater.inflate(R.layout.action_bar, null);
            mCustomView.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#910505")));
            TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.screenname);
//            mTitleTextView.setText(Global_Data.order_retailer +" "+"("+Global_Data.AmountOutstanding+"/"+Global_Data.AmountOverdue+")");
//            TextView todaysTarget = (TextView) mCustomView.findViewById(R.id.todaysTarget);
//            SharedPreferences sp = SubDealer_PreviewActivity.this.getSharedPreferences("SimpleLogic", 0);
//
//            try
//            {
//                int target  = (int) Math.round(sp.getFloat("Target",0));
//                int achieved  = (int) Math.round(sp.getFloat("Achived",0));
//                Float age_float = (sp.getFloat("Achived",0)/sp.getFloat("Target",0))*100;
//                if(String.valueOf(age_float).equalsIgnoreCase("infinity"))
//                {
//                    int age = (int) Math.round(age_float);
//
//                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+"infinity")+"%"+"]");
//                }else
//                {
//                    int age = (int) Math.round(age_float);
//
//                    todaysTarget.setText("T/A : Rs "+String.format(target+"/"+achieved+" ["+age)+"%"+"]");
//                }
//
//            }catch(Exception ex){ex.printStackTrace();}
//            if (sp.getFloat("Target", 0.00f)-sp.getFloat("Current_Target", 0.00f)<0) {
//
//                todaysTarget.setText("Today's Target Acheived");
//            }
            mActionBar.setCustomView(mCustomView);
            mActionBar.setDisplayShowCustomEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }catch(Exception ex){ex.printStackTrace();}

        adapter = new PackageAdapter(SubDealer_PreviewActivity.this, SwipeList);


        totalPrice=0.00f;

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

                    Intent order_home = new Intent(getApplicationContext(),SubDealer_Signature_Activity.class);
                    startActivity(order_home);
                    finish();

                    return true;
                }
                return false;
            }
        });

        buttonPreviewHome=(Button) findViewById(R.id.buttonPreviewHome);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(SubDealer_PreviewActivity.this).create(); //Read Update
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage(" Are you sure you want to cancel?");
                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Global_Data.GLOvel_SUB_GORDER_ID = "";
                            dbvoc.getDeleteTable("sub_orders");
                            dbvoc.getDeleteTable("sub_order_products");
                            Toast.makeText(SubDealer_PreviewActivity.this, "Order Canceled Successfully", Toast.LENGTH_SHORT).show();
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

        buttonPreviewAddMOre=(Button) findViewById(R.id.buttonPreviewAddMOre);
        buttonPreviewCheckout.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviewHome.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviewAddMOre.setBackgroundColor(Color.parseColor("#414042"));
        buttonPreviewAddMOre.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent i=new Intent(SubDealer_PreviewActivity.this, SubDealer_NewOrderActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                ActivitySwitcher.animationOut(findViewById(R.id.containerPreview), getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
                    @Override
                    public void onAnimationFinished() {
                        startActivity(i);
                        finish();
                    }
                });

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
        SharedPreferences spf=SubDealer_PreviewActivity.this.getSharedPreferences("SimpleLogic",0);
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
        if(statusOrderActivity.equalsIgnoreCase("previous")){

            AlertDialog alertDialog = new AlertDialog.Builder(SubDealer_PreviewActivity.this).create(); //Read Update
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
        else
        {
            finish();
        }
    }

    public static void updateSum(Double sum){
        DecimalFormat df = new DecimalFormat("#0.00");
        txttotalPreview.setText("Total		:		"+df.format(sum));
    }
}
