package com.owr.so.commons;

import java.time.Duration;

/**
 * @author Paulius Zubavicius
 *
 */
public class ConvertUtil {

	private static final double KB1 = 1024.0;
	private static final double MB1 = KB1 * KB1;
	private static final double GB1 = MB1 * MB1;

	// private static final int MIN = 60;
	// private static final int HOUR = MIN * 60;

	public static String getSizeInHumanFormat(long sizeInBytes) {

		double size = 0;
		String unit = "b";

		if (sizeInBytes > GB1) {
			size = (sizeInBytes / GB1);
			unit = "Gb";
		} else if (sizeInBytes > MB1) {
			size = (sizeInBytes / MB1);
			unit = "Mb";
		} else if (sizeInBytes > KB1) {
			size = (sizeInBytes / KB1);
			unit = "Kb";
		}

		return String.format("%6.2f %3s", size, unit);

	}

	public static String getTimeInHumanFormat(Duration timeElapsed) {

		long days = timeElapsed.toDays();
		long hrs = timeElapsed.minusDays(days).toHours();
		long min = timeElapsed.minusDays(days).minusHours(hrs).toMinutes();
		long sec = timeElapsed.minusDays(days).minusHours(hrs).minusMinutes(min).getSeconds();
		long mil = timeElapsed.minusDays(days).minusHours(hrs).minusMinutes(min).minusSeconds(sec).toMillis();

		String result = "";
		boolean largestVal = false;

		if (days > 0) {
			largestVal = true;
			result += days + " days, ";
		}

		if (hrs > 0 || largestVal) {
			largestVal = true;
			result += hrs + " hrs, ";
		}

		if (min > 0 || largestVal) {
			largestVal = true;
			result += min + " min, ";
		}

		if (sec > 0 || largestVal) {
			result += sec + " sec, ";
		}

		result += mil + " milisec";

		return result;
	}

}
