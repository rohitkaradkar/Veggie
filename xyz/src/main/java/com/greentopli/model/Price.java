package com.greentopli.model;

import java.util.UUID;

/**
 * Created by rnztx on 9/10/16.
 */

public class Price {
	private String id;
	private String product_id;
	private long date;
	private double price;

	public Price(){}

	public Price(String product_id, long date, double price) {
		this.product_id = product_id;
		this.date = date;
		this.price = price;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public long getDate() {
		return date;
	}

	public double getPrice() {
		return price;
	}
}
