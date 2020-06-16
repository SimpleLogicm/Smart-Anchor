package com.anchor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anchor.activities.R;
import com.anchor.model.Todo_model;

import java.util.List;

public class Todo_list_adaptor extends RecyclerView.Adapter<Todo_list_adaptor.ViewHolder> {
    Context context;
    List<Todo_model> array;

    public Todo_list_adaptor(Context context, List<Todo_model> array) {
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
    public void onBindViewHolder(@NonNull final Todo_list_adaptor.ViewHolder viewHolder, int i) {

        viewHolder.tv.setText(array.get(i).getName());
      viewHolder.btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              viewHolder.btn.setBackgroundColor(Color.WHITE);
              viewHolder.btn.setTextColor(Color.BLACK);
          }
      });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private Button btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
            btn=itemView.findViewById(R.id.btn);

        }
    }
}
