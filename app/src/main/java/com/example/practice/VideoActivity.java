package com.example.practice;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.ui.DefaultTimeBar;
import androidx.media3.ui.PlayerView;

import java.util.HashMap;


@UnstableApi public class VideoActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ProgressBar progressBar;
    private ExoPlayer player;
    private boolean isFullScreen = false;
    private boolean isStop = false;
    private Runnable runnable;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        playerView = findViewById(R.id.exoplayerView);
        progressBar = findViewById(R.id.progressBar);
        ImageView settingsBtn = playerView.findViewById(R.id.settingsBtn);
        ImageView fullScreenBtn = playerView.findViewById(R.id.fullscreenBtn);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Uri videoUrl = Uri.parse("https://mhd.iptv2022.com/p/Iz9NlEATsSPbrr5spjajhg,1689414483/streaming/1kanalott/324/1/index.m3u8");
//        Uri videoUrl = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
//        Uri videoUrl = Uri.parse("https://alanza.iptv2022.com/Miami_TV/index.m3u8");
        //DefaultTrackSelector chooses tracks in the media item
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
//        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());


        player = new ExoPlayer.Builder(getBaseContext())
                .setTrackSelector(trackSelector)
                .build();
        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
                .setAllowCrossProtocolRedirects(true);
        HashMap<String, String> requestProperties = new HashMap<>();
        requestProperties.put("X-LHD-Agent", "{\"platform\":\"android\",\"app\":\"stream.tv.online\",\"version_name\":\"3.1.3\",\"version_code\":\"400\",\"sdk\":\"29\",\"name\":\"Huawei+Wgr-w19\",\"device_id\":\"a4ea673248fe0bcc\",\"is_huawei\":\"0\"}");
        httpDataSourceFactory.setDefaultRequestProperties(requestProperties);
        MediaSource mediaSource = new HlsMediaSource.Factory(new DataSource.Factory() {
            @Override
            public DataSource createDataSource() {
                return null;
            }
        }).createMediaSource(mediaItem);
//        player.setMediaSource(mediaSource);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);

        SeekBar newTimeBar = playerView.findViewById(R.id.newProgress);

        newTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        settingsBtn.setOnClickListener(view -> {
            TrackSelectionDialog trackSelectionDialog =
                    TrackSelectionDialog.createForPlayer(
                            player,
                             dismissedDialog -> {});
            trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);

        });

        fullScreenBtn.setOnClickListener(view -> {
            if (isFullScreen) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                isFullScreen = false;
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                isFullScreen = true;
            }
        });

        player.addListener(new Player.Listener() {

            @Override
            public void onPlaybackStateChanged(int state) {
                if (state == Player.STATE_READY) {
                    newTimeBar.setMax((int) player.getDuration());
                    handler = new Handler(Looper.getMainLooper());
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            newTimeBar.setProgress((int) (player.getCurrentPosition()));
                            handler.postDelayed(runnable, 1000);
                        }
                    };
                    handler.postDelayed(runnable, 0);
                    progressBar.setVisibility(View.GONE);
                } else if (state == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        ImageView ppBtn = playerView.findViewById(R.id.exo_play);
        ppBtn.setOnClickListener(view -> {
            if (player.isPlaying()) {
                player.pause();
                player.setPlayWhenReady(false);
                ppBtn.setImageResource(R.drawable.ic_arrow_play);
            } else {
                player.play();
                player.setPlayWhenReady(true);
                ppBtn.setImageResource(R.drawable.ic_pause);
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
        handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}