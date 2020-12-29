package com.anchor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anchor.activities.Global_Data;
import com.anchor.activities.R;
import com.anchor.activities.TodoEditCustomer;
import com.anchor.model.RCTOData;
import com.anchor.model.Todo_model;

import java.util.List;

public class Todo_list_adaptor extends RecyclerView.Adapter<Todo_list_adaptor.ViewHolder> {
    Context context;
    List<RCTOData> array;
    int selectedPosition=-1;

    public Todo_list_adaptor(Context context, List<RCTOData> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public Todo_list_adaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.row_todo_dialog,viewGroup,false);
        return new Todo_list_adaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Todo_list_adaptor.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        viewHolder.tv.setText(array.get(i).getShop_name()+" , "+array.get(i).getMobile());
        viewHolder.tv_code.setText(array.get(i).getCode());

        if(selectedPosition==i)
        {
            viewHolder.btn.setBackgroundColor(Color.WHITE);
            viewHolder.btn.setTextColor(Color.BLACK);
        }
        else
        {
            viewHolder.btn.setBackgroundColor(Color.parseColor("#D4D4D4"));
            viewHolder.btn.setTextColor(Color.parseColor("#737373"));

        }
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition=i;
                notifyDataSetChanged();
                Global_Data.merge_retailer_code = array.get(i).getCode();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv,tv_code;
        private Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            tv_code=itemView.findViewById(R.id.tv_code);
            btn=itemView.findViewById(R.id.btn);

        }
    }
}
