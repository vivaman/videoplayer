/*
 * AndroidContextUtils.java
 * classes : io.vov.vitamio.utils.AndroidContextUtils
 * @author viva
 * V 1.0.0
 * Create at 2013-4-24 下午3:44:38
 */
package io.viva.videoplayer.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * io.vov.vitamio.utils.AndroidContextUtils
 * 
 * @author viva <br/>
 *         create at 2013-4-24 下午3:44:38
 */
public class AndroidContextUtils {
	public static int getVersionCode(Context ctx) {
		int version = 0;
		try {
			version = ctx.getPackageManager().getPackageInfo(ctx.getApplicationInfo().packageName, 0).versionCode;
		} catch (Exception e) {
			Log.e("getVersionInt", e);
		}
		return version;
	}

	public static String getDataDir(Context ctx) {
		ApplicationInfo ai = ctx.getApplicationInfo();
		if (ai.dataDir != null) {
			return StringUtils.fixLastSlash(ai.dataDir);
		}
		return "/data/data/" + ai.packageName + "/";
	}
}
