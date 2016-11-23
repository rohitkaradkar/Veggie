package com.greentopli.core.storage.helper;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.greentopli.core.storage.product.ProductColumns;
import com.greentopli.core.storage.product.ProductContentValues;
import com.greentopli.core.storage.product.ProductCursor;
import com.greentopli.core.storage.product.ProductSelection;
import com.greentopli.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by rnztx on 21/10/16.
 */

public class ProductDbHelper {
	private Context context;

	public ProductDbHelper(Context context) {
		this.context = context;
	}

	public long storeProduct(@NonNull Product product){
		ProductContentValues values = new ProductContentValues();
		values.putProductId(product.getId());
		values.putNameEnglish(product.getName_english());
		values.putNameHinglish(product.getName_hinglish());
		values.putImageUrl(product.getImageUrl());
		values.putVolume(product.getVolume().name());
		values.putType(product.getType().name());
		values.putPrice(product.getPrice());
		values.putVolumeSet(product.getVolumeSet());
		values.putMinVolume(product.getMinimumVolume());
		values.putMaxVolume(product.getMaximumVolume());
		values.putTime(product.getTime());
		Uri uri = values.insert(context.getContentResolver());
		return ContentUris.parseId(uri);
	}

	public void storeProducts(@NonNull List<Product> productList){
		for (Product item : productList){
			long id = storeProduct(item);
		}
	}

	public List<Product> getProducts(){
		ProductSelection selection = new ProductSelection();
		return getProducts(selection);
	}
	public List<Product> getProducts(String query){
		ProductSelection selection = new ProductSelection();
		selection.nameEnglishContains(query);
		return getProducts(selection);
	}
	public List<Product> getProducts(Product.Type productType){
		ProductSelection selection = new ProductSelection();
		selection.type(productType.name());
		return getProducts(selection);
	}
	private List<Product> getProducts(ProductSelection selection){
		ProductCursor cursor = selection.query(context.getContentResolver(), ProductColumns.ALL_COLUMNS);
		List<Product> list = new ArrayList<>();
		while (cursor.moveToNext()){
			Product product = getProductFromCursor(cursor);
			if (product!=null)
				list.add(product);
		}
		cursor.close();
		return list;
	}
	public List<Product> getProducts(List<String> product_ids){
		if(product_ids.size()<=0)
			return null;
		ProductSelection selection = new ProductSelection();
		String[] ids = product_ids.toArray(new String[product_ids.size()]);
		selection.productId(ids);
		return getProducts(selection);
	}

	public Product getProductFromCursor(ProductCursor cursor){
		Product product = new Product();
		product.setId(cursor.getProductId());
		product.setName_english(cursor.getNameEnglish());
		product.setName_hinglish(cursor.getNameHinglish());
		product.setImageUrl(cursor.getImageUrl());
		product.setPrice(cursor.getPrice());
		product.setVolumeSet(cursor.getVolumeSet());
		product.setMinimumVolume(cursor.getMinVolume());
		product.setMaximumVolume(cursor.getMaxVolume());
		product.setTime(cursor.getTime());
		// convert String to Enums & ensure string are in upper case
		product.setVolume(Product.Volume.valueOf(cursor.getVolume().toUpperCase(Locale.ENGLISH)));
		product.setType(Product.Type.valueOf(cursor.getType().toUpperCase(Locale.ENGLISH)));
		return product;
	}

	public Product getProduct(@NonNull String product_id){
		ProductSelection where = new ProductSelection();
		where.productId(product_id);
		Product product =  new Product();
		ProductCursor cursor = where.query(context);
		if (cursor.moveToNext())
			product = getProductFromCursor(cursor);
		cursor.close();
		return product;
	}
}
