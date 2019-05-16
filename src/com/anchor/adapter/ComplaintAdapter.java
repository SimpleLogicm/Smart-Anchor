package com.anchor.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.Complaint;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>{
    private Context mContext;
    private List<Complaint> complaintData;

    public ComplaintAdapter(Context mContext, List<Complaint> complaintData) {
        this.mContext = mContext;
        this.complaintData = complaintData;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.complaint_card,parent,false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        final String complaintEnteredComplete = String.valueOf(complaintData.get(position).getComplaint());
        final String dateAtEnter = String.valueOf(complaintData.get(position).getDateString());

        //setting the new complaint in the card textview
        //entering date
        final int noOfCharacters = complaintEnteredComplete.length();
        final int noOfNewLines = complaintEnteredComplete.split("\r\n|\n|\r").length;

        holder.tvDate.setText(dateAtEnter);


        if(noOfNewLines>2){
            String parts[] = complaintEnteredComplete.split("\r\n|\n|\r");
            String finalOutputText = parts[0]+"\n"+parts[1]+"...";
            holder.tvNewComplaint.setText(finalOutputText);
        }
        else if(noOfCharacters>30 && noOfNewLines>=1){
            String finalOutputText = complaintEnteredComplete.substring(0,25);
            finalOutputText = finalOutputText + "...";
            holder.tvNewComplaint.setText(finalOutputText);
        }
        else if(noOfCharacters>60){
//            Toast.makeText(mContext, complaintEnteredComplete.split("\r\n|\r|\n").length + " if", Toast.LENGTH_SHORT).show();
            String finalOutputText = complaintEnteredComplete.substring(0,60);
            finalOutputText = finalOutputText + "...";
            holder.tvNewComplaint.setText(finalOutputText);
        }else{
//            Toast.makeText(mContext, complaintEnteredComplete.split("\r\n|\r|\n").length + " else", Toast.LENGTH_SHORT).show();
            holder.tvNewComplaint.setText(complaintEnteredComplete);
        }
        //onClick for cards
        holder.cvComplaintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for some reason noOfNewLines is not working inside this scoped even after i declared it as final
                final int noOfCharacters2 = complaintEnteredComplete.length();
                int noOfNewLines2 = complaintEnteredComplete.split("\r\n|\n|\r").length;
                if (noOfNewLines2>2 || (noOfCharacters2>30 && noOfNewLines2>=1) || noOfCharacters2>60){
                    Dialog dialog = new Dialog(mContext);
                    dialog.setContentView(R.layout.dialog_complaint_card);
                    TextView dialogText = (TextView) dialog.findViewById(R.id.tvComplaintDialog);
                    dialogText.setText(complaintEnteredComplete);
                    dialog.show();
                }
                else {
                    return;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (complaintData.size()!= 0)
            return complaintData.size();
        else return 0;
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        TextView tvNewComplaint;
        CardView cvComplaintCard;
        public ComplaintViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvCardComplaintDate);
            tvNewComplaint = itemView.findViewById(R.id.tvCardComplaintNewComplaint);
            cvComplaintCard = itemView.findViewById(R.id.cvComplaint);
        }
    }
}
