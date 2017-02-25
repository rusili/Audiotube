package nyc.c4q.rusili.audiotube.notifications;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;
import android.widget.RemoteViews;

import nyc.c4q.rusili.audiotube.R;

public class PlayerControlsNotification {
    private RemoteViews smallView;
    private Button buttonExit;
    private String packageName;

    public PlayerControlsNotification () {
    }

    public Notification showNotification(String packageNameParam, Context contextParam ) {
        RemoteViews customView = new RemoteViews( packageNameParam, R.layout.view_notification);

        Notification notification = new NotificationCompat.Builder( contextParam )
                .setAutoCancel( true )
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle("Audiotube")
                .build();

        notification.bigContentView = customView;

        return notification;
    }

    public Notification setUp (String packageNameParam, Context contextParam) {
        this.packageName = packageNameParam;

        //smallView = new RemoteViews(packageNameParam, R.layout.notification_bar);
//        Intent notificationIntent = new Intent(contextParam, ActivityMain.class);
//        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(contextParam, 0,
//                notificationIntent, 0);
//
//        // Repeat On/Off
//        Intent repeatIntent = new Intent(contextParam, ForegroundService.class);
//        repeatIntent.setAction(Constants.ACTION.REPEAT_ACTION);
//        PendingIntent pRepeatIntent = PendingIntent.getService(contextParam, 0,
//                repeatIntent, 0);
//        smallView.setOnClickPendingIntent(R.id.notification_button_repeat, pRepeatIntent);
//
//        // prev
//        Intent prevIntent = new Intent(contextParam, ForegroundService.class);
//        prevIntent.setAction(Constants.ACTION.PREV_ACTION);
//        PendingIntent pPrevIntent = PendingIntent.getService(contextParam, 0,
//                prevIntent, 0);
//        smallView.setOnClickPendingIntent(R.id.notification_button_prev, pPrevIntent);
//
//        // Pause
//        Intent pauseIntent = new Intent(contextParam, ForegroundService.class);
//        pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
//        PendingIntent ppauseIntent = PendingIntent.getService(contextParam, 0,
//                pauseIntent, 0);
//        smallView.setOnClickPendingIntent(R.id.notification_button_pause, ppauseIntent);
//
//        // Play
//        Intent playIntent = new Intent(contextParam, ForegroundService.class);
//        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
//        PendingIntent pplayIntent = PendingIntent.getService(contextParam, 0,
//                playIntent, 0);
//        smallView.setOnClickPendingIntent(R.id.notification_button_play, pplayIntent);
//
//        // Exit
//        Intent exitIntent = new Intent(contextParam, ForegroundService.class);
//        exitIntent.setAction(Constants.ACTION.EXIT_ACTION);
//        PendingIntent pExitIntent = PendingIntent.getService(contextParam, 0,
//                exitIntent, 0);
//        smallView.setOnClickPendingIntent(R.id.notification_button_exit, pExitIntent);
//
//        // Next
//        Intent nextIntent = new Intent(contextParam, ForegroundService.class);
//        playIntent.setAction(Constants.ACTION.NEXT_ACTION);
//        PendingIntent pnextIntent = PendingIntent.getService(contextParam, 0,
//                nextIntent, 0);

        Notification notification = new NotificationCompat.Builder(contextParam)
                .setContentTitle("Audiotube")
                .setSmallIcon(android.R.drawable.btn_radio)
                .setOngoing(true)
                .build();

        return notification;
    }


}
