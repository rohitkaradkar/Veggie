package com.greentopli.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;
import java.util.UUID;

/**
 * Created by rnztx on 17/10/16.
 */

@Entity public class DeliveryDetail {
	@Id private String id;
	@Index private long date;
	private int discount;

	public DeliveryDetail() {
	}

	public DeliveryDetail(long date, int discount) {
		this.id = UUID.randomUUID().toString();
		this.date = date;
		this.discount = discount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}
}
