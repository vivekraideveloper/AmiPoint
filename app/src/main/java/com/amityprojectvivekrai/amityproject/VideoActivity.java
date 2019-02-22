package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class VideoActivity extends YouTubeBaseActivity {
    Toolbar toolbar;
    Button button;
    YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        Bundle bundle = getIntent().getExtras();
        final String videoId = bundle.getString("videoId");



        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle(bundle.getString("name"));

        playerView = findViewById(R.id.youtubePlayerView);
        button = findViewById(R.id.notesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VideoActivity.this, PDFActivity.class);
                startActivity(intent);
            }
        });


        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(videoId);
                youTubePlayer.setPlaybackEventListener(playbackEventListener);
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                if (!b){
                    youTubePlayer.cueVideo(videoId);
                }


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                final int REQUEST_CODE = 1;
                if (youTubeInitializationResult.isUserRecoverableError()){
                    youTubeInitializationResult.getErrorDialog(VideoActivity.this, REQUEST_CODE).show();
                }else {
                    Toast.makeText(VideoActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        };

        playerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }

    } ;

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {
//            Toast.makeText(VideoActivity.this, "Click Ad now, make the video creator rich!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onVideoStarted() {
            Toast.makeText(VideoActivity.this, "Video started", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onVideoEnded() {
            Toast.makeText(VideoActivity.this, "Congratulations, you have completed the video!", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };
}
