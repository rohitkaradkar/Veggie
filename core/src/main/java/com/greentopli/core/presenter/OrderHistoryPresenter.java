package com.greentopli.core.presenter;

import android.content.Context;

import com.greentopli.core.handler.CartDbHandler;
import com.greentopli.core.handler.UserDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.model.OrderHistory;
import com.greentopli.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rnztx on 28/10/16.
 */

public class OrderHistoryPresenter extends BasePresenter<OrderHistoryView> {
	CartDbHandler cartDbHandler;

	public static OrderHistoryPresenter bind(OrderHistoryView mvpView, Context context){
		OrderHistoryPresenter presenter = new OrderHistoryPresenter();
		presenter.attachView(mvpView,context);
		return presenter;
	}
	@Override
	public void attachView(OrderHistoryView mvpView, Context context) {
		super.attachView(mvpView, context);
		cartDbHandler = new CartDbHandler(context);
	}

	public void requestOrderHistory(){
		String userId = new UserDbHandler(getContext()).getSignedUserInfo().getEmail();
		requestOrderHistory(userId);
	}
	
	public void requestOrderHistory(String userId){
		HashMap<Long,Integer> pair = cartDbHandler.getOrderHistoryDates(userId);
		List<OrderHistory> orderHistoryList = new ArrayList<>();

		for (long date : pair.keySet()){
			// create Obj
			OrderHistory orderHistory = new OrderHistory(userId,date);
			List<Product> products = cartDbHandler.getProductsFromCart(true,date);
			if (products.size()>0){
				orderHistory.setProducts(products);
				orderHistory.setTotalItems(products.size());
				orderHistory.setTotalPrice(pair.get(date));
				orderHistoryList.add(orderHistory);
			}
		}

		if (orderHistoryList.size()>0)
			getmMvpView().onDataReceived(orderHistoryList);
	}
}
