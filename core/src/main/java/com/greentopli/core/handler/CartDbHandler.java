package com.greentopli.core.handler;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.greentopli.CommonUtils;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemContentValues;
import com.greentopli.core.storage.purchaseditem.PurchasedItemCursor;
import com.greentopli.core.storage.purchaseditem.PurchasedItemSelection;
import com.greentopli.model.Product;
import com.greentopli.model.PurchasedItem;
import com.greentopli.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

/**
 * Created by rnztx on 21/10/16.
 */

public class CartDbHandler {
	private static String TAG = CartDbHandler.class.getSimpleName();
	private Context context;
	private ProductDbHandler productDbHandler;
	private UserDbHandler userDbHandler;
	public CartDbHandler(Context context) {
		this.context = context;
		productDbHandler = new ProductDbHandler(context);
		userDbHandler = new UserDbHandler(context);
	}

	public long addProductToCart(@NonNull String product_id, @NonNull int volume){
		PurchasedItemContentValues values;

		PurchasedItem item = new PurchasedItem(userDbHandler.getSignedUserInfo().getEmail(),product_id);
		item.setVolume(volume);
		Product product = productDbHandler.getProductFromDatabase(product_id);
		if (!product.isEmpty()){
			int price = CommonUtils.calculatePrice(
					product.getPrice(),product.getMinimumVolume(),item.getVolume()
			);
			item.setTotalPrice(price);
		}
		values = getValuesFromPOJO(item);
		Uri uri = values.insert(context.getContentResolver());
		return ContentUris.parseId(uri);
	}

	public int removeProductFromCart(@NonNull String product_id){
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.productId(product_id)
				.and().accepted(false);
		return selection.delete(context.getContentResolver());
	}

