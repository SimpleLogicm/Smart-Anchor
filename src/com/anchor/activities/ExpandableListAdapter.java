package com.anchor.activities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

	private Activity context;
	private Map<String, List<String>> laptopCollections;
	
	private Map<String, List<String>> laptopCollectionsNEW;
	
	private List<String> laptops;
	private List<String> child_count;
	HashMap<Integer, Integer> mCheckedStates; 
	CheckBox delete;

	public ExpandableListAdapter(Activity context, List<String> laptops,
			Map<String, List<String>> laptopCollections) {
		this.context = context;
		this.laptopCollections = laptopCollections;
		this.laptops = laptops;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return laptopCollections.get(laptops.get(groupPosition)).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	
	
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String laptop = (String) getChild(groupPosition, childPosition);
		LayoutInflater inflater = context.getLayoutInflater();
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.exchild_item, null);
		}
		
		TextView item = convertView.findViewById(R.id.laptop);
		
		// delete = (CheckBox) convertView.findViewById(R.id.delete);
		// delete.setChecked(false);
		 
//		 delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//		       @Override
//		       public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
//		    	   
//		    	   if (isChecked) {
//		    		   
//		    		   List<String> child = laptopCollections.get(laptops.get(groupPosition));
//		    		   //child_count.addAll(laptopCollections.get(laptops.get(groupPosition).toString()));
//		    		   
//		    		  // String abc  = laptopCollections.
//		    		   //laptopCollectionsNEW.put(laptopCollections.g, laptopCollections.get(laptops.get(childPosition)));
//		    		   
//		    		  Log.d("child bame", ""+laptops.get(groupPosition));
//		    		  Log.d("child bame2", ""+child);
//		    		   
//		    		   //laptopCollectionsNEW.put("", );
//		    		   
//		    		  // Log.d("abc", ""+Arrays.asList(laptops.get(groupPosition)));
//		    		   
//		    		  // Log.d("child vame", ""+Arrays.asList(child_count));
////		    		   Set<String> keys = laptopCollections.keySet();
////		    	        for(String key: keys){
////		    	            //System.out.println(key);
////		    	            Log.d("key", ""+key);
////		    	        }
//		    	        
//		    	        for (Entry<String, List<String>> entry : laptopCollections.entrySet()) {
//		    	            String key = entry.getKey();
//		    	            List<String> value = entry.getValue();
//		    	            
//		    	            //laptopCollectionsNEW.put(key,value);
//		    	            Log.d("key a", ""+key);
//		    	            Log.d("value a", ""+Arrays.asList(value));
//		    	        }
//
////				        if (mCheckedStates.containsValue(groupPosition)) {
////				            // I'm using a bitmap to keep resources low, but it's not necessary to do it this way
////				            int childmap = mCheckedStates.get(groupPosition);
////				            childmap += (1 << childPosition);
////				            mCheckedStates.put(groupPosition, childmap);
////				        } else {
////				            mCheckedStates.put(groupPosition, (1 << childPosition));
////				        }
//				    } else {
//				    	
//				    	//laptopCollectionsNEW.get(groupPosition).remove(laptopCollections.get(laptops.get(groupPosition)));
//				    	//child_count.addAll(laptopCollections.get(laptops.get(groupPosition).toString()));
//				    	// Log.d("child vame", ""+Arrays.asList(child_count));
////				        if (mCheckedStates.containsValue(groupPosition)) {
//////				            int childmap = mCheckedStates.get(groupPosition);
//////				            if (childmap == (1 << childPosition)) {
//////				                mCheckedStates.remove(groupPosition);
//////				            } else {
//////				                childmap &= ~(1 << childPosition);
//////				                mCheckedStates.put(groupPosition, childmap);
//////				            }
////				        }
//				    }
//
//		       }
//		   }
		//);     
		 //delete.setOnCheckedChangeListener(this);
		 
		
		//delete.setOnCheckedChangeListener(this);
//		delete.setOnClickListener(new OnClickListener() {
//
//	        @Override
//	        public void onClick(View v) {
//
//	            boolean isChecked = delete.isChecked();
//	            
//	            List<String> child = laptopCollections.get(laptops.get(groupPosition));
//
//	            Log.d("Debug", "isChecked = " + String.valueOf(isChecked));
//
//	            if (isChecked) {
//
//	               // selectedNumbers.add(numberText);
//
//	            } else {
//
//	               // selectedNumbers.remove(numberText);
//	            }
//
//	            //childViewHolder.mCheckBox.setChecked(isChecked);
//
//	           // mGetChecked[mChildPosition] = isChecked;
//	           // mChildCheckStates.put(contactName, mGetChecked);
//	        }
//	    });
//		delete.setOnClickListener(new OnClickListener() {
//			
//			public void onClick(View v) {
//				AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setMessage("Do you want to remove?");
//				builder.setCancelable(false);
//				builder.setPositiveButton("Yes",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//								List<String> child = 
//									laptopCollections.get(laptops.get(groupPosition));
//								child.remove(childPosition);
//								notifyDataSetChanged();
//							}
//						});
//				builder.setNegativeButton("No",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog, int id) {
//								dialog.cancel();
//							}
//						});
//				AlertDialog alertDialog = builder.create();
//				alertDialog.show();
//			}
//		});
		
		item.setText(laptop);
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return laptopCollections.get(laptops.get(groupPosition)).size();
	}

	public Object getGroup(int groupPosition) {
		return laptops.get(groupPosition);
	}

	public int getGroupCount() {
		return laptops.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String laptopName = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.expen_group_item,
					null);
		}
		TextView item = convertView.findViewById(R.id.laptop);
		item.setTypeface(null, Typeface.BOLD);
		item.setText(laptopName);
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	
}