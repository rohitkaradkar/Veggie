package com.greentopli.core.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

import com.greentopli.Constants;
import com.greentopli.core.service.OrderHistoryService;
import com.greentopli.core.dbhandler.CartDbHandler;
import com.greentopli.core.dbhandler.UserDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.model.OrderHistory;
import com.greentopli.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rnztx on 28/10/16.
 */

public class OrderHistoryPresenter extends BasePresenter<OrderHistoryView> {
	private CartDbHandler mCartDbHandler;
	private IntentFilter mIntentFilter;
	private String mUserId;

	OrderHistoryPresenter(){
		mIntentFilter = new IntentFilter();
		// create filters
		mIntentFilter.addAction(OrderHistoryService.ACTION_PROCESSING);
		mIntentFilter.addAction(OrderHistoryService.ACTION_PROCESSING_COMPLETE);
		mIntentFilter.addAction(OrderHistoryService.ACTION_PROCESSING_FAILED);
	}
	public static OrderHistoryPresenter bind(OrderHistoryView mvpView, Context context){
		OrderHistoryPresenter presenter = new OrderHistoryPresenter();
		presenter.attachView(mvpView,context);
		return presenter;
	}

	BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()){
				case OrderHistoryService.ACTION_PROCESSING:
					getmMvpView().showProgressbar(true);
					getmMvpView().onEmpty(false);
					break;
				case OrderHistoryService.ACTION_PROCESSING_COMPLETE:
					// send new data to Views
					requestOrderHistory();
					// send broadcast for WidgetUpdate
					Intent intentWidgetNotify = new Intent(Constants.ACTION_ITEMS_PURCHASED);
					context.sendBroadcast(intentWidgetNotify);
					break;
				case OrderHistoryService.ACTION_PROCESSING_FAILED:
					// retry
					break;
			}
		}
	};
	@Override
	public void attachView(OrderHistoryView mvpView, Context context) {
		super.attachView(mvpView, context);
		mCartDbHandler = new CartDbHandler(context);
		mUserId = new UserDbHandler(context).getSignedUserInfo().getEmail();
		getContext().registerReceiver(mBroadcastReceiver,mIntentFilter);
		// send available data
		requestOrderHistory();
		// start service to get new data
		Intent orderHistoryService = new Intent(getContext(), OrderHistoryService.class);
		orderHistoryService.setData(Uri.parse(mUserId));
		getContext().startService(orderHistoryService);
	}

	@Override
	public void detachView() {
		getContext().unregisterReceiver(mBroadcastReceiver);
		super.detachView();
	}

	private void requestOrderHistory(){
		HashMap<Long,Integer> pair = mCartDbHandler.getOrderHistoryDates(mUserId);
		if (pair.size()==0){
			getmMvpView().onEmpty(true);
			getmMvpView().showProgressbar(false);
			return;
		}

		List<OrderHistory> orderHistoryList = new ArrayList<>();

		for (long date : pair.keySet()){
			// create Obj
			OrderHistory orderHistory = new OrderHistory(mUserId,date);
			List<Product> products = mCartDbHandler.getProductsFromCart(true,date);
			if (products.size()>0){
				orderHistory.setProducts(products);
				orderHistory.setTotalItems(products.size());
				orderHistory.setTotalPrice(pair.get(date));
				orderHistoryList.add(orderHistory);
			}
		}

		if (orderHistoryList.size()>0){
			// sort by date
			Collections.sort(orderHistoryList, new Comparator<OrderHistory>() {
				@Override
				public int compare(OrderHistory o1, OrderHistory o2) {
					Date d1 = new Date(o1.getOrderDate());
					Date d2 = new Date(o2.getOrderDate());
					//return d1.compareTo(d2); // Ascending order
					return d2.compareTo(d1); // Descending order
				}
			});

			getmMvpView().onHistoryReceived(orderHistoryList);
			getmMvpView().onEmpty(false);
		}
		getmMvpView().showProgressbar(false);
	}
}
