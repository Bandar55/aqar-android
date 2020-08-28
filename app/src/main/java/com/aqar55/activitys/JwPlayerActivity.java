package com.aqar55.activitys;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.aqar55.R;
import com.aqar55.helper.Firebase.app.Config;
import com.aqar55.helper.Firebase.utils.NotificationUtils;
import com.aqar55.model.CommonModel;
import com.google.firebase.messaging.FirebaseMessaging;
import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.BufferChangeEvent;
import com.longtailvideo.jwplayer.events.CompleteEvent;
import com.longtailvideo.jwplayer.events.FullscreenEvent;
import com.longtailvideo.jwplayer.events.LevelsChangedEvent;
import com.longtailvideo.jwplayer.events.SeekEvent;
import com.longtailvideo.jwplayer.events.SeekedEvent;
import com.longtailvideo.jwplayer.events.TimeEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;
import com.longtailvideo.jwplayer.media.playlists.PlaylistItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JwPlayerActivity extends AppCompatActivity implements VideoPlayerEvents.OnFullscreenListener, VideoPlayerEvents.OnCompleteListener,VideoPlayerEvents.OnSeekedListener,VideoPlayerEvents.OnBufferChangeListener ,VideoPlayerEvents.OnLevelsChangedListener ,VideoPlayerEvents.OnTimeListener{

    private String viewLink;

    @BindView(R.id.tvASecId)
    TextView tvASecId;

    @BindView(R.id.ivVolume)
    CheckBox ivVolume;

    @BindView(R.id.playerView)
    JWPlayerView playerView;

    String from="";

    //for check for the first time
    private boolean isFirstTime=false;

    //once api hit this boolean restrict it from rehit
    private boolean viewCount=false;

    private BroadcastReceiver notiBroadcastReceiver;
    private String instance_id="";

    private double getTotalDuration=0.0;
    private int getPercentfromDuration=0;
    public static Intent getIntent(Context  context){
        return new Intent(context,JwPlayerActivity.class);
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jw_player);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_SECURE | WindowManager.LayoutParams.FLAG_SECURE);
        ButterKnife.bind(this);



        //handle player
        initialiseJwPlayer();





    }


    @OnClick({R.id.ivVolume})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivVolume:
                if (ivVolume.isChecked()) {
                    playerView.setMute(true);
                } else {
                    playerView.setMute(false);
                }
                break;
        }
    }

    private void initialiseJwPlayer() {


        PlaylistItem video = new PlaylistItem("https://res.cloudinary.com/dfqkwolry/video/upload/v1564570563/rgv1qdzwiz7x069qdhlb.mov");
        playerView.addOnFullscreenListener(this);
        playerView.addOnCompleteListener(this);
        playerView.addOnBufferChangeListener(this);
        playerView.addOnLevelsChangedListener(this);
        playerView.addOnTimeListener(this);
        playerView.load(video);


    }







    @Override
    protected void onPause() {
        super.onPause();

        if (notiBroadcastReceiver != null)
            LocalBroadcastManager.getInstance(this).unregisterReceiver(notiBroadcastReceiver);
        // Let JW Player know that the app is going to the background
        playerView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerView.onDestroy();
        unregisterPushNoti();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerView.onResume();

    }

    protected void unregisterPushNoti() {
        try {
            unregisterReceiver(notiBroadcastReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onFullscreen(FullscreenEvent fullscreenEvent) {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // Set fullscreen when the device is rotated to landscape
        playerView.setFullscreen(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE, true);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Exit fullscreen when the user pressed the Back button
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (playerView.getFullscreen()) {
                playerView.setFullscreen(false, false);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onComplete(CompleteEvent completeEvent) {

        isFirstTime=false;
        viewCount=false;
    }


    @Override
    public void onSeeked(SeekedEvent seekedEvent) {

    }


    @Override
    public void onBufferChange(BufferChangeEvent bufferChangeEvent) {

        if(getTotalDuration==0.0) {
            getTotalDuration = playerView.getDuration();
            getPercentfromDuration=get90PercentofTotalDuration();

        }else {
            isFirstTime=true;
        }
    }


    @Override
    public void onLevelsChanged(LevelsChangedEvent levelsChangedEvent) {


    }

    @Override
    public void onTime(TimeEvent timeEvent) {


        if(from.equalsIgnoreCase("LIVE")){

            if((isFirstTime==true)&&(viewCount==false)) {
                if (getTotalDuration(timeEvent.getPosition()) >= getPercentfromDuration) {
                    // Toast.makeText(this, "Api Hit", Toast.LENGTH_SHORT).show();
                    //increaseVideoCount(instance_id);
                    viewCount=true;
                }
            }
        }
    }

    //get 90 percent from total duration
    private int get90PercentofTotalDuration(){

        int duration=0;
        int duration_new=0;
        try {
            double initial=((getTotalDuration*90)/100);
            duration= (int)initial;
            String duration_value=String.valueOf(duration);
            String[] values = duration_value.split("\\.");
            duration_new=Integer.parseInt(values[0]);

        }catch (Exception e){
            e.printStackTrace();
        }

        return duration_new;
    }

    private int getTotalDuration(double Playerduration){

        int duration=0;
        int duration_new=0;
        try {
            double initial=Playerduration;
            duration= (int)initial;
            String duration_value=String.valueOf(duration);
            String[] values = duration_value.split("\\.");
            duration_new=Integer.parseInt(values[0]);

        }catch (Exception e){
            e.printStackTrace();
        }

        return duration_new;
    }

}
