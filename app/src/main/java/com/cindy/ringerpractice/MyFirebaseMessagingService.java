package com.cindy.ringerpractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private String TAG = getClass().getSimpleName();

    public MyFirebaseMessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String newToken) {
        super.onNewToken(newToken);
        if(BuildConfig.DEBUG) Log.v(TAG, "===== onNewToken =====");
        if(BuildConfig.DEBUG) Log.i(TAG, "newToken: " + newToken);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(BuildConfig.DEBUG) Log.v(TAG, "===== onMessageReceived =====");


    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        if(BuildConfig.DEBUG) Log.v(TAG, "===== handleIntent =====");

    }
}
