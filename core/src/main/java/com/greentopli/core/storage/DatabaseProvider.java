package com.greentopli.core.storage;

import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.greentopli.core.BuildConfig;
import com.greentopli.core.storage.base.BaseContentProvider;
import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.purchaseditem.PurchasedItemColumns;
import com.greentopli.core.storage.user.UserColumns;

import java.util.Arrays;

public class DatabaseProvider extends BaseContentProvider {
	private static final String TAG = DatabaseProvider.class.getSimpleName();

	private static final boolean DEBUG = BuildConfig.DEBUG;

	private static final String TYPE_CURSOR_ITEM = "vnd.android.cursor.item/";
	private static final String TYPE_CURSOR_DIR = "vnd.android.cursor.dir/";

	public static final String AUTHORITY = "com.greentopli.core.storage";
	public static final String CONTENT_URI_BASE = "content://" + AUTHORITY;

	private static final int URI_TYPE_PRODUCT = 0;
	private static final int URI_TYPE_PRODUCT_ID = 1;

	private static final int URI_TYPE_PURCHASED_ITEM = 2;
	private static final int URI_TYPE_PURCHASED_ITEM_ID = 3;

	private static final int URI_TYPE_USER = 4;
	private static final int URI_TYPE_USER_ID = 5;


	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

	static {
		URI_MATCHER.addURI(AUTHORITY, ProductColumns.TABLE_NAME, URI_TYPE_PRODUCT);
		URI_MATCHER.addURI(AUTHORITY, ProductColumns.TABLE_NAME + "/#", URI_TYPE_PRODUCT_ID);
		URI_MATCHER.addURI(AUTHORITY, PurchasedItemColumns.TABLE_NAME, URI_TYPE_PURCHASED_ITEM);
		URI_MATCHER.addURI(AUTHORITY, PurchasedItemColumns.TABLE_NAME + "/#", URI_TYPE_PURCHASED_ITEM_ID);
		URI_MATCHER.addURI(AUTHORITY, UserColumns.TABLE_NAME, URI_TYPE_USER);
		URI_MATCHER.addURI(AUTHORITY, UserColumns.TABLE_NAME + "/#", URI_TYPE_USER_ID);
	}

	@Override
	protected SQLiteOpenHelper createSqLiteOpenHelper() {
		return DatabaseSQLiteOpenHelper.getInstance(getContext());
	}

	@Override
	protected boolean hasDebug() {
		return DEBUG;
	}

	@Override
	public String getType(Uri uri) {
		int match = URI_MATCHER.match(uri);
		switch (match) {
			case URI_TYPE_PRODUCT:
				return TYPE_CURSOR_DIR + ProductColumns.TABLE_NAME;
			case URI_TYPE_PRODUCT_ID:
				return TYPE_CURSOR_ITEM + ProductColumns.TABLE_NAME;

			case URI_TYPE_PURCHASED_ITEM:
				return TYPE_CURSOR_DIR + PurchasedItemColumns.TABLE_NAME;
			case URI_TYPE_PURCHASED_ITEM_ID:
				return TYPE_CURSOR_ITEM + PurchasedItemColumns.TABLE_NAME;

			case URI_TYPE_USER:
				return TYPE_CURSOR_DIR + UserColumns.TABLE_NAME;
			case URI_TYPE_USER_ID:
				return TYPE_CURSOR_ITEM + UserColumns.TABLE_NAME;

		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (DEBUG) Log.d(TAG, "insert uri=" + uri + " values=" + values);
		return super.insert(uri, values);
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		if (DEBUG) Log.d(TAG, "bulkInsert uri=" + uri + " values.length=" + values.length);
		return super.bulkInsert(uri, values);
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (DEBUG)
			Log.d(TAG, "update uri=" + uri + " values=" + values + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
		return super.update(uri, values, selection, selectionArgs);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (DEBUG)
			Log.d(TAG, "delete uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs));
		return super.delete(uri, selection, selectionArgs);
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		if (DEBUG)
			Log.d(TAG, "query uri=" + uri + " selection=" + selection + " selectionArgs=" + Arrays.toString(selectionArgs) + " sortOrder=" + sortOrder
					+ " groupBy=" + uri.getQueryParameter(QUERY_GROUP_BY) + " having=" + uri.getQueryParameter(QUERY_HAVING) + " limit=" + uri.getQueryParameter(QUERY_LIMIT));
		return super.query(uri, projection, selection, selectionArgs, sortOrder);
	}

	@Override
	protected QueryParams getQueryParams(Uri uri, String selection, String[] projection) {
		QueryParams res = new QueryParams();
		String id = null;
		int matchedId = URI_MATCHER.match(uri);
		switch (matchedId) {
			case URI_TYPE_PRODUCT:
			case URI_TYPE_PRODUCT_ID:
				res.table = ProductColumns.TABLE_NAME;
				res.idColumn = ProductColumns._ID;
				res.tablesWithJoins = ProductColumns.TABLE_NAME;
				res.orderBy = ProductColumns.DEFAULT_ORDER;
				break;

			case URI_TYPE_PURCHASED_ITEM:
			case URI_TYPE_PURCHASED_ITEM_ID:
				res.table = PurchasedItemColumns.TABLE_NAME;
				res.idColumn = PurchasedItemColumns._ID;
				res.tablesWithJoins = PurchasedItemColumns.TABLE_NAME;
				res.orderBy = PurchasedItemColumns.DEFAULT_ORDER;
				break;

			case URI_TYPE_USER:
			case URI_TYPE_USER_ID:
				res.table = UserColumns.TABLE_NAME;
				res.idColumn = UserColumns._ID;
				res.tablesWithJoins = UserColumns.TABLE_NAME;
				res.orderBy = UserColumns.DEFAULT_ORDER;
				break;

			default:
				throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
		}

		switch (matchedId) {
			case URI_TYPE_PRODUCT_ID:
			case URI_TYPE_PURCHASED_ITEM_ID:
			case URI_TYPE_USER_ID:
				id = uri.getLastPathSegment();
		}
		if (id != null) {
			if (selection != null) {
				res.selection = res.table + "." + res.idColumn + "=" + id + " and (" + selection + ")";
			} else {
				res.selection = res.table + "." + res.idColumn + "=" + id;
			}
		} else {
			res.selection = selection;
		}
		return res;
	}
}
