package com.greentopli.core.storage.product;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.greentopli.core.storage.base.AbstractSelection;

/**
 * Selection for the {@code product} table.
 */
public class ProductSelection extends AbstractSelection<ProductSelection> {
	@Override
	protected Uri baseUri() {
		return ProductColumns.CONTENT_URI;
	}

	/**
	 * Query the given content resolver using this selection.
	 *
	 * @param contentResolver The content resolver to query.
	 * @param projection      A list of which columns to return. Passing null will return all columns, which is inefficient.
	 * @return A {@code ProductCursor} object, which is positioned before the first entry, or null.
	 */
	public ProductCursor query(ContentResolver contentResolver, String[] projection) {
		Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
		if (cursor == null) return null;
		return new ProductCursor(cursor);
	}

	/**
	 * Equivalent of calling {@code query(contentResolver, null)}.
	 */
	public ProductCursor query(ContentResolver contentResolver) {
		return query(contentResolver, null);
	}

	/**
	 * Query the given content resolver using this selection.
	 *
	 * @param context    The context to use for the query.
	 * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
	 * @return A {@code ProductCursor} object, which is positioned before the first entry, or null.
	 */
	public ProductCursor query(Context context, String[] projection) {
		Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
		if (cursor == null) return null;
		return new ProductCursor(cursor);
	}

	/**
	 * Equivalent of calling {@code query(context, null)}.
	 */
	public ProductCursor query(Context context) {
		return query(context, null);
	}


	public ProductSelection id(long... value) {
		addEquals("product." + ProductColumns._ID, toObjectArray(value));
		return this;
	}

	public ProductSelection idNot(long... value) {
		addNotEquals("product." + ProductColumns._ID, toObjectArray(value));
		return this;
	}

	public ProductSelection orderById(boolean desc) {
		orderBy("product." + ProductColumns._ID, desc);
		return this;
	}

	public ProductSelection orderById() {
		return orderById(false);
	}

	public ProductSelection productId(String... value) {
		addEquals(ProductColumns.PRODUCT_ID, value);
		return this;
	}

	public ProductSelection productIdNot(String... value) {
		addNotEquals(ProductColumns.PRODUCT_ID, value);
		return this;
	}

	public ProductSelection productIdLike(String... value) {
		addLike(ProductColumns.PRODUCT_ID, value);
		return this;
	}

	public ProductSelection productIdContains(String... value) {
		addContains(ProductColumns.PRODUCT_ID, value);
		return this;
	}

	public ProductSelection productIdStartsWith(String... value) {
		addStartsWith(ProductColumns.PRODUCT_ID, value);
		return this;
	}

	public ProductSelection productIdEndsWith(String... value) {
		addEndsWith(ProductColumns.PRODUCT_ID, value);
		return this;
	}

	public ProductSelection orderByProductId(boolean desc) {
		orderBy(ProductColumns.PRODUCT_ID, desc);
		return this;
	}

	public ProductSelection orderByProductId() {
		orderBy(ProductColumns.PRODUCT_ID, false);
		return this;
	}

	public ProductSelection nameEnglish(String... value) {
		addEquals(ProductColumns.NAME_ENGLISH, value);
		return this;
	}

	public ProductSelection nameEnglishNot(String... value) {
		addNotEquals(ProductColumns.NAME_ENGLISH, value);
		return this;
	}

	public ProductSelection nameEnglishLike(String... value) {
		addLike(ProductColumns.NAME_ENGLISH, value);
		return this;
	}

	public ProductSelection nameEnglishContains(String... value) {
		addContains(ProductColumns.NAME_ENGLISH, value);
		return this;
	}

	public ProductSelection nameEnglishStartsWith(String... value) {
		addStartsWith(ProductColumns.NAME_ENGLISH, value);
		return this;
	}

	public ProductSelection nameEnglishEndsWith(String... value) {
		addEndsWith(ProductColumns.NAME_ENGLISH, value);
		return this;
	}

	public ProductSelection orderByNameEnglish(boolean desc) {
		orderBy(ProductColumns.NAME_ENGLISH, desc);
		return this;
	}

	public ProductSelection orderByNameEnglish() {
		orderBy(ProductColumns.NAME_ENGLISH, false);
		return this;
	}

	public ProductSelection nameHinglish(String... value) {
		addEquals(ProductColumns.NAME_HINGLISH, value);
		return this;
	}

	public ProductSelection nameHinglishNot(String... value) {
		addNotEquals(ProductColumns.NAME_HINGLISH, value);
		return this;
	}

	public ProductSelection nameHinglishLike(String... value) {
		addLike(ProductColumns.NAME_HINGLISH, value);
		return this;
	}

	public ProductSelection nameHinglishContains(String... value) {
		addContains(ProductColumns.NAME_HINGLISH, value);
		return this;
	}

