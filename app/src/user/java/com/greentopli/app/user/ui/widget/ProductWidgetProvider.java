package com.greentopli.app.user.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.widget.RemoteViews;

import com.greentopli.CommonUtils;
import com.greentopli.Constants;
import com.greentopli.app.AuthenticatorActivity;
import com.greentopli.app.R;
import com.greentopli.app.user.ui.OrderHistoryActivity;
import com.greentopli.app.user.ui.purchase.PurchaseManagerActivity;
import com.greentopli.core.storage.helper.CartDbHelper;
import com.greentopli.core.storage.helper.UserDbHelper;

import java.util.Locale;

/**
 * Created by rnztx on 5/11/16.
 */

public class ProductWidgetProvider extends AppWidgetProvider {
	private static final String FORMAT_PURCHASE = "%d items purchased for %d Rs.";
	private static final String FORMAT_NO_PURCHASE = "No items purchased today";
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		// number of Widgets
		for(int widgetId: appWidgetIds){
			Intent intent = new Intent(context,WidgetRemoteViewsService.class);
			intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widgetId);

			// Create Widget
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
			views.setRemoteAdapter(R.id.listView_widget_layout,intent);

			// create sub head text
			CartDbHelper cartDbHelper =new CartDbHelper(context);
			long date = CommonUtils.getDateExcludingTime();
			Pair<Integer,Integer> countSubtotalPair = cartDbHelper.getOrderSubtotal(date);
			if (countSubtotalPair!=null && countSubtotalPair.first>0 && countSubtotalPair.second>0){
				int count = countSubtotalPair.first;
				int totalPrice = countSubtotalPair.second;
				views.setTextViewText(R.id.widget_product_count_textView,
						String.format(Locale.ENGLISH, FORMAT_PURCHASE,count,totalPrice));
			}else {
				views.setTextViewText(R.id.widget_product_count_textView,FORMAT_NO_PURCHASE);
			}

			// Broadcast intent for OnClick widget header
			Intent orderHistoryIntent = new Intent(context,ProductWidgetProvider.class);
			orderHistoryIntent.setAction(Constants.ACTION_WIDGET_HEADER_CLICK);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,orderHistoryIntent,0);
			views.setOnClickPendingIntent(R.id.widget_purchased_item_container,pendingIntent);

			//Update Widget on Home screen
			appWidgetManager.updateAppWidget(widgetId,views);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// receive broadcast when new purchase is made.
		super.onReceive(context, intent);
		if (intent.getAction().equals(Constants.ACTION_WIDGET_UPDATE)){
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,getClass()));
			// update header
			this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
			// update list content
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.listView_widget_layout);
		}
		else if (intent.getAction().equals(Constants.ACTION_WIDGET_HEADER_CLICK)){
			if (new UserDbHelper(context).getSignedUserInfo()!=null){
				// on widget header click
				Intent orderHistoryIntent = new Intent(context, OrderHistoryActivity.class);
				orderHistoryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(orderHistoryIntent);
			}else {
				// user is not signed in
				Intent mainActivityIntent = new Intent(context, PurchaseManagerActivity.class);
				mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(mainActivityIntent);
			}
		}
	}
}
