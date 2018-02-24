package com.owr.so.utils;

import java.time.Duration;

/**
 * @author Paulius Zubavicius
 *
 */
public class ConvertUtil {

	private static final double KB1 = 1024.0;
	private static final double MB1 = KB1 * KB1;
	private static final double GB1 = MB1 * MB1;

	private static final int MIN = 60;
	private static final int HOUR = MIN * 60;

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

		long sec = timeElapsed.getSeconds();

		String result = "";

		int integer = (int) (sec / HOUR);
		if (integer > 0) {
			sec %= HOUR;
			result += integer + " h ";
		}

		integer = (int) (sec / MIN);
		if (integer > 0) {
			sec %= MIN;
			result += integer + " min ";
		}

		result += sec + " sec ";

		return result;
	}

}
