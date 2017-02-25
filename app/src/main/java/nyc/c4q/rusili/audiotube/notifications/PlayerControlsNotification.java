package nyc.c4q.rusili.audiotube.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.activities.ActivityMain;
import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.service.ForegroundService;

public class PlayerControlsNotification {
    private RemoteViews customView;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder notificationBuilder;
    private int notifyID = 101;
    private String mPackageName;
    private Context mContext;

    public PlayerControlsNotification (String packageNameParam, Context contextParam) {
        this.mPackageName = packageNameParam;
        this.mContext = contextParam;
    }

    public Notification showNotification() {
        customView = new RemoteViews( mPackageName, R.layout.view_notification);
        customView.setTextViewText(R.id.notification_title, "A");
        customView.setTextViewText(R.id.notification_channel, "B");

        Intent notificationIntent = new Intent(mContext, ActivityMain.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
                notificationIntent, 0);

        // Repeat On/Off
        Intent repeatIntent = new Intent(mContext, ForegroundService.class);
        repeatIntent.setAction(Constants.ACTION.REPEAT_ACTION);
        PendingIntent pRepeatIntent = PendingIntent.getService(mContext, 0,
                repeatIntent, 0);
        customView.setOnClickPendingIntent(R.id.notification_button_repeat, pRepeatIntent);

        // prev
        Intent prevIntent = new Intent(mContext, ForegroundService.class);
        prevIntent.setAction(Constants.ACTION.PREV_ACTION);
        PendingIntent pPrevIntent = PendingIntent.getService(mContext, 0,
                prevIntent, 0);
        customView.setOnClickPendingIntent(R.id.notification_button_prev, pPrevIntent);

        // Pause
        Intent pauseIntent = new Intent(mContext, ForegroundService.class);
        pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
        PendingIntent ppauseIntent = PendingIntent.getService(mContext, 0,
                pauseIntent, 0);
        customView.setOnClickPendingIntent(R.id.notification_button_pause, ppauseIntent);

        // Play
        Intent playIntent = new Intent(mContext, ForegroundService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(mContext, 0,
                playIntent, 0);
        customView.setOnClickPendingIntent(R.id.notification_button_play, pplayIntent);

        // Exit
        Intent exitIntent = new Intent(mContext, ForegroundService.class);
        exitIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        PendingIntent pExitIntent = PendingIntent.getService(mContext, 0,
                exitIntent, 0);
        customView.setOnClickPendingIntent(R.id.notification_button_exit, pExitIntent);

        // Next
        Intent nextIntent = new Intent(mContext, ForegroundService.class);
        playIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(mContext, 0,
                nextIntent, 0);

        Notification notification = new Notification();
        notificationBuilder = new NotificationCompat.Builder( mContext )
                .setAutoCancel( true )
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("Audiotube");
        notification = notificationBuilder.build();
        notification.bigContentView = customView;

        notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        return notification;
    }

    public void getVideoInfo (String titleParam, String channelTitleParam) {
        // set views
    }

}
