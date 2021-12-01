package com.anchor.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Global_Data;
import com.anchor.activities.Local_Data;
import com.anchor.activities.R;
import com.anchor.model.Spiner_List_Model;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteStateAdapter extends ArrayAdapter<String> implements Filterable {

    private ArrayList<String> data;
    Context mcontext;
    StateSpinnerList_Adapter spinner_list_adapter;
    RecyclerView recyclerView;
    DataBaseHelper dbvoc;
    Button list_ok;

    public AutoCompleteStateAdapter(@NonNull Context context, @LayoutRes int resource) {
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
                list_ok = ((Activity) mcontext).findViewById(R.id.list_ok);
                if (constraint != null) {

                    try {

                        Log.d("SearchS","SearchS "+constraint.toString());
                        ArrayList<String> suggestions = new ArrayList<>();
                        List<Local_Data> cont1 = dbvoc.getAllState();

                        if (cont1.size() > 0) {

                            for (Local_Data cnt1 : cont1) {
                                Local_Data spiner_list_model = new Local_Data();
                                spiner_list_model.setSelected(false);

                                String pro_varient = cnt1.getStateName();
                                String term = pro_varient;
                                suggestions.add(term);
                            }


                            results.values = Global_Data.spiner_list_modelListn;
                            results.count = Global_Data.spiner_list_modelListn.size();

                            ((Activity) mcontext).runOnUiThread(new Runnable() {
                                public void run() {

//                                    View view = ((Activity)mcontext).getCurrentFocus();
//                                    if (view != null) {
//                                        InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                                    }

                                    recyclerView.setVisibility(View.VISIBLE);
                                    list_ok.setVisibility(View.VISIBLE);

                                    AutoCompleteTextView s = ((Activity) mcontext).findViewById(R.id.Product_Variant_search);
                                    s.dismissDropDown();
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(mcontext));
                                   // spinner_list_adapter = new StateSpinnerList_Adapter(mcontext, Global_Data.spiner_list_modelListn);
                                    recyclerView.setAdapter(spinner_list_adapter);

                                }
                            });


                            data = suggestions;
                        } else {
                            ((Activity) mcontext).runOnUiThread(new Runnable() {
                                public void run() {
                                    recyclerView.setVisibility(View.GONE);
                                    list_ok.setVisibility(View.GONE);

                                }
                            });
                        }
                    } catch (Exception ex) {
                        ((Activity) mcontext).runOnUiThread(new Runnable() {
                            public void run() {
                                recyclerView.setVisibility(View.GONE);
                                list_ok.setVisibility(View.GONE);

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
