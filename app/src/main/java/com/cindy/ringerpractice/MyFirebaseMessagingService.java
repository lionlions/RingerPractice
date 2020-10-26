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
    private boolean alreadyGetMessage = false;

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

        if(!alreadyGetMessage){
            alreadyGetMessage = true;
            openActivity();
        }


    }

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        if(BuildConfig.DEBUG) Log.v(TAG, "===== handleIntent =====");

        if(!alreadyGetMessage){
            alreadyGetMessage = true;
            openActivity();
        }else{
            alreadyGetMessage = false;
        }

    }

    private void openActivity(){
        if(BuildConfig.DEBUG) Log.v(TAG, "===== openActivity =====");

        Intent newIntent = new Intent();
        newIntent.setClass(this, InCallActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);

    }

}
