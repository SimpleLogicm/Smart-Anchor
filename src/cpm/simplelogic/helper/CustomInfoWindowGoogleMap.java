package cpm.simplelogic.helper;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anchor.activities.R;
import com.anchor.model.InfoWindowData;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_info_layout, null);

        TextView name_tv = view.findViewById(R.id.cname);
        TextView caddress = view.findViewById(R.id.caddress);
        TextView cdistance = view.findViewById(R.id.cdistance);
        TextView cmobile = view.findViewById(R.id.cmobile);
        //ImageView img = view.findViewById(R.id.pic);


        name_tv.setText(marker.getTitle());


        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(marker.getSnippet()))
        {
            caddress.setVisibility(View.VISIBLE);
            caddress.setText(marker.getSnippet());

        }
        else
        {
            caddress.setVisibility(View.GONE);
        }

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

//        int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
//                "drawable", context.getPackageName());
//        img.setImageResource(imageId);



        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(infoWindowData.getCdistance()))
        {
            cdistance.setVisibility(View.VISIBLE);
            cdistance.setText(infoWindowData.getCdistance());

        }
        else
        {
            cdistance.setVisibility(View.GONE);
        }

        if (Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJava(infoWindowData.getCmobile()))
        {
            cmobile.setVisibility(View.VISIBLE);
            cmobile.setText(infoWindowData.getCmobile());
        }
        else
        {
            cmobile.setVisibility(View.GONE);
        }


        cmobile.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = MotionEventCompat.getActionMasked(event);
                switch (action){
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        //transport_tv.setText(infoWindowData.getTransport());

        return view;
    }
}
