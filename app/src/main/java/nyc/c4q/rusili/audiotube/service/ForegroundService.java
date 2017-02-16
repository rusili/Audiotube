package nyc.c4q.rusili.audiotube.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.android.youtube.player.YouTubePlayer;

import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.activities.ActivityMain;
import nyc.c4q.rusili.audiotube.youtube.MyYoutubePlayer;

public class ForegroundService extends Service {
    private String LOG_TAG = "ForegroundService: ";
    private MyYoutubePlayer myYoutubePlayer;
    private YouTubePlayer youTubePlayer;

    @Override
    public void onCreate () {
        super.onCreate();
        myYoutubePlayer = ActivityMain.myYoutubePlayer;
        youTubePlayer = myYoutubePlayer.getYouTubePlayer();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        String url = intent.getStringExtra("url");
        Log.i("url: ", " " + url);
        youTubePlayer.loadVideo(url);

        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");

            Intent notificationIntent = new Intent(this, ActivityMain.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            // Pause
            Intent pauseIntent = new Intent(this, ForegroundService.class);
            pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
            PendingIntent ppauseIntent = PendingIntent.getService(this, 0,
                    pauseIntent, 0);

            // Play
            Intent playIntent = new Intent(this, ForegroundService.class);
            playIntent.setAction(Constants.ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);

            // Next
            Intent nextIntent = new Intent(this, ForegroundService.class);
            playIntent.setAction(Constants.ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Audiotube")
                    .setSmallIcon(android.R.drawable.btn_radio)
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)
                    .addAction(android.R.drawable.ic_media_previous,
                            "Pause", ppauseIntent)
                    .addAction(android.R.drawable.ic_media_play, "Play",
                            pplayIntent)
                    .addAction(android.R.drawable.ic_media_next, "Next",
                            pnextIntent).build();
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
