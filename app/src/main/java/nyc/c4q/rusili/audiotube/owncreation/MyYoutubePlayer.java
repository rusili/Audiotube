package nyc.c4q.rusili.audiotube.owncreation;

import android.util.Log;
import android.view.View;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import nyc.c4q.rusili.audiotube.R;
import nyc.c4q.rusili.audiotube.Youtube.YouTubeFailureRecoveryActivity;

public class MyYoutubePlayer extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnInitializedListener {
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer youTubePlayer;

    public MyYoutubePlayer(View viewParam){
        initializeViews(viewParam);
    }

    private void initializeViews(View viewParam){
        youTubePlayerView = (YouTubePlayerView) viewParam.findViewById(R.id.youtube_view);
        youTubePlayerView.initialize("AIzaSyC0XxmEUaHBgBz0jVEDilWMdsIgk1xTRxw", this);
    }

    @Override
    public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer youTubePlayerParam, boolean b) {
        youTubePlayer = youTubePlayerParam;
        youTubePlayer.loadVideo("wKJ9KzGQq0w");
    }

    public YouTubePlayer getYouTubePlayer(){
        return youTubePlayer;
    }

    @Override
    public void onInitializationFailure (YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d("Initializationfailure:", youTubeInitializationResult.toString());
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider () {
        return youTubePlayerView;
    }
}
