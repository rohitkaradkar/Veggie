package com.greentopli.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rnztx on 28/10/16.
 */

public class OrderHistory {
	private String userId;
	private long orderDate;
	private int totalPrice;
	private int totalItems;
	private List<Product> products;

	public OrderHistory(String userId, long orderDate) {
		this.userId = userId;
		this.orderDate = orderDate;
		this.totalItems = 0;
		this.totalPrice = 0;
		this.products = new ArrayList<>();
	}

	public boolean isEmpty(){
		return (totalItems==0 || totalPrice ==0 || products.size()==0);
	}

	public String getUserId() {
		return userId;
	}

	public long getOrderDate() {
		return orderDate;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
