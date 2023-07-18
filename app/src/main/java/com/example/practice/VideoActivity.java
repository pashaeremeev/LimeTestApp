package com.example.practice;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.LifecycleOwner;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.Timeline;
import androidx.media3.common.TrackSelectionOverride;
import androidx.media3.common.TrackSelectionParameters;
import androidx.media3.common.Tracks;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DataSource;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.datasource.TransferListener;
import androidx.media3.exoplayer.DefaultLoadControl;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.LoadControl;
import androidx.media3.exoplayer.RendererCapabilities;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.exoplayer.source.MediaSource;
import androidx.media3.exoplayer.source.TrackGroupArray;
import androidx.media3.exoplayer.trackselection.AdaptiveTrackSelection;
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector;
import androidx.media3.exoplayer.trackselection.FixedTrackSelection;
import androidx.media3.exoplayer.trackselection.MappingTrackSelector;
import androidx.media3.exoplayer.trackselection.MappingTrackSelector.MappedTrackInfo;
import androidx.media3.exoplayer.trackselection.TrackSelection;
import androidx.media3.exoplayer.upstream.BandwidthMeter;
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter;
import androidx.media3.ui.DefaultTimeBar;
import androidx.media3.ui.DefaultTrackNameProvider;
import androidx.media3.ui.PlayerView;
import androidx.media3.ui.TrackSelectionDialogBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;


@UnstableApi public class VideoActivity extends Fragment {

    public static final String BUNDLE_ID_KEY = "BUNDLE_ID_KEY";

    private PlayerView playerView;
    private ProgressBar progressBar;
    private ExoPlayer player;
    private boolean isFullScreen = false;
    private boolean isStop = false;
    private Runnable runnable;
    private Handler handler;
    private Tracks tracks;

    public static VideoActivity getInstance(int channelId) {
        VideoActivity videoFragment = new VideoActivity();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_ID_KEY, channelId);
        videoFragment.setArguments(bundle);
        return videoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.activity_video, container, false);

        container.setVisibility(View.VISIBLE);
        DataChannels channels = DataChannels.get();

        int channelId = getArguments().getInt(BUNDLE_ID_KEY);
        Uri videoUrl = Uri.parse(channels.getById(channelId).getStream());

        playerView = fragment.findViewById(R.id.exoplayerView);
        progressBar = fragment.findViewById(R.id.progressBar);
        ImageView settingsBtn = playerView.findViewById(R.id.settingsBtn);
        ImageView fullScreenBtn = playerView.findViewById(R.id.fullscreenBtn);

        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Uri videoUrl = Uri.parse("https://mhd.iptv2022.com/p/5tzYJRkx_8x4VIGmmym0KA,1689751804/streaming/1kanalott/324/1/index.m3u8");
//        Uri videoUrl = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
//        Uri videoUrl = Uri.parse("https://alanza.iptv2022.com/Miami_TV/index.m3u8");
//        Uri videoUrl = Uri.parse("https://alanza.iptv2022.com/LawCrime-eng/index.m3u8");
        //DefaultTrackSelector chooses tracks in the media item
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(getContext());
        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());

        LoadControl loadControl = new DefaultLoadControl();

        player = new ExoPlayer.Builder(getContext())
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
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
        MediaSource mediaSource = new HlsMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem);
        player.setMediaSource(mediaSource);
