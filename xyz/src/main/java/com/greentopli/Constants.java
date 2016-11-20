package com.greentopli;

/**
 * Created by rnztx on 9/10/16.
 */

public final class Constants {

	public static final String API_SAVE_PRODUCT = "saveProductInfo";
	public static final String API_UPDATE_PRODUCT = "updateProductInfo";
	public static final String API_GET_PRODUCT = "getProductInfo";
	public static final String API_GET_PRODUCT_LIST = "getProductInfoList";
	public static final String API_DELETE_PRODUCT = "deleteProductInfo";

	public static final String ACTION_ITEMS_PURCHASED =
			"com.greentopli.app.user.ITEMS_PURCHASED";

	public static final String ACTION_WIDGET_HEADER_CLICK =
			"com.greentopli.app.user.ACTION_WIDGET_HEADER_CLICK";

	public static final String ERROR_AUTHTOKEN= "Error Obtaining AuthToken: ";
	public static final String ERROR_INSTANCEID= "Error Obtaining InstanceID: ";
	public static final String ERROR_REGISTERING_USERINFO= "Error Registering UserInfo: ";
	public static final String ERROR_SIGNIN= "Error SignIn: ";
	public static final String ERROR_PRODUCT_LOADING= "Error Product Loading: ";
	public static final String ERROR_CART_CHECKOUT= "Error Cart Checkout: ";

	/**
	 * Firebase Analytics Events
	 */
	public static final String EVENT_CART_ITEM_REMOVED = "item_removed_from_cart";
	public static final String EVENT_CATEGORY_SELECTED = "category_selected";
	public static final String ITEM_PRICE = "item_price";
	public static final String ITEM_VOLUME = "item_volume";

	//JSON key
	public static final String JSON_KEY_DATA = "data";
	public static final String JSON_KEY_TITLE = "title";
	public static final String JSON_KEY_MESSAGE= "message";

}
