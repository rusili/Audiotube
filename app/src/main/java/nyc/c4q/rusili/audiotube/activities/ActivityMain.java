package nyc.c4q.rusili.audiotube.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.youtube.player.YouTubeBaseActivity;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.other.Constants;
import nyc.c4q.rusili.audiotube.service.ForegroundService;
import nyc.c4q.rusili.audiotube.youtube.MyYoutubePlayer;

public class ActivityMain extends YouTubeBaseActivity implements View.OnClickListener{
    private EditText editTextUrl;
    public static MyYoutubePlayer myYoutubePlayer;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myYoutubePlayer = new MyYoutubePlayer(getWindow().getDecorView().getRootView());

        setViews();
    }

    private void setViews(){
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
}
