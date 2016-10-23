package com.greentopli.core.handler;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.greentopli.core.Utils;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemContentValues;
import com.greentopli.core.storage.purchaseditem.PurchasedItemCursor;
import com.greentopli.core.storage.purchaseditem.PurchasedItemModel;
import com.greentopli.core.storage.purchaseditem.PurchasedItemSelection;
import com.greentopli.model.Product;
import com.greentopli.model.PurchasedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rnztx on 21/10/16.
 */

public class CartDbHandler {
	private Context context;
	private ProductDbHandler productDbHandler;
	private UserDbHandler userDbHandler;
	public CartDbHandler(Context context) {
		this.context = context;
		productDbHandler = new ProductDbHandler(context);
		userDbHandler = new UserDbHandler(context);
	}

	public long addProductToCart(@NonNull String product_id, @NonNull int volume){
		PurchasedItemContentValues values = new PurchasedItemContentValues();
		values.putProductId(product_id);
		values.putPurchaseId(UUID.randomUUID().toString());
		values.putUserId(userDbHandler.getSignedUserInfo().getEmail());
		values.putVolume(volume);
		values.putAccepted(false);
		values.putCompleted(false);
		values.putDateAccepted(0);
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

	public List<PurchasedItem> getPurchasedItemList(){
		List<PurchasedItem> purchasedItems = new ArrayList<>();
		PurchasedItemSelection selection = new PurchasedItemSelection();
		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),PurchasedItemColumns.ALL_COLUMNS);
		while (cursor.moveToNext()){
			purchasedItems.add(getPurchasedItemFromCursor(cursor));
		}
		cursor.close();
		return purchasedItems;
	}
	public PurchasedItem getPurchasedItem(@NonNull String product_id){
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.productId(product_id);
		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),PurchasedItemColumns.ALL_COLUMNS);
	    PurchasedItem item = new PurchasedItem();
		while (cursor.moveToNext()){
			item = getPurchasedItemFromCursor(cursor);
		}
		cursor.close();
		return item;
	}
	private PurchasedItem getPurchasedItemFromCursor(PurchasedItemCursor cursor){
		PurchasedItem item = new PurchasedItem();
		item.setOrderId(cursor.getPurchaseId());
		item.setProductId(cursor.getProductId());
		item.setUserId(cursor.getUserId());
		item.setVolume(cursor.getVolume());
		item.setAccepted(cursor.getAccepted());
		item.setCompleted(cursor.getCompleted());
		item.setDateRequested(cursor.getDateRequested());
		item.setDateCompleted(cursor.getDateAccepted());
		return item;
	}

	public int updateVolume(@NonNull String product_id, @NonNull int updated_volume){
		PurchasedItemSelection where = new PurchasedItemSelection();
		where.productId(product_id);
		PurchasedItemContentValues values = new PurchasedItemContentValues();
		values.putVolume(updated_volume);
		return values.update(context,where);
	}

	public int clearCartItems(){
		PurchasedItemSelection allItems = new PurchasedItemSelection();
		return allItems.delete(context);
	}
}
