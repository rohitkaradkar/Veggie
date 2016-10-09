package com.greentopli.core;


import com.greentopli.core.remote.ProductTask;
import com.greentopli.model.ProductInfo;
import com.greentopli.model.ProductType;
import com.greentopli.model.PurchaseType;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	final CountDownLatch latch = new CountDownLatch(1);
	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}

	@Test
	public void data_store() throws Exception{

	}
}