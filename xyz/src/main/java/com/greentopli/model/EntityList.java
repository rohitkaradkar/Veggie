package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;

import java.util.List;

/**
 * Created by rnztx on 24/10/16.
 */

public class EntityList<T extends Object> {
	private List<T> items;

	public EntityList() {
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
