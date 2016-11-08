package com.greentopli.core.presenter.browse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.greentopli.core.service.ProductService;
import com.greentopli.core.dbhandler.ProductDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 13/10/16.
 */

public class BrowseProductsPresenter extends BasePresenter<BrowseProductsView> {
	private static final String TAG = BrowseProductsPresenter.class.getSimpleName();
	private ProductDbHandler dbHandler;
	private IntentFilter mIntentFilter;
	private List<Product> mProducts;

	public BrowseProductsPresenter(){
		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(ProductService.ACTION_SUCCESS);
		mIntentFilter.addAction(ProductService.ACTION_ERROR);
		mIntentFilter.addAction(ProductService.ACTION_EMPTY);
	}

	// receive ProductService Broadcast
	BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			switch (intent.getAction()){
				case ProductService.ACTION_ERROR:
					getmMvpView().showError("");
					break;
				case ProductService.ACTION_SUCCESS:
					mProducts = dbHandler.getProducts();
					if (mProducts.size()>0)
						getmMvpView().showProducts(mProducts);
					break;
				case ProductService.ACTION_EMPTY:
					getmMvpView().showEmpty();
					break;
			}
			getmMvpView().showProgressbar(false);
		}
	};

	public static BrowseProductsPresenter bind(BrowseProductsView mvpView,Context context){
		// helps reduce boilerplate code
		BrowseProductsPresenter presenter = new BrowseProductsPresenter();
		presenter.attachView(mvpView,context);
		return presenter;
	}

	@Override
	public void attachView(BrowseProductsView mvpView,Context context) {
		super.attachView(mvpView,context);
		dbHandler = new ProductDbHandler(getContext());
		getContext().registerReceiver(mBroadcastReceiver,mIntentFilter);
		mProducts = dbHandler.getProducts();
	}

	public void getProductItems(){
		//Sends Product list to View
		getmMvpView().showProgressbar(true);
		if (mProducts.size()>0) {
			getmMvpView().showProducts(mProducts);
			getmMvpView().showProgressbar(false);
		}else{
			Intent intentService = new Intent(getContext(),ProductService.class);
			getContext().startService(intentService);
		}
	}

	public void searchProduct(String query){
		// search products & send them to View
		if (mProducts.size()>0){
			List<Product> queryResultList = dbHandler.getProducts(query);
			if (queryResultList.size()>0)
				getmMvpView().showProducts(queryResultList);
		}
	}

	public void sortProducts(String productTypeString){
		Product.Type productType = Product.Type.valueOf(productTypeString.toUpperCase(Locale.ENGLISH));
		if (productType.equals(Product.Type.ALL))
			getProductItems();
		else if (mProducts.size()>0){
			List<Product> queryResultList = dbHandler.getProducts(productType);
			if (queryResultList.size()>0)
				getmMvpView().showProducts(queryResultList);
		}
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
		getContext().unregisterReceiver(mBroadcastReceiver);
		mBroadcastReceiver = null; mIntentFilter =null; dbHandler = null;
		super.detachView();
	}
}
