package com.anchor.services

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.anchor.activities.LoginActivity
import com.anchor.activities.R


object NotificationHelper {

    fun displayNotification(context: Context, title: String, body: String) {

        val intent = Intent(context, LoginActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )

        val mBuilder = NotificationCompat.Builder(context, LoginActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(Color.RED)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.anchor_logo))
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

        val mNotificationMgr = NotificationManagerCompat.from(context)
        mNotificationMgr.notify(1, mBuilder.build())

    }

}