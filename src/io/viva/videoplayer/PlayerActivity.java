/*
 * Copyright (C) 2012 YIXIA.COM
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

package io.viva.videoplayer;

import io.viva.videoplayer.widget.MediaController;
import io.viva.videoplayer.widget.VideoView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

public class PlayerActivity extends Activity implements OnTouchListener, OnGestureListener, OnErrorListener, OnCompletionListener {

	private static final String TAG = "PlayerActivity";
	private static final String STORE_NAME = "VideoSetting";

	private VideoView mVideoView;
	private View mProgressView;
	private GestureDetector mDetector;

	private PopupWindow mWindow;

	private Intent intent;
	private Uri mUri;
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	private boolean mFinishOnCompletion;
	private boolean mFullScreen;

	private long currentTime;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}

	};

	Runnable mPlayingChecker = new Runnable() {
		public void run() {
			if (mVideoView.isPlaying()) {
				if (currentTime == mVideoView.getCurrentPosition()) {
					mProgressView.setVisibility(View.VISIBLE);
				} else {
					mProgressView.setVisibility(View.GONE);
				}
				currentTime = mVideoView.getCurrentPosition();
			}
			mHandler.postDelayed(mPlayingChecker, 250);
		}
	};

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.videoview);
		mVideoView = (VideoView) findViewById(R.id.surface_view);
		mVideoView.setMediaController(new MediaController(this));
		mProgressView = findViewById(R.id.progress_indicator);
		mDetector = new GestureDetector(this);

		intent = getIntent();
		mUri = intent.getData();
		sp = getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();

		// For streams that we expect to be slow to start up, show a
		// progress spinner until playback starts.
		String scheme = mUri.getScheme();
		if ("http".equalsIgnoreCase(scheme) || "rtsp".equalsIgnoreCase(scheme)) {
			mHandler.postDelayed(mPlayingChecker, 250);
		} else {
			mProgressView.setVisibility(View.GONE);
		}

		mFullScreen = intent.getBooleanExtra(MediaStore.EXTRA_FULL_SCREEN, false);
		mFinishOnCompletion = intent.getBooleanExtra(MediaStore.EXTRA_FINISH_ON_COMPLETION, true);
		mVideoView.setOnErrorListener(this);
		mVideoView.setOnCompletionListener(this);
		if (mFullScreen) {
			mVideoView.setZoomMode(VideoView.ZOOM_FULL_SCREEN_VIDEO_RATIO);
		}
		mVideoView.setVideoURI(mUri);
		mVideoView.requestFocus();
		mVideoView.start();

		initFloatingWindow();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mDetector.onTouchEvent(event);
	}

	private void initFloatingWindow() {
		mWindow = new PopupWindow(this);
		mWindow.setFocusable(false);
		mWindow.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
		mWindow.setOutsideTouchable(true);
		mWindow.setAnimationStyle(R.style.Animationleftright);

		View mRoot = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup, null);
		mWindow.setWidth(200);
		mWindow.setHeight((int) (getResources().getDisplayMetrics().heightPixels * 0.8));
		mWindow.setContentView(mRoot);
		mWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {

			}

		});
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		float x = e2.getX() - e1.getX();
		float y = e2.getY() - e1.getY();
		float x_limit = getResources().getDisplayMetrics().widthPixels / 4;
		float y_limit = getResources().getDisplayMetrics().heightPixels / 4;
		float x_abs = Math.abs(x);
		float y_abs = Math.abs(y);
		if (x_abs >= y_abs) {
			// gesture left or right
			if (x > x_limit || x < -x_limit) {
				if (x > 0) {
					// right
					// index = index == length - 1 ? 0 : ++index;
					findViewById(R.id.main).post(new Runnable() {

						@Override
						public void run() {
							mWindow.showAtLocation(findViewById(R.id.main), Gravity.LEFT | Gravity.CLIP_VERTICAL, 0, 0);
						}

					});
				} else if (x <= 0) {
					// left
					// index = index == 0 ? length - 1 : --index;
					mWindow.dismiss();
				}
			}
		} else {
			// gesture down or up
			if (y > y_limit || y < -y_limit) {
				if (y > 0) {
					// down

				} else if (y <= 0) {
					// up

				}
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media
	 * .MediaPlayer)
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		mVideoView.setOnErrorListener(this);
		this.finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.MediaPlayer.OnErrorListener#onError(android.media.MediaPlayer
	 * , int, int)
	 */
	@Override
	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		mHandler.removeCallbacksAndMessages(null);
		mProgressView.setVisibility(View.GONE);
		// this.finish();
		// android.os.Process.killProcess(android.os.Process.myPid());
		return false;
	}

}
