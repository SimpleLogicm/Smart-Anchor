package com.anchor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ScrollView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;

import static android.content.Context.MODE_PRIVATE;

public class AutocompleteAdapternew extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> data;
    Context mcontext;
    CustomerAutoAdapter spinner_list_adapter;
    // RecyclerView recyclerView;
    DataBaseHelper dbvoc;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    SharedPreferences sp;
    RecyclerView recyclerView;
    CustomerAutoAdapter customerAutoAdapter;

    public AutocompleteAdapternew(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.data = new ArrayList<>();
        this.mcontext = context;
        dbvoc = new DataBaseHelper(mcontext);
        sp = this.mcontext.getSharedPreferences("SimpleLogic", MODE_PRIVATE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                recyclerView = ((Activity) mcontext).findViewById(R.id.auto_recycleview);

                if (constraint != null ) {

                    try {

                        Global_Data.Customers.clear();
                        Global_Data.Customers_map.clear();
                        Global_Data.spiner_list_modelListn.clear();
                        // recyclerView.getRecycledViewPool().clear();
                        Log.d("SearchS", "SearchS " + constraint.toString());
                        ArrayList<String> suggestions = new ArrayList<>();
                        cd = new ConnectionDetector(mcontext);
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {

                            if(!Global_Data.state_code.equalsIgnoreCase(""))
                            {
                                HttpURLConnection conn = null;
                                InputStream input = null;

                                Log.d("SearchS", "SearchS " + constraint.toString());
                                Log.d("SearchS", "SearchS URL" + mcontext.getResources().getString(R.string.service_domain) + "customers/get_statewise_customers?search_string=" + constraint.toString() + "&state_code=" + Global_Data.state_code);

                                URL url = new URL(mcontext.getResources().getString(R.string.service_domain) + "customers/get_statewise_customers?search_string=" + constraint.toString() + "&state_code=" + Global_Data.state_code);
                                conn = (HttpURLConnection) url.openConnection();
                                input = conn.getInputStream();
                                InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
                                BufferedReader buffer = new BufferedReader(reader, 8192);
                                StringBuilder builder = new StringBuilder();
                                String line;
                                while ((line = buffer.readLine()) != null) {
                                    builder.append(line);
                                }

                                String response_result = "";
                                JSONObject json = new JSONObject(new JSONTokener(builder.toString()));

                                if (json.has("message")) {
                                    response_result = json.getString("message");
                                    Log.d("volley", "response_result" + response_result);
                                } else {
                                    List<Local_Data> ress = new ArrayList();
                                    JSONArray customers = json.getJSONArray("customers");
                                    Log.i("volley", "response serch Result Length: " + customers.length());
                                    Log.d("volley", "serch Result" + customers.toString());

                                    if (customers.length() > 0) {
                                        //Global_Data.spiner_list_modelList.clear();
                                        for (int ind = 0; ind < customers.length(); ind++) {
                                            JSONObject object = customers.getJSONObject(ind);

                                            Local_Data di = new Local_Data();
                                            di.name = object.getString("shop_name");
                                            di.Address = object.getString("address");

                                            Global_Data.Customers.add(object.getString("shop_name"));
                                            Global_Data.Customers_map.put(object.getString("shop_name"),object.getString("id"));

                                            ress.add(di);

                                            String pro_varient = object.getString("shop_name");
                                            String term = pro_varient;
                                            suggestions.add(term);


                                        }


                                        results.values = ress;
                                        results.count = ress.size();

                                        HashSet<Local_Data> hashSet = new HashSet<Local_Data>();
                                        hashSet.addAll(ress);
                                        ress.clear();
                                        ress.addAll(hashSet);
                                        Global_Data.spiner_list_modelListn = ress;
                                        ((Activity) mcontext).runOnUiThread(new Runnable() {
                                            public void run() {


                                                if(Global_Data.spiner_list_modelListn.size() > 0)
                                                {
                                                    //recyclerView.setAlpha(1);
                                                    ScrollView auto_scroll = ((Activity) mcontext).findViewById(R.id.auto_scroll);
                                                    if(recyclerView.getVisibility() != View.VISIBLE)
                                                    {
                                                       // auto_scroll.fullScroll(View.FOCUS_DOWN);
                                                    }
                                                    recyclerView.setVisibility(View.VISIBLE);
                                                    Global_Data.click_flag = "";

                                                    AutoCompleteTextView s = ((Activity) mcontext).findViewById(R.id
                                                            .s_dealer_search);

                                                    s.dismissDropDown();
                                                    customerAutoAdapter = new CustomerAutoAdapter(Global_Data.spiner_list_modelListn, mcontext);
                                                    recyclerView.setAdapter(customerAutoAdapter);
                                                    customerAutoAdapter.notifyDataSetChanged();




                                                }


                                            }
                                        });
                                    }
                                }

                            }


                        }
                    } catch (Exception ex) {
                        ((Activity) mcontext).runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerView.setVisibility(View.GONE);
                                // recyclerView.setAlpha(0);

                            }
                        });
                        ex.printStackTrace();
                    } finally {


                    }
                } else {
                    ((Activity) mcontext).runOnUiThread(new Runnable() {
                        public void run() {
                            recyclerView.setVisibility(View.GONE);
                            //recyclerView.setAlpha(0);


                        }
                    });
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else notifyDataSetInvalidated();
            }
        };
    }

}
