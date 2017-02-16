package nyc.c4q.rusili.audiotube.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

// A service used to kill notifications if/when the app is cleared instead of exited.
// Parameters:  1) Service
// Needs:       1) Service ID #: 0

public class KillService extends Service {

    public class KillBinder extends Binder {
        public final Service service;

        public KillBinder (Service service) {
            this.service = service;
        }
    }

    public static int NOTIFICATION_ID = 555;
    private NotificationManager notificationManager;
    private final IBinder mBinder = new KillBinder(this);

    @Override
    public IBinder onBind (Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate () {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }
}