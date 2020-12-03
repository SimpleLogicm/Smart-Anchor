package com.anchor.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

public class Developeroptionservice extends BroadcastReceiver {
    Context context;
    int adb;

    @Override
    public void onReceive(final Context context, Intent intent) {

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;


        if (currentapiVersion >= 17) {
            adb = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        } else {
            adb = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0);
        }

        if (adb == 1) {

            AlertDialog alertDialog = new AlertDialog.Builder(context).create(); //Read Update
            alertDialog.setTitle("Anchor");
            alertDialog.setMessage("Your Developer options is enabled would you like to disable Developer option.");
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog1, int which) {
                    // TODO Auto-generated method stub
                    dialog1.dismiss();
                    context.startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS));


                }
            });


            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.cancel();
                }
            });


            try {
                alertDialog.show();
            } catch (Exception e) {
                // WindowManager$BadTokenException will be caught and the app would not display
                // the 'Force Close' message
            }

        }
    }
}
