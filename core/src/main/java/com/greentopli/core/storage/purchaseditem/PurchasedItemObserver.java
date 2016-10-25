package com.greentopli.core.storage.purchaseditem;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.greentopli.core.handler.CartDbHandler;

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
		if (uri.equals(PurchasedItemColumns.CONTENT_URI)){
			updateCartInformation();
		}
	}

	public void updateCartInformation(){
		int totalOrderPrice = cartDbHandler.getCartSubtotal();
		int item_count = cartDbHandler.getProductsFromCart().size();
		listener.onCartItemsChanged(totalOrderPrice,item_count);
	}

	public interface Listener{
		void onCartItemsChanged(int total_price, int item_count);
	}
}
