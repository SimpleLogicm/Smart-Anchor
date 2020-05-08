package com.anchor.activities;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.adapter.AnimalsAdapter;
import com.anchor.adapter.RankAdapter;
import com.anchor.adapter.RankDetailAdapter;
import com.anchor.adapter.RenlDetail_Dialog_Adapter;
import com.anchor.helper.ActualValueFormater;
import com.anchor.helper.CustomRenkViewDialog;
import com.anchor.model.RankDataModel;
import com.anchor.model.RankDetailModel;
import com.anchor.model.RenkDialogModel;
import com.anchor.slidingmenu.CalendarAct;
import com.anchor.webservice.ConnectionDetector;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment implements OnChartValueSelectedListener, RankAdapter.EventListener, AnimalsAdapter.GraphListListener, RankDetailAdapter.DialogViewListener {


    BufferedReader in = null;
    ProgressDialog dialog;
    String line;
    Cursor cursor;
    private DataBaseHelper dbHelper;
    public SQLiteDatabase db;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    TextView txtWelcomeUser;

    static int cityID, beatID, retailerID;
    int data_stateid, data_cityID;
    LoginDataBaseAdapter loginDataBaseAdapter;
    String data_beatID;
    DataBaseHelper dbvoc;
    View rootView;
    ImageView order, calendar, custom_serve, expenses, target, schedule, logout, pricing, add_retailernew, marketing_data, syncmdata;
    ImageView top_arrow;
    ImageView down_arrow;
    RelativeLayout main_my_view;
    TextView my_text, my_text2;
    TextView header_rank_text, my_header_text2;
    RelativeLayout main_bottomcard;
    boolean isUp;
    RecyclerView rank_list_recycleview, rank_ldetail_recycleview;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<RankDataModel> data;
    private static ArrayList<RankDetailModel> detailsdata;
    private static ArrayList<RenkDialogModel> renkDialogModels;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.Adapter detailadapter;
    CardView recycleviewr_header, recycleviewr_container, detail_view_container;
    PieChart r_piechart;
    ImageView r_arrow_left;
    Button r_details;
    ScrollView home_scroller;
    static String left_arrow_click = "";

    ImageView user_mm, user_header_mm;
    TextView rank_loading_message, r_total_value, rank_current_date;
    ProgressBar rank_progressBar, recycle_rank_progressBar, piechart_rank_progressBar, detils_rank_progressBar;
    private RequestQueue requestQueue;

    ArrayList<Entry> yvalues = new ArrayList<Entry>();
    ArrayList<String> xVals = new ArrayList<String>();
    ArrayList<String> hellodatatext = new ArrayList<String>();
    ArrayList<String> hellodatavalue = new ArrayList<String>();
    String service_flag = "";
    String month_rank = "";
    String m_ranks_date = "";
    String selected_day = "";
    Home context;
    Calendar myCalendar;
    PieChartView pieChartView;

    private RecyclerView rankgrid_recycleview;

    private RecyclerView.Adapter rankgridmAdapter;
    private RecyclerView.LayoutManager rankgridmLayoutManager;
    ArrayList<String> mDataSet = new ArrayList<String>();
    LinearLayout renk_text_container;
    JSONObject RjsonObject = null;


    public Home() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Global_Data.Stock_warehouse_flag = "";
        Global_Data.Stock_product_flag = "";
        Global_Data.Stock_product_flag_value_check = "";
        Global_Data.Stock_warehouse_flag_value_check = "";
        rootView = inflater.inflate(R.layout.fragment_logout, container, false);
        pieChartView = rootView.findViewById(R.id.chart);
        rankgrid_recycleview = rootView.findViewById(R.id.rankgridrecycler_view);

        order = rootView.findViewById(R.id.order);
        calendar = rootView.findViewById(R.id.calendar);
        //custom_serve=(ImageView)rootView.findViewById(R.id.custom_serve);
        expenses = rootView.findViewById(R.id.expenses);
        target = rootView.findViewById(R.id.target);
        pricing = rootView.findViewById(R.id.pricing);
        add_retailernew = rootView.findViewById(R.id.add_retailernew);
        marketing_data = rootView.findViewById(R.id.marketing_data);
        syncmdata = rootView.findViewById(R.id.syncmdata);
        txtWelcomeUser = rootView.findViewById(R.id.txtWelcomeUser);
        top_arrow = rootView.findViewById(R.id.my_button);
        down_arrow = rootView.findViewById(R.id.my_buttonfghf);
        main_my_view = rootView.findViewById(R.id.main_my_view);
        main_bottomcard = rootView.findViewById(R.id.main_bottomcard);
        my_text = rootView.findViewById(R.id.my_text);
        my_text2 = rootView.findViewById(R.id.my_text2);
        renk_text_container = rootView.findViewById(R.id.renk_text_container);
        header_rank_text = rootView.findViewById(R.id.header_rank_text);
        my_header_text2 = rootView.findViewById(R.id.my_header_text2);
        rank_list_recycleview = rootView.findViewById(R.id.rank_list_recycleview);
        rank_ldetail_recycleview = rootView.findViewById(R.id.rank_ldetail_recycleview);
        recycleviewr_header = rootView.findViewById(R.id.recycleviewr_header);
        recycleviewr_container = rootView.findViewById(R.id.recycleviewr_container);
        detail_view_container = rootView.findViewById(R.id.detail_view_container);
        r_piechart = rootView.findViewById(R.id.rr_piechart);
        r_arrow_left = rootView.findViewById(R.id.r_arrow_left);
        r_details = rootView.findViewById(R.id.r_details);
        home_scroller = rootView.findViewById(R.id.home_scroller);

        user_mm = rootView.findViewById(R.id.user_mm);
        user_header_mm = rootView.findViewById(R.id.user_header_mm);
        rank_loading_message = rootView.findViewById(R.id.rank_loading_message);
        r_total_value = rootView.findViewById(R.id.r_total_value);
        rank_current_date = rootView.findViewById(R.id.rank_current_date);
        rank_progressBar = rootView.findViewById(R.id.rank_progressBar);
        recycle_rank_progressBar = rootView.findViewById(R.id.recycle_rank_progressBar);
        piechart_rank_progressBar = rootView.findViewById(R.id.piechart_rank_progressBar);
        detils_rank_progressBar = rootView.findViewById(R.id.detils_rank_progressBar);

        // r_piechart.setUsePercentValues(true);

        r_piechart.setHoleRadius(50f);
        r_piechart.setTransparentCircleAlpha(0);
        r_piechart.setCenterTextSize(10);


        rank_list_recycleview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rank_list_recycleview.setLayoutManager(layoutManager);

        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(rank_list_recycleview.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider);
        horizontalDecoration.setDrawable(horizontalDivider);
        rank_list_recycleview.addItemDecoration(horizontalDecoration);

        rank_list_recycleview.setItemAnimator(new DefaultItemAnimator());


        rank_ldetail_recycleview.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        rank_ldetail_recycleview.setLayoutManager(layoutManager);

        DividerItemDecoration horizontalDecoration2 = new DividerItemDecoration(rank_ldetail_recycleview.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider2 = ContextCompat.getDrawable(getActivity(), R.drawable.horizontal_divider_detail);
        horizontalDecoration2.setDrawable(horizontalDivider2);

//        DividerItemDecoration verticalDecoration = new DividerItemDecoration(rank_ldetail_recycleview.getContext(),
//                DividerItemDecoration.HORIZONTAL);
//        Drawable verticalDivider = ContextCompat.getDrawable(getActivity(), R.drawable.vertical_divider_detils);
//        verticalDecoration.setDrawable(verticalDivider);
//        rank_ldetail_recycleview.addItemDecoration(verticalDecoration);

        rank_ldetail_recycleview.addItemDecoration(horizontalDecoration2);

        rank_ldetail_recycleview.setItemAnimator(new DefaultItemAnimator());

        try {
            SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", MODE_PRIVATE);
            String ranks = sp.getString("rank", "");
            String ranks_date = sp.getString("rank_date", "");
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(ranks)) {
                user_mm.setVisibility(View.VISIBLE);
                rank_loading_message.setVisibility(View.GONE);
                rank_progressBar.setVisibility(View.GONE);
                renk_text_container.setVisibility(View.VISIBLE);

                try {
                    my_text.setText(Html.fromHtml("Rank : " + ranks + "<sup>" + ordinal_suffix_of(Integer.parseInt(ranks)) + "</sup>"));
                    header_rank_text.setText(Html.fromHtml("Rank : " + ranks + "<sup>" + ordinal_suffix_of(Integer.parseInt(ranks)) + "</sup>"));
                    my_text2.setText(Html.fromHtml("Date : " + ranks_date));
                    my_header_text2.setText(Html.fromHtml("Date : " + ranks_date));
                } catch (Exception ex) {
                    my_text.setText(Html.fromHtml("Rank : " + ranks));
                    header_rank_text.setText(Html.fromHtml("Rank : " + ranks));
                    my_text2.setText(Html.fromHtml("Date : " + ranks_date));
                    my_header_text2.setText(Html.fromHtml("Date : " + ranks_date));

                    ex.printStackTrace();

                }

                if (ranks.equalsIgnoreCase("1")) {
                    user_mm.setBackgroundResource(R.drawable.rrank1);
                    user_header_mm.setBackgroundResource(R.drawable.rrank1);

                } else if (ranks.equalsIgnoreCase("2")) {
                    user_mm.setBackgroundResource(R.drawable.rrank2);
                    user_header_mm.setBackgroundResource(R.drawable.rrank2);
                } else if (ranks.trim().equalsIgnoreCase("") || ranks.trim().equalsIgnoreCase(null) || ranks.trim().equalsIgnoreCase("null") || ranks.trim().equalsIgnoreCase("0") || ranks.trim().equalsIgnoreCase("0.0") || ranks.trim().equalsIgnoreCase(" ")) {

                    my_text.setText(Html.fromHtml("Rank : NA"));
                    header_rank_text.setText(Html.fromHtml("Rank : NA"));

                    try {

                        my_text2.setText(Html.fromHtml("Date : " + ranks_date));
                        my_header_text2.setText(Html.fromHtml("Date : " + ranks_date));

                    } catch (Exception ex) {
                        my_text2.setText(Html.fromHtml("Date : " + ranks_date));
                        my_header_text2.setText(Html.fromHtml("Date : " + ranks_date));
                        ex.printStackTrace();
                    }

                } else {
                    user_mm.setBackgroundResource(R.drawable.rdrank3);
                    user_header_mm.setBackgroundResource(R.drawable.rdrank3);
                }

            } else {
                cd = new ConnectionDetector(getActivity());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    user_mm.setVisibility(View.GONE);
                    rank_loading_message.setVisibility(View.VISIBLE);
                    // rank_loading_message.setText("Rank Not Found,Click Arrow To Refresh");
                    rank_progressBar.setVisibility(View.VISIBLE);
                    renk_text_container.setVisibility(View.GONE);
                    // service_flag = "yes";
                    if (Global_Data.rank_service_call_flag.equalsIgnoreCase("TRUE")) {
                        getRankData();
                        Global_Data.rank_service_call_flag = "";
                    } else {
                        rank_loading_message.setText("Rank Not Found,Click Arrow To Refresh");
                        rank_progressBar.setVisibility(View.GONE);
                    }

                } else {
                    // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getActivity(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        } catch (Exception ex) {

        }


        data = new ArrayList<RankDataModel>();
        detailsdata = new ArrayList<RankDetailModel>();
        renkDialogModels = new ArrayList<RenkDialogModel>();

        context = this;

        adapter = new RankAdapter(data, getActivity(), this);
        rank_list_recycleview.setAdapter(adapter);

        detailadapter = new RankDetailAdapter(detailsdata, getActivity(), context);
        rank_ldetail_recycleview.setAdapter(detailadapter);


        main_my_view.setVisibility(View.INVISIBLE);


        isUp = false;


        Global_Data.GLObalOrder_id = "";
        Global_Data.GLOvel_ITEM_NUMBER = "";
        Global_Data.GLOvel_GORDER_ID_RETURN = "";
        Global_Data.GLObalOrder_id_return = "";
        Global_Data.GLOvel_GORDER_ID = "";
        Global_Data.target_amount = "";
        Global_Data.target_grpby = "";

        String user_name = "";
        if (!Global_Data.USER_FIRST_NAME.equalsIgnoreCase("null")) {
            user_name = Global_Data.USER_FIRST_NAME.trim();
            if (!Global_Data.USER_LAST_NAME.equalsIgnoreCase("null")) {
                user_name += " " + Global_Data.USER_LAST_NAME.trim();
            }
        }

        txtWelcomeUser.setText(user_name + " : " + Global_Data.emp_code);

        loginDataBaseAdapter = new LoginDataBaseAdapter(getActivity());
        loginDataBaseAdapter = loginDataBaseAdapter.open();

        Global_Data.CUSTOMER_SERVICE_FLAG = "";


        dbvoc = new DataBaseHelper(getActivity());
        cd = new ConnectionDetector(getActivity());

//        r_piechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//
//            @Override
//            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//                String employee = xVals.get(dataSetIndex);
//                Toast.makeText(getActivity(),  employee , Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onNothingSelected() {
//
//            }
//        });


        r_details.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                cd = new ConnectionDetector(getActivity());
                isInternetPresent = cd.isConnectingToInternet();
                if (isInternetPresent) {
                    left_arrow_click = "details";
                    detils_rank_progressBar.setVisibility(View.VISIBLE);
                    rank_ldetail_recycleview.setVisibility(View.GONE);
                    detail_view_container.setVisibility(View.VISIBLE);
                    r_details.setVisibility(View.GONE);
                    recycleviewr_header.setVisibility(View.GONE);
                    recycleviewr_container.setVisibility(View.GONE);
                    r_piechart.setVisibility(View.GONE);
                    pieChartView.setVisibility(View.GONE);

                    getRankDaySummary(selected_day);
                } else {
                    // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

                    Toast toast = Toast.makeText(getActivity(), "You don't have internet connection.", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });

        top_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                slideUp(main_my_view);

                //myButton.setText("Slide up");

                isUp = true;
            }
        });

        r_arrow_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (left_arrow_click.equalsIgnoreCase("details")) {
                    left_arrow_click = "piechart";
                    recycleviewr_header.setVisibility(View.GONE);
                    recycleviewr_container.setVisibility(View.GONE);
                    detail_view_container.setVisibility(View.GONE);
                    r_piechart.setVisibility(View.GONE);
                    pieChartView.setVisibility(View.VISIBLE);
                    r_details.setVisibility(View.VISIBLE);
                    r_arrow_left.setVisibility(View.VISIBLE);
                    home_scroller.setVisibility(View.GONE);
                    rank_current_date.setVisibility(View.INVISIBLE);
                } else {
                    recycleviewr_header.setVisibility(View.VISIBLE);
                    recycleviewr_container.setVisibility(View.VISIBLE);
                    detail_view_container.setVisibility(View.GONE);
                    rank_list_recycleview.setVisibility(View.VISIBLE);
                    recycle_rank_progressBar.setVisibility(View.GONE);
                    piechart_rank_progressBar.setVisibility(View.GONE);
                    r_piechart.setVisibility(View.GONE);
                    pieChartView.setVisibility(View.GONE);
                    r_arrow_left.setVisibility(View.GONE);
                    r_details.setVisibility(View.GONE);
                    home_scroller.setVisibility(View.GONE);

                    try {
                        my_text.setText(Html.fromHtml("Rank : " + month_rank.trim() + "<sup>" + ordinal_suffix_of(Integer.parseInt(month_rank.trim())) + "</sup>"));
                        header_rank_text.setText(Html.fromHtml("Rank : " + month_rank.trim() + "<sup>" + ordinal_suffix_of(Integer.parseInt(month_rank.trim())) + "</sup>"));
                        my_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                        my_header_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                    } catch (Exception ex) {
                        my_text.setText(Html.fromHtml("Rank : " + month_rank.trim()));
                        header_rank_text.setText(Html.fromHtml("Rank : " + month_rank.trim()));
                        my_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                        my_header_text2.setText(Html.fromHtml("Date : " + m_ranks_date));

                        ex.printStackTrace();

                    }
                    if (month_rank.trim().equalsIgnoreCase("1")) {
                        user_mm.setBackgroundResource(R.drawable.rrank1);
                        user_header_mm.setBackgroundResource(R.drawable.rrank1);

                    } else if (month_rank.trim().equalsIgnoreCase("2")) {
                        user_mm.setBackgroundResource(R.drawable.rrank2);
                        user_header_mm.setBackgroundResource(R.drawable.rrank2);
                    } else if (month_rank.trim().equalsIgnoreCase("") || month_rank.trim().equalsIgnoreCase(null) || month_rank.trim().equalsIgnoreCase("null") || month_rank.trim().equalsIgnoreCase("0") || month_rank.trim().equalsIgnoreCase("0.0") || month_rank.trim().equalsIgnoreCase(" ")) {

                        my_text.setText(Html.fromHtml("Rank : NA"));
                        header_rank_text.setText(Html.fromHtml("Rank : NA"));
                        my_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                        my_header_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                    } else {
                        user_mm.setBackgroundResource(R.drawable.rdrank3);
                        user_header_mm.setBackgroundResource(R.drawable.rdrank3);
                    }

                }

            }
        });

        down_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                recycleviewr_header.setVisibility(View.VISIBLE);
                recycleviewr_container.setVisibility(View.VISIBLE);
                r_piechart.setVisibility(View.GONE);
                pieChartView.setVisibility(View.GONE);
                r_details.setVisibility(View.GONE);
                r_arrow_left.setVisibility(View.GONE);
                r_details.setVisibility(View.GONE);
                home_scroller.setVisibility(View.GONE);

                slideDown(main_my_view);
                isUp = false;
            }
        });

        order.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Local_Data> contacts = dbvoc.checkCustomer();
                if (contacts.size() <= 0) {

                    Toast toast = Toast.makeText(getActivity(),
                            "No customers found. Please try Sync or contact IT team", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                } else {
                    Global_Data.CUSTOMER_SERVICE_FLAG = "";
                    Intent intent = new Intent(getActivity(), Sales_Dash.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    //getActivity().finish();
                }
            }
        });

        calendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

