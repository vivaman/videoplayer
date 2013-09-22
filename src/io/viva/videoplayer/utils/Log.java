/*
 * Log.java
 * classes : io.vov.vitamio.utils.Log
 * @author viva
 * V 1.0.0
 * Create at 2013-4-24 下午3:46:03
 */
package io.viva.videoplayer.utils;

/**
 * io.vov.vitamio.utils.Log
 * 
 * @author viva <br/>
 *         create at 2013-4-24 下午3:46:03
 */
public class Log {
	public static final String TAG = "Vitamio";

	public static void i(String msg, Object[] args) {
		if (msg == null)
			return;
		try {
			android.util.Log.i("Vitamio", String.format(msg, args));
		} catch (Exception e) {
			android.util.Log.e("Vitamio", "me.abitno.utils.Log", e);
			android.util.Log.i("Vitamio", msg);
		}
	}

	public static void d(String msg, Object[] args) {
		if (msg == null)
			return;
		try {
			android.util.Log.d("Vitamio", String.format(msg, args));
		} catch (Exception e) {
			android.util.Log.e("Vitamio", "me.abitno.utils.Log", e);
			android.util.Log.d("Vitamio", msg);
		}
	}

	public static void e(String msg, Object[] args) {
		if (msg == null)
			return;
		try {
			android.util.Log.e("Vitamio", String.format(msg, args));
		} catch (Exception e) {
			android.util.Log.e("Vitamio", "me.abitno.utils.Log", e);
			android.util.Log.e("Vitamio", msg);
		}
	}

	public static void e(String msg, Throwable t) {
		if (msg == null)
			return;
		android.util.Log.e("Vitamio", msg, t);
	}
}
