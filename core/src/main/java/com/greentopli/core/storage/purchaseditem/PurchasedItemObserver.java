package com.greentopli.core.storage.purchaseditem;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.greentopli.core.handler.CartDbHandler;
import com.greentopli.model.PurchasedItem;

import java.util.List;

/**
 * Created by rnztx on 25/10/16.
 */

public class PurchasedItemObserver extends ContentObserver {
	private static final String TAG = PurchasedItemObserver.class.getSimpleName();
	private Context context;
	private Listener listener;
	CartDbHandler cartDbHandler;
	public PurchasedItemObserver(Handler handler, Context context, Listener listener) {
		super(handler);
		this.context = context;
		this.listener = listener;
		cartDbHandler = new CartDbHandler(context);
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		super.onChange(selfChange, uri);
		List<PurchasedItem> purchasedItems = cartDbHandler.getPurchasedItemList(true);
		int totalOrderPrice = 0;
		for (PurchasedItem item : purchasedItems)
			totalOrderPrice =+ item.getTotalPrice();

		listener.onTotalOrderPriceChanged(totalOrderPrice);
	}

	public interface Listener{
		void onTotalOrderPriceChanged(int total_price);
	}
}
