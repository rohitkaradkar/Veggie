package com.greentopli.core;

import com.greentopli.core.presenter.CartCheckoutPresenter;
import com.greentopli.model.PurchaseEntity;

import org.junit.Test;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by rnztx on 19/10/16.
 */

public class UserServiceTest {
	@Test public void test_checkout() throws InterruptedException{
		final CountDownLatch latch = new CountDownLatch(1);
		CartCheckoutPresenter presenter = new CartCheckoutPresenter(new CartCheckoutPresenter.ResultCallbacks() {
			@Override
			public void onCartCheckoutSuccess() {
				latch.countDown();
			}

			@Override
			public void onCartCheckoutFailed(List<String> failedProductIds) {
				latch.countDown();
			}

			@Override
			public void onCartCheckoutError(String error_message) {
				latch.countDown();
			}

			@Override
			public void showProgressbar(boolean show) {

			}
		});
		PurchaseEntity entity = new PurchaseEntity("rohit","orange");
		entity.setTime_completed(Calendar.getInstance().getTime());
		entity.setTime_placed(Calendar.getInstance().getTime());

		presenter.checkOutOrders(entity);
		latch.await();
	}
}
