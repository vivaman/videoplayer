package com.video;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

public class DialogActivity extends Activity {

	private VideoView mVideoView1;
	private String path1 = "http://vod.wasu.tv/pcsan05/mrms/vod/20120411/20120411140737780531f1876_e366ab66_91e6e875.mp4";
	private Activity a = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
//		setTheme(R.style.Transparent);
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.popupview);
		mVideoView1 = (VideoView) findViewById(R.id.surface_view1);

		mVideoView1.setVideoPath(path1);
		mVideoView1.setMediaController(new MediaController(this));
		mVideoView1.requestFocus();
		mVideoView1.start();

		mVideoView1.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
//				mp.setDisplay(mVideoView1.getHolder());
				mp.start();
			}

		});

		mVideoView1.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				a.finish();
			}
		});

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			this.finish();
		}
		return super.onKeyUp(keyCode, event);
	}

}
