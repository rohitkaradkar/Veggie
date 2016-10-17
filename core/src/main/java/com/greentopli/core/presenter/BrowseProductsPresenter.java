package com.greentopli.core.presenter;

import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.BackendResult;
import com.greentopli.model.ProductList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 13/10/16.
 */

public class BrowseProductsPresenter extends BasePresenter<BrowseProductsView> {
	public BrowseProductsPresenter(){}
	@Override
	public void attachView(BrowseProductsView mvpView) {
		super.attachView(mvpView);
		getmMvpView().showProgressbar(true);
		sendProducts();
	}

	private void sendProducts(){
		BackendService service = ServiceGenerator.createService(BackendService.class);
		Call<ProductList> call = service.getProductInfoList();
		call.enqueue(new Callback<com.greentopli.model.ProductList>() {
			@Override
			public void onResponse(Call<ProductList> call, Response<ProductList> response) {

				if (response.body().getItems()!=null){
					if (response.body().getItems().size()>0)
						getmMvpView().showProducts(response.body().getItems());
					else
						getmMvpView().showEmpty();
				}else
					getmMvpView().showError("Error Getting Data");

				getmMvpView().showProgressbar(false);
			}

			@Override
			public void onFailure(Call<ProductList> call, Throwable t) {
				getmMvpView().showError("Error in Connection:: "+t.getMessage());
				getmMvpView().showProgressbar(false);
			}
		});
	}
	public void deleteProduct(final String product_id){
		BackendService service = ServiceGenerator.createService(BackendService.class);
		getmMvpView().showProgressbar(true);
		Call<BackendResult> call = service.deleteProductInfo(product_id);
		call.enqueue(new Callback<BackendResult>() {
			@Override
			public void onResponse(Call<BackendResult> call, Response<BackendResult> response) {
					getmMvpView().onProductDeleted(response.body().isResult(),product_id);
					getmMvpView().showProgressbar(false);
			}

			@Override
			public void onFailure(Call<BackendResult> call, Throwable t) {
				getmMvpView().onProductDeleted(false,product_id);
				getmMvpView().showProgressbar(false);
			}
		});
	}
	@Override
	public void detachView() {
		super.detachView();
	}
}
