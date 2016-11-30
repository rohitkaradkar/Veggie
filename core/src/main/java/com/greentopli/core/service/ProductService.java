package com.greentopli.core.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.greentopli.core.Utils;
import com.greentopli.core.remote.BackendConnectionService;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.storage.helper.ProductDbHelper;
import com.greentopli.model.Product;
import com.greentopli.model.list.EntityList;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by rnztx on 7/11/16.
 */

public class ProductService extends IntentService {
	private static final String TAG = ProductService.class.getSimpleName();

	public static final String ACTION_ERROR = "com.greentopli.core.service.ProductService.ERROR";
	public static final String ACTION_SUCCESS = "com.greentopli.core.service.ProductService.SUCCESS";
	public static final String ACTION_EMPTY = "com.greentopli.core.service.ProductService.EMPTY";

	public ProductService() {
		super(ProductService.class.getSimpleName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		BackendConnectionService service = ServiceGenerator.createService(BackendConnectionService.class);
		final ProductDbHelper dbHandler = new ProductDbHelper(getApplicationContext());
		Log.d(TAG, "started " + Calendar.getInstance().getTime());
		Call<EntityList<Product>> call = service.getProductInfoList();

		try {
			Response<EntityList<Product>> response = call.execute();
			if (response.body() != null && response.body().getItems() != null) {
				// we get Items
				if (response.body().getItems().size() > 0) {
					// store to database
					dbHandler.storeProducts(response.body().getItems());
					broadcast(ACTION_SUCCESS);
				} else { // server sends empty list
					Log.e(TAG, "Empty product list");
					broadcast(ACTION_EMPTY);
				}
			} else {// bad response
				Log.e(TAG, "Bad response " + response.errorBody());
				broadcast(ACTION_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			broadcast(ACTION_ERROR);
		}
	}

	private void broadcast(String action) {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(action);
		sendBroadcast(broadcastIntent);
		Log.d(TAG, "completed with " + action);
	}

	public static void start(Context context) {
		if (!Utils.isMyServiceRunning(ProductService.class, context)) {
			Intent intentService = new Intent(context, ProductService.class);
			context.startService(intentService);
		}
	}
}
