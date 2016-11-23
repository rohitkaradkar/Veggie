package com.greentopli.core.presenter.browse;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.greentopli.core.service.ProductService;
import com.greentopli.core.storage.helper.ProductDbHelper;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.model.Product;

import java.util.List;
import java.util.Locale;

/**
 * Created by rnztx on 13/10/16.
 */

public class BrowseProductsPresenter extends BasePresenter<BrowseProductsView> {
	private static final String TAG = BrowseProductsPresenter.class.getSimpleName();
	private ProductDbHelper dbHandler;
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
					getmMvpView().showEmpty(true);
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
		dbHandler = new ProductDbHelper(getContext());
		getContext().registerReceiver(mBroadcastReceiver,mIntentFilter);
		mProducts = dbHandler.getProducts();
	}

	public void getProductItems(){
		//Sends Product list to View
		getmMvpView().showProgressbar(true);
		if (mProducts.size()>0) {
//			getmMvpView().showProducts(mProducts);
			getmMvpView().onProductServiceFinished();
			getmMvpView().showProgressbar(false);
		}else{
			getmMvpView().showEmpty(true);
			ProductService.start(getContext());
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

	@Override
	public void detachView() {
		if (getContext()!=null)
			getContext().unregisterReceiver(mBroadcastReceiver);
		mBroadcastReceiver = null; mIntentFilter =null; dbHandler = null;
		super.detachView();
	}
}
