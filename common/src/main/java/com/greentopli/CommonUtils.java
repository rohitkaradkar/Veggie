package com.greentopli;

import com.greentopli.model.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rnztx on 19/10/16.
 */

public class CommonUtils {
	public static long getDateExcludingTime() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
//		calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		return getDateExcludingTime(calendar);
	}

	/**
	 * Striping out time details so that we can compare only by long value
	 */
	public static long getDateExcludingTime(Calendar calendar) {
		calendar.clear(Calendar.HOUR);
		calendar.clear(Calendar.HOUR_OF_DAY);
		calendar.clear(Calendar.AM_PM);
		calendar.clear(Calendar.MINUTE);
		calendar.clear(Calendar.SECOND);
		calendar.clear(Calendar.MILLISECOND);
		return calendar.getTimeInMillis();
	}

	public static int calculatePrice(int priceForMinVolume, int minVolume, int requiredVolume) {
		double result = Double.valueOf(requiredVolume) * (Double.valueOf(priceForMinVolume) / Double.valueOf(minVolume));
		return (int) Math.ceil(result); // Rs. 16.45 becomes 17
	}

	public static String getVolumeExtension(int volumeCount, Product.Volume volumeType) {
		if (volumeType.equals(Product.Volume.QUANTITY)) {
			return String.format(Locale.ENGLISH, "%d %s", volumeCount, Product.Volume.QUANTITY.getExtension());
		} else if (volumeType.equals(Product.Volume.WEIGHT)) {
			double count = Double.valueOf(volumeCount) / Double.valueOf(1000);
			// 0.25 kg
			return String.format(Locale.ENGLISH, "%.2f %s", count, "kg");
		}
		return null;
	}

	public static List<String> getFoodCategories() {
		List<String> types = new ArrayList<>();
		for (Product.Type type : Product.Type.values()) {
			String value = String.valueOf(type.toString().charAt(0)).toUpperCase() +
					type.toString().substring(1).toLowerCase();
			types.add(value);
		}
		return types;
	}
}
