package nyc.c4q.rusili.audiotube.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.youtube.player.YouTubeBaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.service.ForegroundService;
import nyc.c4q.rusili.audiotube.utility.Constants;
import nyc.c4q.rusili.audiotube.utility.MyYoutubePlayer;

public class ActivityMain extends YouTubeBaseActivity implements View.OnClickListener {
    @BindView (R.id.edittext_url)
    EditText editTextUrl;
    @BindView (R.id.startService)
    Button startService;
    @BindView (R.id.stopService)
    Button stopService;
    @BindView (R.id.notification_button_repeat)
    ImageButton notificationButtonRepeat;
    @BindView (R.id.notification_button_prev)
    ImageButton notificationButtonPrev;
    @BindView (R.id.notification_button_pause)
    ImageButton notificationButtonPause;
    @BindView (R.id.notification_button_play)
    ImageButton notificationButtonPlay;
    @BindView (R.id.notification_button_exit)
    ImageButton notificationButtonExit;

    private String TAG = "ActivityMain: ";
    public static MyYoutubePlayer myYoutubePlayer;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        myYoutubePlayer = new MyYoutubePlayer(getWindow().getDecorView().getRootView());
        setOnClickListeners();

        IntentFilter filter = new IntentFilter("android.intent.CLOSE_ACTIVITY");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onResume () {
        super.onResume();
        getShareIntent();
    }

    private void getShareIntent () {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String sharedUrl = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedUrl != null) {
                    editTextUrl.setText(sharedUrl);
                    //getVideoInfo(sharedUrl);
                }
            }
        }
    }

    @Override
    protected void onDestroy () {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void setOnClickListeners () {
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);

        notificationButtonPlay.setOnClickListener(this);
        notificationButtonPause.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.startService:
                String url = editTextUrl.getText().toString();
                url = url.replace("https://youtu.be/", "");
                Log.d(TAG, url);
                Intent startIntent = new Intent(ActivityMain.this, ForegroundService.class);
                startIntent.putExtra("url", url);
                startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(startIntent);
                break;
            case R.id.stopService:
                Intent stopIntent = new Intent(ActivityMain.this, ForegroundService.class);
                stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(stopIntent);
                break;
            case R.id.notification_button_play:
                Intent playIntent = new Intent(ActivityMain.this, ForegroundService.class);
                playIntent.setAction(Constants.ACTION.PLAY_ACTION);
                startService(playIntent);
                break;
            case R.id.notification_button_pause:
                Intent pauseIntent = new Intent(ActivityMain.this, ForegroundService.class);
                pauseIntent.setAction(Constants.ACTION.PAUSE_ACTION);
                startService(pauseIntent);
                break;
        }
    }

//    private void getVideoInfo (String sharedUrlParam) {
//        RetrofitData retrofitData = new RetrofitData();
//        retrofitData.getInfo(sharedUrlParam);
//    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent) {
            finish();
        }
    };
}
