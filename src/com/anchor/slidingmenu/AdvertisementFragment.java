package com.anchor.slidingmenu;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;

import com.anchor.activities.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AdvertisementFragment extends Fragment {
		
	public AdvertisementFragment(){}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		View rootView = inflater.inflate(R.layout.fragment_logout, container, false);
         
        ImageView order=(ImageView) rootView.findViewById(R.id.order);
        ImageView calendar=(ImageView) rootView.findViewById(R.id.calendar);
        
        calendar.setOnClickListener(new OnClickListener() {
  		  @Override
  			public void onClick(View v) {
  				// TODO Auto-generated method stub
  				//Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
  			final Intent intent = new Intent(getActivity().getApplicationContext(), HomeFragment.class);
			SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);
			// disable default animation for new intent
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			intent.putExtra("retialer", ""+sp.getString("RetailerName", ""));
  			}
  		});
        
        order.setOnClickListener(new OnClickListener() {
		  @Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			  
			  final Intent intent = new Intent(getActivity().getApplicationContext(), HomeFragment.class);
				SharedPreferences  sp=getActivity().getSharedPreferences("SimpleLogic", 0);
				// disable default animation for new intent
				intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.putExtra("retialer", ""+sp.getString("RetailerName", ""));
				//Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
//				Intent i=new Intent(getActivity(),MainActivity.class);
//				startActivity(i);
				
			}
		});
		
        return rootView;
    }
}
