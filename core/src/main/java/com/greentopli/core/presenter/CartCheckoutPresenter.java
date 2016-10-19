package com.greentopli.core.presenter;

import com.greentopli.core.presenter.base.MvpView;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.remote.UserService;
import com.greentopli.model.BackendResult;
import com.greentopli.model.UserCartItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 19/10/16.
 */

public class CartCheckoutPresenter{
	public interface ResultCallbacks extends MvpView{
		void onCartCheckoutSuccess();
		void onCartCheckoutFailed(List<String> failedProductIds);
		void onCartCheckoutError(String error_message);
	}
	private ResultCallbacks callbacks;
	Call<BackendResult> checkoutCall;

	public CartCheckoutPresenter(ResultCallbacks callbacks) {
		this.callbacks = callbacks;
	}


	public void checkOutOrders(UserCartItems cartItems){
		UserService service = ServiceGenerator.createService(UserService.class);
		checkoutCall = service.storePurchasedItems(cartItems);

		checkoutCall.enqueue(new Callback<BackendResult>() {
			@Override
			public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
				if (response.body()==null)
					callbacks.onCartCheckoutError("Null Pointer "+call.toString());
				else if (response.body().isResult())
					callbacks.onCartCheckoutSuccess();
				else
					callbacks.onCartCheckoutFailed(null);
			}

			@Override
			public void onFailure(Call<BackendResult> call, Throwable t) {
				callbacks.onCartCheckoutError(t.getMessage());
			}
		});
	}
}
