package com.greentopli.core.presenter;

import com.greentopli.core.presenter.base.MvpView;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 12/10/16.
 */

public class AddProductPresenter implements Callback<BackendResult> {
	public interface Callbacks extends MvpView{
		void onSuccess();
		void  onFailure(String message);
	}
	private Callbacks callbacks;
	private BackendService service;

	public AddProductPresenter(Callbacks callbacks) {
		this.callbacks = callbacks;
		service = ServiceGenerator.createService(BackendService.class);
	}
	public void addProductInfo(Product product){
		callbacks.showProgressbar(true);
		Call<BackendResult> saveProductCall = service.saveProductInfo(product);
		saveProductCall.enqueue(this);
	}

	public void editProductInfo(Product product){
		callbacks.showProgressbar(true);
		Call<BackendResult> editProductCall = service.updateProductInfo(product);
		editProductCall.enqueue(this);
	}
	@Override
	public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
		if (response.body().isResult())
			callbacks.onSuccess();
		else
			callbacks.onFailure(response.body().getMessage());
		callbacks.showProgressbar(false);
	}

	@Override
	public void onFailure(Call<BackendResult> call, Throwable t) {
		callbacks.onFailure("Error Connecting Server: "+t.getMessage());
		callbacks.showProgressbar(false);
	}
}
