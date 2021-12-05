package com.example.foodify.Utils;

import android.content.Context;
import android.util.Log;

import com.example.foodify.Model.RecieveNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.nio.Buffer;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String mToken) {
        super.onNewToken(mToken);
        Log.d("TokenFirst", mToken);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fcm_token", mToken).apply();
    }

    @Override
    public void onMessageReceived(RemoteMessage notification) {
        String message = notification.getNotification().getBody();
        Log.d("Message", message);
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fcm_token", "empty");
    }
}
