package com.greentopli.core.service;

import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

import com.greentopli.core.storage.helper.CartDbHelper;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.model.Product;

import java.util.List;

/**
 * Created by rnztx on 25/10/16.
 */

public class PurchasedItemObserver extends ContentObserver {
	private static final String TAG = PurchasedItemObserver.class.getSimpleName();
	private Context context;
	private Listener listener;
	CartDbHelper cartDbHelper;
	public PurchasedItemObserver(Handler handler, Context context, Listener listener) {
		super(handler);
		this.context = context;
		this.listener = listener;
		cartDbHelper = new CartDbHelper(context);
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
		int totalOrderPrice = cartDbHelper.getCartSubtotal();
		List<Product> cartProducts = cartDbHelper.getProductsFromCart(false);
		if (cartProducts!=null){
			listener.onCartItemsChanged(totalOrderPrice,cartProducts.size());
		}
		else {
			// Cart is empty
			listener.onCartItemsChanged(0,0);
		}
	}

	public interface Listener{
		void onCartItemsChanged(int total_price, int item_count);
	}
}
