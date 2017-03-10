package com.anchor.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cpm.simplelogic.helper.QuestionModel;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ViewHolder> {

    private List<QuestionModel> elements = new ArrayList<>();
    private Context context;

    private int[] state;

    public ElementListAdapter(Context context, List<QuestionModel> elements) {
        this.context = context;
        this.elements = elements;

        this.state = new int[elements.size()];
        Arrays.fill(this.state, -1);
    }

    @Override
    public ElementListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_layout_adapter, parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final QuestionModel ele = elements.get(position);
        final String title = ele.getQuestion();
        //final String description = ele.getDescription();

        // Set text
        holder.tvTitle.setText(title);
        holder.Q_code.setText(ele.getQuestioncode());

        if(ele.getoption1value().equalsIgnoreCase("") || ele.getoption1value().equalsIgnoreCase(" ") || ele.getoption1value().equalsIgnoreCase("null") || ele.getoption1value().equalsIgnoreCase(null))
        {
            holder.rb2.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.rb2.setText(ele.getoption1value());
        }

        if(ele.getoption2value().equalsIgnoreCase("") || ele.getoption2value().equalsIgnoreCase(" ") || ele.getoption2value().equalsIgnoreCase("null") || ele.getoption2value().equalsIgnoreCase(null))
        {
            holder.rb3.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.rb3.setText(ele.getoption2value());
        }

        if(ele.getoption3value().equalsIgnoreCase("") || ele.getoption3value().equalsIgnoreCase(" ") || ele.getoption3value().equalsIgnoreCase("null") || ele.getoption3value().equalsIgnoreCase(null))
        {
            holder.rb4.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.rb4.setText(ele.getoption3value());
        }

        if(ele.getoption4value().equalsIgnoreCase("") || ele.getoption4value().equalsIgnoreCase(" ") || ele.getoption4value().equalsIgnoreCase("null") || ele.getoption4value().equalsIgnoreCase(null))
        {
            holder.rb5.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.rb5.setText(ele.getoption4value());
        }

        if(ele.getoption5value().equalsIgnoreCase("") || ele.getoption5value().equalsIgnoreCase(" ") || ele.getoption4value().equalsIgnoreCase("null") || ele.getoption5value().equalsIgnoreCase(null))
        {
            holder.rb6.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.rb6.setText(ele.getoption5value());
        }

//        holder.rb2.setText(ele.getoption1value());
//        holder.rb3.setText(ele.getoption2value());
//        holder.rb4.setText(ele.getoption3value());
//        holder.rb5.setText(ele.getoption4value());
//        holder.rb6.setText(ele.getoption5value());
        //holder.tvDesciption.setText(description);

//        if (ele.isHeader()) {
//            holder.radioGroup.setVisibility(View.GONE);
//        } else {

       // }

        setRadio(holder,elements.get(position).getState());



        holder.rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elements.get(position).setState(0);

                setRadio(holder, elements.get(position).getState());
               // Toast.makeText(context,  elements.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,  elements.get(position).getoption1value(), Toast.LENGTH_SHORT).show();

//                Toast toast = Toast.makeText(context,  elements.get(position).getoption1value(), Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();


                Global_Data.quastionmap.put(elements.get(position).getQuestioncode()+":"+elements.get(position).getQuestion(),elements.get(position).getoption1value());

                Log.d("map value","map value"+Global_Data.quastionmap);
            }
        });
        holder.rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elements.get(position).setState(1);
                setRadio(holder, elements.get(position).getState());

                // Toast.makeText(context,  elements.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(context,  elements.get(position).getoption1value(), Toast.LENGTH_SHORT).show();
                Global_Data.quastionmap.put(elements.get(position).getQuestioncode()+":"+elements.get(position).getQuestion(),elements.get(position).getoption1value());

                Log.d("map value","map value"+Global_Data.quastionmap);
            }
        });
        holder.rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elements.get(position).setState(2);
                setRadio(holder, elements.get(position).getState());
               // Toast.makeText(context,  elements.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(context,  elements.get(position).getoption2value(), Toast.LENGTH_SHORT).show();

                Global_Data.quastionmap.put(elements.get(position).getQuestioncode()+":"+elements.get(position).getQuestion(),elements.get(position).getoption2value());

                Log.d("map value","map value"+Global_Data.quastionmap);
            }
        });
        holder.rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elements.get(position).setState(3);
                setRadio(holder, elements.get(position).getState());
               // Toast.makeText(context,  elements.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,  elements.get(position).getoption3value(), Toast.LENGTH_SHORT).show();

                Global_Data.quastionmap.put(elements.get(position).getQuestioncode()+":"+elements.get(position).getQuestion(),elements.get(position).getoption3value());

                Log.d("map value","map value"+Global_Data.quastionmap);
            }
        });

        holder.rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elements.get(position).setState(4);
                setRadio(holder, elements.get(position).getState());
               // Toast.makeText(context,  elements.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(context,  elements.get(position).getoption4value(), Toast.LENGTH_SHORT).show();

                Global_Data.quastionmap.put(elements.get(position).getQuestioncode()+":"+elements.get(position).getQuestion(),elements.get(position).getoption4value());

                Log.d("map value","map value"+Global_Data.quastionmap);
            }
        });

        holder.rb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                elements.get(position).setState(5);
                setRadio(holder, elements.get(position).getState());
                //Toast.makeText(context,  elements.get(position).getQuestion(), Toast.LENGTH_SHORT).show();
              //  Toast.makeText(context,  elements.get(position).getoption5value(), Toast.LENGTH_SHORT).show();

                Global_Data.quastionmap.put(elements.get(position).getQuestioncode()+":"+elements.get(position).getQuestion(),elements.get(position).getoption5value());

                Log.d("map value","map value"+Global_Data.quastionmap);
            }
        });


    }

    private void setRadio(final ViewHolder holder, int selection) {

        System.out.println("SELECT:" + selection);
        RadioButton b1 = holder.rb1;
        RadioButton b2 = holder.rb2;
        RadioButton b3 = holder.rb3;
        RadioButton b4 = holder.rb4;
        RadioButton b5 = holder.rb5;
        RadioButton b6 = holder.rb6;

//        b1.setChecked(false);
//        b2.setChecked(false);
//        b3.setChecked(false);
//        b4.setChecked(false);

        if (selection == 0) b1.setChecked(true);
        if (selection == 1) b2.setChecked(true);
        if (selection == 2) b3.setChecked(true);
        if (selection == 3) b4.setChecked(true);
        if (selection == 4) b5.setChecked(true);
        if (selection == 5) b6.setChecked(true);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView tvTitle;
        public TextView Q_code;

        public RadioGroup radioGroup;
        public RadioButton rb1, rb2, rb3, rb4, rb5,rb6;


        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.Q_text);
            Q_code = (TextView) itemView.findViewById(R.id.Q_code);

            radioGroup = (RadioGroup) itemView.findViewById(R.id.myRadioGroup);
            rb1 = (RadioButton) itemView.findViewById(R.id.option_1);
            rb2 = (RadioButton) itemView.findViewById(R.id.option_2);
            rb3 = (RadioButton) itemView.findViewById(R.id.option_3);
            rb4 = (RadioButton) itemView.findViewById(R.id.option_4);

            rb5 = (RadioButton) itemView.findViewById(R.id.option_5);

            rb6 = (RadioButton) itemView.findViewById(R.id.option_6);

        }
    }

}