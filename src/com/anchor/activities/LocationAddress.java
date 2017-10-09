package com.anchor.activities;

/**
 * Created by vinod on 04-03-2016.
 */
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.anchor.activities.Check_Null_Value.isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString;

public class LocationAddress {
    private static final String TAG = "LocationAddress";

    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
//                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                            sb.append(address.getAddressLine(i)).append("\n");
//                        }
                        sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addressList.get(0).getAddressLine(0))+" ");
                        if(!(sb.indexOf(addressList.get(0).getLocality()) > 0))
                        {
                            sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addressList.get(0).getLocality())+" ");
                        }

                        if(!(sb.indexOf(addressList.get(0).getAdminArea()) > 0))
                        {
                            sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addressList.get(0).getAdminArea())+" ");
                        }

                        if(!(sb.indexOf(addressList.get(0).getCountryName()) > 0))
                        {
                            sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addressList.get(0).getCountryName())+" ");
                        }

                        if(!(sb.indexOf(addressList.get(0).getPostalCode()) > 0))
                        {
                            sb.append(isNotNullNotEmptyNotWhiteSpaceOnlyByJavaString(addressList.get(0).getPostalCode())+" ");
                        }

                       // sb.append("\n");
                        //sb.append(address.getPostalCode()).append("\n");
                        //sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
//                        result = "Latitude: " + latitude + " Longitude: " + longitude +
//                                "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}
