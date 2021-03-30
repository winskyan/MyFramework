package com.my.library_multimedia.video;

import tv.danmaku.ijk.media.player.IMediaPlayer;

public abstract class VideoPlayerIJKListener implements IMediaPlayer.OnBufferingUpdateListener
        , IMediaPlayer.OnCompletionListener, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnInfoListener
        , IMediaPlayer.OnVideoSizeChangedListener, IMediaPlayer.OnErrorListener
        , IMediaPlayer.OnSeekCompleteListener {
}