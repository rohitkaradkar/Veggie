package com.greentopli.core.presenter.checkout;

import com.greentopli.core.presenter.base.MvpView;
import com.greentopli.model.Product;

import java.util.List;

/**
 * Created by rnztx on 22/10/16.
 */

public interface CartView extends MvpView {
	void onCartCheckoutSuccess(String user_id);
	void onCartCheckoutFailed(List<String> failedProductIds);
	void onCartCheckoutError(String error_message);
	void onCartItemsReceived(List<Product> cartItems);
}