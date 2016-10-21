package com.greentopli.core.presenter;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.greentopli.core.handler.CartDbHandler;
import com.greentopli.core.presenter.base.BasePresenter;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.product.ProductContentValues;
import com.greentopli.core.storage.product.ProductCursor;
import com.greentopli.core.storage.product.ProductSelection;
import com.greentopli.model.BackendResult;
import com.greentopli.model.Product;
import com.greentopli.model.ProductList;

import java.util.ArrayList;
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
	private CartDbHandler cartDbHandler;
	public BrowseProductsPresenter(){}
	@Override
	public void attachView(BrowseProductsView mvpView,Context context) {
		super.attachView(mvpView,context);
		cartDbHandler = new CartDbHandler(context);
	}

	public void getProductItems(){
		getmMvpView().showProgressbar(true);
		BackendService service = ServiceGenerator.createService(BackendService.class);
		Call<ProductList> call = service.getProductInfoList();
		call.enqueue(new Callback<com.greentopli.model.ProductList>() {
			@Override
			public void onResponse(Call<ProductList> call, Response<ProductList> response) {

				if (response.body().getItems()!=null){

					// we get Items
					if (response.body().getItems().size()>0) {
						// store to database
						storeProductListToDatabase(response.body().getItems());
						// retrieve to show
						getmMvpView().showProducts(retrieveProductsFromDatabase());
					}
					else // server sends empty list
						getmMvpView().showEmpty();

				}else // bad response
					getmMvpView().showError("Bad Response");

				getmMvpView().showProgressbar(false);
			}

			@Override
			public void onFailure(Call<ProductList> call, Throwable t) {
				getmMvpView().showError(null);
				Log.e(TAG,"Connection Error "+t.getMessage());
				getmMvpView().showProgressbar(false);
			}
		});
	}

	private void storeProductListToDatabase(@NonNull List<Product> productList){
		for (Product item : productList){
			long id = storeProductToDatabase(item);
		}
	}

	private List<Product> retrieveProductsFromDatabase(){
		ProductSelection selection = new ProductSelection();
		ProductCursor cursor = selection.query(getContext().getContentResolver(), ProductColumns.ALL_COLUMNS);
		List<Product> list = new ArrayList<>();
		while (cursor.moveToNext()){
			Product product = getProductFromCursor(cursor);
			if (product!=null)
				list.add(product);
		}
		cursor.close();
		return list;
	}

	private Product getProductFromCursor(ProductCursor cursor){
		Product product = new Product();
		product.setId(cursor.getProductId());
		product.setName_english(cursor.getNameEnglish());
		product.setName_hinglish(cursor.getNameHinglish());
		product.setImageUrl(cursor.getImageUrl());
		product.setPrice(cursor.getPrice());
		product.setVolumeSet(cursor.getVolumeSet());
		product.setMinimumVolume(cursor.getMinVolume());
		product.setMaximumVolume(cursor.getMaxVolume());
		product.setTime(cursor.getTime());
		// convert String to Enums
		product.setVolume(Product.Volume.valueOf(cursor.getVolume().toUpperCase(Locale.ENGLISH)));
		product.setType(Product.Type.valueOf(cursor.getType().toUpperCase(Locale.ENGLISH)));
		return product;
	}

	private long storeProductToDatabase(@NonNull Product product){
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
		Uri uri = values.insert(getContext().getContentResolver());
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
