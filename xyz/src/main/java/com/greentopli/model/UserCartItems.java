package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.List;

/**
 * Created by rnztx on 19/10/16.
 */

@Entity public class UserCartItems {
	@Index private List<PurchasedItem> cartItems;

	public UserCartItems(List<PurchasedItem> cartItems) {
		this.cartItems = cartItems;
	}

	public UserCartItems() {
	}

	public List<PurchasedItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<PurchasedItem> cartItems) {
		this.cartItems = cartItems;
	}
}
