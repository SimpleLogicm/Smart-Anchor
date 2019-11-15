package com.anchor.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;

import com.anchor.activities.DataBaseHelper;
import com.anchor.activities.Local_Data;
import com.anchor.activities.R;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchAdapter extends ArrayAdapter<String> implements Filterable {
    private ArrayList<String> data;
    Context mcontext;
    Spinner_List_Adapter spinner_list_adapter;
    RecyclerView recyclerView;
    Spinner spnBusinessDiv, spnCategory, spnProduct,spnBu;
    DataBaseHelper dbvoc;
    Button list_ok;
    ArrayList<String> suggestions;

    public ProductSearchAdapter(@NonNull Context context, @LayoutRes int resource) {
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
               // recyclerView = ((Activity) mcontext).findViewById(R.id.spinner_recycleview);
                if (constraint != null) {

                    try {

                        Log.d("SearchS","SearchS "+constraint.toString());
                         suggestions = new ArrayList<>();

                        List<Local_Data> cont1 = dbvoc.ListProduct(constraint.toString());
                        suggestions.clear();
                        if (cont1.size() > 0) {

                            for (Local_Data cnt1 : cont1) {
                                Local_Data spiner_list_model = new Local_Data();
                                cnt1.getProduct_variant();

//                                if(!suggestions.contains(cnt1.getProduct_variant()))
//                                {
                                    suggestions.add(cnt1.getProduct_variant());
                              //  }


                               // String pro_varient = cnt1.getProduct_variant();
                               // String term = pro_varient;
                                //suggestions.add(term);
                            }


                            results.values =suggestions;
                            results.count =suggestions.size();

                            ((Activity) mcontext).runOnUiThread(new Runnable() {
                                public void run() {

//                                    View view = ((Activity)mcontext).getCurrentFocus();
//                                    if (view != null) {
//                                        InputMethodManager imm = (InputMethodManager)mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                                    }


                                    AutoCompleteTextView s = ((Activity) mcontext).findViewById(R.id.Product_Variant);


                                   // s.dismissDropDown();


                                }
                            });


                            data = suggestions;
                        } else {
                            ((Activity) mcontext).runOnUiThread(new Runnable() {
                                public void run() {
//                                    recyclerView.setVisibility(View.GONE);
//                                    list_ok.setVisibility(View.GONE);
//                                    spnBusinessDiv.setVisibility(View.VISIBLE);
//                                    spnCategory.setVisibility(View.VISIBLE);
//                                    spnProduct.setVisibility(View.VISIBLE);
//                                    spnBu.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    } catch (Exception ex) {
                        ((Activity) mcontext).runOnUiThread(new Runnable() {
                            public void run() {
//                                recyclerView.setVisibility(View.GONE);
//                                list_ok.setVisibility(View.GONE);
//                                spnBusinessDiv.setVisibility(View.VISIBLE);
//                                spnCategory.setVisibility(View.VISIBLE);
//                                spnProduct.setVisibility(View.VISIBLE);
//                                spnBu.setVisibility(View.VISIBLE);
                            }
                        });
                        ex.printStackTrace();
                    } finally {


                    }
                } else {
                    ((Activity) mcontext).runOnUiThread(new Runnable() {
                        public void run() {
//                            recyclerView.setVisibility(View.GONE);
//                            list_ok.setVisibility(View.GONE);
//                            spnBusinessDiv.setVisibility(View.VISIBLE);
//                            spnCategory.setVisibility(View.VISIBLE);
//                            spnProduct.setVisibility(View.VISIBLE);
//                            spnBu.setVisibility(View.VISIBLE);
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