//        player.setMediaItem(mediaItem);
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
            ArrayList<Quality> qualities = new ArrayList<>();
            for (int i = 0; i < player.getCurrentTracks().getGroups().get(0).length; i++) {
                int height = player.getCurrentTracks().getGroups().get(0).getMediaTrackGroup().getFormat(i).height;
                int width = player.getCurrentTracks().getGroups().get(0).getMediaTrackGroup().getFormat(i).width;
                qualities.add(new Quality(width, height, i));
            }
            int currentIndex = qualities.size();
            qualities.add(new Quality(-1, -1, qualities.size()));
            for (int i = 0; i < player.getCurrentTracks().getGroups().get(0).length; i++) {
                if (player.getCurrentTracks().getGroups().get(0).isTrackSelected(i)
                        && !(player.getTrackSelector().getParameters().overrides.isEmpty())) {
                    currentIndex = i;
                }
            }
            if (!ItemQualityFragment.isExist) {
                ItemQualityFragment itemQualityFragment = ItemQualityFragment.getInstance(qualities, currentIndex);
                itemQualityFragment.show(getActivity().getSupportFragmentManager(), null);
            }
            getActivity().getSupportFragmentManager().setFragmentResultListener(ItemQualityFragment.REQUEST_KEY,
                    this, (requestKey, result) -> {
                        int index = result.getInt(ItemQualityFragment.BUNDLE_KEY);
                        if (index + 1 == qualities.size()) {
                            TrackSelectionParameters parameters = player.getTrackSelector().getParameters().buildUpon().clearOverrides().build();
                            player.getTrackSelector().setParameters(parameters);
                        } else {
                            TrackSelectionParameters parameters = player.getTrackSelector().getParameters().buildUpon().addOverride(new TrackSelectionOverride(
                                    player.getCurrentTracks().getGroups().get(0).getMediaTrackGroup(), index)).build();
                            player.getTrackSelector().setParameters(parameters);
                        }
                        getActivity().getSupportFragmentManager().clearFragmentResultListener(ItemQualityFragment.REQUEST_KEY);
                    });
        });

        fullScreenBtn.setOnClickListener(view -> {
            if (isFullScreen) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                isFullScreen = false;
            } else {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
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
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getView().setVisibility(View.GONE);
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
//
//        playerView = findViewById(R.id.exoplayerView);
//        progressBar = findViewById(R.id.progressBar);
//        ImageView settingsBtn = playerView.findViewById(R.id.settingsBtn);
//        ImageView fullScreenBtn = playerView.findViewById(R.id.fullscreenBtn);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        Uri videoUrl = Uri.parse("https://mhd.iptv2022.com/p/5tzYJRkx_8x4VIGmmym0KA,1689751804/streaming/1kanalott/324/1/index.m3u8");
////        Uri videoUrl = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4");
////        Uri videoUrl = Uri.parse("https://alanza.iptv2022.com/Miami_TV/index.m3u8");
////        Uri videoUrl = Uri.parse("https://alanza.iptv2022.com/LawCrime-eng/index.m3u8");
//        //DefaultTrackSelector chooses tracks in the media item
//        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
//        trackSelector.setParameters(trackSelector.buildUponParameters().setMaxVideoSizeSd());
//
//        LoadControl loadControl = new DefaultLoadControl();
//
//        player = new ExoPlayer.Builder(getBaseContext())
//                .setTrackSelector(trackSelector)
//                .setLoadControl(loadControl)
//                .build();
//        playerView.setPlayer(player);
//        playerView.setKeepScreenOn(true);
//        MediaItem mediaItem = MediaItem.fromUri(videoUrl);
//        DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory()
//                .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
//                .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
//                .setAllowCrossProtocolRedirects(true);
//        HashMap<String, String> requestProperties = new HashMap<>();
//        requestProperties.put("X-LHD-Agent", "{\"platform\":\"android\",\"app\":\"stream.tv.online\",\"version_name\":\"3.1.3\",\"version_code\":\"400\",\"sdk\":\"29\",\"name\":\"Huawei+Wgr-w19\",\"device_id\":\"a4ea673248fe0bcc\",\"is_huawei\":\"0\"}");
//        httpDataSourceFactory.setDefaultRequestProperties(requestProperties);
//        MediaSource mediaSource = new HlsMediaSource.Factory(httpDataSourceFactory).createMediaSource(mediaItem);
//        player.setMediaSource(mediaSource);
////        player.setMediaItem(mediaItem);
//        player.prepare();
//        player.setPlayWhenReady(true);
//
//        SeekBar newTimeBar = playerView.findViewById(R.id.newProgress);
//
//        newTimeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        settingsBtn.setOnClickListener(view -> {
//            ArrayList<Quality> qualities = new ArrayList<>();
//            for (int i = 0; i < player.getCurrentTracks().getGroups().get(0).length; i++) {
//                int height = player.getCurrentTracks().getGroups().get(0).getMediaTrackGroup().getFormat(i).height;
//                int width = player.getCurrentTracks().getGroups().get(0).getMediaTrackGroup().getFormat(i).width;
//                qualities.add(new Quality(width, height, i));
//            }
//            int currentIndex = qualities.size();
//            qualities.add(new Quality(-1, -1, qualities.size()));
//            for (int i = 0; i < player.getCurrentTracks().getGroups().get(0).length; i++) {
//                if (player.getCurrentTracks().getGroups().get(0).isTrackSelected(i)
//                        && !(player.getTrackSelector().getParameters().overrides.isEmpty())) {
//                    currentIndex = i;
//                }
//            }
//            if (!ItemQualityFragment.isExist) {
//                ItemQualityFragment itemQualityFragment = ItemQualityFragment.getInstance(qualities, currentIndex);
//                itemQualityFragment.show(getSupportFragmentManager(), null);
//            }
//            getSupportFragmentManager().setFragmentResultListener(ItemQualityFragment.REQUEST_KEY,
//                    this, (requestKey, result) -> {
//                        int index = result.getInt(ItemQualityFragment.BUNDLE_KEY);
//                        if (index + 1 == qualities.size()) {
//                            TrackSelectionParameters parameters = player.getTrackSelector().getParameters().buildUpon().clearOverrides().build();
//                            player.getTrackSelector().setParameters(parameters);
//                        } else {
//                        TrackSelectionParameters parameters = player.getTrackSelector().getParameters().buildUpon().addOverride(new TrackSelectionOverride(
//                                player.getCurrentTracks().getGroups().get(0).getMediaTrackGroup(), index)).build();
//                        player.getTrackSelector().setParameters(parameters);
//                        }
//                        getSupportFragmentManager().clearFragmentResultListener(ItemQualityFragment.REQUEST_KEY);
//                    });
//        });
//
//        fullScreenBtn.setOnClickListener(view -> {
//            if (isFullScreen) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                isFullScreen = false;
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                isFullScreen = true;
//            }
//        });
//
//        player.addListener(new Player.Listener() {
//
//            @Override
//            public void onPlaybackStateChanged(int state) {
//                if (state == Player.STATE_READY) {
//                    newTimeBar.setMax((int) player.getDuration());
//                    handler = new Handler(Looper.getMainLooper());
//                    runnable = new Runnable() {
//                        @Override
//                        public void run() {
//                            newTimeBar.setProgress((int) (player.getCurrentPosition()));
//                            handler.postDelayed(runnable, 1000);
//                        }
//                    };
//                    handler.postDelayed(runnable, 0);
//                    progressBar.setVisibility(View.GONE);
//                } else if (state == Player.STATE_BUFFERING) {
//                    progressBar.setVisibility(View.VISIBLE);
//                } else {
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        ImageView ppBtn = playerView.findViewById(R.id.exo_play);
//        ppBtn.setOnClickListener(view -> {
//            if (player.isPlaying()) {
//                player.pause();
//                player.setPlayWhenReady(false);
//                ppBtn.setImageResource(R.drawable.ic_arrow_play);
//            } else {
//                player.play();
//                player.setPlayWhenReady(true);
//                ppBtn.setImageResource(R.drawable.ic_pause);
//            }
//        });
//    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        player.seekToDefaultPosition();
//        player.setPlayWhenReady(true);
//    }
//
//    @Override
//    protected void onPause() {
//        player.setPlayWhenReady(false);
//        handler.removeCallbacksAndMessages(null);
//        super.onPause();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        player.release();
//    }
}