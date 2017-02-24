package nyc.c4q.rusili.audiotube.youtube;

import android.util.Log;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.google.YouTubeFailureRecoveryActivity;

public class MyYoutubePlayer extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;

    public MyYoutubePlayer (View viewParam) {
        initializeViews(viewParam);
    }

    private void initializeViews (View viewParam) {
        youTubePlayerView = (YouTubePlayerView) viewParam.findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer youTubePlayerParam, boolean b) {
        youTubePlayer = youTubePlayerParam;
        youTubePlayer.setFullscreen(false);

        getVideoInfo();
    }

    @Override
    public void onInitializationFailure (YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("Initializationfailure:", youTubeInitializationResult.toString());
    }

    public YouTubePlayer getYouTubePlayer () {
        return youTubePlayer;
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider () {
        return youTubePlayerView;
    }


    public void getVideoInfo () {
    }
}
