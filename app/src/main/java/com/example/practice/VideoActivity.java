package com.example.practice;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultDataSource;
import androidx.media3.datasource.DefaultDataSourceFactory;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.ui.PlayerView;


@UnstableApi public class VideoActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ProgressBar progressBar;
    private ExoPlayer player;
    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        playerView = findViewById(R.id.exoplayerView);
        progressBar = findViewById(R.id.progressBar);
        ImageView btnFullScreen = playerView.findViewById(R.id.bt_fullscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Uri videoUrl = Uri.parse("https://mhd.iptv2022.com/p/DDxgRuR-ZMOZfWqHd8J7oQ,1689322521/streaming/izvestia/324/1/index.m3u8");
        //DefaultTrackSelector chooses tracks in the media item
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
//        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());


        player = new ExoPlayer.Builder(getBaseContext())
                .setTrackSelector(trackSelector)
                .build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        MediaSource mediaSource = new HlsMediaSource.Factory(
                new DefaultDataSource.Factory(getBaseContext())
        ).createMediaSource(mediaItem);
        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);

        btnFullScreen.setOnClickListener(view -> {
            TrackSelectionDialog trackSelectionDialog =
                    TrackSelectionDialog.createForPlayer(
                            player,
                             dismissedDialog -> {});
            trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);

//            if (isFullScreen) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                isFullScreen = false;
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                isFullScreen = true;
//            }
        });

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                } else if (state == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                    playerView.setKeepScreenOn(true);
                } else {
                    progressBar.setVisibility(View.GONE);
                    player.setPlayWhenReady(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.seekToDefaultPosition();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}