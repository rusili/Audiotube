package nyc.c4q.rusili.audiotube.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.youtube.player.YouTubePlayer;

import nyc.c4q.rusili.audiotube.activities.ActivityMain;
import nyc.c4q.rusili.audiotube.notifications.PlayerControlsNotification;
import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.youtube.MyYoutubePlayer;

public class ForegroundService extends Service {
    private String LOG_TAG = "ForegroundService: ";
    private YouTubePlayer youTubePlayer;
    private RemoteViews smallView;

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

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            youTubePlayer.loadVideo(url);

            PlayerControlsNotification playerControlsNotification = new PlayerControlsNotification();
            Notification notification = playerControlsNotification.setUp(getPackageName(), this);

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
        } else if (intent.getAction().equals(Constants.ACTION.PAUSE_ACTION)) {
            Log.i(LOG_TAG, "Clicked Pause");
            youTubePlayer.pause();
        } else if (intent.getAction().equals(Constants.ACTION.PLAY_ACTION)) {
            Log.i(LOG_TAG, "Clicked Play");
            youTubePlayer.play();
        } else if (intent.getAction().equals(Constants.ACTION.NEXT_ACTION)) {
            Log.i(LOG_TAG, "Clicked Next");
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }
}
