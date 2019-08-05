package com.anchor.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;

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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cpm.simplelogic.helper.ConnectionDetector;

import static android.content.Context.MODE_PRIVATE;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> data;
    Context mcontext;
    CustomerAutoAdapter spinner_list_adapter;
   // RecyclerView recyclerView;
    DataBaseHelper dbvoc;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    SharedPreferences sp;

    public AutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
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
              //  recyclerView = ((Activity) mcontext).findViewById(R.id.spinner_recycleview);

                if (constraint != null) {

                    try {

                        Global_Data.Customers.clear();
                        Global_Data.Customers_map.clear();
                        // recyclerView.getRecycledViewPool().clear();
                        Log.d("SearchS", "SearchS " + constraint.toString());
                        ArrayList<String> suggestions = new ArrayList<>();
                        List<String> array_of_code = new ArrayList<>();
                        String BEAT_CITY_CODE = sp.getString("BEAT_CITY_CODE", "");

                        cd = new ConnectionDetector(mcontext);
                        isInternetPresent = cd.isConnectingToInternet();
                        if (isInternetPresent) {

                            StringBuilder ss = new StringBuilder();
                            String bs_check = "";
                            if (array_of_code.size() > 0) {

                                String[] mStringArray = new String[array_of_code.size()];
                                mStringArray = array_of_code.toArray(mStringArray);
                                for (int i = 0; i < array_of_code.size(); i++) {
                                    if (i == 0) {
                                        ss.append("[");
                                    }

                                    ss.append('"' + array_of_code.get(i) + '"');
                                    if ((array_of_code.size() - 1) != i) {
                                        ss.append(",");
                                    } else {
                                        ss.append("]");
                                    }

                                }
                            }

                            if (array_of_code.size() > 0) {
                                bs_check = ss.toString();
                            } else {
                                bs_check = "[]";
                            }
                            HttpURLConnection conn = null;
                            InputStream input = null;

                            sp = mcontext.getSharedPreferences("SimpleLogic", MODE_PRIVATE);
                            String BEAT_CITY_CODEs = sp.getString("BEAT_CITY_CODE", "");

                            Log.d("SearchS", "SearchS " + constraint.toString());
                            Log.d("SearchS", "SearchS URL" + mcontext.getResources().getString(R.string.service_domain) + "sub_dealers/sub_dealer_smart_fuzzily_search?search_string=" + constraint.toString() + "&city_code=" + BEAT_CITY_CODE + "&sub_dealers_code=" + URLEncoder.encode(bs_check));

                            URL url = new URL(mcontext.getResources().getString(R.string.service_domain) + "sub_dealers/sub_dealer_smart_fuzzily_search?search_string=" + constraint.toString() + "&city_code=" + BEAT_CITY_CODEs + "&sub_dealers_code=" + URLEncoder.encode(bs_check));
                            conn = (HttpURLConnection) url.openConnection();
                            input = conn.getInputStream();
                            InputStreamReader reader = new InputStreamReader(input, "UTF-8");
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
                            } else {
                                List<Local_Data> ress = new ArrayList();
                                JSONArray sub_dealers = json.getJSONArray("sub_dealers");
                                Log.i("volley", "response serch Result Length: " + sub_dealers.length());
                                Log.d("volley", "serch Result" + sub_dealers.toString());

                                if (sub_dealers.length() > 0) {
                                    //Global_Data.spiner_list_modelList.clear();
                                    for (int ind = 0; ind < sub_dealers.length(); ind++) {
                                        JSONObject object = sub_dealers.getJSONObject(ind);

                                        Local_Data di = new Local_Data();
                                        di.name = object.getString("name");
                                        di.Address = object.getString("address");

                                        Global_Data.Customers.add(object.getString("code")+object.getString("name"));
                                        Global_Data.Customers_map.put(object.getString("code")+object.getString("name"),object.getString("code"));

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
                                               // recyclerView.setVisibility(View.VISIBLE);
                                                AutoCompleteTextView s = ((Activity) mcontext).findViewById(R.id
                                                        .Product_Variant_search);

                                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext,
                                                        android.R.layout.simple_spinner_dropdown_item,
                                                        Global_Data.Customers);
                                                s.setThreshold(1);// will start working from
                                                // first character
                                                s.setAdapter(adapter);// setting the adapter
                                                // data into the
                                                // AutoCompleteTextView
                                                s.setTextColor(Color.BLACK);

                                                //s.dismissDropDown();
//                                                recyclerView.getRecycledViewPool().clear();
//                                                //recyclerView.setHasFixedSize(true);
//                                                //recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
//                                                spinner_list_adapter = new CustomerAutoAdapter(Global_Data.spiner_list_modelListn,mcontext);
//                                                recyclerView.setAdapter(spinner_list_adapter);
//                                                spinner_list_adapter.notifyDataSetChanged();
                                            }


                                        }
                                    });
                                }
                            }


                        }
                    } catch (Exception ex) {
                        ((Activity) mcontext).runOnUiThread(new Runnable() {
                            public void run() {
                              //  recyclerView.setVisibility(View.GONE);

                            }
                        });
                        ex.printStackTrace();
                    } finally {


                    }
                } else {
                    ((Activity) mcontext).runOnUiThread(new Runnable() {
                        public void run() {
                           // recyclerView.setVisibility(View.GONE);


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
