package com.anchor.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import cpm.simplelogic.helper.TargetValue_info;

public class ExpandableListAdaptern extends BaseExpandableListAdapter {

	private Context _context;
	private List<String> _listDataHeader; // header titles
	// child data in format of header title, child title
	private HashMap<String, List<String>> _listDataChild;
	private List<TargetValue_info> contactList;
	//int a =0;

	public ExpandableListAdaptern(Context context, List<String> listDataHeader,
								  List<TargetValue_info> contactList) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		//this._listDataChild = listChildData;
		this.contactList = contactList;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
//		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//				.get(childPosititon);

		return contactList.size();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

//		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_itemn, null);
		}

		//TargetValue_info det = contactList.get(groupPosition);

		TargetValue_info ci = contactList.get(groupPosition);
       // ++a;
		TextView prdcatg_more = convertView
				.findViewById(R.id.prdcatg_more);

		TextView target_grpby = convertView
				.findViewById(R.id.target_grpby);

		TextView achievd_grpby = convertView
				.findViewById(R.id.achievd_grpby);

		TextView age_grpby = convertView
				.findViewById(R.id.age_grpby);

		prdcatg_more.setText(ci.prdcatg_morestr);
		//month_more.setText(ci.monthgrpmore_str);
		target_grpby.setText(ci.targetgrpmore_str);
		achievd_grpby.setText(ci.achievedgrpmore_str);
		age_grpby.setText(ci.agegrpmore_str);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
//		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//				.size();
		return contactList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) this._context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.list_groupn, null);
		}

		TextView lblListHeader = convertView
				.findViewById(R.id.month_top);
		TextView month_grpby = convertView
				.findViewById(R.id.month_grpby);


		lblListHeader.setTypeface(null, Typeface.BOLD);
		lblListHeader.setText(headerTitle);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
