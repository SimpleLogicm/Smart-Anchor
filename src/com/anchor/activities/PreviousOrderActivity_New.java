package com.anchor.activities;


import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.anchor.model.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class PreviousOrderActivity_New extends Activity {

	TextView textView1,tabletextview1,txtPreviousOrder;
	ImageView imgView;
	Button buttonPreviousAddMOre,buttonPreviousPreview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.previousorder);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#8A0808")));

		Intent i = getIntent();
		String name = i.getStringExtra("retialer");
		actionBar.setTitle(name);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		 TableLayout tl=(TableLayout)findViewById(R.id.tablePrevious);    
		 
		 ArrayList<Product> productList=new ArrayList<Product>();
		 Product p1=new Product("AP Deodorants", "MANGO 30", "50 GM",
				 "2", "2", "1", "Select Scheme", "Buy 6 and Get 2 Free", "178.5","270.0");
		 
		 Product p2=new Product("AP DEO Stick 15", "REBEL AFD", "250 ML",
				 "4", "4", "1", "Select Scheme", "Buy 6 and Get 2 Free", "178.5","270.0");
		
						 
		 productList.add(p1);
		 productList.add(p2);
		
		for (Iterator iterator = productList.iterator(); iterator.hasNext();) {
			Product product = (Product) iterator.next();
			
			
			TableRow tr1 = new TableRow(this);
			tr1.setLayoutParams(new LayoutParams( LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
			
			tabletextview1=new TextView(this);
			tabletextview1.setText(product.getCategory()+","+product.getSubcategory()+",Position:"+product.getProductName()+",Tyre Size:"+product.getProductSpec());

			//tabletextview1.setText(product.getProductName());
			tabletextview1.setWidth(150);
			tr1.addView(tabletextview1);
			tabletextview1=new TextView(this);
			tabletextview1.setText(product.getProductQuantity());
			tr1.addView(tabletextview1);
			
			imgView=new ImageView(this);
			imgView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.reorder));
			tr1.addView(imgView);
			
		
			
//			//textview.getTextColors(R.color.)
//			textview.setTextColor(Color.YELLOW);
//			tr1.addView(tabletextview1);
//			tr1.addView(tabletextview1);
//			tr1.addView(tabletextview1);
			tl.addView(tr1);
			
			View view = new View(this);
			view.setBackgroundResource(R.drawable.line);
			//view.setBackgroundColor(Color.BLACK);
			tl.addView(view);
			buttonPreviousAddMOre=(Button) findViewById(R.id.buttonPreviousAddMOre);
			txtPreviousOrder=(TextView) findViewById(R.id.txtPreviousOrder);
			 String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	     
			txtPreviousOrder.setText("Date : "+date);
		
			buttonPreviousAddMOre.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View b, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_UP)
				    {
				        //up event
				        b.setBackgroundColor(Color.parseColor("#414042"));
				        return true;
				    }
				    if(event.getAction() == MotionEvent.ACTION_DOWN)
				    {
				        //down event
				        b.setBackgroundColor(Color.parseColor("#910505"));
				    }
					return false;
				}
			});
			
			buttonPreviousPreview=(Button) findViewById(R.id.buttonPreviousPreview);
			buttonPreviousAddMOre.setBackgroundColor(Color.parseColor("#414042"));
			buttonPreviousPreview.setBackgroundColor(Color.parseColor("#414042"));
			buttonPreviousPreview.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View b, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == MotionEvent.ACTION_UP)
				    {
				        //up event
				        b.setBackgroundColor(Color.parseColor("#414042"));
				        return true;
				    }
				    if(event.getAction() == MotionEvent.ACTION_DOWN)
				    {
				        //down event
				        b.setBackgroundColor(Color.parseColor("#910505"));
				    }
					return false;
				}
			});
			
			
		}
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