	public List<String> getProductIdsFromCart(boolean isAcceptedBySeller){
		return getProductIdsFromCart(isAcceptedBySeller,0);
	}
	public List<String> getProductIdsFromCart(boolean isAcceptedBySeller, long dateRequested){
		List<String> list = new ArrayList<>();
		PurchasedItemSelection selection = new PurchasedItemSelection();

		if (dateRequested>0) // products accepted by seller
			selection.dateRequested(dateRequested).and().accepted(isAcceptedBySeller);
		else // only added to cart
			selection.accepted(isAcceptedBySeller);

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
		selection.productId(product_id)
			.and().accepted(false);
		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),PurchasedItemColumns.ALL_COLUMNS);
		boolean isAdded = false;
		if (cursor.moveToNext())
			isAdded = true;
		cursor.close();
		return isAdded;
	}

	public List<Product> getProductsFromCart(boolean acceptedBySeller){
		return getProductsFromCart(acceptedBySeller,0);
	}
	public List<Product> getProductsFromCart(boolean acceptedBySeller, long dateRequested){
		List<String> ids = getProductIdsFromCart(acceptedBySeller,dateRequested);
		return productDbHandler.retrieveProductsFromDatabase(ids);
	}

	public List<PurchasedItem> getPurchasedItemList(boolean acceptedBySeller){
		List<PurchasedItem> purchasedItems = new ArrayList<>();
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.accepted(acceptedBySeller); // cart items are not accepted
		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),PurchasedItemColumns.ALL_COLUMNS);
		while (cursor.moveToNext()){
			purchasedItems.add(getPOJOFromCursor(cursor));
		}
		cursor.close();
		return purchasedItems;
	}

	public PurchasedItem getCartItem(@NonNull String product_id, boolean acceptedBySeller){
		return getCartItem(product_id,acceptedBySeller,0);
	}

	public PurchasedItem getCartItem(@NonNull String product_id, boolean acceptedBySeller, long dateRequested){
		PurchasedItemSelection selection = new PurchasedItemSelection();
		if (acceptedBySeller)
			selection.productId(product_id).and().accepted(acceptedBySeller).and().dateRequested(dateRequested);
		else
			selection.productId(product_id).and().accepted(acceptedBySeller);

		PurchasedItemCursor cursor = selection.query(context.getContentResolver(),PurchasedItemColumns.ALL_COLUMNS);
		PurchasedItem item = new PurchasedItem();
		while (cursor.moveToNext()){
			item = getPOJOFromCursor(cursor);
		}
		cursor.close();
		return item;
	}

	public int getCartSubtotal(){
		List<PurchasedItem> purchasedItems = getPurchasedItemList(false);
		int totalOrderPrice = 0;
		for (PurchasedItem item : purchasedItems)
			totalOrderPrice = totalOrderPrice + item.getTotalPrice();
		return totalOrderPrice;
	}

	private PurchasedItem getPOJOFromCursor(PurchasedItemCursor cursor){
		PurchasedItem item = new PurchasedItem();
		item.setOrderId(cursor.getPurchaseId());
		item.setProductId(cursor.getProductId());
		item.setUserId(cursor.getUserId());
		item.setVolume(cursor.getVolume());
		item.setAccepted(cursor.getAccepted());
		item.setCompleted(cursor.getCompleted());
		item.setDateRequested(cursor.getDateRequested());
		item.setDateCompleted(cursor.getDateAccepted());
		item.setTotalPrice(cursor.getTotalPrice());
		return item;
	}

	private PurchasedItemContentValues getValuesFromPOJO(PurchasedItem item){
		PurchasedItemContentValues values = new PurchasedItemContentValues();
		values.putPurchaseId(item.getOrderId());
		values.putProductId(item.getProductId());
		values.putUserId(item.getUserId());
		values.putVolume(item.getVolume());
		values.putAccepted(item.isAccepted());
		values.putCompleted(item.isCompleted());
		values.putDateRequested(item.getDateRequested());
		values.putDateAccepted(item.getDateCompleted());
		values.putTotalPrice(item.getTotalPrice());
		return values;
	}

	public void storeOrderHistory(@NonNull List<PurchasedItem> purchasedItems){
		// delete all orders except from cart order
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.accepted(true);
		selection.delete(context);

		for (PurchasedItem item: purchasedItems){
			PurchasedItemContentValues values = getValuesFromPOJO(item);
			Uri uri = values.insert(context);
			ContentUris.parseId(uri);
		}
	}

	public int updateVolume(@NonNull String product_id, @NonNull int updated_volume){
		PurchasedItemSelection where = new PurchasedItemSelection();
		where.productId(product_id).and().accepted(false);

		PurchasedItemContentValues values = new PurchasedItemContentValues();
		values.putVolume(updated_volume);

		Product product = productDbHandler.getProductFromDatabase(product_id);
		if (!product.isEmpty()){
			int price = CommonUtils.calculatePrice(
					product.getPrice(),product.getMinimumVolume(),updated_volume
			);
			values.putTotalPrice(price);
		}
		return values.update(context,where);
	}

	public int clearCartItems(){
		PurchasedItemSelection allItems = new PurchasedItemSelection();
		allItems.accepted(false);
		return allItems.delete(context);
	}

	// returns Date & price in pair
	public HashMap<Long,Integer> getOrderHistoryDates(String userId){
		// pairing - order Date & total price
		HashMap<Long,Integer> pair = new HashMap<>();
		List<PurchasedItem> list = new ArrayList<>();

		// get accepted Purchased Items
		PurchasedItemSelection selection = new PurchasedItemSelection();
		selection.accepted(true).and().userId(userId);
		PurchasedItemCursor cursor = selection.query(context,PurchasedItemColumns.ALL_COLUMNS);
		while (cursor.moveToNext()){
			list.add(getPOJOFromCursor(cursor));
		}
		cursor.close();

		// Calculate price
		for (PurchasedItem item: list){
			long dateRequested = item.getDateRequested();
			int price = item.getTotalPrice();

			if (pair.containsKey(dateRequested)){
				int priceSum = pair.get(dateRequested) + price;
				pair.put(dateRequested,priceSum);
			}else {
				pair.put(dateRequested,price);
			}
		}

		return pair;
	}
}
