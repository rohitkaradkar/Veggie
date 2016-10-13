package com.greentopli.model;

import java.util.List;

/**
 * Created by rnztx on 13/10/16.
 */

public class ProductList {
	private List<ProductInfo> items;

	public ProductList() {
	}

	public List<ProductInfo> getItems() {
		return items;
	}

	public void setItems(List<ProductInfo> items) {
		this.items = items;
	}
}
