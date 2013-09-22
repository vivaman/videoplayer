/*
 * FileUtils.java
 * classes : io.vov.vitamio.utils.FileUtils
 * @author viva
 * V 1.0.0
 * Create at 2013-4-24 下午3:45:37
 */
package io.viva.videoplayer.utils;

import java.io.File;
import java.io.IOException;

import android.net.Uri;

/**
 * io.vov.vitamio.utils.FileUtils
 * 
 * @author viva <br/>
 *         create at 2013-4-24 下午3:45:37
 */
public class FileUtils {
	public static String getCanonical(File f) {
		if (f == null)
			return null;
		try {
			return f.getCanonicalPath();
		} catch (IOException e) {
		}
		return f.getAbsolutePath();
	}

	public static String getPath(String uri) {
		Log.i("FileUtils#getPath(%s)", new Object[] { uri });
		if (StringUtils.isBlank(uri))
			return null;
		if ((uri.startsWith("file://")) && (uri.length() > 7))
			return Uri.decode(uri.substring(7));
		return Uri.decode(uri);
	}

	public static String getName(String uri) {
		String path = getPath(uri);
		if (path != null)
			return new File(path).getName();
		return null;
	}

	public static void deleteDir(File f) {
		if ((f.exists()) && (f.isDirectory())) {
			for (File file : f.listFiles()) {
				if (file.isDirectory())
					deleteDir(file);
				file.delete();
			}
			f.delete();
		}
	}
}
