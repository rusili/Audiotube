package nyc.c4q.rusili.audiotube.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.activities.ActivityMain;
import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.service.ForegroundService;

public class PlayerControlsNotification {
    private NotificationManager notificationManager;
    private RemoteViews smallView;

    public PlayerControlsNotification (){}

    public Notification setUp(String packageNameParam, Context contextParam){

        smallView = new RemoteViews(packageNameParam, R.layout.notification_bar);
        Intent notificationIntent = new Intent(contextParam, ActivityMain.class);
        notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(contextParam, 0,
                notificationIntent, 0);

        // Pause
        Intent pauseIntent = new Intent(contextParam, ForegroundService.class);
        pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
        PendingIntent ppauseIntent = PendingIntent.getService(contextParam, 0,
                pauseIntent, 0);
        smallView.setOnClickPendingIntent(R.id.notification_button_pause, ppauseIntent);

        // Play
        Intent playIntent = new Intent(contextParam, ForegroundService.class);
        playIntent.setAction(Constants.ACTION.PLAY_ACTION);
        PendingIntent pplayIntent = PendingIntent.getService(contextParam, 0,
                playIntent, 0);
        smallView.setOnClickPendingIntent(R.id.notification_button_play, pplayIntent);

        // Next
        Intent nextIntent = new Intent(contextParam, ForegroundService.class);
        playIntent.setAction(Constants.ACTION.NEXT_ACTION);
        PendingIntent pnextIntent = PendingIntent.getService(contextParam, 0,
                nextIntent, 0);

        android.app.Notification notification = new NotificationCompat.Builder(contextParam)
                .setContentTitle("Audiotube")
                .setSmallIcon(android.R.drawable.btn_radio)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .setContent(smallView).build();

        return notification;
    }

//    public void setUp2(){
//        ServiceConnection mConnection = new ServiceConnection() {
//            public void onServiceConnected (ComponentName className, IBinder binder) {
//                ((KillService.KillBinder) binder).service.startService(new Intent(mContext, KillService.class));
//                smallView = new RemoteViews(mContext.getPackageName(), R.layout.notification_bar);
//                android.app.Notification mBuilder = new NotificationCompat.Builder(mContext)
//                        .setSmallIcon(android.R.drawable.btn_radio)
//                        .setContentTitle(String.valueOf(R.string.app_name))
//                        .setOngoing(true)
//                        .setContent(smallView)
//                        .build();
//
//                notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(555, mBuilder);
//            }
//
//            @Override
//            public void onServiceDisconnected (ComponentName name) {
//            }
//        };
//        mContext.bindService(new Intent(mContext, KillService.class), mConnection, Context.BIND_AUTO_CREATE);
//    }

}
