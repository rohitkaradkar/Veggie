package com.greentopli.app.user.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.greentopli.CommonUtils;
import com.greentopli.Constants;
import com.greentopli.app.R;
import com.greentopli.app.user.ui.OrderHistoryActivity;
import com.greentopli.core.handler.CartDbHandler;

import java.util.Locale;

/**
 * Created by rnztx on 5/11/16.
 */

public class ProductWidgetProvider extends AppWidgetProvider {
	private static final String FORMAT_ITEM_COUNT = "%d items purchased for %d Rs.";

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
			CartDbHandler cartDbHandler =new CartDbHandler(context);
			long date = CommonUtils.getDateExcludingTime();
			int count = cartDbHandler.getProductsFromCart(true,date).size();
			int totalPrice = cartDbHandler.getCartSubtotal(true);
			views.setTextViewText(R.id.widget_product_count_textView,
					String.format(Locale.ENGLISH,FORMAT_ITEM_COUNT,count,totalPrice));

			// open order history on widget click
			Intent orderHistoryIntent = new Intent(context,OrderHistoryActivity.class);
			PendingIntent pendingIntent = TaskStackBuilder.create(context)
					.addNextIntentWithParentStack(orderHistoryIntent)
					.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
			views.setPendingIntentTemplate(R.id.widget_purchased_item_container,pendingIntent);

			//Update Widget on Home screen
			appWidgetManager.updateAppWidget(widgetId,views);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// receive broadcast when new purchase is made.
		super.onReceive(context, intent);
		if (intent.getAction().equals(Constants.ACTION_ITEMS_PURCHASED)){
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,getClass()));
			// update header
			this.onUpdate(context, AppWidgetManager.getInstance(context), appWidgetIds);
			// update list content
			appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.listView_widget_layout);
		}
	}
}
