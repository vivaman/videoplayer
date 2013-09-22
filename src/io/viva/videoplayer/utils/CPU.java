/*
 * CPU.java
 * classes : io.vov.vitamio.utils.CPU
 * @author viva
 * V 1.0.0
 * Create at 2013-4-24 下午3:45:05
 */
package io.viva.videoplayer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.os.Build;

/**
 * io.vov.vitamio.utils.CPU
 * 
 * @author viva <br/>
 *         create at 2013-4-24 下午3:45:05
 */
public class CPU {
	private static final Map<String, String> cpuinfo = new HashMap();
	private static int cachedFeature = -1;
	private static String cachedFeatureString = null;
	public static final int FEATURE_ARM_V5TE = 1;
	public static final int FEATURE_ARM_V6 = 2;
	public static final int FEATURE_ARM_VFP = 4;
	public static final int FEATURE_ARM_V7A = 8;
	public static final int FEATURE_ARM_VFPV3 = 16;
	public static final int FEATURE_ARM_NEON = 32;

	public static String getFeatureString() {
		getFeature();
		return cachedFeatureString;
	}

	public static int getFeature() {
		if (cachedFeature > 0) {
			return getCachedFeature();
		}
		cachedFeature = 1;

		if (cpuinfo.isEmpty()) {
			BufferedReader bis = null;
			try {
				bis = new BufferedReader(new FileReader(new File("/proc/cpuinfo")));
				String line;
				while ((line = bis.readLine()) != null) {
					Log.d(line, new Object[0]);
					if (!line.trim().equals("")) {
						String[] pairs = line.split(":");
						if (pairs.length > 1)
							cpuinfo.put(pairs[0].trim(), pairs[1].trim());
					}
				}
			} catch (Exception e) {
				Log.e("getCPUFeature", e);
				try {
					if (bis != null)
						bis.close();
				} catch (IOException ex) {
					Log.e("getCPUFeature", ex);
				}
			} finally {
				try {
					if (bis != null)
						bis.close();
				} catch (IOException e) {
					Log.e("getCPUFeature", e);
				}
			}
		}

		if (!cpuinfo.isEmpty()) {
			for (String key : cpuinfo.keySet()) {
				Log.d("%s:%s", new Object[] { key, cpuinfo.get(key) });
			}
			boolean hasARMv6 = false;
			boolean hasARMv7 = false;

			String val = (String) cpuinfo.get("CPU architecture");
			if (val != null) {
				try {
					int i = StringUtils.convertToInt(val);
					Log.d("CPU architecture: %s", new Object[] { Integer.valueOf(i) });
					if (i >= 7) {
						hasARMv6 = true;
						hasARMv7 = true;
					} else if (i >= 6) {
						hasARMv6 = true;
						hasARMv7 = false;
					}
				} catch (NumberFormatException ex) {
					Log.e("getCPUFeature", ex);
				}
			}

			val = (String) cpuinfo.get("Processor");
			if (((val != null) && (val.contains("(v7l)"))) || (val.contains("ARMv7"))) {
				hasARMv6 = true;
				hasARMv7 = true;
			}
			if (((val != null) && (val.contains("(v6l)"))) || (val.contains("ARMv6"))) {
				hasARMv6 = true;
				hasARMv7 = false;
			}

			if (hasARMv6)
				cachedFeature |= 2;
			if (hasARMv7) {
				cachedFeature |= 8;
			}
			val = (String) cpuinfo.get("Features");
			if (val != null) {
				if (val.contains("neon"))
					cachedFeature |= 52;
				else if (val.contains("vfpv3"))
					cachedFeature |= 20;
				else if (val.contains("vfp")) {
					cachedFeature |= 4;
				}
			}
		}
		return getCachedFeature();
	}

	private static int getCachedFeature() {
		if (cachedFeatureString == null) {
			StringBuffer sb = new StringBuffer();
			if ((cachedFeature & 0x1) > 0)
				sb.append("V5TE ");
			if ((cachedFeature & 0x2) > 0)
				sb.append("V6 ");
			if ((cachedFeature & 0x4) > 0)
				sb.append("VFP ");
			if ((cachedFeature & 0x8) > 0)
				sb.append("V7A ");
			if ((cachedFeature & 0x10) > 0)
				sb.append("VFPV3 ");
			if ((cachedFeature & 0x20) > 0)
				sb.append("NEON ");
			cachedFeatureString = sb.toString();
		}
		Log.d("GET CPU FATURE: %s", new Object[] { cachedFeatureString });
		return cachedFeature;
	}

	public static boolean isDroidXDroid2() {
		return (Build.MODEL.trim().equalsIgnoreCase("DROIDX")) || (Build.MODEL.trim().equalsIgnoreCase("DROID2")) || (Build.FINGERPRINT.toLowerCase().contains("shadow")) || (Build.FINGERPRINT.toLowerCase().contains("droid2"));
	}
}
