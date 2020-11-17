package com.cindy.ringerpractice;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    private Button vPhoneRingTone;
    private Button vAlarmRingTone;
    private Button vNotificationRingTone;
    private Button vStop;
    private Ringtone mCurrentRingtone;
    private MediaPlayer mCurrentMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processView();
        processListener();

    }

    private void processView(){
        vPhoneRingTone = findViewById(R.id.phone_ringtone);
        vAlarmRingTone = findViewById(R.id.alarm_ringtone);
        vNotificationRingTone = findViewById(R.id.notification_ringtone);
        vStop = findViewById(R.id.stop);
    }

    private void processListener(){
        vPhoneRingTone.setOnClickListener(mButtonClickListener);
        vAlarmRingTone.setOnClickListener(mButtonClickListener);
        vNotificationRingTone.setOnClickListener(mButtonClickListener);
        vStop.setOnClickListener(mButtonClickListener);
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            int ringtoneType = -1;

            switch(view.getId()){
                case R.id.phone_ringtone:
                    ringtoneType = RingtoneManager.TYPE_RINGTONE;
                    break;
                case R.id.alarm_ringtone:
                    ringtoneType = RingtoneManager.TYPE_ALARM;
                    break;
                case R.id.notification_ringtone:
                    ringtoneType = RingtoneManager.TYPE_NOTIFICATION;
                    break;
                case R.id.stop:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        if(mCurrentRingtone!=null){
                            mCurrentRingtone.stop();
                        }
                    }else{
                        if(mCurrentMediaPlayer!=null){
                            mCurrentMediaPlayer.stop();
                        }
                    }
                    break;
            }

            if(ringtoneType!=-1){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    playRingtone(ringtoneType);
                }else{
                    playRingtoneBelowP(ringtoneType);
                }
            }

        }
    };

    /**
     * 這邊要注意一個問題就是
     * 這個方法是用 RingtoneManager 去撥放不同 Type 的鈴聲
     * 所以音量調整都會是調整鈴聲的音量
     * 調整媒體音量或是鬧鐘音量都是沒有效果的
     * */
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void playRingtone(int type){
        if(mCurrentRingtone!=null && mCurrentRingtone.isPlaying()){
            return;
        }
        Uri ringtoneUri = RingtoneManager.getDefaultUri(type);
        if(ringtoneUri!=null){
            mCurrentRingtone = RingtoneManager.getRingtone(this, ringtoneUri);
            mCurrentRingtone.setLooping(true);
            mCurrentRingtone.play();
        }
    }

    /**
     * P 以下為了 Loop 用 MediaPlayer 來播
     * 但這樣要注意一個問題就是
     * 他的音量是照著媒體音量去走的
     * 而不是系統的靜音就靜音、響鈴就響鈴音量
     * 所以可以透過 AudioManager 的 RingerMode 去做不同的事情
     * */
    private void playRingtoneBelowP(int type){
        if(mCurrentMediaPlayer!=null && mCurrentMediaPlayer.isPlaying()){
            return;
        }
        Uri ringtoneUri = RingtoneManager.getDefaultUri(type);
        if(ringtoneUri!=null){
            AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            switch (audioManager.getRingerMode()){
                case AudioManager.RINGER_MODE_NORMAL:
                    //響鈴
                    mCurrentMediaPlayer = MediaPlayer.create(this, ringtoneUri);
                    mCurrentMediaPlayer.setLooping(true);
                    mCurrentMediaPlayer.start();
                    break;
                case AudioManager.RINGER_MODE_VIBRATE:
                    //震動
                    break;
                case AudioManager.RINGER_MODE_SILENT:
                    //靜音
                    break;
            }
        }
    }

}