// 				 new Thread(new Runnable() {
//					   public void run() {
//					    	 Flwg();
//					      }
//					    }).start();
                Intent intent1 = new Intent(getActivity(), CalendarAct.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();
            }
        });

        expenses.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Expenses.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();

            }
        });

        pricing.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Pricing_Main.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();


            }
        });

        add_retailernew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = getActivity().getPackageManager().getLaunchIntentForPackage("subdealer.anchor.com.anchorsubdealer_registration");

                if (mIntent != null) {
                    try {

                        mIntent.putExtra("USER_EMAIL", Global_Data.GLOvel_USER_EMAIL);
                        mIntent.putExtra("USER_ID", Global_Data.GLOVEL_USER_ID);
                        mIntent.putExtra("USER_FIRST_NAME", Global_Data.USER_FIRST_NAME);
                        mIntent.putExtra("USER_LAST_NAME", Global_Data.USER_LAST_NAME);
                        mIntent.putExtra("USER_imei_no", Global_Data.imei_no);
                        startActivity(mIntent);

                    } catch (ActivityNotFoundException err) {
                        err.printStackTrace();
                        Toast.makeText(getActivity(), "Please install bar", Toast.LENGTH_SHORT).show();
//
                        try {
                            final String appPackageName = "subdealer.anchor.com.anchorsubdealer_registration"; // Can also use getPackageName(), as below
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(getActivity(), "App Not available in google play.", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    try {
                        final String appPackageName = "subdealer.anchor.com.anchorsubdealer_registration"; // Can also use getPackageName(), as below
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Toast.makeText(getActivity(), "App Not available in google play.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        marketing_data.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Marketing.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();

            }
        });


        syncmdata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AllOrders_Sync.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                //getActivity().finish();

            }
        });

        target.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                isInternetPresent = cd.isConnectingToInternet();
                Intent i = new Intent(getActivity(), Target_REYC_Main.class);
                startActivity(i);
                getActivity().finish();


            }
        });


        return rootView;
    }

    // slide the view from below itself to the current position
    public void slideUp(View view) {

        cd = new ConnectionDetector(getActivity());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            view.setVisibility(View.VISIBLE);
            main_bottomcard.setVisibility(View.GONE);
            home_scroller.setVisibility(View.GONE);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    view.getHeight(),  // fromYDelta
                    0);                // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            view.startAnimation(animate);

            if (service_flag.equalsIgnoreCase("")) {
                getRankData();
            }
        } else {
            // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

            Toast toast = Toast.makeText(getActivity(), "You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    // slide the view from its current position to below itself
    public void slideDown(View view) {

        main_bottomcard.setVisibility(View.VISIBLE);
        home_scroller.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(
                0,                 // fromXDelta
                0,                 // toXDelta
                0,                 // fromYDelta
                view.getHeight()); // toYDelta
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        // main_my_view.setVisibility(View.GONE);

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    // slide the view from its current position to below itself
    public void slideDownnew() {
        if (isUp == true) {
            isUp = false;
//            main_my_view.setVisibility(View.GONE);
//            main_bottomcard.setVisibility(View.VISIBLE);
//            recycleviewr_header.setVisibility(View.VISIBLE);
//            recycleviewr_container.setVisibility(View.VISIBLE);
//            home_scroller.setVisibility(View.VISIBLE);
//            r_piechart.setVisibility(View.GONE);
//            r_arrow_left.setVisibility(View.GONE);
//            r_pi_date.setVisibility(View.GONE);

            main_bottomcard.setVisibility(View.VISIBLE);
            home_scroller.setVisibility(View.VISIBLE);
            rank_current_date.setVisibility(View.INVISIBLE);

            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    0,                 // fromYDelta
                    main_my_view.getHeight()); // toYDelta
            animate.setDuration(500);
            animate.setFillAfter(true);
            main_my_view.startAnimation(animate);

            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);

        }

    }

    public void updateRankData() {

    }

    public void pieChartData(String date) {


        PieDataSet dataSet = new PieDataSet(yvalues, "");

        //dataSet.setSliceSpace(10);
        // dataSet.setSliceSpace(2);
        dataSet.setValueTextSize(14);
        dataSet.setSliceSpace(3f);

        dataSet.setSelectionShift(5f);

        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setValueLinePart1OffsetPercentage(50f); /** When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size */
        dataSet.setValueLinePart1Length(0.2f); /** When valuePosition is OutsideSlice, indicates length of first half of the line */
        dataSet.setValueLinePart2Length(0.2f);


        PieData data = new PieData(xVals, dataSet);

        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);


        // data.getYValueSum();
        // In Percentage
        //data.setValueFormatter(new PercentFormatter());
        // Default value
        // data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueFormatter(new ActualValueFormater());
        r_piechart.setData(data);
        // r_piechart.setCenterTextRadiusPercent(20);
        r_piechart.setDescription("Rank Data");
        r_piechart.setDrawHoleEnabled(true);
        r_piechart.setTransparentCircleRadius(58f);


        //add colors to dataset
//        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.GRAY);
//        colors.add(Color.BLUE);
//        colors.add(Color.RED);
//        colors.add(Color.GREEN);
//        colors.add(Color.CYAN);
//        colors.add(Color.YELLOW);
//        colors.add(Color.MAGENTA);
//
//        dataSet.setColors(colors);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.BLACK);

        data.setDrawValues(false);
        r_piechart.setDrawSliceText(false);


        r_piechart.highlightValues(null);
        r_piechart.setCenterText(date);
        r_piechart.setCenterTextSize(14);


        //add legend to chart
        Legend legend = r_piechart.getLegend();
        legend.setWordWrapEnabled(true);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);

        r_piechart.setExtraBottomOffset(20f);
        r_piechart.setExtraLeftOffset(20f);
        r_piechart.setExtraRightOffset(20f);

        r_piechart.getLegend().setEnabled(false);

        // r_piechart.setBackgroundColor(Color.TRANSPARENT); //set whatever color you prefer
        // r_piechart.setDrawGridBackground(false);

        // r_piechart.setMinAngleForSlices(36f);

        r_piechart.invalidate();

        r_piechart.animateXY(1400, 1400);


        // r_piechart.setUsePercentValues(true);
        // r_piechart.setDrawValues(false);
        // r_piechart.setDrawSliceText(false);


        r_piechart.setOnChartValueSelectedListener(this);

