package com.anchor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.Invoice;
import java.util.List;

public class CustomerInvoicesAdapter extends RecyclerView.Adapter<CustomerInvoicesAdapter.InvoiceViewHolder> {
//    static ConnectionDetector cd;
    static  Boolean isInternetPresent = false;
    private List<Invoice> invoiceListInAdapter;
//    private List<Invoice> invoiceListInAdapterFilter;

    private Context mcontext;

    public CustomerInvoicesAdapter(List<Invoice> invoiceListInAdapter, Context context) {
        this.invoiceListInAdapter = invoiceListInAdapter;
        this.mcontext = context;
        //this.invoiceListInAdapterfilter.addAll(this.invoiceListInAdapter);
    }
    
    @NonNull
    @Override
    public CustomerInvoicesAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        view = inflater.inflate(R.layout.invoice_card,parent,false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerInvoicesAdapter.InvoiceViewHolder holder, int i) {
        Invoice inv = invoiceListInAdapter.get(i);
        holder.ic_name.setText(Html.fromHtml("<b>" +"Invoice Number : "+ "</b>")+inv.getInvoiceNumber());
        holder.icustomer_code.setText(Html.fromHtml("<b>" +"Customer code : "+ "</b>")+String.valueOf(inv.getCustomerId()));
        holder.invoice_amount.setText(Html.fromHtml("<b>" +"Invoice amount : "+ "</b>")+String.valueOf(inv.getTotal()));
        holder.invoice_date.setText(Html.fromHtml("<b>" +"Invoice date : "+ "</b>")+inv.getDate());
        holder.invoice_due_date.setText(Html.fromHtml("<b>" +"Invoice due date : "+ "</b>")+inv.getInvoiceDueDate());
        holder.invoice_due_amount.setText(Html.fromHtml("<b>" +"Invoice due amount : "+ "</b>")+String.valueOf(inv.getInvoiceDueAmount()));
    }

    @Override
    public int getItemCount() {
        return invoiceListInAdapter.size();
    }

    public void changeList(List<Invoice> changedInvoicesList){
        this.invoiceListInAdapter = changedInvoicesList;
    }

    public class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private TextView ic_name;
        private TextView icustomer_code;
        private TextView invoice_number;
        private TextView invoice_date;
        private TextView invoice_due_date;
        private TextView invoice_due_amount;
        protected TextView invoice_amount;
        
        public InvoiceViewHolder(View itemView) {
            super(itemView);

            ic_name = itemView.findViewById(R.id.ic_name);
            icustomer_code = itemView.findViewById(R.id.icustomer_code);
            invoice_number = itemView.findViewById(R.id.invoice_number);
            invoice_date = itemView.findViewById(R.id.invoice_date);
            invoice_due_date = itemView.findViewById(R.id.invoice_due_date);
            invoice_due_amount = itemView.findViewById(R.id.invoice_due_amount);
            invoice_amount = itemView.findViewById(R.id.invoice_amount);
        }
    }
}
