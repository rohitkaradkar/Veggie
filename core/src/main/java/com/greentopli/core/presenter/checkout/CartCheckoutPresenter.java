package com.greentopli.core.presenter.checkout;

import android.content.Context;

import com.greentopli.core.storage.helper.CartDbHelper;
import com.greentopli.core.storage.helper.UserDbHelper;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.remote.BackendConnectionService;
import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;
import com.greentopli.model.list.UserOrders;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 19/10/16.
 */

public class CartCheckoutPresenter extends BasePresenter<CartView>{

	private Call<BackendResult> checkoutCall;
	private CartDbHelper dbHandler;
	private UserDbHelper userDbHelper;
	public CartCheckoutPresenter() {}

	public static CartCheckoutPresenter bind(CartView cartView,Context context){
		CartCheckoutPresenter presenter = new CartCheckoutPresenter();
		presenter.attachView(cartView,context);
		return presenter;
	}

	@Override
	public void attachView(CartView mvpView, Context context) {
		super.attachView(mvpView, context);
		dbHandler = new CartDbHelper(context);
		userDbHelper = new UserDbHelper(context);
		// TODO: detach view in fragment
	}

	public void checkOutOrders(){
		getmMvpView().showProgressbar(true);
		UserOrders cartItems = new UserOrders();
		cartItems.setItems(dbHandler.getPurchasedItemList(false));

		BackendConnectionService service = ServiceGenerator.createService(BackendConnectionService.class);
		checkoutCall = service.storePurchasedItems(cartItems);

		checkoutCall.enqueue(new Callback<BackendResult>() {
			@Override
			public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
				if (response.body()==null)
					getmMvpView().onCartCheckoutError("Null Pointer "+call.toString());
				else if (response.body().isResult()){
					dbHandler.clearCartItems();
					getmMvpView().onCartCheckoutSuccess(
							userDbHelper.getSignedUserInfo().getEmail()
					);
					return;
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
		this.userDbHelper = null;
		this.dbHandler = null;
		super.detachView();
	}

	public void getProductsFromCart(){
		List<Product> products = dbHandler.getProductsFromCart(false);
		if (products.size()>0)
			getmMvpView().onCartItemsReceived(products);
	}
}