//        r_piechart.setCenterText("BMI:"+df.format(bmi));


    }

    public void helloChart(String date) {
        List pieData = new ArrayList<>();
        List<Integer> piecolors = new ArrayList<>();
        piecolors.add(getResources().getColor(R.color.chart_color1));
        piecolors.add(getResources().getColor(R.color.chart_color2));
        piecolors.add(getResources().getColor(R.color.chart_color3));
        piecolors.add(getResources().getColor(R.color.chart_color4));
        piecolors.add(getResources().getColor(R.color.chart_color5));
        piecolors.add(getResources().getColor(R.color.chart_color6));
        piecolors.add(getResources().getColor(R.color.chart_color7));
        piecolors.add(getResources().getColor(R.color.chart_color8));
        piecolors.add(getResources().getColor(R.color.chart_color9));
        piecolors.add(getResources().getColor(R.color.chart_color10));
        piecolors.add(getResources().getColor(R.color.chart_color11));

        for (int i = 0; i < hellodatatext.size(); i++) {
            //Math.round(Float.valueOf(hellodatavalue.get(i)))

            pieData.add(new SliceValue(20, piecolors.get(i)).setLabel(hellodatatext.get(i) + "\n" + hellodatavalue.get(i)));
        }
//
//        pieData.add(new SliceValue(25, Color.GRAY).setLabel("dsfsfsdfsdf: 4"));
//        pieData.add(new SliceValue(10, Color.RED).setLabel("sfsfsf: 2"));
//        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("sfsfsf: 8"));
//        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("dfsdfs: 2"));
//        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("sfsf: 1"));
//        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("sdfsdf: 0"));
//        pieData.add(new SliceValue(60, Color.MAGENTA).setLabel("Qs4: 0"));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1(date).setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
        pieChartView.setPieChartData(pieChartData);
    }


    public boolean isUpStatus() {
        return isUp;
    }


    @Override
    public void onResume() {
        super.onResume();

//        if(getView() == null){
//            return;
//        }
//
//        getView().setFocusableInTouchMode(true);
//        getView().requestFocus();
//        getView().setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//
//                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
//                    if(isUp == true)
//                    {
//                        slideDown(main_my_view);
//                        isUp = false;
//                    }
//
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);

    }

    @Override
    public void onNothingSelected() {

        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onEvent(String date, String Rank) {

        selected_day = date;
        cd = new ConnectionDetector(getActivity());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            left_arrow_click = "piechart";
            recycleviewr_header.setVisibility(View.GONE);
            recycleviewr_container.setVisibility(View.GONE);
            detail_view_container.setVisibility(View.GONE);

            r_arrow_left.setVisibility(View.VISIBLE);
            //  r_pi_date.setVisibility(View.VISIBLE);

            piechart_rank_progressBar.setVisibility(View.VISIBLE);

            getchartData(date);

            try {

                header_rank_text.setText(Html.fromHtml("Rank : " + Rank.trim() + "<sup>" + ordinal_suffix_of(Integer.parseInt(Rank.trim())) + "</sup>"));
                my_header_text2.setText(Html.fromHtml("Date : " + date));
            } catch (Exception ex) {
                header_rank_text.setText(Html.fromHtml("Rank : " + Rank.trim()));
                my_header_text2.setText(Html.fromHtml("Date : " + date));

                ex.printStackTrace();

            }
            if (Rank.trim().equalsIgnoreCase("1")) {
                user_header_mm.setBackgroundResource(R.drawable.rrank1);

            } else if (Rank.trim().equalsIgnoreCase("2")) {
                user_header_mm.setBackgroundResource(R.drawable.rrank2);
            } else if (Rank.trim().equalsIgnoreCase("") || Rank.trim().equalsIgnoreCase(null) || Rank.trim().equalsIgnoreCase("null") || Rank.trim().equalsIgnoreCase("0") || Rank.trim().equalsIgnoreCase("0.0") || Rank.trim().equalsIgnoreCase(" ")) {

                header_rank_text.setText(Html.fromHtml("Rank : NA"));
                my_header_text2.setText(Html.fromHtml("Date : " + date));
            } else {
                user_header_mm.setBackgroundResource(R.drawable.rdrank3);
            }


        } else {
            // Toast.makeText(getApplicationContext(),"You don't have internet connection.",Toast.LENGTH_LONG).show();

            Toast toast = Toast.makeText(getActivity(), "You don't have internet connection.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }


    }

    public void getRankData() {

        service_flag = "yes";
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        String device_id = sp.getString("devid", "");

        String user_email = "";

        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            String domain = getResources().getString(R.string.service_domain);
            String url = domain + "dms_rankings/rank_dashboard?email=" + user_email + "&date=" + "";

            Log.i("volley", "email: " + user_email);
            Log.i("rank url", "rank url " + url);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());

                    try {
                        String response_result = "";
                        if (response.has("message")) {
                            response_result = response.getString("message");
                            Toast toast = Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            service_flag = "";
                            user_mm.setVisibility(View.VISIBLE);
                            rank_loading_message.setVisibility(View.GONE);
                            rank_progressBar.setVisibility(View.GONE);
                            renk_text_container.setVisibility(View.VISIBLE);
                            slideDown(main_my_view);
                            isUp = false;
                        } else {

                            month_rank = "";
                            m_ranks_date = "";
                            data = new ArrayList<RankDataModel>();
                            JSONArray data1 = response.getJSONArray("records");
                            Log.i("volley", "response reg data1 Length: " + data1.length());
                            Log.d("data1", "data1" + data1.toString());

                            if (data1.length() > 0) {
                                for (int i = 0; i < data1.length(); i++) {

                                    JSONObject jsonObject = data1.getJSONObject(i);


                                    try {
                                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                                        Date date = sdf.parse(Check_Null_Value.ranknullcheck(jsonObject.getString("date")));
                                        String myFormatnew = "dd-MM-yyyy";
                                        SimpleDateFormat datenew = new SimpleDateFormat(myFormatnew);

                                        if (i == 0) {
                                            month_rank = Check_Null_Value.ranknullcheck(jsonObject.getString("day_rank"));
                                            m_ranks_date = datenew.format(date);
                                        }

                                        data.add(new RankDataModel(datenew.format(date),
                                                Check_Null_Value.ranknullcheck(jsonObject.getString("score")),
                                                Check_Null_Value.ranknullcheck(jsonObject.getString("day_rank"))
                                        ));
                                    } catch (Exception ex) {
                                        if (i == 0) {
                                            month_rank = Check_Null_Value.ranknullcheck(jsonObject.getString("day_rank"));
                                            m_ranks_date = Check_Null_Value.ranknullcheck(jsonObject.getString("date"));
                                        }
                                        data.add(new RankDataModel(
                                                Check_Null_Value.ranknullcheck(jsonObject.getString("date")),
                                                Check_Null_Value.ranknullcheck(jsonObject.getString("score")),
                                                Check_Null_Value.ranknullcheck(jsonObject.getString("day_rank"))
                                        ));
                                        ex.printStackTrace();
                                    }


                                    adapter = new RankAdapter(data, getActivity(), context);
                                    rank_list_recycleview.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                                }

                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Rank Not Found", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                slideDown(main_my_view);
                                isUp = false;
                            }


                            if (!month_rank.equalsIgnoreCase("")) {
                                SharedPreferences spf = getActivity().getSharedPreferences("SimpleLogic", 0);
                                SharedPreferences.Editor editor = spf.edit();
                                editor.putString("rank", month_rank.trim());
                                editor.putString("rank_date", m_ranks_date);

                                editor.commit();

                                try {
                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(month_rank.trim())) {
                                        user_mm.setVisibility(View.VISIBLE);
                                        rank_loading_message.setVisibility(View.GONE);
                                        rank_progressBar.setVisibility(View.GONE);
                                        renk_text_container.setVisibility(View.VISIBLE);

                                        try {
                                            my_text.setText(Html.fromHtml("Rank : " + month_rank.trim() + "<sup>" + ordinal_suffix_of(Integer.parseInt(month_rank.trim())) + "</sup>"));
                                            header_rank_text.setText(Html.fromHtml("Rank : " + month_rank.trim() + "<sup>" + ordinal_suffix_of(Integer.parseInt(month_rank.trim())) + "</sup>"));
                                            my_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                                            my_header_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                                        } catch (Exception ex) {
                                            my_text.setText(Html.fromHtml("Rank : " + month_rank.trim()));
                                            header_rank_text.setText(Html.fromHtml("Rank : " + month_rank.trim()));
                                            my_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                                            my_header_text2.setText(Html.fromHtml("Date : " + m_ranks_date));

                                            ex.printStackTrace();

                                        }
                                        if (month_rank.trim().equalsIgnoreCase("1")) {
                                            user_mm.setBackgroundResource(R.drawable.rrank1);
                                            user_header_mm.setBackgroundResource(R.drawable.rrank1);

                                        } else if (month_rank.trim().equalsIgnoreCase("2")) {
                                            user_mm.setBackgroundResource(R.drawable.rrank2);
                                            user_header_mm.setBackgroundResource(R.drawable.rrank2);
                                        } else if (month_rank.trim().equalsIgnoreCase("") || month_rank.trim().equalsIgnoreCase(null) || month_rank.trim().equalsIgnoreCase("null") || month_rank.trim().equalsIgnoreCase("0") || month_rank.trim().equalsIgnoreCase("0.0") || month_rank.trim().equalsIgnoreCase(" ")) {

                                            my_text.setText(Html.fromHtml("Rank : NA"));
                                            header_rank_text.setText(Html.fromHtml("Rank : NA"));
                                            my_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                                            my_header_text2.setText(Html.fromHtml("Date : " + m_ranks_date));
                                        } else {
                                            user_mm.setBackgroundResource(R.drawable.rdrank3);
                                            user_header_mm.setBackgroundResource(R.drawable.rdrank3);
                                        }

                                        rank_list_recycleview.setVisibility(View.VISIBLE);
                                        recycle_rank_progressBar.setVisibility(View.GONE);

                                    } else {
                                        Toast toast = Toast.makeText(getActivity(), "Rank Not Found", Toast.LENGTH_LONG);
                                        toast.setGravity(Gravity.CENTER, 0, 0);
                                        toast.show();
                                        slideDown(main_my_view);
                                        isUp = false;
                                    }
                                } catch (Exception ex) {
                                    service_flag = "";
                                    user_mm.setVisibility(View.VISIBLE);
                                    rank_loading_message.setVisibility(View.GONE);
                                    rank_progressBar.setVisibility(View.GONE);
                                    renk_text_container.setVisibility(View.VISIBLE);
                                    ex.printStackTrace();
                                }


                            }

                            service_flag = "";


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        service_flag = "";
                        user_mm.setVisibility(View.VISIBLE);
                        rank_loading_message.setVisibility(View.GONE);
                        rank_progressBar.setVisibility(View.GONE);
                        renk_text_container.setVisibility(View.VISIBLE);

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);
                    service_flag = "";
                    user_mm.setVisibility(View.VISIBLE);
                    rank_loading_message.setVisibility(View.GONE);
                    rank_progressBar.setVisibility(View.GONE);
                    renk_text_container.setVisibility(View.VISIBLE);
                    //Toast.makeText(MainActivity.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(MainActivity.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//                    if(dialog != null || dialog.isShowing())
//                    {
//                        dialog.dismiss();
//                    }

                }
            });


            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getActivity());
                Log.d("new error", "Setting a new request queue");
            }
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 050000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            service_flag = "";
            user_mm.setVisibility(View.VISIBLE);
            rank_loading_message.setVisibility(View.GONE);
            rank_progressBar.setVisibility(View.GONE);
            renk_text_container.setVisibility(View.VISIBLE);
            e.printStackTrace();