	public ProductSelection nameHinglishStartsWith(String... value) {
		addStartsWith(ProductColumns.NAME_HINGLISH, value);
		return this;
	}

	public ProductSelection nameHinglishEndsWith(String... value) {
		addEndsWith(ProductColumns.NAME_HINGLISH, value);
		return this;
	}

	public ProductSelection orderByNameHinglish(boolean desc) {
		orderBy(ProductColumns.NAME_HINGLISH, desc);
		return this;
	}

	public ProductSelection orderByNameHinglish() {
		orderBy(ProductColumns.NAME_HINGLISH, false);
		return this;
	}

	public ProductSelection imageUrl(String... value) {
		addEquals(ProductColumns.IMAGE_URL, value);
		return this;
	}

	public ProductSelection imageUrlNot(String... value) {
		addNotEquals(ProductColumns.IMAGE_URL, value);
		return this;
	}

	public ProductSelection imageUrlLike(String... value) {
		addLike(ProductColumns.IMAGE_URL, value);
		return this;
	}

	public ProductSelection imageUrlContains(String... value) {
		addContains(ProductColumns.IMAGE_URL, value);
		return this;
	}

	public ProductSelection imageUrlStartsWith(String... value) {
		addStartsWith(ProductColumns.IMAGE_URL, value);
		return this;
	}

	public ProductSelection imageUrlEndsWith(String... value) {
		addEndsWith(ProductColumns.IMAGE_URL, value);
		return this;
	}

	public ProductSelection orderByImageUrl(boolean desc) {
		orderBy(ProductColumns.IMAGE_URL, desc);
		return this;
	}

	public ProductSelection orderByImageUrl() {
		orderBy(ProductColumns.IMAGE_URL, false);
		return this;
	}

	public ProductSelection minVolume(int... value) {
		addEquals(ProductColumns.MIN_VOLUME, toObjectArray(value));
		return this;
	}

	public ProductSelection minVolumeNot(int... value) {
		addNotEquals(ProductColumns.MIN_VOLUME, toObjectArray(value));
		return this;
	}

	public ProductSelection minVolumeGt(int value) {
		addGreaterThan(ProductColumns.MIN_VOLUME, value);
		return this;
	}

	public ProductSelection minVolumeGtEq(int value) {
		addGreaterThanOrEquals(ProductColumns.MIN_VOLUME, value);
		return this;
	}

	public ProductSelection minVolumeLt(int value) {
		addLessThan(ProductColumns.MIN_VOLUME, value);
		return this;
	}

	public ProductSelection minVolumeLtEq(int value) {
		addLessThanOrEquals(ProductColumns.MIN_VOLUME, value);
		return this;
	}

	public ProductSelection orderByMinVolume(boolean desc) {
		orderBy(ProductColumns.MIN_VOLUME, desc);
		return this;
	}

	public ProductSelection orderByMinVolume() {
		orderBy(ProductColumns.MIN_VOLUME, false);
		return this;
	}

	public ProductSelection maxVolume(int... value) {
		addEquals(ProductColumns.MAX_VOLUME, toObjectArray(value));
		return this;
	}

	public ProductSelection maxVolumeNot(int... value) {
		addNotEquals(ProductColumns.MAX_VOLUME, toObjectArray(value));
		return this;
	}

	public ProductSelection maxVolumeGt(int value) {
		addGreaterThan(ProductColumns.MAX_VOLUME, value);
		return this;
	}

	public ProductSelection maxVolumeGtEq(int value) {
		addGreaterThanOrEquals(ProductColumns.MAX_VOLUME, value);
		return this;
	}

	public ProductSelection maxVolumeLt(int value) {
		addLessThan(ProductColumns.MAX_VOLUME, value);
		return this;
	}

	public ProductSelection maxVolumeLtEq(int value) {
		addLessThanOrEquals(ProductColumns.MAX_VOLUME, value);
		return this;
	}

	public ProductSelection orderByMaxVolume(boolean desc) {
		orderBy(ProductColumns.MAX_VOLUME, desc);
		return this;
	}

	public ProductSelection orderByMaxVolume() {
		orderBy(ProductColumns.MAX_VOLUME, false);
		return this;
	}

	public ProductSelection volumeSet(int... value) {
		addEquals(ProductColumns.VOLUME_SET, toObjectArray(value));
		return this;
	}

	public ProductSelection volumeSetNot(int... value) {
		addNotEquals(ProductColumns.VOLUME_SET, toObjectArray(value));
		return this;
	}

	public ProductSelection volumeSetGt(int value) {
		addGreaterThan(ProductColumns.VOLUME_SET, value);
		return this;
	}

