/*
 * StringUtils.java
 * classes : io.vov.vitamio.utils.StringUtils
 * @author viva
 * V 1.0.0
 * Create at 2013-4-24 下午3:47:14
 */
package io.viva.videoplayer.utils;

/**
 * io.vov.vitamio.utils.StringUtils
 * 
 * @author viva <br/>
 *         create at 2013-4-24 下午3:47:14
 */
public class StringUtils {
	public static String fixLastSlash(String str) {
		String res = str.trim() + "/";
		if ((res.length() > 2) && (res.charAt(res.length() - 2) == '/'))
			res = res.substring(0, res.length() - 1);
		return res;
	}

	public static int convertToInt(String str) throws NumberFormatException {
		int e, s;
		for (s = 0; s < str.length(); s++)
			if (Character.isDigit(str.charAt(s)))
				break;
		for (e = str.length(); e > 0; e--)
			if (Character.isDigit(str.charAt(e - 1)))
				break;
		if (e > s) {
			try {
				return Integer.parseInt(str.substring(s, e));
			} catch (NumberFormatException ex) {
				Log.e("convertToInt", ex);
				throw new NumberFormatException();
			}
		}
		throw new NumberFormatException();
	}

	public static String generateTime(long time) {
		int totalSeconds = (int) (time / 1000L);
		int seconds = totalSeconds % 60;
		int minutes = totalSeconds / 60 % 60;
		int hours = totalSeconds / 3600;

		return hours > 0 ? String.format("%02d:%02d:%02d", new Object[] { Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds) }) : String.format("%02d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
	}

	public static boolean isBlank(String s) {
		return (s == null) || (s.trim().equals(""));
	}
}