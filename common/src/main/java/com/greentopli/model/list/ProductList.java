package com.greentopli.model.list;

import com.greentopli.model.Product;

import java.util.List;

/**
 * Created by rnztx on 13/10/16.
 */

public class ProductList {
	private List<Product> items;

	public ProductList() {
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}
}
