package com.greentopli.core.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.greentopli.Constants;
import com.greentopli.core.Utils;
import com.greentopli.core.storage.helper.CartDbHelper;
import com.greentopli.core.storage.helper.UserDbHelper;
import com.greentopli.core.remote.ServiceGenerator;
import com.greentopli.core.remote.BackendConnectionService;
import com.greentopli.model.list.EntityList;
import com.greentopli.model.PurchasedItem;
import com.greentopli.model.User;

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

	CartDbHelper cartDbHelper;
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG,"Started "+ Calendar.getInstance().getTime());
		String user_id = intent.getDataString();

		if (user_id!=null && !user_id.isEmpty()){
			broadcast(ACTION_PROCESSING);
			BackendConnectionService service = ServiceGenerator.createService(BackendConnectionService.class);
			Call<EntityList<PurchasedItem>> call = service.getUserOrderHistory(user_id);
			cartDbHelper = new CartDbHelper(getApplicationContext());

			try {
				// no need to enqueue as we are on Background thread
				Response<EntityList<PurchasedItem>> response = call.execute();
				if (response!=null && response.body()!=null
						&& response.body().getItems()!=null && !response.body().getItems().isEmpty()){
					cartDbHelper.storeOrderHistory(
							response.body().getItems()
					);
				}
				// empty case will be handled by presenter
				broadcast(ACTION_PROCESSING_COMPLETE);
				// send broadcast for WidgetUpdate
				broadcast(Constants.ACTION_WIDGET_UPDATE);
			}catch (Exception e){
				e.printStackTrace();
				broadcast(ACTION_PROCESSING_FAILED);
			}
		}
	}

	private void broadcast(String action){
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(action);
		sendBroadcast(broadcastIntent);
		Log.d(TAG," status "+action);
	}

	public static void start(Context context){
		User user = new UserDbHelper(context).getSignedUserInfo();
		// verify service is not running already
		if (user!=null && !Utils.isMyServiceRunning(OrderHistoryService.class,context)){
			String userId = user.getEmail();
			// start service to get new data
			Intent orderHistoryService = new Intent(context, OrderHistoryService.class);
			orderHistoryService.setData(Uri.parse(userId));
			context.startService(orderHistoryService);
		}
	}
}
