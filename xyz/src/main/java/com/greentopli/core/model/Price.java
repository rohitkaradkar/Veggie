package com.greentopli.core.model;

/**
 * Created by rnztx on 9/10/16.
 */

public class Price {
	private int id;
	private long date;
	private boolean isUpdated = true;
	private double price;

	public Price(){}

	public Price(int id, long date, boolean isUpdated, double price) {
		this.id = id;
		this.date = date;
		this.isUpdated = isUpdated;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public long getDate() {
		return date;
	}

	public boolean isUpdated() {
		return isUpdated;
	}

	public double getPrice() {
		return price;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setUpdated(boolean updated) {
		isUpdated = updated;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
