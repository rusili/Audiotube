/*
 * Copyright 2012 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nyc.c4q.rusili.audiotube.google;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayerView;

import nyc.c4q.rusili.audiotube.R;

public class PlayerControlsDemoActivityStripped extends YouTubeFailureRecoveryActivity implements
        View.OnClickListener,
        TextView.OnEditorActionListener,
        AdapterView.OnItemSelectedListener {

    private static final String KEY_CURRENTLY_SELECTED_ID = "currentlySelectedId";

    private YouTubePlayerView youTubePlayerView;
    private static YouTubePlayer player;
    private TextView stateText;
    private Button playButton;
    private Button pauseButton;
    private EditText skipTo;

    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;

    private String currentlySelectedId;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_controls_demo);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        stateText = (TextView) findViewById(R.id.state_text);
        playButton = (Button) findViewById(R.id.play_button);
        pauseButton = (Button) findViewById(R.id.pause_button);
        skipTo = (EditText) findViewById(R.id.skip_to_text);

        playButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        skipTo.setOnEditorActionListener(this);

        youTubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this);

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();

        setControlsEnabled(false);
    }

    @Override
    public void onInitializationSuccess (YouTubePlayer.Provider provider, YouTubePlayer player,
                                         boolean wasRestored) {
        this.player = player;
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {
            playVideoOnLoad();
        }
        setControlsEnabled(true);
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider () {
        return youTubePlayerView;
    }

    private void playVideoOnLoad () {
        player.loadVideo("ZCu2gwLj9ok");
    }

    @Override
    public void onItemSelected (AdapterView <?> parent, View view, int pos, long id) {
    }

    @Override
    public void onNothingSelected (AdapterView <?> parent) {
    }

    @Override
    public void onClick (View v) {
        if (v == playButton) {
            player.play();
        } else if (v == pauseButton) {
            player.pause();
        }
    }

    @Override
    public boolean onEditorAction (TextView v, int actionId, KeyEvent event) {
        if (v == skipTo) {
            int skipToSecs = parseInt(skipTo.getText().toString(), 0);
            player.seekToMillis(skipToSecs * 1000);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(skipTo.getWindowToken(), 0);
            return true;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState (Bundle state) {
        super.onSaveInstanceState(state);
        state.putString(KEY_CURRENTLY_SELECTED_ID, currentlySelectedId);
    }

    @Override
    protected void onRestoreInstanceState (Bundle state) {
        super.onRestoreInstanceState(state);
        currentlySelectedId = state.getString(KEY_CURRENTLY_SELECTED_ID);
    }

    private void updateText () {
        stateText.setText(String.format("Current state: %s %s %s",
                playerStateChangeListener.playerState, playbackEventListener.playbackState,
                playbackEventListener.bufferingState));
    }

    private void setControlsEnabled (boolean enabled) {
        playButton.setEnabled(enabled);
        pauseButton.setEnabled(enabled);
        skipTo.setEnabled(enabled);
    }

    private static final int parseInt (String intString, int defaultValue) {
        try {
            return intString != null ? Integer.valueOf(intString) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private String formatTime (int millis) {
        int seconds = millis / 1000;
        int minutes = seconds / 60;
        int hours = minutes / 60;

        return (hours == 0 ? "" : hours + ":")
                + String.format("%02d:%02d", minutes % 60, seconds % 60);
    }

    private String getTimesText () {
        int currentTimeMillis = player.getCurrentTimeMillis();
        int durationMillis = player.getDurationMillis();
        return String.format("(%s/%s)", formatTime(currentTimeMillis), formatTime(durationMillis));
    }

    private final class MyPlaybackEventListener implements PlaybackEventListener {
        String playbackState = "NOT_PLAYING";
        String bufferingState = "";

        @Override
        public void onPlaying () {
            playbackState = "PLAYING";
            updateText();
        }

        @Override
        public void onBuffering (boolean isBuffering) {
            bufferingState = isBuffering ? "(BUFFERING)" : "";
            updateText();
        }

        @Override
        public void onStopped () {
            playbackState = "STOPPED";
            updateText();
        }

        @Override
        public void onPaused () {
            playbackState = "PAUSED";
            updateText();
        }

        @Override
        public void onSeekTo (int endPositionMillis) {
        }
    }

    private final class MyPlayerStateChangeListener implements PlayerStateChangeListener {
        String playerState = "UNINITIALIZED";

        @Override
        public void onLoading () {
            playerState = "LOADING";
            updateText();
        }

        @Override
        public void onLoaded (String videoId) {
            playerState = String.format("LOADED %s", videoId);
            updateText();
        }

        @Override
        public void onAdStarted () {
            playerState = "AD_STARTED";
            updateText();
        }

        @Override
        public void onVideoStarted () {
            playerState = "VIDEO_STARTED";
            updateText();
        }

        @Override
        public void onVideoEnded () {
            playerState = "VIDEO_ENDED";
            updateText();
        }

        @Override
        public void onError (ErrorReason reason) {
            playerState = "ERROR (" + reason + ")";
            if (reason == ErrorReason.UNEXPECTED_SERVICE_DISCONNECTION) {
                // When this error occurs the player is released and can no longer be used.
                player = null;
                setControlsEnabled(false);
            }
            updateText();
        }
    }

    @Override
    public void onBackPressed () {
        new AlertDialog.Builder(this)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public static void play () {
        player.play();
    }
}
