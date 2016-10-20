package com.greentopli.core.presenter;

import android.content.ContentUris;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.greentopli.core.CoreApp;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.storage.product.ProductContentValues;
import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;
import com.greentopli.model.ProductList;

import java.util.List;

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
					if (response.body().getItems().size()>0) {
						getmMvpView().showProducts(response.body().getItems());
						storeProducts(response.body().getItems());
					}
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

	private void storeProducts(@NonNull List<Product> productList){
		for (Product item : productList){
			long id = storeProduct(item);

		}
	}

	private long storeProduct(@NonNull Product product){
		ProductContentValues values = new ProductContentValues();
		values.putProductId(product.getId());
		values.putNameEnglish(product.getName_english());
		values.putNameHinglish(product.getName_hinglish());
		values.putImageUrl(product.getImageUrl());
		values.putVolume(product.getVolume().name());
		values.putType(product.getType().name());
		values.putPrice(product.getPrice());
		values.putVolumeSet(product.getVolumeSet());
		values.putMinVolume(product.getMinimumVolume());
		values.putMaxVolume(product.getMaximumVolume());
		values.putTime(product.getTime());
		Uri uri = values.insert(CoreApp.getContext().getContentResolver());
		return ContentUris.parseId(uri);
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
