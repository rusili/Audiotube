package nyc.c4q.rusili.audiotube.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import nyc.c4q.rusili.audiotube.activities.ActivityMain;
import nyc.c4q.rusili.audiotube.notifications.PlayerControlsNotification;
import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.retrofit.RetrofitData;
import nyc.c4q.rusili.audiotube.youtube.MyYoutubePlayer;

public class ForegroundService extends Service {
    private String LOG_TAG = "ForegroundService: ";
    private YouTubePlayer youTubePlayer;
    private boolean isRepeat;
    private NotificationManager notificationManager;
    private PlayerControlsNotification playerControlsNotification = null;

    @Override
    public void onCreate () {
        super.onCreate();
        MyYoutubePlayer myYoutubePlayer = ActivityMain.myYoutubePlayer;
        youTubePlayer = myYoutubePlayer.getYouTubePlayer();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");
        Log.i("url: ", " " + url);
        url = "9JJmHYZQci4";

        RetrofitData retrofitData = new RetrofitData();
        retrofitData.getInfo(url);

        //Start player
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            youTubePlayer.loadVideo(url);
            isRepeat = false;

            if (notificationManager == null) {
                playerControlsNotification = new PlayerControlsNotification(getPackageName(), getApplicationContext());
                startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, playerControlsNotification.showNotification());
                notificationManager = playerControlsNotification.getManager();
            } else {
                startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, playerControlsNotification.updateNotification());
            }

            //Repeat On/Off
        } else if (intent.getAction().equals(Constants.ACTION.REPEAT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Repeat");
            isRepeat = !isRepeat;
            //Previous
        } else if (intent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(LOG_TAG, "Clicked Previous");
            youTubePlayer.previous();
            //Pause
        } else if (intent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Pause");
            youTubePlayer.pause();
            //Play
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play");
            youTubePlayer.play();
            //Next
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
            //Exit
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
            sendBroadcast(new Intent("android.intent.CLOSE_ACTIVITY"));
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }
}
