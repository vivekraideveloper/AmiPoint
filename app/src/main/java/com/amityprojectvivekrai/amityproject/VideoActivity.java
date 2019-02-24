package com.amityprojectvivekrai.amityproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.r0adkll.slidr.Slidr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class VideoActivity extends YouTubeBaseActivity {
    Toolbar toolbar;
    Button button;
    YouTubePlayerView playerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        Slidr.attach(this);

        Bundle bundle = getIntent().getExtras();
        final String videoId = bundle.getString("videoId");
        final String notesLink = bundle.getString("notesLink");
        final String name = bundle.getString("name");

        toolbar = findViewById(R.id.tool_bar);
        toolbar.setTitle(bundle.getString("name"));

        playerView = findViewById(R.id.youtubePlayerView);
        button = findViewById(R.id.notesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(VideoActivity.this, notesLink, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VideoActivity.this, PDFViewerActivity.class);
                intent.putExtra("notesLink", notesLink);
                intent.putExtra("name", name);
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



    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    public void saveBitmap(Bitmap bitmap) {
        Random random = new Random();
        int n = random.nextInt(10000);
        File wallpaperDirectory = new File("/sdcard/Amity Project/");
// have the object build the directory structure, if needed.
        wallpaperDirectory.mkdirs();
        File imagePath = new File(Environment.getExternalStorageDirectory() + "/Amity Project/screenshot"+n+".png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
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
