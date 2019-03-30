package com.noah.taxiclient;

/**
 * Created by YH on 2017-08-26.
 */
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.noah.taxiclient.Activity.Act_main;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String TAG = "FirebaseMsgService";
    public static final String DRIVER_OK = "DRIVER_OK";
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("씌바","왓더뻑");
        Log.i("씌바",remoteMessage.getData().get("message"));

        //고객의 콜요청이 왓을경우.
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(remoteMessage.getData().get("message"));

        if(jsonObject.get("flag").equals("driver_ok")||jsonObject.get("flag").equals("get_client")) {
            Intent intent = new Intent(DRIVER_OK);
            intent.putExtra("msg",remoteMessage.getData().get("message"));
            //인텐트에 DRIVER_OK로 msg를 담아 브로드 캐스트를 보낸다.
            sendBroadcast(intent);
        }else{

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        //추가한것
//        sendNotification(remoteMessage.getData().get("message"));

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, Act_main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("FCM Push Test")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
