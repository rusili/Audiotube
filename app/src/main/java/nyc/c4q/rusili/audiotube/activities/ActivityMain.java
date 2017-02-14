package nyc.c4q.rusili.audiotube.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;

import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.service.ForegroundService;

public class ActivityMain extends YouTubeBaseActivity implements View.OnClickListener{
    public static View mView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView = findViewById(android.R.id.content);

        Button startButton = (Button) findViewById(R.id.startService);
        Button stopButton = (Button) findViewById(R.id.stopService);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }

    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.startService:
                Intent startIntent = new Intent(ActivityMain.this, ForegroundService.class);
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
}
