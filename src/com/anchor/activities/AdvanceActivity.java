package com.anchor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.anchor.adapter.AutoCompleteStateAdapter;
import com.anchor.adapter.AutoSuggestAdapter;
import com.anchor.adapter.Spinner_List_Adapter;
import com.anchor.adapter.StateSpinnerList_Adapter;
import com.anchor.model.Spiner_List_Model;
import com.anchor.model.StateModel;
import com.anchor.util.TagContainerLayout;
import com.anchor.util.TagView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AdvanceActivity extends Activity implements AdapterView.OnItemSelectedListener,
        TagView.OnTagClickListener {
    Spinner city_spinner, state_spinner, retailer_spinner, dealer_spinner;
    EditText edt_mobile_number, edt_aadhar_card_number, edt_pan_card_number, edt_gst_number;
    Button btn_apply, btn_clear, list_ok, btn_city_ok;
    TextView txt_state_tag;

    LinearLayout linear_state, linear_district, linear_city, linear_retailer_name, linear_mobile_no,
            linear_aadhaar_card_no, linear_pancard_no, linear_gst_no, linear_dealer_type,
            linear_dealer, linear_button;

    TagContainerLayout mTagContainer, txt_city_tag;

    DataBaseHelper dbvoc = new DataBaseHelper(this);
    private ArrayList<String> SelectState = new ArrayList<String>();
    private ArrayList<String> SelectCity = new ArrayList<String>();
    private ArrayList<String> SelectRetailer = new ArrayList<String>();
    ArrayAdapter<String> SelectState_Adapter;
    ArrayAdapter<String> SelectCity_Adapter;
    ArrayAdapter<String> SelectRetailer_Adapter;

    ArrayList<Local_Data> snlist = new ArrayList<>();
    private List<String> selectedData;
    ArrayList<String>StateName;

    private int drop_value = 0;
    String S_ID = "",State_Id,State_Code,State_name;
    CardView card_view_advance;
    RadioButton radio_basic, radio_advance;

    String state_name = "", city_name = "";
    RecyclerView spinner_recycleview, city_recycleview;

    StateSpinnerList_Adapter spinner_list_adapter;
    RelativeLayout rel_state, rel_city;
    ArrayList<String> list;
    List<StateModel> products;
    private RequestQueue requestQueue;

    List<StateModel> stateList=new ArrayList<>();
    String localData;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);

        state_spinner = findViewById(R.id.state_spinner);
        city_spinner = findViewById(R.id.city_spinner);
        retailer_spinner = findViewById(R.id.retailer_spinner);
        dealer_spinner = findViewById(R.id.dealer_spinner);
        radio_basic = findViewById(R.id.radio_basic);
        radio_advance = findViewById(R.id.radio_advance);
        card_view_advance = findViewById(R.id.card_view_advance);

        edt_mobile_number = findViewById(R.id.edt_mobile_number);
        edt_aadhar_card_number = findViewById(R.id.edt_aadhar_card_number);
        edt_pan_card_number = findViewById(R.id.edt_pan_card_number);
        edt_gst_number = findViewById(R.id.edt_gst_number);

        spinner_recycleview = findViewById(R.id.spinner_recycleview);
        city_recycleview = findViewById(R.id.city_recycleview);


        list_ok = findViewById(R.id.list_ok);
        btn_city_ok = findViewById(R.id.btn_city_ok);


        btn_apply = findViewById(R.id.btn_apply);
        btn_clear = findViewById(R.id.btn_clear);
        rel_state = findViewById(R.id.rel_state);
        rel_city = findViewById(R.id.rel_city);


        linear_state = findViewById(R.id.linear_state);
        linear_district = findViewById(R.id.linear_district);
        linear_city = findViewById(R.id.linear_city);
        linear_retailer_name = findViewById(R.id.linear_retailer_name);
        linear_mobile_no = findViewById(R.id.linear_mobile_no);
        linear_aadhaar_card_no = findViewById(R.id.linear_aadhaar_card_no);
        linear_pancard_no = findViewById(R.id.linear_pancard_no);
        linear_gst_no = findViewById(R.id.linear_gst_no);
        linear_dealer_type = findViewById(R.id.linear_dealer_type);
        linear_dealer = findViewById(R.id.linear_dealer);
        linear_button = findViewById(R.id.linear_button);

        mTagContainer = findViewById(R.id.txt_state_tag);
        txt_city_tag = findViewById(R.id.txt_city_tag);

        requestQueue = Volley.newRequestQueue(this);

        mTagContainer.setOnTagClickListener(this);
        selectedData = new ArrayList<>();
        StateName = new ArrayList<>();
        products = new ArrayList<>();

        ArrayList<StateModel> stateList = new ArrayList<>();
        getStateName();

        rel_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rel_state.setVisibility(View.GONE);
                linear_district.setVisibility(View.GONE);
                linear_city.setVisibility(View.GONE);
                linear_retailer_name.setVisibility(View.GONE);
                linear_mobile_no.setVisibility(View.GONE);
                linear_aadhaar_card_no.setVisibility(View.GONE);
                linear_pancard_no.setVisibility(View.GONE);
                linear_gst_no.setVisibility(View.GONE);
                linear_dealer_type.setVisibility(View.GONE);
                linear_dealer.setVisibility(View.GONE);
                linear_button.setVisibility(View.GONE);
                mTagContainer.setVisibility(View.GONE);
                list_ok.setVisibility(View.VISIBLE);
                spinner_recycleview.setVisibility(View.VISIBLE);
            }
        });

        List<Local_Data> cont1 = dbvoc.getAllState();
        if (cont1.size() > 0) {
            Global_Data.spiner_list_modelListn.clear();
            for (Local_Data cnt1 : cont1) {
                Local_Data spiner_list_model = new Local_Data();
                String str = cnt1.getStateName();
                spiner_list_model.setStateName(str);
                spiner_list_model.setSelected(false);
                snlist.add(spiner_list_model);

            }
        }

