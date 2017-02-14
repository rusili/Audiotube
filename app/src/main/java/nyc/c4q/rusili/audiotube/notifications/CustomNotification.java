package nyc.c4q.rusili.audiotube.notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Button;
import android.widget.RemoteViews;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.google.PlayerControlsDemoActivityStripped;

public class CustomNotification {
    private Context mContext;
    private NotificationManager notificationManager;
    private Button buttonPlay;
    private Button buttonPause;
    private Button buttonExit;
    private RemoteViews smallView;

    public CustomNotification(Context contextParam){
        this.mContext = contextParam;
        setUp();
        initializeViews();
    }

    private void initializeViews(){
        Intent playIntent = new Intent(mContext, PlayerControlsDemoActivityStripped.class);
        playIntent.setAction("play");
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1, playIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        smallView.setOnClickPendingIntent(R.id.notification_button_play, pendingIntent);

        Intent pauseIntent = new Intent(mContext, PlayerControlsDemoActivityStripped.class);
        pauseIntent.setAction("pause");
        PendingIntent pendingIntent2 = PendingIntent.getActivity(mContext, 2, pauseIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        smallView.setOnClickPendingIntent(R.id.notification_button_play, pendingIntent2);
    }

    private void setUp(){
        smallView = new RemoteViews(mContext.getPackageName(), R.layout.notification_bar);
        android.app.Notification mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(android.R.drawable.btn_radio)
                .setContentTitle(String.valueOf(R.string.app_name))
                .setOngoing(true)
                .setContent(smallView)
                .build();

        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(555, mBuilder);
    }
}
