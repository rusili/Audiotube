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

import com.google.android.youtube.player.YouTubeBaseActivity;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.service.ForegroundService;
import nyc.c4q.rusili.audiotube.utility.Constants;
import nyc.c4q.rusili.audiotube.utility.MyYoutubePlayer;

public class ActivityMain extends YouTubeBaseActivity implements View.OnClickListener {
    private String TAG = "ActivityMain: ";
    private EditText editTextUrl;
    public static MyYoutubePlayer myYoutubePlayer;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myYoutubePlayer = new MyYoutubePlayer(getWindow().getDecorView().getRootView());
        setViews();

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
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    private void setViews () {
        editTextUrl = (EditText) findViewById(R.id.edittest_url);

        Button startButton = (Button) findViewById(R.id.startService);
        Button stopButton = (Button) findViewById(R.id.stopService);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
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
