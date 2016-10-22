package com.greentopli.core.handler;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.greentopli.core.Utils;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemContentValues;
import com.greentopli.core.storage.purchaseditem.PurchasedItemCursor;
import com.greentopli.core.storage.purchaseditem.PurchasedItemSelection;
import com.greentopli.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rnztx on 21/10/16.
 */

public class CartDbHandler {
	private Context context;
	private ProductDbHandler productDbHandler;
	public CartDbHandler(Context context) {
		this.context = context;
		productDbHandler = new ProductDbHandler(context);
	}

	public long addProductToCart(@NonNull String product_id){
		PurchasedItemContentValues values = new PurchasedItemContentValues();
		values.putProductId(product_id);
		values.putPurchaseId(UUID.randomUUID().toString());
		values.putUserId("rohit");
		values.putVolume(0);
		values.putAccepted(false);
		values.putCompleted(false);
		values.putDateAccepted(Utils.getDateExcludingTime());
		values.putDateRequested(Utils.getDateExcludingTime());
		Uri uri = values.insert(context.getContentResolver());
		return ContentUris.parseId(uri);
	}

	public int removeProductFromCart(@NonNull String product_id){
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.productId(product_id);
		return selection.delete(context.getContentResolver());
	}

	public List<String> getProductIdsFromCart(){
		List<String> list = new ArrayList<>();
		PurchasedItemSelection selection = new PurchasedItemSelection();
		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),
				new String[]{PurchasedItemColumns.PRODUCT_ID});
		while (cursor.moveToNext()){
			list.add(cursor.getProductId());
		}
		cursor.close();
		return  list;
	}

	public boolean isProductAddedToCart(@NonNull String product_id){
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.productId(product_id);
		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),PurchasedItemColumns.ALL_COLUMNS);
		boolean isAdded = false;
		if (cursor.moveToNext())
			isAdded = true;
		cursor.close();
		return isAdded;
	}

	public List<Product> getProductsFromCart(){
		List<String> ids = getProductIdsFromCart();
		return productDbHandler.retrieveProductsFromDatabase(ids);
	}
}
