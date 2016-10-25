package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

/**
 * Created by rnztx on 19/10/16.
 */

@Entity public class UserOrders {
	@Index private List<PurchasedItem> items;

	public UserOrders() {
	}

	public UserOrders(List<PurchasedItem> items) {
		this.items = items;
	}

	public void setItems(List<PurchasedItem> items) {
		this.items = items;
	}

	public List<PurchasedItem> getItems() {

		return items;
	}
}