	public ProductSelection volumeSetGtEq(int value) {
		addGreaterThanOrEquals(ProductColumns.VOLUME_SET, value);
		return this;
	}

	public ProductSelection volumeSetLt(int value) {
		addLessThan(ProductColumns.VOLUME_SET, value);
		return this;
	}

	public ProductSelection volumeSetLtEq(int value) {
		addLessThanOrEquals(ProductColumns.VOLUME_SET, value);
		return this;
	}

	public ProductSelection orderByVolumeSet(boolean desc) {
		orderBy(ProductColumns.VOLUME_SET, desc);
		return this;
	}

	public ProductSelection orderByVolumeSet() {
		orderBy(ProductColumns.VOLUME_SET, false);
		return this;
	}

	public ProductSelection price(int... value) {
		addEquals(ProductColumns.PRICE, toObjectArray(value));
		return this;
	}

	public ProductSelection priceNot(int... value) {
		addNotEquals(ProductColumns.PRICE, toObjectArray(value));
		return this;
	}

	public ProductSelection priceGt(int value) {
		addGreaterThan(ProductColumns.PRICE, value);
		return this;
	}

	public ProductSelection priceGtEq(int value) {
		addGreaterThanOrEquals(ProductColumns.PRICE, value);
		return this;
	}

	public ProductSelection priceLt(int value) {
		addLessThan(ProductColumns.PRICE, value);
		return this;
	}

	public ProductSelection priceLtEq(int value) {
		addLessThanOrEquals(ProductColumns.PRICE, value);
		return this;
	}

	public ProductSelection orderByPrice(boolean desc) {
		orderBy(ProductColumns.PRICE, desc);
		return this;
	}

	public ProductSelection orderByPrice() {
		orderBy(ProductColumns.PRICE, false);
		return this;
	}

	public ProductSelection time(long... value) {
		addEquals(ProductColumns.TIME, toObjectArray(value));
		return this;
	}

	public ProductSelection timeNot(long... value) {
		addNotEquals(ProductColumns.TIME, toObjectArray(value));
		return this;
	}

	public ProductSelection timeGt(long value) {
		addGreaterThan(ProductColumns.TIME, value);
		return this;
	}

	public ProductSelection timeGtEq(long value) {
		addGreaterThanOrEquals(ProductColumns.TIME, value);
		return this;
	}

	public ProductSelection timeLt(long value) {
		addLessThan(ProductColumns.TIME, value);
		return this;
	}

	public ProductSelection timeLtEq(long value) {
		addLessThanOrEquals(ProductColumns.TIME, value);
		return this;
	}

	public ProductSelection orderByTime(boolean desc) {
		orderBy(ProductColumns.TIME, desc);
		return this;
	}

	public ProductSelection orderByTime() {
		orderBy(ProductColumns.TIME, false);
		return this;
	}

	public ProductSelection type(String... value) {
		addEquals(ProductColumns.TYPE, value);
		return this;
	}

	public ProductSelection typeNot(String... value) {
		addNotEquals(ProductColumns.TYPE, value);
		return this;
	}

	public ProductSelection typeLike(String... value) {
		addLike(ProductColumns.TYPE, value);
		return this;
	}

	public ProductSelection typeContains(String... value) {
		addContains(ProductColumns.TYPE, value);
		return this;
	}

	public ProductSelection typeStartsWith(String... value) {
		addStartsWith(ProductColumns.TYPE, value);
		return this;
	}

	public ProductSelection typeEndsWith(String... value) {
		addEndsWith(ProductColumns.TYPE, value);
		return this;
	}

	public ProductSelection orderByType(boolean desc) {
		orderBy(ProductColumns.TYPE, desc);
		return this;
	}

	public ProductSelection orderByType() {
		orderBy(ProductColumns.TYPE, false);
		return this;
	}

	public ProductSelection volume(String... value) {
		addEquals(ProductColumns.VOLUME, value);
		return this;
	}

	public ProductSelection volumeNot(String... value) {
		addNotEquals(ProductColumns.VOLUME, value);
		return this;
	}

	public ProductSelection volumeLike(String... value) {
		addLike(ProductColumns.VOLUME, value);
		return this;
	}

	public ProductSelection volumeContains(String... value) {
		addContains(ProductColumns.VOLUME, value);
		return this;
	}

	public ProductSelection volumeStartsWith(String... value) {
		addStartsWith(ProductColumns.VOLUME, value);
		return this;
	}

	public ProductSelection volumeEndsWith(String... value) {
		addEndsWith(ProductColumns.VOLUME, value);
		return this;
	}

	public ProductSelection orderByVolume(boolean desc) {
		orderBy(ProductColumns.VOLUME, desc);
		return this;
	}

	public ProductSelection orderByVolume() {
		orderBy(ProductColumns.VOLUME, false);
		return this;
	}
}
