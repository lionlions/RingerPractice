package com.cindy.ringerpractice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class FirebaseBackgroundService extends WakefulBroadcastReceiver {

    private String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(BuildConfig.DEBUG) Log.v(TAG, "===== onReceive =====");

        Intent newIntent = new Intent();
        newIntent.setClass(context, InCallActivity.class);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(newIntent);

    }
}
