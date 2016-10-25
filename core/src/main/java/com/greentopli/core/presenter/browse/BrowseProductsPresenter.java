package com.greentopli.core.presenter.browse;

import android.content.Context;
import android.util.Log;

import com.greentopli.core.handler.ProductDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.BackendResult;
import com.greentopli.model.EntityList;
import com.greentopli.model.Product;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 13/10/16.
 */

public class BrowseProductsPresenter extends BasePresenter<BrowseProductsView> {
	private static final String TAG = BrowseProductsPresenter.class.getSimpleName();
	private ProductDbHandler dbHandler;

	public BrowseProductsPresenter(){}
	@Override
	public void attachView(BrowseProductsView mvpView,Context context) {
		super.attachView(mvpView,context);
		dbHandler = new ProductDbHandler(context);
	}

	public void getProductItems(){
		getmMvpView().showProgressbar(true);
		BackendService service = ServiceGenerator.createService(BackendService.class);
		Call<EntityList<Product>> call = service.getProductInfoList();
		call.enqueue(new Callback<com.greentopli.model.EntityList<Product>>() {
			@Override
			public void onResponse(Call<EntityList<Product>> call, Response<EntityList<Product>> response) {

				if (response.body()!=null && response.body().getItems()!=null){

					// we get Items
					if (response.body().getItems().size()>0) {
						// store to database
						dbHandler.storeProductListToDatabase(response.body().getItems());
						// retrieve to show
						getmMvpView().showProducts(dbHandler.retrieveProductsFromDatabase());
					}
					else // server sends empty list
						getmMvpView().showEmpty();

				}else// bad response
					getmMvpView().showError("Bad Response");

				getmMvpView().showProgressbar(false);
			}

			@Override
			public void onFailure(Call<EntityList<Product>> call, Throwable t) {
				getmMvpView().showError("Connection Error");
				Log.e(TAG,"Connection Error "+t.getMessage());
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
