package com.greentopli.core.remote;

import com.greentopli.model.BackendResult;
import com.greentopli.model.ProductInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 12/10/16.
 */

public class ProductInfoPresenter {
	public interface Callbacks{
		void onSuccess();
		void  onFailure(String message);
	}
	private Callbacks callbacks;
	private BackendService service;

	public ProductInfoPresenter(Callbacks callbacks) {
		this.callbacks = callbacks;
		service = ServiceGenerator.createService(BackendService.class);
	}
	public void addProductInfo(ProductInfo productInfo){
		Call<BackendResult> saveProductInfo = service.saveProductInfo(productInfo);
		saveProductInfo.enqueue(new Callback<BackendResult>() {
			@Override
			public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
				if (response.body().isResult())
					callbacks.onSuccess();
				else
					callbacks.onFailure(response.body().getMessage());
			}

			@Override
			public void onFailure(Call<BackendResult> call, Throwable t) {
				callbacks.onFailure("Error Connecting Server: "+t.getMessage());
			}
		});
	}
}
