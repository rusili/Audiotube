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
import nyc.c4q.rusili.audiotube.retrofit.JSON.JSONResponse;
import nyc.c4q.rusili.audiotube.retrofit.RetrofitData;
import nyc.c4q.rusili.audiotube.utility.Constants;
import nyc.c4q.rusili.audiotube.utility.MyYoutubePlayer;

public class ForegroundService extends Service {
    private String LOG_TAG = "ForegroundService: ";
    private YouTubePlayer youTubePlayer;
    private boolean isRepeat;
    private NotificationManager notificationManager = null;
    private PlayerControlsNotification playerControlsNotification = null;
    private Intent mIntent;

    @Override
    public void onCreate () {
        super.onCreate();
        MyYoutubePlayer myYoutubePlayer = ActivityMain.myYoutubePlayer;
        youTubePlayer = myYoutubePlayer.getYouTubePlayer();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        this.mIntent = intent;
        String url = intent.getStringExtra("url");
        Log.i("url: ", " " + url);
        url = "9JJmHYZQci4";

        RetrofitData retrofitData = new RetrofitData();
        retrofitData.getInfo(url, this);

        return START_STICKY;
    }

    public void onNetworkResponse(String url, JSONResponse jsonResponse) {
        String title = jsonResponse.getItems().get(0).getSnippet().getTitle();
        String channel = jsonResponse.getItems().get(0).getSnippet().getChannelTitle();

        if (notificationManager == null) {
            playerControlsNotification = new PlayerControlsNotification();
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, playerControlsNotification.showNotification(getPackageName(), getApplicationContext(), title, channel));
            notificationManager = playerControlsNotification.getManager();
        } else {
            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, playerControlsNotification.updateNotification(getPackageName(), getApplicationContext(), title, channel));
        }

        if (mIntent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            youTubePlayer.loadVideo(url);
            isRepeat = false;
            //Repeat On/Off
        } else if (mIntent.getAction().equals(Constants.ACTION.REPEAT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Repeat");
            isRepeat = !isRepeat;
            //Previous
        } else if (mIntent.getAction().equals(Constants.ACTION.PREV_ACTION)) {
            Log.i(LOG_TAG, "Clicked Previous");
            youTubePlayer.previous();
            //Pause
        } else if (mIntent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Pause");
            youTubePlayer.pause();
            //Play
        } else if (mIntent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play");
            youTubePlayer.play();
            //Next
        } else if (mIntent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
            //Exit
        } else if (mIntent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
            sendBroadcast(new Intent("android.intent.CLOSE_ACTIVITY"));
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }
}
