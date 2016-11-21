package com.greentopli.core.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.greentopli.Constants;
import com.greentopli.core.Utils;
import com.greentopli.core.dbhandler.CartDbHandler;
import com.greentopli.core.dbhandler.UserDbHandler;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.remote.UserService;
import com.greentopli.model.EntityList;
import com.greentopli.model.PurchasedItem;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rnztx on 24/10/16.
 */

public class OrderHistoryService extends IntentService {
	private static final String TAG = OrderHistoryService.class.getSimpleName();
	public static final String ACTION_PROCESSING = "com.greentopli.core.service.OrderHistoryService.SERVICE_PROCESSING";
	public static final String ACTION_PROCESSING_COMPLETE = "com.greentopli.core.service.OrderHistoryService.SERVICE_PROCESSING_COMPLETE";
	public static final String ACTION_PROCESSING_FAILED = "com.greentopli.core.service.OrderHistoryService.SERVICE_PROCESSING_FAILED";

	public OrderHistoryService(){
		super(OrderHistoryService.class.getSimpleName());
	}

	CartDbHandler cartDbHandler ;
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG,"Started "+ Calendar.getInstance().getTime());
		String user_id = intent.getDataString();

		if (user_id!=null && !user_id.isEmpty()){
			broadcast(ACTION_PROCESSING);
			UserService service = ServiceGenerator.createService(UserService.class);
			Call<EntityList<PurchasedItem>> call = service.getUserOrderHistory(user_id);
			cartDbHandler = new CartDbHandler(getApplicationContext());

			call.enqueue(new Callback<EntityList<PurchasedItem>>() {
				@Override
				public void onResponse(Call<EntityList<PurchasedItem>> call, Response<EntityList<PurchasedItem>> response) {
					if (response.body()!=null && response.body().getItems()!=null && !response.body().getItems().isEmpty()){
							cartDbHandler.storeOrderHistory(
									response.body().getItems()
							);
					}
					// empty case will be handled by presenter
					broadcast(ACTION_PROCESSING_COMPLETE);
					// send broadcast for WidgetUpdate
					broadcast(Constants.ACTION_WIDGET_UPDATE);
				}

				@Override
				public void onFailure(Call<EntityList<PurchasedItem>> call, Throwable t) {
					broadcast(ACTION_PROCESSING_FAILED);
					t.printStackTrace();
				}
			});
		}
	}

	private void broadcast(String action){
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(action);
		sendBroadcast(broadcastIntent);
		Log.d(TAG,"finished with "+action);
	}

	public static void start(Context context){
		// verify service is not running already
		if (!Utils.isMyServiceRunning(OrderHistoryService.class,context)){
			String userId = new UserDbHandler(context).getSignedUserInfo().getEmail();
			// start service to get new data
			Intent orderHistoryService = new Intent(context, OrderHistoryService.class);
			orderHistoryService.setData(Uri.parse(userId));
			context.startService(orderHistoryService);
		}
	}
}
