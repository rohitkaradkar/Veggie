package com.greentopli.core.presenter.cart;

import android.content.Context;

import com.greentopli.core.handler.CartDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.remote.UserService;
import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;
import com.greentopli.model.UserCartItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 19/10/16.
 */

public class CartCheckoutPresenter extends BasePresenter<CartView>{

	private Call<BackendResult> checkoutCall;
	private CartDbHandler dbHandler;
	public CartCheckoutPresenter() {}

	public static CartCheckoutPresenter bind(CartView cartView,Context context){
		CartCheckoutPresenter presenter = new CartCheckoutPresenter();
		presenter.attachView(cartView,context);
		return presenter;
	}

	@Override
	public void attachView(CartView mvpView, Context context) {
		super.attachView(mvpView, context);
		dbHandler = new CartDbHandler(context);
		// TODO: detach view in fragment
	}

	public void checkOutOrders(){
		getmMvpView().showProgressbar(true);
		UserCartItems cartItems = new UserCartItems();
		cartItems.setCartItems(dbHandler.getPurchasedItemList());

		UserService service = ServiceGenerator.createService(UserService.class);
		checkoutCall = service.storePurchasedItems(cartItems);

		checkoutCall.enqueue(new Callback<BackendResult>() {
			@Override
			public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
				if (response.body()==null)
					getmMvpView().onCartCheckoutError("Null Pointer "+call.toString());
				else if (response.body().isResult()){
					dbHandler.clearCartItems();
					getmMvpView().onCartCheckoutSuccess();
				}
				else
					getmMvpView().onCartCheckoutFailed(null);

				getmMvpView().showProgressbar(false);
			}

			@Override
			public void onFailure(Call<BackendResult> call, Throwable t) {
				getmMvpView().onCartCheckoutError(t.getMessage());
				getmMvpView().showProgressbar(false);
			}
		});
	}

	@Override
	public void detachView() {
		super.detachView();
		checkoutCall.cancel();
	}

	public void getProductsFromCart(){
		List<Product> products = dbHandler.getProductsFromCart();
		if (products.size()>0)
			getmMvpView().onCartItemsReceived(products);
	}
}
