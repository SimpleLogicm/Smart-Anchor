package com.anchor.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.anchor.activities.R;

public class CustomRenkViewDialog extends Dialog implements View.OnClickListener {


//    public CustomRenkViewDialog(Context context, int themeResId) {
//        super(context, themeResId);
//    }
//
//    public CustomRenkViewDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
//        super(context, cancelable, cancelListener);
//    }


    public Activity activity;
    public Dialog dialog;
    public Button renk_dialogok;
    TextView renk_dialog_title;
    RecyclerView renk_dialog_recycleview;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter adapter;
    String titles;


    public CustomRenkViewDialog(Activity a, RecyclerView.Adapter adapter,String title) {
        super(a);
        this.activity = a;
        this.adapter = adapter;
        this.titles = title;
        setupLayout();
    }

    private void setupLayout() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.renk_custom_dialog_layout);
        renk_dialogok = findViewById(R.id.renk_dialogok);

        renk_dialog_title = findViewById(R.id.renk_dialog_title);
        renk_dialog_recycleview = findViewById(R.id.renk_dialog_recycleview);
        mLayoutManager = new LinearLayoutManager(activity);
        renk_dialog_recycleview.setLayoutManager(mLayoutManager);

        renk_dialog_recycleview.setHasFixedSize(true);

        DividerItemDecoration horizontalDecoration2 = new DividerItemDecoration(renk_dialog_recycleview.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider2 = ContextCompat.getDrawable(activity, R.drawable.horizontal_divider_detail);
        horizontalDecoration2.setDrawable(horizontalDivider2);

        renk_dialog_recycleview.addItemDecoration(horizontalDecoration2);

        renk_dialog_recycleview.setItemAnimator(new DefaultItemAnimator());


        renk_dialog_recycleview.setAdapter(adapter);
        renk_dialogok.setOnClickListener(this);

        renk_dialog_title.setText(titles);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.renk_dialogok:
                dismiss();
                break;
                default:
                break;
        }
        dismiss();
    }
}
