package com.video;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoplayerActivity extends Activity {
	/**
	 * TODO: Set the path variable to a streaming video URL or a local media
	 * file path.
	 */

	private Activity activity = this;

	final String LOGTAG = "time";
	private String path = "http://vod.wasu.tv/pcsan05/mrms/vod/20120411/20120411140737780531f1876_e366ab66_91e6e875.mp4";
	private String path1 = "http://vod.wasu.tv/pcsan05/mrms/vod/20120411/20120411140737780531f1876_e366ab66_91e6e875.mp4";
	private VideoView mVideoView;

	private Context aa = this;

	private MediaPlayer mediaPlayer;
	private int stopTime;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// this.getWindow().setFlags(, mask)

		setContentView(R.layout.main);

		mVideoView = (VideoView) findViewById(R.id.surface_view);

		mVideoView.setVideoPath(path);
		mVideoView.setMediaController(new MediaController(this));
		mVideoView.requestFocus();
		mVideoView.start();

		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(final MediaPlayer mp) {
				mp.start();
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						mVideoView.pause();
						mediaPlayer = mp;
						stopTime = mVideoView.getCurrentPosition();
						Intent intent = new Intent(activity, DialogActivity.class);
						activity.startActivity(intent);
					}

				}, 5000);
			}

		});

	}

	@Override
	protected void onResume() {
		super.onResume();
//		if (mediaPlayer != null) {
//			mediaPlayer.setDisplay(mVideoView.getHolder());
//		}
		if (mediaPlayer != null) {
			mVideoView.seekTo(stopTime);
			mVideoView.start();
		}

	}

}