//        spinner_recycleview.setLayoutManager(new LinearLayoutManager(AdvanceActivity.this));
//        spinner_list_adapter = new StateSpinnerList_Adapter(AdvanceActivity.this, stateList);
//        spinner_recycleview.setAdapter(spinner_list_adapter);


        list_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                list = new ArrayList<>();
                for ( i = 0; i < spinner_list_adapter.getList().size(); i++) {
                    if (spinner_list_adapter.getList().get(i).isSelected()) {

                        localData = spinner_list_adapter.getList().get(i).getName();
                        list.add(localData);
                        mTagContainer.setTags(list);

                        List<Local_Data> contacts = dbvoc.getState_id(localData);
                        for (Local_Data cn : contacts) {

                            S_ID = cn.getSTATE_ID();

                        }

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(S_ID)) {
                            SelectCity.clear();
                            SelectCity.add("Select City");
                            List<Local_Data> contacts2 = dbvoc.getcityByState_id(S_ID);
                            for (Local_Data cn : contacts2) {

                                SelectCity.add(cn.getCityName());
                            }
                            SelectCity_Adapter = new ArrayAdapter<String>(AdvanceActivity.this,
                                    android.R.layout.simple_spinner_item, SelectCity);
                            SelectCity_Adapter
                                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            city_spinner.setAdapter(SelectCity_Adapter);
                            // city_spinner.setOnItemSelectedListener(this);

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(state_name)) {
                                int spinnerPosition = SelectState_Adapter.getPosition(state_name);
                                state_spinner.setSelection(spinnerPosition);
                            }

                            if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(city_name)) {
                                int spinnerPosition = SelectCity_Adapter.getPosition(city_name);
                                city_spinner.setSelection(spinnerPosition);
                            }

                        }

                        mTagContainer.setVisibility(View.VISIBLE);
                        card_view_advance.setVisibility(View.VISIBLE);
                        rel_state.setVisibility(View.VISIBLE);
                        linear_district.setVisibility(View.VISIBLE);
                        linear_city.setVisibility(View.VISIBLE);
                        linear_retailer_name.setVisibility(View.VISIBLE);
                        linear_mobile_no.setVisibility(View.VISIBLE);
                        linear_aadhaar_card_no.setVisibility(View.VISIBLE);
                        linear_pancard_no.setVisibility(View.VISIBLE);
                        linear_gst_no.setVisibility(View.VISIBLE);
                        linear_dealer_type.setVisibility(View.VISIBLE);
                        linear_dealer.setVisibility(View.VISIBLE);
                        list_ok.setVisibility(View.GONE);
                        spinner_recycleview.setVisibility(View.GONE);

                    } else {

                               //onTagClick(i, localData);
                                // mTagContainer.setVisibility(View.VISIBLE);
                                card_view_advance.setVisibility(View.VISIBLE);
                                rel_state.setVisibility(View.VISIBLE);
                                linear_district.setVisibility(View.VISIBLE);
                                linear_city.setVisibility(View.VISIBLE);
                                linear_retailer_name.setVisibility(View.VISIBLE);
                                linear_mobile_no.setVisibility(View.VISIBLE);
                                linear_aadhaar_card_no.setVisibility(View.VISIBLE);
                                linear_pancard_no.setVisibility(View.VISIBLE);
                                linear_gst_no.setVisibility(View.VISIBLE);
                                linear_dealer_type.setVisibility(View.VISIBLE);
                                linear_dealer.setVisibility(View.VISIBLE);
                                linear_button.setVisibility(View.VISIBLE);
                                list_ok.setVisibility(View.GONE);
                                spinner_recycleview.setVisibility(View.GONE);
                            }



                }

            }
        });


        List<Local_Data> contacts1 = dbvoc.getAllState();
        SelectState.add("Select State");
        for (Local_Data cn : contacts1) {
            String str_state = "" + cn.getStateName();
            SelectState.add(str_state);
        }

        SelectState_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SelectState);

        SelectState_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state_spinner.setAdapter(SelectState_Adapter);
        state_spinner.setOnItemSelectedListener(this);

        SelectCity.add("Select City");
        SelectCity_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SelectCity);
        SelectCity_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city_spinner.setAdapter(SelectCity_Adapter);

        List<Local_Data> retailername = dbvoc.getAllRetailers();
        SelectRetailer.add("Select Retailer");
        for (Local_Data cn : retailername) {
            String str_retailer_name = "" + cn.getStateName();
            SelectRetailer.add(str_retailer_name);
        }

        SelectRetailer_Adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, SelectState);

        SelectRetailer_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        retailer_spinner.setAdapter(SelectRetailer_Adapter);
        retailer_spinner.setOnItemSelectedListener(this);





    }

    public void getStateName() {
        String url = "https://mumuatsmadms01.anchor-group.in/metal/api/v1/retailers/get_all_states?email=kartik.dubey@simplelogic.in";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("states");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject stats = jsonArray.getJSONObject(i);

                        State_Id = stats.getString("id");
                        State_Code = stats.getString("code");
                        State_name = stats.getString("name");

                        stateList.add(new StateModel(stats.getString("id"),
                                stats.getString("code"),stats.getString("name")));
                    }
                    spinner_recycleview.setLayoutManager(new LinearLayoutManager(AdvanceActivity.this));
                    spinner_list_adapter = new StateSpinnerList_Adapter(AdvanceActivity.this, stateList);
                    spinner_recycleview.setAdapter(spinner_list_adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        drop_value = drop_value + 1;

        if (drop_value > 1) {
            if (spinner.getId() == R.id.state_spinner) {
                if (parent.getItemAtPosition(position).toString()
                        .equalsIgnoreCase("Select State")) {


                    state_name = "";
                    city_name = "";

                    SelectCity.clear();
                    SelectCity.add("Select City");


                    SelectCity_Adapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, SelectCity);
                    SelectCity_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city_spinner.setAdapter(SelectCity_Adapter);
                    city_spinner.setOnItemSelectedListener(this);


                } else {

                    String items = state_spinner.getSelectedItem().toString().trim();
                    //String C_ID = "";
                    Log.i("Selected item : ", items);

                    Log.i("Selected item : ", items);

                    List<Local_Data> contacts = dbvoc.getState_id(items);
                    for (Local_Data cn : contacts) {

                        S_ID = cn.getSTATE_ID();

                    }

                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(S_ID)) {
                        SelectCity.clear();
                        SelectCity.add("Select City");
                        List<Local_Data> contacts2 = dbvoc.getcityByState_id(S_ID);
                        for (Local_Data cn : contacts2) {

                            SelectCity.add(cn.getCityName());
                        }
                        SelectCity_Adapter = new ArrayAdapter<String>(AdvanceActivity.this,
                                android.R.layout.simple_spinner_item, SelectCity);
                        SelectCity_Adapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city_spinner.setAdapter(SelectCity_Adapter);
                        city_spinner.setOnItemSelectedListener(this);

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(state_name)) {
                            int spinnerPosition = SelectState_Adapter.getPosition(state_name);
                            state_spinner.setSelection(spinnerPosition);
                        }

                        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(city_name)) {
                            int spinnerPosition = SelectCity_Adapter.getPosition(city_name);
                            city_spinner.setSelection(spinnerPosition);
                        }

                    }

                    if (!Global_Data.GLOvel_CITY_n.equalsIgnoreCase("")) {
                        city_spinner.setSelection(SelectCity_Adapter
                                .getPosition(Global_Data.GLOvel_CITY_n
                                        .toUpperCase()));
                    } else if (!Global_Data.GLOvel_CITY.equalsIgnoreCase("")) {
                        city_spinner
                                .setSelection(SelectCity_Adapter
                                        .getPosition(Global_Data.GLOvel_CITY
                                                .toUpperCase()));
                    }
                }
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onTagClick(int position, String text) {
        list.remove(text);
        mTagContainer.setTags(list);
        int pos = 0;
        for (int i = 0; i < stateList.size(); i++) {
            if (text.trim().equalsIgnoreCase(stateList.get(i).getName())) {
                pos = i;
                break;
            }
        }
        stateList.get(pos).isSelected = false;

    }

    @Override
    public void onTagLongClick(int position, String text) {

    }
}