//            if(dialog != null || dialog.isShowing())
//            {
//                dialog.dismiss();
//            }


        }
    }


    public void getchartData(final String dates) {

        service_flag = "yes";
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        String device_id = sp.getString("devid", "");

        String user_email = "";

        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            String domain = getResources().getString(R.string.service_domain);
            // String url = "https://mumuatsmadms01.anchor-group.in/metal/api/v1/dms_rankings/daily_details_graph_of_rank?email=dnyanada.patil@simplelogic.in&date";
            String url = domain + "dms_rankings/daily_details_graph_of_rank?email=" + user_email + "&date=" + dates;

            Log.i("volley", "email: " + user_email);
            Log.i("chart url", "chart url " + url);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());


                    try {

                        String response_result = "";
                        if (response.has("message")) {
                            response_result = response.getString("message");
                            Toast toast = Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            recycle_rank_progressBar.setVisibility(View.GONE);
                        } else {

                            yvalues = new ArrayList<Entry>();
                            xVals = new ArrayList<String>();
                            hellodatatext = new ArrayList<String>();
                            hellodatavalue = new ArrayList<String>();
                            mDataSet.clear();


                            JSONArray chartdata = response.getJSONArray("record");


                            Log.i("volley", "response reg chartdata Length: " + chartdata.length());
                            Log.d("chartdata", "chartdata" + chartdata.toString());

                            if (chartdata.length() > 0) {
                                for (int i = 0; i < chartdata.length(); i++) {

                                    JSONObject jsonObject = chartdata.getJSONObject(i);

                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewzpochecck_b(jsonObject.getString("value"))) {
                                        yvalues.add(new Entry(Float.valueOf(Check_Null_Value.ranknullcheckfloat(jsonObject.getString("value"))), i));
                                        xVals.add(Check_Null_Value.ranknullcheck(jsonObject.getString("name")));
                                        hellodatatext.add(Check_Null_Value.ranknullcheckfloat(jsonObject.getString("name")));
                                        hellodatavalue.add(Check_Null_Value.ranknullcheckfloat(jsonObject.getString("value")));
                                        mDataSet.add(jsonObject.getString("name"));

                                    }
//                                    else
//                                    {
//                                        yvalues.add(new Entry(Float.valueOf(Check_Null_Value.ranknullcheckfloat("7")), i));
//                                        xVals.add(Check_Null_Value.ranknullcheck(jsonObject.getString("name")));
//                                    }


                                }


                                r_details.setVisibility(View.VISIBLE);
                                //r_piechart.setVisibility(View.VISIBLE);
                                pieChartView.setVisibility(View.VISIBLE);
                                piechart_rank_progressBar.setVisibility(View.GONE);
                                // pieChartData(dates);
                                helloChart(dates);

                                rankgridmLayoutManager = new GridLayoutManager(getActivity(), 4);
                                rankgrid_recycleview.setLayoutManager(rankgridmLayoutManager);

                                // Initialize a new instance of RecyclerView Adapter instance
                                rankgridmAdapter = new AnimalsAdapter(getActivity(), mDataSet, context);
                                rankgrid_recycleview.setAdapter(rankgridmAdapter);
                                rankgridmAdapter.notifyDataSetChanged();

                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                recycleviewr_header.setVisibility(View.VISIBLE);
                                recycleviewr_container.setVisibility(View.VISIBLE);
                                detail_view_container.setVisibility(View.GONE);
                                r_piechart.setVisibility(View.GONE);
                                pieChartView.setVisibility(View.GONE);
                                r_arrow_left.setVisibility(View.GONE);
                                r_details.setVisibility(View.GONE);
                                home_scroller.setVisibility(View.GONE);
                                piechart_rank_progressBar.setVisibility(View.GONE);
                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        recycle_rank_progressBar.setVisibility(View.GONE);

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);
                    recycle_rank_progressBar.setVisibility(View.GONE);


                }
            });


            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getActivity());
                Log.d("new error", "Setting a new request queue");
            }
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 050000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            recycle_rank_progressBar.setVisibility(View.GONE);


        }
    }

    public void getRankDaySummary(String dates) {

        service_flag = "yes";
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        String device_id = sp.getString("devid", "");

        String user_email = "";

        try {
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            String domain = getResources().getString(R.string.service_domain);
            String url = domain + "dms_rankings/daily_rank_details_of_user?email=" + user_email + "&date=" + dates;

            Log.i("volley", "email: " + user_email);
            Log.i("rank url", "rank url " + url);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());

                    try {


                        String response_result = "";
                        if (response.has("message")) {
                            response_result = response.getString("message");
                            Toast toast = Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            detils_rank_progressBar.setVisibility(View.GONE);
                            rank_ldetail_recycleview.setVisibility(View.GONE);
                        } else {


                            double total = 0;
                            detailsdata = new ArrayList<RankDetailModel>();
                            JSONArray data2 = response.getJSONArray("record");
                            Log.i("volley", "response reg data2 Length: " + data2.length());
                            Log.d("data2", "data2" + data2.toString());

                            if (data2.length() > 0) {
                                for (int i = 0; i < data2.length(); i++) {

                                    JSONObject jsonObject = data2.getJSONObject(i);

                                    detailsdata.add(new RankDetailModel(
                                            Check_Null_Value.ranknullcheck(jsonObject.getString("parameter")),
                                            Check_Null_Value.ranknullcheck(jsonObject.getString("weightage")),
                                            Check_Null_Value.ranknullcheck(jsonObject.getString("obtained")),
                                            Check_Null_Value.ranknullcheck(jsonObject.getString("calculated"))
                                    ));


                                }
                                if (response.has("call_cut_off")) {
                                    try {
                                        rank_current_date.setVisibility(View.VISIBLE);
                                        myCalendar = Calendar.getInstance();
                                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                                        //rank_current_date.setText("Date : "+sdf.format(myCalendar.getTime()) + "\n"+"Cut-off value for Calls: "+Check_Null_Value.ranknullcheckfloat(response.getString("call_cut_off")));
                                        rank_current_date.setText("Cut-off value for Calls: " + Check_Null_Value.ranknullcheckfloat(response.getString("call_cut_off")));

                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }

                                if (response.has("total")) {
                                    try {
                                        total = total + Double.valueOf(Check_Null_Value.ranknullcheckfloat(response.getString("total")));
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }

                                }

                                r_total_value.setText(String.valueOf(total));
                                detils_rank_progressBar.setVisibility(View.GONE);
                                rank_ldetail_recycleview.setVisibility(View.VISIBLE);
                                detailadapter = new RankDetailAdapter(detailsdata, getActivity(), context);
                                rank_ldetail_recycleview.setAdapter(detailadapter);
                                detailadapter.notifyDataSetChanged();

                            } else {
                                Toast toast = Toast.makeText(getActivity(), "Data Not Found", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                left_arrow_click = "piechart";
                                recycleviewr_header.setVisibility(View.GONE);
                                recycleviewr_container.setVisibility(View.GONE);
                                detail_view_container.setVisibility(View.GONE);
                                // r_piechart.setVisibility(View.VISIBLE);
                                pieChartView.setVisibility(View.VISIBLE);
                                r_details.setVisibility(View.VISIBLE);
                                r_arrow_left.setVisibility(View.VISIBLE);
                                home_scroller.setVisibility(View.GONE);
                            }


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        service_flag = "";
                        user_mm.setVisibility(View.VISIBLE);
                        rank_loading_message.setVisibility(View.GONE);
                        rank_progressBar.setVisibility(View.GONE);
                        renk_text_container.setVisibility(View.VISIBLE);

                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("volley", "error: " + error);
                    service_flag = "";
                    user_mm.setVisibility(View.VISIBLE);
                    rank_loading_message.setVisibility(View.GONE);
                    rank_progressBar.setVisibility(View.GONE);
                    renk_text_container.setVisibility(View.VISIBLE);
                    //Toast.makeText(MainActivity.this, "Some server error occur Please Contact it team.", Toast.LENGTH_LONG).show();
//					Toast toast = Toast.makeText(MainActivity.this, "Some server error occurred. Please Contact IT team.", Toast.LENGTH_LONG);
//					toast.setGravity(Gravity.CENTER, 0, 0);
//					toast.show();
//                    if(dialog != null || dialog.isShowing())
//                    {
//                        dialog.dismiss();
//                    }

                }
            });


            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getActivity());
                Log.d("new error", "Setting a new request queue");
            }
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 050000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            service_flag = "";
            user_mm.setVisibility(View.VISIBLE);
            rank_loading_message.setVisibility(View.GONE);
            rank_progressBar.setVisibility(View.GONE);
            renk_text_container.setVisibility(View.VISIBLE);
            e.printStackTrace();
