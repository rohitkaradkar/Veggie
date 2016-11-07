package com.greentopli.core;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.greentopli.core.handler.ProductDbHandler;
import com.greentopli.core.remote.BackendService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.model.EntityList;
import com.greentopli.model.Product;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 7/11/16.
 */

public class ProductService extends IntentService {
	private static final String TAG = ProductService.class.getSimpleName();

	public static final String ACTION_ERROR ="com.greentopli.core.ProductService.ERROR";
	public static final String ACTION_SUCCESS ="com.greentopli.core.ProductService.SUCCESS";
	public static final String ACTION_EMPTY ="com.greentopli.core.ProductService.EMPTY";
	public ProductService() {
		super(ProductService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		BackendService service = ServiceGenerator.createService(BackendService.class);
		final ProductDbHandler dbHandler = new ProductDbHandler(getApplicationContext());
		Log.d(TAG,"started "+ Calendar.getInstance().getTime());
		Call<EntityList<Product>> call = service.getProductInfoList();
		call.enqueue(new Callback<EntityList<Product>>() {
			@Override
			public void onResponse(Call<EntityList<Product>> call, Response<EntityList<Product>> response) {
				if (response.body()!=null && response.body().getItems()!=null){

					// we get Items
					if (response.body().getItems().size()>0) {
						// store to database
						dbHandler.storeProducts(response.body().getItems());
						broadcast(ACTION_SUCCESS);
					}
					else{ // server sends empty list
						Log.e(TAG,"Empty product list");
						broadcast(ACTION_EMPTY);
					}
				}
				else{// bad response
					Log.e(TAG,"Bad response "+response.errorBody());
					broadcast(ACTION_ERROR);
				}
			}

			@Override
			public void onFailure(Call<EntityList<Product>> call, Throwable t) {
				Log.e(TAG,"Connection Error "+t.getMessage());
				broadcast(ACTION_ERROR);
			}
		});
	}

	private void broadcast(String action){
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(action);
		sendBroadcast(broadcastIntent);
		Log.d(TAG,"completed with "+action);
	}
}
