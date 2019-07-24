package com.anchor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.R;
import com.anchor.model.Spiner_List_Model;

import java.util.ArrayList;
import java.util.List;

public class SP_AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> data;
    Context mcontext;
    Spinner_List_Adapter spinner_list_adapter;
    RecyclerView recyclerView;
    ListView swipeListView;
    DataBaseHelper dbvoc;
    Button list_ok,buttonPreviewAddMOre;
   TextView txttotalPreview;

    public SP_AutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.data = new ArrayList<>();
        this.mcontext = context;
        dbvoc = new DataBaseHelper(mcontext);
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
                recyclerView = ((Activity) mcontext).findViewById(R.id.spinner_recycleview);
                swipeListView = ((Activity) mcontext).findViewById(R.id.example_lv_list);
                buttonPreviewAddMOre = ((Activity) mcontext).findViewById(R.id.buttonPreviewAddMOrev);
                txttotalPreview = ((Activity) mcontext).findViewById(R.id.txttotalPreviewv);
                list_ok = ((Activity) mcontext).findViewById(R.id.list_ok);
                if (constraint != null) {

                    try {

                        Log.d("SearchS","SearchS "+constraint.toString());
                        ArrayList<String> suggestions = new ArrayList<>();

                        List<Spiner_List_Model> cont1 = dbvoc.varientserchSub(constraint.toString());

                        if (cont1.size() > 0) {
                            Global_Data.spiner_list_modelList.clear();
                            for (Spiner_List_Model cnt1 : cont1) {
                                Spiner_List_Model spiner_list_model = new Spiner_List_Model();
                                spiner_list_model.setMRP(cnt1.getMRP());
                                spiner_list_model.setRP(cnt1.getRP());
                                spiner_list_model.setName(cnt1.getName());
                                spiner_list_model.setCode(cnt1.getCode());
                                spiner_list_model.setQUANTITY(cnt1.getQUANTITY());
                                spiner_list_model.setSQ(cnt1.getSQ());
                                spiner_list_model.setMQ(cnt1.getMQ());
                                spiner_list_model.setSelected(false);

                                Global_Data.spiner_list_modelList.add(spiner_list_model);

                                String pro_varient = cnt1.getName();
                                String term = pro_varient;
                                suggestions.add(term);
                            }


                            results.values = Global_Data.spiner_list_modelList;
                            results.count = Global_Data.spiner_list_modelList.size();

                            ((Activity) mcontext).runOnUiThread(new Runnable() {
                                public void run() {

//                                    View view = ((Activity)mcontext).getCurrentFocus();
//                                    if (view != null) {
//                                        InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                                    }

                                    recyclerView.setVisibility(View.VISIBLE);
                                    list_ok.setVisibility(View.VISIBLE);
                                    swipeListView.setVisibility(View.GONE);
                                    buttonPreviewAddMOre.setVisibility(View.GONE);
                                    txttotalPreview.setVisibility(View.GONE);

                                    AutoCompleteTextView s = ((Activity) mcontext).findViewById(R.id.newProduct_varient);
                                    s.dismissDropDown();
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
                                    spinner_list_adapter = new Spinner_List_Adapter(mcontext, Global_Data.spiner_list_modelList);
                                    recyclerView.setAdapter(spinner_list_adapter);

                                }
                            });


                            data = suggestions;
                        } else {
                            ((Activity) mcontext).runOnUiThread(new Runnable() {
                                public void run() {
                                    recyclerView.setVisibility(View.GONE);
                                    list_ok.setVisibility(View.VISIBLE);
                                    swipeListView.setVisibility(View.VISIBLE);
                                    buttonPreviewAddMOre.setVisibility(View.VISIBLE);
                                    txttotalPreview.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    } catch (Exception ex) {
                        ((Activity) mcontext).runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerView.setVisibility(View.GONE);
                                list_ok.setVisibility(View.VISIBLE);
                                swipeListView.setVisibility(View.VISIBLE);
                                buttonPreviewAddMOre.setVisibility(View.VISIBLE);
                                txttotalPreview.setVisibility(View.VISIBLE);
                            }
                        });
                        ex.printStackTrace();
                    } finally {


                    }
                } else {
                    ((Activity) mcontext).runOnUiThread(new Runnable() {
                        public void run() {
                            recyclerView.setVisibility(View.GONE);
                            list_ok.setVisibility(View.GONE);
                            swipeListView.setVisibility(View.VISIBLE);
                            buttonPreviewAddMOre.setVisibility(View.VISIBLE);
                            txttotalPreview.setVisibility(View.VISIBLE);
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
