package com.greentopli.core;

import com.greentopli.core.presenter.cart.CartCheckoutPresenter;
import com.greentopli.model.PurchasedItem;
import com.greentopli.model.UserCartItems;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by rnztx on 19/10/16.
 */

public class UserServiceTest {
	@Test public void test_checkout() throws InterruptedException{
		final CountDownLatch latch = new CountDownLatch(1);
		CartCheckoutPresenter presenter = new CartCheckoutPresenter(new CartCheckoutPresenter.CartView() {
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
			public void showProgressbar(boolean show) {}
		});

		presenter.checkOutOrders(new UserCartItems(getDummyData()));
		latch.await();
	}

	private List<PurchasedItem> getDummyData(){
		List<PurchasedItem> list = new ArrayList<>();
		for (int i=0; i<100; i++){
			PurchasedItem entity;
			if (i%2==0){
				entity = new PurchasedItem("rohit", UUID.randomUUID().toString());
				entity.setAccepted(true);
				Calendar calendar = Calendar.getInstance();
				calendar.roll(Calendar.DAY_OF_MONTH,true);
				entity.setDateCompleted(Utils.getDateExcludingTime(calendar));
			} else if (i%3==0){
				entity = new PurchasedItem("amar", UUID.randomUUID().toString());
				entity.setAccepted(true);
				Calendar calendar = Calendar.getInstance();
				calendar.roll(Calendar.DAY_OF_MONTH,false);
				entity.setDateCompleted(Utils.getDateExcludingTime(calendar));
			}else if (i%7==0){
				entity = new PurchasedItem("romen", UUID.randomUUID().toString());
				entity.setCompleted(true);
				entity.setDateCompleted(Utils.getDateExcludingTime());
			}
			else {
				entity = new PurchasedItem("dev", UUID.randomUUID().toString());
				entity.setCompleted(true);
				entity.setAccepted(true);
				entity.setDateCompleted(Utils.getDateExcludingTime());
			}
			list.add(entity);
		}
		return list;
	}
}
