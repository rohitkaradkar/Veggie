package com.greentopli.core;

import com.greentopli.CommonUtils;

import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertTrue;

/**
 * Created by rnztx on 24/10/16.
 */

public class TestTimeStamp {
	@Test
	public void testTimeStamp() {
		Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		long gmtTime = CommonUtils.getDateExcludingTime(gmt);
		gmt.setTimeInMillis(gmtTime);

		Calendar local = Calendar.getInstance(TimeZone.getTimeZone("Asia/Calcutta"));
		long localTime = CommonUtils.getDateExcludingTime(local);
		local.setTimeInMillis(localTime);

		Calendar test = Calendar.getInstance();
		test.setTimeInMillis(localTime);

		System.out.println("Local: " + local.getTime() + "\nGMT: " + gmt.getTime() + "\n TEST: " + test.getTime());
		assertTrue(String.format("GMT %d Local %d", gmtTime, localTime),
				(gmtTime == localTime));
	}
}
