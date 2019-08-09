package com.anchor.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.anchor.API.ApiClient;
import com.anchor.API.ApiInterface;
import com.anchor.adapter.CustomerInvoicesAdapter;
import com.anchor.model.Invoice;
import com.anchor.model.InvoiceResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cpm.simplelogic.helper.ConnectionDetector;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerInvoice extends AppCompatActivity {


    //=====to call from server
    String imeiNo = "";
    String email = "";
    String customerCode = "";
    String filterValue = "";
    String G_c_start_date_value = "";//also used in other parts of code
    String G_c_end_date_value = "";//also used in other parts of code
    //===============
    String G_RadioG_valueC = "";
    String G_c_all_dates_value = "";
    String  G_Filter_Flag = "";

    private static final String TAG = CustomerInvoice.class.getSimpleName();
    public Boolean GOT_INVOICES = false;
    List<Invoice> invoiceList = new ArrayList<>();
    ConnectionDetector cd;
    private RecyclerView myRv;
    CustomerInvoicesAdapter rvAdapter;

    private AutoCompleteTextView actvSearchInvoice;
    private List<String> invoiceNumbersForSearchInvoice = new ArrayList<>();
    List<Invoice> partialInvoiceList = new ArrayList<>();
    String partialInvoiceNumberEntered = null;
    private ImageView filterBtn;
    private Calendar myCalendar;
    private Button filter_submit;
    private Button filter_clear;
    private EditText c_start_date;
    private EditText c_end_date;
    private ImageView close_filter;
    private ProgressDialog dialog;
    DatePickerDialog.OnDateSetListener date,date1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_customer_invoice);

        init();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel1();
            }
        };
        date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR,year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel2();
            }
        };

        actvSearchInvoice.clearFocus();
        // TODO: 30/5/18 clearFocus() not working why?

        //getting invoices from server
        getInvoicesFromServer();
        //onClick Listener
        actvSearchInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actvSearchInvoice.showDropDown();
            }
        });
        actvSearchInvoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                try{
                    if (in != null) {
                        in.hideSoftInputFromWindow(view.getWindowToken(),0);
                    }else{
                        Toast.makeText(CustomerInvoice.this, "Keyboard is null", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NullPointerException n){
                    Log.d(TAG, "onItemSelected: "+n.getMessage());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //on text changed listener
        actvSearchInvoice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: "+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: UPDATING RECYCLER VIEW "+ s);
                updateRecyclerViewBasedOnEnteredInvoice();

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: "+ s);

            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterDialog();
            }
        });
    }

    private void updateLabel1() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        c_start_date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        c_end_date.setText(sdf.format(myCalendar.getTime()));
    }

    void FilterDialog() {
        final Dialog dialog1 = new Dialog(CustomerInvoice.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(false);
        dialog1.setContentView(R.layout.customer_invoice_filter);

        myCalendar = Calendar.getInstance();

        filter_submit = (Button) dialog1.findViewById(R.id.filter_submit);
        filter_clear = (Button) dialog1.findViewById(R.id.filter_clear);
        c_start_date = (EditText) dialog1.findViewById(R.id.c_start_date);
        c_end_date = (EditText) dialog1.findViewById(R.id.c_end_date);

        final RadioButton c_all_dates = (RadioButton) dialog1.findViewById(R.id.c_all_dates);
        final RadioGroup cashradioGroup = (RadioGroup) dialog1.findViewById(R.id.cashradioGroup);
        final RadioButton radio_showall = (RadioButton) dialog1.findViewById(R.id.radio_showall);
        RadioButton radio_overdue = (RadioButton) dialog1.findViewById(R.id.radio_overdue);
        RadioButton radio_outstanding = (RadioButton) dialog1.findViewById(R.id.radio_outstanding);
        close_filter = (ImageView) dialog1.findViewById(R.id.close_filter);


        if (G_RadioG_valueC.equalsIgnoreCase("Show Overdue")) {
            cashradioGroup.check(radio_overdue.getId());
        } else if (G_RadioG_valueC.equalsIgnoreCase("Show Outstanding")) {
            cashradioGroup.check(radio_outstanding.getId());
        }
        else
        {
            cashradioGroup.check(radio_showall.getId());
        }

        if (!G_c_start_date_value.equalsIgnoreCase(""))
        {
            c_start_date.setText(G_c_start_date_value);
        }

        if (!G_c_end_date_value.equalsIgnoreCase(""))
        {
            c_end_date.setText(G_c_end_date_value);
        }

        if (!G_c_all_dates_value.equalsIgnoreCase(""))
        {
            c_all_dates.setChecked(true);
        }

        c_all_dates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    G_c_all_dates_value = "yes";
                    c_start_date.setText("");
                    c_end_date.setText("");

//                    c_start_date.setClickable(false);
//                    c_end_date.setClickable(false);
//                    c_start_date.setEnabled(false);
//                    c_end_date.setEnabled(false);

                    G_c_start_date_value = "";
                    G_c_end_date_value = "";
                }
                else
                {
                    G_c_all_dates_value = "";
                    c_start_date.setClickable(true);
                    c_end_date.setClickable(true);
                }
            }
        });
        c_end_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                c_all_dates.setChecked(false);
                G_c_all_dates_value = "";
                DatePickerDialog picker = new DatePickerDialog(CustomerInvoice.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });

        c_start_date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                c_all_dates.setChecked(false);
                G_c_all_dates_value = "";
                DatePickerDialog picker = new DatePickerDialog(CustomerInvoice.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                //   picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });


        filter_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int radioButtonID = cashradioGroup.getCheckedRadioButtonId();
                View radioButton = cashradioGroup.findViewById(radioButtonID);
                int idx = cashradioGroup.indexOfChild(radioButton);


                RadioButton r = (RadioButton) cashradioGroup.getChildAt(idx);
                String selectedtext = r.getText().toString();

                Log.d("Radio Value", "Radio value" + selectedtext);

                G_RadioG_valueC = selectedtext.trim();

                if(G_c_all_dates_value.equalsIgnoreCase(""))
                {
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_start_date.getText().toString()) && !Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_end_date.getText().toString())) {
                        Toast.makeText(CustomerInvoice.this, "Please select end date.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    if (!Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_start_date.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_end_date.getText().toString())) {
                        Toast.makeText(CustomerInvoice.this, "Please select start date.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_start_date.getText().toString()) && Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(c_end_date.getText().toString())) {


                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            Date strDate = sdf.parse(c_start_date.getText().toString());
                            Date endDate = sdf.parse(c_end_date.getText().toString());
                            Date date1 = new Date(c_start_date.getText().toString());
                            Date date2 = new Date(c_end_date.getText().toString());
                            Calendar cal1 = Calendar.getInstance();
                            Calendar cal2 = Calendar.getInstance();
                            cal1.setTime(date1);
                            cal2.setTime(date1);

                            if(strDate.after(endDate))
                            {
                                Toast toast = Toast.makeText(getApplicationContext(), " End date not a valid date.", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            else
                            {
                                G_c_start_date_value = c_start_date.getText().toString();
                                G_c_end_date_value = c_end_date.getText().toString();
                                dialog1.dismiss();
                                getInvoicesFromServer();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }



                    }
                    else
                    {
                        G_c_start_date_value = c_start_date.getText().toString();
                        G_c_end_date_value = c_end_date.getText().toString();
                        dialog1.dismiss();
                        getInvoicesFromServer();
                    }


                }
                else
                {
                    G_c_start_date_value = "";
                    G_c_end_date_value = "";
                    getInvoicesFromServer();
                    dialog1.dismiss();
                }





            }
        });

        filter_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                G_RadioG_valueC = "";
                G_c_start_date_value = "";
                G_c_end_date_value = "";
                G_c_all_dates_value = "";


                cashradioGroup.check(radio_showall.getId());
                int radioButtonID = cashradioGroup.getCheckedRadioButtonId();
                View radioButton = cashradioGroup.findViewById(radioButtonID);
                int idx = cashradioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) cashradioGroup.getChildAt(idx);
                String selectedtext = r.getText().toString();
                dialog1.dismiss();
                getInvoicesFromServer();

            }
        });

        close_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();

    }

    private void init() {
        imeiNo = "358187071078870";
        //email = "swati.yamgar@simplelogic.in";
        email = Global_Data.CUSTOMER_EMAIL;
        //customerCode = "1390294";
        customerCode = Global_Data.GLOvel_CUSTOMER_ID;
        filterValue = "";
        G_c_start_date_value = "";
        G_c_end_date_value = "";
        cd = new ConnectionDetector(this);
        myRv = findViewById(R.id.c_info_card);
        actvSearchInvoice = findViewById(R.id.actvSearchInvoice);
        filterBtn = findViewById(R.id.ivFilter_btn);
    }

    private void getInvoicesFromServer() {

        if(cd.isConnectedToInternet()) {
            dialog = new ProgressDialog(CustomerInvoice.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            dialog.setMessage("Please Wait....");
            dialog.setTitle("Smart Anchor App");
            dialog.setCancelable(false);
            dialog.show();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            //        Log.d(TAG, "getInvoicesFromServer: CREATED apiService OF ApiInterface");
            // TODO: 1/6/18 implement different call statements for different filter cases

            final Call<InvoiceResponse> invoiceCall = apiService.getInvoices
                    (imeiNo, email, customerCode, filterValue, G_c_start_date_value, G_c_end_date_value);
            //        Log.d(TAG, "getInvoicesFromServer: CREATED invoiceCall READY TO CALL");
            invoiceCall.enqueue(new Callback<InvoiceResponse>() {
                @Override
                public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                    //                Log.d(TAG, "onResponse: SUCCESSFULLY GETTING THE INVOICES");

                    //                Log.d(TAG, "onResponse: BEFORE invoiceList size = " + invoiceList.size());
                    invoiceList = response.body().getInvoices();
                    //                Log.d(TAG, "onResponse: AFTER invoiceList size = " + invoiceList.size());

                    GOT_INVOICES = true;
                    //TODO: 31/5/18 why did we have to set the adapter in the asynchronous method only why didn't it work in onCreate
                    /*for (int i = 0; i < invoiceList.size(); i++) {
                        partialInvoiceList.set(i,invoiceList.get(i));
                    }*/
                    dialog.dismiss();

                    partialInvoiceList.clear();
                    for (int i = 0; i < invoiceList.size(); i++) {
                        partialInvoiceList.add(new Invoice(invoiceList.get(i)));
                    }
                    //                partialInvoiceList = invoiceList;
                    // made a duplicate of the invoice list
                    rvAdapter = new CustomerInvoicesAdapter(partialInvoiceList, CustomerInvoice.this);
                    myRv.setLayoutManager(new LinearLayoutManager(CustomerInvoice.this));
                    myRv.setAdapter(rvAdapter);
                    rvAdapter.notifyDataSetChanged();
                    //                Log.d(TAG, "onCreate: ADAPTER SET");

                    setSearchInvoicesAdapter();

                }

                @Override
                public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                    //                Log.d(TAG, "onFailure: "+t.getMessage());
                    Toast.makeText(CustomerInvoice.this, "Failure to fetch invoices from " +
                            "server", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Connection Detector says there is no internet", Toast.LENGTH_SHORT).show();
        }
    }

    //it just sets the dropdown adapter for the actv in the beginning and not called again(i think)
    private void setSearchInvoicesAdapter() {
        Log.d(TAG, "setSearchInvoicesAdapter: SETTING THE DROPDOWN ADAPTER");
        for (int i = 0; i < invoiceList.size(); i++) {
            invoiceNumbersForSearchInvoice.add(i,invoiceList.get(i).getInvoiceNumber());
        }
// TODO: 31/5/18 create a seperate layout for dropdown or copy it from anchor metal
        ArrayAdapter<String> searchInvoicesDropdownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,invoiceNumbersForSearchInvoice);
        actvSearchInvoice.setThreshold(1);
        actvSearchInvoice.setAdapter(searchInvoicesDropdownAdapter);
        Log.d(TAG, "setSearchInvoicesAdapter: DROPDOWN ADAPTER IS SET");
//        updateRecyclerViewBasedOnEnteredInvoice();
    }

    //changes the partialInvoiceList if the actv was a starting substring of invoiceNumbers
    //only if the size of the actv is >=1 and invoiceList has atleast one invoice
    private void updateRecyclerViewBasedOnEnteredInvoice() {
        Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: TRYING TO UPDATE THE RECYCLER VIEW BECAUSE THE ACTV IS CHANGED");
        partialInvoiceNumberEntered = actvSearchInvoice.getText().toString();
        if(partialInvoiceList.size() == 0 && partialInvoiceNumberEntered.isEmpty()){
            Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: =0 CONDITION SATISFIED");
            //deep copying without referencing to the same object

            partialInvoiceList.clear();
            for (int i = 0; i < invoiceList.size(); i++) {
                partialInvoiceList.add(new Invoice(invoiceList.get(i)));
            }
            rvAdapter.notifyDataSetChanged();
            return;
        }
        if (partialInvoiceList.size()>=0 && invoiceList.size()>=1){
            partialInvoiceList.clear();
            Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: SATISFIED THE >=1 CONDITION");
            for (int i = 0; i < invoiceList.size(); i++) {
                if (invoiceList.get(i).getInvoiceNumber().indexOf(partialInvoiceNumberEntered,0) == 0){
                    if(partialInvoiceNumberEntered == invoiceList.get(i).getInvoiceNumber()){
                        actvSearchInvoice.clearFocus();
                    }
                    Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: SATISFIED THE SUBSTRING CONDITION ALSO");

                    partialInvoiceList.add(new Invoice(invoiceList.get(i)));
                }
                else
                Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: " +String.valueOf(i)+ "FAILED THE SUBSTRING CONDITION" );
            }
            rvAdapter.notifyDataSetChanged();
            Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: UPDATED THE RECYCLER VIEW");
        }else {
            Log.d(TAG, "updateRecyclerViewBasedOnEnteredInvoice: FAILED THE >=1 CONDITION");
        }
    }
}
