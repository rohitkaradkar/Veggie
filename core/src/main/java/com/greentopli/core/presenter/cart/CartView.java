package com.greentopli.core.presenter.cart;

import com.greentopli.core.presenter.base.MvpView;
import com.greentopli.model.Product;
import com.greentopli.model.PurchasedItem;

import java.util.List;

/**
 * Created by rnztx on 22/10/16.
 */

public interface CartView extends MvpView {
	void onCartCheckoutSuccess();
	void onCartCheckoutFailed(List<String> failedProductIds);
	void onCartCheckoutError(String error_message);
	void onCartItemsReceived(List<Product> cartItems);
}