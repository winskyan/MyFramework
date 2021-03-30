package com.my.library_multimedia.video;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoPlayerIJKUtils {
    private static VideoPlayerIJKUtils videoPlayerIJKUtils;

    public VideoPlayerIJKUtils() {

    }

    public static VideoPlayerIJKUtils getInstance() {
        if (null == videoPlayerIJKUtils) {
            videoPlayerIJKUtils = new VideoPlayerIJKUtils();
        }
        return videoPlayerIJKUtils;
    }

    public void onInit() {
        //加载so文件
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStop() {
        IjkMediaPlayer.native_profileEnd();
    }
}
