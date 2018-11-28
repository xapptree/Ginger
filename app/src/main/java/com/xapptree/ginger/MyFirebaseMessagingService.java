package com.xapptree.ginger;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.moengage.push.PushManager;
import com.moengage.pushbase.push.MoEngageNotificationUtils;

import java.util.Map;

/**
 * Created by Akbar on 9/20/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        /*Intercom handling push Message*/
        Map message = remoteMessage.getData();
        Log.e("fbp data", message.toString());
        if (remoteMessage != null) {
            Map<String, String> pushPayload = remoteMessage.getData();
            if (MoEngageNotificationUtils.isFromMoEngagePlatform(pushPayload)) {
                PushManager.getInstance().getPushHandler().handlePushPayload(getApplicationContext(), pushPayload);
            } else {
                //your own logic
                sendNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));

            }
        }

    }

    private void sendNotification(String mTitle, String mMessage) {
        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle(mTitle)
                .setContentText(mMessage)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(mMessage))
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