//            if(dialog != null || dialog.isShowing())
//            {
//                dialog.dismiss();
//            }


        }
    }

    public void getRankDaySummarydailog(final String parameter_name) {

        String type = "";
        service_flag = "yes";
        SharedPreferences sp = getActivity().getSharedPreferences("SimpleLogic", MODE_PRIVATE);
        String device_id = sp.getString("devid", "");

        String user_email = "";

        try {
             type  = parameter_name.replaceAll("[^a-zA-Z0-9 ]", "");
            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(String.valueOf(sp.getString("USER_EMAIL", "")))) {
                user_email = sp.getString("USER_EMAIL", "");
            } else {
                user_email = Global_Data.GLOvel_USER_EMAIL;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {

            String domain = getResources().getString(R.string.service_domain);
            String url = domain + "dms_rankings/rank_dashboard_details?email=" + user_email + "&date=" + selected_day + "&type=" + URLEncoder.encode(type.trim(), "UTF-8");

            Log.i("volley", "email: " + user_email);
            Log.i("rank url", "rank url " + url);
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    Log.i("volley", "response: " + response);
                    //  Log.i("volley", "response reg Length: " + response.length());

                    try {


                        String response_result = "";
                        if (response.has("message")) {
                            response_result = response.getString("message");
                            Toast toast = Toast.makeText(getActivity(), response_result, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            detils_rank_progressBar.setVisibility(View.GONE);

                        } else {

                            detailsdata = new ArrayList<RankDetailModel>();
//                            JSONArray data2 = response.getJSONArray("record");
//                            Log.i("volley", "response reg data2 Length: " + data2.length());
//                            Log.d("data2", "data2" + data2.toString());
                            renkDialogModels.clear();
                           // RjsonObject = response.getJSONObject("recources");
                            JSONArray record = response.getJSONArray("record");
                            Log.i("volley", "response record Length: " + record.length());
                            Log.d("volley", "record" + record.toString());


                            // Log.i("volley", "response recources Length: " + RjsonObject.toString());

                            if (record.length() <= 0) {

                                Toast toast = Toast.makeText(getActivity(), "Record Not Found.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                detils_rank_progressBar.setVisibility(View.GONE);

                            } else {

                                for (int i = 0; i < record.length(); i++) {
                                    JSONObject jsonObject = record.getJSONObject(i);

//                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(RjsonObject.get(RjsonObject.names().getString(i)).toString())) {
//                                        renkDialogModels.add(new RenkDialogModel(RjsonObject.names().getString(i).toString(), RjsonObject.get(RjsonObject.names().getString(i)).toString()));
//
//                                    }
//                                   else
//                                    {
//                                        renkDialogModels.add(new RenkDialogModel(RjsonObject.names().getString(i).toString(), ""));
//
//                                    }

                                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavanewwithzeron(jsonObject.getString("value"))) {
                                        renkDialogModels.add(new RenkDialogModel(jsonObject.getString("key"), jsonObject.getString("value")));

                                    }
                                    else
                                    {
                                        renkDialogModels.add(new RenkDialogModel(jsonObject.getString("key"), " "));

                                    }


                                }
                                detils_rank_progressBar.setVisibility(View.GONE);
                                RenlDetail_Dialog_Adapter dataAdapter = new RenlDetail_Dialog_Adapter(renkDialogModels);
                                CustomRenkViewDialog customDialog = new CustomRenkViewDialog(getActivity(), dataAdapter, parameter_name);

                                customDialog.show();
                                customDialog.setCanceledOnTouchOutside(false);

                            }


                        }

                        detils_rank_progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        detils_rank_progressBar.setVisibility(View.GONE);
                        e.printStackTrace();


                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    detils_rank_progressBar.setVisibility(View.GONE);
                    Log.i("volley", "error: " + error);


                }
            });


            if (requestQueue == null) {
                requestQueue = Volley.newRequestQueue(getActivity());
                Log.d("new error", "Setting a new request queue");
            }
            jsObjRequest.setShouldCache(false);
            int socketTimeout = 050000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            jsObjRequest.setRetryPolicy(policy);
            requestQueue.add(jsObjRequest);

        } catch (Exception e) {
            detils_rank_progressBar.setVisibility(View.GONE);
            e.printStackTrace();

        }
    }

    @Override
    public void GraphListListener(String data, int index) {


        r_piechart.highlightValue(index, index, true);
        //data.setDrawValues(false);
        //  r_piechart.setDrawSliceText(true);
        r_piechart.invalidate();

    }

    public String ordinal_suffix_of(int i) {
        int j = i % 10,
                k = i % 100;
        if (j == 1 && k != 11) {
            return "st";
        }
        if (j == 2 && k != 12) {
            return "nd";
        }
        if (j == 3 && k != 13) {
            return "rd";
        }
        return "th";
    }

    @Override
    public void onClickDialog(String data) {

        detils_rank_progressBar.setVisibility(View.VISIBLE);
        getRankDaySummarydailog(data);


    }
}
